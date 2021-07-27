<%@page import="in.co.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page errorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<div align="center">
<h1><font color="red">Oops! Something went wrong</font></h1>
<font style="size: 30px ;">
Requested Resource not found<br>
Please check the URL entered by you
</font>


</div>


<div align="center">
<h3><a href="<%=ORSView.WELCOME_CTL %>">click here to go back</a></h3>
</div>

</body>
</html>