<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/css/loginstyle.css" rel="stylesheet" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=application.getInitParameter("pageTitle") %></title>
<%String ctxstr = request.getContextPath(); %>
<style type="text/css">
html, body {
  font-size: 62.5%;
  height: 100%;
  overflow: hidden;
}
.demoerror {
 overflow: hidden;
font-size: 20px;
  color:#00AEEF;
 

}

p.serif {
    font-family: "Times New Roman", Times, serif;
    color:white;
    font-size: 40px;
}
p.sansserif {
    font-family: "Times New Roman", Times, serif;
    font-size: 20px;
    color:white
}
a {
    color: lightgreen;
    text-decoration: none;
}

a:visited {
    color: lightblue;
}

a:hover {
    color: white;
}

</style>
</head>
<body>
	
	
	<br/><br/>
	<div class="demoerror" >
	<img src="../images/logo-main.png" width="200" height="80" 
						alt="logo">
						<br/><br/><br/><br/><br/>
	<div><center><strong><p class="serif">Your session has expired. </p></strong></center></div>
	<div><center><strong><p class="sansserif"><a href="../cevaappl.action" >Click Here </a>   to log in again</p></strong></center></div>
</div>
	
</body>
</html>