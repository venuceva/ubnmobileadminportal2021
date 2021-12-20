<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>NBK</title>
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
  
var form1Rules = {
    rules : {
		network : { required : true },
		serviceTrans : { required : true },
		hdmservice: {
				required: {
						depends: function(element) {
							if ($('#Huduma').is(":checked")){
								return true;
							}  else {
								return false;
						   }
						}
					 }
		},
		hudumaSubService : {
			required: {
						depends: function(element) {
							if ($('#Huduma').is(":checked")){
								return true;
							}  else {
								return false;
						   }
						}
					 }
		
		}
    },  
    messages : {
		network : { 
			required : "Please Select Network."
			 }, 
		serviceTrans : { 
			required : "Please Select On-Us/Off-Us Flag."
			 },
		hdmservice : { 
			required : "Please Select Huduma Service."
			 },
		hudumaSubService : {
			required : "Please Select Huduma Sub-Service."
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


var form2Rules = {
     rules : { 
			frequency : {  required: true   },
			channel : {  required: true,  maxlength : 5 },
			currency : { required: true    },
			condition : {   required: true   },
			fromCount : {	
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
			toCount : {
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
			fromAmount : {	 
						required: {
							depends: function(element) {
							if ($("#condition").val() == 'A'){
								return true;
							}  else {
								return false;
						   }
						}
					 },
					 number : {
						depends: function(element) {
							if ($("#condition").val() == 'A'){
								return true;
							}  else {
								return false;
						   }
						} 
					 }
				 },
			toAmount : {		 
						required: {
							depends: function(element) {
							if ($("#condition").val() == 'A'){
								return true;
							}  else {
								return false;
						   }
						}
					 },
					 number : {
						depends: function(element) {
							if ($("#condition").val() == 'A'){
								return true;
							}  else {
								return false;
						   }
						} 
					 }
				} 
    },  
    messages : {
			frequency : {   required: "Please Select Frequency."    },
			channel : {   required: "You must check at least 1 box", maxlength: "Check no more than {0} boxes"     },
			currency : {  required: "Please Select Currency."    },
			condition : {   required: "Please Select Condition."   },
			fromCount : {   required: "Please Select From Count.", number : "Please Input Number From [0-9]"   },
			toCount : { required: "Please Select To Count.", number : "Please Input Number From [0-9]"    },
			fromAmount : {   required: "Please Select From Amount.", number : "Please Input Number From [0-9]"   },
			toAmount : {   required: "Please Select To Amount.", number : "Please Input Number From [0-9]" 	  }
			
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

var form3Rules = {
    rules : {
		flatPercent : { required : true },
		partnerAccount : { required : true },
		faltPercentAmount : { required : true }
    },  
    messages : {
		flatPercent : { 
			required : "Please Select Flat/Percent"
			 },
		partnerAccount : { 
			required : "Please Select Partner Account"
			 },
		faltPercentAmount : {
			required : "Please Enter Flat/Percent Amount"
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

	var feeCode1 = '<s:property value="#respData.feeCode" />';
	feeCode1='F'+leftPad(feeCode1,0,7);
	var feeCode='<s:property value="#respData.subServiceCode" />-'+feeCode1; 
	$("#form1").find('input[name=feeCode]').val(feeCode);
	$("#form4").find('input[name=feeCode]').val(feeCode); 
	 
		$.validator.addMethod("le", function(value, element, param) {
			return this.optional(element) || value <= $("#form2 input[name="+param+"]").val();
		}, "less");	

		$.validator.addMethod("ge", function(value, element, param) {
			return this.optional(element) || value >= $("#form2  input[name="+param+"]").val();
		}, "greater");

		$.validator.addMethod("mo", function(value, element, param) {
			return this.optional(element) || value % $("#form2  input[name="+param+"]").val();
		}, "modulus");	


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
	
	$('#amt-chk').hide();
	$('#count-chk').hide();
	
	 $('#addFee').live('click', function () {  
		 
		$('#form2').removeData('validator');
		
		//if($("#tbody_data1 > tr").length < 1) {
			$("#error_dlno").text(""); 
			$("#form2").validate(form2Rules);
			if($("#form2").valid()) { 	
				var channels = "";
				 $('.chkChannel:checked').each(function(i){        
					var values = $(this).val(); 
					if(i == 0 ) {
						channels += values;
					} else {
						channels += ","+values;
					} 
				});
				
				var freq = $('#frequency option:selected').val() == undefined ? ' ' : $('#frequency option:selected').val();
				var curr = $('#currency option:selected').val() == undefined ? ' ' : $('#currency option:selected').val();
				var cond = $('#condition option:selected').val() == undefined ? ' ' : $('#condition option:selected').val();
				var fromVal = "";
				var toVal = "";
				
				if(cond == 'C') {
					fromVal = $('#fromCount').val();
					toVal =   $('#toCount').val();
				} else if(cond == 'A') {
					fromVal = $('#fromAmount').val();
					toVal =   $('#toAmount').val();
				} else   {
					fromVal = "";
					toVal = "";
				} 
				
				var eachrow = freq+"@"+channels+"@"+curr+"@"+cond+"@"+fromVal+"@"+toVal; 
				
				if(val % 2 == 0 ) {
					addclass = "even";
					val++;
				} else {
					addclass = "odd";
					val++;
				}
				
				var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
								"<td>"+rowindex+"</td>"+
								"<td>"+$('#frequency option:selected').text()+"</td>"+	
								"<td>"+channels+"</td>"+	
								"<td>"+curr+"</td>"+	
								"<td>"+$('#condition option:selected').text()+"</td>"+	
								"<td>"+fromVal+" </td>"+ 
								"<td>"+toVal+" </td>"+  
								"<td><a class='btn btn-min btn-info' href='#' id='editDat'> <i class='icon-edit icon-white'></i></a>  <a class='btn btn-min btn-danger' href='#' id='deleteemail'> <i class='icon-trash icon-white'></i> </a></td></tr>";
										
					$("#tbody_data1").append(appendTxt); 
					 
					if(accountFinalData1 != '') {
						accountFinalData1 += "#"+eachrow;
					} else {
						accountFinalData1 += eachrow;
					}
					 
					
					colindex = ++ colindex; 
					rowindex = ++rowindex;
					
					$('#fromAmount').val('');
					$('#toAmount').val('');
					$('#fromCount').val('');
					$('#toCount').val('');
					$('#frequency').val('');
					$('#frequency').trigger("liszt:updated"); 
					$('#currency').val('') ;
					$('#currency').trigger("liszt:updated"); 
					$('#condition').val('') ;
					$('#condition').trigger("liszt:updated"); 
					//$('#condition').trigger("change");
					 
					
					$('.chkChannel:checked').each(function(i){        
						 $(this).attr('checked', false);
					});
					
			} else {
				return false;
			} 
		//} else {
		//	$("#error_dlno1").text("Accept only one frequency."); 
		//}
		
	 }); 
	 
	  $('#addOffusBin').live('click', function () {
	 
		var channels = "";
		$("#form2").find("input[name=channel]:checked").each(function(index){ 
			if(index == 0) channels+=$(this).val() ;
			else channels+=","+$(this).val() ;
		});
		
		if(channels == "") {
			alert('Please tick atlease one "Network".');
		} else {
			$("#form5").validate(form5Rules); 
			if($("#form5").valid()) { 
				var selbin = $('#serviceBin option:selected').val() == undefined ? ' ' : $('#serviceBin option:selected').val();
				var acqid =  $('#acquirerId').val() == undefined ? ' ' : $('#acquirerId').val();	
				var eachrow=selbin+","+acqid;				
				var addclass = "";
				if(val % 2 == 0 ) {
					addclass = "even";
					val++;
				} else {
					addclass = "odd";
					val++;
				} 
				var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
								"<td>"+rowindex+"</td>"+
								"<td>"+ $('#serviceBin option:selected').text()+"</td>"+	
								"<td>"+acqid+"</td>"+	
								"<td> <a class='btn btn-min btn-info' href='#' id='editDat'> <i class='icon-edit icon-white'></i></a>  <a class='btn btn-min btn-danger' href='#' id='deleteemail'> <i class='icon-trash icon-white'></i> </a></td></tr>";
										
					$("#tbody_data3").append(appendTxt);	 
					
					if(accountFinalData2 != '') {
						accountFinalData2 += "#"+eachrow;
					} else {
						accountFinalData2 += eachrow;
					} 
					
					colindex = ++ colindex; 
					rowindex = ++rowindex;
					
					$('#serviceBin').val(''); 
					$('#serviceBin').trigger("liszt:updated"); 
					
					$('#acquirerId').val(''); 
					$('#acquirerId').trigger("liszt:updated"); 
					
			} else {
				return false;
			}
		}
	});
	
	 $('#addCap').live('click', function () {
		var partnerAccount = $('#partnerAccount option:selected').val() == undefined ? ' ' : $('#partnerAccount option:selected').val();
		var amount = $('#faltPercentAmount').val() == undefined ? ' ' : $('#faltPercentAmount').val();
		var flatPercentile = $('#flatPercent option:selected').val() == undefined ? ' ' : $('#flatPercent option:selected').val();
		$("#form3").validate(form3Rules); 
		if($("#form3").valid()) { 	
			var eachrow=partnerAccount+","+flatPercentile+","+amount;				
			var addclass = ""; 
			if(val % 2 == 0 ) {
				addclass = "even";
				val++;
			} else {
				addclass = "odd";
				val++;
			} 
			 
			var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
							"<td>"+rowindex+"</td>"+
							"<td>"+partnerAccount+"</td>"+	
							"<td>"+$('#flatPercent option:selected').text()+"</td>"+	
							"<td>"+amount+" </td>"+ 
							"<td> <a class='btn btn-min btn-info' href='#' id='editDat'> <i class='icon-edit icon-white'></i></a>  <a class='btn btn-min btn-danger' href='#' id='deleteemail'> <i class='icon-trash icon-white'></i> </a></td></tr>";
									
				$("#tbody_data2").append(appendTxt);	 
				
				$('#faltPercentAmount').val('');
				
				
				if(accountFinalData != '') {
					accountFinalData += "#"+eachrow;
				} else {
					accountFinalData = eachrow;
				} 
				  
				
				colindex = ++ colindex; 
				rowindex = ++rowindex;
				
				$('#partnerAccount').val('');
				$('#partnerAccount').trigger("liszt:updated");
				$('#flatPercent').val('');
				$('#flatPercent').trigger("liszt:updated"); 
				
		} else {
			return false;
		}
	});  
	
	$("#condition").live('change',function(e){ 
	 
		var cond = $(this).val();	
		if(cond == 'C') {
			$('#amt-chk').hide();
			$('#count-chk').show(); 
		} else if( cond == 'A') {
			$('#amt-chk').show();
			$('#count-chk').hide();
		}else {
			$('#amt-chk').hide();
			$('#count-chk').hide(); 
		}
	});
	
	$('#form1').find('input[type=radio]').each(function(){ 
 		if($(this).attr("id") == "on-us" && $(this).is(':checked') ){
			$("#acq-bin-chk").hide(); 
		} else {
			$("#acq-bin-chk").show();
		}
		 
	}); 
	
 	$('input[type=radio]').live('click',function() { 
		if($(this).attr("id") == "on-us" && $(this).is(':checked') ){
			$("#acq-bin-chk").hide();
			$('#form5').removeData('validator');
		} else {
			$("#acq-bin-chk").show();
		} 
 	});
	
	$('#back-button').live('click',function() { 
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/serviceMgmtAct.action';
		$("#form1").submit();
		return true;
	});
	
	
	$('#submit-button').live('click',function() {  
		if($("#form1").valid()) {
			if(accountFinalData == "") {
				$("#error_dlno").text("Please add atleast one record of partner details."); 
			} else 	if(accountFinalData1 == "") {
				$("#error_dlno").text("Please capture atleast one fee range."); 
			}  else { 
				$("#error_dlno").text(""); 
				$("#partnerDetails").val(accountFinalData);
				$("#multiSlabFeeDetails").val(accountFinalData1);
				$("#offusTrnDetails").val(accountFinalData2);  
 				
				$("#form4").find('input[name=serviceTrans]').val($("#form1").find('input[name=serviceTrans]:checked').val());
			 
				$("#form4").find('input[name=hudumaFlag]').val('false');
				$("#form4").find('input[name=hudumaServiceName]').val('NO');
				$("#form4").find('input[name=hudumaSubServiceName]').val('NO');					
				 
				
				$("#form4")[0].action='<%=request.getContextPath()%>/<%=appName %>/feeCreateSubAct.action';
				$("#form4").submit();
				return true; 
			}
		} else {
			return false;
		}
	}); 
	
});
 
 
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
							<tr class="even">
								<td><label for="Sub Service Code"><strong>Sub Service Code<font color="red">*</font></strong></label></td>
								<td><input name="subServiceCode"  type="text" id="subServiceCode" class="field" value='<s:property value="#respData.subServiceCode" />'   readonly /> </td>
								<td><label for="Sub Service Name"><strong>Sub Service Name<font color="red">*</font></strong></label></td>
								<td>
									<input name="subServiceName"  type="text" id="subServiceName" class="field" value='<s:property value="#respData.subServiceName" />'   readonly />
								</td>
							</tr> 
							<tr class="odd"> 
								<td><label for="Fee Code"><strong>Fee Code<font color="red">*</font></strong></label></td>
								<td ><input name="feeCode" type="text" id="feeCode" class="field" value='<s:property value="#respData.feeCode" />' readonly /></td>
								<td> <label for="Service"><strong>ON-US/OFF-US Flag<font color="red">*</font></strong></label> 	</td>
								<td>
									<input name="serviceTrans" type="radio" value="ONUS" id="on-us" />ON-US &nbsp;&nbsp;
									<input name="serviceTrans" type="radio" value="OFFUS" id="off-us" checked="true"/>OFF-US								
 								</td> 
								 
							</tr>  
					</table>
				</fieldset> 
			</div>
		</form>
		<div id="acq-bin-chk" class="box-content">
			<form name="form5" id="form5" method="post" action="">
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
			</form>
		</div>
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
 									<input name="channel" class="chkChannel" type="checkbox" value="CARD" id="channel-card" /> CARD &nbsp;
									<input name="channel" class="chkChannel" type="checkbox" value="POS" id="channel-pos" /> POS &nbsp;
									<input name="channel" class="chkChannel" type="checkbox" value="ATM" id="channel-pos" /> ATM &nbsp;
									<input name="channel" class="chkChannel" type="checkbox" value="WEB" id="channel-web" /> WEB &nbsp;
									<input name="channel" class="chkChannel" type="checkbox" value="MOBILE" id="channel-mobile" /> MOBILE &nbsp;
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
								<td><select name="condition" id="condition" 
										required='true' data-placeholder="Choose location..." 	
										class="chosen-select" style="width: 220px;">
										<option value="">Select</option>
 										<option value="A">Amount</option>
										<option value="C">Count</option>
									</select>
								</td>
							</tr>
							<tr class="even" id="amt-chk">
								<td><label  for="From Amount"><strong>From Amount <font color="red">*</font></strong></label></td>
								<td><input name="fromAmount" id="fromAmount" class="field" type="text"
									  required='true'/></td>
								<td><label  for="To Amount"><strong>To Amount</strong></label></td>
								<td><input name="toAmount" id="toAmount" class="field" type="text"  required='true' /></td>
							</tr>
							<tr class="even" id="count-chk">
								<td><label  for="From Count"><strong>From Count <font color="red">*</font></strong></label></td>
								<td><input name="fromCount" id="fromCount" class="field" type="text"  required='true'/></td>
								<td><label  for="To Count"><strong>To Count<font color="red">*</font></strong></label></td>
								<td><input name="toCount" id="toCount" class="field" type="text"   required='true'/></td>
							</tr>  
							<tr class="odd" align="center">
								<td colspan="4">
									<input type="button" class="btn btn-success" name="addFee" id="addFee" value="Add Fee Slabs" />
									<input type="button" class="btn btn-success" name="updateFee" id="updateFee" value="Update" style="display:none" /> 
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
 							<th>Channels</th>
							<th>Currency</th>
							<th>Condition</th>
							<th>From Amount/Count</th>
							<th>To Amount/Count</th> 
							<th>Actions</th>
						</tr>
					</thead>    
					<tbody id="tbody_data1"> 
					</tbody>
				</table> 
			</div>
		</form>
		</div> 
	</div> 
	
<form name="form3" id="form3" method="post" >
	<div class="row-fluid sortable"><!--/span--> 
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<i class="icon-edit"></i> Partner Account Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div>
			<div class="box-content" id="partner-details">
				<fieldset> 
					 <table width="950" border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable " >
							<tr class="odd">
								<td width="20%"><label  for="Flat/Percentile"><strong>Flat/Percentile <font color="red">*</font></strong></label></td>
								<td width="30%"><select name="flatPercent" id="flatPercent" type="select"  required='true' 
										data-placeholder="Choose location..." 	class="chosen-select" style="width: 220px;">
										<option value="">Select</option>
										<option value="P">Percentile</option>
										<option value="F">Flat</option>
									</select></td> 
								<td width="20%">&nbsp;</td>
								<td width="30%"> &nbsp;</td>
							</tr>
							<tr class="odd">
								<td ><label for="Partner Account"><strong>Partner Account<font color="red">*</font></strong></label></td>
								<td > 
									 <s:select cssClass="chosen-select" 
											headerKey="" 
											headerValue="Select"
											list="#respData.AccountList" 
											name="partnerAccount" 
											id="partnerAccount" 
											requiredLabel="true" 
											theme="simple"
											data-placeholder="Choose Partner..." 
 											 />  
								</td>
								<td ><label for="Amount"><strong>Flat/Percentile Amount<font color="red">*</font></strong></label></td> 
								<td ><input name="faltPercentAmount"  type="text" id="faltPercentAmount" class="field" value="<s:property value="#respData.faltPercentAmount" />" >
								</td>
							</tr> 
							<tr class="odd" >
								<td colspan="4">
									<input type="button" class="btn btn-success" name="addCap" id="addCap" value="Add" ></input>
									<input type="button" class="btn btn-success" name="updateCap" id="updateCap" value="Add" style="display:none"></input>
								</td>
							</tr>
					</table> 
				</fieldset> 
			</div> 
			<div id="partner-details-row" class="box-content">
				<fieldset>  
					<table class="table table-striped table-bordered bootstrap-datatable " 
							id="accountData" style="width: 100%;">
						<thead>
							<tr>
								<th>Sno</th>
								<th>Partner Account</th>
								<th>F/P(%)</th>
								<th>F/P(%) Amount</th>
								<th>Action</th>
							</tr>
						</thead>    
						<tbody  id="tbody_data2">
						</tbody>
					</table>
				</fieldset> 
			</div>  
		</div> 
	</div> 
</form>

<form name="form4" id="form4" method="post" >
 
<input name="serviceCode" type="hidden" id="serviceCode" class="field" value="<s:property value="#respData.serviceCode" />"   /> 
<input name="serviceName" type="hidden"  id="serviceName" class="field"  value="<s:property value="#respData.serviceName" />"  />  
<input name="subServiceCode"  type="hidden" id="subServiceCode" class="field" value="<s:property value="#respData.subServiceCode" />"    />  
<input name="subServiceName"  type="hidden" id="subServiceName" class="field" value="<s:property value="#respData.subServiceName" />"    />  
<input name="feeCode" type="hidden" id="feeCode" class="field" value="<s:property value="#respData.feeCode" />"   /> 

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
		<input type="button" class="btn btn-info" id="back-button" name="back-button" href="#"  value="Cancel" /> &nbsp;&nbsp;
		<input type="button" class="btn btn-success" id="submit-button" name="submit-button"  value="Submit" />&nbsp;&nbsp;
		<span id ="error_dlno" class="errors"></span>
	</div> 
</div>  

</body>
</html>
