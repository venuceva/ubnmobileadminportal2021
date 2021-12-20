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
$(document).ready(function() {
	var Subservice='${responseJSON.SubserviceName}';

	var refno = '${responseJSON.ref_no}';
	
	  $('#RefNumber').val(refno);
	var mydata ='${responseJSON.SubServiceList}';
	
	if(mydata != '') {
    	var json = jQuery.parseJSON(mydata);
    	$.each(json, function(i, v) {
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    $('#subServiceName').append(options);
    	});
    	  $('#subServiceName').append(Subservice);
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
function getServiceScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/serviceMgmtAct.action';
	$("#form1").submit();
	return true;
}
var enterserviceCode = "Please enter Service Code";
var enterserviceName = "Please enter Service Name";
var entersubserviceCode = "Please enter Sub Service Code";
var entersubserviceName = "Please select Sub Service Name";

var serviceCoderules = {
	required : true
};
var serviceNamerules = {
	required : true
};
var subServiceCoderules = {
	required : true
};
var subServiceNamerules = {
	required : true
};

var serviceCodemessages = {
	required : enterserviceCode
};
var serviceNamemessgaes = {
	required : enterserviceName
};
var subServiceCodemessages = {
	required : entersubserviceCode
};
var subServiceNamemessages = {
	required : entersubserviceName
};

var subServiceCreateRules= {
	rules : {
		serviceCode : serviceCoderules,
		serviceName : serviceNamerules,
		subServiceCode : subServiceCoderules,
		subServiceName : subServiceNamerules
	},
	messages : {
		serviceCode : serviceCodemessages,
		serviceName : serviceNamemessgaes,
		subServiceCode : subServiceCodemessages,
		subServiceName : subServiceNamemessages
	}
};
function createSubRejectService(){
	
	$("#form1").validate(subServiceCreateRules);
	if($("#form1").valid()){	
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/subServiceRejectSubmitAct.action';
		$("#form1").submit();
		return true;
	}else{
			return false;
	}

}

function getData(){
	var data=$("#subServiceName option:selected").text();
	$("#subServiceText").val(data);
}

</script>
 
</head>

<body>
	<form name="form1" id="form1" method="post" action=""> 
		<div id="content" class="span10"> 
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="#">Fee Management</a> <span class="divider"> &gt;&gt; </span></li>
				  <li><a href="#">Create Sub Service</a></li>
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
						<i class="icon-edit"></i>Create Sub Service
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable " >
								<tr class="even">
									<td ><strong><label for="Service ID">Service Code<font color="red">*</font></label></strong></td>
									<td><input name="serviceCode" type="text" id="serviceCode" class="field" value="${responseJSON.Servicecode}" readonly  style="width: 420px;" /></td>
								</tr>
								<tr class="odd">
									<td ><strong><label for="Service Name">Service Name<font color="red">*</font></label></strong></td>
									<td><input name="serviceName" type="text"  id="serviceName" class="field"  value="${responseJSON.serviceName}" readonly style="width: 420px;" /></td>

								</tr>
								<tr class="even">
									<td ><strong><label for="Sub Service Code">Sub Service Code<font color="red">*</font></label></strong></td>
									<td ><input name="subServiceCode"  type="text" id="subServiceCode" class="field" value="${responseJSON.SubserviceCode}"   readonly  style="width: 420px;" /> </td>
								</tr>
								<tr class="odd">
									<td><strong><label for="Sub Service Name">Sub Service Name<font color="red">*</font></label></strong></td>
									<td>
											<select id="subServiceName" name="subServiceName" data-placeholder="Choose SubServiceName..." 
												class="chosen-select" style="width: 420px;" required=true  onchange="getData()" >
														<option value="">Select</option>
											</select>
									 
									</td>
								</tr>
							</table>
							</fieldset>
					</div>
						<input name="subServiceText" type="hidden" id="subServiceText" class="field"  />
						<input type="hidden" name="RefNumber" id="RefNumber" value="">
			</div>
		</div>
	<div class="form-actions">
		<a  class="btn btn-info" href="#" onClick="getServiceScreen()">Back</a> &nbsp;&nbsp;
		<a  class="btn btn-success" href="#" onClick="createSubRejectService()">Submit</a>
	</div> 
</div>	
</form>
</body>
</html>
