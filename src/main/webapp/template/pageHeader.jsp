<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://mis.hehuababy.com/mis/assert" prefix="assert" %>
<%--
  ~ Copyright (c) 2014.
  ~ Author WangJun
  ~ Email  wangjuntytl@163.com
  --%>
<%--页面头部基本内容--%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" type="image/x-icon" href="/images/icons/clock_48.png"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="WangJun">
    <title>
        <%=request.getParameter("title")%>
    </title>
    <%--公用asserts--%>
    <assert:file name="/css/bootstrap3.css"></assert:file>
    <assert:file name="/css/docs.css"></assert:file>
    <assert:file name="/css/common.css"></assert:file>
    <assert:file name="/css/my-mis.css"></assert:file>
    <assert:file name="/js/jquery-1.9.1.js"></assert:file>
    <assert:file name="/js/bootstrap.js"></assert:file>
    <assert:file name="/js/jquery.validate.js"></assert:file>
</head>
<body>
<jsp:include page="/template/nav.jsp"></jsp:include>

