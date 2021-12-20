<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <html>
<head> 
<link href="${pageContext.request.contextPath}/css/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css" media="screen"  />
 <link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet" type="text/css" media="screen"  />  
<link href="${pageContext.request.contextPath}/css/agency-app.css" rel="stylesheet" media="screen" />  
<style>

.errores {

color: #D8000C;
font-family: 'Sansita One';
font-style: normal;
font-size: 18px;
font-weight: 400;

}

.screenexit {
padding: 10px;
position: relative;
}

.msg {
color: #D8000C;
font-family: 'Sansita One';
font-style: normal;
font-size: 15px;
font-weight: 400;
}

.msg1 {
color: green;
font-family: 'Sansita One';
font-weight: bold;
font-style: normal;
font-size: 17px;
font-weight: 400;
}

label.error {
	font-family: 'Sansita One';
	font-weight: bold;
	color: #D8000C;
	padding: 2px 8px;
	margin-top: 2px;
}

.errmsg {
	color: #D8000C;
	
}

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

</style>
</head>
<body> 
	<!-- <div  style="position: absolute;top: 90%;width:15% " > -->
	<footer>
		<p class="pull-left" style="margin-right: 50px; margin-top: -5px;">&copy; <a href="http://cevaltd.com" target="_blank">CEVA</a> 2017, Version 1.1</p>
		<p class="pull-right"><a href="http://cevaltd.com"><img width="80" height="50" src="${pageContext.request.contextPath}/images/logo_ceva.png" alt="CEVA"></img></a></p>
	
	
	</footer>
	 
	 <footer>
		<p class="pull-left"> <a href="http://cevaltd.com" target="_blank"> </a></p>
		<p class="pull-right" id="title-pow" style="margin-right: 33px; margin-top: -5px;"><a href="http://cevaltd.com">Powered</a></p>
	</footer>  
	<!-- </div>  -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.21.custom.min.js"></script> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.min.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.chosen.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-tooltip.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-popover.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/agency-default.js"></script>

<script type="text/javascript">
$(document).ready(function(){
$('#cssmenu > ul > li:has(ul)').addClass("has-sub");
	$('#cssmenu > ul > li > a').click(function() {

		var checkElement = $(this).next();

		$('#cssmenu li').removeClass('active');
		$(this).closest('li').addClass('active');	

		if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
			$(this).closest('li').removeClass('active');
			checkElement.slideUp('normal');
		}

		if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
			$('#cssmenu ul ul:visible').slideUp('normal');
			checkElement.slideDown('normal');
		}

		if (checkElement.is('ul')) {
			return false;
		} else {
			return true;	
		}
	}); 
}); 
</script> 
</body>
</html>
