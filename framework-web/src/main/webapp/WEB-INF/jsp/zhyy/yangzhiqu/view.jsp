<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String id = "";
    if(request.getAttribute("id") != null){
        id = (String)request.getAttribute("id");
    }else{
        id = "";
    }
    List<Map<String,Object>> zhyy_yangzhichangList =  (List)request.getAttribute("zhyy_yangzhichangList");
%>
<html>
<head>
    <title></title>
    <%@ include file="../../include/commontop.jsp"%>
</head>
<body>
<div class="box_Bomb">
    <div class="box_b_t">
        
        <form id="forms">
            <h2 class="mb10">
            <span class="icon12">水质指标</span>
            </h2>
            <table id="tab_ai" class="box_tab_1" >
            </table>
            <h2 class="mb10">
            <span class="icon12">设备状态</span>
            </h2>
            <table id="tab_di" class="box_tab_1" >
            </table>
            <h2 class="mb10">
            <span class="icon12">设备开关</span>
            </h2>
            <table id="tab_do" class="box_tab_1" >
            </table>
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
        var id = "<%=id%>";
        $(function () {
            JCTools.intInit($("#QQiwen"));
            if(id != ""){
               loaddata();
               setInterval(loaddata, 30000);
            }
        })
	function loaddata(){
            var url = "<%=path%>/ifs/zhyy/yangzhiqu/getYzq";
            $.ajax({
                url: url,
                data: {yzqid: "<%=id%>"
                },
                type: 'POST',
                timeout: 3000,
                success: function (json, textStatus) {
                    var textstr = "";
                    var datatime = "";
                    //var style = "font-color:red";
                    //var style = "font-color:green";
                    if(json != ""){
                        var contentstr = "";
                        for(var i = 0;i < json.length;i ++){
                            if(json[i].s_leibie == "ai" &&  json[i].s_obj == "zhyy_shuizhishuju" ){
                                if(json[i].val == undefined){
                                    textstr = "暂无数据";
                                    datatime = "";
                                }else{
                                    textstr = json[i].val;
                                    datatime = json[i].datatime;
                                }
                                contentstr +=   "<tr>"+
                                                    "<td class=\"tab1_tit\">"+json[i].s_mingcheng+"：</td>"+
                                                    "<td title=\""+json[i].title+"\" style=\"color:"+json[i].style+"\">"+textstr+"</td>"+
                                                    "<td >最新更新时间:"+datatime+"</td>"+
                                                "</tr>";
                            }
                        }
                        $("#tab_ai").empty();
                        $("#tab_ai").append(contentstr);
                    }
                    if(json != ""){
                        var contentstr = "";
                        
                        for(var i = 0;i < json.length;i ++){
                            if(json[i].s_leibie == "di" || json[i].s_attr.indexOf('s_dianliu') > -1 ){
                                if(json[i].s_leibie == "di" ){
                                    if(json[i].val == "1"){
                                        textstr = "运行中";
                                        datatime = json[i].datatime;
                                    }else if(json[i].val == "0"){
                                        textstr = "未运行";
                                        datatime = json[i].datatime;
                                    }else{
                                        textstr = "暂无数据";
                                        datatime = "";
                                    }
                                }else{
                                    if(json[i].val == undefined){
                                        textstr = "暂无数据";
                                        datatime = "";
                                    }else{
                                        textstr = json[i].val;
                                        datatime = json[i].datatime;
                                    }
                                }
                                contentstr +=   "<tr>"+
                                                    "<td class=\"tab1_tit\">"+json[i].s_mingcheng+"：</td>"+
                                                    "<td title=\""+json[i].title+"\" style=\"color:"+json[i].style+"\">"+textstr+"</td>"+
                                                    "<td >最新更新时间:"+datatime+"</td>"+
                                                "</tr>";
                            }
                        }
                        $("#tab_di").empty();
                        $("#tab_di").append(contentstr);
                    }
                    if(json != ""){
                        var contentstr = "";
                        for(var i = 0;i < json.length;i ++){
                            if(json[i].s_leibie == "do"){
                                if(json[i].val == "1"){
                                    textstr = "开&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:onoffclick('"+json[i].zhyy_rtu_id+"','"+json[i].s_tongdao+"','OFF')\" style=\"color:blue\">关闭</a>";
                                    datatime = json[i].datatime;
                                }else if(json[i].val == "0"){
                                    textstr = "关&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:onoffclick('"+json[i].zhyy_rtu_id+"','"+json[i].s_tongdao+"','ON')\" style=\"color:blue\">打开</a>";
                                    datatime = json[i].datatime;
                                }else{
                                    textstr = "暂无数据";
                                    datatime = "";
                                }
                                contentstr +=   "<tr>"+
                                                    "<td class=\"tab1_tit\">"+json[i].s_mingcheng+"：</td>"+
                                                    "<td title=\""+json[i].title+"\" style=\"color:"+json[i].style+"\">"+textstr+"</td>"+
                                                    "<td >最新更新时间:"+datatime+"</td>"+
                                                "</tr>";
                                }
                        }
                        $("#tab_do").empty();
                        $("#tab_do").append(contentstr);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
        function onoffclick(rtuid,donum, val) {
            sendMsg(rtuid,"#SETDO," + donum + "," + val + ";");
        }
        var sb_smsg_url = "/framework-web/ifs/zhyy/shebeijiankong/sendCode";
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
                        alert("命令发送成功,开关状态需要1-3分钟后更新,请不要重复发送命令");
                    } else {
                        alert("命令发送失败" );
                    }
                },
                error: function (data) {
                    alert("error: " + JSON.stringify(data));
                }
            })
        }
</script>
</html>