<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
//List<Map<String,Object>> zhyy_shebeiList = (List)request.getAttribute("zhyy_shebeiList");
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
                    <li class="parent"><a>气象数据</a></li>
                    <li class="current"><a>气象数据</a></li>
                </ul>

                <div class="titlst">
                    <div class="box_c">
                        <!--                    <h3 class="box_c_tit">查询</h3>
                                            <ul class="search">
                                                <li>
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
                                                    <span class="mlr5 left">风力：</span>
                                                    <div class="left mlr5">
                                                        <input class="input_1 Grayline" id="q_fengli" type="text"/>
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
                                                        <input class="input_1 Grayline" id="q_systime" type="text"/>
                                                    </div>
                                                </li>
                                                <li>
                                                    <span class="mlr5 left">：</span>
                                                    <div class="left mlr5">
                                                        <input class="input_1 Grayline" id="q_hour" type="text"/>
                                                    </div>
                                                </li>
                                                <li>
                                                    <span class="mlr5 left">湿度：</span>
                                                    <div class="left mlr5">
                                                        <input class="input_1 Grayline" id="q_shidu" type="text"/>
                                                    </div>
                                                </li>
                                                <li>
                                                    <span class="mlr5 left">：</span>
                                                    <div class="left mlr5">
                                                        <input class="input_1 Grayline" id="q_year" type="text"/>
                                                    </div>
                                                </li>
                                                <li>
                                                    <span class="mlr5 left">：</span>
                                                    <div class="left mlr5">
                                                        <input class="input_1 Grayline" id="q_month" type="text"/>
                                                    </div>
                                                </li>
                                                <li>
                                                    <span class="mlr5 left">：</span>
                                                    <div class="left mlr5">
                                                        <input class="input_1 Grayline" id="q_day" type="text"/>
                                                    </div>
                                                </li>
                                                <li>
                                                    <span class="mlr5 left">大气压：</span>
                                                    <div class="left mlr5">
                                                        <input class="input_1 Grayline" id="q_qiya" type="text"/>
                                                    </div>
                                                </li>
                                                <li>
                                                    <span class="mlr5 left">雨量：</span>
                                                    <div class="left mlr5">
                                                        <input class="input_1 Grayline" id="q_yuliang" type="text"/>
                                                    </div>
                                                </li>
                                                <li>
                                                    <span class="mlr5 left">设备id：</span>
                                                    <div class="left mlr5">
                                                        <select id="zhyy_shebei_id">
                                                        
                                                        </select>
                                                    </div>
                                                </li>
                                                <li><span class="btn2" id="search">查询</span></li>
                                            </ul>-->
                        <div class="box_c" id="tableDiv"></div>
                    </div>
                </div>
            </div>
        </div>
        <canvas id="arrow1" class="drawArrow" width="100" height="100"></canvas>
        <canvas id="arrow2" class="drawArrow" width="100" height="100"></canvas>
</body>
<script type="text/javascript">
    function drawArrow(ctx, fromX, fromY, toX, toY, theta, headlen, width, color) { 
        theta = typeof (theta) != 'undefined' ? theta : 30; 
        headlen = typeof (theta) != 'undefined' ? headlen : 10; 
        width = typeof (width) != 'undefined' ? width : 1; 
        color = typeof (color) != 'color' ? color : '#000'; // 计算各角度和对应的P2,P3坐标 
        var angle = Math.atan2(fromY - toY, fromX - toX) * 180 / Math.PI, 
                angle1 = (angle + theta) * Math.PI / 180, 
                angle2 = (angle - theta) * Math.PI / 180, 
                topX = headlen * Math.cos(angle1), 
                topY = headlen * Math.sin(angle1), 
                botX = headlen * Math.cos(angle2), 
                botY = headlen * Math.sin(angle2); 
        ctx.save(); 
        ctx.beginPath(); 
        var arrowX = fromX - topX, arrowY = fromY - topY; 
        ctx.moveTo(arrowX, arrowY); 
        ctx.moveTo(fromX, fromY); 
        ctx.lineTo(toX, toY); 
        arrowX = toX + topX; 
        arrowY = toY + topY; 
        ctx.moveTo(arrowX, arrowY); 
        ctx.lineTo(toX, toY); 
        arrowX = toX + botX; 
        arrowY = toY + botY; 
        ctx.lineTo(arrowX, arrowY); 
        ctx.strokeStyle = color; 
        ctx.lineWidth = width; 
        ctx.stroke(); 
        ctx.restore(); 
    }
    
    var datalist;
    function exportFun(){
        var titleinfo = new Object();
        titleinfo["titles"] = "气温,风向,风力,天气,日期,,湿度,,,,大气压,雨量,设备id,";
        titleinfo["keys"] = "q_qiwen,q_fengxiang,q_fengli,q_tianqi,q_systime,q_hour,q_shidu,q_year,q_month,q_day,q_qiya,q_yuliang,zhyy_shebei_id,";
        exportExcelFroData(titleinfo, datalist);
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
            method:"<%=path%>/ifs/zhyy/qixiangshuju/list",
            buttonList:[
//            {name:"导出", className:"btn_2", event:"click", eventFunc:exportFun},
//            {name:"导入", className:"btn_3", event:"click", eventFunc:importFun},
//            {name:"打印", className:"btn_4", event:"click", eventFunc:printFun},
            {name:"刷新", className:"btn_5", event:"click", eventFunc:searchlist}
            ],
            colModel:[
            {name:'QSystime', value:'日期', width:"30%", align:"center"},
            {name:'QQiwen', value:'气温(摄氏度)', width:"10%", align:"center"},
            {name:'QFengxiang', value:'风向', width:"10%", align:"center"},
            {name:'QFengli', value:'风力(米/每秒)', width:"10%", align:"center"},
//            {name:'QTianqi', value:'天气', width:"10%",align:"center"},	
            {name:'QShidu', value:'湿度', width:"10%", align:"center"},
            {name:'QQiya', value:'大气压', width:"10%", align:"center"},
            {name:'QYuliang', value:'雨量(毫米)', width:"10%", align:"center"},
            ]
    }
    function initARG(){
    initOption.arg = {
//                    q_qiwen:$("#q_qiwen").val(),
//                    q_fengxiang:$("#q_fengxiang").val(),
//                    q_fengli:$("#q_fengli").val(),
//                    q_tianqi:$("#q_tianqi").val(),
//                    q_systime:$("#q_systime").val(),
//                    q_hour:$("#q_hour").val(),
//                    q_shidu:$("#q_shidu").val(),
//                    q_year:$("#q_year").val(),
//                    q_month:$("#q_month").val(),
//                    q_day:$("#q_day").val(),
//                    q_qiya:$("#q_qiya").val(),
//                    q_yuliang:$("#q_yuliang").val(),
//                            zhyy_shebei_id:$("#zhyy_shebei_id").val(),
    }
    }
    function dataHandle(list, opt) {
    datalist = list;
    var rows = [];
    var fx;
    var fxstr;
    for (var i = 0, j = list.length; i < j; i++) {
//        fx = parseFloat(list[i].q_fengxiang);
//        if( fx > 360 - 11.25 && fx <= 360 ||  fx <= 11.25){
//            fxstr = "北";
//        }else if(fx > 0 + 11.25 && fx <= 22.5 + 11.25){
//            fxstr = "北东北";
//        }else if(fx > 22.5 + 11.25 && fx <= 45 + 11.25){
//            fxstr = "东北";
//        }else if(fx > 45 + 11.25 && fx <= 67.5 + 11.25){
//            fxstr = "东东北";
//        }else if(fx > 67.5 + 11.25 && fx <= 90 + 11.25){
//            fxstr = "东";
//        }else if(fx > 90 + 11.25 && fx <= 112.5 + 11.25){
//            fxstr = "东东南";
//        }else if(fx > 112.5 + 11.25 && fx <= 135 + 11.25){
//            fxstr = "东南";
//        }else if(fx > 135 + 11.25 && fx <= 157.5 + 11.25){
//            fxstr = "南东南";
//        }else if(fx > 157.5 + 11.25 && fx <= 180 + 11.25){
//            fxstr = "南";
//        }else if(fx > 180 + 11.25 && fx <= 202.5){
//            fxstr = "南西南";
//        }else if(fx > 202.5 + 11.25 && fx <= 225 + 11.25){
//            fxstr = "西南";
//        }else if(fx > 225 + 11.25 && fx <= 247.5 + 11.25){
//            fxstr = "西西南";
//        }else if(fx > 247.5 + 11.25 && fx <= 270 + 11.25){
//            fxstr = "西";
//        }else if(fx > 270 + 11.25 && fx <= 292.5 + 11.25){
//            fxstr = "西西北";
//        }else if(fx > 292.5 + 11.25 && fx <= 315 + 11.25){
//            fxstr = "西北";
//        }else if(fx > 315 + 11.25 && fx <= 337.5 + 11.25){
//            fxstr = "北西北";
//        }
        //alert(fxstr);
        rows.push({
            cell:{
                id:{value:list[i].mainid},
                QQiwen:{value:list[i].q_qiwen, align:"center"},
                //QFengxiang:{value:"<line x1=\"10\" y1=\"10\" x2=\"100\" y2=\"50\"  stroke=\"red\" stroke-width=\"2\"  marker-start=\"url(#markerArrow)\" marker-mid=\"url(#markerArrow)\" marker-end=\"url(#markerArrow)\" /> ", align:"center"},
                QFengli:{value:list[i].q_fengli, align:"center"},
                QFengxiang:{value:list[i].q_fengxiang_code + "(" + list[i].q_fengxiang + "度)", align:"center"},
                QSystime:{value:list[i].q_systime, align:"center"},
                QShidu:{value:list[i].q_shidu + "%", align:"center"},
                QQiya:{value:list[i].q_qiya, align:"center"},
                QYuliang:{value:list[i].q_yuliang, align:"center"}
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
//        var c = document.getElementById("arrow1");
//        var cxt = c.getContext("2d");
//        drawArrow(cxt, 150, 100, 400,100,30,30,10,'#f36');
//      
//        var fx = 180;
//        var line = 40; 
//        var p1 = new Array(50,50);
//        var p2 = new Array();
//        if(fx <= 90){
//            p2[0] = 50 + 40 * (fx/90);
//            p2[1] = 10 + 40 * (fx/90);
//        } else if(fx <= 180){
//            p2[0] = 50 + 40 * ((180-fx) /90);
//            p2[1] = 50 + 40 * ((fx - 90)/90 );
//        }  else if(fx <= 270){
//            p2[0] = 50 - 40 * ((270-fx) /90);
//            p2[1] = 50 + 40 * ((fx - 90)/90 );
//        }  else if(fx <= 360){
//            p2[0] = 50 + 40 * ((180-fx) /90);
//            p2[1] = 50 + 40 * ((fx - 90)/90 );
//        } 
//        
//        var c = document.getElementById("arrow2");
//        var cxt = c.getContext("2d");
//        drawArrow(cxt, 50, 50, p2[0],p2[1],10,10,2,'#f36');
        

    //    ctx = document.getElementById("arrow2");
    //    drawArrow(ctx, 150, 100, 400,100,30,30,10,'#f36');
    });
    var formDialog;
    function addZhyyQixiangshuju() {
    formDialog = new $.dialog({
    id:'formDialogFrame',
            title:"新增**",
            height:500,
            width:750,
            cover:true,
            content: 'url:<%=path%>/con/zhyy/qixiangshuju/add',
            autoPos:true
    });
    }



    //编辑方法
    function editZhyyQixiangshuju(e) {
    var cellObj = e.data.arg;
    var id = cellObj.cell.id.value
            formDialog = new $.dialog({
            id: 'formDialogFrame',
                    title: "编辑**",
                    height: 500,
                    width: 750,
                    cover:true,
                    content: 'url:<%=path%>/con/zhyy/qixiangshuju/edit?id=' + id,
                    autoPos:true
            });
    }
    var delurl = "<%=path%>/ifs/zhyy/qixiangshuju/delete";
    function deleteZhyyQixiangshuju(e) {
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
    if (ids != ""){
    ids += "," + $(this).val();
    } else{
    ids = $(this).val();
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