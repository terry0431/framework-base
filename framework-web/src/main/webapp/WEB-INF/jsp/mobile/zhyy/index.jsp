<%--
  User: wangbo
  Date: 2019\2\26 0026
  Time: 9:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>智慧渔业信息管理平台</title>
    <%@ include file="../include/common.jsp" %>
    <link rel="stylesheet" href="<%=path%>/common/zhyy/mobile/wap.css">
    <style type="text/css">
        /*div#findBar.ui-grid-a .ui-block-a { width: 32% }*/
        /*div#findBar.ui-grid-a .ui-block-b { width: 32% }*/
        /*div#findBar.ui-grid-a .ui-block-c { width: 32% }*/
        .ui-grid-b,.ui-block-a,.ui-grid-b .ui-bar{ background: #7CC0D9; color: #4a4a4a}
        .ui-grid-a{ background: #678BA3; color: #FFFFFF;}
        /*.ui-block-c{ margin: 0 auto;}*/
        .ui-block-d .ui-bar{ height:45px;background: #ffc09f; color:#4a4a4a; }
    </style>
</head>
<body>
<div data-role="page" id="pageone" data-theme="a">
    <div data-role="header">
        <h1>智慧渔业</h1>
    </div>
    <div data-role="main" class="ui-content">
        <div class="pet_circle_nav">
            <ul class="pet_circle_nav_list">
                <li><a href="" ><img width="55" height="55" src="<%=path%>/images/zhyy/mobile/zxjc.png" /></a><span>池塘监测</span></li>
                <li><a href="" ><img width="55" height="55" src="<%=path%>/images/zhyy/mobile/sbgl.png" /></a><span>设备管理</span></li>
                <li><a href="" ><img width="55" height="55" src="<%=path%>/images/zhyy/mobile/yzzx.png" /></a><span>养殖咨询</span></li>
                <li><a href="" ><img width="55" height="55" src="<%=path%>/images/zhyy/mobile/yyzx.png" /></a><span>渔业咨询</span></li>
                <li><a data-ajax="false" href="<%=path%>/con/mobile/zhyy/waterConveyance/index" ><img width="55" height="55" src="<%=path%>/images/zhyy/mobile/sskz.png" /></a><span>输水控制</span></li>
                <li><a href="" ><img width="55" height="55" src="<%=path%>/images/zhyy/mobile/scgl.png" /></a><span>生产管理</span></li>
                <li><a href="" ><img width="55" height="55" src="<%=path%>/images/zhyy/mobile/qxss.png" /></a><span>气象监测</span></li>
            </ul>
        </div>

        <div class="ui-grid-b">
            <p>&nbsp;&nbsp;气象实时 :2019-03-01 8点</p>
            <div class="ui-block-a">
                <div class="ui-bar ui-bar-a" style="height:45px">气温<br/>18.3度</div>
            </div>
            <div class="ui-block-b">
                <div class="ui-bar ui-bar-a" style="height:45px">湿度<br/>40%</div>
            </div>
            <div class="ui-block-c">
                <div class="ui-bar ui-bar-a" style="height:45px">风力<br/>2米/秒</div>
            </div>
        </div>
        <div class="ui-grid-a">
            <p style="height: 25px;">&nbsp;&nbsp;系统消息</p>
            <div class="ui-block-d">
                <div class="ui-bar ui-bar-a" >2019-01-01 第1养殖池的pH数值异常,当前为8.5 </div>
            </div>
            <div class="ui-block-d">
                <div class="ui-bar ui-bar-a" >2019-01-01 第1养殖池的pH数值异常,当前为8.5 </div>
            </div>
            <div class="ui-block-d">
                <div class="ui-bar ui-bar-a" >2019-01-01 第1养殖池的pH数值异常,当前为8.5 </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
