<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">

<link href="${pageContext.request.contextPath}/css/link/css1" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/link/screen.css">
<link href="${pageContext.request.contextPath}/css/link/sticky.css" rel="stylesheet" type="text/css">

<script type="text/javascript">




function funcall(v,v1){
	
	$("#application").val(v);
	$("#txntype").val(v1);
	var url="${pageContext.request.contextPath}/<%=appName %>/Settlemetrequestapprovaldetails.action"; 
	$("#form1")[0].action=url;
	$("#form1").submit();
	return true;
}

</script>
<s:set value="responseJSON" var="respData" />

</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Wallet Transaction Settlement Request Approval</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
			
			 
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<!-- Customer Negotiated Rate Confirmation -->
						Search Details
						 <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
					
				 
					<div class="box-content" id="secondaryDetails">
						<fieldset>
						
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd" id="mobileselect" >
								<td width="25%" ><strong>Request Type</strong></td>
									<td width="25%" ><strong>Pending</strong></td>
									<td width="25%" ><strong>Approved</strong></td>
									
							</tr>
							
							<tr class="odd" id="searchenter" >
									<td >Agent Fund</td>
									<td ><div class='label label-success' onclick=funcall("P","AGENTFUND") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.FUNDING_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","AGENTFUND") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.FUNDING_COMPLETED}</div></td>
									
								</tr>
								
								<tr class="odd" id="searchenter" >
									<td >Fund Transfer Own Bank</td>
									<td ><div class='label label-success' onclick=funcall("P","WALAGNOWNBANK") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.WALAGNOWNBANK_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","WALAGNOWNBANK") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.WALAGNOWNBANK_COMPLETED}</div></td>
									
								</tr>
								
								<tr class="odd" id="searchenter" >
									<td >Paybill</td>
									<td ><div class='label label-success' onclick=funcall("P","WALPAYBILLAGN") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.WALPAYBILLAGN_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","WALPAYBILLAGN") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.WALPAYBILLAGN_COMPLETED}</div></td>
									
								</tr>
								
								<tr class="odd" id="searchenter" >
									<td >Fund Transfer Other Bank</td>
									<td ><div class='label label-success' onclick=funcall("P","WALAGNOTBANK") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.WALAGNOTBANK_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","WALAGNOTBANK") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.WALAGNOTBANK_COMPLETED}</div></td>
									
								</tr>
								
								
								<tr class="odd" id="searchenter" >
									<td >Paybill Airtime Recharge</td>
									<td ><div class='label label-success' onclick=funcall("P","AGNPAYBILLAIRTIME") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNPAYBILLAIRTIME_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","AGNPAYBILLAIRTIME") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNPAYBILLAIRTIME_COMPLETED}</div></td>
									
								</tr>
								
								<tr class="odd" id="searchenter" >
									<td >Cashwithdrawal Card Union bank</td>
									<td ><div class='label label-success' onclick=funcall("P","AGNCRDCSHWTDOWN") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNCRDCSHWTDOWN_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","AGNCRDCSHWTDOWN") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNCRDCSHWTDOWN_COMPLETED}</div></td>
									
								</tr>
								
								<tr class="odd" id="searchenter" >
									<td >Airtime Recharge</td>
									<td ><div class='label label-success' onclick=funcall("P","AGNUSSDAIRTIME") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNUSSDAIRTIME_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","AGNUSSDAIRTIME") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNUSSDAIRTIME_COMPLETED}</div></td>
									
								</tr>
							
							
							<tr class="odd" id="searchenter" >
									<td >Fundtransfer Card Union bank</td>
									<td ><div class='label label-success' onclick=funcall("P","AGNCRDFTXNOWN") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNCRDFTXNOWN_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","AGNCRDFTXNOWN") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNCRDFTXNOWN_COMPLETED}</div></td>
									
								</tr>
								
								<tr class="odd" id="searchenter" >
									<td >Cashwithdrawal Card Other bank</td>
									<td ><div class='label label-success' onclick=funcall("P","AGNCRDCSHWTDOTH") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNCRDCSHWTDOTH_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","AGNCRDCSHWTDOTH") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNCRDCSHWTDOTH_COMPLETED}</div></td>
									
								</tr>
								
								<tr class="odd" id="searchenter" >
									<td >Cash Deposit</td>
									<td ><div class='label label-success' onclick=funcall("P","AGCASHDEP") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGCASHDEP_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","AGCASHDEP") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGCASHDEP_COMPLETED}</div></td>
									
								</tr>
								
								<tr class="odd" id="searchenter" >
									<td >Fundtransfer Card Otherbank	</td>
									<td ><div class='label label-success' onclick=funcall("P","AGNCRDFTXNOTH") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNCRDFTXNOTH_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","AGNCRDFTXNOTH") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGNCRDFTXNOTH_COMPLETED}</div></td>
									
								</tr>
								
								<tr class="odd" id="searchenter" >
									<td >cash withdrawal</td>
									<td ><div class='label label-success' onclick=funcall("P","AGCASHWTHD") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGCASHWTHD_PENDING}</div></td>
									<td><div class='label label-important' onclick=funcall("C","AGCASHWTHD") style="cursor: pointer;width:20px;text-align:center">${responseJSON.VIEW_LMT_DATA.AGCASHWTHD_COMPLETED}</div></td>
									
								</tr>
								
							</table>
							
						</fieldset>
						
					</div>
					
				</div>
			</div>
			
			<input name="application" type="hidden" id="application"     />
			<input name="txntype" type="hidden" id="txntype"     />
			
			<div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Cancel" />
				 
			</div>  

	
			
			</div>
			

</form>

	
</body>
</html>
