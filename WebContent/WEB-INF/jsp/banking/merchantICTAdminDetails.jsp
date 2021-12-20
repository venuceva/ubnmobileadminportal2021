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
   var charSet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    var randomString = '';
    for (var i = 0; i < 8; i++) {
    	var randomPoz = Math.floor(Math.random() * charSet.length);
    	randomString += charSet.substring(randomPoz,randomPoz+1);
    }
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
	
	/* test = "#respData.GROUP_TYPE";
	
	alert("8989"+test); */

	$("#MerchantInfo").hide();
	$("#MerchantAdminInfo").hide();

	$(listDate).each(function(i,val){
 		$('#'+val).datepicker(datepickerOptions);
	});

 	var mydata ='${responseJSON.LOCATION_LIST}';
 	var json = jQuery.parseJSON(mydata);
	 var options = "";
 	$.each(json, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	      options = $('<option/>', {value: v.val+"-"+v.key, text: v.val+"-"+v.key }).attr('data-id',i);
	    // append the options to job selectbox
	    $('#officeLocation1').append(options);
	});

	var merchantList='${responseJSON.MERCHANT_LIST}';
	var json = jQuery.parseJSON(merchantList);
	var options = "";
 	$.each(json, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	      options = $('<option/>', {value: v.val, text: v.key }).attr('data-id',i);
	    // append the options to job selectbox
	    $('#merchantId').append(options);
	});

	var groupType='${responseJSON.GROUP_TYPE}';
	var userlevel='${responseJSON.USER_LEVEL}';
    // alert(userlevel);
	mydata ='${responseJSON.USER_DESIGNATION}';

	if(userlevel=="MS" || userlevel=="MU"){
		$("#MerchantInfo").show();
	}
	if(userlevel=="MA" ){
		$("#MerchantAdminInfo").show();
		var data='${responseJSON.MERCHANT_ADMIN_LIST}';
		var json = jQuery.parseJSON(data);
		var options = "";
		$.each(json, function(i, v) {
			// create option with value as index - makes it easier to access on change
			  options = $('<option/>', {value: v.val, text: v.val+'-'+v.key }).attr('data-id',i);
			// append the options to job selectbox
			$('#merchantAdminId').append(options);
		});
	}
	var userdesignation="";
	json = jQuery.parseJSON(mydata);
 	$.each(json, function(i, v) {
			var userdesig=v.val;
			userdesig=userdesig.split("-")[0];
			if(userlevel==userdesig) {
				userdesignation=v.val;
			}
			options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);
			$('#adminType').append(options);
	});

	$("#adminType").val(userdesignation);
	$('#adminType').prop('disabled', true);


	$('#merchantId').live('change', function () {

		var merchantId=$('#merchantId').val();
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

	
/* if( test="#respData.GROUP_TYPE != 'MERTADMIN'" ){
	mydata ='${responseJSON.USERS_LIST}';
		json = jQuery.parseJSON(mydata);
		if(mydata != '' || mydata.indexOf(",") !=-1 ) {
		$.each(json, function(i, v) {
			options = $('<option/>', {value: v, text: v}).attr('data-id',i);
			$('#userId').append(options);
		});
	}
} */

	 /*  <s:if test="#respData.GROUP_TYPE != 'MERTADMIN'" >
			mydata ='${responseJSON.USERS_LIST}';
	 		json = jQuery.parseJSON(mydata);
	 		if(mydata != '' || mydata.indexOf(",") !=-1 ) {
				$.each(json, function(i, v) {
					options = $('<option/>', {value: v, text: v}).attr('data-id',i);
					$('#userId').append(options);
				});
			}
	  </s:if>  
*/
	
	

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

<s:if test="#respData.GROUP_TYPE == 'MERTADMIN'" >
	var listid = "adminType,officeLocation1,storeId,merchantId".split(",");
	var headerList = "userId,employeeNo,firstName,lastName,officeLocation1,email".split(",");
</s:if>
<s:else>
	var listid = "adminType,officeLocation1,userId,storeId,merchantId".split(",");
	var headerList = "userId,employeeNo,firstName,lastName,officeLocation1,email".split(",");
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
	$('#fax').val('');
	$('#expiryDate').val('');
	$('#aval').text('');
	//$('#merchantId').text('');
	//$('#storeId').text('');
console.log("listid..:"+listid);
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
					<s:if test="#respData.GROUP_TYPE == 'MERTADMIN'" >
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
		userId : { required : true },
		employeeNo : { required : true } ,//regex: /^[0-9]+$/
		//firstName : { required : true,regex: /^[a-zA-Z]+$/} ,
		firstName : { required : true} ,
		//lastName : { required : true,regex: /^[a-zA-Z]+$/ } ,
		lastName : { required : true } ,
		//adminType : { required : true } ,
		officeLocation1 : { required : true } ,
		//expiryDate : { required :true, date : true } ,
		email : { required : true , email : true},
		mobile : { required : true }
		
		//merchantId : { required : true }
		//storeId
		//merchantAdminId :  {required : true},
		
		/* merchantId : { 
			required: {
				depends: function(element) {
					if (($('#merchantAdminId').val() == ' ' || $('#merchantAdminId').val() == null) ){
						return true;
					} else {
						return false;
				   }
				}
			 }
		  },
		  storeId : { 
				required: {
					depends: function(element) {
						if (($('#merchantAdminId').val()  == ' ' || $('#merchantAdminId').val() == null) ){
							return true;
						} else {
							return false;
					   }
					}
				 }  
			  },
			  
		merchantAdminId : { 
			required: {
				depends: function(element) {
					if (($('#merchantId').val() != '' || $('#merchantId').val() != null) && 
						($('#storeId').val() != '' || $('#storeId').val() != null) ){
						return true;
					 } else {
						return false;
				   }
				}
			 }
		  } */
		  
	   },
	   
	   
	messages : {
		  userId : {
					required : "Please Select User Id.",
 				    },
		  employeeNo : {
						required : "Please Enter ID/Driving License.",
						//regex : "Employee No, contain digits only."
					   },
		   firstName : {
						required : "Please Enter First Name."
						//regex : "First Name, can not allow digits or special characters."
					 },
		  lastName : {
						required : "Please Enter Last Name."
						//regex : "Last Name, can not allow digits or special characters."
					},
		/* adminType : {
						required : "Please Choose User Designation."
					}, */
		   officeLocation1 : {
						required : "Please Choose Office Location."
			},
		/* expiryDate : {
						required : "Please Select Expiry Date.",
						date  : "Expiry date, should be always greater than system date."
					}, */
		    email : {
						required : "Please Enter email address.",
						email : "Please enter a valid email address."
			},
			mobile : {
				   required : "Please Enter Mobile No."
			}
			
			/* ,
			merchantAdminId : { 
				   required : "Please Choose MerchantAdmin Id."
			} ,
			merchantId : { 
				   required : "Please Choose Merchant Id."
			},
			storeId : { 
				   required : "Please Choose Store Id."
			} */
			
	}
};

function recordCheck(idtocheck,userIdInput){
	var check = false;
	var userIdRecords = new Array();
	//console.log('Inside Record check......');
	try {
		if($(idtocheck).length > 0) {
			// To Check The Record Exists In The Add-Row
			$(idtocheck+' > td:nth-child(4)').each(function(indx){
				console.log("Records : ");
				userIdRecords[indx]=$(this).text().trim();
				console.log(userIdRecords);
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

	//For Closing Select Box Error Messages_Start
	$(document).on('change','select',function(event) { 

	if($('#'+$(this).attr('id')).next('label').text().length > 0) {
	 $('#'+$(this).attr('id')).next('label').text(''); 
	 $('#'+$(this).attr('id')).next('label').remove();
	}

	});
	//For Closing Select Box Error Messages_End
	
	// Update Edited-Row Starts Here.
	$('#modCap').live('click', function () {
		$("#error_dlno").text('');
		$("#form1").validate(userictadminrules);
		
		if($("#form1").valid()) {

			var  userIdToSend = $('#userId').val();



				var queryString = "entity=<s:property value='loginEntity'/>&method=searchUser&userId="+userIdToSend+"&employeeNo="+$('#employeeNo').val();
			///alert(queryString);
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

						/* var spanValues = modDataVals("MODIFY");
						alert(spanValues); */
					}
					//alignSerialNo(textMess);
				});



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
			$('#multiData').val(finalData);
	 
 			var url="${pageContext.request.contextPath}/<%=appName %>/merchantictAdmincreatePageConfirm.action";
			$("#form1")[0].action=url;
			$("#form1").submit();
			 }else {
				$("#error_dlno").text("Please add atleast one user.");
			}
	});
	
	

	$("#telephoneRes,#telephoneOff,#mobile,#fax").keypress(function (e) {
	 //if the letter is not digit then display error and don't type anything
	 if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		//display error message
		$("#"+$(this).attr('id')+"_err").html("Digits Only").show().fadeOut(4000);
			   return false;
		}
   });

	$.validator.addMethod("regex", function(value, element, regexpr) {
		 return regexpr.test(value);
	   }, "");



	// Add Row Starts Here.
	$('#addCap').live('click', function ()
	{
		 
		$("#error_dlno").text('');
		$("#form1").validate(userictadminrules);
		
		var textMess = "#tbody_data > tr";
		
 		   // Is To Check User-Id Exist's In DB Or Not
 		
 		   //alert("cuming");
 		
		if($("#form1").valid() && $("#form1").manualValidation()) {
			
			//if($("#form1").valid()){
					
			 //alert("cuming1");
			
			var userIdToSend = "";
			
			//userIdToSend = $('#userId').val();
			
			//alert("iiiiiiii"+userIdToSend);
			
			
			<s:if test="#respData.GROUP_TYPE == 'MERCHANTGRP'" > 
			
				 userIdToSend = $('#userId').val();
				 
			</s:if>
			<s:else>
			//alert("cuming2");
				 userIdToSend = $('#userId option:selected').val();
			</s:else>

			//alert("cuming3");
			
			//if(recordCheck(userIdToSend)) {
				
 			if(recordCheck(textMess,userIdToSend)) {
 				
 				//alert("inside cuming");

				var queryString = "entity=<s:property value='loginEntity'/>&method=searchUser&userId="+userIdToSend+"&employeeNo="+$('#employeeNo').val();

				$.getJSON("postJson.action", queryString,function(data) {

					userstatus = data.status;

					console.log(data);

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

	// Clear Form Records Row Starts Here.
	$('#row-cancel').live('click', function () {
		$("#error_dlno").text('');
		 clearVals();

		//Show Add Button and Hide Update Button
		$('#modCap').hide();
		$('#addCap').show();
	});

	/* <s:if test="#respData.GROUP_TYPE != 'MERTADMIN'" >
		$('#userId').live('change', function () {
				$("#error_dlno").text('');
				if($('#userId option:selected').val() != '') {
					var queryString = "method=userDetails&userId="+$('#userId option:selected').val();
					$.getJSON("postJson.action", queryString,function(data) {
						var dataUser = data.responseJSON.USERS_LIST;
						$.each(dataUser, function(i, v) {
							$('#firstName').val(v.givenName);
							$('#lastName').val(v.sn);
							$('#email').val(v.mail);
						});
					});
				} else {
					$('#firstName').val('');
					$('#lastName').val('');
					$('#email').val('');
				}
			});

	</s:if> */


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


			var service=eachfieldArr[0];
			var accountname=eachfieldArr[2];
			var openbalance=eachfieldArr[3];
			var closebalance = eachfieldArr[1];
			var accounttype = offArr[i];
			var accounttype1 = eachfieldArr[12];

			var appendTxt = "<tr align='center' id='tr-"+rowindex+"' index='"+rowindex+"'>"+
				 "<td class='col_"+colindex+"'> &nbsp;"+colindex+"   </td> "+
				 "<td>"+service+"</td>"+
				 "<td>"+closebalance+"</td>"+
				 "<td>"+accountname+" </td>"+
				 "<td>"+openbalance+"</td>"+
				 "<td>"+accounttype+"</td>"+
				 "<td>"+accounttype1+"</td>"+
				 "<td><a class='btn btn-min btn-info' href='#' id='editDat' index='"+rowindex+"'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel' index='"+rowindex+"'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete' index='"+rowindex+"'> <i class='icon-trash icon-white'></i></a></td></tr>";

			$("#tbody_data").append(appendTxt);
			rowindex++;
		}
		alignSerialNo("#tbody_data > tr");
	}

});

$('#lastName').live('blur', function ()
{
	if($('#firstName').val().length > 0 && $('#lastName').val().length > 0)
	{
		$.ajax({
		    url : "${pageContext.request.contextPath}/getautouserid.action",
		    type: "POST",
		    data : "firstName="+$('#firstName').val()+"&lastName="+$('#lastName').val(),
		    success: function(data, textStatus, jqXHR)
		    {
		    	if(data.userid.length > 0)
		    	{
		    		$('#userId').val(data.userid.toUpperCase());
		    		$('#aval').html("<font color='green'>Available</font>");
		    	}
		    	else
		    	{

		    	}
		    },
		    error: function (jqXHR, textStatus, errorThrown)
		    {
		    }
		});
	}
});

$('#userId').live('blur', function ()
{
	if($('#userId').val().length > 0)
	{
		$.ajax({
		    url : "${pageContext.request.contextPath}/<%=appName %>/availablitycheck.action",
		    type: "POST",
		    data : "userid="+$('#userId').val(),
		    success: function(data, textStatus, jqXHR)
		    {
				$('#aval').empty();
		    	if(data.AVAILABLITY == "YES")
		    		$('#aval').html("<font color='green'>Available</font>");
		    	else
		    		$('#aval').html("<font color='red'>"+$('#userId').val()+" Not Available</font>");
		    },
		    error: function (jqXHR, textStatus, errorThrown)
		    {
		    }
		});
	}
});

var groupType;
$(document).ready(function()
{
	groupType = '${respData.GROUP_TYPE}';
	var formInput="firstName="+$('#firstName').val()+"&lastName="+$('#lastName').val();

	console.log('${responseJSON}');
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
 							<td><label for="First Name"><strong>First Name<font color="red">*</font></strong></label></td>
							<td><input name="firstName" id="firstName"  type="text" class="field" required=true  maxlength="50" /></td>
 							<td><label for="Last Name"><strong>Last Name<font color="red">*</font></strong></label></td>
							<td><input name="lastName" type="text" class="field" id="lastName"  required=true maxlength="50" /></td>
						</tr>
						<tr class="odd">
 							<td width="15%"><label for="User Id"><strong>User Id<font color="red">*</font></strong></label></td>
							<td width="30%">
								 <s:if test="#respData.GROUP_TYPE == 'MERCHANTGRP'" >
									<input name="userId" id="userId" class="field" type="text" required=true   maxlength="50"/>
									<div id="aval"></div>
								</s:if>
								<s:else >
									 <select id="userId" name="userId"    data-placeholder = "Choose UserId..."
										 class="chosen-select-width" style="width: 250px;" required=true>
										<option value="">Select</option>
 									</select>
								</s:else>

								<!-- <input name="userId" id="userId" class="field" type="text" required=true   maxlength="50"/>  -->

							</td>
 							<td width="20%"><label for="ID/Driving License"><strong>ID/Driving License<font color="red">*</font></strong></label></td>
	                    	<td width="30%"><input name="employeeNo" id="employeeNo" type="text"  class="field" required=true   maxlength="10" /> <span id="employeeNo_err" class="errmsg"></span> </td>
						</tr>
						<tr class="odd">
 							<td><label for="Telephone Res"><strong>Telephone(Res)</strong></label></td>
							<td><input name="telephoneRes" type="text" class="field" id="telephoneRes"  customvalidation="max::10||min::10" maxlength="10" size="30" /><span id="telephoneRes_err" class="errmsg"></span></td>
 					        <td><label for="Telephone Off"><strong>Telephone(Off)</strong></label></td>
					        <td><input name="telephoneOff" type="text" class="field" id="telephoneOff"  maxlength="10" size="30" /><span id="telephoneOff_err" class="errmsg"></span></td>
				       </tr>

				     <tr class="odd">
 						    <td><label for="Mobile"><strong>Mobile<font color="red">*</font></strong></label></td>
							<td>
								<select id="code" name="code" onchange="countrycode()" style="width: 90px;"
										data-placeholder = "Choose countrycode..."
										class="chosen-select-width" required =true>

										<option value="234">234</option>
								</select>
							&nbsp;<input name="mobile" type="text" class="field" id="mobile" customvalidation="max::9||min::9" maxlength="9" size="10" required=true style="width:115px;" /><span id="mobile_err" class="errmsg"></span></td>
							<td><label for="E-Mail"><strong>E-Mail<font color="red">*</font></strong></label></td>
							<td>
					 			<input name="email" type="text" class="field" id="email" required=true  maxlength="70" size="90"/>
								<input name="password" type="hidden" class="field" id="password"   />
								<input name="encryptPassword" type="hidden" class="field" id="encryptPassword"  />
					</td>
				     </tr>

				    <tr class="odd">
 					  <td><label for="User Designation"><strong>User Level<font color="red">*</font></strong></label></td>
					  <td>
						<select id="adminType" name="adminType" data-placeholder="Choose User Designation..."
											class="chosen-select" style="width: 220px;" required=true  >
								<option value="">Select</option>
							</select>
					 </td>
 					 <td><label for="Office Location"><strong>Office Location<font color="red">*</font></strong></label></td>
					  <td>
						<select id="officeLocation1" name="officeLocation1" data-placeholder="Choose office location..."
										class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option>
						</select>
					   </td>
				    </tr>
				<tr class="odd" style="display:none">

					<td><label for="Expiry Date"><strong><font color="red"></font></strong></label></td>
					<td><input name="expiryDate" type="hidden" class="field" id="expiryDate" value="30/12/2044"  maxlength="20" size="20" readonly /></td>
				</tr>
				<tr class="odd" id="MerchantInfo">
 					<td><label for="Merchant Id"><strong>Merchant Id<font color="red">*</font></strong></label></td>
					<td>
					 	<select id="merchantId" name="merchantId"  data-placeholder="Choose  Merchant Id..."
										class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option>
						</select>
					</td>
 					<td><label for="Store Id"><strong>Store Id<font color="red">*</font></strong></label></td>
					<td>
					 	<select id="storeId" name="storeId" data-placeholder="Choose office Store Id..."
										class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option>
						</select>
					</td>
				</tr>
				<tr class="odd" id="MerchantAdminInfo">
 					<td><label for="Merchant Id"><strong>Merchant Id<font color="red">*</font></strong></label></td>
					<td colspan="3">
					 	<select id="merchantAdminId" name="merchantAdminId" data-placeholder="Choose office location..."
										class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option>
						</select>
					</td>

				</tr>
			</table>
		</fieldset>
			<div class="box-content">
				<table class="table table-striped table-bordered bootstrap-datatable dataTable"
							id="mytable" style="width: 100%;">
				  <thead>
						<tr>
							<th>Sno</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>User Id</th>
							<th>ID/Driving License</th>
							<th>E-Mail</th>
							<th>Office Location</th>
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
	                &nbsp;<input type="button" class="btn" type="text"  name="btn-cancel" id="btn-cancel" value="Cancel" width="100" ></input>
					<span id ="error_dlno" class="errors"></span>

	                <input name="tag" type="hidden" id="tag" value="newprod" />
					<input name="groupID" type="hidden" id="groupID" value="<s:property value="groupID"/>" />
					<input name="entity" type="hidden" id="entity" value="<s:property value="entity"/>" />
					<input type="hidden" name="multiData" id="multiData" value="<s:property value="multiData"/>" />
					<input type="hidden" name="officeLocation" id="officeLocation" value="<s:property value="officeLocation"/>" />
					<input type="hidden" name="typeuser" id="typeuser" value="Merchant" />
	        </div>

			</div>
			</div>
	</div>
</div>
</form>

<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
</body>
</html>