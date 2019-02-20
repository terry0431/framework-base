<%--
  Created by IntelliJ IDEA.
  User: wj
  Date: 2011-12-5
  Time: 14:58:41
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Map map = (HashMap) request.getAttribute("map");
    String menuId = (String) request.getAttribute("menuId");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>编辑模块</title>
    <%@ include file="../include/commontop.jsp" %>
    <script type="text/javascript" src="<%=basePath %>dwr/interface/MenuController.js"></script>
</head>
<body>
<div class="box_Bomb">
    <span class="right">带<b class="red mlr5">*</b>号为必填项</span>

    <div class="box_b_t">
        <h2 class="mb10">
            <span class="icon12">编辑模块信息</span>
        </h2>
        <form id="forms">
            <table class="box_tab_1">
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>模块名称：</td>
                    <td><input id="m_name" type="text" maxlength="100" value="<%=map.get("m_name")%>"/></td>
                </tr>
                <tr>
                    <td class="tab1_tit"><b class="red mlr5"></b>url：</td>
                    <td><input id="m_url" type="text" maxlength="100" value="<%=map.get("m_url")%>"/></td>
                </tr>
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>次序：</td>
                    <td><input id="m_num" type="text" itype="int" positive="positive"  maxlength="4" value="<%=map.get("m_num")%>"/></td>
                </tr>
				<tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>是否可用：</td>
                    <%
                    if(map.get("m_enable").toString().equals("1")){
                    %>
                    <td><input type="checkbox" id="m_enable" checked="checked" /></td>
                    <%
                    }else{
                    %>
                    <td><input type="checkbox" id="m_enable" /></td>
                    <%
                    }
                    %>
                    
                </tr>
                <tr>
                    <td class="tab1_btn" colspan="4">
                        <span class="mlr5" id="save"><b class="btn btn_Lg btn_yellow">保存</b></span>
                        <span class="mlr5" id="clear"><b class="btn btn_Lg btn_yellow">重置</b></span>
                        <span class="mlr5" id="exit"><b class="btn btn_Lg btn_yellow">关闭</b></span>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
    $(function() {
        JCTools.intInit($("#m_num"))
        JC.validateByEvent(rule);
    });
    var pid = <%=menuId%>;
    var rule = {
        divInfo:{
            id:"msg-error "
        },
        objInfo:{
            m_name : {
                checkEmpty:["m_name","模块名称"]
            },
            m_num : {
                checkEmpty:["m_num","次序"]
//                checkNum: ["m_num","次序"]
            }
        }
    };
    $("#save").click(function() {
        if (!JC.validate(rule)) {
            return;
        }
        var obj = new Object();
        obj.m_name = $("#m_name").val();
        obj.m_url = $("#m_url").val();
        obj.m_num = $("#m_num").val();
        if(document.getElementById("m_enable").checked){
        	obj.m_enable = '1';
        }else{
        	obj.m_enable = '0';
        }
        obj.id = <%=menuId%>;
        // 修改模块名称，修改后更新列表
        top.win.$("#myworldcupgirdtree_" + pid + "_nodeclick_vl").html(obj.m_name);
        // 修改次序，修改后更新列表
        top.win.$("#myworldcupgirdtree_" + pid + "_2_vl").html(obj.m_num);
        MenuController.updateMenu(obj, function(data) {
            if (data == "1") {
                alert("保存成功")
            } else {
                alert("保存失败")
            }
            top.win.dialog.cancel();
        })
    });

    $("#exit").click(function() {
        top.win.dialog.cancel();
    });

    $("#clear").click(function() {
        $("#forms")[0].reset();
    })
</script>
</html>