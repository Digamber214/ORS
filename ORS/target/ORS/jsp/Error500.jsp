<%@page import="in.co.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page errorPage="true" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title>Error Page</title>
<style>

/*  #error
{
	 padding: 60px;
	 margin-left: 200px;
}
 */ 
</style>

</head>

<body>
<div align="center">

 <form action="<%= ORSView.ERROR_CTL %>" method="GET">
      <img src="<%=ORSView.APP_CONTEXT%>/img/error.jpg" width="170" height="150">


<h1 style="color: red">OOPS!! No internet</h1>
<h2>Try:</h2>
<h3>

  Checking the network cables, modem, and router<br>
  Running Windows Network Diagnostics<br>
  Reconnecting to Wi-Fi<br>
  Problem in Web-Application..!! Try after Some Time <br>
</h3>

<h3><a href="<%=ORSView.WELCOME_CTL %>">click here to go back</a></h3>
</form>
</div>
</body>
</html>