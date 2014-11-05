<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>


<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="商品评测详情"></jsp:param>
</jsp:include>
<link href="/css/bootstrap-datetimepicker.css" rel="stylesheet">

<script type="text/javascript" src="/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/js/locals/bootstrap-datetimepicker.zh-CN.js"></script>

<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="goods"></jsp:param>
</jsp:include>

<div class="container">
    <div class="row-fluid">
        <h4 class="modal-title" id="myModalListLabel">评测详细列表</h4>
        <input id="itemId" type="hidden" value="${itemId}">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>达人名称</th>
                <th>评测信息</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${itemAppraiseList != null}">
                <c:forEach var="appraise" items="${itemAppraiseList}" varStatus="status">
                    <tr>
                        <td>${appraise.id}</td>
                        <td>${appraise.name}</td>
                        <td>${appraise.appraise}</td>

                        <td>
                            <a href="javascript:void(0)" class="btn-link" id="appraise_${appraise.id}">删除</a>
                        </td>


                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>

    </div>

</div>

<script type="text/javascript">

    function addAppraise(itemId) {
        $('#appraiseModal').modal('toggle');
        $('#itemIdAppraiseHidden').val(itemId);
    }


    $(function () {
        $(".btn-link").click(function () {
            var itemId = $('#itemId').val()
            if (confirm("确定要删除吗？？？")) {
                var url = "/item/appraise/" + $(this).attr("id").split("_")[1];
                $.get(url, function (msg) {
                    if (msg.code == 1){
                        alert(msg.message);
                        document.location.href = "/item/appraise/list/"+itemId;
                    }
                    else
                        alert(msg.message);
                })
            }

        });
    });
</script>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

