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
    List<Map<String,Object>> zhyy_shebeiList =  (List)request.getAttribute("zhyy_shebeiList");
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
            <span class="icon12">编辑**</span>
        </h2>
        <form id="forms">
            <table class="box_tab_1" >
                <tr>
                    <td class="tab1_tit">气温：</td>
                    <td><input type="text" id="QQiwen" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">风向：</td>
                    <td><input type="text" id="QFengxiang" maxlength="10"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">风向code：</td>
                    <td><input type="text" id="QFengxiangCode" maxlength="6"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">风力：</td>
                    <td><input type="text" id="QFengli" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">天气：</td>
                    <td><input type="text" id="QTianqi" maxlength="10"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">日期：</td>
                    <td><input type="text" id="QSystime" maxlength="19"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="QHour" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">湿度：</td>
                    <td><input type="text" id="QShidu" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="QYear" maxlength="10"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="QMonth" maxlength="10"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="QDay" maxlength="10"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">大气压：</td>
                    <td><input type="text" id="QQiya" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">雨量：</td>
                    <td><input type="text" id="QYuliang" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>设备id：</td>
                    <td>
                        <select id="zhyyShebeiId">
                        <%
                        for(Map<String,Object> zhyy_shebeimap : zhyy_shebeiList){
                        %>
                        <option value="<%=zhyy_shebeimap.get("id")%>"><%=zhyy_shebeimap.get("_name")%></option>
                        <%
                        }
                        %>
                        </select>
                    </td>
                </tr> 
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
            alert("load");
            var url = "<%=path%>/ifs/zhyy/qixiangshuju/get";
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
			id=json.id;
				        $("#QQiwen").val(json.q_qiwen);
				        $("#QFengxiang").val(json.q_fengxiang);
				        $("#QFengxiangCode").val(json.q_fengxiang_code);
				        $("#QFengli").val(json.q_fengli);
				        $("#QTianqi").val(json.q_tianqi);
				        $("#QSystime").val(json.q_systime);
				        $("#QHour").val(json.q_hour);
				        $("#QShidu").val(json.q_shidu);
				        $("#QYear").val(json.q_year);
				        $("#QMonth").val(json.q_month);
				        $("#QDay").val(json.q_day);
				        $("#QQiya").val(json.q_qiya);
				        $("#QYuliang").val(json.q_yuliang);
								        $("#zhyyShebeiId").val(json.zhyy_shebei_id);
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
	var url = "<%=path%>/ifs/zhyy/qixiangshuju/save";
        var obj = {};
        if(id != ""){
                obj.id = id;
            }
        obj.q_qiwen = $("#QQiwen").val();
        obj.q_fengxiang = $("#QFengxiang").val();
        obj.q_fengxiang_code = $("#QFengxiangCode").val();
        obj.q_fengli = $("#QFengli").val();
        obj.q_tianqi = $("#QTianqi").val();
        obj.q_systime = $("#QSystime").val();
        obj.q_hour = $("#QHour").val();
        obj.q_shidu = $("#QShidu").val();
        obj.q_year = $("#QYear").val();
        obj.q_month = $("#QMonth").val();
        obj.q_day = $("#QDay").val();
        obj.q_qiya = $("#QQiya").val();
        obj.q_yuliang = $("#QYuliang").val();
        obj.zhyy_shebei_id = $("#zhyyShebeiId").val();
        $.ajax({    
		   url:url,
		   data:{arg:JSON.stringify(obj)
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