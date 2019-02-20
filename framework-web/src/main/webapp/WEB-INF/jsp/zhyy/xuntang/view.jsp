<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String cjriqi = request.getAttribute("cjriqi").toString();
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
                    <span class="icon12">添加寻塘记录</span>
                </h2>
                <form id="forms">
                    <table class="box_tab_1" >
                        <tr>
                            <td class="tab1_tit">调查日期：</td>
                            <td>
                                <input type="text" id="XCaijiriqi" readonly="readonly" value="<%=cjriqi%>" />
                            </td>
                        </tr> 
                    </table>
                    <h2 class="mb10">
                        <span class="icon12">养殖对象记录</span>
                    </h2>
                    <table id="tab_dianwei" class="box_tab_1" >
                    </table>
                            
                    <h2 class="mb10">
                        <span class="icon12">设备状况记录</span>
                    </h2>
                    <table class="box_tab_1">
                        <tr>
                            <td class="tab1_tit">设备状况：</td>
                            <td>
                                <input disabled="disabled" onclick="shebeizhuangtai(0)" name="x_shebei_flag" type="radio" value="0" checked="checked" />正常
                                <input disabled="disabled" onclick="shebeizhuangtai(1)" name="x_shebei_flag" type="radio" value="1" />异常
                            </td>
                        </tr> 
                    </table>
                    <table class="box_tab_1" id="tab_shebei">
                    </table>
                    <h2 class="mb10">
                        <span class="icon12">灾害隐患记录</span>
                    </h2>
                    <table class="box_tab_1" >
                        <tr>
                            <td class="tab1_tit">灾害隐患：</td>
                            <td>
                                <input disabled="disabled"  onclick="zaihaizhuangtai(0)" name="x_zaihai_flag" type="radio" value="0" checked="checked" />无
                                <input disabled="disabled"  onclick="zaihaizhuangtai(1)" name="x_zaihai_flag" type="radio" value="1" />有
                            </td>
                        </tr> 
                    </table>
                    <table class="box_tab_1" id="tab_zaihai">
                    </table>
                </form>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        var dianweistr = new Array("A","B","C");
        var hsggstr = new Array("大","中","小");
        var hsfbstr = new Array("无","少","中","多");
        var dianweinum = 3;//采集点位  A B C 3个点  TODO:之后动态获取
        var yangzhilist = new Array(dianweinum);//养殖数据列表 分别保存a b c三个点位数据
        var nowdianwei = parseInt($("#XCaijidian").val());//当前点位
        
        var yzq_num = 22;//养殖池数  TODO:之后动态获取
        var sbyc_num = 0;//设备异常条目数  每添加一次 +1
        var zhyc_num = 0;//灾害异常条目数  每添加一次 +1
        var ycoptionstr = new Array();//异常情况类型 0 设备异常 1 灾害异常 
        ycoptionstr[0] = "<option value=\"-1\">请选择</option><option value=\"1\">防逃网</option><option value=\"2\">进水闸门</option><option value=\"3\">排水闸门</option><option value=\"4\">养水机</option><option value=\"5\">视频监控</option><option value=\"6\">坝体</option>";
        ycoptionstr[1] = "<option value=\"-1\">请选择</option><option value=\"1\">漂参</option><option value=\"2\">化皮</option><option value=\"3\">飞鸟</option><option value=\"4\">敌害</option><option value=\"5\">杂草</option><option value=\"6\">偷盗</option><option value=\"7\">水色</option>";

        var zyqstr = "";//养殖区checkbox字符串
        var yzqDialog;//养殖区弹出窗口
        
        var url = "<%=path%>/ifs/zhyy/xuntang/add";
        var yangzhi_url = "<%=path%>/ifs/zhyy/xuntang/getYangzhi";
        var yichang_url = "<%=path%>/ifs/zhyy/xuntang/getYichang";

        $(function () {
            JCTools.intInit($("#XYzwHssl"));
            laydate.render({
                elem: '#XCaijiriqi' //指定元素
                ,min: -7
                ,max: 0
                ,done: function(value, date){
                    loaddata();
                }
            });
            //初始化点位数据集合
            for (var i = 0; i < dianweinum; i++) {
                yangzhilist[i] = new Object();
            }
            //隐藏设备异常记录表单
            $("#tab_shebei").hide();
            //隐藏灾害记录表单
            $("#tab_zaihai").hide();
            //初始化养殖区checkbox
            for (var i = 1; i <= yzq_num; i++) {

                if (i < 10) {
                    zyqstr += "<input name=\"yzqcheck\" type=\"checkbox\" value=\"" + i + "\">0" + i + "号圈&nbsp;&nbsp;";
                } else {
                    zyqstr += "<input name=\"yzqcheck\" type=\"checkbox\" value=\"" + i + "\">" + i + "号圈&nbsp;&nbsp;";
                }
                if (i % 8 == 0) {
                    zyqstr += "<br/>";
                }
            }
            loaddata();
        })
        
        //初始化表单
        function clearForm(){
            $("#tab_dianwei").empty();
            
            sbyc_num = 0;
            //$("input[name='x_shebei_flag'][value='1']").attr("checked",false);
            $("input[name='x_shebei_flag'][value='0']").prop("checked",true);
            $("#tab_shebei").empty();
            $("#tab_shebei").hide();
            
            zhyc_num = 0;
            //$("input[name='x_zaihai_flag'][value='1']").attr("checked",false);
            $("input[name='x_zaihai_flag'][value='0']").prop("checked",true);
            $("#tab_zaihai").empty();
            $("#tab_zaihai").hide();
        }
        //加载数据
        function loaddata(){
            clearForm();
            $.ajax({    
		   url:yangzhi_url,
		   data:{cjriqi:$("#XCaijiriqi").val()
                   },    
		   type: 'POST',
		   timeout:3000,
		   dataFilter:function(json){
		       console.log("jsonp.filter:"+json  );
		       return json;
		   },
		   success:function(json,textStatus){
                       //alert("json"+json);
                       var tabstr = $("#tab_dianwei").html();
                       
                       for(var i = 0;i < json.length;i ++){
                           var hsggvalue = "";
                           var hsfbvalue = "";
                           if(json[i].x_hsgg != "-1"){
                               hsggvalue = hsggstr[json[i].x_hsgg - 1];
                           }
                           if(json[i].x_hsfb != "-1"){
                               hsfbvalue = hsfbstr[json[i].x_hsfb - 1];
                           }
                           
                            var formstr = "<tr><td class=\"tab1_tit\">点位：</td><td>"+dianweistr[json[i].x_dianwei]+"</td></tr><tr>"+
                                "<td class=\"tab1_tit\">海参数量：</td>"+
                                "<td>"+json[i].x_hssl+"头</td>"+
                            "</tr> "+
                            "<tr>"+
                                "<td class=\"tab1_tit\">海参规格：</td>"+
                                "<td>" + hsggvalue + "</td>"+
                            "</tr> "+
                            "<tr>"+
                                "<td class=\"tab1_tit\">海参粪便：</td>"+
                                "<td>"+ hsfbvalue +"</td>"+
                            "</tr>"
                            $("#tab_dianwei").append(formstr);
                       }
                   },
		   error:function(XMLHttpRequest,textStatus,errorThrown){
		       console.log("jsonp.error:"+XMLHttpRequest.returnCode);    
		   }
		});
                $.ajax({    
		   url:yichang_url,
		   data:{cjriqi:$("#XCaijiriqi").val()
                   },    
		   type: 'POST',
		   timeout:3000,
		   dataFilter:function(json){
		       console.log("jsonp.filter:"+json  );
		       return json;
		   },
		   success:function(json,textStatus){
                       //alert("json"+json);
                       var sbnum = 0,zhnum = 0;
                       
                       for(var i = 0;i < json.length;i ++){
                           if(json[i].x_leixing == 1){
                               if(sbnum == 0){
                                   //$("input[name='x_shebei_flag']).get(1).checked=true;
                                   //$("input[name='x_shebei_flag'][value='0']").attr("checked",false);
                                   $("input[name='x_shebei_flag'][value='1']").prop("checked",true);
                                   $("#tab_shebei").show();
                               }
                               addSbYichang();
                               sbnum ++;
                               $("#yc_sb_sel"+sbnum).val(json[i].x_leibie);
                               $("#input_sb_yzq"+sbnum).val(json[i].zhyy_yangzhiqu_id);
                           }else{
                               if(zhnum == 0){
                                   $("input[name='x_zaihai_flag'][value='1']").prop("checked",true);
                                   $("#tab_zaihai").show();
                               }
                               addZhYichang();
                               zhnum ++;
                               $("#yc_zh_sel"+zhnum).val(json[i].x_leibie);
                               $("#input_zh_yzq"+zhnum).val(json[i].zhyy_yangzhiqu_id);
                           }
                       }
                   },
		   error:function(XMLHttpRequest,textStatus,errorThrown){
		       console.log("jsonp.error:"+XMLHttpRequest.returnCode);    
		   }
		});
        }
        //设备异常状态变更
        function shebeizhuangtai(flag) {
            if (flag == "0") {
                $("#tab_shebei").hide();
            } else {
                $("#tab_shebei").show();
                if(sbyc_num == 0){
                    addSbYichang();
                }
            }
        }
        //添加设备异常记录
        function addSbYichang() {
            sbyc_num++;
            var formstr = "<tr><td class=\"tab1_tit\">" +
                    "<select disabled=\"disabled\" id=\"yc_sb_sel" + sbyc_num + "\">" + ycoptionstr[0] + "</select>" +
                    "</td><td><input readonly=\"readonly\" id=\"input_sb_yzq" + sbyc_num + "\" type=\"text\" /></td></tr>";
            $("#tab_shebei").append(formstr);
        }
        //灾害状态变更
        function zaihaizhuangtai(flag) {
            if (flag == "0") {
                $("#tab_zaihai").hide();
            } else {
                $("#tab_zaihai").show();
                if(zhyc_num == 0){
                    addZhYichang();
                }
            }
        }
        //添加灾害记录
        function addZhYichang() {
            zhyc_num++;
            var formstr = "<tr><td class=\"tab1_tit\">" +
                    "<select disabled=\"disabled\" id=\"yc_zh_sel" + zhyc_num + "\">" + ycoptionstr[1] + "</select>" +
                    "</td><td><input readonly=\"readonly\" id=\"input_zh_yzq" + zhyc_num + "\" type=\"text\" /></td></tr>";
            $("#tab_zaihai").append(formstr);
        }
        $("#exit").click(function() {
            top.win.addDialog.close();
        })
    </script>
</html>