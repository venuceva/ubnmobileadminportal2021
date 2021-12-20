
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Brian Kiptoo">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<%String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<style type="text/css">
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
input#merchantName{text-transform:uppercase};

.errmsg {
	color: red;
}
.errors {
	color: red;
}
</style>
<s:set value="responseJSON" var="respData"/>
<script type="text/javascript" >


$(document).ready(function() {
	$("#form4").hide();
  	$("#userData1").hide();
	$("#userData2").hide();
	//$('#bankAgenctNo').val((Math.random() * 100000).toString().substring(0,7).replace(".",""));

	var val = 1;
	var rowindex = 1;
	var colindex = 0;
	  $.validator.addMethod("regex", function(value, element, regexpr) {
			 return regexpr.test(value);
	  }, "");
});

function getGenerateMerchantScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateMerchantAct.action';
	$("#form1").submit();
	return true;
}
</script>
<script type="text/javascript">

 
 var merchantCreateRules= {
			rules : {
				merchantName : { required : true, minlength :4 },
				merchantID : {required : true, 	minlength :10, 	maxlength :15},
				storeId : {required : true},
				storeName : {required : true},
				location : {required : true},
				kraPin : {required : true },// regex: /^([A-Z]{1}[0-9]{5,}[A-Z]{0,})+$/ },
		 		managerName : {required : true},
				addressLine1 : {required : true},
				addressLine2 : {required : true},
				city		: {required : true},
				poBoxNumber : {required : true},
				//telephoneNumber1 : {required : true,maxlength: 10,minlength: 10,number:true},
				mobileNumber : {required : true , number : true},
				//faxNumber : {required : true , number : true},
				prmContactPerson : {required : true,  regex: /^[a-zA-Z\/\ ]+$/ },
				prmContactNumber : {required : true , number : true},
				email : {required : true, email : true},
		 		country : {required : true},
				memberType : {required : true},
				postalCode : {required : true, number : true},
				area : {required : true},
				lrNumber : { required : true, regex: /^[a-zA-Z0-9\/\ ]+$/ },
				merchantType : { required : true },
				accountNumber : { required : true },
				relationShipManagerNumber : { required : true },
				relationShipManagerName : { required : true },
				relationShipManagerEmail : { required : true , email : true },
				
			    latitude : { required : true , regex: /[-+]?(?:90(?:\\.0+)?|[1-8]?[0-9](?:\\.[0-9]+)?)/},	
			    //latitude : { required : true , regex: /^(\+|-)?(?:90(?:(?:\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\.[0-9]{1,6})?))$/},	
				//longitude : { required : true , regex: /^([1-9][.][1-9]|[1-9])/}
			    longitude : { required : true , regex: /[-+]?(?:180(?:\\.0+)?|(?:(?:[0-9]?[0-9]|1[0-7][0-9])(?:\\.[0-9]+)?))/},
			    googleCoOrdinates : { required : true }
	         },
			messages : {
				merchantName : { required : "Please Enter Merchant Name."},
				//merchantID : { required : "Merchant Id ."},
				//storeId : { required : "Please Enter Merchant Name."},
				storeName : { required : "Please Enter Store Name."},
				location : { required : "Please Select  Bank Branch."},
				kraPin : { required : "Please Enter KRA Pin." }, //regex : "Please Enter valid KRA." },
				managerName : { required : "Please Enter Manager Name.",
								regex:"Please Enter Valid Manager Name" },
				addressLine1 : { required : "Please Enter Address Line1."},
				addressLine2 : { required : "Please Enter Address Line2."},
				city		: { required : "Please Enter City/Town."},
				poBoxNumber : { required : "Please Enter P.O. Box Number."},
				//telephoneNumber1 : { required : "Please Enter Merchant Name."},
				mobileNumber : {required : "Please Enter Mobile Number.",
										number : "Please Enter Numbers Only."
					},
				//faxNumber : { required : "Please Enter Merchant Name."},
				prmContactPerson : { required : "Please Enter Primary Contact Person.",
									regex : "Please Enter valid Contact person"
					},
				prmContactNumber : { required : "Please Enter Primary Contact Number."},
				email : { required : "Please Enter Email."},
				country : { required : "Please Select Country."}, 
				memberType : { required : "Please Select Member Type."},
				postalCode : {
						required : "Please Enter Postal Code.",
						number : "Please Enter Numbers Only."
				},
				area : {
						required : "Please Select County."
		 		},
				lrNumber : {
						required : "Please Enter L/R Number.",
						regex : "Please Enter valid L/R Number."

				},
				merchantType : {
					required : "Please select Merchant Type."
				},
				
				relationShipManagerNumber : {
					required : "Please Enter RelationShip Manager Number."
				},
				
				relationShipManagerName : {
					required : "Please Enter RelationShip Manager Name."
				},
				
				relationShipManagerEmail : {
					required : "Please Enter RelationShip Manager  Email."
				},
				
				latitude : {
					required : "Please Enter Latitude.",
					regex    : "Please Enter valid  Number Latitude."
				} ,
				longitude : {
					required : "Please Enter Longitude.",
					regex    : "Please Enter valid  Number Longitude."
				}, 
			    googleCoOrdinates : {
					required : "Please Enter Google Coordinates.",
				}
				
			}
		};
 
	var form5Rules = {
		     rules : {
		    	 prmContactPerson : {  required: true},
		    	 prmContactNumber : {  required: true },
		    	 mobileNumber     : {  required: true }
				
				},
		    messages : {
		    	prmContactPerson : {   required: "Please Enter Primary Contact Person"},
		    	prmContactNumber : {   required: "Please Enter Primary  Contact Number"},
		    	mobileNumber     : {  required: "Please Enter Mobile Number" }
		    	
		      }
		};
	
		
	/* 
	var form4Rules = {
	     rules : {
			documentId : {  required: true   }
	    },
	    messages : {
				documentId : {   required: "Please Select DocumentId." }
	    },
		errorPlacement: function(error, element) {
		    if ( element.is( ':radio' ) || element.is( ':checkbox' ) )
				error.appendTo( element.parent() );
		    else if ( element.is( ':password' ) )
				error.hide();
		    else
				error.insertAfter( element );
		}
	}; */

   var form2Rules = {
     rules : {
		accountNumber : {  required: true , digits : true, maxlength: 14, minlength:14  },
		acctDescription : {  required: true },
		bankBranch : { required: {
						depends: function(element) {
						if (!$('#acc-check').is(":checked")){
							return true;
						} else {
							return false;
					   }
					}
				 }
		},
		bankName : {   required: {
						depends: function(element) {
						if ($('#acc-check').is(":checked")){
							return true;
						} else {
							return false;
					   }
					}
				 }
		}
    },
    messages : {
			accountNumber : {   required: "Please Enter Account Number."    },
			acctDescription : {   required: "Please Select Account Description." },
			bankBranch : {  required: "Please Select Branch Code."    },
			bankName : {   required: "Please Select Bank Code."   }
    } ,
	errorPlacement: function(error, element) {
	    if ( element.is( ':radio' ) || element.is( ':checkbox' ) )
			error.appendTo( element.parent() );
	    else if ( element.is( ':password' ) )
			error.hide();
	    else
			error.insertAfter( element );
	}
};

   
   


var val = 1;
var rowindex = 0;
var colindex = 0;
var userstatus = "";
var v_message = "";
var tabArry ;
var modTabArry ;
var modHeaderBodyArry ;
var listid = "acctDescription,bankBranch".split(",");
function clearVals(){
	$('#accountNumber').val('');
	$('#bankName').val('');

	$(listid).each(function(index,val){
		$('#'+val).find('option').each(function(i, opt ) {
			if( opt.value == '' ) {
				$(opt).attr('selected', 'selected');
				$('#'+val).trigger("liszt:updated"); //bankBranch
			}
		});
	});

	rowCount = $('#tbody_data1 > tr').length;
	if(rowCount > 0 )  $("#error_dlno").text('');
}

/** Form2 Add,Modify Starts*/
var index1 = "";
var searchTdRow = "";
var searchTrRows = "";
var selTextList = "acctDescription,bankBranch";
var headerList = "accountNumber,acctDescription,bankBranch,bankName".split(",");

function commonData(id,type){
	var hiddenInput ="";
	var hiddenNames = "";
	var tabarrindex = 0;
	tabArry = new Array();
	modTabArry = new Array();
	try {
		//console.log(id);
		$(id).find('input[type=text],input[type=hidden],select').each(function(indxf){
			var nameInput = "";
			var valToSpn =  "";
			try{
				  nameInput = $(this).attr('name') ;
				  valToSpn = ($(this).attr('value') =='' ? ' ' :$(this).attr('value'));
			} catch(e1) {
				//console.log('The Exception Stack is :: '+ e1);
			}
			//console.log("Type ["+$(this).attr('type')+"]  Name ["+$(this).attr('name')+"] Values["+$(this).val()+"]");

			if(nameInput != undefined) {
			  if(indxf == 0)  {
				hiddenInput += valToSpn;
				hiddenNames += nameInput;
			  } else {
				hiddenInput += ","+valToSpn;
				hiddenNames += ","+nameInput;
			  }

			   if(jQuery.inArray(nameInput, headerList) != -1){
				  if(selTextList.indexOf(nameInput) != -1) {
					tabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
					modTabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
				  } else {
					tabArry[tabarrindex] = valToSpn;
					modTabArry[tabarrindex] = valToSpn;
				  }
				  tabarrindex++;
			  }
			}

		});
	} catch (e) {
		console.log(e);
	}
	return hiddenInput+"@@"+hiddenNames;
}

var addBankInfo = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	 var tabArrText = "";
	 var data1 = "";
	try {
		data1 = commonData('#bankAccountInformation','ADD');
		rowindex = $("#multi-row-bank > span") .length ;
		$("#multi-row-bank").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+tabArry[0]+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
		$('#multi-row-bank > span#hidden_span_'+rowindex).append(data1.split("@@")[0]);

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
			tabArrText+="<td>"+tabArry[index].trim()+"</td> ";
		});

		appendTxt = "<tr class="+addclass+" align='center' id='tr-"+rowindex+"' index='"+rowindex+"'>"+
				"<td class='col_"+colindex+"'>"+colindex+"</td>"+ tabArrText+
				//"<td><a class='btn btn-min btn-info' href='#' id='editDat' index='"+rowindex+"'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel-bank' index='"+rowindex+"'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete-bank' index='"+rowindex+"'> <i class='icon-trash icon-white'></i></a></td></tr>";
				"<td><a class='btn btn-min btn-info' href='#' id='editDatBank' index='"+rowindex+"' title='Edit' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel-bank' index='"+rowindex+"' title='Reset' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete-bank' index='"+rowindex+"' title='Delete' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";
		rowindex = ++rowindex;

	} catch(e) {
		console.log(e);
	}
	return appendTxt;
};

var modBankAccVals = function (clickType) {
	 // add custom behaviour
		var tabArrText = "";
		var appendTxt = "";

		var data1 = "";
	try {
		modHeaderBodyArry = new Array();

		data1 = commonData('#bankAccountInformation','MODIFY');

		modHeaderBodyArry[0]=data1.split("@@")[1];
		modHeaderBodyArry[1]=data1.split("@@")[0];
	} catch(e)  {
		console.log(e);
	}
	//console.log(data1.split("@@")[1]+"##"+data1.split("@@")[0]);
	return "";
};

/** Form2 Add,Modify Ends*/

function alignSerialNo(serialId) {
	if($(serialId).length > 0) {
		$(serialId +' > td:first-child').each(function(index){
			$(this).text(index+1);
		});
	}
}



/** Form5 Add,Modify Starts*/
  function clearValsContactInfo(){
	$('#prmContactPerson').val('');
	$('#prmContactNumber').val('');
	$('#mobileNumber').val('');
}
  
var index1 = "";
var searchTdRow = "";
var searchTrRows = "";
var headerList1 = "prmContactPerson,prmContactNumber,mobileNumber".split(",");

function commonData1(id,type){
	var hiddenInput ="";
	var hiddenNames = "";
	var tabarrindex = 0;
	tabArry = new Array();
	modTabArry = new Array();
	try {
		//console.log(id);
		$(id).find('input[type=text],input[type=hidden],select').each(function(indxf){
			var nameInput = "";
			var valToSpn =  "";
			try{
				  nameInput = $(this).attr('name') ;
				  valToSpn = ($(this).attr('value') =='' ? ' ' :$(this).attr('value'));
			} catch(e1) {
				//console.log('The Exception Stack is :: '+ e1);
			}
			//console.log("Type ["+$(this).attr('type')+"]  Name ["+$(this).attr('name')+"] Values["+$(this).val()+"]");

			if(nameInput != undefined) {
			  if(indxf == 0)  {
				hiddenInput += valToSpn;
				hiddenNames += nameInput;
			  } else {
				hiddenInput += ","+valToSpn;
				hiddenNames += ","+nameInput;
			  }

			   if(jQuery.inArray(nameInput, headerList1) != -1){
				  if(selTextList.indexOf(nameInput) != -1) {
					tabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
					modTabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
				  } else {
					tabArry[tabarrindex] = valToSpn;
					modTabArry[tabarrindex] = valToSpn;
				  }
				  tabarrindex++;
			  }
			}

		});
	} catch (e) {
		console.log(e);
	}
	return hiddenInput+"@@"+hiddenNames;
}

var addBankInfo1 = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	 var tabArrText = "";
	 var data1 = "";
	try {
		data1 = commonData1('#bankContactInformation','ADD');
		rowindex = $("#multi-row-ContactInfo > span") .length ;
		$("#multi-row-ContactInfo").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+tabArry[0]+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
		$('#multi-row-ContactInfo > span#hidden_span_'+rowindex).append(data1.split("@@")[0]);

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
			tabArrText+="<td>"+tabArry[index].trim()+"</td> ";
		});

		appendTxt = "<tr class="+addclass+" align='center' id='tr-"+rowindex+"' index='"+rowindex+"'>"+
				"<td class='col_"+colindex+"'>"+colindex+"</td>"+ tabArrText+
				//"<td><a class='btn btn-min btn-info' href='#' id='editDat' index='"+rowindex+"'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel-bank' index='"+rowindex+"'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete-bank' index='"+rowindex+"'> <i class='icon-trash icon-white'></i></a></td></tr>";
				"<td><a class='btn btn-min btn-info' href='#' id='editDatBank' index='"+rowindex+"' title='Edit' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel-bank' index='"+rowindex+"' title='Reset' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete-bank' index='"+rowindex+"' title='Delete' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";
		rowindex = ++rowindex;

	} catch(e) {
		console.log(e);
	}
	return appendTxt;
};

var modBankAccVals = function (clickType) {
	 // add custom behaviour
		var tabArrText = "";
		var appendTxt = "";

		var data1 = "";
	try {
		modHeaderBodyArry = new Array();

		data1 = commonData('#bankContactInformation','MODIFY');

		modHeaderBodyArry[0]=data1.split("@@")[1];
		modHeaderBodyArry[1]=data1.split("@@")[0];
	} catch(e)  {
		console.log(e);
	}
	//console.log(data1.split("@@")[1]+"##"+data1.split("@@")[0]);
	return "";
};

/** Form5 Add,Modify Ends */
 
</script>

<script type="text/javascript">
function recordCheck(idtocheck,userIdInput){
	var check = false;
	var userIdRecords = new Array();

	try {
		if($(idtocheck).length > 0) {
			// To Check The Record Exists In The Add-Row
			$(idtocheck+' > td:nth-child(2)').each(function(indx){
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

function loadToolTip(){
	$('[rel="tooltip"],[data-rel="tooltip"]').tooltip({"placement":"bottom",delay: { show: 400, hide: 200 }});
}

$(function(){
	
	/** Form5 Add Row Validation Starts*/
	// Add Row Starts Here.
	
	$('#addConInfo').live('click', function(){
		 
		//$("#bankAcctMsg").text('');
 		//$("#error_dlno").text('');
		$("#form5").validate(form5Rules);
		
		 var textMess = "#tbody_data5 > tr";

		 //console.log("Inside bank Adding....");

		if($("#form5").valid() && $("#form5").manualValidation()) {
			// Is To Check Bank Number Exist's or not
			if(recordCheck(textMess,$('#bankRepresentativeNumber').val())) {
				var appendTxt = addBankInfo1("ADD");
				//console.log("appendTxt ===> "+ appendTxt);
				$("#tbody_data5").append(appendTxt);
				//clearVals();
				clearValsContactInfo();
				alignSerialNo(textMess);
				$("#bankAcctMsg").text('');
				$('#error_dlno').text('');
				loadToolTip();
			} else {
				$("#error_dlno").text('');
				$('#bankAcctMsg').text('Account Number Exists.');
			}

		} else {
			// This is to throw error nessage.
			$('#error_dlno').text('');
			//$('#error_dlno').text('The User With "'+$('#userId option:selected').val()+'" Exists.');
			return false;
		}
	});

	// Update Edited-Row Starts Here.
	$('#modCapBank').live('click', function () {
		$("#error_dlno").text('');
		$("#form5").validate(form5Rules);
		if($("#form5").valid()) {

 			var spanValues = modBankAccVals("MODIFY");

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
				}
			});

			$("#multi-row-bank span").each(function(index,value) {
 				 if($(this).attr("index") == index1 ) {
					 $(this).attr("hid-names",modHeaderBodyArry[0]);
					 $(this).text(modHeaderBodyArry[1]);
					 return;
				 }
			});

			clearValsContactInfo();
			alignSerialNo("#tbody_data1 > tr");

			//Hide Update Button and Display Add Button
			$('#modCapBank').hide();
			$('#addConInfo').show();

		} else {
			return false;
		}

	});

	// The below event is to Edit row on selecting the delete button
	$('#editDatBank').live('click',function() {
		$("#form5").validate().cancelSubmit = true;

		index1 = $(this).attr('index');
		var parentId =$(this).parent().closest('tbody').attr('id');
		searchTrRows = parentId+" tr";
		searchTdRow = '#'+searchTrRows+"#tr-"+index1 +' > td';

		var idSearch = "";
		var hidnames = "";
		var text_val = "";

		var spanData = $("#multi-row-bank > span#hidden_span_"+index1);
		hidnames =  $("#multi-row-bank > span#hidden_span_"+index1).attr('hid-names');
		text_val =  $("#multi-row-bank > span#hidden_span_"+index1).text();

		var hidarr=hidnames.split(",");
		var textarr=text_val.split(",");

		//console.log(hidarr);
		//console.log(textarr);
		//$('#acc-check').is(":checked");

		if(textarr[3] != " ") {
			$("#acc-check").prop("checked", false);
			$("#bankBranch").val("");
			$("#bankBranch").trigger("liszt:updated"); //bankBranch
		} else {
			$('#acc-check').prop("checked", true);
		}

		if( $(".checkclass").is(':checked') ) {
			// Non-NBK Account
			$('#bankinfospn').hide();
			$('#bankinfospn1').show();
		} else {
			//NBK Account
			$('#bankinfospn').show();
			$('#bankinfospn1').hide();
		}

		$(hidarr).each(function(index,value) {
			 if(jQuery.inArray( value, listid ) != -1){
				$('#'+value).find('option').each(function( i, opt ) {
					if( opt.value == textarr[index] ) {
						$(opt).attr('selected', 'selected');
						$('#'+value).trigger("liszt:updated");
					}
				});
			} else {
				$("#"+value).val(textarr[index] == undefined ? " " : textarr[index] .trim());
			}
		});

		//Hide Add Button and Display Update Button
		$('#modCapBank').show();
		$('#addConInfo').hide();

	});

	// The below event is to delete the entire row on selecting the delete button
	$("#delete-bank").live('click',function() {
		var delId = $(this).attr('index');
		$(this).parent().parent().remove();

		var spanId = "";
		$('#multi-row-bank > span').each(function(index){
			spanId =  $(this).attr('index');
			if(spanId == delId) {
				$(this).remove();
			}
		});

		clearValsContactInfo();
		// Aligning the serial number
		alignSerialNo("#tbody_data5 > tr");
		$('.tooltip').remove();
		//Show Add Button and Hide Update Button
		$('#modCapBank').hide();
		$('#addConInfo').show();
	});

	// Clear Form Records Row Starts Here.
	$('#row-cancel-bank').live('click', function () {
		$("#error_dlno").text('');
		 clearVals();

		//Show Add Button and Hide Update Button
		$('#modCapBank').hide();
		$('#addConInfo').show();
	});

});

/** Form5 Add Row Validation Ends*/
 

/** Form2 Add Row Validation Starts*/
	// Add Row Starts Here.
	$('#addCapBank').live('click', function(){
		$("#bankAcctMsg").text('');
 		$("#error_dlno").text('');
 		
 		 
		$("#form2").validate(form2Rules);
		 var textMess = "#tbody_data1 > tr";

		 //console.log("Inside bank Adding....");

		if($("#form2").valid() && $("#form2").manualValidation()) {
			// Is To Check Account Exist's or not
			if(recordCheck(textMess,$('#accountNumber').val())) {
				var appendTxt = addBankInfo("ADD");
				console.log("appendTxt ===> "+ appendTxt);
				$("#tbody_data1").append(appendTxt);
				clearVals();
				alignSerialNo(textMess);
				$("#bankAcctMsg").text('');
				$('#error_dlno').text('');
				loadToolTip();
			} else {
				$("#error_dlno").text('');
				$('#bankAcctMsg').text('Account Number Exists.');
			}

		} else {
			// This is to throw error nessage.
			$('#error_dlno').text('');
			//$('#error_dlno').text('The User With "'+$('#userId option:selected').val()+'" Exists.');
			return false;
		}

	});

	// Update Edited-Row Starts Here.
	$('#modCapBank').live('click', function () {
		$("#error_dlno").text('');
		$("#form2").validate(form2Rules);
		if($("#form2").valid()) {

 			var spanValues = modBankAccVals("MODIFY");

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
				}
			});

			$("#multi-row-bank span").each(function(index,value) {
 				 if($(this).attr("index") == index1 ) {
					 $(this).attr("hid-names",modHeaderBodyArry[0]);
					 $(this).text(modHeaderBodyArry[1]);
					 return;
				 }
			});

			clearVals();
			alignSerialNo("#tbody_data1 > tr");

			//Hide Update Button and Display Add Button
			$('#modCapBank').hide();
			$('#addCapBank').show();

		} else {
			return false;
		}

	});

	// The below event is to Edit row on selecting the delete button
	$('#editDatBank').live('click',function() {
		$("#form2").validate().cancelSubmit = true;

		index1 = $(this).attr('index');
		var parentId =$(this).parent().closest('tbody').attr('id');
		searchTrRows = parentId+" tr";
		searchTdRow = '#'+searchTrRows+"#tr-"+index1 +' > td';

		var idSearch = "";
		var hidnames = "";
		var text_val = "";

		var spanData = $("#multi-row-bank > span#hidden_span_"+index1);
		hidnames =  $("#multi-row-bank > span#hidden_span_"+index1).attr('hid-names');
		text_val =  $("#multi-row-bank > span#hidden_span_"+index1).text();

		var hidarr=hidnames.split(",");
		var textarr=text_val.split(",");

		//console.log(hidarr);
		//console.log(textarr);
		//$('#acc-check').is(":checked");

		if(textarr[3] != " ") {
			$("#acc-check").prop("checked", false);
			$("#bankBranch").val("");
			$("#bankBranch").trigger("liszt:updated"); //bankBranch
		} else {
			$('#acc-check').prop("checked", true);
		}

		if( $(".checkclass").is(':checked') ) {
			// Non-NBK Account
			$('#bankinfospn').hide();
			$('#bankinfospn1').show();
		} else {
			//NBK Account
			$('#bankinfospn').show();
			$('#bankinfospn1').hide();
		}

		$(hidarr).each(function(index,value) {
			 if(jQuery.inArray( value, listid ) != -1){
				$('#'+value).find('option').each(function( i, opt ) {
					if( opt.value == textarr[index] ) {
						$(opt).attr('selected', 'selected');
						$('#'+value).trigger("liszt:updated");
					}
				});
			} else {
				$("#"+value).val(textarr[index] == undefined ? " " : textarr[index] .trim());
			}
		});

		//Hide Add Button and Display Update Button
		$('#modCapBank').show();
		$('#addCapBank').hide();

	});

	// The below event is to delete the entire row on selecting the delete button
	$("#delete-bank").live('click',function() {
		var delId = $(this).attr('index');
		$(this).parent().parent().remove();

		var spanId = "";
		$('#multi-row-bank > span').each(function(index){
			spanId =  $(this).attr('index');
			if(spanId == delId) {
				$(this).remove();
			}
		});

		clearVals();
		// Aligning the serial number
		alignSerialNo("#tbody_data1 > tr");
		$('.tooltip').remove();
		//Show Add Button and Hide Update Button
		$('#modCapBank').hide();
		$('#addCapBank').show();
	});

	// Clear Form Records Row Starts Here.
	$('#row-cancel-bank').live('click', function () {
		$("#error_dlno").text('');
		 clearVals();

		//Show Add Button and Hide Update Button
		$('#modCapBank').hide();
		$('#addCapBank').show();
	});

 
/** Form2 Add Row Validation Ends*/

function createMerchant(){

	$("#form1").validate(merchantCreateRules);
	encryptPassword();
	
	var finalData5 ="";
	var finalData1 = "";
	//var finalData = "";
	//var finalData2 = "";
	
	$('#multi-row-ContactInfo > span').each(function(index) {
	//console.log("index ["+$(this).attr('index')+"] name ["+$(this).attr('name')+"]  value["+$(this).val()+"] ");
	if(index == 0)  finalData5 = $(this).text();
	else finalData5 += "#"+ $(this).text();
	});
	$('#contactInfoMultidata').val(finalData5);
 

	$('#multi-row-agent > span').each(function(index) {
		//console.log("index ["+$(this).attr('index')+"] name ["+$(this).attr('name')+"]  value["+$(this).val()+"] ");
	if(index == 0)  finalData1 = $(this).text();
	else finalData1 += "#"+ $(this).text();
	});
   $('#agentMultiData').val(finalData1);


/* 	$('#multi-row-bank > span').each(function(index) {
			//console.log("index ["+$(this).attr('index')+"] name ["+$(this).attr('name')+"]  value["+$(this).val()+"] ");
		if(index == 0)  finalData = $(this).text();
		else finalData += "#"+ $(this).text();
	});
	$('#bankAcctMultiData').val(finalData); */
	

 /* 	$('#multi-row-doc > span').each(function(index) {
				//console.log("index ["+$(this).attr('index')+"] name ["+$(this).attr('name')+"]  value["+$(this).val()+"] ");
		if(index == 0)  finalData2 = $(this).text();
		else finalData2 += "#"+ $(this).text();
	});

	$('#documentMultiData').val(finalData2); */

	if($("#form1").valid() && $("#form1").manualValidation()){
		
			$("#bankAcctMsg").text('');
			var data=$( "#location option:selected" ).text();
			$("#locationVal").val(data);
			data=$( "#merchantType option:selected" ).text();
			$("#merchantTypeVal").val(data);
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/getMerchantCreateConfirmScreenAct.action';
			$("#form1").submit();
			return true;
		 
	 } else {
		return false;
	}
}


function getLocation(){
	var data=$( "#location option:selected" ).val();
	data = data.split('-');
	console.log(data[0]);
	$("#locationVal").val(data[0]);

}
function getMerhchantType(){
	var data=$( "#merchantType option:selected" ).text();
	$("#merchantTypeVal").val(data);
}


/*   function getMerchantID(){
 	var formInput='merchantName='+$('#merchantName').val();
    $.getJSON('generateMerchantIdAct.action', formInput,function(data) {
		var merchantId = data.responseJSON.merchantID;
		var storeId=data.responseJSON.storeId;
		$('#merchantID').val(merchantId);
		$('#storeId').val(storeId);
    });
   return false;
}  */   

     

</script>

<script type="text/javascript">
function clearValsAgent(){
	$('#bankAgenctNo').val('');
	$('#MPeasAgenctNo').val('');
	$('#airtelMoneyAgenetNo').val('');
	$('#orangeMoneyAgentNo').val('');
	$('#mpesaTillNumber').val('');
}
var index2 = "";
var selTextListAgent = "";
var headerListAgent = "bankAgenctNo,MPeasAgenctNo,airtelMoneyAgenetNo,orangeMoneyAgentNo,mpesaTillNumber".split(",");
var listidagent = "".split(",");

function commonDataAgent(id,type){
	var hiddenInput ="";
	var hiddenNames = "";
	var tabarrindex = 0;
	tabArry = new Array();
	modTabArry = new Array();

	try {
		//console.log("commonDataAgent  :::" + id);
		$(id).find('input[type=text],input[type=hidden],select').each(function(indxf){
			var nameInput = "";
			var valToSpn =  "";
			try{
				  nameInput = $(this).attr('name') ;
				  valToSpn = ($(this).attr('value') =='' ? ' ' :$(this).attr('value'));
			} catch(e1) {
				//console.log('The Exception Stack is :: '+ e1);
			}

			if(nameInput != undefined) {
			  if(indxf == 0)  {
				hiddenInput += valToSpn;
				hiddenNames += nameInput;
			  } else {
				hiddenInput += ","+valToSpn;
				hiddenNames += ","+nameInput;
			  }

			   if(jQuery.inArray(nameInput, headerListAgent) != -1){
				  if(selTextListAgent.indexOf(nameInput) != -1) {
					tabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
					modTabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
				  } else {
					tabArry[tabarrindex] = valToSpn;
					modTabArry[tabarrindex] = valToSpn;
				  }
				  tabarrindex++;
			  }
			}

		});
	} catch (e) {
		console.log(e);
	}
	return hiddenInput+"@@"+hiddenNames;
}
/** Form3 Add Row Validation Agent Starts */
var addAgentInfo = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	 var tabArrText = "";
	 var data1 = "";
	try {
		//console.log("Before commonDataAgent  :::");
		data1 = commonDataAgent('#agentBasedInfo','ADD');
		rowindex = $("#multi-row-agent > span") .length ;

		$("#multi-row-agent").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+tabArry[0]+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
		$('#multi-row-agent > span#hidden_span_'+rowindex).append(data1.split("@@")[0]);

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
			tabArrText+="<td>"+tabArry[index].trim()+"</td> ";
		});

		appendTxt = "<tr class="+addclass+" align='center' id='tr-"+rowindex+"' index='"+rowindex+"'>"+
				"<td class='col_"+colindex+"'>"+colindex+"</td>"+ tabArrText+
 				"<td><a class='btn btn-min btn-info' href='#' id='editDatAgent' index='"+rowindex+"' title='Edit' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel-agent' index='"+rowindex+"' title='Reset' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete-agent' index='"+rowindex+"' title='Delete' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";


	} catch(e) {
		console.log(e);
	}

	return appendTxt;
};

var modAgentAccVals = function (clickType) {
	 // add custom behaviour
	var tabArrText = "";
	var appendTxt = "";

	var data1 = "";
	try {
		modHeaderBodyArry = new Array();

		data1 = commonDataAgent('#agentBasedInfo','MODIFY');

		modHeaderBodyArry[0]=data1.split("@@")[1];
		modHeaderBodyArry[1]=data1.split("@@")[0];
	} catch(e)  {
		console.log(e);
	}
	//console.log(data1.split("@@")[1]+"##"+data1.split("@@")[0]);
	return "";
};

function recordAgentCheck(idtocheck,userIdInput) {
	var check = false;
	var userIdRecords = new Array();
	try {
		if($(idtocheck).length > 0) {
			// To Check The Record Exists In The Add-Row
			$(idtocheck+' > td:nth-child(2)').each(function(indx){
 				userIdRecords[indx]=$(this).text().trim();
			});

			if(jQuery.inArray(userIdInput.trim(), userIdRecords) == -1){
				check = true;
			}

		} else {
			check = true;
		}
	} catch(e) {
		console.log(e);
	}
	//console.log('Duplicate Record Check.. '+ check);
	return check;
}

/** Form3 Add,Modify Ends*/
 
$(function(){
	// Add Row Starts Here.
	
	$('#addCapAgent').live('click', function(){
		
		$("#bankAgenctNo").text('');
 		$("#error_dlno").text('');
 		
 		if($('#accountNumber').val() == null || $('#accountNumber').val() == ' ' || $('#accountNumber').val() ==''){
 			
 			// $("#form3").validate(form3Rules); 
  
 			$("#agentAcctNo").text('please Enter M visa Number');
 			return false;
 			
 		 }else{
 			 
 			 $("#agentAcctNo").text('');
 			 
 		 }
 		
		
		var textMess = "#tbody_data3 > tr";


		if($("#form3").valid()) {
			// Is To Check Account Exist's or not
			
			if(recordCheck(textMess,$('#bankAgenctNo').val())) {
				
				var appendTxt = addAgentInfo("ADD");
				//console.log("appendTxt ===> "+ appendTxt);
				$("#tbody_data3").append(appendTxt);
				clearValsAgent();
				alignSerialNo(textMess);
				$("#agentAcctMsg").text('');
				$('#error_dlno').text('');
				loadToolTip();
				$('#bankAgenctNo').val((Math.random() * 100000).toString().substring(0,7).replace(".",""));
			} else {
				$("#error_dlno").text('');
				$('#agentAcctMsg').text('Account Number Exists.');
			}
		}/* else {
			// This is to throw error nessage.
			$('#error_dlno').text('');
			//$('#error_dlno').text('The User With "'+$('#userId option:selected').val()+'" Exists.');
			return false;
		} */

	});

	// Update Edited-Row Starts Here.
	$('#modCapAgent').live('click', function () {
		$("#error_dlno").text('');
		 $("#form3").validate(form3Rules);
		//if($("#form3").valid()) {

 			var spanValues = modAgentAccVals("MODIFY");

 			searchTdRow = '#'+searchTrRows+"#tr-"+index2 +' > td';
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
					$(this).text(modTabArry[3]);
				}
			});

			$("#multi-row-agent span").each(function(index,value) {
 				 if($(this).attr("index") == index2 ) {
					 $(this).attr("hid-names",modHeaderBodyArry[0]);
					 $(this).text(modHeaderBodyArry[1]);
					 return;
				 }
			});

			clearValsAgent();
			alignSerialNo("#tbody_data3 > tr");

			//Hide Update Button and Display Add Button
			$('#modCapAgent').hide();
			$('#addCapAgent').show();
			$('#bankAgenctNo').val((Math.random() * 100000).toString().substring(0,7).replace(".",""));
 		/*} else {
			return false;
		} */
	});

	// The below event is to Edit row on selecting the delete button
	$('#editDatAgent').live('click',function() {
		$("#form3").validate().cancelSubmit = true;

		index2 = $(this).attr('index');
		var parentId =$(this).parent().closest('tbody').attr('id');
		searchTrRows = parentId+" tr";
		searchTdRow = '#'+searchTrRows+"#tr-"+index2 +' > td';

		var idSearch = "";
		var hidnames = "";
		var text_val = "";

		var spanData = $("#multi-row-agent > span#hidden_span_"+index2);
		hidnames =  $("#multi-row-agent > span#hidden_span_"+index2).attr('hid-names');
		text_val =  $("#multi-row-agent > span#hidden_span_"+index2).text();

		var hidarr=hidnames.split(",");
		var textarr=text_val.split(",");

		//console.log(hidarr);
		//console.log(textarr);

		$(hidarr).each(function(index,value) {
			 if(jQuery.inArray( value, listidagent ) != -1){
				$('#'+value).find('option').each(function( i, opt ) {
					if( opt.value == textarr[index] ) {
						$(opt).attr('selected', 'selected');
						$('#'+value).trigger("liszt:updated");
					}
				});
			} else {
				$("#"+value).val(textarr[index] == undefined ? " " : textarr[index].trim());
			}
		});

		//Hide Add Button and Display Update Button
		$('#modCapAgent').show();
		$('#addCapAgent').hide();

	});

	// The below event is to delete the entire row on selecting the delete button
	$("#delete-agent").live('click',function() {
		var delId = $(this).attr('index');
		$(this).parent().parent().remove();

		var spanId = "";
		$('#multi-row-agent > span').each(function(index){
			spanId =  $(this).attr('index');
			if(spanId == delId) {
				$(this).remove();
			}
		});

		clearValsAgent();

		//if($('#tbody_data3 > tr').length == 0) {
			// Aligning the serial number
			alignSerialNo("#tbody_data3 > tr");
			/* if($('#bankAgenctNo').val() == "") {
				$('#bankAgenctNo').val((Math.random() * 100000).toString().substring(0,7).replace(".",""));
			} */
		//}

		//Show Add Button and Hide Update Button
		$('.tooltip').remove();
		$('#modCapAgent').hide();
		$('#addCapAgent').show();
	});

	// Clear Form Records Row Starts Here.
	$('#row-cancel-agent').live('click', function () {
		$("#error_dlno").text('');
		 clearValsAgent();

		//Show Add Button and Hide Update Button
		$('#modCapAgent').hide();
		$('#addCapAgent').show();
	});

});


/** Form3 Add Row Validation Agent Ends*/
 </script>

<script type="text/javascript">
function clearValsDocs(){
	$('#documentDescription').val('');
	$('#gracePeriod').val('');

	$(listiddoc).each(function(index,val){
		$('#'+val).find('option').each(function(i, opt ) {
			if( opt.value == '' ) {
				$(opt).attr('selected', 'selected');
				$('#'+val).trigger("liszt:updated"); //bankBranch
			}
		});
	});

}
var index3 = "";
var selTextListDocs = "documentId,mandatory";
var headerListDocs = "documentId,documentDescription,gracePeriod,mandatory".split(",");
var listiddoc = "documentId,mandatory".split(",");
function commonDataDocs(id,type){
	var hiddenInput ="";
	var hiddenNames = "";
	var tabarrindex = 0;
	tabArry = new Array();
	modTabArry = new Array();

	try {
		//console.log(id);
		$(id).find('input[type=text],input[type=hidden],select').each(function(indxf){
			var nameInput = "";
			var valToSpn =  "";
			try{
				  nameInput = $(this).attr('name') ;
				  valToSpn = ($(this).attr('value') =='' ? ' ' :$(this).attr('value'));
			} catch(e1) {
				//console.log('The Exception Stack is :: '+ e1);
			}
			//console.log("Type ["+$(this).attr('type')+"]  Name ["+$(this).attr('name')+"] Values["+$(this).val()+"]");

			if(nameInput != undefined) {
			  if(indxf == 0)  {
				hiddenInput += valToSpn;
				hiddenNames += nameInput;
			  } else {
				hiddenInput += ","+valToSpn;
				hiddenNames += ","+nameInput;
			  }

			  //console.log(nameInput+"===" +headerListDocs);
 			  //console.log(jQuery.inArray(nameInput, headerListDocs));
			   if(jQuery.inArray(nameInput, headerListDocs) != -1){
				  if(selTextListDocs.indexOf(nameInput) != -1) {
					tabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
					modTabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
				  } else {
					tabArry[tabarrindex] = valToSpn;
					modTabArry[tabarrindex] = valToSpn;
				  }
				  tabarrindex++;
			  }
			}

		});
	} catch (e) {
		console.log(e);
	}
	return hiddenInput+"@@"+hiddenNames;
}
/** Form3 Add Row Validation Docs Starts */
var addDocsInfo = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	 var tabArrText = "";
	 var data1 = "";
	try {
		data1 = commonDataDocs('#documentInformation','ADD');

		rowindex = $("#multi-row-doc > span") .length ;

		$("#multi-row-doc").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+tabArry[0]+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
		$('#multi-row-doc > span#hidden_span_'+rowindex).append(data1.split("@@")[0]);

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
			tabArrText+="<td>"+tabArry[index].trim()+"</td> ";
		});

		appendTxt = "<tr class="+addclass+" align='center' id='tr-"+rowindex+"' index='"+rowindex+"'>"+
				"<td class='col_"+colindex+"'>"+colindex+"</td>"+ tabArrText+
 				"<td><a class='btn btn-min btn-info' href='#' id='editDatDocs' index='"+rowindex+"' title='Edit' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel-docs' index='"+rowindex+"' title='Reset' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete-docs' index='"+rowindex+"' title='Delete' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";

	} catch(e) {
		console.log(e);
	}

	return appendTxt;
};

var modDocsAccVals = function (clickType) {
	 // add custom behaviour
		var tabArrText = "";
		var appendTxt = "";

		var data1 = "";
	try {
		modHeaderBodyArry = new Array();

		data1 = commonDataDocs('#documentInformation','MODIFY');

		modHeaderBodyArry[0]=data1.split("@@")[1];
		modHeaderBodyArry[1]=data1.split("@@")[0];
	} catch(e)  {
		console.log(e);
	}
	//console.log(data1.split("@@")[1]+"##"+data1.split("@@")[0]);
	return "";
};
/** Form3 Add,Modify Ends*/


/** Form4 Add,Modify Starts*/
$(function(){
	// Add Row Starts Here.
	$('#addCapDocs').live('click', function(){
		$("#bankAcctMsg").text('');
 		$("#error_dlno").text('');
		$("#form4").validate(form4Rules);
		 var textMess = "#tbody_data2 > tr";

		if($("#form4").valid()) {
			// Is To Check Account Exist's or not
			if(recordCheck(textMess,$('#documentId option:selected').text())) {
				var appendTxt = addDocsInfo("ADD");
				//console.log("appendTxt ===> "+ appendTxt);
				$("#tbody_data2").append(appendTxt);
				clearValsDocs();
				alignSerialNo(textMess);
				$("#docsAcctMsg").text('');
				$('#error_dlno').text('');
				loadToolTip();
			} else {
				$("#error_dlno").text('');
				$('#docsAcctMsg').text('Document Id Exists.');
			}
		} else {
			// This is to throw error nessage.
			$('#error_dlno').text('');
 			return false;
		}

	});

	// Update Edited-Row Starts Here.
	$('#modCapDocs').live('click', function () {
		$("#error_dlno").text('');
		$("#form4").validate(form4Rules);
		if($("#form4").valid()) {

 			var spanValues = modDocsAccVals("MODIFY");

 			searchTdRow = '#'+searchTrRows+"#tr-"+index3 +' > td';
 			$(searchTdRow).each(function(index,value) {
 				if(index == 1){
					$(this).text(modTabArry[0]);
				}if(index == 2){
					$(this).text(modTabArry[1]);
				}if(index == 3){
					$(this).text(modTabArry[2]);
				}if(index == 4){
					$(this).text(modTabArry[3]);
				}
			});

			$("#multi-row-doc span").each(function(index,value) {
 				 if($(this).attr("index") == index3 ) {
					 $(this).attr("hid-names",modHeaderBodyArry[0]);
					 $(this).text(modHeaderBodyArry[1]);
					 return;
				 }
			});

			clearValsDocs();
			alignSerialNo("#tbody_data2 > tr");

			//Hide Update Button and Display Add Button
			$('#modCapDocs').hide();
			$('#addCapDocs').show();

		} else {
			return false;
		}

	});

	// The below event is to Edit row on selecting the delete button
	$('#editDatDocs').live('click',function() {
		$("#form1").validate().cancelSubmit = true;
		$("#form2").validate().cancelSubmit = true;
		$("#form3").validate().cancelSubmit = true;

		index3 = $(this).attr('index');
		var parentId =$(this).parent().closest('tbody').attr('id');
		searchTrRows = parentId+" tr";
		searchTdRow = '#'+searchTrRows+"#tr-"+index3 +' > td';

		var idSearch = "";
		var hidnames = "";
		var text_val = "";

		var spanData = $("#multi-row-doc > span#hidden_span_"+index3);
		hidnames =  $("#multi-row-doc > span#hidden_span_"+index3).attr('hid-names');
		text_val =  $("#multi-row-doc > span#hidden_span_"+index3).text();

		var hidarr=hidnames.split(",");
		var textarr=text_val.split(",");

		$(hidarr).each(function(index,value) {
			 if(jQuery.inArray( value, listiddoc ) != -1){
				$('#'+value).find('option').each(function( i, opt ) {
					if( opt.value == textarr[index] ) {
						$(opt).attr('selected', 'selected');
						$('#'+value).trigger("liszt:updated");
					}
				});
			} else {
				//$("#"+value).val(textarr[index].trim());
				$("#"+value).val(textarr[index] == undefined ? " " : textarr[index] .trim());
			}
		});

		//Hide Add Button and Display Update Button
		$('#modCapDocs').show();
		$('#addCapDocs').hide();

	});

	// The below event is to delete the entire row on selecting the delete button
	$("#delete-docs").live('click',function() {
		var delId = $(this).attr('index');
		$(this).parent().parent().remove();

		var spanId = "";
		$('#multi-row-doc > span').each(function(index){
			spanId =  $(this).attr('index');
			if(spanId == delId) {
				$(this).remove();
			}
		});

		clearValsDocs();
		// Aligning the serial number
		alignSerialNo("#tbody_data2 > tr");

		//Show Add Button and Hide Update Button
		$('#modCapDocs').hide();
		$('#addCapDocs').show();
	});

	// Clear Form Records Row Starts Here.
	$('#row-cancel-docs').live('click', function () {
		$("#error_dlno").text('');
		 clearValsDocs();

		//Show Add Button and Hide Update Button
		$('#modCapDocs').hide();
		$('#addCapDocs').show();
	});
});
/** Form4 Add Row Validation Docs Ends*/
 </script>

<script type="text/javascript">
$(function(){

	$("#telephoneNumber1,#telephoneNumber2,#mobileNumber,#faxNumber,#prmContactNumber,#postalCode,#accountNumber").keypress(function (e) {
	 //if the letter is not digit then display error and don't type anything
	 if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		//display error message
		$("#"+$(this).attr('id')+"_err").html("Digits Only").show().fadeOut("slow");
			   return false;
		}
   });



	$('#kraPin').live('keyup',function(){
		 var id = $(this).attr('id');
		 var v_val = $(this).val();
		 $("#"+id).val(v_val.toUpperCase());
	});

	$('.checkclass').click(function(){
		//console.log("IS checked.... " + $(this).is(':checked'));
		if( $(this).is(':checked') ) {
			// Non-NBK Account
			$('#bankinfospn').hide();
			$('#bankinfospn1').show();
			$("#bankBranch").val("");
			$("#bankBranch").trigger("liszt:updated"); //bankBranch
		} else {
			//NBK Account
			$('#bankinfospn').show();
			$('#bankinfospn1').hide();
			$('#bankName').val('');
		}
	});
	$('#bankinfospn1').hide();
});

function getInfo(){
 	var merchantAdminId=$("#merchantAdminId option:selected" ).val();
  	var formInput='merchantAdminId='+merchantAdminId+'&method=getMerchantAdminDetails';
 	   $.getJSON('getMerchantAdminInfo.action', formInput,function(data) {
			$("#userData1").show();
			$("#userData2").show();
			$("#userName").text(data.responseJSON.username);
			$("#userStatus").text(data.responseJSON.userStatus);
			$("#empNo").text(data.responseJSON.EMPLOYEE_NO);
			$("#userEmailId").text(data.responseJSON.emailAddress);
 		});
 }
 
$(function(){
	
	$("#accountNumber_err").text('');
	
	$('#prmContactNumber').keyup(function(){
		
		var prmContactNumber=$('#prmContactNumber').val();
		var mobileNumber=$('#mobileNumber').val();
		if(prmContactNumber.length == 10){
			
			if(parseInt(prmContactNumber)==parseInt(mobileNumber))
				{
				$("#prmContactNumber_err").text("Pri contact Number should not be same as manager Phone number.");
				}
		}
	});
	
	
  	 $("#merchantName").focusout(function(e){   
	        
			 var formInput='merchantName='+$('#merchantName').val();
			 var ses= $('#merchantName').val().length;
			 
			if(parseInt(ses)<= 3){
				 $('#error_dlno5').text("Enter Minimum Of 4 Charecters");
				 $('#merchantID').val('');
				 $('#storeId').val('');
				 return false;
			 }else{
					 
				$('#error_dlno5').text(''); 
			    $.getJSON('generateMerchantIdAct.action', formInput,function(data) {
				 
				var merchantId = data.responseJSON.merchantID;
				
				var storeId=data.responseJSON.storeId;
				$('#merchantID').val(merchantId);
				$('#storeId').val(storeId);
            });
			 //return false;
			} 
	       
       });  
  	 
  	// geting Account Number
  	
  
  	
  	 $("#accountNumber").change(function(e){ 
  		$("#accountNumber_err").text('');
		    var formInput='accountNumber='+$('#accountNumber').val();
			 
		      if($('#accountNumber').val().length != 14 ){
		    	 
		    	$("#accountNumber_err").text("Account Nummber Contains 14 Digits");
		    	$('#bankAgenctNo').val('');
		    	return false;
		     }else{
		    	   $('#error_dlno5').text(''); 
				    
				    $.getJSON('generateVisaNumber.action', formInput,function(data) {
					 
					var cardNo = data.responseJSON.cardNumber;
					$('#bankAgenctNo').val(cardNo);
		       
		           });  
		     }
		   
  	 });
  	
 /*  	$("#latitude").focusout(function(event){
 		alert("entering charecters");
 	    if (event.keyCode < 48 || event.keyCode > 57){
 	    	$("#error_dlno6").text("Please Enter Digits Only");
 	        return false;
 	    }
 	    	
 	}); */
  	
	/*  $('#latitude').keypress(function (e) {
	   	  $('#card_err1').empty();
	         if (e.which != 8 && e.which != 0 && e.which == 187 && e.which == 189 && (e.which < 48 || e.which > 57)) {
	        $("#"+$(this).attr('id')+"_err").html("Digits Only").show().fadeOut("slow");
	         return false;
	       }
     }); */
    
    		 
  	 $("#latitude").focusout(function(e){ 
  		 
  		 $("#error_dlno6").text('');
     	 var latitude1 = $("#latitude").val();
     	 var reg = new RegExp(/[-+]?(?:90(?:\\.0+)?|[1-8]?[0-9](?:\\.[0-9]+)?)/);
     	
     	  if( reg.exec(latitude1) ) {
     		 //do nothing
     		} else {
     		 //error
     		$("#error_dlno6").text("Please Enter Digits Only");
     		return false;
     		}
  	 });
     
  	 $("#longitude").focusout(function(e){ 
  		 
  		 $("#error_dlno7").text('');
     	 var longitude2 = $("#longitude").val();
     	 var reg = new RegExp(/[-+]?(?:90(?:\\.0+)?|[1-8]?[0-9](?:\\.[0-9]+)?)/);
     	
     	  if( reg.exec(longitude2) ) {
     		 //do nothing
     		} else {
     		 //error
     		$("#error_dlno7").text("Please Enter Digits Only");
     		return false;
     		}
  	 });
	 
   });
   
function changemvisa(value){
	//alert(value);
	
	
	 	
	  	var formInput='dlName='+value+'&method=generatevisaid';
	 	   $.getJSON('postJson.action', formInput,function(data) {
				/* $("#userData1").show();
				$("#userData2").show();
				$("#userName").text(data.responseJSON.username);
				
				$("#userStatus").text(data.responseJSON.userStatus);
				$("#empNo").text(data.responseJSON.EMPLOYEE_NO);
				$("#userEmailId").text(data.responseJSON.emailAddress); */
	 		  //alert(data.mvisaid);
				
				$('#mrcode').val(data.mvisaid);
	 		});
	 
}
   
//For Closing Select Box Error Message_Start
$(document).on('change','select',function(event) {  
	if($('#'+$(this).attr('id')).next('label').text().length > 0) {
	 $('#'+$(this).attr('id')).next('label').text(''); 
	 $('#'+$(this).attr('id')).next('label').remove();
	}
});

  function randomString1() {
	   var charSet = '0123456789';
	    var randomString2 = '';
	    for (var i = 0; i < 5; i++) {
	    	var randomPoz = Math.floor(Math.random() * charSet.length);
	    	randomString2 += charSet.substring(randomPoz,randomPoz+1);
	    }
	    return randomString2;
	}
	 
  function randomString() {
	   var charSet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
	    var randomString = '';
	    for (var i = 0; i < 5; i++) {
	    	var randomPoz = Math.floor(Math.random() * charSet.length);
	    	randomString += charSet.substring(randomPoz,randomPoz+1);
	    }
	    return randomString;
	}

	function encryptPassword(){
		var encryptPass;
		
		var otpData = randomString1();
		$("#otp").val(otpData);
		var password=randomString();
		$("#password").val(password);
		encryptPass = b64_sha256(password);
		$("#encryptPassword").val(encryptPass);
	}
	
	
</script>
</head>

<body>
		<div id="content" class="span10">
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Add New Merchant</a></li>
					</ul>
				</div>
		<form name="form1" id="form1" method="post" action="">
				<div class="row-fluid sortable"><!--/span-->

					<div class="box span12">
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Merchant Primary Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"
									class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="Merchant Name"><strong>Merchant Name<font color="red">*</font></strong></label></td>
									<td width="30%"><input name="merchantName" type="text"  id="merchantName" class="field"
														value="<s:property value='#respData.merchantName' />"
														maxlength="50" required='true'  >
														<span id ="error_dlno5" class="errors"></td>
									<td width="20%"><label for="Merchant ID"><strong>Merchant ID<font color="red">*</font></strong></label></td>
									<td width="30%"><input name="merchantID" type="text" id="merchantID" class="field" value="<s:property value='#respData.merchantID' />" maxlength="14" readonly ></td>
								</tr>
								<tr class="odd">
									<td><label for="Store Name"><strong>Primary Store Name<font color="red">*</font></strong></label></td>
									<td><input name="storeName" type="text" class="field" id="storeName" value="<s:property value='#respData.storeName' />" required='true' /></td>
									<td ><label for="Store ID"><strong>Primary Store ID<font color="red">*</font></strong></label></td>
									<td ><input name="storeId"  type="text" id="storeId" class="field"  value="<s:property value='#respData.storeId' />" readonly> </td>

								</tr>
								<tr class="even">
									<td ><label for="Bank Branch"><strong>Bank Branch<font color="red">*</font></strong></label></td>
									<td >
										
										 <s:select cssClass="chosen-select"
											headerKey=""
											headerValue="Select"
											list="#respData.LOCATION_LIST"
											name="location"
											value="#respData.location"
											id="location" requiredLabel="true"
											theme="simple"
											data-placeholder="Choose Location..."
											onchange="getLocation()"
											 />
										<input type="hidden" name="locationVal" id="locationVal" value="<s:property value='#respData.location' />" />
									</td>
									<td ><label for="KRA PIN"><strong>KRA PIN<font color="red">*</font></strong></label></td>
									<td ><input name="kraPin" type="text" class="field" id="kraPin" value="<s:property value='#respData.kraPin' />" required='true' maxlength="11"/></td>
								</tr>
								<tr class="odd">
									<td ><label for="Merchant Type"><strong>Merchant Type<font color="red">*</font></label></td>
									<td >
										
										 <s:select cssClass="chosen-select"
											headerKey=""
											headerValue="Select"
											list="#respData.MERCHANT_TYPE"
											name="merchantType"
											value="#respData.merchantType"
											id="merchantType" requiredLabel="true"
											theme="simple"
											data-placeholder="Choose Merchant Type..."
											onchange="getMerhchantType()"
											 />
										<input type="hidden" name="merchantTypeVal" id="merchantTypeVal" value="<s:property value="#respData.merchantType"/>" />
										<input type="hidden" name="genmvisaid" id="genmvisaid"  />
									</td>
									<td></td>
									<td></td>
									
								</tr>
								<tr class="even">
										<td ><label for="BIN"><strong>BIN<font color="red">*</font></strong></label></td>
												<td >  <s:select cssClass="chosen-select"
															headerKey=""
															headerValue="Select"
															list="#respData.binCode"
															name="bincode"
															value=""
															id="bincode" requiredLabel="true"
															theme="simple"
															data-placeholder="Choose BIN ..."
															onchange="changemvisa(this.value)"
													   />
													 </td>
													 
													 
											<td width="20%"><label for="M Visa Number"><strong>mVisa Number</strong></label></td>
											<td width="30%"><input name="mrcode" id="mrcode" class="field" type="text"  readonly="readonly"   value="<s:property value="#responseJSON.AGEN_BILL_RAND" />"  />
											<span id="agentAcctNo" class="errors"></span></td>
											
										</tr>
								
							</table>
						</fieldset>
						</div>
					</div>
				</div>
				
				
				<div class="row-fluid sortable"><!--/span-->
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
										
											<tr class="odd">
												<td ><label for="Address Line 1"><strong>Address Line 1<font color="red">*</font></strong></label></td>
												<td ><input name="addressLine1" id="addressLine1" class="field" type="text"  maxlength="50" value="<s:property value="#respData.addressLine1"/>" required='true' ></td>
												<td ><label for="Address Line 2 "><strong>Address Line 2<font color="red">*</font> </strong></label></td>
												<td ><input name="addressLine2" type="text" class="field" id="addressLine2"   value="<s:property value="#respData.addressLine2"/>" required='true' /></td>
											</tr>
											<tr class="even">
												<td ><label for="Address Line 3 "><strong>Address Line 3</strong></label></td>
												<td ><input name="addressLine3" type="text" class="field" id="addressLine3" value="<s:property value="#respData.addressLine3"/>" /></td>
												<td ><label for="Country"><strong>Country<font color="red">*</font></strong></label></td>
												<td >
												
												<s:select cssClass="chosen-select"
													headerKey=""
													headerValue="Select"
													list="#respData.Country_List"
													name="country"
													value="#respData.country"
													id="country" requiredLabel="true"
													theme="simple"
													data-placeholder="Choose Country ..."
													 />
												</td>
											</tr>
											<tr class="odd">
												<td ><label for="County"><strong>County<font color="red">*</font></strong></label></td>
												<td >  <s:select cssClass="chosen-select"
															headerKey=""
															headerValue="Select"
															list="#respData.County_List"
															name="area"
															value="#respData.country"
															id="area" requiredLabel="true"
															theme="simple"
															data-placeholder="Choose County ..."
													   />
													 </td>
												<td><label for="City"><strong>City/Town<font color="red">*</font></strong></label></td>
												<td ><input name="city" type="text" class="field" id="city"  value="<s:property value="#respData.city"/>" required='true' /></td>
										   </tr>
											<tr class="even">
												<td ><label for="Postal Code"><strong>Postal Code<font color="red">*</font></strong></label></td>
												<td ><input name="postalCode" type="text" id="postalCode" class="field" value="<s:property value="#respData.postalCode"/>"  required='true'/> <span id="postalCode_err" class="errmsg"></span></td>
												<td ><label for="P.O. Box Number "><strong>P.O. Box Number<font color="red">*</font></strong></label></td>
												<td ><input name="poBoxNumber" type="text" id="poBoxNumber" class="field" value="<s:property value="#respData.poBoxNumber"/>" required='true' /></td>

										   </tr>
										   <tr class="odd">
										   		<td ><label for="Telephone Number 1"><strong>Telephone Number 1</strong></label></td>
												<td><input name="telephoneNumber1" type="text" customvalidation="max::10||min::10" id="telephoneNumber1" class="field" value="<s:property value="#respData.telephoneNumber1"/>" /><span id="telephoneNumber1_err" class="errmsg" ></span></td>
												<td ><label for="Telephone Number 2 "><strong>Telephone Number 2</strong></label>	</td>
												<td ><input name="telephoneNumber2" type="text" customvalidation="max::10||min::10" class="field" id="telephoneNumber2"  value="<s:property value="#respData.telephoneNumber2"/>" /><span id="telephoneNumber2_err" class="errmsg"></span></td>

										   </tr>
										<tr class="even">
											
											<td ><label for="Fax Number"><strong>Fax Number </strong></label></td>
											<td ><input name="faxNumber"  type="text" class="field" id="faxNumber" value="<s:property value="#respData.faxNumber"/>" /><span id="faxNumber_err" class="errmsg"></span></td>
											<td><label for="L/R Number"><strong>L/R Number<font color="red">*</font></strong></label></td>
											<td ><input name="lrNumber" type="text" class="field" id="lrNumber" value="<s:property value="#respData.lrNumber"/>" required='true' /></td>
										</tr>
										 <tr class="odd">
											<%-- <td><label for="Google Co-Ordinates"><strong>Google Co-Ordinates<font color="red">*</font></strong></label></td>
											<td><input name="googleCoOrdinates" type="text" class="field" id="googleCoOrdinates" value="<s:property value="#respData.googleCoOrdinates"/>" required='true' /></td> --%>
											<td><label for="Google Maps Latitude"><strong>Google Maps Latitude<font color="red">*</font></strong></label></td>
											<td><input name="latitude" type="text" class="field" id="latitude" value="<s:property value="#respData.latitude"/>" required='true' />
										     <span id ="error_dlno6" class="errors"></span> 
										    </td>
											<td><label for="Google Maps Longitude"><strong>Google Maps Longitude<font color="red">*</font></strong></label></td>
											<td><input name="longitude" type="text" class="field" id="longitude" value="<s:property value="#respData.longitude"/>" required='true' />
											<span id ="error_dlno7" class="errors"></span>
											</td>
										</tr>
									</table>
								</fieldset>
							</div>
						</div>
				</div>
				
				<div class="row-fluid sortable"><!--/span-->
						<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Bank Account Infromation 
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
									</div>
								</div>
								<div id="bankRelationShipDetails" class="box-content">
									<fieldset>
										<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
											<tr class="even">
											    <td width="20%"><label for="Account Number"><strong>Account Number<font color="red">*</font></strong></label></td>
										        <td width="30%"><input name="accountNumber"  maxlength ='14' id="accountNumber" class="field" type="text"    class="field" value="<s:property value="#respData.accountNumber"/>" maxlength="50" required='true' >
										        <span id ="accountNumber_err" class="errors"></span>
										        </td>
												<td width="20%"><label for="RelationShip Manager Number"><strong>RelationShip Manager Number<font color="red">*</font></strong></label>
												</td>
												<td width="30%"><input name="relationShipManagerNumber"   maxlength ='10' type="text" id="relationShipManagerNumber" class="field" value="<s:property value="#respData.relationShipManagerNumber"/>"  required='true' ></td>
											</tr>
											<tr>
												<td width="20%"><label for="RelationShip Manager Name"><strong>RelationShip Manager Name<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="relationShipManagerName" maxlength ='30' type="text" id="relationShipManagerName" class="field" value="<s:property value="#respData.relationShipManagerName"/>"   required='true' ></td>
												<td width="20%"><label for="RelationShip Manager Email"><strong>RelationShip Manager Email<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="relationShipManagerEmail" type="text" id="relationShipManagerEmail" class="field" value="<s:property value="#respData.relationShipManagerEmail"/>"  required='true' ></td>
											</tr>
									  </table>
								</fieldset>
							</div>
						</div>
				</div>
		
	                 <input name="password" type="hidden" class="field" id="password" />
					 <input name="encryptPassword" type="hidden" class="field" id="encryptPassword"  />
					 <input name="otp" type="hidden" class="field" id="otp"  />
					  
					 
	<input type="hidden" name="agentMultiData"  id="agentMultiData"       value="<s:property value="#respData.agentMultiData"/>" />
    <input type="hidden" name="contactInfoMultidata" id="contactInfoMultidata" value="<s:property value="#respData.contactInfoMultidata"/>" /> 
	</form>
	
	<form name="form5" id="form5" method="post" action="">
				<div class="row-fluid sortable"><!--/span-->
					<div class="box span12">
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Contact Details &nbsp;&nbsp;
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							</div>
						</div>
						<div id="bankContactInformation" class="box-content" >
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"
										class="table table-striped table-bordered bootstrap-datatable ">
									 <tr class="even">	
											<td ><label for="Primary Contact Person"><strong>Primary Contact Person<font color="red">*</font></strong></label></td>
											<td ><input name="prmContactPerson" id="prmContactPerson" class="field" type="text" value="<s:property value="#respData.prmContactPerson"/>" required='true' ></td>
										
											<td><label for="Primary Contact Number"><strong>Primary Contact Number<font color="red">*</font></strong></label></td>
											<td ><input name="prmContactNumber" type="text"  maxlength='10'  class="field" id="prmContactNumber" value="<s:property value="#respData.prmContactNumber"/>" required='true' /><span id="prmContactNumber_err" class="errmsg"></span><span id ="error_dlno1" class="errors"></span></td>
											<%-- <span id ="error_dlno1" class="errors"></span> --%>
									 </tr>
									 <tr class="odd" >
										 <td ><label for="Mobile Number"><strong>Mobile Number<font color="red">*</font></strong></label></td>
										 <td ><input name="mobileNumber" maxlength='10'  id="mobileNumber" class="field" type="text"  value="<s:property value="#respData.mobileNumber"/>" required='true' /><span id="mobileNumber_err" class="errmsg"></span></td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									 </tr>
									 
									<tr class="odd">
										<td colspan="4" align="center"><input type="button" class="btn btn-success"
											name="addConInfo" id="addConInfo" value="Add Contact Information" /> &nbsp;
										<input type="button" class="btn btn-success"
											name="modConInfo" id="modConInfo" value="Update Contact Information" style="display:none"/> &nbsp;
											<span id="conInfoMsg" class="errors"></span></td>
									</tr>
								</table>
							</fieldset>
							<span id="multi-row-ContactInfo" name="multi-row-ContactInfo" style="display:none"> </span>
						<table width="100%"
							class="table table-striped table-bordered bootstrap-datatable "
							id="bankContactInfoTable" style="width: 100%;">
							<thead>
								<tr>
									<th>Sno</th>
									<th>Primary Contact  Number</th>
									<th>Primary Contact Person</th>
									<th>Mobile Number</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody id="tbody_data5">

							<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
								<s:param name="jsonData" value="%{#respData.contactInfoMultidata}"/>
								<s:param name="searchData" value="%{'#'}"/>
							</s:bean>
							<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" >

										<script type="text/javascript">
											var hiddenNames1 = "";
											$(function(){
												$('#bankContactInformation').find('input[type=text],input[type=hidden],select').each(function(index){
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

												$("#multi-row-ContactInfo").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
											});
											</script>

									 	<s:if test="#mulDataStatus.even == true" >
											<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
										</s:if>
										<s:elseif test="#mulDataStatus.odd == true">
											<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
		   								</s:elseif>

									<td><s:property value="#mulDataStatus.index+1" /></td>
										<s:generator val="%{#mulData}"
											var="bankDat" separator="," >
											<s:iterator status="itrStatus">
												<s:if test="#itrStatus.index != 4" >
													<td><s:property /></td>
												</s:if>
											</s:iterator>
										</s:generator>
									<td><a class='btn btn-min btn-info' href='#' id='edit-ContactInfo' index='<s:property value="#mulDataStatus.index" />' title='Edit' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;
										<a class='btn btn-min btn-warning' href='#' id='row-edit-ContactInfo' index='<s:property value="#mulDataStatus.index" />' title='Reset' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp;
										<a class='btn btn-min btn-danger' href='#' id='delete-edit-ContactInfo' index='<s:property value="#mulDataStatus.index" />' title='Delete' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a>
									</td>
							</s:iterator>
							</tbody>
						</table>
					</div>
					</div>
				</div>
	     </form>
	      
	
	<form name="form3" id="form3" method="post" action="">
				<div class="row-fluid sortable"><!--/span-->
					<div class="box span12">
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Agent Based Information
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							</div>
						</div>
						<div id="agentBasedInfo" class="box-content">
							<fieldset>
								 <table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable " >
										
										<tr  class="odd">
											<td><label for="Grace Period"><strong>Airtel Money Agent Number </strong></label></td>
											<td><input name="airtelMoneyAgenetNo" id="airtelMoneyAgenetNo" class="field"  type="text" /></td>
											<td><label for="mandatory"><strong>Orange Money Agent Number</strong></label></td>
											<td><input name="orangeMoneyAgentNo" id="orangeMoneyAgentNo" class="field"  type="text" /></td>
										</tr>
										<tr class="even">
											<td><label for="Master Card Agent Number"><strong>Master Card Agent Number</strong></label></td>
											<td ><input name="mpesaTillNumber" id="mpesaTillNumber" class="field" type="text" /></td>
											<td width="20%"><label for="Document Description"><strong>MPesa Agent Number</strong></label></td>
											<td width="30%"><input name="MPeasAgenctNo" id="MPeasAgenctNo" class="field" type="text" /> </td>
										</tr>
										<tr class="odd">
											    <td colspan="4" align="center"><input type="button" class="btn btn-success"
												name="addCapAgent" id="addCapAgent" value="Add Other Billers" /> &nbsp;
											    <input type="button" class="btn btn-success"
											    name="modCapAgent" id="modCapAgent" value="Update Agent Details" style="display:none"/> &nbsp;
												<span id="agentAcctMsg" class="errors"></span></td>
										</tr>
								</table>
							</fieldset>
							<span id="multi-row-agent" name="multi-row-agent" style="display:none"> </span>
							<table width="100%" class="table table-striped table-bordered bootstrap-datatable "
									id="agentTable" style="width: 100%;">
								  <thead>
										<tr>
											<th>Sno</th>
											<!-- <th>mVisa Number</th> -->
											<th>MPesa Agent Number</th>
											<th>Airtel Money Agent Number</th>
											<th>Orange Money Agent Number</th>
											<th>Master Card Agent Number</th>
											<th>Action</th>
										</tr>
								  </thead>
								 <tbody id="tbody_data3">
									<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{#respData.agentMultiData}"/>
										<s:param name="searchData" value="%{'#'}"/>
									</s:bean>
									<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" >

												<script type="text/javascript">
													var hiddenNames1 = "";
													$(function(){
														$('#agentBasedInfo').find('input[type=text],input[type=hidden],select').each(function(index){
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

														$("#multi-row-agent").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
													});
													</script>
												<s:if test="#mulDataStatus.even == true" >
													<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
												</s:if>
												<s:elseif test="#mulDataStatus.odd == true">
													<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
												</s:elseif>

											<td><s:property value="#mulDataStatus.index+1" /></td>
												<s:generator val="%{#mulData}"
													var="bankDat" separator="," >
													<s:iterator status="itrStatus">
														<td><s:property /></td>
													</s:iterator>
												</s:generator>
											<td><a class='btn btn-min btn-info' href='#' id='editDatAgent' index='<s:property value="#mulDataStatus.index" />' title='Edit' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;
												<a class='btn btn-min btn-warning' href='#' id='row-cancel-agent' index='<s:property value="#mulDataStatus.index" />' title='Reset' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp;
												<a class='btn btn-min btn-danger' href='#' id='delete-agent' index='<s:property value="#mulDataStatus.index" />' title='Delete' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a>
											</td>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
				</div>
	</form>
	<form name="form4" id="form4" method="post" action="">
				<div class="row-fluid sortable"><!--/span-->
					<div class="box span12">
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Document Information
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							</div>
						</div>
						<div id="documentInformation" class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable " >
									<tr class="even">
										<td width="20%"><label for="Document ID"><strong>Document ID</strong></label></td>
										<td width="30%">
 											<select name="documentId" id="documentId" required='true'
												data-placeholder="Choose DocumentId..."
												class="chosen-select" style="width: 220px;" >
												<option value="">Select</option>
												<option value="BUSS">Business Permit</option>
												<option value="DID_PASS">Director Id/Passport</option>
												<option value="CER_INC">Certificate Of Incorporation</option>
												<option value="BUSS">Business Permit</option>
											</select>
											</td>
										<td width="20%"><label for="Document Description"><strong>Document Description</strong></label></td>
										<td width="30%"><input name="documentDescription" id="documentDescription" class="field" type="text"> </td>
									</tr>
									<tr  class="odd">
										<td><label for="Grace Period"><strong>Grace Period(in Days) </strong></label></td>
										<td><input name="gracePeriod" id="gracePeriod" class="field"  maxlength="50" type="text" ></td>
										<td><label for="mandatory"><strong>Mandatory</strong></label></td>
										<td>
											<select id="mandatory" name="mandatory"  required='true' data-placeholder="Choose member type..." class="chosen-select" style="width: 220px;" >
												<option value="">Select</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</td>
									</tr>
									<tr class="odd">
										<td colspan="4" align="center"><input type="button" class="btn btn-success"
											name="addCapDocs" id="addCapDocs" value="Add Document Information" /> &nbsp;
											 <input type="button" class="btn btn-success"
											name="modCapDocs" id="modCapDocs" value="Update Document Information" style="display:none"/> &nbsp;
											<span id="docsAcctMsg" class="errors"></span></td>
									</tr>
							</table>
						</fieldset>
						<span id="multi-row-doc" name="multi-row-doc" style="display:none"> </span>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable "	id="documentTable" style="width: 100%;">
								  <thead>
										<tr>
											<th>Sno</th>
											<th>Document ID</th>
											<th>Document Description</th>
											<th>Grace Period</th>
											<th>Mandatory</th>
											<th>Action</th>
										</tr>
								  </thead>
								 <tbody id="tbody_data2">
								 	<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{#respData.documentMultiData}"/>
										<s:param name="searchData" value="%{'#'}"/>
									</s:bean>
									<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" >

												<script type="text/javascript">
													var hiddenNames1 = "";
													$(function(){
														$('#documentInformation').find('input[type=text],input[type=hidden],select').each(function(index){
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
														$("#multi-row-doc").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
													});
													</script>

												<s:if test="#mulDataStatus.even == true" >
													<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
												</s:if>
												<s:elseif test="#mulDataStatus.odd == true">
													<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
												</s:elseif>

											<td><s:property value="#mulDataStatus.index+1" /></td>
												<s:generator val="%{#mulData}"
													var="bankDat" separator="," >
													<s:iterator status="itrStatus">
															<td><s:property /></td>
													</s:iterator>
												</s:generator>
											<td><a class='btn btn-min btn-info' href='#' id='editDatDocs' index='<s:property value="#mulDataStatus.index" />' title='Edit' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;
												<a class='btn btn-min btn-warning' href='#' id='row-cancel-docs' index='<s:property value="#mulDataStatus.index" />' title='Reset' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp;
												<a class='btn btn-min btn-danger' href='#' id='delete-docs' index='<s:property value="#mulDataStatus.index" />' title='Delete' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a>
											</td>
										</tr>
									</s:iterator>
								 </tbody>
							</table>
						</div>
						
					</div>
				</div>
	</form>
		<div class="form-actions">
			
			<a  class="btn btn-danger" href="generateMerchantAct.action" onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp;
			<a  class="btn btn-success" href="#" onClick="createMerchant()">Submit</a>
			<span id ="error_dlno" class="errors"></span>
		</div>
	</div>
<script type="text/javascript">
$(function(){

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

	$('#merchantType').val('<s:property value="#respData.merchantType" />');
	$('#merchantType').trigger("liszt:updated");

	$('#memberType').val('<s:property value="#respData.memberType" />');
	$('#memberType').trigger("liszt:updated");

	$('#merchantAdminId').val('<s:property value="#respData.merchantAdminId" />');
	$('#merchantAdminId').trigger("liszt:updated");

	getInfo();

	$('#country').val('<s:property value="#respData.country" />');
	$('#country').trigger("liszt:updated");

	});
	</script>
</body>
<script language="Javascript" src="${pageContext.request.contextPath}/js/manual-validation.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
</html>