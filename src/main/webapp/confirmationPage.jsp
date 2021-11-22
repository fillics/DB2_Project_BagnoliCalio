<%@ page import="it.polimi.db2project.entities.ServicePackageEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="it.polimi.db2project.entities.ServiceEntity" %>
<%@ page import="it.polimi.db2project.entities.OptionalProductEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Telco Website</title>
</head>
<body>

<%
  ServicePackageEntity servicePackage = (ServicePackageEntity)
          request.getAttribute("servicePackage");
%>

<p align=right>Username of the user: ${user.username}</p>
<p align=right><a href="${pageContext.request.contextPath}/logout">Logout</a></p>
<form action="confirmationPage" method="post">

  <div style="text-align: center">
    <h1>ORDER SUMMARY</h1>
    <br>
    <h2>Details of the chosen service package</h2>


    <div style="text-align: center">
      <table style="border:2px solid black;margin-left:auto;margin-right:auto;" >
        <tr>
          <td>Package Selected</td>
        </tr>

        <tr>
          <td><%=servicePackage.getPackageSelected().getName()%></td>
        </tr>

        <br><br>

        <tr>
          <td>Type of services</td>
        </tr>
        <%
            for (ServiceEntity service: servicePackage.getPackageSelected().getServices()) {
        %>
        <tr>
          <td><%=service.getTypeOfService()%></td>
        </tr>
        <%
          }
        %>

        <br><br>

        <tr>
          <td>Optional products</td>
        </tr>
        <%
          for (OptionalProductEntity optProduct: servicePackage.getOptionalProducts()) {
        %>
        <tr>
          <td><%=optProduct.getName()%></td>
        </tr>
        <%
          }
        %>

        <br><br>

        <tr>
          <td>Start date of subscription</td>
        </tr>
        <tr>
          <td><%=servicePackage.getStartDate()%></td>
        </tr>

        <br><br>

        <tr>
          <td>End date of subscription</td>
        </tr>
        <tr>
          <td><%=servicePackage.getEndDate()%></td>
        </tr>

        <br><br>

        <tr>
          <td>Total price to be pre-paid</td>
        </tr>
        <tr>
          <td><%=servicePackage.getTotalValuePackage()%></td>
        </tr>

    <br><br>

    <button style="height:150px;width:200px" type="submit">BUY</button>
</form>
</div>


</body>
</html>
