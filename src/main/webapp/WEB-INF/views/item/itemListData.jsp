<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>

<%--
<div>
    <form class="well form-search" id="queryForm" method="post" action="/users/search">
        <div class="input-append search">
            <table>
                <tr>
                    <td><label for="status">状态：</label></td>
                    <td>
                        <select class="form-control" name="id" id="status">
                            <option value="-1">
                                全部
                            </option>
                            <option value="1">上线</option>
                            <option value="2">下线</option>
                            <option value="3">删除</option>
                        </select>
                    </td>
                    <td>  &nbsp;
                        <button type="submit" class="btn btn-default">搜索</button>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
--%>
<table class="table table-bordered table-striped table-hover">
    <thead>
    <tr>
        <th>商品名称</th>
        <th>商品标识</th>
        <th>商品发布时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${itemList}" varStatus="status">
        <tr>
            <td>${item.name}</td>
            <td>${item.id}</td>
            <td>${misfn:formatDateToStrBy(item.cts)}</td>
            <td>
                <c:choose>
                    <c:when test="${statusList[status.index]== 1}">
                        <a href="javascript:void(0)" class="offline" id="${idList[status.index]}">下线</a>
                        &nbsp;&nbsp;<a href="javascript:void(0)" class="delete" id="${idList[status.index]}">删除</a>
                    </c:when>
                    <c:when test="${statusList[status.index] == 2}">
                        <span style="color: #808080">已下线</span>
                        &nbsp;&nbsp;<a href="javascript:void(0)" class="delete" id="${idList[status.index]}">删除</a>
                    </c:when>
                    <c:when test="${statusList[status.index] == 3}">
                        <span style="color: #808080">已下线</span>
                        &nbsp;&nbsp; <span style="color: #808080">已删除</span>
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--
<script>
    function viewSearchGoods(id,status) {
        var url = "/item/brand/brandGroup/" + id + "/"+status+"/goods/list";
        $("#load").load(url, function () {
            var body = $("#load").html();
            $("#myModalBody").html(body);

        });

    }

    $($("#status").change(
            function () {
                alert(this.id);
                viewSearchGoods(this.id,this.value);
            }
    ));
</script>

--%>

<script>
    $($(".offline").click(
            function () {
                var url = "/item/brand/brandGroupItem/" + this.id + "/2";
                var current = $(this);
                $.get(url, function (data) {
                    if (data == 1)
                        current.html("已下线").css("color", "grey").removeClass("offline");
                });
            }
    ));
    $($(".delete").click(
            function () {
                if (confirm("确定要删除该商品吗？\n只会在品牌团中删除，不会影响仓库中的商品")) {
                    var url = "/item/brand/brandGroupItem/" + this.id + "/3";
                    var current = $(this);
                    $.get(url, function (data) {
                        if (data == 1)
                            current.html("已删除").css("color", "grey").removeClass("delete");
                    });
                }
            }
    ));
</script>
