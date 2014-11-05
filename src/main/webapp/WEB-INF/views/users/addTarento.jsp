<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="新增达人"></jsp:param>
</jsp:include>


<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="users"></jsp:param>
    <jsp:param name="L2" value="addTarento"></jsp:param>
</jsp:include>

<link href="/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script type="text/javascript" src="/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/js/locals/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript" src="/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="/js/jquery.fileupload.js"></script>


<script>
    var mis_data = {
        rules: {
            account: {
                required: true
            },
            name: {
                required: true,
                maxlength: 16,
                minlength: 2
            },
            password: {
                required: true,
                maxlength: 12,
                minlength: 6
            },
            title: {
                required: true
            },
            desc: {
                required: true
            }


        },
        messages: {
            account: {
                required: "达人账号不可以为空"
            },
            name: {
                required: "达人昵称不可以为空",
                maxlength: "2-16个字符，一个汉字是两个字符:",
                minlength: "2-16个字符，一个汉字是两个字符:"
            },
            password: {
                required: "达人密码不可以为空",
                maxlength: "密码最大长度为12",
                minlength: "密码最小的长度为6"
            },
            title: {
                required: "必填",
                maxlength: "2-16个字符，一个汉字是两个字符:",
                minlength: "2-16个字符，一个汉字是两个字符:"
            },
            desc: {
                required: "达人介绍不可以为空"
            }
        },
        url: "/users/addTarento",
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
            <label for="account" class="col-sm-2 control-label">账号</label>

            <div class="col-sm-5">
                <input type="text" class="form-control" id="account" placeholder="达人账户" name="account">
            </div>
            <div class="col-sm-5"></div>
        </div>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">昵称</label>

            <div class="col-sm-5">
                <input type="text" class="form-control" id="name" placeholder="2-16个字符" name="name">
            </div>
            <div class="col-sm-5"></div>

        </div>
        <div class="form-group">
            <label for="nickname" class="col-sm-2 control-label">名称</label>

            <div class="col-sm-5">
                <input type="text" class="form-control" id="nickname" placeholder="真实姓名，不是必填项" name="nickname">
            </div>
            <div class="col-sm-5"></div>

        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">密码</label>

            <div class="col-sm-5">
                <input type="text" class="form-control" id="password" placeholder="6-12个字符" name="password">
            </div>
            <div class="col-sm-5"></div>

        </div>
        <div class="form-group">
            <label for="title" class="col-sm-2 control-label">认证头衔</label>

            <div class="col-sm-5">
                <input type="text" class="form-control" id="title" placeholder="2-16个字符" name="title">
            </div>
            <div class="col-sm-5"></div>

        </div>
        <div class="form-group">
            <label for="location" class="col-sm-2 control-label">城市</label>

            <div class="col-sm-5">
                <select class="form-control" id="location" name="location">
                    <c:forEach items="${requestScope.location}" var="city">
                        <option value="${city.name}">${city.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="stage" class="col-sm-2 control-label">达人状态</label>

            <div class="col-sm-5">
                <select class="form-control" name="stage" id="stage">
                    <option value="1">备孕中</option>
                    <option value="2">怀孕中</option>
                    <option value="3">已有宝宝</option>
                </select>
            </div>
        </div>
        <div id="choose" style="display: none">
            <div class="form-group">
                <label for="descA" class="col-sm-2 control-label">宝宝性别</label>

                <div class="col-sm-5">
                    <input type="radio" id="descA" placeholder="1" name="gender" value="男"><label for="descA">男</label>
                    <input type="radio" id="descB" placeholder="2" name="gender" value="女"><label for="descA">女</label>
                </div>
            </div>
            <div class="form-group">
                <label for="birthday" class="col-sm-2 control-label">宝宝生日</label>

                <div class="col-sm-5">
                    <input type="text" class="form-control datetimepicker" id="birthday" placeholder="" name="birthday">
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="desc" class="col-sm-2 control-label">达人介绍</label>

            <div class="col-sm-5">
                <textarea class="form-control" id="desc" placeholder="" name="description">

                </textarea>
            </div>
        </div>
        <div class="col-sm-8 alert alert-info">
            <p>
                <label class="col-sm-8">1.图片必须小于2m，仅限jpg，jpg，bmp格式</label>
            </p>

            <p>
                <label class="col-sm-8">2.用截图工具，截成72*72的</label>
            </p>
        </div>
        <div class="form-group">
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">达人头像</label>

            <div class="col-sm-5">
                <a class="btn btn-success" id="uploadImage">上传图像</a>&nbsp;&nbsp;&nbsp;<span id="uploadImageUrl"></span>

                <div style="display: none">
                    <input id="uploadFileId" type="file" class="btn btn-default"/> <span id="imageUrlIds"></span>
                    <input id="imageId" type="hidden" name="avatarid"/>
                </div>
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
