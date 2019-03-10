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
    <input type="button" onclick="shutdownServer()" value="停止服务" /><br/>
    <a href="http://www.haihuodl.com/framework-mq/logs/debug.log">消息队列debug日志</a>
    <a href="http://www.haihuodl.com/framework-mq/logs/info.log">消息队列info日志</a>
    <a href="http://www.haihuodl.com/framework-mq/logs/warn.log">消息队列warn日志</a>
    <a href="http://www.haihuodl.com/framework-mq/logs/error.log">消息队列error日志</a><br/>
    <a href="http://www.haihuodl.com/framework-transceriver/logs/debug.log">收发器debug日志</a>
    <a href="http://www.haihuodl.com/framework-transceriver/logs/info.log">收发器info日志</a>
    <a href="http://www.haihuodl.com/framework-transceriver/logs/warn.log">收发器warn日志</a>
    <a href="http://www.haihuodl.com/framework-transceriver/logs/error.log">收发器error日志</a><br/>
    <a href="http://www.haihuodl.com/framework-web/logs/debug.log">WEB debug日志</a>
    <a href="http://www.haihuodl.com/framework-web/logs/info.log">WEB info日志</a>
    <a href="http://www.haihuodl.com/framework-web/logs/warn.log">WEB warn日志</a>
    <a href="http://www.haihuodl.com/framework-web/logs/error.log">WEB error日志</a><br/>
</body>
<script type="text/javascript">
    var mq_server_run_url = "http://localhost:8080/framework-mq/ifs/mp/shutdown";
    var tc_server_run_url = "http://localhost:8080/framework-trancseriver/ifs/tc/shutdown";

    var tc_server_shutdown_url = "http://www.haihuodl.com/framework-transceriver/ifs/tc/shutdown";
    var mq_server_shutdown_url = "http://www.haihuodl.com/framework-mq/ifs/mp/shutdown";
    var web_server_shutdown_url = "http://www.haihuodl.com/framework-web/ifs/web/shutdownAll";

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
