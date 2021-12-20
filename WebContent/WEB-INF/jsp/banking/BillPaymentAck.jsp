
<!DOCTYPE html>

<%@taglib uri="/struts-tags" prefix="s"%>

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
$(document).ready(function() { 
	var data='${responseJSON.modeOfPayment}';
	if(data=='CASH') {
		$("#print").show();
	}else{
		$("#print").hide();
	}
	
	if(data=='MPESA') {
		$("#mobile").show();
	}else{
		$("#mobile").hide();
	}
});

function onSubmit(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/billPaymentAct.action';
	$("#form1").submit();
	return true;
}
function createSubService(){

	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/subServiceCreateConfirmAct.action';
	$("#form1").submit();
	return true;
}
</script>
<style type="text/css" media="print">
    @media print
    {
    #non-printable { display: none; }
    #printable {
    display: block;
    width: 100%;
    height: 100%;
    }
    }
</style>
 
</head>

<body>
	<form name="form1" id="form1" method="post" action="">
	<div id="content" class="span10">   
	    <div>
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li><a href="#">Bill Payment Acknowledgement</a></li>
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
					<i class="icon-edit"></i>Bill Payment Details
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					</div>
				</div>

				<div id="primaryDetails" class="box-content">
					<fieldset>
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable " >
							<tr class="even">
								<td ><strong><label for="Service ID">Biller</label></strong></td>
								<td> ${responseJSON.biller}
									<input name="biller" type="hidden" id="biller" class="field" value="${responseJSON.billerId}"  >
								</td>
							</tr>
							<tr class="odd">
								<td ><strong><label for="Service Name">Customer</label></strong></td>
								<td> ${responseJSON.customerKey}
									<input name="customerKey" type="hidden"  id="customerKey" class="field"  value="${responseJSON.customerKey}" >
								</td>
							</tr>
							<tr class="even">
                                  <td ><strong><label for="Sub Service Code">Mode of Payment</label></strong></td>
								<td > ${responseJSON.modeOfPayment}
									<input name="modeOfPayment"  type="hidden" id="modeOfPayment" class="field" value="${responseJSON.modeOfPayment}" > 
								</td>
							</tr>
							<tr class="odd">
								<td><strong><label for="Sub Service Name">Amount</label></strong></td>
								<td> ${responseJSON.amount}
									<input name="amount"  type="hidden" id="amount" class="field" value="${responseJSON.amount}" >
								</td>
							</tr>
							<tr class="even" id="mobile">
								<td><strong><label for="Sub Service Name">Mobile NO</label></strong></td>
								<td> ${responseJSON.mobileNumber}
								</td>
							</tr>
							<input name="mobileNo"  type="hidden" id="mobileNo" class="field" value="${responseJSON.mobileNumber}" >

							</table>
					</fieldset>  
				</div> 
		</div>
	</div>
	<div class="form-actions"> 
		<a  class="btn btn-danger" href="#" onClick="onSubmit()">Next</a> &nbsp;
		<span id="print">
			<input type="button" id="non-printable" class="btn btn-success" onclick="JavaScript:window.print();" value="print" />
		</span>
	</div> 
</div> 
</form>
</body>
</html>
