<%@page import="in.co.controller.UserCtl"%>
<%@page import="in.co.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="java.util.List"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>User View Page</title>
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
			yearRange:'1980:2001',
			dateFormat: 'dd-mm-yy'
		});
	});
</script>
</head> 
<body>
<%@include file="Header.jsp" %>

<%
List l = (List)request.getAttribute("roll_list");
%>

<jsp:useBean id="bean" class="in.co.bean.UserBean" scope="request"></jsp:useBean>

<center>
    
 <form action="<%=ORSView.USER_CTL %>" method="post" >

<%
if(bean.getId()>0 && bean!=null){
%>
<h1>Update User</h1>
<%}else{%>
<h1>Add User</h1>
<%} %>

    <h2>
    <font color ="red"><%=ServletUtility.getErrorMessage(request) %></font>
    </h2>

    <h2>
    <font color ="green"><%=ServletUtility.getSuccessMessage(request) %></font>
    </h2>
 
     <input type="hidden" name="id" value="<%=bean.getId()%>">
     <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
     <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
     <input type="hidden" name="createDateTime" value="<%=DataUtility.getTimestamp(bean.getCreateDateTime())%>">
     <input type="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

<table>


       <tr>
           <th align="left">First Name<span style="color: red">*</span></th>
           <td><input type="text" name="firstName" value="<%=DataUtility.getStringData(bean.getFirstName()) %>" placeholder="Enter First Name">
           </td>
           <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstName",request) %></font>
           </td>
     </tr>
     
     <tr>
          <th align="left">Last Name<span style="color: red">*</span></th>
          <td><input type="text" name="lastName" value="<%=DataUtility.getStringData(bean.getLastName()) %>" placeholder="Enter Last Name">
          </td>
          <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastName",request) %></font>
          </td>
     </tr>
     
     <tr>
         <th align="left">Login<span style="color: red">*</span></th>
         <td><input type="text" name="login" value="<%=DataUtility.getStringData(bean.getLogin()) %>" placeholder="Enter Emailid">
         </td>
         <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login",request) %></font>
         </td>
     </tr>
     
     <%if(bean.getId()>0 && bean!=null){ %>
     
     <input type="hidden" name="password" value="<%=DataUtility.getStringData(bean.getPassword()) %>">
     <input type="hidden" name="confirmPassword" value="<%=DataUtility.getStringData(bean.getConfirmPassword()) %>">
    
   <%--  <input type="hidden" value="<%=HTMLUtility.getList("roleName",String.valueOf(bean.getRollId()),l)%>">
     --%>
     <%}else{ %>
     <tr>
       <th align="left">Password<span style="color: red">*</span></th>
       <td><input type="password" name="password" value="<%=DataUtility.getStringData(bean.getPassword()) %>" placeholder="Enter Password">
       </td>
       <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password",request) %></font>
       </td>
     </tr>
     
     <tr>
       <th align="left">Confirm Password<span style="color: red">*</span></th>
       <td><input type="password" name="confirmPassword" value="<%=DataUtility.getStringData(bean.getConfirmPassword()) %>" placeholder="Enter Confirm Password">
       </td>
       <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("confirmPassword",request) %></font>
       </td>
     </tr>
     
     <%} %>
     
     <tr>
        <th align="left">Address<span style="color: red">*</span></th>
        <td><input type="text" name ="address" placeholder="Enter Address"
        value="<%= DataUtility.getStringData(bean.getAddress())%>">
        </td>
        <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("Address", request) %></font>        
     </tr>
 
     <tr>
       <th align="left">Mobile<span style="color: red">*</span></th>
       <td><input type="text" name="mobile" value="<%=DataUtility.getStringData(bean.getMobileNo())%>" placeholder="Enter Mobile No">
       </td>
       <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobile",request) %></font>
       </td>
     </tr>
     
     <tr>
       <th align="left">Gender<span style="color: red">*</span></th>
       <td>
       <%
           HashMap map = new HashMap();
         
           map.put("M","Male");
           map.put("F","Female");
           
           String htmlList=HTMLUtility.getList("gender",bean.getGender(),map);         		   	   
       %>
       <%=htmlList%>
        </td>
        <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("gender",request) %></font>
      </td>
      </tr> 
     
     <tr>
       <th align="left">Role<span style="color: red">*</span></th>
       <td>
       <%=HTMLUtility.getList("roleId",String.valueOf(bean.getRollId()),l)%>
       </td>
       <td style="position: fixed;"><font color = "red"><%=ServletUtility.getErrorMessage("roll",request) %></font>
       </td>
     </tr>
      
     <tr>
         <th align ="left">DOB<span style="color: red">*</span></th>
         <td><input type="text" name="dob" id ="dob" readonly="readonly" value="<%=DataUtility.getDateString(bean.getDob()) %>" placeholder="Enter DOB"> 
         </td>
         <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob",request) %></font>
         </td>
      </tr>
      
      <%
      if(bean.getId()>0){
      %>
      
      <tr>
          <th></th>
          <td><input type="submit" name="operation" value="Update">
            
            &nbsp;<input type="submit" name="operation" value="<%=UserCtl.OP_CANCEL%>">
      <%}else{ %>
      <tr>
          <th></th>
          <td><input type="submit" name ="operation" value="<%=UserCtl.OP_SAVE%>">
          &nbsp;<input type="submit" name="operation" value="<%=UserCtl.OP_RESET%>">
          </td>
          </tr>
        <%} %>
        
        
</table> 
 
 </form>   
</center>
<%@include file ="Footer.jsp" %>
</body>
</html>