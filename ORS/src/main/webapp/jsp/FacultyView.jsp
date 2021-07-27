<%@page import="in.co.bean.SubjectBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.controller.FacultyCtl"%>
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
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#doj").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange:'1980:2020',
			dateFormat: 'dd-mm-yy'
		});
	});
</script>
</head>
<body>
<%@include file="Header.jsp"%>
<center>

<jsp:useBean id="bean" class="in.co.bean.FacultyBean" scope="request"></jsp:useBean>

<%if(bean.getId()>0){ %>
<h1>Update Faculty</h1>
<%}else{ %>
<h1>Add Faculty</h1>
<%} %>

<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h2>

<h2>
<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h2>

<%
List l1 = (List)request.getAttribute("collegeList");
List l2 = (List)request.getAttribute("courseList");
List l3 = (List)request.getAttribute("subjectList");
/* System.out.println("in view");
Iterator it = l3.iterator();
SubjectBean bean11=null;
while(it.hasNext()){
	bean11= (SubjectBean)it.next();
	System.out.println(bean11.getId()+" "+bean11.getCourseName()+" "+bean11.getSubjectName());
} */
%>
<form action="<%=ORSView.FACULTY_CTL%>" method="post">

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="created_by" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name="modified_by" value="<%=bean.getModifiedBy()%>">
<input type="hidden" name="createDateTime" value="<%=bean.getCreateDateTime()%>">
<input type="hidden" name="modifiedDateTime" value="<%=bean.getModifiedDateTime()%>">

<table>

<tr>
    <th align="left">College Name<span style="color: red">*</th>
    <td><%=HTMLUtility.getList("collegeid",String.valueOf(bean.getCollegeId()),l1)%></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("collegeName1",request) %></font>
   </td>  
  </tr>

<tr>
    <th align="left">Course Name<span style="color: red">*</th>
    <td><%=HTMLUtility.getList("courseid",String.valueOf(bean.getCourseId()),l2) %></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseName1",request) %></font>
   </td>  
  </tr>
   
<tr>
    <th align="left">Subject Name<span style="color: red">*</th>
    <td><%=HTMLUtility.getList("subjectid",String.valueOf(bean.getSubjectId()),l3) %></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectName1",request) %></font>
   </td>  
  </tr>

<tr>
    <th align="left">First Name<span style="color: red">*</th>
    <td><input type="text" name = "fname" value="<%=DataUtility.getStringData(bean.getFirstName())%>" placeholder="Enter First Name"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("fname1",request) %></font>
   </td>  
  </tr>
  
<tr>
    <th align="left">Last Name<span style="color: red">*</th>
    <td><input type="text" name = "lname" value="<%=DataUtility.getStringData(bean.getLastName())%>" placeholder="Enter Last Name"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lname1",request) %></font>
   </td>  
  </tr>
  
 <tr>
    <th align="left">Gender<span style="color: red">*</th>
    <td> 
    <%
       HashMap map = new HashMap();
      
       map.put("M","male");
       map.put("F","female");
       
       String htmlList= HTMLUtility.getList("gender",bean.getGender(), map);
       
    %><%=htmlList%>
    </td>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("gender1",request) %></font>
  </td>
 </tr>

<tr>
    <th align="left">Login<span style="color: red">*</th>
    <td><input type="text" name = "login" value="<%=DataUtility.getStringData(bean.getLoginId())%>" placeholder="Enter Emailid"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login1",request) %></font>
   </td>  
  </tr>
 
 <tr>
     <th align="left">Address<span style="color: red">*</span></th>
     <td><input type="text" name="address" placeholder="Enter Address"
      value="<%=DataUtility.getStringData(bean.getAddress()) %>">
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("address1", request) %></font></td>  
 </tr>
 <tr>
    <th align="left">Date_Of_Joining<span style="color: red">*</th>
    <td><input type="text" name = "doj" id="doj" readonly="readonly"
    value="<%=DataUtility.getStringData(bean.getDateOfJoining())%>" placeholder="Enter Date Of Joining">
     </td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("doj1",request) %></font>
   </td>  
  </tr>
   
<tr>
    <th align="left">Qualification<span style="color: red">*</th>
    <td><input type="text" name = "qual" value="<%=DataUtility.getStringData(bean.getQualification())%>" placeholder="Enter Qualification"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("qual1",request) %></font>
   </td>  
  </tr>
   
<tr>
    <th align="left">Mobile<span style="color: red">*</th>
    <td><input type="text" name = "mobile" value="<%=DataUtility.getStringData(bean.getMobileNo())%>" placeholder="Enter Mobile No"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobile1",request) %></font>
   </td>  
  </tr>


<%
      if(bean.getId()>0){
      %>
      
      <tr>
          <th></th>
          <td><input type="submit" name="operation" value="Update">
            &nbsp;<input type="submit" name="operation" value="<%=FacultyCtl.OP_CANCEL%>">
            
          
      <%}else{ %>
      <tr>
          <th></th>
          <td><input type="submit" name ="operation" value="<%=FacultyCtl.OP_SAVE%>">
          
          &nbsp;<input type="submit" name="operation" value="Reset">
          </td>
          </tr>
        <%} %>
        
   
</table>
</form>

</body>
</center>
<br><br><br><br><br>
<%@include file="Footer.jsp" %>
</html>