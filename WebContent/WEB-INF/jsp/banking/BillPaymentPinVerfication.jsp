
<!DOCTYPE html>
<html lang="en">
<%@taglib uri="/struts-tags" prefix="s"%> 
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
 function onSubmit(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/billPayVerifyPinAct.action';
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
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li> <a href="#">Pay Bill</a>  </li> 
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

							<div class="box-content"> 
								 <fieldset>  
									<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable " >
										<tr class="even">
											<td >
												<strong><label for="Service ID">Transaction Pin</label></strong>
											</td>
											<td> 
												<input type="password" name="transPin" id="transPin" >
											</td>
												<input name="biller" type="hidden" id="biller" class="field" value="${responseJSON.billerId}"  >
												<input name="billerName" type="hidden" id="billerName" class="field" value="${responseJSON.biller}"  >
												<input name="customerKey" type="hidden"  id="customerKey" class="field"  value="${responseJSON.customerKey}" >
												<input name="modeOfPayment"  type="hidden" id="modeOfPayment" class="field" value="${responseJSON.modeOfPayment}" >
												<input name="amount"  type="hidden" id="amount" class="field" value="${responseJSON.amount}" >
												<input name="mobileNo"  type="hidden" id="mobileNo" class="field" value="${responseJSON.mobileNumber}" >
										
										</tr>
									</table>
								</fieldset>  		
							</div>
					</div>
				</div>
				
			<div class="form-actions"> 
				<a  class="btn btn-success" href="#" onClick="onSubmit()">Submit</a>
			</div> 
		</div> 
</form>
</body>
</html>
