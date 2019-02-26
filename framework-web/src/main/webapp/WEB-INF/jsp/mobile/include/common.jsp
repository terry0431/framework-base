<%
    //response.addHeader("P3P", "CP=CAO PSA OUR");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://demos.jquerymobile.com/1.4.5/css/themes/default/jquery.mobile-1.4.5.min.css">
<script src="https://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>

<base href="<%=basePath%>"/>
