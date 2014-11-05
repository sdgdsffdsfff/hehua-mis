<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="众测管理"></jsp:param>
</jsp:include>


<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="votesManage"></jsp:param>
    <jsp:param name="L2" value="vote_new_audit"></jsp:param>
</jsp:include>

<div class="container">
    <div class="row-fluid" style="text-align: center">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>账号</th>
                <th>昵称</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}" varStatus="status">
                <tr>
                    <td>${user.id }</td>
                    <td>${user.account }</td>
                    <td>${misfn:escapeHtml(user.name) }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

