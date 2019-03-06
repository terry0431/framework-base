package com.os.framework.web.handler.zhyy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.os.framework.quartz.jobs.bundle.zhyy.ShuiCengKongzhi;
import com.os.framework.vo.transceriver.RtuEquipment;
import com.os.framework.web.cache.zhyy.SystemCache;
import com.os.framework.web.service.zhyy.CheckRTUData;
import com.os.framework.web.service.zhyy.DataBuilder;
import com.os.framework.web.subscribe.handler.MsgHanlder;

import java.util.*;

/**
 * @program: framework-base
 * @description:RTU消息处理类
 * @author: wangbo
 * @create: 2019-03-03 21:29
 **/
public class RTUHandler {
    private int cachetime = 1000 * 60 * 60;
    private Map<String, Long> cachemap = new HashMap();

    public static Map<String, RtuEquipment> rtumap = new HashMap();
    //    private static Map<String, SocketChannel> socketmap = new HashMap();
    public static String content = "";
    public static String remsg = "";
    public static Map<String, Boolean[]> bjinfo = new HashMap();
    public static Map<String, List<RtuEquipment>> hourDateMap = new HashMap();
    private boolean server_flag = true;

    CheckRTUData checkRTUData = new CheckRTUData();
    List<Map<String, Object>> msglist = null;
    DataBuilder databuilder = new DataBuilder();
    List<RtuEquipment> hourdatalist = null;
    SystemCache syscache = new SystemCache();

    public void builderMsg(String msg) {
        //TODO 数据量大时 该方法加同步锁 方法内用线程池处理消息
        if (msg != null && msg.indexOf("{") > -1 && msg.indexOf("RS232") == -1) {
            content = msg;
            RtuEquipment rtumod = JSON.parseObject(msg.toString(), new TypeReference<RtuEquipment>() {
            });
            rtumod.setServertime(new Date());
            rtumod.formatVal();
            //保存十分钟间隔数据
            System.out.println(rtumod.getDatatime().substring(15, 16));
            if (rtumod.getDatatime().substring(15, 16).equals("0")) {
                databuilder.builddata_10fz(rtumod);
            }
            //1小时数据缓存 为空 实例化 添加
            if (hourDateMap.get(rtumod.getRtuid()) == null) {
                hourdatalist = new ArrayList();
                hourdatalist.add(rtumod);
                hourDateMap.put(rtumod.getRtuid(), hourdatalist);
            } else {
                //当前分钟数 大于 缓存最新分钟数 继续添加
                if (Integer.valueOf(rtumod.getDatatime().substring(14, 16)) >
                        //获取小时数据列表中最后一条记录的时间中的分钟部分
                        Integer.parseInt(hourDateMap.get(rtumod.getRtuid()).get(hourDateMap.get(rtumod.getRtuid()).size() - 1).getDatatime().substring(14, 16))
                ) {
                    hourDateMap.get(rtumod.getRtuid()).add(rtumod);
                } else {
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
            try {
                databuilder.saveRtuData(rtumod);
                System.out.println("===========builddata=======");
                //bjinfo = checkRTUData.check(rtumod,bjinfo);
                checkRTUData.check(rtumod);
                System.out.println("===========check data =======");
                final String rtuid = rtumod.getRtuid();
                if (rtumap.get(rtumod.getRtuid()) == null) {
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
                if (rtumod.getRtuid().equals("00010001")) {
                    //满足条件开关打开
                    //if(rtumod.getRtudi().get(2).get("va").toString().equals("1") &&  (Double)rtumod.getRtuai().get(7).get("va") > 0.5){
                    System.out.println("========外海采集设备开关控制=============");
                    if ((Double) rtumod.getRtuai().get(7).get("va") > 0.5) {
                        if (rtumod.getRtudo().get(5).get("va").toString().equals("0")) {
//                        doWrite("00010001", "#SETDO,6,ON;");
                            //添加指令消息到队列
                        }
                    } else {
                        //未满足条件关闭开关
                        if (rtumod.getRtudo().get(5).get("va").toString().equals("1")) {
//                        doWrite("00010001", "#SETDO,6,OFF;");
                            //添加指令消息到队列
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            rtumap.put(rtumod.getRtuid(), rtumod);
            //msglist = checkRTUData.check(rtumod);
            //if(msglist != null && msglist.size() > 0 ){
            //for(Map m : msglist){
            //System.out.println(m.get("baojingneirong"));
            //}
            //}

        } else if (msg != null && msg.indexOf("{") == -1) {
            if (remsg.length() > 512) {
                remsg = "";
            }
            remsg += msg;
            System.out.println("remsg:" + remsg);
        }
    }

    public static void sendMsg(String rtuid,String msg){
        MsgHanlder.sendMsg(rtuid,msg);
    }
}