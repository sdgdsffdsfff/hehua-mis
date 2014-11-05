<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mis" uri="http://mis.hehuababy.com/mis/functions" %>
<%--
  ~ Copyright (c) 2014.
  ~ Author WangJun
  ~ Email  wangjuntytl@163.com
  --%>

<%--导航栏--%>

<header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="/home" class="navbar-brand">荷花管理系统</a>
        </div>
        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
            <ul class="nav navbar-nav">
                <c:choose>
                    <c:when test="${!empty mis:session('current_user')}">
                        <li>
                            <c:if test="${!empty mis:session('system_notice')}">
                                <a href="#getting-started">（通知：${mis:session('system_notice')}）</a>
                            </c:if>
                            <c:if test="${empty mis:session('system_notice')}">
                                <a href="#getting-started">(内部管理系统，非请勿入)</a>
                            </c:if>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="#getting-started">（内部管理系统，非请勿入）</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${!empty mis:session('current_user')}">
                        <li>
                            <a data-target="#" id="dLabel" type="button" data-toggle="dropdown" aria-haspopup="true"
                               aria-expanded="false">${mis:session('current_user')}
                                <span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="/users/updatePassPre">修改密码</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="/logout">退出&nbsp;&nbsp;&nbsp;&nbsp;</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="/login.jsp">请登录&nbsp;&nbsp;&nbsp;&nbsp;</a>
                        </li>
                    </c:otherwise>
                </c:choose>


            </ul>
        </nav>
    </div>
</header>

