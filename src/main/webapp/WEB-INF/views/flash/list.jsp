<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>


<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="商品列表详情"></jsp:param>
</jsp:include>
<link href="/css/bootstrap-datetimepicker.css" rel="stylesheet">

<script type="text/javascript" src="/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/js/locals/bootstrap-datetimepicker.zh-CN.js"></script>

<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="flashes"></jsp:param>
    <jsp:param name="L2" value="flash_list"></jsp:param>
</jsp:include>

<div class="container">
    <div class="highlight">
        <form  role="form" action="/flash/search" method="get"class="form-inline">
            <div class="form-group">
                <input type="text" class="form-control datetimepicker" id="id" placeholder="上线时间" name="onlinedate" value="${onlinedate}">
            </div>
            <button type="submit" class="btn btn-default">搜索</button>
        </form>
    </div>
    <div class="row-fluid" style="padding-top: 10px">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>闪购编号</th>
                <th>闪购类型</th>
                <th>商品编号</th>
                <th>闪购商品</th>
                <th>上线日期</th>
                <th>显示优先级</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="flash" items="${allFlashes}" varStatus="status">
                <c:if test="${flash.status == 1 }">
                    <c:set var="item" value="${itemsMap[flash.itemid] }"/>
                    <c:set var="brandGroup" value="${groupsMap[flash.groupid] }"/>
                    <tr>

                        <td>${flash.id}</td>
                        <td>
                            <c:if test="${flash.flashtype == 1 }">
                                单品
                            </c:if>
                            <c:if test="${flash.flashtype == 2 }">
                                品牌团
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${flash.flashtype == 1 }">
                                ${item.id }
                            </c:if>
                            <c:if test="${flash.flashtype == 2 }">
                                ${brandGroup.id }
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${flash.flashtype == 1 }">
                                <a href="/item/detail/${item.id}">${item.name}</a>
                            </c:if>
                            <c:if test="${flash.flashtype == 2 }">
                                <a href="#${brandGroup.id }">${brandGroup.name} - ${brandGroup.title }</a>
                            </c:if>
                        </td>
                        <td>${misfn:formatDateToStr(flash.onlinedate)}</td>
                        <td>
                                ${flash.priority} &nbsp;
                            <a href="#" class="btn-link" data-toggle="modal"
                               onclick="addPriorityData(${flash.id})">设置 </a>
                        </td>
                        <td>
                            <c:if test="${flash.status == 1 }">
                                <a href="#" onclick="setFlashOffline(${flash.id})" class="btn-link">删除</a>
                            </c:if>
                            <c:if test="${flash.status == 0 }">
                                <a href="#" onclick="setFlashOnline(${flash.id})" class="btn-link">上线</a>
                            </c:if>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>

    </div>

    <div class="modal fade bs-example-modal-lg" id="priorityModal" role="dialog" aria-labelledby="myPriorityModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myPriorityModalLabel">设置商品优先级</h4>
                </div>
                <div class="modal-body">

                    <div class="input-group">
                        <span class="input-group-addon"><span class="markDisplay">*</span>优先级(输入整数)</span>
                        <input type="text" name="priorityVal" id="priorityId" class="form-control" placeholder="请您输入整数">
                    </div>
                    <input type="hidden" id="flashId" value="">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="savePriority()" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    $(function(){
        $('.datetimepicker').datetimepicker({
            language: "zh-CN",
            weekStart: 1,
            autoclose: true,
            todayHighlight: 1,
            minView: 2,
            format: "yyyy-mm-dd",
            forceParse: 0
        });

    });
    function addPriorityData(flashId) {
        $('#priorityModal').modal('toggle');
        $('#flashId').val(flashId);
    }

    function savePriority() {
        var flashId = $('#flashId').val();
        var priority = $('#priorityId').val();


        $.ajax({
            type: "POST",
            url: "/flash/" + flashId + "/setPriority",
            data: {"priorityVal": priority},
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                    alert("设置商品优先级成功");
                    $("#priorityModal").modal("hide");
                    document.location.href = "/flash/list";
                } else {
                    alert("设置商品优先级失败");
                }
            }
        })
    }

    function setFlashOnline(flashId) {
        $.post("/flash/" + flashId + "/setonline", {}, function (res) {
            if (res.code == 0) {
                //成功
                //判断是否跳转
                if (res.redirect) {
                    window.location.href = res.redirect;
                } else {
                    window.location.reload();
                }
            }
            //打印全局消息
            if (res.message) {
                top.window.receiveMessage(res.message);
            }
        });
    }

    function setFlashOffline(flashId) {
        $.post("/flash/" + flashId + "/setoffline", {}, function (res) {
            if (res.code == 0) {
                //成功
                //判断是否跳转
                if (res.redirect) {
                    window.location.href = res.redirect;
                } else {
                    window.location.reload();
                }
            }
            //打印全局消息
            if (res.message) {
                top.window.receiveMessage(res.message);
            }
        });
    }
</script>

<script src="/js/utils/map.js"></script>
<script src="/js/utils/editTable.js"></script>
<script src="/js/utils/stringUtils.js"></script>
<script src="/js/goodsManage.js"></script>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

