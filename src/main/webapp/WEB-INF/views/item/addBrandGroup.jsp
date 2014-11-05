<%@ page contentType="text/html; charset=utf-8" %>
<jsp:include page="/template/pageHeader.jsp">
    <jsp:param name="title" value="添加品牌团"></jsp:param>
</jsp:include>
<jsp:include page="/template/menus.jsp">
    <jsp:param name="L1" value="goods"></jsp:param>
    <jsp:param name="L2" value="addBrandGroup"></jsp:param>
</jsp:include>
<script type="text/javascript" src="/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="/js/jquery.fileupload.js"></script>
<div class="container">
    <form role="form" class="form-horizontal" action="/item/brand/addGroup" method="post">
        <div class="form-group">
            <label for="title" class="col-sm-2 control-label">标题</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="title" placeholder="小于30个字，可重复" name="title">

            </div>
        </div>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">品牌名</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" placeholder="小于20个字，可重复" name="name">
            </div>
        </div>
        <div class="form-group">
            <label for="uploadFileId1" class="col-sm-2 control-label">品牌图</label>

            <div class="col-sm-10">
                <input id="uploadFileId1" type="file" class="btn btn-default"/> <span id="imageUrlIds"></span>
                <input id="imageId" type="hidden" name="imageurl" value=""/>

                <p class="help-block">只可添加一张图片，图片大小720*414</p>
            </div>
        </div>

        <div class="form-group">
            <label for="desc" class="col-sm-2 control-label">品牌介绍</label>

            <div class="col-sm-10">
                <textarea class="form-control" id="desc" rows="3" placeholder="小于300字" name="desc"></textarea>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">提交</button>
            </div>
        </div>
    </form>
</div>
<script src="/js/addBrandGroup.js"></script>
<jsp:include page="/template/pageFooter.jsp"></jsp:include>

