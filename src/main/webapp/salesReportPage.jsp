<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.*" %>
<%@ page import="it.polimi.db2project.entities.*" %>
<%@ page import="it.polimi.db2project.entities.employeeQueries.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Telco Website</title>
    <style><%@include file="css/style.css"%></style>
</head>
<body>

<%
    //list of all service packages
    List<ServicePackageToSelectEntity> servicePackagesToSelect = (List<ServicePackageToSelectEntity>) request.getAttribute("servicePackagesToSelect");

    ServicePackageToSelectEntity servicePackageSelected = (ServicePackageToSelectEntity) request.getAttribute("servicePackageSelected");


    //first query
    TotalPurchasesPerPackageEntity totPurchaseXPackage = (TotalPurchasesPerPackageEntity) request.getAttribute("totPurchaseXPackage");

    //second query
    TotalPurchasesPerPackageAndValPeriodEntity totalPurchasesPerPackageAndValPeriod = (TotalPurchasesPerPackageAndValPeriodEntity) request.getAttribute("totalPurchasesPerPackageAndValPeriod");
    List<ValidityPeriodEntity> validityPeriods = (List<ValidityPeriodEntity>) request.getAttribute("validityPeriods");

    //third query
    SalesPerPackageWithOptProduct salesPerPackageWithOptProduct = (SalesPerPackageWithOptProduct) request.getAttribute("salesPerPackageWithOptProduct");
    SalesPerPackageWithoutOptProduct salesPerPackageWithoutOptProduct = (SalesPerPackageWithoutOptProduct) request.getAttribute("salesPerPackageWithoutOptProduct");

    //forth query
    AvgNumOfOptProductsSoldPerPackage avgNumOfOptProductsSoldPerPackage = (AvgNumOfOptProductsSoldPerPackage) request.getAttribute("avgNumOfOptProductsSoldPerPackage");

    //fifth query
    List<Alerts> alerts = (List<Alerts>) request.getAttribute("alerts");
    List<SuspendedOrders> suspendedOrders = (List<SuspendedOrders>) request.getAttribute("suspendedOrders");
    List<InsolventUsers> insolventUsers = (List<InsolventUsers>) request.getAttribute("insolventUsers");

    //sixth query
    SalesPerOptProduct salesPerOptProduct = (SalesPerOptProduct) request.getAttribute("salesPerOptProduct");


%>
<div style="text-align: center">

    <p align=right>Username of the employee: ${employee.username}</p>

    <p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

    <h1>SALES REPORT PAGE</h1>
    <h2>Visualize data about the sales and the users</h2>
</div>

<br>
<div class="shadowDiv">
    <h3>Number of total purchases per package</h3>

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
        <button name="button" class="niceButton" type="submit">SELECT SERVICE PACKAGE</button>
            <br><br>
            <%
                if(totPurchaseXPackage !=null){
            %>
            <p class="cyanText"><%=totPurchaseXPackage%></p>
            <%
                }
            %>

    </form>

    </div>

<br><br>


<div class="shadowDiv">
    <h3>Number of total purchases per package and validity period</h3>

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
        <button class="niceButton" type="submit">SELECT SERVICE PACKAGE</button>
        <br><br>
    </form>
        <%
            if(validityPeriods!=null){
        %>
        <br>
        <p>Package selected: <%=servicePackageSelected.getName() %></p>
        <form action="salesReportPage" method="post">
            <br><br>
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
            <br><br>

            <button class="niceButton" type="submit">SELECT VALIDITY PERIOD</button>

    </form>
        <br><br>
            <%
                if(totalPurchasesPerPackageAndValPeriod !=null){
            %>
            <p class="cyanText"><%=totalPurchasesPerPackageAndValPeriod%></p>
            <%
                 }
                }
            %>
</div>

<br><br>

<div class="shadowDiv">
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

        <button name="button" class="niceButton" type="submit">SELECT SERVICE PACKAGE</button>
        <br><br>
        <%
            if(salesPerPackageWithOptProduct !=null && salesPerPackageWithoutOptProduct!=null){
        %>
        <p class="cyanText"><%=salesPerPackageWithOptProduct%></p>
        <br>
        <p class="cyanText"><%=salesPerPackageWithoutOptProduct%></p>
        <%
            }
        %>
    </form>
</div>

<br><br>

<div class="shadowDiv">
    <h3>Average number of optional products sold together with each service package</h3>

        <form action="salesReportPage" method="post">


            <label for="srvPackageAvg">Choose a service package:</label>
            <select name="srvPackageAvg" id="srvPackageAvg">
                <%
                    for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
                %>
                <option value="<%=servicePackageToSelect.getServicePackageToSelect_id()%>"><%=servicePackageToSelect.getName() %></option>
                <%
                    }
                %>
            </select>
            <br><br>

            <button name="button" class="niceButton"  type="submit">SELECT SERVICE PACKAGE</button>
            <br><br>
            <%
                if(avgNumOfOptProductsSoldPerPackage !=null){
            %>
            <p class="cyanText"><%=avgNumOfOptProductsSoldPerPackage%></p>

            <%
                }
            %>
        </form>

</div>

<br><br>

<div class="shadowDiv">
    <h3>List of insolvent users, suspended orders and alerts.</h3>
    <div style="text-align: center">
        <h5>INSOLVENT USERS</h5>
    </div>

    <table style="border:2px solid black;margin-left:auto;margin-right:auto;">
        <tr>
            <td>Username</td>
        </tr>
        <%
            if(insolventUsers!=null){
            for (InsolventUsers insolventUser: insolventUsers) {
        %>
        <tr>
            <td><%=insolventUser.getUser().getUsername() %></td>
        </tr>
        <%
            }
            }
        %>
    </table>

    <div style="text-align: center">
        <h5>SUSPENDED ORDERS</h5>
    </div>
    <table style="border:2px solid black;margin-left:auto;margin-right:auto;">
        <tr>
            <td>Username</td>
            <td>Date and Hour</td>
        </tr>
       <%
           if(suspendedOrders!=null){
            for (SuspendedOrders suspendedOrder: suspendedOrders) {
        %>
        <tr>
            <td><%=suspendedOrder.getOrder().getUserOwner().getUsername() %></td>
            <td><%=suspendedOrder.getOrder().getDateAndHour() %></td>
        </tr>
        <%
            }
           }
        %>
    </table>

    <table style="border:2px solid black;margin-left:auto;margin-right:auto;">
        <div style="text-align: center">
            <h5>ALERTS</h5>
        </div>
        <tr>
            <td>UserID</td>
            <td>Username</td>
            <td>Email</td>
            <td>Amount</td>
            <td>Date and Time of the last rejection</td>
        </tr>
       <%
           if(alerts!=null){
            for (Alerts alert: alerts) {
        %>
        <tr>
            <td><%=alert.getAlert().getUserOwner().getUser_id()%></td>
            <td><%=alert.getAlert().getUserOwner().getUsername()%></td>
            <td><%=alert.getAlert().getUserOwner().getEmail() %></td>
            <td><%=alert.getAlert().getAmount() %></td>
            <td><%=alert.getAlert().getDateAndTime() %></td>
        </tr>
        <%
            }
           }
        %>
    </table>
</div>

<br><br>

<div class="shadowDiv">
    <h3>Best Seller Optional Product: the optional product with the greatest value of sales across all
        the sold service packages.</h3>
    <div style="text-align: center">
        <p class="cyanText"><%=salesPerOptProduct %></p>

    </div>
</div>


</body>
</html>