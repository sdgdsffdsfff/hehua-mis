$('#uploadFileId').fileupload({
    autoUpload: true,
    url: "/version/upload",
    dataType: 'json',
    done: function (e, data) {
        if (data.result.stat == "SUCCESS") {
            $("#downLoadUrlId").val(data.result.url);
            $("#imageUrlIds").html(data.result.fileName);
            alert("上传文件成功!");

        } else {
            alert(data.msg);
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

function updateVersion(phoneVersionId, forceUpdateStatus) {
    $.ajax({
        type: "POST",
        url: "/users/updateVersion",
        data: "id=" + phoneVersionId + "&isForceUpdate=" + forceUpdateStatus,
        dataType: "json",
        success: function (data) {
            if (data.code == 1) {
                alert(data.result);
                document.location.href = "/users/version/list";
            } else {
                alert(data.result);
            }
        }
    })
}
