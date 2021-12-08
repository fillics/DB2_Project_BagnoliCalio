<%@ page import="it.polimi.db2project.entities.OrderEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="it.polimi.db2project.entities.ServiceEntity" %>
<%@ page import="it.polimi.db2project.entities.OptionalProductEntity" %>
<%@ page import="it.polimi.db2project.entities.UserEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Service Activation Schedule</title>
</head>

<style><%@include file="css/style.css"%></style>

<%
    List<OrderEntity> ordersToActivate = (List<OrderEntity>) request.getAttribute("ordersToActivate");

    for (OrderEntity orderEntity: ordersToActivate){
        System.out.println(orderEntity);
    }

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
    <h2>Orders successfully bought but not yet activated</h2>

    <%
        for (OrderEntity order: ordersToActivate) {
    %>
    <div style="text-align: center">
        <h3>ID order to activate: <%=order.getOrder_id() %></h3>
        <table class="tableGreen">
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
            <tr>
                <td><%=order.getDateAndHour() %></td>
                <td><%=order.getServicePackage().getPackageSelected().getName() %></td>
                <%
                    for(ServiceEntity service: order.getServicePackage().getPackageSelected().getServices()){
                %>
                <td><%=service.getTypeOfService() %></td>

                <%
                    }
                    if(order.getServicePackage().getOptionalProducts()!= null && order.getServicePackage().getOptionalProducts().size()!=0){
                        for (OptionalProductEntity optionalProduct: order.getServicePackage().getOptionalProducts()){
                %>
                    <td><%=optionalProduct.getName() %></td>
                <%
                    }
                    }
                %>
                <td><%=order.getTotalValueOrder() %></td>
                <td><%=order.getServicePackage().getStartDate() %></td>
                <td><%=order.getServicePackage().getEndDate()%></td>

            </tr>
        </table>

    </div>
    <%
        }
    %>


</div>


<div style="text-align: center">

    <div class="inner">
        <form action="serviceActivationSchedule" method="post">
            <button class="button" type="submit">GO TO THE HOME PAGE</button>
        </form>

    </div>

</div>

</body>
</html>
