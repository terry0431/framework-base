<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
List<Map<String,Object>> t_equipment_pondList = (List)request.getAttribute("t_equipment_pondList");
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
                                <select><option>固德水产有限公司</option></select>
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
                            <span class="mlr5 left"><img src="<%=path%>/common/style1/images/icon_20.gif" /></span>
                            <div class="left mlr5">
                                获取设备状况信息失败
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left"><img src="<%=path%>/common/style1/images/icon_9.gif" /></span>
                            <div class="left mlr5">
                                设备故障
                            </div>
                        </li>
                    </ul>
                    <h3 align="center" style="font-size: 16px;font-weight:bold;">养水机</h1>
                    <table  id="datatable_ysj" width="1800px"  align="center">
                    </table>
                </div>
        </div>
    </div>
</div>
    </body>
<script type="text/javascript">
    var sb_sbjk_url = "/framework-web/ifs/zhyy/shebeijiankong/nowlist";
    var yzcnum = 22;
    var retime = 1000 * 10;
    var baojing = new Object();
    //var alltime = 24 * 60 * 60 * 1000;

    //var lasttime = 0;
    //var rows = 20;
    function init_table() {
        //alert("re load");
        $.ajax({
            url: sb_sbjk_url,
            type: 'POST',
            async: false,
            data: {yzcid:"1"},
            success: function (data) {
                var js = 0;
                var iconsrc;
                $("#datatable_ysj").empty();
                var nowtime = new Date();
                var str = "<tr><th width=\"90px\">采集时间</th>"
                for (var i = 0; i < yzcnum; i++) {
                    str += "<th>" + (i + 1) + "号设备</th>";
                }
                str += "</tr>";
                
                for(i = data.length - 1;i >= 0;i --){
                //for (i = 24; i > 0; i--) {
                //for(var key in data){
                    //var thistime = new Date(zhuanhuan(i * cjtime, 'yyyy/MM/dd hh:mm:ss'));
                    //if (nowtime.getTime() > thistime.getTime()) {
                        //str += "<tr><td >" + zhuanhuan(i * cjtime, 'hh:mm:ss') + "</td>";
                        str += "<tr><td >" + (i + 1) + "点</td>";
                        if(data[i] != null && data[i].length > 0){
                            for (j = 0; j < data[i].length; j++) {
                                if (data[i][j].s_flag == "1" || data[i][j].s_dianliu == "0") {
                                    iconsrc = iconImg.deleteIcon;
                                    if(baojing[i + "_" + j] == null){
                                        playSound();
                                        //baojing = 1;
                                        baojing[i + "_" + j] = 1;
                                    }
                                } else if(data[i][j].s_flag == "0"){
                                    iconsrc = iconImg.checkBoxIcon;
                                    iconsrc = iconImg.checkBoxIcon + "<br/>"+data[i][j].s_dianliu ;
                                } else{
                                    iconsrc = iconImg.whIcon;
                                }

                                str += "<td>" + iconsrc + "</td>";
                            }
                        }else{
                            str += "<td align=\"center\" colspan=\"22\">暂无数据</td>";
                        }
                        str += "</tr>";

                        //lasttime = i * cjtime;
                        js++;
                        
                }
                $("#datatable_ysj").append(str);
                setTimeout("init_table()", retime);
            },
            error: function (data) {
                alert("error: " + JSON.stringify(data));
            }
        });

    }
    init_table();
    
    function playSound()
    {
        var borswer = window.navigator.userAgent.toLowerCase();
      if ( borswer.indexOf( "ie" ) >= 0 )
      {
        //IE内核浏览器
        var strEmbed = '<embed name="embedPlay" src="http://www.gongqinglin.com/accessory/ding.wav" autostart="true" hidden="true" loop="false"></embed>';
        if ( $( "body" ).find( "embed" ).length <= 0 )
          $( "body" ).append( strEmbed );
        var embed = document.embedPlay;

        //浏览器不支持 audion，则使用 embed 播放
        embed.volume = 100;
        //embed.play();这个不需要
      } else
      {
        //非IE内核浏览器
        var strAudio = "<audio id='audioPlay' src='http://fjdx.sc.chinaz.com/files/download/sound1/201406/4611.wav' hidden='true'>";
        if ( $( "body" ).find( "audio" ).length <= 0 )
          $( "body" ).append( strAudio );
        var audio = document.getElementById( "audioPlay" );

        //浏览器支持 audion
        audio.play();
      }
    }
</script>
</html>
