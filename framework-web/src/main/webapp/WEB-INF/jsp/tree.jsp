<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: wj
  Date: 2011-12-7
  Time: 15:25:16
--%>
    <%
        List<Map> thirdMenuList = (List) request.getAttribute("thirdMenuList");
        Map fourMap = (Map) request.getAttribute("fourMap");
        for (int i = 0, j = thirdMenuList.size(); i < j; i++) {
            Map valueMap = thirdMenuList.get(i);
    %>
    <li id="tree_<%=i + 1%>"><a onclick="menuSlide('menu_<%=i + 1%>')"><span><%=valueMap.get("m_name")%></span></a></li>
    <ul class="subNav pl7" style="display: none" id="menu_<%=i + 1%>">
    <%
        if (fourMap.get(valueMap.get("id").toString()) != null) {
            List fourlist = (List)fourMap.get(valueMap.get("id").toString());
            for (int m = 0, n = fourlist.size(); m < n; m++) {
                Map fourCMap = (Map)fourlist.get(m);

if(fourCMap.get("m_url").equals("supermap")){
    %>
        <li><a href="iclient8/maps/map1012.html" target="_blank"><%=fourCMap.get("m_name")%></a></li>
        
    <%}else{%>
        <li><a href="<%=fourCMap.get("m_url")%>" target="mainFrame"><%=fourCMap.get("m_name")%></a></li>
    <%}    

            }
        }
    %>
    </ul>
    <%
        }
    %>
