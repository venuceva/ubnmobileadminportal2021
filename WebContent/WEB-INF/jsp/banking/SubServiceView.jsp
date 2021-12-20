
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>IMPERIAL</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

	 
<script type="text/javascript" >
  
function createSubService(){

	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/serviceMgmtAct.action';
	$("#form1").submit();
	return true;
}


</script>
 

</head>

<body>
	<form name="form1" id="form1" method="post" action="">
	 
		<div id="content" class="span10">
            			<!-- content starts -->
			    <div>
						<ul class="breadcrumb">
						  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
						  <li> <a href="#">Fee Management</a> <span class="divider"> &gt;&gt; </span></li>
						  <li><a href="#">View Sub Service</a></li>
						</ul>
				</div>
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
							<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>View Sub Service
								<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>

							<div id="primaryDetails" class="box-content">
								<fieldset>
									<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable " >
										<tr class="even">
											<td ><strong><label for="Service ID">Service Code</label></strong></td>
											<td> ${responseJSON.serviceCode}
											</td>
										</tr>
										<tr class="odd">
											<td ><strong><label for="Service Name">Service Name</label></strong></td>
											<td> ${responseJSON.serviceName}
											</td>

										</tr>
										<tr class="even">
										<td ><strong><label for="Sub Service Code">Sub Service Code</label></strong></td>
											<td > ${responseJSON.subServiceCode}
											</td>
										</tr>
										<tr class="odd">
											<td><strong><label for="Sub Service Name">Sub Service Name</label></strong></td>
											<td> ${responseJSON.subServiceName}
											</td>
										</tr>
										<tr class="odd">
											<td ><strong><label for="Sub Service Code">Created By</label></strong></td>
											<td > ${responseJSON.makerId}
											</td>
										</tr>
										<tr class="odd">
											<td><strong><label for="Sub Service Name">Created Date</label></strong></td>
											<td> ${responseJSON.makerDate}
											</td>
										</tr>
									</table>
								</fieldset>
							</div>
					</div>
				</div> 
		<div class="form-actions">
			<a  class="btn btn-primary " href="#" onClick="createSubService()">Next</a>
		</div> 
	</div><!--/#content.span10--> 
</form>
</body>
</html>
