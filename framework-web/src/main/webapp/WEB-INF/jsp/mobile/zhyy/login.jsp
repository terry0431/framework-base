<%--
  User: wangbo
  Date: 2019\2\26 0026
  Time: 9:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会员登录</title>
    <%@ include file="../include/common.jsp" %>
    <script src="<%=path%>/common/mobile/js/common.js"></script>
    <script type="text/javascript">
        var id = "2";
        var getMenber_url = "<%=path%>/wx/getMenber";
        var apply_url = "<%=path%>/ifs/zhyy/zhanghao/apply";
        function menberLogin() {
            var code = getUrlParam("code");
            alert("code:" + code);
            var menberlogin_serverurl = getMenber_url;
            $.ajax({
                url: menberlogin_serverurl,
                type: 'GET',
                async: false,
                //dataType:'JSONP',
                data: {code: code},
                success: function (data) {
                    alert("ok " + data.z_state);
                    alert("ok " + data.z_state);
                    if (data.z_state == 1) {//审核通过
                        //跳转主页
                        $.mobile.changePage("index.html", "slideup");
                    } else if (data.z_state == -2) {//申请失败
                        //不做处理 可重新申请
                    } else if (data.z_state == -1) {//未申请
                        //不做处理 正常填写表单
                    } else if (data.z_state == -0) {//等待审核
                        //清除表单 显示 正在等待审核
                        $("#div_main").html("<h1>您的账号正在审核中。。。</h1>");
                    }
                    id = data.id;
                },
                error: function (data) {
                    alert("error: " + JSON.stringify(data));
                }
            });
        }



        function apply() {
            //alert(123);
//                if (!JC.validate(rule)) {
//                    return;
//                }
            var obj = {};
            obj.id = id;
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
                    } else if (data == "0") {
                        //alert("保存失败");
                        $("#div_main").html("<h1>提交失败。。。</h1>");
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
            $('#btn_submit').bind('click', apply);
            //$("#p_msg").show();
        })
        function showLoader() {
            //显示加载器.for jQuery Mobile 1.2.0
            $.mobile.loading('show', {
                text: '加载中...', //加载器中显示的文字
                textVisible: true, //是否显示文字
                theme: 'a',        //加载器主题样式a-e
                textonly: false,   //是否只显示文字
                html: ""           //要显示的html内容，如图片等
            });
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
        <div class="ui-field-contain">
            <label for="ZMobile">姓名:</label>
            <input type="text" id="ZMobile" value="" placeholder="您的姓名是？" />
        </div>

        <div class="ui-field-contain">
            <label for="ZName">联系电话</label>
            <input type="text" id="ZName" value="" placeholder="请输入电话号码" />
        </div>

        <div class="ui-field-contain">
            <label for="ZYangzhichang">机构名称</label>
            <input type="text" id="ZYangzhichang" value="" placeholder="要管理的机构名称" />
        </div>
        <input id="btn_submit" type="button" value="提交申请">
    </div>
</div>
</body>
</html>
