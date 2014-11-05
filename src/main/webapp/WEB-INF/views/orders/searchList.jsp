<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="订单管理"></jsp:param>
</jsp:include>
<link href="/css/bootstrap-datetimepicker.css" rel="stylesheet">

<script type="text/javascript" src="/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/js/locals/bootstrap-datetimepicker.zh-CN.js"></script>
<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="orders"></jsp:param>
    <jsp:param name="L2" value="orders_list_${slug}"></jsp:param>
</jsp:include>


<div class="container">

    <form role="form" class="well form-search" id="queryForm" action="/order/search" method="post">
        <input name="pageNo" type="hidden" id="pageNo">
            <div class="row div-pad">
                <div class="col-lg-6">
                    <div class="input-group">
                      <span class="input-group-addon">
                        商品名称
                      </span>
                        <input class="form-control" id="itemNameId" type="text" value="${itemName}" placeholder="商品名称" name="itemName">
                    </div>
                </div>
                <div class="col-lg-5">
                    <div class="input-group">
                      <span class="input-group-addon">
                        商品总价
                      </span>
                        <input class="form-control" id="priceMinId" type="text" placeholder="最小价格" value="${minPrice}" name="minPrice">
                      <span class="input-group-btn">
                        －
                      </span>
                        <input class="form-control" id="priceMaxId" type="text" placeholder="最大价格" value="${maxPrice}" name="maxPrice">
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-6">
                    <div class="input-group">
                      <span class="input-group-addon">
                        商品支付时间
                      </span>
                        <input type="text" class="form_datetime form-control" id="onlinetimeId" placeholder="开始时间" value="${startTime}" name="startTime">
                      <span class="input-group-btn">
                        －
                      </span>
                        <input type="text" class="form_datetime form-control"  placeholder="结束时间" value="${endTime}" name="endTime">
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="input-group">
                        <button type="submit" class="btn btn-default">搜索</button>
                    </div>
                </div>
            </div>
    </form>

    <div class="row-fluid" style="padding-top: 10px">
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
                <c:if test="${slug =='payed'}">
                    <th>已同步到派友</th>
                </c:if>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}" varStatus="status">
                <tr>

                    <td><a href="/orders/detail/${order.id}">${order.id}</a></td>
                    <td>${statusMap[order.status]}</td>
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
                    <c:if test="${slug =='payed'}">
                        <c:choose>
                            <c:when test="${order.isSendedToPaiu()}">
                                <td>是</td>
                            </c:when>
                            <c:otherwise>
                                <td>否</td>
                            </c:otherwise>

                        </c:choose>
                    </c:if>
                    <td>
                        <a href="/orders/detail/${order.id}">查看详情</a>&nbsp;
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/template/pagination.jsp"></jsp:include>

    </div>

</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

<script type="text/javascript">

    $('.form_datetime').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd HH:mm:ss',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 0,
        pickTime: false,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1
    });

    $(function () {
        var data = new Date();
        //$(".form_datetime").val(data.format('yyyy-MM-dd hh:mm:ss'));
    });
</script>
