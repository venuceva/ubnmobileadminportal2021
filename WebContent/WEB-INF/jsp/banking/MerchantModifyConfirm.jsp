
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
<script type="text/javascript" >
 
$(document).ready(function() {
	//alert('${storeData}');
	var storeRecs =  '${storeData}'.split('#');

	$.each(storeRecs, function(key,value) {
		var store = value.split(',');
			var tr ='<tr><td class="  sorting_1">'+(key+1)+'</td>'+
					'<td class="hidden-phone ">'+store[0]+'</td>'+
					'<td class="hidden-phone ">'+store[1]+'</td>'+
					'<td class="hidden-phone ">'+store[2]+'</td>'+
					'<td class="hidden-phone ">'+store[3]+'</td>'+
					'<td class="hidden-phone ">'+store[4]+'</td>'+
					'<td class="hidden-phone ">'+store[5]+'</td>'+
					'<td class="hidden-phone "><a href="#" class="label label-success">'+store[6]+'</a></td></tr>';
				$('#StoreDetails').append(tr);	
		});
	$("#merchantAdminDetails").hide();
//	$("#Storeinfo").hide();
	

}); 
 
function modifyMerchant(){ 
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantmodifyConfirmAct.action';
	$("#form1").submit(); 
}

function getGenerateScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateMerchantAct.action';
	$("#form1").submit();
 }
 
function getPreviousScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantModifyScreenAct.action';
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
					  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Merchant Modify Confirmation</a></li>
					</ul>
				</div>
			<div class="errors">
				<s:actionerror />
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
								<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="even">
										<td width="20%"><label for="Merchant Name"><strong>Merchant Name</strong></label></td>
										<td width="30%">	
											<s:property value='merchantName' />
											<input name="merchantName" type="hidden"  id="merchantName" class="field"  value="<s:property value='merchantName' />" >
										</td>
										<td width="20%"><label for="Merchant ID"><strong>Merchant ID</strong></label></td>
										<td width="30%"> <s:property value='merchantID' />
											<input name="merchantID" type="hidden" id="merchantID" class="field" value="<s:property value='merchantID' />" >
										</td>
									</tr>
									<tr class="odd">
										<td ><label for="NBK Branch Location"><strong>NBK Branch Location</strong></label></td>
										<td > <s:property value='locationcity' />
											<input name="location" type="hidden" class="field" id="location" value="<s:property value='locationcity' />"  />
										</td>
										<td ><label for="KRA PIN"><strong>KRA PIN</strong></label></td>
										<td > <s:property value='kraPin' />
										<input name="kraPin" type="hidden" class="field" id="kraPin" value="<s:property value='kraPin' />"  />
										</td>	
									</tr> 
									<tr class="even">
										<td ><label for="Merchant Type"><strong>Merchant Type</strong></label></td>
										<td >
											<s:property value='merchantType' />
											<input name="merchantType" type="hidden" class="field" id="merchantType" value="<s:property value='merchantType' />"  />
										</td>
											<td ><label for="Member Type"><strong>Member Type</strong></label></td>
										<td >
											<s:property value='memberType' /> 
											<input name="memberType" type="hidden" class="field" id="memberType" value="<s:property value='memberType' />"  />
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
					<div class="row-fluid sortable" id="merchantAdminDetails">					
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
											<input name="merchantAdmin" type="hidden" class="field" id="merchantAdmin" value="<s:property value='#respData.merchantAdmin' />"  />
										 
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
						   		 	<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable ">
											<tr class="even">
												<td width="20%"><label for="Manager Name"><strong>Manager Name</strong></label></td>
												<td width="30%"> <s:property value='managerName' />
													<input name="managerName" type="hidden"  id="managerName" class="field"  value="<s:property value='managerName' />" maxlength="50" >
												</td>
												<td width="20%"><label for="Email"><strong>Email</strong></label></td>
							                    <td width="30%">  <s:property value='email' />
													<input name="email" type="hidden"  id="email" class="field"  value="<s:property value='email' />" maxlength="50" >
												</td>
											</tr>
											<tr class="odd">
												<td ><label for="Address Line 1"><strong>Address Line 1</strong></label></td>
												<td >  <s:property value='addressLine1' />
													<input name="addressLine1" type="hidden"  id="addressLine1" class="field"  value="<s:property value='addressLine1' />" maxlength="50" >
												</td>
												<td ><label for="Address Line 2 "><strong>Address Line 2 </strong></label></td>
												<td > <s:property value='addressLine2' />
													<input name="addressLine2" type="hidden"  id="addressLine2" class="field"  value="<s:property value='addressLine2' />" maxlength="50" >
												</td>
											</tr>
											<tr class="even">
												<td ><label for="Address Line 3 "><strong>Address Line 3</strong></label></td>
												<td > <s:property value='addressLine3' />
													<input name="addressLine3" type="hidden"  id="addressLine3" class="field"  value="<s:property value='addressLine3' />" maxlength="50" >
												</td>
												<td ><label for="Country"><strong>Country</strong></label></td>
											    <td ><s:property value='country' />
												 <input name="country" id="country" class="field" type="hidden" value="<s:property value='country' />" >
											     </td>
												
											</tr>
												<tr class="odd">
												<td ><label for="County"><strong>County</strong></label></td>
												<td ><s:property value='area' /><input name="area" type="hidden" class="field" id="area" value="<s:property value='area' />"  /></td> 
												<td><label for="City"><strong>City/Town</strong></label></td>
												<td > <s:property value='city' />
													<input name="city" type="hidden"  id="city" class="field"  value="<s:property value='city' />" maxlength="50" >
																								
										   </tr>
											<tr class="even">
											</td>
												<td ><label for="Postal Code"><strong>Postal Code</strong></label></td>
												<td ><s:property value='postalCode' /> <input name="postalCode" type="hidden" id="postalCode" class="field" value="<s:property value='postalCode' />"   />  </td>
												<td ><label for="P.O. Box Number "><strong>P.O. Box Number</strong></label></td>
												<td > <s:property value='poBoxNumber' />
													<input name="poBoxNumber" type="hidden"  id="poBoxNumber" class="field"  value="<s:property value='poBoxNumber' />" maxlength="50" >
												</td>
												
											</tr>
							               <tr class="odd">
										        <td ><label for="Telephone Number 1"><strong>Telephone Number 1</strong></label></td>
												<td>  <s:property value='telephoneNumber1' />
													<input name="telephoneNumber1" type="hidden"  id="telephoneNumber1" class="field"  value="<s:property value='telephoneNumber1' />" maxlength="50" >
												</td>
												<td ><label for="Telephone Number 2 "><strong>Telephone Number 2</strong></label>	</td>
												<td > <s:property value='telephoneNumber2' />
													<input name="telephoneNumber2" type="hidden"  id="telephoneNumber2" class="field"  value="<s:property value='telephoneNumber2' />" maxlength="50" >
												</td>
												
										   </tr>
							               <tr class="even">
										        <td ><label for="Mobile Number"><strong>Mobile Number</strong></label></td>
												<td ><s:property value='mobileNumber' />
													<input name="mobileNumber" type="hidden"  id="mobileNumber" class="field"  value="<s:property value='mobileNumber' />"   >
												</td>
												<td ><label for="Fax Number "><strong>Fax Number </strong></label></td>
												<td > <s:property value='faxNumber' />
													<input name="faxNumber" type="hidden"  id="faxNumber" class="field"  value="<s:property value='faxNumber' />"   >
												</td>
												
										   </tr>
										   	<tr class="odd">
											<td><label for="L/R Number"><strong>L/R Number</strong></label></td>
											<td ><s:property value='lrNumber' /> <input name="lrNumber" type="hidden" class="field" id="lrNumber" value="<s:property value='lrNumber' />"   /></td>
											<td ><label for="Primary Contact Person"><strong>Primary Contact Person</strong></label></td>
											<td > <s:property value='prmContactPerson' />
												<input name="prmContactPerson" id="prmContactPerson" class="field" type="hidden" value="<s:property value='prmContactPerson' />" >
											</td>
											
										</tr>
											<tr class="even">  
											<td><label for="Primary Contact Number"><strong>Primary Contact Number</strong></label></td>
											<td > <s:property value='prmContactNumber' />
												<input name="prmContactNumber" id="prmContactNumber" class="field" type="hidden" value="<s:property value='prmContactNumber' />" >
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
			 
				<input type="hidden" name="bankAcctMultiData" id="bankAcctMultiData" value="<s:property value="bankAcctMultiData"/>"></input> 
				
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
										<s:param name="jsonData" value="%{bankAcctMultiData}"/>  
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
						<div class="form-actions">
								<a  class="btn btn-danger" href="#" onClick="getPreviousScreen()">Back</a>
								<a  class="btn btn-success" href="#" onClick="modifyMerchant()">Confirm</a> 
						</div> 
      		</div>
		 
	</form>
</body>
</html>
