<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String id = "";
    if(request.getAttribute("id") != null){
        id = (String)request.getAttribute("id");
    }else{
        id = "";
    }
    List<Map<String,Object>> zhyy_yangzhiquList =  (List)request.getAttribute("zhyy_yangzhiquList");
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
            <span class="icon12">智控设备设置</span>
        </h2>
        <form id="forms">
            <table class="box_tab_1" >
                <tr>
                     <td class="tab1_tit">数值通道</td>
                </tr>
                <%
                    for(int i = 1;i <= 8;i ++){
                %>
                <tr>
                    <td class="tab1_tit">通道<%=i%></td>
                </tr>
                <tr>
                    
                    <td class="tab1_tit">接入内容：</td>
                    <td><input type="text" id="ai_<%=i%>SMingcheng" maxlength="20"  /></td>
      
                    <td class="tab1_tit">储存表：</td>
                    <td>
                        <select id="ai_<%=i%>SObj">
                            <option value=""></option>
                            <option value="zhyy_rtu_data">设备状态数据</option>
                            <option value="zhyy_shuizhishuju">数值数据</option>
                        </select>
                    </td>
              
                    <td class="tab1_tit">储存字段：</td>
                    <td><input type="text" id="ai_<%=i%>SAttr" maxlength="20"  /></td>

                    <td class="tab1_tit"><b class="red mlr5">*</b>养殖区：</td>
                    <td>
                        <select id="ai_<%=i%>zhyyYangzhiquId">
                            <option value=""></option>
                        <%
                        for(Map<String,Object> zhyy_yangzhiqumap : zhyy_yangzhiquList){
                        %>
                        <option value="<%=zhyy_yangzhiqumap.get("id")%>"><%=zhyy_yangzhiqumap.get("y_bianhao")%></option>
                        <%
                        }
                        %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tab1_tit">报警设置：</td>
                    <td><input type="text" id="ai_<%=i%>Bjsz" maxlength="50"  /></td>
                    <td class="tab1_tit">报警内容：</td>
                    <td><input type="text" id="ai_<%=i%>Bjnr" maxlength="50"  /></td>
                    <td class="tab1_tit">报警等级：</td>
                    <td>
                        <select id="ai_<%=i%>Bjdj">
                            <option value=""></option>
                            <option value="1">严重</option>
                            <option value="2">较重</option>
                            <option value="3">一般</option>
                        </select>
                    </td>
                    <td class="tab1_tit">报警类型：</td>
                    <td>
                        <select id="ai_<%=i%>Bjlx">
                            <option value=""></option>
                            <option value="1">设备故障</option>
                            <option value="2">数值异常</option>
                        </select>
                    </td>
                    <td class="tab1_tit">间隔时间(分钟)：</td>
                    <td><input type="text" id="ai_<%=i%>Bjsj" maxlength="50"  /></td>
                </tr>
                <%
                    }
                %>
                <tr>
                     <td class="tab1_tit">状态通道</td>
                </tr>
                <%
                    for(int i = 1;i <= 8;i ++){
                %>
                <tr>
                    <td class="tab1_tit">通道<%=i+1%></td>
                </tr>
                <tr>
                    <td class="tab1_tit">接入内容：</td>
                    <td><input type="text" id="di_<%=i%>SMingcheng" maxlength="20"  /></td>
      
                    <td class="tab1_tit">储存表：</td>
                    <td>
                        <select id="di_<%=i%>SObj">
                            <option value=""></option>
                            <option value="zhyy_rtu_data">设备状态数据</option>
                            <option value="zhyy_shuizhishuju">数值数据</option>
                        </select>
                    </td>
              
                    <td class="tab1_tit">储存字段：</td>
                    <td><input type="text" id="di_<%=i%>SAttr" maxlength="20"  /></td>

                    <td class="tab1_tit"><b class="red mlr5">*</b>养殖区：</td>
                    <td>
                        <select id="di_<%=i%>zhyyYangzhiquId">
                            <option value=""></option>
                        <%
                        for(Map<String,Object> zhyy_yangzhiqumap : zhyy_yangzhiquList){
                        %>
                        <option value="<%=zhyy_yangzhiqumap.get("id")%>"><%=zhyy_yangzhiqumap.get("y_bianhao")%></option>
                        <%
                        }
                        %>
                        </select>
                    </td>
                    <tr>
                        <td class="tab1_tit">报警设置：</td>
                        <td><input type="text" id="di_<%=i%>Bjsz" maxlength="50"  /></td>
                        <td class="tab1_tit">报警内容：</td>
                        <td><input type="text" id="di_<%=i%>Bjnr" maxlength="50"  /></td>
                        <td class="tab1_tit">报警等级：</td>
                        <td>
                            <select id="di_<%=i%>Bjdj">
                                <option value=""></option>
                                <option value="1">严重</option>
                                <option value="2">较重</option>
                                <option value="3">一般</option>
                            </select>
                        </td>
                        <td class="tab1_tit">报警类型：</td>
                        <td>
                            <select id="di_<%=i%>Bjlx">
                                <option value=""></option>
                                <option value="1">设备故障</option>
                                <option value="2">数值异常</option>
                            </select>
                        </td>
                        <td class="tab1_tit">间隔时间(分钟)：</td>
                        <td><input type="text" id="di_<%=i%>Bjsj" maxlength="50"  /></td>
                    </tr>
                </tr> 
                <%
                    }
                %>
                <tr>
                     <td class="tab1_tit">控制通道</td>
                </tr>
                <%
                    for(int i = 1;i <= 8;i ++){
                %>
                <tr>
                    <td class="tab1_tit">通道<%=i+1%></td>
                </tr>
                <tr>
                    <td class="tab1_tit">接入内容：</td>
                    <td><input type="text" id="do_<%=i%>SMingcheng" maxlength="20"  /></td>
                    <td class="tab1_tit">储存表：</td>
                    <td>
                        <select id="do_<%=i%>SObj">
                            <option value=""></option>
                            <option value="zhyy_rtu_data">设备状态数据</option>
                            <option value="zhyy_shuizhishuju">数值数据</option>
                        </select>
                    </td>
              
                    <td class="tab1_tit">储存字段：</td>
                    <td><input type="text" id="do_<%=i%>SAttr" maxlength="20"  /></td>

                    <td class="tab1_tit"><b class="red mlr5">*</b>养殖区：</td>
                    <td>
                        <select id="do_<%=i%>zhyyYangzhiquId">
                            <option value=""></option>
                        <%
                        for(Map<String,Object> zhyy_yangzhiqumap : zhyy_yangzhiquList){
                        %>
                        <option value="<%=zhyy_yangzhiqumap.get("id")%>"><%=zhyy_yangzhiqumap.get("y_bianhao")%></option>
                        <%
                        }
                        %>
                        </select>
                    </td>
                <tr>
                    <td class="tab1_tit">报警设置：</td>
                    <td><input type="text" id="do_<%=i%>Bjsz" maxlength="50"  /></td>
                    <td class="tab1_tit">报警内容：</td>
                    <td><input type="text" id="do_<%=i%>Bjnr" maxlength="50"  /></td>
                    <td class="tab1_tit">报警等级：</td>
                    <td>
                        <select id="do_<%=i%>Bjdj">
                            <option value=""></option>
                            <option value="1">严重</option>
                            <option value="2">较重</option>
                            <option value="3">一般</option>
                        </select>
                    </td>
                    <td class="tab1_tit">报警类型：</td>
                    <td>
                        <select id="do_<%=i%>Bjlx">
                            <option value=""></option>
                            <option value="1">设备故障</option>
                            <option value="2">数值异常</option>
                        </select>
                    </td>
                    <td class="tab1_tit">间隔时间(分钟) ：</td>
                    <td><input type="text" id="do_<%=i%>Bjsj" maxlength="50"  /></td>
                </tr>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td class="tab1_btn" colspan="2">
                        <span class="mlr5" id="save"><b class="btn btn_Lg btn_yellow">保存</b></span>
                        <span class="mlr5" id="exit"><b class="btn btn_Lg btn_yellow">关闭</b></span>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
var id = "<%=id%>";
$(function () {
            JCTools.intInit($("#QQiwen"));
            if(id != ""){
               loaddata();
            }
        })
	function loaddata(){
            var url = "<%=path%>/ifs/zhyy/rtu_shezhi/get";
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
                    if(json != ""){
                        var szlist = json.szlist;
                        var bjlist = json.bjlist;
                        for(var i = 0; i < szlist.length;i ++){
                            //$("#" + json[i].s_leibie + "_" + json[i].s_tongdao + "SLeibie").val(json[i].s_leibie);
                            $("#" + szlist[i].s_leibie + "_" + szlist[i].s_tongdao + "SMingcheng").val(szlist[i].s_mingcheng);
                            $("#" + szlist[i].s_leibie + "_" + szlist[i].s_tongdao + "SAttr").val(szlist[i].s_attr);
                            $("#" + szlist[i].s_leibie + "_" + szlist[i].s_tongdao + "SObj").val(szlist[i].s_obj);
                            $("#" + szlist[i].s_leibie + "_" + szlist[i].s_tongdao + "zhyyYangzhiquId").val(szlist[i].zhyy_yangzhiqu_id);
                        }
                        for(var i = 0; i < bjlist.length;i ++){
                            //$("#" + json[i].s_leibie + "_" + json[i].s_tongdao + "SLeibie").val(json[i].s_leibie);
                            $("#" + szlist[i].s_leibie + "_" + szlist[i].s_tongdao + "Bjsz").val(bjlist[i].b_gongshi);
                            $("#" + szlist[i].s_leibie + "_" + szlist[i].s_tongdao + "Bjnr").val(bjlist[i].b_baojingneirong);
                            $("#" + szlist[i].s_leibie + "_" + szlist[i].s_tongdao + "Bjdj").val(bjlist[i].b_baojingdengji);
                            $("#" + szlist[i].s_leibie + "_" + szlist[i].s_tongdao + "Bjlx").val(bjlist[i].b_leixing);
                            $("#" + szlist[i].s_leibie + "_" + szlist[i].s_tongdao + "Bjsj").val(bjlist[i].b_jiangeshijian);
                        }
		    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
    $("#save").click(function() {
        if (!JC.validate(rule)) {
            return;
        }
	var url = "<%=path%>/ifs/zhyy/rtu_shezhi/save";
        var objs = new Array();
        var obj ;
        var bjobjs = new Array();
        var bjboj;
        var lbs = new Array("ai","di","do");
        for(var j = 0;j < lbs.length;j ++){
            for(var i = 1;i <=8;i ++){
                if($("#" + lbs[j] + "_" + i + "SMingcheng").val() != ""){
                    obj = new Object();
                    obj.s_tongdao = i;
                    obj.s_leibie = lbs[j];
                    obj.s_mingcheng = $("#" + lbs[j] + "_" + i + "SMingcheng").val();
                    obj.s_attr = $("#" + lbs[j] + "_" + i + "SAttr").val();
                    obj.s_obj = $("#" + lbs[j] + "_" + i + "SObj").val();
                    obj.zhyy_yangzhiqu_id = $("#" + lbs[j] + "_" + i + "zhyyYangzhiquId").val();
                    obj.zhyy_rtu_id = id;
                    objs.push(obj);
                    if($("#" + lbs[j] + "_" + i + "Bjsz").val() != ""){
                        bjboj = new Object();
                        bjboj.b_tongdao = i;
                        bjboj.zhyy_rtu_id = id;
                        bjboj.b_tongdaoleibie = lbs[j];
                        bjboj.b_gongshi = $("#" + lbs[j] + "_" + i + "Bjsz").val();
                        bjboj.b_baojingdengji = $("#" + lbs[j] + "_" + i + "Bjdj").val();
                        bjboj.b_baojingneirong = $("#" + lbs[j] + "_" + i + "Bjnr").val();
                        bjboj.b_leixing = $("#" + lbs[j] + "_" + i + "Bjlx").val();
                        bjboj.b_jiangeshijian = $("#" + lbs[j] + "_" + i + "Bjsj").val();
                        bjobjs.push(bjboj);
                    }
                }
            }
        }
        
        $.ajax({    
		   url:url,
		   data:{rtuid:id,arg1:JSON.stringify(objs),arg2:JSON.stringify(bjobjs)
                   },    
		   type: 'POST',
		   timeout:3000,
		   dataFilter:function(json){
		       console.log("jsonp.filter:"+json  );
		       return json;
		   },
		   success:function(json,textStatus){
			if (json == "1") {
		                alert("保存成功");
		            } else if(data == "0"){
		                alert("保存失败");
		            }
			top.win.JC.table(top.win.initOption);
                       top.win.formDialog.close();
                   },    
		   error:function(XMLHttpRequest,textStatus,errorThrown){
		       console.log("jsonp.error:"+XMLHttpRequest.returnCode);    
		   }
		});
    })
    
    $("#exit").click(function() {
        top.win.formDialog.close();
    })
    

    var rule = {
        divInfo:{
            id:"msg-error "
        },
        objInfo:{
        }
    }
</script>
</html>