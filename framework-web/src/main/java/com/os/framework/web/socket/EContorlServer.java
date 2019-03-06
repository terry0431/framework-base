package com.os.framework.web.socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.core.util.BinaryConversionUtil;
import com.os.framework.db.dao.MainDao;
import com.os.framework.web.intef.zhyy.WaterConveyanceIntfc;
import com.os.framework.core.util.crc.CRCUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author wangbo
 */
public class EContorlServer {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.ISO_8859_1;

    private static final int BUFFER_SIZE = 4096;

    private int port = 10021;
    
    public static String data = "";
    public EContorlServer(int port) {
        this.port = port;
    }
    
    public EContorlServer() {
    }

    private static Map<String, SocketChannel> socketmap = new HashMap();
    public static String content = "";
    public static String remsg = "";
    
    private boolean server_flag = true;

    // 队列    数据队列  指令反馈队列  心跳队列
    private static ServerSocketChannel ssc = null;

    public void start() {
        ServerSocketChannel ssc = null;
        SocketChannel clientChannel = null;
        ObjectMapper mapper = new ObjectMapper();
        Map map = null;
        String sessionstr = "";
        MainDao dao = new MainDao();
        WaterConveyanceIntfc ecinf = new WaterConveyanceIntfc();
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
                            data +=  "conn ----- <br/>";
                            System.out.println(sKey.attachment() + " - 已连接");
                            socketmap.put("hongyuan", clientChannel);
                        } else if (sKey.isReadable()) {
//							SocketChannel channel = (SocketChannel) sKey.channel();
                            clientChannel = (SocketChannel) sKey.channel();
//							socketmap.put("00010001", clientChannel);
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            //content = "";
                            String tmp = "";
                            String str2 = "";
                            int zj;
                            try {
                                int readBytes = clientChannel.read(byteBuffer);
                                if (readBytes > 0) {
                                    try{
                                        byteBuffer.flip(); //为write()准备
                                        byte[] bytes = new byte[byteBuffer.remaining()];
                                        byteBuffer.get(bytes);
                                        tmp = BinaryConversionUtil.bytesToHex(bytes);
                                        //tmp += new String(bytes);
                                        //读取开关命令
                                        if(tmp.substring(2,4).equals("01")){
                                            //0102020000b9b8
                                            
                                            //010101005188
                                            //0201020000fdfc
                                            //0201023f00ec0c
                                            zj = Integer.parseInt( tmp.substring(0,2) ) - 1;
                                            str2 = BinaryConversionUtil.hexStringToBinaryString(tmp.substring(6,tmp.length() - 4));
                                            ecinf.updateSbflag(zj, str2);
//                                            str16 = intToHex(Integer.parseInt(tmp.substring(6,10)) );
                                            
                                            System.out.println(" zj："+ zj + " str2  : " + tmp.substring(6,10) + " 2:" + str2  );
                                        }
                                        if(data.length() > 1024){
                                            data = "";
                                        }
                                        data += tmp + "<br/>";
                                        System.out.println("data  : " + tmp);
                                        socketmap.put("hongyuan", clientChannel);
                                        sKey.interestOps(SelectionKey.OP_READ);
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }    
                                } else if (readBytes == -1) {
                                    sKey.cancel();
                                    if (sKey.channel() != null) {
                                        sKey.channel().close();
                                    }
                                }
                                // 写完就把状态关注去掉，否则会一直触发写事件(改变自身关注事件)

                            } catch (IOException i) {
                                //如果捕获到该SelectionKey对应的Channel时出现了异常,即表明该Channel对于的Client出现了问题
                                //所以从Selector中取消该SelectionKey的注册
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

    public static synchronized void doWrite(String rtuid, String msg) throws Exception {
        byte[] req = CRCUtil.hexToByteArray(msg);
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();
        if (socketmap.get(rtuid) != null) {
            socketmap.get(rtuid).write(byteBuffer);
        }else{
            System.out.println("Send 2 ====   " + msg + "   =====  Service fail");
        }
        //clientChannel.write(byteBuffer);
        if (!byteBuffer.hasRemaining()) {
            System.out.println("Send 2 ====   " + msg + "   =====  Service successed");
        }
        Thread.sleep(500);
    }
//    private static String intToHex(int n) {
//        StringBuffer s = new StringBuffer();
//        String a;
//        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
//        while(n != 0){
//            s = s.append(b[n%16]);
//            n = n/16;            
//        }
//        a = s.reverse().toString();
//        if(a.length() == 1){
//            a = "0" + a;
//        }
//        return a;
//    }
    public static void main(String[] args) {
        new EContorlServer().start();
    }
}
