var MyValidator = function () {
    var handleSubmit = function () {
        $('form').validate({
            errorElement: 'span',
            errorClass: 'help-block',
            focusInvalid: false,
            rules: {
                name: {
                    required: true,
                    maxlength: 20
                },
                title: {
                    required: true,
                    maxlength: 30

                },
                desc: {
                    required: true,
                    maxlength: 300
                },
                imageUrl:{
                    required: true
                }
            },
            messages: {
                name: {
                    required: "品牌名是必须得",
                    maxlength: "小于20个字，可重复"
                },
                title: {
                    required: "标题是必须得",
                    maxlength: "小于30个字，可重复"
                },
                desc: {
                    required: "品牌介绍是必须得",
                    maxlength: "要小于300个字"
                },
                imageUrl:{
                    required: "你还未上传图片"
                }
            },

            highlight: function (element) {
                $(element).closest('.form-group').addClass('has-error');
            },

            success: function (label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function (error, element) {
                element.parent('div').append(error);
            },

            submitHandler: function (form) {
                form.submit();
            }
        });

        $('form input').keypress(function (e) {
            if (e.which == 13) {
                if ($('form').validate().form()) {
                    $('form').submit();
                    document.location.href = "item/brand/brandGroup/list";
                }
                return false;
            }
        });
    }
    return {
        init: function () {
            handleSubmit();
        }
    };

}();
MyValidator.init();
$('#uploadFileId1').fileupload({
    autoUpload: true,
    url: "/upload",
    dataType: 'json',
    done: function (e, data) {
        if (data.result.state == "SUCCESS") {
            var imagesStr = $("#imageId").val();
            imagesStr = data.result.id;

            $("#imageId").val(imagesStr);

            var imagesUrl = $("#imageUrlIds").html();
            if (isNull(imagesUrl)) {
                imagesUrl += data.result.original;
            } else {
                imagesUrl += " ;" + data.result.original;
            }
            $("#imageUrlIds").html(imagesUrl);
            alert("上传图片成功!");

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
})
