<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: wangf
  Date: 2011-12-7
  Time: 下午3:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色列表</title>
    <%@ include file="../include/commontop.jsp" %>
</head>
<body>
<div class="Area">
    <div class="mainRight">
        <ul class="Location Grayline mb10 Gray_bg">
            <li class="parent"><img src="<%=path%>/common/style1/images/icon_14.gif" width="14" height="14"/>当前位置</li>
            <li class="parent"><a>权限管理</a></li>
            <li class="parent"><a>权限管理</a></li>
            <li class="current"><a>角色管理</a></li>
        </ul>
        <form id="subForm" action="role/roleList.do" method="post" target="_self">
            <div class="titlst">
                <div class="box_c">
                    <h3 class="box_c_tit">查询</h3>
                    <ul class="search">
                        <li>
                            <span class="mlr5 left">角色名称：</span>
                            <div class="left mlr5">
                                <input class="input_1 Grayline" name="rolename" id="rolename" type="text"/>
                            </div>
                        </li>
                        <li>
                            <span class="mlr5 left">角色类别：</span>
                            <div class="left mlr5">
                                <select id="roletype">
                                	<option value="">全部</option>
                                	<option value="1">菜单角色</option>
                                	<option value="2">审批角色</option>
                                	<option value="3">栏目角色</option>
                                </select>
                            </div>
                        </li>
                        <li><span class="btn2" id="search">查询</span></li>
                    </ul>
                    <div class="box_c" id="tableDiv"></div>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
    var editRoleDialog;
    var setUserDialog;
    var setModuleRoleDialog;
    var setLanmuRoleDialog;
    var setApplicationDialog;

    

    function rowClickFunc(e) {
        var rowObj = e.data.arg;
//        alert("row:"+rowObj.cell.name.value)
    }
           var initOption = {
        container:'#tableDiv',
        caption:"角色管理",
        head:"角色列表",
        method:"<%=path%>/ifs/role/list",
//        arg:{name:"%理%"},
        buttonList:[
            {name:"新增", className:"btn_1", style:"",event:"click", eventFunc:addRole,customArg:{}},
//            {name:"导出", className:"btn_2", event:"click", eventFunc:tableButtonEvent},
//            {name:"导入", className:"btn_3", event:"click", eventFunc:tableButtonEvent},
//            {name:"打印", className:"btn_4", event:"click", eventFunc:tableButtonEvent},
            {name:"刷新", className:"btn_5", event:"click", eventFunc:refresh}
        ],
        colModel:[
            //{name:'checkbox', value:iconImg.checkBoxIcon, width:"10%",align:"center"},
            {name:'edit', value:'编辑', width:"10%",className:"",style:"" ,align:"center"},
            {name:'name', value:'角色名称', width:"20%",align:"center"},
            {name:'type', value:'角色类别', width:"20%", align:"center"},
            {name:'description', value:'描述', width:"20%", align:"center"},
//            {name:'setapplication', value:'系统权限', width:"10%", align:"center"},
            {name:'setmodule', value:'平台权限', width:"10%", align:"center"},
            {name:'setlanmu', value:'养殖场权限', width:"10%", align:"center"},
            {name:'setuser', value:'用户权限', width:"10%", align:"center"},
//            {name:'setindex', value:'指标权限', width:"10%", align:"center"},
            {name:'delete', value:'删除', width:"10%", align:"center"}
        ]
    }




    function dataHandle(list, opt) {
        var rows = [];
        for (var i = 0, j = list.length; i < j; i++) {
        	 rows.push({
                cell:{
                    id:{value:list[i].id},
                    name:{value:list[i].name, align:"center"},
                    description:{value:list[i].description,align:"center"},
                    type:{value:list[i].type,align:"center"},
                    checkbox:{value:'<input name="" type="checkbox" value="' + list[i].id + '"/>', align:"center"},
                    edit:{value:iconImg.editIcon, event:"click", eventFunc:editRole, customArg:{}, align:"center"},
//                    setapplication:{value:iconImg.setIcon,event:"click", eventFunc:setapplications, customArg:{}, align:"center"},
                    setlanmu:{value:list[i].setlanmu,align:"center"},
                    setmodule:{value:list[i].setmodule,align:"center"},
                    setuser:{value:iconImg.setIcon,event:"click", eventFunc:setusers, customArg:{}, align:"center"},
//                    setindex:{value:list[i].setindex,align:"center"},
                    "delete":{value:iconImg.deleteIcon,event:"click", eventFunc:deleteRole, customArg:{}, align:"center"}
                }
            });
        }
        return rows;
    }

    $(function () {
        $("#search").click(function() {
            initOption.arg = { name:"%" + $("#rolename").val() + "%",type:$("#roletype").val()
            }
            JC.table(initOption)
        })
        JC.table(initOption)
    })
    
    function refresh() {
        $("#subForm").submit();
    }
    
    
    
    function deleteRole(e) {
        var cellObj = e.data.arg;
//        alert("cell:" + cellObj.cell.id.value)
        var ids = [];
        ids.push(cellObj.cell.id.value)
        var that = this;
        if (confirm("确认删除本条数据？")) {
            RoleController.remove(ids, function(data) {
                if (data == "1") {
                    alert("删除成功");
                } else {
                    alert("删除失败");
                }
                $(that).parents("tr").remove();
                JC.tableOpt.rowTotal-=1;
                JC.table(JC.tableOpt)
            })
        }
    }
    function addRole() {
         formDialog = new $.dialog({
                id:'formDialogFrame',
                title:"新增水质信息",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/zhyy/shuizhi/add',
                autoPos:true
	});
    }


    //编辑方法
    function editRole(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        formDialog = new $.dialog({
                    id: 'formDialogFrame',
                    title: "编辑水质信息",
                    height: 500,
                    width: 750,
		    cover:true,
		    content: 'url:<%=path%>/con/zhyy/shuizhi/edit?id=' + id,
                    autoPos:true
                });

    }
    
    //用户权限
    function setusers(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        setUserRoleDialog = new parent.win.$.dialog({
            id:'setusers',
            title:"用户角色添加",
            height:400,
            width:520,
            cover:true,
            page:"role/userAuthority.do?id=" + id,
            autoPos:true
        });
        setUserRoleDialog.ShowDialog();
    }
    
    //模块权限
    var setModuleRoleDialog;
    function setmodules(id) {
        
        setModuleRoleDialog = new $.dialog({
                id:'formDialogFrame',
                title:"新增水质信息",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/role/moduleAuthority?id=' + id,
                autoPos:true
	});
    }
    
    //栏目权限
    function setlanmus(id) {
       // var cellObj = e.data.arg;
       // var id = cellObj.cell.id.value
        setLanmuRoleDialog = new parent.win.$.dialog({
            id:'setmodules',
            title:"角色模块权限",
            height:800,
            width:620,
            cover:true,
            page:"role/lanmuAuthority.do?id=" + id,
            autoPos:true
        });
        setLanmuRoleDialog.ShowDialog();
    }

     //模块权限
    function setapplications(e) {
        var cellObj = e.data.arg;
        var id = cellObj.cell.id.value
        setApplicationDialog = new parent.win.$.dialog({
            id:'setapplications',
            title:"角色系统权限",
            height:800,
            width:620,
            cover:true,
            page:"role/applicationAuthority.do?id=" + id,
            autoPos:true
        });
        setApplicationDialog.ShowDialog();
    }
</script>

<script type="text/javascript">
top.win.dom = document;

//选择学员   flag：0单选1多选
function chooseXueyuan(id){
	selectDialog = new parent.win.$.dialog({
        id:'xueyuanSel',
        title:"选择学员",
        height:500,
        width:950,
        cover:true,
        page:"common/toselectxueyuan.do?id="+id+"&flag=1",
        autoPos:true
    });
    top.win.dialog = selectDialog;
    selectDialog.ShowDialog();
}
//选择讲师   flag：0单选1多选
function chooseJiangshi(id){
	selectDialog = new parent.win.$.dialog({
        id:'jiangshiSel',
        title:"选择讲师",
        height:500,
        width:950,
        cover:true,
        page:"common/toselectjiangshi.do?id="+id+"&flag=1",
        autoPos:true
    });
    top.win.dialog = selectDialog;
    selectDialog.ShowDialog();
}
//选择代理   flag：0单选1多选
function chooseDaili(id){
	selectDialog = new parent.win.$.dialog({
        id:'dailiSel',
        title:"选择事业妈妈（代理）",
        height:500,
        width:950,
        cover:true,
        page:"common/toselectdaili.do?id="+id+"&flag=1",
        autoPos:true
    });
    top.win.dialog = selectDialog;
    selectDialog.ShowDialog();
}
//选择开班计划   flag：0单选1多选
function chooseKaibanjihua(id){
	selectDialog = new parent.win.$.dialog({
        id:'kaibanjihuaSel',
        title:"选择开班计划",
        height:500,
        width:950,
        cover:true,
        page:"common/toselectkaibanjihua.do?id="+id+"&flag=1",
        autoPos:true
    });
    top.win.dialog = selectDialog;
    selectDialog.ShowDialog();
}
//选择中心   flag：0单选1多选
function chooseZhongxin(id){
	selectDialog = new parent.win.$.dialog({
        id:'zhongxinSel',
        title:"选择中心",
        height:500,
        width:950,
        cover:true,
        page:"common/toselectzhongxin.do?id="+id+"&flag=1",
        autoPos:true
    });
    top.win.dialog = selectDialog;
    selectDialog.ShowDialog();
}
</script>
</html>

