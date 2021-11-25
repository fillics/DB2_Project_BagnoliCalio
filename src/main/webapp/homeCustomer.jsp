<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.polimi.db2project.entities.*" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Telco Website</title>
</head>

<style><%@include file="css/style.css"%></style>

<body>

<%
    List<ServicePackageToSelectEntity> servicePackagesToSelect = (List<ServicePackageToSelectEntity>)
    request.getAttribute("servicePackagesToSelect");

    UserEntity user = (UserEntity) request.getSession().getAttribute("user");
    String userUsername = null;
    try {
        userUsername = user.getUsername();
    }catch (Exception e){
        e.printStackTrace();
    }


%>

<%
    if(userUsername!=null){

%>
<p align=right>Username of the user: ${user.username}</p>
<p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>
<%
    }
    else{

%>
<p align=right><a href="${pageContext.request.contextPath}/login">Login</a></p>
<%
    }
%>

<form action="buyPage">

<div style="text-align: center">
    <h1>HOME PAGE</h1>
    <br>
    <h2>List of Service Package available</h2>

    <br>

    <%
        for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
    %>
    <div style="text-align: center">
        <h3>Service Package Name: <%=servicePackageToSelect.getName() %></h3>
        <table class="table">
            <tr>
                <td>Name Service</td>
                <td>Number of Minutes</td>
                <td>Number of SMS</td>
                <td>Number of GB</td>
                <td>Fee Extra Minutes</td>
                <td>Fee Extra SMS</td>
                <td>Fee Extra GB</td>
            </tr>
            <%
                for (ServiceEntity service: servicePackageToSelect.getServices()) {
            %>
            <tr>
                <td><%=service.getTypeOfService() %></td>
                <td><%=service.getNumMinutes() %></td>
                <td><%=service.getNumSMS() %></td>
                <td><%=service.getNumGigabytes() %></td>
                <td><%=service.getFeeExtraMinute() %></td>
                <td><%=service.getFeeExtraSMSs() %></td>
                <td><%=service.getFeeForExtraGigabytes() %></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <%
        }
    %>

    <br><br>

    <button style="height:150px;width:200px" type="submit">GO TO THE BUY PAGE</button>
    </form>
    </div>


</body>
</html>



