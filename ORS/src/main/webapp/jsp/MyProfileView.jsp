<%@page import="in.co.controller.MyProfileCtl"%>
<%@page import="in.co.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.util.ServletUtility"%>
<%@page import="in.co.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>My Profile Page</title>
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
</script></head>
<body>
<form action="<%=ORSView.MY_PROFILE_CTL %>" method="post">
<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.bean.UserBean" scope ="request"></jsp:useBean>

<center>
<h1>My Profile</h1>

<H2>
    <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</H2>
<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
</h2>

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
<input type="hidden" name="createDateTime" value="<%=DataUtility.getTimestamp(bean.getCreateDateTime())%>">
<input type="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">


<table>
<tr>
    <th align="left">Login<span style="color:red">*</span></th>
    <td><input type ="text" name ="login" value="<%=bean.getLogin()%>" readonly="readonly" >
      </td>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login",request) %></font>
    </td>
 </tr>

<tr>
    <th align="left">First Name<span style="color: red">*</span></th>
    <td><input type ="text" name ="firstName" value="<%=DataUtility.getStringData(bean.getFirstName())%>">
      </td>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstName",request) %></font>
    </td>
</tr>

<tr>
    <th align="left">last Name<span style="color: red">*</span></th>
    <td><input type ="text" name ="lastName" value="<%=DataUtility.getStringData(bean.getLastName())%>">
     </td>   
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastName",request) %></font>
    </td>
</tr>

<tr>
    <th align="left">Gender<span style="color: red">*</span></th>
    <td>
       <%
          HashMap map = new HashMap();
          map.put("M","Male");
          map.put("F","Female");
          
          String htmlList =HTMLUtility.getList("gender",bean.getGender(),map);
       %>
       <%=htmlList %>
    </td>
</tr>

<tr>
    <th align="left">Mobile<span style="color: red">*</span></th>
    <td><input type ="text" name ="mobile" value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
     </td>   
       <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobile",request) %></font>
    </td>
</tr>

<tr>
    <th align="left">DOB<span style="color: red">*</span></th>
	<td><input type="text" name="dob" id="dob" value="<%=DataUtility.getDateString(bean.getDob())%>">
	  </td>	 
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font>
	</td>
</tr>

<tr>
    <th></th>
    <td>
        <input type="submit" name="operation" value="<%=MyProfileCtl.OP_SAVE%>">
          &nbsp;<input type = "submit" name ="operation" value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD %>">
    </td>
 </tr>  
       
</table>
</center>
<%@ include file="Footer.jsp"%>
</form>
</body>
</html>