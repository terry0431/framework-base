<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    List<Map> secondMenuList = (List) request.getAttribute("secondMenuList");
    for (int i = 0, j = secondMenuList.size(); i < j; i++) {
        Map valueMap = secondMenuList.get(i);
%>
<li><img src="<%=request.getContextPath()%>/common/blue/images/menubgline_1.gif"/></li>
<li>
<%
	    if(valueMap.get("m_target") != null && !valueMap.get("m_target").toString().equals("")){
%>
	<a target="<%=valueMap.get("m_target") %>" href="<%=valueMap.get("m_url")%>" parentId="<%=valueMap.get("id")%>"><%=valueMap.get("m_name")%></a></li>
<%	
	    }else{
%>
    <a url="<%=valueMap.get("m_url")%>" parentId="<%=valueMap.get("id")%>"><%=valueMap.get("m_name")%></a></li>
<%
	    }
    }
%>