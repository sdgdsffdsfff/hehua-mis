<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<div class="row-fluid" style="text-align: right">
    <div class="row-fluid">
        <c:if test="${pagination.getPageCount() > 0}">
            第${pagination.getNo()}页 共${pagination.getPageCount()}页
        </c:if>
    </div>
    <div class="row-fluid">
        <ul class="pagination">
            <li class="<c:if test='${pagination.isHeadNo()}'>disabled</c:if>">
                <a href="javascript:targetNo();" pageNo="${pagination.getPreNo()}"><<</a>
            </li>
            <%--<c:choose>--%>
                <%--<c:when test="${pagination.getNo() <= 10000}">--%>
                    <%--<c:forEach var="i" begin="1" end="600">--%>
                        <%--<c:if test="${i <= pagination.getPageCount()}">--%>
                            <%--<li class="<c:if test='${i == pagination.getNo()}'>active</c:if>">--%>
                                <%--<a href="javascript:void(0);" pageNo="${i}">${i}</a>--%>
                            <%--</li>--%>
                        <%--</c:if>--%>
                    <%--</c:forEach>--%>
                <%--</c:when>--%>
            <%--</c:choose>--%>
            <c:choose>
                 <c:when test="${pagination.getNo() > 2}">
                     <c:choose>
                         <c:when test="${((pagination.getPageCount() - pagination.getNo()) < 2) && (pagination.getPageCount()-4 > 0)}">
                             <c:forEach var="i" begin="${pagination.getPageCount()-4}" end="${pagination.getPageCount()}">
                                 <c:if test="${i > 0}">
                                     <li class="<c:if test='${i == pagination.getNo()}'>active</c:if>">
                                         <a href="javascript:void(0);" pageNo="${i}">${i}</a>
                                     </li>
                                 </c:if>
                             </c:forEach>
                         </c:when>
                         <c:otherwise>
                             <c:forEach var="i" begin="${pagination.getNo()-2}" end="${pagination.getNo()+2}">
                                 <c:if test="${i <= pagination.getPageCount()}">
                                     <li class="<c:if test='${i == pagination.getNo()}'>active</c:if>">
                                         <a href="javascript:void(0);" pageNo="${i}">${i}</a>
                                     </li>
                                 </c:if>
                             </c:forEach>
                         </c:otherwise>
                     </c:choose>
                 </c:when>
                <c:otherwise>
                    <c:forEach var="i" begin="1" end="5">
                        <c:if test="${i <= pagination.getPageCount()}">
                            <li class="<c:if test='${i == pagination.getNo()}'>active</c:if>">
                                <a href="javascript:void(0);" pageNo="${i}">${i}</a>
                            </li>
                        </c:if>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

            <li class="<c:if test='${pagination.isEndNo()}'>disabled</c:if>"><a href="javascript:void(0);"
                                                                                pageNo="${pagination.getNextNo()}">>></a>
            </li>
        </ul>
    </div>
</div>
<script type="text/javascript">
    $(".pagination li a").click(function () {
        if ($(this).parent().attr("class") == "active") return;
        $("#pageNo").val($(this).attr("pageNo"));
        $(".form-search").submit();
    });

    function targetNo() {
        var numVal = $("#gotoNumber").val()
        if (numVal == "") {
            alert("请输入要进入的页面")
        } else {
            $("#pageNo").val(numVal);
            $(".form-search").submit();
        }
    }

    function checkEnter(event) {
        if (event.keyCode == 13) {
            targetNo();
        }
    }


</script>