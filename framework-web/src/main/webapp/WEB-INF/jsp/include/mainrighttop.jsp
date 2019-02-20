<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String parentPosition=request.getParameter("parentPosition");
    String parentPosition2=request.getParameter("parentPosition2");
    String childPosition=request.getParameter("childPosition");
%>
<div class="mainRight">
    <ul class="Location Grayline mb10">
        <li class="parent"><img src="images/Location_oicn1.gif" width="14" height="14" />当前位置</li>
        <%if (parentPosition != null) {%>
        <li class="parent"><%=parentPosition%></li>
        <%}%>
        <%if (parentPosition2 != null) {%>
        <li class="parent"><%=parentPosition2%></li>
        <%}%>
        <%if (childPosition != null) {%>
        <li class=""><%=childPosition%></li>
        <%}%>
    </ul>

