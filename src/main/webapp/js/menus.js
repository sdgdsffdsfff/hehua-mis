$(function () {
    var L1 = $("#L1").html();
    var L2 = $("#L2").html();
    if (L1 != null || L1 != "") {
        $("#myTab li").removeClass("active");
        $("#" + L1).addClass("active");
        $(".tab-pane").removeClass("active");
        $("#_"+L1).addClass("active");
    }

    if (L2 != null || L2 != "") {
        $("#tab-content a").removeClass("btn-primary")
        $("#"+L2).addClass("btn-primary");
    }

});
/*
$("#myTab a").click(function () {
    $("#myTab li").removeClass("active");
    $(this).parent().addClass("class", "active");
    $("#tab-content div").removeClass("active");
    var pane = $(this).attr("href");
    $(pane).addClass("active");
    $(pane + " a").removeClass("btn-primary");
    $(pane + " a:first").addClass("btn-primary");
});
$("#tab-content a").click(function () {
    $("#tab-content div").removeClass("active");
    var paneId = $(this).parent().parent().parent().attr("id");
    $("#" + paneId).addClass("active");
    $("#tab-content a").removeClass("btn-primary")
    $(this).removeClass("btn-block").addClass("btn-primary");
});*/
