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

    <h1>BUY PAGE</h1>
    <br>
    <h2>Selecting Service Package</h2>
    <form action="buyPage" method="post">

        <br><br>
        <label for="srvPackage">Choose a service package to buy:</label>
        <select name="srvPackage" id="srvPackage">
            <%
                List<ServicePackageToSelectEntity> servicePackagesToSelect = (List<ServicePackageToSelectEntity>)
                request.getAttribute("servicePackagesToSelect");
                List<ValidityPeriodEntity> validityPeriods = (List<ValidityPeriodEntity>)
                request.getAttribute("validityPeriods");
                List<OptionalProductEntity> optionalProducts = (List<OptionalProductEntity>)
                request.getAttribute("optionalProducts");
                for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
            %>
            <option value="<%=servicePackageToSelect.getServicePackageToSelect_id()%>"><%=servicePackageToSelect.getName() %></option>
            <%
                }
            %>
        </select>
        <br><br>

        <button type="submit" name = "button1" value="${user.user_id}">SELECT SERVICE PACKAGE</button>

        <br><br>
        </form>

        <form action="buyPage" method="post">
        <%
            if(validityPeriods!=null && optionalProducts!= null){
        %>

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

        <div style="text-align: center">
            <table style="border:2px solid black;margin-left:auto;margin-right:auto;" >
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
        <br><br>

        <fieldset>
            <legend>Choose one or more optional products</legend>
            <%
                for (OptionalProductEntity optProd: optionalProducts) {
            %>
            <input type="checkbox" name="optProducts"
                   value="<%=optProd.getOptionalProduct_id() %>"><%=optProd.getName() %><br>
            <%
                }
            %>
        </fieldset>
        <br><br>

        <form>
            <label>Choose the start date:
                <input type="date" name="startDate">
            </label>
        </form>
        <br><br>

        <button type="submit" name ="button2" value="${user.user_id}">CONFIRM</button>

        <%
            }
        %>

    </form>
</div>

</body>
</html>



