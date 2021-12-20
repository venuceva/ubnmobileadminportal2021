<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
 <%@taglib uri="/struts-tags" prefix="s"%> 
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

  
<script type="text/javascript" > 
  
var val = 1;
var rowindex = 0;
var colindex = 0;
var bankAcctFinalData="";
var binstatus = "";
var v_message = "";



var createlmtRules = {
		 rules : {
			  

			 txnamount 			: { required : true,number :true },
			 Noofpoints 			: { required : true,number :true }
			
			
		   },  
	   messages : {
		
		   txnamount : { 
		         required : "Please Enter Txn Amount."
		       },
		       Noofpoints : { 
			         required : "Please Enter No of Points."
			       }
  }
};


$(function() {
	
	
	$('#btn-submit').live('click', function () {
	
	
		$("#form1").validate(createlmtRules); 
 		if($("#form1").valid()) {
	
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/ModifyAssignLoyaltyDetailsconf.action';
		$("#form1").submit();
		return true;
 		} else {
 			return false;
		} 
		
	});
	
	$('#btn-back').live('click', function () {
		
		
	
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/loyalitypointsettings.action';
		$("#form1").submit();
		
		
	});
	
});

</script>
<style type="text/css">
label.error {
  font-weight: bold;
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
<s:set value="responseJSON" var="respData" />

</head> 
<body>
	<form name="form1" id="form1" method="post" action="">
	
		
			<div id="content" class="span10"> 
			<div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"> Loyalty Management</a></li>
				</ul>
			</div>
			<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Assigned Service Loyalty Modify
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

							</div>
						</div>

						<div id="primaryDetails" class="box-content">
							<fieldset>   
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd">
										<td width="20%"><strong><label for="Bank Code"><strong>Product Code</strong></label></strong></td>
										<td width="30%"> ${responseJSON.PRODUCT} 
											<input name="productcode" type="hidden"  id="productcode" class="field" value='${responseJSON.PRODUCT}' >
										</td>
									
										<td width="20%"><strong><label for="Bank Code"><strong>Application</strong></label></strong></td>
										<td width="30%"> ${responseJSON.APPLICATION} 
											<input name="application" type="hidden"  id="application" class="field" value='${responseJSON.APPLICATION}' >
										</td>
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Bank Code"><strong>Loyalty Code</strong></label></strong></td>
										<td width="30%"> ${responseJSON.LOYALTY_CODE} 
											<input name="loyaltycode" type="hidden"  id="loyaltycode" class="field" value='${responseJSON.LOYALTY_CODE}' >
										</td>
									
										<td width="20%"><strong><label for="Bank Code"><strong>Loyalty Description</strong></label></strong></td>
										<td width="30%"> ${responseJSON.LOYALTY_DESC} 
											<input name="loyaltydesc" type="hidden"  id="loyaltydesc" class="field" value='${responseJSON.LOYALTY_DESC}' >
										</td>
									</tr>
									
									
									 <tr class="even">
								<td width="20%"><label for="Transaction"><strong>Service Type</strong></label></td>
									<td width="30%">${responseJSON.SERVICE_CODE} 
											<input name="servicecode" type="hidden"  id="servicecode" class="field" value='${responseJSON.SERVICE_CODE}' >
											
									</td>
								<td width="20%"></td>
								<td width="30%"></td>
								</tr>
							
							<tr class="even">
							<td width="20%"><label for="MaxCount"><strong>Txn Amount<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="txnamount" id="txnamount" type="text" maxlength ='6'  required="true" class="field" value='${responseJSON.TXN_AMOUNT}'  /> <span id="bin_err" class="errmsg"></span></td>
								
								 <td width="20%"><label for="Max Amount"><strong>No Of Points<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="Noofpoints" id="Noofpoints" type="text" maxlength ='6'   class="field"  value='${responseJSON.NO_OF_POINT}' /> <span id="bin_err1" class="errmsg"></span></td>
								
								 </tr>
									 
								</table>
							 </fieldset> 
							</div>
							
								
							
						</div>
					</div>
						 
				 
			<span id="multi-row-data" class="multi-row-data" style="display:none"> </span>
			<div class="form-actions"> 
				<input type="button" class="btn btn-info" name="btn-submit" id="btn-submit" value="Submit" />&nbsp;
				<input type="button" class="btn btn-info" name="btn-back" id="btn-back" value="Back" />
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div> 
	</div> 
</form>
</body>
</html>
