<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
List<Map<String,Object>> zhyy_yangzhichangList = (List)request.getAttribute("zhyy_yangzhichangList");
List<Map<String,Object>> zhyy_yangzhiquList = (List)request.getAttribute("zhyy_yangzhiquList");
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
            <li class="parent"><a>水质数据管理</a></li>
            <li class="current"><a>水质数据管理</a></li>
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
                            <span class="mlr5 left">养殖区：</span>
                            <div class="left mlr5">
                                <select id="yzq"></select>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">智控设备：</span>
                            <div class="left mlr5">
                                <select id="rtu"></select>
                            </div>
                        </li>
<!--                        <li>
                            <span class="mlr5 left">水层数：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="s_cengshu" type="text"/>
                            </div>
                        </li>-->
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
    	titleinfo["titles"] = "水温（℃）,盐度,PH,DO2（mg/L）,浊度,水面情况,水深（cm）,,,,,层数（1-5层 ，0 平均数据）,,,,,,";
    	titleinfo["keys"] = "s_shuiwen,s_yandu,s_ph,s_d02,s_zhuodu,s_shuimian,s_shuishen,s_year,s_month,s_day,s_systime,s_cengshu,s_caijishijian,s_flag,zhyy_yangzhichang_id,zhyy_yangzhiqu_id,zhyy_rtu_id,";
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
        caption:"水质数据管理",
        head:"水质数据列表",
        method:"<%=path%>/ifs/zhyy/shuizhishuju/list",
        buttonList:[
//            {name:"新增", className:"btn_1", style:"",event:"click", eventFunc:addZhyyShuizhishuju,customArg:{}},
//            {name:"导出", className:"btn_2", event:"click", eventFunc:exportFun},
//            {name:"导入", className:"btn_3", event:"click", eventFunc:importFun},
//            {name:"打印", className:"btn_4", event:"click", eventFunc:printFun},
            {name:"刷新", className:"btn_5", event:"click", eventFunc:searchlist}
//            {name:"删除", className:"btn_9", event:"click", eventFunc:deleteAll}
        ],
        colModel:[

            {name:'SCaijishijian', value:'采集时间', width:"150px",align:"center"},	
//            {name:'SSystime', value:'系统记录时间', width:"150px",align:"center"},	
            {name:'SShuiwen', value:'水温（℃）', width:"10%",align:"center"},	
            {name:'SYandu', value:'盐度', width:"10%",align:"center"},	
            {name:'SPh', value:'PH', width:"10%",align:"center"},	
            {name:'SD02', value:'DO2（mg/L）', width:"10%",align:"center"},	
            {name:'SZhuodu', value:'浊度', width:"10%",align:"center"},	
//            {name:'SShuimian', value:'水面情况', width:"10%",align:"center"},	
            {name:'SShuishen', value:'水深（cm）', width:"10%",align:"center"},	
            {name:'SCengshu', value:'水层', width:"10%",align:"center"}
        ]
    }
    function initARG(){
            initOption.arg={
                    s_shuiwen:$("#s_shuiwen").val(),
                    s_yandu:$("#s_yandu").val(),
                    s_ph:$("#s_ph").val(),
                    s_d02:$("#s_d02").val(),
                    s_zhuodu:$("#s_zhuodu").val(),
                    s_shuimian:$("#s_shuimian").val(),
                    s_shuishen:$("#s_shuishen").val(),
                    s_year:$("#s_year").val(),
                    s_month:$("#s_month").val(),
                    s_day:$("#s_day").val(),
//                    s_systime:$("#s_systime").val(),
                    s_cengshu:$("#s_cengshu").val(),
                    s_caijishijian:$("#s_caijishijian").val(),
                    s_flag:$("#s_flag").val(),
                            zhyy_yangzhichang_id:$("#yzc").val(),
                    zhyy_yangzhiqu_id:$("#yzq").val(),
                    zhyy_rtu_id:$("#rtu").val(),
                    }
    }
    function dataHandle(list, opt) {
        datalist = list;
        var rows = [];
        var cjsj = "";
        
        for (var i = 0, j = list.length; i < j; i++) {
            cjsj = list[i].s_caijishijian.substring(0,10) + " " + list[i].s_hour + "时";
            rows.push({
                cell:{
                    id:{value:list[i].mainid},
                    checkbox:{value:"<input type=\"checkbox\" name=\"checks\" value=\""+list[i].mainid+"\" />", align:"center"},
                    SCaijishijian:{value:cjsj, align:"center"},
//                    SSystime:{value:list[i].s_systime, align:"center"},
                    SShuiwen:{value:list[i].s_shuiwen, align:"center"},
                    SYandu:{value:list[i].s_yandu, align:"center"},
                    SPh:{value:list[i].s_ph, align:"center"},
                    SD02:{value:list[i].s_d02, align:"center"},
                    SZhuodu:{value:list[i].s_zhuodu, align:"center"},
                    SShuimian:{value:list[i].s_shuimian, align:"center"},
                    SShuishen:{value:list[i].s_shuishen, align:"center"},
                    SCengshu:{value:list[i].s_cengshu, align:"center"},
                    SFlag:{value:list[i].s_flag, align:"center"},
                    edit:{value:iconImg.editIcon, event:"click", eventFunc:editZhyyShuizhishuju, customArg:{}, align:"center"},
                    "delete":{value:iconImg.deleteIcon,event:"click", eventFunc:deleteZhyyShuizhishuju, customArg:{}, align:"center"}
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
        searchlist();
    }
    $("#yzc").change(function(){        
        getYzqList($("#yzc").val(),init_yzqsel);
    });
    $("#yzq").change(function(){        
        getRtuList($("#yzq").val(),init_rtusel);
    });
    $("#rtu").change(function(){        
        searchlist();
    });
    var formDialog;
    function addZhyyShuizhishuju() {
         formDialog = new $.dialog({
                id:'formDialogFrame',
                title:"新增**",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/zhyy/shuizhishuju/add',
                autoPos:true
	});
    }



    //编辑方法
    function editZhyyShuizhishuju(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        formDialog = new $.dialog({
                    id: 'formDialogFrame',
                    title: "编辑**",
                    height: 500,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/shuizhishuju/edit?id=' + id,
                    autoPos:true
                });

    }
    var delurl = "<%=path%>/ifs/zhyy/shuizhishuju/delete"; 
    function deleteZhyyShuizhishuju(e) {
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