<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<script type="text/javascript" src="/js/jquery.js"></script>
<link rel="stylesheet" href="/css/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="/css/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="/css/invalid.css" type="text/css" media="screen" />
<script type="text/javascript" src="/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/js/facebox.js"></script>
<script type="text/javascript" src="/js/jquery.wysiwyg.js"></script>
<script type="text/javascript" src="/js/jquery.datePicker.js"></script>
<script type="text/javascript" src="/js/jquery.date.js"></script>
</head>
<body>

<div id="main-content">
	<div class="content-box">
	    <div class="content-box-header">
	        <h3>修改密码</h3>
	    </div>
	    
	    <div class="content-box-content">
	       <form action="/setting/password" method="post">
	        <table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
				<tr align="center" bgcolor="#FAFAF1" height="22">
				    <td width="15%">账号：</td>
				    <td width="60%">${gm_user.username }</td>
				</tr>
				
				<tr align="center" bgcolor="#FAFAF1" height="22">
                    <td width="15%">当前密码：</td>
                    <td width="60%"><input type="password" name="password"/></td>
                </tr>
                
                <tr align="center" bgcolor="#FAFAF1" height="22">
                    <td width="15%">新密码：</td>
                    <td width="60%"><input type="password" name="new_password"/></td>
                </tr>
				
				<tr align="center" bgcolor="#FAFAF1" height="22">
                    <td width="15%">再次输入新密码：</td>
                    <td width="60%"><input type="password" name="new_password_confirm"/></td>
                </tr>
                
                <tr align="center" bgcolor="#FAFAF1" height="22">
                    <td colspan="2" align="center"><input type="submit" value="修改" /></td>
                </tr>
			</table>
			</form>
	    </div>
	</div>
</div>
</body>
</html>