<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>
<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="商品列表详情"></jsp:param>
</jsp:include>
<%@ include file="/template/star.inc" %>
<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="goods"></jsp:param>
    <jsp:param name="L2" value="good_list"></jsp:param>
</jsp:include>
<script>
    var mis_data = {
        rules: {
            title: {
                required: true,
                maxlength: 50
            },
            summary: {
                required: true,
                maxlength: 200
            }
        },
        messages: {
            title: {
                required: "不可以为空哦",
                maxlength: "超出50个字"
            },
            summary: {
                required: "不可以为空哦",
                maxlength: "超出200个字"
            }
        },
        url: "/item/appraise/add",
        nextUrl:"/item/list",
        postFront:function() {
            var descStr = UE.getEditor('post').getContent();
            if (descStr == null || descStr.length <= 200) {
                alert("评测内容为空,或者少于200字");
                return false;
            }
            $("#appraiseVal").val(descStr);
            var score = $("#scoreId").val();
            if (isNumber(score) && score > 0 && score <= 10) {
                return true;
            } else {
                alert("评测的分数必须大于0且小于10分");
                return false;
            }
        }
    }
</script>
<div class="container">
    <div>
        <form role="form" method="post">
            <div style="clear: left">
                <h5 class="float-left">商品达人</h5>

                <div class="float-clear-left input-group col-lg-12">
                    <select class="form-control" name="uid">
                        <c:forEach items="${darenList}" var="daren">
                            <option value="${daren.id}">${daren.name}</option>
                        </c:forEach>
                    </select>
                    <input name="itemid" type="hidden" value="${itemId}">
                </div>
            </div>

            <div style="clear: left">
                <h5 class="float-left">标题</h5>

                <div class="float-clear-left input-group col-lg-12">
                    <input type="text" class="form-control" placeholder="不要超过20个字哦" id="title" name="title">
                </div>
            </div>
            <div>
                <h5>摘要</h5>

                <div class="input-group col-lg-12">
                    <textarea class="form-control" placeholder="不要超过140个字哦" rows="6" name="summary"></textarea>
                </div>
            </div>
            <div>
                <h5>评分</h5>

                <div class="input-group col-lg-12">
                    <input id="input-id" type="number" class="rating" data-min="0" data-max="10" data-step="1"
                           data-show-clear="false" data-show-caption="true" data-size="sm">
                    <input type="hidden" name="score" value="" id="scoreId">

                </div>
            </div>

            <div>
                <h5>正文（不要少于500字哦）</h5>
                <jsp:include page="/template/editor.jsp"></jsp:include>
                <input name="appraise" type="hidden" id="appraiseVal">
            </div>

            <div>
                <div class="col-lg-2">
                    <button class="btn btn-primary" type="submit">提交</button>
                </div>
            </div>
        </form>
    </div>
</div>


</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

<script>
    $(function () {
        UE.getEditor('post', {
            initialFrameHeight:320,
            autoHeight:true
        });

        $("#input-id").on("rating.change", function (event, value, caption) {
            $("#scoreId").val(value);
        });
    });
</script>