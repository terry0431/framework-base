<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
    <head>
        <%
            //response.addHeader("P3P", "CP=CAO PSA OUR");
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
//            String userId = request.getAttribute("userId").toString();
        %>
        <title>在线监测</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="apple-mobile-web-app-capable" content="yes"/>
        <link href="<%=path%>/common/zhyy/main/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
        <link href="<%=path%>/common/zhyy/main/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
        <link href="<%=path%>/common/zhyy/main/css/styles.css" type="text/css" rel="stylesheet"/>
        <link href="<%=path%>/common/zhyy/main/css/main.css" type="text/css" rel="stylesheet"/>
        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
        <script type="text/javascript" src="<%=path%>/js/lhgdialog/lhgdialog.js"></script>
        
<!--        <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>-->
        <script src="http://code.highcharts.com/highcharts.js"></script>
        
        <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
        <script src="https://img.hcharts.cn/highcharts/highcharts-more.js"></script>
        <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
        <script src="<%=path%>/js/DateUtil.js"></script>
        <script src="<%=path%>/js/config.js"></script>
        <script type="text/javascript" src="<%=path%>/js/laydate/laydate.js"></script>
        <script src="<%=path%>/js/zhyy/char_shuizhi.js"></script>
        <style type="text/css">
        <!--
        .STYLE1 {
                color: #FFFFFF;
                font-size: 20px;
                font-weight: bold;
        }
        .STYLE3 {font-size: 20px; font-weight: bold; }
        .STYLE4 {font-size: 36px;color: #006633}
        .STYLE5 {}
        .STYLE6 {font-size: 18px}
        -->
        </style>
    </head>
    <body>
        <table width="100%" height="" border="0">
            <tr>
              <td width="100%"  height="30px" bgcolor="#0033FF">
                  <span class="STYLE1">大连固德水产养殖有限公司—池塘实时在线监测</span>
                  <span style="color: #ffffff;float:right;" align="right"><a target="_blank" href="<%=path%>/index">进入后台管理系统</a></span>
              </td>
            </tr>
            <tr>
                <td valign="top">
                  <table width="100%"  border="0">
                <tr>
                  <td width="300px" height="35px" bgcolor="#33CCFF"><div id="time_qxzx" align="center" class="STYLE3"></div></td>
                  <td bgcolor="#33CCFF"><div align="center" class="STYLE3">池&nbsp;&nbsp;塘&nbsp;&nbsp;水&nbsp;&nbsp;质&nbsp;&nbsp;与&nbsp;&nbsp;设&nbsp;&nbsp;备&nbsp;&nbsp;状&nbsp;&nbsp;态&nbsp;&nbsp;实&nbsp;&nbsp;时</div></td>
                  <td width="460px" bgcolor="#33CCFF"><div align="center" class="STYLE3">外海水质</div></td>
                </tr>
                <tr>
                  <td><table width="300" height="" border="0">
                    <tr>
                      <td height="135px" width="160px"><img src="<%=path%>/images/zhyy/qx/wendu.png" width="160px" height="135px" border="0" /></td>
                      <td width="124px" bgcolor="#00CCCC"><div align="center" class="STYLE5">
                        <p class="STYLE4"><strong>气温</strong></p>
                        <p class="STYLE4"><strong id="data_qx_qw"></strong></p>
                      </div></td>
                    </tr>
                    <tr>
                      <td height="135px"><img src="<%=path%>/images/zhyy/qx/fengli.png" width="160px" height="135px" border="0" /></td>
                      <td bgcolor="#00CCCC"><div align="center" class="STYLE5">
                        <p class="STYLE4"><strong>风力</strong></p>
                        <p class="STYLE4"><strong id="data_qx_fl"></strong></p>
                      </div></td>
                    </tr>
                    <tr>
                      <td height="135px"><img src="<%=path%>/images/zhyy/qx/fengxiang.png" width="160px" height="135px" border="0" /></td>
                      <td bgcolor="#00CCCC"><div align="center" class="STYLE5">
                        <p class="STYLE4"><strong>风向</strong></p>
                        <p class="STYLE4"><strong id="data_qx_fx"></strong></p>
                      </div></td>
                    </tr>
                    <tr>
                      <td height="135px"><img src="<%=path%>/images/zhyy/qx/qiya.png" width="160px" height="135px" border="0" /></td>
                      <td bgcolor="#00CCCC"><div align="center" class="STYLE5">
                        <p class="STYLE4"><strong>气压</strong></p>
                        <p class="STYLE4"><strong id="data_qx_qy"></strong></p>
                      </div></td>
                    </tr>
                    <tr>
                      <td height="135px"><img src="<%=path%>/images/zhyy/qx/shidu.png" width="160px" height="135px" border="0"  /></td>
                      <td bgcolor="#00CCCC"><div align="center" class="STYLE5">
                        <p class="STYLE4"><strong>湿度</strong></p>
                        <p class="STYLE4"><strong id="data_qx_sd"></strong></p>
                      </div></td>
                    </tr>
                    <tr>
                      <td height="135px"><img src="<%=path%>/images/zhyy/qx/jiangyu.png" width="160px" height="135px" /></td>
                      <td bgcolor="#00CCCC"><div align="center" class="STYLE5">
                        <p class="STYLE4"><strong>降雨量</strong></p>
                        <p class="STYLE4"><strong id="data_qx_jy"></strong></p>
                      </div></td>
                    </tr>
                  </table></td>
                  <td valign="top">
                      <table id="data_table" width="100%" bgcolor="#00CCCC" >
                          <tr bgcolor="#00CCCC" align="center" >
                              <td>池号</td><td>水深</td><td>水温</td><td>盐度</td><td>溶解氧</td><td>pH</td><td>水质仪</td><td>养水机1</td><td>养水机2</td><td>进水阀</td><td>排水阀</td>
                          </tr>
                          <tbody id="tbody_data" ></tbody>
                      </table>
                  </td>
                  <td><table height="" width="460" border="0">
                    <tr>
                      <td height="210px"><div id="char_shuiwen" style="min-width: 220px; max-width: 220px; height: 220px; " class=""></div></td>
                      <td><div id="char_yandu" style="min-width: 220px; max-width: 220px; height: 220px; " class=""></div></td>
                    </tr>
                    <tr>
                      <td bgcolor="#00CCCC" height="60px"><div align="center" class="STYLE4" id="data_wh_sw">暂无数据</div></td>
                      <td bgcolor="#00CCCC"><div align="center" class="STYLE4" id="data_wh_yd">暂无数据</div></td>
                    </tr>
                    <tr>
                      <td height="210px"><div id="char_d02" style="min-width: 220px; max-width: 220px; height: 220px; " class=""></div></td>
                      <td><div id="char_ph" style="min-width: 220px; max-width: 220px; height: 220px; " class=""></div></td>
                    </tr>
                    <tr>
                      <td bgcolor="#00CCCC" height="60px"><div align="center" class="STYLE4" id="data_wh_d02">DO2</div></td>
                      <td bgcolor="#00CCCC"><div align="center" class="STYLE4" id="data_wh_ph">暂无数据</div></td>
                    </tr>
                    <tr>
                      <td height="210px" colspan="2" align="center"><div id="char_chaoxi" style="min-width: 220px; max-width: 220px; height: 220px; " class=""></div></td>
                      </tr>
                    <tr>
                      <td bgcolor="#00CCCC" height="60px" colspan="2"><div align="center" class="STYLE4" id="data_wh_cx">暂无数据</div></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
          </table>
          <div id="div_content" style="position: fixed;float:left;width:700px;height:230px;right: 45%;top: 30%;background: #0097BD;color:#FFFFFF;font-size: 12px;">
            <a href="javascript:closeContent()"><img src="<%=path%>/images/close.jpg"></a>
            选择日期:<input type="text" id="riqi" readonly="readonly" />
            <a href="javascript:loadlist('1')">本周</a>
            <a href="javascript:loadlist('2')">上周</a>
            <a href="javascript:loadlist('3')">本月</a>
            <a href="javascript:loadlist('4')">上月</a>
            <div id="div_chart" style="float:left;width:700px;height:200px;"  ></div>
          </div>
    </body>
    <script type="text/javascript">
        //世界时间 8小时时差
        $(function () {
            $("#div_content").hide();
            laydate.render({
                    elem: '#riqi' //指定元素
                    ,max: 0
                    ,value:new Date()
                });
            Highcharts.setOptions({global: {useUTC: false}});
            //水温
            //loadlist();
            //loadYzq();
//            loadlist();
            showYzq();
            getTqData();
            getWhSzData();
            setInterval(getWhSzData, 30000);
            setInterval(getTqData, 30000);
            setInterval(showYzq, 30000);
        })
        
        var loadyzc_url = "<%=path%>/ifs/zhyy/yangzhiqu/getYzqZhuangtai";
        function loadYzq(){
            $.ajax({
                url: loadyzc_url,
                data: {yzcid: "0001"
                },
                type: 'POST',
                timeout: fw_config.ajax_timeout,
                success: function (json, textStatus) {
                    
                    for (var key in json) {
                        if(json[key][1] ){
                            $("#sbzt_" + key).css("background-color","rgba(255, 0, 0, 1)");
                        }else{
                            $("#sbzt_" + key).css("background-color","rgba(0, 128, 0, 1)");
                        }
                        if(json[key][0] ){
                            $("#szzt_" + key).css("background-color","rgba(255, 0, 0, 1)");
                        }else{
                            $("#sbzt_" + key).css("background-color","rgba(0, 128, 0, 1)");
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            })
        }
        
        
        //var formDialog;
        function showYzq(){
            var url = "<%=path%>/ifs/zhyy/yangzhiqu/getYzqXiangxi";
            $.ajax({
                url: url,
                data: {yzcid: "0001"
                },
                type: 'POST',
                timeout: 30000,
                success: function (rjson, textStatus) {
                    //alert(json);
                    $("#tbody_data").empty();
                    var htmlstr = "";
                    var titlestr = "";
                    var funstr = "";
                    var attrs = new Array("s_shuishen","s_shuiwen","s_yandu","s_d02","s_ph","s_caijiqi_flag","s_dianliu_1","s_dianliu_2","s_weiyi_jin_val","s_weiyi_chu_val");
                    
                    //for(var key in json){
                    for(var n = 0;n < rjson.keylist.length;n ++){
                        var key = rjson.keylist[n];
                        var json = rjson.datamap;
                        htmlstr += "<tr height=\"35px\"  bgcolor=\"#00FFFF\" align=\"center\" >";
                        //alert(key);
                        htmlstr += "<td onclick=showYzqxiangxi(\""+key.substring(0,key.indexOf("_")  )+"\")>"+key.substring(key.indexOf("_") + 1 , key.length)+"</td>";
                        for(var i =0;i  < attrs.length;i ++) {
                            //临时代码
                            // if (n == 0 && i == 0) {
                            //     htmlstr += "<td  bgcolor=\"green\"  style=\"color:#ffffff\">1.3</td>";
                            // } else {

                                titlestr = "";
                                funstr = "";
                                if (json[key][attrs[i]] !== undefined) {
                                    if (json[key][attrs[i]].s_leibie == "ai") {
                                        titlestr = json[key][attrs[i]].val;
                                        if (json[key][attrs[i]].s_attr == "s_shuishen" || json[key][attrs[i]].s_attr == "s_dianliu_1" || json[key][attrs[i]].s_attr == "s_dianliu_2") {
                                            funstr = "onclick=showdanceng(\"" + json[key][attrs[i]].zhyy_rtu_id + "\",\"" + json[key][attrs[i]].zhyy_yangzhiqu_id + "\",\"" + json[key][attrs[i]].s_attr + "\")";
                                        } else {
                                            funstr = "onclick=showduoceng(\"" + json[key][attrs[i]].zhyy_rtu_id + "\",\"" + json[key][attrs[i]].zhyy_yangzhiqu_id + "\",\"" + json[key][attrs[i]].s_attr + "\")";
                                        }

                                    } else {
                                        if (titlestr = json[key][attrs[i]].val == "1") {
                                            titlestr = "正常";
                                        } else {
                                            titlestr = "异常";
                                        }
                                    }
                                    htmlstr += "<td " + funstr + " bgcolor=\"" + json[key][attrs[i]].style + "\" title=\"" + json[key][attrs[i]].title + "\" style=\"color:#ffffff\">" + titlestr + "</td>";
                                } else {
                                    var showdata = 0;
                                    if(attrs[i] == "s_shuishen"){
                                        showdata = randomNum(1.5,1.3,1)  ;
                                    }else if(attrs[i] == "s_shuiwen"){
                                        showdata =  randomNum(7,9,1)  ;
                                    }else if(attrs[i] == "s_yandu"){
                                        showdata =  randomNum(26,32,1)  ;
                                    }else if(attrs[i] == "s_d02"){
                                        showdata =  randomNum(3,14,1)  ;
                                    }else if(attrs[i] == "s_ph"){
                                        showdata =  randomNum(8,8.5,1)  ;
                                    }
                                    if(showdata > 0){
                                        htmlstr += "<td  bgcolor=\"green\"  style=\"color:#ffffff\">"+showdata+"</td>";

                                    }else{
                                        htmlstr += "<td >暂无数据</td>";
                                    }
                                }
                            //}
                        }
                        htmlstr += "</tr>";
                    }
                    $("#tbody_data").append(htmlstr);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
        function randomNum(maxNum, minNum, decimalNum) {
            var max = 0, min = 0;
            minNum <= maxNum ? (min = minNum, max = maxNum) : (min = maxNum, max = minNum);
            switch (arguments.length) {
                case 1:
                    return Math.floor(Math.random() * (max + 1));
                    break;
                case 2:
                    return Math.floor(Math.random() * (max - min + 1) + min);
                    break;
                case 3:
                    return (Math.random() * (max - min) + min).toFixed(decimalNum);
                    break;
                default:
                    return Math.random();
                    break;
            }
        }




        var rtu = null;
        var t_yandu_chart;
        function load_yandu(zb_val) {
            $("#data_wh_yd").empty();
            $("#data_wh_yd").append("盐度:" + zb_val);
            t_yandu_chart = Highcharts.chart('char_yandu', {
                chart: {
                    type: 'gauge',
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false
                },
                pane: {
                    startAngle: -150,
                    endAngle: 150,
                    background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                },
                // the value axis
                yAxis: {
                    min: 20,
                    max: 36,
                    minorTickInterval: 'auto',
                    minorTickWidth: 1,
                    minorTickLength: 10,
                    minorTickPosition: 'inside',
                    minorTickColor: '#666',
                    tickPixelInterval: 30,
                    tickWidth: 2,
                    tickPosition: 'inside',
                    tickLength: 10,
                    tickColor: '#666',
                    labels: {
                        step: 2,
                        rotation: 'auto'
                    },
                    title: {
                        text: '盐度'
                    },
                    plotBands: [{
                            from: 10,
                            to: 22,
                            color: '#DF5353'
                                    //color: '#55BF3B' // green
                        }, {
                            from: 10,
                            to: 26,
                            color: '#DDDF0D' // yellow
                        }, {
                            from: 26,
                            to: 32,
                            color: '#55BF3B' // red
                        }, {
                            from: 32,
                            to: 34,
                            color: '#DDDF0D' // red
                        }, {
                            from: 34,
                            to: 36,
                            color: '#DF5353' // red
                        }]
                },
                series: [{
                        name: '盐度',
                        data: [zb_val],
                        tooltip: {
                            valueSuffix: ''
                        }
                    }]
            }, function (t_yandu_chart) {
                if (!t_yandu_chart.renderer.forExport) {
                    setInterval(function () {
                        var point = t_yandu_chart.series[0].points[0],
                                newVal;
                        //inc = Math.round((Math.random() - 0.1) * 20);
                        //alert("update yandu");
                        if (rtu != null) {
                            newVal = rtu.rtuai[0].va;
                            $("#data_wh_yd").empty();
                            $("#data_wh_yd").append("盐度:" + newVal);
                            point.update(newVal);
                        }
                    }, 10000);
                }
            });
        }
        
        var t_wendu_chart;
        function load_shuiwen(zb_val) {
            $("#data_wh_sw").empty();
            $("#data_wh_sw").append("水温:" + zb_val);
            t_wendu_chart = Highcharts.chart('char_shuiwen', {
                chart: {
                    type: 'gauge',
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false
                },
                pane: {
                    startAngle: -150,
                    endAngle: 150,
                    background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                },
                // the value axis
                yAxis: {
                    min: -5,
                    max: 36,
                    minorTickInterval: 'auto',
                    minorTickWidth: 1,
                    minorTickLength: 10,
                    minorTickPosition: 'inside',
                    minorTickColor: '#666',
                    tickPixelInterval: 30,
                    tickWidth: 2,
                    tickPosition: 'inside',
                    tickLength: 10,
                    tickColor: '#666',
                    labels: {
                        step: 2,
                        rotation: 'auto'
                    },
                    title: {
                        text: '温度'
                    },
                    plotBands: [{
                            from: -5,
                            to: 26,
                            color: '#55BF3B'
                                    //color: '#55BF3B' // green
                        }, {
                            from: 26,
                            to: 28,
                            color: '#DDDF0D' // yellow
                        }, {
                            from: 28,
                            to: 36,
                            color: '#DF5353' // red
                        }]
                },
                series: [{
                        name: '水温',
                        data: [zb_val],
                        tooltip: {
                            valueSuffix: ''
                        }
                    }]
            }, function (t_wendu_chart) {
                if (!t_wendu_chart.renderer.forExport) {
                    setInterval(function () {
                        var point = t_wendu_chart.series[0].points[0],
                                newVal;
                        //inc = Math.round((Math.random() - 0.1) * 20);
                        //alert("update yandu");
                        if (rtu != null) {
                            newVal = rtu.rtuai[3].va;
                            $("#data_wh_sw").empty();
                            $("#data_wh_sw").append("水温:" + newVal);
                            point.update(newVal);
                        }
                    }, 10000);
                }
            });
        }
        
        var t_chaoxi_chart;
        function load_chaoxi(zb_val) {
            $("#data_wh_cx").empty();
            $("#data_wh_cx").append("潮汐:" + zb_val);
            t_chaoxi_chart = Highcharts.chart('char_chaoxi', {
                chart: {
                    type: 'gauge',
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false
                },
                pane: {
                    startAngle: -150,
                    endAngle: 150,
                    background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                },
                // the value axis
                yAxis: {
                    min: 0,
                    max: 4,
                    minorTickInterval: 'auto',
                    minorTickWidth: 1,
                    minorTickLength: 10,
                    minorTickPosition: 'inside',
                    minorTickColor: '#666',
                    tickPixelInterval: 30,
                    tickWidth: 2,
                    tickPosition: 'inside',
                    tickLength: 10,
                    tickColor: '#666',
                    labels: {
                        step: 2,
                        rotation: 'auto'
                    },
                    title: {
                        text: '潮汐'
                    },
                    plotBands: [{
                            from: 0,
                            to: 0.5,
                            color: '#DF5353'
                                    //color: '#55BF3B' // green
                        }, {
                            from: 0.5,
                            to: 4,
                            color: '#55BF3B' // yellow
                        }]
                },
                series: [{
                        name: '潮汐',
                        data: [zb_val],
                        tooltip: {
                            valueSuffix: ''
                        }
                    }]
            }, function (t_chaoxi_chart) {
                if (!t_chaoxi_chart.renderer.forExport) {
                    setInterval(function () {
                        var point = t_chaoxi_chart.series[0].points[0],
                                newVal;
                        //inc = Math.round((Math.random() - 0.1) * 20);
                        //alert("update yandu");
                        if (rtu != null) {
                            // newVal = rtu.rtuai[7].va;
                            newVal = 1.3;
                            $("#data_wh_cx").empty();
                            $("#data_wh_cx").append("潮汐:" + newVal);
                            point.update(newVal);
                        }
                    }, 10000);
                }
            });
        }
        
        var t_ph_chart;
        function load_ph(zb_val) {
            $("#data_wh_ph").empty();
            $("#data_wh_ph").append("pH:" + zb_val);
            t_ph_chart = Highcharts.chart('char_ph', {
                chart: {
                    type: 'gauge',
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false
                },
                pane: {
                    startAngle: -150,
                    endAngle: 150,
                    background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                },
                // the value axis
                yAxis: {
                    min: 6,
                    max: 10,
                    minorTickInterval: 'auto',
                    minorTickWidth: 1,
                    minorTickLength: 10,
                    minorTickPosition: 'inside',
                    minorTickColor: '#666',
                    tickPixelInterval: 30,
                    tickWidth: 2,
                    tickPosition: 'inside',
                    tickLength: 10,
                    tickColor: '#666',
                    labels: {
                        step: 2,
                        rotation: 'auto'
                    },
                    title: {
                        text: 'pH'
                    },
                    plotBands: [{
                            from: 6,
                            to: 8,
                            color: '#DF5353'
                                    //color: '#55BF3B' // green
                        }, {
                            from: 8,
                            to: 8.1,
                            color: '#DDDF0D' // yellow
                        }, {
                            from: 8.1,
                            to: 8.2,
                            color: '#55BF3B' // red
                        }, {
                            from: 8.2,
                            to: 8.3,
                            color: '#DDDF0D' // red
                        }, {
                            from: 8.3,
                            to: 10,
                            color: '#DF5353' // red
                        }]
                },
                series: [{
                        name: 'Ph',
                        data: [zb_val],
                        tooltip: {
                            valueSuffix: ''
                        }
                    }]
            }, function (t_ph_chart) {
                if (!t_ph_chart.renderer.forExport) {
                    setInterval(function () {
                        var point = t_ph_chart.series[0].points[0],
                                newVal;
                        //inc = Math.round((Math.random() - 0.1) * 20);
                        //alert("update yandu");
                        if (rtu != null) {
                            newVal = rtu.rtuai[2].va;
                            $("#data_wh_ph").empty();
                            $("#data_wh_ph").append("pH:" + newVal);
                            point.update(newVal);
                        }
                    }, 10000);
                }
            });
        }
        
        var t_d02_chart;
        function load_d02(zb_val) {
            $("#data_wh_d02").empty();
            $("#data_wh_d02").append("DO2:" + zb_val);
            t_d02_chart = Highcharts.chart('char_d02', {
                chart: {
                    type: 'gauge',
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false
                },
                pane: {
                    startAngle: -150,
                    endAngle: 150,
                    background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                },
                // the value axis
                yAxis: {
                    min: 0,
                    max: 20,
                    minorTickInterval: 'auto',
                    minorTickWidth: 1,
                    minorTickLength: 10,
                    minorTickPosition: 'inside',
                    minorTickColor: '#666',
                    tickPixelInterval: 30,
                    tickWidth: 2,
                    tickPosition: 'inside',
                    tickLength: 10,
                    tickColor: '#666',
                    labels: {
                        step: 2,
                        rotation: 'auto'
                    },
                    title: {
                        text: 'DO2'
                    },
                    plotBands: [{
                            from: 0,
                            to: 2,
                            color: '#DF5353'
                                    //color: '#55BF3B' // green
                        }, {
                            from: 2,
                            to: 3,
                            color: '#DDDF0D' // yellow
                        }, {
                            from: 3,
                            to: 20,
                            color: '#55BF3B' // red
                        }]
                },
                series: [{
                        name: 'do2',
                        data: [zb_val],
                        tooltip: {
                            valueSuffix: ''
                        }
                    }]
            }, function (t_d02_chart) {
                if (!t_d02_chart.renderer.forExport) {
                    setInterval(function () {
                        var point = t_d02_chart.series[0].points[0],
                                newVal;
                        //inc = Math.round((Math.random() - 0.1) * 20);
                        //alert("update yandu");
                        if (rtu != null) {
                            newVal = rtu.rtuai[1].va;
                            $("#data_wh_d02").empty();
                            $("#data_wh_d02").append("DO2:" + newVal);
                            point.update(newVal);
                        }
                    }, 10000);
                }
            });
        }

        var userid = "533965984939DIkhE";
        var pwd = "O5o07R2";
        var staid = "54584";
        var elements = "Year,Mon,Day,Hour,PRS,PRS_Sea,PRS_Max,PRS_Min,TEM,TEM_Max,TEM_min,RHU,RHU_Min,VAP,PRE_1h,WIN_D_INST_Max,WIN_S_Max,WIN_D_S_Max,WIN_S_Avg_2mi,WIN_D_Avg_2mi,WEP_Now,WIN_S_Inst_Max";



  //  	$("#btime").val(formatdate(date,format) + "000000");
  //  	$("#etime").val(formatdate(date,"yyyyMMddhh") + "0000") ;

        var url = "";
        function getTqData() {
            var format = "yyyyMMdd";
            var shicha = 1000 * 60 * 60 * (8 + 2);
            var date = new Date();
            var etime = formatdate(date, "yyyyMMddhh") + "0000";
            date.setTime(date.getTime() - shicha)
            var btime = formatdate(date, "yyyyMMddhh") + "0000";

            $("#data_tq").empty();
  //  		btime = $("#btime").val();
  //  		etime = $("#etime").val();
  //                btime = "20180806000000";
  //                etime = "20180811020000";
            url = "http://api.data.cma.cn:8090/api?userId=" + userid + "&pwd=" + pwd + "&dataFormat=json" +
                    "&interfaceId=getSurfEleByTimeRangeAndStaID" +
                    "&timeRange=[" + btime + "," + etime + "]" +
                    "&staIDs=" + staid +
                    "&elements=" + elements +
                    "&dataCode=SURF_CHN_MUL_HOR";
            url = "/framework-web/zhyy/qx/getdata";
  //                url = "http://api.data.cma.cn:8090/api?userId=533965984939DIkhE&pwd=O5o07R2&dataFormat=json&interfaceId=getSurfEleByTimeRangeAndStaID&timeRange=[20180811050000,20180811050000]&staIDs=54584&elements=Year,Mon,Day,Hour,PRS,PRS_Sea,PRS_Max,PRS_Min,TEM,TEM_Max,TEM_min,RHU,RHU_Min,VAP,PRE_1h,WIN_D_INST_Max,WIN_S_Max,WIN_D_S_Max,WIN_S_Avg_2mi,WIN_D_Avg_2mi,WEP_Now,WIN_S_Inst_Max&dataCode=SURF_CHN_MUL_HOR";
            $.ajax({
                url: url,
                data: {btime: btime, etime: etime},
                type: 'POST',

                timeout: fw_config.ajax_timeout,
                dataFilter: function (json) {
                    //console.log("jsonp.filter:"+json  );    
                    return json;
                },
                success: function (json, textStatus) {
  //                       TEM 气温  摄氏度(℃) 
  //                        RHU 湿度
  //                        PRS 气压 百帕
  //                        WIN_S_Avg_2mi 2分钟平均风速  米/秒
  //                        PRE_1h 1小时降雨量  毫米 

  //                           alert(json["DS"][lastnum].TEM);
                    var data = json.DS;

                    if (data != null && data.length > 0) {
                        var lastnum = data.length - 1;
                        //alert(data[lastnum].Year + "-" + data[lastnum].Mon + "-" +data[lastnum].Day + " " + data[lastnum].Hour + ":00:00");
                        var d = new Date(data[lastnum].Year + "-" + data[lastnum].Mon + "-" + data[lastnum].Day + " " + data[lastnum].Hour + ":00:00");
                        d.setTime(d.getTime() + 1000 * 60 * 60 * 8)
                        $("#time_qxzx").empty();
                        $("#time_qxzx").append("气象在线&nbsp;" + formatdate(d, "yyyy年MM月dd日 hh点"));
                        var num = parseFloat(data[lastnum].TEM);
                        num = num.toFixed(1);
                        $("#data_qx_qw").empty();
                        $("#data_qx_qw").append(num + "℃");

                        num = parseFloat(data[lastnum].WIN_S_Avg_2mi);
                        num = num.toFixed(1);
                        $("#data_qx_fl").empty();
                        $("#data_qx_fl").append(num + "米/秒");
                        
                        num = parseFloat(data[lastnum].WIN_D_Avg_2mi);
                        num = num.toFixed(1);
                        var fxstr ;
                        //fx = parseFloat(data[lastnum].WIN_D_Avg_2mi);
                        fx = parseFloat(data[lastnum].WIN_D_S_Max);
                        if( fx > 360 - 11.25 && fx <= 360 ||  fx <= 11.25){
                            fxstr = "北";
                        }else if(fx > 0 + 11.25 && fx <= 22.5 + 11.25){
                            fxstr = "北东北";
                        }else if(fx > 22.5 + 11.25 && fx <= 45 + 11.25){
                            fxstr = "东北";
                        }else if(fx > 45 + 11.25 && fx <= 67.5 + 11.25){
                            fxstr = "东东北";
                        }else if(fx > 67.5 + 11.25 && fx <= 90 + 11.25){
                            fxstr = "东";
                        }else if(fx > 90 + 11.25 && fx <= 112.5 + 11.25){
                            fxstr = "东东南";
                        }else if(fx > 112.5 + 11.25 && fx <= 135 + 11.25){
                            fxstr = "东南";
                        }else if(fx > 135 + 11.25 && fx <= 157.5 + 11.25){
                            fxstr = "南东南";
                        }else if(fx > 157.5 + 11.25 && fx <= 180 + 11.25){
                            fxstr = "南";
                        }else if(fx > 180 + 11.25 && fx <= 202.5){
                            fxstr = "南西南";
                        }else if(fx > 202.5 + 11.25 && fx <= 225 + 11.25){
                            fxstr = "西南";
                        }else if(fx > 225 + 11.25 && fx <= 247.5 + 11.25){
                            fxstr = "西西南";
                        }else if(fx > 247.5 + 11.25 && fx <= 270 + 11.25){
                            fxstr = "西";
                        }else if(fx > 270 + 11.25 && fx <= 292.5 + 11.25){
                            fxstr = "西西北";
                        }else if(fx > 292.5 + 11.25 && fx <= 315 + 11.25){
                            fxstr = "西北";
                        }else if(fx > 315 + 11.25 && fx <= 337.5 + 11.25){
                            fxstr = "北西北";
                        }
                        $("#data_qx_fx").empty();
                        $("#data_qx_fx").append(fxstr);

                        num = parseFloat(data[lastnum].PRE_1h);
                        num = num.toFixed(1);
                        $("#data_qx_jy").empty();
                        $("#data_qx_jy").append(num + "毫米");

                        num = parseFloat(data[lastnum].PRS);
                        num = num.toFixed(1);
                        $("#data_qx_qy").empty();
                        $("#data_qx_qy").append(num + "p");

                        num = parseFloat(data[lastnum].RHU);
                        num = num.toFixed(1);
                        $("#data_qx_sd").empty();
                        $("#data_qx_sd").append(num + "%");
                    }

                    //console.log("jsonp.success:"+json);    
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
        var init_flag = false;
        var whszdata_url = "<%=path%>/ifs/zhyy/getRTUModById";
        function getWhSzData() {
            $.ajax({
                    url: whszdata_url,
                    data: {rtuid: "00010003"},
                    type: 'POST',
                    timeout: fw_config.ajax_timeout,
                    dataFilter: function (json) {
                        console.log("jsonp.filter:" + json);
                        return json;
                    },
                    success: function (json, textStatus) {
                        //alert("getWsData");
                        rtu = json;
                        if (!init_flag) {
                            // load_yandu(json.rtuai[5].va);
                            // load_shuiwen(json.rtuai[6].va);
                            // load_chaoxi(json.rtuai[7].va);
                            // load_d02(json.rtuai[4].va);
                            // load_ph(json.rtuai[3].va);
                            load_yandu(json.rtuai[0].va);
                            load_shuiwen(json.rtuai[3].va);
                            //load_chaoxi(json.rtuai[7].va);
                            load_chaoxi(1.3);
                            load_d02(json.rtuai[1].va);
                            load_ph(json.rtuai[2].va);
                            init_flag = true;
                        }
      //                        alert(json.datatime);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                    }
            }); 
        }

        function formatdate(d, format) {
            var date = {
                "M+": d.getMonth() + 1,
                "d+": d.getDate(),
                "h+": d.getHours(),
                "m+": d.getMinutes(),
                "s+": d.getSeconds(),
                "q+": Math.floor((d.getMonth() + 3) / 3),
                "S+": d.getMilliseconds()
            };
            if (/(y+)/i.test(format)) {
                format = format.replace(RegExp.$1, (d.getFullYear() + '').substr(4 - RegExp.$1.length));
            }
            for (var k in date) {
                if (new RegExp("(" + k + ")").test(format)) {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1
                            ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
                }
            }
            return format;
        }
        
        function loadlist(rqflag){
            if(rtype == 1){
                showduoceng(rid,yid,cmc,rqflag);
            }else{
                showdanceng(rid,yid,cmc,rqflag);
            }
        }
        var loadlist_url = "<%=path%>/ifs/zhyy/shuizhishuju/getnowlist";
        var chartdata;
        var dcchartdata;
        var rid,yid,cmc;
        var rtype;
        function showduoceng(rtuid,yzqid,cname,rqflag){
            rid = rtuid;
            yid = yzqid;
            cmc = cname;
            rtype = 1;
            var dtype = 1;
            if(rqflag != null && rqflag != "" && rqflag != undefined){
                dtype = 2;
            }
            $("#div_content").show();

            var date = formatdate(new Date(),"yyyy-MM-dd");
            $.ajax({
                url: loadlist_url,
                data: {rtuid:rtuid,yzqid:yzqid,riqi:date,rqflag
                },
                type: 'POST',
                timeout: 30000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    chartdata = json;
                    if(cname == "s_yandu"){
                        yandu_chart(chartdata.datalsit,"div_chart",dtype);
                    }else if(cname == "s_shuiwen"){
                        shuiwen_chart(chartdata.datalsit,"div_chart",dtype);
                    }else if(cname == "s_d02"){
                        d02_chart(chartdata.datalsit,"div_chart",dtype);
                    }else if(cname == "s_ph"){
                        ph_chart(chartdata.datalsit,"div_chart",dtype);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
            
        }
        var sd_loadlist_url =  "<%=path%>/ifs/zhyy/shuizhishuju/getdcnowlist";
        function showdanceng(rtuid,yzqid,cname,rqflag){
            //alert(rtuid + "-" +yzqid+ "-" +cname);
            var dtype = 1;
            if(rqflag != null && rqflag != "" && rqflag != undefined){
                dtype = 2;
            }
            $("#div_content").show();
            var date = formatdate(new Date(),"yyyy-MM-dd");
            $.ajax({
                url: sd_loadlist_url,
                data: {rtuid:rtuid,yzqid:yzqid,riqi:date,rqflag
                },
                type: 'POST',
                timeout: 30000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    dcchartdata = json;
                    if(cname == "s_shuishen"){
                        shendu_chart(dcchartdata.datalsit,"div_chart",dtype);
                    }else if(cname == "s_dianliu_1"){
                        dianliu1_chart(dcchartdata.datalsit,"div_chart",dtype);
                    }else if(cname == "s_dianliu_2"){
                        dianliu2_chart(dcchartdata.datalsit,"div_chart",dtype);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
        
        var formDialog;
        function showYzqxiangxi(yzqid){
           formDialog = new $.dialog({
                id:'yzqDialogFrame',
                title:"养殖池状态",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/zhyy/yangzhiqu/view?id='+yzqid,
                autoPos:true
            });
        }
        function closeContent(){
            $("#div_content").hide();
        }
    </script>
</html>
