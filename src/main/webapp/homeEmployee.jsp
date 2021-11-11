<%@ page import="java.util.List" %>
<%@ page import="it.polimi.db2project.entities.OptionalProductEntity" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.*" %>
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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url="jdbc:mysql://localhost:3306/dbtelco";
        String username="admin";
        String password="admin";
        String query="select * from optionalproduct";

        ResultSet rs = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            conn= DriverManager.getConnection(url,username,password);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        while(rs.next()) {
            OptionalProductEntity optionalProduct = new OptionalProductEntity();
            try {
                optionalProduct.setOptionalProduct_id(rs.getLong("optionalProduct_id"));
                optionalProduct.setName(rs.getString("name"));
                optionalProduct.setMonthlyFee(rs.getFloat("monthlyFee"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            optionalProducts.add(optionalProduct);
        }
%>


<div style="text-align: center">

    <p align=right>Username Employee: ${user.username}</p>
    <p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

    <h1>EMPLOYEE PAGE</h1>
    <br><br>
    <h3>Creating Service Package</h3>
    <form action="login" method="post">

        <label for="name">Name Service Package:</label>
        <input name="name" size="30" />
        <br><br>

        <label for="optionalProducts">Choose an optional product:</label>

        <select name="optionalProducts" id="optionalProducts">
            <%
                for (OptionalProductEntity optProd: optionalProducts) {
            %>
            <option value="<%=optProd.getName() %>"><%=optProd.getName() %></option>
            <%
                }
            %>
        </select>

        <br>${messageLogin}
        <br><br>
        <button type="submit">CREATE</button>
    </form>



    <br><br>


    <h3>Creating Optional Product</h3>
    <form action="optionalProduct" method="post">
        <%--@declare id="name"--%><%--@declare id="monthlyfee"--%>
        <label for="name">Name Optional Product:</label>
        <input name="name" size="30" required/>

        <br><br>

        <label for="monthlyFee">Monthly Fee:</label>
        <input id="monthlyFee" type="number" name="monthlyFee" step="1" min="0" required/>

        <br><br>

        <br>${message}
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
            rs.close();
            stmt.close();
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