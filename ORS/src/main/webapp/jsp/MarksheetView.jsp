<%@page import="in.co.controller.MarksheetCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.util.HTMLUtility"%>
<%@page import="in.co.util.ServletUtility"%>
<%@page import="in.co.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>Marksheet View Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@include file="Header.jsp" %>

<center>
<jsp:useBean id="bean" class="in.co.bean.MarksheetBean" scope="request"></jsp:useBean>

<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">

<%
List l = (List)request.getAttribute("StudentList");

/* List list= ServletUtility.getList(request); */
%>


 <%
if(bean!=null && bean.getId()>0){
%>

    <h1>Update Marksheet</h1>
 
<%}else{ %>

<h1>Add Marksheet</h1>
<%} %>
                            
            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>
            
             
            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            


<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
<input type="hidden" name="createDateTime" value="<%=DataUtility.getTimestamp(bean.getCreateDateTime())%>">
<input type="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">



<table align="center">





</table>

<table align="center">

<tr>
    <th align="left">RollNo<span style="color: red">*</span></th>
    <td><input type="text" name="rollno" value="<%=DataUtility.getStringData(bean.getRollNo()) %>" placeholder="Enter Roll No">
    </td>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("rollno",request) %></font>
    </td>
 </tr>
 
<tr>
    <th align="left">Name<span style="color: red">*</span></th>
    <td>
    <font color="red"><%=HTMLUtility.getList("studentid",String.valueOf(bean.getStudentId()),l) %></font>
    </td>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("studentid1",request) %></font>
    </td>
        
 </tr>

<tr>
    <th align="left">Physics<span style="color:red">*</span></th>
    <td><input type="text" name="physics" value="<%=(DataUtility.getStringData(bean.getPhysics()).equals("0")?"":DataUtility.getStringData(bean.getPhysics()))%>" 
            placeholder="Enter Physics Marks">
    </td>
  <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("physics1",request) %></font>
    </td>
 </tr>

<tr>
    <th align="left">Chemistry<span style="color: red">*</span></th>
    <td><input type="text" name="chemistry" placeholder="Enter Chemistry Marks"
    value="<%=DataUtility.getStringData(bean.getChemistry()).equals("0")?"":DataUtility.getStringData(bean.getChemistry())%>">
    </td>
   <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("chemistry",request) %></font>
    </td>
 </tr>

<tr>
    <th align="left">Maths<span style ="color: red">*</span></th>
    <td><input type="text" name="math" value="<%=DataUtility.getStringData(bean.getMaths()).equals("0")?"":DataUtility.getStringData(bean.getMaths())%>"
    placeholder="Enter Maths Marks">
    </td>
   <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("math",request) %></font>
    </td>
 </tr>
<br>
<tr>
   <th></th>
   <td>
    <%if (bean.getId() >0 && bean!=null) 
    {
    System.out.println("id is in side if "+bean.getId());
    %> &emsp;
    <input type="submit" value="Update" name="operation">
    <input type="submit" value="<%=MarksheetCtl.OP_CANCEL %>" name="operation">
    
     
     <%
     }else{%>
     <input type="submit" value="<%=MarksheetCtl.OP_SAVE %>" name="operation">
     <input type="submit" value="<%=MarksheetCtl.OP_RESET%>" name="operation">
     <%}%>   
   </td>
   </tr>


    
</table>
</form>
</center>
<%@include file="Footer.jsp" %>
</body>
</html>