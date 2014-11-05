<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="用户管理"></jsp:param>
</jsp:include>


<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="users"></jsp:param>
    <jsp:param name="L2" value="tarentoList"></jsp:param>
</jsp:include>

<div class="container">
    <div class="row-fluid">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>昵称</th>
                <th>名字</th>
                <th>位置</th>
                <th>创建时间</th>
                <th>状态</th>
                <th>宝宝性别</th>
                <th>宝宝生日</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="daren" items="${darenList}" varStatus="status">
                <tr>
                    <td>${daren.userid }</td>
                    <td>${misfn:escapeHtml(daren.name) }</td>
                    <td>${misfn:escapeHtml(daren.nickname) }</td>
                    <td>${daren.location }</td>
                    <td>${misfn:formatDateToStrBy(user.cts)}</td>
                    <td>${daren.stage} </td>
                    <td>${daren.gender} </td>
                    <td>${daren.birthday} </td>
                    <td><a href="/users/findTarento?userid=${daren.userid}">查看详情</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>
