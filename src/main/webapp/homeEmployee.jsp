<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Telco Website</title>
    <link href="/css/style.css" data-th-href="@{/css/style.css}" rel="stylesheet">
</head>
<body>
<div style="text-align: center">
    <h1>YOU ARE AN EMPLOYEE</h1>
    <b>${user.username} (${user.email})</b>
    <br><br>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
</body>
</html>