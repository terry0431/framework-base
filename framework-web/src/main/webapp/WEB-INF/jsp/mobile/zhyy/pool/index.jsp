<%--
  User: wangbo
  Date: 2019\2\26 0026
  Time: 9:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>智慧渔业信息管理平台</title>
    <%@ include file="../../include/common.jsp" %>
    <style type="text/css">
        #slider-container {
            position: relative;
            float: right;
            margin-top: -10px !important;
        }

        #slider-container .ui-slider {
            margin-top: -50px !important;
        }

        a {
            text-decoration: none
        }
    </style>
    <script type="text/javascript">
        var pagelistsize = 30;
        var num = 0;
        var groupnum = 0; //当前池塘组 （当前分页 页数）
        var target_impvalue = 0;//当前池塘号
        //var maxnum ; //同时开启开关最大数   -> $("#concurrentNum").val()
        var sbtd; //设备通道集合
        var sbnum = 120;//当前可控制的有效设备数
        var settimedata = new Array(sbnum);//记录设备输水时间
        var switchState = new Array(sbnum);//记录设备开关状态
        //var jobinfo = new Array(sbnum);//任务信息  与settimedata重复了
        var stoplist = new Array();//手动停止列表
        var swnum = 2;// 顺位数2  当设备1号的情况从3通道开始
        var cnum; //当前剩余任务数

        var CorrectionValue = 200;//修正值 毫秒  发送任务信息到服务器 + 修正值   根据网络环境设置
        var currentsbnum; //当前选中池塘号

        var getkgflag_url =     "<%=path%>/ifs/econtrol/econtrol/getKgFlag";    //获取开关状态
        var checkJob_url =      "<%=path%>/ifs/econtrol/econtrol/getJob"//;     //获取任务信息
        var savelog_url =       "<%=path%>/ifs/econtrol/econtrol/saveLog";      //保存日志
        var runjob_url =        "<%=path%>/ifs/econtrol/econtrol/runJob";       //执行任务
        var loadstate_url =     "<%=path%>/ifs/econtrol/econtrol/loadState";    //获取开关状态
        var send_url =          "<%=path%>/ifs/econtrol/econtrol/sendData";     //发送指令

        var alldata;
        var runflag = false;
        var elc = "";

        var rtuid = "hongyuan";
        $(function () {
            //$("#runjob").prop('disabled','').addClass("ui-disabled").button('refresh');
            //$("#runjob").attr('disabled','disabled').button('refresh');
            //$("#id").prop('disabled',false).removeClass("ui-disabled");
            //$("#runjob").attr('disabled','disabled').checkboxradio('refresh'); ;
            sbtd = new Array();
            sbtd.push(8 - 2); //1号设备 0-7  去掉2个主阀控制 之后为池塘开关控制 2-7
            sbtd.push(12); //2号设备 增加扩展为12个口 0-11

            $('#btn_set').bind('click', function () {
                if ($("#imptime").val() == "" || $("#imptime").val() == "0") {
                    settimedata[target_impvalue] = "";
                    $("#cont_" + (target_impvalue + 1)).empty();
                } else {
                    settimedata[target_impvalue] = $("#imptime").val();
                    $("#imptime").val("");
                    $("#cont_" + (target_impvalue + 1)).html(settimedata[target_impvalue] + "秒");
                }
            });
            $('#btn_pre').bind('click', function () {
                updateGroup(-1);
            });
            $('#btn_next').bind('click', function () {
                updateGroup(1);
            });
            $('#runjob').bind('click', function () {
                runjob();
            });
            loadstate();
            initList();
            loadkgflag();
            checkJob();
            timer = setInterval(loadkgflag, 2000);//获取开关状态
            timer = setInterval(loadstate, 2000);//加载开关状态
            //timer = setInterval(loadstate, 3000);
            //$("#p_msg").show();
        })

        function loadstate() {
            $.ajax({
                url: loadstate_url,
                data: {
                    rtuid: rtuid
                },
                type: 'POST',
                timeout: 15000,
                dataFilter: function (json) {
                    //console.log("job state :" + json.run_state);
                    return json;
                },
                success: function (json, textStatus) {
                    console.log("jsonp:" + json);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }

        function checkJob() {
            $.ajax({
                url: checkJob_url,
                data: {
                    rtuid: rtuid
                },
                type: 'POST',
                timeout: 15000,
                dataFilter: function (json) {
                    //console.log("job state :" + json.run_state);
                    return json;
                },
                success: function (json, textStatus) {
                    if (json == null || json.length < 1) {
                        //无任务
                        return false;
                    } else {
                        //有任务
                        //加载任务信息
                        console.log("job state :" + json.run_state);
                        if (json.run_state) {//当前有任务
                            showMsg("当前有任务正在执行...");
                            console.log("job state ---- :" + json["run_state"]);
                            clearCt();
                            $("#runjob").attr('disabled', 'disabled').button('refresh');
                            //加载任务信息 显示倒计时
                            $("#concurrentNum").val(json.concurrentNum);
                            if (json.elcnum == 1) {
                                // $("input[name='elc']:first").prop("checked", true).checkboxradio('refresh');
                                $("input[name='elc']:first").attr("checked", true);
                                $("input[name='elc']:last").attr("checked", false);
                                // $("input[name='elc']:last").prop("checked", false);
                            } else {
                                // $("input[name='elc']:first").prop("checked", false).checkboxradio('refresh');
                                $("input[name='elc']:first").attr("checked", false);
                                $("input[name='elc']:last").attr("checked", true);
                                //$("input[name='elc']:last").prop("checked", true);
                            }


                            //$("input[name='radioName'][value=2]").attr("checked",true);
                            //$("#r_elc_2").attr('checked','true');
                            var ctnum;
                            alldata = new Array();
                            var executedtime = parseInt((json.currenttime - json.runtime) / 1000);

                            //var concurrentval ; //计算用存储器
                            var jtime = json.jobinfo; //计算用副本
                            // var ztime = json.jobinfo; //解析用副本
                            // var cnum; //循环并发数
                            var ii = 0;//next jtime 数组位
                            //var incnum = 0;//记录已经完成任务数
                            var mixnum = 0;//计算用数组内的最小值
                            //var clearjob = new Array();//已完成的任务
                            var jtimelength = 0;
                            while (executedtime > 0) {
                                jtimelength = 0;
                                for (var j = 0; j < jtime.length; j++) {
                                    if (jtime[j] > 0) {
                                        jtimelength++;
                                    }
                                }
                                if (jtimelength == 0) {
                                    alert("全部完成");
                                    return;
                                }
                                if (jtimelength >= json.concurrentNum) { //如果任务数大于并发数
                                    cnum = json.concurrentNum;
                                } else {
                                    cnum = jtimelength;
                                }

                                ii = 0;
                                //取最小值
                                mixnum = jtime.min();
                                //alert(mixnum);
                                //如果最小值大于任务已执行时间 循环剩余任务时间数组 减去 任务已执行时间
                                if (mixnum > executedtime) {
                                    //for(var i = 0;i < cnum;i ++){
                                    for (var j = 0; j < jtime.length; j++) {
                                        if (jtime[j] > 0) {
                                            jtime[j] = jtime[j] - executedtime;
                                            ii++;
                                        }
                                        if (ii == cnum) {
                                            break;
                                        }
                                    }
                                    break;
                                    //}
                                } else {
                                    executedtime = executedtime - mixnum;
                                    for (var j = 0; j < jtime.length; j++) {
                                        if (jtime[j] > 0) {
                                            jtime[j] = jtime[j] - mixnum;
                                            ii++;
                                        }
                                        if (ii == cnum) {
                                            break;
                                        }
                                    }
                                }
                            }
                            //alert(jtime);
                            for (var i = 0; i < jtime.length; i++) {
                                ctnum = getCtnumByLinenum(json.lineNum[i], json.equipmentNum[i] );
                                if (jtime[i] > 0) {

                                    settimedata[ctnum] = jtime[i];
                                    alldata.push(ctnum + 1);
                                } else {
                                    $("#cont_" + (ctnum + 1)).html("已完成");
                                }
                            }
                            cnum = jtime.length;
                            var xhnum = 0;

                            if (cnum < json.concurrentNum) {
                                xhnum = cnum;
                            } else {
                                xhnum = json.concurrentNum;
                            }

                            for (var i = 1; i <= xhnum; i++) {
                                var inpnum = alldata.shift();
                                resetTime(inpnum, settimedata[inpnum - 1]);
                            }
                        }
                        return true;
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }

        function clearCt() {
//             	for(var i = 1;i <= alldata.length;i ++){
//             		if(settimedata[i-1] != "" && parseInt(settimedata[i-1]) > 0){
//             			$("#cont_" + (target_impvalue + 1) ).empty();
//             		}
//             		$("#cont_" + alldata[i] ).empty();
//             	}
            settimedata = new Array();//清空池塘设置
        }

        function getCtnumByLinenum(linenum, zjnum) {
            var returnnum = 0;
            if (zjnum > 0) {
                for(var i = 0;i < zjnum ; i ++){
                    returnnum += sbtd[i];
                    if(i + 1 == zjnum){
                        return returnnum + linenum;
                    }
                }
                //return sbtd[zjnum - 2] + linenum;
            } else {
                return linenum - swnum;
            }
        }

        function loadkgflag() {
            $.ajax({
                url: getkgflag_url,
                data: {},
                type: 'POST',
                timeout: 15000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    //<img src="<%=path%>/images/oicn_6.gif" /><a href="javascript:cgkg(2,1,1)">打开</a>

                    if (json != "") {
                        for (zjnum in json) {
                            var zjn = parseInt(zjnum); //设备号
                            var qsn = getQsnum(zjn);   //起始号
                            console.log("jsonp.filter:" + json[zjn].join("-"));
                            //                            var n = sbtd[zjn];
                            //                                if(zjn == 0){
                            //                                    n = n - 2;
                            //                                }
                            var j = sbtd[zjn];
                            //                            if(zjn == 0){
                            //                                j = j + 2;
                            //                            }
                            if (zjn == 0) {
                                if (json[zjn][7] == 0) {
                                    $("#elc_1").html("<img src=\"<%=path%>/images/oicn_6a.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg(-1,1)\">打开</a>");
                                } else {
                                    $("#elc_1").html("<img src=\"<%=path%>/images/oicn_6.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg(-1,2)\">关闭</a>");
                                }
                                if (json[zjn][6] == 0) {
                                    $("#elc_2").html("<img src=\"<%=path%>/images/oicn_6a.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg(0,1)\">打开</a>");
                                } else {
                                    $("#elc_2").html("<img src=\"<%=path%>/images/oicn_6.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg(0,2)\">关闭</a>");
                                }
                            }
                            for (var i = sbtd[zjn]; i > 0; i--) {

                                //alert(json[zjn][i]);
                                switchState[qsn - 1] = json[zjn][i - 1];
                                if (qsn == currentsbnum) {
                                    if (switchState[qsn - 1] == "0") {//关闭状态
                                        $("#contorl_div").html("<img src=\"<%=path%>/images/oicn_6.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:contorlaction(" + qsn + ",1)\">打开</a>");
                                    } else if (switchState[qsn - 1] == "1") {
                                        $("#contorl_div").html("<img src=\"<%=path%>/images/oicn_6a.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:contorlaction(" + qsn + ",2)\">关闭</a>");
                                    } else {
                                        $("#contorl_div").html("暂无状态信息 ...");
                                    }
                                }
                                colorBuild(qsn);
// 	                                if(json[zjn][i - 1] == 0){
// 	                                    $("#kg_" + qsn).html("<img src=\"../images/oicn_6a.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg("+qsn+",1)\">打开</a>");
// 	                                }else{
// 	                                    $("#kg_" + qsn).html("<img src=\"../images/oicn_6.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:sendMsg("+qsn+",2)\">关闭</a>");
// 	                                }
                                j--;
                                qsn++;
                            }
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }

        function initList() {
            var ctstr = ""; //池塘列表内容
            var datasize = (groupnum + 1) * pagelistsize;
            if (datasize > sbnum) {
                datasize = sbnum;
            }
//                 var numstr = "";
            for (var i = (groupnum * pagelistsize) + 1; i <= datasize; i++) {//power-black forbidden-black
                ctstr += "<a class=\"ui-btn ui-btn-inline \" style=\"color:#363636;width:60px;height:30px; background:#CCCCCC;font-size:10px; \" onclick=\"setImpValue(" + i + ")\" id=\"a_" + i + "\" href=\"#setTime\">" + i + "号池<br/>";
                if (settimedata[i - 1] != null & settimedata[i - 1] != "") {
                    ctstr += "<span id=\"cont_" + i + "\">" + settimedata[i - 1] + "秒</span>";
                } else {
                    ctstr += "<span id=\"cont_" + i + "\"></span>";
                }
                ctstr += "</a>";
            }
            $("#ctlist").html(ctstr);

            for (var i = (groupnum * pagelistsize) + 1; i <= datasize; i++) {//power-black forbidden-black
                $('#a' + i).trigger("create");
            }

        }

        function onoff(sbnum) {
            alert(sbnum);
        }

        //根据主机号获取起始池塘号
        function getQsnum(zj) {
            var num = 0;
            for (var i = 0; i < zj; i++) {
                num += sbtd[i];
            }
            num += 1;
            return num;
        }


        //开关状态颜色表示  橘黄色 空闲关闭状态  绿色 任务中打开状态  灰色 无法读取状态
        function colorBuild(sbnum) {
            var color = "";
            if (switchState[sbnum - 1] == 0) {
                color = "#FAA732";
            } else if (switchState[sbnum - 1] == 1) {
                color = "#99CC99";
            } else {
                color = "#CCCCCC1`";
            }
            $("#a_" + sbnum).css("background", color);
        }

        function setImpValue(value) {
            currentsbnum = value;
            value--;
            target_impvalue = value;//当前设置的池塘号
            if (settimedata[value] != null) {
                $("#imptime").val(settimedata[value]);
            } else {
                $("#imptime").val("");
            }
//                 alert(switchState[value]);

            if (switchState[value] == "0") {//关闭状态
                $("#imptime").removeAttr("disabled");
                $("#contorl_div").html("<img src=\"<%=path%>/images/oicn_6.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:contorlaction(" + value + ",1)\">打开</a>");
            } else if (switchState[value] == "1") {
                $("#imptime").removeAttr("disabled");
                $("#contorl_div").html("<img src=\"<%=path%>/images/oicn_6a.gif\" />&nbsp;&nbsp;<a tabindex=\"-1\" href=\"javascript:contorlaction(" + value + ",2)\">关闭</a>");
            } else {
                $("#contorl_div").html("暂无状态信息 ...");
                //禁止设置时间
                $("#imptime").attr("disabled","disabled");
                // $("#imptime").prop('disabled','').addClass("ui-disabled").button('refresh');
                // $("#imptime").attr('disabled','disabled').button('refresh');
            }
        }

        function updateGroup(type) {
            groupnum = groupnum + type;
            if (groupnum < 0) {
                groupnum = 0;
                return;
            }
            var pcount = 0;
            if (sbnum % pagelistsize == 0) {
                pcount = sbnum / pagelistsize;
            } else {
                pcount = parseInt(sbnum / pagelistsize) + 1;
            }
            if (groupnum >= 0 && groupnum < pcount) {
                initList();
            } else {
                groupnum = groupnum - 1;
            }
        }

        // type 1 开 2 关
        function contorlaction(sbnum, type) {
            sendMsg(sbnum, type);
            $("#contorl_div").html("命令已发送，正在获取当前状态...");
            if (runflag && type == "2") { //关闭
                stoplist.push(sbnum);
            }
        }


        function loadjob() {
            var xhnum = 0;
            if (alldata.length < parseInt($("#concurrentNum").val())) {
                xhnum = alldata.length;
            } else {
                xhnum = parseInt($("#concurrentNum").val());
            }
            //resetTime(inpnum,$("#inp_" + inpnum).val());
            for (var i = 1; i <= xhnum; i++) {
                //cnum ++;
                var inpnum = alldata.shift();
                resetTime(inpnum, settimedata[inpnum - 1]);
            }
        }

        function runjob() {
            //alert($("input[name='elc']:checked").val() );
            //alert("runjob");
            elc = $("input[name='elc']:checked").val();

            if (elc == "" || elc == undefined) {
                alert("请选择饵料池");
                return;
            }
            //sendElcMsg(elc,1);  //*转移到服务器端执行
            //sleep(500);

//                 if(checkJob()){
// //                     $("#span_msg").empty();
// //                     $("#span_msg").css("color","red");
// //                     $("#span_msg").append("当前任务仍在继续，请完成当前任务后再继续操作");
//                     return ;
//                 }
            //$("#reset").show();
            runflag = true;
            //$("#save").hide();


//                 private boolean run_state = false; //当前任务执行状态
//             	private Integer[] jobinfo;	//任务信息
//             	private Integer[] lineNum;	//执行任务的通道
//             	private Integer elcnum;//饵料池号
//             	private Integer concurrentNum; //并发数
//             	private Integer[] equipmentNum;

            var job = new Object();
            job.elcnum = parseInt(elc);
            job.concurrentNum = parseInt($("#concurrentNum").val());
            var jobinfo = new Array();
            var lineNum = new Array();
            var equipmentNum = new Array();
            alldata = new Array();
            var j = 0;
            var lnum;
            for (var i = 1; i <= settimedata.length; i++) {
                if (settimedata[i - 1] != "" && parseInt(settimedata[i - 1]) > 0) {
                    alldata.push(i);
                    jobinfo.push(parseInt(settimedata[i - 1]));
                    j = 0;
                    for (var ii = 0; ii < sbtd.length; ii++) { //计算池塘开关是哪个控制设备的 开关线路号是多少
                        j += sbtd[ii];
                        if (i <= j) {
                            lnum = sbtd[ii] - (j - i);
                            lnum = lnum - 1; //从0位开始
                            if (ii == 0) { //1号机器顺位+2
                                lnum += swnum;
                            }
                            lineNum.push(lnum);
                            equipmentNum.push(ii);
                            break;
                        }
                    }
                }
            }
            job.jobinfo = jobinfo;
            job.lineNum = lineNum;
            job.equipmentNum = equipmentNum;
            cnum = alldata.length;
            if (alldata.length < 1) {
                alert("请输入操作内容后执行");
                return;
            }
            showMsg("开始执行任务...");
            $("#runjob").attr('disabled', 'disabled').button('refresh');
            //锁定执行按钮

            $.ajax({
                url: runjob_url,
                data: {
                    josnStr: JSON.stringify(job)
                },
                type: 'POST',
                timeout: 15000,
                success: function (json, textStatus) {
                    //alert(json);
                    if (json == "-1") {
                        checkjob();
                    } else {
                        var xhnum = 0;
                        if (alldata.length < parseInt($("#concurrentNum").val())) {
                            xhnum = alldata.length;
                        } else {
                            xhnum = parseInt($("#concurrentNum").val());
                        }
                        //resetTime(inpnum,$("#inp_" + inpnum).val());
                        for (var i = 1; i <= xhnum; i++) {
                            //cnum ++;
                            var inpnum = alldata.shift();
                            resetTime(inpnum, settimedata[inpnum - 1]);
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });


        }

        function resetTime(snum, time) {
            //var snum = "1";
            //inpnum = inpnum + "";
            console.log(snum + "开始================================");
            //sendMsg(snum,1);
            var timer = null;
            var t = time;
            var m = 0;
            var s = 0;
            m = Math.floor(t / 60 % 60);
            m < 10 && (m = '0' + m);
            s = Math.floor(t % 60);

            function countDown() {
                inpnum = inpnum + "";
                if (stoplist.length > 0) {//检查手动停止的列表中是否包含当前任务 如果有就立即停止该任务 并启动下一个任务
                    for (var nn = 0; nn < stoplist.length; nn++) {
                        if (stoplist[nn] == snum) {
                            clearInterval(timer); //停止倒计时
                            $("#cont_" + snum).html(""); //时间清空

                            if (cnum == 1) { //  当前为最后一个任务
                                runflag = false;
                                stoplist = new Array();//任务结束 清空手动停止列表
                                settimedata = new Array(sbnum);//清空所有设备的时间设置
// 		                            $("#span_msg").empty();
// 		                            $("#span_msg").css("css","forestgreen");
// 		                            $("#span_msg").append("任务已成功完成");
// 		                            sendElcMsg(elc,2);
                                //save log
                            } else {
                                if (alldata.length > 0) {
                                    var inpnum = alldata.shift();
                                    resetTime(inpnum, settimedata[inpnum - 1]);
                                }
                                cnum--;
                            }
                            return;
                        }
                    }
                }
                s--;
                s < 10 && (s = '0' + s);
                if (s.length >= 3) {
                    s = 59;
                    if (Number(m) - 1 < 10) {
                        m = "0" + (Number(m) - 1);
                    } else {
                        m = (Number(m) - 1);
                    }
                }
                //console.log("m====" + ":" + m + "分钟" + s + "秒");
                if (m.length >= 3 && m == '0-1') {
                    //alert(m);
                    clearInterval(timer);
                    m = '00';
                    s = '00';
                    //$("#cont_"+snum).empty();
                    $("#cont_" + snum).html("已完成");

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
                    if (cnum == 1) {
                        runflag = false;
// 	                            $("#span_msg").empty();
// 	                            $("#span_msg").css("css","forestgreen");
// 	                            $("#span_msg").append("任务已成功完成");
//                         alert("任务已完成");
                        showMsg("任务执行完成...");
                        clearCt();
                        $("#runjob").removeAttr("disabled").button('refresh');
                        //$("#runjob").attr('disabled','').button('refresh');
                        //sendElcMsg(elc,2);
                        //save log
                    } else {
                        if (alldata.length > 0) {
                            var inpnum = alldata.shift();
                            resetTime(inpnum, settimedata[inpnum - 1]);
                        }
                        cnum--;
                    }
                    console.log(snum + "结束================================");

                } else {
                    if (currentsbnum == snum) {
                        $("#imptime").val(parseInt(m) * 60 + parseInt(s));
                    }
                    $("#cont_" + snum).html(m + "分" + s + "秒");
                }
            }

            timer = setInterval(countDown, 1000);
        }

        function sendMsg(sbnum, type) {
            var j = 0;
            var tdnum;
            var zhuji = ""; //主机号
            var td = "";
            for (var i = 0; i < sbtd.length; i++) {
                j += sbtd[i];
                if (sbnum <= j) {
                    tdnum = sbtd[i] - (j - sbnum);
                    tdnum = tdnum - 1; //从0位开始
                    if (i == 0) {
                        tdnum += 2;
                    }
                    $.ajax({
                        url: send_url,
                        data: {
                            rtuid: rtuid, lineNum: tdnum, equipmentNum: i, type: type
                        },
                        type: 'POST',
                        timeout: 15000,
                        dataFilter: function (json) {
                            console.log("jsonp.filter:" + json);
                            return json;
                        },
                        success: function (json, textStatus) {
                            if (json == "1") {
                                //$("#remsg").append(json);
                                if (sbnum < 1) {
                                    $("#elc_" + (sbnum + 2)).html("正在执行...");
                                }
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                        }
                    });
                    return;
                }
            }
        }
        function showMsg(msg){
            $("#consolemsg").html(msg);
        }
        Array.prototype.min = function () {
            var min = this[0];
            this.forEach(function (ele, index, arr) {
                if (min == 0) {
                    if (ele > 0) {
                        min = ele;
                    }
                } else {
                    if (ele > 0 && ele < min) {
                        min = ele;
                    }
                }

            })
            return min;
        }
    </script>
</head>
<body>
<div data-role="page" id="pageone" data-theme="a">
    <div data-role="panel" id="setTime" data-dismissible="false"
         data-swipe-close="false">
        <h2>设置输水时间</h2>
        <input type="number" id="imptime" pattern="[0-9]*" max="3600"
               oninput="if(value.length>4)value=value.slice(0,4)"/>
        <a href="#" id="btn_set" data-rel="close"
           class="ui-btn ui-btn-inline ui-shadow ui-corner-all ui-btn-b ">设置</a>
        <h2>开关手动控制</h2>
        <div id="contorl_div"></div>
        <a href="#" data-rel="close"
           class="ui-btn ui-btn-inline ui-shadow ui-corner-all ui-btn-b ">返回</a>
    </div>

    <div data-role="main" class="ui-content">
        <div>
            <div data-role="navbar">
                <ul>
                    <li><a data-ajax="false" href="<%=path%>/con/mobile/zhyy/index" class="ui-btn-active">智慧渔业</a></li>
                </ul>
            </div><!-- /navbar -->
            <div id="consolemsg" style="color: #3C6E31;"></div>
            <div class="ui-field-contain">
                <label for="concurrentNum">最大并发数</label> <input type="text"
                                                                id="concurrentNum" value="4" placeholder="最大并发数"/>
            </div>
            <input data-role="none" type="radio" name="elc" id="r_elc_1" value="1" checked="checked">1号饵料池
            <span id="elc_1"></span>
            <br/>
            <input data-role="none" type="radio" name="elc" id="r_elc_2" value="2"/>2号饵料池
            <span id="elc_2"></span>
            <div id="ctlist">
            </div>
        </div>
        <div>
            <button class="ui-btn ui-btn-inline" style="width:45%" id="btn_pre">上一组</button>
            <button class="ui-btn ui-btn-inline" style="width:45%;float:right;    margin-right:0" id="btn_next">下一组
            </button>
        </div>
        <div>
            <input id="runjob" type="button" value="开始执行">
        </div>
    </div>
</div>
</body>
</html>
