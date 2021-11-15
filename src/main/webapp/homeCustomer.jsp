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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css" />
</head>
<body>


<div style="text-align: center">

    <p align=right>Username of the user: ${user.username}</p>
    <p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

    <h1>HOME PAGE</h1>
    <br>
    <h2>Selecting Service Package</h2>
    <form action="servicePackage" method="post">

        <br><br>

        <%
            List<ServicePackageToSelectEntity> servicePackagesToSelect = (List<ServicePackageToSelectEntity>)
            request.getAttribute("servicePackagesToSelect");
            for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
        %>
        <div style="text-align: center">
            <h3>Service Package Name: <%=servicePackageToSelect.getName() %></h3>
            <table border="2">
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
        <label for="srvPackage">Choose a service package to buy:</label>
        <select name="srvPackage" id="srvPackage">
            <%
                for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
            %>
            <option value="<%=servicePackageToSelect.getServicePackageToSelect_id() %>"><%=servicePackageToSelect.getName() %></option>
            <%
                }
            %>
        </select>
        <br><br>
<%--

        <fieldset>
            <legend>Choose one Service Package</legend>
            <%

                for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
            %>
            <input type="radio" name="servicePackageToSelect"
                   value="<%=servicePackageToSelect.getServicePackageToSelect_id() %>"><%=servicePackageToSelect.toString()%>
            <br>
            <%
                }
            %>
        </fieldset>


        <br><br>--%>


    <br>${messageServicePackage}<br>
    <button type="submit">BUY</button>
</form>
</div>

<%--
<div style="text-align: center">
    <table border="2">
        <tr>
            <td>Name Service Package</td>
            <td>Services</td>
        </tr>
        <%
            for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
        %>
        <tr>
            <td><%=servicePackageToSelect.getName() %></td>
            <%
                for (ServiceEntity service: servicePackageToSelect.getServices()) {
            %>
            <td><%=service.getTypeOfService() %></td>
            <%
                }
            %>
        </tr>
        <%
            }
        %>
    </table>
</div>

<div style="text-align: center">
    <table border="2">
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
            List<ServiceEntity> services = (List<ServiceEntity>)
            request.getAttribute("services");
            for (ServiceEntity service: services) {
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

<br><br>--%>

    </body>
</html>



