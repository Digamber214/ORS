<%@page import="in.co.controller.SubjectCtl"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.util.HTMLUtility"%>
<%@page import="in.co.util.ServletUtility"%>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>Subject View Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@include file="Header.jsp" %>

<center>

<jsp:useBean id="bean" class="in.co.bean.SubjectBean" scope="request"></jsp:useBean>

<form action="<%=ORSView.SUBJECT_CTL%>" method="post">

<%
List l = (List)request.getAttribute("courseList");
%>

<%if(bean!=null && bean.getId()>0){ %>
<h1>Update Subject</h1>
<%}else{%>
<h1>Add Subject</h1>
<%}%>

<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
</h2>
<h2>
<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h2>

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
<input type="hidden" name="createDateTime" value="<%=bean.getCreateDateTime()%>">
<input type="hidden" name="modifiedDateTime" value="<%=bean.getModifiedDateTime()%>">



<table>
<tr>
    <th align="left">Course Name<span style="color: red">*</span></th>
    <td><%=HTMLUtility.getList("courseid",String.valueOf(bean.getCourseId()),l) %>
    </td>   
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("coursename1", request) %></font>
    </td>
  </tr>
 
 <tr>
     <th align="left">Subject Name<span style="color: red">*</span></th>
     <td><input type="text" name="subjectname" value="<%=DataUtility.getStringData(bean.getSubjectName())%>" placeholder="Enter Subject Name">
      </td>
      <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectname1", request) %></font>  
    </td>  
  </tr>
  
<tr>
     <th align="left">Description<span style="color: red">*</span></th>
     <td><input type="text" name="desc" value="<%=DataUtility.getStringData(bean.getDescription())%>" placeholder="Enter Subject Description">
      </td>
      <td style="position: fixed;">
      <font color="red"><%=ServletUtility.getErrorMessage("desc1", request) %></font>  
    </td>  
  </tr>
 
<tr>
    <th></th>
    <td>
    
  <%if(bean.getId()>0){
	  
	  System.out.println("id is in side if "+bean.getId());
  %>
  <input type="submit" name="operation" value="Update">
  &nbsp;<input type="submit" name="operation" value="<%=SubjectCtl.OP_CANCEL%>">
  
  <%}else{ %>
  
  <input type="submit" name="operation" value="<%=SubjectCtl.OP_SAVE%>">
  <input type="submit" name="operation" value="Reset">
  
  <%} %>     
  </td>
</tr>
</table>  
</form>
</center>
<%@include file="Footer.jsp"%>
</body>

</html>