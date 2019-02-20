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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pid = request.getAttribute("parentId") == null ? "0" : (String) request.getAttribute("parentId");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>新增模块</title>
    <%@ include file="../include/commontop.jsp" %>
    <script type="text/javascript" src="<%=basePath %>dwr/interface/MenuController.js"></script>
</head>
<body>
<div class="box_Bomb">
    <span class="right">带<b class="red mlr5">*</b>号为必填项</span>

    <div class="box_b_t">
        <h2 class="mb10">
            <span class="icon12">新增模块信息</span>
        </h2>

        <form id="forms">
            <table class="box_tab_1">
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>模块名称：</td>
                    <td><input id="m_name" type="text" maxlength="100"/></td>
                </tr>
                <tr>
                    <td class="tab1_tit"><b class="red mlr5"></b>url：</td>
                    <td><input id="m_url" type="text" maxlength="100"/></td>
                </tr>
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>次序：</td>
                    <td><input id="m_num" type="text" itype="int" positive="positive" maxlength="4"/></td>
                </tr>
				<tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>是否可用：</td>
                    <td><input type="checkbox" id="m_enable" checked="checked" /></td>
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
</html>
<script type="text/javascript">
    $(function() {
         JCTools.intInit($("#m_num"))
        JC.validateByEvent(rule);
    });
    var pid = <%=pid%>;
    var rule = {
        divInfo:{
            id:"msg-error "
        },
        objInfo:{
            m_name : {
                checkEmpty:["m_name","模块名称"]
            },
            m_num : {
                checkEmpty:["m_num","次序"],
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
        
        obj["m_parent_id"] = <%=pid%>;
        MenuController.saveMenu(obj, pid, function(data) {
            if (data == "0") {
                alert("保存失败")
            } else {
                if (pid == 0) { // 当父节点为0时，当前添加的节点为一级节点，只存入节点名称
                    top.win.topsave(data, obj["m_name"], $("#m_num").val())
                } else { // 否则，存入节点名称和节点父节点ID
                    top.win.save(data, obj["m_name"], pid, $("#m_num").val());
                }
                alert("保存成功")
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
