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
<script type="text/javascript" >
var merchantCreateRules= {
	rules : { 
		managerName : {required : true,regex: /^[a-zA-Z0-9\/\ ]+$/},
		addressLine1 : {required : true},
		addressLine2 : {required : true},
		city		: {required : true},
		poBoxNumber : {required : true},
		//telephoneNumber1 : {required : true},
		mobileNumber : {required : true , number : true},
		//faxNumber : {required : true , number : true},
		prmContactPerson : {required : true},
		prmContactNumber : {required : true , number : true},
		email : {required : true, email : true}, 
		postalCode : {required : true, number : true},
		area : {required : true},
		country : {required : true},
		lrNumber : { required : true, regex: /^[a-zA-Z0-9\/\ ]+$/ }
  	},
	messages : { 
		managerName : { required : "Please Enter Manager Name.",
									regex   :  "Please Enter Valid Manager name"
			},
		addressLine1 : { required : "Please Enter Address Line1."},
		addressLine2 : { required : "Please Enter Address Line2."},
		city		: { required : "Please Enter City/Town."},
		poBoxNumber : { required : "Please Enter P.O. Box Number."},
		//telephoneNumber1 : { required : "Please Enter Merchant Name."},
		mobileNumber : {required : "Please Enter Mobile Number."},
		//faxNumber : { required : "Please Enter Merchant Name."},
		prmContactPerson : { required : "Please Enter Primary Contact Person."},
		prmContactNumber : { required : "Please Enter Primary Contact Number."},
		country : {required : "Please Select Country"},
		email : { required : "Please Enter Email."}, 
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
		} 
	}
};


$(document).ready(function() {  
	var county='${responseJSON.AREA}';
	$("#area").append('${responseJSON.AREA}');
	$("#MerchantAdminDetails").hide();
	
	var data = jQuery.parseJSON('${responseJSON.storeData}');
	var storeData = '';
	  $.each(data, function(key,value) {
		storeData = value.storeId+','+value.storeName+','+value.status+','+value.makerId+','+value.makerDate+','+value.authid+','+value.authdttm+'#';
		}); 
	$('#storeData').val(storeData);
    $.validator.addMethod("regex", function(value, element, regexpr) {          
		 return regexpr.test(value);
   }, "");  
}); 
 

function modifyMerchant(){ 
	$("#form1").validate(merchantCreateRules);
	var finalData = ""; 
	$('#multi-row-bank > span').each(function(index) {  
		//console.log("index ["+$(this).attr('index')+"] name ["+$(this).attr('name')+"]  value["+$(this).val()+"] "); 
		if(index == 0)  finalData = $(this).text();
		else finalData += "#"+ $(this).text();
	}); 
	$('#bankAcctMultiData').val(finalData);
	finalData = ""; 
	$('#multi-row-agent > span').each(function(index) {  
				//console.log("index ["+$(this).attr('index')+"] name ["+$(this).attr('name')+"]  value["+$(this).val()+"] "); 
		if(index == 0)  finalData = $(this).text();
		else finalData += "#"+ $(this).text();
	});

	if($("#form1").valid() && $("#form1").manualValidation()){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantmodifySubmitAct.action';
		$("#form1").submit();
		return true;
	}else{
		return false;
	}
}

function getGenerateScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateMerchantAct.action';
	$("#form1").submit();
	return true;
}
</script>

<script type="text/javascript" >


$(document).ready(function() {  
	$('#form4').hide();
	
	$('#merchantadminDetails').hide();
	
	$('#bankAgenctNo').val((Math.random() * 100000).toString().substring(0,7).replace(".",""));
	
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


var form2Rules = {
     rules : { 
		accountNumber : {  required: true   },
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

</script>

<script type="text/javascript"> 

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

/** Form2 Add Row Validation Starts*/
	// Add Row Starts Here.
	$('#addCapBank').live('click', function(){
		$("#bankAcctMsg").text('');
 		$("#error_dlno").text('');	 
		$("#form2").validate(form2Rules); 
		 var textMess = "#tbody_data1 > tr";  
		  
		 //console.log("Inside bank Adding....");
	
		//if($("#form2").valid()) {  
			// Is To Check Account Exist's or not 
			if(recordCheck(textMess,$('#accountNumber').val())) { 
				var appendTxt = addBankInfo("ADD");
				//console.log("appendTxt ===> "+ appendTxt);
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
			
		/* } else {
			// This is to throw error nessage.
			$('#error_dlno').text('');
			//$('#error_dlno').text('The User With "'+$('#userId option:selected').val()+'" Exists.');
			return false;
		}  */
	 
	});   
	
	// Update Edited-Row Starts Here.
	$('#modCapBank').live('click', function () {
	
		$("#error_dlno").text('');	 
		$("#form2").validate(form2Rules); 

		/* if($("#form2").valid()) { */
		
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
			
	/* 	} else {
			return false;				
		}  */
		
	}); 
	
	// The below event is to Edit row on selecting the delete button 
	$('#editDatBank').live('click',function() { 
		//$("#form2").validate().cancelSubmit = true;
		
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
		 //$('#acc-check').is(":checked")
		 
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
				$("#"+value).val(textarr[index].trim()); 
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

});

/** Form2 Add Row Validation Ends*/
  

function getLocation(){
	var data=$( "#location option:selected" ).text();
	$("#locationVal").val(data);
}
 
</script>

<script type="text/javascript">
$(function(){ 

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
	//$('.checkclass').trigger("click"); 
	$('#bankinfospn1').hide();  
});	
 
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
</style>	 
</head>
<s:set value="responseJSON" var="respData"/>	 
<body>
	<form name="form1" id="form1" method="post" action="" > 
		
			<div id="content" class="span10"> 
			    <div>
						<ul class="breadcrumb">
						  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
						  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
						  <li><a href="#">Merchant Modify</a></li>
						</ul>
				</div>
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
										<td width="20%"><strong><label for="Merchant Name"><strong>Merchant Name</strong></label></td>
										<td width="30%"> <s:property value='#respData.merchantName' /> 
										<input name="merchantName" type="hidden"  id="merchantName" class="field"  value="<s:property value='#respData.merchantName' />" > </td>
										<td width="20%"><strong><label for="Merchant ID"><strong>Merchant ID</strong></label></td>
										<td width="30%"> <s:property value='#respData.merchantID' /> <input name="merchantID" type="hidden" id="merchantID" class="field" value="<s:property value='#respData.merchantID' />" ></td>
									</tr>
									<tr class="odd">
										<td ><label  for="Bank Branch"><strong>Bank Branch</strong></label></td>
										<td >  <s:property value='#respData.locationName' />
											<input name="locationcity" type="hidden" class="field" id="locationcity" value="<s:property value='#respData.locationName' />"  />
										</td>
										<td ><label  for="KRA PIN"><strong>KRA PIN</strong></label></td>
										<td > <s:property value='#respData.KRA_PIN' />
										<input name="kraPin" type="hidden" class="field" id="kraPin" value="<s:property value='#respData.KRA_PIN' />"  />
										</td>	
									</tr> 
									<tr class="even">
										<td ><label  for="Merchant Type"><strong>Merchant Type</strong></label></td>
										<td > <s:property value='#respData.MERCHANT_TYPE' /> 
											  <input name="merchantType" type="hidden" class="field" id="merchantType" value="<s:property value='#respData.MERCHANT_TYPE' />"  />
										</td>
										<td></td>
										<td></td>
										<%-- <td ><label for="Member Type"><strong>Member Type</strong></label></td>
										<td >
											<s:property value='#respData.MEMBER_TYPE' /> 
											<input name="memberType" type="hidden" class="field" id="memberType" value="<s:property value='#respData.MEMBER_TYPE' />"  />
										</td>	 --%>
									</tr>
								</table>
						 </fieldset> 
					</div>
					 
				</div> 
				</div>
			<div class="row-fluid sortable" id="MerchantAdminDetails">					
					<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Merchant Admin Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						</div>
					</div>  
						
					<div class="box-content" id="merchantAdminDetails"> 
						 <fieldset>   
								<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="even">
										<td width="20%"><label for="Merchant Admin Id"><strong>Merchant Admin Id</strong></label></td>
										<td width="30%"><s:property value='#respData.merchantAdmin' /> 
											 <input name="merchantAdmin" type="hidden" class="field" id="merchantAdmin" value="<s:property value='#respData.merchantAdmin' />"  />
										</td>
										<td width="20%"></td>
										<td width="30%"></td>
									</tr>
									<tr class="even" id="userData1">
										<td><label for="User Name"><strong>User Name</strong></label></td>
										<td> <s:property value='#respData.userName' /> 
											<input name="userName" type="hidden"   id="userName" value="<s:property value='#respData.userName' />"  />
										</td>
										<td><label for="User Status"><strong>User Status</strong></label></td>
										<td> <s:property value='#respData.userStatus' />
											<input name="userStatus" type="hidden"   id="userStatus" value="<s:property value='#respData.userStatus' />"  />
										</td>
									</tr>
									<tr class="odd" id="userData2">
										<td><label for="Employee No"><strong>Employee No</strong></label></td>
										<td><s:property value='#respData.employeeNo' />
											<input name="employeeNo" type="hidden"   id="employeeNo" value="<s:property value='#respData.employeeNo' />"  />
										</td>
										<td><label for="Email Id"><strong>Email Id</strong></label></td>
										<td><s:property value='#respData.emailId' />
											<input name="emailId" type="hidden"  id="emailId" value="<s:property value='#respData.emailId' />"  />
										</td>
									</tr>
								</table> 
							 </fieldset> 
						</div> 
					</div> 
				</div>
				<div class="row-fluid sortable">
						<div class="box span12">
								<div class="box-header well" data-original-title>Store Information
									<div class="box-icon"> 
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
										<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
									</div>
								</div> 
								<div class="box-content" > 
									<table style = 'border: 1px solid #d7d7d7; font-family: Arial, Helvetica, sans-serif;font-size: 12px; color: #000000; font-weight: normal;' width='100%'   
										class='table table-striped table-bordered bootstrap-datatable datatable'  >
										<thead>
											<tr>
												<th>S No</th>
												<th>Store ID</th>
												<th class='hidden-phone'>Store Name </th>
												<th >Store Created By</th>
												<th class='hidden-phone'>Store Creation Date</th>
												<th >Store Authorized By</th>
												<th class='hidden-phone'>Store Authorized Date</th>
 												<th>Status </th> 
 											</tr>
										</thead> 
										<tbody id="StoreDetails" > 		
											<s:set value="responseJSON['storeData']" var="storeInfoForMer"/>
											<s:iterator value="#storeInfoForMer" var="userGroups" status="userStatus"> 
												 <s:if test="#userStatus.even == true" > 
													<tr class="even" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
												 </s:if>
												 <s:elseif test="#userStatus.odd == true">
													<tr class="odd" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
												 </s:elseif> 
														<td><s:property value="#userStatus.index+1" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['storeId']" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['storeName']" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['makerId']" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['makerDate']" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['authid']" /></td> 
														<td class='hidden-phone'><s:property value="#userGroups['authdttm']" /></td> 
															<s:if test="#userGroups['status'] == 'Active'" > 
																 <s:set value="%{'label-success'}" var="merchStatus" /> 
															</s:if>
															<s:elseif test="#userGroups['status'] == 'Inactive'" >
																  <s:set value="%{'label-warning'}" var="merchStatus" /> 
															 </s:elseif>
															 <s:elseif test="#userGroups['status'] == 'Un-Authorize'" >
																  <s:set value="%{'label-primary'}" var="merchStatus" /> 
															 </s:elseif> 
														<td class='hidden-phone'><a href='#' class='label <s:property value="#merchStatus" />' ><s:property value="#userGroups['status']" /> </a></td> 
											</s:iterator>   
										 </tbody>
									</table>
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
											<tr class="even">
												<td width="20%"><label for="Manager Name"><strong>Manager Name<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="managerName" type="text" id="managerName" class="field" value="<s:property value="#respData.managerName"/>" maxlength="50" required='true' ></td>
												<td width="20%"><label for="Email"><strong>Email<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="email" type="text"  id="email" class="field"  value="<s:property value="#respData.email"/>" required='true' > </td>
											</tr>
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
												<%--  <select id="country" name="country"   required='true' data-placeholder="Choose country..."
													class="chosen-select" style="width: 220px;">
													<option value="">Select</option>
													<option value="Kenya">Kenya</option>
													<option value="Uganda">Uganda</option>
												</select> --%>
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
												<td >
												     <%-- <select id="area" name="area"   required='true' data-placeholder="Choose country..."
													 class="chosen-select" style="width: 220px;">
													 	<option value="">select</option>
													 	<option value="01-Mombasa">01-Mombasa  </option>
													 	<option value="01-Diani">01-Diani    </option>
													 	<option value="02-Malindi">02-Malindi  </option>
														<option value="02-Watamu">02-Watamu   </option>
													 	<option value="03-Kilifi">03-Kilifi   </option>
													 	<option value="27-Eldoret">27-Eldoret  </option>
													 	<option value="32-Nakuru">32-Nakuru   </option>
													 	<option value="47-Nairobi">47-Nairobi  </option>
														<option value="47-Thika">47-Thika    </option>
													 </select>  --%>
													 <s:select cssClass="chosen-select"
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
												<td ><input name="postalCode" type="text" id="postalCode" class="field" value="<s:property value="#respData.POSTALCODE"/>"  required='true'/> <span id="postalCode_err" class="errmsg"></span></td>
												<td ><label for="P.O. Box Number "><strong>P.O. Box Number<font color="red">*</font></strong></label></td>
												<td ><input name="poBoxNumber" type="text" id="poBoxNumber" class="field" value="<s:property value="#respData.poBoxNumber"/>" required='true' /></td>
										   </tr>
										   <tr class="odd">
										   		<td ><label for="Telephone Number 1"><strong>Telephone Number 1</strong></label></td>
												<td><input name="telephoneNumber1" type="text"  customvalidation="max::10||min::10" id="telephoneNumber1"  maxlength="10" class="field" value="<s:property value='#respData.telephoneNumber1' />" /><span id="telephoneNumber1_err" class="errmsg"></span></td>
												<td ><label for="Telephone Number 2 "><strong>Telephone Number 2</strong></label>	</td>
												<td ><input name="telephoneNumber2" customvalidation="max::10||min::10" type="text" class="field" id="telephoneNumber2"  maxlength="10" value="<s:property value='#respData.telephoneNumber2' />" /><span id="telephoneNumber2_err" class="errmsg"></span></td>
										   </tr>
											<%-- <td ><label for="Mobile Number"><strong>Mobile Number<font color="red">*</font></strong></label></td>
											<td ><input name="mobileNumber" id="mobileNumber" customvalidation="max::10||min::10" class="field" type="text"  maxlength="10"  value="<s:property value='#respData.mobileNumber' />" required='true' /><span id="mobileNumber_err" class="errmsg" ></span></td> --%>
										<tr class="even">	
											<td ><label for="Fax Number"><strong>Fax Number </strong></label></td>
											<td ><input name="faxNumber"  type="text" class="field" id="faxNumber" value="<s:property value="#respData.faxNumber"/>" /><span id="faxNumber_err" class="errmsg"></span></td>
											<td><label for="L/R Number"><strong>L/R Number<font color="red">*</font></strong></label></td>
											<td ><input name="lrNumber" type="text" class="field" id="lrNumber" value="<s:property value="#respData.LRNUMBER"/>" required='true' /></td>
										</tr>
										<tr class="odd">
											<td><label for="Google Co-Ordinates"><strong>Google Co-Ordinates<font color="red">*</font></strong></label></td>
											<td><input name="googleCoOrdinates" type="text" class="field" id="googleCoOrdinates" value="<s:property value="#respData.googleCoOrdinates"/>" required='true' /></td>
											<td><label for="Latitude"><strong>Latitude<font color="red">*</font></strong></label></td>
											<td><input name="latitude" type="text" class="field" id="latitude" value="<s:property value="#respData.latitude"/>" required='true' /></td>
										</tr>
										<tr class = "even">
											<td><label for="Longitude"><strong>Longitude<font color="red">*</font></strong></label></td>
											<td><input name="longitude" type="text" class="field" id="longitude" value="<s:property value="#respData.longitude"/>" required='true' /></td>
											<td></td>
											<td></td>
										</tr>
									</table>
								</fieldset>
							</div>
						</div> 
				</div> 
				
				<%-- <input type="hidden" name="bankAcctMultiData" id="bankAcctMultiData" value="<s:property value="#respData.bankAcctMultiData"/>" /> --%>
			 
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
										        <td width="30%"><input name="accountNumber"  maxlength ='14' id="accountNumber" class="field" type="text"    class="field" value="<s:property value="#respData.accountNumber"/>" maxlength="50" required='true' ></td>
												<td width="20%"><label for="RelationShip Manager Number"><strong>RelationShip Manager Number<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="relationShipManagerNumber" type="text" id="relationShipManagerNumber" class="field" value="<s:property value="#respData.relationShipManagerNumber"/>"  required='true' ></td>
											</tr>
											<tr>
												<td width="20%"><label for="RelationShip Manager Name"><strong>RelationShip Manager Name<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="relationShipManagerName" type="text" id="relationShipManagerName" class="field" value="<s:property value="#respData.relationShipManagerName"/>"   required='true' ></td>
												<td width="20%"><label for="RelationShip Manager Email"><strong>RelationShip Manager Email<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="relationShipManagerEmail" type="text" id="relationShipManagerEmail" class="field" value="<s:property value="#respData.relationShipManagerEmail"/>"  required='true' ></td>
											</tr>
									</table>
								</fieldset>
							</div>
						</div>
				</div>
				
				
				
				<input type="hidden" name="storeData" id="storeData"/>
				<div class="form-actions">
					<a  class="btn btn-danger" href="generateMerchantAct.action" onClick="getGenerateScreen()">Back</a>&nbsp;
					<a  class="btn btn-success" href="#" onClick="modifyMerchant()">Submit</a>  
				</div> 
</div> 
</form>
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
	$('#country').val('<s:property value="#respData.COUNTRY" />');
	$('#country').trigger("liszt:updated"); 
	
	$("#area > option").each(function() {
		var county =this.text;
		var area = county.split('-')[1];
		if('<s:property value="#respData.AREA" />' == area){
			$("#area").val(this.value);
			$('#area').trigger("liszt:updated"); 
		}
	});
});
</script>
</body>
</html>
