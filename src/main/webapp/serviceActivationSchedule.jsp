<%@ page import="it.polimi.db2project.entities.OrderEntity" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Service Activation Schedule</title>
</head>

<style><%@include file="css/style.css"%></style>

<%
    List<OrderEntity> ordersToActivate = (List<OrderEntity>) request.getAttribute("ordersToActivate");


%>

<body>

<div style="text-align: center">
    <h1>SERVICE ACTIVATION SCHEDULE</h1>
    <br>
    <h2>Orders not yet activated</h2>


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
