<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
 <!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>lifo</title>
	<link rel="stylesheet" type="text/css" href="/staticfile/css/flatpickr.min.css">  
</head>
<body>
	<h1 align="center">
        <a style=" text-decoration:none;color:#000000;" href="/index.html">词频搜索可视化</a>
        <input id="checkdate" value="Select date" style="height: 20px;border: 0px;font-size: 15px;"  />
    </h1>
	<div id="main_char" style="width:720px;height:480px;margin: 0 auto;"></div> 
	<div id="main_col" style="width:720px;height:480px;margin: 0 auto;"></div>  
	<div id="main_round" style="width:720px;height:480px;margin: 0 auto;"></div> 
    <script type="text/javascript" src="/staticfile/js/echarts.min.js"></script>
    <script type="text/javascript" src="/staticfile/js/echarts-wordcloud.min.js"></script>
    <script type="text/javascript" src="/staticfile/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/staticfile/js/flatpickr.min.js"></script>
    <script type="text/javascript">
    var chart_char = echarts.init(document.getElementById('main_char'));
    var chart_round = echarts.init(document.getElementById('main_col'));
    var chart_col = echarts.init(document.getElementById('main_round')); 
    function getDateParse(offset) {
        var today = new Date();
        today.setDate(today.getDate()+offset);
        var y = today.getFullYear();
        var m = today.getMonth()+1;
        m = m>9?m:'0'+m;
        var d = today.getDate();
        d = d>9?d:'0'+d;
        return y+"-"+m+"-"+d;
    } 
    $("#checkdate").val(getDateParse(-1));
    var option_char = {
    	title: {
    		text:'搜索榜-字符云'
    	},
        tooltip: {},
        series: [ {
            type: 'wordCloud',
            gridSize: 15,
            sizeRange: [18, 50],
            rotationRange: [-90, 90],
            shape: 'pentagon',
            width: 600,
            height: 400,
            drawOutOfBound: true,
            textStyle: {
                normal: {
                    color: function () {
                        return 'rgb(' + [
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160)
                        ].join(',') + ')';
                    }
                },
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            data: []
        } ]
    };
	option_col = {
		title: {
		    x: 'left',
		    text: '搜索榜-柱形图',
		},
		tooltip: {
		    trigger: 'item'
		},
		toolbox: {
		    show: true,
		    feature: {
		        dataView: {show: true, readOnly: false},
		        restore: {show: true},
		        saveAsImage: {show: true}
		    }
		},
		calculable: true,
		grid: {
		    borderWidth: 0,
		    y: 80,
		    y2: 60
		},
		xAxis: [
		    {
		        type: 'category',
		        show: false,
		        data: []
		    }
		],
		yAxis: [
		    {
		        type: 'value',
		        show: false
		    }
		],
		series: [
		    {
		        name: '搜索次数',
		        type: 'bar',
		        itemStyle: {
		            normal: {
		                color: function(params) {
		                    var colorList = [
		                      '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
		                       '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
		                       '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
		                    ];
		                    return colorList[params.dataIndex];
		                },
		                label: {
		                    show: true,
		                    position: 'top',
		                    formatter: '{b}\n{c}'
		                }
		            }
		        },
		        data: []
			}
		]
	};
    option_round = {
        title : {
            text: '搜索榜-饼形图',
            x:'left'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'center',
            data:[]
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [

            {
                name:'搜索次数',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[]
            }
        ]
    };
    function getAjax() {
        $.ajax({
            type: "post",
            async: true,
            url: "/word.html",
            data: {"checkDate": $("#checkdate").val()},
            dataType: "json",
            success: function(result) {
                if (result != null && result.length > 0) {
                    option_char.series[0].data=[];
                    option_col.xAxis[0].data=[];
                    option_col.series[0].data=[];
                    option_round.series[0].data=[];
                    option_round.legend.data=[];   
                    for (var i = 0; i < result.length; i++) {
                        option_char.series[0].data.push(result[i]);
                        if(i<8) {
                            option_col.xAxis[0].data.push(result[i].name);
                            option_col.series[0].data.push(result[i].value);
                        }
                        if(i<6) {
                        	 option_round.series[0].data.push(result[i]);
                             option_round.legend.data.push(result[i].name);   
                        }
                    }
                    chart_char.setOption(option_char);   
                    chart_col.setOption(option_col);
                    chart_round.setOption(option_round);
                    setTimeout(function (){
                    window.onresize = function () {
                        chart_col.resize();  
                        chart_char.resize();
                        chart_round.resize(); 
                    };
                },200);
                } else {
                    alert("图表数据为空！");
                }
            },
            error: function(errorMsg) {
                alert("图表加载失败!");
            }
        });        
    }
    getAjax();
    var check_in = document.getElementById("checkdate").flatpickr({
        onChange:function() {   
            getAjax();
        }
    });
	</script>                  
</body>
</html>