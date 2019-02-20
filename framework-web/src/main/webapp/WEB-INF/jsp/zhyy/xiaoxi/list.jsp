<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
%>
<html>
<head>
    <title></title>
    <%@ include file="../../include/commontop.jsp" %>
</head>
<body>
<div class="Area">
    <div class="mainRight">
        <ul class="Location Grayline mb10 Gray_bg">
            <li class="parent"><img src="<%=path%>/common/style1/images/icon_14.gif" width="14" height="14"/>当前位置</li>
            <li class="parent"><a>消息列表</a></li>
            <li class="current"><a>消息列表</a></li>
        </ul>

            <div class="titlst">
                <div class="box_c">
                    <h3 class="box_c_tit">查询</h3>
                    <ul class="search">
                        <li>
                            <span class="mlr5 left">养殖场：</span>
                            <div class="left mlr5">
                                <select id="yzc"><option value="-1">全部</option></select>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">级别：</span>
                            <div class="left mlr5">
                                <select id="x_jibie">
                                    <option value="-1">全部</option>
                                    <option value="1">严重</option>
                                    <option value="2">较重</option>
                                    <option value="3">一般</option>
                                </select>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">处理情况：</span>
                            <div class="left mlr5">
                                <select id="x_chuli">
                                    <option value="-1">全部</option>
                                    <option value="0">未处理</option>
                                    <option value="1">已处理</option>
                                </select>
                            </div>
                        </li>
                        <li><span class="btn2" id="search">查询</span></li>
                    </ul>
                    <div class="box_c" id="tableDiv"></div>
                </div>
            </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var datalist ;
    function exportFun(){
    	var titleinfo = new Object();
    	titleinfo["titles"] = "发送时间,消息内容,级别,";
    	titleinfo["keys"] = "x_systime,x_content,x_jibie,";
    	exportExcelFroData(titleinfo,datalist);
    }
    function importFun(){
    }
    function printFun(){
    }
    function rowClickFunc(e) {
        var rowObj = e.data.arg;
    }
    function selclick(){
    	$("input[name=checks]:checkbox").each(function() {
    	    $(this).click();
		})
    }

    var initOption = {
        container:'#tableDiv',
        caption:"消息管理",
        head:"消息列表",
        method:"<%=path%>/ifs/zhyy/xiaoxi/list",
        buttonList:[
            {name:"已读", className:"btn_2", event:"click", eventFunc:xxyidu},
            {name:"刷新", className:"btn_5", event:"click", eventFunc:searchlist}
        ],
        colModel:[
            {name:'checkbox', value:iconImg.checkBoxIcon, width:"10%",align:"center" ,event:"click", eventFunc:selclick},
            {name:"XView", value:'查看', width:"50px", align:"center"},
            {name:'XSystime', value:'发送时间', width:"150px",align:"center"},	
            {name:'Yzc', value:'养殖场', width:"300px",align:"center"},	
            {name:'XJibie', value:'级别', width:"50px",align:"center"},	
            {name:'XContent', value:'消息内容', width:"",align:"center"},	
            {name:"XChuli", value:'处理', width:"50px", align:"center"}
        ]
    }
    function initARG(){
            initOption.arg={
                zhyy_yangzhichang_id:$("#yzc").val(),
                x_chuli:$("#x_chuli").val(),
                x_jibie:$("#x_jibie").val()
            }
    }
    function dataHandle(list, opt) {
        datalist = list;
        var rows = [];
        var jibie = "";
        var chuli = "";
        var yidu = "";
        //var stime = new Array("2019-05-21 21:33:00","2019-05-22 05:12:00","2019-05-23 14:05:00");
        for (var i = 0, j = list.length; i < j; i++) {
            if(list[i].x_jibie == "1"){
                jibie = "严重";
            }else if(list[i].x_jibie == "1"){
                jibie = "较重";
            }else{
                jibie = "一般";
            }
            //alert(parseInt(list[i].x_yidu));
            if(parseInt(list[i].x_yidu) > 0){
                yidu = "已读";
            }else{
                yidu = "<input type=\"checkbox\" name=\"checks\" value=\""+list[i].id+"\" />";
            }
            if(list[i].x_chuli == "0"){
                chuli = "<img onclick=\"editZhyyXiaoxi("+list[i].id+")\" style=\"cursor: pointer\" src=\"<%=path%>/common/style1/images/icon_7.gif\">";
            }else{
                chuli = "已处理";
            }
            rows.push({
                cell:{
                    id:{value:list[i].id},
                    checkbox:{value:yidu, align:"center"},
                    XView:{value:iconImg.infoIcon, event:"click", eventFunc:view, customArg:{}, align:"center"},
                    XSystime:{value:list[i].x_systime, align:"center"},
                    Yzc:{value:list[i].y_mingcheng, align:"center"},
                    XContent:{value:list[i].x_content, align:"center"},
                    XJibie:{value:jibie, align:"center"},
                    XChuli:{value:chuli, align:"center"}
                }
            });
        }
        return rows;
    }
    function searchlist(){
        initARG();
        JC.table(initOption);
    }
    $(function () {
        getYzcList(init_yzcsel);
        
    });
    
    function init_yzcsel(json) {
            var optstr = "";
            for (var i = 0; i < json.length; i++) {
                optstr += "<option value=\"" + json[i].id + "\">" + json[i].y_mingcheng + "</option>";
            }
            $("#yzc").append(optstr);
            $("#search").click(searchlist);
            searchlist();
        }
    var formDialog;
    //变更为已处理
    function editZhyyXiaoxi(id) {
//        var cellObj = e.data.arg;
//        var id = cellObj.cell.id.value
        formDialog = new $.dialog({
                    id: 'formDialogFrame',
                    title: "处理消息",
                    zIndex:2000,
                    height: 600,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/xiaoxi/edit?id=' + id,
                    autoPos:true
                });
    }
    
    var viewDialog;
    //变更为已处理
    function view(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        formDialog = new $.dialog({
                    id: 'viewDialogFrame',
                    title: "查看消息",
                    zIndex:2000,
                    height: 600,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/xiaoxi/view?id=' + id,
                    autoPos:true
                });
    }
    var chuli_url = "<%=path%>/ifs/zhyy/xiaoxi/chuli";
    //变更为已处理
    function chuli(id){
        if (confirm("确认该消息已处理完成？")) {
        $.ajax({
                url: chuli_url,
                data: {id: id
                },
                type: 'POST',
                timeout: fw_config.ajax_time,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if(json == "1"){
                        alert("保存成功");
                        searchlist();
                    }else{
                        alert("保存失败");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
    }
    var yidu_url = "<%=path%>/ifs/zhyy/xiaoxi/yidu";
    function xxyidu(){
        var ids = "";
        $("input[name=checks][checked]").each(function() {
    	//$("[name='checks'][checked]").each(function(){
//            alert($(this).val());
    		if(ids != ""){
    			ids += "," +  $(this).val() ;
    		}else{
    			ids = $(this).val() ;
    		}
	})
        if (confirm("确认选中的消息已全部查看？")) {
            $.ajax({
                url: yidu_url,
                data: {xxids: ids
                },
                type: 'POST',
                timeout: fw_config.ajax_time,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if(json == "1"){
                        alert("保存成功");
                        searchlist();
                    }else{
                        alert("保存失败");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
    }
    
</script>
</html>