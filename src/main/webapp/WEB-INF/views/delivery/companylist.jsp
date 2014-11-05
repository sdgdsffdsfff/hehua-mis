<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="订单管理"></jsp:param>
</jsp:include>
<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="delivery"></jsp:param>
    <jsp:param name="L2" value="companylist"></jsp:param>
</jsp:include>


<div class="container">
    <div class="row-fluid" style="padding-top: 10px">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>名称</th>
                <th>简写</th>
                <th>官网地址</th>
                <th>显示优先级（越大显示越靠前）</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="deliveryCompany" items="${deliveryCompanys}" varStatus="status">
                <tr>
                    <td>${deliveryCompany.id}</td>
                    <td>${deliveryCompany.name}</td>
                    <td>${deliveryCompany.code}</td>
                    <td>${deliveryCompany.website}</td>
                    <td>${deliveryCompany.sort}</td>
                    <td><button class="btn btn-sm" data-toggle="model" onclick="displaySetCompanySortDialog(${deliveryCompany.id}, '${deliveryCompany.name}')">设置显示优先级</button></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>

</div>

<div class="modal fade bs-example-modal-lg" id="setDeliveryCompanySortModal" role="dialog" aria-labelledby="setDeliveryCompanySortModal" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="setDeliveriedModalLabel">显示优先级设置</h4>
            </div>
            <div class="modal-body">
                <input id="companyId" type="hidden" value=""><br />
                物流公司：<span id="companyName"></span><br />
                <span class="markDisplay">*</span>优先级（越大越靠前）：
                <input  id="companySort" class="input-sm" value="" />
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" onclick="setCompanySort()" class="btn btn-primary">确定</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function displaySetCompanySortDialog(companyId, companyName) {
        $('#companyId').val(companyId);
        $('#companySort').val('');
        $('#companyName').html(companyName);
        $('#setDeliveryCompanySortModal').modal('toggle');
    }

    function setCompanySort() {
        var companyId = $('#companyId').val();
        var companySort = $('#companySort').val();
        $.ajax({
            type: "POST",
            url:"/delivery/company/setsort",
            data:{"id":companyId,"sort":companySort},
            dataType:"json",
            success: function(data) {
                if (data.code == 0) {
                    alert(data.message);
                } else {
                    alert(data.message);
                }
                $('#setDeliveryCompanySortModal').modal('toggle');
            }
        });
    }
</script>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>
