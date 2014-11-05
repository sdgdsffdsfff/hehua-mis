$("#account").blur(function () {
        var uid = $(this).val();
        if (uid != null) {
            $.getJSON("/users/validUserId?account=" + uid, function (data) {
                $("#account").closest('.form-group').removeClass('has-error');
                $("#account").parent('div').find('span').remove();
                if (data.code != 1) {
                    $("#account").closest('.form-group').addClass('has-error');
                    $("#account").parent('div').append("<span class='text-danger'>"+data.result+"</span>");
                } else {
                    $("#account").closest('.form-group').removeClass('has-error');
                    $("#account").parent('div').find('span').remove();
                }
            });
        }
    }
);

$("#stage").click(function(){
    var state = $("#stage").val();
    if(state == 3){
        $("#choose").css("display","block");
    }else{
        $("#choose").css("display","none");

    }


});

$("#uploadImage").click(function(){
    $("#uploadFileId").trigger("click");
    $("#uploadFileId").change(function(){
       $("#uploadImageUrl").html($("#uploadFileId").val());
    });

    $('#uploadFileId').fileupload({
        autoUpload: true,
        url: "/upload",
        dataType: 'json',
        done: function (e, data) {
            if (data.result.state == "SUCCESS") {
                alert("上传图片成功!");
                $("#imageId").val(data.result.id);
            } else {
                alert("上传文件失败");
            }

        },
        progressall: function (e, data) {//设置上传进度事件的回调函数
            var progress = parseInt(data.loaded / data.total * 5, 10);
            $('#progress .bar').css(
                'width',
                    progress + '%'
            );
        }
    });
});

$(function(){
    var state = $("#stage").val();
    if(state == 3){
        $("#choose").css("display","block");
    }else{
        $("#choose").css("display","none");

    }

    $('#birthday').datetimepicker({
        language: "zh-CN",
        weekStart: 1,
        autoclose: true,
        todayHighlight: 1,
        minView: 2,
        format: "yyyy-mm-dd",
        forceParse: 0
    });

});