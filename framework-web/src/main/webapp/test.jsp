<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <div id="remsg"></div>
        发送命令:<input id="msg" type="text" /> <input onclick="senddata()" type="button" value="发送" />
    </body>
    <script type="text/javascript">
        var loaddata_url = "ifs/econtrol/econtrol/getData";
        var senddata_url = "ifs/econtrol/econtrol/sendData";
        function loaddata(){
            $.ajax({
                url: loaddata_url,
                data: {
                },
                type: 'POST',
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                timeout: 3000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if (json != "") {
                        $("#remsg").empty();
                        $("#remsg").append(json);
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
        var int=self.setInterval("loaddata()",3000);
        loaddata();
    </script>
</html>
