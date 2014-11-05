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
    <jsp:param name="L2" value="${selected}"></jsp:param>
</jsp:include>

<div class="container">
    <div style="display: none">
        <form class="well form-search" id="queryForm" method="get" action="/vote/list/${selected}">
            <input name="pageNo" id="pageNo">
        </form>
    </div>

    <div class="row-fluid" style="text-align: center">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>众测id</th>
                <th>商品名称</th>
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
                    <td>${freeFlash.freequantity}</td>
                    <td>${misfn:formatDateToStrBy(freeFlash.starttime)}</td>
                    <td>${misfn:formatDateToStrBy(freeFlash.endtime)}</td>
                    <td>${freeFlash.freeFlashEnums.getMessage()}</td>
                    <td>
                         <td><a href="/vote/audit/appraise/${freeFlash.freeOrderId}">详情</a></td>
                    </td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/template/pagination.jsp"></jsp:include>

    </div>
</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>