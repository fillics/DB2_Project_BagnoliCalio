<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Telco Website</title>
</head>
<body>
<div style="text-align: center">
    <h1>YOU ARE A CLIENT</h1>
    <b>${user.username} (${user.email})</b>
    <br><br>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
</body>
</html>