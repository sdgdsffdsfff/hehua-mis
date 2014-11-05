<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>

<jsp:include page="/template/reportHeader.jsp">
    <jsp:param name="title" value="数据分析"></jsp:param>
</jsp:include>
<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="report"></jsp:param>
    <jsp:param name="L2" value="report_channel_kpi"></jsp:param>
</jsp:include>
<div class="container">

    <div class="highlight">
        <span>渠道:</span>
        <c:forEach var="channel" items="${summaryKpiDayChannelList}" varStatus="i">
            <c:if test="${channel.value != 'UNDEFINED' && channel.value != 'MULTIPLE'}">
                <c:choose>
                    <c:when test="${i.index == 0}">
                        <a class="btn btn-success channel" value="${channel.value}">${channel.value}</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-default channel" value="${channel.value}">${channel.value}</a>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
        <a class="btn btn-default channel" value="MULTIPLE">跨渠道</a>


    </div>
    <div id="channelToTotal">
        <div class="highlight">
            <h4 class="text-success">1. 应用拉新转化&nbsp;&nbsp;&nbsp;渠道(蓝)\总体(灰)</h4>

            <div class="row" style="margin-top: -25px;">
                <div class="col-md-11">
                    <h4 class="page-header">1.1 注册转化率</h4>
                </div>
                <div class="col-md-5">
                    <div>
                        注册转化率 = （未登录访问）注册请求率 * 请求完成率
                        <canvas id="anonymousToRegloginFunnel" height="400" width="500"></canvas>
                    </div>

                </div>

                <div class="col-md-5 col-md-offset-1">
                    <div>
                        注册请求率 : 未登录访问 -> 请求注册验证码
                        <canvas id="anonymousToRegrequestFunnel" height="200" width="500"></canvas>
                        请求完成率 : 请求注册验证码 -> 完成注册并登录
                        <canvas id="RegrequestToRegloginFunnel" height="200" width="500"></canvas>
                    </div>

                </div>
            </div>

            <div class="row">
                <div class="col-md-11">
                    <h4 class="page-header">1.2 其他参考指标</h4>
                </div>
                <div class="col-md-5">
                    <div style="text-align: center"><h3>来访情况</h3></div>
                    <div>
                        访问人次（一个session会话为一次）
                        <canvas id="traceCountValue" height="200" width="500"></canvas>
                        未登录访问人次
                        <canvas id="anonymousTraceCountValue" height="200" width="500"></canvas>
                    </div>

                </div>

                <div class="col-md-5 col-md-offset-1">
                    <div style="text-align: center"><h3>流失参考</h3></div>
                    <div>
                        请求注册验证码出错人次
                        <canvas id="errorRegrequestTraceCountValue" height="200" width="500"></canvas>
                        完成注册并登录出错人次
                        <canvas id="errorRegloginTraceCountValue" height="200" width="500"></canvas>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <div id="channelFunnel">
        <div class="highlight">
            <h4 class="text-success">2. 用户交易转化</h4>

            <div class="row" style="margin-top: -25px;">
                <div class="col-md-11">
                    <h4 class="page-header">2.1 活跃 -> 下单</h4>
                </div>
                <div class="col-md-5">
                    <div>
                        活跃下单率 = （活跃用户）结帐请求率 * 订单生成率
                        <canvas id="userToOrderFunnel" height="400" width="500"></canvas>
                    </div>

                </div>

                <div class="col-md-5 col-md-offset-1">
                    <div>
                        结帐请求率
                        <canvas id="userToPayinfoFunnel" height="200" width="500"></canvas>
                        订单生成率
                        <canvas id="payinfoToOrderFunnel" height="200" width="500"></canvas>
                    </div>

                </div>
            </div>

            <div class="row">
                <div class="col-md-11">
                    <h4 class="page-header">2.2 下单 -> 支付</h4>
                </div>
                <div class="col-md-5">
                    <div>
                        下单用户支付率 = 下单用户数 / 完成支付用户数
                        <canvas id="orderUserToPayFunnel" height="400" width="500"></canvas>
                    </div>

                </div>
            </div>
        </div>
    </div>


</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

<script>
var xaxisOrder = ${xaxis_day};

var recordObject = {
    <c:forEach var="record" items="${summaryKpiDayList}" varStatus="i">
    "${record.dt}_${record.channelid}": {
        tracecount: ${record.tracecount},
        anonymous_tracecount: ${record.anonymous_tracecount},
        anonymous_regrequest_tracecount: ${record.anonymous_regrequest_tracecount},
        regrequest_reglogin_tracecount: ${record.regrequest_reglogin_tracecount},
        erroranonymous_tracecount: ${record.erroranonymous_tracecount},
        errorregrequest_tracecount: ${record.errorregrequest_tracecount},
        errorreglogin_tracecount: ${record.errorreglogin_tracecount},
        usercount: ${record.usercount},
        payinfo_usercount: ${record.payinfo_usercount},
        payreturn_usercount: ${record.payreturn_usercount},
        order_usercount: ${record.order_usercount},
        ordercount: ${record.ordercount},
        payreturn_ordercount: ${record.payreturn_ordercount},
        orderamount: ${record.orderamount},
        payamount: ${record.payamount}
    },
    </c:forEach>
};

function getValues(data, keys, flag) {
    var result = {};
    var denominator = {};
    for (var i in keys) {
        result[keys[i].toString()] = 0;
        denominator[keys[i].toString()] = 0;
    }
    var filter = function (channel) {
        return true;
    }
    switch (flag["method"]) {
        case "include":
            filter = function (channel) {
                return flag["channels"].indexOf(channel) >= 0;
            };
            break;
        case "exclude":
            filter = function (channel) {
                return flag["channels"].indexOf(channel) < 0;
            };
            break;
        default :
            console.log("Channels have not be filtered.");
            break;
    }
    switch (flag["type"]) {
        case "value":
            for (var ref in data) {
                var datekey = ref.substr(0, ref.indexOf("_"));
                var channel = ref.substr(ref.indexOf("_") + 1);
                if (filter(channel)) {
                    result[datekey] += data[ref][flag["field"]];
                }
            }
            ;
            break;
        case "ratio":
            for (var ref in data) {
                var datekey = ref.substr(0, ref.indexOf("_"));
                var channel = ref.substr(ref.indexOf("_") + 1);
                denominator [datekey] += data[ref][flag["field"]];
                if (filter(channel)) {
                    result[datekey] += data[ref][flag["field"]];
                }
            }
            ;
            for (i in keys) {
                result[keys[i].toString()] = denominator[keys[i].toString()] == 0 ? 0 : Math.round(result[keys[i].toString()] / denominator[keys[i].toString()] * 1000) / 10;
            }
            ;
            break;
        case "funnel":
            for (var ref in data) {
                var datekey = ref.substr(0, ref.indexOf("_"));
                var channel = ref.substr(ref.indexOf("_") + 1);
                if (filter(channel)) {
                    denominator[datekey] += data[ref][flag["field_denominator"]];
                    result[datekey] += data[ref][flag["field"]];
                }
            }
            ;
            for (i in keys) {
                result[keys[i].toString()] = denominator[keys[i].toString()] == 0 ? 0 : Math.round(result[keys[i].toString()] / denominator[keys[i].toString()] * 1000) / 10;
            }
            ;

    }
    return result;
}
;

var getData = function (config) {
    return Object.create({
        labels: xaxisOrder,
        datasets: [
            {
                label: config["grey"]["field"],
                fillColor: "rgba(220,220,220,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(220,220,220,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: getValues(recordObject, xaxisOrder,
                        {
                            "field": config["grey"]["field"],
                            "field_denominator": config["grey"]["field_denominator"],
                            "type": config["type"],
                            "method": config["grey"]["method"],
                            "channels": config["grey"]["channels"]
                        })
            },
            {
                label: config["blue"]["field"],
                fillColor: "rgba(151,187,205,0.2)",
                strokeColor: "rgba(151,187,205,1)",
                pointColor: "rgba(151,187,205,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(151,187,205,1)",
                data: getValues(recordObject, xaxisOrder,
                        {
                            "field": config["blue"]["field"],
                            "field_denominator": config["blue"]["field_denominator"],
                            "type": config["type"],
                            "method": config["blue"]["method"],
                            "channels": config["blue"]["channels"]
                        })
            }
        ]

    });
};

var getchannels = function () {
    var boxes = document.getElementsByName("channel");
    var channelsChecked = [];
    for (var i in boxes) {
        if (boxes[i].checked) {
            channelsChecked.push(boxes[i].value);
        }
    }
    return channelsChecked;
};

var drawLine = function (id, config) {
    var canvas = document.getElementById(id);
    var context = canvas.getContext("2d");
    context.clearRect(0, 0, canvas.width, canvas.height);
    new Chart(context).Line(getData(config), {responsive: true, animation: false});
};

var drawRegFunnelChannelVsTotal = function (channelsToInclude) {
    drawLine("anonymousToRegloginFunnel", {
        "grey": {
            "field": "regrequest_reglogin_tracecount",
            "field_denominator": "anonymous_tracecount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "regrequest_reglogin_tracecount",
            "field_denominator": "anonymous_tracecount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "funnel"
    });

    drawLine("anonymousToRegrequestFunnel", {
        "grey": {
            "field": "anonymous_regrequest_tracecount",
            "field_denominator": "anonymous_tracecount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "anonymous_regrequest_tracecount",
            "field_denominator": "anonymous_tracecount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "funnel"
    });

    drawLine("RegrequestToRegloginFunnel", {
        "grey": {
            "field": "regrequest_reglogin_tracecount",
            "field_denominator": "anonymous_regrequest_tracecount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "regrequest_reglogin_tracecount",
            "field_denominator": "anonymous_regrequest_tracecount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "funnel"
    });

};

var drawRegOthersChannelVsTotal = function (channelsToInclude) {
    drawLine("traceCountValue", {
        "grey": {
            "field": "tracecount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "tracecount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("anonymousTraceCountValue", {
        "grey": {
            "field": "anonymous_tracecount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "anonymous_tracecount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("errorRegrequestTraceCountValue", {
        "grey": {
            "field": "errorregrequest_tracecount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "errorregrequest_tracecount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("errorRegloginTraceCountValue", {
        "grey": {
            "field": "errorreglogin_tracecount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "errorreglogin_tracecount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });
};

var drawOrderFunnelChannelVsTotal = function (channelsToInclude) {
    drawLine("userToOrderFunnel", {
        "grey": {
            "field": "order_usercount",
            "field_denominator": "usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "order_usercount",
            "field_denominator": "usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "funnel"
    });

    drawLine("userToPayinfoFunnel", {
        "grey": {
            "field": "payinfo_usercount",
            "field_denominator": "usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "payinfo_usercount",
            "field_denominator": "usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "funnel"
    });

    drawLine("payinfoToOrderFunnel", {
        "grey": {
            "field": "order_usercount",
            "field_denominator": "payinfo_usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "order_usercount",
            "field_denominator": "payinfo_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "funnel"
    });
};

var drawPayFunnelChannelVsTotal = function (channelsToInclude) {
    drawLine("orderUserToPayFunnel", {
        "grey": {
            "field": "payreturn_usercount",
            "field_denominator": "order_usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "payreturn_usercount",
            "field_denominator": "order_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "funnel"
    });
};


$(function () {
    $(".channel").click(function () {
        var channelsToInclude = $(this).attr("value");
        $(".channel").removeClass("btn-success").addClass("btn-default");
        $(this).removeClass("btn-default").addClass("btn-success");
        drawRegFunnelChannelVsTotal(channelsToInclude);
        drawRegOthersChannelVsTotal(channelsToInclude);
        drawOrderFunnelChannelVsTotal(channelsToInclude);
        drawPayFunnelChannelVsTotal(channelsToInclude);
    });
});

window.onload = function () {
    var channelsToInclude = $(".channel").first().attr("value");
    $(this).removeClass("btn-default").addClass("btn-success");
    drawRegFunnelChannelVsTotal(channelsToInclude);
    drawRegOthersChannelVsTotal(channelsToInclude);
    drawOrderFunnelChannelVsTotal(channelsToInclude);
    drawPayFunnelChannelVsTotal(channelsToInclude);
}

</script>
