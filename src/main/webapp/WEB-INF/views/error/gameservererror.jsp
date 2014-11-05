<%@ page language="java" import="org.apache.log4j.Logger" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%  
    Exception exception = (Exception)request.getAttribute("exception");  
    final Logger logger = Logger.getRootLogger();  
    logger.error(exception.getMessage(),exception);  
%> 
<html>
<head>
<title>连接游戏服务器失败</title>
</head>
<body>
<div style="margin: 30px 30px 0 0;">
	<font color="#FF0000">连接游戏服务器失败，请稍后再试。</font>
	<input type="button" onclick='javascript:history.go(-1)' value='返回'/>
</div>
</body>
</html>