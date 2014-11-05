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
    <jsp:param name="L2" value="vote_audit_apply"></jsp:param>
</jsp:include>

<div class="container">
    <div style="display: none">
        <form class="well form-search" id="queryForm" method="get" action="/vote/list">
            <input name="pageNo" id="pageNo">
        </form>
    </div>
    <!--
    <div class="highlight">
        <form class="form-inline" role="form"  action="/vote/search" method="post">
            <div class="form-group">
                <input type="text" class="form-control" id="id" placeholder="id" name="id">
            </div>
            <button type="submit" class="btn btn-default">搜索</button>
        </form>
    </div>-->

    <div class="row-fluid" style="text-align: center">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>众测id</th>
                <th>商品名称</th>
                <th>商品申请人数</th>
                <th>众测库存</th>
                <th>众测开始时间</th>
                <th>众测结束时间</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="freeFlash" items="${freeFlashList}" varStatus="status">
                <tr>
                    <td>${freeFlash.id }</td>
                    <td style="max-width: 208px;"> <a href="/item/detail/${freeFlash.itemLite.id}">${freeFlash.itemLite.name}</a> </td>
                    <td>${freeFlash.applynum }</td>
                    <td>${freeFlash.freequantity}</td>
                    <td>${misfn:formatDateToStrBy(freeFlash.starttime)}</td>
                    <td>${misfn:formatDateToStrBy(freeFlash.endtime)}</td>
                    <td>${freeFlash.freeFlashEnums.getMessage()}</td>
                    <td>
                    <c:if test="${!(misfn:isSessionRange(freeFlash.starttime, freeFlash.endtime)) && freeFlash.status == 1}">
                        <a href="#" class="btn-link" data-toggle="modal" onclick="updateWaitAuditStatus(${freeFlash.id}, 2)">待审核</a>
                    </c:if>
                    <c:if test="${freeFlash.status <= 5 && freeFlash.status >= 2}">
                            <a href="/vote/audit/apply/${freeFlash.itemid}" class="btn-link" data-toggle="modal">查看详情</a>
                    </c:if>

                    <c:if test="${freeFlash.status == 0 && (misfn:isBefore(freeFlash.starttime))}">
                        <a href="#" class="btn-link" data-toggle="modal" onclick="updateOnLineStatus(${freeFlash.id}, 1)">上线</a>
                    </c:if>
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
    function updateOfflineStatus(freeId, status) {
        updateStatus(freeId, status);
    }
    function updateOnLineStatus(freeId, status) {
        updateStatus(freeId, status);
    }

    function updateWaitAuditStatus(freeId, status) {
        updateStatus(freeId, status);
    }
        function updateStatus(freeId, status) {
        $.ajax({
            type: "POST",
            url: "/vote/status/"+ freeId +"/"+status,
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                   document.location.href="/vote/list";
                } else {
                    alert("更新状态失败");
                }
            }
        })
    }
</script>
