<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
List<Map<String,Object>> zhyy_rtuList = (List)request.getAttribute("zhyy_rtuList");
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
            <li class="parent"><a>**</a></li>
            <li class="current"><a>**</a></li>
        </ul>

            <div class="titlst">
                <div class="box_c">
                    <h3 class="box_c_tit">查询</h3>
                    <ul class="search">
                        <li>
                            <span class="mlr5 left">养殖场：</span>
                            <div class="left mlr5">
                                <select id="yzc"></select>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">智控设备：</span>
                            <div class="left mlr5">
                                <select id="rtu"></select>
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
    var rtulist_url = "<%=path%>/ifs/zhyy/getRtuszListByRtuid";
    var datalist ;
    function exportFun(){
    	var titleinfo = new Object();
    	titleinfo["titles"] = "系统记录时间,采集数据时间,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
    	titleinfo["keys"] = "r_systime,r_caijishijian,r_year,r_month,r_day,ai_1,ai_2,ai_3,ai_4,ai_5,ai_6,ai_7,ai_8,di_1,di_2,di_3,di_4,di_5,di_6,di_7,di_8,do_1,do_2,do_3,do_4,do_5,do_6,do_7,do_8,zhyy_rtu_id,";
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

    function initOpt(){
        $.ajax({
                url: url,
                data: {rtuid: $("rtuid").val()
                },
                type: 'POST',
                timeout: 3000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    var colmodel = new Array();
                    if(json != ""){

                        for(var i = 0;i < json.length;i ++){
                            colmodel[i ] = new Object();
                            colmodel[i]["name"] = json[i].s_mingcheng;
                            colmodel[i]["value"] = "";
                            colmodel[i]["width"] = 100/json.length + "%";
                            colmodel[i]["align"] = "center";
                        }
                        initOption = colmodel;
                    }else{
                        initOption = {
                            container:'#tableDiv',
                            caption:"**管理",
                            head:"**列表",
                            method:"<%=path%>/ifs/zhyy/rtu_data/list",
                            buttonList:[
                                {name:"新增", className:"btn_1", style:"",event:"click", eventFunc:addZhyyRtuData,customArg:{}},
                                {name:"导出", className:"btn_2", event:"click", eventFunc:exportFun},
                                {name:"导入", className:"btn_3", event:"click", eventFunc:importFun},
                                {name:"打印", className:"btn_4", event:"click", eventFunc:printFun},
                                {name:"刷新", className:"btn_5", event:"click", eventFunc:searchlist},
                                {name:"删除", className:"btn_9", event:"click", eventFunc:deleteAll}
                            ],
                            colModel:[
                                {name:'checkbox', value:iconImg.checkBoxIcon, width:"10%",align:"center" ,event:"click", eventFunc:selclick},
                                {name:'edit', value:'编辑', width:"10%",className:"",align:"center"},
                                {name:'RSystime', value:'系统记录时间', width:"10%",align:"center"},	
                                {name:'RCaijishijian', value:'采集数据时间', width:"10%",align:"center"},	
                                {name:'RYear', value:'', width:"10%",align:"center"},	
                                {name:'RMonth', value:'', width:"10%",align:"center"},	
                                {name:'RDay', value:'', width:"10%",align:"center"},	
                                {name:'ai1', value:'', width:"10%",align:"center"},	
                                {name:'ai2', value:'', width:"10%",align:"center"},	
                                {name:'ai3', value:'', width:"10%",align:"center"},	
                                {name:'ai4', value:'', width:"10%",align:"center"},	
                                {name:'ai5', value:'', width:"10%",align:"center"},	
                                {name:'ai6', value:'', width:"10%",align:"center"},	
                                {name:'ai7', value:'', width:"10%",align:"center"},	
                                {name:'ai8', value:'', width:"10%",align:"center"},	
                                {name:'di1', value:'', width:"10%",align:"center"},	
                                {name:'di2', value:'', width:"10%",align:"center"},	
                                {name:'di3', value:'', width:"10%",align:"center"},	
                                {name:'di4', value:'', width:"10%",align:"center"},	
                                {name:'di5', value:'', width:"10%",align:"center"},	
                                {name:'di6', value:'', width:"10%",align:"center"},	
                                {name:'di7', value:'', width:"10%",align:"center"},	
                                {name:'di8', value:'', width:"10%",align:"center"},	
                                {name:'do1', value:'', width:"10%",align:"center"},	
                                {name:'do2', value:'', width:"10%",align:"center"},	
                                {name:'do3', value:'', width:"10%",align:"center"},	
                                {name:'do4', value:'', width:"10%",align:"center"},	
                                {name:'do5', value:'', width:"10%",align:"center"},	
                                {name:'do6', value:'', width:"10%",align:"center"},	
                                {name:'do7', value:'', width:"10%",align:"center"},	
                                {name:'do8', value:'', width:"10%",align:"center"},	
                                {name:"delete", value:'删除', width:"10%", align:"center"}
                            ]
                        }
                    }
                    
                }
        });
    }

    
    var initOption ;
    function initARG(){
            initOption.arg={
                    r_systime:$("#r_systime").val(),
                    r_caijishijian:$("#r_caijishijian").val(),
                    r_year:$("#r_year").val(),
                    r_month:$("#r_month").val(),
                    r_day:$("#r_day").val(),
                    ai_1:$("#ai_1").val(),
                    ai_2:$("#ai_2").val(),
                    ai_3:$("#ai_3").val(),
                    ai_4:$("#ai_4").val(),
                    ai_5:$("#ai_5").val(),
                    ai_6:$("#ai_6").val(),
                    ai_7:$("#ai_7").val(),
                    ai_8:$("#ai_8").val(),
                    di_1:$("#di_1").val(),
                    di_2:$("#di_2").val(),
                    di_3:$("#di_3").val(),
                    di_4:$("#di_4").val(),
                    di_5:$("#di_5").val(),
                    di_6:$("#di_6").val(),
                    di_7:$("#di_7").val(),
                    di_8:$("#di_8").val(),
                    do_1:$("#do_1").val(),
                    do_2:$("#do_2").val(),
                    do_3:$("#do_3").val(),
                    do_4:$("#do_4").val(),
                    do_5:$("#do_5").val(),
                    do_6:$("#do_6").val(),
                    do_7:$("#do_7").val(),
                    do_8:$("#do_8").val(),
                            zhyy_rtu_id:$("#zhyy_rtu_id").val(),
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
                    RSystime:{value:list[i].r_systime, align:"center"},
                    RCaijishijian:{value:list[i].r_caijishijian, align:"center"},
                    RYear:{value:list[i].r_year, align:"center"},
                    RMonth:{value:list[i].r_month, align:"center"},
                    RDay:{value:list[i].r_day, align:"center"},
                    ai1:{value:list[i].ai_1, align:"center"},
                    ai2:{value:list[i].ai_2, align:"center"},
                    ai3:{value:list[i].ai_3, align:"center"},
                    ai4:{value:list[i].ai_4, align:"center"},
                    ai5:{value:list[i].ai_5, align:"center"},
                    ai6:{value:list[i].ai_6, align:"center"},
                    ai7:{value:list[i].ai_7, align:"center"},
                    ai8:{value:list[i].ai_8, align:"center"},
                    di1:{value:list[i].di_1, align:"center"},
                    di2:{value:list[i].di_2, align:"center"},
                    di3:{value:list[i].di_3, align:"center"},
                    di4:{value:list[i].di_4, align:"center"},
                    di5:{value:list[i].di_5, align:"center"},
                    di6:{value:list[i].di_6, align:"center"},
                    di7:{value:list[i].di_7, align:"center"},
                    di8:{value:list[i].di_8, align:"center"},
                    do1:{value:list[i].do_1, align:"center"},
                    do2:{value:list[i].do_2, align:"center"},
                    do3:{value:list[i].do_3, align:"center"},
                    do4:{value:list[i].do_4, align:"center"},
                    do5:{value:list[i].do_5, align:"center"},
                    do6:{value:list[i].do_6, align:"center"},
                    do7:{value:list[i].do_7, align:"center"},
                    do8:{value:list[i].do_8, align:"center"},
                    edit:{value:iconImg.editIcon, event:"click", eventFunc:editZhyyRtuData, customArg:{}, align:"center"},
                    "delete":{value:iconImg.deleteIcon,event:"click", eventFunc:deleteZhyyRtuData, customArg:{}, align:"center"}
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
        $("#search").click(searchlist);
    });
    function init_yzcsel(json) {
            var optstr = "";
            for (var i = 0; i < json.length; i++) {
                optstr += "<option value=\"" + json[i].id + "\">" + json[i].y_mingcheng + "</option>";
            }
            $("#yzc").append(optstr);
            getRtuListByYzcid($("#yzc").val(),init_zhikong);
            
    }
    function init_zhikong(json){
        $("#rtu").empty();
        //alert(json);
        var optstr = "";
        for (var i = 0; i < json.length; i++) {
            optstr += "<option value=\"" + json[i].zhyy_rtu_id + "\">" + json[i].zhyy_rtu_id + "</option>";
        }
        $("#rtu").append(optstr);
        initOpt();
        searchlist();
    }
    var formDialog;
    function addZhyyRtuData() {
         formDialog = new $.dialog({
                id:'formDialogFrame',
                title:"新增**",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/zhyy/rtu_data/add',
                autoPos:true
	});
    }



    //编辑方法
    function editZhyyRtuData(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        formDialog = new $.dialog({
                    id: 'formDialogFrame',
                    title: "编辑**",
                    height: 500,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/rtu_data/edit?id=' + id,
                    autoPos:true
                });

    }
    var delurl = "<%=path%>/ifs/zhyy/rtu_data/delete"; 
    function deleteZhyyRtuData(e) {
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