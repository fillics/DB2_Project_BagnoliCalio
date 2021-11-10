<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>

<jsp:forward page="/employeePage"></jsp:forward>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Telco Website</title>
    <link href="/css/style.css" data-th-href="@{/css/style.css}" rel="stylesheet">
</head>
<body>

<ul>
    <li><a href="#home">Home</a></li>
    <li><a href="#news">News</a></li>
    <li><a href="#contact">Contact</a></li>
    <li style="float:right"><a class="active" href="#about">About</a></li>
</ul>

<div style="text-align: center">
    <h1>YOU ARE AN EMPLOYEE</h1>
    <b>${user.username} (${user.email})</b>
    <br><br>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
</body>
</html>