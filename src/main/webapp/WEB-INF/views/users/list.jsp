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
    <jsp:param name="L2" value="users_list"></jsp:param>
</jsp:include>

<div class="container">
    <div style="display: none">
        <form class="well form-search" id="queryForm" method="get" action="/users/list">
            <input name="pageNo" id="pageNo">
        </form>
    </div>
    <div class="highlight">
        <form class="form-inline" role="form"  action="/users/search" method="post">
            <div class="form-group">
                <input type="text" class="form-control" id="id" placeholder="id" name="id">
            </div>
            <div class="form-group">
                <div class="input-group">
                    <input class="form-control" type="text" placeholder="账号" name="account">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <input class="form-control" type="text" placeholder="邮箱" name="mail">
                </div>
            </div>
            <div class="form-group">
                <input type="text" class="form-control" id="cts" placeholder="注册时间" name="cts">
            </div>
            <button type="submit" class="btn btn-default">搜索</button>
        </form>
    </div>

    <div class="row-fluid" style="text-align: center">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>账号</th>
                <th>昵称</th>
                <th>注册时间</th>
                <th>地址</th>
                <%--<th>妈妈状态</th>--%>
                <%--<th>宝宝年龄</th>--%>
                <%--<th>宝宝状态</th>--%>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}" varStatus="status">
                <tr>
                    <td>${user.id }</td>
                    <td>${user.account }</td>
                    <td>${misfn:escapeHtml(user.name) }</td>
                    <td>${misfn:formatDateToStrBy(user.cts)}</td>
                    <td>${misfn:escapeHtml(user.desc) }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/template/pagination.jsp"></jsp:include>

    </div>
</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>
