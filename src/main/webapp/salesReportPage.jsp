<%@ page import="java.util.List" %>
<%@ page import="it.polimi.db2project.entities.OptionalProductEntity" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.*" %>
<%@ page import="it.polimi.db2project.entities.ServiceEntity" %>
<%@ page import="it.polimi.db2project.entities.ValidityPeriodEntity" %>
<%@ page import="it.polimi.db2project.entities.ServicePackageToSelectEntity" %>
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



<div style="text-align: center">

    <p align=right>Username of the employee: ${employee.username}</p>
    <p align=right>ID of the employee: ${employee.employee_id}</p>

    <p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

    <h1>SALES REPORT PAGE</h1>
    <br>
    <h2>Visualize data about the sales and the users</h2>
</div>

<div>
    <h4>Number of total purchases per package</h4>
    <form action="salesReportPage" method="post">

        <br>
        <label for="srvPackage">Choose a service package:</label>
        <select name="srvPackage" id="srvPackage">
            <%
                List<ServicePackageToSelectEntity> servicePackagesToSelect = (List<ServicePackageToSelectEntity>)
                request.getAttribute("servicePackagesToSelect");
                for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
            %>
            <option value="<%=servicePackageToSelect.getServicePackageToSelect_id()%>"><%=servicePackageToSelect.getName() %></option>
            <%
                }
            %>
        </select>
        <br><br>
        <button name="bottone" value="${employee.employee_id}" type="submit">SELECT SERVICE PACKAGE</button>
    </form>
</div>

<div>
    <h4>Number of total purchases per package and validity period.</h4>
    <form action="salesReportPage" method="post">

        <br>
        <label for="srvPackageWithValPeriod">Choose a service package:</label>
        <select name="srvPackageWithValPeriod" id="srvPackageWithValPeriod">
            <%
                List<ValidityPeriodEntity> validityPeriods = (List<ValidityPeriodEntity>)
                request.getAttribute("validityPeriods");
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
    </form>

            <%
                    }
            %>
</div>



</body>
</html>