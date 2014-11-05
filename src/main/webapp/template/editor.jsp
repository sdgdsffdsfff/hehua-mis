<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://mis.hehuababy.com/mis/assert" prefix="assert" %>
<assert:file name="/js/ueditor/ueditor.config.js"></assert:file>
<assert:file name="/js/ueditor/ueditor.all.min.js"></assert:file>
<assert:file name="/js/ueditor/lang/zh-cn/zh-cn.js"></assert:file>
<div id="post"></div>
<script type="text/javascript">
    UE.getEditor('post',{
        initialFrameHeight:320
    });
</script>