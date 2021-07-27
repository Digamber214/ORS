<%@page import="in.co.util.ServletUtility"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> Login Page</title>
</head>
<%@ include file="Header.jsp"%>
<body>
	<form action="<%=ORSView.LOGIN_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.bean.UserBean" scope="request"></jsp:useBean>
		<center>
			<h1>Login</h1>
<%
/* String uri= (String)request.getAttribute("URI"); */
%>


             <% String str= (String)request.getAttribute("error11");
             if(str!=null){ 
             %>
             <h2><font color="red"><%=request.getAttribute("error11") %></font></h2>
              <%} %>
             <h2>
             <font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
             </h2>
              <h2>
             <font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
             </h2> 
             
			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
			<input type="hidden" name="CreateDateTime" value="<%=DataUtility.getTimestamp(bean.getCreateDateTime())%>">
			<input type="hidden" name="ModifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

			<table>
				<tr>
					<th align="left">LoginId<span style="color: red">*</span></th>
					<td><input type="text" name="login" value="<%=DataUtility.getStringData(bean.getLogin())%>" placeholder="Enter Emailid"> </td>	
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>

				<tr>
					<th align="left">password<span style="color: red">*</span></th>
					<td><input type="password" name="password" value="<%=DataUtility.getStringData(bean.getPassword())%>" placeholder="Enter Password"> 
				</td>
			<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font>
					</td>
				</tr>

				<tr>
					<th></th>
					<td><input type="submit" name="operation"
						value="<%=LoginCtl.OP_SIGN_IN%>"> &nbsp;
						<input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>">
						&nbsp;</td>
				</tr>

				<tr>
					<th></th>
					<td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget
								my password</b></a></td>
				</tr>
			</table>
		</center>
	
	<%-- <input type="hidden" name="str" value="<%=uri%>"> --%>
	
	</form>
	
	<%@include file="Footer.jsp"%>
</body>
</html>