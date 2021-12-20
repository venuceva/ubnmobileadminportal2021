<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title> </title>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 <%@taglib uri="/struts-tags" prefix="s"%>  
 <link rel="stylesheet" href='${pageContext.request.contextPath}/css/agency-app.css'>
 <style type="text/css">
label.error {
	 /*  font-weight: bold; */
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
	color: red;
}
.errors {
	color: red;
}
 </style>
<script type="text/javascript" >
$(document).ready(function(){
	var config = {
		      '.chosen-input'           : {},
		      '.chosen-input-deinput'  : {allow_single_deinput:true},
		      '.chosen-input-no-single' : {disable_search_threshold:10},
		      '.chosen-input-no-results': {no_results_text:'Oops, nothing found!'},
		      '.chosen-input-width'     : {width:"95%"}
		    };
			
		    for (var inputor in config) {
		      $(inputor).chosen(config[inputor]);
		    }
	$('#btn-make-payment').on('click', function(){
		$('#form1').attr("action", 'AccountSettingupdate.action').attr("method", "post"); 
		$('#form1').submit();
	});
});
</script>
</head>
 <body>  
 <form name="form1" id="form1" action="" method="post" > 
	<div id="content" class="span10">
			<ul class="breadcrumb">
				<li>
					<a href="home.action">Home</a> <span class="divider">&gt;&gt;</span>
				</li>
				<li><a href="accountproperties.action">Account Properties Modify</a>  <span class="divider">&gt;&gt;</span>  
				</li> 
				<li><a href="#">Account Properties Modify Confirmation</a>  
				</li> 
			</ul>
 		<table height="2">
			<tr>
				<td colspan="3">
					<div class="messages" id="messages">
						<s:actionmessage />
					</div>
					<div class="errors" id="errors">
						<s:actionerror />
					</div>
				</td>
			</tr>
		</table>  
		<div class="row-fluid sortable" id="kra-information"> 
			<div class="box span12">
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>&nbsp;Account Settings Modify
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					</div>
				</div> 		
				<div class="box-content">
					<fieldset>  
 							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr class="odd">
									<td width="25%"><label for="DL No"> <strong>Type Of Account </strong></label></td>
									<td width="30%"><s:property value="acType"/><input name="acType" value='<s:property value="acType"/>' type="hidden"></td>
									<td width="25%"><label for="BusinessUnit"><strong>Business Unit</strong></label></td>
									<td width="30%"><s:property value="businessUnit"/><input type="hidden" name="businessUnit" id="businessUnit" value='<s:property value="businessUnit"/>' /></td>
								</tr>
								<tr>
									<td><label for="Description"><strong>Description<font color="red">*</font></strong></label></td>
									<td colspan=3><s:property value="description"/><input type="hidden" name="description" id="description" style="width:440px" value="<s:property value='description' />" /></td>
								</tr>
								<tr>
									<td ><label for="Service"><strong>Product Code</strong></label></td>
									<td ><s:property value="productCode"/><input name="productCode" value='<s:property value="productCode"/>' type="hidden"></td>
									<td ><label for="Service"><strong>Channel ID</strong></label></td>
									<td ><s:property value="channelID"/><input name="channelID" value='<s:property value="channelID"/>' type="hidden"></td>
								</tr>
								<tr>
									<td ><label for="Customer"><strong>Customer Type </strong></label></td>
									<td ><s:property value="customerType"/><input name="customerType" value='<s:property value="customerType"/>' type="hidden"></td>
									<td ><label for="CustomerSubType"><strong>Customer Sub Type </strong></label></td>
									<td ><s:property value="customerSubType"/><input name="customerSubType" value='<s:property value="customerSubType"/>' type="hidden"></td>
								</tr>
							<tr>
								<td ><label for="custSegmentID"><strong> Cust Segment ID</strong></label></td>
								<td ><s:property value="custSegmentID"/><input name="custSegmentID" value='<s:property value="custSegmentID"/>' type="hidden"></td>
								<td ><label for="SubProductID"><strong>Sub Product ID</strong></label></td>
								<td ><s:property value="subProductID"/><input name="subProductID" value='<s:property value="subProductID"/>' type="hidden"></td>
							</tr>
							<tr>
								<td ><label for="IsCustSWIFTEnabled"><strong>Is Cust SWIFT Enabled</strong></label></td>
								<td ><s:property value="isCustSWIFTEnabled"/><input name="isCustSWIFTEnabled" value='<s:property value="isCustSWIFTEnabled"/>' type="hidden"></td>
								<td ><label for="IsChequeBookReq"><strong>Is Cheque Book Required</strong></label></td>
								<td ><s:property value="isChequeBookReq"/><input name="isChequeBookReq" value='<s:property value="isChequeBookReq"/>' type="hidden"></td>
							</tr> 
							<tr>
								<td ><label for="ChequeBookType"><strong>Cheque Book Type</strong></label></td>
								<td ><s:property value="chequeBookType"/><input name="chequeBookType" value='<s:property value="chequeBookType"/>' type="hidden"></td>
								<td ><label for="RestrictionFlag"><strong>Restriction Flag</strong></label></td>
								<td ><s:property value="restrictionFlag"/><input name="restrictionFlag" value='<s:property value="restrictionFlag"/>' type="hidden"></td>
							</tr> 
							<tr>
								<td ><label for="IsdebitCardReq"><strong>Isdebit Card Required</strong></label></td>
								<td ><s:property value="isdebitCardReq"/><input name="isdebitCardReq" value='<s:property value="isdebitCardReq"/>' type="hidden"></td>
								<td ><label for="IsEStatementReq"><strong>Is E-Statement Required</strong></label></td>
								<td ><s:property value="isEStatementReq"/><input name="isEStatementReq" value='<s:property value="isEStatementReq"/>' type="hidden"></td>
							</tr> 
							<tr>
								<td ><label for="IsInternetBankingReq"><strong>Is Internet Banking Required</strong></label></td>
								<td ><s:property value="isInternetBankingReq"/><input name="isInternetBankingReq" value='<s:property value="isInternetBankingReq"/>' type="hidden"></td>
								<td ><label for="IsBancassuranceReq"><strong>Is Bank assurance Required</strong></label></td>
								<td ><s:property value="isBancassuranceReq"/><input name="isBancassuranceReq" value='<s:property value="isBancassuranceReq"/>' type="hidden"></td>
							</tr> 
							<tr>
								<td ><label for="IsSimpleBankingReq"><strong>Is Simple Banking Required</strong></label></td>
								<td ><s:property value="isSimpleBankingReq"/><input name="isSimpleBankingReq" value='<s:property value="isSimpleBankingReq"/>' type="hidden"></td>
								<td ><label for="IsCreditCardReq"><strong>Is Credit Card Required</strong></label></td>
								<td ><s:property value="isCreditCardReq"/>
									<input name="isCreditCardReq" value='<s:property value="isCreditCardReq"/>' type="hidden" /> 
									<input type="hidden" name="accountId" id="accountId" value='<s:property value="accountId"/>'/>
								</td>
								</tr>  
							</table>  
 					</fieldset> 
				<div class="form-actions" id="form1-submit"> 
					<input type="button" name="btn-make-payment" class="btn btn-success" id="btn-make-payment" value="Update" />
				</div>					
				</div>
			</div> 
		</div> 
	</div>
	</form>
</body> 
<form name="form2" id="form2" method="post" action="">	
</form>	
 
</html>