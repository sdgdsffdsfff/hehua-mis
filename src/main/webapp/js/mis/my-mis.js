$(document).ready(function () {
    $('.selectpicker').change(function () {
        var cateId = $('.selectpicker').val();
        $.ajax({
            type: "POST",
            url:"/goods/category",
            data:{"cateId":cateId},// 要提交的表单
            dataType:"json",
            success: function(data) {
                if (data.code == 0) {
                    var goods_norms_detail = $("#goods_norms_detail").html("");
                    var detail = data.data.skuArray;
                    if(detail.length == 0 ){
                        var barcode = "<input name='barcode' placeholder='请输入商品二维码'>"
                        goods_norms_detail.append(barcode);
                        return;
                    }
                    goods_norms_detail.append("<br>");

                    for(var i = 0 ;i < detail.length; i++){
                        var newLine = $("<p></p>").attr("id","goods_norms_type_key_"+detail[i].pid).html("<a data-placeholder='必须填写' data-placement='right' data-pk='1' data-type='text'  href='#' class='editable editable-click' id='pedit_"+detail[i].pid+"'>"
                         + detail[i].pName + "</a>");
                        goods_norms_detail.append(newLine);
                        var pvs = detail[i].pvs;
                        var newLine_ = $("<p></p>").attr("id","goods_norms_type_value_"+detail[i].pid);
                        for(var n = 0; n<pvs.length;n++){
                            newLine_.append("<input type='checkbox' value='"+pvs[n].pvid+"'>").append("<a data-placeholder='必须填写' data-placement='right' data-pk='1' data-type='text'  href='#' class='editable editable-click' id='pvedit_"+pvs[n].pvid+"'>"+pvs[n].pvname+"</a>")
                            goods_norms_detail.append(newLine_);
                        }
                    }
                    //组合商品规格时渲染表格入口
                    $("p[id^='goods_norms_type'] input").on("click",function () {
                        renderTable();
                    });

                    $("a[id^='pedit_']").each(function(){
                        $(this).editable({
                            success: function(response, newValue) {
                                $(this).html(newValue);
                                renderTable();
                            }
                        });
                    });

                    $("a[id^='pvedit_']").each(function(){
                        $(this).editable({
                            success: function(response, newValue) {
                                $(this).html(newValue);
                                renderTable();
                            }
                        });
                    });

                } else {
                    alert(data.message);
                }
            }
        })
    })

    $.fn.editable.defaults.mode = 'inline';

    $("#submmitId").click(function() {
        $("#tipIds").html("");
        var descStr = UE.getEditor('post').getContent();
            $("#itemDescId").val(descStr);
            var data = $("#formItemId").serialize();
            $.ajax({
                type: "POST",
                url: "/item/save",
                data: data,// 要提交的表单
                dataType: "json",
                success: function(data) {
                    if (data.code == 0) {
                        alert("保存成功！");
                        document.location.href = "/goods";

                    } else {
                        var str = '<div class="alert alert-danger" id="alertId" role="alert">'
                        str += data.message + "</div>";
                        $("#tipIds").html(str);
                    }
                }
            })
    })

    $('#uploadFileId').fileupload({
        autoUpload: true,
        url: "/upload",
        dataType: 'json',
        done: function (e, data) {
                if (data.result.state == "SUCCESS") {
                    var imagesStr = $("#imageId").val();
                    imagesStr += data.result.id + "@";

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
    });
})