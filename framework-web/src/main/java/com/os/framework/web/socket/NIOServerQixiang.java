package com.os.framework.web.socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.core.util.StringUtil;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.os.framework.quartz.jobs.bundle.zhyy.RTUMod;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author wangbo
 */
public class NIOServerQixiang {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.ISO_8859_1;

    private static final int BUFFER_SIZE = 4096;

    private int port = 1000;

    public NIOServerQixiang(int port) {
        this.port = port;
    }

    public NIOServerQixiang() {
    }

    private static Map<String, SocketChannel> socketmap = new HashMap();
    public static String content = "";
    public static String remsg = "";

    private boolean server_flag = true;

    // 队列    数据队列  指令反馈队列  心跳队列
    ServerSocketChannel ssc = null;

    public void start() {
//        ServerSocketChannel ssc = null;
//        SocketChannel clientChannel = null;
//        ObjectMapper mapper = new ObjectMapper();
//        Map map = null;
//        String sessionstr = "";
//        MainDao dao = new MainDao();
//        try {
//            ssc = ServerSocketChannel.open();
//            ssc.configureBlocking(false);
//            ssc.bind(new InetSocketAddress(this.port));
//            Selector sel = Selector.open();
//            ssc.register(sel, SelectionKey.OP_ACCEPT);
//            while (server_flag) {
//                Set<SelectionKey> keySet = null;
//                try {
//                    sel.select();
//                    keySet = sel.selectedKeys();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    break;
//                }
//
//                for (Iterator<SelectionKey> it = keySet.iterator(); it.hasNext();) {
//                    SelectionKey sKey = it.next();
//                    it.remove();
//                    try {
//
//                        if (sKey.isAcceptable()) {
//                            ServerSocketChannel serChannel = (ServerSocketChannel) sKey.channel();
//
//                            clientChannel = serChannel.accept();
//                            clientChannel.configureBlocking(false);
//
//                            SelectionKey k2 = clientChannel.register(sel, SelectionKey.OP_READ);
//                            k2.attach(ByteBuffer.allocate(BUFFER_SIZE));
//                            System.out.println(sKey.attachment() + " - 已连接");
//                        } else if (sKey.isReadable()) {
////							SocketChannel channel = (SocketChannel) sKey.channel();
//                            clientChannel = (SocketChannel) sKey.channel();
////							socketmap.put("00010001", clientChannel);
//                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//                            //content = "";
//                            String tmp = "";
//                            try {
//                                int readBytes = clientChannel.read(byteBuffer);
//                                if (readBytes > 0) {
//                                    try{
//                                        byteBuffer.flip(); //为write()准备
//                                        byte[] bytes = new byte[byteBuffer.remaining()];
//                                        byteBuffer.get(bytes);
////                                        System.out.println("bytes  : " + bytes);
//                                        tmp = StringUtil.bytesToHexFun2(bytes);
//                                        
//                                        System.out.println("qixiangdata  : " + tmp);
//                                        tmp = tmp.replaceAll(" ", "");
//    //                                    System.out.println("id:" + tmp.substring(8,16));
//    //                                    System.out.println("session:" + tmp.substring(16,24));
//    //                                    System.out.println("length:" + tmp.substring(24,32));
//                                        String sbid = tmp.substring(8,16);
////                                        if(sessionstr.equals(tmp.substring(16,24))){
////                                            break;
////                                        }
//                                        sessionstr = tmp.substring(16,24);
//
//                                        float daqiya = StringUtil.HexString2int(tmp.substring(32,40)) ;
//                                        daqiya = daqiya / 1000;
//                                        float wendu = StringUtil.HexString2int(tmp.substring(40,48));
//                                        if(wendu >= 65536 ){
//                                            wendu = (wendu - 65536) * -1;
//                                        }
//                                        wendu = wendu / 10;
//                                        float shidu = StringUtil.HexString2int(tmp.substring(48,56));
//                                        shidu = shidu / 10;
//                                        float yuliang = StringUtil.HexString2int(tmp.substring(56,64));
//                                        int fx = StringUtil.HexString2int(tmp.substring(64,72));
//                                        String fxstr = "";
//                                        if( fx > 360 - 11.25 && fx <= 360 ||  fx <= 11.25){
//                                            fxstr = "S";
//                                        }else if(fx > 0 + 11.25 && fx <= 22.5 + 11.25){
//                                            fxstr = "SSW";
//                                        }else if(fx > 22.5 + 11.25 && fx <= 45 + 11.25){
//                                            fxstr = "SW";
//                                        }else if(fx > 45 + 11.25 && fx <= 67.5 + 11.25){
//                                            fxstr = "WSW";
//                                        }else if(fx > 67.5 + 11.25 && fx <= 90 + 11.25){
//                                            fxstr = "W";
//                                        }else if(fx > 90 + 11.25 && fx <= 112.5 + 11.25){
//                                            fxstr = "WNW";
//                                        }else if(fx > 112.5 + 11.25 && fx <= 135 + 11.25){
//                                            fxstr = "NW";
//                                        }else if(fx > 135 + 11.25 && fx <= 157.5 + 11.25){
//                                            fxstr = "NNW";
//                                        }else if(fx > 157.5 + 11.25 && fx <= 180 + 11.25){
//                                            fxstr = "N";
//                                        }else if(fx > 180 + 11.25 && fx <= 202.5){
//                                            fxstr = "NNE";
//                                        }else if(fx > 202.5 + 11.25 && fx <= 225 + 11.25){
//                                            fxstr = "NE";
//                                        }else if(fx > 225 + 11.25 && fx <= 247.5 + 11.25){
//                                            fxstr = "ENE";
//                                        }else if(fx > 247.5 + 11.25 && fx <= 270 + 11.25){
//                                            fxstr = "E";
//                                        }else if(fx > 270 + 11.25 && fx <= 292.5 + 11.25){
//                                            fxstr = "ESE";
//                                        }else if(fx > 292.5 + 11.25 && fx <= 315 + 11.25){
//                                            fxstr = "SE";
//                                        }else if(fx > 315 + 11.25 && fx <= 337.5 + 11.25){
//                                            fxstr = "SSE";
//                                        }
//                                      
//                                        float fengsu = StringUtil.HexString2int(tmp.substring(72,80));
//                                        fengsu = fengsu/10;
//                                        Calendar calendar = Calendar.getInstance();
//                                        int year = calendar.get(Calendar.YEAR);
//                                        int month = calendar.get(Calendar.MONTH) + 1;
//                                        int day = calendar.get(Calendar.DAY_OF_MONTH);
//                                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                                        map = new HashMap();
//                                        map.put("zhyy_shebei_id",tmp.substring(8,16) );
//                                        map.put("q_systime", new Date());
//                                        map.put("q_year", year);
//                                        map.put("q_month", month);
//                                        map.put("q_day", day);
//                                        map.put("q_hour",hour );
//                                        map.put("q_qiwen", wendu);
//                                        map.put("q_qiya", daqiya);
//                                        map.put("q_shidu", shidu);
//                                        map.put("q_yuliang", yuliang);
//                                        map.put("q_fengli",fengsu );
//                                        map.put("q_fengxiang", fx);
//                                        map.put("q_fengxiang_code", fxstr);
//                                        dao.save("zhyy_qixiangshuju", map, PKBean.NATIVE);
//    //                                  socketmap.put(rtumod.getRtuid(), clientChannel);
//                                        sKey.interestOps(SelectionKey.OP_READ);
//                                    }catch(Exception e){
//                                        e.printStackTrace();
//                                    }    
//                                } else if (readBytes == -1) {
//                                    sKey.cancel();
//                                    if (sKey.channel() != null) {
//                                        sKey.channel().close();
//                                    }
//                                }
//                                // 写完就把状态关注去掉，否则会一直触发写事件(改变自身关注事件)
//
//                            } catch (IOException i) {
//                                //如果捕获到该SelectionKey对应的Channel时出现了异常,即表明该Channel对于的Client出现了问题
//                                //所以从Selector中取消该SelectionKey的注册
//                                sKey.cancel();
//                                if (sKey.channel() != null) {
//                                    sKey.channel().close();
//                                }
//                            }
//                        }
//                    } catch (Exception e) {
//                        sKey.cancel();
//                        e.printStackTrace();
//                    }
//                    keySet.remove(sKey);
//                }
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (ssc != null) {
//                try {
//                    ssc.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

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

    public void doWrite(String rtuid, String msg) throws IOException {
        byte[] req = msg.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();
        if (socketmap.get(rtuid) != null) {
            socketmap.get(rtuid).write(byteBuffer);
        }
        //clientChannel.write(byteBuffer);
        if (!byteBuffer.hasRemaining()) {
            System.out.println("Send 2 Service successed");
        }
    }

    public static void main(String[] args) {
        new NIOServerQixiang().start();
    }

}
