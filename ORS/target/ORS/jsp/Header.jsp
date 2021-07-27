<%@page import="in.co.util.ServletUtility"%>
<%@page import="java.util.Enumeration"%>
<%@page import="in.co.controller.LoginCtl"%>
<%@page import="in.co.controller.ORSView" %>
<%@page import="in.co.bean.RoleBean" %>
<%@page import="in.co.bean.UserBean" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

<%
UserBean userBean = (UserBean)session.getAttribute("user");
  

boolean userLogged = userBean != null;

String welcomeMsg="Hi";

if(userLogged){
	String role = (String)session.getAttribute("role");
	
	welcomeMsg= welcomeMsg+"  "+userBean.getFirstName()+" "+"("+role+")";
	
}
else{
	welcomeMsg = welcomeMsg+" , "+"Guest";
}
%>

<table  width="100%" >
<tr>
        <td ><a href="<%=ORSView.WELCOME_CTL%>">Welcome</b></a> |
            <%
            if (userLogged) {
        %>  <a href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</b></a>
              
            <%
                } else {
            %> <a href="<%=ORSView.LOGIN_CTL%>">Login</a> <%
     }
 %></td>
        <td rowspan="2">
            
                <img align="right" src="<%=ORSView.APP_CONTEXT%>/img/sun.jpg"  width="180"
                    height="40">
            
        </td>

    </tr>
    <tr>
        <td >
            <h3>
                <%=welcomeMsg%></h3>
        </td>
    </tr>
     
	<%
        if (userLogged) {
    %>

    <tr>
        <td colspan="2" >
        
        
        <%
           if (userBean.getRollId() == RoleBean.ADMIN) 
            {
         %> 
        <a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> | 
        <a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> |
        <a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> |
        <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet MeritList</b></a> |
        
            <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a>  | <a
            href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a>  | 
            <a href="<%=ORSView.USER_CTL%>">Add User</b></a>  | <a
            href="<%=ORSView.USER_LIST_CTL%>">User List</b></a>  | <a
            href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a>  | <a
            href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a>  | <a
            href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a>  | <a
            href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a>  | <a
            href="<%=ORSView.ROLE_CTL%>">Add Role</b></a>  | <a
            href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a>  |  <a
            href="<%=ORSView.COURSE_CTL%>">Add Course</b></a>  | <a
            href="<%=ORSView.COURSE_LIST_CTL%>">Course List</b></a>  |<a
            href="<%=ORSView.SUBJECT_CTL%>">Add Subject</b></a>  |<a
            href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a>  | <a
            href="<%=ORSView.FACULTY_CTL%>">Add Faculty</b></a>  | <a
            href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List</b></a> | <a
            href="<%=ORSView.TIMETABLE_CTL%>">Add Timetable</b></a>  | <a
            href="<%=ORSView.TIMETABLE_LIST_CTL%>">Timetable List</b></a>  |
        <a href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank">Java Doc</b></a>  
    
      <%
         }
      %>

      <%
      if(userBean.getRollId() == RoleBean.STUDENT){
    	  System.out.println("roll name  "+userBean.getRollId());
       %>

    
        <a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> | 
        <a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> |
        
         <a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> |
        <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet MeritList</b></a> |
        
      
       <a href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a>  |
      
       
    <%
      }
    %>

     <%
      if(userBean.getRollId() == RoleBean.FACULTY){
    	  System.out.println("roll name  "+userBean.getRollId());
       %>

      <a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> |
        <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet MeritList</b></a> |
        <a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> | 
        <a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> |
        
      <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> | 
            <a href="<%=ORSView.USER_CTL%>">Add User</b></a> | <a
            href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> | <a
            href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> | <a
            href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | <a
            href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | <a
            href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> | <a
            href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> | <a
            href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> |<a
            href="<%=ORSView.COURSE_CTL%>">Add Course</b></a> | |<a
            href="<%=ORSView.COURSE_LIST_CTL%>">Course List</b></a> |<a
            href="<%=ORSView.SUBJECT_CTL%>">Add Subject</b></a> |<a
            href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a> |<a
            href="<%=ORSView.FACULTY_CTL%>">Add Faculty</b></a> |<a
            href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List</b></a>|<a
            href="<%=ORSView.TIMETABLE_CTL%>">Add Timetable</b></a> |<a
            href="<%=ORSView.TIMETABLE_LIST_CTL%>">Timetable List</b></a> |
        
       
    <%
      }
    %>
         
      <%
      if(userBean.getRollId() == RoleBean.COLLEGE){
    	  System.out.println("roll name  "+userBean.getRollId());
       %>

     <a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> |
        <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet MeritList</b></a> |
        
      <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a>  |  
      <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> | 
            <a
            href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | <a
            href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> | 
            <a
            href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List</b></a>|<a
            href="<%=ORSView.TIMETABLE_LIST_CTL%>">Timetable List</b></a> |
        <a
            href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a>
       
    <%
      }
    %>

 
  </td>    
        
    </tr>
    <%
        }
    %>
            
 </table>
<hr>
 </body>
</html>