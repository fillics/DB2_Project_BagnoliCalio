<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.polimi.db2project.entities.*" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
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
<%
    try
    {
        ArrayList<ServicePackageToSelectEntity> servicePackagesToSelect = new ArrayList<>();
        ArrayList<OptionalProductEntity> optionalProducts = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url="jdbc:mysql://localhost:3306/dbtelco";
        String username="admin";
        String password="admin";
        String queryServicePackageToSelect = "select * from servicePackageToSelect";
        String queryOptionalProduct = "select * from optionalProduct";

        ResultSet rsServicePackageToSelect = null;
        ResultSet rsOptProduct = null;
        Connection conn = null;
        Statement stmtServicePackageToSelect = null;
        Statement stmtOptProd = null;

        try {
            conn= DriverManager.getConnection(url,username,password);
            stmtServicePackageToSelect = conn.createStatement();
            stmtOptProd = conn.createStatement();
            rsServicePackageToSelect = stmtServicePackageToSelect.executeQuery(queryServicePackageToSelect);
            rsOptProduct = stmtOptProd.executeQuery(queryOptionalProduct);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        while(rsServicePackageToSelect.next()) {
            ServicePackageToSelectEntity servicePackageToSelect = new ServicePackageToSelectEntity();
            try {
                servicePackageToSelect.setServicePackageToSelect_id((rsServicePackageToSelect.getLong("servicePackageToSelect_id")));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            servicePackagesToSelect.add(servicePackageToSelect);
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
%>

<div style="text-align: center">

    <p align=right>Username of the user: ${user.username}</p>
    <p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

    <h1>USER PAGE</h1>
    <br>
    <h2>Selecting Service Package</h2>
    <form action="servicePackage" method="post">

        <br><br>

        <fieldset>
            <legend>Choose one Service Package</legend>
            <%
                for (ServicePackageToSelectEntity servicePackageToSelect: servicePackagesToSelect) {
            %>
            <input type="checkbox" name="servicePackageToSelect"
                   value="<%=servicePackageToSelect.getServicePackageToSelect_id() %>"><%=servicePackageToSelect.getName()%>
            <br>
            <details>
                <p>Optional Products: ${servicePackageToSelect.getOptionalProducts.getName()}</p>
                <p>Monthly Fee: ${servicePackageToSelect.getValidityPeriod()}</p>
            </details>
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
            <input type="checkbox" name="optionalProducts" value="<%=optProd.getOptionalProduct_id() %>"><%=optProd.getName() %><br>
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

        <br>${messageServicePackage}<br>
        <button type="submit">CONFIRM</button>
    </form>
</div>
<%
        rsOptProduct.close();
        rsServicePackageToSelect.close();
        stmtOptProd.close();
        stmtServicePackageToSelect.close();
        conn.close();
    }
    catch(Exception e) {
        e.printStackTrace();
    }
%>
</body>
</html>



