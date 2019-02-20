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
            <span class="icon12">养殖区</span>
        </h2>
        <form id="forms">
            <table class="box_tab_1" >
                <tr>
                    <td class="tab1_tit"><b class="red mlr5">*</b>养殖池：</td>
                    <td>
                        <select id="zhyyYangzhichangId">
                        <%
                        for(Map<String,Object> zhyy_yangzhichangmap : zhyy_yangzhichangList){
                        %>
                        <option value="<%=zhyy_yangzhichangmap.get("id")%>"><%=zhyy_yangzhichangmap.get("y_mingcheng")%></option>
                        <%
                        }
                        %>
                        </select>
                    </td>
                </tr> 
                <tr>
                    <td class="tab1_tit">区号：</td>
                    <td><input type="text" id="YNum" maxlength="10"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">坐标x：</td>
                    <td><input type="text" id="YX" maxlength="50"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">坐标y：</td>
                    <td><input type="text" id="YY" maxlength="50"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">编号：</td>
                    <td><input type="text" id="YBianhao" maxlength="10"  /></td>
                </tr> 
                <tr>
                    <td class="tab1_tit">标准水深：</td>
                    <td><input type="text" id="YShuishen" maxlength="12"  /></td>
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
            var url = "<%=path%>/ifs/zhyy/yangzhiqu/get";
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
				        $("#YNum").val(json.y_num);
				        $("#YX").val(json.y_x);
				        $("#YY").val(json.y_y);
				        $("#YBianhao").val(json.y_bianhao);
				        $("#YShuishen").val(json.y_shuishen);
								        $("#zhyyYangzhichangId").val(json.zhyy_yangzhichang_id);
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
	var url = "<%=path%>/ifs/zhyy/yangzhiqu/save";
        var obj = {};
        if(id != ""){
                obj.id = id;
            }
        obj.y_num = $("#YNum").val();
        obj.y_x = $("#YX").val();
        obj.y_y = $("#YY").val();
        obj.y_bianhao = $("#YBianhao").val();
        obj.y_shuishen = $("#YShuishen").val();
        obj.zhyy_yangzhichang_id = $("#zhyyYangzhichangId").val();
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