<%@ page import="it.polimi.db2project.entities.OrderEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="it.polimi.db2project.entities.UserEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Service Activation Schedule</title>
</head>

<style><%@include file="css/style.css"%></style>

<%
    List<OrderEntity> ordersToActivate = (List<OrderEntity>) request.getAttribute("ordersToActivate");
    
    UserEntity user = null;
    String userUsername = null;
    if(request.getSession().getAttribute("user")!=null){
        user = (UserEntity) request.getSession().getAttribute("user");
        userUsername = user.getUsername();
    }
%>

<body>

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

<div style="text-align: center">
    <h1>SERVICE ACTIVATION SCHEDULE</h1>
    <br>
    <h2>Orders successfully bought, but not yet activated</h2>

    <%
        for (OrderEntity order: ordersToActivate) {
    %>
    <div style="text-align: center">
        <h3>ID order: <%=order.getOrder_id() %></h3>
        <table class="table">
            <thead class="theadGreen">
                <tr>
                    <td>Date and Hour Order</td>
                    <td>Name Service Package</td>
                    <td>Services included</td>
                    <%
                        if(order.getServicePackage().getOptionalProducts()!= null && order.getServicePackage().getOptionalProducts().size()!=0){
                    %>
                    <td>Optional Products</td>
                    <%
                        }
                    %>
                    <td>Total Value Order</td>
                    <td>Start Date</td>
                    <td>End Date</td>
                </tr>
            </thead>

            <tbody>
                <tr>
                    <td><%=order.getDateAndHour() %></td>
                    <td><%=order.getServicePackage().getPackageSelected().getName() %></td>
                    <td><%=order.getServicePackage().getPackageSelected().getServices()%></td>
                    <%
                        if(order.getServicePackage().getOptionalProducts()!= null && order.getServicePackage().getOptionalProducts().size()!=0){
                    %>
                    <td><%=order.getServicePackage().getOptionalProducts() %></td>
                    <%
                        }
                    %>
                    <td><%=order.getTotalValueOrder() %></td>
                    <td><%=order.getServicePackage().getStartDate() %></td>
                    <td><%=order.getServicePackage().getEndDate()%></td>

                </tr>
            </tbody>

        </table>

    </div>
    <%
        }
    %>


</div>
<br>

<div style="text-align: center">

    <div>
        <form action="serviceActivationSchedule" method="post">
            <button class="button" type="submit">GO TO THE HOME PAGE</button>
        </form>

    </div>

</div>

</body>
</html>
