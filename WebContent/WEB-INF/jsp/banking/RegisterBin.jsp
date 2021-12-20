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
var val = 1;
var rowindex = 0;
var colindex = 0;
var bankAcctFinalData="";
var binstatus = "";
var v_message = ""; 
var registerBinRules = {
		   rules : {
				bankCode : { required : true},
				bankName : { required : true },
				bin : { required : true , digits : true }, 
				binDescription : { required : true }
				//bankIndex : { required : true , digits : true}
		   },  
		   messages : {
			bankCode : { 
			   required : "Please enter bank code."
				},
			bankName : { 
			   required : "Please enter bank name."
				},
			bin : { 
			   required : "Please enter six digit bin."
				},
			binDescription : { 
			   required : "Please enter bin description."
				}
			/*bankIndex : { 
			   required : "Please enter bank index."
				} */
		   } 
		 };
var listid = "bankCode".split(","); 
var headerList = "bankCode,bankName,bin,binDescription".split(",");
var tabArry = "";

function clearVals() { 
	$('#bankName').val('');
	$('#bin').val('');
	$('#binDescription').val(''); 
	
	$(listid).each(function(index,val) { 
		$('#'+val).find('option').each(function( i, opt ) { 
			if( opt.value === '' ) {
				$(opt).attr('selected', 'selected');
				$('#'+val).trigger("liszt:updated");
			}
		}); 
	});  
}

var index1 = "";
var searchTdRow = "";
var searchTrRows = "";
var addDataVals = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	try { 
		var hiddenInput ="";
		var hiddenNames = "";
		var tabArrText = "";
		
		var tabarrindex = 0;
		tabArry = new Array(); 
 		
		$('#primaryDetails').find('input[type=text],select').each(function(index){ 
			var nameInput = $(this).attr('name');
			var valToSpn = ($(this).attr('value') =='' ? ' ' :$(this).attr('value')); 
			//console.log("Name ["+$(this).attr('name')+"] Values["+$(this).val()+"]");
			if(nameInput != undefined) {
			  if(index == 0)  {
				hiddenInput += valToSpn;
				hiddenNames += nameInput;
			  } else {
				hiddenInput += ","+valToSpn; 
				hiddenNames += ","+nameInput; 
			  }
			  
			   if(jQuery.inArray(nameInput, headerList) != -1) {   
					if( nameInput == 'bankCode' ) {
						tabArry[tabarrindex] = $('select#'+nameInput+' option:selected').text(); 
					  } else {
						tabArry[tabarrindex] = valToSpn; 
					  } 
				  tabarrindex++;
			  }
			} 
		});  
		 
		 $("#multi-row-data").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+tabArry[0]+"' hid-names='"+hiddenNames+"' ></span>");
		 $('#hidden_span_'+rowindex).append(hiddenInput);
	 
		var addclass = ""; 
		
		if(val % 2 == 0 ){
			addclass = "even";
			val++;
		} else {
			addclass = "odd";
			val++;
		}
		
		colindex = ++ colindex;
		
		$(tabArry).each(function(index){ 
			tabArrText+="<td>"+tabArry[index]+"</td> ";
		});
		
		appendTxt = "<tr class="+addclass+" align='center' id='tr-"+rowindex+"' index='"+rowindex+"'>"+
				"<td class='col_"+colindex+"'>"+colindex+"</td> "+ tabArrText+ 
				"<td><a class='btn btn-min btn-info' href='#' id='editDat' index='"+rowindex+"' title='Edit User' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel' index='"+rowindex+"' title='Reset Data' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete' index='"+rowindex+"' title='Delete User' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";
		rowindex = ++rowindex;	 
		
		tabarrindex = 0;
	} catch(e) {
		console.log(e);
	} 
	return appendTxt; 
};
	
var modDataVals = function (clickType) {
	 // add custom behaviour
	try { 
		var  hiddenInput ="";
		var hiddenNames = "";
		var tabArrText = "";
		var appendTxt = "";
		var tabarrindex = 0;
		modTabArry = new Array(); 
		modHeaderBodyArry = new Array(); 
		
		 
		$('#primaryDetails').find('input[type=text],select').each(function(index){ 
			var nameInput = $(this).attr('name');
			var valToSpn = ($(this).attr('value') =='' ? ' ' :$(this).attr('value')); 
			//console.log("Name ["+$(this).attr('name')+"] Values["+$(this).val()+"]");
			if(nameInput != undefined) {
			  if(index == 0)  {
				hiddenInput += valToSpn;
				hiddenNames += nameInput;
			  }  else {
				hiddenInput += ","+valToSpn  ; 
				hiddenNames += ","+nameInput  ; 
			  }
			  
			   if(jQuery.inArray(nameInput, headerList) != -1){ 
				  if( nameInput == 'bankCode') {
					modTabArry[tabarrindex] = $('select#'+nameInput+' option:selected').text(); 
				  } else {
					modTabArry[tabarrindex] = valToSpn; 
				  }
				  
				  tabarrindex++;
			  }
			} 
		});  
		
		tabarrindex = 0;
		modHeaderBodyArry[0]=hiddenNames;
		modHeaderBodyArry[1]=hiddenInput;
	} catch(e)  {
		console.log(e);
	}  
	//console.log(hiddenNames+"##"+hiddenInput);
	return ""; 
};


function alignSerialNo(serialId) {  
	if($(serialId).length > 0) {
		$(serialId +' > td:first-child').each(function(index){  
			$(this).text(index+1);
		}); 
	}
}

$(document).ready(function() {
		
		var mydata ='${responseJSON.BANK_LIST}';
    	var json = jQuery.parseJSON(mydata);
    	$.each(json, function(i, v) {
    	    var options = $('<option/>', {value: v.key , text:v.val}).attr('data-id',i);  
    	    $('#bankCode').append(options);
    	}); 
		$('#btn-back').live('click', function () { 
			$("form").validate().cancelSubmit = true; 
			var url="${pageContext.request.contextPath}/<%=appName %>/binManagement.action"; 
			$("#form1")[0].action=url;
			$("#form1").submit(); 
			 
		});
			
		$('#btn-submit').live('click', function () {  
			var rowCount = $('#tbody_data > tr').length; 
			 $("#error_dlno").text('');
			if(rowCount > 0) {
				$("#form1").validate().cancelSubmit = true;
				var specChar = "";
				var prevCount = "";
				$('#multi-row-data > span').each(function(index){  
					//console.log("index ["+$(this).attr('index')+"] name ["+$(this).attr('name')+"]  value["+$(this).val()+"] ");
				 
					if(index == 0)  finalData = $(this).text();
					else finalData += "#"+ $(this).text();
					 
				}); 
				//console.log("finalData ["+finalData+"]");
				$('#bankMultiData').val(finalData);
 				//var url="${pageContext.request.contextPath}/<%=appName %>/insertIctAdminDetails.action"; 
 				var url="${pageContext.request.contextPath}/<%=appName %>/registerBinSubmitAct.action"; 
				$("#form1")[0].action=url;
				$("#form1").submit(); 
			} else { 
				$("#error_dlno").text("Please add atleast one record.");
			}
		});
		
		$("#bin,#bankIndex").keypress(function (e) {
		 //if the letter is not digit then display error and don't type anything
		 if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
			//display error message
			$("#"+$(this).attr('id')+"_err").html("Digits Only").show().fadeOut("slow");
				   return false;
			}
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

function getBankName(){
	var data=$( "#bankCode option:selected").text();
	var sdata=data.split('-');
	$( "#bankName" ).val(sdata[1]);
}

function recordCheck(idtocheck,userIdInput){ 
	var check = false; 
	var userIdRecords = new Array();
	//console.log('Inside Record check......');
	try { 
		if($(idtocheck).length > 0) {
			// To Check The Record Exists In The Add-Row
			$(idtocheck+' > td:nth-child(2)').each(function(indx){ 
				console.log($(this).text()); 
				userIdRecords[indx]=$(this).text().trim(); 
			});
			
			if(jQuery.inArray(userIdInput.trim(), userIdRecords) == -1){ 
				check = true; 
			} 
			
		} else {
			check = true; 
		}
		
		
	} catch(e){
		console.log(e);
	}
	//console.log('Duplicate Record Check.. '+ check);
	return check;
}
	
$(function() {    

	$.validator.addMethod("date", function (value, element) { 
		var expDate = value.split('/'); 
		var myDate = new Date(expDate[2], expDate[1] - 1, expDate[0]); 
		var today = new Date(); 
		
		if ( value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/) && myDate > today )
			return true;
		else
			return false;
	}); 
	
	// Update Edited-Row Starts Here.
	$('#modCap').live('click', function () {
		$("#error_dlno").text('');	 
		$("#form1").validate(registerBinRules); 
		if($("#form1").valid()) {

 			var spanValues = modDataVals("MODIFY");
			
 			searchTdRow = '#'+searchTrRows+"#tr-"+index1 +' > td';
 			$(searchTdRow).each(function(index,value) {
 				if(index == 1){
					$(this).text(modTabArry[0]);
				}if(index == 2){
					$(this).text(modTabArry[1]);
				}if(index == 3){
					$(this).text(modTabArry[2]);
				}if(index == 4){
					$(this).text(modTabArry[3]);
				}if(index == 5){
					$(this).text(modTabArry[4]);
				}if(index == 6){
					$(this).text(modTabArry[5]);
				}
			});  
			 
			 
			$("#multi-row-data span").each(function(index,value) { 
 				 if($(this).attr("index") == index1 ) {  
					 $(this).attr("hid-names",modHeaderBodyArry[0]);
					 $(this).text(modHeaderBodyArry[1]); 
					 
					 return;
				 }
			}); 
		 
			clearVals();   
			alignSerialNo("#tbody_data > tr");
			
			//Hide Add Button and Display Update Button
			$('#modCap').hide();
			$('#addCap').show();
			
		} else {
			return false;				
		} 
		
	});  
 
 
	// The below event is to delete the entire row on selecting the delete button 
	$("#delete").live('click',function() { 
		var delId = $(this).attr('index');
		$(this).parent().parent().remove();
 		
		var spanId = "";
		$('#multi-row-data > span').each(function(index){  
			spanId =  $(this).attr('index'); 
			if(spanId == delId) {
				$(this).remove();
			}
		}); 
		
		clearVals();
		// Aligning the serial number
		alignSerialNo("#tbody_data > tr");
		
		//Show Add Button and Hide Update Button
		$('#modCap').hide();
		$('#addCap').show();
	});  
	
	// The below event is to Edit row on selecting the delete button 
	$('#editDat').live('click',function() { 
		$("form").validate().cancelSubmit = true; 
		 
		index1 = $(this).attr('index');  
		var parentId =$(this).parent().closest('tbody').attr('id'); 
		searchTrRows = parentId+" tr"; 
		searchTdRow = '#'+searchTrRows+"#tr-"+index1 +' > td';
		var idSearch = "";
		var hidnames = "";
		var text_val = "";
		
 		
		$(searchTdRow).each(function(index,value) { 
 			if(index == 1) { 
				idSearch = $(this).text().trim();
			} 
		});  
		
 		
		$("#multi-row-data > span").each(function(index,value) {  
			if(idSearch == $(this).attr('ind-id') ) {
				hidnames = $(this).attr('hid-names');
				text_val = $(this).text();  
			} 
		});  
		
		var hidarr=hidnames.split(",");
		var textarr=text_val.split(",");  
		 
		$(hidarr).each(function(index,value) { 
			 if(jQuery.inArray( value, listid ) != -1){
				$('#'+value).find('option').each(function( i, opt ) { 
					if( opt.value == textarr[index] ) {
						$(opt).attr('selected', 'selected');
						$('#'+value).trigger("liszt:updated");
					}
				}); 
			} else {	
				$("#"+value).val(textarr[index].trim()); 
			}
		}); 
		
		//Hide Add Button and Display Update Button
		$('#modCap').show();
		$('#addCap').hide();
		
	});   
   
	$.validator.addMethod("regex", function(value, element, regexpr) {          
		 return regexpr.test(value);
	   }, "");    
	
	
			
	// Add Row Starts Here.
	$('#addCap').live('click', function () {
		$("#error_dlno").text('');	 
		$("#form1").validate(registerBinRules);
		var textMess = "#tbody_data > tr"; 
 		// Is To Check User-Id Exist's In DB Or Not
		if($("#form1").valid()) { 
			var rowCount = $('#bankAcctData > tbody > tr').length; 
			var data= $( "#bankCode option:selected" ).val();
			var bankCode=data.split("-")[0];
			var queryString = "entity=${loginEntity}&method=searchBin&bin="+$('#bin').val()+"&bankIndex="+$('#bankIndex').val()+"&bankCode="+bankCode;
			
			$.getJSON("postJson.action", queryString,function(data) {   
					binstatus = data.status; 
					v_message = data.message; 
					//console.log('binstatus ===> '+binstatus);
					//console.log('v_message ===> '+v_message);
					if(binstatus == 'FOUND') {
						if(v_message == 'BIN_BANK_DUP') {
							$('#error_dlno').text('Bin with Bank Code Exists.');
						} else if(v_message == 'BIN_DUP') {
							$('#error_dlno').text('Bin Already Exists.');
						} else if(v_message == 'BANK_CODE_DUP') {
							$('#error_dlno').text('Bank Code Exists.');
						}
					} else {			 
						var appendTxt = addDataVals("ADD"); 
						$("#tbody_data").append(appendTxt);  
						clearVals();
					}  
					alignSerialNo(textMess); 
				});    
		} else {
			return false;				
		}
	});   
	
	// Clear Form Records Row Starts Here.
	$('#row-cancel').live('click', function () {
		$("#error_dlno").text(''); 
		 clearVals(); 
		 
		//Show Add Button and Hide Update Button
		$('#modCap').hide();
		$('#addCap').show();
	});  
}); 

</script>

</head> 
<body>
	<form name="form1" id="form1" method="post" action=""> 
			<div id="content" class="span10"> 
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="binManagement.action">Bin Management</a> <span class="divider"> &gt;&gt; </span></li>
				  <li><a href="#">Register Bin</a></li>
				</ul>
			</div>
			<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Register Bin
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
										<td width="20%"><strong><label for="Bank Code">Bank Code<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="bankCode" name="bankCode" class="chosen-select"
											    required="required" onChange="getBankName()">
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>
										<!--<input name="bankCode" type="text" id="bankCode" class="field"  maxlength="15"  value='${responseJSON.bankCode}' required='true'>  -->
										<td width="20%"><strong><label for="Bank Name">Bank Name<font color="red">*</font></label></strong></td>
										<td width="30%"><input name="bankName" type="text"  id="bankName" class="field" value='${responseJSON.bankName}'  maxlength="50" required='true' readonly></td>
									</tr>
									<tr class="even">
										<td ><strong><label for="Bin">Bin<font color="red">*</font></label></strong></td>
										<td><input name="bin" type="text" id="bin" class="field"  maxlength="6"  value='${responseJSON.bin}' required='true'> <span id="bin_err" class="errmsg"></span></td>
										<td ><strong><label for="Bin Desc">Bin Description<font color="red">*</font></label></strong></td>
										<td><input name="binDescription" type="text"  id="binDescription" class="field" value='${responseJSON.binDescription}' required='true' maxlength="50" ></td>
									</tr>
									<tr class="even" style="display:none">
										<td ><strong><label for="Bank Index">Bank Index<font color="red">*</font></label></strong></td>
										<td><input name="bankIndex" type="text" id="bankIndex" class="field"  maxlength="20"   value='${responseJSON.bankIndex}' /><span id="bankIndex_err" class="errmsg"></span></td>
										<td >&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									 
								</table>
							 </fieldset> 
							</div>
							
								<input type="hidden" name="bankMultiData" id="bankMultiData" value=""></input>
							<table width="100%" class="table table-striped table-bordered bootstrap-datatable "
								id="bankAcctData"  >
									  <thead>
											<tr >
												<th>Sno</th>
												<th>Bank Code</th>
												<th>Bank Name</th>
												<th>Bin</th>
												<th>Bin Description</th>
 												<th>Action</th>
											</tr>
									  </thead>    
									 <tbody  id="tbody_data">
									 </tbody>
							</table> 
							

							<input name="subServiceText" type="hidden" id="subServiceText" class="field"  />
							 
						</div>
					</div>
						 
				 
			<span id="multi-row-data" name="multi-row-data" class="multi-row-data" style="display:none" ></span>
			<div class="form-actions"> 
				<input type="button" class="btn btn-success" name="modCap" id="modCap" value="Update" width="100" style="display:none" ></input> 
				<input type="button" class="btn btn-success" name="addCap" id="addCap" value="Add" width="100"  ></input> &nbsp;
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" />&nbsp;
				<input type="button" class="btn btn-danger" name="btn-back" id="btn-back" value="Back" /> 
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div> 
	</div> 
</form>
</body>
</html>
