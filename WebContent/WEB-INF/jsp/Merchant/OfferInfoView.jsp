
<!DOCTYPE html>
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

$(function(){
	var status="${responseJSON.STATUS}";
	
	if(status=="Rejected"){
		$("#res").show();
	}else{
		$("#res").hide();
	}
});

function createSubService(){

	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/marketsCheckerAct.action';
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
						  <li> <a href="#">Markets</a> <span class="divider"> &gt;&gt; </span></li>
						  <li><a href="#">View Offer/Deal Information</a></li>
						</ul>
				</div>
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
							<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>View Offer/Deal Information
								<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>

							<div id="primaryDetails" class="box-content">
								<fieldset>
									<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable " >
										<tr class="even">
											<td ><label for="Service ID"><strong>Offer ID</strong></label></td>
											<td colspan="3"> ${responseJSON.OFFER_ID}
											</td>
										</tr>
										<tr class="even">
										<td ><label for="Service Name"><strong>Category Id</strong></label></td>
											<td> ${responseJSON.CATEGORY_ID}
											</td>
										<td ><label for="Sub Service Code"><strong>Category Desc</strong></label></td>
											<td > ${responseJSON.CATEGORY_DESC}
											</td>
										</tr>
										<tr class="odd">
											<td ><label for="Service Name"><strong>Sub Category Id</strong></label></td>
											<td> ${responseJSON.SUB_CATEGORY_ID}
											</td>
										<td ><label for="Sub Service Code"><strong>Sub Category Desc</strong></label></td>
											<td > ${responseJSON.SUB_CATEGORY_DESC}
											</td>
										</tr>
										
										<tr class="even">
											<td ><label for="Service ID"><strong>Product Id</strong></label></td>
											<td> ${responseJSON.PRODUCT_ID}
											</td>
											<td ><label for="Service ID"><strong>Product Name</strong></label></td>
											<td> ${responseJSON.PRODUCT_NAME}
											</td>
										</tr>
										<tr class="odd">
											<td ><label for="Service Name"><strong>Product Price/Discount Codes</strong></label></td>
											<td colspan="3"> ${responseJSON.PRODUCT_PRICE}
											</td>
										</tr>
										<tr class="odd">
											<td ><label for="Service Name"><strong>Deal/Offer</strong></label></td>
											<td> ${responseJSON.OFFER_TYPE}
											</td>
											<td ><label for="Service Name"><strong>Deal/Offer Status</strong></label></td>
											<td> ${responseJSON.STATUS}
											</td>
										</tr>
										
										<tr class="odd">
											<td ><label for="Service Name"><strong>Time Limit</strong></label></td>
											<td> ${responseJSON.TIME_LIMIT}
											</td>
										<td ><label for="Sub Service Code"><strong>End Date</strong></label></td>
											<td > ${responseJSON.END_DATE}
											</td>
										</tr>
										<tr class="odd">
											<td ><label for="Service Name"><strong>Discount/Cash Back</strong></label></td>
											<td> ${responseJSON.DISCOUNT_CASHBACK}
											</td>
										<td ><label for="Sub Service Code"><strong>Discount(%) /Cash Back</strong></label></td>
											<td > ${responseJSON.DIS_CASHBACK_PER}
											</td>
										</tr>
										<tr class="odd">
											<td ><label for="Service Name"><strong>Create By</strong></label></td>
											<td> ${responseJSON.MAKER_ID}
											</td>
										<td ><label for="Sub Service Code"><strong>Created Date</strong></label></td>
											<td > ${responseJSON.MAKER_DTTM}
											</td>
										</tr>
										<tr class="odd">
											<td ><label for="Service Name"><strong>Approve/Rejected By</strong></label></td>
											<td> ${responseJSON.CHECKER_ID}
											</td>
										<td ><label for="Sub Service Code"><strong>Approve/Rejected Date</strong></label></td>
											<td > ${responseJSON.CHECKER_DTTM}
											</td>
										</tr>
										<tr style="display: none;" id="res">
											<td ><label for="Sub Service Code"><strong>Rejected Reason</strong></label></td>
												<td colspan="3" >
													<b><font color="red"><span id="reason">${responseJSON.COMMENTS}</span></font></b>
												 </td>
										</tr>
									</table>
								</fieldset>
							</div>
					</div>
				</div> 
		<div class="form-actions">
			<a  class="btn btn-danger" href="#" onClick="createSubService()">Next</a>
		</div> 
	</div><!--/#content.span10--> 
</form>
</body>
</html>
