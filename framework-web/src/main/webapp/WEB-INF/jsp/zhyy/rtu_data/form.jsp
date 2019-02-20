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
                    <td class="tab1_tit">系统记录时间：</td>
                    <td><input type="text" id="RSystime" maxlength="19"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">采集数据时间：</td>
                    <td><input type="text" id="RCaijishijian" maxlength="19"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="RYear" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="RMonth" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="RDay" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="ai1" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="ai2" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="ai3" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="ai4" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="ai5" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="ai6" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="ai7" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="ai8" maxlength="12"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="di1" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="di2" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="di3" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="di4" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="di5" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="di6" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="di7" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="di8" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="do1" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="do2" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="do3" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="do4" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="do5" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="do6" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="do7" maxlength="11"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">：</td>
                    <td><input type="text" id="do8" maxlength="11"  /></td>
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
            var url = "<%=path%>/ifs/zhyy/rtu_data/get";
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
				        $("#RSystime").val(json.r_systime);
				        $("#RCaijishijian").val(json.r_caijishijian);
				        $("#RYear").val(json.r_year);
				        $("#RMonth").val(json.r_month);
				        $("#RDay").val(json.r_day);
				        $("#ai1").val(json.ai_1);
				        $("#ai2").val(json.ai_2);
				        $("#ai3").val(json.ai_3);
				        $("#ai4").val(json.ai_4);
				        $("#ai5").val(json.ai_5);
				        $("#ai6").val(json.ai_6);
				        $("#ai7").val(json.ai_7);
				        $("#ai8").val(json.ai_8);
				        $("#di1").val(json.di_1);
				        $("#di2").val(json.di_2);
				        $("#di3").val(json.di_3);
				        $("#di4").val(json.di_4);
				        $("#di5").val(json.di_5);
				        $("#di6").val(json.di_6);
				        $("#di7").val(json.di_7);
				        $("#di8").val(json.di_8);
				        $("#do1").val(json.do_1);
				        $("#do2").val(json.do_2);
				        $("#do3").val(json.do_3);
				        $("#do4").val(json.do_4);
				        $("#do5").val(json.do_5);
				        $("#do6").val(json.do_6);
				        $("#do7").val(json.do_7);
				        $("#do8").val(json.do_8);
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
	var url = "<%=path%>/ifs/zhyy/rtu_data/save";
        var obj = {};
        if(id != ""){
                obj.id = id;
            }
        obj.r_systime = $("#RSystime").val();
        obj.r_caijishijian = $("#RCaijishijian").val();
        obj.r_year = $("#RYear").val();
        obj.r_month = $("#RMonth").val();
        obj.r_day = $("#RDay").val();
        obj.ai_1 = $("#ai1").val();
        obj.ai_2 = $("#ai2").val();
        obj.ai_3 = $("#ai3").val();
        obj.ai_4 = $("#ai4").val();
        obj.ai_5 = $("#ai5").val();
        obj.ai_6 = $("#ai6").val();
        obj.ai_7 = $("#ai7").val();
        obj.ai_8 = $("#ai8").val();
        obj.di_1 = $("#di1").val();
        obj.di_2 = $("#di2").val();
        obj.di_3 = $("#di3").val();
        obj.di_4 = $("#di4").val();
        obj.di_5 = $("#di5").val();
        obj.di_6 = $("#di6").val();
        obj.di_7 = $("#di7").val();
        obj.di_8 = $("#di8").val();
        obj.do_1 = $("#do1").val();
        obj.do_2 = $("#do2").val();
        obj.do_3 = $("#do3").val();
        obj.do_4 = $("#do4").val();
        obj.do_5 = $("#do5").val();
        obj.do_6 = $("#do6").val();
        obj.do_7 = $("#do7").val();
        obj.do_8 = $("#do8").val();
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