<%@page import="in.co.model.StudentModel"%>
<%@page import="in.co.controller.StudentListCtl"%>
<%@page import="in.co.controller.StudentCtl"%>
<%@page import="in.co.bean.StudentBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.util.ServletUtility"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>StudentList View Page</title>
<script  src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js">
	
</script>	
<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>
 
</head>
<body>
<%@include file="Header.jsp" %>

<jsp:useBean id="bean" class="in.co.bean.StudentBean" scope="request"></jsp:useBean>

<center>
<h1>Student List</h1>

<form action="<%=ORSView.STUDENT_LIST_CTL %>" method="post">

          <%-- <%List li=(List)ServletUtility.getList(request);
          if(li.size()==0){   
        	  %> --%> 
       <h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h2> 
 
     <h2><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h2>
     

<%
 List li1=(List)ServletUtility.getList(request);
  System.out.println("list............"+li1);
if(li1==null ||li1.size()==0){   
	System.out.println("@@@@@@@@@@@@@");	
%>  
<input type="submit" name ="operation" value="<%=StudentListCtl.OP_BACK%>">

<%}else{ %>

<table width="100%">
<tr align="center">
    <td><label>First Name</label>
        <input type="text" name="firstName" value="<%=ServletUtility.getParameter("firstName", request) %>">
        <label>Last Name</label>
        <input type="text" name="lastName" value="<%=ServletUtility.getParameter("lastName", request) %>">
        <label>Email Id</label>
        <input type="text" name="email" value="<%=ServletUtility.getParameter("email", request) %>">
        <input type="submit" name="operation" value="<%=StudentListCtl.OP_SEARCH%>">
        <input type="submit" name="operation" value="<%=StudentListCtl.OP_RESET%>">
    </td>
 </tr>   
</table>


<table border="1" width="100%">

<tr><th><input type="checkbox" id = "select_all" name="ids">Select All</th>
    <th>S.NO</th>
    <th>COLLEGE</th>
    <th>FIRST NAME</th>
    <th>LAST NAME</th>
    <th>DOB</th>
    <th>MOBILE NO</th>
    <th>Address</th>
    <th>EMAIL</th>
    <th>EDIT</th>
 </tr>

 <%
   
    int pageNo = ServletUtility.getPageNo(request);
    int pageSize = ServletUtility.getPageSize(request);
    int index = ((pageNo-1)*pageSize)+1;
 
    List list= ServletUtility.getList(request);
    
    Iterator<StudentBean> it = list.iterator();
    
    //StudentBean bean1;
    while(it.hasNext()){
    	bean = it.next();
    %>
    
    <tr><td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
        <td align="center"><%=index++%></td>
        <td align="center"><%=bean.getCollegeName()%></td>
        <td align="center"><%=bean.getFirstName()%></td>
        <td align="center"><%=bean.getLastName()%></td>
        <td align="center"><%=bean.getDob()%></td>
        <td align="center"><%=bean.getMobileNo()%></td>
        <td align="center"><%=bean.getAddress()%></td>
        <td align="center"><%=bean.getEmail()%></td>
        <td align="center"><a href="StudentCtl?id=<%=bean.getId()%>">EDIT</a></td>
    <%} %>
    </tr>
    
</table>
<%
StudentModel model = new StudentModel();
%>
<table width="100%">

<tr>
    <td align="left"><input type="submit" name ="operation" value="<%=StudentListCtl.OP_PREVIOUS %>"
    <%=(pageNo>1)?"":"disabled" %>></td>
 <td align="center"><input type="submit" name ="operation" value="<%=StudentListCtl.OP_NEW%>"></td>
 <td align="center"><input type="submit" name ="operation" value="<%=StudentListCtl.OP_DELETE%>"></td>
 
 <td align="right"><input type="submit" name ="operation" value="<%=StudentListCtl.OP_NEXT %>"<%=(list.size()<pageSize || model.nextPk()-1==bean.getId())?"disabled":""%>>
   </td>
   </tr> 
</table>

  <input type="hidden" name="pageNo" value="<%=pageNo%>">
  <input type="hidden" name="pageSize" value="<%=pageSize%>">
</form>

</center>
<%}%>
</body>
<BR><BR><BR>
<%@include file="Footer.jsp"%>
</html>