<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
    <head>
        <%
            //response.addHeader("P3P", "CP=CAO PSA OUR");
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
            String userId = session.getAttribute("userId").toString();
        %>
        <title>在线监测</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="apple-mobile-web-app-capable" content="yes"/>
        <link href="<%=path%>/common/zhyy/main/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
        <link href="<%=path%>/common/zhyy/main/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
        <link href="<%=path%>/common/zhyy/main/css/styles.css" type="text/css" rel="stylesheet"/>
        <link href="<%=path%>/common/zhyy/main/css/main.css" type="text/css" rel="stylesheet"/>
        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
        <script type="text/javascript" src="<%=path%>/js/lhgdialog/lhgdialog.js"></script>
        
        <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
        <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
        <script src="https://img.hcharts.cn/highcharts/highcharts-more.js"></script>
        <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
        <script src="<%=path%>/js/DateUtil.js"></script>
        <script src="<%=path%>/js/config.js"></script>

    </head>
    <body>
        <div id="base" class="">

            <!-- Unnamed (矩形) -->
            <div id="u687" class="ax_default sticky_2">
                <div id="u687_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u688" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- Unnamed (矩形) -->
            <div id="u689" class="ax_default label">
                <div id="u689_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u690" class="text">
                    <p><span id="time_qxzx">气象在线</span></p>
                </div>
            </div>

            <!-- data_qx_fl (矩形) -->
            <div id="u691" class="ax_default label" data-label="data_qx_fl">
                <div id="u691_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u692" class="text">
                    <p><span><br></span></p><p><span>风力</span></p><p><span><br></span></p><p><span id="data_qx_fl">暂无数据</span></p>
                </div>
            </div>

            <!-- data_qx_jy (矩形) -->
            <div id="u693" class="ax_default label" data-label="data_qx_jy">
                <div id="u693_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u694" class="text">
                    <p><span><br></span></p><p><span>风向</span></p><p><span><br></span></p><p><span id="data_qx_fx">暂无数据</span></p>
                </div>
            </div>

            <!-- data_qx_qy (矩形) -->
            <div id="u695" class="ax_default label" data-label="data_qx_qy">
                <div id="u695_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u696" class="text">
                    <p><span><br></span></p><p><span>气压</span></p><p><span><br></span></p><p><span id="data_qx_qy">暂无数据</span></p>
                </div>
            </div>

            <!-- data_qx_sd (矩形) -->
            <div id="u697" class="ax_default label" data-label="data_qx_sd">
                <div id="u697_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u698" class="text">
                    <p><span><br></span></p><p><span>湿度</span></p><p><span><br></span></p><p><span id="data_qx_sd">暂无数据</span></p>
                </div>
            </div>

            <!-- data_qx_qw (矩形) -->
            <div id="u699" class="ax_default label" data-label="data_qx_qw">
                <div id="u699_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u700" class="text">
                    <p><span><br></span></p><p><span>气温</span></p><p><span><br></span></p><p><span id="data_qx_qw">暂无数据</span></p>
                </div>
            </div>

            <!-- Unnamed (表格) -->
            <div id="u701" class="ax_default">

                <!-- yzc_16 (单元格) -->
                <div id="u702" class="ax_default table_cell" data-label="yzc_16">
                    <img id="u702_img" class="img " src="<%=path%>/images/main2/yzc_16_u702.png"/>
                    <!-- Unnamed () -->
                    <div id="u703" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_15 (单元格) -->
                <div id="u704" class="ax_default table_cell" data-label="yzc_15">
                    <img id="u704_img" class="img " src="<%=path%>/images/main2/yzc_15_u704.png"/>
                    <!-- Unnamed () -->
                    <div id="u705" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u706" class="ax_default table_cell">
                    <img id="u706_img" class="img " src="<%=path%>/images/main/u594.png"/>
                    <!-- Unnamed () -->
                    <div id="u707" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_17 (单元格) -->
                <div id="u708" class="ax_default table_cell" data-label="yzc_17">
                    <img id="u708_img" class="img " src="<%=path%>/images/main2/yzc_16_u702.png"/>
                    <!-- Unnamed () -->
                    <div id="u709" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u710" class="ax_default table_cell">
                    <img id="u710_img" class="img " src="<%=path%>/images/main2/yzc_15_u704.png"/>
                    <!-- Unnamed () -->
                    <div id="u711" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u712" class="ax_default table_cell">
                    <img id="u712_img" class="img " src="<%=path%>/images/main/u594.png"/>
                    <!-- Unnamed () -->
                    <div id="u713" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_18 (单元格) -->
                <div id="u714" class="ax_default table_cell" data-label="yzc_18">
                    <img id="u714_img" class="img " src="<%=path%>/images/main2/yzc_16_u702.png"/>
                    <!-- Unnamed () -->
                    <div id="u715" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_baokun (单元格) -->
                <div id="u716" class="ax_default table_cell" data-label="yzc_baokun">
                    <img id="u716_img" class="img " src="<%=path%>/images/main2/yzc_15_u704.png"/>
                    <!-- Unnamed () -->
                    <div id="u717" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u718" class="ax_default table_cell">
                    <img id="u718_img" class="img " src="<%=path%>/images/main/u594.png"/>
                    <!-- Unnamed () -->
                    <div id="u719" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_19 (单元格) -->
                <div id="u720" class="ax_default table_cell" data-label="yzc_19">
                    <img id="u720_img" class="img " src="<%=path%>/images/main2/yzc_16_u702.png"/>
                    <!-- Unnamed () -->
                    <div id="u721" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_20 (单元格) -->
                <div id="u722" class="ax_default table_cell" data-label="yzc_20">
                    <img id="u722_img" class="img " src="<%=path%>/images/main2/yzc_15_u704.png"/>
                    <!-- Unnamed () -->
                    <div id="u723" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u724" class="ax_default table_cell">
                    <img id="u724_img" class="img " src="<%=path%>/images/main/u594.png"/>
                    <!-- Unnamed () -->
                    <div id="u725" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u726" class="ax_default table_cell">
                    <img id="u726_img" class="img " src="<%=path%>/images/main2/u726.png"/>
                    <!-- Unnamed () -->
                    <div id="u727" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u728" class="ax_default table_cell">
                    <img id="u728_img" class="img " src="<%=path%>/images/main2/u728.png"/>
                    <!-- Unnamed () -->
                    <div id="u729" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u730" class="ax_default table_cell">
                    <img id="u730_img" class="img " src="<%=path%>/images/main2/u730.png"/>
                    <!-- Unnamed () -->
                    <div id="u731" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u732" class="ax_default table_cell">
                    <img id="u732_img" class="img " src="<%=path%>/images/main2/yzc_16_u702.png"/>
                    <!-- Unnamed () -->
                    <div id="u733" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u734" class="ax_default table_cell">
                    <img id="u734_img" class="img " src="<%=path%>/images/main2/yzc_15_u704.png"/>
                    <!-- Unnamed () -->
                    <div id="u735" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u736" class="ax_default table_cell">
                    <img id="u736_img" class="img " src="<%=path%>/images/main/u594.png"/>
                    <!-- Unnamed () -->
                    <div id="u737" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u738" class="ax_default table_cell">
                    <img id="u738_img" class="img " src="<%=path%>/images/main2/yzc_16_u702.png"/>
                    <!-- Unnamed () -->
                    <div id="u739" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u740" class="ax_default table_cell">
                    <img id="u740_img" class="img " src="<%=path%>/images/main2/yzc_15_u704.png"/>
                    <!-- Unnamed () -->
                    <div id="u741" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u742" class="ax_default table_cell">
                    <img id="u742_img" class="img " src="<%=path%>/images/main/u594.png"/>
                    <!-- Unnamed () -->
                    <div id="u743" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u744" class="ax_default table_cell">
                    <img id="u744_img" class="img " src="<%=path%>/images/main2/u744.png"/>
                    <!-- Unnamed () -->
                    <div id="u745" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u746" class="ax_default table_cell">
                    <img id="u746_img" class="img " src="<%=path%>/images/main2/u746.png"/>
                    <!-- Unnamed () -->
                    <div id="u747" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- Unnamed (单元格) -->
                <div id="u748" class="ax_default table_cell">
                    <img id="u748_img" class="img " src="<%=path%>/images/main2/u748.png"/>
                    <!-- Unnamed () -->
                    <div id="u749" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>
            </div>

            <!-- Unnamed (表格) -->
            <div id="u750" class="ax_default">

                <!-- Unnamed (单元格) -->
                <div id="u751" class="ax_default table_cell">
                    <img id="u751_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u752" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_13 (单元格) -->
                <div id="u753" class="ax_default table_cell" data-label="yzc_13">
                    <img id="u753_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u754" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_12 (单元格) -->
                <div id="u755" class="ax_default table_cell" data-label="yzc_12">
                    <img id="u755_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u756" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_11 (单元格) -->
                <div id="u757" class="ax_default table_cell" data-label="yzc_11">
                    <img id="u757_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u758" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_10 (单元格) -->
                <div id="u759" class="ax_default table_cell" data-label="yzc_10">
                    <img id="u759_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u760" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_9 (单元格) -->
                <div id="u761" class="ax_default table_cell" data-label="yzc_9">
                    <img id="u761_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u762" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_8 (单元格) -->
                <div id="u763" class="ax_default table_cell" data-label="yzc_8">
                    <img id="u763_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u764" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_7 (单元格) -->
                <div id="u765" class="ax_default table_cell" data-label="yzc_7">
                    <img id="u765_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u766" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_6 (单元格) -->
                <div id="u767" class="ax_default table_cell" data-label="yzc_6">
                    <img id="u767_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u768" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_5 (单元格) -->
                <div id="u769" class="ax_default table_cell" data-label="yzc_5">
                    <img id="u769_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u770" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_4 (单元格) -->
                <div id="u771" class="ax_default table_cell" data-label="yzc_4">
                    <img id="u771_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u772" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_3 (单元格) -->
                <div id="u773" class="ax_default table_cell" data-label="yzc_3">
                    <img id="u773_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u774" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_2 (单元格) -->
                <div id="u775" class="ax_default table_cell" data-label="yzc_2">
                    <img id="u775_img" class="img " src="<%=path%>/images/main2/u751.png"/>
                    <!-- Unnamed () -->
                    <div id="u776" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>

                <!-- yzc_1 (单元格) -->
                <div id="u777" class="ax_default table_cell" data-label="yzc_1">
                    <img id="u777_img" class="img " src="<%=path%>/images/main2/yzc_1_u777.png"/>
                    <!-- Unnamed () -->
                    <div id="u778" class="text" style="display:none; visibility: hidden">
                        <p><span></span></p>
                    </div>
                </div>
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u779" class="ax_default ellipse">
                <img id="u779_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u780" class="text">
                    <p><span>16</span></p>
                </div>
            </div>

            <!-- sb_16 (矩形) -->
            <div id="u781" class="ax_default primary_button" data-label="sb_16">
      
            </div>

            <!-- sz_16 (矩形) -->
            <div id="u783" class="ax_default primary_button" data-label="sz_16">
           
            </div>

            <!-- Unnamed (矩形) -->
            <div id="u785" class="ax_default sticky_2">
                <div id="u785_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u786" class="text">
                    <p><span>池&nbsp;&nbsp; 塘&nbsp;&nbsp; 常&nbsp;&nbsp; 规&nbsp;&nbsp; 运&nbsp;&nbsp; 行&nbsp;&nbsp; 指&nbsp;&nbsp; 标&nbsp;&nbsp; 在&nbsp;&nbsp; 线&nbsp;&nbsp; 监&nbsp;&nbsp; 测</span></p>
                </div>
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u787" class="ax_default ellipse">
                <img id="u787_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u788" class="text">
                    <p><span>13</span></p>
                </div>
            </div>

            <!-- sb_13 (矩形) -->
            <div id="u789" class="ax_default primary_button" data-label="sb_13">
      
            </div>

            <!-- sz_13 (矩形) -->
            <div id="u791" class="ax_default primary_button" data-label="sz_13">

            </div>

            <!-- Unnamed (矩形) -->
            <div id="u793" class="ax_default box_1">
                <div id="u793_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u794" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- Unnamed (矩形) -->
            <div id="u795" class="ax_default sticky_2">
                <div id="u795_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u796" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- Unnamed (矩形) -->
            <div id="u797" class="ax_default label">
                <div id="u797_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u798" class="text">
                    <p><span>外海水质</span></p>
                </div>
            </div>

            <!-- div_tianqi (矩形) -->
            <div id="u799" class="ax_default sticky_4" data-label="div_tianqi">
                <div id="u799_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u800" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- Unnamed (矩形) -->
            <div id="u801" class="ax_default box_1">
                <div id="u801_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u802" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- char_yandu (矩形) -->
            <div id="u803" class="ax_default box_1" data-label="char_yandu">
                <div id="char_d02" style="min-width: 220px; max-width: 220px; height: 220px; " class=""></div>
                <!-- Unnamed () -->
                <div id="u804" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- char_shuiwen (矩形) -->
            <div id="u805" class="ax_default box_1" data-label="char_shuiwen">
                <div id="char_chaoxi" style="min-width: 440px; max-width: 440px; height: 220px; " class=""></div>
                <!-- Unnamed () -->
                <div id="u806" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- char_ph (矩形) -->
            <div id="u807" class="ax_default box_1" data-label="char_ph">
                <div id="char_ph" style="min-width: 220px; max-width: 220px; height: 220px; " class=""></div>
                <!-- Unnamed () -->
                <div id="u808" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- char_chaoxi (矩形) -->
            <div id="u809" class="ax_default box_1" data-label="char_chaoxi">
                <div id="char_shuiwen" style="min-width: 220px; max-width: 220px; height: 220px; " class=""></div>
                <!-- Unnamed () -->
                <div id="u810" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- char_do2 (矩形) -->
            <div id="u811" class="ax_default box_1" data-label="char_d02">
                <div id="char_yandu" style="min-width: 220px; max-width: 220px; height: 220px; " class=""></div>
                <!-- Unnamed () -->
                <div id="u812" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- Unnamed (矩形) -->
            <div id="u813" class="ax_default sticky_2">
                <div id="u813_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u814" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- data_wh_cx (矩形) -->
            <div id="u815" class="ax_default label" data-label="data_wh_cx">
                <div id="u815_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u816" class="text">
                    <p><span id="data_wh_sw">暂无数据</span></p>
                </div>
            </div>

            <!-- Unnamed (矩形) -->
            <div id="u817" class="ax_default sticky_2">
                <div id="u817_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u818" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- data_wh_do2 (矩形) -->
            <div id="u819" class="ax_default label" data-label="data_wh_do2">
                <div id="u819_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u820" class="text">
                    <p><span id="data_wh_yd">暂无数据</span></p>
                </div>
            </div>

            <!-- Unnamed (矩形) -->
            <div id="u821" class="ax_default sticky_2">
                <div id="u821_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u822" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- data_wh_yd (矩形) -->
            <div id="u823" class="ax_default label" data-label="data_wh_yd">
                <div id="u823_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u824" class="text">
                    <p><span id="data_wh_d02">暂无数据</span></p>
                </div>
            </div>

            <!-- Unnamed (矩形) -->
            <div id="u825" class="ax_default sticky_2">
                <div id="u825_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u826" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- data_wh_ph (矩形) -->
            <div id="u827" class="ax_default label" data-label="data_wh_ph">
                <div id="u827_div" class=""></div>
                <!-- Unnamed () -->
                <div id="data_wh_ph" class="text">
                    <p><span>暂无数据</span></p>
                </div>
            </div>

            <!-- Unnamed (矩形) -->
            <div id="u829" class="ax_default sticky_2">
                <div id="u829_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u830" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- data_wh_sw (矩形) -->
            <div id="u831" class="ax_default label" data-label="data_wh_sw">
                <div id="u831_div" class=""></div>
                <!-- Unnamed () -->
                <div id="data_wh_cx" class="text">
                    <p><span>暂无数据</span></p>
                </div>
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u833" class="ax_default ellipse">
                <img id="u833_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u834" class="text">
                    <p><span>15</span></p>
                </div>
            </div>

            <!-- sb_15 (矩形) -->
            <div id="u835" class="ax_default primary_button" data-label="sb_15">
               
            </div>

            <!-- sz_15 (矩形) -->
            <div id="u837" class="ax_default primary_button" data-label="sz_15">
                
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u839" class="ax_default ellipse">
                <img id="u839_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u840" class="text">
                    <p><span>17</span></p>
                </div>
            </div>

            <!-- sb_17 (矩形) -->
            <div id="u841" class="ax_default primary_button" data-label="sb_17">
               
            </div>

            <!-- sz_17 (矩形) -->
            <div id="u843" class="ax_default primary_button" data-label="sz_17">
               
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u845" class="ax_default ellipse">
                <img id="u845_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u846" class="text">
                    <p><span>11</span></p>
                </div>
            </div>

            <!-- sb_11 (矩形) -->
            <div id="u847" class="ax_default primary_button" data-label="sb_11">
                
            </div>

            <!-- sz_11 (矩形) -->
            <div id="u849" class="ax_default primary_button" data-label="sz_11">
               
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u851" class="ax_default ellipse">
                <img id="u851_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u852" class="text">
                    <p><span>12</span></p>
                </div>
            </div>

            <!-- sb_12 (矩形) -->
            <div id="u853" class="ax_default primary_button" data-label="sb_12">
                
            </div>

            <!-- sz_12 (矩形) -->
            <div id="u855" class="ax_default primary_button" data-label="sz_12">
               
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u857" class="ax_default ellipse">
                <img id="u857_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u858" class="text">
                    <p><span>9</span></p>
                </div>
            </div>

            <!-- sb_9 (矩形) -->
            <div id="u859" class="ax_default primary_button" data-label="sb_9">
                
            </div>

            <!-- sz_9 (矩形) -->
            <div id="u861" class="ax_default primary_button" data-label="sz_9">
                
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u863" class="ax_default ellipse">
                <img id="u863_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u864" class="text">
                    <p><span>10</span></p>
                </div>
            </div>

            <!-- sb_10 (矩形) -->
            <div id="u865" class="ax_default primary_button" data-label="sb_10">
                
            </div>

            <!-- sz_10 (矩形) -->
            <div id="u867" class="ax_default primary_button" data-label="sz_10">
               
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u869" class="ax_default ellipse">
                <img id="u869_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u870" class="text">
                    <p><span>7</span></p>
                </div>
            </div>

            <!-- sb_7 (矩形) -->
            <div id="u871" class="ax_default primary_button" data-label="sb_7">
                
            </div>

            <!-- sz_7 (矩形) -->
            <div id="u873" class="ax_default primary_button" data-label="sz_7">
                
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u875" class="ax_default ellipse">
                <img id="u875_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u876" class="text">
                    <p><span>8</span></p>
                </div>
            </div>

            <!-- sb_8 (矩形) -->
            <div id="u877" class="ax_default primary_button" data-label="sb_8">
                
            </div>

            <!-- sz_8 (矩形) -->
            <div id="u879" class="ax_default primary_button" data-label="sz_8">
                
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u881" class="ax_default ellipse">
                <img id="u881_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u882" class="text">
                    <p><span>5</span></p>
                </div>
            </div>

            <!-- sb_5 (矩形) -->
            <div id="u883" class="ax_default primary_button" data-label="sb_5">
               
            </div>

            <!-- sz_5 (矩形) -->
            <div id="u885" class="ax_default primary_button" data-label="sz_5">
               
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u887" class="ax_default ellipse">
                <img id="u887_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u888" class="text">
                    <p><span>6</span></p>
                </div>
            </div>

            <!-- sb_6 (矩形) -->
            <div id="u889" class="ax_default primary_button" data-label="sb_6">
                
            </div>

            <!-- sz_6 (矩形) -->
            <div id="u891" class="ax_default primary_button" data-label="sz_6">
                
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u893" class="ax_default ellipse">
                <img id="u893_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u894" class="text">
                    <p><span>3</span></p>
                </div>
            </div>

            <!-- sb_3 (矩形) -->
            <div id="u895" class="ax_default primary_button" data-label="sb_3">
            
            </div>

            <!-- sz_3 (矩形) -->
            <div id="u897" class="ax_default primary_button" data-label="sz_3">
          
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u899" class="ax_default ellipse">
                <img id="u899_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u900" class="text">
                    <p><span>4</span></p>
                </div>
            </div>

            <!-- sb_4 (矩形) -->
            <div id="u901" class="ax_default primary_button" data-label="sb_4">
               
            </div>

            <!-- sz_4 (矩形) -->
            <div id="u903" class="ax_default primary_button" data-label="sz_4">
                
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u905" class="ax_default ellipse">
                <img id="u905_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u906" class="text">
                    <p><span>1</span></p>
                </div>
            </div>

            <!-- sb_1 (矩形) -->
            <div id="u907" class="ax_default primary_button" data-label="sb_1" onclick="showYzq(1)">
                <div id="sbzt_1" class="zhuangtai_div"></div>
                <!-- Unnamed () -->
                <div class="text ztneirong">
                    <p><span>设备</span></p>
                </div>
            </div>

            <!-- sz_1 (矩形) -->
            <div id="u909" class="ax_default primary_button" data-label="sz_1" onclick="showYzq(1)">
                <div id="szzt_1" class="zhuangtai_div"></div>
                <!-- Unnamed () -->
                <div class="text ztneirong">
                    <p><span>水质</span></p>
                </div>
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u911" class="ax_default ellipse">
                <img id="u911_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u912" class="text">
                    <p><span>2</span></p>
                </div>
            </div>

            <!-- sb_2 (矩形) -->
            <div id="u913" class="ax_default primary_button" data-label="sb_2">
             
            </div>

            <!-- sz_2 (矩形) -->
            <div id="u915" class="ax_default primary_button" data-label="sz_2">
  
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u917" class="ax_default ellipse">
                <img id="u917_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u918" class="text">
                    <p><span>18</span></p>
                </div>
            </div>

            <!-- sb_18 (矩形) -->
            <div id="u919" class="ax_default primary_button" data-label="sb_18">
       
            </div>

            <!-- sz_18 (矩形) -->
            <div id="u921" class="ax_default primary_button" data-label="sz_18">
                
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u923" class="ax_default ellipse">
                <img id="u923_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u924" class="text">
                    <p><span>19</span></p>
                </div>
            </div>

            <!-- sb_19 (矩形) -->
            <div id="u925" class="ax_default primary_button" data-label="sb_19">
                
            </div>

            <!-- sz_19 (矩形) -->
            <div id="u927" class="ax_default primary_button" data-label="sz_19">
                
            </div>

            <!-- Unnamed (椭圆形) -->
            <div id="u929" class="ax_default ellipse">
                <img id="u929_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u930" class="text">
                    <p><span>宝坤</span></p>
                </div>
            </div>

            <!-- sb_baokun (矩形) -->
            <div id="u931" class="ax_default primary_button" data-label="sb_baokun">
                
            </div>

            <!-- sz_baokun (矩形) -->
            <div id="u933" class="ax_default primary_button" data-label="sz_baokun">
                
            </div>

            <!-- yzc_20 (椭圆形) -->
            <div id="u935" class="ax_default ellipse" data-label="yzc_20">
                <img id="u935_img" class="img " src="<%=path%>/images/main2/u779.png"/>
                <!-- Unnamed () -->
                <div id="u936" class="text">
                    <p><span>20</span></p>
                </div>
            </div>

            <!-- sb_20 (矩形) -->
            <div id="u937" class="ax_default primary_button" data-label="sb_20">
                
            </div>

            <!-- sz_20 (矩形) -->
            <div id="u939" class="ax_default primary_button" data-label="sz_20">
                
            </div>

            <!-- img_fl (图片) -->
            <div id="u941" class="ax_default _图片" data-label="img_fl">
                <img id="u941_img" class="img " src="<%=path%>/images/main2/img_fl_u941.png"/>
                <!-- Unnamed () -->
                <div id="u942" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- img_qw (图片) -->
            <div id="u943" class="ax_default _图片" data-label="img_qw">
                <img id="u943_img" class="img " src="<%=path%>/images/main2/img_qw_u943.png"/>
                <!-- Unnamed () -->
                <div id="u944" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- img_jy (图片) -->
            <div id="u945" class="ax_default _图片" data-label="img_jy">
                <img id="u949_img" class="img " src="<%=path%>/images/zhyy/qx/fengxiang.png">
                
                <!-- Unnamed () -->
                <div id="u946" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- img_qy (图片) -->
            <div id="u947" class="ax_default _图片" data-label="img_qy">
                <img id="u947_img" class="img " src="<%=path%>/images/main2/img_qy_u947.png"/>
                <!-- Unnamed () -->
                <div id="u948" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- img_sd (图片) -->
            <div id="u949" class="ax_default _图片" data-label="img_sd">
                <img id="u949_img" class="img " src="<%=path%>/images/main2/img_sd_u949.png"/>
                <!-- Unnamed () -->
                <div id="u950" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- img_tq (图片) -->
            <div id="u951" class="ax_default _图片" data-label="img_tq">
 
                <img id="u945_img" class="img " src="<%=path%>/images/main2/img_jy_u945.png"/>
                <!--<img id="u951_img" class="img " src="<%=path%>/images/main2/img_tq_u951.png"/>-->
                <!-- Unnamed () -->
                <div id="u952" class="text" style="display:none; visibility: hidden">
                    <p><span></span></p>
                </div>
            </div>

            <!-- data_qx_dy (矩形) -->
            <div id="u953" class="ax_default label" data-label="data_qx_dy">
                <div id="u953_div" class=""></div>      
                <div id="u954" class="text">
                   <p><span><br></span></p><p><span>降雨量</span></p><p><span><br></span></p><p><span id="data_qx_jy">暂无数据</span></p>
                </div>
            </div>

            <!-- Unnamed (矩形) -->
            <div id="u955" class="ax_default sticky_2">
                <div id="u955_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u956" class="text">
                    <p><span>大连固德水产养殖有限公司</span></p>
                </div>
            </div>

            <!-- btn_sys (矩形) -->
            <a target="_blank" href="<%=path%>/index" >
            <div id="u957" class="ax_default primary_button" data-label="btn_sys">
                <div id="u957_div" class=""></div>
                <!-- Unnamed () -->
                <div id="u958" class="text">
                    <p><span>进入后台管理系统</span></p>
                </div>
            </div>
            </a>
        </div>

        <div id="data_tq"></div>
    </body>
    <script type="text/javascript">
        //世界时间 8小时时差
        $(function () {
            Highcharts.setOptions({global: {useUTC: false}});
            //水温
            //loadlist();
            loadYzq();
            getTqData();
            getWhSzData();
            setInterval(getWhSzData, 30000);
            setInterval(getTqData, 30000);
            setInterval(loadYzq, 30000);
        })
        
        var loadyzc_url = "<%=path%>/ifs/zhyy/yangzhiqu/getYzqZhuangtai";
        function loadYzq(){
            $.ajax({
                url: loadyzc_url,
                data: {yzcid: "0001"
                },
                type: 'POST',
                timeout: fw_config.ajax_timeout,
                success: function (json, textStatus) {
                    
                    for (var key in json) {
                        if(json[key][1] ){
                            $("#sbzt_" + key).css("background-color","rgba(255, 0, 0, 1)");
                        }else{
                            $("#sbzt_" + key).css("background-color","rgba(0, 128, 0, 1)");
                        }
                        if(json[key][0] ){
                            $("#szzt_" + key).css("background-color","rgba(255, 0, 0, 1)");
                        }else{
                            $("#sbzt_" + key).css("background-color","rgba(0, 128, 0, 1)");
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            })
        }
        
        
        var formDialog;
        function showYzq(yzqid){
           //alert(yzqid); 
           formDialog = new $.dialog({
                id:'yzqDialogFrame',
                title:"养殖池状态",
                height:500,
                width:750,
                cover:true,
		content: 'url:<%=path%>/con/zhyy/yangzhiqu/view?id='+yzqid,
                autoPos:true
            });
        }
//        var loadlist_url = "<%=path%>/ifs/zhyy/shuizhi/getnowlist";
//        function loadlist() {
//            $.ajax({
//                url: loadlist_url,
//                data: {rtuid: "00010001", yzqid: 1, riqi: "2018-07-28"
//                },
//                type: 'POST',
//                timeout: fw_config.ajax_timeout,
//                dataFilter: function (json) {
//                    console.log("jsonp.filter:" + json);
//                    return json;
//                },
//                success: function (json, textStatus) {
//                    var sellist = json.setlsit;
//                    for (var i = 0; i < sellist.length; i++) {
//                        if (sellist[i].s_attr === "s_yandu") {
//                            //yandu_chart(json.datalsit,sellist[i].s_tongdao);
//                        } else if (sellist[i].s_attr == "s_shuiwen") {
//                            shuiwen_chart(json.datalsit, sellist[i].s_tongdao);
//                        } else if (sellist[i].s_attr == "s_ph") {
//                            //ph_chart(json.datalsit,sellist[i].s_tongdao);
//                        } else if (sellist[i].s_attr == "s_d02") {
//                            //d02_chart(json.datalsit,sellist[i].s_tongdao);
//                        }
//                    }
//                },
//                error: function (XMLHttpRequest, textStatus, errorThrown) {
//                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
//                }
//            });
//        }

        var rtu = null;
        var yandu_chart;
        function load_yandu(zb_val) {
            $("#data_wh_yd").empty();
            $("#data_wh_yd").append("盐度:" + zb_val);
            yandu_chart = Highcharts.chart('char_yandu', {
                chart: {
                    type: 'gauge',
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false
                },
                pane: {
                    startAngle: -150,
                    endAngle: 150,
                    background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                },
                // the value axis
                yAxis: {
                    min: 20,
                    max: 36,
                    minorTickInterval: 'auto',
                    minorTickWidth: 1,
                    minorTickLength: 10,
                    minorTickPosition: 'inside',
                    minorTickColor: '#666',
                    tickPixelInterval: 30,
                    tickWidth: 2,
                    tickPosition: 'inside',
                    tickLength: 10,
                    tickColor: '#666',
                    labels: {
                        step: 2,
                        rotation: 'auto'
                    },
                    title: {
                        text: '盐度'
                    },
                    plotBands: [{
                            from: 10,
                            to: 22,
                            color: '#DF5353'
                                    //color: '#55BF3B' // green
                        }, {
                            from: 10,
                            to: 26,
                            color: '#DDDF0D' // yellow
                        }, {
                            from: 26,
                            to: 32,
                            color: '#55BF3B' // red
                        }, {
                            from: 32,
                            to: 34,
                            color: '#DDDF0D' // red
                        }, {
                            from: 34,
                            to: 36,
                            color: '#DF5353' // red
                        }]
                },
                series: [{
                        name: '盐度',
                        data: [zb_val],
                        tooltip: {
                            valueSuffix: ''
                        }
                    }]
            }, function (yandu_chart) {
                if (!yandu_chart.renderer.forExport) {
                    setInterval(function () {
                        var point = yandu_chart.series[0].points[0],
                                newVal;
                        //inc = Math.round((Math.random() - 0.1) * 20);
                        //alert("update yandu");
                        if (rtu != null) {
                            newVal = rtu.rtuai[5].va;
                            $("#data_wh_yd").empty();
                            $("#data_wh_yd").append("盐度:" + newVal);
                            point.update(newVal);
                        }
                    }, 10000);
                }
            });
        }
        
        var wendu_chart;
        function load_shuiwen(zb_val) {
            $("#data_wh_sw").empty();
            $("#data_wh_sw").append("水温:" + zb_val);
            wendu_chart = Highcharts.chart('char_shuiwen', {
                chart: {
                    type: 'gauge',
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false
                },
                pane: {
                    startAngle: -150,
                    endAngle: 150,
                    background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                },
                // the value axis
                yAxis: {
                    min: -5,
                    max: 36,
                    minorTickInterval: 'auto',
                    minorTickWidth: 1,
                    minorTickLength: 10,
                    minorTickPosition: 'inside',
                    minorTickColor: '#666',
                    tickPixelInterval: 30,
                    tickWidth: 2,
                    tickPosition: 'inside',
                    tickLength: 10,
                    tickColor: '#666',
                    labels: {
                        step: 2,
                        rotation: 'auto'
                    },
                    title: {
                        text: '温度'
                    },
                    plotBands: [{
                            from: -5,
                            to: 26,
                            color: '#55BF3B'
                                    //color: '#55BF3B' // green
                        }, {
                            from: 26,
                            to: 28,
                            color: '#DDDF0D' // yellow
                        }, {
                            from: 28,
                            to: 36,
                            color: '#DF5353' // red
                        }]
                },
                series: [{
                        name: '水温',
                        data: [zb_val],
                        tooltip: {
                            valueSuffix: ''
                        }
                    }]
            }, function (wendu_chart) {
                if (!wendu_chart.renderer.forExport) {
                    setInterval(function () {
                        var point = wendu_chart.series[0].points[0],
                                newVal;
                        //inc = Math.round((Math.random() - 0.1) * 20);
                        //alert("update yandu");
                        if (rtu != null) {
                            newVal = rtu.rtuai[6].va;
                            $("#data_wh_sw").empty();
                            $("#data_wh_sw").append("水温:" + newVal);
                            point.update(newVal);
                        }
                    }, 10000);
                }
            });
        }
        
        var chaoxi_chart;
        function load_chaoxi(zb_val) {
            $("#data_wh_cx").empty();
            $("#data_wh_cx").append("潮汐:" + zb_val);
            chaoxi_chart = Highcharts.chart('char_chaoxi', {
                chart: {
                    type: 'gauge',
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false
                },
                pane: {
                    startAngle: -150,
                    endAngle: 150,
                    background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                },
                // the value axis
                yAxis: {
                    min: 0,
                    max: 4,
                    minorTickInterval: 'auto',
                    minorTickWidth: 1,
                    minorTickLength: 10,
                    minorTickPosition: 'inside',
                    minorTickColor: '#666',
                    tickPixelInterval: 30,
                    tickWidth: 2,
                    tickPosition: 'inside',
                    tickLength: 10,
                    tickColor: '#666',
                    labels: {
                        step: 2,
                        rotation: 'auto'
                    },
                    title: {
                        text: '潮汐'
                    },
                    plotBands: [{
                            from: 0,
                            to: 0.5,
                            color: '#DF5353'
                                    //color: '#55BF3B' // green
                        }, {
                            from: 0.5,
                            to: 4,
                            color: '#55BF3B' // yellow
                        }]
                },
                series: [{
                        name: '潮汐',
                        data: [zb_val],
                        tooltip: {
                            valueSuffix: ''
                        }
                    }]
            }, function (chaoxi_chart) {
                if (!chaoxi_chart.renderer.forExport) {
                    setInterval(function () {
                        var point = chaoxi_chart.series[0].points[0],
                                newVal;
                        //inc = Math.round((Math.random() - 0.1) * 20);
                        //alert("update yandu");
                        if (rtu != null) {
                            newVal = rtu.rtuai[7].va;
                            $("#data_wh_cx").empty();
                            $("#data_wh_cx").append("潮汐:" + newVal);
                            point.update(newVal);
                        }
                    }, 10000);
                }
            });
        }
        
        var ph_chart;
        function load_ph(zb_val) {
            $("#data_wh_ph").empty();
            $("#data_wh_ph").append("pH:" + zb_val);
            ph_chart = Highcharts.chart('char_ph', {
                chart: {
                    type: 'gauge',
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false
                },
                pane: {
                    startAngle: -150,
                    endAngle: 150,
                    background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                },
                // the value axis
                yAxis: {
                    min: 6,
                    max: 10,
                    minorTickInterval: 'auto',
                    minorTickWidth: 1,
                    minorTickLength: 10,
                    minorTickPosition: 'inside',
                    minorTickColor: '#666',
                    tickPixelInterval: 30,
                    tickWidth: 2,
                    tickPosition: 'inside',
                    tickLength: 10,
                    tickColor: '#666',
                    labels: {
                        step: 2,
                        rotation: 'auto'
                    },
                    title: {
                        text: 'pH'
                    },
                    plotBands: [{
                            from: 6,
                            to: 8,
                            color: '#DF5353'
                                    //color: '#55BF3B' // green
                        }, {
                            from: 8,
                            to: 8.1,
                            color: '#DDDF0D' // yellow
                        }, {
                            from: 8.1,
                            to: 8.2,
                            color: '#55BF3B' // red
                        }, {
                            from: 8.2,
                            to: 8.3,
                            color: '#DDDF0D' // red
                        }, {
                            from: 8.3,
                            to: 10,
                            color: '#DF5353' // red
                        }]
                },
                series: [{
                        name: 'Ph',
                        data: [zb_val],
                        tooltip: {
                            valueSuffix: ''
                        }
                    }]
            }, function (ph_chart) {
                if (!ph_chart.renderer.forExport) {
                    setInterval(function () {
                        var point = ph_chart.series[0].points[0],
                                newVal;
                        //inc = Math.round((Math.random() - 0.1) * 20);
                        //alert("update yandu");
                        if (rtu != null) {
                            newVal = rtu.rtuai[3].va;
                            $("#data_wh_ph").empty();
                            $("#data_wh_ph").append("pH:" + newVal);
                            point.update(newVal);
                        }
                    }, 10000);
                }
            });
        }
        
        var d02_chart;
        function load_d02(zb_val) {
            $("#data_wh_d02").empty();
            $("#data_wh_d02").append("DO2:" + zb_val);
            d02_chart = Highcharts.chart('char_d02', {
                chart: {
                    type: 'gauge',
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false
                },
                pane: {
                    startAngle: -150,
                    endAngle: 150,
                    background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                },
                // the value axis
                yAxis: {
                    min: 0,
                    max: 20,
                    minorTickInterval: 'auto',
                    minorTickWidth: 1,
                    minorTickLength: 10,
                    minorTickPosition: 'inside',
                    minorTickColor: '#666',
                    tickPixelInterval: 30,
                    tickWidth: 2,
                    tickPosition: 'inside',
                    tickLength: 10,
                    tickColor: '#666',
                    labels: {
                        step: 2,
                        rotation: 'auto'
                    },
                    title: {
                        text: 'DO2'
                    },
                    plotBands: [{
                            from: 0,
                            to: 2,
                            color: '#DF5353'
                                    //color: '#55BF3B' // green
                        }, {
                            from: 2,
                            to: 3,
                            color: '#DDDF0D' // yellow
                        }, {
                            from: 3,
                            to: 20,
                            color: '#55BF3B' // red
                        }]
                },
                series: [{
                        name: 'do2',
                        data: [zb_val],
                        tooltip: {
                            valueSuffix: ''
                        }
                    }]
            }, function (d02_chart) {
                if (!d02_chart.renderer.forExport) {
                    setInterval(function () {
                        var point = d02_chart.series[0].points[0],
                                newVal;
                        //inc = Math.round((Math.random() - 0.1) * 20);
                        //alert("update yandu");
                        if (rtu != null) {
                            newVal = rtu.rtuai[4].va;
                            $("#data_wh_d02").empty();
                            $("#data_wh_d02").append("DO2:" + newVal);
                            point.update(newVal);
                        }
                    }, 10000);
                }
            });
        }
//        function shuiwen_chart(datalist) {
//            var seriesdata = new Array();
//  //        for(var i = 0;i < datalist.length;i ++){
//  //            seriesdata[i] = new Object();
//  //            seriesdata[i]["name"] = "第" + (i + 1) + "水层";
//  //            seriesdata[i]["data"] = getdata(datalist[i],"s_shuiwen");
//  //        }
//            seriesdata[0] = new Object();
//            seriesdata[0]["name"] = "水温";
//            seriesdata[0]["data"] = getdata(datalist[0], "s_shuiwen");
//            var chart2 = Highcharts.chart('char_shuiwen', {
//                chart: {
//                    type: 'spline',
//                    marginRight: 10
//  //                ,
//  //                events: {
//  //                    load: function () {
//  //                        var chart = this;
//  //                        activeLastPointToolip(chart);
//  //			setInterval(function () {
//  //                           getnow(chart,6,1);
//  //			}, 10000);
//  //                    }
//  //                }
//                },
//                credits: {
//                    enabled: false
//                },
//                title: {
//                    text: ''
//                },
//                xAxis: {
//                    type: 'datetime',
//                    tickPixelInterval: 150
//                },
//                yAxis: {
//                    title: {
//                        text: null
//                    }
//                },
//                tooltip: {
//                    formatter: function () {
//                        return '<b>' + this.series.name + '</b><br/>' +
//                                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
//                                Highcharts.numberFormat(this.y, 2);
//                    }
//                }, legend: {
//                    align: 'right',
//                    verticalAlign: 'bottom'
//                },
//                series: seriesdata
//            });
//        }
//
//        function activeLastPointToolip(chart) {
//            var points = chart.series[0].points;
//            chart.tooltip.refresh(points[points.length - 1]);
//        }
//
//        function getdata(list, attr) {
//            var data = [], i;
//            for (i = 0 - list.length; i < 0; i += 1) {
//                data.push({
//                    x: list[i + list.length].s_caijishijian,
//                    y: list[i + list.length][attr]
//                });
//            }
//            return data;
//        }

        var userid = "533965984939DIkhE";
        var pwd = "O5o07R2";
        var staid = "54584";
        var elements = "Year,Mon,Day,Hour,PRS,PRS_Sea,PRS_Max,PRS_Min,TEM,TEM_Max,TEM_min,RHU,RHU_Min,VAP,PRE_1h,WIN_D_INST_Max,WIN_S_Max,WIN_D_S_Max,WIN_S_Avg_2mi,WIN_D_Avg_2mi,WEP_Now,WIN_S_Inst_Max";



  //  	$("#btime").val(formatdate(date,format) + "000000");
  //  	$("#etime").val(formatdate(date,"yyyyMMddhh") + "0000") ;

        var url = "";
        function getTqData() {
            var format = "yyyyMMdd";
            var shicha = 1000 * 60 * 60 * (8 + 2);
            var date = new Date();
            var etime = formatdate(date, "yyyyMMddhh") + "0000";
            date.setTime(date.getTime() - shicha)
            var btime = formatdate(date, "yyyyMMddhh") + "0000";

            $("#data_tq").empty();
  //  		btime = $("#btime").val();
  //  		etime = $("#etime").val();
  //                btime = "20180806000000";
  //                etime = "20180811020000";
            url = "http://api.data.cma.cn:8090/api?userId=" + userid + "&pwd=" + pwd + "&dataFormat=json" +
                    "&interfaceId=getSurfEleByTimeRangeAndStaID" +
                    "&timeRange=[" + btime + "," + etime + "]" +
                    "&staIDs=" + staid +
                    "&elements=" + elements +
                    "&dataCode=SURF_CHN_MUL_HOR";
            url = "/framework-web/zhyy/qx/getdata";
  //                url = "http://api.data.cma.cn:8090/api?userId=533965984939DIkhE&pwd=O5o07R2&dataFormat=json&interfaceId=getSurfEleByTimeRangeAndStaID&timeRange=[20180811050000,20180811050000]&staIDs=54584&elements=Year,Mon,Day,Hour,PRS,PRS_Sea,PRS_Max,PRS_Min,TEM,TEM_Max,TEM_min,RHU,RHU_Min,VAP,PRE_1h,WIN_D_INST_Max,WIN_S_Max,WIN_D_S_Max,WIN_S_Avg_2mi,WIN_D_Avg_2mi,WEP_Now,WIN_S_Inst_Max&dataCode=SURF_CHN_MUL_HOR";
            $.ajax({
                url: url,
                data: {btime: btime, etime: etime},
                type: 'POST',

                timeout: fw_config.ajax_timeout,
                dataFilter: function (json) {
                    //console.log("jsonp.filter:"+json  );    
                    return json;
                },
                success: function (json, textStatus) {
  //                       TEM 气温  摄氏度(℃) 
  //                        RHU 湿度
  //                        PRS 气压 百帕
  //                        WIN_S_Avg_2mi 2分钟平均风速  米/秒
  //                        PRE_1h 1小时降雨量  毫米 

  //                           alert(json["DS"][lastnum].TEM);
                    var data = json.DS;

                    if (data != null && data.length > 0) {
                        var lastnum = data.length - 1;
                        //alert(data[lastnum].Year + "-" + data[lastnum].Mon + "-" +data[lastnum].Day + " " + data[lastnum].Hour + ":00:00");
                        var d = new Date(data[lastnum].Year + "-" + data[lastnum].Mon + "-" + data[lastnum].Day + " " + data[lastnum].Hour + ":00:00");
                        d.setTime(d.getTime() + 1000 * 60 * 60 * 8)
                        $("#time_qxzx").empty();
                        $("#time_qxzx").append("气象在线&nbsp;" + formatdate(d, "yyyy年MM月dd日 hh点"));
                        var num = parseFloat(data[lastnum].TEM);
                        num = num.toFixed(1);
                        $("#data_qx_qw").empty();
                        $("#data_qx_qw").append(num + "℃");

                        num = parseFloat(data[lastnum].WIN_S_Avg_2mi);
                        num = num.toFixed(1);
                        $("#data_qx_fl").empty();
                        $("#data_qx_fl").append(num + "米/秒");
                        
                        num = parseFloat(data[lastnum].WIN_D_Avg_2mi);
                        num = num.toFixed(1);
                        var fxstr ;
                        //fx = parseFloat(data[lastnum].WIN_D_Avg_2mi);
                        fx = parseFloat(data[lastnum].WIN_D_S_Max);
                        if( fx > 360 - 11.25 && fx <= 360 ||  fx <= 11.25){
                            fxstr = "北";
                        }else if(fx > 0 + 11.25 && fx <= 22.5 + 11.25){
                            fxstr = "北东北";
                        }else if(fx > 22.5 + 11.25 && fx <= 45 + 11.25){
                            fxstr = "东北";
                        }else if(fx > 45 + 11.25 && fx <= 67.5 + 11.25){
                            fxstr = "东东北";
                        }else if(fx > 67.5 + 11.25 && fx <= 90 + 11.25){
                            fxstr = "东";
                        }else if(fx > 90 + 11.25 && fx <= 112.5 + 11.25){
                            fxstr = "东东南";
                        }else if(fx > 112.5 + 11.25 && fx <= 135 + 11.25){
                            fxstr = "东南";
                        }else if(fx > 135 + 11.25 && fx <= 157.5 + 11.25){
                            fxstr = "南东南";
                        }else if(fx > 157.5 + 11.25 && fx <= 180 + 11.25){
                            fxstr = "南";
                        }else if(fx > 180 + 11.25 && fx <= 202.5){
                            fxstr = "南西南";
                        }else if(fx > 202.5 + 11.25 && fx <= 225 + 11.25){
                            fxstr = "西南";
                        }else if(fx > 225 + 11.25 && fx <= 247.5 + 11.25){
                            fxstr = "西西南";
                        }else if(fx > 247.5 + 11.25 && fx <= 270 + 11.25){
                            fxstr = "西";
                        }else if(fx > 270 + 11.25 && fx <= 292.5 + 11.25){
                            fxstr = "西西北";
                        }else if(fx > 292.5 + 11.25 && fx <= 315 + 11.25){
                            fxstr = "西北";
                        }else if(fx > 315 + 11.25 && fx <= 337.5 + 11.25){
                            fxstr = "北西北";
                        }
                        $("#data_qx_fx").empty();
                        $("#data_qx_fx").append(fxstr);

                        num = parseFloat(data[lastnum].PRE_1h);
                        num = num.toFixed(1);
                        $("#data_qx_jy").empty();
                        $("#data_qx_jy").append(num + "毫米");

                        num = parseFloat(data[lastnum].PRS);
                        num = num.toFixed(1);
                        $("#data_qx_qy").empty();
                        $("#data_qx_qy").append(num + "p");

                        num = parseFloat(data[lastnum].RHU);
                        num = num.toFixed(1);
                        $("#data_qx_sd").empty();
                        $("#data_qx_sd").append(num + "%");
                    }

                    //console.log("jsonp.success:"+json);    
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                }
            });
        }
        var init_flag = false;
        var whszdata_url = "<%=path%>/ifs/zhyy/getRTUModById";
        function getWhSzData() {
            $.ajax({
                    url: whszdata_url,
                    data: {rtuid: "00010001"},
                    type: 'POST',
                    timeout: fw_config.ajax_timeout,
                    dataFilter: function (json) {
                        console.log("jsonp.filter:" + json);
                        return json;
                    },
                    success: function (json, textStatus) {
                        //alert("getWsData");
                        rtu = json;
                        if (!init_flag) {
                            load_yandu(json.rtuai[5].va);
                            load_shuiwen(json.rtuai[6].va);
                            load_chaoxi(json.rtuai[7].va);
                            load_d02(json.rtuai[4].va);
                            load_ph(json.rtuai[3].va);
                            init_flag = true;
                        }
      //                        alert(json.datatime);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        console.log("jsonp.error:" + XMLHttpRequest.returnCode);
                    }
            }); 
        }

        function formatdate(d, format) {
            var date = {
                "M+": d.getMonth() + 1,
                "d+": d.getDate(),
                "h+": d.getHours(),
                "m+": d.getMinutes(),
                "s+": d.getSeconds(),
                "q+": Math.floor((d.getMonth() + 3) / 3),
                "S+": d.getMilliseconds()
            };
            if (/(y+)/i.test(format)) {
                format = format.replace(RegExp.$1, (d.getFullYear() + '').substr(4 - RegExp.$1.length));
            }
            for (var k in date) {
                if (new RegExp("(" + k + ")").test(format)) {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1
                            ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
                }
            }
            return format;
        }
    </script>
</html>
