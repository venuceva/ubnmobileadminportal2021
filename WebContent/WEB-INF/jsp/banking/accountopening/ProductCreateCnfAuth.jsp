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
  
<SCRIPT type="text/javascript"> 

$(document).ready(function(){   
	 
	var userStatus = '${responseJSON.status}';

	var text = "";
	 
	if( userStatus == 'Active') {
		text = "<a href='#' class='label label-success'  >"+userStatus+"</a>";
	} else if( userStatus == 'Inactive') {
		text = "<a href='#'  class='label label-warning'  >"+userStatus+"</a>";
	}
	
	$('#spn-user-status').append(text);
	
	$('#btn-make-payment').on('click', function(){   
 		
 		 $("#form1").validate(remarkRules);
 		 
 		var searchIDs="";
		$("div#auth-data input:radio:checked").each(function(index) {
			
			 searchIDs=$(this).val();	
			 $('#DECISION').val(searchIDs);
			 
		});
		
		  $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthRecordconfirm.action";
		  $("#form1").submit();	 
	}); 
	
	$('#btn-make-back').on('click', function(){ 
		
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthListAct.action";
		$("#form1").submit();		
		
	}); 
	
 
		 
	 var selData = "<s:property value='selectBoxData' />";
	 
	 $('#inssp').text(selData.split('##')[0]);
	 $('#opesp').text(selData.split('##')[1]);
	 $('#syssp').text(selData.split('##')[2]);
	
});

 


var remarkRules = {
		
		 errorElement: "div",
		 errorPlacement: function(error, element) {

         if ( element.is(":radio") ) {
        	 error.appendTo( "div#errors1" );
         }
         else{
        	 error.appendTo( "div#errors" );
              }
     },
			rules : { 
				remark : { required : false },
				authradio : { required : true }
			},		
			messages : { 
				remark : { 	required : "Please Enter Remarks."	},
				authradio : { required : "Please Select One Option." }
				
					} 
			
		};
		
//For Closing Select Box Error Message_End


</SCRIPT> 
</head>
   <s:set value="responseJSON" var="respData"/> 
 <body>  
 <form name="form1" id="form1" action="" method="post" > 
	<div id="content" class="span10">
			<ul class="breadcrumb">
				<li>
					<a href="home.action">Home</a> <span class="divider">&gt;&gt;</span>
				</li>
				<li><a href="accountproperties.action">Account Properties</a>   <span class="divider">&gt;&gt;</span>
				</li> 
				<li><a href="#">Account Properties Confirmation</a>  
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
					 <i class="icon-edit"></i>&nbsp;Account Settings 
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					</div>
				</div> 		
				<div class="box-content">
					<fieldset>  
						<div  id="kra-custDetails"> 
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr class="odd">
									<td width="25%"><label for="DL No"> <strong>Type Of Account </strong></label></td>
									<td width="30%"><s:property value="#respData.acType"/><input name="acType" value='<s:property value="#respData.acType"/>' type="hidden"></td>
									<td width="25%"><label for="BusinessUnit"><strong>Business Unit</strong></label></td>
									<td width="30%"><s:property value="#respData.businessUnit"/><input type="hidden" name="businessUnit" id="businessUnit" value='<s:property value="#respData.businessUnit"/>' /></td>
								</tr>
								<tr>
									<td><label for="Description"><strong>Description</strong></label></td>
									<td colspan=3><s:property value="#respData.description"/><input type="hidden" name="description" id="description" style="width:440px" value="<s:property value='#respData.description' />" /></td>
									
								</tr>
								<tr>
									<td ><label for="Service"><strong>Product Code</strong></label></td>
									<td ><s:property value="#respData.productCode"/><input name="productCode" value='<s:property value="#respData.productCode"/>' type="hidden"></td>
									<td ><label for="Service"><strong>Channel ID</strong></label></td>
									<td ><s:property value="#respData.channelID"/><input name="channelID" value='<s:property value="#respData.channelID"/>' type="hidden"></td>
								</tr>
								<tr>
									<td ><label for="Customer"><strong>Customer Type </strong></label></td>
									<td ><s:property value="#respData.customerType"/><input name="customerType" value='<s:property value="#respData.customerType"/>' type="hidden"></td>
									<td ><label for="CustomerSubType"><strong>Customer Sub Type </strong></label></td>
									<td ><s:property value="#respData.customerSubType"/><input name="customerSubType" value='<s:property value="#respData.customerSubType"/>' type="hidden"></td>
								</tr>
							<tr>
								<td ><label for="custSegmentID"><strong> Cust Segment ID</strong></label></td>
								<td ><s:property value="#respData.custSegmentID"/><input name="custSegmentID" value='<s:property value="#respData.custSegmentID"/>' type="hidden"></td>
								<td ><label for="SubProductID"><strong>Sub Product ID</strong></label></td>
								<td ><s:property value="#respData.subProductID"/><input name="subProductID" value='<s:property value="#respData.subProductID"/>' type="hidden"></td>
							</tr>
							<tr>
								<td ><label for="IsCustSWIFTEnabled"><strong>Is Cust SWIFT Enabled</strong></label></td>
								<td ><s:property value="#respData.isCustSWIFTEnabled"/><input name="isCustSWIFTEnabled" value='<s:property value="#respData.isCustSWIFTEnabled"/>' type="hidden"></td>
								<td ><label for="IsChequeBookReq"><strong>Is Cheque Book Required</strong></label></td>
								<td ><s:property value="#respData.isChequeBookReq"/><input name="isChequeBookReq" value='<s:property value="#respData.isChequeBookReq"/>' type="hidden"></td>
							</tr> 
							<tr>
								<td ><label for="ChequeBookType"><strong>Cheque Book Type</strong></label></td>
								<td ><s:property value="#respData.chequeBookType"/><input name="chequeBookType" value='<s:property value="#respData.chequeBookType"/>' type="hidden"></td>
								<td ><label for="RestrictionFlag"><strong>Restriction Flag</strong></label></td>
								<td ><s:property value="#respData.restrictionFlag"/><input name="restrictionFlag" value='<s:property value="#respData.restrictionFlag"/>' type="hidden"></td>
							</tr> 
							<tr>
								<td ><label for="IsdebitCardReq"><strong>Isdebit Card Required</strong></label></td>
								<td ><s:property value="#respData.isdebitCardReq"/><input name="isdebitCardReq" value='<s:property value="#respData.isdebitCardReq"/>' type="hidden"></td>
								<td ><label for="IsEStatementReq"><strong>Is E-Statement Required</strong></label></td>
								<td ><s:property value="#respData.isEStatementReq"/><input name="isEStatementReq" value='<s:property value="#respData.isEStatementReq"/>' type="hidden"></td>
							</tr> 
							<tr>
								<td ><label for="IsInternetBankingReq"><strong>Is Internet Banking Required</strong></label></td>
								<td ><s:property value="#respData.isInternetBankingReq"/><input name="isInternetBankingReq" value='<s:property value="#respData.isInternetBankingReq"/>' type="hidden"></td>
								<td ><label for="IsBancassuranceReq"><strong>Is Bank assurance Required</strong></label></td>
								<td ><s:property value="#respData.isBancassuranceReq"/><input name="isBancassuranceReq" value='<s:property value="#respData.isBancassuranceReq"/>' type="hidden"></td>
							</tr> 
							<tr>
								<td ><label for="IsSimpleBankingReq"><strong>Is Simple Banking Required</strong></label></td>
								<td ><s:property value="#respData.isSimpleBankingReq"/><input name="isSimpleBankingReq" value='<s:property value="#respData.isSimpleBankingReq"/>' type="hidden"></td>
								<td ><label for="IsCreditCardReq"><strong>Is Credit Card Required</strong></label></td>
								<td ><s:property value="#respData.isCreditCardReq"/><input name="isCreditCardReq" value='<s:property value="#respData.isCreditCardReq"/>' type="hidden"></td>
								</tr> 
							
							</table> 
						</div> 
					</fieldset> 
					<div class="row-fluid sortable" id='remarks'><!--/span-->
					<div class="box span12">
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Remarks
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
								</div>
							</div>  
							<div id="remarksInformation" class="box-content"> 
								<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
										id="documentData" > 
										<tr > 
											<td><label for="Remarks"><strong>Remarks<font color="red">*</font></strong></label></td>
											<td> <input type="text" name="remark"  id="remark"  value="${responseJSON.remarks}" /></td> 
											<td></td>
											<td></td>
										</tr> 
								</table>
						   </div>
				     </div>
		   </div>	
				<div id="auth-data"> 
				    <ul class="breadcrumb">
				      <li> <strong>Authorize&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio"  class='center-chk' type='radio' value='A' />&nbsp&nbsp </li>
				      <li> <strong>Reject&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio"  class='center-chk' type='radio' value='R' /> &nbsp&nbsp&nbsp</li>
				      <li><div id="errors1" class="errors"></div></li>
				 </ul>
		      </div> 
					
				<div class="form-actions" id="form1-submit"> 
					<input type="button" name="btn-make-payment" class="btn btn-success" id="btn-make-payment" value="Confirm" /> &nbsp;
					<input type="button" name="btn-make-back" class="btn btn-info" id="btn-make-back" value="Back" />
				</div>		
				
				<input name="STATUS" type="hidden" id="STATUS" value="${STATUS}" />
  				<input name="AUTH_CODE" type="hidden" id="AUTH_CODE" value="${AUTH_CODE}"  />
				<input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
				<input type="hidden" name="DECISION" id="DECISION" />
				<input type="hidden" name="type" id="type" value="${type}"/>
 				<input type="hidden" name="multiData" id="multiData" value="#respData['multiData']"/>
 				<input type="hidden" id="acttype" name="acttype" value="${responseJSON.acttype}" />	 				
				</div>
			</div> 
		</div> 
	</div>
	</form>
	 <script type="text/javascript">
  
$(function(){
	var auth=$('#STATUS').val();
	
	if ( auth == 'C'){
		$("#auth-data").hide();
		$("#btn-make-payment").hide();
		$("#remark").prop('disabled', true);
		$("#remarks").hide();
	}else{		
		$("#remarks").show();
	}
});	  

</script>
</body> 
<form name="form2" id="form2" method="post" action="">	
</form>	 
</html>