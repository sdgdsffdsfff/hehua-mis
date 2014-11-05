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
            <h3>仓库列表</h3>
        </div>


        <div class="content-box-content">
            <table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
                <tr align="center" bgcolor="#FAFAF1" height="22">
                    <th width="16%">ID</th>
                    <th width="26%">仓库名称</th>
                    <th width="36%">创建时间</th>
                    <th width="36%">操作</th>
                </tr>
                <c:forEach var="warehouse" items="${warehouseList}">
                    <tr align="center" bgcolor="#FAFAF1" height="22">
                        <td width="16%">${warehouse.id }</td>
                        <td width="26%">${warehouse.name }</td>
                        <td width="36%">${warehouse.cts }</td>
                        <th width="36%"><a class="btn-link" href="/ware/modify">修改</a> &nbsp &nbsp / &nbsp &nbsp
                        <a class="btn-link" href="/ware/delete">删除</a></th>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>