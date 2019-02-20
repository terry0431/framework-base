<%--
  Created by IntelliJ IDEA.
  User: wangf
  Date: 2011-12-6
  Time: 下午4:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%
    String roleId = (String) request.getAttribute("roleId");
    String tree = (String) request.getAttribute("tree");
    
    List<Map<String,Object>> zdlist = (List)request.getAttribute("zdlist");
    String zhandianid = (String)request.getAttribute("zhandianid");
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
            <span class="icon12">角色栏目权限</span>
        </h2>
		选择站点:
		<select id="zhandian_id" onchange="reload_zhandian()">
		<%
		for(Map m : zdlist){
			
		%>
			<option value="<%=m.get("id") %>"><%=m.get("z_mingcheng") %></option>
		<%
		}
		%>
		</select>
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
    </div>
</div>
</body>
</html>
<SCRIPT>

    $(function() {
    	$("#zhandian_id").val("<%=zhandianid%>");
        $("#save").bind("click", function() {
            var nodes = zTreeObj.getCheckedNodes();
            var roleId = $("#roleId").val();
            var submitList = [];
            for (var node in nodes) {
//                if(!nodes[node].isParent){
                var obj = {}
                obj.cms_lanmu_id = nodes[node].id;
                obj.sys_role_id = roleId;
                obj.cms_zhandian_id = "<%=zhandianid%>";
                submitList.push(obj);
//                }
            }
            RoleController.saveLanmuAuthority(submitList, roleId,$("#zhandian_id").val(), function(data) {
                if (data) {
                    alert('保存成功')
                }
                else {
                    alert("由于网络原因删除失败，请稍候重试！");
                }
                top.win.setLanmuRoleDialog.cancel();
            })
        })
        $("#exit").bind("click", function() {
            top.win.setLanmuRoleDialog.cancel();
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
	function reload_zhandian(){
		window.location.href="role/lanmuAuthority.do?zhandian_id=" + $("#zhandian_id").val() + "&id=<%=roleId%>";
	}

</SCRIPT>