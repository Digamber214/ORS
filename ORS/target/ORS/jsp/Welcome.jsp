<%@page import="in.co.controller.ORSView"%>

<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" />
<title>Welcome Page</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> Welcome Page</title>
</head>
<body>
<%@ include file="Header.jsp" %>

<form action="<%=ORSView.WELCOME_CTL%>" method="post">

<h1 align="Center">
 <font size="10px" color="red">Welcome to ORS</font>
      </h1>
      
            <!-- user value obtained from ? -->
      <%
      UserBean userbean1 =(UserBean)session.getAttribute("user");
        if(userbean1!=null){
        	if(userbean1.getRollId()==RoleBean.STUDENT){
       %>
       
       <h2 align="center">
       <a href="<%=ORSView.GET_MARKSHEET_CTL %>">click here to see your Marksheet </a>
       </h2>
       
       <%
        	}
        }
      
        %>

</form>
</body>
<%@ include file="Footer.jsp"%> 

</html>