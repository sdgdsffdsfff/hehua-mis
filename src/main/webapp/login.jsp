<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="用户登陆！！荷花管理后台"></jsp:param>
</jsp:include>
<div class="container">
    <div style="margin-top: 150px;">
        <div style="width: 400px;margin: 0 auto;">
            <form class="form-horizontal" role="form" action="/gogo" method="post">
                <input type="hidden" name="CSRFToken" value="${CSRFToken}">

                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-3 control-label">用户名</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputEmail3" placeholder="Account" name="username"
                               required="true" message="email是必须的" value="${requestScope.username}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-3 control-label">密码</label>

                    <div class="col-sm-9">
                        <input type="password" class="form-control" id="inputPassword3" placeholder="Password"
                               name="password" required="true" message="password是必须的">

                        <div class="text-danger">${requestScope.tip}</div>

                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-3 col-sm-9">
                        <button type="submit" class="btn btn-default" id="normalSubmit">登录</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/template/pageFooter.jsp"></jsp:include>