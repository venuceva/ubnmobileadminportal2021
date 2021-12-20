
<%@page
	import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<script src="<%=ctxstr%>/js/html5shiv.js"></script>
<meta charset="utf-8">
<title>NBK</title>



<link rel="stylesheet" type="text/css" media="screen"
	href="<%=ctxstr%>/css/jquery-ui-1.8.21.custom.css" />

<link href="<%=ctxstr%>/css/agency-app.css"
	rel="stylesheet" />
<link href="<%=ctxstr%>/css/core.css" rel="stylesheet" />

<link href="<%=ctxstr%>/css/metro/bootstrap.min.css" rel="stylesheet" />
<link href="<%=ctxstr%>/css/metro/main.min.css" rel="stylesheet" />


<link rel="shortcut icon" href="<%=ctxstr%>/images/<%=appName %>/favicon.ico" />


<style type="text/css">
body {
	padding-bottom: 20px;
}

.sidebar-nav {
	padding: 3px 0;
}

#nav {
	float: left;
	min-width: 200px;
	/*color:#ffffff;*/
	/* border-top: 1px solid #999;
    border-right: 1px solid #999;
    border-left: 1px solid #999;*/
	list-style: none;
}

#nav li a {
	/*display: block;*/
	/* padding: 10px 15px;*/
	/*background: #AC4A02;*/
	/* border-top: 1px solid #eee;
    border-bottom: 1px solid #999;*/
	text-decoration: none;
	/*color: #ffffff;*/
	list-style: none;
}

#nav li a:hover,#nav li a.active {
	/*background: #FC8023;*/
	/* color: #fff;*/

}

#nav li ul {
	display: none;
	list-style: none;
}

#nav li ul li a {
	/*background: #D35B03;*/
	list-style: none;
}
.amtclass {
	font-weight : bold;
}


.td-bold{
	font-size: 0.8em; font-family:Tahoma;
	font-weight : bold;
}
.row{
     margin-left: 0px !important;
     margin-right: 0px !important;
}
</style>


<script type="text/javascript">

$(document).ready(function(){

	$('#gymcount').text('${responseJSON.gymcount}'+' KES');
	$('#bookcentercount').text('${responseJSON.bookcentercount}'+' KES');
	$('#librarycount').text('${responseJSON.librarycount}'+' KES');
	$('#cashofficecount').text('${responseJSON.cashofficecount}'+' KES');
	$('#cafteriacount').text('Count :'+'${responseJSON.cafteriacount}');
	$('#securitycount').text('Count : '+'${responseJSON.securitycount}');
	$('#attendancecount').text('Count : '+'${responseJSON.attendancecount}');
	$('#regcount').text('Count : '+'${responseJSON.regcount}');
});

$(function(){
	$('#gymgr,#bookcentergr,#librarygr,#cashofficegr,#cafinternalgr,#seccheckgr,#attendancegr,#registergr').click(function(){
		var id=$(this).attr("id");
		$('#type').val(id);

		//alert(id);

		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/graphProcessorAct.action';
		$("#form1").submit();
	});

	$('#ao').click(function(){
		var id=$(this).attr("id");
		$('#type').val(id);

		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/graphProcessorAct.action';
		$("#form1").submit();
	});
});

</script>


<body>
<form method="post" name="form1" id="form1">
	<div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
				<li><a href="home.action">Home</a> <span class="divider">&gt;&gt;</span> </li>
				<li><a href="#">Dashboard</a></li>
			</ul>
		</div>
		<span class="container">
			<h6 class="flash-colour"><span id="groupannoumcement"></span></h6>
			<h6 class="flash-colour"><span id="userannoumcement"></span></h6>
		</span>

		<input type="hidden" name="type" id="type"/>

		<br/>
		

				

  <div class="row-fluid sortable">
    <div class="box span12" id="groupInfo" style="margin-top: -44px;">
	  <div class="box-header well" data-original-title>Live Transactions
	 
				<div class="box-icon">
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
				</div>
				
	   </div>
	<div class="box-content">
	  <div class="row">

		<div class="row" >
			<div class="col-md-4 col-sm-6" id="gymgr">
				<div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
							<div class="info center">
							<div style="float:center;"><img style="width:70px;height:75px;" src="<%=ctxstr%>/images/icons/android.png" /></div>
							</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 center"><b>Android</b></p>
						
					</div>
				 </div>
			  </div>

			  <div class="col-md-4 col-sm-6" id="bookcentergr">
				<div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
					<div class="info center">
							<div style="float:center;"><img style="width:70px;height:75px;" src="<%=ctxstr%>/images/icons/ussd.jpg" /></div>
					</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 center"><b>USSD</b></p>
						
					</div>
				</div>
			  </div>

			<div class="col-md-4 col-sm-6" id="librarygr">
			   <div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
						
						<div class="info center">
							
							<div style="float:center;"><img style="width:70px;height:75px;" src="<%=ctxstr%>/images/icons/windows.png" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 center"><b>Windows</b></p>
						
					</div>
				</div>
			 </div>

</div>
	
	
	<div class="row" >
	
			<div class="col-md-4 col-sm-6" id="seccheckgr">
			   <div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
						<div class="info center">
							<div style="float:center;"><img style="width:70px;height:75px;" src="<%=ctxstr%>/images/icons/www.jpg" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 center"><b>Web</b></p>
						
					</div>
				</div>
			 </div>

		<%-- 	 <div class="col-md-4 col-sm-6" id="attendancegr">
				<div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
							<div class="info center">
							<div style="float:center;"><img style="width:70px;height:75px;" src="<%=ctxstr%>/images/icons/m-pesa.png" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 center"><b>M-Pesa</b></p>
						
					</div>
				</div>
			  </div> --%>
			  
			   <div class="col-md-4 col-sm-6" id="attendancegr">
				<div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
							<div class="info center">
							<div style="float:center;"><img style="width:120px;height:75px;" src="<%=ctxstr%>/images/icons/fraud2.jpg" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 center"><b>Fraud Alerts</b></p>
						
					</div>
				</div>
			  </div>

			 <div class="col-md-4 col-sm-6" id="cafinternalgr">
				<div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
							<div class="info center">
							<div style="float:center;"><img style="width:70px;height:75px;" src="<%=ctxstr%>/images/icons/Picture3.png" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 center"><b>All</b></p>
						
					</div>
				 </div>
			  </div>

			
         </div>
      </div>
	</div>
  </div>
</div>
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script>
</body>
</html>
