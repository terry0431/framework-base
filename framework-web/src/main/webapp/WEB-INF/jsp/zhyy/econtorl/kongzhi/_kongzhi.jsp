<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        
        <%@ include file="../../../include/commontop.jsp"%>
    </head>
    <body>
        <div class="box_Bomb">
            <div class="box_b_t">
                <h2 class="mb10">
                    <span class="icon12">输水控制</span>
                </h2>
                <form id="forms">
                    <table class="box_tab_1" >
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_1" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_1"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_2" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_2"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_3" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_3"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_4" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_4"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_5" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_5"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_6" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_6"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_7" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_7"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_8" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_8"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_9" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_9"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_10" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_10"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_11" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_11"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_tit"><input type="text" id="chitang_12" maxlength="19"  />号池塘</td>
                            <td id="XSystime"><input type="text" id="shijian_12"  />分钟</td>
                        </tr> 
                        <tr>
                            <td class="tab1_btn" colspan="2">
                                <span class="mlr5" id="save"><b class="btn btn_Lg btn_yellow">开始执行</b></span>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </body>
    <script type="text/javascript">

        $(function () {

            resetTime(500);
        })
        //获取全部开关状态
//        function getFlag() {
//            var url = "<%=path%>/ifs/econtorl/kongzhi/getKgFlag";
//            $.ajax({
//                url: url,
//                data: {id:
//                },
//                type: 'POST',
//                timeout: 3000,
//                dataFilter: function (json) {
//                    console.log("jsonp.filter:" + json);
//                    return json;
//                },
//                success: function (json, textStatus) {
//                    if (json != "") {
//
//
//                    }
//                },
//                error: function (XMLHttpRequest, textStatus, errorThrown) {
//                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
//                }
//            });
//        }
        var nowtime;
        //开始执行
        $("#save").click(function () {
//            if (!JC.validate(rule)) {
//                return;
//            }
//            nowtime = new Date();
//
//            $.ajax({
//                url: url,
//                data: {arg: JSON.stringify(obj)
//                },
//                type: 'POST',
//                timeout: 3000,
//                dataFilter: function (json) {
//                    console.log("jsonp.filter:" + json);
//                    return json;
//                },
//                success: function (json, textStatus) {
//                    if (json == "1") {
//                        alert("保存成功");
//                    } else if (data == "0") {
//                        alert("保存失败");
//                    }
//                    top.win.JC.table(top.win.initOption);
//                    top.win.formDialog.close();
//                },
//                error: function (XMLHttpRequest, textStatus, errorThrown) {
//                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
//                }
//            });
        })
//单纯分钟和秒倒计时
        function resetTime(time) {
            var timer = null;
            var t = time;
            var m = 0;
            var s = 0;
            m = Math.floor(t / 60 % 60);
            m < 10 && (m = '0' + m);
            s = Math.floor(t % 60);
            function countDown() {
                s--;
                s < 10 && (s = '0' + s);
                if (s.length >= 3) {
                    s = 59;
                    m = "0" + (Number(m) - 1);
                }
                if (m.length >= 3) {
                    m = '00';
                    s = '00';
                    clearInterval(timer);
                }
                console.log(m + "分钟" + s + "秒");
            }
            timer = setInterval(countDown, 1000);
        }
    </script>
</html>