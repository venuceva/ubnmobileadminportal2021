
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
  
function createSubService(){
	
	
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/marketsCheckerAct.action';
		$("#form1").submit();
		return true;
	
}

function approveReject(){
	if($("#approveOrReject").val()==""){
		$("#errormsg").text("Please select Approved/Reject");
	}else{
		if($("#approveOrReject").val()=="R"){
			if($("#reason").val()==""){
				$("#errormsg").text("Please Enter Reason");
				return false;
			}
		}
		
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/approveOrRejectProduct.action';
	$("#form1").submit();
	return true;
	}
}

function getReason(){
	//alert("hello");
	var data=$("#approveOrReject").val();
	if(data=="R"){
		$("#res").show();
	}else{
		$("#res").hide();
	}
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
						  <li><a href="#">View Online Product</a></li>
						</ul>
				</div>
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
							<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>View Online Product
								<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>
<div id="errormsg" class="errores"></div>
							<div id="primaryDetails" class="box-content">
								<fieldset>
									<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable " >
										<tr class="even">
											<td ><label for="Service ID"><strong>Product Id</strong></label></td>
											<td> ${responseJSON.PRODUCT_ID}
												<input type="hidden" name="productId" id="productId" value="${responseJSON.PRODUCT_ID}" />
											</td>
											<td ><label for="Service ID"><strong>Product Name</strong></label></td>
											<td> ${responseJSON.PRODUCT_NAME}
											</td>
										</tr>
										<tr class="odd">
											<td ><label for="Service Name"><strong>Product Price/Discount Codes</strong></label></td>
											<td> ${responseJSON.PRODUCT_PRICE}
											</td>
											<td ><strong><label for="Service Name">Product Status</label></strong></td>
											<td> ${responseJSON.STATUS}
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
										<tr class="odd">
											<td ><label for="Service Name"><strong>Created By</strong></label></td>
											<td> ${responseJSON.MAKER_ID}
											</td>
										<td ><label for="Sub Service Code"><strong>Created Date</strong></label></td>
											<td > ${responseJSON.MAKER_DTTM}
											</td>
										</tr>
										<tr class="odd">
											<td ><label for="Service Name"><strong>Approved/Reject<font color="red">*</font></strong></label></td>
											<td colspan="3"> 
												<select id="approveOrReject" name="approveOrReject" data-placeholder="Choose User Designation..." 
													class="chosen-select" style="width: 220px;" required=true  onChange="getReason()">
													<option value="">Select</option>
													<option value="A">APPROVE</option>
													<option value="R">REJECT</option>
												</select>
											</td>
										</tr>
										<tr class="odd" id="res" style="display: none">
											<td><strong>Reason<font color="red">*</font></strong></td>
											<td colspan="3"><input type="text" name="reason" id="reason" /></td>
										</tr>	
									</table>
								</fieldset>
							</div>
					</div>
				</div> 
		<div class="form-actions">
			
			<a  class="btn btn-danger" href="#" onClick="createSubService()">Next</a>
			<a  class="btn btn-success" href="#" onClick="approveReject()">Confirm</a>
		</div> 
	</div><!--/#content.span10--> 
</form>
</body>
</html>
