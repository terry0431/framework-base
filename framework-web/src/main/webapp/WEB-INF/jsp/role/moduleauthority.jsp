<%--
  Created by IntelliJ IDEA.
  User: wangf
  Date: 2011-12-6
  Time: 下午4:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String roleId = (String) request.getAttribute("roleId");
    String tree = (String) request.getAttribute("tree");
%>
<html>
<head>
    <title>权限管理</title>
    <%@ include file="../include/commontop.jsp" %>
    <script type="text/javascript" src="<%=path%>/js/zTree/js/jquery.ztree.core-3.0.min.js"></script>
    <script type="text/javascript" src="<%=path%>/js/zTree/js/jquery.ztree.excheck-3.0.min.js"></script>
    <script type="text/javascript" src="<%=path%>/js/zTree/js/jquery.ztree.exedit-3.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path%>/js/zTree/css/zTreeStyle/zTreeStyle.css"/>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>
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
            <span class="icon12">角色模块权限</span>
        </h2>

        <form id="forms">
            <table class="box_tab_1">
                <tr>
                    <td class="tab1_tit">
                        <ul id="organization" class="ztree"></ul>
                    </td>
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
    var save_url = "<%=path%>/ifs/role/saveModuleAuthority";
    $(function() {
        $("#save").bind("click", function() {
            var nodes = zTreeObj.getCheckedNodes();
            var roleId = $("#roleId").val();
            var data = [];
            for (var node in nodes) {
//                if(!nodes[node].isParent){
                var obj = {}
                obj.menu_id = nodes[node].id;
                obj.role_id = roleId;
                data.push(obj);
//                }
            }
            $.ajax({
                url: save_url,
                data: {savedata: JSON.stringify(data),roleId:roleId
                },
                type: 'POST',
                timeout: 3000,
                dataFilter: function (json) {
                    console.log("jsonp.filter:" + json);
                    return json;
                },
                success: function (json, textStatus) {
                    if (json == "1") {
                        alert("保存成功");
                    } else if (json == "0") {
                        alert("保存失败");
                    }
                    top.win.setModuleRoleDialog.close();
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("由于网络原因删除失败，请稍候重试！");
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
//            RoleController.saveModuleAuthority(submitList, roleId, function(data) {
//                if (data) {
//                    alert('保存成功')
//                }
//                else {
//                    alert("由于网络原因删除失败，请稍候重试！");
//                }
//                top.win.setModuleRoleDialog.cancel();
//            })
        })
        $("#exit").bind("click", function() {
            top.win.setModuleRoleDialog.close();
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
//        zTreeObj.expandAll(true);
    });


</SCRIPT>