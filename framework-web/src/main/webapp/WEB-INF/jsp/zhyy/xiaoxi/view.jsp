<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String id = "";
    if (request.getAttribute("id") != null) {
        id = (String) request.getAttribute("id");
    } else {
        id = "";
    }
    //List<Map<String,Object>> zhyy_yangzhichangList =  (List)request.getAttribute("zhyy_yangzhichangList");
    //List<Map<String,Object>> zhyy_baojingshezhiList =  (List)request.getAttribute("zhyy_baojingshezhiList");
%>
<html>
    <head>
        <title></title>
        <%@ include file="../../include/commontop.jsp"%>
    </head>
    <body>
        <div class="box_Bomb">
            <span class="right">带<b class="red mlr5">*</b>号为必填项</span>
            <div class="box_b_t">
                <h2 class="mb10">
                    <span class="icon12">处理报警消息</span>
                </h2>
                <form id="forms">
                    <table class="box_tab_1" >
                        <tr>
                            <td class="tab1_tit">发送时间：</td>
                            <td id="XSystime"></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">消息内容：</td>
                            <td id="XContent"></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">级别</td>
                            <td id="XJibie"></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">处理情况：</td>
                            <td><textarea disabled="disabled" cols="40" rows="8" id="XChuliqingkuang" ></textarea></td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">处理时间：</td>
                            <td id="XChulishijian"></td>
                        </tr> 
                    </table>
                </form>
            </div>
        </div>
    </body>
    <script type="text/javascript">
    var id = "<%=id%>";
    $(function () {
        if (id != "") {
            loaddata();
        }
    })
    function loaddata() {
        var url = "<%=path%>/ifs/zhyy/xiaoxi/get";
        $.ajax({
            url: url,
            data: {id: "<%=id%>"
            },
            type: 'POST',
            timeout: 3000,
            dataFilter: function (json) {
                console.log("jsonp.filter:" + json);
                return json;
            },
            success: function (json, textStatus) {
                if (json != "") {
                    id = json.id;
                    $("#XSystime").append(json.x_systime);
                    $("#XContent").append(json.x_content);
                    if(json.x_jibie == "1"){
                        $("#XJibie").append("严重");
                    }else if(json.x_jibie == "2"){
                        $("#XJibie").append("一般");
                    }else{
                        $("#XJibie").append("轻微");
                    }
                    $("#XChuliqingkuang").val(json.x_chuliqingkuang);
                    $("#XChulishijian").append(json.x_chulishijian);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jsonp.error:" + XMLHttpRequest.returnCode);
            }
        });
    }
    $("#save").click(function () {
        if (!JC.validate(rule)) {
            return;
        }
        var url = "<%=path%>/ifs/zhyy/xiaoxi/save";
        var obj = {};
        if (id != "") {
            obj.id = id;
        }  
        obj.x_chuliqingkuang = $("#XChuliqingkuang").val();
        obj.x_chulishijian = $("#XChulishijian").val();
        $.ajax({
            url: url,
            data: {arg: JSON.stringify(obj)
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
                } else if (data == "0") {
                    alert("保存失败");
                }
                top.win.JC.table(top.win.initOption);
                top.win.formDialog.close();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jsonp.error:" + XMLHttpRequest.returnCode);
            }
        });
    })

    $("#exit").click(function () {
        top.win.formDialog.close();
    })


    var rule = {
        divInfo: {
            id: "msg-error "
        },
        objInfo: {
        }
    }
    </script>
</html>