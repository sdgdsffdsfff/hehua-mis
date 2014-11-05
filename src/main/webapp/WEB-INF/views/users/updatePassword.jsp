<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="密码修改！！荷花管理后台"></jsp:param>
</jsp:include>
<link href="/css/strength-meter.css" media="all" rel="stylesheet" type="text/css">
<script src="/js/strength-meter.js"></script>

<div class="container">
    <div style="margin-top: 150px;">
        <div style="width: 600px;margin: 0 auto;" id="updateForm">
            <form class="form-horizontal" role="form" action="/users/updatePass" method="post">
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-3 control-label">新密码</label>

                    <div class="col-sm-9">
                        <input type="password" class="form-control strength" id="inputEmail3" placeholder="新密码"
                               name="password"
                               required="true" message="新密码是必须的" value="${requestScope.username}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-3 control-label">旧密码</label>

                    <div class="col-sm-9">
                        <input type="password" class="form-control strength" id="inputPassword3" placeholder="旧密码"
                               name="oldPassword" required="true" message="password是必须的">

                        <div class="text-danger">${requestScope.tip}</div>

                    </div>
                </div>
            </form>
            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-9">
                    <button class="btn btn-default" id="normalSubmit">更新</button>
                </div>
            </div>

        </div>
    </div>
</div>
<script>
    $("#normalSubmit").click(function () {
        if ($("form").validForm() && validPassStrength()) {
            var url = $("#updateForm form").attr("action");
            var submit_data = $("#updateForm form").serialize();
            $.post(url, submit_data, function (response) {
                if (response.code == 1)
                    toggleModal("通知", response.result, "/logout");
                else
                    toggleModal("通知", response.result);
            });
        }
    });

    function validPassStrength() {
        var strength = $('#inputEmail3').strength('score');
        var verdict = $('#inputEmail3').strength('verdict');
        if (strength.split('%')[0] < 50) {
            toggleModal("通知", "新密码强度不能低于50%，请尝试字母大小写数字与特殊字符混合组合");
            return false;
        }
        return true;
    }
</script>
<jsp:include page="/template/modal.jsp"></jsp:include>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

