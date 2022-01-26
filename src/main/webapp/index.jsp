<%@ page import="it.polimi.db2project.entities.ServicePackageEntity" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Telco Website</title>
</head>

<style><%@include file="css/index.css"%></style>

<%
    ServicePackageEntity servicePackage = (ServicePackageEntity) request.getSession().getAttribute("servicePackage");
%>

<body>


<div class='parent'>
<h1>Telco Company</h1>
    <div class="login-page">
        <div class="form">
            <h2>LOGIN</h2>
            <form action="login" method="post">
                <input type="text" name="username" placeholder="username" required/>
                <input type="password" name="password" placeholder="password" required/>
                <br>${messageLogin}
                <button type="submit">login</button>
            </form>
        </div>
    </div>

    <div class="login-page">
        <div class="form">
            <h2>REGISTRATION</h2>
            <form action="signup" method="post">
                <input type="text" name="username" placeholder="username" required/>
                <input type="text" name="email" placeholder="email" required/>
                <input type="password" name="password" placeholder="password" required/>
                <label for="employee">I am an employee:</label>
                <input type="checkbox" class="check" id="employee" name="employee" value="employee">
                <br>${messageSignUp}
                <button type="submit">sign up</button>
            </form>
        </div>
    </div>

</div>


<%
    if(servicePackage==null){
%>

<div class="parent">
    <div class="login-page">
        <div class="form">
            <form action="homePageCustomer">
                <button type="submit">
                    skip login
                </button>
            </form>
        </div>
    </div>
</div>


<%
    }
%>

</body>
</html>