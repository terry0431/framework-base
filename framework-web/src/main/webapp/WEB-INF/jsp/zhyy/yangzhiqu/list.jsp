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
            <li class="parent"><a>养殖池管理</a></li>
            <li class="current"><a>养殖池管理</a></li>
        </ul>

            <div class="titlst">
                <div class="box_c">
                    <h3 class="box_c_tit">查询</h3>
                    <ul class="search">
                        <li>
                            <span class="mlr5 left">养殖场：</span>
                            <div class="left mlr5">
                                <select id="zhyy_yangzhichang_id">
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
                        <li>
                            <span class="mlr5 left">区号：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="y_num" type="text"/>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">编号：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="y_bianhao" type="text"/>
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
    	titleinfo["titles"] = "区号,坐标x,坐标y,编号,标准水深,养殖场";
    	titleinfo["keys"] = "y_num,y_x,y_y,y_bianhao,y_shuishen,zhyy_yangzhichang_id";
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
        caption:"养殖池管理",
        head:"养殖池列表",
        method:"<%=path%>/ifs/zhyy/yangzhiqu/list",
        buttonList:[
            {name:"新增", className:"btn_1", style:"",event:"click", eventFunc:addZhyyYangzhiqu,customArg:{}},
            {name:"刷新", className:"btn_5", event:"click", eventFunc:searchlist},
            {name:"删除", className:"btn_9", event:"click", eventFunc:deleteAll}
        ],
        colModel:[
            {name:'checkbox', value:iconImg.checkBoxIcon, width:"10%",align:"center" ,event:"click", eventFunc:selclick},
            {name:'edit', value:'编辑', width:"10%",className:"",align:"center"},
            {name:'YNum', value:'区号', width:"10%",align:"center"},	
            {name:'YBianhao', value:'编号', width:"10%",align:"center"},	
            {name:'YShuishen', value:'标准水深', width:"10%",align:"center"},	
            {name:'YX', value:'坐标x', width:"10%",align:"center"},	
            {name:'YY', value:'坐标y', width:"10%",align:"center"},	
            {name:"delete", value:'删除', width:"10%", align:"center"}
        ]
    }
    function initARG(){
            initOption.arg={
                        y_num:$("#y_num").val(),
                        y_bianhao:$("#y_bianhao").val(),
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
                    YNum:{value:list[i].y_num, align:"center"},
                    YX:{value:list[i].y_x, align:"center"},
                    YY:{value:list[i].y_y, align:"center"},
                    YBianhao:{value:list[i].y_bianhao, align:"center"},
                    YShuishen:{value:list[i].y_shuishen, align:"center"},
                    edit:{value:iconImg.editIcon, event:"click", eventFunc:editZhyyYangzhiqu, customArg:{}, align:"center"},
                    "delete":{value:iconImg.deleteIcon,event:"click", eventFunc:deleteZhyyYangzhiqu, customArg:{}, align:"center"}
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
    function addZhyyYangzhiqu() {
         formDialog = new $.dialog({
                id:'formDialogFrame',
                title:"新增养殖池",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/zhyy/yangzhiqu/add',
                autoPos:true
	});
    }



    //编辑方法
    function editZhyyYangzhiqu(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        formDialog = new $.dialog({
                    id: 'formDialogFrame',
                    title: "编辑养殖池",
                    height: 500,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/yangzhiqu/edit?id=' + id,
                    autoPos:true
                });

    }
    var delurl = "<%=path%>/ifs/zhyy/yangzhiqu/delete"; 
    function deleteZhyyYangzhiqu(e) {
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