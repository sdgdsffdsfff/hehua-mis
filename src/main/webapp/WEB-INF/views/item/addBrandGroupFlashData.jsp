<%@ page contentType="text/html; charset=utf-8" %>

<form role="form" class="form-horizontal" action="/item/brand/addBrandGroupFlash" method="post">
    <div class="form-group">
        <label for="date" class="col-sm-2 control-label">请输入日期：</label>
        <div class="col-sm-10">
            <input type="text" class="datetimepicker input-sm" name="date" id="date">
        </div>
        <input type="hidden" value="${id}" name="id">
    </div>
</form>

<script>

    $('.datetimepicker').datetimepicker({
        language: "zh-CN",
        weekStart: 1,
        autoclose: true,
        todayHighlight: 1,
        minView: 2,
        format: "yyyy-mm-dd",
        forceParse: 0
    });

    $(function () {
        var data = new Date();
        $(".datetimepicker").val(data.format('yyyy-MM-dd'));
    });

</script>