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
            <li class="parent"><a>寻塘日记管理</a></li>
            <li class="current"><a>寻塘日记管理</a></li>
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
                            <span class="mlr5 left">采集日期：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" id="x_cjriqi" type="text" readonly="readonly"/>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">设备状态：</span>
                            <div class="left mlr5">
                                <select id="x_shebei_flag" >
                                    <option value="-1">全部</option>
                                    <option value="1">异常</option>
                                    <option value="0">正常</option>
                                </select>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">灾害状况：</span>
                            <div class="left mlr5">
                                <select id="x_zaihai_flag" >
                                    <option value="-1">全部</option>
                                    <option value="1">有</option>
                                    <option value="0">无</option>
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
    	titleinfo["titles"] = "采集日期,设备状态,养殖对象状态,灾害状况,总体状况,录入人员";
    	titleinfo["keys"] = "x_cjriqi,x_shebei_flag,x_yangzhiduixiang_flag,x_zaihai_flag,x_flag,sys_userinfo_id";
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
        caption:"寻塘日记管理",
        head:"寻塘日记列表",
        method:"<%=path%>/ifs/zhyy/xuntang/list",
        buttonList:[
            {name:"新增", className:"btn_1", style:"",event:"click", eventFunc:addZhyyXuntang,customArg:{}},
            {name:"导出", className:"btn_2", event:"click", eventFunc:exportFun},
            {name:"刷新", className:"btn_5", event:"click", eventFunc:searchlist}
        ],
        colModel:[	
            {name:'edit', value:'编辑', width:"10%",className:"",align:"center"},	
            {name:'XCjriqi', value:'采集日期', width:"10%",align:"center"},	
            {name:'XShebeiFlag', value:'设备状态', width:"10%",align:"center"},	
            {name:'XZaihaiFlag', value:'灾害状况', width:"10%",align:"center"}
        ]
    }
    function initARG(){
            initOption.arg={
                    x_cjriqi:$("#x_cjriqi").val(),
                    x_shebei_flag:$("#x_shebei_flag").val(),
                    x_zaihai_flag:$("#x_zaihai_flag").val(),
                    x_flag:$("#x_flag").val(),
                    zhyy_yangzhichang_id:$("#yzc").val()
                    }
    }
    function dataHandle(list, opt) {
        datalist = list;
        var rows = [];
        var sbflag = "";
        var zhflag = "";
        for (var i = 0, j = list.length; i < j; i++) {
            if(list[i].x_shebei_flag == 0){
                sbflag = "无异常";
            }else{
                sbflag = "有异常";
            }
            if(list[i].x_zaihai_flag == 0){
                zhflag = "无";
            }else{
                zhflag = "有";
            }
            rows.push({
                cell:{
                    id:{value:list[i].mainid},
                    checkbox:{value:"<input type=\"checkbox\" name=\"checks\" value=\""+list[i].mainid+"\" />", align:"center"},
                    XCjriqi:{value:list[i].x_cjriqi, align:"center"},
                    XShebeiFlag:{value:sbflag, align:"center"},
                    XYangzhiduixiangFlag:{value:list[i].x_yangzhiduixiang_flag, align:"center"},
                    XZaihaiFlag:{value:zhflag, align:"center"},
                    edit:{value:iconImg.editIcon, event:"click", eventFunc:editZhyyXuntang, customArg:{}, align:"center"}
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
        laydate.render({
                elem: '#x_cjriqi' //指定元素
            });
        
    })
    function init_yzcsel(json) {
            var optstr = "";
            for (var i = 0; i < json.length; i++) {
                optstr += "<option value=\"" + json[i].id + "\">" + json[i].y_mingcheng + "</option>";
            }
            $("#yzc").append(optstr);
            $("#search").click(searchlist);
            searchlist();
        }

    var addDialog;
    function addZhyyXuntang() {
        addDialog = new $.dialog({
                    id: 'addDialogFrame',
                    title: "添加寻塘日记",
                    height: 600,
                    width: 550,
                    content: 'url:<%=path%>/con/zhyy/xuntang/add'
                });
    }


    //编辑方法
    function editZhyyXuntang(e) {
        var cellObj = e.data.arg;
        var cjriqi = cellObj.cell.XCjriqi.value;
        //alert(cellObj.cell.XCjriqi.value);
        
        addDialog = new $.dialog({
                    id: 'addDialogFrame',
                    title: "添加寻塘日记",
                    height: 600,
                    width: 550,
                    content: 'url:<%=path%>/con/zhyy/xuntang/add?cjriqi='+cjriqi
                });
    }
    
</script>
</html>