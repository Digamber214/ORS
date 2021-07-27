<%@page import="in.co.controller.RoleCtl"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>Role Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@include file="Header.jsp" %>

<form action="<%=ORSView.ROLE_CTL%>" method="post">

<jsp:useBean id="bean" class="in.co.bean.RoleBean" scope="request"></jsp:useBean>

<center>

<%if(bean.getId()>0){ %>
<h1>Update role</h1>
<%}else{ %>
<h1>Add role</h1>
<%} %>

<h2>
<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
</h2>

<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
</h2>

 <input type="hidden" name="id" value="<%=bean.getId()%>">
 <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy() %>">
 <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy() %>">
 <input type="hidden" name="createDateTime" value ="<%=DataUtility.getTimestamp(bean.getCreateDateTime()) %>">
 <input type="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime()) %>">

<table >

<tr>
    <th align="left">Name<span style="color: red">*</span></th>
    <td><input type="text" name="name" value="<%=DataUtility.getStringData(bean.getName()) %>" placeholder="Enter Role Name">
    </td>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name1", request) %></font>
   </td>
  </tr>  

<tr>
    <th align="left">Description<span style="color: red">*</span></th>
    <td><input type="text" name="desc" value="<%=DataUtility.getStringData(bean.getDescription()) %>" placeholder="Enter Role Description">
    </td>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("desc1", request) %></font>
   </td>
  </tr>  

<tr>
    <th></th>
    <%if(bean.getId()>0){ %>
    
    <td><input type="submit" name = "operation" value="Update">
    &nbsp;<input type="submit" name ="operation" value="<%=RoleCtl.OP_CANCEL%>">
  </td>
  <%}else{ %>
  <td><input type="submit" name = "operation" value="<%=RoleCtl.OP_SAVE%>">
    &nbsp;<input type="submit" name ="operation" value="<%=RoleCtl.OP_RESET%>">
  </td>
  <%} %>
 </tr>   
</table>
</center>
<%@include file="Footer.jsp" %>
</form>

</body>
</html>