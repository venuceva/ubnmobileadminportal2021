
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Brian Kiptoo">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %> 
    	 
 
<style type="text/css">
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
  font-weight: bold;
  color: red;
  padding: 2px 8px;
  margin-top: 2px;
}
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
 
</style>  
<script type="text/javascript" >

var assignServiceToTerminal = {
	     rules : {
	      selectedServices : { required : true }, 
	     },
	     messages : {
	      selectedServices : { 
	          required : "Please Select Atleast One Service."
	       }
	     }
	};  
	
$(document).ready(function() { 
	var mydata ='${responseJSON.SERVICE_LIST}';
	var json = jQuery.parseJSON(mydata);
	$.each(json, function(i, v) {
	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
	    $('#selectedServices').append(options);
	}); 
 
	
	mydata='${responseJSON.ASSIGNED_LIST}';
	json = jQuery.parseJSON(mydata); 
	$.each(json, function(ind,e) { 
		$("#selectedServices option[value]").each(function(index,val){ 
			//console.log($(this).attr('value') +"=="+ e.key.trim());
			if($(this).attr('value') == e.key.trim()) {
				$(this).attr("selected",true) ;
			}
		});
	});
	
	
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
 

function getGenerateMerchantScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>'+"/"+'<%=appName %>'+'/generateMerchantAct.action';
	$("#form1").submit();
	return true;
}


function assignUserToTerminal(){
	$("#form1").validate(assignServiceToTerminal);
	  if($("#form1").valid()){
	var selectedUserText="";
	$("#selectedServices option:selected").each(function () {
	   var $this = $(this);
	   if ($this.length) {
		var selText = $this.text();
		selectedUserText=selectedUserText+"#"+selText;
	   }
	});
	selectedUserText=selectedUserText.slice(1);
	$("#selectedUserText").val(selectedUserText);
				
	$("#form1")[0].action='<%=request.getContextPath()%>'+"/"+'<%=appName %>'+'/assignServiceTerminalCfmScreenAct.action';
	$("#form1").submit();
	return true;
	  }
	 else {
		    return false;
		  }
}
 

</script>
</head>

<body>
<form name="form1" id="form1" method="post" action="">	
			<div id="content" class="span10">  
			    <div>
						<ul class="breadcrumb">
						  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
						  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
						  <li><a href="#">Assign Services to Terminal</a></li>
						</ul>
				</div>
				
				<div class="row-fluid sortable"><!--/span-->
					<div class="box span12"> 
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Terminal Information
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>  
						<div class="box-content" id="terminalDetails">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="even">
										<td ><label  for="Merchant ID"><strong>Merchant ID</strong></label></td>
										<td> ${responseJSON.merchantID}
											<input name="merchantID" type="hidden" id="merchantID" class="field" value=" ${responseJSON.merchantID}">
										</td>
										<td><label  for="Store ID"><strong>Store ID</strong></label></td>
										 <td > ${responseJSON.storeId}
											<input name="storeId"  type="hidden" id="storeId" class="field"  value=" ${responseJSON.storeId}" readonly  > 
										</td>
									</tr>
									<tr class="odd">
										<td><label  for="Terminal ID"><strong>Terminal ID</strong></label></td>
										<td> ${responseJSON.terminalID}
											<input name="terminalID" type="hidden"  id="terminalID" class="field" value="${responseJSON.terminalID}"  maxlength="8">
										</td>
										<td></td>
										<td > </td>		
									</tr>							
								</table>
							</fieldset>
						 
						</div>
						</div>
						</div>
				<div class="row-fluid sortable"><!--/span-->
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Services Information
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							</div>
						</div>  
						<div id="userDetails" class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="even">
										<td><label  for="Select Users"><strong>Select Services<font color="red">*</font></strong></label></td>
										<td class="odd">
											<select multiple id="selectedServices" name="selectedServices" 
													data-placeholder="Choose Services to Assign Terminals..." 
												class="chosen-select" style="width: 270px;">
											</select>
									   </td>
									</tr>							
								</table>
							</fieldset>
						</div>
						<input type="hidden" name="selectedUserText" id="selectedUserText" value="${responseJSON.selectedUserText}"/> 
					</div>
                </div>  
			<div class="form-actions">
				<a  class="btn btn-danger" href="generateMerchantAct.action" onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp;
				<a  class="btn btn-success" href="#" onClick="assignUserToTerminal()">Submit</a>
			</div>	
		</div><!--/#content.span10-->
</form>
 </body>
</html>
