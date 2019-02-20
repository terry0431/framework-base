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
<html>
<head>
    <title>新增角色</title>
    <%@ include file="../include/commontop.jsp" %>
    <script type="text/javascript" src="<c:url value="/dwr/interface/RoleController.js"/>"></script>
</head>
<body>
<div class="box_Bomb">
    <span class="right">带<b class="red mlr5">*</b>号为必填项</span>

    <div class="box_b_t">
        <h2 class="mb10">
            <span class="icon12">新增角色</span>
        </h2>

        <form id="forms">
            <table class="box_tab_1">
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>角色名称：</td>
                    <td><input id="name" type="text" maxlength="50"/></td>
                </tr>
                <tr>
                    <td class="tab1_tit"><b class="red mlr5"></b>角色类别：</td>
                    <td>
                    	<select id="type">
                        	<option value="1">菜单角色</option>
                        	<option value="2">审批角色</option>
                        	<option value="3">栏目角色</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tab1_tit"><b class="red mlr5"></b>角色描述：</td>
                    <td><input id="description" type="text" maxlength="100"/></td>
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
        JC.validateByEvent(rule);
    })
    $("#save").click(function() {
        if (!JC.validate(rule)) {
            return;
        }
        var obj = new Object();
        obj.r_name = $("#name").val();
        obj.r_type = $("#type").val();
        obj.r_description = $("#description").val();
        RoleController.save(obj, function(data) {
            if (data == "1") {
                alert("保存成功");
            } else {
                alert("保存失败");
            }
//            top.win.location.reload();
             top.win.JC.table(top.win.initOption);
            top.win.addRoleDialog.cancel();
        })

    })

    $("#exit").click(function() {
        top.win.addRoleDialog.cancel();
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