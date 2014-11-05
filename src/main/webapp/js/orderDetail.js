$(".audit").click(function () {
    var url = $(this).attr("url");
    var current = $(this);
    $.getJSON(url, function (data) {
        if (data.code == 1) {
            current.parent().html(data.result);
        } else {
            alert(data.result);
        }
    });
});

