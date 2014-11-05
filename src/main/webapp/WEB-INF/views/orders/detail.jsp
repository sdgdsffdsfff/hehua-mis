<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>


<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="订单详情页"></jsp:param>
</jsp:include>
<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="orders"></jsp:param>
</jsp:include>

<div class="container">
    <div class="row-fluid" style="padding-top: 10px">
        <h3>订单详情</h3>
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>订单号</th>
                <th>订单状态</th>
                <th>订单总价</th>
                <th>物流费</th>
                <th>实际支付</th>
                <th>支付类型</th>
                <th>下单时间</th>
                <th>支付时间</th>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td><a href="/orders/detail/${order.id}">${order.id}</a></td>
                    <td>${orderStatusMap[order.status]}</td>
                    <td>${order.totalfee}</td>
                    <td>${order.deliveryfee}</td>
                    <td>${order.payed}</td>
                    <td>${payMap[order.paytype]}</td>
                    <td>
                        <fmt:formatDate value="${order.getOrderDate()}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    <td>
                        <c:if test="${order.paytime > 0}">
                            <fmt:formatDate value="${order.getPayDate()}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </c:if>
                    </td>
                </tr>
            </tbody>
        </table>

        <c:if test="${order.hasPayed()}">
            <h3>支付账号信息</h3>
            <p>${buyerAccount}</p>
        </c:if>

        <h3>操作</h3>
        <c:choose>
            <c:when test="${order.isStatusPayed()}">
                <button class="btn btn-primary" data-toggle="model" onclick="displaySetDeliveriedDialog()">设为已发货</button>
            </c:when>
            <c:when test="${order.isStatusDeliveried()}">
                <button class="btn btn-primary" onclick="displaySetSignedDialog()">设为已签收</button>
            </c:when>
        </c:choose>

        <h3>购买商品</h3>
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>商品ID</th>
                <th>商品名称</th>
                <th>SKUID</th>
                <th>商品属性</th>
                <th>数量</th>
                <th>价格</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orderInfo.itemInfo}" var="itemInfo">
            <tr>
                <td>${itemInfo.itemid}</td>
                <td>${itemInfo.title}</td>
                <td>${itemInfo.skuid}</td>
                <td>
                <c:forEach items="${itemInfo.types}" var="itemType">
                    ${itemType.name}:${itemType.value},
                </c:forEach>
                </td>
                <td>${itemInfo.quantity}</td>
                <td>${itemInfo.price}</td>
            </tr>
            </c:forEach>
            </tbody>
        </table>

        <c:if test="${refundStatusMap != null}">
        <h3>退款详情</h3>
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>退款商品id</th>
                <th>商品名称</th>
                <th>skuid</th>
                <th>商品属性</th>
                <th>价格</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${refundItemInfos}" var="refundItemInfo">
                <tr>
                    <td>${refundItemInfo.itemid}</td>
                    <td>${refundItemInfo.title}</td>
                    <td>${refundItemInfo.skuid}</td>
                    <td>
                        <c:forEach items="${refundItemInfo.types}" var="itemType">
                            ${itemType.name}:${itemType.value},
                        </c:forEach>
                    </td>
                    <td>${refundItemInfo.price}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>退款ID</th>
                <th>退款状态</th>
                <th>最多可退（元）</th>
                <th>详情</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orderInfo.refundInfo}" var="refundInfo">
                <tr>
                    <td>${refundInfo.id}</td>
                    <td>${refundStatusMap[refundInfo.status]}</td>
                    <td>${refundInfo.money}</td>
                    <td>
                        <c:forEach items="${refundInfo.event}" var="refundEvent">
                            时间：<fmt:formatDate value="${refundEvent.getTimestampDate()}" pattern="yyyy-MM-dd HH:mm:ss" />，操作：${refundEventType[refundEvent.type]}，备注：${refundEvent.getComment()}<br />
                        </c:forEach>
                    </td>
                    <td>
                        <c:if test="${refundInfo.status == 0}">
                            <button class="btn btn-primary" data-toggle="model" onclick="displayRefundApprove1Dialog(${refundInfo.id})">审核通过</button>
                            <button class="btn btn-primary" data-toggle="model" onclick="displayRefundReject1Dialog(${refundInfo.id})">审核拒绝</button>
                        </c:if>
                        <c:if test="${refundInfo.status == 2}">
                            <button class="btn btn-primary" data-toggle="model" onclick="displayRefundApprove2Dialog(${refundInfo.id})">退货成功</button>
                            <button class="btn btn-primary" data-toggle="model" onclick="displayRefundReject2Dialog(${refundInfo.id})">退货失败</button>
                        </c:if>
                        <c:if test="${refundInfo.status == 8 || refundInfo.status == 12}">
                            <c:if test="${!empty misfn:menu('refund')}">
                                <button class="btn btn-primary" data-toggle="model" onclick="displayWithdrawDialog(${refundInfo.id}, ${refundInfo.money})">${misfn:menu('refund').name}</button>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:if>

        <h3>物流信息</h3>
        <table class="table table-bordered table-striped table-hover">
            <tr>
                <th>收货人</th><td>${misfn:escapeHtml(orderInfo.address.name)}</td>
            </tr>
            <tr>
                <th>电话</th><td>${orderInfo.address.phone}</td>
            </tr>
            <tr>
                <th>收货地址</th><td>${orderInfo.address.province} ${orderInfo.address.city} ${orderInfo.address.county} ${misfn:escapeHtml(orderInfo.address.detail)} (${misfn:escapeHtml(orderInfo.address.postCode)})</td>
            </tr>
            <tr>
                <th>物流公司</th><td>${orderInfo.deliveryInfo.deliveryCompany}</td>
            </tr>
            <tr>
                <th>物流单号</th><td>${orderInfo.deliveryInfo.deliveryNum}</td>
            </tr>
            <tr>
                <th>物流详情</th>
                <td>
                    <c:forEach var="traceInfo" items="${orderInfo.deliveryInfo.traceInfo}">
                        ${traceInfo.time}：${traceInfo.event}<br />
                    </c:forEach>
                </td>
            </tr>
        </table>

        <h3>发票信息</h3>
        <table class="table table-bordered table-striped table-hover">
            <tr>
                <th>发票类型</th><td>${invoiceMap[orderInfo.invoiceType]}</td>
            </tr>
            <tr>
                <th>发票备注</th><td>${orderInfo.invoiceComment}</td>
            </tr>
        </table>

    </div>

</div>

<div class="modal fade bs-example-modal-lg" id="setDeliveriedModal" role="dialog" aria-labelledby="setDeliveriedModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="setDeliveriedModalLabel">订单设为已发货</h4>
            </div>
            <div class="modal-body">
                <span class="markDisplay">*</span>物流公司
                <select class="form-control" id="deliveryCompanyPinyin">
                    <c:forEach var="deliveryCompanyInfo" items="${deliveryCompanyInfos}">
                        <c:choose>
                            <c:when test="${deliveryCompanyInfo.deliveryComPinyin.equals(\"yuantong\")}">
                                <option value="${deliveryCompanyInfo.deliveryComPinyin}" selected>${deliveryCompanyInfo.deliveryCompany}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${deliveryCompanyInfo.deliveryComPinyin}">${deliveryCompanyInfo.deliveryCompany}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                <br>
                <span class="markDisplay">*</span>物流单号
                <div id="deliveryNumDiv"></div>
                <input  id="deliveryNum" class="input-sm" value="5735417469" />
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" onclick="setDeliveried(${order.id})" class="btn btn-primary">设为已发货</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade bs-example-modal-lg" id="withdrawModal" role="dialog" aria-labelledby="withdrawModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="withdrawModalLabel">退款至原支付方</h4>
            </div>
            <div class="modal-body">
                <span>最多可退：</span>${orderInfo.refundInfo[0].money}
                <br>
                <span class="markDisplay">*</span>实退金额：
                <div id="money">
                    <input  id="refundMoney" class="input-sm" value="" />
                </div>
                <input type="hidden" id="refundid" value="" />
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" onclick="withdraw()" class="btn btn-primary">立即退款</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function displaySetDeliveriedDialog() {
        $('#setDeliveriedModal').modal('toggle');
    }
    function setDeliveried(orderid) {
        var deliveryCompanyPinyin = $('#deliveryCompanyPinyin').val();
        var deliveryNum = $('#deliveryNum').val();
        //alert(orderid + " " + deliveryCompanyPinyin + " " + deliveryNum);
        $.ajax({
            type: "POST",
            url:"/orders/setdeliveried/" + orderid,
            data:{"deliveryComPinyin":deliveryCompanyPinyin,"deliveryNum":deliveryNum},
            dataType:"json",
            success: function(data) {
                if (data.code == 0) {
                    alert(data.message);
                    $('#setDeliveriedModal').modal('toggle');
                } else {
                    alert(data.message);
                }
            }
        });
    }

    function displaySetSignedDialog() {
        if (confirm("确定设置订单为已签收？")) {
            $.ajax({
                type: "POST",
                url:"/orders/setsigned/" + ${order.id},
                dataType:"json",
                success: function(data) {
                    alert(data.message);

                }
            });
        }
    }

    function displayRefundApprove1Dialog(refundid) {
        if (confirm("确定审核通过？")) {
            $.ajax({
                type: "POST",
                url:"/refund/approve1/" + refundid,
                dataType:"json",
                success: function(data) {
                    alert(data.message);
                }
            });
        }
    }

    function displayRefundReject1Dialog(refundid) {
        if (confirm("确定审核拒绝？")) {
            $.ajax({
                type: "POST",
                url:"/refund/reject1/" + refundid,
                dataType:"json",
                success: function(data) {
                    alert(data.message);
                }
            });
        }
    }

    function displayRefundApprove2Dialog(refundid) {
        if (confirm("确定收到货且符合退款要求？请谨慎点击！")) {
            $.ajax({
                type: "POST",
                url:"/refund/approve2/" + refundid,
                dataType:"json",
                success: function(data) {
                    alert(data.message);
                }
            });
        }
    }

    function displayRefundReject2Dialog(refundid) {
        if (confirm("确定收货失败？")) {
            $.ajax({
                type: "POST",
                url:"/refund/reject2/" + refundid,
                dataType:"json",
                success: function(data) {
                    alert(data.message);
                }
            });
        }
    }

    function displayWithdrawDialog(refundid, refundMoney) {
        $('#refundMoney').val(refundMoney);
        $('#refundid').val(refundid);
        $('#withdrawModal').modal('toggle');
    }

    function withdraw() {
        if (confirm("确定立即退款给用户？如果确定，请去了支付宝后输入密码执行退款，否则点取消！")) {
            refundid = $('#refundid').val();
            refundMoney = $('#refundMoney').val();
            $.ajax({
                type: "POST",
                url:"/refund/thirdparty",
                data:{"refundid":refundid,"money":refundMoney},
                dataType:"json",
                success: function(data) {
                    if (data.code == 0) {
                        window.location = data.data.refundUrl;
                    } else {
                        alert(data.message);
                        $('#withdrawModal').modal('toggle');
                    }
                }
            });
        }
    }
</script>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>
