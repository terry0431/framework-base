<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3c.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>智慧渔业信息管理平台</title>
        <LINK href="index_css/loginin.css" rel=stylesheet type=text/css>
        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
        <script src="<%=path%>/js/ui/ui.js"></script>
    </head>

    <body>
        <!-- 页头 -->
        <div id="top_lgbg">
            <div id="top_lg"></div>
        </div>

        <!-- 登录 -->
        <div id="login_bg">
            <div id="login_form">
                <div class="blank93"></div>
                <form id="subForm" action="login" method="post">
                    <table border="0" cellpadding="0" cellspacing="0" class="login_dl">
                        <tr>
                            <td width="87"><strong>用户名：</strong></td>
                            <td class="login_dlsr"><label>
                                    <input name="loginName" type="text" id="loginName" />
                                </label></td>
                        </tr>
                    </table>
                    <div class="blank11"></div>
                    <table border="0" cellpadding="0" cellspacing="0" class="login_dl">
                        <tr>
                            <td width="87"><strong>密&nbsp;&nbsp;码：</strong></td>
                            <td class="login_dlsr"><label>
                                    <input type="password" id="password" name="password" />
                                </label></td>
                        </tr>
                    </table>
                    <div class="blank11"></div>
                    <table width="356" border="0" cellspacing="0" cellpadding="0" align="center">
                        <tr>
                            <td align="left" id="loginbtn">

                                <label>
                                    <input type="button" id="submitBtn" value=""/>
                                </label>
                            </td>
                            <td align="right" id="cancelbtn">
                                <label>
                                    <input type="button" id="cancel" value=""/>
                                </label>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div id="login_bottom"></div>

        </div>
    </body>
</html>

<script type="text/javascript">
    $(function () {
        var h = window.screen.height;
        var w = window.screen.height.width;

        $("#top_lgbg").css("height", h / 2);
        $("#top_lg").css("height", h / 2);

        $("#submitBtn").click(function () {
            $("#password").val($("#password").val().md5());
            $("#password").unbind("keydown");
            $("#subForm").submit();
        })
        $("#cancel").click(function () {
            $("#password").val("");
            $("#loginName").val("");
        })
        $("#password").keydown(function (e) {
            if (e.which == 13) {
                $("#submitBtn").click();
                return false;
            }
            if (e.which == 9) {
                $("#validateCodeImage").click();
                return false;
            }
        })
        $("#changeValidateCode,#validateCodeImage").click(function () {
            $("#validateCodeImage").attr("src", "validateCode.do?" + Math.random());
        })
    })

</script>
