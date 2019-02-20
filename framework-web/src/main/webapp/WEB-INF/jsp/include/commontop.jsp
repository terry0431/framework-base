<%
    //response.addHeader("P3P", "CP=CAO PSA OUR");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/config.js"></script>
<script type="text/javascript" src="<%=path%>/js/ui/ui.js"></script>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/cover/cover.js"></script>
<script type="text/javascript" src="<%=path%>/js/lhgdialog/lhgdialog.js"></script>
<script type="text/javascript" src="<%=path%>/js/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=path%>/js/zhyy/Global.js"></script>

<link rel="stylesheet" type="text/css" href="<%=path%>/common/css/global.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/common/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/common/blue/css/style.css"/>

<link rel="stylesheet" type="text/css" href="<%=path%>/js/ui/validate.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/cover/cover.css"/>

<base href="<%=basePath%>"/>
