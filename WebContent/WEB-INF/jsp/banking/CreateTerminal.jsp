 <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<s:set value="responseJSON" var="respData"/>
<script type="text/javascript" >
$(document).ready(function() {
	$("#terminalDate").val($.datepicker.formatDate("dd/mm/yy", new Date()));
    var terminalId="${responseJSON.tid}";
    //alert(terminalId);
   // 
    
    
    var merchantList='${responseJSON.MERCHANT_LIST}';
	var json = jQuery.parseJSON(merchantList);
	var options = "";
 	$.each(json, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	      options = $('<option/>', {value: v.val, text: v.key }).attr('data-id',i);
	    // append the options to job selectbox
	    $('#merchantID').append(options);
	});    

  
	
	
	var tpkIndex="${responseJSON.tmkIndex}";
	if(tpkIndex==""){
		tpkIndex="${tpkIndex}";
	}
	$("#tpkIndex").val(tpkIndex);
	//$("#tmkIndexSpan").empty();
	//$("#tmkIndexSpan").append(tpkIndex);
	
	var tpkKey="${responseJSON.tpkKey}";
	if(tpkKey==""){
		tpkKey="${tpkKey}";
	}
	$("#tpkKey").val(tpkKey);
	//$("#tpkKeySpan").empty();
	//$("#tpkKeySpan").append(tpkKey);
	
});

//For Closing Select Box Error Messages_Start
$(document).on('change','select',function(event) { 

if($('#'+$(this).attr('id')).next('label').text().length > 0) {
 $('#'+$(this).attr('id')).next('label').text(''); 
 $('#'+$(this).attr('id')).next('label').remove();
}

});
//For Closing Select Box Error Messages_End

var list = "validFrom,validThru".split(",");
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
	
 	$('#storeId').live('change', function () {

		var merchantId=$('#storeId').val();
		var formInput='storeId='+merchantId;

		$.getJSON('retriveTerminalAct.action', formInput,function(data) {

			var json = data.responseJSON.terminalID
			$('#terminalID').val(json);
			
			
		});
	}); 
	
	
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
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
	$("#serialNumber").keypress(function (e) {
	 if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		$("#"+$(this).attr('id')+"_err").html("Digits Only").show().fadeOut("slow");
			   return false;
		}
   }); 
   
   var obj = {
	  "hrs1": "12",
	  "hrs2": "12",
	  "min1": "59" ,
	  "min2": "59" 
	};
 	
	$.each( obj, function( key, value ) { 
		var i = 0; 
		for (i = 1; i <= value; i++ ) { 
			var options = $('<option/>', {value: i , text: i});
			 $('#'+key).append(options);
		}
	});
	
});
var createTerminalRules = {
   rules : {
    merchantID : { required : true },
	storeId : { required : true },
	//terminalID : { required : true },
	modelNumber : { required : true },
	serialNumber : { required : true },
	terminalMake : { required : true },
	//validFrom : { required : true },
	//validThru : { required : true },
	terminalDate : { required : true },
	terminalType : { required : true },
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
        },*/
        status : { 
            required : "Please Select The Status"
             },
	terminalDate : { 
       required : "Please Select Date"
        },
	terminalType : { 
       required : "Please Select Terminal Type to generate Terminal Id"
        }
   } 
 };
  
function getGenerateMerchantScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateMerchantAct.action';
	$("#form1").submit();
	return true;
}

//function createTerminal(){
	
	 
$("#submit1").live('click', function () {
	 
	$("#form1").validate(createTerminalRules);
	var serialNumber =$('#serialNumber').val();

	var queryString = "method=serialNumberMethod&serialNumber="+serialNumber;	
	
	$.getJSON("postJson.action", queryString,function(data) {

		userstatus = data.status;

		console.log(data);

		v_message = data.message;

		if(userstatus == "FOUND") {
			if(v_message != "SUCCESS") {
				$('#error_dlno').text(v_message);
			}
		} else {
			/* var appendTxt = addDataVals("ADD");
			$("#mytable").append(appendTxt);
			clearVals(); */
			if($("#form1").valid()){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/getCreateTerminalConfirmAct.action';
				$("#form1").submit();
				return true;
			}else{
				return false;
			}
		}
		alignSerialNo(textMess);
	});
	
	
});
	 
 	

 
</script>


</head>

<body>
	<form name="form1" id="form1" method="post">
		<div id="content" class="span10"> 
		    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Create Terminal</a></li>
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
								<td><input name="terminalID" type="text"  id="terminalID" class="field" readonly></td> 
								<td ><label for="Terminal Make"><strong>Terminal Make <font color="red">*</font></strong></label></td>
								<td ><input name="terminalMake" type="text" class="field" id="terminalMake" value=""  />
									<input name="terminalUsage" type="hidden" class="field" id="terminalUsage" value="Yes" /> </td>
								
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
								<td ><input name="modelNumber"  type="text" id="modelNumber" class="field" value=""  /> </td>
								
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
								 <td><input name="terminalDate"  type="text" id="terminalDate" class="field"  value="${terminalDate}"  readonly="readonly" > </td>
							</tr>
						</table>
						<input name="pinEntry" type="hidden" class="field" id="pinEntry"  value="NO"  />
						</fieldset>
						</div> 
					</div> 
				</div>
				
			<input name="tpkIndex"  type="hidden" id="tpkIndex" class="field"  value="${tmkIndex}"  />
			<input name="tpkKey"  type="hidden" id="tpkKey" class="field"  value="${tpkKey}" />
			
			<div class="form-actions">
				<a  class="btn btn-danger" href="generateMerchantAct.action" onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp;
				<!-- <a  class="btn btn-success" href="#" onClick="createTerminal()">Submit</a> -->
				<a  class="btn btn-success" href="#"  id="submit1" name="submit1">Submit</a>
				<span id ="error_dlno" class="errors"></span>
			</div>	 
	</div>
 </form>
 <s:token/>
</body>
</html>
