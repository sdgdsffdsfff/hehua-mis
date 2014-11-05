<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/string-1.1" prefix="str" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>潘多拉</title>
<link rel="stylesheet" href="/css/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="/css/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="/css/invalid.css" type="text/css" media="screen" />
<script type="text/javascript" src="/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/js/facebox.js"></script>
<script type="text/javascript" src="/js/jquery.wysiwyg.js"></script>
</head>
<body>
    
<div id="main-content">
    <div class="content-box">
        <div class="content-box-header">
            <h3>欢迎页</h3>
        </div>
        
        <div class="content-box-content">
		<form action="/gm/create" method="post">
			<div class="form-item clearfix">
			    <label for="username">账号：</label>
			    <input type="text" name="username" id="username">
		    </div>
		    <div class="form-item clearfix">
			    <label for="">昵称：</label>
			    <input type="text" name="nickname" id="nickname">
		    </div>
		    <div class="form-item clearfix">
			    <label for="">邮箱：</label>
			    <input type="text" name="email" id="email">
		    </div>
		    <div class="form-item clearfix">
			    <label for="">密码</label>
			    <input type="text" name="password" id="password">
		    </div>
		    <div class="form-item clearfix">
			    <label for="">角色</label>
			    <select name="role">
			        <option value="ROLE_ADMIN">管理员</option>
			        <option value="ROLE_OPERATOR">运营</option>
			        <option value="ROLE_FAQ">客服</option>
			    </select>
		    </div>
		    <div class="form-item clearfix">
			    <label for="">状态：</label>
			    <select name="enabled">
			        <option value="false">锁定</option>
			        <option value="true">激活</option>
			    </select>
		    </div>
		    <div class="submit clearfix">
		    	<input type="submit" value="新增"/>
		    </div>
		</form>
        </div>
    </div>
</div>
    </body>
</html>
