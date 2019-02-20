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
                    <span class="icon12">养殖场</span>
                </h2>
                <form id="forms">
                    <table class="box_tab_1" >
                        <tr>
                            <td class="tab1_tit">养殖场名称：</td>
                            <td><input type="text" id="YMingcheng" maxlength="50"  /></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">养殖场编号：</td>
                            <td><input type="text" id="YCode" maxlength="4"  /></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">联系人：</td>
                            <td><input type="text" id="YLianxiren" maxlength="50"  /></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">联系人电话：</td>
                            <td><input type="text" id="YLianxidianhua" maxlength="50"  /></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">经度：</td>
                            <td><input type="text" id="zuobiaoX" maxlength="22"  /></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">纬度：</td>
                            <td><input type="text" id="zuobiaoY" maxlength="22"  /></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">地址：</td>
                            <td><input type="text" id="YDizhi" maxlength="200"  /></td>
                        </tr> 
                        <!--                <tr>
                                            <td class="tab1_tit">省份：</td>
                                            <td><input type="text" id="YSheng" maxlength="11"  /></td>
                                        </tr> 
                                        <tr>
                                            <td class="tab1_tit">城市：</td>
                                            <td><input type="text" id="YShi" maxlength="11"  /></td>
                                        </tr> 
                                        <tr>
                                            <td class="tab1_tit">区县：</td>
                                            <td><input type="text" id="YQuxian" maxlength="11"  /></td>
                                        </tr> -->
                        <tr>
                            <td class="tab1_btn" colspan="2">
                                <span class="mlr5" id="save"><b class="btn btn_Lg btn_yellow">保存</b></span>
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
            if (id != "") {
                $("#YCode").attr({"disabled":"disabled"});
                loaddata();
            }
        })
        function loaddata() {
            var url = "<%=path%>/ifs/zhyy/yangzhichang/get";
            $.ajax({
                url: url,
                data: {id: "<%=id%>"
                },
                type: 'POST',
                timeout: 3000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if (json != "") {
                        id = json.id;
                        $("#YMingcheng").val(json.y_mingcheng);
                        $("#YCode").val(json.id);
                        $("#YLianxiren").val(json.y_lianxiren);
                        $("#YLianxidianhua").val(json.y_lianxidianhua);
                        $("#zuobiaoX").val(json.zuobiao_x);
                        $("#zuobiaoY").val(json.zuobiao_y);
                        $("#YDizhi").val(json.y_dizhi);
                        $("#YSheng").val(json.y_sheng);
                        $("#YShi").val(json.y_shi);
                        $("#YQuxian").val(json.y_quxian);
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
            var url = "<%=path%>/ifs/zhyy/yangzhichang/save";
            var obj = {};
            if (id != "") {
                obj.id = id;
            }else{
               obj.id = $("#YCode").val();
            }
            obj.y_mingcheng = $("#YMingcheng").val();
            obj.y_lianxiren = $("#YLianxiren").val();
            obj.y_lianxidianhua = $("#YLianxidianhua").val();
            obj.zuobiao_x = $("#zuobiaoX").val();
            obj.zuobiao_y = $("#zuobiaoY").val();
            obj.y_dizhi = $("#YDizhi").val();
            obj.y_sheng = $("#YSheng").val();
            obj.y_shi = $("#YShi").val();
            obj.y_quxian = $("#YQuxian").val();
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
                    } else if (data == "0") {
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