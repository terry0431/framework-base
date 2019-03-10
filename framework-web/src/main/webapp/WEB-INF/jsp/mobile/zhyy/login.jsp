<%@ page import="java.util.Map" %><%--
  User: wangbo
  Date: 2019\2\26 0026
  Time: 9:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        Map menber = (Map) request.getAttribute("menber");
    %>
    <title>会员登录</title>
    <%@ include file="../include/common.jsp" %>
    <script src="<%=path%>/common/mobile/js/common.js"></script>
    <script src="http://static.runoob.com/assets/jquery-validation-1.14.0/lib/jquery.js"></script>
    <script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
    <script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript">
        var id = "";
        var getMenber_url = "<%=path%>/ifs/web/menber/getMenber";
        var apply_url = "<%=path%>/ifs/web/menber/apply";



        function apply() {
            //alert(123);
//                if (!JC.validate(rule)) {
//                    return;
//                }
            var obj = {};
            obj.id = <%=menber.get("id")%>;
            obj.z_mobile = $("#ZMobile").val();
            obj.z_name = $("#ZName").val();
            obj.z_yangzhichang = $("#ZYangzhichang").val();
            $.ajax({
                url: apply_url,
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
                        //alert("保存成功");
                        $("#div_main").html("<h1>您的账号正在审核中。。。</h1>");
                    } else  {
                        //alert("保存失败");
                        $("#div_main").html("<h1>"+json+"</h1>");
                    }
//                        top.win.JC.table(top.win.initOption);
//                        top.win.formDialog.close();
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
        $(function () {
            <%
                if(menber.get("z_state").toString().equals("0") ){
            %>
            $("#div_main").html("<h1>您的账号正在审核中。。。</h1>");
            $("#btn_submit").attr("disabled","disabled");
            <%
                }else{
            %>
            $("#form1").validate({
                submitHandler:function(form){
                    apply();
                    // alert("submitted");
                    //form.submit();
                }
            });
            <%
                }
            %>
            //menberLogin();
            //$('#btn_submit').bind('click', apply);
            //$("#p_msg").show();
        })
        function showLoader() {
            //显示加载器.for jQuery Mobile 1.2.0
            //menberLogin();
        }
        //隐藏加载器.for jQuery Mobile 1.2.0
        function hideLoader()
        {
            //隐藏加载器
            $.mobile.loading('hide');
        }
        //            var rule = {
        //                divInfo: {
        //                    id: "msg-error "
        //                },
        //                objInfo: {
        //                }
        //            }
    </script>
</head>
<body>
<div data-role="page" id="pageone" data-theme="a">
    <!--            <div class="ui-header ui-bar-a" data-swatch="a" data-theme="a" data-form="ui-bar-a" data-role="header" role="banner">
                    <a class="ui-btn-left ui-btn-corner-all ui-btn ui-icon-home ui-btn-icon-notext ui-shadow" title=" Home " data-form="ui-icon" data-role="button" role="button"> Home </a>
                    <h1 id="head_txt" class="ui-title" tabindex="0" role="heading" aria-level="1">申请注册</h1>
                    <a class="ui-btn-right ui-btn-corner-all ui-btn ui-icon-grid ui-btn-icon-notext ui-shadow" title=" Navigation " data-form="ui-icon" data-role="button" role="button"> Navigation </a>
                </div>-->
    <div id="div_main" data-role="main" class="ui-content">
        <div class="ui-field-contain">
            <h1>注册申请</h1>
        </div>
        <form id="form1">
        <div class="ui-field-contain">
            <label for="ZMobile">姓名:</label>
            <input type="text" id="ZMobile" value="" placeholder="您的姓名是？" required />
        </div>

        <div class="ui-field-contain">
            <label for="ZName">联系电话</label>
            <input type="text" id="ZName" value="" placeholder="请输入电话号码" required />
        </div>

        <div class="ui-field-contain">
            <label for="ZYangzhichang">机构名称</label>
            <input type="text" id="ZYangzhichang" value="" placeholder="要管理的机构名称" required />
        </div>
        <input id="btn_submit" type="submit" value="提交申请">
        </form>
    </div>
</div>
</body>
</html>
