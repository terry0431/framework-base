<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String id = "";
    if (request.getAttribute("id") != null) {
        id = (String) request.getAttribute("id");
    } else {
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
                    <span class="icon12">水质数据</span>
                </h2>
                <table class="box_tab_1" >
                    <tr>
                        <td class="tab1_tit">日期：</td>
                        <td><input type="text" id="SDate" maxlength="10" readonly="readonly" value="<%=nowdate%>" /></td>
                    </tr> 
                    <tr>
                        <td class="tab1_tit"><b class="red mlr5">*</b>：</td>
                        <td>
                            <select style="width: 140px" id="zhyyYangzhichangId">
                                <option selected="selected" value="1">固德养殖有限公司</option>
                            </select>
                        </td>
                    </tr> 
                </table>
                <h2 class="mb10">
                    <span class="icon12">外海水纹</span>
                </h2>
                <table class="box_tab_1" >
                    <tr>
                        <td class="tab1_tit">水温（℃）：</td>
                        <td><input type="text" id="SWhShuiwen" maxlength="12" itype="float"  /></td>
                    </tr> 
                    <tr>
                        <td class="tab1_tit">盐度：</td>
                        <td><input type="text" id="SWhYandu" maxlength="12" itype="float"  /></td>
                    </tr> 
                </table>
                <h2 class="mb10">
                    <span class="icon12">池塘水纹</span>
                </h2>
                <table class="box_tab_1" >

                    <tr>
                        <td class="tab1_tit">水温（℃）：</td>
                        <td><input type="text" id="SCtShuiwen" maxlength="12" itype="float"  /></td>
                    </tr> 
                    <tr>
                        <td class="tab1_tit">盐度：</td>
                        <td><input type="text" id="SCtYandu" maxlength="12" itype="float"  /></td>
                    </tr> 
                    <tr>
                        <td class="tab1_tit">PH：</td>
                        <td><input type="text" id="SCtPh" maxlength="12" itype="float" /></td>
                    </tr> 
                    <tr>
                        <td class="tab1_tit">DO2（mg/L）：</td>
                        <td><input type="text" id="SCtD02" maxlength="12" itype="float" /></td>
                    </tr> 
                    <tr>
                        <td class="tab1_tit">浊度：</td>
                        <td><input type="text" id="SCtZhuodu" maxlength="12" itype="float" /></td>
                    </tr> 
                    <tr>
                        <td class="tab1_tit">水面情况：</td>
                        <td>
                            <select style="width: 140px" id="SCtShuimian">
                                <option value="-1">请选择</option>
                                <option value="1">水面无冰</option>
                                <option value="2">结薄冰</option>
                                <option value="3">薄冰融化</option>
                                <option value="4">冰已结大半</option>
                                <option value="5">水面已封冻</option>
                            </select>
                        </td>
                    </tr> 
                    <tr>
                        <td class="tab1_tit">水深（cm）：</td>
                        <td><input type="text" id="SCtShuishen" maxlength="12" itype="float" /></td>
                    </tr> 

                    <tr>
                        <td class="tab1_btn" colspan="2">
                            <span class="mlr5" id="save"><b class="btn btn_Lg btn_yellow">保存</b></span>
                            <span class="mlr5" id="exit"><b class="btn btn_Lg btn_yellow">关闭</b></span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
    <script type="text/javascript">
    var id = "<%=id%>";
    $(function () {
        JCTools.intInit($("#QQiwen"));
        if(id === ""){
                laydate.render({
                    elem: '#SDate' //指定元素
                    ,max: 0
                    ,done: function(value, date){
                        loaddata();
                    }
                });
            }
            loaddata();
    })
    function loaddata() {
        var url = "<%=path%>/ifs/zhyy/shuizhi/get";
        $.ajax({
            url: url,
            data: {id: "<%=id%>",s_date:$("#SDate").val()
            },
            type: 'POST',
            timeout: 3000,
            dataFilter: function (json) {
                console.log("jsonp.filter:" + json);
                return json;
            },
            success: function (json, textStatus) {
                if(json != ""){
                    id=json.id;
                    $("#SWhShuiwen").val(json.s_wh_shuiwen);
                    $("#SWhYandu").val(json.s_wh_yandu);
                    $("#SCtShuiwen").val(json.s_ct_shuiwen);
                    $("#SCtYandu").val(json.s_ct_yandu);
                    $("#SCtPh").val(json.s_ct_ph);
                    $("#SCtD02").val(json.s_ct_d02);
                    $("#SCtZhuodu").val(json.s_ct_zhuodu);
                    $("#SCtShuimian").val(json.s_ct_shuimian);
                    $("#SCtShuishen").val(json.s_ct_shuishen);
                }else{
                    id = "";
                    $("#SWhShuiwen").val("");
                    $("#SWhYandu").val("");
                    $("#SCtShuiwen").val("");
                    $("#SCtYandu").val("");
                    $("#SCtPh").val("");
                    $("#SCtD02").val("");
                    $("#SCtZhuodu").val("");
                    $("#SCtShuimian").val(-1);
                    $("#SCtShuishen").val("");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jsonp.error:" + XMLHttpRequest.returnCode);
            }
        });
    }
    $("#save").click(function () {
        if (!JC.validate(rule)) {
            return;
        }
        var url = "<%=path%>/ifs/zhyy/shuizhi/save";
            var obj = {};
            if(id != ""){
                obj.id = id;
            }
        obj.s_date = $("#SDate").val();
        obj.s_wh_shuiwen = $("#SWhShuiwen").val();
        obj.s_wh_yandu = $("#SWhYandu").val();
        obj.s_ct_shuiwen = $("#SCtShuiwen").val();
        obj.s_ct_yandu = $("#SCtYandu").val();
        obj.s_ct_ph = $("#SCtPh").val();
        obj.s_ct_d02 = $("#SCtD02").val();
        obj.s_ct_zhuodu = $("#SCtZhuodu").val();
        obj.s_ct_shuimian = $("#SCtShuimian").val();
        obj.s_ct_shuishen = $("#SCtShuishen").val();
        obj.zhyy_yangzhichang_id = $("#zhyyYangzhichangId").val();
        $.ajax({
            url: url,
            data: {arg: JSON.stringify(obj)
            },
            type: 'POST',
            timeout: 3000,
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


    var rule = {
        divInfo: {
            id: "msg-error "
        },
        objInfo: {
        }
    }
    </script>
</html>