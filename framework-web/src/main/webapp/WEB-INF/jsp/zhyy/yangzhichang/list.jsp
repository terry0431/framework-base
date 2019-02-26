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
            <li class="parent"><a>养殖场管理</a></li>
            <li class="current"><a>养殖场管理</a></li>
        </ul>

            <div class="titlst">
                <div class="box_c">
<!--                    <h3 class="box_c_tit">查询</h3>
                    <ul class="search">
                        <li>
                            <span class="mlr5 left">养殖场名称：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="y_mingcheng" type="text"/>
                            </div>
                        </li>
                        
                        <li>
                            <span class="mlr5 left">省份：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="y_sheng" type="text"/>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">城市：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="y_shi" type="text"/>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">区县：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="y_quxian" type="text"/>
                            </div>
                        </li>
                        <li><span class="btn2" id="search">查询</span></li>
                    </ul>-->
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
    	titleinfo["titles"] = "养殖场名称,联系人,联系人电话,经度,纬度,地址,省份,城市,区县,";
    	titleinfo["keys"] = "y_mingcheng,y_lianxiren,y_lianxidianhua,zuobiao_x,zuobiao_y,y_dizhi,y_sheng,y_shi,y_quxian,";
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
        caption:"养殖场管理",
        head:"养殖场列表",
        method:"<%=path%>/ifs/zhyy/yangzhichang/list",
        buttonList:[
            {name:"新增", className:"btn_1", style:"",event:"click", eventFunc:addZhyyYangzhichang,customArg:{}},
            {name:"更新缓存", className:"btn_2", event:"click", eventFunc:exportFun},
//            {name:"导入", className:"btn_3", event:"click", eventFunc:importFun},
//            {name:"打印", className:"btn_4", event:"click", eventFunc:printFun},
            {name:"刷新", className:"btn_5", event:"click", eventFunc:searchlist}
        ],
        colModel:[
            {name:'checkbox', value:iconImg.checkBoxIcon, width:"10%",align:"center" ,event:"click", eventFunc:selclick},
            {name:'view', value:'查看', width:"10%",className:"",align:"center"},
            {name:'edit', value:'编辑', width:"10%",className:"",align:"center"},
            {name:'YMingcheng', value:'养殖场名称', width:"10%",align:"center"},	
            {name:'YCode', value:'养殖场编号', width:"10%",align:"center"},	
            {name:'YLianxiren', value:'联系人', width:"10%",align:"center"},	
            {name:'YLianxidianhua', value:'联系人电话', width:"10%",align:"center"},	
            {name:'zuobiaoX', value:'经度', width:"10%",align:"center"},	
            {name:'zuobiaoY', value:'纬度', width:"10%",align:"center"},	
            {name:'YDizhi', value:'地址', width:"10%",align:"center"}
//            {name:'YSheng', value:'省份', width:"10%",align:"center"},	
//            {name:'YShi', value:'城市', width:"10%",align:"center"},	
//            {name:'YQuxian', value:'区县', width:"10%",align:"center"},
        ]
    }
    function initARG(){
            initOption.arg={
//                    y_mingcheng:$("#y_mingcheng").val(),
//                    y_lianxiren:$("#y_lianxiren").val(),
//                    y_lianxidianhua:$("#y_lianxidianhua").val(),
//                    zuobiao_x:$("#zuobiao_x").val(),
//                    zuobiao_y:$("#zuobiao_y").val(),
//                    y_dizhi:$("#y_dizhi").val(),
//                    y_sheng:$("#y_sheng").val(),
//                    y_shi:$("#y_shi").val(),
//                    y_quxian:$("#y_quxian").val(),
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
                    YMingcheng:{value:list[i].y_mingcheng, align:"center"},
                    YCode:{value:list[i].mainid, align:"center"},
                    YLianxiren:{value:list[i].y_lianxiren, align:"center"},
                    YLianxidianhua:{value:list[i].y_lianxidianhua, align:"center"},
                    zuobiaoX:{value:list[i].zuobiao_x, align:"center"},
                    zuobiaoY:{value:list[i].zuobiao_y, align:"center"},
                    YDizhi:{value:list[i].y_dizhi, align:"center"},
//                    YSheng:{value:list[i].y_sheng, align:"center"},
//                    YShi:{value:list[i].y_shi, align:"center"},
//                    YQuxian:{value:list[i].y_quxian, align:"center"},
                    edit:{value:iconImg.editIcon, event:"click", eventFunc:editZhyyYangzhichang, customArg:{}, align:"center"},
                    view:{value:iconImg.infoIcon, event:"click", eventFunc:viewZhyyYangzhichang, customArg:{}, align:"center"}

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
    function addZhyyYangzhichang() {
         formDialog = new $.dialog({
                id:'formDialogFrame',
                title:"新增养殖场",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/zhyy/yangzhichang/add',
                autoPos:true
	});
    }



    //编辑方法
    function editZhyyYangzhichang(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        formDialog = new $.dialog({
                    id: 'formDialogFrame',
                    title: "编辑养殖场",
                    height: 500,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/yangzhichang/edit?id=' + id,
                    autoPos:true
                });

    }
    var initurl = "<%=path%>/ifs/zhyy/yangzhichang/initCache"; 
    function initZhyyYangzhichang() {
	
        if (confirm("确认更新缓存？")) {
            $.ajax({
                url: initurl,
                data: {
                },
                type: 'POST',
                timeout: 10000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if (json == "1") {
                        alert("缓存更新成功");
                        searchlist();
                    } else {
                        alert("缓存更新失败");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
    }

    var viewDialog;
    function viewZhyyYangzhichang(e){
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value;
        viewDialog = new $.dialog({
                    id: 'formDialogFrame',
                    title: "在线监测",
                    height: 950,
                    width: 1900,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/yangzhichang/viewyzc?yzcid=' + id,
                    autoPos:true
                });
    }
</script>
</html>