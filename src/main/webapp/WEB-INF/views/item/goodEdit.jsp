<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="商品管理！！荷花管理后台"></jsp:param>
</jsp:include>
<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="goods"></jsp:param>
    <jsp:param name="L2" value="publish_goods"></jsp:param>
</jsp:include>
<link href="/css/my-mis.css" rel="stylesheet">

<script type="text/javascript" src="/js/bootstrap-select.js"></script>

<div class="container">
    <form role="form" id="formItemId">
        <input name="itemId" value="${itemResult.item.id}" type="hidden">
        <input id="itemSkuId" name="itemSku" value="" type="hidden">
        <div class="formDisplay">
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>商品类目</span>
                <input type="text" name="cateId" class="form-control" value="${itemResult.cateName}" readonly>
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>商品品牌</span>
            <input type="text" name="brandName" class="form-control" placeholder="输入商品品牌,最多20个字" value="${itemResult.brand}">
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>商品名称</span>
            <input type="text" name="itemName" class="form-control" placeholder="输入商品名称,最多30个字" value="${itemResult.item.name}">
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon">商品材质</span>
            <input type="text" name="materialName" value="${material.name}" class="form-control" placeholder="输入商品材质,最多20个字">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>适用人群</span>
            <div class="col-sm-10">
                <input type="text" name="crowedId" value="${crows}" class="form-control" readonly>
            </div>
        </div><br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>适用性别</span>
            <div class="col-sm-10">
                <input type="text" name="genderId" value="${gender}" class="form-control" readonly>
            </div>
        </div><br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>商品采购地</span>
            <div class="col-sm-10">
                <c:forEach var="purchase" items="${purchaseList}" varStatus="status">
                    <input type="radio" name="purchaseId" value="${purchase.id}" <c:if test="${purchase.id == itemResult.item.purchaseid}">checked</c:if> >${purchase.name}
                </c:forEach>
            </div>
        </div><br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>商品原价(单位:元)</span>
                <input type="text" name="originPrice" class="form-control" value="${itemResult.item.originprice}" readonly>
            <span class="input-group-addon"><span class="markDisplay">*</span>商品售价(单位:元)</span>
                <input type="text" class="form-control" name="realPrice" value="${itemResult.item.realprice}" readonly>
        </div><br>

        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>专题达人</span>
            <select class="form-control" name="darenId">
                <c:forEach var="daren" items="${darenList}" varStatus="status">
                    <option name="darenId" value="${daren.id}" <c:if test="${daren.id == itemRecommend.uid}"> selected="selected"</c:if>>${daren.name}</option>
                 </c:forEach>
            </select>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>推荐理由</span>
            <input type="text" name="reason" value="${itemRecommend.reason}" class="form-control" placeholder="输入推荐理由,最多140个字">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>所属仓库</span>
            <select class="form-control" name="warehouseId">
                <c:forEach var="warehouse" items="${warehouseList}" varStatus="status">
                    <option name="warehouseId" value="${warehouse.id}" <c:if test="${warehouse.id == itemResult.item.warehouseid}"> selected="selected" </c:if>>${warehouse.name}</option>
                </c:forEach>
            </select>
        </div>
          <br>
          <div class="input-group">
              <span class="input-group-addon"><span class="markDisplay">*</span>邮费类型</span>
              <select class="form-control" name="postTypeId">
                  <option name="postTypeId" value="1" <c:if test="${itemResult.item.postagetype == 1}"> selected="selected" </c:if>>满158元免邮</option>
                  <option name="postTypeId" value="2" <c:if test="${itemResult.item.postagetype == 2}"> selected="selected" </c:if>>不免邮</option>
              </select>
         </div>
        <br>
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
                                        <td><input type="email" class="form-control" id="sku_quantity_${sku.id}" value="${sku.quantity}"></td>
                                        <td>${sku.barcode}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                </c:if>
      </div>
       <br>

        <br>
        <div class="col-sm-2"><span class="markDisplay">*</span>商品描述</div><br>
        <div class="formAllDisplay">
                <div id="contentDivId" style="display: none">
                    ${itemResult.item.desc}
                </div>
            <br>
        </div>
        <jsp:include page="/template/editor.jsp"></jsp:include>
        <input id="itemDescId" type="hidden" name="desc" value="">
    </form>

</div>
<br>
<div id="tipIds" class="alertTip">
</div>
<div class="buttonDisplay">
    <button id="submmitEditId" type="button" class="btn btn-primary btn-lg">保存</button>
    <br><br><br>
</div>
<script src="/js/utils/map.js"></script>
<script src="/js/utils/editTable.js"></script>
<script>
    function SkuInfo(skuId, quantity) {
        this.skuId = skuId;
        this.quantity = quantity;
        this.toString = function () {
            return JSON.stringify(this);
        }
    }
    UE.getEditor('post').ready(function(){
        UE.getEditor('post').setContent($("#contentDivId").html());
    })

    $("#submmitEditId").click(function() {
        $("#tipIds").html("");
        var descStr = UE.getEditor('post').getContent();
        $("#itemDescId").val(descStr);
        var skuArray = new Array();
        var status = true;
        $("td[id^=sku_id_]").each(function(){

            var skuid = $(this).attr("name");
            var quantity = $("#sku_quantity_"+skuid).val();
            quantity = quantity.trim();
            if (!isInteger(quantity)) {
                alert("输入商品库存数量一定要为整数");
                status = false;
                return;
            }
            skuArray.push(new SkuInfo(skuid, quantity));
        });
        if (!status) {
            return;
        }
        $("#itemSkuId").val(JSON.stringify(skuArray));
        var data = $("#formItemId").serialize();

        $.ajax({
            type: "POST",
            url: "/item/edit",
            data: data,// 要提交的表单
            dataType: "json",
            success: function(data) {
                if (data.code == 0) {
                    alert("编辑成功！");
                    document.location.href = "/item/list";

                } else {
                    var str = '<div class="alert alert-danger" id="alertId" role="alert">'
                    str += data.message + "</div>";
                    $("#tipIds").html(str);
                }
            }
        })
    })
</script>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

