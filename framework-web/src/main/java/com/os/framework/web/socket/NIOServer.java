package com.os.framework.web.socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.quartz.jobs.bundle.zhyy.RTUMod;
import com.os.framework.quartz.jobs.bundle.zhyy.ShuiCengKongzhi;
import com.os.framework.web.cache.zhyy.SystemCache;
import com.os.framework.web.service.zhyy.CheckRTUData;
import com.os.framework.web.service.zhyy.DataBuilder;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author wangbo
 */
public class NIOServer {

	private static final Charset DEFAULT_CHARSET = StandardCharsets.ISO_8859_1;

	private static final int BUFFER_SIZE = 4096;

	private int port = 1021;

	private int cachetime = 1000 * 60 * 60;

	private Map<String,Long> cachemap = new HashMap();

	public NIOServer(int port) {
		this.port = port;
	}

	public NIOServer() {
	}
	public static Map<String,RTUMod> rtumap = new HashMap();
	private static Map<String,SocketChannel> socketmap = new HashMap();
	public static String content = "";
	public static String remsg = "";
	public static Map<String,Boolean[]> bjinfo = new HashMap();
	public static Map<String ,List<RTUMod>> hourDateMap = new HashMap();
	private boolean server_flag = true;


	// 队列    数据队列  指令反馈队列  心跳队列
	ServerSocketChannel ssc = null;
	public void start() {
		ServerSocketChannel ssc = null;
		SocketChannel clientChannel = null;
		ObjectMapper mapper = new ObjectMapper();
		CheckRTUData checkRTUData = new CheckRTUData();
		List<Map<String, Object>> msglist = null;
		DataBuilder databuilder = new DataBuilder();
		List<RTUMod> hourdatalist = null;
		SystemCache syscache = new SystemCache();
		try {
			ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ssc.bind(new InetSocketAddress(this.port));
			Selector sel = Selector.open();
			ssc.register(sel, SelectionKey.OP_ACCEPT);
			while (server_flag) {
				Set<SelectionKey> keySet = null;
				try {
					sel.select();
					keySet = sel.selectedKeys();
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				for (Iterator<SelectionKey> it = keySet.iterator(); it.hasNext();) {
					SelectionKey sKey = it.next();
					it.remove();
					try {

						if (sKey.isAcceptable()) {
							ServerSocketChannel serChannel = (ServerSocketChannel) sKey.channel();

							clientChannel = serChannel.accept();
							clientChannel.configureBlocking(false);

							SelectionKey k2 = clientChannel.register(sel, SelectionKey.OP_READ);
							k2.attach(ByteBuffer.allocate(BUFFER_SIZE));
							System.out.println(sKey.attachment() + " - 已连接");
						} else if (sKey.isReadable()) {
							//							SocketChannel channel = (SocketChannel) sKey.channel();
							clientChannel = (SocketChannel) sKey.channel();
							//							socketmap.put("00010001", clientChannel);
							ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
							//content = "";
							String tmp = "";
							try {
								int readBytes = clientChannel.read(byteBuffer);
								if (readBytes > 0) {
									byteBuffer.flip(); //为write()准备
									byte[] bytes = new byte[byteBuffer.remaining()];
									byteBuffer.get(bytes);
									tmp += new String(bytes);
									if(tmp.indexOf("}]}") > -1){
										tmp = tmp.substring(0,tmp.indexOf("}]}")  + 3);
									}
									System.out.println("msg : " + tmp);
									//									mainframe.setStr(content);
									//回应客户端
									//doWrite(clientChannel);
									//if(tmp.indexOf("{") == 0){
									if (tmp != null && tmp.indexOf("{") > -1 && tmp.indexOf("RS232") == -1) {
										content = tmp;
										RTUMod rtumod = mapper.readValue(content.toLowerCase(), RTUMod.class);
										rtumod.setServertime(new Date());
										rtumod.formatVal();
										//保存十分钟间隔数据
										System.out.println(rtumod.getDatatime().substring(15,16));
										if( rtumod.getDatatime().substring(15,16).equals("0")){
											databuilder.builddata_10fz(rtumod);
										}
										//1小时数据缓存 为空 实例化 添加
										if(hourDateMap.get(rtumod.getRtuid()) == null){
											hourdatalist = new ArrayList();
											hourdatalist.add(rtumod);
											hourDateMap.put(rtumod.getRtuid(), hourdatalist);
										}else{ 
											//当前分钟数 大于 缓存最新分钟数 继续添加
											if( Integer.valueOf(rtumod.getDatatime().substring(14,16)) > 
											//获取小时数据列表中最后一条记录的时间中的分钟部分
											Integer.parseInt( hourDateMap.get(rtumod.getRtuid()).get(hourDateMap.get(rtumod.getRtuid()).size() - 1).getDatatime().substring(14,16))
													){
												hourDateMap.get(rtumod.getRtuid()).add(rtumod);
											}else{
												//保存数据 清空缓存 重新开始添加
												databuilder.builddata(rtumod.getRtuid());
												//保存1小时平局值水质数据
												hourDateMap.remove(rtumod.getRtuid());
												hourdatalist = new ArrayList();
												hourdatalist.add(rtumod);
												hourDateMap.put(rtumod.getRtuid(), hourdatalist);
											}
										}
										rtumod.formatVal();

										System.out.println("new databuilder");
										try{
											databuilder.saveRtuData(rtumod);
											System.out.println("===========builddata=======");
											//bjinfo = checkRTUData.check(rtumod,bjinfo);
											checkRTUData.check(rtumod);
											System.out.println("===========check data =======");
											final String rtuid =   rtumod.getRtuid();
											if(rtumap.get(rtumod.getRtuid()) == null){
												new Thread(new Runnable() {
													@Override
													public void run() {
														// TODO Auto-generated method stub
														try {
															ShuiCengKongzhi scsz = new ShuiCengKongzhi();
															scsz.initShuicengDo(rtuid);
														} catch (Exception e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													}
												}).start();
											}
											System.out.println("===========init shuiceng=======");
											//检查水深与di状态
											if(rtumod.getRtuid().equals("00010001")){
												//满足条件开关打开
												//if(rtumod.getRtudi().get(2).get("va").toString().equals("1") &&  (Double)rtumod.getRtuai().get(7).get("va") > 0.5){
												System.out.println("========外海采集设备开关控制=============");
												if((Double)rtumod.getRtuai().get(7).get("va") > 0.5){
													if(rtumod.getRtudo().get(5).get("va").toString().equals("0")){
														doWrite("00010001","#SETDO,6,ON;");
													}
												}else{
													//未满足条件关闭开关
													if(rtumod.getRtudo().get(5).get("va").toString().equals("1")){
														doWrite("00010001","#SETDO,6,OFF;");
													}
												}
											}
										}catch(Exception e){
											e.printStackTrace();
										}
										rtumap.put(rtumod.getRtuid(), rtumod);
										//                                                                            msglist = checkRTUData.check(rtumod);
										//                                                                            if(msglist != null && msglist.size() > 0 ){
										//                                                                                for(Map m : msglist){
										//                                                                                    System.out.println(m.get("baojingneirong"));
										//                                                                                }
										//                                                                            }
										socketmap.put(rtumod.getRtuid(), clientChannel);
									}else if (tmp != null && tmp.indexOf("{") == -1)  {
										if(remsg.length() > 512){
											remsg = "";
										}
										remsg += tmp;
										System.out.println("remsg:" + remsg);
									}
									// }
									sKey.interestOps(SelectionKey.OP_READ);

								}else if(readBytes == -1){
									sKey.cancel();
									if (sKey.channel() != null) {
										sKey.channel().close();
									}
								}
								// 写完就把状态关注去掉，否则会一直触发写事件(改变自身关注事件)

							} catch (Exception i) {
								//如果捕获到该SelectionKey对应的Channel时出现了异常,即表明该Channel对于的Client出现了问题
								//所以从Selector中取消该SelectionKey的注册
								i.printStackTrace();
								sKey.cancel();
								if (sKey.channel() != null) {
									sKey.channel().close();
								}
							}
						}
					} catch (Exception e) {
						sKey.cancel();
						e.printStackTrace();
					}
					keySet.remove(sKey);
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ssc != null) {
				try {
					ssc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void shutdown() {
		server_flag = false;
		if (ssc != null) {
			try {
				ssc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void doWrite(String rtuid,String msg) throws IOException {
		byte[] req = msg.getBytes();
		ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
		byteBuffer.put(req);
		byteBuffer.flip();
		if(socketmap.get(rtuid) != null){
			socketmap.get(rtuid).write(byteBuffer);
		}
		//clientChannel.write(byteBuffer);
		if (!byteBuffer.hasRemaining()) {
			System.out.println("Send 2 "+rtuid+" " + msg + " successed");
		}
	}

	public static void main(String[] args) {
		new NIOServer().start();
	}

}