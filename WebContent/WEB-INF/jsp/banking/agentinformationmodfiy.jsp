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
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<s:set value="responseJSON" var="respData"/>
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
<script type="text/javascript" > 

var list = "dob,registrationDate".split(",");
var datepickerOptions = {
			showTime: false,
			showHour: false,
			showMinute: false,
  		dateFormat:'dd/mm/yy',
  		alwaysSetTime: false,
		changeMonth: true,
		changeYear: true
};	
	  
 $(function(){ 
	
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	}); 
 });

$(document).ready(function() {
	
	var mydata ='${responseJSON.TERMINAL_ID}';
	var terminalid="${responseJSON.terminalid}";
	
	if(mydata != '') {
    	var json = jQuery.parseJSON(mydata);
    	$.each(json, function(i, v) {
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    $('#terminalID').append(options);
    	    $('#terminalID').trigger("liszt:updated"); 
    	});
    	 $('#terminalID').val(terminalid); 
	}	
	
	
	var branchdata ='${responseJSON.BRANCH_DETAILS}';
	var branchlid="${responseJSON.branch}";
	if(branchdata != '') {
    	var json = jQuery.parseJSON(branchdata);
    	$.each(json, function(i, v) {
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    $('#branch').append(options);
    	    $('#branch').trigger("liszt:updated"); 
    	});
    	  $('#branch').val(branchlid);
	}	
	
	var zonedata ='${responseJSON.ZONE_DETAILS}';
	
	var znedata="${responseJSON.zone}";
	
	if(zonedata != '') {
    	var json1 = jQuery.parseJSON(zonedata);
    	$.each(json1, function(i, v) {
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    $('#zone').append(options);
    	    $('#zone').trigger("liszt:updated"); 
    	});
    	   $('#zone').val(znedata);
	}	
	
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

	   
}); 




$('#btn-submit').live('click', function () { 
	
  $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/updateagentinforamtion.action';
	$("#form1").submit(); 
	return true;
});
$('#btn-back').live('click', function () { 
	
	  $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/agentRegistration.action';
		$("#form1").submit(); 
		return true;
	});




</script>

</head> 
<body>
	<form name="form1" id="form1" method="post" action=""> 
			<div id="content" class="span10"> 
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="#">Modify agent Registration</a></li>
				
				</ul>
			</div>
			
			
			<div class="row-fluid sortable" id="agentRegistration"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Modify agent Registration
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

							</div>
						</div>

						<div id="primaryDetails" class="box-content">
							<fieldset>   
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd">
										<td width="20%"><strong><label for="Terminal ID">Terminal ID<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="terminalID" name="terminalID" class="chosen-select"
											    required="required" >
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Branch">Branch<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="branch" name="branch" class="chosen-select"
											    required="required" >
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Zone">Zone<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="zone" name="zone" class="chosen-select"
											    required="required" >
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Physical premis">Physical premis<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="physicalpremis" type="text" class="field" id="physicalpremis" value="${responseJSON.physicalpremis}"  />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Agent Bank Account Number">Agent Bank Account Number<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="agentbankacnumber" type="text" class="field" id="agentbankacnumber" value="${responseJSON.agentbankacnumber}"  />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Number of Outlets">Number of Outlets<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="numberofoutlets" type="text" class="field" id="numberofoutlets" value="${responseJSON.numberofoutlets}" />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Comercial Activity">Comercial Activity<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="comercialActivity" type="text" class="field" id="comercialActivity" value="${responseJSON.comercialActivity}"  />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Date of Birth">Date of Birth<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="dob" type="text" class="field" id="dob" value="${responseJSON.dob}"   />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Registration Date">Registration Date<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="registrationDate" type="text" class="field" id="registrationDate" value="${responseJSON.registrationDate}" />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									 
								</table>
							 </fieldset> 
							 
						
							 </div>
						</div>
					</div> <!-- end of span -->
			
				 
			<span id="multi-row-data" name="multi-row-data" class="multi-row-data" style="display:none" ></span>
			<div class="form-actions"> 
				
				<input type="button" class="btn btn-info" name="btn-submit" id="btn-submit" value="Submit" />&nbsp;
				<input type="button" class="btn" name="btn-back" id="btn-back" value="Back" /> 
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div> 
	</div> 
</form>
</body>
</html>
