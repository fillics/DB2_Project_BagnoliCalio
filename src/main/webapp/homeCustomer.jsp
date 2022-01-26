<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.polimi.db2project.entities.*" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Home Page Customer</title>
</head>

<style><%@include file="css/style.css"%></style>

<body>

<%
    List<ServicePackageToSelectEntity> servicePackagesToSelect = (List<ServicePackageToSelectEntity>) request.getAttribute("servicePackagesToSelect");

    UserEntity user = null;
    String userUsername = null;
    if(request.getSession().getAttribute("user")!=null){
        user = (UserEntity) request.getSession().getAttribute("user");
        userUsername = user.getUsername();
    }
    List<OrderEntity> rejectedOrders = (List<OrderEntity>) request.getAttribute("rejectedOrders");

    if(user!=null){
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

<div style="text-align: center">
    <h1>HOME PAGE</h1>

    <div class="form">
        <form action="serviceActivationSchedule">
            <button type="submit" class="niceButton">
                my orders
            </button>
        </form>
    </div>


    <div class="shadowDiv">

    <%
        if(userUsername!=null && rejectedOrders!=null && rejectedOrders.size()>0) {
    %>
    <br>
    <h2>You are an insolvent user. List of rejected orders:</h2>
    <br>
        <%
        for (OrderEntity order: rejectedOrders) {
        %>
        <div style="text-align: center">
            <h3>ID rejected order: <%=order.getOrder_id() %></h3>
            <table class="table">
                <thead class="theadRed">
                    <tr>
                        <td>Date and Hour</td>
                        <td>Name Service Package</td>
                        <td>Total Value Order</td>
                    </tr>
                </thead>
                <tbody  class="tbodyWhite">
                    <tr>
                        <td><%=order.getDateAndHour() %></td>
                        <td><%=order.getServicePackage().getPackageSelected().getName() %></td>
                        <td><%=order.getTotalValueOrder() %></td>
                    </tr>
                </tbody>

            </table>
            <br>
            <form action="confirmationPage" method="get">
                <button class="niceButton" name="rejectedOrder" value="<%=order.getOrder_id()%>" type="submit">PAY AGAIN</button>
            </form>
        </div>
            <%
                 }
            }
            %>
    </div>

    <br>

    <h2>List of Service Package available</h2>
    <div style="text-align: center">
    <%
        for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
    %>
        <h3>Service Package Name: <%=servicePackageToSelect.getName() %></h3>

        <table class="table">
            <thead class="theadGrey">
            <tr>
                <td>Name Service</td>
                <td>Number of Minutes</td>
                <td>Number of SMS</td>
                <td>Number of GB</td>
                <td>Fee Extra Minutes</td>
                <td>Fee Extra SMS</td>
                <td>Fee Extra GB</td>
            </tr>
            </thead>

            <tbody class="tbodyWhite">
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
            </tbody>

        </table>

    <%
        }
    %>
        <br>
        <form action="buyPage">
            <button class="niceButton" type="submit">GO TO THE BUY PAGE</button>
        </form>


</div>
</div>


</body>
</html>



