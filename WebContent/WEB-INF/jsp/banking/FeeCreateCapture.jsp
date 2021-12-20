<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>IMPERIAL</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="Another one from the CodeVinci" />
 <%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
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
<script type="text/javascript">
var globalIndex = 0;
var form2Rules = {
     rules : {
			frequency : {  required: true   },
			merchantid : {  required: true   },
			channel : {  required: true,  maxlength : 5 },
			currency : { required: true    },
			condition : {   required: true   },
			fromRange : {
						required: {
							depends: function(element) {
							if ($("#condition").val() == 'C'){
								return true;
							}  else {
								return false;
						   }
						}
					 },
					 number : {
						depends: function(element) {
							if ($("#condition").val() == 'C'){
								return true;
							}  else {
								return false;
						   }
						}
					 }
				 },
			toRange : {
						required: {
							depends: function(element) {
							if ($("#condition").val() == 'C'){
								return true;
							}  else {
								return false;
						   }
						}
					 },
					 number : {
						depends: function(element) {
							if ($("#condition").val() == 'C'){
								return true;
							}  else {
								return false;
						   }
						}
					 }
				 },

				flatPercent : { required : true },
				bankAccount : { required : true },
				bankAmount : { required : true , number : true},
				agentAccount : { required : true },
				agentAmount : { required : true , number : true },
				serviceTax : { required : true , number : true },
				ServiceTaxAccount : { required : true , number : true }

    },
    messages : {
			frequency : {   required: "Please Select Frequency."    },
			merchantid : {   required: "Please Select MerchantId."    },
			channel : {   required: "You must check at least 1 box", maxlength: "Check no more than {0} boxes"     },
			currency : {  required: "Please Select Currency."    },
			condition : {   required: "Please Select Condition."   },
			fromRange : {   required: "Please Select From.", number : "Please Input Number From [0-9]"   },
			toRange : { required: "Please Select To.", number : "Please Input Number From [0-9]"    },
			flatPercent : {
				required : "Please Select Flat/Percent"
				 },
			bankAccount : {
				required : "Please Select Bank Account"
				 },
			bankAmount : {
				required : "Please Input Bank Amount"
				} ,
			agentAccount : {
				required : "Please Select Agent Account"
				 },
			agentAmount : {
				required : "Please Input Agent Amount"
			} ,
			serviceTax : {
						required : "Please Enter Service Tax"
					},
					ServiceTaxAccount : {
				required : "Please  Select Service  tax account"
			}
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

var form5Rules = {
    rules : {
		serviceBin : {
					required: {
							depends: function(element) {
								if ($('#off-us').is(":checked")){
									return true;
								}  else {
									return false;
							   }
							}
						}
				},
		acquirerId : {
					required: {
							depends: function(element) {
								//console.log("Inside....")
								if ($("#form1").find("input[name=serviceTrans]:checked").val() == 'OFFUS') {
									var channels = "";
									$("#form2").find("input[name=channel]:checked").each(function(index){
										if(index == 0) channels+=$(this).val() ;
										else channels+=","+$(this).val() ;
									});

									if(channels == 'CARD'
										&& $("#form5").find("#acquirerId option:selected").val() == "") {
										return true;
									} else {
										return false;
									}
								}  else {
									return false;
							   }
							}
					 }
			 }
    },
    messages : {
		serviceBin : {
			required : "Please Select Bin."
			 },
		acquirerId : {
			required : "Acquirer Id is applicable for the network 'Card' alone."
			 }
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
var rowindex = 1;
var colindex = 0;
var accountFinalData="";
var accountFinalData1="";
var accountFinalData2="";


 function leftPad(toPad, padChar, length){
    return (String(toPad).length < length)
        ? new Array(length - String(toPad).length + 1).join(padChar) + String(toPad)
        : toPad;
}

$(document).ready(function() {
	
	$("#disp1").hide();
	$("#disp2").hide();
	
	var feeCode1 = '<s:property value="#respData.feeCode" />';
	feeCode1='F'+leftPad(feeCode1,0,7);
	var feeCode='<s:property value="#respData.serviceCode" />-'+feeCode1;
	$("#form1").find('input[name=feeCode]').val(feeCode);
	$("#form4").find('input[name=feeCode]').val(feeCode);
	console.log('${responseJSON}');
	$.validator.addMethod("le", function(value, element, param) {
		return this.optional(element) || value <= $("#form2 input[name="+param+"]").val();
	}, "less");

	$.validator.addMethod("ge", function(value, element, param) {
		return this.optional(element) || value >= $("#form2  input[name="+param+"]").val();
	}, "greater");

	$.validator.addMethod("mo", function(value, element, param) {
		return this.optional(element) || value % $("#form2  input[name="+param+"]").val();
	}, "modulus");

	//$('#amt-chk').hide();
	//$('#count-chk').hide();

	$("#condition").live('change',function(e){
		var cond = $(this).val();
		if(cond == 'C') {
 			$('.rngdata').text('Count');
			$('#rng-chk').show();
		} else if( cond == 'A') {
			$('.rngdata').text('Amount');
			$('#rng-chk').show();
		}else {
			$('#rng-chk').hide();
			$('.rngdata').text('');
		}
	});

	$('#form1').find('input[type=radio]').each(function(){
 		if($(this).attr("id") == "on-us" && $(this).is(':checked') ){
			$("#acq-bin-chk").hide();
			$("#disp1").hide();
			$("#disp2").hide();
		} else if($(this).attr("id") == "off-us" && $(this).is(':checked') ){
			$("#acq-bin-chk").show();
			$("#disp1").show();
			$("#disp2").show();
		}
	});

 	$('input[type=radio]').live('click',function() {
		if($(this).attr("id") == "on-us" && $(this).is(':checked') ){
			$("#acq-bin-chk").hide();
			$("#disp1").hide();
			$("#disp2").hide();
			$('#form5').removeData('validator');
		} else if($(this).attr("id") == "off-us" && $(this).is(':checked') ) {
			$("#acq-bin-chk").show();
			$("#disp1").show();
			$("#disp2").show();	
		}
 	});

	$('#back-button').live('click',function() {
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/serviceMgmtAct.action';
	$("#form1").submit();
	return true;
	});

});
</script>
<script type="text/javascript">
var index2 = "";
var selTextListFee = "frequency,currency,condition,flatPercent,bankAccount,agentAccount";
var headerListFee = "frequency,currency,condition,fromRange,toRange,flatPercent,bankAmount,agentAmount,serviceTax".split(",");
var listid = selTextListFee.split(",");

function clearValsFee() {
	$('#frequency').val('');
	$('#currency').val('');
	$('#fromRange').val('');
	$('#toRange').val('');
	$('#agentAmount').val('');
	$('#bankAmount').val('');
	$('#serviceTax').val('');

	$(listid).each(function(index,val){
		$('#'+val).find('option').each(function( i, opt ) {
			if( opt.value == '' ) {
				$(opt).attr('selected', 'selected');
				$('#'+val).trigger("liszt:updated");
			}
		});
	});
  //	$(".chkChannel").removeAttr("checked");
}

function commonData(id,type){
	var hiddenInput ="";
	var hiddenNames = "";
	var tabarrindex = 0;
	tabArry = new Array();
	modTabArry = new Array();

	try {
 		 var channels = "";
		 $('.chkChannel:checked').each(function(i){
			var values = $(this).val();
			if(i == 0 ) {
				channels += values;
			} else {
				channels += ","+values;
			}
		});

		$(id).find('input[type=text],input[type=hidden],select').each(function(indxf) {
			var nameInput = "";
			var valToSpn =  "";
			try {
				  nameInput = $(this).attr('name') ;
				  valToSpn = ($(this).attr('value') =='' ? ' ' :$(this).attr('value'));
			} catch(e1) {
				console.log('The Exception Stack is :: '+ e1);
			}
			//console.log("Type ["+$(this).attr('type')+"]  Name ["+$(this).attr('name')+"] Values["+$(this).val()+"] valToSpn ["+valToSpn+"]");

			if(nameInput != undefined) {
			  if(indxf == 0)  {
				hiddenInput += valToSpn;
				hiddenNames += nameInput;
			  } else {
				hiddenInput += "@"+valToSpn;
				hiddenNames += ","+nameInput;
			  }

			   if(jQuery.inArray(nameInput, headerListFee) != -1){
				  if(selTextListFee.indexOf(nameInput) != -1) {
					tabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
					modTabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
				  }  else {
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
		data1 = commonData('#secondaryDetails','ADD');
		rowindex = $("#multi-row-data > span").length ;
		var indData = tabArry[0]+"-"+rowindex;
		$("#multi-row-data").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+indData+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
		$('#multi-row-data > span#hidden_span_'+rowindex).append(data1.split("@@")[0]);

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
 				"<td><a class='btn btn-min btn-info' href='#' id='editDatFee' index='"+rowindex+"' title='Edit' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel-fee' index='"+rowindex+"' title='Reset' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete-fee' index='"+rowindex+"' title='Delete' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";
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
		data1 = commonData('#secondaryDetails','MODIFY');
		modHeaderBodyArry[0]=data1.split("@@")[1];
		modHeaderBodyArry[1]=data1.split("@@")[0];
	} catch(e)  {
		console.log(e);
	}
	//console.log(data1.split("@@")[1]+"##"+data1.split("@@")[0]);
	return "";
};

function recordCheck(idtocheck,userIdInput) {
	var check = false;
	var userIdRecords = new Array();
	try {
		if($(idtocheck).length > 0) {
			// To Check The Record Exists In The Add-Row
			$(idtocheck+' > td:nth-child(2)').each(function(indx) {
 				userIdRecords[indx]=$(this).text().trim();
			});
			if(jQuery.inArray(userIdInput.trim(), userIdRecords) == -1) {
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

function loadToolTip() {
	$('[rel="tooltip"],[data-rel="tooltip"]').tooltip({"placement":"bottom",delay: { show: 400, hide: 200 }});
}

/** Form2 Add,Modify Ends*/
$(function(){
	// Add Row Starts Here.
	$('#addFee').live('click', function() {

		var  fromamount=$("#fromRange").val();

		var toamount=$("#toRange").val();

		if(parseInt(fromamount)>parseInt(toamount))
		{
			alert("Sorry, toAmount should always Greater then from Amount");
			return false;
		}
		/* else
		if($('#bankAmount').val() != null && $('#agentAmount').val() != null && parseFloat($('#bankAmount').val()) < parseFloat($('#agentAmount').val()))
		{
			alert("Sorry, Bank Amount should always Greater then Agent Amount. ");
			return false;
		} */
		else
			{
		$("#bankAcctMsg").text('');
 		$("#error_dlno").text('');

		$("#form2").validate(form2Rules);
		 var textMess = "#tbody_data1 > tr";
		if($("#form2").valid()) {
			// Is To Check Account Exist's or not
			//if(recordCheck(textMess,$('#bankAgenctNo').val())) {
				var appendTxt = addAgentInfo("ADD");
				//console.log("appendTxt ===> "+ appendTxt);
				var service=$("#serviceTax").val();

				if(service>= 100)
					{
						alert("Service Tax Only Before 100 kes Only");

					}else
						{
								$("#tbody_data1").append(appendTxt);
						}
				//clearValsFee();
				alignSerialNo(textMess);
				resetnextfromamount();
				$("#bankAcctMsg").text('');
				$('#error_dlno').text('');
 				loadToolTip();
			//} else {
			//	$("#error_dlno").text('');
			//	$('#bankAcctMsg').text('Account Number Exists.');
			//}
		} else {
			// This is to throw error nessage.
			$('#error_dlno').text('');
			//$('#error_dlno').text('The User With "'+$('#userId option:selected').val()+'" Exists.');
			return false;
		}
			}
	});

	// Update Edited-Row Starts Here.
	$('#updateFee').live('click', function () {
		$("#error_dlno").text('');
		$("#form2").validate(form2Rules);
		if($("#form2").valid()) {
 			var spanValues = modAgentAccVals("MODIFY");
 			searchTdRow = '#'+searchTrRows+"#tr-"+index2 +' > td';
			var textToSearch = "";
			//console.log(searchTdRow);
 			$(searchTdRow).each(function(index,value) {
					if(index == 1){
						$(this).text(modTabArry[0]);
 					}if(index == 2){
						$(this).text(modTabArry[1]);
					}if(index == 3){
						$(this).text(modTabArry[2]);
					}if(index == 4){
						$(this).text(modTabArry[3]);
					} if(index == 5){
						$(this).text(modTabArry[4]);
					}
					if(index == 6){
						$(this).text(modTabArry[5]);
					}if(index == 7){
						$(this).text(modTabArry[6]);
					}if(index == 8){
						$(this).text(modTabArry[7]);
					}if(index == 9){
						$(this).text(modTabArry[8]);
					}if(index == 10){
						$(this).text(modTabArry[9]);
					}if(index == 11){
						$(this).text(modTabArry[10]);
					}if(index == 12){
						$(this).text(modTabArry[11]);
					}
			});

			$("#multi-row-data span").each(function(index,value) {
 				// if($(this).attr("index") == index2 ) {
				console.log( $(this).attr("index")  +" <====>" + globalIndex);
 				 if( $(this).attr("index")  == globalIndex ) {
					 $(this).attr("hid-names",modHeaderBodyArry[0]);
					 $(this).text(modHeaderBodyArry[1]);
					 return;
				 }
			});
			clearValsFee();
			alignSerialNo("#tbody_data1 > tr");

			//Hide Update Button and Display Add Button
			$('#updateFee').hide();
			$('#addFee').show();

		} else {
			return false;
		}
	});

	// The below event is to Edit row on selecting the delete button
	$('#editDatFee').live('click',function() {
		$("#form2").validate().cancelSubmit = true;

		index2 = $(this).attr('index');
		var parentId =$(this).parent().closest('tbody').attr('id');
		searchTrRows = parentId+" tr";
		searchTdRow = '#'+searchTrRows+"#tr-"+index2 +' > td';

		var idSearch = "";
		var hidnames = "";
		var text_val = "";

		//console.log("searchTdRow ==> "+searchTdRow);

		var spanData = $("#multi-row-data > span#hidden_span_"+index2);
		hidnames =  $("#multi-row-data > span#hidden_span_"+index2).attr('hid-names');
		text_val =  $("#multi-row-data > span#hidden_span_"+index2).text();

		globalIndex = $(this).attr('index');

		var hidarr=hidnames.split(",");
		var textarr=text_val.split("@");   //Spliting span values

		//console.log(hidarr);
		//console.log(textarr);
		//console.log("GlobalIndex in Edit  for ==> "+globalIndex);

		$(hidarr).each(function(index,value) {
			 if(jQuery.inArray( value, listid ) != -1){
				$('#'+value).find('option').each(function( i, opt ) {
					if( opt.value == textarr[index] ) {
						$(opt).attr('selected', 'selected');
						$('#'+value).trigger("liszt:updated");
					}
				});
			} else {
				//$("#"+value).val(textarr[index].trim());
				$("#"+value).val(textarr[index] == undefined ? " " : textarr[index].trim());
			}
		});
		//Hide Add Button and Display Update Button
		$('#updateFee').show();
		$('#addFee').hide();

	});

	// The below event is to delete the entire row on selecting the delete button
	$("#delete-fee").live('click',function() {
		var delId = $(this).attr('index');
		$(this).parent().parent().remove();

		var spanId = "";
		$('#multi-row-data > span').each(function(index){
			spanId =  $(this).attr('index');
			if(spanId == delId) {
				$(this).remove();
			}
		});

		clearValsFee();
		// Aligning the serial number
		alignSerialNo("#tbody_data1 > tr");

		//Show Add Button and Hide Update Button
		$('#updateFee').hide();
		$('#addFee').show();
	});

	// Clear Form Records Row Starts Here.
	$('#row-cancel-fee').live('click', function () {
		$("#error_dlno").text('');
		 clearValsFee();

		//Show Add Button and Hide Update Button
		$('#updateFee').hide();
		$('#addFee').show();
	});

	$('#submit-button').live('click',function() {


		if($("#feename1").val().length == 0)
			{
				alert("Please Enter Fee Name");
			}else
				{
			var data=$("#feename1").val();
			$("#feename").val(data);

		var finalData = "";

		$('#multi-row-data > span').each(function(index) {
 			if(index == 0)  finalData = $(this).text();
			else finalData += "#"+ $(this).text();
		});
		$("#multiSlabFeeDetails").val(finalData);

		finalData="";
		$('#multi-row-bin > span').each(function(index) {
 			if(index == 0)  finalData = $(this).text();
			else finalData += "#"+ $(this).text();
		});
		$("#offusTrnDetails").val(finalData);

		if($("#form1").valid()) {
				if($("#multiSlabFeeDetails").val() == "") {
					$("#error_dlno").text("Please capture atleast one fee range.");
				}  else {
					$("#error_dlno").text("");
					$("#form4").find('input[name=serviceTrans]').val($("#form1").find('input[name=serviceTrans]:checked').val());

					$("#form4").find('input[name=hudumaFlag]').val('false');
					$("#form4").find('input[name=hudumaServiceName]').val('NO');
					$("#form4").find('input[name=hudumaSubServiceName]').val('NO');
					var data1=$("#form4").find('input[name=feename]').val();
					if($("#offUsFeeVal").val() == ""){
						$("#offUsFeeVal").val("0");
						alert($("#offUsFeeVal").val());	
					}
					$("#offUsFee").val($("#offUsFeeVal").val());
					$("#form4")[0].action='<%=request.getContextPath()%>/<%=appName %>/feeCreateSubAct.action';
					$("#form4").submit();
					return true;
				}
		} else {
			return false;
		}
				}
	});

});

/** Form3 Add Row Validation Agent Ends*/
function alignSerialNo(serialId) {
	if($(serialId).length > 0) {
		$(serialId +' > td:first-child').each(function(index){
			$(this).text(index+1);
		});
	}
}
 </script>
 <script type="text/javascript">
function clearValsBin(){

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
var selTextListDocs = "serviceBin,acquirerId";
var headerListDocs = "serviceBin,acquirerId".split(",");
var listiddoc = selTextListDocs.split(",");

function commonDataDocs(id,type){
	var hiddenInput ="";
	var hiddenNames = "";
	var tabarrindex = 0;
	tabArry = new Array();
	modTabArry = new Array();

	try {
		//console.log(id);
		$(id).find('input[type=text],select').each(function(indxf){
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
/** Form5 Add Row Validation Bin-Acq Starts */
var addDocsInfo = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	 var tabArrText = "";
	 var data1 = "";
	try {
		data1 = commonDataDocs('#acq-bin-chk','ADD');

		rowindex = $("#multi-row-bin > span") .length ;

		$("#multi-row-bin").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+tabArry[0]+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
		$('#multi-row-bin > span#hidden_span_'+rowindex).append(data1.split("@@")[0]);

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
 				"<td><a class='btn btn-min btn-info' href='#' id='editDatBin' index='"+rowindex+"' title='Edit' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel-bin' index='"+rowindex+"' title='Reset' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete-bin' index='"+rowindex+"' title='Delete' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";

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
		data1 = commonDataDocs('#acq-bin-chk','MODIFY');

		modHeaderBodyArry[0]=data1.split("@@")[1];
		modHeaderBodyArry[1]=data1.split("@@")[0];
	} catch(e)  {
		console.log(e);
	}
	//console.log(data1.split("@@")[1]+"##"+data1.split("@@")[0]);
	return "";
};

/** Form2 Add,Modify Ends*/
$(function(){
	// Add Row Starts Here.
	$('#addOffusBin').live('click', function(){
		$("#bankAcctMsg").text('');
 		$("#error_dlno").text('');
		$("#form5").validate(form5Rules);
		 var textMess = "#tbody_data3 > tr";
		 //console.log("Inside bank Adding....");
		if($("#form5").valid()) {
			// Is To Check Account Exist's or not
			//if(recordCheck(textMess,$('#serviceBin option:selected').val())) {
				var appendTxt = addDocsInfo("ADD");
				//console.log("appendTxt ===> "+ appendTxt);
				$("#tbody_data3").append(appendTxt);
				clearValsBin();
				alignSerialNo(textMess);
				$("#bankAcctMsg").text('');
				$('#error_dlno').text('');
				loadToolTip();
			//} else {
			//	$("#error_dlno").text('');
			//	$('#bankAcctMsg').text('Bin Already Exists.');
			//}
		} else {
			// This is to throw error nessage.
			$('#error_dlno').text('');
			//$('#error_dlno').text('The User With "'+$('#userId option:selected').val()+'" Exists.');
			return false;
		}

	});

	// Update Edited-Row Starts Here.
	$('#updateOffusBin').live('click', function () {
		$("#error_dlno").text('');
		$("#form5").validate(form5Rules);
		if($("#form5").valid()) {

 			var spanValues = modDocsAccVals("MODIFY");
 			searchTdRow = '#'+searchTrRows+"#tr-"+index3 +' > td';
 			$(searchTdRow).each(function(index,value) {
 				if(index == 1){
					$(this).text(modTabArry[0]);
				}if(index == 2){
					$(this).text(modTabArry[1]);
				}
			});

			$("#multi-row-bin span").each(function(index,value) {
 				 if($(this).attr("index") == index3 ) {
					 $(this).attr("hid-names",modHeaderBodyArry[0]);
					 $(this).text(modHeaderBodyArry[1]);
					 return;
				 }
			});

			clearValsBin();
			alignSerialNo("#tbody_data3 > tr");

			//Hide Update Button and Display Add Button
			$('#updateOffusBin').hide();
			$('#addOffusBin').show();

		} else {
			return false;
		}

	});

	// The below event is to Edit row on selecting the delete button
	$('#editDatBin').live('click',function() {
		$("#form1").validate().cancelSubmit = true;
		$("#form2").validate().cancelSubmit = true;
		$("#form5").validate().cancelSubmit = true;

		index3 = $(this).attr('index');
		var parentId =$(this).parent().closest('tbody').attr('id');
		searchTrRows = parentId+" tr";
		searchTdRow = '#'+searchTrRows+"#tr-"+index3 +' > td';

		var idSearch = "";
		var hidnames = "";
		var text_val = "";

		/*$(searchTdRow).each(function(index,value) {
 			if(index == 1) {
				idSearch = $(this).text().trim();
			}
		});

		$("#multi-row-bin > span").each(function(index,value) {
			if(idSearch == $(this).attr('ind-id') ) {
				hidnames = $(this).attr('hid-names');
				text_val = $(this).text();
			}
		});  */
		var spanData = $("#multi-row-bin > span#hidden_span_"+index3);
		hidnames =  $("#multi-row-bin > span#hidden_span_"+index3).attr('hid-names');
		text_val =  $("#multi-row-bin > span#hidden_span_"+index3).text();

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
				$("#"+value).val(textarr[index].trim());
			}
		});

		//Hide Add Button and Display Update Button
		$('#updateOffusBin').show();
		$('#addOffusBin').hide();

	});

	// The below event is to delete the entire row on selecting the delete button
	$("#delete-bin").live('click',function() {
		var delId = $(this).attr('index');
		$(this).parent().parent().remove();

		var spanId = "";
		$('#multi-row-bin > span').each(function(index){
			spanId =  $(this).attr('index');
			if(spanId == delId) {
				$(this).remove();
			}
		});

		clearValsBin();
		// Aligning the serial number
		alignSerialNo("#tbody_data3 > tr");

		//Show Add Button and Hide Update Button
		$('#updateOffusBin').hide();
		$('#addOffusBin').show();
	});

	// Clear Form Records Row Starts Here.
	$('#row-cancel-bin').live('click', function () {
		$("#error_dlno").text('');
		 clearValsBin();

		//Show Add Button and Hide Update Button
		$('#updateOffusBin').hide();
		$('#addOffusBin').show();
	});

});

function feecodename(){


    var merid_val= $('#merchantid').val();
    var merid_text= $('#merchantid').text();
    var subservice= $('#subServiceName').val();

    // Fee Name MERC00000001+" "+WDL

  //  $('#feename').val(merid_val+" "+subservice)

    $('#merchantname').val(merid_val);

    $('#merchantdata').val($('#merchantid').val());

		$('#agentAccount').find('option').each(function( i, opt ) {

			 var selmerch=opt.text.split("-");

			if( selmerch[1] === merid_val ) {
				$(opt).attr('selected', 'selected');
				$('#agentAccount').trigger("liszt:updated");
			}
		});

		//$("#form4").find('input[name=feename]').val(merid_val+" "+subservice);
		$("#form4").find('input[name=merchantname]').val(merid_val);

}

function resetnextfromamount(){
	  var cnd= $('#condition').val();
	  $('#toRange').val();
	  if( cnd =="A" ) {

		  $('#fromRange').val(parseInt($('#toRange').val())+1);
		  $('#toRange').val("")
	  }

}
/** Form3 Add Row Validation Docs Ends*/

function ServiceValidation()
{
	var service=$("#serviceTax").val();
	alert(service);
	if(service>= 100)
		{
			alert("Service Only Before 100 kes Only");

		}
}

/* function Amountvalid()
{
	var  fromamount=$("#fromRange").val();
	alert(fromamount)
	var toamount=$("#toRange").val();
alert(toamount);
	if(parseInt(fromamount)>parseInt(toamount))
		{
				alert("Sorry, toAmount always Greater then from Amount");
				return false;
		}
} */
 </script>
</head>
<body>

<div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="serviceMgmtAct.action">Fee Management</a> <span class="divider"> &gt;&gt; </span></li>
			  <li><a href="#">Create Fee</a></li>
			</ul>
		</div>

	  <div class="row-fluid sortable"><!--/span-->
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<i class="icon-edit"></i>Create Fee
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>
		<form name="form1" id="form1" method="post" action="">
			<div id="primaryDetails" class="box-content">
				<fieldset>
					 <table width="950" border="0" cellpadding="5" cellspacing="1"
						class="table table-striped table-bordered bootstrap-datatable " >
							<tr class="odd">
								<td width="20%"><label for="Service ID"><strong>Service Code<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="serviceCode" type="text" id="serviceCode" class="field" value='<s:property value="#respData.serviceCode" />' readonly  /></td>
								<td width="20%"><label for="Service Name"><strong>Service Name<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="serviceName" type="text"  id="serviceName" class="field"  value='<s:property value="#respData.serviceName" />' readonly /></td>
							</tr>
							<tr class="odd" id='merchantDetails'>
								<td> <label for="Service"><strong>Merchant Details<font color="red">*</font></strong></label> 	</td>
								<td>
										 <s:select cssClass="chosen-select"
												headerKey=""
												headerValue="Select"
												list="#respData.merchantList"
												name="merchantid"
												id="merchantid"
												requiredLabel="true"
												theme="simple"
												data-placeholder="Choose Acquirer Id..."
												onchange="feecodename()"
												 />
 								</td>
 								<td> <label for="Mercahnt Name"><strong>Mercahnt Name<font color="red">*</font></strong></label> 	</td>
								<td ><input name="merchantname" type="text" id="merchantname" class="field"    readonly /></td>


							</tr>
							<tr class="even">
								<td><label for="Fee Code"><strong>Fee Code<font color="red">*</font></strong></label></td>
								<td ><input name="feeCode" type="text" id="feeCode" class="field" readonly /></td>
								<td> <label for="Fee Name"><strong>Fee Name<font color="red">*</font></strong></label> 	</td>
								<td ><input name="feename1" type="text" id="feename1" class="field"   value=""/></td>
								<%-- <input type="hidden" name="feename" id="feename" value="${feename}"/> --%>
							</tr>
							<tr class="odd">
								<td> <label for="Service"><strong>ON-US/OFF-US Flag<font color="red">*</font></strong></label> 	</td>
								<td>
									<input name="serviceTrans" type="radio" value="ONUS" id="on-us" />ON-US &nbsp;&nbsp;
									<input name="serviceTrans" type="radio" value="OFFUS" id="off-us" checked="true"/>OFF-US
 								</td>
 								<td><span id="disp1">OFF-US Fee</span></td>
								<td ><span id="disp2"><input type="text" name="offUsFeeVal" id="offUsFeeVal" value="${offUsFee}"/></span></td>


							</tr>
					</table>
				</fieldset>
			</div>
		</form>
		<form name="form5" id="form5" method="post" action="">
			<div id="acq-bin-chk" class="box-content">
					<fieldset>
						 <table width="950" border="0" cellpadding="5" cellspacing="1"
							class="table table-striped table-bordered bootstrap-datatable " >
								<tr class="odd" >
									<td width="20%"> <label for="Bins"><strong>Select Bin</strong></label> </td>
									<td width="30%">
											 <s:select cssClass="chosen-select"
												headerKey=""
												headerValue="Select"
												list="#respData.BINS"
												name="serviceBin"
												id="serviceBin"
												requiredLabel="true"
												theme="simple"
												data-placeholder="Choose Acquirer Id..."
												 />
									</td>
									<td width="20%"><span id="acq-chk"> <label for="Acquirer Id"><strong>Acquirer ID <br/>
									(Applicable only for NBK Cards)</label>  </span></td>
									<td width="30%"><span id="acq-chk1">
													 <s:select cssClass="chosen-select"
														headerKey=""
														headerValue="Select"
														list="#respData.ACQ_ID"
														name="acquirerId"
														id="acquirerId" requiredLabel="true"
														theme="simple"
														data-placeholder="Choose Acquirer Id..."
														 />
													</span>
									</td>
								</tr>
								<tr class="odd">
									<td colspan="4">
										<input type="button" class="btn btn-success" name="addOffusBin" id="addOffusBin" value="Add" />
										<input type="button" class="btn btn-success" name="updateOffusBin" id="updateOffusBin" value="Update" style="display:none" />
									</td>

								</tr>
						</table>
						<table id="mytable" width="950" border="0" cellpadding="5" cellspacing="1"
							class="table table-striped table-bordered bootstrap-datatable ">
							<thead>
								<tr>
									<th>Sno</th>
									<th>Bin</th>
									<th>Acquirer Id</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody id="tbody_data3">
							</tbody>
						</table>
					<fieldset>
					<span id="multi-row-bin" class="multi-row-bin" style="display:none"> </span>
			</div>
		</form>
		<form name="form2" id="form2" method="post" action="">
			<div id="secondaryDetails" class="box-content">
				<fieldset>
					<table width="950" border="0" cellpadding="5" cellspacing="1"
							class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label  for="Frequency"><strong>Frequency<font color="red">*</font></strong></label></td>
								<td width="30%">
											 <s:select cssClass="chosen-select"
												headerKey=""
												headerValue="Select"
												list="#respData.FREQ_DATA"
												name="frequency"
												id="frequency"
												requiredLabel="true"
												theme="simple"
												data-placeholder="Choose Frequency..."
												 />
								</td>
								<td width="20%"><label for="Networks"><strong>Networks<font color="red">*</font></strong></label></td>
								<td width="30%">
 									<input name="channel" class="chkChannel" type="radio" value="POS" id="channel-pos" /> Agent POS &nbsp;
						          <input name="channel" class="chkChannel" type="radio" value="CARD" id="channel-card" /> NBK CARD &nbsp;
						         <!--  <input name="channel" class="chkChannel" type="radio" value="ATM" id="channel-pos" /> ATM &nbsp;
						         <input name="channel" class="chkChannel" type="radio" value="WEB" id="channel-web" /> WEB &nbsp;-->
						         <input name="channel" class="chkChannel" type="radio" value="MOBILE" id="channel-mobile" /> iWallet &nbsp;
								</td>
							</tr>
							<tr class="odd">
								<td><label  for="Currency"><strong>Currency<font color="red">*</font></strong></strong></label></td>
								<td>
									<s:select cssClass="chosen-select"
										headerKey=""
										headerValue="Select"
										list="#respData.CRCY_CODE"
										name="currency"
 										id="currency"
										requiredLabel="true"
										theme="simple"
										data-placeholder="Choose Currency..."
									/>
									</td>
								<td><label  for="Condition"><strong>Condition<font color="red">*</font></strong></label></td>
								<td><select name="condition" id="condition"  	required='true' data-placeholder="Choose Condition..."
											class="chosen-select" style="width: 220px;">
											<option value="">Select</option>
											<option value="A">Amount</option>
											<option value="C">Count</option>
										</select>
								</td>
							</tr>
							<tr class="even" id="rng-chk" style="display:none">
								<td><label  for="From Amount"><strong>From <span class="rngdata"></span> <font color="red">*</font></strong></label></td>
								<td><input name="fromRange" id="fromRange" class="field" type="text"  required='true'/></td>
								<td><label  for="To Amount"><strong>To <span class="rngdata"></span> <font color="red">*</font> </strong></label></td>
								<td><input name="toRange" id="toRange" class="field" type="text"  required='true' /></td>
							</tr>
							<tr class="odd">
								<td ><label  for="Flat/Percentile"><strong>Flat/Percentile <font color="red">*</font></strong></label></td>
								<td ><select name="flatPercent" id="flatPercent" type="select"  required='true'
										data-placeholder="Choose Type..." 	class="chosen-select" style="width: 220px;">
										<option value="">Select</option>
										<option value="P">Percentile</option>
										<option value="F">Flat</option>
									</select></td>
								<td></td>
								<td></td>
							</tr>
							<tr class="even">


								<td ><label for="Amount"><strong>Bank Amount<font color="red">*</font></strong></label></td>
								<td ><input name="bankAmount"  type="text" id="bankAmount" class="field" value="<s:property value="#respData.bankAmount" />" ></td>
								<td ><label for="Amount"><strong>Agent Amount<font color="red">*</font></strong></label></td>
								<td ><input name="agentAmount"  type="text" id="agentAmount" class="field" value="<s:property value="#respData.agentAmount" />" >
								</td>
							</tr>
							<tr class="odd">

								<%-- <td>
									<input name="agentAccount"  type="text" id="agentAccount" class="field" value='<s:property value="#respData.merchantdata" />'   readonly />
								</td> --%>
								<%-- <td ><label for="Amount"><strong>Agent Amount<font color="red">*</font></strong></label></td>
								<td ><input name="agentAmount"  type="text" id="agentAmount" class="field" value="<s:property value="#respData.agentAmount" />" >
								</td> --%>

								<td ><label for="Service Tax"><strong>Service Tax<font color="red">*</font></strong></label></td>
								<td ><input name="serviceTax"  type="text"  style="width:50px;height:15px;" id="serviceTax" class="field"   value="<s:property value="#respData.serviceTax" />" ><span>%</span>
								</td>
								<td></td>	<td></td>
							</tr>

							<tr class="odd" align="center">
								<td colspan="4">
									<input type="button" class="btn btn-success" name="addFee" id="addFee" value="Add Fee Slabs" />
									<input type="button" class="btn btn-success" name="updateFee" id="updateFee" value="Update Fee Slabs" style="display:none" />
									&nbsp; <span id ="error_dlno1" class="errors"></span>
								</td>
							</tr>
					</table>
				</fieldset>
			</div>
			<div id="secondaryDetails-row" class="box-content" >
				<table id="mytable" width="950" border="0" cellpadding="5" cellspacing="1"
					class="table table-striped table-bordered bootstrap-datatable ">
					<thead>
						<tr>
 							<th>Sno</th>
 							<th>Frequency</th>
 							<!-- <th>Channels</th> -->
							<th>Currency</th>
							<th>Condition</th>
							<th>From Amount/Count</th>
							<th>To Amount/Count</th>
							<th>F/P</th>
						<!-- 	<th>Bank Account</th>  -->
							<th>Bank Amount</th>
						<!-- 	<th>Agent Account</th>  -->
							<th>Agent Amount</th>
							<!-- <th>Service Tax Account</th> -->
							<th>Service Tax</th>
							<th  width="17%">Actions</th>
						</tr>
					</thead>
					<tbody id="tbody_data1">
					</tbody>
				</table>
			</div>
			<span id="multi-row-data" class="multi-row-data" style="display:none"> </span>
		</form>
		</div>
	</div>

<form name="form4" id="form4" method="post" >
	<input name="serviceCode" type="hidden" id="serviceCode" class="field" value="<s:property value="#respData.serviceCode" />"   />
	<input name="serviceName" type="hidden"  id="serviceName" class="field"  value="<s:property value="#respData.serviceName" />"  />
	<input name="subServiceCode"  type="hidden" id="subServiceCode" class="field" value="<s:property value="#respData.subServiceCode" />"    />
	<input name="subServiceName"  type="hidden" id="subServiceName" class="field" value="<s:property value="#respData.subServiceName" />"    />
	<input name="feeCode" type="hidden" id="feeCode" class="field" value="<s:property value="#respData.feeCode" />"   />
	<input name="feename" type="hidden" id="feename" class="field" value=""   />
	<input name="merchantname" type="hidden" id="merchantname" class="field"   />
	<input name="offUsFee" type="hidden" id="offUsFee" class="field"   />
<!-- <input name="feename" type="hidden" id="feename" class="field"   />  -->


	<!--  Data Is For Multiple Data Display Settings  Start-->
	<input type="hidden" name="partnerDetails" id="partnerDetails" value="" />
	<input type="hidden" name="multiSlabFeeDetails" id="multiSlabFeeDetails" value="" />
	<input type="hidden" name="offusTrnDetails" id="offusTrnDetails" value="" />
	<!-- Data Is For Multiple Data Display Settings  End -->

	<input name="serviceTrans" type="hidden" value="" id="serviceTrans" />
	<input name="network" type="hidden" value="" id="network" />
	<input name="hudumaFlag" type="hidden" value="" id="hudumaFlag" />

	<input name="hudumaServiceName"  type="hidden" id="hudumaServiceName" class="field" value="<s:property value="#respData.hudumaServiceName" />" />
	<input name="hudumaSubServiceName"  type="hidden" id="hudumaSubServiceName" class="field" value="<s:property value="#respData.hudumaSubServiceName" />" />

</form>
	<div class="form-actions">
		<input type="button" class="btn" id="back-button" name="back-button" href="#"  value="Cancel" /> &nbsp;&nbsp;
		<input type="button" class="btn btn-success" id="submit-button" name="submit-button"  value="Submit" />&nbsp;&nbsp;
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
});

$(document).ready(function() {


	$("#merchantDetails").hide();

});
</script>
</body>
</html>
