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
    <jsp:param name="L2" value="report_channel_user"></jsp:param>
</jsp:include>

<div class="container">
    <div class="highlight">
        <span>渠道:</span>
        <c:forEach var="channel" items="${summaryUserDayChannelList}" varStatus="i">
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
        <a class="btn btn-default channel" value="UNDEFINED">未知渠道</a>
    </div>
    <div id="channelToTotal">
        <div class="highlight">
            <h4 class="text-success">渠道(蓝)\总体(灰)</h4>

            <div class="row" style="margin-top: -25px;">
                <div class="col-md-5" style="text-align: center"><h4>数量</h4></div>
                <div class="col-md-5 col-md-offset-1" style="text-align: center"><h4>占比</h4></div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div>
                        活跃用户数
                        <canvas id="userCountValue" height="200" width="500"></canvas>
                        注册用户数
                        <canvas id="regloginUserCountValue" height="200" width="500"></canvas>
                        下单用户数
                        <canvas id="orderUserCountValue" height="200" width="500"></canvas>
                        购买用户数
                        <canvas id="payreturnUserCountValue" height="200" width="500"></canvas>
                    </div>

                </div>

                <div class="col-md-5 col-md-offset-1">
                    <div>
                        活跃用户占比
                        <canvas id="userCountRatio" height="200" width="500"></canvas>
                        注册用户占比
                        <canvas id="regloginUserCountRatio" height="200" width="500"></canvas>
                        下单用户占比
                        <canvas id="orderUserCountRatio" height="200" width="500"></canvas>
                        购买用户占比
                        <canvas id="payreturnUserCountRatio" height="200" width="500"></canvas>

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
    <c:forEach var="record" items="${summaryUserDayList}" varStatus="i">
    "${record.dt}_${record.channelid}": {
        usercount: ${record.usercount},
        reglogin_usercount: ${record.reglogin_usercount},
        loadcart_usercount: ${record.loadcart_usercount},
        payinfo_usercount: ${record.payinfo_usercount},
        order_usercount: ${record.order_usercount},
        payreturn_usercount: ${record.payreturn_usercount},
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

var drawUserChannelVsTotal = function (channelsToInclude) {
    drawLine("userCountValue", {
        "grey": {
            "field": "usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("userCountRatio", {
        "grey": {
            "field": "usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "ratio"
    });

    drawLine("regloginUserCountValue", {
        "grey": {
            "field": "reglogin_usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "reglogin_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("regloginUserCountRatio", {
        "grey": {
            "field": "reglogin_usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "reglogin_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "ratio"
    });

    drawLine("orderUserCountValue", {
        "grey": {
            "field": "order_usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "order_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("orderUserCountRatio", {
        "grey": {
            "field": "order_usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "order_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "ratio"
    });

    drawLine("payreturnUserCountValue", {
        "grey": {
            "field": "payreturn_usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "payreturn_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "value"
    });

    drawLine("payreturnUserCountRatio", {
        "grey": {
            "field": "payreturn_usercount",
            "method": "exclude",
            "channels": ["TEST_IOS", "TEST_ANDROID"]
        },
        "blue": {
            "field": "payreturn_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        "type": "ratio"
    });
};

$(function () {
    $(".channel").click(function () {
        var channelsToInclude = $(this).attr("value");
        $(".channel").removeClass("btn-success").addClass("btn-default");
        $(this).removeClass("btn-default").addClass("btn-success");
        drawUserChannelVsTotal(channelsToInclude);
    });
});


window.onload = function () {
    var channelsToInclude = $(".channel").first().attr("value");
    $(this).removeClass("btn-default").addClass("btn-success");
    drawUserChannelVsTotal(channelsToInclude);

}


</script>
