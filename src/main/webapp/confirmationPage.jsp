<%@ page import="it.polimi.db2project.entities.ServicePackageEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="it.polimi.db2project.entities.ServiceEntity" %>
<%@ page import="it.polimi.db2project.entities.OptionalProductEntity" %>
<%@ page import="it.polimi.db2project.entities.UserEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Telco Website</title>
</head>

<style><%@include file="css/style.css"%></style>


<body>

<%
  Boolean loggedIn = false;
  ServicePackageEntity servicePackage = (ServicePackageEntity)
  request.getAttribute("servicePackage");

  UserEntity user = (UserEntity) request.getSession().getAttribute("user");
  String userUsername = null;
  try {
    userUsername = user.getUsername();
    loggedIn=true;
  }catch (Exception e){
    e.printStackTrace();
  }
  if(!loggedIn){
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
  <h1>ORDER SUMMARY</h1>
  <br>
  <h2>Details of the chosen service package</h2>

  <div style="text-align: center">
    <table class="table">
      <tr>
        <td>Package Selected</td>
        <td>Type of services</td>
        <%
          if(servicePackage.getOptionalProducts()!=null){
            for (OptionalProductEntity optProduct: servicePackage.getOptionalProducts()) {
        %>
        <td>Optional products</td>
        <%
            }
          }
        %>
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
  <br><br>
</div>
</div>


<div style="text-align: center">

  <%
    if(loggedIn){
  %>
  <form action="confirmationPage" method="post">
    <button class="button" value=<%=loggedIn%>type="submit">BUY</button>
  </form>
  <%
    }
    else{
  %>
  <form action="login" method="post">
    <button class="button" value=<%=loggedIn%>type="submit">LOGIN/SIGNUP</button>
  </form>
  <%
    }
  %>

</div>
</body>
</html>
