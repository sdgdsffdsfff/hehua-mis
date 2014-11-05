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
    <jsp:param name="L2" value="report_week"></jsp:param>
</jsp:include>
<div class="container">

    <div class="highlight">
        <span>渠道:</span>
        <a class="btn btn-success channel" value="ALL">全渠道</a>
        <c:forEach var="channel" items="${summaryChannelWeekList}" varStatus="i">
            <c:if test="${channel.value != 'UNDEFINED' && channel.value != 'MULTIPLE'}">
                <a class="btn btn-default channel" value="${channel.value}">${channel.value}</a>
            </c:if>
        </c:forEach>
        <a class="btn btn-default channel" value="MULTIPLE">跨渠道</a>
        <a class="btn btn-default channel" value="UNDEFINED">未知渠道</a>
    </div>


    <div class="row">
        <div class="col-md-11"><h4 class="page-header">1. 交易详情</h4></div>
        <div class="col-md-5">
            <div><canvas id="orderFunnel" height="300" width="500"></canvas></div>
        </div>
        <div class="col-md-5 col-md-offset-1">
            <div><canvas id="orderValue" height="300" width="500"></canvas></div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-11"><h4 class="page-header">2. 应用拉新转化</h4></div>
        <div class="col-md-5">
            <div>注册转化率 = （未登录访问）注册请求率 * 请求完成率</div>
            <div>注册请求率：未登录访问 -> 请求注册验证码</div>
            <div>请求完成率：请求注册验证码 -> 完成注册并登录</div>
            <div><canvas id="regFunnel" height="300" width="500"></canvas></div>
        </div>
        <div class="col-md-5 col-md-offset-1">
            <div>启动设备数：卸载应用并重新安装且启动会重复计算设备数</div>
            <div>访问人次：相邻操作间隔不超过半小时的连续动作集合（Session）</div>
            <div>**注：一台设备可能产生多次访问（多次访问的间隔大于半小时）</div>
            <div><canvas id="visitValue" height="300" width="500"></canvas></div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-11"><h4 class="page-header">3. 用户交易转化</h4></div>
        <div class="col-md-5">
            <div>交易转化率</div>
            <div><canvas id="buyFunnel" height="300" width="500"></canvas></div>
        </div>
        <div class="col-md-5 col-md-offset-1">
            <div>参与用户数</div>
            <div><canvas id="buyValue" height="300" width="500"></canvas></div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-11"><h4 class="page-header">4. 用户留存</h4></div>
        <div class="col-md-5">
            <div>活跃留存分布</div>
            <div><canvas id="retentionFunnel" height="300" width="500"></canvas></div>
        </div>
        <div class="col-md-5 col-md-offset-1">
            <div>活跃用户数</div>
            <div><canvas id="retentionValue" height="300" width="500"></canvas></div>
        </div>
    </div>
   <div class="row">
        <div class="col-md-5">
            <div>购买留存分布</div>
            <div><canvas id="buyRetentionFunnel" height="300" width="500"></canvas></div>
        </div>
        <div class="col-md-5 col-md-offset-1">
            <div>购买用户数</div>
            <div><canvas id="buyRetentionValue" height="300" width="500"></canvas></div>
        </div>
    </div>


</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

<script>
var xaxisOrder = [<c:forEach var="record" items="${xaxis_week}" varStatus="i">"${record}"<c:if test="${!i.last}">,</c:if></c:forEach>];
var xaxisDisplay = [<c:forEach var="record" items="${xaxis_weekenddate}" varStatus="i">"${record}"<c:if test="${!i.last}">,</c:if></c:forEach>];

var kpiObject = {
    <c:forEach var="record" items="${summaryKpiWeekList}" varStatus="i">
    "${record.yearweek}_${record.channelid}": {
        tracecount: ${record.tracecount},
        anonymous_tracecount: ${record.anonymous_tracecount},
        erroranonymous_tracecount: ${record.erroranonymous_tracecount},
        erroranonymous_noregrequest_tracecount: ${record.erroranonymous_noregrequest_tracecount},
        anonymous_regrequest_tracecount: ${record.anonymous_regrequest_tracecount},
        noanonymous_regrequest_tracecount: ${record.noanonymous_regrequest_tracecount},
        errorregrequest_tracecount: ${record.errorregrequest_tracecount},
        errorregrequest_norelogin_tracecount: ${record.errorregrequest_norelogin_tracecount},
        regrequest_reglogin_tracecount: ${record.regrequest_reglogin_tracecount},
        noregrequest_reglogin_tracecount: ${record.noregrequest_reglogin_tracecount},
        errorreglogin_tracecount: ${record.errorreglogin_tracecount},
        login_tracecount: ${record.login_tracecount},
        errorlogin_tracecount: ${record.errorlogin_tracecount},
        usercount: ${record.usercount},
        reglogin_usercount: ${record.reglogin_usercount},
        loadcart_usercount: ${record.loadcart_usercount},
        payinfo_usercount: ${record.payinfo_usercount},
        order_usercount: ${record.order_usercount},
        payreturn_usercount: ${record.payreturn_usercount},
        ordercount: ${record.ordercount},
        payreturn_ordercount: ${record.payreturn_ordercount},
        errorpayreturn_ordercount: ${record.errorpayreturn_ordercount},
        orderamount: ${record.orderamount},
        payamount: ${record.payamount},
        clientcount: ${record.clientcount},
        reglogin_clientcount: ${record.reglogin_clientcount},
        onesession_clientcount: ${record.onesession_clientcount},
        onelog_clientcount: ${record.onelog_clientcount},
        nouser_clientcount: ${record.nouser_clientcount},
        order_clientcount: ${record.order_clientcount}
    },
    </c:forEach>
};

var userSegmentObject = {
    <c:forEach var="record" items="${summaryUserSegmentWeekList}" varStatus="i">
    "${record.yearweek}_${record.channelid}": {
        usercount: ${record.usercount},
        newreg_usercount: ${record.newreg_usercount},
        retention_usercount: ${record.retention_usercount},
        return_usercount: ${record.return_usercount},
        buy_usercount: ${record.buy_usercount},
        directbuy_usercount: ${record.directbuy_usercount},
        buyandbuy_usercount: ${record.buyandbuy_usercount},
        returnbuy_usercount: ${record.returnbuy_usercount},
        buy_payamount: ${record.buy_payamount},
        directbuy_payamount: ${record.directbuy_payamount},
        buyandbuy_payamount: ${record.buyandbuy_payamount},
        returnbuy_payamount: ${record.returnbuy_payamount}
    },
    </c:forEach>
};

function getValues(data, keys, flag) {
    var result = {};
    var denominator = {};
    for (var i in keys) {
        result[keys[i]] = 0;
        denominator[keys[i]] = 0;
    }
    var filter = function (channel) {
        return true;
    };
    switch (flag["method"]) {
        case "include":
            filter = function (channel) {
                return flag["channels"].indexOf("ALL") >= 0 || flag["channels"].indexOf(channel) >= 0;
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
            for (i in keys) {
                result[keys[i]] = result[keys[i]] % 1 === 0 ? result[keys[i]] : Math.round(result[keys[i]] * 100) / 100;
            }
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
            for (i in keys) {
                result[keys[i]] = denominator[keys[i]] == 0 ? 0 : Math.round(result[keys[i]] / denominator[keys[i]] * 1000) / 10;
            }
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
            for (i in keys) {
                result[keys[i]] = denominator[keys[i]] == 0 ? 0 : Math.round(result[keys[i]] / denominator[keys[i]] * 1000) / 10;
            }
            break;
        case "average":
            for (var ref in data) {
                var datekey = ref.substr(0, ref.indexOf("_"));
                var channel = ref.substr(ref.indexOf("_") + 1);
                if (filter(channel)) {
                    denominator[datekey] += data[ref][flag["field_denominator"]];
                    result[datekey] += data[ref][flag["field"]];
                }
            }
            for (i in keys) {
                result[keys[i]] = denominator[keys[i]] == 0 ? 0 : Math.round(result[keys[i]] / denominator[keys[i]] * 100) / 100;
            }
            break;
    }
    return result;
}

var getData = function (dataObject, config) {
    var data = Object.create({
        labels: xaxisDisplay,
        datasets: []
    });
    for (var key in config) {
        data.datasets.push({
            label: config[key]["label"],
            fillColor: "rgba("+colors[key].getRGB().r+","+colors[key].getRGB().g+","+colors[key].getRGB().b+",0.3)",
            strokeColor: "rgba("+colors[key].getRGB().r+","+colors[key].getRGB().g+","+colors[key].getRGB().b+",1)",
            pointColor: "rgba("+colors[key].getRGB().r+","+colors[key].getRGB().g+","+colors[key].getRGB().b+",1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba("+colors[key].getRGB().r+","+colors[key].getRGB().g+","+colors[key].getRGB().b+",1)",
            data: getValues(dataObject, xaxisOrder, {
                "field": config[key]["field"],
                "field_denominator": config[key]["field_denominator"],
                "type": config[key]["type"],
                "method": config[key]["method"],
                "channels": config[key]["channels"]
            })
        });
    }
    return data;
};

var getchannels = function (buttonGroup) {
    var channelsChecked = [];
    $(buttonGroup).each(function(){
        if ($(this).hasClass("btn-success")) {
            channelsChecked.push($(this).attr("value"))
        }
    });
    return channelsChecked;
};

var chartTable = {};
Chart.defaults.global.responsive = true;
Chart.defaults.global.scaleBeginAtZero = true;
Chart.defaults.global.animation = false;
Chart.defaults.global.multiTooltipTemplate = "<\%= datasetLabel %> : <\%= value %>";

var colors = $.xcolor.tetrad($.xcolor.webround("rgba(151,187,205,1)"));

var drawLine = function (id, dataObject, config) {
    if (!chartTable.hasOwnProperty(id)) {
        chartTable[id] = "surprise!";
        new Chart(document.getElementById(id).getContext("2d")).Line(getData(dataObject, config));
    } else {
        parent = document.getElementById(id).parentNode;
        newCanvas = parent.lastChild.cloneNode(true);
        parent.replaceChild(newCanvas, parent.lastChild);
        new Chart(newCanvas.getContext("2d")).Line(getData(dataObject, config));
    }
};

var refresh = function () {
    var channelsToInclude = getchannels(".channel");
    drawLine("regFunnel", kpiObject, [
        {
            "label": "注册转化率",
            "type": "funnel",
            "field": "regrequest_reglogin_tracecount",
            "field_denominator": "anonymous_tracecount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "注册请求率",
            "type": "funnel",
            "field": "anonymous_regrequest_tracecount",
            "field_denominator": "anonymous_tracecount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "请求完成率",
            "type": "funnel",
            "field": "regrequest_reglogin_tracecount",
            "field_denominator": "anonymous_regrequest_tracecount",
            "method": "include",
            "channels": channelsToInclude
        }
    ]);
    drawLine("visitValue", kpiObject, [
        {
            "label": "启动设备数",
            "type": "value",
            "field": "clientcount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label": "访问人次",
            "type": "value",
            "field": "tracecount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "未登录访问人次",
            "type": "value",
            "field": "anonymous_tracecount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "完成注册访问人次",
            "type": "value",
            "field": "regrequest_reglogin_tracecount",
            "method": "include",
            "channels": channelsToInclude
        }
    ]);
    drawLine("buyFunnel", kpiObject, [
        {
            "label": "活跃用户完成购买率",
            "type": "funnel",
            "field": "payreturn_usercount",
            "field_denominator": "usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "活跃用户下单率",
            "type": "funnel",
            "field": "order_usercount",
            "field_denominator": "usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "下单用户支付率",
            "type": "funnel",
            "field": "payreturn_usercount",
            "field_denominator": "order_usercount",
            "method": "include",
            "channels": channelsToInclude
        }
    ]);
    drawLine("buyValue", kpiObject, [
        {
            "label": "活跃用户数",
            "type": "value",
            "field": "usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label": "购物车请求结帐用户数",
            "type": "value",
            "field": "payinfo_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label": "下单用户数",
            "type": "value",
            "field": "order_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "购买用户数",
            "type": "value",
            "field": "payreturn_usercount",
            "method": "include",
            "channels": channelsToInclude
        }
    ]);
    drawLine("orderFunnel", kpiObject, [
        {
            "label": "订单支付率",
            "type": "funnel",
            "field": "payreturn_ordercount",
            "field_denominator": "ordercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "支付单均价",
            "type": "average",
            "field": "payamount",
            "field_denominator": "payreturn_ordercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "支付客单价",
            "type": "average",
            "field": "payamount",
            "field_denominator": "payreturn_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "人均支付订单",
            "type": "average",
            "field": "payreturn_ordercount",
            "field_denominator": "payreturn_usercount",
            "method": "include",
            "channels": channelsToInclude
        }
    ]);
    drawLine("orderValue", kpiObject, [
        {
            "label": "生成订单数",
            "type": "value",
            "field": "ordercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label": "完成支付订单数",
            "type": "value",
            "field": "payreturn_ordercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label": "完成支付金额",
            "type": "value",
            "field": "payamount",
            "method": "include",
            "channels": channelsToInclude
        }
    ]);
    drawLine("retentionFunnel", userSegmentObject, [
        {
            "label": "新注册活跃用户占比",
            "type": "funnel",
            "field": "newreg_usercount",
            "field_denominator": "usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "连续活跃用户占比",
            "type": "funnel",
            "field": "retention_usercount",
            "field_denominator": "usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "间歇回访活跃用户占比",
            "type": "funnel",
            "field": "return_usercount",
            "field_denominator": "usercount",
            "method": "include",
            "channels": channelsToInclude
        }
    ]);
    drawLine("retentionValue", userSegmentObject, [
        {
            "label": "新增用户",
            "type": "value",
            "field": "newreg_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "连续活跃用户",
            "type": "value",
            "field": "retention_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "间歇回访活跃用户",
            "type": "value",
            "field": "return_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label": "总活跃用户",
            "type": "value",
            "field": "usercount",
            "method": "include",
            "channels": channelsToInclude
        }
    ]);
    drawLine("buyRetentionFunnel", userSegmentObject, [
        {
            "label": "注册当周购买用户占比",
            "type": "funnel",
            "field": "directbuy_usercount",
            "field_denominator": "buy_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "连续购买用户占比",
            "type": "funnel",
            "field": "buyandbuy_usercount",
            "field_denominator": "buy_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "间歇购买用户占比",
            "type": "funnel",
            "field": "returnbuy_usercount",
            "field_denominator": "buy_usercount",
            "method": "include",
            "channels": channelsToInclude
        }
    ]);
    drawLine("buyRetentionValue", userSegmentObject, [
        {
            "label": "注册当周购买用户",
            "type": "value",
            "field": "directbuy_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "连续购买用户",
            "type": "value",
            "field": "buyandbuy_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label" : "间歇购买用户",
            "type": "value",
            "field": "returnbuy_usercount",
            "method": "include",
            "channels": channelsToInclude
        },
        {
            "label": "总购买用户",
            "type": "value",
            "field": "buy_usercount",
            "method": "include",
            "channels": channelsToInclude
        }
    ]);
};

$(function () {
    $(".channel").click(function () {
        $(".channel").removeClass("btn-success").addClass("btn-default");
        $(this).removeClass("btn-default").addClass("btn-success");
        refresh();
    });
});

window.onload = refresh;

</script>
