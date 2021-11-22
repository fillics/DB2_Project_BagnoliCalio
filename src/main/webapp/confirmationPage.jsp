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
          <td>Type of services</td>
          <td>Optional products</td>
          <td>Start date of subscription</td>
          <td>End date of subscription</td>
          <td>Total price to be pre-paid</td>
        </tr>

        <tr>
          <td><%=servicePackage.getPackageSelected().getName()%></td>
          <%
            for (ServiceEntity service: servicePackage.getPackageSelected().getServices()) {
          %>
          <td><%=service.getTypeOfService()%></td>
          <%
            }
          %>
          <%
            if(servicePackage.getOptionalProducts()!=null){
              for (OptionalProductEntity optProduct: servicePackage.getOptionalProducts()) {
          %>
          <td><%=optProduct.getName()%></td>
          <%
              }
            }
          %>
          <td><%=servicePackage.getStartDate()%></td>
          <td><%=servicePackage.getEndDate()%></td>
          <td><%=servicePackage.getTotalValuePackage()%></td>

        </tr>

      </table>
    </div>
  </div>
    <br><br>
    <button style="height:150px;width:200px" type="submit">BUY</button>
</form>
</div>


</body>
</html>
