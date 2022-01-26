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
  ServicePackageEntity servicePackage = (ServicePackageEntity) request.getAttribute("servicePackage");

  UserEntity user = null;
  String userUsername = null;
  if(request.getSession().getAttribute("user")!=null){
    user = (UserEntity) request.getSession().getAttribute("user");
    userUsername = user.getUsername();
  }

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
  <h1>ORDER SUMMARY</h1>
  <br>
  <h2>Details of the chosen service package</h2>

  <div style="text-align: center">
    <table class="table">
      <tr>
        <td>Package Selected</td>
        <td>Type of services</td>
        <%
          if(servicePackage.getOptionalProducts()!=null && servicePackage.getOptionalProducts().size()!=0){
        %>
        <td>Optional products</td>
        <%
          }
        %>
        <td>Start date of subscription</td>
        <td>End date of subscription</td>
        <td>Total price to be pre-paid</td>
      </tr>

      <tr>
        <td><%=servicePackage.getPackageSelected().getName()%></td>
        <td><%=servicePackage.getPackageSelected().getServices()%></td>
        <%
          if(servicePackage.getOptionalProducts()!=null && servicePackage.getOptionalProducts().size()!=0){
        %>
        <td><%=servicePackage.getOptionalProducts()%></td>
        <%
          }
        %>
        <td><%=servicePackage.getStartDate()%></td>
        <td><%=servicePackage.getEndDate()%></td>
        <td><%=servicePackage.getValuePackage()%></td>

      </tr>

    </table>
  </div>
  <br><br>
</div>
</div>


<div style="text-align: center">

  <%
    if(userUsername!=null){
  %>
  <div>
    <form class="inner" action="confirmationPage" method="post">
      <button class="buttonGreen" name="result" value="success" type="submit">BUY (billing accepted)</button>
      <button class="buttonRed" name="result" value="fail" type="submit">BUY (billing rejected)</button>
    </form>

  </div>
  <br><br>

    <form action="confirmationPage" method="post">
      <button class="button" name="result" value="random" type="submit">BUY (random billing status)</button>
    </form>


  <%
    }
    else{
  %>
  <form action="login" method="get">
    <button class="button" type="submit">LOGIN/SIGNUP</button>
  </form>
  <%
    }
  %>

</div>
</body>
</html>
