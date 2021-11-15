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


    <p align=right>Username of the employee: ${user.username}</p>
    <p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

    <h1>EMPLOYEE PAGE</h1>
    <br>
    <h2>Creating Service Package</h2>
    <form action="servicePackageToSelect" method="post">

        <%--@declare id="nameservpackage"--%>
        <label for="nameServPackage">Name Service Package:</label>
        <input name="nameServPackage" size="30" />
        <br><br>

        <fieldset>
            <legend>Choose one or more services</legend>
            <%
                List<ServiceEntity> services = (List<ServiceEntity>)
                request.getAttribute("services");
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
                List<OptionalProductEntity> optionalProducts = (List<OptionalProductEntity>)
                request.getAttribute("optionalProducts");
                for (OptionalProductEntity optProd: optionalProducts) {
            %>
            <input type="checkbox" name="optionalProducts"
                   value="<%=optProd.getOptionalProduct_id() %>"><%=optProd.getName() %><br>
            <%
                }
            %>
        </fieldset>


        <fieldset>
            <legend>Choose one or more validity periods associated to this service package</legend>
            <%
                List<ValidityPeriodEntity> validityPeriods = (List<ValidityPeriodEntity>)
                request.getAttribute("validityPeriods");
                for (ValidityPeriodEntity valPer: validityPeriods) {
            %>
            <input type="checkbox" name="validityPeriods"
                   value="<%=valPer.getValidityPeriod_id() %>"><%=valPer.toString() %><br>
            <%
                }
            %>
        </fieldset>

        <%-- CON QUESTO SCEGLIAMO SOLO UN VALIDITY PERIOD

        <label for="validityPeriod">Choose a validity period:</label>
        <select name="validityPeriod" id="validityPeriod">
            <%
                for (ValidityPeriodEntity valPer: validityPeriods) {
            %>
            <option value="<%=valPer.toString() %>"><%=valPer.toString() %></option>
            <%
                }
            %>
        </select>--%>

        <br>${messageServicePackage}
        <br><br>
        <button type="submit">CREATE</button>
    </form>



    <br><br>


    <h2>Creating Optional Product</h2>
    <form action="optionalProduct" method="post">
        <%--@declare id="name"--%><%--@declare id="monthlyfee"--%>
        <label for="name">Name Optional Product:</label>
        <input name="name" size="30" required/>

        <br><br>

        <label for="monthlyFee">Monthly Fee:</label>
        <input id="monthlyFee" type="number" name="monthlyFee" step="0.10" min="0" required/>

        <br><br>

        <br>${messageOptProduct}<br>
        <button type="submit">CREATE</button>
    </form>
</div>

<div style="text-align: center">
    <table border="2">
        <tr>
            <td>Name Service Package to Select</td>

        </tr>
        <%
            List<ServicePackageToSelectEntity> servicePackagesToSelect = (List<ServicePackageToSelectEntity>)
            request.getAttribute("servicePackagesToSelect");
            for (ServicePackageToSelectEntity servicePackageToSelectEntity: servicePackagesToSelect) {
        %>
        <tr>
            <td><%=servicePackageToSelectEntity.getName() %></td>
        </tr>
        <%
            }
        %>
    </table>
</div>


<div style="text-align: center">
    <table border="2">
        <tr>
            <td>Name Optional Product</td>
            <td>Monthly Fee</td>
        </tr>
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
    </table>
</div>

</body>
</html>