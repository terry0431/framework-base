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
    List<Map<String,Object>> zhyy_yangzhichangList =  (List)request.getAttribute("zhyy_yangzhichangList");
    List<Map<String,Object>> zhyy_yangzhiquList =  (List)request.getAttribute("zhyy_yangzhiquList");
    List<Map<String,Object>> zhyy_rtuList =  (List)request.getAttribute("zhyy_rtuList");
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
                    <td class="tab1_tit">水温（℃）：</td>
                    <td><input type="text" id="SShuiwen" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">盐度：</td>
                    <td><input type="text" id="SYandu" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">PH：</td>
                    <td><input type="text" id="SPh" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">DO2（mg/L）：</td>
                    <td><input type="text" id="SD02" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">浊度：</td>
                    <td><input type="text" id="SZhuodu" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">水面情况：</td>
                    <td><input type="text" id="SShuimian" maxlength="10"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">水深（cm）：</td>
                    <td><input type="text" id="SShuishen" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="SYear" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="SMonth" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="SDay" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="SSystime" maxlength="19"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">层数（1-5层 ，0 平均数据）：</td>
                    <td><input type="text" id="SCengshu" maxlength="10"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="SCaijishijian" maxlength="19"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="SFlag" maxlength="1"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>：</td>
                    <td>
                        <select id="zhyyYangzhichangId">
                        <%
                        for(Map<String,Object> zhyy_yangzhichangmap : zhyy_yangzhichangList){
                        %>
                        <option value="<%=zhyy_yangzhichangmap.get("id")%>"><%=zhyy_yangzhichangmap.get("_name")%></option>
                        <%
                        }
                        %>
                        </select>
                    </td>
                </tr> 
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>：</td>
                    <td>
                        <select id="zhyyYangzhiquId">
                        <%
                        for(Map<String,Object> zhyy_yangzhiqumap : zhyy_yangzhiquList){
                        %>
                        <option value="<%=zhyy_yangzhiqumap.get("id")%>"><%=zhyy_yangzhiqumap.get("_name")%></option>
                        <%
                        }
                        %>
                        </select>
                    </td>
                </tr> 
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>：</td>
                    <td>
                        <select id="zhyyRtuId">
                        <%
                        for(Map<String,Object> zhyy_rtumap : zhyy_rtuList){
                        %>
                        <option value="<%=zhyy_rtumap.get("id")%>"><%=zhyy_rtumap.get("_name")%></option>
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
            var url = "<%=path%>/ifs/zhyy/shuizhishuju/get";
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
				        $("#SShuiwen").val(json.s_shuiwen);
				        $("#SYandu").val(json.s_yandu);
				        $("#SPh").val(json.s_ph);
				        $("#SD02").val(json.s_d02);
				        $("#SZhuodu").val(json.s_zhuodu);
				        $("#SShuimian").val(json.s_shuimian);
				        $("#SShuishen").val(json.s_shuishen);
				        $("#SYear").val(json.s_year);
				        $("#SMonth").val(json.s_month);
				        $("#SDay").val(json.s_day);
				        $("#SSystime").val(json.s_systime);
				        $("#SCengshu").val(json.s_cengshu);
				        $("#SCaijishijian").val(json.s_caijishijian);
				        $("#SFlag").val(json.s_flag);
								        $("#zhyyYangzhichangId").val(json.zhyy_yangzhichang_id);
				        $("#zhyyYangzhiquId").val(json.zhyy_yangzhiqu_id);
				        $("#zhyyRtuId").val(json.zhyy_rtu_id);
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
	var url = "<%=path%>/ifs/zhyy/shuizhishuju/save";
        var obj = {};
        if(id != ""){
                obj.id = id;
            }
        obj.s_shuiwen = $("#SShuiwen").val();
        obj.s_yandu = $("#SYandu").val();
        obj.s_ph = $("#SPh").val();
        obj.s_d02 = $("#SD02").val();
        obj.s_zhuodu = $("#SZhuodu").val();
        obj.s_shuimian = $("#SShuimian").val();
        obj.s_shuishen = $("#SShuishen").val();
        obj.s_year = $("#SYear").val();
        obj.s_month = $("#SMonth").val();
        obj.s_day = $("#SDay").val();
        obj.s_systime = $("#SSystime").val();
        obj.s_cengshu = $("#SCengshu").val();
        obj.s_caijishijian = $("#SCaijishijian").val();
        obj.s_flag = $("#SFlag").val();
        obj.zhyy_yangzhichang_id = $("#zhyyYangzhichangId").val();
        obj.zhyy_yangzhiqu_id = $("#zhyyYangzhiquId").val();
        obj.zhyy_rtu_id = $("#zhyyRtuId").val();
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