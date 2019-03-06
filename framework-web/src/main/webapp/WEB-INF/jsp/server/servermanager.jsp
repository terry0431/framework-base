<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/6/006
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="../include/commontop.jsp"%>
</head>
<body>
    <input type="button" onclick="shutdownServer()" value="停止收发器服务" /><br/>
    <input type="button" onclick="shu" value="停止消息队列服务" /><br/>
    <input type="button" onclick="shutdownServer()" value="停止WEB服务" /><br/>
</body>
<script type="text/javascript">
    var mq_server_run_url = "http://localhost:8080/framework-mq/ifs/mp/shutdown";
    var tc_server_run_url = "http://localhost:8080/framework-trancseriver/ifs/tc/shutdown";

    var tc_server_shutdown_url = "http://localhost:8080/framework-transceriver/ifs/tc/shutdown";
    var mq_server_shutdown_url = "http://localhost:8080/framework-mq/ifs/mp/shutdown";
    var web_server_shutdown_url = "http://localhost:8080/framework-web/ifs/web/shutdownAll";

    var server_state_url = "";

    function shutdownServer(){
        $.ajax({
            url:tc_server_shutdown_url,
            data:{},
            type:'POST',
            timeout:10000,
            success: function (json, textStatus) {
                if (json == "1") {
                    alert("停止服务成功");
                } else {
                    alert("停止服务失败");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jsonp.error:" + XMLHttpRequest.returnCode);
            }
        });
        $.ajax({
            url:mq_server_shutdown_url,
            data:{},
            type:'POST',
            timeout:10000,
            success: function (json, textStatus) {
                if (json == "1") {
                    alert("停止服务成功");
                } else {
                    alert("停止服务失败");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jsonp.error:" + XMLHttpRequest.returnCode);
            }
        });
        $.ajax({
            url:web_server_shutdown_url,
            data:{},
            type:'POST',
            timeout:10000,
            success: function (json, textStatus) {
                if (json == "1") {
                    alert("停止服务成功");
                } else {
                    alert("停止服务失败");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jsonp.error:" + XMLHttpRequest.returnCode);
            }
        });
    }


</script>
</html>
