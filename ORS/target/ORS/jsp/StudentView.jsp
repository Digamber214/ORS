<%@page import="in.co.controller.StudentCtl"%>
<%@page import="in.co.util.HTMLUtility"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>Student View Page</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#dob").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange:'1980:2020',
			dateFormat: 'dd-mm-yy'
		});
	});
</script>
</head>
<body>
<%@include file ="Header.jsp" %>
<jsp:useBean id="bean" class="in.co.bean.StudentBean" scope="request"></jsp:useBean>
<center>
  

<form action="<%=ORSView.STUDENT_CTL %>" method="post">

<%
List l = (List)request.getAttribute("Collegelist");
%>

<%
System.out.println("id in jsp"+bean.getId());

if(bean!=null && bean.getId()>0){
%>
<h1>Update Student</h1>
<%}else{%>

<h1>Add Student</h1>
<%} %>

<h2>
<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h2>

<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
</h2>


<input type="hidden" name = "id" value="<%=bean.getId()%>">
<input type="hidden" name = "createdBy" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name = "modifiedBy" value="<%=bean.getModifiedBy()%>">
<input type="hidden" name = "createDateTime" value="<%=DataUtility.getTimestamp(bean.getCreateDateTime())%>">
<input type="hidden" name = "modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">




<table >
  
  <tr>
      <th align="left">College<span style="color: red">*</span></th>
      <td><%=HTMLUtility.getList("college",String.valueOf(bean.getCollegeId()),l) %>
      </td>
      <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("college",request) %></font>
      </td>
 </tr>
      
   <tr>
      <th align="left">First Name<span style="color: red">*</span></th>
      <td><input type="text" name = "firstName" value="<%=DataUtility.getStringData(bean.getFirstName()) %>" placeholder="enter First Name">
      </td>
      <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstName",request) %></font>
      </td>
 </tr>
 
 <tr>
      <th align="left">Last Name<span style="color: red">*</span></th>
      <td><input type="text" name = "lastName" value="<%=DataUtility.getStringData(bean.getLastName()) %>" placeholder="enter last Name">
      </td>
      <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastName",request) %></font>
      </td>
 </tr>
      
 <tr>
      <th align="left">DOB<span style="color: red">*</span></th>
      <td><input type="text" name = "dob1" id="dob" readonly="readonly" 
      value="<%=DataUtility.getDateString(bean.getDob()) %>" placeholder="enter DOB">
      </td>
      <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob",request) %></font>
      </td>
 </tr>
 
 <tr>
      <th align="left">Mobile<span style="color: red">*</span></th>
      <td><input type="text" name = "mobile" value="<%=DataUtility.getStringData(bean.getMobileNo()) %>" placeholder="enter Mobile No">
      </td>
      <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobile",request) %></font>
      </td>
 </tr>
 
 <tr>
     <th align="left">Address<span style="color: red">*</span></th>
     <td><input type="text" name="address" placeholder="Enter Address"
      value="<%=DataUtility.getStringData(bean.getAddress()) %>">
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("address", request) %></font>
     </td>   
 </tr>
 
 <tr>
      <th align="left">Email<span style="color: red">*</span></th>
      <td><input type="text" name = "email" value="<%=DataUtility.getStringData(bean.getEmail()) %>" placeholder="enter Emailid">
      </td>
      <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("email",request) %></font>
      </td>
 </tr>
      



<tr>
   <th></th>
   <td>
<%
if(bean.getId()>0 && bean!=null){
%> 
   <input type="submit" name ="operation" value="Update">
 &nbsp;<input type="submit" name ="operation" value="<%=StudentCtl.OP_CANCEL %>">
 
 <%}else{ %>
  <input type="submit" name ="operation" value="<%=StudentCtl.OP_SAVE %>">
  
 &nbsp; <input type="submit" name ="operation" value="<%=StudentCtl.OP_RESET%>">
   
<%} %>
</td>
</tr>
</table>
</form>
</center>
</body>
<%@include file="Footer.jsp"%>
</html>