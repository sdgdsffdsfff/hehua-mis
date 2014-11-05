<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 14-10-17
  Time: 上午11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div>
    <form role="form" action="/vote/setdeliveried/${freeOrderId}" method="post">
        <div class="form-group">
            <label for="deliveryCompany">物流公司'</label>
            <select name="deliveryComPinyin" class="form-control" id="deliveryCompany">
                <c:forEach items="${deliveryCompanyInfos}" var="com">
                    <option value="${com.deliveryComPinyin}">${com.deliveryCompany}</option>
                </c:forEach>
            </select>
            <input type="hidden" name="deliveryComPinyin">
        </div>

        <div class="form-group">
            <label for="deliveryCompany">物流单号</label>
            <input type="email" class="form-control" id="deliveryNum" placeholder="输入物流单号" name="deliveryNum" required="true">
        </div>
    </form>
</div>

