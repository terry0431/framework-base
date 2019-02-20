<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
            <li class="parent"><a>气象数据管理</a></li>
            <li class="current"><a>气象数据管理</a></li>
        </ul>
            <div class="titlst">
                <div class="box_c">
                    <h3 class="box_c_tit">查询</h3>
                    <ul class="search">
<!--                        <li>
                            <span class="mlr5 left">气温：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="q_qiwen" type="text"/>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">风向：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="q_fengxiang" type="text"/>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">最小风力：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="q_zuixiaofengli" type="text"/>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">最大风力：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="q_zuidafengli" type="text"/>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">天气：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="q_tianqi" type="text"/>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">日期：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="q_riqi" type="text"/>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">：</span>
                            <div class="left mlr5">
                                <select id="zhyy_shebei_id">
                     
                                <option value="1">固德水产有限公司</option>
                        
                                </select>
                            </div>
                        </li>
                        <li><span class="btn2" id="search">查询</span></li>-->
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
    	titleinfo["titles"] = "气温,风向,风力,天气,日期";
    	titleinfo["keys"] = "q_qiwen,q_fengxiang,q_fengli,q_tianqi,q_riqi,zhyy_shebei_id";
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
        caption:"气象数据管理",
        head:"气象数据列表",
        method:"<%=path%>/ifs/zhyy/qixiang/list",
        buttonList:[
            {name:"新增", className:"btn_1", style:"",event:"click", eventFunc:addZhyyQixiang,customArg:{}},
//            {name:"导出", className:"btn_2", event:"click", eventFunc:exportFun},
//            {name:"导入", className:"btn_3", event:"click", eventFunc:importFun},
//            {name:"打印", className:"btn_4", event:"click", eventFunc:printFun},
            {name:"刷新", className:"btn_5", event:"click", eventFunc:searchlist},
            {name:"删除", className:"btn_9", event:"click", eventFunc:deleteAll}
        ],
        colModel:[
            {name:'checkbox', value:iconImg.checkBoxIcon, width:"10%",align:"center" ,event:"click", eventFunc:selclick},
            {name:'view', value:'查看', width:"10%",className:"",align:"center"},
            {name:'edit', value:'编辑', width:"10%",className:"",align:"center"},
            {name:'QQiwen', value:'气温', width:"10%",align:"center"},	
            {name:'QFengxiang', value:'风向', width:"10%",align:"center"},	
            {name:'QFengli', value:'风力', width:"10%",align:"center"},	
            {name:'QTianqi', value:'天气', width:"10%",align:"center"},	
            {name:'QRiqi', value:'日期', width:"10%",align:"center"},	
            {name:"delete", value:'删除', width:"10%", align:"center"}
        ]
    }
    function initARG(){
            initOption.arg={
                    q_qiwen:$("#q_qiwen").val(),
                    q_fengxiang:$("#q_fengxiang").val(),
                    q_fengli:$("#q_fengli").val(),
                    q_tianqi:$("#q_tianqi").val(),
                    q_riqi:$("#q_riqi").val(),
                            zhyy_shebei_id:$("#zhyy_shebei_id").val(),
                    }
    }
    function dataHandle(list, opt) {
        datalist = list;
        var rows = [];
        //var fengli = new Array("","多云","阴天","雾","小雨","中雨","大雨","暴雨","雷阵雨","冰雹","冻雨","小雪","中雪","大雪","冻霜");
        var fengxiang = new Array("东风","东北风","东南风","西风","西北风","西南风","北风","南风");
        var tianqi = new Array("晴","多云","阴天","雾","小雨","中雨","大雨","暴雨","雷阵雨","冰雹","冻雨","小雪","中雪","大雪","冻霜","阵雨","雨夹雪");
        for (var i = 0, j = list.length; i < j; i++) {
            if(list[i].q_fengxiang != "-1"){
                list[i].q_fengxiang = fengxiang[parseInt(list[i].q_fengxiang) - 1]
            }else{
                list[i].q_fengxiang = "";
            }
            
            if(list[i].q_tianqi != "-1"){
                list[i].q_tianqi = tianqi[parseInt(list[i].q_tianqi) - 1]
            }else{
                list[i].q_tianqi = "";
            }
            
            if(list[i].q_fengli != "-1"){
                list[i].q_fengli = list[i].q_fengli + "级风";
            }else{
                list[i].q_fengli = "";
            }
            rows.push({
                cell:{
                    id:{value:list[i].mainid},
                    checkbox:{value:"<input type=\"checkbox\" name=\"checks\" value=\""+list[i].mainid+"\" />", align:"center"},
                    view:{value:iconImg.infoIcon, event:"click", eventFunc:viewZhyyQixiang, customArg:{}, align:"center"},
                    QQiwen:{value:list[i].q_qiwen, align:"center"},
                    QFengxiang:{value:list[i].q_fengxiang, align:"center"},
                    QFengli:{value:list[i].q_fengli, align:"center"},
                    QTianqi:{value:list[i].q_tianqi, align:"center"},
                    QRiqi:{value:list[i].q_riqi, align:"center"},
                    edit:{value:iconImg.editIcon, event:"click", eventFunc:editZhyyQixiang, customArg:{}, align:"center"},
                    "delete":{value:iconImg.deleteIcon,event:"click", eventFunc:deleteZhyyQixiang, customArg:{}, align:"center"}
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
    function addZhyyQixiang() {
         formDialog = new $.dialog({
                id:'addDialogFrame',
                title:"新增气象数据",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/zhyy/qixiang/add',
                autoPos:true
	});
    }


    //编辑方法
    function editZhyyQixiang(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        formDialog = new $.dialog({
                    id: 'editDialogFrame',
                    title: "编辑气象数据",
                    height: 500,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/qixiang/edit?id=' + id,
                    autoPos:true
                });
    }
    var viewDialog;
    function viewZhyyQixiang(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        viewDialog = new $.dialog({
                    id: 'viewDialogFrame',
                    title: "气象数据",
                    height: 500,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/qixiang/view?id=' + id,
                    autoPos:true
                });
    }
    var delurl = "<%=path%>/ifs/zhyy/qixiang/delete"; 
    function deleteZhyyQixiang(e) {
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