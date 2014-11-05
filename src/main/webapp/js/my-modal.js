/*
 * Copyright (c) 2014.
 * Author WangJun
 * Email  wangjuntytl@163.com
 */


/**
 *
 * @param title 弹框的标题
 * @param body 主要内容显示
 * @param nextUrl 如果body中含有form，会自动识别该表单并可以通过确认按钮异步提交数据，
 *                 响应数据格式：{'code':0,'result':'成功'},如果code=1,以nextUrl刷新当前页面
 */
$.fn.myModal = function (title, url, nextUrl) {
    $(this).on("click", function () {
        $.get(url, function (data) {
            toggleModal(title, data, nextUrl);
        });
    });
}

var bootstrap_modal_extension_myModal = {};
//初始化modal:是否显示title标题，如果表单对象显示确定按钮用来提交表单
bootstrap_modal_extension_myModal.init = function (title, body, nextUrl) {
    if ($("#myModal") == null) {
        alert("未引入myModal模板：modal.jsp");
        return;
    }
    $("#myModal").modal('show');
    $("#myModalLabel").html(title);
    $("#myModalBody").html(body)
    if (title == null)
        $("#myModalLabel").parent().css("display", "none");
    if ($("#myModalBody form") == null || $("#myModalBody form").length == 0) {
        $("#modalSubmit").css("display", "none");
        $("#modalClose").unbind('click').bind("click", function () {
            if (nextUrl != null)
                location.replace(nextUrl);
        });
    } else
        $("#modalSubmit").css("display", "inline-block");
}

//提交表单成功后的回调函数
bootstrap_modal_extension_myModal.callback = function (response, nextUrl) {
    $("#myModal").modal('hide');
    $("#myModalBody").html("");
    $("#modalSubmit").css("display", "inline-block");
    try {
        if (response.code == 1) {
            alert(response.result);
            if (nextUrl != null)
                location.replace(nextUrl);
        } else if (response != undefined && response != null) {
            alert(response.result);
        }
    } catch (e) {
        alert("页面发生错误：\n" + data);
    }
}

//处理modal内表单，防止由于双击按钮导致的重复提交
bootstrap_modal_extension_myModal.handle = function (nextUrl) {
    var TimeFn = null;
    $("#modalSubmit").unbind('click').bind("click", function () {
        clearTimeout(TimeFn);
        TimeFn = setTimeout(function () {
            if ($("#myModalBody form") != null) {
                if ($("#myModalBody form").validForm() == true) {
                    $("#modalSubmit").css("display", "none");
                    var url = $("#myModalBody form").attr("action");
                    var submit_data = $("#myModalBody form").serialize();
                    var method = $("#myModalBody form").attr("method");
                    if (method == null)
                        method = "get";
                    if (method == "get") {
                        $.getJSON(url, submit_data, function (data) {
                            bootstrap_modal_extension_myModal.callback(data, nextUrl);
                        });
                    } else {
                        $.post(url, submit_data, function (data) {
                            bootstrap_modal_extension_myModal.callback(data, nextUrl);
                        });
                    }
                }
            }
        }, 300);
    });
}

function toggleModal(title, body, nextUrl) {
    bootstrap_modal_extension_myModal.init(title, body, nextUrl);
    if ($("#myModalBody form") != null) {
        bootstrap_modal_extension_myModal.handle(nextUrl);
    }
}