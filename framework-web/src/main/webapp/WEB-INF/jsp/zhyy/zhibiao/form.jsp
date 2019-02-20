<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String id = "";
    if(request.getAttribute("id") != null){
        id = (String)request.getAttribute("id");
    }else{
        id = "";
    }
    String nowdate = "";
    if(request.getAttribute("nowdata") != null){
        nowdate = (String)request.getAttribute("nowdata");
    }
%>
<html>
    <head>
        <title></title>
        <%@ include file="../../include/commontop.jsp"%>
    </head>
    <body>
        <div class="box_Bomb">
            <span class="right">带<b class="red mlr5">*</b>号为必填项</span>
            <div class="box_b_t">
                <h2 class="mb10">
                    <span class="icon12">指标维护</span>
                </h2>
                <form id="forms">
                    <table class="box_tab_1" >
                        
                        <tr>
                            <td class="tab1_tit">指标名称：</td>
                            <td><input type="text" id="QQiwen" maxlength="12" itype="int" max="50" min="-40"  /></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">频道类型：</td>
                            <td>
                                <select style="width: 140px" id="QFengxiang">
                                    <option value="-1">请选择</option>
                                    <option value="1">AI</option>
                                    <option value="2">DI</option>
                                    <option value="2">DO</option>
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">频道：</td>
                            <td>
                                <select style="width: 140px" id="QFengli">
                                    <option value="-1">请选择</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="6">6</option>
            
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">是否报警：</td>
                            <td>
                                <select style="width: 140px" id="QTianqi">
                                    <option value="-1">请选择</option>
                                    <option value="1">是</option>
                                    <option value="2">否</option>
                                    
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">报警等级：</td>
                            <td>
                                <select style="width: 140px" id="QTianqi">
                                    <option value="-1">请选择</option>
                                    <option value="1">一般</option>
                                    <option value="2">严重</option>
                                    <option value="2">紧急</option>
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">报警等级：</td>
                            <td>
                                <select style="width: 140px" id="QTianqi">
                                    <option value="-1">请选择</option>
                                    <option value="1">一般</option>
                                    <option value="2">严重</option>
                                    <option value="2">紧急</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="tab1_tit">阈值：</td>
                            <td>
                                <select style="width: 140px" id="QTianqi">
                                    <option value="-1">请选择</option>
                                    <option value="1">></option>
                                    <option value="2">>=</option>
                                    <option value="2">=</option>
                                    <option value="2"><=</option>
                                    <option value="2"><</option>
                                </select>
                                <input type="text" id="QQiwen" maxlength="12" itype="int" max="50" min="-40"  /><input type="button" value="增加条件">
                            </td>
                        </tr>
                        <tr>
                            <td class="tab1_btn" colspan="2">
                                <span class="mlr5" id="save"><b class="btn btn_Lg btn_yellow">保存</b></span>
                                <span class="mlr5" id="clear"><b class="btn btn_Lg btn_yellow">重置</b></span>
                                <span class="mlr5" id="exit"><b class="btn btn_Lg btn_yellow">关闭</b></span>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        var id = "<%=id%>";
        $(function () {
            JCTools.intInit($("#QQiwen"));
            if(id === ""){
                laydate.render({
                    elem: '#QRiqi' //指定元素
                    ,max: 0
                    ,done: function(value, date){
                        loaddata();
                    }
                });
            }
            loaddata();
        })
        function clearform(){
            $("#QQiwen").val("");
            $("#QFengxiang").val("-1");
            $("#QFengli").val("-1");
            $("#QTianqi").val("-1");
            $("#zhyyShebeiId").val("1");
        }
        function loaddata(){
            clearform();
            var url = "<%=path%>/ifs/zhyy/qixiang/get";
            $.ajax({
                url: url,
                data: {id: "<%=id%>",q_riqi:$("#QRiqi").val()
                },
                type: 'POST',
                timeout: fw_config.ajax_time,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if(json != ""){
                        id=json.id;
                        $("#QQiwen").val(json.q_qiwen);
                        $("#QFengxiang").val(json.q_fengxiang);
                        $("#QFengli").val(json.q_fengli);
                        $("#QTianqi").val(json.q_tianqi);
                        $("#QRiqi").val(json.q_riqi);
                        $("#zhyyShebeiId").val(json.zhyy_shebei_id);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
        $("#save").click(function () {
            var url = "<%=path%>/ifs/zhyy/qixiang/save";
            var obj = {};
            if(id != ""){
                obj.id = id;
            }
            obj.q_qiwen = $("#QQiwen").val();
            obj.q_fengxiang = $("#QFengxiang").val();
            obj.q_fengli = $("#QFengli").val();
            obj.q_tianqi = $("#QTianqi").val();
            obj.q_riqi = $("#QRiqi").val();
            obj.zhyy_shebei_id = $("#zhyyShebeiId").val();
            $.ajax({
                url: url,
                data: {arg: JSON.stringify(obj)
                },
                type: 'POST',
                timeout: fw_config.ajax_time,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if (json == "1") {
                        alert("保存成功");
                    } else if (json == "0") {
                        alert("保存失败");
                    }
                    top.win.JC.table(top.win.initOption);
                    top.win.formDialog.close();
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        })

        $("#exit").click(function () {
            top.win.formDialog.close();
        })



    </script>
</html>