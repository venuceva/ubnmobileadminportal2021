
<!DOCTYPE html>
<html lang="en">
<%@taglib uri="/struts-tags" prefix="s"%> 
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Brian Kiptoo">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 <s:set value="responseJSON" var="respData"/>
 
<script type="text/javascript">

$( document ).ready(function() {
	
 /* 	var ses= "<s:property value="contactInfoMultidata"/>";
	    alert("11111"+ses); */
    $("#DocumentInformation").hide();
    var data="${bankAcctMultiData}";
});
 
function createMerchant(){ 
	var str = $("#password").val();
	alert(str);
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantCreateConfirmAct.action';
	$("#form1").submit();
	return true;
		 
}

function getPreviousScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/getMerchantCreateScreenAct.action';
	$("#form1").submit();
	return true;
}


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
</style > 
</head>

<body>
	<form name="form1" id="form1" method="post" action="">
		<div id="content" class="span10"> 
			 
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="generateMerchantAct.action">Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
				  <li><a href="#">Create New Merchant Confirmation</a></li>
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
		<div class="row-fluid sortable">
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
								<td width="30%">	<s:property value="merchantName"/>
									<input name="merchantName" type="hidden"  id="merchantName" class="field"  value="<s:property value="merchantName"/>" maxlength="50">
								</td>
								<td width="20%"><label for="Merchant ID"><strong>Merchant ID</strong></label></td>
								<td width="30%"> <s:property value="merchantID"/>
									<input name="merchantID" type="hidden" id="merchantID" class="field" value="<s:property value="merchantID"/>" maxlength="15" >
								</td>
							</tr>
							<tr class="odd">
								<td><label for="Primary Store Name"><strong>Primary Store Name</strong></label></td>
								<td> <s:property value="storeName"/>
									<input name="storeName" type="hidden" class="field" id="storeName" value="<s:property value="storeName"/>" />
								</td>
								<td ><label for="Primary Store ID"><strong>Primary Store ID</strong></label></td>
								<td > <s:property value="storeId"/>
									<input name="storeId"  type="hidden" id="storeId" class="field"  value="<s:property value="storeId"/>" > 
								</td>
							</tr>
							<tr class="even">
								<td ><label for="Bank Branch"><strong>Bank Branch</strong></label></td>
								<td > <s:property value="locationVal"/>
									<input name="location" type="hidden" class="field" id="location" value="<s:property value="location"/>" />
								</td>
								<td ><label for="KRA PIN"><strong>KRA PIN</strong></label></td>
								<td > <s:property value="kraPin"/>
									<input name="kraPin" type="hidden" class="field" id="kraPin" value="<s:property value="kraPin"/>"  />
								</td>	
							</tr> 
							<tr class="odd">
								<td ><label for="Merchant Type"><strong>Merchant Type</strong></label></td>
								<td > <s:property value="merchantTypeVal"/> <input name="merchantType" type="hidden" class="field" id="merchantType" value="<s:property value="merchantType"/>"  />
								</td>
								<td ><label for="Merchant Type"><strong>Merchant Type</strong></label></td>
								<td > <s:property value="mrcode"/> <input name="mrcode" type="hidden" class="field" id="mrcode" value="<s:property value="mrcode"/>"  />
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
							<%-- <tr class="even">
								<td width="20%"><label for="Manager Name"><strong>Manager Name</strong></label></td>
								<td width="30%"> <s:property value="managerName"/>
									<input name="managerName" type="hidden" id="managerName" class="field" value="<s:property value="managerName"/>" maxlength="50" >
								</td>
								<td width="20%"><label for="Email"><strong>Email</strong></label></td>
								<td width="30%"> <s:property value="email"/>
									<input name="email" type="hidden"  id="email" class="field"  value="<s:property value="email"/>" >
								</td>
							</tr> --%>
							<tr class="odd">
								<td ><label for="Address Line 1"><strong>Address Line 1</strong></label></td>
								<td > <s:property value="addressLine1"/>
									<input name="addressLine1" id="addressLine1" class="field" type="hidden"  maxlength="50" value="<s:property value="addressLine1"/>"  >
								</td>
								<td ><label for="Address Line 2 "><strong>Address Line 2 </strong></label></td>
								<td > <s:property value="addressLine2"/>
									<input name="addressLine2" type="hidden" class="field" id="addressLine2"   value="<s:property value="addressLine2"/>" />
								</td>
							</tr>
							<tr class="even">
								<td ><label for="Address Line 3 "><strong>Address Line 3</strong></label></td>
								<td > <s:property value="addressLine3"/>
									<input name="addressLine3" type="hidden" class="field" id="addressLine3" value="<s:property value="addressLine3"/>" />
								</td>
								<td ><label for="Country"><strong>Country</strong></label></td>
								<td > <s:property value="country"/>
									<input name="country" id="country" class="field" type="hidden" value="<s:property value="country"/>" >
								</td>
							</tr>
							<tr class="odd"> 
								<td ><label for="County"><strong>County</strong></label></td>
								<td > <s:property value="area"/>
									<input name="area" type="hidden" class="field" id="area" value="<s:property value="area"/>" />
								</td>
								<td><label for="City"><strong>City/Town</strong></label></td>
								<td > <s:property value="city"/>
									<input name="city" type="hidden" class="field" id="city"  value="<s:property value="city"/>" />
								</td> 
							</tr>
							<tr class="even">
							    <td><label for="Postal Code"><strong>Postal Code</strong></label></td>
								<td > <s:property value="postalCode"/>
									<input name="postalCode" type="hidden" class="field" id="postalCode"  value="<s:property value="postalCode"/>" />
								</td>
								<td ><label for="P.O. Box Number "><strong>P.O. Box Number</strong></label></td>
								<td > <s:property value="poBoxNumber"/>
									<input name="poBoxNumber" type="hidden" id="poBoxNumber" class="field" value="<s:property value="poBoxNumber"/>" />
								</td>
							</tr>
						   <tr class="even">
						        <td ><label for="Telephone Number 1"><strong>Telephone Number 1</strong></label></td>
								<td> <s:property value="telephoneNumber1"/>
									<input name="telephoneNumber1" type="hidden" id="telephoneNumber1" class="field" value="<s:property value="telephoneNumber1"/>" />
								</td>
								<td ><label for="Telephone Number 2 "><strong>Telephone Number 2</strong></label>	</td>
								<td > <s:property value="telephoneNumber2"/>
									<input name="telephoneNumber2" type="hidden" class="field" id="telephoneNumber2"  value="<s:property value="telephoneNumber2"/>" />
								</td>
						   </tr>
						   <tr class="odd">
						        
								<td ><label for="Fax Number "><strong>Fax Number </strong></label></td>
								<td > <s:property value="faxNumber"/>
									<input name="faxNumber"  type="hidden" class="field" id="faxNumber" value="<s:property value="faxNumber"/>"  />
								</td>
							    <td><label for="L/R Number"><strong>L/R Number</strong></label></td>
								<td > <s:property value="lrNumber"/> 
								    <input name="lrNumber" type="hidden" class="field" id="lrNumber" value="<s:property value="lrNumber"/>" required='true' />
								</td>
						 	</tr>
						 	<tr class="even">
						 		<td ><label for="Google Co-Ordinates"><strong>Google Maps Latitude</strong></label></td>
								<td > <s:property value="googleCoOrdinates"/>
									<input name="googleCoOrdinates"  type="hidden" class="field" id="googleCoOrdinates" value="<s:property value="googleCoOrdinates"/>"  />
								</td>
							    <td><label for="Latitude"><strong>Google Maps Longitude</strong></label></td>
								<td > <s:property value="latitude"/> 
								    <input name="latitude" type="hidden" class="field" id="latitude" value="<s:property value="latitude"/>" required='true' />
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
										<i class="icon-edit"></i>Bank Account Information
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
									</div>
								</div>
								<div id="bankRelationShipDetails" class="box-content">
									<fieldset>
										<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
										      
											<tr class="even">
												<td width="20%"><label for="Account Number"><strong>Account Number</strong></label></td>
												<td width="30%"><s:property value="accountNumber"/> 
												<input name="accountNumber"  type="hidden" class="field" id="accountNumber" value="<s:property value="accountNumber"/>"  />
												</td>
												<td width="20%"><label for="RelationShip Manager Number"><strong>RelationShip Manager Number</strong></label></td>
												<td width="30%"><s:property value="relationShipManagerNumber"/> 
												<input name="relationShipManagerNumber"  type="hidden" class="field" id="relationShipManagerNumber" value="<s:property value="relationShipManagerNumber"/>"  />
												</td>
											</tr>
											
											<tr class="even">
												
												<td width="20%"><label for="RelationShip Manager Email"><strong>RelationShip Manager Email</strong></label></td>
												<td width="30%"><s:property value="relationShipManagerEmail"/> 
												<input name="relationShipManagerEmail"  type="hidden" class="field" id="relationShipManagerEmail" value="<s:property value="relationShipManagerEmail"/>"  />
												</td>
												<td></td>
												<td></td>
												
											</tr>
											
									</table>
								</fieldset>
							</div>
						</div>
				</div>
				
		<div class="row-fluid sortable"><!--/span--> 
			<div class="box span12">
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Contact Details
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

					</div>
				</div>  
							<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
								id="contactInfo">
								  <thead>
										<tr>
											<th>Sno</th>
											<th>Primary Contact  Number</th>
											<th>Primary COntact Person</th>
											<th>Mobile Number</th> 
										</tr>
								  </thead>    
								 <tbody   id="tbody_data5">
									<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{contactInfoMultidata}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
									</s:bean> 
									<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" >   
									<script type="text/javascript">
													var hiddenNames1 = "";
													$(function(){
														$('#contactInfo').find('input[type=text],input[type=hidden],select').each(function(index){ 
															var nameInput = $(this).attr('name'); 
															if(nameInput != undefined) {
															  if(index == 0)  {
																hiddenNames1 += nameInput;
															  } else {
																hiddenNames1 += ","+nameInput; 
															  }  
															} 
														}); 
														var data1 = "<s:property />";
														data1 = data1.split(",");
														$("#multi-row-bank").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
														 
													});
													</script>
										 
												
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
													<!-- <th>Visa Mid</th> -->
													<th>MPesa Agent Number</th>
													<th>Airtel Money Agent Number</th>
													<th>Orange Money Agent Number</th>
													<th>Master Card Agent Number</th>
 												</tr>
										</thead>    
										<tbody id="tbody_data3">
											<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
												<s:param name="jsonData" value="%{agentMultiData}"/>  
												<s:param name="searchData" value="%{'#'}"/>  
											</s:bean> 
											<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" >   
														<script type="text/javascript">
															var hiddenNames1 = "";
															$(function(){
																$('#agentBasedInfo').find('input[type=text],input[type=hidden],select').each(function(index){ 
																	var nameInput = $(this).attr('name'); 
																	if(nameInput != undefined) {
																	  if(index == 0)  {
																		hiddenNames1 = nameInput;
																	  } else {
																		hiddenNames1 += ","+nameInput; 
																	  }  
																	} 
																}); 
																var data1 = "<s:property />";
																data1 = data1.split(",");
														
																$("#multi-row-agent").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
															});
															</script> 
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
				<a  class="btn btn-danger" href="#" onClick="getPreviousScreen()">Back</a>
				<a  class="btn btn-success" href="#" onClick="createMerchant()">Save</a>
		</div> 
	</div> 
	<input type="hidden" name="agentMultiData" id="agentMultiData" value="<s:property value="agentMultiData"/>"></input>
	<input type="hidden" name="contactInfoMultidata" id="contactInfoMultidata" value="<s:property value="contactInfoMultidata"/>"></input>
	
	<input type="hidden" name="password" id="password" value="<s:property value="password"/>"></input>
	<input type="hidden" name="encryptPassword" id="encryptPassword" value="<s:property value="encryptPassword"/>"></input>
	<input type="hidden" name="otp" id="otp" value="<s:property value="otp"/>"></input>
	
	

 </form>
</body>
</html>
