<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Map<String, Object>> yzclist = (List) request.getAttribute("yzclist");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>设备状态</title>
        <%@ include file="../../include/commontop.jsp" %>
        <style type="text/css">
            table  
            {  
                border-collapse: collapse;  
                margin: 0 auto;  
                text-align: center;  
            }  
            table td, table th  
            {  
                border: 1px solid #cad9ea;  
                color: #666;  
                height: 30px;  
            }  
            table thead th  
            {  
                background-color: #CCE8EB;  
                width: 100px;  
            }  

        </style>

        <script src="https://img.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
        <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
        <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
        <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
        <script type="text/javascript" src="/framework-web/js/common.js"></script>
    </head>

    <body>
        <div class="Area">
            <div class="mainRight">
                <ul class="Location Grayline mb10 Gray_bg">
                    <li class="parent"><img src="<%=path%>/common/style1/images/icon_14.gif" width="14" height="14"/>当前位置</li>
                    <li class="parent"><a>设备实时</a></li>
                    <li class="current"><a>设备实时</a></li>
                </ul>
                <div class="titlst">
                    <div class="box_c">
                        <h3 class="box_c_tit">查询</h3>
                        <ul class="search">
                            <li>
                                <span class="mlr5 left">养殖场：</span>
                                <div class="left mlr5">
                                    <select id="yzc" onchange="init_table()"></select>
                                </div>
                            </li>
                        </ul>
                        <ul class="search">
                            <li>
                                <span class="mlr5 left"><img src="<%=path%>/common/style1/images/icon_6.gif" /></span>
                                <div class="left mlr5">
                                    设备运行正常
                                </div>
                            </li>
                            <li>
                                <span class="mlr5 left"><img src="<%=path%>/common/style1/images/icon_9.gif" /></span>
                                <div class="left mlr5">
                                    设备故障
                                </div>
                            </li>
                        </ul>
                        <div id="sbjklist"></div>
                        
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        var sb_sbjk_url = "/framework-web/ifs/zhyy/shebeijiankong/getJkData";
        var sb_rmsg_url = "/framework-web/ifs/zhyy/shebeijiankong/getRemsg";
        var sb_smsg_url = "/framework-web/ifs/zhyy/shebeijiankong/sendCode";
        var yzcnum = 22;
        var retime = 1000 * 60;
        var baojing = new Object();
        $(function () {
            getYzcList(init_yzcsel);
        })
        function init_yzcsel(json) {
            var optstr = "";
            for (var i = 0; i < json.length; i++) {
                optstr += "<option value=\"" + json[i].id + "\">" + json[i].y_mingcheng + "</option>";
            }
            $("#yzc").append(optstr);
            init_table();
            getRemsg();
        }
        var sbinit_flag = false; //设备初次加载状态
        function init_table() {
            //alert("re load");
            $.ajax({
                url: sb_sbjk_url,
                type: 'POST',
                async: false,
                data: {yzcid: $("#yzc").val()},
                success: function (data) {
                    $("#sbjklist").empty();
                    var tdlb = "";
                    for(var attr in data){
                        var rtuid = attr.substring(0,8);
                        var dtime = attr.substring(9);
                        //alert(data[attr]);
//                        if(!sbinit_flag){
                        contentstr = "";
//                      }
                        contentstr += "<table  width=\"60%\"  align=\"left\">";
                        contentstr += "<tr><td colspan=\"5\">" + 
                                    "<p style=\"font-size:14px;\">智控设备:" + rtuid  + " 最新接收数据时间:" + dtime + " &nbsp;&nbsp;执行命令&nbsp;&nbsp;<input id=\"sendmsg_"+rtuid+"\" type=\"text\"  />&nbsp;&nbsp;"+
                                    "<span class=\"mlr5\" onclick=\"sendclick('"+rtuid+"')\"><b class=\"btn btn_Lg btn_yellow\">发送</b></span></p>" +
                                    " </td></tr>";
                        contentstr += "<tr>" ;
                        contentstr += "<td width=\"80px\" >接入类别</td><td width=\"80px\">接入号</td><td width=\"80px\">养殖区</td><td width=\"200px\">接入内容</td><td width=\"150px\">当前值</td>";
                        
                        for(var i=0;i < data[attr].length;i ++){
                            if(data[attr][i].s_leibie == "ai"){
                                tdlb = "采集";
                                contentstr += "<tr bgcolor=\"#0000FF\" >";
                            }else if(data[attr][i].s_leibie == "di"){
                                tdlb = "状态监测";
                                contentstr += "<tr bgcolor=\"#3399CC\">";
                            }else{
                                tdlb = "控制"; 
                                contentstr += "<tr bgcolor=\"#CC9933\">";
                            }
                            
                            contentstr += "<td style=\"color:#ffffff;\">"+tdlb+"</td><td style=\"color:#ffffff;\">"+data[attr][i].s_tongdao+"</td><td style=\"color:#ffffff;\">"+data[attr][i].zhyy_yangzhiqu_id+"</td><td style=\"color:#ffffff;\">"+data[attr][i].s_mingcheng+"</td><td style=\"color:#ffffff;\">"+data[attr][i].val+"</td>";
                            contentstr += "</tr>";
                        }
                        contentstr += "</table>";
                        $("#sbjklist").append(contentstr);
                    }

                    
//                    sbinit_flag = true;
                    
                    //                if(true){
                    //                    $("#sb_zhuangtai").append("<img src=\"<%=path%>/common/style1/images/icon_6.gif\" />");
                    //                }
                    setTimeout("init_table()", retime);
                },
                error: function (data) {
                    alert("error: " + JSON.stringify(data));
                }
            });

        }

        function getRemsg() {
            $.ajax({
                url: sb_rmsg_url,
                type: 'POST',
                async: false,
                data: {},
                success: function (data) {

                    if (data != "") {
                        $("#remsg").val($("#remsg").val() + data + "\r");
                    }
                    setTimeout("getRemsg()", 5000);
                },
                error: function (data) {
                    alert("error: " + JSON.stringify(data));
                }
            })
        }
        

        function onoffclick(donum, val) {

            sendMsg("#SETDO," + donum + "," + val + ";");
        }
        function sendclick(rtuid) {
            sendMsg(rtuid,$("#sendmsg_"+rtuid).val());
        }
        function sendMsg(rtuid,msg) {
            $.ajax({
                url: sb_smsg_url,
                type: 'POST',
                async: false,
                data: {rtuid: rtuid,
                    code: msg
                },
                success: function (data) {
                    alert(data);
                    if (data != "-1") {
                        $("#remsg").val($("#remsg").val() + "发送成功:" + data + "\r");
                    } else {
                        $("#remsg").val($("#remsg").val() + "发送失败" + "\r");
                    }
                },
                error: function (data) {
                    alert("error: " + JSON.stringify(data));
                }
            })
        }

        function playSound()
        {
            var borswer = window.navigator.userAgent.toLowerCase();
            if (borswer.indexOf("ie") >= 0)
            {
                //IE内核浏览器
                var strEmbed = '<embed name="embedPlay" src="http://www.gongqinglin.com/accessory/ding.wav" autostart="true" hidden="true" loop="false"></embed>';
                if ($("body").find("embed").length <= 0)
                    $("body").append(strEmbed);
                var embed = document.embedPlay;

                //浏览器不支持 audion，则使用 embed 播放
                embed.volume = 100;
                //embed.play();这个不需要
            } else
            {
                //非IE内核浏览器
                var strAudio = "<audio id='audioPlay' src='http://fjdx.sc.chinaz.com/files/download/sound1/201406/4611.wav' hidden='true'>";
                if ($("body").find("audio").length <= 0)
                    $("body").append(strAudio);
                var audio = document.getElementById("audioPlay");

                //浏览器支持 audion
                audio.play();
            }
        }
    </script>
</html>
