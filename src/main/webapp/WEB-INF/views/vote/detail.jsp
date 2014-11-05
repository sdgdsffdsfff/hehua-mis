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

<div class="container">
    <div style="display: none">
        <form class="well form-search" id="queryForm" method="get" action="/vote/audit/apply/${itemid}">
            <input name="pageNo" id="pageNo">
        </form>
    </div>
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
    <div style="text-align: center" class="div-pad">
        <input type="hidden" id="itemIdHidden" value="${itemid}"/>
        <h3 class="text-center">
            <button type="button" id="finishAuditId" class="btn btn-primary">完成审核</button>&nbsp&nbsp
            <button type="button" id="finishDeliveryId" class="btn btn-primary">完成发货</button>
        </h3>

    </div>

    <div class="row-fluid" style="text-align: center">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th colspan="7"><h4 class="text-center">审核结果</h4></th>
            </tr>
            <tr>
                <th>用户id</th>
                <th>账号</th>
                <th>昵称</th>
                <th>注册时间</th>
                <th>收货地址</th>
                <th>申请状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="orderInfo" items="${orderAuditInfo}" varStatus="status">
                <tr>
                    <td>${orderInfo.user.id }</td>
                    <td style="max-width: 208px;">${orderInfo.user.account} </td>
                    <td>${orderInfo.user.name }</td>
                    <td>${misfn:formatDateToStrBy(orderInfo.user.cts)}</td>
                    <td style="max-width: 208px;">${orderInfo.address.city} &nbsp; ${orderInfo.address.county} &nbsp;${orderInfo.address.detail}</td>
                    <td>${misfn:convertFreeOrderStatus(orderInfo.status)}</td>
                    <td><a href="/vote/audit/appraise/${orderInfo.freeOrderid}">订单详情</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
    <div style="text-align: center" class="div-pad">
    </div>

    <div class="row-fluid">
        <table class="table table-bordered table-striped table-hover">
            <thead style="text-align: center">
            <tr>
                <th colspan="7"><h4 class="text-center">申请人总数 ${applyNum}</h4></th>
            </tr>
            <tr>
                <th>用户id</th>
                <th>账号</th>
                <th>昵称</th>
                <th>注册时间</th>
                <th>收货地址</th>
                <th>申请状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="orderInfo" items="${orderInfoList}" varStatus="status">
                <tr>
                    <td>${orderInfo.user.id}</td>
                    <td style="max-width: 208px;">${orderInfo.user.account} </td>
                    <td>${orderInfo.user.name }</td>
                    <td>${misfn:formatDateToStrBy(orderInfo.user.cts)}</td>
                    <td style="max-width: 208px;">${orderInfo.address.city} &nbsp; ${orderInfo.address.county} &nbsp;${orderInfo.address.detail}</td>
                    <td>${misfn:convertFreeOrderStatus(orderInfo.status)}</td>
                    <td>
                        <c:choose>
                            <c:when test="${orderInfo.status == 0}">
                                <a href="#" onclick="updateUserStatus(${orderInfo.freeOrderid},${itemid},2)">审核通过</a>/<a href="#" onclick="updateUserStatus(${orderInfo.freeOrderid},${itemid},4)">审核拒绝</a>
                            </c:when>
                            <c:otherwise>
                                审核完毕
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/template/pagination.jsp"></jsp:include>

    </div>
</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

<script>
    $(function() {
        var itemid=$("#itemIdHidden").val();
        $("#finishAuditId").click(function(){
            $.ajax({
                type: "POST",
                url: "/vote/" + itemid + "/status",
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        document.location.href="/vote/list";
                    } else {
                        alert(data.message);
                    }
                }
            })
        });


        $("#finishDeliveryId").click(function(){
            $.ajax({
                type: "POST",
                url: "/vote/" + itemid + "/develivery",
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        document.location.href="/vote/list";
                    } else {
                        alert(data.message);
                    }
                }
            })
        });
    })

        function updateUserStatus(freeOrderId, itemId, status ) {
        $.ajax({
            type: "POST",
            url: "/vote/freeorder/"+ freeOrderId +"/"+itemId + "/" + status,
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                   document.location.href="/vote/audit/apply/" + itemId;
                } else {
                    alert("更新状态失败");
                }
            }
        })
    }
</script>
