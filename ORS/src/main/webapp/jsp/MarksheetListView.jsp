	<%@page import="in.co.model.MarksheetModel"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.controller.MarksheetListCtl"%>
<%@page import="in.co.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>MarksheetList view Page</title>
<script  src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js">	
</script>	
<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>
 

</head>
<body >

<%@include file ="Header.jsp" %>

<jsp:useBean id="bean" class="in.co.bean.MarksheetBean" scope="request"></jsp:useBean>
  <form action="<%=ORSView.MARKSHEET_LIST_CTL %>" method="post">

<center>
<div>  <h1>Marksheet List</h1>
  
  <h2>
   <td><font color ="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
   </h2>
   
  <h2>
   <td><font color ="green"><%=ServletUtility.getSuccessMessage(request)%></font></td>
   </h2>

</div>  
 
<% List l = ServletUtility.getList(request); 
System.out.println("list is--------"+l.size());
if(l==null || l.size()==0){%>
<input type="submit" name="operation" value="<%=MarksheetListCtl.OP_BACK%>">	
<%}else{ %>  
  <table width="100%" cellpadding="2px" cellspacing="-0.5px">
    
      <td align="center"><label>Name :</label> 
      <input type="text" name="name" value="<%=ServletUtility.getParameter("name", request)%>">
      <label>RollNo :</label> 
      <input type="text" name="rollNo" value="<%=ServletUtility.getParameter("rollNo", request)%>">
     <%--  <input type="text" name="physics" value="<%=ServletUtility.getParameter("physics", request) %>"> --%>
      <input type="submit" name="operation" value="<%=MarksheetListCtl.OP_SEARCH %>">
      <input type="submit" name="operation" value="<%=MarksheetListCtl.OP_RESET%>">
      </td>
      
      </table>        
    
    <table border="1" width="100%" align="center" >
      <tr>
      <th><input type="checkbox" id = "select_all" name="ids" >Select All</th>
      <th>S.No.</th>
      <th>Roll No</th>
      <th>Name</th>
      <th>Physics</th>
      <th>Chemistry</th>
      <th>Math</th>
      <th>Edit</th>
      </tr>
   
   <%

   int pageNo= ServletUtility.getPageNo(request);
   int pageSize= ServletUtility.getPageSize(request);
   int index= (pageNo-1)*pageSize+1;
  
   
   /* int page11=10;
   System.out.println("totla page size"+page11); */
   
   List<MarksheetBean> list = ServletUtility.getList(request);
   
   Iterator it = list.iterator();
   
   //MarksheetBean bean1 = new MarksheetBean();

   System.out.println("size list"+list.size());
   while(it.hasNext()){
	   
	   bean= (MarksheetBean)it.next();
	   System.out.println("roll id"+bean.getId());
	%>
	  <tr>
	      <td align="center"><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId() %>" >
	      <td align="center"><%=index++%></td>
	      <td align="center"><%=bean.getRollNo()%></td>
	      <td align="center"><%=bean.getName()%></td>
	      <td align="center"><%=bean.getPhysics()%></td>
	      <td align="center"><%=bean.getChemistry()%></td>
	      <td align="center"><%=bean.getMaths()%></td>
	  <td align="center"><a href="MarksheetCtl?id=<%=bean.getId()%>">Edit</a></td>
	 </tr>  
	   
   <%}%>
    
  </table>
  <%
  MarksheetModel model = new MarksheetModel();
  %>
  <table width=100%>
  
  <tr>
     <td align="left"><input type="submit" name = "operation" value="<%=MarksheetListCtl.OP_PREVIOUS%>"
     <%=(pageNo==1)?"disabled":"" %>>
     </td>
     <td align="center"><input type="submit" name = "operation" value="<%=MarksheetListCtl.OP_NEW%>">
     </td>
     <td align="center"><input type="submit" name = "operation" value="<%=MarksheetListCtl.OP_DELETE%>">
     </td>
     <td align="right"><input type="submit" name = "operation" value="<%=MarksheetListCtl.OP_NEXT%>"
       <%=((list.size()<pageSize || model.nextPk()-1 == bean.getId())?"disabled":"") %>> 
     </td>
     </tr> 
  </table>
  
      <input type="hidden" name="pageNo" value="<%=pageNo%>">
      <input type="hidden" name="pageSize" value="<%=pageSize%>">
  
  </form>
</center>
<%} %>
</body>
<%@include file="Footer.jsp"%>
<br><br><br>
</html>