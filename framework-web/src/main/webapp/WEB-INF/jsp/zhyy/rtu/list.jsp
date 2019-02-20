<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
List<Map<String,Object>> zhyy_yangzhichangList = (List)request.getAttribute("zhyy_yangzhichangList");
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
            <li class="parent"><a>智控设备管理</a></li>
            <li class="current"><a>智控设备管理</a></li>
        </ul>

            <div class="titlst">
                <div class="box_c">
                    <h3 class="box_c_tit">查询</h3>
                    <ul class="search">
                        <li>
                            <span class="mlr5 left">养殖场：</span>
                            <div class="left mlr5">
                                <select id="zhyy_yangzhichang_id">
                                    <option value="">全部</option>
                                <%
                                for(Map zhyy_yangzhichangmap : zhyy_yangzhichangList){
                                %>
                                <option value="<%=zhyy_yangzhichangmap.get("id")%>"><%=zhyy_yangzhichangmap.get("y_mingcheng")%></option>
                                <%
                                }
                                %>
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
    	titleinfo["titles"] = ",";
    	titleinfo["keys"] = "zhyy_yangzhichang_id,";
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
    	    $(this).parent().parent().click();
		})
    }
    
    var initOption = {
        container:'#tableDiv',
        caption:"智控设备管理",
        head:"智控设备列表",
        method:"<%=path%>/ifs/zhyy/rtu/list",
        buttonList:[
            {name:"新增", className:"btn_1", style:"",event:"click", eventFunc:addZhyyRtu,customArg:{}},
            
//            {name:"导入", className:"btn_3", event:"click", eventFunc:importFun},
//            {name:"打印", className:"btn_4", event:"click", eventFunc:printFun},
            {name:"刷新", className:"btn_5", event:"click", eventFunc:searchlist}
//            {name:"删除", className:"btn_9", event:"click", eventFunc:deleteAll}
        ],
        colModel:[
            {name:'checkbox', value:iconImg.checkBoxIcon, width:"10%",align:"center" ,event:"click", eventFunc:selclick},

            {name:'set', value:'设置', width:"10%",className:"",align:"center"},
            {name:'tongb', value:'同步时间', width:"10%",className:"",align:"center"},
            {name:'SBID', value:'设备ID', width:"",align:"center"},
            {name:'yzc', value:'养殖场', width:"",align:"center"},
            {name:'servertime', value:'服务器时间', width:"",align:"center"},
            {name:'datetime', value:'客户端时间', width:"",align:"center"},
            {name:'sjc', value:'时间差', width:"",align:"center"}
//            {name:"delete", value:'删除', width:"10%", align:"center"}
        ]
    }
    function initARG(){
            initOption.arg={
                            zhyy_yangzhichang_id:$("#zhyy_yangzhichang_id").val()
                    }
    }
    function dataHandle(list, opt) {
        datalist = list;
        var rows = [];
        for (var i = 0, j = list.length; i < j; i++) {
            rows.push({
                cell:{
                    id:{value:list[i].mainid},
                    checkbox:{value:"<input type=\"checkbox\" name=\"checks\" value=\""+list[i].mainid+"\" />", align:"center"},
                    
                    set:{value:iconImg.editIcon, event:"click", eventFunc:setZhyyRtu, customArg:{}, align:"center"},
                    tongb:{value:iconImg.editIcon, event:"click", eventFunc:setDatetime, customArg:{}, align:"center"},
                    SBID:{value:list[i].mainid, align:"center"},
                    yzc:{value:list[i].y_mingcheng, align:"center"},
                    servertime:{value:list[i].servertime, align:"center"},
                    datetime:{value:list[i].datetime, align:"center"},
                    sjc:{value:list[i].sjc, align:"center"}
//                    "delete":{value:iconImg.deleteIcon,event:"click", eventFunc:deleteZhyyRtu, customArg:{}, align:"center"}
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
        $("#search").click(searchlist);
        searchlist();
    });
    var formDialog;
    function addZhyyRtu() {
         formDialog = new $.dialog({
                id:'formDialogFrame',
                title:"新增**",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/zhyy/rtu/add',
                autoPos:true
	});
    }



    //编辑方法
    function editZhyyRtu(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value;
        formDialog = new $.dialog({
                    id: 'formDialogFrame',
                    title: "编辑**",
                    height: 500,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/rtu/edit?id=' + id,
                    autoPos:true
                });

    }
    var delurl = "<%=path%>/ifs/zhyy/rtu/delete"; 
    function deleteZhyyRtu(e) {
	var cellObj = e.data.arg;
        var ids = cellObj.cell.id.value;
        if (confirm("确认删除本条数据？")) {
            $.ajax({
                url: delurl,
                data: {ids: ids
                },
                type: 'POST',
                timeout: 10000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if (json == "1") {
                        alert("删除成功");
                        searchlist();
                    } else if (data == "0") {
                        alert("删除失败");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
    }

    function deleteAll() {
    	var ids = "";
    	$("[name='checks'][checked]").each(function(){
    		if(ids != ""){
    			ids += "," +  $(this).val() ;
    		}else{
    			ids = $(this).val() ;
    		}
	})
        if (confirm("确认删除选中数据？")) {
            
            $.ajax({
                url: delurl,
                data: {ids: ids
                },
                type: 'POST',
                timeout: 3000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if (json == "1") {
                        alert("删除成功");
                        searchlist();
                    } else if (data == "0") {
                        alert("删除失败");
                    }

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
    }
    function setZhyyRtu(e){
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value;
        formDialog = new $.dialog({
                    id: 'szformDialogFrame',
                    title: "智控设备设置",
                    height: 700,
                    width: 1650,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/rtu_shezhi/edit?id=' + id,
                    autoPos:true
                });
    }
    var tburl = "ifs/zhyy/shebeijiankong/setTiem";
    function setDatetime(e){
        
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value;
        $.ajax({
                url: tburl,
                data: {rtuid: id
                },
                type: 'POST',
                timeout: 3000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if (json == "1") {
                        alert("同步成功,下次接收数据时更新间隔时间，请勿重复同步");
                        searchlist();
                    } else if (data == "0") {
                        alert("同步失败");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
    }
</script>
</html>