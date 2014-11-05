<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="手机版本管理"></jsp:param>
</jsp:include>


<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="versions"></jsp:param>
    <jsp:param name="L2" value="version_control"></jsp:param>
</jsp:include>
<script type="text/javascript" src="/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="/js/jquery.fileupload.js"></script>
<script>
    var mis_data = {
        rules: {
            version: {
                required: true,
                maxlength: 30
            },
            channel: {
                required: true,
                maxlength: 30

            },
            releasenote: {
                required: true,
                maxlength: 300
            },
            downloadurl: {
                required: true,
                maxlength: 100

            }
        },
        messages: {
            channel: {
                required: "渠道名称必须填写",
                maxlength: "小于30个字，可重复"
            },
            version: {
                required: "版本名称必须填写",
                maxlength: "小于30个字，可重复"
            },
            releasenote: {
                required: "升级内容必须填写",
                maxlength: "要小于300个字"
            },
            downloadurl: {
                required: "下载url必须填写",
                maxlength: "要小于100个字"
            }
        },
        url: "/users/addPhoneVersion",
        nextUrl: "/users/version/list"

    }
</script>
<div class="container">
    <form role="form" class="form-horizontal" id="formPhoneVersionId" method="post">
        <input type="hidden" name="CSRFToken" value="${CSRFToken}">

        <div class="form-group">
            <label for="uploadFileId" class="col-sm-2 control-label">上传文件</label>

            <div class="col-sm-10">
                <input id="uploadFileId" type="file" class="btn btn-default"/> <span id="imageUrlIds"></span>
                <input id="imageId" type="hidden" name="imageurl" value=""/>

                <p class="help-block">只可添加一个文件，文件大小最好10M内</p>
            </div>
        </div>
        <div class="form-group">
            <label for="versionId" class="col-sm-2 control-label">版本信息</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="versionId" placeholder="按照规范X.X.X" name="version">

            </div>
        </div>
        <div class="form-group">
            <label for="channelId" class="col-sm-2 control-label">渠道信息</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="channelId" placeholder="小于20个字" name="channel">
            </div>
        </div>
        <div class="form-group">
            <label for="channelId" class="col-sm-2 control-label">是否强制刷新</label>

            <div class='col-sm-10'>
                <select name='forceupdate'>
                    <option value='1'>强制
                    <option value='0'>不强制
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="downLoadUrlId" class="col-sm-2 control-label">客户端下载url</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="downLoadUrlId" placeholder="小于20个字" name="downloadurl">
            </div>
        </div>

        <div class="form-group">
            <label for="releaseNoteId" class="col-sm-2 control-label">升级内容</label>

            <div class="col-sm-10">
                <textarea class="form-control" id="releaseNoteId" rows="3" placeholder="小于300字"
                          name="releasenote"></textarea>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">保存</button>
            </div>
        </div>
    </form>
</div>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>
<script type="text/javascript" src="/js/addPhoneVersion.js"></script>
