<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="众测管理"></jsp:param>
</jsp:include>


<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="votesManage"></jsp:param>
    <jsp:param name="L2" value="vote_list"></jsp:param>
</jsp:include>
<script>
    var mis_data = {
        rules:{},
        messages:{},
        url: "/vote/setsigned/${freeOrderInfo.freeOrderid}",
        nextUrl: window.location
    }
</script>
<div class="container">
    <h3 class="page-header">试用商品</h3>

    <div class="row-fluid">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>商品id</th>
                <th>商品名称</th>
                <th>数量</th>
                <th>价格</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${item.id}</td>
                <td><a href="/item/detail/${item.id}">${item.name}</a></td>
                <td>${item.sales}</td>
                <td>${item.originprice}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <h3 class="page-header">操作</h3>
    <c:choose>
        <c:when test="${freeOrderInfo.status != 6 && freeOrderInfo.status != 8}">
            <div><a class="btn btn-primary" href="javascript:void(0)" id="updateDelivery">
                设为已发货
            </a></div>
        </c:when>
        <c:when test="${freeOrderInfo.status == 6}">
            <form  method="post">
                <button class="btn btn-primary" type="submit"> 设为已签收</button>
            </form>
        </c:when>
    </c:choose>


    <h3 class="page-header">物流信息</h3>

    <div class="row-fluid">
        <table class="table table-bordered table-striped table-hover">
            <tr>
                <td>收货人</td>
                <td>${freeOrderInfo.address.name}</td>
            </tr>
            <tr>
                <td>电话</td>
                <td>${freeOrderInfo.address.phone}</td>
            </tr>
            <tr>
                <td>收货地址</td>
                <td>${freeOrderInfo.address.province}${freeOrderInfo.address.city}${freeOrderInfo.address.county}${freeOrderInfo.address.detail}</td>
            </tr>
            <tr>
                <td>物流公司</td>
                <td>${freeOrderInfo.deliveryInfo.deliveryCompany}</td>
            </tr>
            <tr>
                <td>物流单号</td>
                <td>${freeOrderInfo.deliveryInfo.deliveryNum}</td>
            </tr>
            <tr>
                <td>物流详情</td>
                <td><c:forEach items="${freeOrderInfo.deliveryInfo.traceInfo}" var="traceInfo">
                    ${traceInfo.time}:${traceInfo.event}
                </c:forEach></td>
            </tr>
        </table>
    </div>
    <h3 class="page-header">评测报告</h3>

    <div class="row-fluid">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <th>评测id</th>
            <th>评测状态</th>
            <th>评测时间</th>
            <th>操作</th>
            <th width="75%">评测内容</th>
            </thead>
            <tbody>
            <tr>
                <td>${itemAppraise.id}</td>

                <td><c:if test="${itemAppraise.status == 1}"> 未评测 </c:if>
                    <c:if test="${itemAppraise.status == 2}"> 通过 </c:if>
                    <c:if test="${itemAppraise.status == 3}"> 拒绝 </c:if>
                    <c:if test="${itemAppraise.status == 4}"> 删除 </c:if>
                </td>
                <td>${misfn:formatDateToStr(itemAppraise.cts)}</td>
                <td>
                    <c:if test="${itemAppraise.status == 1}">
                        <a href="javascript:void(0)" class="btn btn-primary audit"
                           url="/item/appraise/updateState?id=${itemAppraise.id}&state=2">通过</a>&nbsp;&nbsp;&nbsp;<a
                        href="javascript:void(0)" class="btn btn-danger audit"
                        url="/item/appraise/updateState?id=${itemAppraise.id}&state=3">拒绝</a>
                    </c:if>
                </td>
                <td width="75%">
                    ${itemAppraise.appraise}
                </td>
            </tr>

            </tbody>
        </table>
    </div>
</div>
<jsp:include page="/template/modal.jsp"></jsp:include>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>
<script src="/js/orderDetail.js"></script>
<script>
    $(function () {
        $("#updateDelivery").myModal("设置物流信息", "/order/updateDelivery/${freeOrderInfo.freeOrderid}", window.location);
    });
</script>
