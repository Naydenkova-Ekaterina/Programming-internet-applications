<%--
  Created by IntelliJ IDEA.
  User: ekaterina
  Date: 04.10.17
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        <%@include file='style.css' %>
    </style>
    <jsp:useBean id="resultBean" class="lab2.ResultBean" scope="session" ></jsp:useBean>
</head>
<body>


<table class="answer">
    <tr><th>X</th><th>Y</th><th>R</th><th>AreaReach</th></tr>
    <c:forEach var="item" items="${resultBean.result}">
        <tr>
            <td><c:out value="${item.x}"/></td>
            <td><c:out value="${item.y}"/></td>
            <td><c:out value="${item.r}"/></td>
            <td><c:out value="${item.areaReach}"/></td>
        </tr>
    </c:forEach>
</table>

<script src="validation.js"></script>
</body>
</html>
