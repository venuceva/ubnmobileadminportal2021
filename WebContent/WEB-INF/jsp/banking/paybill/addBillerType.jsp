
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.min.css' />
<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}

input#billerType{text-transform:uppercase};
input#billerId{text-transform:uppercase};
</style>

<script type="text/javascript">
var billerrules = {
		rules : {
			institute : { required : true },
			operator : { required : true },
			billerType : { required : true  },
			billerTypeDescription : { required : true },
 			fixedamountcheck : { required : true },
			amount : { required : true },
			bfubCreditAccount : { required : true, regexchk: /^([0-9]{14,14})$/i},
			billerIdPrefix : { required : true, regexchk: /^([0-9]{2,2})$/i }
		},
		messages : {
			institute : {
				required : "Please Select Channel."
		  	},
		 	 	operator : {
				required : "Please Select Biller Category."
			} ,
				billerType : {
					required : "Please Enter Biller Type."
			} ,
				billerTypeDescription : {
					required : "Please Enter Biller Type Description."
			}, amount : {
					required : "This Field Is Required."
			},  bfubCreditAccount : {
				regexchk :   "Only numeric of 14 digits allowed."
			},  billerIdPrefix : {
				regexchk :   "Only numeric of 2 digits allowed."
			}
		},
		errorPlacement: function(error, element) {
		    if ( element.is( ':radio' ) || element.is( ':checkbox' ) )
				error.appendTo( element.parent() );
		    else if ( element.is( ':password' ) )
				error.hide();
		    else
				error.insertAfter( element );
		}
	};


	var form2Rules = {
	     rules : {
	    	 billerId : {  required: true   },
	    	 billerIdDescription : {  required: true } ,
	    	 bfubCreditAcctBillerId : { required : false , notEqualTo: ['#bfubDebitAcctBillerId'], regexchk: /^([A-Z0-9]{14,14})$/i  },
	    	 bfubDebitAcctBillerId : { required : false , notEqualTo: ['#bfubCreditAcctBillerId'], regexchk: /^([A-Z0-9]{14,14})$/i  }
	    },
	    messages : {
	    	billerId : {   required: "Please Input Account Number."    },
	    	billerIdDescription : {   required: "Please Select Account Description." },
	    	bfubCreditAcctBillerId : {
				regexchk :   "Only alpha with caps and numeric of 14 characters allowed."
			}, bfubDebitAcctBillerId : {
				regexchk :   "Only alpha with caps and numeric of 14 characters allowed."
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


$(document).ready(function(){

	var fixOrPer = "<s:property value='fixedamountcheckval' />";
	 if(fixOrPer=='F'){
		 $("#fixedamountcheckval").val('F');
	 }else{
		 $("#fixedamountcheckval").val('P');
	 }

	$("#dialog").dialog({
	      autoOpen: false,
	      modal: true
     });

	$.validator.addMethod("regexchk", function(value, element, regexpr) {
		 return this.optional(element) || regexpr.test(value);
	   }, "");

	$('#institute').change(function(){
		var inst = $(this).val();
		//alert(inst);
		var queryString = "method=getInstCategories&institute="+ $('#institute').val();
		$.getJSON("paybillJsonCall.action", queryString,function(data) {
			var json = jQuery.parseJSON(data.selectBoxData);
			console.log(json.BILLER_CATEGORY_LIST);
			 var options = "";
			 $('#operator').empty();
			 $('#operator').append($('<option/>', {value: '', text: 'Select'}));
			$.each(json.BILLER_CATEGORY_LIST, function(i, v) {
			      options = $('<option/>', {value: v.key, text: v.value }).attr('data-id',i);
			    $('#operator').append(options);
			});
			$('#operator').trigger("liszt:updated");

		});

	});
	$('#btn-submit').on('click',function() {
		$('#billerMsg').text('');
		$('#error_dlno').text('');
		var finalData = "";
		$("#form1").validate(billerrules);
		console.log($("#form1").valid());
		if($("#form1").valid()) {
			$("#form1").validate().cancelSubmit = true;
			$("#operatorText").val($('#operator option:selected').text());
			$("#instituteText").val($('#institute option:selected').text())

			$('#form1').find('input, textarea, button, select').attr('disabled','disabled');
			$('#form1').find('.chosen-select').prop('readonly', true).trigger("liszt:updated");

			$('#cnf').show();
			$('#crt').hide();
<%--           $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/addBillerTypeConfirm.action"; --%>
// 		  $("#form1").submit();
// 			return true;
		} else {
			return false;
		}

	});

	$('#btn-cnf').live('click',function() {
		$('#form1').find('input, textarea, button, select').removeAttr('disabled');
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/addBillerTypeAck.action";
		$("#form1").submit();
	});
	$('#cnf-cancel').live('click',function() {
		$('#form1').find('input, textarea, button, select').removeAttr('disabled');
		$('#form1').find('.chosen-select').prop('readonly', false).trigger("liszt:updated");

		$('#cnf').hide();
		$('#crt').show();
	});

	$('#btn-Cancel').live('click',function() {
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/mpesaAccManagement.action";
		$("#form1").submit();
	});


    $("input[name=fixedamountcheck]:radio").change(function () {
    	var fixedamountcheck = $("input[name='fixedamountcheck']:checked").val();

    	if(fixedamountcheck == 'P'){
    		$('#fixed-amt-F').hide();
    		$('#fixed-amt-P').show();

    	}else{
    		$('#fixed-amt-P').hide();
    		$('#fixed-amt-F').show();
    	}
    	$('#fixedamountcheckval').val($("input[name='fixedamountcheck']:checked").val());
    });

    $("input[name=billeridcheck]:radio").change(function () {
    	$('#billerMsg').text('');
    	$('#error_dlno').text('');

    	clearVals();

         if($('#billerid-y').attr("checked")){
        	$('#billerid-div').show();
        	$('.acctbillertype').hide();
        	$('#bfubCreditAccount').val('');
        	$('#bfubDebitAccount').val('');
        	$('#biller-len-tr').show();
    	} else {
    		$('#billerid-div').hide();
    		$('.acctbillertype').show();
    		$('#biller-len-tr').hide();
     	}
    });

});

//For Closing Select Box Error Message_Start
$(document).on('change','select',function(event) {
	 if($('#'+$(this).attr('id')).next('label').text().length > 0) {
		  $('#'+$(this).attr('id')).next('label').text('');
		  $('#'+$(this).attr('id')).next('label').remove();
	 }

});
//For Closing Select Box Error Message_End
</SCRIPT>
<script type="text/javascript">
/** Form2 Add,Modify Starts*/
var val = 1;
var rowindex = 0;
var colindex = 0;
var userstatus = "";
var masterstatus = "";
var v_message = "";
var listid = "".split(",");
var headerList = "billerId,billerIdDescription,bfubCreditAcctBillerId,bfubDebitAcctBillerId".split(",");
var tabArry ;
var modTabArry ;
var modHeaderBodyArry ;

var index1 = "";
var searchTdRow = "";
var searchTrRows = "";
var selTextList = "";
var headerList = "billerId,billerIdDescription,bfubCreditAcctBillerId,bfubDebitAcctBillerId".split(",");
var rowCount = 0;
function clearVals(){
	$('#billerId').val('');
	$('#billerIdDescription').val('');
	$('#bfubCreditAcctBillerId').val('');
	$('#bfubDebitAcctBillerId').val('');


	rowCount = $('#tbody_data > tr').length;
	if(rowCount > 0 )  $("#error_dlno").text('');
}

function commonData(id,type){
	var hiddenInput ="";
	var hiddenNames = "";
	var tabarrindex = 0;
	tabArry = new Array();
	modTabArry = new Array();
	try {
 		$(id).find('input[type=text],input[type=hidden],select,textarea').each(function(indxf){
			var nameInput = "";
			var valToSpn =  "";
			try {
				  nameInput = $(this).attr('name');
				  valToSpn = ($(this).attr('value') == '' ? ' ' :  nameInput == 'billerId'? $(this).attr('value').toUpperCase() : $(this).attr('value'));
			} catch(e1) {
				console.log('The Exception Stack is :: '+ e1);
			}

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

var addbillerInfo = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	 var tabArrText = "";
	 var data1 = "";
	try {
		data1 = commonData('#biller-details','ADD');

		rowindex = $("#multi-row > span") .length ;
		$("#multi-row").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+tabArry[0]+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
		$('#multi-row > span#hidden_span_'+rowindex).append(data1.split("@@")[0]);

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
 				"<td><a class='btn btn-min btn-info' href='#' id='edit-biller' index='"+rowindex+"' title='Edit Biller' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-warning' href='#' id='row-cancel' index='"+rowindex+"' title='Reset Biller' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp; <a class='btn btn-min btn-danger' href='#' id='delete-biller' index='"+rowindex+"' title='Delete Biller' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";

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

		data1 = commonData('#biller-details','MODIFY');

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

function recordCheck(idtocheck,billerId,creditAcct,debitAcct){
	var check = false;
	var userIdRecords = new Array();
	var data = '';

	try {
		if($(idtocheck).length > 0) {
			// To Check The Record Exists In The Add-Row
			$(idtocheck+' > td:nth-child(2)').each(function(indx){
 				userIdRecords[indx]=$(this).text().trim();
			});

			if(jQuery.inArray(billerId.toUpperCase().trim(), userIdRecords) != -1){
				//check = true;
				data = 'Biller id exists in the below table, please input another.';
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
		 $('#add-biller').live('click', function(){
			$("#billerMsg").text('');
	 		$("#error_dlno").text('');
			$("#form2").validate(form2Rules);
			 var textMess = "#tbody_data > tr";
			 var flag = false;

 			 if($('#billerType').val() .length > 0 ) {
	 				if($("#form2").valid() ) {
						// Is To Check Account Exist's or not
						var dat1 = recordCheck(textMess,$('#billerId').val(),$('#bfubCreditAcctBillerId').val(),$('#bfubDebitAcctBillerId').val());

						if(dat1 == 'NO') {
							var queryString = "method=searchBfubAccounts&bfubCreditAccount="+ $('#bfubCreditAcctBillerId').val()+"&bfubDebitAccount="+ $('#bfubDebitAcctBillerId').val()+"&operator=NO&billerId="+$('#billerId').val()+"&type=BILLERID&billeridcheckval=N&billerType="+$('#billerType').val();
							$.getJSON("paybillJsonCall.action", queryString,function(data) {
								userstatus = data.status;
								v_message = data.message;
								masterstatus = data.masterStatus;
								if(v_message == "SUCCESS") {
									if(userstatus != null
										&& masterstatus != null ) {

										if("~NO~".indexOf(userstatus) != -1 && "~NO~".indexOf(masterstatus) != -1){
											flag = true;
										} else {
											if(userstatus == 'BIWBTFOUND' && masterstatus == '~NO~'){
												$('#error_dlno').text('Biller Id Is Already Associated With Biller Type.');
											} else if(userstatus == 'MDCRNOTFOUND' && masterstatus == 'MDDRNOTFOUND'){
												$('#error_dlno').text('Both Credit Account && Debit Account Of Biller Id Not Found In Master Table.');
											} else if(userstatus == 'MDCRNOTFOUND'){
												$('#error_dlno').text('Credit Account Of Biller Id Not Found In Master Table.');
											} else if(masterstatus == 'MDDRNOTFOUND'){
												$('#error_dlno').text('Debit Account Of Biller Id Not Found In Master Table.');
											} if(userstatus == 'BTCRFOUND'){
												$('#error_dlno').text('Biller Id is already associated with the "Credit Account", please input another.');
											}

										}

											if(flag) {
												$('#billerId').val($('#billerId').val().toUpperCase());

												var appendTxt = addbillerInfo("ADD");
						 						$("#tbody_data").append(appendTxt);
												clearVals();
												alignSerialNo(textMess);
												$("#bankAcctMsg").text('');
												$('#error_dlno').text('');
												loadToolTip();
											}

									} else {
										 $('#billerMsg').text('');
										 $('#error_dlno').text('');
								}

							} else {
								$('#billerMsg').text(v_message);
							}

							});

						  } else {

								$("#error_dlno").text('');
								$('#billerMsg').text(dat1);
						 }


				 } else {
					 return false;
				 }
 			 } else {
 				$('#error_dlno').text('Please Input Biller Type Under Biller Type Information.');
			 }

		});


		// Update Edited-Row Starts Here.
		$('#mod-biller').live('click', function () {
			$("#error_dlno").text('');
			$("#form2").validate(form2Rules);
			 var textMess = "#tbody_data > tr";
			var flag = false;
			if($("#form2").valid()) {
				// Is To Check Account Exist's or not
				var dat1 = recordCheck(textMess,$('#billerId').val(),$('#bfubCreditAcctBillerId').val(),$('#bfubDebitAcctBillerId').val());

				if(dat1 == 'NO') {
					var queryString = "method=searchBfubAccounts&bfubCreditAccount="+ $('#bfubCreditAcctBillerId').val()+"&bfubDebitAccount="+ $('#bfubDebitAcctBillerId').val()+"&operator=NO&billerId="+$('#billerId').val()+"&type=BILLERID&billeridcheckval=N&billerType="+$('#billerType').val();
					$.getJSON("paybillJsonCall.action", queryString,function(data) {
						userstatus = data.status;
						v_message = data.message;
						masterstatus = data.masterStatus;

							if(v_message == "SUCCESS") {
								if(userstatus != null
									&& masterstatus != null ) {

										if("~NO~".indexOf(userstatus) != -1 && "~NO~".indexOf(masterstatus) != -1){
											flag = true;
										} else {
											if(userstatus == 'BIWBTFOUND' && masterstatus == '~NO~'){
												$('#error_dlno').text('Biller Id Is Already Associated With Biller Type.');
											} else if(userstatus == 'MDCRNOTFOUND' && masterstatus == 'MDDRNOTFOUND'){
												$('#error_dlno').text('Both Credit Account && Debit Account Of Biller Id Not Found In Master Table.');
											} else if(userstatus == 'MDCRNOTFOUND'){
												$('#error_dlno').text('Credit Account Of Biller Id Not Found In Master Table.');
											} else if(masterstatus == 'MDDRNOTFOUND'){
												$('#error_dlno').text('Debit Account Of Biller Id Not Found In Master Table.');
											} if(userstatus == 'BTCRFOUND'){
												$('#error_dlno').text('Biller Id is already associated with the "Credit Account", please input another.');
											}
										}
										if(flag) {
											$('#billerId').val($('#billerId').val().toUpperCase());
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

								 			$("#multi-row span").each(function(index,value) {
								 				 if($(this).attr("index") == index1 ) {
													 $(this).attr("hid-names",modHeaderBodyArry[0]);
													 $(this).text(modHeaderBodyArry[1]);
													 return;
												 }
											});

											clearVals();
											alignSerialNo("#tbody_data > tr");

											//Hide Update Button and Display Add Button
											$('#mod-biller').hide();
											$('#add-biller').show();

										} else {
											 $('#billerMsg').text('');
											 $('#error_dlno').text('');
										}
									}
							} else {
								$('#billerMsg').text(v_message);
							}
					});
				} else {

					$("#error_dlno").text('');
					$('#billerMsg').text(dat1);
			 	}
			} else {
				return false;
			}
		});

		// The below event is to Edit row on selecting the delete button
		$('#edit-biller').live('click',function() {
			$("#form2").validate().cancelSubmit = true;

			index1 = $(this).attr('index');
			var parentId =$(this).parent().closest('tbody').attr('id');
			searchTrRows = parentId+" tr";
			searchTdRow = '#'+searchTrRows+"#tr-"+index1 +' > td';

			var idSearch = "";
			var hidnames = "";
			var text_val = "";

			var spanData = $("#multi-row > span#hidden_span_"+index1);
			hidnames =  $("#multi-row > span#hidden_span_"+index1).attr('hid-names');
			text_val =  $("#multi-row > span#hidden_span_"+index1).text();

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
					$("#"+value).val(textarr[index] == undefined ? " " : textarr[index] .trim());
				}
			});

			//Hide Add Button and Display Update Button
			$('#mod-biller').show();
			$('#add-biller').hide();

		});

		// The below event is to delete the entire row on selecting the delete button
		$("#delete-biller").live('click',function() {
			var delId = $(this).attr('index');
			$(this).parent().parent().remove();

			var spanId = "";
			$('#multi-row > span').each(function(index){
				spanId =  $(this).attr('index');
				if(spanId == delId) {
					$(this).remove();
				}
			});

			clearVals();
			// Aligning the serial number
			alignSerialNo("#tbody_data > tr");

			//Show Add Button and Hide Update Button
			$('#mod-biller').hide();
			$('#add-biller').show();
			$('.tooltip').remove();
			$('#billerMsg').text('');
			//$('#error_dlno').text('');
		});

		// Clear Form Records Row Starts Here.
		$('#row-cancel').live('click', function () {
			$("#error_dlno").text('');
			 clearVals();

			//Show Add Button and Hide Update Button
			$('#mod-biller').hide();
			$('#add-biller').show();
		});


		// To do regular expression

		//([a-zA-Z0-9_-]*)
		$('#billerType').live('focus',function (e) {
 			$('#regex').val(regPrepare($(this).val().toUpperCase()));
		}).live('blur',function(e) {
			$('#regex').val(regPrepare($(this).val().toUpperCase()));
		});

});

function regPrepare(val){
	var str=")([a-zA-Z0-9_-]*)";
	var str1="(";

	return str1+val+str;
}

/** Form2 Add Row Validation Ends*/
</script>
</head>
<body>
	  <div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="mpesaAccManagement.action">Biller Management</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Add Biller Type</a></li>
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
	<form name="form1" id="form1" method="post" autocomplete="off" style="margin: 0px 0px 50px">
		<div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Biller Information
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>

				<div class="box-content">
					<fieldset>
					<table width="98%"  border="0" cellpadding="5" cellspacing="1"
						class="table table-striped table-bordered bootstrap-datatable " >

							<tr >
								<td width="20%"><label for="Select Institute"><strong>Select Channel<font color="red">*</font></strong></label></td>
								<td width="30%" colspan=3> <s:select cssClass="chosen-select"
										headerKey=""
										headerValue="Select"
										list="responseJSON.institutesel"
										name="institute"
	 									id="institute"
	 									value="institute"
										requiredLabel="true"
										theme="simple"
										data-placeholder="Choose Institute..."
	 									 />  &nbsp; <label id="institute-id" class="errors" ></label>
	 							</td>
							</tr>
							<tr >
								<td><label for="Select Operator"><strong>Select Biller Category<font color="red">*</font></strong></label></td>
								<td> <s:select cssClass="chosen-select"
										headerKey=""
										headerValue="Select"
										list="#{ }"
										name="operator"
	 									id="operator"
	 									value="operator"
										requiredLabel="true"
										theme="simple"
										data-placeholder="Choose Biller Category..."
	 									 />  &nbsp; <label id="operator-id" class="errors" ></label>
	 							</td>
							</tr>
							<tr >
								<td><label for="Biller Type Description"><strong>Biller Type Description<font color="red">*</font></strong></label></td>
								<td><textarea name="billerTypeDescription"  id="billerTypeDescription"   required=true style="height: 69px; width: 453px;" ><s:property value='billerTypeDescription' /></textarea> </td>
							</tr>
							<tr >
								<td><label for="Transaction Fee"><strong> Type Of Transaction Fee <font color="red">*</font></strong></label></td>
								<td><input type="radio" id="fixedamountcheck" name="fixedamountcheck" value="F" > Fixed &nbsp;
									<input type="radio" id="fixedamountcheck" name="fixedamountcheck" value="P" checked> Percent
									<input type="hidden" name="fixedamountcheckval"  id="fixedamountcheckval" value="<s:property value='fixedamountcheckval' />"    />
									<input type="hidden" name="operatorText" id="operatorText"/>
									<input type="hidden" name="instituteText" id="instituteText"/>
								</td>
							</tr>
							<tr>
								<td ><label for="Fixed Amount" id="fixed-amt-F" style="display:none;"><strong>Fixed Amount<font color="red">*</font></strong></label>
									<label for="Percent" id="fixed-amt-P"><strong>Percent<font color="red">*</font></strong></label>
								</td>
								<td> <input type="text" name="amount"  id="amount" value="<s:property value='amount' />" required=true   />   </td>
							</tr>

							<tr class="acctbillertype">
								<%-- <td><label for="Credit Account"><strong>Credit Account</strong></label></td> --%>
								<td><label for="Credit Account"><strong>Account Number<font color="red">*</font></strong></strong></label></td>
								<td><input type="text" name="bfubCreditAccount"  id="bfubCreditAccount" value="<s:property value='bfubCreditAccount' />"   />
									<input type="hidden" name="multiData"  id="multiData"   value="<s:property value='multiData' />"  />
									<input type="hidden" name="selectBoxData"  id="selectBoxData"   value="<s:property value='selectBoxData' />"  />
								</td>
							</tr>
							<tr class="acctbillertype">
								<td><label for="Debit Account"><strong>Biller Id Prefix <font color="red">*</font></strong></strong></label></td>
								<td><input type="text" name="billerIdPrefix"  id="billerIdPrefix" value="<s:property value='billerIdPrefix' />"   />
								</td>
							</tr>
					</table>
				</fieldset>
			</div>
		</div>
		</div>
	</form>

	<form name="form2" id="form2" method="post" autocomplete="off" style="margin: 0px 0px 50px">
		<div class="row-fluid sortable" id="billerid-div">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Biller Id Details
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>

				<div class="box-content" id="biller-details">
					<fieldset>
						<table width="98%"  border="0" cellpadding="5" cellspacing="1"
							class="table table-striped table-bordered bootstrap-datatable " >
							 <tr>
								<td width="20%"><label for="Biller Id"><strong>Biller Id<font color="red">*</font></strong></label></td>
								<td width="30%"><input type="text" name="billerId"  id="billerId" required=true  value="<s:property value='billerId' />"   />   </td>
								<td width="20%"> <label for="Biller Id Description"><strong>Biller Id Description<font color="red">*</font></strong></label></td>
								<td width="30%"><textarea name="billerIdDescription"  id="billerIdDescription"  required=true style="height: 69px; width: 353px;"><s:property value='billerIdDescription' /></textarea></td>
							</tr>
							<tr >
								<td><label for="Credit Account"><strong>Credit Account</strong></label></td>
								<td><input type="text" name="bfubCreditAcctBillerId"  id="bfubCreditAcctBillerId" value="<s:property value='bfubCreditAcctBillerId' />"   />  </td>
								<td><label for="Debit Account"><strong>Debit Accounts</strong></label></td>
								<td><input type="text" name="bfubDebitAcctBillerId"  id="bfubDebitAcctBillerId" value="<s:property value='bfubDebitAcctBillerId' />"   />  </td>
							</tr>
							<tr class="odd">
								<td colspan="4" align="center">
									<input type="button" class="btn btn-success"
										name="add-biller" id="add-biller" value="Add Biller Id" /> &nbsp;
								 	<input type="button" class="btn btn-success"
										name="mod-biller" id="mod-biller" value="Update Biller Id" style="display:none"/> &nbsp;
									<span id="billerMsg" class="errors"></span>
								</td>
							</tr>
					</table>
				</fieldset>
				<span id="multi-row" name="multi-row" style="display:none"> </span>
					<table width="100%" class="table table-striped table-bordered bootstrap-datatable "
						id="documentTable" style="width: 100%;">
							  <thead>
									<tr>
										<th>Sno</th>
										<th>Biller ID</th>
										<th>Biller ID Description</th>
										<th>Credit Account</th>
										<th>Debit Account</th>
										<th>Action</th>
									</tr>
							  </thead>
							 <tbody id="tbody_data">
							 	<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
									<s:param name="jsonData" value="%{multiData}"/>
									<s:param name="searchData" value="%{'#'}"/>
								</s:bean>
								<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" >
											<script type="text/javascript">
												var hiddenNames1 = "";
												$(function(){
													$('#biller-details').find('input[type=text],input[type=hidden],select,textarea').each(function(index){
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
													$("#multi-row").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
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
										<td><a class='btn btn-min btn-info' href='#' id='edit-biller' index='<s:property value="#mulDataStatus.index" />' title='Edit Biller' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;
											<a class='btn btn-min btn-warning' href='#' id='row-cancel' index='<s:property value="#mulDataStatus.index" />' title='Reset Biller' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp;
											<a class='btn btn-min btn-danger' href='#' id='delete-biller' index='<s:property value="#mulDataStatus.index" />' title='Delete Biller' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a>
										</td>
									</tr>
								</s:iterator>
							 </tbody>
						</table>
			</div>
		</div>
		</div>

		<div id="dialog" title="Confirmation Required" style="display:none">
		   Proceed ?
		</div>

	 </form>
   		<div class="form-actions" id="crt">
         <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100" ></input>&nbsp;
         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input>&nbsp;
         <span id ="error_dlno" class="errors"></span>
       </div>
	<div class="form-actions" id="cnf" style="display:none;">
	         <input type="button" class="btn btn-success"  name="btn-cnf" id="btn-cnf" value="Confirm" width="100" ></input>&nbsp;
	         &nbsp;<input type="button" class="btn btn-info" name="cnf-cancel" id="cnf-cancel" value="Back" width="100" ></input>
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
    };

    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }

    if($('#fixedamountcheckval').val() == 'Y'){
    	$('#fixed-amt-y').attr('checked','');
    	$('#fixed-amt-n').removeAttr('checked');
    	$('#fixed-amt-tr').show();
	} else {
		$('#fixed-amt-n').attr('checked','');
		$('#fixed-amt-y').removeAttr('checked');
		$('#fixed-amt-tr').hide();
	}
    clearVals();

    if($('#billeridcheckval').val() == 'Y' ){
    	$('#billerid-div').show();

    	$('.acctbillertype').hide();
    	$('#bfubCreditAccount').val('');
    	$('#bfubDebitAccount').val('');
    	$('#biller-len-tr').show();

    	$('#billerid-y').attr("checked","checked");
    	$('#billerid-n').removeAttr("checked");
	} else {

		$('#billerid-div').hide();
		$('.acctbillertype').show();
		$('#biller-len-tr').hide();

		$('#billerid-y').removeAttr("checked");
		$('#billerid-n').attr("checked","checked");
	}
});
</script>
</body>
</html>