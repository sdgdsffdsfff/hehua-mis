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
    <jsp:param name="L2" value="report_channel_order"></jsp:param>
</jsp:include>

<div class="container">
    <div class="highlight">
        <span>渠道:</span>
        <c:forEach var="channel" items="${summaryOrderDayChannelList}" varStatus="i">
            <c:if test="${channel.value != 'UNDEFINED'}">
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
        <a class="btn btn-default channel" value="UNDEFINED">未知渠道</a>
    </div>
    <div id="channelToTotal">
        <div class="highlight">
            <h4 class="text-success">1. 渠道对比总体&nbsp;&nbsp;&nbsp;渠道(蓝)\总体(灰)</h4>

            <div class="row" style="margin-top: -25px;">
                <div class="col-md-11">
                    <h4 class="page-header">1.1 下单情况</h4>
                </div>
                <div class="col-md-5">
                    <div style="text-align: center"><h3>数量</h3></div>
                    <div>
                        订单数
                        <canvas id="orderCountValue" height="200" width="500"></canvas>
                        订单数渠道占比
                        <canvas id="orderCountRatio" height="200" width="500"></canvas>
                    </div>

                </div>

                <div class="col-md-5 col-md-offset-1">
                    <div style="text-align: center"><h3>金额</h3></div>
                    <div>
                        订单金额
                        <canvas id="orderAmountValue" height="200" width="500"></canvas>
                        订单金额渠道占比
                        <canvas id="orderAmountRatio" height="200" width="500"></canvas>
                    </div>

                </div>
            </div>

            <div class="row">
                <div class="col-md-11">
                    <h4 class="page-header">1.2 支付情况</h4>
                </div>
                <div class="col-md-5">
                    <div style="text-align: center"><h3>数量</h3></div>
                    <div>
                        完成支付订单数
                        <canvas id="payreturnOrderCountValue" height="200" width="500"></canvas>
                        完成支付订单数渠道占比
                        <canvas id="payreturnOrderCountRatio" height="200" width="500"></canvas>
                    </div>

                </div>

                <div class="col-md-5 col-md-offset-1">
                    <div style="text-align: center"><h3>金额</h3></div>
                    <div>
                        完成支付金额
                        <canvas id="payAmountValue" height="200" width="500"></canvas>
                        完成支付金额渠道占比
                        <canvas id="payAmountRatio" height="200" width="500"></canvas>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div id="channelFunnel">
        <div class="highlight">
            <h4 class="text-success">2. 渠道转化分析</h4>

            <div class="row" style="margin-top: -25px;">
                <div class="col-md-11">
                    <h4 class="page-header">2.1 下单 -> 支付</h4>
                </div>
                <div class="col-md-5">
                    <div style="text-align: center"><h3>数量</h3></div>
                    <div>
                        支付订单数（蓝） vs 下单数（灰）
                        <canvas id="orderCountToPayValue" height="200" width="500"></canvas>
                        渠道支付订单转化率（蓝） vs 总体支付订单转化率（灰）
                        <canvas id="orderCountToPayFunnel" height="200" width="500"></canvas>
                    </div>

                </div>

                <div class="col-md-5 col-md-offset-1">
                    <div style="text-align: center"><h3>金额</h3></div>
                    <div>
                        支付订单总额（蓝） vs 下单订单总额（灰）
                        <canvas id="orderAmountToPayValue" height="200" width="500"></canvas>
                        渠道支付金额转化率（蓝） vs 总体支付金额转化率（灰）
                        <canvas id="orderAmountToPayFunnel" height="200" width="500"></canvas>
                    </div>

                </div>
            </div>

            <div class="row">
                <div class="col-md-11">
                    <h4 class="page-header">2.2 支付 -> 退款（未来添加）</h4>
                </div>
            </div>
        </div>
    </div>

</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

<script>
var xaxisOrder = ${xaxis_day};

var recordObject = {
    <c:forEach var="record" items="${summaryOrderDayList}" varStatus="i">
    "${record.dt}_${record.channelid}": {
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

var drawOrderChannelVsTotal = function (channelsToInclude) {
    drawLine("orderCountValue", {
        "grey": {
            "field": "ordercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "ordercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("orderCountRatio", {
        "grey": {
            "field": "ordercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "ordercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "ratio"
    });

    drawLine("orderAmountValue", {
        "grey": {
            "field": "orderamount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "orderamount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("orderAmountRatio", {
        "grey": {
            "field": "orderamount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "orderamount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "ratio"
    });
};

var drawPayChannelVsTotal = function (channelsToInclude) {
    drawLine("payreturnOrderCountValue", {
        "grey": {
            "field": "payreturn_ordercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "payreturn_ordercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("payreturnOrderCountRatio", {
        "grey": {
            "field": "payreturn_ordercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "payreturn_ordercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "ratio"
    });

    drawLine("payAmountValue", {
        "grey": {
            "field": "payamount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "payamount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("payAmountRatio", {
        "grey": {
            "field": "payamount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "payamount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "ratio"
    });
};


var drawFunnelChannel = function (channelsToInclude) {
    drawLine("orderCountToPayValue", {
        "grey": {
            "field": "ordercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "blue": {
            "field": "payreturn_ordercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("orderAmountToPayValue", {
        "grey": {
            "field": "orderamount",
            "method": "include",
            "channels": channelsToInclude
        },
        "blue": {
            "field": "payamount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("orderCountToPayFunnel", {
        "grey": {
            "field": "payreturn_ordercount",
            "field_denominator": "ordercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "payreturn_ordercount",
            "field_denominator": "ordercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "funnel"
    });

    drawLine("orderAmountToPayFunnel", {
        "grey": {
            "field": "payamount",
            "field_denominator": "orderamount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "payamount",
            "field_denominator": "orderamount",
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
        drawOrderChannelVsTotal(channelsToInclude);
        drawPayChannelVsTotal(channelsToInclude);
        drawFunnelChannel(channelsToInclude);
    });
});

window.onload = function () {
    var channelsToInclude = $(".channel").first().attr("value");
    $(this).removeClass("btn-default").addClass("btn-success");
    drawOrderChannelVsTotal(channelsToInclude);
    drawPayChannelVsTotal(channelsToInclude);
    drawFunnelChannel(channelsToInclude);
}

</script>
