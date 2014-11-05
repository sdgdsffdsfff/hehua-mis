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
		<form action="/gm/modify" method="post">
		    <input type="hidden" name="userid" value="${gm_user.id }"/>
		    <table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
                <tr align="center" bgcolor="#FAFAF1" height="22">
                    <td width="15%">账号：</td>
                    <td width="60%">${gm_user.username }</td>
                </tr>
                
                <tr align="center" bgcolor="#FAFAF1" height="22">
                    <td width="15%">密码：</td>
                    <td width="60%"><input type="password" name="password"/></td>
                </tr>
                
                <tr align="center" bgcolor="#FAFAF1" height="22">
                    <td width="15%">角色：</td>
                    <td width="60%">
                    <select name="role">
                        <option value="ROLE_ADMIN" <c:if test="${gm_user.role == 'ROLE_ADMIN' }">selected</c:if>>管理员</option>
                        <option value="ROLE_OPERATOR" <c:if test="${gm_user.role == 'ROLE_OPERATOR' }">selected</c:if>>运营</option>
                        <option value="ROLE_FAQ" <c:if test="${gm_user.role == 'ROLE_FAQ' }">selected</c:if>>客服</option>
                    </select>
                    </td>
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
