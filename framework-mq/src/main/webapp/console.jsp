<%@ page import="com.os.framework.mq.server.TransceriverConnectionServer" %>
<%@ page import="com.os.framework.mq.server.WebConnectionServer" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/5/005
  Time: 9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        TransceriverConnectionServer.getInstance().shutdown();
        WebConnectionServer.getInstance().shutdown();
    %>
    <title>Title</title>
</head>
<body>
<%=application.getRealPath("/")%>
</body>
</html>
