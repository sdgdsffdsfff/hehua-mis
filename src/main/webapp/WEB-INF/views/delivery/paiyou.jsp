<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="物流管理"></jsp:param>
</jsp:include>
<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="delivery"></jsp:param>
    <jsp:param name="L2" value="paiyou_switch"></jsp:param>
</jsp:include>


<div class="container">
    <div class="row-fluid" style="padding-top: 10px">
        <c:if test="${requestScope['switch'] == 1}">
            <p class="text-danger">当前订单付款后会自动同步到派友，点击下面按钮关闭</p>
            <button id="paiyouSwitch" onclick="paiyouSwithch('off')" class="btn-primary">关闭同步</button>
        </c:if>
        <c:if test="${requestScope['switch'] == 0}">
            <p class="text-danger">当前订单付款后不会自动同步到派友，点击下面按钮开启</p>
            <button id="paiyouSwitch" onclick="paiyouSwithch('on')" class="btn-primary">开启同步</button>
        </c:if>
    </div>

</div>



<script type="text/javascript">

    function paiyouSwithch(sw) {
        if (confirm("您确定要执行此操作？")) {
            $.ajax({
                type: "POST",
                url: "/delivery/paiyou/" + sw,
                dataType: "json",
                success: function (data) {
                    alert(data.message);
                    window.location.reload();
                }
            });
        }
    }
</script>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>
