<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.controller.TimetableCtl"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.util.HTMLUtility"%>
<%@page import="in.co.controller.ORSView"%>
<%@page import="in.co.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>Timetable View Page</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
function DisableSunday(date) {
	 
	  var day = date.getDay();
	 // If day == 0 then it is Sunday
	 if (day == 0) {
	 
	 return [false] ; 
	 
	 } else { 
	 
	 return [true] ;
	 }
	  
	}
	$(function() {
		$("#examdate").datepicker({
			beforeShowDay: DisableSunday,
			changeMonth : true,
			changeYear : true,
			yearRange:'1980:2020',
			dateFormat: 'dd-mm-yy',
			minDate: 0          //It will disable previous dates
			
		});
	});
</script>
</head>
<body>
<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.bean.TimetableBean" scope="request"></jsp:useBean>

<center>

<%if(bean.getId()>0){ %>
<h1>Update Timetable</h1>
<%}else{ %>
<h1>Add Timetable</h1>
<%} %>

<h2>
<font color ="red"><%=ServletUtility.getErrorMessage(request) %></font>
</h2>

<h2>
<font color ="green"><%=ServletUtility.getSuccessMessage(request) %></font>
</h2>

<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">

<%
List clist= (List)request.getAttribute("courseList");
List slist= (List)request.getAttribute("subjectList");
%>

<input type="hidden" name ="id" value="<%=bean.getId()%>">
<input type="hidden" name ="createdBy" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name ="modifiedBy" value="<%=bean.getModifiedBy()%>">
<input type="hidden" name ="createDateTime" value="<%=bean.getCreateDateTime()%>">
<input type="hidden" name ="modifiedDateTime" value="<%=bean.getModifiedDateTime()%>">

<table>
<tr>
    <th align="left">Course Name<span style="color: red">*</span></th>
    <td><%=HTMLUtility.getList("courseid",String.valueOf(bean.getCourseId()), clist)%></td>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseName1", request) %></font>
   </td>
</tr>

<tr>
    <th align="left">Subject Name<span style="color: red">*</span></th>
    <td><%=HTMLUtility.getList("subjectid",String.valueOf(bean.getSubjectId()),slist)%>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectName1", request) %></font>
   </td>
</tr>

<tr>
    <th align="left">Semester<span style="color: red">*</span></th>
  <td>
   <%
      //LinkedHashMap map = new LinkedHashMap();
     
      LinkedHashMap map = new LinkedHashMap();
   //map.put("","-------------");
    map.put("1st","1st");
    map.put("2nd","2nd");
    map.put("3rd","3rd");
    map.put("4th","4th");
    map.put("5th","5th");
    map.put("6th","6th");
    map.put("7th","7th");
    map.put("8th","8ht");
    String htmllist = HTMLUtility.getList("sem",bean.getSemester(), map);
    %>
    <%=htmllist%>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("sem1", request) %></font>
   </td>
</tr>
  
<tr>
    <th align="left">Exam Time<span style="color: red">*</span></th>
    <td>
    <%
      HashMap map1 = new HashMap();
    map1.put("8 to 10 AM","8 to 10 AM");
    map1.put("12 to 2 PM","12 to 2 PM");
    map1.put("3 to 5 PM","3 to 5 PM");
    
    String htmllist1 = HTMLUtility.getList("examtime",bean.getExamTime(), map1);
    %>
    <%=htmllist1%>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("examtime1", request) %></font>
   </td>
</tr>

<tr>
    <th align="left">Exam date<span style="color: red">*</span></th>
    <td><input type="text" name ="examdate" id ="examdate" readonly="readonly"
    value="<%=DataUtility.getDateString(bean.getExamDate())%>" placeholder="Enter Exam Date">
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("examdate1", request) %></font>
   </td>
</tr>

<tr>
    <th></th>
<td>
<%
if(bean.getId()>0){
%>
    <input type="submit" name = "operation" value="Update">	
    &nbsp;<input type="submit" name = "operation" value="<%=TimetableCtl.OP_CANCEL%>">
	<%}else{ %>
	<input type="submit" name = "operation" value="<%=TimetableCtl.OP_SAVE%>">
	<input type="submit" name = "operation" value="Reset">
	
	<%} %>
</td>
</tr>   
  
</table> 
</form>
</center>
<%@include file="Footer.jsp"%>
</body>
</html>