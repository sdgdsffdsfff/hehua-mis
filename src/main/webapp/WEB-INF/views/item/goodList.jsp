<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>
<%@taglib uri="http://mis.hehuababy.com/mis/assert" prefix="assert" %>
<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="商品列表详情"></jsp:param>
</jsp:include>
<%@include file="/template/datetimepicker.inc" %>
<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="goods"></jsp:param>
    <jsp:param name="L2" value="good_list"></jsp:param>
</jsp:include>
<style>
    .modal-dialog {
        width: 800px;
        height: 800px;
    }
</style>
<script>

</script>
<div class="container">
<form role="form" class="well form-search" id="queryForm" action="/items/search" method="GET">
    <input name="pageNo" type="hidden" id="pageNo">

    <div class="row div-pad">
        <div class="col-lg-6">
            <div class="input-group">
                      <span class="input-group-addon">
                        商品名称
                      </span>
                <input class="form-control" id="itemNameId" type="text" placeholder="商品名称" value="${itemName}"
                       name="itemName">
            </div>
        </div>
        <div class="col-lg-5">
            <div class="input-group">
                      <span class="input-group-addon">
                        商品价格
                      </span>
                <input class="form-control" id="priceMinId" type="text" placeholder="最小价格" value="${minPrice}"
                       name="minPrice">
                      <span class="input-group-btn">
                        －
                      </span>
                <input class="form-control" id="priceMaxId" type="text" placeholder="最大价格" value="${maxPrice}"
                       name="maxPrice">
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-6">
            <div class="input-group">
                      <span class="input-group-addon">
                        上线时间
                      </span>
                <input type="text" class="datetimepicker form-control" id="onlinetimeId" placeholder="上架开始时间"
                       value="${startTime}" name="startTime">
                      <span class="input-group-btn">
                        －
                      </span>
                <input type="text" class="datetimepicker form-control" placeholder="上架结束时间" name="endTime"
                       value="${endTime}">
            </div>
        </div>
        <div class="col-lg-6">
            <div class="input-group">
                <button type="submit" class="btn btn-default">搜索</button>
            </div>
        </div>
    </div>
</form>

<div class="row-fluid">
<table class="table table-bordered table-striped table-hover">
    <thead>
    <tr>
        <th>ID</th>
        <th>商品名称</th>
        <th>商品原始价格</th>
        <th>商品实际价格</th>
        <th>商品库存</th>
        <th>商品发布时间</th>
        <th>商品评测</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${itemList}" varStatus="status">
        <tr>

            <td><a href="/item/detail/${item.id}">${item.id}</a></td>
            <td style="max-width: 128px;">${item.name}</td>
            <td>${item.originprice}</td>
            <td>${item.realprice}</td>
            <td>${item.quantity}</td>
            <td>${misfn:formatDateToStrBy(item.cts)}</td>
            <td>
                <a href="/item/appraise/list/${item.id}" class="btn-link" data-toggle="modal">查看评测 </a>
                <br>
                <a href="/item/appraise/add/${item.id}" class="btn-link" data-toggle="modal">添加评测 </a>

            </td>

            <td>
                <a href="#" class="btn-link" data-toggle="modal" onclick="addSession(${item.id})">添加闪购 /</a>
                <a href="#" class="btn-link" data-toggle="modal" onclick="addVote(${item.id})">设置众测 </a>

                <br>
                <a href="#" class="btn-link" data-toggle="modal" onclick="addMetaData(${item.id})">添加质检报告 </a>
                <br>
                <a href="/item/goodEdit/${item.id}" class="btn-link" data-toggle="modal">编辑</a>&nbsp; &nbsp;


                <c:if test="${item.status == 0}">
                    <a href="javascript:void(0)" class="btn-link" style="color: #808080">已删除</a>
                </c:if>

                <c:if test="${item.status == 1}">
                    <a href="javascript:void(0)" class="btn-link deleteItem" id="item_${item.id}">删除</a>
                </c:if>

                <c:set var="restriction" value="${restrictions[item.id]}"/>

                <c:if test="${restriction != null }">
                    <a href="/item/${item.id }/restriction" class="btn-link" data-toggle="modal">取消限购</a>
                </c:if>

                <c:if test="${restriction == null }">
                    <a href="/item/${item.id }/restriction" class="btn-link" data-toggle="modal">设置限购</a>
                </c:if>


            </td>


        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="/template/pagination.jsp"></jsp:include>

<div class="modal fade bs-example-modal-lg" id="voteModal" role="dialog" aria-labelledby="myVoteModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myVoteModalLabel">添加众测商品</h4>
            </div>
            <div class="modal-body">
                <div class="row div-pad">
                    <div class="col-lg-11">
                        <div class="input-group">
                                  <span class="input-group-addon">
                                    <span class="markDisplay">*</span>众测开始和结束时间
                                  </span>
                            <input type="text" class="form_datetime form-control" id="voteStarttimeId"
                                   placeholder="开始时间" name="startTime">
                                  <span class="input-group-btn">
                                    －
                                  </span>
                            <input type="text" class="form_datetime form-control" id="voteEndtimeId" placeholder="结束时间"
                                   name="endTime">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-11">
                        <div class="input-group">
                                  <span class="input-group-addon">
                                    <span class="markDisplay">*</span>免费发放商品的数量
                                  </span>
                            <input type="text" class="form-control" id="freeQuantityId" placeholder="免费发放数量"
                                   name="freeQuantity">
                        </div>
                    </div>
                </div>
                <input type="hidden" id="itemVoteId" value="">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="setVoteItem()" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade bs-example-modal-lg" id="metaModal" role="dialog" aria-labelledby="myMetaModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myMetaModalLabel">添加质检报告</h4>
            </div>
            <div class="modal-body">

                <span class="markDisplay">*</span>质检报告
                <div id="metaId"></div>
                <jsp:include page="/template/editor.jsp"></jsp:include>
                <input type="hidden" id="itemMetaId" value="">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="saveMeta()" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade bs-example-modal-lg" id="appraiseModal" role="dialog"
     aria-labelledby="myAppraiseModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myAppraiseModalLabel">添加评测信息</h4>
            </div>
            <div class="modal-body">
                <span class="markDisplay">*</span>商品达人
                <select class="form-control" name="darenId" id="daren_id">
                    <c:forEach var="daren" items="${darenList}" varStatus="status">
                        <option name="darenId" value="${daren.id}">${daren.name}</option>
                    </c:forEach>
                </select>
                <br>
                <span class="markDisplay">*</span>评测内容
                <div id="appraiseId"></div>
                <input type="hidden" id="itemIdAppraiseHidden" value="">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="saveAppraise()" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade appraiseList" id="appraiseList" role="dialog" aria-labelledby="myModalListLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalListLabel">评测详细列表</h4>
            </div>
            <div class="modal-body">
                <div id="contentId">

                </div>
                <input type="hidden" id="itemIdListHidden" value="">
            </div>

        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="dateTimeModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">闪购详情</h4>
            </div>
            <div class="modal-body">
                <div data-link-field="dtp_input1" data-date-format="yyyy-mm-dd" data-date="2014-08-28"
                     class="input-group date form_datetime col-md-9">
                    <input type="text" id="dtp_input1" readonly="" value="" size="20"
                           class="form-control datetimepicker">
                </div>
                <%--<input type="hidden" value="" id="dtp_input1">--%>
                <input type="hidden" id="itemIdHidden" value="">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="saveSession()" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>
</div>
</div>
<%@include file="/template/fileupload.inc"%>
<assert:file name="/js/goodList.js"></assert:file>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

