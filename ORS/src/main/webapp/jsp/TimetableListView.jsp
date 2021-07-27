<%@page import="java.util.HashMap"%>
<%@page import="in.co.model.TimetableModel"%>
<%@page import="in.co.controller.TimetableListCtl"%>
<%@page import="in.co.util.ServletUtility"%>
<%@page import="in.co.bean.TimetableBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>TimetableList View Page</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>Login Page</title>
<script  src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js">
	
</script>	
<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>

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
			dateFormat: 'dd-mm-yy'
				
						
			
		});
	});
</script>
</head>
<body>
<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class= "in.co.bean.TimetableBean" scope="request"></jsp:useBean>

<form action="<%=ORSView.TIMETABLE_LIST_CTL%>" method="post">

<center>
        
     <h1>Timetable List</h1>    
<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h2>

<h2>
<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h2>



<%
List clist = (List)request.getAttribute("courseList");
List slist = (List)request.getAttribute("subjectList");
%>


<%-- //List li = ServletUtility.getList(request);
System.out.println("list............"+list);
/* if(list.size()!=0 || list!=null){ */
	if(list==null || list.size()==0){
	System.out.println("if1 of list    ***********");
%>
 <input type="submit" name="back" value="<%=TimetableListCtl.OP_BACK%>"> 
<%} %>	 --%>
<%-- <%}if(list.size()!=0){  System.out.println("if22222222 of list"); %> --%>

 <%
List list1 = ServletUtility.getList(request);
System.out.println("list size====="+list1.size());
if(list1==null || list1.size()==0 ){ // System.out.println("if22222222 of list");
%>
<input type="submit" name="operation" value="<%=TimetableListCtl.OP_BACK%>">
<%}else{ %> 



<table width="100%" >
<tr align="center">
    <td><label>Course Name</label>
    <%=HTMLUtility.getList("courseid",String.valueOf(bean.getCourseId()),clist)%></td>
   </tr>
 <tr align="center">
   <td>
   <label>Subject Name</label>
    <%=HTMLUtility.getList("subjectid",String.valueOf(bean.getSubjectId()),slist)%></td>
   </tr>
 <tr align="center">
     <td><label>Exam Time</label>
     <%
        HashMap map1 = new HashMap();
        map1.put("8 to 10 AM","8 to 10 AM");
        map1.put("12 to 2 PM","12 to 2 PM");
        map1.put("3 to 5 PM","3 to 5 PM");
        String st = HTMLUtility.getList("examtime",(bean.getExamTime()),map1);
        %>
        <%=st%></td>
   </tr>
 <tr align="center">
     <td><label>Exam Date</label>
     <input type="text" name ="examdate" id="examdate" readonly="readonly"
        value="<%=DataUtility.getDateString(bean.getExamDate())%>"></td>
   </tr>
   <tr>
   <td><input type="submit" name ="operation" value="<%=TimetableListCtl.OP_SEARCH%>">     
   &nbsp;<input type="submit" name ="operation" value="<%=TimetableListCtl.OP_RESET%>"></td>
 </tr>          
</table>


<table width="100%" border="1">
<tr>
    <th><input type="checkbox" id="select_all" name="ids">Select All</th>
    <th>SNO</th>
    <th>COURSE NAME</th>
    <th>SUBJECT NAME</th>
    <th>SEMESTER</th>
    <th>EXAM DATE</th>
    <th>EXAM TIME</th>
    <th>EDIT</th>
 </tr>
 <tr>   
 
 <%
 int pageNo= ServletUtility.getPageNo(request);
 int pageSize=ServletUtility.getPageSize(request);
 int index= ((pageNo-1)*pageSize)+1;
 
 //List list = ServletUtility.getList(request);
 Iterator it = list1.iterator();
/*  TimetableBean bean1=null; */
 
 while(it.hasNext()){
	 bean= (TimetableBean)it.next();
%>
    <td align="center"><input type="checkbox" class="checkbox" name ="ids" value="<%=bean.getId() %>"></td>
    <td align="center"><%=index++%></td>
    <td align="center"><%=bean.getCourseName()%></td>
    <td align="center"><%=bean.getSubjectName()%></td>
    <td align="center"><%=bean.getSemester()%></td>
    <td align="center"><%=bean.getExamDate()%></td>
    <td align="center"><%=bean.getExamTime()%></td>
    <td align="center"><a href="TimetableCtl?id=<%=bean.getId()%>">Edit</a></td>
</tr>    
<%} %>

<% System.out.println("list size"+list1.size()); 
System.out.println("page size"+pageSize);
%>
</table>
<%
TimetableModel model = new TimetableModel();
%>
<table width="100%">
<tr>
   
    <td align="left"><input type="submit" name ="operation" value="<%=TimetableListCtl.OP_PREVIOUS %>"
    <%=(pageNo==1)?"disabled":"" %>></td>
 <td align="center" ><input type="submit" name ="operation" value="<%=TimetableListCtl.OP_NEW%>">
 </td><td align="center"><input type="submit" name ="operation" value="<%=TimetableListCtl.OP_DELETE%>">
 </td>
 <td align="right"><input type="submit" name ="operation" value="<%=TimetableListCtl.OP_NEXT %>"<%=(list1.size()<pageSize || model.nextPk()-1 == bean.getId())?"disabled":"" %> >
   </td>
</tr>
</table>
</center>
<input type ="hidden" name="pageNo" value="<%=pageNo%>">
<input type ="hidden" name="pageSize" value="<%=pageSize%>">
 
</form>
<%} %>

 
 <%@include file ="Footer.jsp"%>
</body>
<!-- <br><br><br><br><br> -->
</html>