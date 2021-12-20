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

var acctRules = {
		   rules : {
			   chname : { required : true},
			   chval : { required : false}
		   },  
		   messages : {
			   chname : { 
			   		required : "Please Enter Channel Name"
				},
				chval : { 
			   		required : "Please Enter Channel Value."
				}
		   } 
		 };
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
 
function countrycode(){
	 var Code = $("#code").val();
	 $("#mobile").val(Code);
}


$(function() {

	//$("#MerchantInfo").hide();
	$("#MerchantAdminInfo").hide();
	
	
	//alert("8989898"+'${responseJSON.MERCHANTID_LIST}');
	   var type='${responseJSON.typeuser}';
	   //alert(type);
	   
	   if (type=='meradm')
		   {
			    options='<option value="MA">Merchant Admin</option>';
				$('#adminType').append(options);
				$('#adnwac').hide();
				$('#td3').hide();
				$('#td4').hide();
				$('#td5').show();
				$('#td6').show();
				
			}  

	   else if(type=='merusr'){
				options='<option value="MU">Merchant User</option><option value="MS">Merchant Supervisor</option>';
				   $('#adminType').append(options);
				   $('#adnwac').show();
				   $('#td3').show();
					$('#td4').show();
					$('#td5').hide();
					$('#td6').hide();
			}
		   

	
    var merchantList='${responseJSON.MERCHANTID_LIST}';
	var json = jQuery.parseJSON(merchantList);
	var options = "";
 	$.each(json, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	      options = $('<option/>', {value: v.val, text: v.key }).attr('data-id',i);
	    // append the options to job selectbox
	    $('#merchantId').append(options);
	});    

	mydata ='${responseJSON.USER_DESIGNATION}';
	
	$('#adminType').live('change', function () {

		  var optionSel = $("option:selected", this);
		    var valueSel = this.value;
		  //  alert(valueSel);
		    if(valueSel=='MS') 	 $('#adnwac').hide();
		    else if(valueSel=='MU') $('#adnwac').show();
		    
	$("#MerchantInfo").show();
	
	
	});
	var tcnt=null;
	var terminalList='${responseJSON.TERMINALID_LIST}';
	var json1 = jQuery.parseJSON(terminalList);
	//alert(json1);
	var options = "";
 	$.each(json1, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	      options = $('<option/>', {value: v.val, text: v.key }).attr('data-id',i);
	    // append the options to job selectbox
	    $('#Terminalid').append(options);
	    
	}); 
	
	$('#chname').on('change', function (e) {
	    var optionSelected = $("option:selected", this);
	    var valueSelected = this.value;
	  var tcnt=$('#Terminalid option').size(); 
	    
	    if(valueSelected=="POS"){
		$('#posdiv').show();
		$('#mobdiv').hide();
		if(tcnt=='1'){
		alert("Terminals Not Found.Please Create Terminals To Assign User to the Terminal Under this Store.");
		}
		
		//alert(tcnt);
	    }
	    else if(valueSelected=="Mobile"){
	    	$('#posdiv').hide();
			$('#mobdiv').show();	
	    }
});
	
	 
});

</script>

<script type="text/javascript">

var val = 1;
var rowindex = 0;
var colindex = 0;
var userstatus = "";
var v_message = "";

	<s:if test="#respData.typeuser == 'merusr'" >
		var listid = "location,".split(",");
		var headerList = "adminType,storeId,userId,merchantId,mobile,location,employeeNo,firstName,lastName,email".split(",");
	</s:if>
	<s:else>
		var listid = "location,".split(",");
		var headerList = "adminType,userId,merchantId,mobile,location,employeeNo,firstName,lastName,email".split(",");
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
	$('#storeId').text('');
      //console.log("listid..:"+listid);
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
				
						if( nameInput == 'location'  
								) {
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
			console.log(index);
			console.log(" JC "+tabArry[index]);
			<s:if test="#respData.typeuser == 'merusr'" >
			if(index==3||index==5||index==9||index==8){
				tabArrText+="<td style='display:none'>"+tabArry[index]+"</td> ";
			}
			else
				{
			tabArrText+="<td>"+tabArry[index]+"</td> ";
				}
			</s:if>
			<s:else >
			if(index==3||index==5||index==7||index==8){
				tabArrText+="<td style='display:none'>"+tabArry[index]+"</td> ";
			}
			else
				{
			tabArrText+="<td>"+tabArry[index]+"</td> ";
				}
			</s:else>
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
		var hiddenInput ="";
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
				  if( nameInput == 'location') {
					modTabArry[tabarrindex] = $('select#location option:selected').text();
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
		firstName : { required : true,regex: /^[A-Z]+$/} ,
		lastName : { required : true,regex: /^[A-Z]+$/} ,
		location : { required : true } ,
		email : { required : true , email : true},
		mobile : { required : true }
		
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
						required : "Please Enter First Name.",
						regex : "First Name, can not allow digits or special characters."
					 },
		  lastName : {
						required : "Please Enter Last Name.",
						regex : "Last Name, can not allow digits or special characters."
					},
		   location : {
						required : "Please Choose Office Location."
			},
	
		    email : {
						required : "Please Enter email address.",
						email : "Please enter a valid email address."
			},
			mobile : {
				   required : "Please Enter Mobile No."
			}
			
		
			
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
				//console.log(userIdRecords);
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

					//console.log(data);

					v_message = data.message;

					if(userstatus == "FOUND") {
						if(v_message != "SUCCESS") {
							$('#error_dlno').text(v_message);
						}
					} else {
					}
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
		 
		 var selt = $("#chname").val();
		/*  if(selt=="POS")
			 {
			 
			 $("#chval").val($("#Terminalid").val());
			 }
		 else
			 {
			 $("#chval").val($("#mobval").val());
			 } */
		

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
			$("#multiData").val(finalData);
			$('#officeLocation').val(offVal);
			
			var channelData="";
			
			 $('#multi-row-data1 > span').each(function(index) {  
	 				if(index == 0)  {channelData = $(this).text(); console.log("in index"+channelData);}
					else {channelData += "#"+ $(this).text();console.log("in index else"+channelData);}
				 }); 

			 $('#channeldata').val(channelData);
			 
			 //alert(finalData);
			 //$('#userId').val();
			 console.log("multidata valueee"+$("#multiData").val());
			 console.log("channeldata valueee"+$("#channeldata").val());
			   // alert("-----"    +finalData);
	 
	 			var url="${pageContext.request.contextPath}/<%=appName %>/merchantCreatePageConfirm.action";
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
 	
		if($("#form1").valid()) {
			
			var userIdToSend = "";
			
		 	<s:if test="#respData.GROUP_TYPE == 'MERCHANTGRP'" > 
				 userIdToSend = $('#userId').val();
			</s:if>
			<s:else>
				 userIdToSend = $('#userId option:selected').val();
			</s:else> 

 			if(recordCheck(textMess,userIdToSend)) {

				var queryString = "entity=<s:property value='loginEntity'/>&method=searchUser&userId="+userIdToSend+"&employeeNo="+$('#employeeNo').val();

				$.getJSON("postJson.action", queryString,function(data) {

					userstatus = data.status;
					//console.log(data);
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
				 "<td>"+accountname+"</td>"+
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

/* $('#lastName').live('blur', function ()
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
}); */


$('#lastName').live('blur', function () {

	var formInput='firstName='+$('#firstName').val()+'&lastName='+$('#lastName').val();
	$.getJSON('getautouserid.action', formInput,function(data) {
			$('#userId').val(data.userid.toUpperCase());
    		$('#aval').html("<font color='green'>Available</font>");
	});
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

	/* console.log('${responseJSON}'); */
});



  $(function(){
	
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
/** Form2 Add,Modify Starts*/
var val = 1; 
var rowindex1 = 0;
var colindex1 = 0;
var userstatus1 = "";
var masterstatus1 = "";
var v_message1 = "";

var tabArry ; 
 
var index1 = "";
var searchTdRow1 = "";
var searchTrRows1 = "";
var selTextList1 = ""; 
var rowCount1 = 0;
 
function clearVals1(){ 
	$('#chname').val('');
	$('#chval').val(''); 
	
	 
	rowCount1 = $('#tbody_data > tr').length; 
	if(rowCount1 > 0 )  $("#error_dlno").text('');
}

function commonData1(id,type){
	var hiddenInput ="";
	var hiddenNames = "";
	var tabarrindex = 0;
	tabArry = new Array();
	modTabArry = new Array(); 
	try {
 		
		$(id).find('input[type=text],input[type=hidden],select').each(function(indxf){ 
			var nameInput = "";
			var valToSpn =  "";
			try {
				  nameInput = $(this).attr('name'); 
				  valToSpn = $(this).attr('value').length == 0 ? " " : $(this).attr('value');
				  
				 // console.log("nameInput   "+nameInput);
				  //console.log("valToSpn   "+valToSpn);
			} catch(e1) {
				console.log('The Exception Stack is :: '+ e1);
			}  
			 var Code = $("#chname").val();
			 var kk='';
			  if(Code=="POS") kk="Terminalid";
			  else kk="mobval";
			  
			
			if(nameInput != undefined) {
			  if(indxf == 0)  {
				hiddenInput += valToSpn;
				hiddenNames += nameInput;
			  } else {
				  if(nameInput==kk){
					  //alert(valToSpn);
				hiddenInput += ","+valToSpn; 
				hiddenNames += ","+nameInput;
				} 
			  }
			  
			  console.log("hidden names   [ "+hiddenNames +" ]hidden input ["+hiddenInput );
			  
			 
			
			  if(Code=="POS"){
				  //alert(Code);
			  var listid1 = "".split(",");
			  var headerList1 = "chname,Terminalid".split(",");
			  }else if(Code=="Mobile"){
				  //alert(Code);
			  var listid1 = "".split(",");
			  var headerList1 = "chname,mobval".split(",");
			  }
			  
			  
			   if(jQuery.inArray(nameInput, headerList1) != -1){  
					tabArry[tabarrindex] = valToSpn; 
 				  tabarrindex++;
 				  console.log("valToSpn"+valToSpn);
			  }
			} 			 
		}); 
 		
	} catch (e) {
		console.log(e);
	}  
	return hiddenInput+"@@"+hiddenNames;
}

var addAccountInfo1 = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	 var tabArrText = ""; 
	 var data1 = "";
	try { 
		data1 = commonData1('#account-details','ADD');
		 
		rowindex1 = $("#multi-row-data1 > span") .length ;
		
		$("#multi-row-data1").append("<span id='hidden_span_"+rowindex1+"' index="+rowindex1+" ind-id='"+tabArry[0]+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
		$('#multi-row-data1 > span#hidden_span_'+rowindex1).append(data1.split("@@")[0]);
		console.log("row indexxxxxxxxxxx"+tabArry[0] +"assssssssssss"+data1);
		var addclass = ""; 
		
		if(val % 2 == 0 ){
			addclass = "even";
			val++;
		} else {
			addclass = "odd";
			val++;
		}
		
		colindex1 = ++ colindex1;
		
		$(tabArry).each(function(index){ 
			tabArrText+="<td>"+tabArry[index].trim()+"</td> ";
		});
		//alert(tabArrText);
		appendTxt = "<tr class="+addclass+" align='center' id='tr-"+rowindex1+"' index='"+rowindex1+"'>"+
				"<td class='col_"+colindex1+"'>"+colindex1+"</td>"+ tabArrText+ 
 				"<td> <a class='btn btn-min btn-danger' href='#' id='delete-account' index='"+rowindex1+"' title='Delete Account' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";
 		console.log("hiiiiiiiiii"+appendTxt);
	} catch(e) {
		console.log(e);
	}   
	return appendTxt; 
};
 
/** Form2 Add,Modify Ends*/

function alignSerialNo1(serialId) {  
	if($(serialId).length > 0) {
		$(serialId +' > td:first-child').each(function(index){  
			$(this).text(index+1);
		}); 
	}
}

function recordCheck1(idtocheck,billerId,tid){ 
	var check = false; 
	var userIdRecords = new Array();
	var data = '';
	 
	try { 
		if($(idtocheck).length > 0) {
			// To Check The Record Exists In The Add-Row
			$(idtocheck+' > td:nth-child(2)').each(function(indx){ 
				//console.log($(this).text());
 				userIdRecords[indx]=$(this).text().trim(); 
			}); 
			
			if(jQuery.inArray(billerId.toUpperCase().trim(), userIdRecords) != -1){ 
				//check = true; 
				if(tid=='1'){
				data = 'Account exists in the above table, please input another.';
				}
				else
					{ data = 'Account already added.'; }
			}  else {
				data =  'NO';
			} 
			
		} else {
			return 'NO';
		} 
		
	} catch(e){
		console.log(e);
		return 'Error while adding biller id.';
	} 
	 
	return data;
}




function loadToolTip(){
	$('[rel="tooltip"],[data-rel="tooltip"]').tooltip({"placement":"bottom",delay: { show: 400, hide: 200 }});
}
$(function(){

	/** Form2 Add Row Validation Starts*/
		// Add Row Starts Here.
		 $('#add-account').live('click', function(){
			 
			 
	 		$("#error_dlno").text('');	 
			$("#form2").validate(acctRules); 
			 var textMess = "#tbody_data1 > tr";  
			 var flag = false; 
			 
			  if($("#form2").valid() ) {  
					// Is To Check Account Exist's or not 
					var dat = recordCheck1("#tbody_data1 > tr",$('#chname').val(),'2');
 					
						if(dat == 'NO'){
							 
							   
								var appendTxt = addAccountInfo1("ADD");
								//alert(appendTxt);
								console.log("in append "+appendTxt);
		 						$("#tbody_data1").append(appendTxt);  
		 						clearVals1();
								alignSerialNo1(textMess); 
								$('#error_dlno').text('');
								loadToolTip();  
						} 
						else {
							$('#billerMsg').text(dat);
							} 
				 } else { 
					 return false;
				 }  
		}); 
		 
		
		// The below event is to delete the entire row on selecting the delete button 
		$("#delete-account").live('click',function() { 
			var delId = $(this).attr('index');
			$(this).parent().parent().remove();
	 		
			var spanId = "";
			$('#multi-row-data1 > span').each(function(index){  
				spanId =  $(this).attr('index'); 
				if(spanId == delId) {
					$(this).remove();
				}
			}); 
			
			clearVals();
			// Aligning the serial number
			alignSerialNo1("#tbody_data1 > tr"); 
		 
			$('.tooltip').remove();
			$('#billerMsg').text('');
			$('#error_dlno').text('');
		}); 
		
		// Clear Form Records Row Starts Here.
		$('#row-cancel').live('click', function () {
			$("#error_dlno").text(''); 
			 clearVals();  
		});  
		
}); 

/** Form2 Add Row Validation Ends*/
</script>


</head>
<body>
<div id="content" class="span10">



	<!-- content starts -->
	<div>
		<ul class="breadcrumb">
		  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
		  <li> <a href="userGrpCreation.action">User Management</a> <span class="divider"> &gt;&gt; </span></li>
		  <li> <a href="#">User Creation</a></li>
		</ul>
	</div>

<form name="form1" id="form1" method="post">
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
							
								  <input name="userId" id="userId" type="text"  class="field" required=true readonly  maxlength="15" /> <span id="userId_err" class="errmsg"></span>  
									<!-- <div id="aval"></div> -->
								 <%-- <s:if test="#respData.GROUP_TYPE == 'MERCHANTGRP'" >
									<input name="userId" id="userId" class="field" type="text" required=true   maxlength="50"/>
									
								</s:if>
								<s:else >
									 <select id="userId" name="userId"    data-placeholder = "Choose UserId..."
										 class="chosen-select-width" style="width: 250px;" required=true>
										<option value="">Select</option>
 									</select>
								</s:else> --%>

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
					<tr class="odd" id="MerchantInfo">
 					<td><label for="Merchant Id"><strong>Merchant Id<font color="red">*</font></strong></label></td>
 					<td><input name="merchantId" id="merchantId"  type="text" class="field" required=true  maxlength="50" value='<s:property value="#respData.merchantID" />' /></td> 
 					<td id='td3'><label for="Store Id"><strong>Store Id<font color="red">*</font></strong></label></td>
 					<td id='td4'><input name="storeId" id="storeId"  type="text" class="field" required=true  maxlength="50"  value='<s:property value="#respData.storeId" />' /></td>
 					<td id='td5' style="display:none"></td> 
 					<td id='td6' style="display:none"></td> 
					</tr>
					
					
				    <tr class="odd">
 				     <td><label for="User Designation"><strong>User Level<font color="red">*</font></strong></label></td>
					  <td colspan="3">
						<select id="adminType" name="adminType" data-placeholder="Choose User Level..."
											class="chosen-select" style="width: 220px;" required=true  >
								<option value="">Select</option>
							</select>
					 </td> 
					<%--  <td><label for="Office Location"><strong>Office Location<font color="red">*</font></strong></label></td>
							<!-- <td><input name="location" id="location"  type="text" class="field" required=true  maxlength="50" /></td> --> 
 					  <td><label for="Office Location"><strong>Office Location<font color="red">*</font></strong></label></td>
					  <td>
						<select id="location" name="location" data-placeholder="Choose office location..."
										class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option>
						</select>
							<s:select cssClass="chosen-select"
								headerKey=""
								headerValue="Select"
								list="#respData.LOCATION_LIST"
								name="location"
								value="#respData.location"
								id="location" requiredLabel="true"
								theme="simple"
								data-placeholder="Choose Location..."
							 />
					   </td>  --%>
					 
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
							<th>Mobile Number</th>
							<th>Merchant ID</th>
							<s:if test="#respData.typeuser == 'merusr'" >
							<th>Store ID</th>
							</s:if>
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
	               

	                <input name="tag" type="hidden" id="tag" value="newprod" />
					<input name="groupID" type="hidden" id="groupID" value="<s:property value="groupID"/>" />
					<input name="entity" type="hidden" id="entity" value="<s:property value="entity"/>" />
					<input type="hidden" name="multiData" id="multiData" value="<s:property value="multiData"/>" />
					<input type="hidden" name="officeLocation" id="officeLocation" value="<s:property value="officeLocation"/>" />
					<input type="hidden" name="typeuser" id="typeuser" value="<s:property value="typeuser"/>" />
					<input type="hidden" name="channeldata" id="channeldata" value="<s:property value="channeldata"/>" />
					<input type="hidden" name="chval" id="chval" />
	        </div>

			</div>
			</div>
	</div>

</form>


<form name="form2" id="form2" method="post"> 	
		<div class="row-fluid sortable" id="adnwac"> 
			<div class="box span12" > 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Add Channel
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
					
				<div class="box-content" id="account-details">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
						
							<td width="20%"><label for="Account Number"><strong>Channel Name<font color="red">*</font></strong></label></td>
							<td width="30%">
							
							<%-- <s:select cssClass="chosen-select"
								headerKey=""
								headerValue="Select"
								list="#respData.CHANNELS"
								name="location"
								value="#respData.location"
								id="location" requiredLabel="true"
								theme="simple"
								data-placeholder="Choose Location..."
							 /> --%>
														
							
							<select id="chname" name="chname" style="width: 240px;" class="chosen-select-width" required =true>
									<option value="Select">Select</option>
									<option value="POS">POS</option>
									<option value="Mobile">Mobile</option>
								</select> 
							 </td>
							<td width="20%"><label for="Channel value"><strong>Channel Value</strong></label> </td>
							<td width="30%"><div id="posdiv" style="display:none">
							<select id="Terminalid" name="Terminalid" data-placeholder="Choose Terminal..."
										class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option>
						</select></div>
						<div id="mobdiv" ><input name="mobval" id="mobval"  type="text" class="field" required=true  maxlength="50" /></div>
							</td>
						</tr>  
						<tr class="odd"> 
								<td colspan="4" align="center">
									<input type="button" class="btn btn-success" 
										name="add-account" id="add-account" value="Add Channel" /> &nbsp;  
									<span id="billerMsg" class="errors"></span>
								</td> 
						</tr> 
 				 </table>
				</fieldset> 
				</div> 
				
				<div class="box-content"> 
						<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
									id="mytable" style="width: 100%;">
						  <thead>
								<tr>
									<th>Sno</th>
									<th>Channel Name</th>
									<th>Channel Value </th>
									<th>Actions</th> 
								</tr>
						  </thead>    
							 <tbody id="tbody_data1"> 
							 	<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
									<s:param name="jsonData" value="%{accBean.newAccountData}"/>  
									<s:param name="searchData" value="%{'#'}"/>  
								</s:bean> 
								<s:iterator value="#jsonToList.data" var="mulData2"  status="mulDataStatus" >   
											<script type="text/javascript">
												var hiddenNames1 = "";
												$(function(){
													$('#account-details').find('input[type=text],input[type=hidden]').each(function(index){ 
														var nameInput = $(this).attr('name'); 
														if(nameInput != undefined) {
														  if(index == 0)  {
															hiddenNames1 = nameInput;
														  } else {
															hiddenNames1 += ","+nameInput; 
														  }  
														} 
													}); 
													var data1 = "<s:property />";
													data1 = data1.split(",");
													$("#multi-row-data").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
												});
												</script> 
											
											<s:if test="#mulDataStatus.even == true" > 
												<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:if>
											<s:elseif test="#mulDataStatus.odd == true">
												<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:elseif> 
									
										<td><s:property value="#mulDataStatus.index+1" /></td>
											<s:generator val="%{#mulData2}"
												var="bankDat" separator="," >  
												<s:iterator status="itrStatus">  
														<td><s:property /></td> 
												</s:iterator>  
											</s:generator>
										<td>  
											<a class='btn btn-min btn-danger' href='#' id='delete-biller' index='<s:property value="#mulDataStatus.index" />' title='Delete Biller' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a>
										</td>
									</tr>
								</s:iterator>
							 </tbody>
						</table> 
					</div>   
			
					<span id="multi-row-data1" class="multi-row-data1" style="display:none"> </span>
			  </div>
		</div>  
		
		<div id="dialog" title="Confirmation Required" style="display:none">
		   Proceed ? <!-- <div id="dia1"></div><font color="red"><div id="dia2"></div> --></font>
		</div>
		
</form>	


<%-- <input type="button" class="btn btn-success" type="text"  name="btn-submit" id="btn-submit" value="Submit" width="100" ></input>
	               <input type="button" class="btn" type="text"  name="btn-cancel" id="btn-cancel" value="Cancel" width="100" ></input>
					<span id ="error_dlno" class="errors"></span> --%>
					
<div > 
<a href="#" id="btn-cancel" class="btn btn-danger ajax-link">Back </a>&nbsp;
<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Submit</a>	
<span id ="error_dlno" class="errors"></span>	 
</div> 

</div>

<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
</body>
</html>