<%@page import="in.co.controller.ForgetPasswordCtl"%>
<%@page import="in.co.util.ServletUtility"%>
<%@page import="in.co.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>Forget Password Page</title>
</head>
<body>
<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method ="post">

<%@include file="Header.jsp" %>

<jsp:useBean id="bean" class="in.co.bean.UserBean" scope="request" ></jsp:useBean>

<center>
 <h1>Forget Password</h1>

<h3>Submit your email address and we'll send you password.</h3>

<h2>
<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h2>

<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h2>

<input type="hidden" name ="id" value="<%=bean.getId()%>">

<table>
<tr>
    <th align="left">Email Id<span style="color: red">*</span></th>                
 <td><input type= "text" name="login" placeholder="Enter EmailId"
 value="<%=ServletUtility.getParameter("login", request) %>">
 </td>

 
 &emsp;
 <td><input type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_GO%>">
 <td><input type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_RESET%>">
 <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login",request)%></font></td> 
 
 </tr>
 </table>
</center>
</form>
<%@include file ="Footer.jsp" %>
</body>
</html>