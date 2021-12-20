
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  

<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>


<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js" type="text/javascript"></script>



<script type="text/javascript" >

	
$(function() {
	
	

		
		$('#btn-submit').live('click', function () {
		
		
			
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/loyalityack.action';
			$("#form1").submit();
			return true;
			
		});
		
 	
	});


</script>

<s:set value="responseJSON" var="respData"/> 
</head>

<body>	
	
		<div id="content" class="span10"> 
		    <div>
					<ul class="breadcrumb">
					  <li><a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li><a href="loyalitypointsettings.action">Loyalty Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <!-- <li><a href="#">Setting</a></li> -->
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
	<form name="form1" id="form1" method="post">
	
				
			
				<div class="box span12"> 
				
				
				<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Product Details  
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>
						</div>
					</div>


					<div class="box-content" id="terminalDetails">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="Product Code"><strong>Product Code</strong></label></td>
									<td width="30%"><s:property value='#respData.productcode' />
									
									</td>
									<td width="20%"><label for="Product Description"><strong>Product Description</strong></label></td>
									<td width="30%"><s:property value='#respData.productdesc' />
									
									</td>
								</tr>
								<tr class="even">
									
									<td><label for="Product Currency"><strong>Product Currency</strong></label></td>
									<td><s:property value='#respData.productcurr' />
									</td>
									<td><label for="Application"><strong>Application</strong></label></td>
									<td><s:property value='#respData.application' />
									</td>
									
								</tr>
								

								
							</table>
							


						</fieldset>
					</div>
				</div>
				</div>
				
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Loyalty Point Setting
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>   
						
					<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Loyalty Code</strong></label></td>
								<td width="30%"><s:property value='#respData.limitCodeval' />
								<input name="limitCodeval" type="hidden" maxlength ='6' id="limitCodeval" required="true" class="field"  value="<s:property value='#respData.limitCodeval' />"  /> <span id="bin_err" class="errmsg"></span></td>
								 <td width="20%"><label for="Limit Description"><strong>Loyalty Description</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitDescriptionval' />
								 <input name="limitDescriptionval"  type="hidden" maxlength ='25' id="limitDescriptionval" required="true"  class="field"  value="<s:property value='#respData.limitDescriptionval' />" /><span id="bin_err1" class="errmsg"></span> </td>
							</tr>
							
							<tr class="even">
								<td width="20%"><label for="Min Points"><strong>Redemption Min Points</strong></label></td>
								<td width="30%"><s:property value='#respData.MinPoints' />
								<input name="MinPoints" type="hidden" maxlength ='6' id="MinPoints" required="true" class="field"  value="<s:property value='#respData.MinPoints' />" /></td>
								 <td width="20%"><label for="Unit Size"><strong>Redemption Unit Size</strong></label></td>
								 <td width="30%"><s:property value='#respData.UnitSize' />
								 <input name="UnitSize"  type="hidden" maxlength ='25' id="UnitSize" required="true"  class="field"   value="<s:property value='#respData.UnitSize' />" /> </td>
							</tr>
							
						</table>
						
					</fieldset>
					
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							
							
							<tr class="even">
								<td width="20%"><label for="Min Points"><strong>Redemption Points</strong></label></td>
								<td width="30%"><s:property value='#respData.redemptionpoints' />
								<input name="redemptionpoints" type="hidden" maxlength ='6' id="redemptionpoints" required="true" class="field"   value="<s:property value='#respData.redemptionpoints' />"  /></td>
								 <td width="20%"><label for="Unit Size"><strong>Redemption Amount</strong></label></td>
								 <td width="30%"><s:property value='#respData.redemptionamount' />
								 <input name="redemptionamount"  type="hidden" maxlength ='25' id="redemptionamount" required="true"  class="field"   value="<s:property value='#respData.redemptionamount' />"  /> </td>
							</tr>
							
							<%-- <tr class="even">
							<td width="20%"><label for="MaxCount"><strong>Txn Amount</strong></label></td>
								<td width="30%"><s:property value='#respData.txnamount' />
								<input name="txnamount" id="txnamount" type="hidden" maxlength ='6'  required="true" class="field"   value="<s:property value='#respData.txnamount' />"   /> <span id="bin_err" class="errmsg"></span></td>
								
								 <td width="20%"><label for="Max Amount"><strong>No Of Points</strong></label></td>
								<td width="30%"><s:property value='#respData.Noofpoints' />
								<input name="Noofpoints" id="Noofpoints" type="hidden" maxlength ='6'   class="field"    value="<s:property value='#respData.Noofpoints' />"  /> <span id="bin_err1" class="errmsg"></span></td>
								
								 </tr>
							 --%>
							
							
						</table>
						
					</fieldset>
				
					<input name="productcode"  type="hidden" id="productcode" class="field" value="<s:property value='#respData.productcode' />" />
					<input name="productdesc" type="hidden" id="productdesc" class="field" value="<s:property value='#respData.productdesc' />" />
					<input name="productcurr" type="hidden" id="productcurr" class="field" value="<s:property value='#respData.productcurr' />" />
					<input name="application" type="hidden" id="application" class="field" value="<s:property value='#respData.application' />" />
						
					
					</div> 		
		</div>
		
		<a  class="btn btn-success" href="#" id="btn-submit" >Confirm</a>
		&nbsp; <a  class="btn btn-danger" href="#"  id="btn-cancel" >Cancel</a>
			
		</form>
				
				
		<!-- Fee Settings End -->
				
						
	
	
			

	</div>			
	
	</div>
	
 
 
 
</body>
</html>
