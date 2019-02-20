<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
%>
<html>
<head>
    <title></title>
    <%@ include file="../../../include/commontop.jsp" %>
</head>
<body>
<div class="Area">
    <div class="mainRight">
        <ul class="Location Grayline mb10 Gray_bg">
            <li class="parent"><img src="<%=path%>/common/style1/images/icon_14.gif" width="14" height="14"/>当前位置</li>
            <li class="parent"><a>输水日志管理</a></li>
            <li class="current"><a>输水日志管理</a></li>
        </ul>

            <div class="titlst">
                <div class="box_c">
                    <h3 class="box_c_tit">查询</h3>
                    <ul class="search">
<!--                        <li>
                            <span class="mlr5 left">：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="sys_time" type="text"/>
                            </div>
                        </li>-->
                        <li>
                            <span class="mlr5 left">池塘号：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="c_chitang" type="text"/>
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
    	titleinfo["titles"] = ",,,";
    	titleinfo["keys"] = "sys_time,c_chitang,c_shijian,";
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
        caption:"输水日志管理",
        head:"输水日志列表",
        method:"<%=path%>/ifs/econtorl/log/list",
        buttonList:[
            {name:"刷新", className:"btn_5", event:"click", eventFunc:searchlist}
        ],
        colModel:[
            {name:'sysTime', value:'操作时间', width:"10%",align:"center"},	
            {name:'CChitang', value:'养殖池', width:"10%",align:"center"},	
            {name:'CShijian', value:'输水时间', width:"10%",align:"center"},	
            {name:"delete", value:'删除', width:"10%", align:"center"}
        ]
    }
    function initARG(){
            initOption.arg={
                    sys_time:$("#sys_time").val(),
                    c_chitang:$("#c_chitang").val(),
                    c_shijian:$("#c_shijian").val(),
                            }
    }
    function dataHandle(list, opt) {
        datalist = list;
        var rows = [];
        for (var i = 0, j = list.length; i < j; i++) {
            rows.push({
                cell:{
                    id:{value:list[i].mainid},
                    sysTime:{value:list[i].sys_time, align:"center"},
                    CChitang:{value:list[i].c_chitang + "号池塘", align:"center"},
                    CShijian:{value:list[i].c_shijian + "秒", align:"center"},
                    "delete":{value:iconImg.deleteIcon,event:"click", eventFunc:deleteConLog, customArg:{}, align:"center"}
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
    function addConLog() {
         formDialog = new $.dialog({
                id:'formDialogFrame',
                title:"新增**",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/econtorl/log/add',
                autoPos:true
	});
    }



    //编辑方法
    function editConLog(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        formDialog = new $.dialog({
                    id: 'formDialogFrame',
                    title: "编辑**",
                    height: 500,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/econtorl/log/edit?id=' + id,
                    autoPos:true
                });

    }
    var delurl = "<%=path%>/ifs/econtorl/log/delete"; 
    function deleteConLog(e) {
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
</script>
</html>