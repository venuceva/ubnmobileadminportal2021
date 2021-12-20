
<!DOCTYPE html>
<html lang="en">
<%@taglib uri="/struts-tags" prefix="s"%> 
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

 
<script type="text/javascript" >

var createTerminalRules = {
		   rules : {
		    merchantID : { required : true },
			storeId : { required : true },
			terminalID : { required : true },
			modelNumber : { required : true },
			serialNumber : { required : true },
			terminalMake : { required : true },
			//validFrom : { required : true },
			//validThru : { required : true },
			terminalDate : { required : true },
			status : { required : true }
		   },  
		   messages : {
		    merchantID : { 
		       required : "Merchant Id Missing"
		        },
			storeId : { 
		       required : "Store Id Missing"
		        },
			terminalID : { 
		       required : "Please Enter Terminal Id"
		        },
			modelNumber : { 
		       required : "Please Enter Model No"
		        },
			serialNumber : { 
		       required : "Please Enter Serial No"
		        },
			terminalMake : { 
		       required : "Please Enter Terminal Make"
		        },
			/*validFrom : { 
		       required : "Please select Valid From"
		        },
			validThru : { 
		       required : "Please select Valid Thru"
		        },*/
			terminalDate : { 
		       required : "Please Select Date"
		        },
		    status : { 
		       required : "Please Select Terminal Status"
		        }
		   } 
		 };
		 
$(document).ready(function() { 
	
	var mydata ='${responseJSON.MERCHANT_LIST}';
    	//alert(mydata);
    	var json = jQuery.parseJSON(mydata);
    	//alert(json);
    	$.each(json, function(i, v) {
    	    // create option with value as index - makes it easier to access on change
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    // append the options to job selectbox
    	    $('#merchantID').append(options);
    	});
	var mydata ='${responseJSON.STORE_LIST}';
    	//alert(mydata);
    	var json = jQuery.parseJSON(mydata);
    	//alert(json);
    	$.each(json, function(i, v) {
    	    // create option with value as index - makes it easier to access on change
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    // append the options to job selectbox
    	    $('#storeId').append(options);
    	});
		
	var merchantId = "${responseJSON.merchantID}";
	$('#merchantID option[value="' + merchantId + '"]').prop('selected', true);
	
	var storeId = "${responseJSON.storeId}";
	$('#storeId option[value="' + storeId + '"]').prop('selected', true); 
	
});

//For Closing Select Box Error Messages_Start
$(document).on('change','select',function(event) { 

if($('#'+$(this).attr('id')).next('label').text().length > 0) {
 $('#'+$(this).attr('id')).next('label').text(''); 
 $('#'+$(this).attr('id')).next('label').remove();
}

});
//For Closing Select Box Error Messages_End
 

function getGenerateMerchantScreen(){
	
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/TerminalGrid.action';
	$("#form1").submit();
	return true;
}

function modifyTerminal(){
	$("#form1").validate(createTerminalRules);
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modifyTerminalAct.action';
	$("#form1").submit();
	return true;
	
	<%-- var serialNumber =$('#serialNumber').val();
	alert(serialNumber);
	var queryString = "method=serialNumberMethod&serialNumber="+serialNumber;	
	
	$.getJSON("postJson.action", queryString,function(data) {

		userstatus = data.status;
		alert(userstatus);
		console.log(data);

		v_message = data.message;
		alert(v_message);
		if(userstatus == "FOUND") {
			if(v_message != "SUCCESS") {
				$('#error_dlno').text(v_message);
			}
		} else {
		
			
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modifyTerminalAct.action';
				$("#form1").submit();
				return true;
			
		}
		alignSerialNo(textMess);
	}); --%>
}

var list = "validFrom,validThru,terminalDate".split(",");
var datepickerOptions = {
			showTime: false,
			showHour: false,
			showMinute: false,
  		dateFormat:'dd-mm-yy',
  		alwaysSetTime: false,
		changeMonth: true,
		changeYear: true
};

$(function() {
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	}); 
	  
/* 	  $("#merchantID").change(function(){
	   //var formInput=$("#form1").serialize()+'&merchantId='+$('#merchantID').val();
	   var formInput='merchantId='+$('#merchantID').val();
	   $.getJSON('retriveStoresAct.action', formInput,function(data) {
	     	var json = data.responseJSON.STORE_LIST;
	     	document.form1.storeId.options.length=1;
	    	$.each(json, function(i, v) {
				var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);  
				$('#storeId').append(options);
			});
	   });
	  
	   return false;
	  
	  }); */
	  
	  
		$('#merchantID').live('change', function () {

			var merchantId=$('#merchantID').val();
			var formInput='merchantId='+merchantId;

			$.getJSON('retriveStoresAct.action', formInput,function(data) {

				var json = data.responseJSON.STORE_LIST;
				$('#storeId').find('option:not(:first)').remove();
				$('#storeId').trigger("liszt:updated");
				$.each(json, function(i, v) {
					
					var options = $('<option/>', {value: v.val, text: v.val+'~'+v.key}).attr('data-id',i);
					$('#storeId').append(options);
					$('#storeId').trigger("liszt:updated");
				});
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

var flagAcc = true;
function SeriaNumberCheck()
{
	var serialNumber =$('#serialNumber').val();
	alert(serialNumber);
		var formInput='serialNumber='+serialNumber;	
		 $.getJSON('CheckSerialNumber.action', formInput,function(data) {
		alert(serialNumber);
		var count = data.responseJSON.RESULT_COUNT;
	if(count==1)
		{
			alert("This Serial Number Already Exsited With Another Merchant");
			flagAcc=false;
		}
	
		 });
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
					  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Modify Terminal</a></li>
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
								<td width="20%"><label for="Merchant ID"><strong>Merchant ID<font color="red">*</font></strong></label></td>
								<!-- <td width="30%"><input name="merchantID" type="text" id="merchantID" class="field" value="mer" readonly></td> -->
								<td width="20%">
								
								<select id="merchantID" name="merchantID"  data-placeholder="Choose  Merchant Id..." class="chosen-select-width" style="width: 220px;" required=true >
								<option value="">Select</option>
								</select></td>
								
								<td width="20%"><label for="Store ID"><strong>Store ID<font color="red">*</font></strong></label></td>
								<!-- <td width="30%"><input name="storeId"  type="text" id="storeId" class="field"  value="str" readonly  > </td> -->
								<td>
								<select id="storeId" name="storeId" data-placeholder="Choose office Store Id..." class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option></select>
							</td>
							</tr>
							<tr class="odd">
								<td><label for="Terminal ID"><strong>Terminal ID<font color="red">*</font></strong></label></td>
								<td><input name="terminalID" type="text"  id="terminalID" class="field" value="${responseJSON.terminalID}" readonly></td> 
								<td ><label for="Terminal Make"><strong>Terminal Make <font color="red">*</font></strong></label></td>
								<td ><input name="terminalMake" type="text" class="field" id="terminalMake" value=" ${responseJSON.terminalMake}" /></td>
								
							</tr> 		
							 <tr  class="even">
								<td><label for="MSIDN Number"><strong>MSIDN Number<font color="red">*</font></strong></label></td>
								<td><input name="MsisdnNumber" id="MsisdnNumber" class="field" value="" type="text" maxlength="8" /> <span id="MsisdnNumber_err" class="errmsg"> </span></td>		
								<td ><label for="Serial Number"><strong>Serial Number<font color="red">*</font></strong></label></td>
								<td ><input name="serialNumber" id="serialNumber" class="field" value="" type="text" maxlength="8" /> <span id="serialNumber_err" class="errmsg"> </span></td>
								
							</tr>
							
							<tr class="odd">
								<!-- <td><strong><label for="Terminal Usage">Terminal Usage<font color="red">*</font></label></strong></td>
								<td ><input name="terminalUsage" type="text" class="field" id="terminalUsage" value="${terminalUsage}" /></td> -->
								<td ><label for="Model Number "><strong>Model Number<font color="red">*</font></strong></label></td>
								<td ><input name="modelNumber"  type="text" id="modelNumber" class="field" value=" ${responseJSON.modelNumber}"  /> </td>
								
								<td></td>
								<td></td>
							</tr> 
							 <tr  class="even" style="display:none">
								<td><label for="Valid From"><strong>Valid From (dd-mon-yyyy)<font color="red">*</font></strong></label></td>
								<td><input name="validFrom" id="validFrom" class="field" value="${validFrom}" type="text"  /></td>
								<td><label for="Valid Thru"><strong>Valid Thru (dd-mon-yyyy)<font color="red">*</font></strong></label></td>
								<td><input name="validThru" type="text" class="field" id="validThru"   value="" /></td>	
							</tr>
							<tr class="even">
								<td ><label for="Status"><strong>Status<font color="red">*</font></strong></label></td>
								<td> <select id="status" name="status" data-placeholder="Choose status..." 
												class="chosen-select" style="width: 220px;" required="true" >
												<option value="">Select</option>
												<option value="Active" selected>Active</option>
												<option value="Close">Close</option>
									</select>
								</td>
								 <td><label for="date"><strong>Create Date<font color="red">*</font></strong></label></td>
								 <td><input name="terminalDate"  type="text" id="terminalDate" class="field"  value=" ${responseJSON.TERMINAL_DATE}" readonly="readonly" > </td>
							</tr>
						</table>
						<input name="pinEntry" type="hidden" class="field" id="pinEntry"  value="NO"  />
						<input name="terminalUsage" id='terminalUsage' type="hidden" class="field" id="pinEntry"  value=" "  />
						</fieldset>
						</div> 
					</div> 
				</div>
			<!-- <div class="row-fluid sortable">  
				<div class="box span12"> 
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>TMK Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

						</div>
					</div> 
					<div id="feeDetails" class="box-content">
						<fieldset>
							 <table width="950" border="0" cellpadding="5" cellspacing="1"  
									class="table table-striped table-bordered bootstrap-datatable " >
								<tr class="even">
									<td width="20%"><strong><label for="Status">Status</label></strong></td>
									<td colspan=3>
										<select  id="status" name="status" data-placeholder="Choose Services to Assign Terminals..." class="chosen-select" style="width: 220px;">
											<option value="">Select</option>
											<option value="Active" selected>Active</option>
											<option value="Close">Close</option>
										</select>
									</td>
								</tr>
								<tr class="odd">
									<td><strong><label for="date">Date</label></strong></td>
									 <td > 
										<input name="terminalDate" type="text" id="terminalDate" class="field" value=" ${responseJSON.TERMINAL_DATE}" >
									</td>
								</tr>
							</table>
						</fieldset>
					</div>  
				</div>
			</div> -->
		<div class="form-actions">
			<a  class="btn btn-danger" href="#" onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp;
			<a  class="btn btn-warning" href="#" onClick="modifyTerminal()">Modify</a>
			<span id ="error_dlno" class="errors"></span>
		</div>	
	</div> 
</form> 
<script src="${pageContext.request.contextPath}/js/jquery.chosen.min.js"></script>
 
</body>
</html>
