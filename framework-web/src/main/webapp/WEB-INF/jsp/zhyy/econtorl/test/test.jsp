<%-- 
    Document   : test
    Created on : 2018-12-18, 9:15:00
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="remsg"></div>
        发送命令:<input id="msg" type="text" /> <input onclick="senddata()" type="button" value="发送" />
    </body>
    <script>
        var loaddata_url = "";
        var senddata_url = "";
        function loaddata(){
            $.ajax({
                url: loaddata_url,
                data: {id: ""
                },
                type: 'POST',
                timeout: 3000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if (json != "") {
                        
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
        function senddata(){
            $.ajax({
                url: senddata_url,
                data: {data: $("#msg").val()
                },
                type: 'POST',
                timeout: 3000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if (json != "") {
                        $("#remsg").append(json);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
    </script>
</html>
