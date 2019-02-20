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
                    <table class="box_tab_1" >
                        <tr>
                            <td class="tab1_tit">调查点位：</td>
                            <td><select style="width: 145px" id="XCaijidian" onchange="dianweichange()">
                                    <option value="0">A点</option>
                                    <option value="1">B点</option>
                                    <option value="2">C点</option>
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">海参数量：</td>
                            <td><input type="text" id="XYzwHssl" maxlength="3" itype="int" max="50" min="0" />头</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">海参规格：</td>
                            <td>
                                <select style="width: 145px" id="XYzwHsgg">
                                    <option value="-1">请选择</option>
                                    <option value="1">大</option>
                                    <option value="2">中</option>
                                    <option value="3">小</option>
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit">海参粪便：</td>
                            <td>
                                <select style="width: 145px" id="XYzwHsfb">
                                    <option value="-1">请选择</option>
                                    <option value="1">无</option>
                                    <option value="2">少</option>
                                    <option value="3">中</option>
                                    <option value="4">多</option>
                                </select>
                            </td>
                        </tr> 
                    </table>
                    <h2 class="mb10">
                        <span class="icon12">设备状况记录</span>
                    </h2>
                    <table class="box_tab_1">
                        <tr>
                            <td class="tab1_tit">设备状况：</td>
                            <td>
                                <input onclick="shebeizhuangtai(0)" name="x_shebei_flag" type="radio" value="0" checked="checked" />正常
                                <input onclick="shebeizhuangtai(1)" name="x_shebei_flag" type="radio" value="1" />异常
                            </td>
                        </tr> 
                    </table>
                    <table class="box_tab_1" id="tab_shebei">
                        <tr>
                            <td class="tab1_tit"><span class="mlr5" onclick="addSbYichang()"><b class="btn btn_Lg btn_yellow">添加</b></span></td>
                            <td></td>
                        </tr>
                    </table>
                    <h2 class="mb10">
                        <span class="icon12">灾害隐患记录</span>
                    </h2>
                    <table class="box_tab_1" >
                        <tr>
                            <td class="tab1_tit">灾害隐患：</td>
                            <td>
                                <input onclick="zaihaizhuangtai(0)" name="x_zaihai_flag" type="radio" value="0" checked="checked" />无
                                <input onclick="zaihaizhuangtai(1)" name="x_zaihai_flag" type="radio" value="1" />有
                            </td>
                        </tr> 
                    </table>
                    <table class="box_tab_1" id="tab_zaihai">
                        <tr>
                            <td class="tab1_tit"><span class="mlr5" onclick="addZhYichang()"><b class="btn btn_Lg btn_yellow">添加</b></span></td>
                            <td></td>
                        </tr>
                    </table>
                    <table class="box_tab_1" >
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
            
            $("#XCaijidian").val("0");
            $("#XYzwHssl").val("");
            $("#XYzwHsgg").val("-1");
            $("#XYzwHsfb").val("-1");
            for (var i = 0; i < dianweinum; i++) {
                yangzhilist[i] = new Object();
            }
            
            sbyc_num = 0;
            //$("input[name='x_shebei_flag'][value='1']").attr("checked",false);
            $("input[name='x_shebei_flag'][value='0']").prop("checked",true);
            $("#tab_shebei").empty();
            $("#tab_shebei").append("<tr><td class=\"tab1_tit\"><span class=\"mlr5\" onclick=\"addSbYichang()\"><b class=\"btn btn_Lg btn_yellow\">添加</b></span></td></tr>");
            $("#tab_shebei").hide();
            
            zhyc_num = 0;
            //$("input[name='x_zaihai_flag'][value='1']").attr("checked",false);
            $("input[name='x_zaihai_flag'][value='0']").prop("checked",true);
            $("#tab_zaihai").empty();
            $("#tab_zaihai").append("<tr><td class=\"tab1_tit\"><span class=\"mlr5\" onclick=\"addZhYichang()\"><b class=\"btn btn_Lg btn_yellow\">添加</b></span></td></tr>");
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
                       for(var i = 0;i < json.length;i ++){
                           if(json[i].x_dianwei == 0){
                               //表单加载
                               $("#XYzwHssl").val(json[i].x_hssl);
                               $("#XYzwHsgg").val(json[i].x_hsgg);
                               $("#XYzwHsfb").val(json[i].x_hsfb);
                           }
                           yangzhilist[json[i].x_dianwei].x_hssl = json[i].x_hssl;
                           yangzhilist[json[i].x_dianwei].x_hsgg = json[i].x_hsgg;
                           yangzhilist[json[i].x_dianwei].x_hsfb = json[i].x_hsfb;
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
        //点位变更
        function dianweichange() {
            yangzhilist[nowdianwei].x_hssl = $("#XYzwHssl").val();
            yangzhilist[nowdianwei].x_hsgg = $("#XYzwHsgg").val();
            yangzhilist[nowdianwei].x_hsfb = $("#XYzwHsfb").val();
            yangzhilist[nowdianwei].x_dianwei = nowdianwei;
            var newdianwei = parseInt($("#XCaijidian").val());
            if (yangzhilist[newdianwei].x_hssl != undefined) {
                $("#XYzwHssl").val(yangzhilist[newdianwei].x_hssl);
            } else {
                $("#XYzwHssl").val("");
            }

            if (yangzhilist[newdianwei].x_hsgg != undefined) {
                $("#XYzwHsgg").val(yangzhilist[newdianwei].x_hsgg);
            } else {
                $("#XYzwHsgg").val("-1");
            }

            if (yangzhilist[newdianwei].x_hsfb != undefined) {
                $("#XYzwHsfb").val(yangzhilist[newdianwei].x_hsfb);
            } else {
                $("#XYzwHsfb").val("-1");
            }
            nowdianwei = newdianwei;
        }
        
        //选择养殖区
        function check_yzq(num,type) {
                yzqDialog = new $.dialog({
                    id: 'yzqDialogFrame',
                    title: "选择养殖圈",
                    height: 100,
                    width: 300,
                    lock:true,
                    zIndex:9999,
                    content: zyqstr,
                    init: function(){
                        //alert('正在执行初始化函数，此时你可看到窗口内容没有发生变化');
                        //this.content('我是初始化函数执行后的窗口中的内容');
                        var str = $("#input_" + type + "_yzq" + num).val();
                        if(str != ""){
                            var strs = str.split(",");
                            for(var i = 0;i < strs.length;i ++){
                                $("input[name='yzqcheck'][value='"+strs[i]+"']",window.parent.document).prop("checked",true);
                            }
                        }
                    },
                    ok: function () {
                        loadCheckValue(num,type);
                        cancel: true
                    },
                    okVal: '确定'
                });
        }
        //读取选中的养殖区  num 条目序列，type 异常类别
        function loadCheckValue(num,type) {
            // this.title('3秒后自动关闭').time(3);
            var tmpstr = "";
            //alert($('[name=yzqcheck]',window.parent.document).length);
            $('[name=yzqcheck]:checkbox:checked',window.parent.document).each(function () {
                if (tmpstr != "") {
                    tmpstr += ",";
                }
                tmpstr += $(this).val();
            });
            $("#input_"+type+"_yzq" + num).val(tmpstr);
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
                    "<select id=\"yc_sb_sel" + sbyc_num + "\">" + ycoptionstr[0] + "</select>" +
                    "</td><td><input id=\"input_sb_yzq" + sbyc_num + "\" type=\"text\" /><input type=\"button\" value=\"选择养殖圈\" onclick=\"check_yzq(" + sbyc_num + ",'sb')\" /></td></tr>";
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
                    "<select id=\"yc_zh_sel" + zhyc_num + "\">" + ycoptionstr[1] + "</select>" +
                    "</td><td><input id=\"input_zh_yzq" + zhyc_num + "\" type=\"text\" /><input type=\"button\" value=\"选择养殖圈\" onclick=\"check_yzq(" + zhyc_num + ",'zh')\" /></td></tr>";
            $("#tab_zaihai").append(formstr);
        }

        $("#save").click(function () {
            yangzhilist[nowdianwei].x_hssl = $("#XYzwHssl").val();
            yangzhilist[nowdianwei].x_hsgg = $("#XYzwHsgg").val();
            yangzhilist[nowdianwei].x_hsfb = $("#XYzwHsfb").val();
            yangzhilist[nowdianwei].x_dianwei = nowdianwei;
            //yangzhilist
            var sbyclist ;
            var zhyclist;
            
            if( $("input[name='x_shebei_flag']:checked").val() == 1){
                sbyclist = new Array(sbyc_num);
                var datanum = 0;
                for(var i = 0;i < sbyc_num;i ++){
                    if($("#yc_sb_sel" + (i + 1)).val() != "-1" && $("#input_sb_yzq" + (i + 1)).val() != ""){
                        sbyclist[datanum] = new Object();
                        sbyclist[datanum].x_leixing = "1";
                        sbyclist[datanum].x_leibie = $("#yc_sb_sel" + (i + 1)).val();
                        sbyclist[datanum].zhyy_yangzhiqu_id = $("#input_sb_yzq" + (i + 1)).val();
                        datanum ++;
                    }
                }
            }
            if( $("input[name='x_zaihai_flag']:checked").val() == 1){
                zhyclist = new Array(zhyc_num);
                var datanum = 0;
                for(var i = 0;i < zhyc_num;i ++){
                    if($("#yc_zh_sel" + (i + 1)).val() != "-1" && $("#input_zh_yzq" + (i + 1)).val() != ""){
                        zhyclist[datanum] = new Object();
                        zhyclist[datanum].x_leixing = "2";
                        zhyclist[datanum].x_leibie = $("#yc_zh_sel" + (i + 1)).val();
                        zhyclist[datanum].zhyy_yangzhiqu_id = $("#input_zh_yzq" + (i + 1)).val();
                    }
                }
            }
//            alert(JSON.stringify(yangzhilist));
//            alert(JSON.stringify(sbyclist));
//            alert(JSON.stringify(zhyclist));
            $.ajax({    
		   url:url,
		   data:{caijiriqi:$("#XCaijiriqi").val()
                       ,yzlist:JSON.stringify(yangzhilist)
                       ,sblist:JSON.stringify(sbyclist)
                       ,zhlist:JSON.stringify(zhyclist)
                   },    
//                   data:{caijiriqi:$("#XCaijiriqi").val()
//                       ,yzlist:yangzhilist
//                       ,sblist:sbyclist
//                       ,zhlist:zhyclist
//                   },  
		   type: 'POST',
		   timeout:3000,
		   dataFilter:function(json){
		       console.log("jsonp.filter:"+json  );
		       return json;
		   },
		   success:function(json,textStatus){
                       alert("保存成功");
                       top.win.addDialog.close();
                   },    
		   error:function(XMLHttpRequest,textStatus,errorThrown){
		       console.log("jsonp.error:"+XMLHttpRequest.returnCode);    
		   }
		});
        })
        $("#exit").click(function() {
            top.win.addDialog.close();
        })
    </script>
</html>