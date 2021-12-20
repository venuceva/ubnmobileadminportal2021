<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %> 
 

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
</style>
<script type="text/javascript">

$(function() {  
	
	if("<s:property value='accBean.supercriteria' />"=="UNION_BANK_CUSTOMER"){
		$("#rmtbl").css({"display":""});
		$("#rmtbl1").css({"display":"none"});
		$("#rmtbl2").css({"display":"none"});
		$("#rmtbl3").css({"display":"none"});
	}
	
	if("<s:property value='accBean.supercriteria' />"=="ALEDIN_AGENCY"){
		$("#rmtbl").css({"display":"none"});
		$("#rmtbl1").css({"display":""});
		 if("<s:property value='accBean.aledinagency' />"=="SUB_AGENT"){
    		 $("#rmtbl2").css({"display":""}); 
    		 $("#rmtbl3").css({"display":""});
    	 }
	}
	
	 if($('#apptype').val()=="AGENTWALLET"){
	    	
	    	$("#superadmin").css({"display":""});
	    	$("#accno").css({"display":""});
	    }
	
	 
    if($('#apptype').val()=="AGENTWALLET"){
    	
    	$("#superadmin").css({"display":""});
    	$("#accno").css({"display":""});
    }
	
	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){
		
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/walletregcustinfoCnfAct.action'; 
		$("#form1").submit();
		
		 $(this).prop('disabled', true);
			$("#btn-submit").hide();
		return true; 
	}); 
});

</script> 
</head> 
<body>
<form name="form1" id="form1" method="post"> 
		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					   <li> <a href="#">Wallet Customer Registration</a>  </li> 
 					</ul>
				</div>  

				<table>
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
					<i class="icon-edit"></i>Customer Details Confirmation
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>  
				<div class="box-content">
					<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details"> 
						<tr class="even" id="accno" style="display:none">
							<td width="25%"><label for="From Date"><strong>Core Bank Account Number</strong></label></td>
							<td width="25%"><s:property value='accBean.accountno' /><input type="hidden" name="accountno"  id="accountno"   value="<s:property value='accBean.accountno' />"   /></td>
							<td  width="25%"><label for="From Date"><strong>Branch Code</strong></label></td>
							<td  width="25%">
							<s:property value='accBean.branchcode' /> <input type="hidden" name="branchcode"  id="branchcode"   value="<s:property value='accBean.branchcode' />"   />  </td>
							</tr>   
						<tr class="even">
							<td width="25%"><label for="From Date"><strong>Full Name</strong></label></td>
							<td width="25%"><s:property value='accBean.fullname' />
							<input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   /></td>
							<td></td>
						    <td></td>
							
							</tr>
						<tr class="even">
						<td  ><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value="accBean.dob"/>
							<input type="hidden" maxlength="10"  class="dob" id="dob" name="accBean.dob" required=true  readonly="readonly" value="<s:property value="accBean.dob"/>"/>
							</td>
						
							<td ><label for="From Date"><strong>Email ID</strong></label></td>
							<td ><s:property value='accBean.email' />
							<input type="hidden" name="email"  id="email"   value="<s:property value='accBean.email' />"   />  </td>
							  
						</tr>
						<tr class="even">
						
							
						   <td><label for="To Date"><strong>Mobile Number</strong></label> </td>
					       <td ><s:property value='accBean.telephone' />
					    
								<input type="hidden" value='<s:property value='accBean.telephone' />' class="field" maxlength="13" name="telephone" id="telephone"  />
 							
						     
						   </td> 
						     <td><strong>Gender</strong></td>
						      <td> <s:property value='accBean.gender' />
						    
						      <input type="hidden" name="gender"  id="gender"  class="field"  value="<s:property value='accBean.gender' />"   />
						      </td>
 						</tr>
						 <tr class="even" id="superadmin" name="superadmin" style="display:none">
							<td><label for="Product"><strong>Super Admin</strong></label></td>
							<td><s:property value='accBean.product' /> <input type="hidden" name="product"  id="product"   value="<s:property value='product' />"   />  </td>
							<td><label for="Description"><strong>Description</strong></label> </td>
							<td><s:property value='prodesc' />
							<input type="hidden" value='<s:property value='prodesc' />' name="prodesc" id="prodesc" readonly style="width:130px;" /> </td>
						</tr>
						
						<tr class="even" id="rmtbl" style="display:none">
						
							
						   <td><label for="To Date"><strong>RM Code</strong></label> </td>
					       <td ><s:property value='accBean.staffid' />
					    
								<input type="hidden"  class="field"  name="staffid" id="staffid"  value="<s:property value='accBean.staffid' />" />
 							
						     
						   </td> 
						     <td>
						     
						     <input type="hidden"  class="field" value="<s:property value='accBean.supercriteria' />"  name="supercriteria" id="supercriteria"  />
						     </td>
						     <td></td>
 						</tr>	
 						
 						<tr class="even" id="rmtbl1" style="display:none">
						
							
						   <td><label for="To Date"><strong>Select Agency Type</strong></label> </td>
					       <td >
					       <s:property value='accBean.aledinagency' />
					    
								<input type="hidden"  class="field"  name="aledinagency" id="aledinagency" value="<s:property value='accBean.aledinagency' />" />
					    
						     
						   </td> 
						     <td></td>
						     <td></td>
 						</tr>
 						
 						
 						
 						
 						<tr class="even" id="rmtbl2" style="display:none">
						
							
						   
						    
						     <td><label for="To Date"><strong>Aledin Agency Name</strong></label>  </td>
					       <td ><s:property value='accBean.agencyname' />
					       <input type="hidden"  class="field"  name="agencyname" id="agencyname" value="<s:property value='accBean.agencyname' />" />
					       </td> 
					        <td>
						     
						     </td>
						     <td>
						    
						     </td>
 						</tr>
 						<tr class="even" id="rmtbl3" style="display:none">
						
							
						   
						     <td>
						     <label for="To Date"><strong>Aledin Agency Mobile No</strong></label> 
						     
						     </td>
						     <td><s:property value='accBean.agencymobileno' />
						      <input type="hidden"  class="field"  name="agencymobileno" id="agencymobileno"  value="<s:property value='accBean.agencymobileno' />" />
						     </td>
						     <td><label for="To Date"><strong>Aledin Agency Customer Id</strong></label>  </td>
					       <td ><s:property value='accBean.agencycustid' />
					       <input type="hidden"  class="field"  name="agencycustid" id="agencycustid"  value="<s:property value='accBean.agencycustid' />" />
					       </td> 
 						</tr>
 						
 											
				 </table>
				  <input type="hidden" name="apptype" id="apptype" value='<s:property value='accBean.apptype' />'   />
				</fieldset> 
				
				
				 <div class="row-fluid sortable">
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
										   		
												<td ><label for="Address Line 1"><strong>Address Line</strong></label></td>
												<td ><s:property value="accBean.addressLine"/>
												<input type="hidden"  name="addressLine" id="addressLine" class="field"    required='true' value="<s:property value="accBean.addressLine"/>" /></td>
												
											    <td><label for="Nationality"><strong>Nationality</strong></label></td>
												<td><s:property value="accBean.nationality"/>
												<input type="hidden"  name="nationality" id="nationality" class="field"     required='true' value="<s:property value="accBean.nationality"/>" />
												</td>
												 
										  
										   </tr>
										    
											<tr class="odd">
										   <td width="25%"><label for="IDNumber"><strong>Branch</strong></label>	</td>
												<td width="25%"><s:property value="accBean.agbranch"/>
												<input name="agbranch" type="hidden" class="field" id="agbranch"  value="<s:property value="accBean.agbranch"/>"/></td>
										   
										    
											<td><label for="Local Government"><strong>Cluster</strong></label></td>
												<td><s:property value="accBean.cluster"/>
					                 <input type="hidden"  name="cluster" id="cluster" class="field"    required='true' value="<s:property value="accBean.cluster"/>" />
												 
													 </td>
											</tr>
										   <tr class="odd">
										   <td width="25%"><label for="IDNumber"><strong>Area</strong></label>	</td>
												<td width="25%"><s:property value="accBean.idnumber"/>
												<input name="idnumber" type="hidden" class="field" id="idnumber"  value="<s:property value="accBean.idnumber"/>"/></td>
										   <td><label for="State"><strong>State</strong></label></td>
												<td> <s:property value="accBean.state"/>
										<input type="hidden" name="state" id="state" class="field"  required='true' value="<s:property value="accBean.state"/>" />
											
														 </td>
										    
											
											</tr>
										   <tr class="odd">
												
										<td><label for="Local Government"><strong>Local Government</strong></label></td>
												<td><s:property value="accBean.localGovernment"/>
					                 <input type="hidden"  name="localGovernment" id="localGovernment" class="field"    required='true' value="<s:property value="accBean.localGovernment"/>" />
												 
													 </td>
												<td><label for="Nationality"><strong>Country</strong></label></td>
												<td><s:property value="accBean.country"/>
											<input type="hidden" name="country" id="country" class="field"     required='true' value="<s:property value="accBean.country"/>" />
												
												</td>
												
										   </tr>
										   
										 
										  

									</table>
								</fieldset>
							</div>
						</div>
				</div>
				
				
				
				</div>  
				
	  </div>
	  </div> 
	  
	
										<input type="hidden" name="langugae"  id="langugae"   value="English"  />
										<input type="hidden" name="telco"  id="telco"   value=""  />
										<input type="hidden" name="isocode"  id="isocode"   value=""  />
										<input type="hidden" name="IDType"  id="IDType"   value=""  />
			
	
		<div >
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">&nbsp;Home </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Confirm</a>					 
		</div> 
	</div> 	 
 </form>
 <script type="text/javascript">
$(function(){
	
	var actlen = $('#tbody_data > tr').length;
	
	if(actlen == 0){
		$('#add-new-act').hide();
	}else {
		$('#add-new-act').show();
	}
	 
});  
</script>
</body> 
</html>