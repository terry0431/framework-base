<%@page import="com.os.framework.vo.manager.User"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=8" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>主页</title>
        <%@include file="include/commontop.jsp"%>
        <script type="text/javascript" src="<%=path%>/js/index.js"></script>

        <link rel="Shortcut Icon" href="<%=path%>/images/icon.ico"/>
        <LINK href="<%=path%>/common/index_css/loginin.css" rel=stylesheet type=text/css />

    </head>
        <%
            List<Map> stairMenuList = (List) request.getAttribute("stairMenuList"); 
            User user = (User) session.getAttribute("userInfo");
            
        %>
        <div id="top_banner">
            <div id="top_banner_pic"></div>
            <div id="top_banner_txt">
                <table border="0" cellspacing="0" cellpadding="0"
                       class="top_banner_date">
                     <tr>
                        <td id="div_datetime" class=""></td>
                    </tr>
                </table>
                <div class="blank17"></div>

                <table border="0" cellspacing="0" cellpadding="0"
                       class="top_banner_date">
                    <tr>                  
                        <td width="100" align="center" class="font_login_blue">
                            <%
                                if(user.getType().equals("2")){
                            %>
                            <b class="font_login_org">
                            <a target="_blank" href="con/zhyy/yangzhichang/view">在线监测</a>
                            </b>
                            <%
                                }
                            %>
                            
                        </td>
                        <td width="60">
                            <b class="font_login_org">
                                <a id="notify" ><img height="25px" width="35px" src="/framework-web/common/style1/images/baojing.png" />(<span id="counter">0</span>)</a>
                            </b>
                        </td>
                        <td width="180">
                            <span class="font_login_blue">欢迎，</span><b class="font_login_org">
                                <%=user.getName()%>
                            </b>
                        </td>
                         <td width="62" align="center" class="font_login_blue">
                             <a id="userInfoModify" href="#">信息维护</a>
                         </td>
                        <td align="left" class="font_login_blue">
                            <a href="logout">|&nbsp;注&nbsp;&nbsp;销</a>
                        </td>
                     </tr>
                </table>
            </div>
        </div>
        <div class="menu">
            <ul id="menuUl">
            </ul>
        </div>
        <div class="main mb10">
            <div class="mainLeft Sideline" id="leftDiv"
                  style="overflow-x: hidden;">
                 <ul class="mainNav" id="mainNav"></ul>
            </div>
            <div class="mainRArea Sideline" id="mainDiv">
                <iframe id="mainFrame" name="mainFrame" frameborder="0"
                        style="width: 99%"></iframe>
            </div>
        </div>
        <div class="footer">
            <span clas s="left">智慧渔业信息管理平台</span>
        </div>
</form>
</body>

 <script type="text/javascript">

    //var desktopsrc = "gis/haishen/index.html";
    var desktopsrc = "con/zhyy/shuizhishuju/now";
    //var pid = "80";
    pid = "null";
    var run;
    $(function() {
        showLeftTime();
        //viewYangzhichang();
    });
    
//    var viewDialog;
//    function viewYangzhichang() {
//
//        viewDialog = new $.dialog({
//                    id: 'formDialogFrame',
//                    title: "zaixia",
//                    zIndex:2000,
//                    
//                    width:'100%',
//                    height:'100%',
//		    cover:true,
//		    content: 'url:<%=path%>/con/zhyy/yangzhichang/view',
//                    autoPos:true
//                });
//                viewDialog.max();
//    }
//    $("#viewYzc").click(function () {    	
//        viewYangzhichang();
//    })
    var initializationTime = (new Date()).getTime();

    function showLeftTime() {
        var dayNames = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
        var now = new Date();
        var year = now.getFullYear();
        var month = now.getMonth() + 1;
        var day = now.getDate();
        var hours = now.getHours();
        var minutes = now.getMinutes();
        var seconds = now.getSeconds();
        $("#div_datetime").empty();
            $("#div_datetime").html(
                    "" + year + "年" + month + "月" + day + "日 " + dayNames[now.getDay()]
                    + " " + hours + ":" + minutes + ":" + seconds + "");
        //一秒刷新一次显示时间
        var timeID = setTimeout(showLeftTime, 1000);
    }


</script>
</html>