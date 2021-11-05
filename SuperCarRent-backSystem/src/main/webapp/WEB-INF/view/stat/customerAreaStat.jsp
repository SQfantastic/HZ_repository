<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>客户地区统计页面</title>

</head>
<body>
<div id="container" style="height: 100%"></div>
<script src="${ctx}/resources/js/echarts.js"></script>
<script src="${ctx}/resources/js/jquery-3.4.1.js"></script>
<script type="text/javascript">
    $.get("${ctx}/stat/customer?method=loadCustomerAreaStatic", function (data) {
        var dom = document.getElementById("container");
        var myChart = echarts.init(dom);
        var app = {};
        option = null;
        option = {
            title: {
                text: '客户地区统计',
                subtext: 'provided by LHZ',
                x: 'center',
                textStyle: {
                    fontWeight: 'bold',
                    fontSize: 28

                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: data,
            },
            series: [
                {
                    name: '客户数量及占比',
                    type: 'pie',
                    radius: '70%',//圆的大小
                    center: ['50%', '60%'],
                    data: data,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        },
                    },
                    label:{
                        //设置饼状图的字体大小
                        textStyle : {
                            fontWeight : 'normal',
                            fontSize : 30,
                        }
                    }
                }
            ]
        };
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }


    })
</script>
</body>
</html>
