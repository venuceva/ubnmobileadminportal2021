
<!DOCTYPE html>
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
<%@taglib uri="/struts-tags" prefix="s"%> 	
	 
<script type="text/javascript" > 
 
function createMerchant(){
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateMerchantAct.action';
	$("#form1").submit();
	return true;
}
$(document).ready(function() {  
	
	$("#documnetDetails").hide();
	
});

</script>
<s:set value="responseJSON" var="respData"/>	 
</head>

<body>
	<form name="form1" id="form1" method="post" action=""> 
			<div id="content" class="span10">  
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#"> Merchant View</a></li>
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
							<i class="icon-edit"></i>Merchant Primary Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

						</div>
					</div>  
						
						<div class="box-content" id="primaryDetails"> 
						 <fieldset>  
								<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="even">
										<td width="20%"><label for="Merchant Name"><strong>Merchant Name</strong></label></td>
										<td width="30%">	<s:property value='#respData.merchantName' /> </td>
										<td width="20%"><label for="Merchant ID"><strong>Merchant ID</strong></label></td>
										<td width="30%"> <s:property value='#respData.merchantID' />  </td>
									</tr>
									<tr class="even">
										<td ><label for="Location"><strong>Location</strong></label></td>
										<td > <s:property value='#respData.locationName' /> </td>
										<td ><label for="KRA PIN"><strong>KRA PIN</strong></label></td>
										<td > <s:property value='#respData.KRA_PIN' /> </td>	
									</tr> 
									<tr class="odd">
										<td ><label for="Merchant Type"><strong>Merchant Type</strong></label></td>
										<td >
											<s:property value='#respData.MERCHANT_TYPE' /></td>
										<td ><label for="Merchant Type"><strong>Member Type</label></td>
										<td ><s:property value='#respData.MEMBER_TYPE' /> </td>	
									</tr>
									<tr class="even">
										<td ><label for="Merchant Created By"><strong>Merchant Created By</strong></label></td>
										<td >
											<s:property value='#respData.createId' /></td>
										<td ><label for="Merchant Created Date"><strong>Member Created Date</label></td>
										<td ><s:property value='#respData.createDate' /> </td>	
									</tr>
									<tr class="odd">
										<td ><label for="Merchant Authorized By"><strong>Merchant Authorized By</strong></label></td>
										<td >
											<s:property value='#respData.authId' /></td>
										<td ><label for="Merchant Authorized Date"><strong>Member Authorized Date</label></td>
										<td ><s:property value='#respData.authDate' /> </td>	
									</tr>
								</table>
								</fieldset>  
							</div>
							 
						</div> 
						</div>
					<div class="row-fluid sortable">					
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
											<input name="employeeNo" type="hidden"   id="employeeNo" value="<s:property value='#respData.employeeNo' />"  />
										</td>
										<td><label for="Email Id"><strong>Email Id</strong></label></td>
										<td><s:property value='#respData.email' />
											<input name="email" type="hidden"  id="email" value="<s:property value='#respData.email' />"  />
										</td>
									</tr>
								</table> 
							 </fieldset> 
						</div> 
				</div> 
				</div>
		
			<div class="row-fluid sortable">
						<div class="box span12">
								<div class="box-header well" data-original-title>Store Information
									<div class="box-icon"> 
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
										<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
									</div>
								</div> 
								<div class="box-content" > 
									<table style = 'border: 1px solid #d7d7d7; font-family: Arial, Helvetica, sans-serif;font-size: 12px; color: #000000; font-weight: normal;' width='100%'   
										class='table table-striped table-bordered bootstrap-datatable datatable'  >
										<thead>
											<tr>
												<th>S No</th>
												<th>Store ID</th>
												<th class='hidden-phone'>Store Name </th>
												<th >Store Created By</th>
												<th class='hidden-phone'>Store Creation Date</th>
												<th >Store Authorized By</th>
												<th class='hidden-phone'>Store Authorized Date</th>
 												<th>Status </th> 
 											</tr>
										</thead> 
										<tbody id="StoreDetails" > 		
											<s:set value="responseJSON['storeData']" var="storeInfoForMer"/>
											<s:iterator value="#storeInfoForMer" var="userGroups" status="userStatus"> 
												<s:if test="#userStatus.even == true" > 
													<tr class="even" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
												 </s:if>
												 <s:elseif test="#userStatus.odd == true">
													<tr class="odd" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
												 </s:elseif> 
														<td><s:property value="#userStatus.index+1" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['storeId']" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['storeName']" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['makerId']" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['makerDate']" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['authid']" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['authdttm']" /></td> 
															<s:if test="#userGroups['status'] == 'Active'" > 
																 <s:set value="%{'label-success'}" var="merchStatus" /> 
															</s:if>
															<s:elseif test="#userGroups['status'] == 'Inactive'" >
																  <s:set value="%{'label-warning'}" var="merchStatus" />
																 
															 </s:elseif>
															 <s:elseif test="#userGroups['status'] == 'Un-Authorize'" >
																  <s:set value="%{'label-primary'}" var="merchStatus" /> 
															 </s:elseif> 
														<td class='hidden-phone'><a href='#' class='label <s:property value="#merchStatus" />' ><s:property value="#userGroups['status']" /> </a></td> 
													</tr>  
											</s:iterator>   
										 </tbody>
									</table>
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
					<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="Manager Name"><strong>Manager Name</strong></label></td>
								<td width="30%"> <s:property value="#respData.managerName"/>
 								</td>
								<td width="20%"><label for="Email"><strong>Email</strong></label></td>
								<td width="30%"> <s:property value="#respData.email"/>
 								</td>
							</tr>
							<tr class="odd">
								<td ><label for="Address Line 1"><strong>Address Line 1</strong></label></td>
								<td > <s:property value="#respData.addressLine1"/>
 								</td>
								<td ><label for="Address Line 2 "><strong>Address Line 2 </strong></label></td>
								<td > <s:property value="#respData.addressLine2"/>
 								</td>
							</tr>
							<tr class="even">
								<td ><label for="Address Line 3 "><strong>Address Line 3</strong></label></td>
								<td > <s:property value="#respData.addressLine3"/>
 								</td>
								<td><label for="City"><strong>City/Town</strong></label></td>
								<td > <s:property value="#respData.city"/>
 								</td>
							</tr>
							<tr class="odd"> 
								<td ><label for="Area"><strong>Area</strong></label></td>
								<td > <s:property value="#respData.AREA"/>
 								</td>
								<td><label for="Postal Code"><strong>Postal Code</strong></label></td>
								<td > <s:property value="#respData.POSTALCODE"/>
 								</td>
							</tr>
							<tr class="even">
								<td ><label for="P.O. Box Number "><strong>P.O. Box Number</strong></label></td>
								<td > <s:property value="#respData.poBoxNumber"/>
 								</td>
								<td ><label for="Telephone Number 1"><strong>Telephone Number 1</strong></label></td>
								<td> <s:property value="#respData.telephoneNumber1"/>
 								</td>
							</tr>
						   <tr class="even">
								<td ><label for="Telephone Number 2 "><strong>Telephone Number 2</strong></label>	</td>
								<td > <s:property value="#respData.telephoneNumber2"/>
 								</td>
								<td ><label for="Mobile Number"><strong>Mobile Number</strong></label></td>
								<td > <s:property value="#respData.mobileNumber"/>
 								</td>
						   </tr>
						   <tr class="odd">
								<td ><label for="Fax Number "><strong>Fax Number </strong></label></td>
								<td > <s:property value="#respData.faxNumber"/>
 								</td>
								<td><label for="L/R Number"><strong>L/R Number</strong></label></td>
								<td ><s:property value="#respData.LRNUMBER"/> 
 					
						   </tr>
							<tr class="even">
								<td ><label for="Primary Contact Person"><strong>Primary Contact Person</strong></label></td>
								<td > <s:property value="#respData.PRIMARY_CONTACT_NAME"/>
 								</td>
								<td><label for="Primary Contact Number"><strong>Primary Contact Number</strong></label></td>
								<td > <s:property value="#respData.PRIMARY_CONTACT_NUMBER"/>
 								</td>
							</tr>
							<tr class="odd">
								<td ><label for="Country"><strong>Country</strong></label></td>
								<td > <s:property value="#respData.COUNTRY"/>
 								</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
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
										<s:param name="jsonData" value="%{#respData['bankAcctMultiData']}"/>  
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
										id="documentData"  >
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
												<s:param name="jsonData" value="%{#respData['AgenctAcctMultiData']}"/>  
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
						<div class="row-fluid sortable" id="documnetDetails"><!--/span-->
        
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
												<s:param name="jsonData" value="%{#respData['documentMultiData']}"/>  
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
				<a  class="btn btn-danger" href="#" onClick="createMerchant()">Next</a>
		</div> 
	</div>
		   
</form>
</body>
</html>
