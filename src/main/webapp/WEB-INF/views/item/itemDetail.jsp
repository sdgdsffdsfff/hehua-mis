<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>


<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="商品详情"></jsp:param>
</jsp:include>
<link href="/css/bootstrap-datetimepicker.css" rel="stylesheet">

<script type="text/javascript" src="/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/js/locals/bootstrap-datetimepicker.zh-CN.js"></script>

<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="flashes"></jsp:param>
    <jsp:param name="L2" value="flash_list"></jsp:param>
</jsp:include>

<div class="container">
    <div class="row-fluid">
        <p class="text-center"><strong>商品基本信息</strong></p>
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>商品ID</th>
                <th>商品名称</th>
                <th>商品类目</th>
                <th>商品品牌</th>
                <th>商品材质</th>
                <th>商品原价</th>
                <th>商品售价</th>
            </tr>
            </thead>
            <tbody>
                <tr>

                    <td>${itemResult.item.id}</td>
                    <td>${itemResult.item.name}</td>
                    <td>${itemResult.cateName}</td>
                    <td>${itemResult.brand}</td>
                    <td>${material.name}</td>
                    <td>${itemResult.item.originprice}</td>
                    <td>${itemResult.item.realprice}</td>
                </tr>
            </tbody>
        </table>

        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>适用人群</th>
                <th>适用性别</th>
                <th>商品购地</th>
                <th>专题达人</th>
                <th>所属仓库</th>
                <th>邮费类型</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${crows}</td>
                <td>${gender}</td>
                <td>
                    <c:forEach var="purchase" items="${purchaseList}" varStatus="status">
                       <c:if test="${purchase.id == itemResult.item.purchaseid}">${purchase.name}</c:if>
                    </c:forEach>
                </td>
                <td>
                    <c:if test="${itemRecommend != null}">
                        <c:forEach var="daren" items="${darenList}" varStatus="status">
                            <c:if test="${daren.id == itemRecommend.uid}">${daren.name}</c:if>
                        </c:forEach>
                    </c:if>
                </td>
                <td>
                    <c:forEach var="warehouse" items="${warehouseList}" varStatus="status">
                        <c:if test="${warehouse.id == itemResult.item.warehouseid}">${warehouse.name}</c:if>
                    </c:forEach>
                </td>
                <td>
                    <c:if test="${itemResult.item.postagetype == 1}"> 满158元免邮 </c:if>
                    <c:if test="${itemResult.item.postagetype == 2}">不免邮</c:if>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            商品推荐理由
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel">
                    <div class="panel-body">
                        <textarea class="form-control" rows="3" disabled>
                            <c:if test="${itemRecommend != null}">
                                ${itemRecommend.reason}
                            </c:if>
                        </textarea>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            商品图片
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel">
                    <div class="panel-body">
                        <div style="text-align: center">
                        <c:if test="${imageList != null}">
                            <c:forEach var="image" items="${imageList}" varStatus="status">
                                <img  src="${image}"/>
                            </c:forEach>
                        </c:if>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            商品描述
                        </a>
                    </h4>
                </div>
                <div id="collapseThree" class="panel-collapse collapse" role="tabpanel">
                    <div class="panel-body">
                        <div style="text-align: center">
                            ${itemResult.item.desc}
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                            商品用户评论
                        </a>
                    </h4>
                </div>
                <div id="collapseFour" class="panel-collapse collapse" role="tabpanel">
                    <div class="panel-body">
                        <c:if test="${commentList != null}">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                <tr>
                                    <th>用户id</th>
                                    <th>订单id</th>
                                    <th>用户评价</th>
                                    <th>skuid</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="comment" items="${commentList}" varStatus="status">
                                    <tr>
                                        <td>${comment.userid}</td>
                                        <td>${comment.orderid}</td>
                                        <td>${comment.comment}</td>
                                        <td>${comment.skuid}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                            商品评测
                        </a>
                    </h4>
                </div>
                <div id="collapseFive" class="panel-collapse collapse" role="tabpanel">
                    <div class="panel-body">
                        <c:if test="${itemAppraise != null}">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                <tr>
                                    <th>评测人id</th>
                                    <th>评测人名字</th>
                                    <th>评测内容</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="appraise" items="${itemAppraise}" varStatus="status">
                                    <tr>
                                        <td>${appraise.uid}</td>
                                        <td>${appraise.name}</td>
                                        <td>${appraise.appraise}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseSix" aria-expanded="false" aria-controls="collapseSix">
                            商品规格
                        </a>
                    </h4>
                </div>
                <div id="collapseSix" class="panel-collapse collapse" role="tabpanel">
                    <div class="panel-body">
                        <c:if test="${itemResult.itemSkus != null && itemResult.itemSkus.size() != 0}">
                            <c:if test="${itemResult.propertyList != null}">
                                <table class="table table-bordered table-striped table-hover">
                                    <thead>
                                    <tr id="table_thead_body">
                                        <c:forEach var="property" items="${itemResult.propertyList}">
                                            <td id="thead_${property.id}">${property.name}</td>
                                        </c:forEach>
                                        <td>原始价格</td>
                                        <td>真实价格</td>
                                        <td>数量</td>
                                        <td>商品条形码</td>
                                    </tr>
                                    </thead>
                                    <tbody id="goods_norms">
                                    <c:forEach var="sku" items="${itemResult.itemSkus}" varStatus="status">
                                        <tr>
                                            <td style="display:none;" id="sku_id_" name="${sku.id}">${sku.id}</td>
                                            <c:forEach var="pv" items="${sku.propertyValueList}">
                                                <td id="${pv.id}">${pv.name}</td>
                                            </c:forEach>
                                            <td>${sku.originprice}</td>
                                            <td>${sku.realprice}</td>
                                            <td>${sku.quantity}</td>
                                            <td>${sku.barcode}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </div>

        </div>
    </div>

</div>


<script type="text/javascript">

</script>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

