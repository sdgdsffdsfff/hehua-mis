<%@ page contentType="text/html; charset=utf-8" %>
<%--页面头部基本内容--%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" type="image/x-icon" href="/images/icons/clock_48.png"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <%=request.getParameter("title")%>
    </title>
    <%--公用asserts--%>
    <link href="/css/bootstrap3.css" rel="stylesheet">
    <link href="/css/docs.css" rel="stylesheet">
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/my-mis.css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="/js/bootstrap.js"></script>
    <script type="text/javascript" src="/js/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/Chart.min.js"></script>
    <script type="text/javascript" src="/js/jquery.xcolor.min.js"></script>
</head>
<body>
<jsp:include page="/template/nav.jsp"></jsp:include>

