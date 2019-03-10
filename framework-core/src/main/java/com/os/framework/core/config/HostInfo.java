package com.os.framework.core.config;

/**
 * @program: framework-base
 * @description: 主机信息设置
 * @author: wangbo
 * @create: 2019-02-25 20:17
 **/
public class HostInfo {
    //接收设备消息端口
    public static final Integer RTU_POST = 1021;
    public static final Integer JMRTU_POST = 10021;
    //web通信端口
    public static final Integer WEB_POST = 10201;
    //transceriver通信端口
    public static final Integer TRANSCERVIER_POST = 10301;

    public static final String HOST_NAME = "localhost";
    public static final String SEPARATOR = "@";

}
