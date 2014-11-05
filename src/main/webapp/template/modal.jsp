<%--
  ~ Copyright (c) 2014.
  ~ Author WangJun
  ~ Email  wangjuntytl@163.com
  --%>

<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 14-8-30
  Time: 下午2:05
  To change this template use File | Settings | File Templates.
--%>
<%--页面弹框模板
使用方法：1.先使用jsp：include指令引用该页面，
        2.使用$("#toggleModal").bootstrap_modal_extension_myModal(title, body);触发弹框title:弹框标题，如果不需要标题填null，body：主要内容显示
          若body含有form，会自动识别该表单并显示确认按钮提交数据*/
--%>
<%@ page contentType="text/html; charset=utf-8" %>
<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body" id="myModalBody">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="modalClose">关闭</button>
                <a type="button" class="btn btn-primary" id="modalSubmit" href="javascript:void(0)">确认</a>
            </div>
        </div>
    </div>
</div>