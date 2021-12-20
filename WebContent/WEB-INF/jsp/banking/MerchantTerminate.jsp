
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 <s:set value="responseJSON" var="respData"/>
<script type="text/javascript"> 
$(document).ready(function() {  
	$("#DocumentInfo").hide();
	$("#MerchantAdminDetails").hide();
	
});
function createMerchant(){ 
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantCreateConfirmAct.action';
	$("#form1").submit(); 
} 

function TerminateConfirm(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantTermnateConfirmAct.action';
		$("#form1").submit();
		return true;
}

function getGenerateScreen(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateMerchantAct.action';
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
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt;</span></li>
				  <li><a href="#">Activate / De-activate Merchant</a></li>
				</ul>
			</div>
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12">  
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Merchant Primary Details
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
								</div>
							</div> 
							<div class="box-content" id="primaryDetails"> 
						 <fieldset> 
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="even">
										<td width="20%"><strong><label for="Merchant Name"><strong>Merchant Name</strong></label></td>
										<td width="30%"> <s:property value='#respData.merchantName' /> 
										<input name="merchantName" type="hidden"  id="merchantName" class="field"  value="<s:property value='#respData.merchantName' />" > </td>
										<td width="20%"><strong><label for="Merchant ID"><strong>Merchant ID</strong></label></td>
										<td width="30%"> <s:property value='#respData.merchantID' /> 
										<input name="merchantID" type="hidden" id="merchantID" class="field" value="<s:property value='#respData.merchantID' />" ></td>
									</tr>
									<tr class="odd">
										<td ><label  for="Location"><strong>NBK Branch Location</strong></label></td>
										<td > <s:property value='#respData.locationName' />
											<input name="location" type="hidden" class="field" id="location" value="<s:property value='#respData.locationName' />"  />
										</td>
										<td ><label  for="KRA PIN"><strong>KRA PIN</strong></label></td>
										<td > <s:property value='#respData.KRA_PIN' />
										<input name="kraPin" type="hidden" class="field" id="kraPin" value="<s:property value='#respData.KRA_PIN' />"  />
										</td>	
									</tr> 
									<tr class="even">
										<td ><label  for="Merchant Type"><strong>Merchant Type</strong></label></td>
										<td >
											<s:property value='#respData.MERCHANT_TYPE' /> 
											<input name="merchantType" type="hidden" class="field" id="merchantType" value="<s:property value='#respData.MERCHANT_TYPE' />"  />
										</td>
										<td ><label for="Member Type"><strong>Member Type</strong></label></td>
										<td >
											<s:property value='#respData.MEMBER_TYPE' /> 
											<input name="memberType" type="hidden" class="field" id="memberType" value="<s:property value='#respData.MEMBER_TYPE' />"  />
										</td>	
									</tr>
								</table>
						 </fieldset> 
					</div> 
					</div>
				</div>
				<div class="row-fluid sortable" id="MerchantAdminDetails">					
					<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Merchant Admin Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						</div>
					</div>   
					<div class="box-content" id="merchantAdminDetails"> 
						 <fieldset>   
								<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="even">
										<td width="20%"><label for="Merchant Admin Id"><strong>Merchant Admin Id</strong></label></td>
										<td width="30%"><s:property value='#respData.merchantAdmin' /> 
											<input name="merchantAdminId" type="hidden" class="field" id="merchantAdminId" value="<s:property value='#respData.merchantAdmin' />"  />										 
										</td>
										<td width="20%"></td>
										<td width="30%"></td>
									</tr>
									<tr class="even" id="userData1">
										<td><label for="User Name"><strong>User Name</strong></label></td>
										<td> <s:property value='#respData.userName' /> 
											<input name="userName" type="hidden"   id="userName" value="<s:property value='#respData.userName' />"  />
										</td>
										<td><label for="User Status"><strong>User Status</strong></label></td>
										<td> <s:property value='#respData.userStatus' />
											<input name="userStatus" type="hidden"   id="userStatus" value="<s:property value='#respData.userStatus' />"  />
										</td>
									</tr>
									<tr class="odd" id="userData2">
										<td><label for="Employee No"><strong>Employee No</strong></label></td>
										<td><s:property value='#respData.employeeNo' />
											<input name="empNo" type="hidden"   id="empNo" value="<s:property value='#respData.employeeNo' />"  />
										</td>
										<td><label for="Admin Email Id"><strong>Admin Email Id</strong></label></td>
										<td><s:property value='#respData.emailId' />
											<input name="emailId" type="hidden"  id="emailId" value="<s:property value='#respData.emailId' />"  />
										</td>
									</tr>
								</table> 
							 </fieldset> 
						</div> 
					</div> 
				</div>
				<div class="row-fluid sortable"><!--/span--> 
						<div class="box span12">
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Communication Details
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
								</div>
							</div> 
							 <div id="communicationDetails" class="box-content">
									<fieldset> 
										<table width="950" border="0" cellpadding="5" cellspacing="1" 
												class="table table-striped table-bordered bootstrap-datatable ">
											<tr class="even">	
												<td width="20%"><label for="Manager Name"><strong>Manager Name</strong></label></td>
												<td width="30%"> <s:property value='#respData.managerName' /> 
												<input name="managerName" type="hidden" id="managerName" class="field" value="<s:property value='#respData.managerName' />"   ></td>
												<td width="20%"><label for="Email"><strong>Email</strong></label></td>
												<td width="30%"><s:property value='#respData.email' /> 
												<input name="email" type="hidden"  id="email" class="field"  value="<s:property value='#respData.email' />"  > </td>
											</tr>
											<tr class="odd">
												<td ><label for="Address Line 1"><strong>Address Line 1</strong></label></td>
												<td ><s:property value='#respData.addressLine1' />
												<input name="addressLine1" id="addressLine1" class="field" type="hidden"  maxlength="50" value="<s:property value='#respData.addressLine1' />"  ></td>
												<td ><label for="Address Line 2 "><strong>Address Line 2 </strong></label></td>
												<td ><s:property value='#respData.addressLine2' />
												<input name="addressLine2" type="hidden" class="field" id="addressLine2"   value="<s:property value='#respData.addressLine2' />"  /></td>
											</tr>
											<tr class="even">
												<td ><label for="Address Line 3 "><strong>Address Line 3</strong></label></td>
												<td ><s:property value='#respData.addressLine3' />
												<input name="addressLine3" type="hidden" class="field" id="addressLine3" value="<s:property value='#respData.addressLine3' />" /></td>
												<td><label for="City"><strong>City/Town</strong></label></td>
												<td ><s:property value='#respData.city' />
												<input name="city" type="hidden" class="field" id="city"  value="<s:property value='#respData.city' />"  /></td>
											</tr>
											<tr class="odd">
												<td ><label for="Address Line 3 "><strong>Area</strong></label></td>
												<td ><s:property value='#respData.AREA' />
												<input name="area" type="hidden" class="field" id="area" value="<s:property value='#respData.AREA' />"  /></td> 
												<td ><label for="Postal Code"><strong>Postal Code</strong></label></td>
												<td ><s:property value='#respData.POSTALCODE' />
												<input name="postalCode" type="hidden" id="postalCode" class="field" value="<s:property value='#respData.POSTALCODE' />"  />  </td>												
										   </tr>
											<tr class="even">
												<td ><label for="P.O. Box Number "><strong>P.O. Box Number</strong></label></td>
												<td ><s:property value='#respData.poBoxNumber' />
												<input name="poBoxNumber" type="hidden" id="poBoxNumber" class="field" value="<s:property value='#respData.poBoxNumber' />"  /></td>
												<td ><label for="Telephone Number 1"><strong>Telephone Number 1</strong></label></td>
												<td><s:property value='#respData.telephoneNumber1' />
												<input name="telephoneNumber1" type="hidden" id="telephoneNumber1" class="field" value="<s:property value='#respData.telephoneNumber1' />" /> </td>											 												
										   </tr>
										   <tr class="odd">
												<td ><label for="Telephone Number 2 "><strong>Telephone Number 2</strong></label>	</td>
												<td ><s:property value='#respData.telephoneNumber2' />
												<input name="telephoneNumber2" type="hidden" class="field" id="telephoneNumber2"  value="<s:property value='#respData.telephoneNumber2' />" /> </td>												
												<td ><label for="Mobile Number"><strong>Mobile Number</strong></label></td>
												<td ><s:property value='#respData.mobileNumber' />
												<input name="mobileNumber" id="mobileNumber" class="field" type="hidden"  value="<s:property value='#respData.mobileNumber' />"  /> </td>												
										   </tr>
										<tr class="even">
											<td ><label for="Fax Number"><strong>Fax Number </strong></label></td>
											<td ><s:property value='#respData.faxNumber' />
											<input name="faxNumber"  type="hidden" class="field" id="faxNumber" value="<s:property value='#respData.faxNumber' />" /> </td>											 
											<td><label for="L/R Number"><strong>L/R Number</strong></label></td>
											<td ><s:property value='#respData.LRNUMBER' />
											<input name="lrNumber" type="hidden" class="field" id="lrNumber" value="<s:property value='#respData.LRNUMBER' />"  /></td>
										</tr>
										<tr class="odd">  
											<td ><label for="Primary Contact Person"><strong>Primary Contact Person</strong></label></td>
											<td ><s:property value='#respData.PRIMARY_CONTACT_NAME' />
											<input name="prmContactPerson" id="prmContactPerson" class="field" type="hidden" value="<s:property value='#respData.PRIMARY_CONTACT_NAME' />"  ></td>
											<td><label for="Primary Contact Number"><strong>Primary Contact Number</strong></label></td>
											<td ><s:property value='#respData.PRIMARY_CONTACT_NUMBER' />
											<input name="prmContactNumber" type="hidden" class="field" id="prmContactNumber" value="<s:property value='#respData.PRIMARY_CONTACT_NUMBER' />"  /> </td>
										</tr>
										<tr class="even">  
											<td ><label for="Country"><strong>Country</strong></label></td>
											<td >
											 <s:property value='#respData.COUNTRY' /> 
											 <input name="country" type="hidden" class="field" id="country" value="<s:property value='#respData.COUNTRY' />"  /> 
											</td>
											<td >&nbsp;</td >
											<td >&nbsp;</td >
										</tr>
									</table>
								</fieldset> 
							</div> 
						</div> 
				</div> 
				
				<div class="row-fluid sortable"><!--/span--> 
			<div class="box span12">
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Bank Account Information
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

					</div>
				</div>  
							<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
								id="bankAcctData">
								  <thead>
										<tr>
											<th>Sno</th>
											<th>Account Number</th>
											<th>Account Description</th>
											<th>Bank Code</th>
											<th>Branch Code</th> 
										</tr>
								  </thead>    
								 <tbody   id="tbody_data1">
									<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{#respData.bankAcctMultiData}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
									</s:bean> 
									<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" >   
												
												<s:if test="#mulDataStatus.even == true" > 
													<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
												</s:if>
												<s:elseif test="#mulDataStatus.odd == true">
													<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
												</s:elseif> 
											<td><s:property value="#mulDataStatus.index+1" /></td>
												<s:generator val="%{#mulData}"
													var="bankDat" separator="," >  
													<s:iterator status="itrStatus"> 
														<s:if test="#itrStatus.index != 4" > 
															<td><s:property /></td>
														</s:if>
													</s:iterator>  
												</s:generator> 
										</tr>
									</s:iterator>
								 </tbody>
							</table>
							 
						</div> 
						</div>
						<div class="row-fluid sortable"><!--/span-->
        
							<div class="box span12">
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Agent Based Information
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
								</div>
							</div>  
							<div id="agentBasedInfo" class="box-content">  
								<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
										id="documentData" >
										<thead>
												<tr>
													<th>Sno</th>
													<th>Bank Agent Number</th>
													<th>MPesa Agent Number</th>
													<th>Airtel Money Agent Number</th>
													<th>Orange Money Agent Number</th>
													<th>Visa Mid</th>
 												</tr>
										</thead>    
										<tbody id="tbody_data3">
											<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
												<s:param name="jsonData" value="%{#respData.AgenctAcctMultiData}"/>  
												<s:param name="searchData" value="%{'#'}"/>  
											</s:bean> 
											<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" >   
														 
														<s:if test="#mulDataStatus.even == true" > 
															<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
														</s:if>
														<s:elseif test="#mulDataStatus.odd == true">
															<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
														</s:elseif> 
												
													<td><s:property value="#mulDataStatus.index+1" /></td>
														<s:generator val="%{#mulData}"
															var="bankDat" separator="," >  
															<s:iterator status="itrStatus">  
																<td><s:property /></td> 
															</s:iterator>  
														</s:generator> 
												</tr>
											</s:iterator>
										</tbody> 
								</table> 
							</div> 
						</div> 
						</div>
						<div class="row-fluid sortable" id="DocumentInfo"><!--/span-->
        
							<div class="box span12">
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Document Information
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>  
							<div id="documentInformation" class="box-content"> 
								<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
										id="documentData" > 
										  <thead>
												<tr>
													<th>Sno</th>
													<th>Document ID</th>
													<th>Document Description</th>
													<th>Grace Period</th>
													<th>Mandatory</th>
 												</tr>
										  </thead>    
										 <tbody id="tbody_data2">
											<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
												<s:param name="jsonData" value="%{#respData.documentMultiData}"/>  
												<s:param name="searchData" value="%{'#'}"/>  
											</s:bean> 
											<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" >  
														<s:if test="#mulDataStatus.even == true" > 
															<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
														</s:if>
														<s:elseif test="#mulDataStatus.odd == true">
															<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
														</s:elseif> 
												
													<td><s:property value="#mulDataStatus.index+1" /></td>
														<s:generator val="%{#mulData}"
															var="bankDat" separator="," >  
															<s:iterator status="itrStatus">  
																	<td><s:property /></td> 
															</s:iterator>  
														</s:generator> 
												</tr>
											</s:iterator>
										 </tbody>
								</table>
										
						</div>
				</div>
		</div> 	
							
		<div class="form-actions">
			<a  class="btn btn-danger" href="#" onClick="getGenerateScreen()">Back</a>
			<a  class="btn btn-success" href="#" onClick="TerminateConfirm()">Confirm</a>
		</div> 
	</div><!--/#content.span10--> 
</form>
<script type="text/javascript">
$(function(){ 
	var config = {
      '.chosen-select'           : {},
      '.chosen-select-deselect'  : {allow_single_deselect:true},
      '.chosen-select-no-single' : {disable_search_threshold:10},
      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chosen-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    } 
	 
	$('#country').val('<s:property value="#respData.country" />');
	$('#country').trigger("liszt:updated"); 
	
	
});
</script>
</body>
</html>
