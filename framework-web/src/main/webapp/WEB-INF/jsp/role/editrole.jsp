<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: wangf
  Date: 2011-12-6
  Time: 下午3:42
  To change this template use File | Settings | File Templates.
--%>
<%
 Map role = (Map)request.getAttribute("role");
%>
<html>
<head>
    <title>编辑角色</title>
    <%@ include file="../include/commontop.jsp" %>
    <script type="text/javascript" src="<c:url value="/dwr/interface/RoleController.js"/>"></script>
</head>
<body>
<div class="box_Bomb">
    <span class="right">带<b class="red mlr5">*</b>号为必填项</span>

    <div class="box_b_t">
        <h2 class="mb10">
            <span class="icon12">编辑角色</span>
        </h2>

        <form id="forms">
            <table class="box_tab_1">
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>角色名称：</td>
                    <td><input id="name" type="text" maxlength="50" value="<%=role.get("name")%>"/></td>
                </tr>
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>角色类别：</td>
                    <td>
                    	<select disabled="disabled" id="type">
                        	<option value="1">菜单角色</option>
                        	<option value="2">审批角色</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tab1_tit"><b class="red mlr5"></b>角色描述：</td>
                    <td><input id="description" type="text" maxlength="100" value="<%=role.get("description")%>" /></td>
                </tr>
                <tr>
                    <td class="tab1_btn" colspan="2">
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
    	$("#type").val("<%=role.get("type")%>");
        JC.validateByEvent(rule);
    })
    $("#save").click(function() {
        if (!JC.validate(rule)) {
            return;
        }
        var obj = {};
        obj.id=<%=role.get("id")%>
        obj.r_name = $("#name").val();
        obj.r_type = $("#type").val();
        obj.r_description = $("#description").val();
        RoleController.update(obj,'1', function(data) {
            if (data == "1") {
                alert("保存成功");
              	top.win.JC.table(top.win.JC.tableOpt);
              	top.win.editRoleDialog.cancel();
            } else {
                alert("保存失败");
            }
        });
    })

    $("#exit").click(function() {
        top.win.editRoleDialog.cancel();
    })
    $("#clear").click(function() {
        $("#forms")[0].reset();
    })

    var rule = {
        divInfo : {
            id : "msg-error "
        },
        objInfo : {
            name : {
                checkEmpty : [ "name", "角色名称" ]
            }
        }
    }
</script>
</html>