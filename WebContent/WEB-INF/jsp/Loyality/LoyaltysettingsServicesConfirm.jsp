
<!DOCTYPE html>

<%@taglib uri="/struts-tags" prefix="s"%> 

<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
		
	 
<script type="text/javascript" >
$(document).ready(function() {
	 
	var val = 1;
	var rowindex = 1;
	var colindex = 0;
	var bankacctfinalData="${responseJSON1.BANK_MULTI_DATA}";
	var bankacctfinalDatarows=bankacctfinalData.split("#");
	if(val % 2 == 0 ) {
	addclass = "even";
	val++;
	}
	else {
	addclass = "odd";
	val++;
	}  
	var rowCount = $('#tbody_data > tr').length;

	
		for(var i=0;i<bankacctfinalDatarows.length;i++){
			var eachrow=bankacctfinalDatarows[i];
			var eachfieldArr=eachrow.split(",");
			var billerId = eachfieldArr[4];
			var billername = eachfieldArr[5];
			var billerCode = eachfieldArr[6];
			
			colindex = ++ colindex; 
			var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
			"<td>"+colindex+"</td>"+
			"<td>"+billerId+"</td>"+	
			"<td>"+billername+"</td>"+
			"<td>"+billerCode+"</td>"+	
			"</tr>";
			
			$("#tbody_data1").append(appendTxt);	  
			rowindex = ++rowindex;
		}
		
}); 

function createSubService(){ 
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/loyalitypointsettingsservicesack.action';
	$("#form1").submit();
	 
}


</script>
 
</head>

<body>
	<form name="form1" id="form1" method="post" action="">
	 
			<div id="content" class="span10"> 
			 
			    <div>
					<ul class="breadcrumb">
						<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
						<li><a href="#">Loyalty Management</a></li>
					</ul>
				</div>
				 
				<table height="3">
					 <tr>
						<td colspan="3">
							<div class="messages" id="messages"><s:actionmessage /></div>
							<div class="errors" id="errors"><s:actionerror /></div>
						</td>
					</tr>
				 </table>
					 
				<div class="row-fluid sortable"><!--/span-->

					<div class="box span12"> 
							<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Assign Service for Loyalty Confirmation
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
								</div>
							</div> 
							<div id="bankAccountInformation"  class="box-content"> 
							
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd">
										<td width="20%"><strong><label for="Bank Code"><strong>Product Code</strong></label></strong></td>
										<td width="30%"> ${responseJSON1.productcode} 
											<input name="productcode" type="hidden"  id="productcode" class="field" value='${responseJSON1.productcode}' >
										</td>
									
										<td width="20%"><strong><label for="Bank Code"><strong>Application</strong></label></strong></td>
										<td width="30%"> ${responseJSON1.application} 
											<input name="application" type="hidden"  id="application" class="field" value='${responseJSON1.application}' >
										</td>
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Bank Code"><strong>Loyalty Code</strong></label></strong></td>
										<td width="30%"> ${responseJSON1.loyaltycode} 
											<input name="loyaltycode" type="hidden"  id="loyaltycode" class="field" value='${responseJSON1.loyaltycode}' >
										</td>
									
										<td width="20%"><strong><label for="Bank Code"><strong>Loyalty Description</strong></label></strong></td>
										<td width="30%"> ${responseJSON1.loyaltydesc} 
											<input name="loyaltydesc" type="hidden"  id="loyaltydesc" class="field" value='${responseJSON1.loyaltydesc}' >
										</td>
									</tr>
									</table>
							
							
								<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
									id="bankAcctData"  >
										  <thead>
												<tr >
												<th>Sno</th>
												<th>Service Type</th>
												<th>Txn Amount</th>
												<th>No Of Points</th>
											</tr>
										  </thead>    
										 <tbody  id="tbody_data1">
										 </tbody>
								</table>
							</div>
							<input type="hidden" name="bankMultiData" id="bankMultiData" value="${responseJSON1.BANK_MULTI_DATA}" /> 
										
							</div>
						</div>
					 
					<div class="form-actions">  
						<a  class="btn btn-danger" href="#" onClick="createSubService()">Confirm</a>
					</div> 
			</div>
	 
</form>
</body>
</html>
