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
        <script src="https://cdn.hcharts.cn/highcharts/highcharts.js"></script>
        <script src="<%=path%>/js/zhyy/char_shuizhi.js"></script>
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
        <div id="" style="width:100%;height:20px; background-color: #0097BD">
            养殖场:<select id="yzc"></select>
            养殖区:<select id="yzq"></select>
            智控设备:<select id="rtu"></select>
            选择日期:<input type="text" id="riqi" readonly="readonly" />
            <a href="javascript:loadlist('1')">本周</a>
            <a href="javascript:loadlist('2')">上周</a>
            <a href="javascript:loadlist('3')">本月</a>
            <a href="javascript:loadlist('4')">上月</a>
        </div>
        <div id="char_yandu" style="float:left;width:50%;height:300px"></div>
        <div id="char_shuiwen" style="float:left;width:50%;height:300px"></div>
        <div id="char_ph" style="float:left;width:50%;height:300px"></div>
        <div id="char_do2" style="float:left;width:50%;height:300px"></div>
        <div id="char_shuishen" style="float:left;width:50%;height:200px"></div>
        <div id="char_dianliu1" style="float:left;width:50%;height:200px"></div>
        <div id="char_dianliu2" style="float:left;width:50%;height:200px"></div>
    </body>
<script type="text/javascript">
    var loadlist_url = "<%=path%>/ifs/zhyy/shuizhishuju/getnowlist";
    var sd_loadlist_url =  "<%=path%>/ifs/zhyy/shuizhishuju/getdcnowlist";
    var sb_sbjk_url = "/framework-web/ifs/zhyy/shebeijiankong/getJkData";
    $(function () {
//        Highcharts.setOptions({
//            global: {
//                useUTC: false
//            }
//        });
//        loadlist();
        laydate.render({
                    elem: '#riqi' //指定元素
                    ,max: 0
                    ,value:new Date()
                    ,done: function(value, date){
                        loadlist();
                    }
                });
        Highcharts.setOptions({ global: { useUTC: false } });
        getYzcList(init_yzcsel);
    })

    function init_yzcsel(json) {
        var optstr = "";
        for (var i = 0; i < json.length; i++) {
            optstr += "<option value=\"" + json[i].id + "\">" + json[i].y_mingcheng + "</option>";
        }
        $("#yzc").append(optstr);
        //alert($("#yzc").val());
        getYzqList($("#yzc").val(), init_yzqsel);
    }
    function init_yzqsel(json) {
        $("#yzq").empty();
        //alert(json);
        var optstr = "";
        for (var i = 0; i < json.length; i++) {
            optstr += "<option value=\"" + json[i].id + "\">" + json[i].y_bianhao + "</option>";
        }
        $("#yzq").append(optstr);
        getRtuList($("#yzq").val(), init_rtusel);
    }
    function init_rtusel(json) {
        $("#rtu").empty();
        //alert(json);
        var optstr = "";
        for (var i = 0; i < json.length; i++) {
            optstr += "<option value=\"" + json[i].zhyy_rtu_id + "\">" + json[i].zhyy_rtu_id + "</option>";
        }
        $("#rtu").append(optstr);
        loadlist();
    }
    $("#yzc").change(function(){        
        getYzqList($("#yzc").val(),init_yzqsel);
    });
    $("#yzq").change(function(){        
        getRtuList($("#yzq").val(),init_rtusel);
    });
    //获取当前时间以前所有数据
    function loadlist(rqflag){
        var dtype = 1;
        if(rqflag != null && rqflag != "" && rqflag != undefined){
            dtype = 2;
        }
        $.ajax({
            url: loadlist_url,
            data: {rtuid:$("#rtu").val(),yzqid:$("#yzq").val(),riqi:$("#riqi").val(),rqflag
            },
            type: 'POST',
            timeout: 30000,
            dataFilter: function (json) {
                console.log("jsonp.filter:" + json);
                return json;
            },
            success: function (json, textStatus) {
                var sellist = json.setlsit;
                for(var i = 0;i < sellist.length;i ++){
                    if(sellist[i].s_attr === "s_yandu"){
                        yandu_chart(json.datalsit, "char_yandu",dtype);
                    }else if(sellist[i].s_attr == "s_shuiwen"){
                        shuiwen_chart(json.datalsit,"char_shuiwen",dtype);
                    }else if(sellist[i].s_attr == "s_ph"){
                        ph_chart(json.datalsit,"char_ph",dtype);
                    }else if(sellist[i].s_attr == "s_d02"){
                        d02_chart(json.datalsit,"char_do2",dtype);
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jsonp.error:" + XMLHttpRequest.returnCode);
            }
        });
        
        $.ajax({
            url: sd_loadlist_url,
            data: {rtuid:$("#rtu").val(),yzqid:$("#yzq").val(),riqi:$("#riqi").val(),rqflag
            },
            type: 'POST',
            timeout: 30000,
            dataFilter: function (json) {
                console.log("jsonp.filter:" + json);
                return json;
            },
            success: function (json, textStatus) {
                var sellist = json.setlsit;
                for(var i = 0;i < sellist.length;i ++){
                    if(sellist[i].s_attr == "s_shuishen"){
                        shendu_chart(json.datalsit,"char_shuishen");
                    }
                    else if(sellist[i].s_attr == "s_dianliu_1"){
                        dianliu1_chart(json.datalsit,"char_dianliu1");
                    }
                    else if(sellist[i].s_attr == "s_dianliu_2"){
                        dianliu2_chart(json.datalsit,"char_dianliu2");
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jsonp.error:" + XMLHttpRequest.returnCode);
            }
        });
    }
    //获取最新一条数据
    var nowtime = new Array("","","","");
    function getnow(chart,rtuainum,timenum,sclength){
        series1 = chart.series[0],series2 = chart.series[1],series3 = chart.series[2];
        $.ajax({
            url: sb_sbjk_url,
            type: 'POST',
            async: false,
            data: {},
            success: function (data) {
                if(data != null || data != ""){
                    if(nowtime[timenum] == ""){
                        nowtime[timenum] = data.datatime;
                    }else if(nowtime[timenum] == data.datatime){
                        return;
                    }
                    var x = parseInt(data.longtime), 
                    y = data.rtuai[rtuainum].va;          
                    if(data.rtudo[3].va == "1"){
                        series1.addPoint([x, y], true, true);
                    }else if(data.rtudo[4].va == "1"){
                        series2.addPoint([x, y], true, true);
                    }else if(data.rtudo[5].va == "1"){
                        series3.addPoint([x, y], true, true);
                    }
                    activeLastPointToolip(chart);
                    nowtime[timenum] = data.datatime;
                }
            },
            error: function (data) {
                alert("error: " + JSON.stringify(data));
            }
        });
    }
    
    function getnow_sd(chart,rtuainum,timenum){
        series1 = chart.series[0];
        $.ajax({
            url: sb_sbjk_url,
            type: 'POST',
            async: false,
            data: {},
            success: function (data) {
                if(data != null || data != ""){
                    if(nowtime[timenum] == ""){
                        nowtime[timenum] = data.datatime;
                    }else if(nowtime[timenum] == data.datatime){
                        return;
                    }
                    var x = parseInt(data.longtime), 
                    y = data.rtuai[rtuainum].va;       
                    series1.addPoint([x, y], true, true);
                    activeLastPointToolip(chart);
                    nowtime[timenum] = data.datatime;
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
    
    
</script>
</html>