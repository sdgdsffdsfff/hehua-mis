<%@ page contentType="text/html; charset=utf-8" %>

<form role="form" class="form-horizontal" action="/item/brand/addBrandGroupItem" method="post">
    <div class="form-group">
        <label for="title" class="col-sm-2 control-label">商品ID</label>
        <div class="col-sm-10">
            <input name="id" value="${id}" type="hidden">
            <input type="text" class="form-control" id="title" placeholder="请输入商品id，用,分割" name="ids" required="required">
        </div>
    </div>
</form>