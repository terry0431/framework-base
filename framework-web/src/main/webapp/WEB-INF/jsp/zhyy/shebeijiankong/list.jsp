<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Map<String, Object>> t_equipment_pondList = (List) request.getAttribute("t_equipment_pondList");
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
                    <li class="parent"><a>设备历史</a></li>
                    <li class="current"><a>设备历史</a></li>
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
                                        <option value="2">营城子</option>
                                    </select>
                                </div>
                            </li>
<!--                            <li>
                                <span class="mlr5 left">养殖圈：</span>
                                <div class="left mlr5">
                                    <select><option>1号圈</option></select>
                                </div>
                            </li>-->
                            <li>
                                <span class="mlr5 left">设备情况：</span>
                                <div class="left mlr5">
                                    <select id="s_flag"><option value="-1">全部</option><option value="0">正常</option><option value="2">无信号</option><option value="1">故障</option></select>
                                </div>
                            </li>

                            <li>
                                <span class="mlr5 left">设备：</span>
                                <div class="left mlr5">
                                    <select id="t_equipment_pond_id">
                                        <option>养水机</option>
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
        var datalist;
        function exportFun() {
            var titleinfo = new Object();
            titleinfo["titles"] = "通道,设备工况,电压,电流,数据采集日期时间,设备id,";
            titleinfo["keys"] = "channel,status,voltage,electric_current,record_time,t_equipment_pond_id";
            exportExcelFroData(titleinfo, datalist);
        }
        function importFun() {
        }
        function printFun() {
        }
        function rowClickFunc(e) {
            var rowObj = e.data.arg;
        }
        function selclick() {
            $("input[name=checks]:checkbox").each(function () {
                $(this).parent().parent().click();
            })
        }

        var initOption = {
            container: '#tableDiv',
            caption: "设备历史管理",
            head: "设备历史",
            method: "<%=path%>/ifs/zhyy/shebeijiankong/list",
            buttonList: [
                {name: "导出", className: "btn_2", event: "click", eventFunc: exportFun},
                {name: "打印", className: "btn_4", event: "click", eventFunc: printFun},
                {name: "刷新", className: "btn_5", event: "click", eventFunc: searchlist}
            ],
            colModel: [
                {name: 's_flag', value: '运行状态', width: "10%", align: "center"},
                {name: 'zhyy_yangzhiqu_id', value: '养殖圈', width: "10%", align: "center"},
                {name: 'systime', value: '采集时间', width: "10%", align: "center"},
                {name: 's_dianliu', value: '电流', width: "10%", align: "center"}
            ]
        }
        function initARG() {
            initOption.arg={
                    s_flag: $("#s_flag").val(),
                    zhyy_yangzhichang_id:$("#zhyy_yangzhichang_id").val()
                    }
            
        }
        function dataHandle(list, opt) {
            datalist = list;
            var rows = [];
            var iconsrc = "";
            for (var i = 0, j = list.length; i < j; i++) {
                if (list[i].s_flag == "1") {
                    iconsrc = iconImg.deleteIcon;
                } else if (list[i].s_flag == "0") {
                    iconsrc = iconImg.checkBoxIcon;
                } else {
                    iconsrc = iconImg.whIcon;
                }
                rows.push({
                    cell: {
                        id: {value: list[i].mainid},
                        s_flag: {value: iconsrc, align: "center"},
                        zhyy_yangzhiqu_id: {value: list[i].zhyy_yangzhiqu_id, align: "center"},
                        systime: {value: list[i].systime, align: "center"},
                        s_dianliu: {value: list[i].s_dianliu, align: "center"}
                    }
                });
            }
            return rows;
        }
        function searchlist() {
            initARG();
            JC.table(initOption);
        }
        $(function () {
            $("#search").click(searchlist);
            searchlist();
        })

    </script>
</html>