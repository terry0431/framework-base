<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>relogin!</title>
    <base href="<%=basePath%>"/>
</head>
<body>
<input id="msg" type="hidden" value="<%=request.getAttribute("msg") == null ? "" :request.getAttribute("msg").toString()%>"/>
</body>
</html>
<script>
    (function msg() {
        var msg = document.getElementById("msg").value;
        if (msg != "") {
            alert(msg);
        }
        top.window.location = "<%=basePath%>login.jsp";
    })();
</script>