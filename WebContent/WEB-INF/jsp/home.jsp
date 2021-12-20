
<%@page
	import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<script src="<%=ctxstr%>/js/html5shiv.js"></script>
<meta charset="utf-8">
<title><%=application.getInitParameter("pageTitle") %></title>



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
	padding-bottom: 40px;
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
<div id="sortable1"></div>
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
			<div class="col-md-3 col-sm-6" id="gymgr">
				<div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
						<div class="clearfix">
							<div class="info left">
								<h4 class="mt0 text-primary text-bold"><span id="gymcount"></span></h4>
								<h5 class="text-light mb0"></h5>
							</div>
							<div style="float:right;"><img style="width:57px;height:57px;" src="<%=ctxstr%>/images/icons/gym.jpg" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 left"><b>Gym</b></p>
						<span sparkline="" class="right" type="bar" bar-color="#fff" width="1.15em" height="1.15em"><canvas style="display: inline-block; width: 34px; height: 14px; vertical-align: top;" width="34" height="14"></canvas></span>
					</div>
				 </div>
			  </div>

			  <div class="col-md-3 col-sm-6" id="bookcentergr">
				<div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
						<div class="clearfix">
							<div class="info left">
								<h4 class="mt0 text-primary text-bold"><span id="bookcentercount"></span></h4>
								<h5 class="text-light mb0"></h5>
							</div>
							<div style="float:right;"><img style="width:57px;height:57px;" src="<%=ctxstr%>/images/icons/bookcenter.jpg" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 left"><b>Book Center</b></p>
						<span sparkline="" class="right" type="bar" bar-color="#fff" width="1.15em" height="1.15em"><canvas style="display: inline-block; width: 34px; height: 14px; vertical-align: top;" width="34" height="14"></canvas></span>
					</div>
				</div>
			  </div>

			<div class="col-md-3 col-sm-6" id="librarygr">
			   <div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
						<div class="clearfix">
							<div class="info left">
								<h4 class="mt0 text-primary text-bold"><span id="librarycount"></span></h4>
								<h5 class="text-light mb0"></h5>
							</div>
							<div style="float:right;"><img style="width:57px;height:57px;" src="<%=ctxstr%>/images/icons/LibraryNews.jpg" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 left"><b>Library</b></p>
						<span sparkline="" class="right" type="bar" bar-color="#fff" width="1.15em" height="1.15em"><canvas style="display: inline-block; width: 29px; height: 14px; vertical-align: top;" width="29" height="14"></canvas></span>
					</div>
				</div>
			 </div>

			 <div class="col-md-3 col-sm-6" id="cashofficegr">
			    <div class="panel panel-default mb20 mini-box panel-hovered">
				  <div class="panel-body">
					<div class="clearfix">
						<div class="info left">
							<h4 class="mt0 text-primary text-bold"><span id="cashofficecount"></span></h4>
							<h5 class="text-light mb0"></h5>
						</div>
						<div style="float:right;"><img style="width:45px;height:45px;" src="<%=ctxstr%>/images/icons/cashoffice.jpg" /></div>
					</div>
				  </div>
				  <div class="panel-footer clearfix panel-footer-sm panel-footer-white">
					<p class="mt0 mb0 left"><b>Cash Office</b></p>
					<span sparkline="" class="right" type="bar" bar-color="#fff" width="1.15em" height="1.15em"><canvas style="display: inline-block; width: 29px; height: 14px; vertical-align: top;" width="29" height="14"></canvas></span>
				  </div>
			   </div>
			</div>
         </div>

         <div class="row" >

			<div class="col-md-3 col-sm-6" id="seccheckgr">
			   <div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
						<div class="clearfix">
							<div class="info left">
								<h4 class="mt0 text-primary text-bold"><span id="securitycount"></span></h4>
								<h5 class="text-light mb0"></h5>
							</div>
							<!-- <div class="right ion ion-ios-people-outline icon"></div> -->
							<div style="float:right;"><img style="width:57px;height:57px;" src="<%=ctxstr%>/images/icons/Security_CheckIn.jpg" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 left"><b>Security</b></p>
						<span sparkline="" class="right" type="bar" bar-color="#fff" width="1.15em" height="1.15em"><canvas style="display: inline-block; width: 29px; height: 14px; vertical-align: top;" width="29" height="14"></canvas></span>
					</div>
				</div>
			 </div>

			 <div class="col-md-3 col-sm-6" id="attendancegr">
				<div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
						<div class="clearfix">
							<div class="info left">
								<h4 class="mt0 text-primary text-bold"><span id="attendancecount"></span></h4>
								<h5 class="text-light mb0"></h5>
							</div>
							<div style="float:right;"><img style="width:57px;height:57px;" src="<%=ctxstr%>/images/icons/Attendance1.jpg" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 left"><b>Attendance</b></p>
						<span sparkline="" class="right" type="bar" bar-color="#fff" width="1.15em" height="1.15em"><canvas style="display: inline-block; width: 34px; height: 14px; vertical-align: top;" width="34" height="14"></canvas></span>
					</div>
				</div>
			  </div>

			 <div class="col-md-3 col-sm-6" id="cafinternalgr">
				<div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
						<div class="clearfix">
							<div class="info left">
								<h4 class="mt0 text-primary text-bold"><span id="cafteriacount"></span></h4>
								<h5 class="text-light mb0"></h5>
							</div>
							<div style="float:right;"><img style="width:57px;height:57px;" src="<%=ctxstr%>/images/icons/Cafeteria_Internal.jpg" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 left"><b>Cafeteria</b></p>
						<span sparkline="" class="right" type="bar" bar-color="#fff" width="1.15em" height="1.15em"><canvas style="display: inline-block; width: 34px; height: 14px; vertical-align: top;" width="34" height="14"></canvas></span>
					</div>
				 </div>
			  </div>

			 <div class="col-md-3 col-sm-6" id="registergr">
				<div class="panel panel-default mb20 mini-box panel-hovered">
					<div class="panel-body">
						<div class="clearfix">
							<div class="info left">
								<h4 class="mt0 text-primary text-bold"><span id="regcount"></span></h4>
								<h5 class="text-light mb0"></h5>
							</div>
							<div style="float:right;"><img style="width:57px;height:57px;" src="<%=ctxstr%>/images/icons/Registration.jpg" /></div>
						</div>
					</div>
					<div class="panel-footer clearfix panel-footer-sm panel-footer-white">
						<p class="mt0 mb0 left"><b>Registration</b></p>
						<span sparkline="" class="right" type="bar" bar-color="#fff" width="1.15em" height="1.15em"><canvas style="display: inline-block; width: 34px; height: 14px; vertical-align: top;" width="34" height="14"></canvas></span>
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
