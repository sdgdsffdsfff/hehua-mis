<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mis" uri="http://mis.hehuababy.com/mis/functions" %>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="欢迎！！荷花管理后台"></jsp:param>
</jsp:include>
<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="xxx"></jsp:param>
    <jsp:param name="L2" value="xxx"></jsp:param>
</jsp:include>
<div class="container" style="margin-top: 0px;;">
    <h4  class="text-success page-header">你已成功进入系统...</h4>
    <h5 class="page-header text-success">个人信息</h5>
    <div class="highlight">
        <h5>账号:&nbsp;&nbsp;&nbsp;${mis:session("current_user")}</h5>
        <h5>姓名:&nbsp;&nbsp;&nbsp;${requestScope.user.name}</h5>
        <h5>角色:&nbsp;&nbsp;&nbsp;<c:forEach items="${requestScope.user.roles}" var="role">
            ${role.name}&nbsp;&nbsp;&nbsp;
        </c:forEach></h5>
    </div>
    <h5 class="page-header text-success">消息通知</h5>

    <div class="highlight">
        <c:if test="${!empty sessionScope.system_notice}">
            <h5>${sessionScope.system_notice}</h5>
        </c:if>
        <c:if test="${empty sessionScope.system_notice}">
            <h5 class="text-muted">没有消息通知</h5>
        </c:if>
    </div>
</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>