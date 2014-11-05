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
<script type="text/javascript">
function activeUser(userId) {
	$.post("/gm/active", {"userId": userId}, function(data){
	    $("#alertMessage").html(data);
	});
}

function disactiveUser(userId)  {
	$.post("/gm/unactive", {"userId": userId}, function(data){
	    $("#alertMessage").html(data);
	});
}
</script>
</head>
<body>

<div id="main-content">
	<div class="content-box">
	    <div class="content-box-header">
	        <h3>GM列表</h3>
	        <div style="float: right; margin: 12px 30px 0 0; font-size: 16px;"><a href="/gm/create">添加管理员</a></div>
	    </div>
	    
	    <div class="content-box-content">
	        <table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
				<tr align="center" bgcolor="#FAFAF1" height="22">
				    <th width="6%">ID</th>
				    <th width="6%">账号</th>
				    <th width="6%">昵称</th>
				    <th width="6%">邮箱</th>
				    <th width="6%">角色</th>
				    <th width="6%">创建时间</th>
				    <th width="6%">状态</th>
				    <th width="6%">操作</th>
				</tr>
				<c:forEach var="gm_user" items="${gm_users }">
				<tr align="center" bgcolor="#FAFAF1" height="22">
				    <td width="6%">${gm_user.id }</td>
				    <td width="6%">${gm_user.username }</td>
				    <td width="6%">${gm_user.nickname }</td>
				    <td width="6%">${gm_user.email }</td>
				    <td width="6%">${gm_user.role }</td>
				    <td width="6%">${gm_user.createdTime }</td>
				    <td width="6%">${gm_user.enabled }</td>
				    <td width="6%">
				    <c:if test="${gm_user.id != 1 }">
				    <a onclick="javascript:activeUser(${gm_user.id})" href="#">激活</a>|<a onclick="javascript:disactiveUser(${gm_user.id})" href="#">锁定</a>|<a href="/gm/modify?userid=${gm_user.id }">修改</a>
				    </c:if>
				    </td>
				</tr>
				</c:forEach>
			</table>
	    </div>
	</div>
</div>
</body>
</html>