<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashSet"%>
<%@page import="in.co.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.controller.CourseCtl"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title>Course View Page</title>
</head>
<body>
<%@include file="Header.jsp" %>
<jsp:useBean id="bean" class="in.co.bean.CourseBean" scope="request"></jsp:useBean>

<center>
<form action="<%=ORSView.COURSE_CTL%>" method="post">


<%if(bean.getId()>0){ %>
<h1>Update Course</h1>
<%}else{ %>
<h1>Add Course</h1>
<%} %>

<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
</h2>

<h2>
<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
</h2>

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
<input type="hidden" name="createDateTime" value="<%=bean.getCreateDateTime()%>">
<input type="hidden" name="modifiedDateTime" value="<%=bean.getModifiedDateTime()%>">

<table >
 <tr>
     <th align="left">Course<span style="color: red">*</span></th>
     <td><input type="text" name="name" value="<%=DataUtility.getStringData(bean.getcName())%>" placeholder="Enter Course Name"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name1",request) %></font>
     </td>
  </tr>
  
<tr>
     <th align="left">Duration<span style="color: red">*</span></th>
     <td>
     <%
       LinkedHashMap map = new LinkedHashMap();
      
       map.put("1 year","1 year");
       map.put("2 year","2 year");
       map.put("3 year","3 year");
       map.put("4 year","4 year");
       map.put("5 year","5 year");
       
       String htmlList= HTMLUtility.getList("duration",bean.getDuration(),map);
     %><%=htmlList%>
     </td>
      <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("duration1",request) %></font>
     </td>
  </tr>   
     
<tr>
     <th align="left">Description<span style="color: red">*</span></th>
     <td><input type="text" name="desc" value="<%=DataUtility.getStringData(bean.getDescription())%>" placeholder="Enter Course Description">
     </td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("desc1",request) %></font>
     </td>
  </tr>
     
 <tr>
   <th></th>
   <td>
    <%if (bean.getId() >0 && bean!=null) 
    {%> &emsp;
    <input type="submit" value="Update" name="operation">
    <input type="submit" value="<%=CourseCtl.OP_CANCEL %>" name="operation"> 
     <%}else{%>
     <input type="submit" value="<%=CourseCtl.OP_SAVE %>" name="operation">
     <input type="submit" value="Reset" name="operation">
     <%}%>   
   </td>
   </tr>
 
     
</table>
</form>
</center>
<%@include file="Footer.jsp" %>
</body>
</html>