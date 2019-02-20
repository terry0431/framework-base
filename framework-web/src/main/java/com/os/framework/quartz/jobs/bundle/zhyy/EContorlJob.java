package com.os.framework.quartz.jobs.bundle.zhyy;

import com.os.framework.core.util.BinaryConversionUtil;
import com.os.framework.quartz.jobs.*;
import com.os.framework.web.handler.bundle.econtorl.EContorlAdapterInterface;
import com.os.framework.web.handler.bundle.econtorl.EContorlPoolModelJMRtuAdapter;
import com.os.framework.web.socket.NIOServer;
import com.os.framework.web.util.crc.CRCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * @author wangbo
 * @date 2017-4-20 10:46:32
 */
public class EContorlJob implements Job {

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException{
        EContorlAdapterInterface eContorlAdapterInterface = new EContorlPoolModelJMRtuAdapter();
        eContorlAdapterInterface.getState("hongyuan");
//        NIOServer nserver = new NIOServer();
//        JobKey jobKey = context.getJobDetail().getKey();
//        List<Integer> sblist = new ArrayList();
//        sblist.add(8 - 2); // 第一个设备 前2个通路被总控制占用了 池塘开关设备顺序从3开始
//        sblist.add(12); //第2个设备
//        String sb = "";
//        String td = "";
//        String flag = "0000";
//        String msg = "";
//        for (int i = 0; i < sblist.size(); i++) {//设备循环
//            if((i+1) < 10){
//                sb = "0" + (i+1) + "01";
//            }else{
//                sb = BinaryConversionUtil.intToHex(i + 1) + "01";
//            }
//            if(i == 0){
//                td = "00" + BinaryConversionUtil.intToHex(sblist.get(i) + 2 );
//            }else{
//                td = "00" + BinaryConversionUtil.intToHex(sblist.get(i) );
//            }
//
//            msg = sb + flag + td ;
//
//                byte[] bytes = CRCUtil.hexToByteArray(msg);
//                msg += CRCUtil.getCRC3(bytes);
//                try{
//                    nserver.doWrite("hongyuan", msg);
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//
//        }
//        System.out.println("SimpleJob says: " + jobKey + " executing at " + new Date());
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
}
