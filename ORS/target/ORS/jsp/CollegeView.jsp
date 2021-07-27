<%@page import="in.co.controller.CollegeCtl"%>
<%@page import="in.co.controller.ORSView"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title>College View Page</title>
</head>
<body>
<%@include file="Header.jsp" %>

<jsp:useBean id="bean" class="in.co.bean.CollegeBean" scope="request"></jsp:useBean>

<center>

<%
if(bean!=null && bean.getId()>0){
%>
<h1>Update College</h1>
<%}else{ %>
<h1>Add college</h1>
<%} %>


  <h2>
  <font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
  </h2>
  
  <h2>
  <font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
  </h2>
  
  
<form action="<%=ORSView.COLLEGE_CTL%>" method ="post">

  <input type="hidden" name="id" value="<%=bean.getId() %>">
  <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy() %>">
  <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy() %>">
  <input type="hidden" name="createDateTime" value="<%=DataUtility.getTimestamp(bean.getCreateDateTime()) %>">
  <input type="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime()) %>">


<table>
 <tr>
     <th align="left">Name<span style="color: red">*</span></th>
     <td><input type="text" name="cName" value="<%=DataUtility.getStringData(bean.getName()) %>" placeholder="Enter College Name"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("c_Name",request)%></font></td>
     
  </tr>
  
  <tr>
     <th align="left">Address<span style="color: red">*</span></th>
     <td><input type="text" name="cAddress" value="<%=DataUtility.getStringData(bean.getAddress()) %>" placeholder="Enter College Address"></td>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("c_Address",request)%></font>
     </td>
  </tr>
  
  <tr>
     <th align="left">State<span style="color: red">*</span></th>
     <td><input type="text" name="cState" value="<%=DataUtility.getStringData(bean.getState()) %>" placeholder="Enter College State"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("c_State",request)%></font>
     </td>
  </tr>
  
  <tr>
     <th align="left">City<span style="color: red">*</span></th>
     <td><input type="text" name="cCity" value="<%=DataUtility.getStringData(bean.getCity()) %>" placeholder="Enter College City"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("c_City",request)%></font>
     </td>
  </tr>
  
  <tr>
     <th align="left">Phone<span style="color: red">*</span></th>
     <td><input type="text" name="cPhone" value="<%=DataUtility.getStringData(bean.getPhoneNo()) %>" placeholder="Enter College Phone No"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("c_Phone",request)%></font>
     </td>
  </tr>
  
  <tr>
     <th></th>
     <td>
     <%
     if(bean.getId()>0){
     %>
     &nbsp;<input type="submit" name="operation" value="Update">
     &nbsp;<input type="submit" name="operation" value="<%=CollegeCtl.OP_CANCEL %>">
     
     <%}else{ %>
     
     <input type="submit" name="operation" value="<%=CollegeCtl.OP_SAVE %>">
    
     &nbsp;<input type="submit" name="operation" value="<%=CollegeCtl.OP_RESET%>">
     <%} %>
     </td>
  </tr>
</table>
</form>
</center>
<%@include file="Footer.jsp" %>
</body>
</html>