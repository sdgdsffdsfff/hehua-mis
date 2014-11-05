<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  ~ Copyright (c) 2014.
  ~ Author WangJun
  ~ Email  wangjuntytl@163.com
  --%>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="手机版本管理"></jsp:param>
</jsp:include>


<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="versions"></jsp:param>
    <jsp:param name="L2" value="version_control"></jsp:param>
</jsp:include>

<div class="container">
    <div class="row-fluid" style="text-align: center">
        <div style="display: none">
            <form class="well form-search" id="queryForm" method="get" action="/users/version/list">
                <input name="pageNo" id="pageNo">
            </form>
        </div>
        <a href="/add/phoneVersion" class="btn-link" data-toggle="modal">新增版本信息 </a>

        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th class="text-center">标识</th>
                <th class="text-center">版本信息</th>
                <th class="text-center">渠道信息</th>
                <th class="text-center">下载url</th>
                <th class="text-center">更新内容</th>
                <th class="text-center">是否强制更新</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="phoneVersion" items="${phoneVersionList}">
                <tr>
                    <td>${phoneVersion.id}</td>
                    <td>${phoneVersion.version}</td>
                    <td>${phoneVersion.channel}</td>
                    <td width="30%">${phoneVersion.downloadurl}</td>
                    <td width="30%">${phoneVersion.releasenote}</td>
                    <td>
                        <c:if test="${phoneVersion.forceupdate == 1}">
                            <a href="#"  class="btn-link" data-toggle="modal" onclick="updateVersion(${phoneVersion.id}, 0)">是</a>
                        </c:if>
                        <c:if test="${phoneVersion.forceupdate == 0}">
                            <a href="#"  class="btn-link" data-toggle="modal" onclick="updateVersion(${phoneVersion.id}, 1)">否</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/template/pagination.jsp"></jsp:include>
    </div>
</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>
<script>

    function updateVersion(phoneVersionId, forceUpdateStatus) {
        $.ajax({
            type: "POST",
            url: "/users/updateVersion",
            data: "id=" + phoneVersionId + "&isForceUpdate=" + forceUpdateStatus,
            dataType: "json",
            success: function (data) {
                if (data.code == 1) {
                    alert(data.msg);
                    document.location.href = "/users/version/list";
                } else {
                    alert(data.msg);
                }
            }
        })
    }
</script>
