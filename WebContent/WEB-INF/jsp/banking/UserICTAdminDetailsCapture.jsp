<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<s:set value="responseJSON" var="respData"/>
<style type="text/css">
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
input#userId{text-transform:uppercase};
.errmsg {
color: red;
}
.errors {
color: red;
}
</style>
<!-- add row validation -->

<script type="text/javascript">  
function randomString() {
   var charSet = 'acdeFGHIJKLMNOpqrstUVWXYZ0123456789';
   var smlSet = 'abcdefghijklmnopqrstuvwxyz';
   var splCharSet = "!@#$%^&?";
    var randomString = '';
    for (var i = 0; i < 8; i++) {
    	var randomPoz = Math.floor(Math.random() * charSet.length);
    	randomString += charSet.substring(randomPoz,randomPoz+1);
    	if(i==7){
    		var randomPoz = Math.floor(Math.random() * splCharSet.length);
    		randomString+=splCharSet.substring(randomPoz, randomPoz+1);
    	}
    }
		var randomPoz = Math.floor(Math.random() * smlSet.length);
		randomString+=splCharSet.substring(randomPoz, randomPoz+1);
  // alert(randomString);
    return randomString;
}

function encryptPassword(){
	var encryptPass;
	var password=randomString();
	//alert("password::"+password);
	$("#password").val(password);
	encryptPass = b64_sha256(password);
	$("#encryptPassword").val(encryptPass);
}
var finalData="";
var listDate = "expiryDate".split(",");
var datepickerOptions = {
			showTime: false,
			showHour: false,
			showMinute: false,
  			dateFormat:'dd/mm/yy',
  			alwaysSetTime: false,
			changeMonth: true,
			changeYear: true
};
function countrycode(){ 
	 var Code = $("#code").val(); 
	 $("#mobile").val(Code); 
}

$(function() {
	$(listDate).each(function(i,val){
 		$('#'+val).datepicker(datepickerOptions);
	});  
	
 	/* var mydata ='${responseJSON.LOCATION_LIST}';
 	var json = jQuery.parseJSON(mydata);
	 var options = "";
 	$.each(json, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	      options = $('<option/>', {value: v.key+"-"+v.val, text: v.key+"-"+v.val }).attr('data-id',i);
	    // append the options to job selectbox
	    $('#officeLocation1').append(options);
	}); 
 	
	$("#officeLocation1").val("1");
	//$('#officeLocation1').prop('disabled', true);
	*/
	
	var mydata ='${responseJSON.CLUSTER_LIST}';
 	var json = jQuery.parseJSON(mydata);
	 var options = "";
 	$.each(json, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	      options = $('<option/>', {value: v.key+"-"+v.val, text: v.key+"-"+v.val }).attr('data-id',i);
	    // append the options to job selectbox
	    $('#clusterid').append(options);
	}); 
 	
	$("#clusterid").val("1"); 
	
	var groupType='${responseJSON.GROUP_TYPE}';
	//alert(groupType);
	var userLevel='${responseJSON.USER_LEVEL}';
	mydata ='${responseJSON.USER_DESIGNATION}';
	json = jQuery.parseJSON(mydata);
 	$.each(json, function(i, v) {
		if(groupType!="MERCHANTGRP" && v.val=="MA"){
		}else{
			options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);
			$('#adminType').append(options);
		}
	}); 

	$("#adminType").val(userLevel);
	$('#adminType').prop('disabled', true);
	
/* 	<s:if test="groupID != 'MERTADMIN'" > 
		mydata ='${responseJSON.USERS_LIST}';
 		json = jQuery.parseJSON(mydata);
 		if(mydata != '' || mydata.indexOf(",") !=-1 ) {
			$.each(json, function(i, v) { 
				options = $('<option/>', {value: v, text: v}).attr('data-id',i);
				$('#userId').append(options); 
			}); 
		}
	</s:if> */
	
	if(groupType=="MERCHANTGRP") {
		$("#adminType").val("MA");
		$('#adminType').prop('disabled', true);
	} 
	
	
	var config = {
      '.chosen-select'           : {},
      '.chosen-select-deselect'  : {allow_single_deselect:true},
      '.chosen-select-no-single' : {disable_search_threshold:10},
      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chosen-select-width'     : {width:"95%"}
    };
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
	
	
});	
</script>

<script type="text/javascript">

var val = 1; 
var rowindex = 0;
var colindex = 0;
var userstatus = "";
var v_message = "";

<s:if test="groupID == 'MERTADMIN'" > 
	var listid = "adminType,clusterid".split(","); 
	var headerList = "userId,employeeNo,firstName,lastName,officeLocation1,email,clusterid".split(",");
</s:if>
<s:else>
	var listid = "adminType,userId,clusterid".split(","); 
	var headerList = "userId,employeeNo,firstName,lastName,officeLocation1,email,clusterid".split(",");
</s:else>


var tabArry ; 
var modTabArry ;  
var modHeaderBodyArry ;  

function clearVals(){ 
	$('#employeeNo').val('');
	$('#firstName').val('');
	$('#lastName').val('');
	$('#userId').val('');
	$('#email').val('');
	$('#telephoneRes').val('');
	$('#telephoneOff').val('');
	$('#mobile').val('');
	$('#officeLocation1').val('');
	$('#clusterid').val('');	
	$('#fax').val('');
	$('#expiryDate').val(''); 
	
	$(listid).each(function(index,val){ 
		$('#'+val).find('option').each(function( i, opt ) { 
			if( opt.value === '' ) {
				$(opt).attr('selected', 'selected');
				$('#'+val).trigger("liszt:updated");
			}
		}); 
	});

	rowCount = $('#tbody_data > tr').length; 
	if(rowCount > 0 )  $("#error_dlno").text('');

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
		
		encryptPassword(); 
		
		$('#user-details').find('input[type!=button],select').each(function(index){ 
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
			  
			   if(jQuery.inArray(nameInput, headerList) != -1){  
					<s:if test="groupID == 'MERTADMIN'" > 
						 if( nameInput == 'officeLocation1') {
							tabArry[tabarrindex] = $('select#'+nameInput+' option:selected').text(); 
						  } else { 
							tabArry[tabarrindex] = valToSpn; 
						  }
					</s:if>
					<s:else>
						 if( nameInput == 'officeLocation1' 
								) {
							tabArry[tabarrindex] = $('select#'+nameInput+' option:selected').text(); 
						  } else { 
							tabArry[tabarrindex] = valToSpn; 
						  }
					</s:else>
				  
				  
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
				"<td><a class='btn btn-min btn-info' href='#' id='editDat' index='"+rowindex+"' title='Edit User' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel' index='"+rowindex+"' title='Reset Data' data-rel='tooltip'> <i class='icon icon-repeat icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete' index='"+rowindex+"' title='Delete User' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";
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
		
		 
		$('#user-details').find('input[type!=button],select').each(function(index){ 
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
			  console.log("hiddenNames..:"+hiddenNames);
			   if(jQuery.inArray(nameInput, headerList) != -1){ 
				   if( nameInput == 'officeLocation1') {
					modTabArry[tabarrindex] = $('select#officeLocation1 option:selected').text(); 
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

</script>

<script type="text/javascript">
var userictadminrules = {
	rules : {
		userId : { required : true},
		employeeNo : { required : true  } , //, regex: /^[0-9]+$/
		firstName : { required : true} ,
		lastName : { required : true} ,
		adminType : { required : true } ,
		clusterid : { required : true } ,
		expiryDate : { required : false, date : false } ,
		email : { required : true , email : true},
		mobile : { required : false },
		passportNo :  { required : false }
		/* telephoneOff:  { required : true, regex: /^[0-9]+$/  } */
	},		
	messages : {
		userId : { 
					required : "Please Select User Id."
 				  }, 
		employeeNo : { 
						required : "Please Enter Employee No."
					},
		firstName : { 
						required : "Please Enter First Name."
					},
		lastName : { 
						required : "Please Enter Last Name."
					},
		adminType : { 
						required : "Please Choose User Designation."
					},
		expiryDate : { 
						required : "Please Select Expiry Date.",
						date  : "Expiry date, should be always greater than system date."
					},
		email : { 
						required : "Please Enter email address.",
						email : "Please enter a valid email address."
					},
					clusterid : { 
						required : "Please Select Branch.",
					},
					/*mobile : { 
						required : "Please Enter Mobile No."
					},*/
		passportNo : {
			required : "Please Enter ID / Passport No",
		},
		/* telephoneOff : { 
			required : "Please Enter Office Telephone Number.",
			regex : "Office Telephone Number, contain digits only."
		}, */
	} 
};

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
		$("#form1").validate(userictadminrules); 
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
				$("#userId").val(textarr[0].trim());
				$("#"+value).val(textarr[index].trim()); 
			}
		}); 
		
		//Hide Add Button and Display Update Button
		$('#modCap').show();
		$('#addCap').hide();
		
	}); 
	
	
	$('#btn-cancel').live('click', function () { 
		$("form").validate().cancelSubmit = true; 
		var url="${pageContext.request.contextPath}/<%=appName %>/userGrpCreation.action"; 
		$("#form1")[0].action=url;
		$("#form1").submit();  
	});
	
	$('#btn-submit').live('click', function () { 
		//$("form").validate().cancelSubmit = true;
		var rowCount = $('#tbody_data > tr').length; 
		 $("#error_dlno").text('');
		  
		if(rowCount > 0) {
			$("#form1").validate().cancelSubmit = true;
		 
			$('#multi-row-data > span').each(function(index) {  
				//console.log("index ["+$(this).attr('index')+"] name ["+$(this).attr('name')+"]  value["+$(this).val()+"] "); 
				if(index == 0)  finalData = $(this).text();
				else finalData += "#"+ $(this).text();
			}); 
			
			var offVal = ''; 
			
			// To Get The Location's From Add-Row
			$('#tbody_data > tr > td:nth-child(6)').each(function(indx){ 
				if(indx == 0) {
					offVal+= $(this).text();
				} else {
					offVal+=","+ $(this).text();
				}  
			});
			
			$('#officeLocation').val(offVal);
		 	//console.log(finalData);
			$('#multiData').val(finalData);
 			var url="${pageContext.request.contextPath}/<%=appName %>/ictAdmincreatePageConfirm.action"; 
			$("#form1")[0].action=url;
			$("#form1").submit(); 
		} else { 
			$("#error_dlno").text("Please add atleast one user.");
		}
	});
	
	/* $("#telephoneRes,#telephoneOff,#mobile,#fax,#employeeNo").keypress(function (e) {
	 //if the letter is not digit then display error and don't type anything
	 if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		//display error message
		$("#"+$(this).attr('id')+"_err").html("Digits Only").show().fadeOut(4000);
			   return false;
		}
   });  */
   
	$.validator.addMethod("regex", function(value, element, regexpr) {          
		 return regexpr.test(value);
	   }, "");    
	
	
			
	// Add Row Starts Here.
	$('#addCap').live('click', function () {
		$("#error_dlno").text('');	 
		$("#form1").validate(userictadminrules);
		var textMess = "#tbody_data > tr"; 
 		// Is To Check User-Id Exist's In DB Or Not
		if($("#form1").valid()) { 
			var userIdToSend = "";
			
			<s:if test="groupID == 'MERTADMIN'" > 
				 userIdToSend = $('#userId').val();
			</s:if>
			<s:else>
				 userIdToSend = $('#userId').val();
			</s:else>
		
 			if(recordCheck(textMess,userIdToSend)) {
				
				var queryString = "entity=<s:property value='loginEntity'/>&method=searchUser&userId="+userIdToSend+"&employeeNo="+$('#employeeNo').val();	
				$.getJSON("postJson.action", queryString,function(data) { 
					userstatus = data.status; 
					v_message = data.message;
					
					if(userstatus == "FOUND") { 
						if(v_message != "SUCCESS") {
							$('#error_dlno').text(v_message);
						}  
					} else { 					 
						var appendTxt = addDataVals("ADD");
						$("#mytable").append(appendTxt);  
						clearVals();
					}  
					alignSerialNo(textMess); 
				});   
			
			} else {
				// This is to throw error nessage.
				$('#error_dlno').text('');
				$('#error_dlno').text('The User With "'+userIdToSend+'" Exists.');
			}
			 
		} else {
			return false;				
		}
		
	});   
;	
	// Clear Form Records Row Starts Here.
	$('#row-cancel').live('click', function () {
		$("#error_dlno").text(''); 
		 clearVals(); 
		 
		//Show Add Button and Hide Update Button
		$('#modCap').hide();
		$('#addCap').show();
	});  

	//<s:if test="groupID != 'MERTADMIN'" > 
	
		$('#userId').blur( function () {
			
				$("#error_dlno").text('');
				if($('#userId').val() != '') {
					var queryString = "method=userDetails&userId="+$('#userId').val();	
					$.getJSON("postJson.action", queryString,function(data) { 
						var dataUser = data.responseJSON.USERS_LIST;
						
						$('#firstName').val(dataUser.FirstName);
						$('#lastName').val(dataUser.LastName);
						$('#email').val(dataUser.Email);
						$('#employeeNo').val(dataUser.EmployeeId);
						$('#mobile').val((dataUser.MobileNumber).replace("+234",""));
						//$('#officeLocation1').val(dataUser.BranchName);
						//alert(dataUser.BranchCode+"-"+dataUser.BranchName);
						$('#officeLocation1').val(dataUser.BranchCode+"-"+dataUser.BranchName).prop('selected', true);
						
						$('#firstName').prop("readonly", true);
						$('#lastName').prop("readonly", true);
						$('#email').prop("readonly", true);
						$('#employeeNo').prop("readonly", true);
						$('#officeLocation1').prop("readonly", true);
					}); 
				} else {
					$('#firstName').val('');
					$('#lastName').val('');
					$('#email').val('');
					$('#employeeNo').val('');
					$('#telephoneOff').val('');
					$('#mobile').val('');
					$('#officeLocation1').val('');
				}
			});   
			
	//</s:if>
	 
	
	var bankfinalData = '<s:property value="multiData"/>';
 	
	if( bankfinalData.indexOf(",") != -1) {
		var val = 0; 
		$('#officeLocation').val('');
		$('#officeLocation').val('<s:property value="officeLocation"/>');
		var offArr = $('#officeLocation').val().split(",");
		var addclass = ""; 
		var bankfinalDatarows = bankfinalData.split("#");
		
		var hiddenNames1 = "";
		
		$('#user-details').find('input[type!=button],select').each(function(index){ 
			var nameInput = $(this).attr('name');
			 
			if(nameInput != undefined) {
			  if(index == 0)  {
				hiddenNames1 += nameInput;
			  } else {
				hiddenNames1 += ","+nameInput; 
			  }  
			} 
		});
		
		for(var i=0;i<bankfinalDatarows.length;i++) { 
			if(val % 2 == 0 ){
				addclass = "even";
				val++;
			} else {
				addclass = "odd";
				val++;
			}
			var eachrow=bankfinalDatarows[i];
			var eachfieldArr=eachrow.split(",");  
			 
			$("#multi-row-data").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+eachfieldArr[0]+"' hid-names='"+hiddenNames1+"' ></span>");
			$('#hidden_span_'+rowindex).append(eachrow);
		 
			
			var userid=eachfieldArr[0];
			var accountname=eachfieldArr[2];
			var openbalance=eachfieldArr[3];
			var secondname=eachfieldArr[4];
			var closebalance = eachfieldArr[1];
			var accounttype1 = eachfieldArr[8];
			var cluster = eachfieldArr[12];

			var appendTxt = "<tr align='center' id='tr-"+rowindex+"' index='"+rowindex+"'>"+
				 "<td class='col_"+colindex+"'> &nbsp;"+colindex+"   </td> "+
				 "<td>"+userid+"</td>"+
				 "<td>"+accountname+"</td>"+
				 "<td>"+openbalance+" </td>"+
				 "<td>"+closebalance+"</td>"+
				 "<td>"+accounttype1+"</td>"+
				 "<td>"+cluster+"</td>"+
				 "<td><a class='btn btn-min btn-info' href='#' id='editDat' index='"+rowindex+"'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel' index='"+rowindex+"'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete' index='"+rowindex+"'> <i class='icon-trash icon-white'></i></a></td></tr>";
			 console.log('appendTxt..:'+appendTxt);
			$("#tbody_data").append(appendTxt); 
			rowindex++;
		}
		alignSerialNo("#tbody_data > tr");
	}
	 $('#userId').live('keyup', function() {
			// var id = $(this).attr('id');
			//var v_val = $(this).val(); 
			$("#userId").val($(this).val().toUpperCase());
		}); 
}); 
</script>

</head>
<body>
<form name="form1" id="form1" method="post">

<div id="content" class="span10">
	<!-- content starts -->
	<div>
		<ul class="breadcrumb">
		  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
		  <li> <a href="userGrpCreation.action">User Management</a> <span class="divider"> &gt;&gt; </span></li>
		  <li> <a href="#">User Creation</a></li>
		</ul>
	</div>


  <div class="row-fluid sortable">

	<div class="box span12">

			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>User Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>
		<div class="box-content">
			<fieldset>
				 <table width="950" border="0" cellpadding="5" cellspacing="1"
							class="table table-striped table-bordered bootstrap-datatable" id="user-details">

						<tr class="odd">
 							<td width="15%"><label for="User Id"><strong>User Id<font color="red">*</font></strong></label></td>
							<td width="30%">
								<%--  <s:if test="#respData.GROUP_TYPE == 'MERCHANTGRP'" >
									<input name="userId" id="userId" class="field" type="text" required=true   maxlength="50"/>
									<div id="aval"></div>
								</s:if>
								<s:else >
									 <select id="userId" name="userId"    data-placeholder = "Choose UserId..."
										 class="chosen-select-width" style="width: 250px;" required=true>
										<option value="">Select</option> 
 									</select>  

								</s:else>
 --%>
								 <input name="userId" id="userId" class="field" type="text" required=true   maxlength="50"/>  

							</td>
 							<td width="20%"><label for="Employee No"><strong>Employee No</strong></label></td>
	                    	<td width="30%"><input name="employeeNo" id="employeeNo" type="text"  class="field" required=true    /> <span id="employeeNo_err" class="errmsg"></span> </td>
						</tr>
						<tr class="odd">
 							<td><label for="First Name"><strong>First Name<font color="red">*</font></strong></label></td>
							<td><input name="firstName" id="firstName"  type="text" class="field" required=true maxlength="50" /></td>
 							<td><label for="Last Name"><strong>Last Name<font color="red">*</font></strong></label></td>
							<td><input name="lastName" type="text" class="field" id="lastName"  required=true maxlength="50" /></td>
						</tr>
						<tr class="odd">
 							<td><label for="Telephone Res"><strong>Telephone(Res)</strong></label></td>
							<td><input name="telephoneRes" type="text" class="field" customvalidation="max::10||min::10" id="telephoneRes"  maxlength="10" size="30" /><span id="telephoneRes_err" class="errmsg"></span></td>
 					        <td><label for="Telephone Ext"><strong>Telephone(Ext)</strong></label></td>
					        <td><input name="telephoneOff" type="text" class="field" customvalidation="max::4||min::4" id="telephoneOff"  maxlength="4" size="30" /><span id="telephoneOff_err" class="errmsg"></span></td>
				       </tr>

				     <tr class="odd">
 						    <td><label for="Mobile"><strong>Mobile<font color="red">*</font></strong></label></td>
							<td>
								<select id="code" name="code" onchange="countrycode()" style="width: 90px;"
										data-placeholder = "Choose countrycode..."
										class="chosen-select-width" required =true> 
										<option value="234">234</option>
								</select>
							&nbsp;<input name="mobile" type="text"  class="field" id="mobile" maxlength="13" size="13" required=true style="width:115px;" /><span id="mobile_err" class="errmsg"></span></td>
							<td><label for="E-Mail"><strong>E-Mail<font color="red">*</font></strong></label></td>
							<td>
					 				<input name="email" type="text" class="field" id="email" required=true  maxlength="70" size="90"/>
									<input name="password" type="hidden" class="field" id="password"   />
									<input name="encryptPassword" type="hidden" class="field" id="encryptPassword"  />
									<input type="hidden" id="adminType" name="adminType" value="NBK ADMIN"/>

							</td>
				     </tr>

				    <%-- <tr class="odd">

 					 <td><label for="Office Location"><strong>Office Location<font color="red">*</font></strong></label></td>
					  <td>
					  <select id="officeLocation1" name="officeLocation1" data-placeholder="Choose office location..."
										class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option>
						</select>
					 
					  <!-- <input name="officeLocation1" type="text" class="field" id="officeLocation1"  required=true maxlength="50"  /> -->
						
						<!-- <input name="expiryDate" type="hidden" class="field" id="expiryDate" value="30/12/2044"  maxlength="20" size="20" readonly /> -->
					   </td>  --%>
					   <td><label for="User Level"><strong>Branch<font color="red">*</font></strong></label></td>
					  <td><select id="clusterid" name="clusterid" data-placeholder="Choose office location..."
										class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option>
						</select>
					 </td>
					 <td></td>
					 <td></td>
				    </tr>


			</table>
			 <input name="officeLocation1" type="hidden" class="field" id="officeLocation1"  required=true maxlength="50"  /> 
		</fieldset>
			<div class="box-content">
				<table class="table table-striped table-bordered bootstrap-datatable dataTable"
							id="mytable" style="width: 100%;">
				  <thead>
						<tr>
							<th>Sno</th>
							<th>User Id</th>
							<th>Employee No</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>E-Mail</th>
							<th>Branch</th>
							<th>Actions</th>
						</tr>
				  </thead>
				  <tbody id="tbody_data">
				  </tbody>
				</table>
			</div>

			<span id="multi-row-data" class="multi-row-data" style="display:none"> </span>

			<div class="form-actions">
				   <input type="button" class="btn btn-success" name="modCap" id="modCap" value="Update" width="100" style="display:none" ></input>
				   <input type="button" class="btn btn-success" name="addCap" id="addCap" value="Add" width="100"  ></input>

	                &nbsp;<input type="button" class="btn btn-success" type="text"  name="btn-submit" id="btn-submit" value="Submit" width="100" ></input>
	             <!--  <a href="userGrpCreation.action" type="text"  name="btn-cancel" id="btn-cancel" value="Cancel" width="100" ></input> -->
	                  &nbsp;<a  class="btn btn-warning" href="userGrpCreation.action" name="btn-cancel" id="btn-cancel">Cancel</a> &nbsp;&nbsp;
					<span id ="error_dlno" class="errors"></span>

	                <input name="tag" type="hidden" id="tag" value="newprod" />
					<input name="groupID" type="hidden" id="groupID" value="<s:property value="groupID"/>" />
					<input name="entity" type="hidden" id="entity" value="<s:property value="entity"/>" />
					<input type="hidden" name="multiData" id="multiData" value="<s:property value="multiData"/>" />
					<input type="hidden" name="officeLocation" id="officeLocation" value="<s:property value="officeLocation"/>" />
					<input name="typeuser" type="hidden" id="typeuser" value="<s:property value="typeuser"/>" />
	        </div>

			</div>
			</div>
	</div>
</div>
</form>

<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/manual-validation.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
</body>
</html>