function dispAppraise(itemId) {
    $('#itemIdListHidden').val(itemId);
    $.ajax({
        type: "POST",
        url: "/item/appraise/list",
        data: "itemId=" + itemId,
        dataType: "json",
        success: function (data) {
            if (data.code == 0) {
                if (data.data.data != "") {
                    var resultHtml = '<div class="row-fluid" style="text-align: center">' +
                        '<table class="table table-bordered table-striped table-hover">' +
                        '<thead>' +
                        '<tr>' +
                        '<th>达人名称</th><th>评测信息</th></tr></thead>' +
                        '<tbody>';
                    var retArray = JSON.parse(data.data.data);
                    for (var i = 0; i < retArray.length; i++) {
                        resultHtml += "<tr><td>" + $(retArray[i]).attr("darenName") + "</td>"
                            + "<td>" + $(retArray[i]).attr("appraiseStr") + "</td>";
                    }
                    resultHtml += "</tbody></table></div>";
                } else {
                    var resultHtml = "没有评测相关数据";
                }


                $("#contentId").html(resultHtml);
                $('#appraiseList').modal('toggle');
            } else {
                alert("获取数据失败");
            }
        }
    })
}


function saveMeta() {
    var itemId = $('#itemMetaId').val();
    var metaStr = UE.getEditor('post').getContent();
    if (isNull(metaStr)) {
        alert("请填写商品元数据");
    }

    $.ajax({
        type: "POST",
        url: "/item/meta/add",
        data: {"itemId": itemId, "metaStr": metaStr},
        dataType: "json",
        success: function (data) {
            if (data.code == 0) {
                alert("添加商品数据字典成功");
                $("#metaModal").modal("hide");
            } else {
                alert("添加商品数据字典失败");
            }
        }
    })
}

function addSession(itemId) {
    $('#dateTimeModal').modal('toggle');
    $('#itemIdHidden').val(itemId);
}

function addMetaData(itemId) {
    $('#metaModal').modal('toggle');
    $('#itemMetaId').val(itemId);
}

function addVote(itemId) {
    $('#voteModal').modal('toggle');
    $('#itemVoteId').val(itemId);
}

function setVoteItem() {
    var itemId = $('#itemVoteId').val();
    var startTime = $("#voteStarttimeId").val();
    var endTime = $("#voteEndtimeId").val();
    var freeQuantity = $("#freeQuantityId").val();
    var data = {"startTime": startTime, "endTime": endTime, "freeQuantity": freeQuantity};
    $.ajax({
        type: "POST",
        url: "/vote/setting/" + itemId,
        data: data,
        dataType: "json",
        success: function (data) {
            if (data.code == 0) {
                alert("该商品添加众测成功");
                $("#voteModal").modal("hide");
            } else {
                alert(data.message);
            }
        }
    })
}

function addAppraise(itemId) {
    $('#appraiseModal').modal('toggle');
    $('#itemIdAppraiseHidden').val(itemId);
}

function saveAppraise() {
    var darenId = $("#daren_id").val();
    var itemId = $('#itemIdAppraiseHidden').val();
    var descStr = UE.getEditor('appraiseId').getContent();
    if (isNull(descStr)) {
        alert("请填写评测信息");
    }
    var data = {"darenId": darenId, "itemId": itemId, "descStr": descStr};

    $.ajax({
        type: "POST",
        url: "/item/appraise/add",
        data: data,
        dataType: "json",
        success: function (data) {
            if (data.code == 0) {
                alert("添加评测信息成功");
                $("#appraiseModal").modal("hide");
            } else {
                alert("添加评测信息失败");
            }
        }
    })

}

function saveSession() {
    var onlineStr = $("#dtp_input1").val();
    var itemId = $('#itemIdHidden').val();
    if (isNull(onlineStr)) {
        alert("请输入闪购时间");
    }

    $.ajax({
        type: "POST",
        url: "/flash/add",
        data: {"itemId": itemId, "onlineStr": onlineStr},
        dataType: "json",
        success: function (data) {
            if (data.code == 0) {
                alert("添加闪购成功");
                $("#dateTimeModal").modal("hide");
            } else {
                alert("添加闪购失败");
            }
        }
    })

}



$(function () {
    //var data = new Date();
    //$(".datetimepicker").val(data.format('yyyy-MM-dd'));
    $(".datetimepicker").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        weekStart: 1,
        todayBtn: 1,
        autoclose: true,
        todayHighlight: 1,
        forceParse: 0,
        minView: 3
    });
});


$(function () {
    $(".deleteItem").click(function () {
        if (confirm("确定要删除吗？？？")) {
            var url = "/item/delete/" + $(this).attr("id").split("_")[1];
            var current = $(this);
            $.get(url, function (data) {
                if (data.code == 1) {
                    current.html("已删除").removeClass("deleteItem").css("color", "grey");
                    alert(data.result);
                } else
                    alert(data.result);
            })
        }
    })

});
