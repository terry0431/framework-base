package com.os.framewrok.transceriver.mp;

//import com.os.framework.web.handler.zhyy.EContorlAdapterInterface;
//import com.os.framework.web.handler.zhyy.EContorlPoolModelJMRtuAdapter;


/**
 * @program: framework-base
 * @description: 消息处理器
 * @author: wangbo
 * @create: 2019-03-02 10:03
 **/
public class MsgHandler {
    /**
     * 	消息分发
     */
//    public void msgDistribution() {
//        EContorlAdapterInterface contorlPoolModelJMRtuAdapter = new EContorlPoolModelJMRtuAdapter();
//        for(String key : MsgDelayQueue.jmrtuqueue.keySet()) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // TODO Auto-generated method stub
//                    Map<String,String> m = null;
//                    for(;;) {
//                        try {
//                            if(MsgDelayQueue.jmrtuqueue.get(key) != null ) {
//                                m = MsgDelayQueue.jmrtuqueue.get(key).take().getMsg();
//                                //TODO 改为 本模块的 server channel send
//                                contorlPoolModelJMRtuAdapter.sendMsg(key, m.get(key));
//                                Thread.sleep(300);
//                            }
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();
//        }
//    }
}
