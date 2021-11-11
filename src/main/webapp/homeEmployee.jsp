<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Telco Website</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>

<p align=right>${employee.username}</p>

<p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>


<div style="text-align: center">
    <h1>EMPLOYEE PAGE</h1>
    <br><br>
    <h3>Creating Service Package</h3>
    <form action="login" method="post">

        <br><br>

        <br>${messageLogin}
        <br><br>
        <button type="submit">LOGIN</button>
    </form>
    <br><br>



    <h3>Creating Optional Product</h3>
    <form action="optionalProduct" method="post">
        <%--@declare id="name"--%><%--@declare id="monthlyfee"--%>
            <label for="name">Name:</label>
        <input name="name" size="30" />

        <br><br>

        <label for="monthlyFee">Monthly Fee:</label>
            <input name="monthlyFee" size="15"/>

        <br><br>

        <br>${message}
        <br><br>
        <button type="submit">CREATE</button>
    </form>
</div>


</body>
</html>