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

<style><%@include file="css/style.css"%></style>

<body>

<%
    List<ServicePackageToSelectEntity> servicePackagesToSelect = (List<ServicePackageToSelectEntity>)
    request.getAttribute("servicePackagesToSelect");
    List<ValidityPeriodEntity> validityPeriods = (List<ValidityPeriodEntity>)
            request.getAttribute("validityPeriods");
    List<OptionalProductEntity> optionalProducts = (List<OptionalProductEntity>)
    request.getAttribute("optionalProducts");
    String packageSelected = (String) request.getAttribute("packageSelected");

    UserEntity user = null;
    String userUsername = null;
    if(request.getSession().getAttribute("user")!=null){
        user = (UserEntity) request.getSession().getAttribute("user");
        userUsername = user.getUsername();
    }

%>

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

    <h1>BUY PAGE</h1>
    <br>
    <h2>Selecting Service Package</h2>
    <form action="buyPage" method="post">

        <br><br>
        <label for="srvPackage">Choose a service package to buy:</label>
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

        <button type="submit" name = "servPackageBtn">SELECT SERVICE PACKAGE</button>

        <br><br>
        </form>

        <form action="buyPage" method="post">
        <%
            if(validityPeriods!=null && optionalProducts!= null){
        %>

            <h4>Package selected: <%=packageSelected%></h4>
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

        <%
            if(optionalProducts.size()!= 0){
        %>

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

        <label>Choose the start date:
            <input type="date" name="startDate" required>
        </label>

        <br><br>

        <button type="submit" name ="confirmBtn">CONFIRM</button>
            <%
                }
            %>
        </form>
</div>

</body>
</html>



