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

        try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String url="jdbc:mysql://localhost:3306/dbtelco";
            String username="admin";
            String password="admin";
            String queryServicePackageToSelect = "select * from servicePackageToSelect";

            ResultSet rsServicePackageToSelect = null;
            Connection conn = null;
            Statement stmtServicePackageToSelect = null;

            try {
                conn= DriverManager.getConnection(url,username,password);
                stmtServicePackageToSelect = conn.createStatement();
                rsServicePackageToSelect = stmtServicePackageToSelect.executeQuery(queryServicePackageToSelect);
             } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        while(rsServicePackageToSelect.next()) {
            ServicePackageToSelectEntity servicePackageToSelect = new ServicePackageToSelectEntity();
            try {
                servicePackageToSelect.setServicePackageToSelect_id((rsServicePackageToSelect.getLong("servicePackageToSelect_id")));
                servicePackageToSelect.setName((rsServicePackageToSelect.getString("name")));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            servicePackagesToSelect.add(servicePackageToSelect);
        }



%>

<div style="text-align: center">

    <p align=right>Username of the user: ${user.username}</p>
    <p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

    <h1>HOME PAGE</h1>
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
                   value="<%=servicePackageToSelect.getServicePackageToSelect_id() %>"><%=servicePackageToSelect.getServicePackageToSelect_id()%>
            <br>
            <%
                }
            %>
        </fieldset>


        <br><br>


    <br>${messageServicePackage}<br>
    <button type="submit">BUY</button>
</form>
</div>
<%
        rsServicePackageToSelect.close();
        stmtServicePackageToSelect.close();
        conn.close();
    }
    catch(Exception e) {
    e.printStackTrace();
    }
%>
    </body>
</html>



