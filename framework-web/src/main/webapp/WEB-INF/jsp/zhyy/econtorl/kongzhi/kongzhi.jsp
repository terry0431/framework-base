<%-- 
    Document   : newjsp
    Created on : 2018-12-21, 9:47:01
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="../../../include/commontop.jsp"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="box_Bomb">
            <div class="box_b_t">
                <h2 class="mb10">
                    <span class="icon12">输水控制</span>
                </h2>
                <h2 >
                    <span id="span_msg" style="color:forestgreen" ></span>
                </h2>
                <form id="forms">
                    <table class="box_tab_1" >
                        <%
                            int num  = 0;
                            int sbnum = 18;
                            String inpflag = "";
                            String inpcss = "";
                            for(int i = 1;i <= 20 ;i ++){
                        %>
                            <tr>
                        <%
                                for(int j = 1;j <= 6 ;j ++){
                                    num ++;
                                    if(num > sbnum){
                                        inpflag = "disabled=\"disabled\"";
                                        inpcss = "style=\"color:red\"";
                                    }
                        %>
                        <td <%=inpcss%> class="tab1_tit"><%=num%>号池塘</td>
                        <td >
                            <div id="cont_<%=num%>"><input  type="text"  id="inp_<%=num%>" <%=inpflag%> size="4" maxlength="4" value='' />秒</div>
                            <div id="kg_<%=num%>"></div>
                        </td>
                        <%
                                }
                        %>
                            </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td class="tab1_tit">选择饵料池</td>
                            <td>
<!--                                (当前深度<span style="color:green;font-weight:bold" id="el_1">5.8米</span>)-->
                                <input name="elc" type="radio" value="1" />1号饵料池<div id="elc_1"></div>
                                <input name="elc" type="radio" value="2" />2号饵料池<div id="elc_2"></div>
                            </td>
                        </tr>
                        <tr>
                            <td id="btn_area" class="tab1_btn" colspan="2">
                                <span class="mlr5" id="save"><b class="btn btn_Lg btn_yellow">开始执行</b></span>
                                <span class="mlr5" id="reset"><b class="btn btn_Lg btn_yellow">重置</b></span>
                                <span class="mlr5" id="read"><b class="btn btn_Lg btn_yellow">读取开关状态</b></span>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        var sbtd; //设备通道集合
        var sum;
        var maxnum = 6; //同时开启开关最大数
        var swnum = 2;// 顺位数2  当设备1号的情况从3通道开始
        var cnum; //当前剩余任务数
        var getkgflag_url = "ifs/econtrol/econtrol/getKgFlag";
        $(function () {
            $("#reset").hide();
            sum = 18; //当前可控制的总开关数
            sbtd = new Array();
            sbtd.push(8-2); //1号设备 0-7  去掉2个主阀控制 之后为池塘开关控制 2-7
            sbtd.push(12); //2号设备 增加扩展为12个口 0-11
            //readkg();
            loadkgflag();
            timer = setInterval(readkg, 3000);
            timer = setInterval(loadkgflag, 3000);
            //loadkgflag();
        })
        
        function loadkgflag(){
            $.ajax({
                url: getkgflag_url,
                data: {
                },
                type: 'POST',
                timeout: 15000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    //<img src="<%=path%>/images/oicn_6.gif" /><a href="javascript:cgkg(2,1,1)">打开</a>
                    
                    if (json != "") {
                        for(zjnum in json){
                            var zjn = parseInt(zjnum);
                            var qsn = getQsnum(zjn);
                            console.log("jsonp.filter:" + json[zjn].join("-") );
//                            var n = sbtd[zjn];
//                                if(zjn == 0){
//                                    n = n - 2;
//                                }
                            var j = sbtd[zjn];
//                            if(zjn == 0){
//                                j = j + 2;
//                            }
                            if(zjn == 0){
                                if(json[zjn][7] == 0){
                                    $("#elc_1" ).html("<img src=\"<%=path%>/images/oicn_6a.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg(-1,1)\">打开</a>");  
                                }else{
                                    $("#elc_1" ).html("<img src=\"<%=path%>/images/oicn_6.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg(-1,2)\">关闭</a>");
                                }
                                if(json[zjn][6] == 0){
                                    $("#elc_2").html("<img src=\"<%=path%>/images/oicn_6a.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg(0,1)\">打开</a>");  
                                }else{
                                    $("#elc_2").html("<img src=\"<%=path%>/images/oicn_6.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg(0,2)\">关闭</a>");
                                }
                            }
                            for(var i = sbtd[zjn]; i > 0 ;i --){
                                //alert(json[zjn][i]);
                                
                                if(json[zjn][i - 1] == 0){
                                    $("#kg_" + qsn).html("<img src=\"<%=path%>/images/oicn_6a.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg("+qsn+",1)\">打开</a>");  
                                }else{
                                    $("#kg_" + qsn).html("<img src=\"<%=path%>/images/oicn_6.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg("+qsn+",2)\">关闭</a>");
                                }
                                j --;
                                qsn ++;
                            }
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
        //根据主机号获取起始池塘号
        function getQsnum(zj){
            var num = 0;
            for(var i = 0;i < zj;i ++){
                num += sbtd[i];
            }
            num += 1;
            return num;
        }
        //按当前需求 有客户端发送请求获取状态数据，当客户端过多时 采用服务器定时加载数据到缓存
        function readkg(){
            var gnm = "01";
            var pym = "0000";
            var msg = "";
            var zj = "";
            var tdm = "";
            for(var i = 0; i < sbtd.length;i ++){
                if(i < (10-1)){
                    zj = "0" + (i+1);
                }else{
                    zj = "" + (i+1).toString(16);
                }
                
                if(i == 0){
                    tdm = (sbtd[i] + 2).toString(16);
                }else{
                    tdm = sbtd[i].toString(16);
                }
                
                if(tdm.length < 2){
                    tdm = "0" + tdm;
                }
                //alert(tdm);
                msg = zj + gnm + pym + "00" + tdm;
                $.ajax({
                        url: senddata_url,
                        data: {data: msg 
                        },
                        type: 'POST',
                        timeout: 15000,
                        dataFilter: function (json) {
                            console.log("jsonp.filter:" + json);
                            return json;
                        },
                        success: function (json, textStatus) {
                            if (json != "") {
                                $("#remsg").append(json);
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                        }
                    });
            }
        }
        //开始执行
        var alldata ;
        var runflag = false;
        var elc = "";
        
        
        $("#read").click(function () {
            readkg();
        });
        $("#reset").click(function () {
            if(runflag){
                $("#span_msg").empty();
                $("#span_msg").css("color","red");
                $("#span_msg").append("当前任务仍在继续，请完成当前任务后再继续操作");
                return ;
            }
            location.reload();
        });
        $("#save").click(function () {
            //alert($("input[name='elc']:checked").val() );
            
            elc = $("input[name='elc']:checked").val();
            
            if(elc == "" || elc == undefined ){
                alert("请选择饵料池");
                return ;
            }
            sendElcMsg(elc,1);
            sleep(500);
            if(runflag){
                $("#span_msg").empty();
                $("#span_msg").css("color","red");
                $("#span_msg").append("当前任务仍在继续，请完成当前任务后再继续操作");
                return ;
            }
            $("#reset").show();
            runflag = true;
            $("#save").hide();
            alldata = new Array();
            for(var i = 1;i <= sum;i ++){
                if($("#inp_"+i ).val() != ""){
                    alldata.push(i);
                }
            }
            cnum = alldata.length;
            if(alldata.length < 1){
                alert("请输入操作内容后执行");
                return; 
            }
            var xhnum = 0;
            if(alldata.length < maxnum){
                xhnum = alldata.length;
            }else{
                xhnum = maxnum;
            }
            //resetTime(inpnum,$("#inp_" + inpnum).val());
            for(var i = 1;i <= xhnum;i ++){
                //cnum ++;
                var inpnum = alldata.shift();
                resetTime(inpnum,$("#inp_" + inpnum).val());
                //alert(inpnum);
//                setTimeout(function() {  
//                    //alert("hello");  
//                    resetTime(inpnum,$("#inp_" + inpnum).val());
//                }, 0)  
                //resetTime(inpnum,$("#inp_" + inpnum).val());
                //sleep(2000);
            }
        })
        var savelog_url = "<%=path%>/ifs/econtrol/econtrol/saveLog";
       
        
        //倒计时
        function resetTime(snum,time) {
            //var snum = "1";
            //inpnum = inpnum + "";
            console.log(snum + "开始================================");
            sendMsg(snum,1);
            var timer = null;
            var t = time;
            var m = 0;
            var s = 0;
            m = Math.floor(t / 60 % 60);
            m < 10 && (m = '0' + m);
            s = Math.floor(t % 60);
            function countDown() {
                inpnum = inpnum + "";
                s--;
                s < 10 && (s = '0' + s);
                if (s.length >= 3) {
                    s = 59;
                    if(Number(m) - 1 < 10){
                        m = "0" + (Number(m) - 1);
                    }else{
                        m = (Number(m) - 1);
                    }
                }
                //console.log("m====" + ":" + m + "分钟" + s + "秒");
                if (m.length >= 3 && m == '0-1') {
                    //alert(m);
                    m = '00';
                    s = '00';
                    $("#cont_"+snum).empty();
                    $("#cont_"+snum).html("已完成 总计 " + time + "秒");
                    sendMsg(snum,2);
                    $.ajax({
                        url: savelog_url,
                        data: {ctno: snum,time :time
                        },
                        type: 'POST',
                        timeout: 15000,
                        success: function (json, textStatus) {
                                
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                        }
                    });
                    console.log("cnum:" + cnum);
                    if(cnum == 1){
                            runflag = false;
                            $("#span_msg").empty();
                            $("#span_msg").css("css","forestgreen");
                            $("#span_msg").append("任务已成功完成");
                            sendElcMsg(elc,2);
                        //save log
                        }else{
                            if(alldata.length > 0){
                                var inpnum = alldata.shift();
                                resetTime(inpnum,$("#inp_" + inpnum).val());
                            }
                            cnum --;
                        }
                        console.log(snum + "结束================================");
//                    if(alldata.length > 0){
//                        runflag = false;
//                            $("#span_msg").empty();
//                            $("#span_msg").css("css","forestgreen");
//                            $("#span_msg").append("任务已成功完成");
//                    }else{
//                        var inpnum = alldata.shift();
//                            resetTime(inpnum,$("#inp_" + inpnum).val());
//                            cnum --;
//                    }
                    clearInterval(timer); 
                }else{
                    //console.log(snum + ":" + m + "分钟" + s + "秒");
                    $("#cont_"+snum).empty();
                    $("#cont_"+snum).html(m + "分钟" + s + "秒");
                }
            }
            timer = setInterval(countDown, 1000);
            //console.log(timer + "===timer===");
        }
        
        // type 1 开 2 关
        var senddata_url = "ifs/econtrol/econtrol/sendData";
        function sendMsg(sbnum,type){
            var j = 0;
            var tdnum;
            var type1 = "FF00";
            var type2 = "0000";
            var zhuji = ""; //主机号
            var td = "";
            var msg = "";
            var str16 = "";
            for(var i = 0;i < sbtd.length;i ++){
                j += sbtd[i];
                if(sbnum <= j){

                        tdnum = sbtd[i] - (j - sbnum);
                        tdnum = tdnum - 1; //从0位开始
                        if(i == 0 ){
                            tdnum += 2;
                        }

                    
                    if(i < 10 ){
                        zhuji = "0" + (i+1) + "05";
                    }else{
                        str16 = i.toString(16);
                        if(str16.length == 1){
                            str16 = "0" + str16;
                        }
                        zhuji = str16 + "05";
                    }
                    if(tdnum < 10){
                        td = "000" + tdnum;
                    }else{
                        str16 = tdnum.toString(16);
                        if(str16.length == 1){
                            str16 = "0" + str16;
                        }
                        td = "00" + str16;
                    }
                    if(type == 1){
                        msg = zhuji + td + type1;
                    }else{
                        msg = zhuji + td + type2;
                    }
                    $.ajax({
                        url: senddata_url,
                        data: {data: msg 
                        },
                        type: 'POST',
                        timeout: 15000,
                        dataFilter: function (json) {
                            console.log("jsonp.filter:" + json);
                            return json;
                        },
                        success: function (json, textStatus) {
                            if (json != "") {
                                $("#remsg").append(json);
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                        }
                    });
                    //console.log("type:" + type + " sbnum:"+ sbnum +" j:" + j + " tdnum:" + tdnum );
                    return ;
                }
            }
        }
        function sendElcMsg(sbnum,type){
            var j = 0;
            var tdnum;
            var type1 = "FF00";
            var type2 = "0000";
            var zhuji = ""; //主机号
            var td = "";
            var msg = "";
            var str16 = "";
            //for(var i = 0;i < sbtd.length;i ++){
            //    j += sbtd[i];
            //    if(sbnum <= j){
                    tdnum = sbnum;
                    tdnum = tdnum - 1; //从0位开始
                    zhuji = "0105";
                    td = "000" + tdnum;
                   
                    if(type == 1){
                        msg = zhuji + td + type1;
                    }else{
                        msg = zhuji + td + type2;
                    }
                    $.ajax({
                        url: senddata_url,
                        data: {data: msg 
                        },
                        type: 'POST',
                        timeout: 15000,
                        dataFilter: function (json) {
                            console.log("jsonp.filter:" + json);
                            return json;
                        },
                        success: function (json, textStatus) {
                            if (json != "") {
                                $("#remsg").append(json);
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                        }
                    });
                    //console.log("type:" + type + " sbnum:"+ sbnum +" j:" + j + " tdnum:" + tdnum );
                    return ;
        
        }
        function sleep(n) {
            var start = new Date().getTime();
            //  console.log('休眠前：' + start);
            while (true) {
                if (new Date().getTime() - start > n) {
                    break;
                }
            }
            // console.log('休眠后：' + new Date().getTime());
        }
    </script>
</html> 