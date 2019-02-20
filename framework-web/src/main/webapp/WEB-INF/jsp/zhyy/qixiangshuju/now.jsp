<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String id = "";
    if (request.getAttribute("id") != null) {
        id = (String) request.getAttribute("id");
    } else {
        id = "";
    }
    String nowdate = "";
    if(request.getAttribute("nowdata") != null){
        nowdate = (String)request.getAttribute("nowdata");
    }
%>
<html>
    <head>
        <title></title>
        <%@ include file="../../include/commontop.jsp"%>
        <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
        <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
        <script src="https://img.hcharts.cn/highcharts/modules/oldie.js"></script>
        <script src="https://img.hcharts.cn/highcharts/highcharts-more.js"></script>
        <script src="https://img.hcharts.cn/highcharts/modules/data.js"></script>
        <script src="<%=path%>/js/highcharts/windbarb.js"></script>
        <style type="text/css">
            #container {
                height: 400px; 
                min-width: 310px; 
                max-width: 800px;
                margin: 0 auto;
            }
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
    </head>
    <body>
        <div id="" style="width:100%;height:20px; background-color: #0097BD"><h1>智控设备编号: 7250d4a7 &nbsp;&nbsp;选择日期:<input type="text" id="riqi" readonly="readonly" /></h1></div>
        <div id="container1" style="float:left;width:50%;height:200px"></div>
        <div id="container2" style="float:left;width:50%;height:200px"></div>
        <div id="container3" style="float:left;width:50%;height:200px"></div>
        <div id="container4" style="float:left;width:50%;height:200px"></div>
        <div id="container_fy" style="float:left;width:50%;height:400px"></div>
        <div id="container_mg" style="float:left;width:50%;height:400px"></div>
        <div style="display:none">
            <!-- Source: http://or.water.usgs.gov/cgi-bin/grapher/graph_windrose.pl -->
            <table id="freq" border="0" cellspacing="0" cellpadding="0">
                <tr nowrap bgcolor="#CCCCFF">
                    <th colspan="9" class="hdr">Table of Frequencies (percent)</th>
                </tr>
                <tr nowrap bgcolor="#CCCCFF">
                    <th class="freq">Direction</th>
                    <th class="freq">< 0.5 m/s</th>
                    <th class="freq">0.5-2 m/s</th>
                    <th class="freq">2-4 m/s</th>
                    <th class="freq">4-6 m/s</th>
                    <th class="freq">6-8 m/s</th>
                    <th class="freq">8-10 m/s</th>
                    <th class="freq">> 10 m/s</th>
                    <th class="freq">Total</th>
                </tr>
                <tr id="N_tr" nowrap>
                    <td class="dir">N</td>
                </tr>		
                <tr id="NNE_tr" nowrap bgcolor="#DDDDDD">
                    <td class="dir">NNE</td>
                </tr>
                <tr id="NE_tr" nowrap>
                    <td class="dir">NE</td>
                </tr>
                <tr id="ENE_tr" nowrap bgcolor="#DDDDDD">
                    <td class="dir">ENE</td>
                </tr>
                <tr id="E_tr" nowrap>
                    <td class="dir">E</td>
                </tr>
                <tr id="ESE_tr" nowrap bgcolor="#DDDDDD">
                    <td class="dir">ESE</td>
                </tr>
                <tr id="SE_tr" nowrap>
                    <td class="dir">SE</td>
                </tr>
                <tr id="SSE_tr" nowrap bgcolor="#DDDDDD">
                    <td class="dir">SSE</td>
                </tr>
                <tr id="S_tr" nowrap>
                    <td class="dir">S</td>
                </tr>
                <tr id="SSW_tr" nowrap bgcolor="#DDDDDD">
                    <td class="dir">SSW</td>
                </tr>
                <tr id="SW_tr" nowrap>
                    <td class="dir">SW</td>
                </tr>
                <tr id="WSW_tr" nowrap bgcolor="#DDDDDD">
                    <td class="dir">WSW</td>
                </tr>
                <tr id="W_tr" nowrap>
                    <td class="dir">W</td>
                </tr>
                <tr id="WNW_tr" nowrap bgcolor="#DDDDDD">
                    <td class="dir">WNW</td>
                </tr>
                <tr id="NW_tr" nowrap>
                    <td class="dir">NW</td>
                </tr>		
                <tr id="NNW_tr" nowrap bgcolor="#DDDDDD">
                    <td class="dir">NNW</td>
                </tr>
                <tr id="Total_tr" nowrap>
                </tr>
            </table>
        </div>
    </body>
<script type="text/javascript">
    
    var loadlist_url = "<%=path%>/ifs/zhyy/qixiangshuju/getnowlist";
    var lastdata_url = "<%=path%>/ifs/zhyy/qixiangshuju/getlastdata";
    var fydata_url = "<%=path%>/ifs/zhyy/qixiangshuju/getfydata";
    var mgdata_url = "<%=path%>/ifs/zhyy/qixiangshuju/getmgdata";
    var nowdate ;
    $(function () {
        laydate.render({
                    elem: '#riqi' //指定元素
                    ,max: 0
                    ,value:new Date()
                    ,done: function(value, date){
                        loadlist();
                    }
                });
        nowdate = $("#riqi").val();
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
        loadlist();
    })
    //获取当前时间以前所有数据
    function loadlist(){
        $.ajax({
            url: loadlist_url,
            data: {sbid:"7250d4a7",riqi:$("#riqi").val()
            },
            type: 'POST',
            timeout: 3000,
            dataFilter: function (json) {
//                console.log("jsonp.filter:" + json);
                return json;
            },
            success: function (json, textStatus) {
                wendu_chart(json);
                fengli_chart(json);
                shidu_chart(json);
                yuliang_chart(json);
                
//                fengyu_chart(json);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jsonp.error:" + XMLHttpRequest.returnCode);
            }
        });
        $.ajax({
            url: fydata_url,
            data: {sbid:"7250d4a7",riqi:$("#riqi").val()
            },
            type: 'POST',
            timeout: 3000,
            dataFilter: function (json) {
//                console.log("jsonp.filter:" + json);
                return json;
            },
            success: function (json, textStatus) {
                fengyu_chart(json);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jsonp.error:" + XMLHttpRequest.returnCode);
            }
        });
        $.ajax({
            url: mgdata_url,
            data: {sbid:"7250d4a7",riqi:$("#riqi").val()
            },
            type: 'POST',
            timeout: 20000,
            dataFilter: function (json) {
//                console.log("jsonp.filter:" + json);
                return json;
            },
            success: function (json, textStatus) {
                mg_chart(json);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jsonp.error:" + XMLHttpRequest.returnCode);
            }
        });
    }
    //获取最新一条数据
    var nowtime = "";
    function getnow(chart,attr){
//        alert($("#riqi").val());
//        alert(nowdate);
        if($("riqi").val() != nowdate){
            return; 
        }
        series1 = chart.series[0];
        $.ajax({
            url: lastdata_url,
            type: 'POST',
            async: false,
            data: {},
            success: function (data) {
                if(data != null || data != ""){
                    if(nowtime == ""){
                        nowtime = data.q_systime;
                    }else if(nowtime == data.q_systime){
                        return;
                    }
                    var x = parseInt(data.longtime), 
                    y = data[attr];          
                    series1.addPoint([x, y], true, true);
                    activeLastPointToolip(chart);
                    timenum = data.q_systime;
                }
            },
            error: function (data) {
                alert("error: " + JSON.stringify(data));
            }
        });
    }
    
    function activeLastPointToolip(chart) {
        var points = chart.series[0].points;
        chart.tooltip.refresh(points[points.length - 1]);
    }
    
    function getdata(list,attr) {
        var data = [],i;
        for (i = 0 - list.length; i < 0; i += 1) {
            data.push({
                x: list[i + list.length].q_systime,
                y: list[i + list.length][attr]
            });
        }
        return data;
    }
    
    function wendu_chart(json){
        var chart1 = Highcharts.chart('container1', {
            chart: {
                type: 'spline',
                marginRight: 10,
                events: {
                    load: function () {
                        var chart = this;
                        activeLastPointToolip(chart);
			setInterval(function () {
                           getnow(chart,"q_qiwen");
			}, 10000);
                    }
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '气温实时'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: null
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                            Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                            Highcharts.numberFormat(this.y, 2);
                }
            },legend: {
                    align: 'right',
                    verticalAlign: 'bottom'
            },
            series: [{
                    name: '气温',
                    data: (getdata(json.datalist,"q_qiwen"))
                }
                ]
        });
    }
    function fengli_chart(json){
        var chart2 = Highcharts.chart('container2', {
            chart: {
                type: 'spline',
                marginRight: 10,
                events: {
                    load: function () {
                        var chart = this;
                        activeLastPointToolip(chart);
			setInterval(function () {
                           getnow(chart,"q_fengli");
			}, 10000);
                    }
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '风力实时'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: null
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                            Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                            Highcharts.numberFormat(this.y, 2);
                }
            },legend: {
                    align: 'right',
                    verticalAlign: 'bottom'
            },
            series: [{
                    name: '风力(米/秒)',
                    data: (getdata(json.datalist,"q_fengli"))
                }
                ]
        });
    }
    function shidu_chart(json){
        var chart3 = Highcharts.chart('container3', {
            chart: {
                type: 'spline',
                marginRight: 10,
                events: {
                    load: function () {
                        var chart = this;
                        activeLastPointToolip(chart);
			setInterval(function () {
                           getnow(chart,"q_shidu");
			}, 10000);
                    }
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '湿度实时'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: null
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                            Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                            Highcharts.numberFormat(this.y, 2) + "%";
                }
            },legend: {
                    align: 'right',
                    verticalAlign: 'bottom'
            },
            series: [{
                    name: '湿度',
                    data: (getdata(json.datalist,"q_shidu"))
                }
                ]
        });
    }
    function yuliang_chart(json){
        var chart4 = Highcharts.chart('container4', {
            chart: {
                type: 'spline',
                marginRight: 10,
                events: {
                    load: function () {
                        var chart = this;
                        activeLastPointToolip(chart);
			setInterval(function () {
                           getnow(chart,"q_yuliang");
			}, 10000);
                    }
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '降雨量实时'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: null
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                            Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                            Highcharts.numberFormat(this.y, 2);
                }
            },legend: {
                    align: 'right',
                    verticalAlign: 'bottom'
            },
            series: [{
                    name: '降雨量(毫米)',
                    data: (getdata(json.datalist,"q_yuliang"))
                }
                ]
        });
    }
    
    function fengyu_chart(json){
        var fydata = new Array(24);
        for(var i = 0;i < fydata.length;i ++){
            fydata[i] = null;
        }
        for(var i = 0;i < json.length;i ++){
            fydata[parseInt(json[i].q_hour)] = new Array(parseFloat(json[i].fengli),parseFloat(json[i].fengxiang) );
        }
        
        Highcharts.chart('container_fy', {
            title: {
                text: '风羽图'
            },
            credits: {
                enabled: false
            },
            xAxis: {
                categories: ['0时', '1时', '2时', '3时', '4时', '5时', '6时', '7时', '8时', '9时', '10时', '11时','12时','13时','14时','15时','16时','17时','18时','19时','20时','21时','22时','23时']
            },
//            plotOptions: {
//                series: {
//                    pointStart: Date.UTC(2018, 5, 20),
//                    pointInterval: 36e5
//                }
//            },
            series: [{
                type: 'windbarb',
                data: fydata,
                name: '风力',
                color: Highcharts.getOptions().colors[1],
                showInLegend: false,
                tooltip: {
                    valueSuffix: ' m/s'
                }
            }, {
                type: 'area',
                keys: ['y', 'rotation'], // rotation is not used here
                data: fydata,
                color: Highcharts.getOptions().colors[0],
                fillColor: {
                    linearGradient: { x1: 0, x2: 0, y1: 0, y2: 1 },
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [
                            1,
                            Highcharts.color(Highcharts.getOptions().colors[0])
                            .setOpacity(0.25).get()
                        ]
                    ]
                },
                name: '风速',
                tooltip: {
                    valueSuffix: ' m/s'
                }
            }]
        });
    }
    
    function mg_chart(json){
        var fxs = new Array("N","NNE","NE","ENE","E","ESE","SE","SSE","S","SSW","SW","WSW","W","WNW","NW","NNW");
        var snum = new Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        for(var i=0;i < json.length;i ++){
            for(var num in fxs){
                if(json[i][fxs[num]] != undefined ){
                    $("#" + fxs[num] + "_tr").append("<td class=\"data\">"+json[i][fxs[num]]+"</td>");
                    snum[num] += parseFloat(json[i][fxs[num]]);
                }else{
                    $("#" + fxs[num] + "_tr").append("<td class=\"data\">0.00</td>");
                }
//                alert( $("#" + fxs[num] + "_tr").html() );
            }
        }
        for(var num in fxs){
            $("#" + fxs[num] + "_tr").append("<td class=\"data\">"+snum[num]+"</td>");
            $("#Total_tr").append("<td class=\"data\">"+snum[num]+"</td>");
        }
        
        //Total_tr
        //var fls = {0f,0.5f,2f,4f,6f,8f,10f};
        // 使用数据功能模块进行数据解析
        var chart = Highcharts.chart('container_mg', {
                        data: {
                                        table: 'freq',
                                        startRow: 1,
                                        endRow: 17,
                                        endColumn: 7
                        },
                        chart: {
                                        polar: true,
                                        type: 'column'
                        },
                        credits: {
                            enabled: false
                        },
                        title: {
                                        text: '玫瑰图'
                        },
                        subtitle: {
                                        text: ''
                        },
                        pane: {
                                        size: '85%'
                        },
                        legend: {
                                        align: 'right',
                                        verticalAlign: 'top',
                                        y: 100,
                                        layout: 'vertical'
                        },
                        xAxis: {
                                        tickmarkPlacement: 'on'
                        },
                        yAxis: {
                                        min: 0,
                                        endOnTick: false,
                                        showLastLabel: true,
                                        title: {
                                                        text: '频率 (%)'
                                        },
                                        labels: {
                                                        formatter: function () {
                                                                        return this.value + '%';
                                                        }
                                        },
                                        reversedStacks: false
                        },
                        tooltip: {
                                        valueSuffix: '%'
                        },
                        plotOptions: {
                                        series: {
                                                        stacking: 'normal',
                                                        shadow: false,
                                                        groupPadding: 0,
                                                        pointPlacement: 'on'
                                        }
                        }
        });
    }
</script>
</html>