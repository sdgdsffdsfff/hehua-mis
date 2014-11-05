/*
 * Copyright (c) 2014.
 * Author WangJun
 * Email  wangjuntytl@163.com
 */

$.fn.editInnerHTML = function () {
    $(this).on("click", function () {
        editTd($(this));
    });
}

function editTd(arg) {
    var value = $(arg).html();
    var width = $(arg).width();
    var height = $(arg).height();
    var current = $(arg);
    current.html("").append("<input type='text' onkeydown='submitEnter(this)' onblur='inputBlur(this)' value='" + value + "' style='border-left:0px;border-top:0px;border-right:0px;border-bottom:0px;margin:-1px;'>");
    $(arg).css("width", width).css("height", height).unbind("click");
    current.find("input").css("width", width).css("height", height).focusEnd();
}


function submitEnter(arg) {
    var event = arguments.callee.caller.arguments[0] || window.event;//消除浏览器差异
    if (event.keyCode == 13) {
        var parent = $(arg).parent();
        var value = $(arg).val();
        parent.html(value)
        parent.bind("click");
        parent.click(function () {
            editTd(this)
        });
    }
}


function inputBlur(arg) {
    var parent = $(arg).parent();
    var value = $(arg).val();
    parent.html(value)
    parent.bind("click");
    parent.click(function () {
        editTd(this)
    });
}

$.fn.setCursorPosition = function (position) {
    if (this.lengh == 0) return this;
    return $(this).setSelection(position, position);
}

$.fn.setSelection = function (selectionStart, selectionEnd) {
    if (this.lengh == 0) return this;
    input = this[0];

    if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    } else if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }

    return this;
}

$.fn.focusEnd = function () {
    this.setCursorPosition(this.val().length);
}