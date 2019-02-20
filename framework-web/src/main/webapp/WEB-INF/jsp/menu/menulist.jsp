<%--
  Created by IntelliJ IDEA.
  User: wj
  Date: 2011-12-5
  Time: 14:07:19
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>模块管理</title>
        <%@ include file="../include/commontop.jsp" %>
        <script type="text/javascript" src="<%=basePath%>js/tabletree4j/Core4j.js"></script>
        <link rel="StyleSheet" href="<%=basePath%>js/tabletree4j/tabletree4j.css" type="text/css" />
        <script type="text/javascript" src="<%=basePath%>js/tabletree4j/TableTree4j.js"></script>
        <script type="text/javascript" src="<%=basePath%>dwr/interface/MenuController.js"></script>
        <style type="text/css">
            .bigtitle{
                font-family:Arial;
                font-weight:bold;
                font-size:22px;
                margin-bottom:10px;
            }
            .footer{
                font-family:Arial;
                font-weight:bold;
                font-size:12px;
                color:#747474;
            }

            .title{
                padding-left:10px;
                font-family:Arial;
                font-size:18px;
                color:#747474;
            }

            .fromtitle{
                background-color:#f5f5f5;
                text-align:center;
                font-family:Arial;
                color:#acac59;
                padding-top:5px;
                padding-bottom:5px;
            }

            .fromcontent{
                font-family:Arial;
                padding-right:5px;
                padding-bottom:10px;
                padding-top:10px;
            }

            .content{
                font-family:Arial;
            }
            .clickbtn{
                cursor:pointer;
            }
            .headerbg{
                /*background-image:url(../images/table_thbg.gif)*/
                background: #328aa4 url("<%=basePath%>images/table_thbg.gif") repeat-x;
                color: #333;
                height:27px;
                font-family:"Arial";
            }
            .headerbg td{
                text-align:center;
            }


            .tabletree4j-gird td{
            height:27px;
            }

            .arrow-tt-node-content{
                text-align:center;
                width:100%;
            }
            .circle-tt-node-content{
                text-align:center;
                width:100%;
            }
            .default-tt-node-content{
                text-align:center;
                width:100%;
            }
        </style>
    </head>
    <%
        String treeStr = (String)request.getAttribute("treeStr");
    %>
    <body>
       <div class="Area">
    <div class="mainRight">
        <ul class="Location Grayline mb10 Gray_bg">
            <li class="parent"><img src="<%=path%>/common/style1/images/icon_14.gif" width="14" height="14"/>当前位置</li>
            <li class="parent"><a>权限管理</a></li>
            <li class="parent"><a>权限管理</a></li>
            <li class="current"><a>模块管理</a></li>
        </ul>
        <form id="subForm" action="menu/getMenuList.do" method="post" target="_self">
            <div class="box_tab">
                <div class="b_tb_btn">
                    <span class="b_tb_tit tlrline left">模块列表</span>
                    <ul class="b_tb_tit tlrline right">
                        <li class="btn_1" id="add">新增</li>
                        <%--<li class="btn_2">导出</li>--%>
                        <%--<li class="btn_3">导入</li>--%>
                        <%--<li class="btn_4">打印</li>--%>
                        <li class="btn_5" id="refresh">刷新</li>
                    </ul>
                </div>
                 <div class="box_c" >
                <div id="worldcupgird" class="content"
                     style="margin-top:0px;float:left;width:100%;margin-left:0%"></div>
                     </div>
            </div>
        </form>
    </div>
</div>
    </body>
</html>
<script type="text/javascript">
    dummyJsonData = function() {
        this.jsonFIFAHeaders = [
            {
                columns:[
                    {dataIndex:'edit'},
                    {dataIndex:'m_name'},
                    {dataIndex:'m_num'},
                    {dataIndex:'add'},
                    {dataIndex:'operate'}
                ],
                dataObject:{edit:'编辑',m_name:'模块名称',m_num:'次序',add:"新增",operate:'删除'},
                trAttributeNames:['classStyle','style'],
                trAttributeValueObject:{classStyle:'headerbg',style:''}
            }
        ];
        this.jsoninitNodes = <%=treeStr%>;
    }

    //ExpandNodeEvent
    function fifaExpandNodeEvent(node, tree) {
        if (!node.isLoad) {
            tree.startLoadingNode(node);
            alert("此节点无子节点");

            //you logic,you can call ajax here , when call success ,use function 'endLoadingNode(node)' to init the loading node;

            //example for my dummy data logic
            var userObject = node.userObject;
            if (userObject != null) {
                var jsonName = userObject.jsonName;
                if (jsonName != null) {
                    var jsonNodes = eval('new dummyJsonData().' + jsonName);
                    if (jsonNodes != null) {
                        tree.loadingAddNodes(jsonNodes, node.id);
                    }
                }
            }
            /////////////////////////////////

            tree.endLoadingNode(node);
        }
    }

    //infoObj {dataValue:,node:,tabletreeObj:,rowObj:,rowIndex:,container:,columnIndex:}
    function operateRenderFunction(infoObj) {
    //    var value = infoObj.dataValue;
        var node = infoObj.node;
        var tree = infoObj.tabletreeObj;
        var aElobj = Core4j.Domhelper.createElement("a", {
        });
        Core4j.Domhelper.addEventToEl(aElobj, Core4j.Domhelper.ElEventType.CLICK, function() {
            if (node.childs != null && node.childs.length > 0) {
                if (confirm("此节点存在子节点！确定删除吗?")) {
                    MenuController.removeMenu(node.id, function(data) {
                        if (data == "1") {
                            alert("删除成功");
                        } else {
                            alert("删除失败");
                        }
                    });
                    tree.removeNode(node.id);
                }
            } else {
                if (confirm("确定删除吗？")) {
                    MenuController.removeMenu(node.id, function(data) {
                        if (data == "1") {
                            alert("删除成功");
                        } else {
                            alert("删除失败");
                        }
                    });
                    tree.removeNode(node.id);

                }

            }
        });
        aElobj.innerHTML = "<img  src='images/oicn_9.gif'/>";
        return aElobj;
    }
    function editoperateRenderFunction(infoObj) {
    //    var value = infoObj.dataValue;
        var node = infoObj.node;
    //    var tree = infoObj.tabletreeObj;
        var aElobj = Core4j.Domhelper.createElement("a", {
        });
        Core4j.Domhelper.addEventToEl(aElobj, Core4j.Domhelper.ElEventType.CLICK, function() {
            var m_name = encodeURI($("#myworldcupgirdtree_" + node.id + "_nodeclick_vl").html());
            pid = node.id;
            dialog = new parent.win.$.dialog({
                id:'updatemenu',
                title:"编辑模块信息",
                height:300,
                width:320,
                cover:true,
                page:"menu/toEditMenu.do?menuId=" + pid +
                        "&m_name=" + m_name,
                autoPos:true
            });

            dialog.ShowDialog();


        });
        aElobj.innerHTML = "<img  src='images/oicn_7.gif'/>";
        return aElobj;
    }
    function addoperateRenderFunction(infoObj) {
        var value = infoObj.dataValue;
        var node = infoObj.node;
        var tree = infoObj.tabletreeObj;
        var aElobj = Core4j.Domhelper.createElement("a", {
        });
        Core4j.Domhelper.addEventToEl(aElobj, Core4j.Domhelper.ElEventType.CLICK, function() {
            pid = node.id
            dialog = new parent.win.$.dialog({
                id:'addOrganization',
                title:"新增模块信息",
                height:300,
                width:320,
                cover:true,
                page:"menu/toAddMenu.do?parentId=" + pid,
                autoPos:true
            });

            dialog.ShowDialog();

        });
        aElobj.innerHTML = "<img  src='images/oicn_1.gif'/>";
        return aElobj;
    }

    ///////////////////////////////////////////////////////////
    function selectoperateRenderFunction(infoObj) {
        var value = infoObj.dataValue;
        var node = infoObj.node;
        var tree = infoObj.tabletreeObj;
        var aElobj = Core4j.Domhelper.createElement("input", {
            attributeNames:['type','name','checkedvalue'],
            valueObject:{type:'radio'
                ,name:"checks"
                ,checkedvalue:node.id}
        });


        return aElobj;
    }

    //the flow of build tabletree

    //create and config tabletree object
    var fifaGirdTree = new Core4j.toolbox.TableTree4j({
        columns:[
            {width:'20%',canSort:false,renderFunction:editoperateRenderFunction},
            {isNodeClick:true,dataIndex:'m_name',width:'30%'},
            {dataIndex:'m_num',width:'10%',canSort:true},
            {width:'20%',canSort:false,renderFunction:addoperateRenderFunction},
            {width:'20%',canSort:false,renderFunction:operateRenderFunction}
        ],
        treeMode:'gird',
        renderTo:'worldcupgird',
        useLine:true,
        useIcon:true,
        id:'myworldcupgirdtree',
        useCookie:false,
        onExpandNodeEvents:[fifaExpandNodeEvent],
        headers:new dummyJsonData().jsonFIFAHeaders,
        //footers:jsonfooters,
        themeName:'arrow',
        selectMode:'single'
        //floatRight:true
    });

    //build tree by nodes
    fifaGirdTree.build(new dummyJsonData().jsoninitNodes, true);

    var pid
    var dialog
    $(function() {

        $("#add").bind("click", function() {
            pid = 0;
            dialog = new parent.win.$.dialog({
                id:'addOrganization',
                title:"新增模块信息",
                height:300,
                width:320,
                cover:true,
                page:"menu/toAddMenu.do?pid=" + pid,
                autoPos:true
            });
            dialog.ShowDialog();
        })
        $("#refresh").bind("click", function() {
              $("#subForm").submit();
          })

    });

    // 新增保存后，更新一级节点列表
    function topsave(id, name, num) {
        var addnode = new Core4j.toolbox.TableTreeNode({
            id:id,
            order:"0",
            isLeaf:false,
            isLoad:true,
            dataObject:{m_name:name,m_num:num},
            userObject:{isGroup:true}
        });
        //add one node
        fifaGirdTree.addNode(addnode);
    }
    
    // 新增保存后，更新非一级节点列表
    function save(id, name, pid, num) {
        var addnode = new Core4j.toolbox.TableTreeNode({
            id:id,
            isLeaf:false,
            isLoad:true,
            dataObject:{m_name:name,m_num:num},
            userObject:{isGroup:true}
        });
        //add one node
        fifaGirdTree.addNode(addnode, pid);
    }
</script>
