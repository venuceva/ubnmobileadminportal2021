
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 
<script type="text/javascript" > 
function getGenerateLimitScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/creditAuthManagentAct.action';
	$("#form1").submit();
	return true;
}

function createStoreLimit(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/StoreCreditApproveAct.action';
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
						<li><a href="#">Float Management</a> <span class="divider"> &gt;&gt;  </span></li>
						<li><a href="#">Credit Management</a> <span class="divider"> &gt;&gt;  </span></li>
						<li><a href="#">Authorization Confirmation</a></li>
					</ul>
				</div>
				<div class="row-fluid sortable"><!--/span-->
        
					<div class="box span12">
						 <div class="box-header well" data-original-title>Store Details
							<div class="box-icon"> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
								<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
							</div>
						</div>
                 
						<div class="box-content"  id="primaryDetails">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
										class="table table-striped table-bordered bootstrap-datatable " >
									<tr class="even">
										<td width="20%"><strong><label for="Store Name">Store Name</label></strong></td>
										<td width="30%">  ${responseJSON.storeName}
											<input name="storeName" type="hidden" class="field" id="storeName" value="${responseJSON.storeName}" />
										</td>
										<td width="20%"><strong><label for="Store ID">Store ID</label></strong></td>
										<td width="30%"> ${responseJSON.storeId}
											<input name="storeId"  type="hidden" id="storeId" class="field"  value="${responseJSON.storeId}" > 
										</td>	
									</tr>
									<tr class="odd">
										<td  ><strong><label for="Merchant Name">Merchant Name</label></strong></td>
										<td  > ${responseJSON.merchantName}
											<input name="merchantName" type="hidden"  id="merchantName" class="field"  value="${responseJSON.merchantName}" >
										</td>
										<td  ><strong><label for="Merchant ID">Merchant ID</label></strong></td>
										<td  > ${responseJSON.merchantID}
											<input name="merchantID" type="hidden" id="merchantID" class="field" value="${responseJSON.merchantID}"  >
										</td>
									</tr>
									<tr class="even">
										<td ><strong><label for="Location">Location</label></strong></td>
										<td >
											${responseJSON.locationName}
											<input name="location" type="hidden" id="location" class="field" value="${responseJSON.locationName}"  >
										</td>
										<td ><strong><label for="KRA PIN">KRA PIN</label></strong></td>
										<td > ${responseJSON.KRA_PIN}
											<input name="kraPin" type="hidden" class="field" id="kraPin" value="${responseJSON.KRA_PIN}"  />
										</td>	
									</tr>    
								</table>
							</fieldset>
						</div>
					</div>
				</div>
				 <div class="row-fluid sortable"><!--/span-->
        
					<div class="box span12">
						<div class="box-header well" data-original-title>Credit Details
							<div class="box-icon"> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
								<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
							</div>
						</div>
						 
					<div class="box-content"  id="limitDetails">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable " >
								<tr class="even">
									<td width="20%"><strong><label for="Store Limit">Store Credit Amount</label></strong></td>
									<td width="30%">  kshs ${responseJSON.storeCreditAmt} /=
										<input name="storeCreditAmt" type="hidden" id="storeCreditAmt" class="field" value="${responseJSON.storeCreditAmt}" >
									</td>
									<td width="20%"><strong><label for="Store Limit">Store Credit Status</label></strong></td>
									<td width="30%"> 
										${responseJSON.storeCreditStatus} 
										<input name="storeCreditStatus" type="hidden" id="storeCreditStatus" class="field" value="${responseJSON.storeCreditStatus}" >
									</td>
								</tr>
								<tr class="odd">
									<td><strong><label for="Store Limit">Requested By</label></strong></td>
									<td> 
										  ${responseJSON.requestedBy} 
										<input name="requestedBy" type="hidden" id="requestedBy" class="field" value="${responseJSON.requestedBy}" >
									</td>
									<td><strong><label for="Store Limit">Requested Date</label></strong></td>
									<td> 
										  ${responseJSON.requestedDate} 
										<input name="requestedDate" type="hidden" id="requestedDate" class="field" value="${responseJSON.requestedDate}" >
										
									</td>
								</tr>
								
								<tr class="even">
									<td><strong><label for="Store Limit">Reference No</label></td>
									<td>
										${responseJSON.referenceNo}
										<input name="storeCreditRefNo" type="hidden" id="storeCreditRefNo" class="field" value="${responseJSON.referenceNo}" >
									</td>
									<td><strong><label for="Store Limit">Approve/Reject </label></strong></td>
									<td>   ${responseJSON.approveReject} 
										<input name="approveReject" type="hidden" id="approveReject" class="field" value="${responseJSON.approveReject}" >
									</td>
								</tr> 
							</table>
						</fieldset>
					</div> 
							
                </div>
 				</div> 
		<div class="form-actions">
			<a  class="btn btn-danger" href="#" onClick="">Back</a> &nbsp;&nbsp;
			<a  class="btn btn-danger" href="#" onClick="createStoreLimit()">Confirm</a>
		</div>
	</div><!--/#content.span10--> 
		 				 
</form>
</body>
</html>
