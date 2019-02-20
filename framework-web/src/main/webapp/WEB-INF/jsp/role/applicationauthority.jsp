<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: wangf
  Date: 2011-12-6
  Time: 下午2:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String roleId = (String) request.getAttribute("roleId");
    String tree = (String) request.getAttribute("tree");
    Map authority =(Map)request.getAttribute("authority");
%>
<html>
<head>
    <title>权限管理</title>
    <%@ include file="../include/commontop.jsp" %>
    <script type="text/javascript" src="<%=basePath%>dwr/interface/RoleController.js"></script>
</head>
<body>
<style type="text/css">
    .ztree li button.add {
        margin-left: 2px;
        margin-right: -1px;
        background-position: -112px 0;
        vertical-align: top;
        *vertical-align: middle
    }
</style>


<div class="box_Bomb">
    <span class="right">带<b class="red mlr5">勾</b>号为选中项</span>
    <input type="hidden" id="roleId" value="<%=roleId%>"/>

    <div class="box_b_t">
        <h2 class="mb10">
            <span class="icon12">系统添加</span>
        </h2>

        <form id="forms">
            <table class="box_tab_1">
                <tr>
                    <td class="tab1_tit">
                        <ul id="organization" class="ztree"></ul>
                    </td>
                </tr>
                    <tr>
                <td>权限选择： 系统权限 <input type="checkbox"  id='authority' name='authority' />系统数据 <input type="checkbox"  id='data' name='data'/> </td>

                </tr>
                <tr>
                    <td class="tab1_btn" colspan="1">
                        <span class="mlr5" id="save"><b class="btn btn_Lg btn_yellow">保存</b></span>
                        <span class="mlr5" id="exit"><b class="btn btn_Lg btn_yellow">关闭</b></span>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
<SCRIPT>

    $(function() {
        if ("<%=authority.get("g_dataflag")%>" == "1") {
            $("#data").attr("checked", "checked")
        }
        if ("<%=authority.get("g_authorityflag")%>" == "1") {
            $("#authority").attr("checked", "checked")
        }
        $("#save").bind("click", function() {
            if($("#authority").attr("checked")!="checked"&&$("#data").attr("checked")!="checked"){
                alert("权限选择至少选择一个")
                return;
            }
            var nodes = zTreeObj.getCheckedNodes();
            var roleId = $("#roleId").val();
            var submitList = [];
            for (var node in nodes) {
                if (!nodes[node].isParent) {
                    var obj = {}
                    obj.g_application_id = nodes[node].id;
                    obj.g_role_id = roleId;
                    obj.g_dataflag = $("#data").attr("checked")=="checked"?1:0;
                    obj.g_authorityflag = $("#authority").attr("checked")=="checked"?1:0;
                    submitList.push(obj);
                }
            }
            RoleController.saveApplicationAuthority(submitList, roleId, function(data) {
                if (data) {
                    alert('保存成功')
                }
                else {
                    alert("由于网络原因删除失败，请稍候重试！");
                }
                top.win.setApplicationDialog.cancel();
            })
        })
        $("#exit").bind("click", function() {
          top.win.setApplicationDialog.cancel();
        })
        var organizationSetting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };
        var zTreeObj = zTree($("#organization"), organizationSetting, <%=tree%>);
    });


</SCRIPT>