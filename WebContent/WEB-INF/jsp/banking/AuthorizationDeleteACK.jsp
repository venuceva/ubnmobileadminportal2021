
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<script type="text/javascript" >
function authmainpage(){
	var actype= $('#acttype').val(); 		
	//alert(actype);
	
	if (actype=="REG"){
	 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/authreglist.action';		
	}
	else{
	 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action';
	}
	$("#form1").submit();
	return true;
}
</script>
</head>
<body>
<form name="form1" id="form1" method="post" action="">
			<div id="content" class="span10">
			    <div>
					<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="serviceMgmtAct.action">All Authorizations</a> <span class="divider"> &gt;&gt; </span> </li>
					<li><a href="#"><span id="header-data" class="merchant"> </span> ${responseJSON.formName} Authorization Acknowledgement</a></li>
				</ul>
				</div>
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i> ${responseJSON.formName} Authorization Acknowledge
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>

						<div id="primaryDetails" class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong> Record  Deleted </strong></td> 
									</tr> 
								</table>
							<fieldset>
						</div>
      			    </div>
                </div>
		<div class="form-actions">
			<a  class="btn btn-primary" href="#" onClick="authmainpage()">Next</a>
			<input type="hidden" id="acttype" name="acttype" value="${responseJSON.acttype}" />
		</div>
	</div>  
</form>
</body>
</html>
