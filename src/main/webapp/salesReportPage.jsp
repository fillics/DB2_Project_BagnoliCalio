<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.*" %>
<%@ page import="it.polimi.db2project.entities.*" %>
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

<%
    List<ServicePackageToSelectEntity> servicePackagesToSelect = (List<ServicePackageToSelectEntity>)
    request.getAttribute("servicePackagesToSelect");
    List<ValidityPeriodEntity> validityPeriods = (List<ValidityPeriodEntity>)
    request.getAttribute("validityPeriods");
    List<OptionalProductEntity> optionalProducts = (List<OptionalProductEntity>)
    request.getAttribute("optionalProducts");

    int avgNumOptProductsWithServPackage = 0;
    request.getAttribute("avgNumOptProductsWithServPackage");

    List<AlertEntity> alerts = (List<AlertEntity>)
    request.getAttribute("alerts");
    List<OrderEntity> orders = (List<OrderEntity>)
    request.getAttribute("orders");

    ArrayList<Integer> purchasePerPackage = (ArrayList<Integer>) request.getAttribute("purchasePerPackage");

    TotalPurchasesPerPackageEntity packageSelected = (TotalPurchasesPerPackageEntity) request.getAttribute("totPurchaseXPackage");


%>
<div style="text-align: center">

    <p align=right>Username of the employee: ${employee.username}</p>

    <p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

    <h1>SALES REPORT PAGE</h1>
    <h2>Visualize data about the sales and the users</h2>
</div>

<br>
<div>
    <h3>Number of total purchases per package</h3>
    <div style="text-align: center">

        <form action="salesReportPage" method="post">

        <label for="srvPackage">Choose a service package:</label>
        <select name="srvPackage" id="srvPackage">
            <%
                for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
            %>
            <option value="<%=servicePackageToSelect.getServicePackageToSelect_id()%>"><%=servicePackageToSelect.getName() %></option>
            <%
                }
            %>
        </select>
        <br><br>
            <%
                if(packageSelected!=null){
            %>
            <p><%=packageSelected%></p>
            <%
                }
            %>
        <button name="button" type="submit">SELECT SERVICE PACKAGE</button>
    </form>
</div>
</div>

<br><br>


<div>
    <h3>Number of total purchases per package and validity period.</h3>
    <form action="salesReportPage" method="post">

        <label for="srvPackageWithValPeriod">Choose a service package:</label>
        <select name="srvPackageWithValPeriod" id="srvPackageWithValPeriod">
            <%
                for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
            %>
            <option value="<%=servicePackageToSelect.getServicePackageToSelect_id()%>"><%=servicePackageToSelect.getName() %></option>
            <%
                }
            %>
        </select>
        <br><br>
        <button type="submit">SELECT SERVICE PACKAGE</button>
        <br>

        <%
            if(validityPeriods!=null){
        %>

        <form action="servicePackage" method="post">
            <label for="valPeriod">Choose a validity period:</label>
            <select name="valPeriod" id="valPeriod">
                <%
                    for (ValidityPeriodEntity valPeriod: validityPeriods) {
                %>
                <option value="<%=valPeriod.getValidityPeriod_id()%>"><%=valPeriod.toString() %></option>
                <%
                    }
                %>
            </select>
            <button type="submit">SELECT VALIDITY PERIOD</button>
        </form>

            <%
                    }
            %>

</div>

<br><br>

<div>
    <h3>Total value of sales per package with and without the optional products.</h3>
    <form action="salesReportPage" method="post">

        <label for="srvPackageWithOptProducts">Choose a service package:</label>
        <select name="srvPackageWithOptProducts" id="srvPackageWithOptProducts">
            <%
                for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
            %>
            <option value="<%=servicePackageToSelect.getServicePackageToSelect_id()%>"><%=servicePackageToSelect.getName() %></option>
            <%
                }
            %>
        </select>
        <br><br>
        <button type="submit">SELECT SERVICE PACKAGE</button>

        <%
            if(optionalProducts!=null){
        %>

        <form action="servicePackage" method="post">
            <legend>Choose one or more optional products</legend>
            <%
                for (OptionalProductEntity optProd: optionalProducts) {
            %>
            <input type="checkbox" name="optionalProducts"
                   value="<%=optProd.getOptionalProduct_id() %>"><%=optProd.getName() %><br>
            <%
                }
            %>

            <button type="submit">SELECT OPTIONAL PRODUCTS</button>

        </form>

            <%
                }
            %>
</div>

<br><br>

<div>
    <h3>Average number of optional products sold together with each service package.</h3>
    <form action="salesReportPage" method="post">

        <label for="avgNumOptProductsWithServPackage">Choose a service package:</label>
        <select name="avgNumOptProductsWithServPackage" id="avgNumOptProductsWithServPackage">
            <%
                for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
            %>
            <option value="<%=servicePackageToSelect.getServicePackageToSelect_id()%>"><%=servicePackageToSelect.getName() %></option>
            <%
                }
            %>
        </select>
        <br><br>
        <button type="submit">SELECT SERVICE PACKAGE</button>
    </form>
            <%
                if(avgNumOptProductsWithServPackage!=0){
            %>

            <%
                }
            %>

</div>

<br><br>

<div>
    <h3>List of insolvent users, suspended orders and alerts.</h3>
    <table style="border:2px solid black;margin-left:auto;margin-right:auto;">
        <h5>INSOLVENT USERS</h5>
        <tr>
            <td>Username</td>
        </tr>
        <%--<%
            for (OptionalProductEntity optProd: optionalProducts) {
        %>
        <tr>

        </tr>
        <%
            }
        %>--%>
    </table>

    <table style="border:2px solid black;margin-left:auto;margin-right:auto;">
        <h5>SUSPENDEND ORDERS</h5>
        <tr>
            <td>Username</td>
            <td>Date and Hour</td>
        </tr>
       <%-- <%
            for (OrderEntity order: orders) {
        %>
        <tr>
            <td><%=order.getUserOwner().getUsername() %></td>
            <td><%=order.getDateAndHour() %></td>
        </tr>
        <%
            }
        %>--%>
    </table>

    <table style="border:2px solid black;margin-left:auto;margin-right:auto;">
        <h5>ALERTS</h5>
        <tr>
            <td>UserID</td>
            <td>Username</td>
            <td>Email</td>
            <td>Amount</td>
            <td>Date and Time of the last rejection</td>
        </tr>
       <%-- <%
            for (AlertEntity alert: alerts) {
        %>
        <tr>
            <td><%=alert.getUserOwner().getUserId() %></td>
            <td><%=alert.getUserOwner().getUsername() %></td>
            <td><%=alert.getUserOwner().getEmail() %></td>
            <td><%=alert.getAmount() %></td>
            <td><%=alert.getDateAndTime() %></td>
        </tr>
        <%
            }
        %>--%>
    </table>
</div>

<br><br>

<div>
    <h3>Best Seller Optional Product.</h3>

</div>


</body>
</html>