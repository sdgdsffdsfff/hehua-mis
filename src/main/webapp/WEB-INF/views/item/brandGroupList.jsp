<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>


<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="品牌团列表"></jsp:param>
</jsp:include>
<link href="/css/bootstrap-datetimepicker.css" rel="stylesheet">

<script type="text/javascript" src="/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/js/locals/bootstrap-datetimepicker.zh-CN.js"></script>

<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="goods"></jsp:param>
    <jsp:param name="L2" value="brand_group_list"></jsp:param>
</jsp:include>
<style>
    .modal-dialog {
        width: 1000px;
    }
</style>
<div class="container">
    <div class="row-fluid">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>标题</th>
                <th>品牌名称</th>
                <th>状态</th>
                <th>上线日期</th>
                <th>优先级</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="brandGroup" items="${brandGroupList}" varStatus="status">
                <tr>
                    <td>${brandGroup.title}</td>
                    <td>${brandGroup.name}</td>
                    <td>${brandGroup.status}</td>
                    <td>${misfn:formatDateToStrBy(brandGroup.starttime)}</td>
                    <td>${brandGroup.priority}</td>
                    <td><a href="javascript:void(0)" id="${brandGroup.id}" class="viewGoods">查看商品</a>&nbsp;&nbsp;<a
                            href="javascript:void(0)" class="addGoods" id="add_${brandGroup.id}">添加商品</a>&nbsp;&nbsp;
                        <c:if test="${brandGroup.status == 1}">
                            <a href="javascript:void(0)" style="color: #808080">设为闪购</a>
                        </c:if>
                        <c:if test="${brandGroup.status != 1}">
                        <a href="javascript:void(0)" id="flash_${brandGroup.id}" class="addFlash">设为闪购</a></td>
                    </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div id="load" style="display: none"></div>
<script>
    function viewGoods(id) {
        var url = "/item/brand/brandGroup/" + id + "/-1/goods/list?token=" + (new Date()).valueOf();
        $("#load").load(url, function () {
            var body = $("#load").html();
            $("#load").html("");
            toggleModal("查看商品", body);
        });

    }

    function addGoods(id) {
        var url = "/item/brand/addBrandGroupItemData/" + id.split("_")[1] + "?token=" + (new Date()).valueOf();
        $("#load").load(url, function () {
            var body = $("#load").html();
            $("#load").html("");
            toggleModal("增加商品", body);
        });
    }

    function addFlash(id) {
        var url = "/item/brand/addBrandGroupFlashData/" + id.split("_")[1] + "?token=" + (new Date()).valueOf();
        $("#load").load(url, function () {
            var body = $("#load").html();
            $("#load").html("");
            toggleModal("添加闪购", body,"/item/brand/brandGroup/list");
        });

    }
    $($(".viewGoods").click(
            function () {
                viewGoods(this.id);
            }
    ));
    $($(".addGoods").click(
            function () {
                addGoods(this.id);
            }
    ));
    $($(".addFlash").click(
            function () {
                addFlash(this.id);

            }
    ));
</script>
<jsp:include page="/template/modal.jsp"></jsp:include>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

