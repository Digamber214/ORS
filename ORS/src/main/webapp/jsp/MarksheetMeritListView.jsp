<%@page import="in.co.controller.MarksheetMeritListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>MarksheetMeritList Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@ include file="Header.jsp" %>

<center>
<h1>MarksheetMeritList</h1>
<h2>
    <font color="red"><%=ServletUtility.getErrorMessage(request) %></font></td>
</h2> 

<form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL %>" method="post">

<table border="1px" width="100%">

<tr>
   
    <th>SNO</th>
    <th>Roll No</th>
    <th>Name</th>
    <th>Physics</th>
    <th>Chemistry</th>
    <th>Maths</th>
    <th>Total</th>
    <th>Percentage(%)</th>
</tr>


<%

int pageNo=(int)ServletUtility.getPageNo(request);

int pageSize=(int)ServletUtility.getPageSize(request);
int index= ((pageNo-1)*pageSize)+1; 

//System.out.println(pageNo+pageSize);
/* List<MarksheetBean> list = (List<MarksheetBean>)request.getAttribute("list"); */
List list = ServletUtility.getList(request);
Iterator it = list.iterator();
while(it.hasNext()){
	
	MarksheetBean bean = new MarksheetBean();
	bean = (MarksheetBean)it.next();
	
	long id = bean.getId();
	String roll = bean.getRollNo();
	String name= bean.getName();
	int phy = bean.getPhysics();
	int che = bean.getChemistry();
	int mat = bean.getMaths();
%>	
<tr>
    <td align="center"><%=index++%></td> 
    <td align="center"><%=roll%></td>
    <td align="center"><%=name%></td>
    <td align="center"><%=phy%></td>
    <td align="center"><%=che%></td>
    <td align="center"><%=mat%></td>
    <td align="center"><%=(phy+che+mat)%></td>
    <td align="center"><%=(phy+che+mat)/3%>%</td> 
</tr>
<%
}
%>
</table>
<table>
<tr>
    <td><input type="submit" name="operation" value="<%=MarksheetMeritListCtl.OP_BACK%>">
     </td>
   </tr>  

</table>

 <input type ="hidden" name ="pageNo" value="<%=pageNo%>">
<input type ="hidden" name ="pageSize" value="<%=pageSize%>"> 
</form>
</center>
<%@include file ="Footer.jsp" %>
</body>
</html>