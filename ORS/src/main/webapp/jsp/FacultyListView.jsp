<%@page import="in.co.model.FacultyModel"%>
<%@page import="in.co.bean.CollegeBean"%>
<%@page import="in.co.bean.CourseBean"%>
<%@page import="in.co.controller.FacultyListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.bean.FacultyBean"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.util.DataValidator"%>
<%@page import="in.co.util.HTMLUtility"%>
<%@page import="in.co.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>Faculty list Page</title>
<script  src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js">
	
</script>	
<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>
 
</head>
<body>
<%@include file="Header.jsp"%>
<center>
<h1>Faculty list</h1>
<jsp:useBean id="bean" class="in.co.bean.FacultyBean" scope="request"></jsp:useBean>

<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h2>

<h2>
<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h2>

<%-- <%
List college= (List)request.getAttribute("collegeList");
List subject=(List)request.getAttribute("subjectList");

Iterator it1 = college.iterator();
CollegeBean bean11;
while(it1.hasNext()){
	bean11= (CollegeBean)it1.next();
	
	System.out.println(bean11.getId()+" "+bean11.getCity()+" ");
}
%>
 --%>
<form action="<%=ORSView.FACULTY_LIST_CTL%>" method="post">
<%
List li = ServletUtility.getList(request);

if(li==null || li.size()==0){
%>
<input type="submit" name ="operation" value="<%=FacultyListCtl.OP_BACK%>">	
<%} else { %>


<table width="100%">
<tr align="center">
    <td><label>FirstName</label>
    <input type="text" name = "fname" value="<%=ServletUtility.getParameter("fname", request)%>">
   &nbsp;<label>LastName</label> 
   <input type="text" name = "lname" value="<%=ServletUtility.getParameter("lname", request)%>">
  &nbsp;<label>LoginId</label>
  <input type="text" name = "login" value="<%=ServletUtility.getParameter("login", request)%>">
  &nbsp;
  <input type="submit" name="operation" value="<%=FacultyListCtl.OP_SEARCH%>">
   &nbsp;
  <input type="submit" name="operation" value="<%=FacultyListCtl.OP_RESET%>">
  
  </td>
 </tr>  
</table>

<table width="100%" border="1">
<tr>
    <th><input type="checkbox" id ="select_all" name="ids">Select All</th>
    <th>SNO</th>
    <th>FIRST_NAME</th>
    <th>LAST_NAME</th>
    <th>LOGIN_ID</th>
    <th>Address</th>
    <th>DATE_OF_JOINING</th>
    <th>QUALIFICATION</th>
    <th>MOBILE_NO</th>
    <th>COLLEGE NAME</th>
    <th>COURSE NAME</th>
    <th>SUBJECT NAME</th>
    <th>EDIT</th>
 </tr>
 <tr>
 <%
 int pageNo  = ServletUtility.getPageNo(request);
 int pageSize= ServletUtility.getPageSize(request);
 int index   = ((pageNo-1)*pageSize)+1;
 
 List list = ServletUtility.getList(request);
 
 Iterator it= list.iterator();
  
 while(it.hasNext()){
	  bean= (FacultyBean)it.next();
	 System.out.println("view in"+bean.getCollegeName());
 
 %> 
    <td><input type="checkbox" class="checkbox" name ="ids" value="<%=bean.getId()%>"></td>
    <td align="center"><%=index++%></td>
    <td align="center"><%=bean.getFirstName()%></td> 
    <td align="center"><%=bean.getLastName()%></td>
    <td align="center"><%=bean.getLoginId()%></td>
    <td align="center"><%=bean.getAddress()%></td>
    <td align="center"><%=bean.getDateOfJoining()%></td>
    <td align="center"><%=bean.getQualification()%></td>
    <td align="center"><%=bean.getMobileNo()%></td>
    <td align="center"><%=bean.getCollegeName()%></td>
    <td align="center"><%=bean.getCourseName() %></td>
    <td align="center"><%=bean.getSubjectName()%></td>
    <td align="center"><a href="FacultyCtl?id=<%=bean.getId()%>">Edit</a></td>
 </tr>
 <%} %>
 
</table>
<%
FacultyModel model = new FacultyModel();
%>
<table width="100%">
<tr>
    <td align="left"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
 <td align="center"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEW%>"></td>
 <td align="center"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_DELETE%>"></td>
 <td align="right"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEXT%>"<%=((list.size()<pageSize || model.nextPk()-1==bean.getId())?"disabled":"")%>>
  </td>
</tr>
</table>

  <input type="hidden" name = "pageNo" value="<%=pageNo%>">
  <input type="hidden" name = "pageSize" value="<%=pageSize%>">
</form>
</center>
<%} %>
<%@include file="Footer.jsp"%>
</body>
<br><br><br>
</html>