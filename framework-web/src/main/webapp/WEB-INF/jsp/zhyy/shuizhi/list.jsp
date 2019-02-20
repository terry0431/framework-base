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
            <li class="parent"><a>水质管理</a></li>
            <li class="current"><a>水质管理</a></li>
        </ul>

            <div class="titlst">
                <div class="box_c">
                    <h3 class="box_c_tit">查询</h3>
                    <ul class="search">
                        <li>
                            <span class="mlr5 left">养殖场：</span>
                            <div class="left mlr5">
                                <select id="zhyy_yangzhichang_id">
                                    <option value="1">固德水产有限公司</option>
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
    	titleinfo["titles"] = ",水温（℃）,盐度,水温（℃）,盐度,PH,DO2（mg/L）,浊度,水面情况,水深（cm）";
    	titleinfo["keys"] = "id,s_wh_shuiwen,s_wh_yandu,s_ct_shuiwen,s_ct_yandu,s_ct_ph,s_ct_d02,s_ct_zhuodu,s_ct_shuimian,s_ct_shuishen";
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
        caption:"水质管理",
        head:"水质列表",
        method:"<%=path%>/ifs/zhyy/shuizhi/list",
        buttonList:[
            {name:"新增", className:"btn_1", style:"",event:"click", eventFunc:addZhyyShuizhi,customArg:{}},
//            {name:"导出", className:"btn_2", event:"click", eventFunc:exportFun},
//            {name:"导入", className:"btn_3", event:"click", eventFunc:importFun},
//            {name:"打印", className:"btn_4", event:"click", eventFunc:printFun},
            {name:"刷新", className:"btn_5", event:"click", eventFunc:searchlist},
            {name:"删除", className:"btn_9", event:"click", eventFunc:deleteAll}
        ],
        colModel:[
            {name:'checkbox', value:iconImg.checkBoxIcon, width:"10%",align:"center" ,event:"click", eventFunc:selclick},
//            {name:'view', value:'查看', width:"10%",className:"",align:"center"},
            {name:'edit', value:'编辑', width:"10%",className:"",align:"center"},
            {name:'SDate', value:'日期', width:"10%",align:"center"},	
            {name:'SWhShuiwen', value:'外海水温（℃）', width:"10%",align:"center"},	
            {name:'SWhYandu', value:'外海盐度', width:"10%",align:"center"},		
            {name:'SCtShuiwen', value:'池塘水温（℃）', width:"10%",align:"center"},	
            {name:'SCtYandu', value:'池塘盐度', width:"10%",align:"center"},	
            {name:'SCtPh', value:'池塘PH', width:"10%",align:"center"},	
            {name:'SCtD02', value:'池塘DO2（mg/L）', width:"10%",align:"center"},	
            {name:'SCtZhuodu', value:'池塘浊度', width:"10%",align:"center"},	
            {name:'SCtShuimian', value:'池塘水面情况', width:"10%",align:"center"},	
            {name:'SCtShuishen', value:'池塘水深（cm）', width:"10%",align:"center"},	
            {name:"delete", value:'删除', width:"10%", align:"center"}
        ]
    }
    function initARG(){
            initOption.arg={
                    s_ct_cengshu:$("#s_shuiceng").val(),
                    s_wh_shuiwen:$("#s_wh_shuiwen").val(),
                    s_wh_yandu:$("#s_wh_yandu").val(),
                    s_ct_shuiwen:$("#s_ct_shuiwen").val(),
                    s_ct_yandu:$("#s_ct_yandu").val(),
                    s_ct_ph:$("#s_ct_ph").val(),
                    s_ct_d02:$("#s_ct_d02").val(),
                    s_ct_zhuodu:$("#s_ct_zhuodu").val(),
                    s_ct_shuimian:$("#s_ct_shuimian").val(),
                    s_ct_shuishen:$("#s_ct_shuishen").val()
                    }
    }
    function dataHandle(list, opt) {
        datalist = list;
        var rows = [];
        var shuimian = "";
        for (var i = 0, j = list.length; i < j; i++) {
            if(list[i].s_ct_shuimian == "-1"){
                shuimian = "";
            }else if(list[i].s_ct_shuimian == "1"){
                shuimian = "水面无冰";
            }
            else if(list[i].s_ct_shuimian == "2"){
                shuimian = "结薄冰";
            }
            else if(list[i].s_ct_shuimian == "3"){
                shuimian = "薄冰融化";
            }
            else if(list[i].s_ct_shuimian == "4"){
                shuimian = "冰已结大半";
            }else if(list[i].s_ct_shuimian == "5"){
                shuimian = "水面已封冻";
            }
            rows.push({
                cell:{
                    id:{value:list[i].id},
                    checkbox:{value:"<input type=\"checkbox\" name=\"checks\" value=\""+list[i].mainid+"\" />", align:"center"},
                    SDate:{value:list[i].s_date, align:"center"},
                    SWhShuiwen:{value:list[i].s_wh_shuiwen, align:"center"},
                    SWhYandu:{value:list[i].s_wh_yandu, align:"center"},
                    SCtShuiwen:{value:list[i].s_ct_shuiwen, align:"center"},
                    SCtYandu:{value:list[i].s_ct_yandu, align:"center"},
                    SCtPh:{value:list[i].s_ct_ph, align:"center"},
                    SCtD02:{value:list[i].s_ct_d02, align:"center"},
                    SCtZhuodu:{value:list[i].s_ct_zhuodu, align:"center"},
                    SCtShuimian:{value:shuimian, align:"center"},
                    SCtShuishen:{value:list[i].s_ct_shuishen, align:"center"},
                    SYear:{value:list[i].s_year, align:"center"},
                    SMonth:{value:list[i].s_month, align:"center"},
                    SDay:{value:list[i].s_day, align:"center"},
                    SDate:{value:list[i].s_date, align:"center"},
//                    view:{value:iconImg.infoIcon, event:"click", eventFunc:viewZhyyShuizhi, customArg:{}, align:"center"},
                    edit:{value:iconImg.editIcon, event:"click", eventFunc:editZhyyShuizhi, customArg:{}, align:"center"},
                    "delete":{value:iconImg.deleteIcon,event:"click", eventFunc:deleteZhyyShuizhi, customArg:{}, align:"center"}
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
    function addZhyyShuizhi() {
         formDialog = new $.dialog({
                id:'formDialogFrame',
                title:"新增水质信息",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/zhyy/shuizhi/add',
                autoPos:true
	});
    }


    //编辑方法
    function editZhyyShuizhi(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        formDialog = new $.dialog({
                    id: 'formDialogFrame',
                    title: "编辑水质信息",
                    height: 500,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/shuizhi/edit?id=' + id,
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
		    content: 'url:<%=path%>/con/zhyy/shuizhi/view?id=' + id,
                    autoPos:true
                });
    }
    
    var delurl = "<%=path%>/ifs/zhyy/shuizhi/delete";
    function deleteZhyyShuizhi(e) {
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