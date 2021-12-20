<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%
	String ctxstr = request.getContextPath();
%>
<title><%=application.getInitParameter("pageTitle") %></title>

<style type="text/css">
.errors {
	background-color: #FFCCCC;
	border: 0px solid #CC0000;
	width: 400px;
	margin-bottom: 8px;
}

.errors li {
	list-style: none;
}
 
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

#nav {
	float: left;
	width: 170px;
	color: #ffffff;
	border-top: 1px solid #999;
	border-right: 1px solid #999;
	border-left: 1px solid #999;
	list-style: none;
}

#nav li a {
	display: block;
	padding: 10px 15px;
	background: #AC4A02;
	border-top: 1px solid #eee;
	border-bottom: 1px solid #999;
	text-decoration: none;
	color: #ffffff;
	list-style: none;
}

#nav li a:hover,#nav li a.active {
	background: #FC8023;
	color: #fff;
}

#nav li ul {
	display: none;
	list-style: none;
}

#nav li ul li a {
	padding: 5px 5px;
	background: #D35B03;
	border-bottom: 1px dotted #ccc;
	list-style: none;
}
</style>
<script type="text/javascript">
function redirectLogin() {
	document.form1.action = "<%=ctxstr%>/cevaappl.action";
	document.form1.submit();
}
</script>
</head>
<body onload="redirectLogin()">
	<form class="form-horizontal" name="form1" id="form1" action=""
		method="post">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12 center login-header">
					<h2>Mobile Banking</h2>
				</div>
				<!--/span-->
			</div>
			<!--/row-->

			<div class="row-fluid">
				<div class="well span5 center login-box">
					<div class="alert alert-info"></div>

					<div class="input-prepend" title="Username">
						<span class="add-on"><i class="icon-user"></i></span>&nbsp;&nbsp;&nbsp;
						<img src="<%=ctxstr%>/images/ajax-loaders/ajax-loader-4.gif" />
						&nbsp;&nbsp;&nbsp;Redirecting to login page... Please Wait..
					</div>
				</div>
				<!--/span-->
			</div>
			<!--/row-->
		</div>
		<!--/fluid-row-->
	</form>
</body>
</html>
