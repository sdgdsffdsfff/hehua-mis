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
<link href="/css/bootstrap-select.css" rel="stylesheet">
<link href="/css/bootstrap-editable.css" rel="stylesheet">

<script type="text/javascript" src="/js/bootstrap-select.js"></script>
<script type="text/javascript" src="/js/mis/my-mis.js"></script>
<script type="text/javascript" src="/js/bootstrap-editable.min.js"></script>

<script type="text/javascript" src="/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="/js/jquery.fileupload.js"></script>


<div class="container">
    <form role="form" id="formItemId">
      <div class="formDisplay">
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>选择商品类目</span>
            <div class="form-group">
                <select class="selectpicker" data-live-search="true" name="cateId">
                    <c:forEach var="category" items="${categoryList}" varStatus="status">
                        <c:if test="${category.parentid == 0}">
                            <optgroup label="${category.name}">
                                <c:forEach var="cate" items="${categoryList}" varStatus="status">
                                    <c:if test="${cate.parentid == category.id}">
                                        <option name="cateId" value="${cate.id}">${cate.name}</option>
                                    </c:if>
                                </c:forEach>
                            </optgroup>
                        </c:if>
                    </c:forEach>

                </select>
                &nbsp &nbsp
                <%--<button id="genId" type="button" class="btn btn-default">生成商品属性</button>--%>
            </div>
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>商品品牌</span>
            <input type="text" name="brandName" class="form-control" placeholder="输入商品品牌,最多20个字">
            <%--<select class="form-control" name="brandId">--%>
            <%--<c:forEach var="brand" items="${brandList}" varStatus="status">--%>
                <%--<option name="brandId" value="${brand.id}">${brand.name}</option>--%>
            <%--</c:forEach>--%>
            <%--</select>--%>
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>商品名称</span>
            <input type="text" name="name" class="form-control" placeholder="输入商品名称,最多30个字">
        </div>
        <br>

        <div class="input-group">
            <span class="input-group-addon">商品材质</span>
            <input type="text" name="materialName" class="form-control" placeholder="输入商品材质,最多20个字">

            <%--<select class="form-control" name="materialId">--%>
                <%--<c:forEach var="material" items="${materialList}" varStatus="status">--%>
                    <%--<option name="materialId" value="${material.id}">${material.name}</option>--%>
                <%--</c:forEach>--%>
            <%--</select>--%>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>适用人群</span>
            <div class="col-sm-10">
                <c:forEach var="crowed" items="${crowedList}" varStatus="status">
                    <input type="checkbox" name="crowedId" value="${crowed.id}">${crowed.name}
                </c:forEach>
            </div>
        </div><br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>适用性别</span>
            <div class="col-sm-10">
                <c:forEach var="gender" items="${genderList}" varStatus="status">
                    <input type="radio" name="genderId" value="${gender.id}">${gender.name}
                </c:forEach>
            </div>
        </div><br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>商品采购地</span>
            <div class="col-sm-10">
                <c:forEach var="purchase" items="${purchaseList}" varStatus="status">
                    <input type="radio" name="purchaseId" value="${purchase.id}">${purchase.name}
                </c:forEach>
            </div>
        </div><br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>商品原价(单位:元)</span>
                <input type="text" name="originPrice" class="form-control" value="1">
            <span class="input-group-addon"><span class="markDisplay">*</span>商品售价(单位:元)</span>
                <input type="text" class="form-control" name="realPrice" value="1">
        </div><br>

        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>专题达人</span>
            <%--<input type="text" name="darenName" class="form-control" placeholder="输入专题达人,最多20个字">--%>
            <select class="form-control" name="darenId">
                <c:forEach var="daren" items="${darenList}" varStatus="status">
                    <option name="darenId" value="${daren.id}">${daren.name}</option>
                 </c:forEach>

            </select>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>推荐理由</span>
            <input type="text" name="reason" class="form-control" placeholder="输入推荐理由,最多140个字">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="markDisplay">*</span>所属仓库</span>
            <select class="form-control" name="warehouseId">
                <c:forEach var="warehouse" items="${warehouseList}" varStatus="status">
                    <option name="warehouseId" value="${warehouse.id}">${warehouse.name}</option>
                </c:forEach>
            </select>
        </div>
          <br>
          <div class="input-group">
              <span class="input-group-addon"><span class="markDisplay">*</span>邮费类型</span>
              <select class="form-control" name="postTypeId">
                  <option name="postTypeId" value="1">满158元免邮</option>
                  <option name="postTypeId" value="2">不免邮</option>
              </select>
         </div>
        <br>
          <div class="alert alert-info">
                 <p>
                  1.图片必须是gif，gpeg，png，jpg
                 </p>
                 <p>
                  2.本商品最多只能上传5张图片
                 </p>
                 <p>
                  3.上传图片的大小不能超过1M
                 </p>
                 <p>
                  4.第一张即为产品头图
                 </p>
          </div>
          <div class="input-group">

              <span class="input-group-addon"><span class="markDisplay">*</span>选择本地图片</span>
              <input id="uploadFileId" type="file" class="btn btn-default"/>
              <span id="imageUrlIds"></span>
          </div><br>
          <input id="imageId" type="hidden" name="imags" value="">
      </div>

        <div class="formAllDisplay">
            <div class="col-sm-2">商品规格</div>
            <div class="col-sm-10" id="goods_norms_detail">

            </div>

            <div class="col-sm-2"></div>
            <div class="col-sm-10">
                <div id="json_result"></div>
                <table class="table table-bordered" id="goods_norms_table">

                    <thead id="table_thead" style="display: none">
                    <tr id="table_thead_body">
                        <td>原始价格</td>
                        <td>真实价格</td>
                        <td>数量</td>
                        <td>商品条形码</td>
                    </tr>
                    </thead>
                    <tbody id="goods_norms">

                    </tbody>
                </table>
            </div>
            <br>
            <div class="col-sm-2">商品数量</div><br>
            <div class="col-sm-10">
                <input value="" id="goods_num" name="quantity" type="text">
                <br><br>
            </div>

        </div>

        <div class="formAllDisplay">
            <div class="col-sm-2"><span class="markDisplay">*</span>商品描述</div>
            <div class="col-sm-10">
                <jsp:include page="/template/editor.jsp"></jsp:include>
            </div><br>
        </div>

        <input id="itemDescId" type="hidden" name="desc" value="">
    </form>

</div>
<br>
<div id="tipIds" class="alertTip">
</div>
<div class="buttonDisplay">
    <button id="submmitId" type="button" class="btn btn-primary btn-lg" onclick="submitItemForm()">保存</button>
    <br><br><br>
</div>
<script src="/js/utils/map.js"></script>
<script src="/js/utils/editTable.js"></script>
<script src="/js/goodsManage.js"></script>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

