    function getdata(list,attr,dtype) {
        var data = [],i;
        var num = parseInt(list[0].s_hour);
        if( dtype == 1 ){
            for (i = num - list.length; i < 0; i += 1) {
                data.push({
                    x: list[i + list.length].s_hour,
                    y: list[i + list.length][attr]
                });
            }
        
            for (i = 24-(list.length + num); i < 24; i ++) {
                data.push({
                    x: i,
                    y: null
                });
            }
        }else{
            for (i = 0; i < list.length; i ++) {
                data.push({
                    x: list[i].s_systime,
                    y: list[i][attr]
                });
            }
        }
        return data;
    }
    
    function getdataAll(list,attr) {
        var data = [],i;

        for (i = 0; i < list.length; i ++) {
            data.push({
                x: list[i].s_caijishijian,
                y: list[i][attr]
            });
        }
        return data;
    }
    
    function yandu_chart(datalist,div_id,dtype){
        var seriesdata = new Array();
        var titstr = "";
        for(var i = 0;i < datalist.length;i ++){
            seriesdata[i] = new Object();
            seriesdata[i]["name"] = "第" + (i + 1) + "水层";
            seriesdata[i]["data"] = getdata(datalist[i],"s_yandu",dtype);
            titstr += "  第" + (i + 1) + "层水:" + datalist[i][datalist[i].length - 1]["s_yandu"] ;
        }
        if(dtype == 1){
            var chart1 = Highcharts.chart(div_id, {
                chart: {
                    type: 'spline',
                    marginRight: 10
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: '盐度实时' + titstr
                },
                xAxis: {
                    type: '',
                    tickPixelInterval: 50
                },
                yAxis: {
                    title: {
                        text: null
                    }
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.series.name + '</b><br/>' +
                                this.x + '点<br/>' +
                                this.y;
                    }
                },legend: {
                        align: 'right',
                        verticalAlign: 'bottom'
                },
                series: seriesdata
            });
        }else{
            var chart1 = Highcharts.chart(div_id, {
                chart: {
                    type: 'spline',
                    marginRight: 10
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: '盐度' 
                },
                xAxis: {
                    type: 'datetime',
                    tickPixelInterval: 50,
                    dateTimeLabelFormats: {
                            day: '%Y-%m-%d'
                        }
                },
                yAxis: {
                    title: {
                        text: null
                    }
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.series.name + '</b><br/>' +
                                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                                this.y;
                    }
                },legend: {
                        align: 'right',
                        verticalAlign: 'bottom'
                },
                series: seriesdata
            });
        }
    }
    function shuiwen_chart(datalist,div_id,dtype){
        var seriesdata = new Array();
        var titstr = "";
        for(var i = 0;i < datalist.length;i ++){
            seriesdata[i] = new Object();
            seriesdata[i]["name"] = "第" + (i + 1) + "水层";
            seriesdata[i]["data"] = getdata(datalist[i],"s_shuiwen",dtype);
            titstr += "  第" + (i + 1) + "层水:" + datalist[i][datalist[i].length - 1]["s_shuiwen"] ;
        }
        if(dtype == 1){
            var chart2 = Highcharts.chart(div_id, {
                chart: {
                    type: 'spline',
                    marginRight: 10
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: '水温实时' + titstr
                },
                xAxis: {
                    type: '',
                    tickPixelInterval: 50
                },
                yAxis: {
                    title: {
                        text: null
                    }
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.series.name + '</b><br/>' +
                                this.x + '点<br/>' +
                                this.y;
                    }
                },legend: {
                        align: 'right',
                        verticalAlign: 'bottom'
                },
                series: seriesdata
            });
        }else{
            var chart2 = Highcharts.chart(div_id, {
                chart: {
                    type: 'spline',
                    marginRight: 10
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: '水温'
                },
                xAxis: {
                    type: 'datetime',
                    tickPixelInterval: 50,
                    dateTimeLabelFormats: {
                            day: '%Y-%m-%d'
                        }
                },
                yAxis: {
                    title: {
                        text: null
                    }
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.series.name + '</b><br/>' +
                                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                                this.y;
                    }
                },legend: {
                        align: 'right',
                        verticalAlign: 'bottom'
                },
                series: seriesdata
            });
        }
    }
    function ph_chart(datalist,div_id,dtype){
        var seriesdata = new Array();
        var titstr = "";
        for(var i = 0;i < datalist.length;i ++){
            seriesdata[i] = new Object();
            seriesdata[i]["name"] = "第" + (i + 1) + "水层";
            seriesdata[i]["data"] = getdata(datalist[i],"s_ph",dtype);
            titstr += "  第" + (i + 1) + "层水:" + datalist[i][datalist[i].length - 1]["s_ph"] ;
        }
        if(dtype == 1){
            var chart3 = Highcharts.chart(div_id, {
                chart: {
                    type: 'spline',
                    marginRight: 10
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: 'PH实时' + titstr
                },
                xAxis: {
                    type: '',
                    tickPixelInterval: 50
                },
                yAxis: {
                    title: {
                        text: null
                    }
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.series.name + '</b><br/>' +
                                this.x + '点<br/>' +
                                this.y;
                    }
                },legend: {
                        align: 'right',
                        verticalAlign: 'bottom'
                },
                series: seriesdata
            });
        }else{
            var chart3 = Highcharts.chart(div_id, {
            chart: {
                    type: 'spline',
                    marginRight: 10
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: 'PH' 
                },
                xAxis: {
                    type: 'datetime',
                    tickPixelInterval: 50,
                    dateTimeLabelFormats: {
                            day: '%Y-%m-%d'
                        }
                },
                yAxis: {
                    title: {
                        text: null
                    }
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.series.name + '</b><br/>' +
                                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                                this.y;
                    }
                },legend: {
                        align: 'right',
                        verticalAlign: 'bottom'
                },
                series: seriesdata
            });
        }
    }
    function d02_chart(datalist,div_id,dtype){
        var seriesdata = new Array();
        var titstr = "";
        for(var i = 0;i < datalist.length;i ++){
            seriesdata[i] = new Object();
            seriesdata[i]["name"] = "第" + (i + 1) + "水层";
            seriesdata[i]["data"] = getdata(datalist[i],"s_d02",dtype);
            titstr += "  第" + (i + 1) + "层水:" + datalist[i][datalist[i].length - 1]["s_d02"] ;
        }
        if(dtype == 1){
            var chart4 = Highcharts.chart(div_id, {
                chart: {
                    type: 'spline',
                    marginRight: 10
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: 'd02实时' + titstr
                },
                xAxis: {
                    type: '',
                    tickPixelInterval: 50
                },
                yAxis: {
                    title: {
                        text: null
                    }
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.series.name + '</b><br/>' +
                                this.x + '点<br/>' +
                                this.y;
                    }
                },legend: {
                        align: 'right',
                        verticalAlign: 'bottom'
                },
                series: seriesdata
            });
        }else{
            var chart4 = Highcharts.chart(div_id, {
                chart: {
                    type: 'spline',
                    marginRight: 10
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: 'd02'
                },
               xAxis: {
                    type: 'datetime',
                    tickPixelInterval: 50,
                    dateTimeLabelFormats: {
                            day: '%Y-%m-%d'
                        }
                },
                yAxis: {
                    title: {
                        text: null
                    }
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.series.name + '</b><br/>' +
                                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                                this.y;
                    }
                },legend: {
                        align: 'right',
                        verticalAlign: 'bottom'
                },
                series: seriesdata
            });
        }
    }
    
    
    function shendu_chart(datalist,div_id,dtype){
        var alldata = new Array();
        var seriesdata = new Array();
        var titstr = "";
//        for(var i = 0;i < datalist.length;i ++){
//            alldata = alldata.concat(datalist[i]);
//        }
        seriesdata[0] = new Object();
        seriesdata[0]["name"] = "水深";
        seriesdata[0]["data"] = getdataAll(datalist,"s_shuishen");
        if(dtype == 1){
            if(datalist.length > 0){
                titstr += "水深实时  " + datalist[datalist.length - 1]["s_shuishen"] ;
            }
        }else{
                titstr += "水深";
            }
        
        var chart5 = Highcharts.chart(div_id, {
            chart: {
                type: 'spline',
                marginRight: 10
                
//                ,
//                events: {
//                    load: function () {
//                        var chart = this;
//                        activeLastPointToolip(chart);
//			setInterval(function () {
//                           getnow_sd(chart,4,5);
//			}, 10000);
//                    }
//                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: ' ' + titstr
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150,
                dateTimeLabelFormats: {
                        day: '%Y-%m-%d'
                    }
            },
            yAxis: {
                title: {
                    text: null
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                            Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                            this.y;
                }
            },legend: {
                    align: 'right',
                    verticalAlign: 'bottom'
            },
            series: seriesdata
        });
    }
    
    function dianliu1_chart(datalist,div_id,dtype){
        var alldata = new Array();
        var seriesdata = new Array();
        var titstr = "";
//        for(var i = 0;i < datalist.length;i ++){
//            alldata = alldata.concat(datalist[i]);
//        }
        seriesdata[0] = new Object();
        seriesdata[0]["name"] = "电流";
        seriesdata[0]["data"] = getdataAll(datalist,"s_dianliu_1");
       
        if(dtype == 1){
            if(datalist.length > 0){
                titstr += "养水机1 电流实时  " + datalist[datalist.length - 1]["s_dianliu_1"] ;
            }
        }else{
                titstr += "养水机1 电流";
            }
        var chart6 = Highcharts.chart(div_id, {
            chart: {
                type: 'spline',
                marginRight: 10
            },
            credits: {
                enabled: false
            },
            title: {
                text: titstr
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150,
                dateTimeLabelFormats: {
                        day: '%Y-%m-%d'
                    }
            },
            yAxis: {
                title: {
                    text: null
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                            Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                            this.y;
                }
            },legend: {
                    align: 'right',
                    verticalAlign: 'bottom'
            },
            series: seriesdata
        });
    }
    
    function dianliu2_chart(datalist,div_id,dtype){
        var alldata = new Array();
        var seriesdata = new Array();
        var titstr = "";
//        for(var i = 0;i < datalist.length;i ++){
//            alldata = alldata.concat(datalist[i]);
//        }
        seriesdata[0] = new Object();
        seriesdata[0]["name"] = "电流";
        seriesdata[0]["data"] = getdataAll(datalist,div_id);
        if(dtype == 1){
            if(datalist.length > 0){
                titstr += "养水机2 电流实时  " + datalist[datalist.length - 1]["s_dianliu_2"] ;
            }
        }else{
                titstr += "养水机2 电流";
            }
        var chart7 = Highcharts.chart('char_dianliu2', {
            chart: {
                type: 'spline',
                marginRight: 10
            },
            credits: {
                enabled: false
            },
            title: {
                text:  titstr
            },
            xAxis: {
                type: '',
                tickPixelInterval: 50
            },
            yAxis: {
                title: {
                    text: null
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                            Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                            this.y;
                }
            },legend: {
                    align: 'right',
                    verticalAlign: 'bottom'
            },
            series: seriesdata
        });
    }