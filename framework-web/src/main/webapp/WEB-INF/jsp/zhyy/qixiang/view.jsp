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
                    <span class="icon12">气象数据</span>
                </h2>
                <form id="forms">
                    <table class="box_tab_1" >
                        <tr>
                            <td class="tab1_tit">日期：</td>
                            <td><input type="text" id="QRiqi" maxlength="10" readonly="readonly" value="" /></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">气温：</td>
                            <td><input type="text" id="QQiwen" maxlength="12" itype="int" max="50" min="-40" readonly="readonly" />度</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">风向：</td>
                            <td>
                                <select disabled="disabled" style="width: 140px" id="QFengxiang">
                                    <option value="-1"></option>
                                    <option value="1">东风</option>
                                    <option value="2">东北风</option>
                                    <option value="3">东南风</option>
                                    <option value="4">西风</option>
                                    <option value="5">西北风</option>
                                    <option value="6">西南风</option>
                                    <option value="7">北风</option>
                                    <option value="8">南风</option>
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">风力：</td>
                            <td>
                                <select disabled="disabled" style="width: 140px" id="QFengli">
                                    <option value="-1"></option>
                                    <option value="0">无风</option>
                                    <option value="1">1级风</option>
                                    <option value="2">2级风</option>
                                    <option value="3">3级风</option>
                                    <option value="4">4级风</option>
                                    <option value="5">5级风</option>
                                    <option value="6">6级风</option>
                                    <option value="7">7级风</option>
                                    <option value="8">8级风</option>
                                    <option value="9">9级风</option>
                                    <option value="10">10级风</option>
                                    <option value="11">11级风</option>
                                    <option value="12">12级风</option>
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">天气：</td>
                            <td>
                                <select disabled="disabled" style="width: 140px" id="QTianqi">
                                    <option value="-1"></option>
                                    <option value="1">晴</option>
                                    <option value="2">多云</option>
                                    <option value="3">阴天</option>
                                    <option value="4">雾</option>
                                    <option value="5">小雨</option>
                                    <option value="6">中雨</option>
                                    <option value="7">大雨</option>
                                    <option value="8">暴雨</option>
                                    <option value="16">阵雨</option>
                                    <option value="9">雷阵雨</option>
                                    <option value="10">冰雹</option>
                                    <option value="17">雨夹雪</option>
                                    <option value="11">冻雨</option>
                                    <option value="12">小雪</option>
                                    <option value="13">中雪</option>
                                    <option value="14">大雪</option>
                                    <option value="15">冻霜</option>
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><b class="red mlr5">*</b>气象站位置</td>
                            <td>
                                <select disabled="disabled" style="width: 140px" id="zhyyShebeiId">
                                    <option selected="selected" value="1">固德养殖有限公司</option>
                                </select>
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
            loaddata();
        })

        function loaddata(){
            var url = "<%=path%>/ifs/zhyy/qixiang/get";
            $.ajax({
                url: url,
                data: {id: "<%=id%>"
                },
                type: 'POST',
                timeout: fw_config.ajax_time,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if(json != ""){
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
        

        $("#exit").click(function () {
            top.win.addZhyyQixiangDialog.cancel();
        })

    </script>
</html>