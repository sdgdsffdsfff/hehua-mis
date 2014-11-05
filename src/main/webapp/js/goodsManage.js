/*
 * Copyright (c) 2014.
 * Author WangJun
 * Email  wangjuntytl@163.com
 */
var goods_norms_data_controller =
{
    'version': 2,
    'name': 'goods'
};
//渲染表格
function renderTable() {
    var goods_norms_types = $("p[id^='goods_norms_type_key']");//当前商品规格组合
    checkVersion(goods_norms_types, 2);
    var goods_norms_group = getGoodsNormsGroupsArray(goods_norms_types);
    if (goods_norms_group.length > 0) {
        initTable();
        startRenderTable(goods_norms_group);
        count();
        $('#goods_norms_table').editTable("goods_norms_table");
    }
}
function startRenderTable(goods_norms_group) {
    version2(goods_norms_group)
}

//检测规格组合的选择的规格是否为空
function GoodsNormsPageIsNotEmpty(goods_norms_page) {
    if (goods_norms_page.chooseMap.arr.length == 0)
        return false;
    else
        return true;
}

function version2(goods_norms_group) {
    var norms_1 = goods_norms_group[0];
    var norms_2 = goods_norms_group[1];
    var norms_1_choose_map = norms_1.chooseMap;
    if (norms_2 != null || norms_2 != undefined) {
        var norms_2_choose_map = norms_2.chooseMap;
    }
    if ($("#thead_1") != null)
        $("#thead_1").remove();
    if ($("#thead_2") != null)
        $("#thead_2").remove();
    var thead = $("#table_thead_body");
    var thead_1 = $("<td id='thead_1'></td>");
    var thead_2 = $("<td id='thead_2'></td>");
    var originPrice = $("input[name='originPrice']").val();
    var realPrice = $("input[name='realPrice']").val();
    if (originPrice == null || originPrice == "")
        originPrice = 0;
    if (realPrice == null || realPrice == "")
        realPrice = 0;
    if (norms_2 != undefined) {
        if (GoodsNormsPageIsNotEmpty(norms_1) && GoodsNormsPageIsNotEmpty(norms_2)) {
            thead_1.html(norms_1.typeName);
            thead_2.html(norms_2.typeName);
            thead.prepend(thead_2).prepend(thead_1);
            for (var i = 0; i < norms_1_choose_map.size(); i++) {
                for (var n = 0; n < norms_2_choose_map.size(); n++) {
                    var newTr = $("<tr id='json_i_" + i + "_n_" + n + "'></tr>")
                        .append("<td>" + norms_1_choose_map.arr[i].value + "</td>").
                        append("<td>" + norms_2_choose_map.arr[n].value + "</td>").
                        append("<td class='editTb origin_price'>"+originPrice+"</td><td class='editTb real_price'>"+realPrice+"</td><td class='editTb goods_count'>0</td><td class='editTb barcode'></td>")
                    $("#goods_norms").append(newTr);
                    //生成json表单数据
                    var json = new GoodsNorms(norms_1.typeId + ":" + norms_1_choose_map.arr[i].key + "," + norms_2.typeId + ":" + norms_2_choose_map.arr[n].key,
                        [new SkuProperty(norms_1.typeId, norms_1.typeName,norms_1_choose_map.arr[i].key, norms_1_choose_map.arr[i].value),
                        new SkuProperty(norms_2.typeId, norms_2.typeName, norms_2_choose_map.arr[n].key, norms_2_choose_map.arr[n].value)],
                        originPrice, realPrice, "0", 0);
                    $("#json_result").append("<input value='" + json.toString() + "' type='hidden' id='input_json_i_" + i + "_n_" + n + "' name='goodsNorms'>");
                }

            }
        }
    } else if (GoodsNormsPageIsNotEmpty(norms_1)) {
        var real_goods_norm_page;
        var real_header;
        if (GoodsNormsPageIsNotEmpty(norms_1)) {
            thead_1.html(norms_1.typeName);
            real_goods_norm_page = norms_1;
            real_header = thead_1;
        } else {
            thead_2.html(norms_2.typeName);
            real_goods_norm_page = norms_2;
            real_header = thead_2;
        }
//        var flag = real_goods_norm_page.typeName.indexOf("颜色") == -1;
//        if (flag) {
        thead.prepend(real_header);
        for (var i = 0; i < real_goods_norm_page.chooseMap.size(); i++) {
            var newLine = $("<tr id='json_i_" + i + "'></tr>").
                append("<td>" + real_goods_norm_page.chooseMap.arr[i].value + "</td>").
                append("<td class='editTb origin_price'>"+originPrice+"</td><td class='editTb real_price'>"+realPrice+"</td><td class='editTb goods_count'>0</td><td class='editTb barcode'></td>")
            $("#goods_norms").append(newLine);
            var json = new GoodsNorms(real_goods_norm_page.typeId + ":" + real_goods_norm_page.chooseMap.arr[i].key,
                [new SkuProperty(real_goods_norm_page.typeId, real_goods_norm_page.typeName,real_goods_norm_page.chooseMap.arr[i].key, real_goods_norm_page.chooseMap.arr[i].value)],
                originPrice, realPrice, "0", 0);
            $("#json_result").append("<input value='" + json.toString() + "' type='hidden' id='input_json_i_" + i + "' name='goodsNorms'>");
        }
//        }
    }
}

//得到商品规格组合的GoodsNormsPage集合结果
function getGoodsNormsGroupsArray(goods_norms_types) {
    var goods_norms_group = new Array();//商品规格组合后的结果
    for (var i = 0; i < goods_norms_types.length; i++) {
        var id = $(goods_norms_types[i]).attr("id");
        var typeId = id.split("_")[4]; //  规格id
        var prefix = "goods_norms_type_value" + "_" + typeId + " input:checked";
        var chooseList = $("#" + prefix);
        var typeName = $(goods_norms_types[i]).find("a").html(); //规格名字
        var chooseValues = new Map(); //当前规格可选的值
        for (var n = 0; n < chooseList.length; n++) {
            var key = $(chooseList[n]).val();
            var value = $(chooseList[n]).next().html();
            chooseValues.put(key, value);
        }
        var goodsNormsPage = new GoodsNormsPage(typeId, typeName, chooseValues);
        goods_norms_group.push(goodsNormsPage);
    }
    return goods_norms_group;
}

//检测系统当前可以支持最多由几个规格去进行组合
function checkVersion(goods_norms_groups, version) {
    if (goods_norms_groups.length > goods_norms_data_controller.version) {
        alert("请注意当前系统只支持在两种商品规格的组合，如果你看到有三种规格可选，你只可以选前两种！！！");
    }
}

//渲染表格前必须先初始化表格
function initTable() {
    $("#table_thead").css("display", "table-header-group");//表格头部
    $("#goods_norms").html("");//表格body
    $("#json_result").html("");//json表单
    $("#goods_num").val(0);
}

function toMap(list, prefix) {
    var map = new Map();
    $.each(list, function () {
        var temp = $(this).val();
        var real_value = $("#" + prefix + "_" + temp).html();
        map.put($(this).val(), real_value);
    });
    return map;
}


function editJson(arg) {
    var id = $(arg).attr("id");
    var input = $("#input_" + id);
    alert(input.val());
}

//页面端描述商品规格对象格式
function GoodsNormsPage(typeId, TypeName, chooseMap) {
    this.typeId = typeId; //规格id
    this.typeName = TypeName;//规格名字
    this.chooseMap = chooseMap;//选择的规格值
    this.toString = function () {
        JSON.stringify(this);
    }
}

function SkuProperty(pid, pname, pvid, pvname) {
    this.pid = pid;
    this.pname = pname;
    this.pvid = pvid;
    this.pvname = pvname;
    this.toString = function () {
        JSON.stringify(this);
    }
}

//后端描述商品规格组合后的对象格式
function GoodsNorms(pids, skus, originprice, realprice, barcode, quantity) {
    this.skus = skus;
    this.pids = pids;  //规格id组合
    this.originprice = originprice;//原价
    this.realprice = realprice;//标价
    this.barcode = barcode;//商品二维码
    this.quantity = quantity;//数量
    this.toString = function () {
        return JSON.stringify(this);
    }
}

//计算商品数量
function count() {
    var total = 0;
    var countTds = $(".goods_count");
    for (var i = 0; i < countTds.length; i++) {
        var value = $(countTds[i]).html();
        if (isNumber(value)) {
            total = total + toNum(value);
        } else if (!isNumber(value) && !isNull(value)) {
            $(countTds[i]).html("0");
            alert("商品数量必须为数字,数量自动置为0");
        }
    }
    $("#goods_num").val(total);
}

function changeJson(arg, id, which) {
    var value = $(arg).val();
    var json = $("#input_" + id).val();
    var data = JSON.parse(json);
    if (which.indexOf("origin_price") != -1) {
        data.originprice = value;
    }
    if (which.indexOf("real_price") != -1) {
        data.realprice = value;
    }
    if (which.indexOf("goods_count") != -1) {
        data.quantity = value;
    }
    if (which.indexOf("barcode") != -1) {
        data.barcode = value;
    }
    $("#input_" + id).attr("value", JSON.stringify(data));

}
