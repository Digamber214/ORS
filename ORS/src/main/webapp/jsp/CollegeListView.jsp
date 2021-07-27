<%@page import="in.co.model.CollegeModel"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.bean.CollegeBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.controller.CollegeCtl"%>
<%@page import="in.co.controller.CollegeListCtl"%>
<%@page import="in.co.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title>College List Page</title>
<script  src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js">
	
</script>	
<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>
 
</head>
<body>
<jsp:useBean id="bean" class="in.co.bean.CollegeBean" scope="request"></jsp:useBean>
<%@include file="Header.jsp" %>
<center>
  <h1>College List</h1>

  
  
  <form action="<%=ORSView.COLLEGE_LIST_CTL %>" method="post">
  
  <h2>
  <font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
  </h2>

  <h2>
  <font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
  </h2>
 <% List l = ServletUtility.getList(request); 
if(l==null || l.size()==0){%>
<input type="submit" name="operation" value="<%=CollegeListCtl.OP_BACK %>">	
<%}else{ %> 
  
  <table width="100%" > 
   <tr >
   <td align ="center">
   <lable>Name</lable>
   <input type="text" name ="cName" value="<%=ServletUtility.getParameter("cName", request) %>">
   &nbsp;<label>city</label>
   <input type="text" name ="cCity" value=""<%=ServletUtility.getParameter("cCity", request) %>">
   <input type="submit" name ="operation" value="<%=CollegeListCtl.OP_SEARCH%>">
   <input type="submit" name="operation" value="<%=CollegeListCtl.OP_RESET%>">
 </td>
 </tr>
  </table>
  
  <table width="100%" border="1">
  
  <tr><th><input type="checkbox"  id = "select_all" name="ids" >Select All</th>
       <th>S.NO</th>
       <th>NAME</th>
       <th>ADDRESS</th>
       <th>CITY</th>
        <th>STATE</th>
       <th>PHONE NO</th>
       <th>EDIT</th>
   </tr>
   
   
   <%
   int pageNo = ServletUtility.getPageNo(request);
   int pageSize= ServletUtility.getPageSize(request);
   int index = ((pageNo-1)*pageSize)+1;
   
   //int nextlist= DataUtility.getInt(request.getAttribute("nextList1").toString());
   //System.out.println("next list "+nextlist);
   // Another way to disable next button
   
   List list = ServletUtility.getList(request);
   
   
   Iterator<CollegeBean> it = list.iterator();
   
   while(it.hasNext()){
	   bean = it.next();
	%>   
	  <tr>
	      <td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>">
	      <td align ="center"><%=index++%></td>
	      <td align ="center"><%=bean.getName()%></td>
	      <td align ="center"><%=bean.getAddress()%></td>
	      <td align ="center"><%=bean.getState()%></td>
	      <td align ="center"><%=bean.getCity()%></td>
	      <td align ="center"><%=bean.getPhoneNo()%></td>
	      <td align ="center"><a href ="CollegeCtl?id=<%=bean.getId() %>">Edit</a></td>
	      
	   </tr>   
   <%} %>
       
  </table>
  <%
  CollegeModel model = new CollegeModel();
  %>
  <table width="100%">
  <tr>
     <td align="left"><input type="submit" name = "operation" value="<%=CollegeListCtl.OP_PREVIOUS %>"
     <%=(pageNo>1)?"":"disabled"%>>
     </td>
     <td align="center"><input type="submit" name = "operation" value="<%=CollegeListCtl.OP_DELETE %>">
     <td align="center"><input type="submit" name = "operation" value="<%=CollegeListCtl.OP_NEW %>">
     <td align="right"><input type="submit" name = "operation" value="<%=CollegeListCtl.OP_NEXT%>"
      <%=(list.size()<pageSize || model.nextPK()-1 == bean.getId())?"disabled":""%>>
     
     <%-- <%=(nextlist!=0)?"":"disabled"%>> --%>
     </td>
    </tr>
    
  </table>
  
  <input type="hidden" name = "pageNo" value="<%=pageNo%>">
  <input type="hidden" name = "pageSize" value="<%=pageSize%>">
  
  </form>
  <%} %>
</center>
<%@include file ="Footer.jsp"%>
</body>
<BR><BR><BR><BR>
</html>