<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://mis.hehuababy.com/mis/functions" prefix="misfn" %>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="新增达人"></jsp:param>
</jsp:include>


<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="users"></jsp:param>
    <jsp:param name="L2" value="tarentoList"></jsp:param>
</jsp:include>

<link href="/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script type="text/javascript" src="/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="/js/jquery.fileupload.js"></script>

<script type="text/javascript" src="/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/js/locals/bootstrap-datetimepicker.zh-CN.js"></script>
<script>
    var mis_data = {
        rules: {
            userid: {
                required: true,
                number: true
            },
            name: {
                required: true,
                maxlength: 16,
                minlength: 2
            },
            desc: {
                required: true
            }


        },
        messages: {
            userid: {
                required: "达人id不可以为空",
                number: "id必须是数字"
            },
            name: {
                required: "达人昵称不可以为空",
                maxlength: "2-16个字符，一个汉字是两个字符:",
                minlength: "2-16个字符，一个汉字是两个字符:"
            },
            desc: {
                required: "达人介绍不可以为空"
            }
        },
        url: "/users/updateTarento",
        nextUrl: "/users/tarentoList",
        postFront: function () {
            if ($("#imageId").val() == null || $("#imageId").val() == "") {
                alert("未上传达人图片");
                return false;
            }
            return true;
        }

    };

</script>
<div class="container">
    <form role="form" class="form-horizontal" method="post" action="addTarento">
        <div class="form-group">
            <label for="userid" class="col-sm-2 control-label">账号</label>

            <div class="col-sm-5">
                <input type="text" class="form-control" id="userid" placeholder="达人id号" name="account"
                       value="${account}" readonly>
                <input type="hidden" class="form-control" id="id" name="id"
                       value="${daren.id}">
            </div>
            <div class="col-sm-5"></div>
        </div>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">昵称</label>

            <div class="col-sm-5">
                <input type="text" class="form-control" id="name" placeholder="2-16个字符" name="name"
                       value="${daren.name}">
            </div>
            <div class="col-sm-5"></div>

        </div>
        <div class="form-group">
            <label for="nickname" class="col-sm-2 control-label">名字</label>

            <div class="col-sm-5">
                <input type="text" class="form-control" id="nickname" placeholder="真实姓名" name="nickname"
                       value="${daren.nickname}">
            </div>
            <div class="col-sm-5"></div>

        </div>
        <div class="form-group">
            <label for="location" class="col-sm-2 control-label">城市</label>

            <div class="col-sm-5">
                <select class="form-control" id="location" name="location">
                    <c:forEach items="${requestScope.location}" var="city">
                        <c:choose>
                            <c:when test="${city.name == daren.location}">
                                <option value="${city.name}" selected="">${city.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${city.name}">${city.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="stage" class="col-sm-2 control-label">达人状态</label>

            <div class="col-sm-5">
                <select class="form-control" name="stage" id="stage">
                    <c:choose>
                        <c:when test="${daren.stage == '备孕中'}">
                            <option value="1" selected="">备孕中</option>
                        </c:when>
                        <c:otherwise>
                            <option value="1">备孕中</option>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>

                        <c:when test="${daren.stage == '怀孕中'}">
                            <option value="2" selected="">怀孕中</option>
                        </c:when>
                        <c:otherwise>
                            <option value="2">怀孕中</option>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${daren.stage == '已有宝宝'}">
                            <option value="3" selected="">已有宝宝</option>
                        </c:when>
                        <c:otherwise>
                            <option value="3">已有宝宝</option>

                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
        </div>
        <div id="choose" style="display: none">
            <div class="form-group">
                <label for="descA" class="col-sm-2 control-label">宝宝性别</label>

                <div class="col-sm-5">
                    <c:choose>
                        <c:when test="${daren.gender == '男'}">
                            <input type="radio" id="descA" placeholder="1" name="gender" value="男" checked>
                        </c:when>
                        <c:otherwise>
                            <input type="radio" id="descA" placeholder="1" name="gender" value="男">
                        </c:otherwise>
                    </c:choose>
                    <label for="descA">男</label>
                    <c:choose>
                        <c:when test="${daren.gender == '女'}">
                            <input type="radio" id="descB" placeholder="2" name="gender" value="女" checked>
                        </c:when>
                        <c:otherwise>
                            <input type="radio" id="descB" placeholder="2" name="gender" value="女">
                        </c:otherwise>
                    </c:choose>
                    <label for="descA">女</label>
                </div>
            </div>
            <div class="form-group">
                <label for="birthday" class="col-sm-2 control-label">宝宝生日</label>

                <div class="col-sm-5">
                    <input type="text" class="form-control datetimepicker" id="birthday" placeholder="" name="birthday"
                           value="${daren.birthday}">
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="desc" class="col-sm-2 control-label">达人介绍</label>

            <div class="col-sm-5">
                <textarea class="form-control" id="desc" placeholder="" name="description">
                    ${daren.description}
                </textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">达人头像</label>

            <div class="col-sm-5">
                <a class="btn btn-success" id="uploadImage">上传图像</a>&nbsp;&nbsp;&nbsp;<span id="uploadImageUrl"></span>

                <div style="display: none">
                    <input id="uploadFileId" type="file" class="btn btn-default"/> <span id="imageUrlIds"></span>
                    <input id="imageId" type="hidden" name="avatarid" value="${daren.avatarid}"/>
                </div>
                <img src="${image}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-2"></div>
            <div class="col-sm-5">
                <button type="submit" class="btn btn-primary">保存</button>
            </div>
        </div>

    </form>
</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>
<script src="/js/addTarento.js"></script>
