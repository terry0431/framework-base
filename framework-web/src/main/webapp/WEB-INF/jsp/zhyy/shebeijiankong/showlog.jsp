<%-- 
    Document   : showlog
    Created on : 2018-4-26, 10:14:22
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="../../include/commontop.jsp" %>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>log</h1>
        <div id="div_log"></div>
    </body>
    <script type="text/javascript">
    var getdata_url = "/framework-web/ifs/zhyy/shebeijiankong/getData";
    getlog();
    var oldstr = "";
    function getlog() {
        //alert("re load");
        $.ajax({
            url: getdata_url,
            type: 'POST',
            async: false,
            data: {},
            success: function (data) {
                if(oldstr != data){
                    $("#div_log").append(data + "<br/>");
                    oldstr = data;
                }
                setTimeout("getlog()",5000);
            },
            error: function (data) {
                alert("error: " + JSON.stringify(data));
            }
        });

    }
    </script>
</html>
