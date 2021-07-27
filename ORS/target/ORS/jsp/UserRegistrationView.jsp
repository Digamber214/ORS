<%@page import="in.co.controller.UserRegistrationCtl"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.util.ServletUtility"%>
<%@page import="in.co.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>Registration Page</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#dob1").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange:'1980:2001',
			dateFormat: 'dd-mm-yy'
		});
	});
</script>
</head>

<body>
	<jsp:useBean id="bean" class="in.co.bean.UserBean" scope="request"></jsp:useBean>

	<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<center>
			<h1>User Registration</h1>

               
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

			<input type="hidden" name="id"value"<%=bean.getId()%>"> <input
				type="hidden" name="createdBy"value"<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"value"<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDateTime"value"<%=DataUtility.getTimestamp(bean.getCreateDateTime())%>">
			<input type="hidden" name="ModifiedDateTime"value"<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

			<table>
				<tr>
					<th align="left">First Name<span style="color: red">*</span></th>
					<td><input type="text" name="firstName" placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>">
					</td>	
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">Last Name<span style="color: red">*</span></th>
					<td><input type="text" name="lastName"
						placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>">
					</td>	
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">Login<span style="color: red">*</span></th>
					<td><input type="text" name="login"
						placeholder="Enter Email ID"
						value="<%=DataUtility.getStringData(bean.getLogin())%>">
					</td>	
						
				<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">Password<span style="color: red">*</span></th>
					<td><input type="password" name="password" placeholder="Enter Password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>">
					</td>
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">Confirm password<span style="color: red">*</span></th>
					<td><input type="password" name="confirmPassword" placeholder="Enter Confirm Password"
						value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>">
					</td>	
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></font>
					</td>
				</tr>

              <tr>
                  <th align="left">Address<span style="color: red">*</span></th>
                  <td><input type="text" name = "address" placeholder="Enter address" 
                  value="<%=DataUtility.getStringData(bean.getAddress())%>">
                </td>
               <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("address1", request) %></font>   
              </tr>
				<tr>
					<th align="left">Gender<span style="color: red">*</span></th>
					<td>
                        <%
                            HashMap map = new HashMap();
                            map.put("M", "Male");
                            map.put("F", "Female");

                            String htmlList = HTMLUtility.getList("gender", bean.getGender(),
                                    map);
                        %> <%=htmlList%>

                    </td>
          <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font>          
                </td>
               </tr>


				<tr>
					<th align="left">DOB<span style="color: red">*</span></th>
					<td><input type="text" name="dob" id="dob1" placeholder="Enter DOB" readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDob())%>"> 
					</td>	
				<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob2", request)%></font>
					</td>
				</tr>

                <tr>
					<th align="left">Mobile<span style="color: red">*</span></th>
					<td><input type="text" name="mobile"  placeholder="Enter Mobile No" value="<%=DataUtility.getStringData(bean.getMobileNo())%>"> 
					</td>	
						
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobile", request)%></font>
					</td>
				</tr>
				<tr>
					<th></th>
					<td><input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP%>">&nbsp;
					    <input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_RESET%>">
					</td>	
				</tr>
			</table>
		</center>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>