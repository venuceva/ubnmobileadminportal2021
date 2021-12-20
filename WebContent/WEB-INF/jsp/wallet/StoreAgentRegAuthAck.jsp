<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString();%>
<%@taglib uri="/struts-tags" prefix="s"%> 

<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
input#abbreviation{text-transform:uppercase};
</style>
<s:set value="responseJSON" var="respData"/>
<link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.css' />	
<SCRIPT type="text/javascript">
var rowCount1=0;
var remarkRules = {
		 errorElement: "div",
		 errorPlacement: function(error, element) {

          if ( element.is(":radio") ) {
         	 error.appendTo( "div#errors1" );
          }
          else{
         	 error.appendTo( "div#errors" );
               }
      },
			rules : { 
				rmrk : { required : true },
				authradio : { required : true }
			},		
			messages : { 
				rmrk : { 	required : "Please Enter Remarks."	},
				authradio : { required : "Please Select One Option." }
				
					} 
			
		};
	
	
$(document).ready(function(){   
	
	 
 	$('#btn-submit').live('click',function() {
 		
 		var finalData = "";
 		 $("#form2").validate(remarkRules);
 		 $("#error_dlno").text(''); 
 		 
 		
 		var searchIDs="";
		$("div#auth-data input:radio:checked").each(function(index) {
			searchIDs=$(this).val();			
			$('#DECISION').val(searchIDs);
		});
		if(searchIDs.length == 0) {
			$("#error_dlno").text("Please check atleast one record."); 
		} else {
		 $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthRecordconfirm.action";
		 $("#form1").submit();	 
		}
		
		
	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true; 
		
		var actype= $('#acttype').val(); 		
		if (actype=="REG"){
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/authreglist.action';		
		}
		else{
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action';
		}
		$("#form1").submit();		
	}); 
	

});

</script> 


</head> 
<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
		
			<div id="content" class="span10">  
			    <div> 
				<ul class="breadcrumb">
				 <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				 <li> <a href="AuthorizationAll.action">Authorization</a> <span class="divider"> &gt;&gt; </span></li>
				 <li><a href="#"> ${type} Authorization Details </a></li>
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
					<i class="icon-edit"></i>Agent Customer Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>  
				<div class="box-content">
					<fieldset> 
						 
					 
				 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">  
							<tr class="even" id="accno" >
							<td width="25%"><label for="From Date"><strong>Core Bank Account Number</strong></label></td>
							<td width="25%">${responseJSON.accountno}<input type="hidden" name="accountno"  id="accountno"   value="${responseJSON.accountno}"   /></td>
							<td width="25%"><strong>User Id</strong></td>
							<td width="25%">${responseJSON.userid}
							<input type="hidden" name="userid"  id="userid"   value="${responseJSON.userid}"   />
							</td>
							</tr>
							<tr class="even" id="accno" >
							<td width="25%"><label for="From Date"><strong>Wallet Account Number</strong></label></td>
							<td width="25%">${responseJSON.walletaccountno}<input type="hidden" name="walletaccountno"  id="walletaccountno"   value="${responseJSON.walletaccountno}"   /></td>
							<td ><strong>Agent Customer Id</strong></td>
							<td >${responseJSON.id}
							<input type="hidden" name="id"  id="id"   value="${responseJSON.id}"   />
							</td>
							</tr>
					 	 <tr class="even">
							<td width="25%"><label for="From Date"><strong>Name</strong></label></td>
							<td width="25%">${responseJSON.fullname}<input type="hidden" name="fullname"  id="fullname"   value="${responseJSON.fullname}"   /></td>
							<td><label for="Product"><strong>Status</strong></label></td>
							<td>${responseJSON.status} <input type="hidden" name="status"  id="status"   value="${responseJSON.status}"   />  </td>
							</tr> 
									
				 </table>
				 
				 <input type="hidden" name="STATUS" id="STATUS" value="${STATUS}" />
  							 <input type="hidden" name="AUTH_CODE"  id="AUTH_CODE" value="${AUTH_CODE}"  />
							 <input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
							 <input type="hidden" name="DECISION" id="DECISION" />
							 <input type="hidden" name="remark" id="remark" />
							 <input type="hidden" name="type" id="type" value="${type}"/>
							 <input type="hidden" name="multiData" id="multiData"/>
				 
				 
				</fieldset> 
				</div>  
				
				
				
				<div class="row-fluid sortable">
						<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Store Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
									</div>
								</div>

								<div id="communicationDetails" class="box-content">
									<fieldset>
										<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
										   <tr class="odd">
										   <td width="25%"><label for="Address Line 1"><strong>Store Name</strong></label></td>
												<td width="25%">${responseJSON.storename}
												<input  type="hidden"   name="storename" id="storename" class="field" value="${responseJSON.storename}" />
												</td>
										   <td width="25%"><label for="Address Line 1"><strong>Store Id</strong></label></td>
												<td width="25%">${responseJSON.storeid}
												<input  type="hidden"   name="storeid" id="storeid" class="field" value="${responseJSON.storeid}" /></td>
												
										     	
										   
												
											</tr>
											
										

									</table>
								</fieldset>
							</div>
						</div>
				</div>
				
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
												<td width="25%"><label for="Nationality"><strong>Business Owner Name</strong></label></td>
												<td width="25%">${responseJSON.boname}
												<input type="hidden" class="field" name="boname" id="boname"  value="${responseJSON.boname}" /></td>
												<td width="25%"><label for="Nationality"><strong>Email</strong></label></td>
												<td width="25%">${responseJSON.email}
												<input type="hidden" class="field" name="email" id="email"  value="${responseJSON.email}" /></td>
											</tr>
										
										<tr class="even">
												 <td><label for="Nationality"><strong>Mobile Number</strong></label></td>
												<td>234${responseJSON.mobileno}
												<input type="hidden" class="field" name="mobileno" id="mobileno"  value="234${responseJSON.mobileno}" /></td>
												 <td width="25%"><label for="Address Line 1"><strong>Address Line</strong></label></td>
												<td width="25%">${responseJSON.address}
												<input  type="hidden"  maxlength="50" name="addressLine" id="addressLine" class="field" value="${responseJSON.address}" /></td>
											</tr>
										    

										   <tr class="odd">
										  
												
												<td><label for="Nationality"><strong>Area</strong></label></td>
												<td>${responseJSON.area}
												<input type="hidden" class="field" name="area" id="area" value="${responseJSON.area}"  /></td>
										     	<td width="25%"><label for="Local Government"><strong>Local Government</strong></label></td>
												<td width="25%" >${responseJSON.localGovernment}
												<input type="hidden" class="field" name="localGovernment" id="localGovernment"  value="${responseJSON.localGovernment}"/></td>
												
											</tr>
											<tr class="even">
											

												<td><label for="State"><strong>State</strong></label></td>
												<td>${responseJSON.state}
												 <input type="hidden" class="field" name="state" id="state"  value="${responseJSON.state}" /> </td>
												
												<td><label for="Nationality"><strong>Country</strong></label></td>
												<td>${responseJSON.country}
												<input type="hidden" class="field" name="country" id="country"  value="${responseJSON.country}" /></td>
											</tr>
											<tr class="even">
												<td><label for="Nationality"><strong>Latitude</strong></label></td>
												<td>${responseJSON.latitude}
												<input type="hidden" class="field" name="latitude" id="latitude"  value="${responseJSON.latitude}" /></td>
												<td><label for="Nationality"><strong>Longitude</strong></label></td>
												<td>${responseJSON.longitude}
												<input type="hidden" class="field" name="longitude" id="longitude" value="${responseJSON.longitude}"  /></td>
											</tr>
											

									</table>
								</fieldset>
							</div>
						</div>
				</div> 
				
	  </div>
	  </div> 
	  
	</form>	  
	
<form name="form2" id="form2" method="post"> 	
	  
	  
			
		<div id="auth-data"> 
				<ul class="breadcrumb">
				 <li> <strong>Authorize&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio2"  class='center-chk' type='radio' value='A' />&nbsp&nbsp </li>
				 <li> <strong>Reject&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio1"  class='center-chk' type='radio' value='R' /> &nbsp&nbsp&nbsp</li>
				<li><div id="error_dlno" class="errors"></div></li>
				</ul>
				
				
				     <span id ="error_dlno" class="errors"></span>
	           
		</div>  
 					 
	 
 </form>
 
    	<div class="form-actions" >
	         <input type="button" class="btn btn-success"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>&nbsp;
	         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input> &nbsp;
	         
       </div>  
 
 <script src="${pageContext.request.contextPath}/js/jquery.cleditor.min.js"></script>
 <script type="text/javascript">
 
$(function(){
	
if($('#tbody_data > tr').length < 1){
	$("#regacc").hide();
	}

	var auth=$('#STATUS').val();

	if ( auth == 'C'){
		$("#auth-data").hide();
		$("#btn-submit").hide();
		$("#rmrk").prop('disabled', true);
		$("#rmk").hide();

	}else if ( auth == 'D'){
		$("#auth-data").hide();
		$("#btn-submit").hide();
		$("#rmrk").prop('disabled', true);
		$("#rmk").hide();

	}
	else{
		$("#remarksInformation").hide();
		$("#auth-data").show();
		$("#btn-edit").show();

		}
	});	  
</script>
</body> 
</html>