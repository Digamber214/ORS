<%@page import="java.text.DecimalFormat"%>
<%@page import="in.co.util.DataUtility"%>
<%@page import="in.co.controller.GetMarksheetCtl"%>
<%@page import="in.co.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Marksheet</title>
</head>
<body>
	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.bean.MarksheetBean" scope="request"></jsp:useBean>

	<center>
		<h1>
			Get Marksheet
		</h1>
		
         <form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">

			<input type="hidden" name="id" value="<%=bean.getId()%>">

          <h2> 
		<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
        </h2>
		<h2>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h2>
		

			<table>
           <tr>
               <th align="left">Roll No<span style="color: red">*</span></th>        
				<td><input type="text" name="rollNo" placeholder="enter roll no"
					value="<%=ServletUtility.getParameter("rollNo",request)%>">&emsp;
                </td>
      <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("rollno1",request)%></font>
      
      </td>          
	   </tr>
	   <tr>	<th></th>	
			<td align="center"><input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_GO%>">
			&nbsp;<input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_RESET%>"></td>
            </td>
				</tr>
				</table>

				<%
		    if(bean.getRollNo()!=null && bean.getRollNo().trim().length()>0 ){
		               System.out.println("roll no is"+bean.getRollNo());
		    	%>
            <table border="1">           
				<table border="1" width="35%">
					<tr>

						<td align="center"><h2>Rays Technologies</h2></td>
					</tr>
				</table>

				<table border="1" width="35%">
					<tr>
						<td align="center">Name</td>
						<th><%=DataUtility.getStringData(bean.getName()) %></th>

						<td align="center">Roll No</td>
						<th><%=DataUtility.getStringData(bean.getRollNo())%>
					</tr>
					<tr>
						<td align="center">Status</td>
						<th>Regular</th>

						<td align="center">Course</td>
						<th>BE</th>
					</tr>
				</table>
				<%
		    int phy= DataUtility.getInt(DataUtility.getStringData(bean.getPhysics()));
		    int che=DataUtility.getInt(DataUtility.getStringData(bean.getChemistry()));
		    int mat=DataUtility.getInt(DataUtility.getStringData(bean.getMaths()));
		    int tot=(phy+che+mat);
		    System.out.print("per "+tot);
		    float per= tot/3;
		      
		    System.out.print("per "+per);
		    
		    
		    %>
				<table border="1" width ="35%">
					<tr>
						<th align="center" style="width: 25%">Subject</th>
						<th align="center" style="width: 25%">Marks Obtained</th>
						<th align="center" style="width: 25%">Maximum Marks</th>
						<th align="center" style="width: 25%">Minimum Marks</th>
						<th align="center" style="width: 35%">Grade</th>
					</tr>
					<tr>
						<td align="center">Physics</td>
						<td align="center"><%=phy%>
						 <%
 	if (phy < 33) {
 %><span style="color: red">*</span> <%
 	}
 %></td>
						<td align="center">100</td>
						<td align="center">33</td>
						<td align="center">
							<%
							if (phy > 90 && phy <= 100) {
						%>A+ <%
							} else if (phy > 80 && phy <= 90) {
						%>A <%
							} else if (phy > 70 && phy <= 80) {
						%>B+ <%
							} else if (phy > 70 && phy <= 80) {
						%>B <%
							} else if (phy > 60 && phy <= 70) {
						%>C+ <%
							} else if (phy > 50 && phy <= 60) {
						%>C <%
							} else if (phy >= 33 && phy <= 50) {
						%>D <%
							} else if (phy >= 0 && phy < 33) {
						%><span style="color: red;">Fail</span> <%
 	}
 %>
						</td>

					</tr>

					<tr>
						<td align="center">Chemistry</td>
						<td align="center"><%=che%>
						<%
if(che<33){
%><span style="color: red">*</span>
						<%} %>
						</td>
						
						<td align="center">100</td>
						<td align="center">33</td>
						<td align="center">
							<%if(che>90 && che<=100){
           %>A+<%
           } else if(che>80 && che <=90){				
        	   %>A<%
        	   } else if(che>70 && che <=80){
        	   %>B+<%
        	   }else if(che>60 && che <=70){
        	   %>B<%
        	   }else if(che>50 && che <=60){
        	   %>C+<%
        	   }else if(che>40 && che <=50){
        	   %>C<%
        	   }else if(che>=33 && che <=40){
        	   %>D<%
        	   }else if(che>0 && che < 33){
        	   %><span color="red">Fail</span> <%}
%>
						</td>
					</tr>
					<tr>
						<td align="center">Math</td>
						<td align="center"><%=mat%>
<%if(mat<33){
%><span style="color: red">*</span>
<%} %>
</td>
						<td align="center">100</td>
						<td align="center">33</td>
						<td align="center">
							
              <%if(mat>90 && mat<=100){ 
             %>A+<%
             }else if(mat>80 && mat<=90){
             %>A<%
             }else if(mat>70 && mat<=80){
             %>B+<%
             }else if(mat>60 && mat<=70){
             %>B<%
             }else if(mat>50 && mat<=60){
             %>C+<%
             }else if(mat>40 && mat<=50){
             %>C<%
             }else if(mat>=33 && mat<=40){
             %>D<%
             }else if(mat>0 && mat<33){
             %><span style="color: red">Fail</span><% 	 
            	 }%>
						</td>
					</tr>
				</table>
				
		  <table border="1" width="35%">
		  <tr>
		      <th>Total</th>
		      <th>Percentage(%)</th>
		      <th>Division</th>
		      <th>Result</th>
		   </tr>
		   <tr>
		       <th><%=tot%>
		       <%
		       if(tot<99 ||phy<33||che<33||mat<33){
		       %><span style="color: red">*</span>
		       <%} %>
		       </th>
		       
		      <th><%=per%>%</th>
		      <th>
		      <%
		      if(per>=60 && per<100){
		      %>1<sup>st</sup>
		      <%} else if(per>=40 && per<60){
		      %>2<sup>nd</sup>
		      <%}else if(per>0 && per<40){ 
		      %>3<sup>rd</sup>
		      <%} %> 
		      </th>
		      
		      <th>
		      <%
		      if(phy>=33 && che>=33 && mat>=33)
		      {%><span style="color: green">Pass</span>
		      <%}else{ %>
		      <span style="color: red">Fail</span>
		      <%} %>
		      </th>
		      </tr>    
		  </table>		

			<%} %>	
			
	</table>		
			</center>
		
</body>
 <BR><BR><BR><BR><br><br> 

<%@include file="Footer.jsp"%>
</html>