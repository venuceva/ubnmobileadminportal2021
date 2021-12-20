<!DOCTYPE html>
<html>
<head>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@page import="com.ceva.base.common.utils.StringUtil"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />


<link href="${pageContext.request.contextPath}/css/agency-app.css"	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/chosen.css"	rel="stylesheet" type="text/css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>

<!--[if lt IE 9]>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/html5.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <![endif]-->
<title><%=application.getInitParameter("pageTitle") %></title>

<script type="text/javascript">
	function preventBack() {
		window.history.forward();
	}
	setTimeout("preventBack()", 0);
		window.onunload = function() {
		null
	};
	
/* 	window.onbeforeunload = function() {
        return "Are you sure you want to leave";
    } */
    
/*     $(document).keydown(function(e) {
        var elid = $(document.activeElement).hasClass('textInput');
        if (e.keyCode === 8 && !elid) {
            return false;
        };
    }); */
    
    
    $(document).on("keydown", function (e) {
        if (e.which === 8 && !$(e.target).is("input, textarea")) {
            e.preventDefault();
        }
    });
	
	function disableF5(e) { if ((e.which || e.keyCode) == 116) e.preventDefault(); };

	$(document).ready(function(){
	     $(document).on("keydown", disableF5);
	});
	

	
	
</script>
<style type="text/css">
.messages {
	font-weight: bold;
	color: green;
	padding: 2px 8px;
	margin-top: 2px;
}

.errors {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}

label.error {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}

.errmsg {
	color: red;
}

.table12{
    table-layout: fixed;
   width:100%;
}

th, td {
   vertical-align: top;
  padding-left:8px;	
   
    
}

</style>

</head>
<body>

	<%
		String userid = (String) session
				.getAttribute(CevaCommonConstants.MAKER_ID);
		if (StringUtil.isNullOrEmpty(userid)) {
	%>
	<%
		response.sendRedirect("logout.action");
	%>
	<%
		} else {
	%>

	<table class="table12" >
	<tr>
 <td colspan="6" ><tiles:insertAttribute name="header" /></td>
 </tr>
 <tr>
 <td class="row-fluid"><tiles:insertAttribute name="menu" /></td>
 <td colspan="5" class="row-fluid"><tiles:insertAttribute name="body" /></td>
 </tr>
 <tr>
 <td colspan="6"  ><tiles:insertAttribute name="footer" /></td>
 </tr>
 </table>
	
	<%
		}
	%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-1.8.21.custom.min.js"></script>
		<script type="text/javascript"
	src='${pageContext.request.contextPath}/js/jquery.dataTables11.min.js'></script>
<script type="text/javascript"
	src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script>
<script type="text/javascript"
	src='${pageContext.request.contextPath}/js/fnFilterAll.min.js'></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.cookie.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.chosen.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/bootstrap-tooltip.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/bootstrap-popover.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/agency-default.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/modernizr.custom.51611.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/css3-mediaqueries.min.js"></script>



</body>
</html>

