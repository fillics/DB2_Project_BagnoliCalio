<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.*" %>
<%@ page session="true" %>
<%@ page import="it.polimi.db2project.services.EmployeeService" %>
<%@ page import="it.polimi.db2project.entities.*" %>
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
    List<ServiceEntity> services = (List<ServiceEntity>) request.getAttribute("services");
    List<OptionalProductEntity> optionalProducts = (List<OptionalProductEntity>) request.getAttribute("optionalProducts");
    List<ValidityPeriodEntity> validityPeriods = (List<ValidityPeriodEntity>) request.getAttribute("validityPeriods");
    List<ServicePackageToSelectEntity> servicePackagesToSelect = (List<ServicePackageToSelectEntity>) request.getAttribute("servicePackagesToSelect");

%>

<div style="text-align: center">

    <p align=right>Username of the employee: ${employee.username}</p>
    <p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

    <h1>EMPLOYEE PAGE</h1>
    <br>

    <form action="salesReportPage">
        <button class="niceButtonEmployee" type="submit">GO TO THE SALES REPORT PAGE</button>
    </form>
    <br>

    <div class="shadowDiv">
    <h2>Creating Service Package</h2>
    <form action="servicePackageToSelect" method="post">

        <%--@declare id="nameservpackage"--%>
        <input class="inputSrvPackage" name="nameServPackage" placeholder="Name Service Package" required/>
        <br><br>

        <fieldset>
            <legend>Choose one or more services</legend>
            <%
                for (ServiceEntity serv: services) {
            %>
            <input type="checkbox" name="services"
                   value="<%=serv.getService_id()%>"><%=serv.getTypeOfService() %><br>
            <%
                }
            %>
        </fieldset>

            <br><br>

        <fieldset>
            <legend>Choose one or more optional products</legend>
            <%

                for (OptionalProductEntity optProd: optionalProducts) {
            %>
            <input type="checkbox" name="optionalProducts"
                   value="<%=optProd.getOptionalProduct_id() %>"><%=optProd.getName() %><br>
            <%
                }
            %>
        </fieldset>

            <br><br>

        <fieldset>
            <legend>Choose one or more validity periods associated to this service package</legend>
            <%
                for (ValidityPeriodEntity valPer: validityPeriods) {
            %>
            <input type="checkbox" name="validityPeriods"
                   value="<%=valPer.getValidityPeriod_id() %>"><%=valPer.toString() %><br>
            <%
                }
            %>
        </fieldset>

        <br>${messageServicePackage}
        <br><br>
        <button class="niceButton" type="submit">CREATE</button>
    </form>

    </div>

    <br><br>

    <div class="shadowDiv">
    <h2>Creating Optional Product</h2>
    <form action="optionalProduct" method="post">
        <%--@declare id="name"--%><%--@declare id="monthlyfee"--%>

        <input class="inputSrvPackage" name="name" placeholder="Name Optional Product" required/>

        <br><br>

        <label for="monthlyFee">Monthly Fee:</label>
        <input id="monthlyFee" type="number" name="monthlyFee" step="0.10" min="0" required/>

        <br><br>

        <br>${messageOptProduct}<br>
        <button class="niceButton" type="submit">CREATE</button>
    </form>

    </div>

</div>

<div style="text-align: center">
    <h2>Service Packages created</h2>
    <table class="table" >
        <thead class="white">
            <tr>
                <td>Name Service Package</td>
            </tr>
        </thead>
        <tbody>
            <%
                for (ServicePackageToSelectEntity servicePackageToSelectEntity: servicePackagesToSelect) {
            %>
            <tr>
                <td><%=servicePackageToSelectEntity.getName() %></td>
            </tr>
            <%
                }
            %>
        </tbody>

    </table>
</div>

<br><br>
<div style="text-align: center">
    <h2>Optional Products created</h2>
    <table class="table">
        <thead class="white">
        <tr>
            <td>Name Optional Product</td>
            <td>Monthly Fee</td>
        </tr>
        </thead>
       <tbody>
       <%
           for (OptionalProductEntity optProd: optionalProducts) {
       %>
       <tr>
           <td><%=optProd.getName() %></td>
           <td><%=optProd.getMonthlyFee() %></td>
       </tr>
       <%
           }
       %>
       </tbody>

    </table>
</div>

</body>
</html>