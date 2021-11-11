<%@ page import="java.util.List" %>
<%@ page import="it.polimi.db2project.entities.OptionalProductEntity" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.*" %>
<%@ page import="it.polimi.db2project.entities.ServiceEntity" %>
<%@ page import="it.polimi.db2project.entities.ValidityPeriodEntity" %>
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
    try
    {
        ArrayList<OptionalProductEntity> optionalProducts = new ArrayList<>();
        ArrayList<ServiceEntity> services = new ArrayList<>();
        ArrayList<ValidityPeriodEntity> validityPeriods = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url="jdbc:mysql://localhost:3306/dbtelco";
        String username="admin";
        String password="admin";
        String queryOptProducts = "select * from optionalproduct";
        String queryServices = "select * from service";
        String queryValidityPeriod = "select * from validityperiod";

        ResultSet rsOptProduct = null;
        ResultSet rsService = null;
        ResultSet rsValPeriod = null;
        Connection conn = null;
        Statement stmtOptProd = null;
        Statement stmtService = null;
        Statement stmtValPeriod = null;
        try {
            conn= DriverManager.getConnection(url,username,password);
            stmtOptProd = conn.createStatement();
            stmtService = conn.createStatement();
            stmtValPeriod = conn.createStatement();
            rsOptProduct = stmtOptProd.executeQuery(queryOptProducts);
            rsService = stmtService.executeQuery(queryServices);
            rsValPeriod = stmtValPeriod.executeQuery(queryValidityPeriod);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        while(rsOptProduct.next()) {
            OptionalProductEntity optionalProduct = new OptionalProductEntity();
            try {
                optionalProduct.setOptionalProduct_id(rsOptProduct.getLong("optionalProduct_id"));
                optionalProduct.setName(rsOptProduct.getString("name"));
                optionalProduct.setMonthlyFee(rsOptProduct.getFloat("monthlyFee"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            optionalProducts.add(optionalProduct);
        }
        while(rsService.next()) {
            ServiceEntity service = new ServiceEntity();
            try {
                service.setService_id(rsService.getLong("service_id"));
                service.setTypeOfService(rsService.getString("typeOfService"));
                service.setNumMinutes(rsService.getInt("numMinutes"));
                service.setNumSMS(rsService.getInt("numSMS"));
                service.setFeeExtraMinute(rsService.getFloat("feeExtraMinute"));
                service.setFeeExtraSMSs(rsService.getFloat("feeExtraSMSs"));
                service.setNumberOfGigabytes(rsService.getInt("numGigabytes"));
                service.setFeeForExtraGigabytes(rsService.getFloat("feeForExtraGigabytes"));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            services.add(service);
        }
        while(rsValPeriod.next()) {
            ValidityPeriodEntity validityPeriod = new ValidityPeriodEntity();
            try {
                validityPeriod.setValidityPeriod_id(rsValPeriod.getLong("validityPeriod_id"));
                validityPeriod.setNumOfMonths(rsValPeriod.getInt("numOfMonths"));
                validityPeriod.setMonthlyFee(rsValPeriod.getFloat("monthlyFee"));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            validityPeriods.add(validityPeriod);
        }
%>


<div style="text-align: center">

    <p align=right>Username of the employee: ${user.username}</p>
    <p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

    <h1>EMPLOYEE PAGE</h1>
    <br>
    <h2>Creating Service Package</h2>
    <form action="servicePackage" method="post">

        <%--@declare id="nameservpackage"--%>
        <label for="nameServPackage">Name Service Package:</label>
        <input name="nameServPackage" size="30" />
        <br><br>

        <fieldset>
            <legend>Choose one or more services</legend>
            <%
                for (ServiceEntity serv: services) {
            %>
            <input type="checkbox" name="services" value="<%=serv.getTypeOfService() %>"><%=serv.getTypeOfService() %><br>
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
            <input type="checkbox" name="optionalProducts" value="<%=optProd.getName() %>"><%=optProd.getName() %><br>
            <%
                }
            %>
        </fieldset>

        <label for="validityPeriod">Choose a validity period:</label>
        <select name="validityPeriod" id="validityPeriod">
            <%
                for (ValidityPeriodEntity valPer: validityPeriods) {
            %>
            <option value="<%=valPer.toString() %>"><%=valPer.toString() %></option>
            <%
                }
            %>
        </select>

        <br>${messageLogin}
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
        <input id="monthlyFee" type="number" name="monthlyFee" step="0.01" min="0" required/>

        <br><br>

        <br>${messageOptProduct}
        <button type="submit">CREATE</button>
    </form>
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
    <%
            rsOptProduct.close();
            rsService.close();
            rsValPeriod.close();
            stmtOptProd.close();
            stmtService.close();
            stmtValPeriod.close();
            conn.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    %>
</div>
<p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

</body>
</html>