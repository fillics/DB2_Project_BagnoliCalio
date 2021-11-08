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
    <h1>LOGIN</h1>
    <form action="login" method="post">
        <%--@declare id="password"--%><%--@declare id="email"--%>
        <%--@declare id="username"--%>
        <label for="username">Username:</label>
        <input name="username" size="30" />

        <br><br>

        <label for="password">Password:</label>
        <input type="password" name="password" size="30" />
        <br><br>
        <label for="employee">I am an employee</label>
        <input type="checkbox" id="employee" name="employee" value="employee">

        <br>${messageLogin}
        <br><br>
        <button type="submit">LOGIN</button>
    </form>

    <h1>REGISTRATION</h1>
    <form action="signup" method="post">
        <%--@declare id="password"--%><%--@declare id="email"--%>
        <label for="username">Username:</label>
        <input name="username" size="30" />
        <br><br>
        <label for="email">Email:</label>
        <input name="email" size="30" />
        <br><br>
        <label for="password">Password:</label>
        <input type="password" name="password" size="30" />
        <br><br>
        <label for="employee">I am an employee</label>
        <input type="checkbox" id="employee" name="employee" value="employee">
        <br>${messageSignUp}
        <br><br>
        <button type="submit">SIGN UP</button>
    </form>

    <br><br>
    <h2>Skip the Login</h2>
    <a href="homeCustomer.jsp">Click here</a>
</div>
</body>
</html>