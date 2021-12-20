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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Create Entity</title> 
 
<style type="text/css">
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
span.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
</style>  
<script type="text/javascript" > 
var status = "";
var serialstatus = ""; 
var mpesastatus = ""; 

function serviceCheck(id){
	$('#agn-id').text('');
	$('#error_dlno').text('');
	var val = $("#"+id).val();
	if(val == 'select') { 
		status = "FOUND";
		serialstatus ='NOTFOUND';
		$("#kra-information").hide(); 
		$("#ncc-information").hide();
		$("#moh-information").hide(); 
		$("#state-land-information").hide();
		$("#state-law-information").hide(); 
		$("#nhif-information").hide();
	} else if(val == 'KRA'){
		status = "FOUND";
		serialstatus ='NOTFOUND';
		$("#kra-information").show(); 
		$("#ncc-information").hide();
		$("#moh-information").hide(); 
		$("#state-land-information").hide();
		$("#state-law-information").hide(); 
		$("#nhif-information").hide();
	} else if(val == 'NCC'){ 
		status = "FOUND";
		serialstatus ='NOTFOUND';
		$("#kra-information").hide();
		$("#ncc-information").show(); 
		$("#moh-information").hide(); 
		$("#state-land-information").hide();
		$("#state-law-information").hide(); 
		$("#nhif-information").hide();
	} else if(val == 'NHIF'){ 
		status = "FOUND";
		serialstatus ='NOTFOUND';
		$("#kra-information").hide();
		$("#ncc-information").hide();
		$("#moh-information").hide(); 
		$("#state-land-information").hide();
		$("#state-law-information").hide(); 
		$("#nhif-information").show();
	} else if(val == 'STATE_LAW'){ 
		status = "FOUND";
		serialstatus ='NOTFOUND';
		$("#kra-information").hide();
		$("#ncc-information").hide();
		$("#moh-information").hide(); 
		$("#state-land-information").hide();
		$("#state-law-information").show(); 
		$("#nhif-information").hide();
	} else if(val == 'LAND'){ 
		status = "FOUND";
		serialstatus ='NOTFOUND';
		$("#kra-information").hide();
		$("#ncc-information").hide();
		$("#moh-information").hide(); 
		$("#state-land-information").show();
		$("#state-law-information").hide(); 
		$("#nhif-information").hide();
	} else if(val == 'MOH'){ 
		status = "FOUND";
		serialstatus ='NOTFOUND';
		$("#kra-information").hide();
		$("#ncc-information").hide();
		$("#moh-information").show(); 
		$("#state-land-information").hide();
		$("#state-law-information").hide(); 
		$("#nhif-information").hide();
	}
}  

var list = "paymentDate,nhif-from-date,nhif-to-date".split(",");
var datepickerOptions = {
			showTime: false,
			showHour: false,
			showMinute: false,
  		dateFormat:'dd/mm/yy',
  		alwaysSetTime: false,
		changeMonth: true,
		changeYear: true
};	
var count = 0;
function AddDate(oldDate, offset, offsetType) {
    var year = parseInt(oldDate.getFullYear());
    var month = parseInt(oldDate.getMonth());
    var date = parseInt(oldDate.getDate());
    var hour = parseInt(oldDate.getHours());
    var newDate;
     switch (offsetType) {
         case "Y":
         case "y":
             newDate = new Date(year + parseInt(offset), month, date, hour);
             break;
  
         case "M":
         case "m":
             newDate = new Date(year, month + parseInt(offset), date, hour);
             break;
  
         case "D":
         case "d":
             newDate = new Date(year, month, date + parseInt(offset), hour);
             break;
  
         case "H":
         case "h":
             newDate = new Date(year, month, date, hour + parseInt(offset));
             break;
     }
     return newDate;            
 } 
 
var vid = "";
function mpesaMobileStatus(type){
	$("#error_dlno").text('');  
	if($('#mobile').val().length > 8 && $('#select-payment-type').val() == 'MPESA') {
		var queryString="method=searchHudumaMobile&mobile=254"+$("#mobile").val()+"&amount="+$("#receivedAmount").val(); 
		$.getJSON("postJson.action", queryString,function(data) {  
			mpesastatus = data.status;
			vid =  data.message;
			 
			if(mpesastatus == "FOUND") {
				$("#error_dlno").text(''); 
				$("#CV0030").val(vid);
				 mobStatus=true;
			} else {
				$("#error_dlno").html("MPESA Record Not Exist.").show(); 
			}
		});
	} 
}

$(function(){ 
	$(list).each(function(i,val){ 
		$('#'+val).datepicker(datepickerOptions);
	});
	
	serviceCheck('selectService');
	$("#selectService").live('change',function() {
		serviceCheck($(this).attr('id'));
   });
	
	$(".chosen-select").chosen({no_results_text: "Oops, nothing found!"});  
	
	$('#kra-service').live('change',function(){   
		$('#ser-id').text(''); 
		var amt = "";
		var val = $(this).val();
		if(val != 'select') {
			$('#ser-id').text('');
			amt = (val == 'DLR1' ? '640' : '1200');  
			$('#receivedAmount').val(amt);
			$('#CV0012').val(amt);
		} else {
			$('#ser-id').text('Please Select KRA Service.');
		} 
	}); 
	
	$('#select-ncc-service').live('change',function(){ 
		var val = $(this).val();
		$('#payment-mode').text(''); 
		if(val != 'select') {
			$('#ncc-id').text('');
		} else {
			$('#ncc-id').text('Please Select NCC Service.');
		}
	}); 
	
	$('#select-payment-type').live('change',function(){ 
		var val = $(this).val();
		$('#payment-mode').text(''); 
		if(val != 'select') {
			$('#payment-mode').text('');
		} else {
			$('#payment-mode').text('Please Select Payment Mode.');
		}
	}); 
	
	$('#select-statelaw-service').live('change',function(){ 
		var val = $(this).val();
		$('#state-law-id').text(''); 
		if(val != 'select') {
			$('#state-law-id').text('');
		} else {
			$('#state-law-id').text('Please Select STATE LAW Service.');
		}
	}); 
	
	$('#select-land-service').live('change',function(){ 
		var val = $(this).val();
		$('#land-id').text(''); 
		if(val != 'select') {
			$('#land-id').text('');
		} else {
			$('#land-id').text('Please Select STATE LAND Service.');
		}
	});  
	
	$('#select-moh-service').live('change',function(){ 
		var val = $(this).val();
		$('#moh-id').text(''); 
		if(val != 'select') {
			$('#moh-id').text('');
		} else {
			$('#moh-id').text('Please Select MOH Service.');
		}
	}); 
	
	$('#nhif-service').live('change',function(){ 
		var val = $(this).val();
		$('#nhif-id').text(''); 
		if(val != 'select') {
			$('#nhif-id').text('');
		} else {
			$('#nhif-id').text('Please Select NHIF Service.');
		}
	}); 
 
	
	 /*$('#element').on('keyup keypress blur change', function() { 
		});*/

	var paymentDateFunction = function(){
 		
		var str = $(this).val(); 
		var datePattern = /^\d{2}\/\d{2}\/\d{4}$/i;
		var selVal = $('#kra-service').val();
	
		//console.log(AddDate(new Date(), "3", "Y"));
		
		var expDateVal = selVal == 'DLR1' ? '1' : selVal == 'DLR3' ? '3' : '0';
		
        if( datePattern.test(str) ) {

            var parts = str.split("/");

            var day = parts[0] && parseInt( parts[0], 10 );
            var month = parts[1] && parseInt( parts[1], 10 );
            var year = parts[2] && parseInt( parts[2], 10 );
            var duration = parseInt(expDateVal, 10);
			//var duration = parseInt( 3, 10);

            if( day <= 31 && day >= 1 && month <= 12 && month >= 1 ) {

                var expiryDate = new Date( year, month - 1, day );
                expiryDate.setFullYear( expiryDate.getFullYear() + duration );

                var day = ( '0' + expiryDate.getDate() ).slice( -2 );
                var month = ( '0' + ( expiryDate.getMonth() + 1 ) ).slice( -2 );
                var year = expiryDate.getFullYear();
				
				var expDateChk = checkNum((parseInt(day))) + "/" +  month  + "/" + year; 

                $("#expiryDate").val( expDateChk ); 
                $("#CV0011").val(expDateChk);  

            } else {
                // display error message
            }
        } else {
			$('#paymentDate').css("{background-color : red}");
		}
	}
	 
	
	$('#paymentDate') 
    .mouseout(paymentDateFunction)
    .change(paymentDateFunction); 
	
	$('#dlNo').live('keyup',function(){
		 var id = $(this).attr('id');
		 var v_val = $(this).val(); 
		 $("#"+id).val(v_val.toUpperCase());
	}); 
	
	$('#dl-search').live('click',function() {
		$("#error_dlno").text('');
		var queryString="method=searchKraData&dlNo="+$("#dlNo").val(); 
		$.getJSON("postJson.action", queryString,function(data) {  
			status = data.status;
			if(status == "NOTFOUND") { 
				$("#error_dlno").html("The Entered DL Number Is Invalid. Please check the entered DL Number.").show();
				$('#customer').val('').removeAttr('disabled');
				$('#idNumber').val('').removeAttr('disabled');
				$('#CV0007').val('') ;
				$('#CV0008').val('');
			} else {
				$("#error_dlno").text('');
				$('#customer').val(data.dlName).attr('disabled','disabled');
				$('#idNumber').val(data.dlIdnumber).attr('disabled','disabled');
				$('#CV0007').val(data.dlName) ;
				$('#CV0008').val(data.dlIdnumber);
			} 
		});
	});
	
	$('#serialNo').live('keyup',function() {
		$("#error_dlno").text(''); 
		if($(this).val().length > 5) {
			var queryString="method=searchSerial&serialNo="+$("#serialNo").val(); 
			$.getJSON("postJson.action", queryString,function(data) {  
				serialstatus = data.status;
				 
				if(serialstatus == "FOUND")  {
					$("#error_dlno").html("The Entered Serial Number Exists. Please enter a new Serial No.").show(); 
				} else {
					$("#error_dlno").text(''); 
				} 
			});
		}
		 
	});
	var mobStatus= false;
	
	
	$('#mobile').live('keyup',function() { 
		 mpesaMobileStatus("MOBILE");
	});
	
	$('#select-payment-type').live('change',function() { 
		 mpesaMobileStatus("MOP");
	});
	
	var agentFlag= true;
	var checkSubmitFlag= false;
	
	var flag1= true;
	var flag2= true;
 	
	var rulesToValidate = "";
	var agent = "";
	
	$('#btn-make-payment').live('click',function() {
		 
		$("#error_dlno").text('');
		
		agent = $('#selectService').val(); 
		 
		if(agent == 'select') {
			$('#agn-id').text('Please select Agent Type.');
			agentFlag = false;
		}  
		else {  
			var queryString = ""; 
			queryString+="method="+agent.toLowerCase()+"&CV0001="+agent+"&CV0014="+agent;
			
			if(agent.toUpperCase() == 'KRA') {
				 
				if($('#kra-service').val() == 'select') {
					$('#ser-id').text('Please Select KRA Service.');
					flag1 = false;
				} else {
					flag1 = true;
				}				
				if($('#select-payment-type').val() == 'select') {
					$('#payment-mode').text('Please Select Payment Mode.');
					flag2 = false;
				}  else {
					flag2 = true;
				}  
				
				if(status != "NOTFOUND" && serialstatus=='NOTFOUND' && flag2 && flag1)  {
					$('#ser-id').text('');
					$('#agn-id').text('');
					$("#error_dlno").text(''); 
					//$('#CV0002').val($('#kra-service option:selected').text()); 
					queryString+="&CV0002="+$('#kra-service option:selected').text();
					checkSubmitFlag= true; 
				} else if(status == "NOTFOUND") { 
					$("#error_dlno").html("The Entered DL Number Is Invalid. Please check the entered DL Number.").show();	 
				}  else if(serialstatus!='NOTFOUND') { 
					$("#error_dlno").html("The Entered Serial Number Exists. Please enter a new Serial No.").show();	 
				}    
				if(mpesastatus!='FOUND' && $('#select-payment-type').val() == 'MPESA') { 
					$("#error_dlno").html("MPESA Record Not Exist.").show();
					mobStatus=false;
				} else{
					mobStatus=true;
				}

				$("#form1").validate(hudumaKraRules); 
				//console.log(mobStatus);
				//console.log(mpesastatus);
				if($("#form1").valid() && agentFlag && checkSubmitFlag && mobStatus) {
					// Need to check the record for MPESA Transactions  
					postData("#form1",queryString);					
				} else { 
					return false;
				}
			} else if(agent.toUpperCase() == 'NCC') {
				flag1= true;
				queryString+="&CV0002="+$('#select-ncc-service option:selected').text(); 
				if($('#select-ncc-service').val() == 'select') {
					$('#ncc-id').text('Please Select NCC Service.');
					flag1 = false;
				} else {
					flag1 = true;
				}  
				
				if(flag1) {
					checkSubmitFlag= true; 
				} 
				
				$("#form2").validate(hudumaNccRules);
				if($("#form2").valid() && agentFlag && checkSubmitFlag) { 
					postData("#form2",queryString);
				} else { 
					return false;
				} 
				
			} else if(agent.toUpperCase() == 'STATE_LAW') {
				flag1= true;
				queryString+="&CV0002="+$('#select-statelaw-service option:selected').text(); 
				if($('#select-statelaw-service').val() == 'select') {
					$('#state-law-id').text('Please Select STATE LAND Service.');
					flag1 = false;
				} else {
					flag1 = true;
				}  
				
				if(flag1) {
					checkSubmitFlag= true; 
				} 
				
				$("#form5").validate(hudumaStateLawRules);
				if($("#form5").valid() && agentFlag && checkSubmitFlag) { 
					postData("#form5",queryString);
				} else { 
					return false;
				}  
			} else if(agent.toUpperCase() == 'LAND') {
				flag1= true;
				queryString+="&CV0002="+$('#select-land-service option:selected').text(); 
				if($('#select-land-service').val() == 'select') {
					$('#land-id').text('Please Select STATE LAND Service.');
					flag1 = false;
				} else {
					flag1 = true;
				}  
				
				if(flag1) {
					checkSubmitFlag= true; 
				} 
				
				$("#form3").validate(hudumaLandRules);
				if($("#form3").valid() && agentFlag && checkSubmitFlag) { 
					postData("#form3",queryString);
				} else { 
					return false;
				}  
			} else if(agent.toUpperCase() == 'MOH') {
				flag1= true;
				queryString+="&CV0002="+$('#select-moh-service option:selected').text(); 
				if($('#select-moh-service').val() == 'select') {
					$('#moh-id').text('Please Select STATE LAND Service.');
					flag1 = false;
				} else {
					flag1 = true;
				}  
				
				if(flag1) {
					checkSubmitFlag= true; 
				} 
				
				$("#form4").validate(hudumaMohRules);
				if($("#form4").valid() && agentFlag && checkSubmitFlag) { 
					postData("#form4",queryString);
				} else { 
					return false;
				}  
			} else if(agent.toUpperCase() == 'NHIF') {
				flag1= true;
				queryString+="&CV0002="+$('#nhif-service option:selected').text(); 
				if($('#nhif-service').val() == 'select') {
					$('#nhif-id').text('Please Select NHIF Service.');
					flag1 = false;
				} else {
					flag1 = true;
				}  
				
				if(flag1) {
					checkSubmitFlag= true; 
				} 
				
				$("#form6").validate(hudumaNhifRules);
				if($("#form6").valid() && agentFlag && checkSubmitFlag) { 
					postData("#form6",queryString);
				} else { 
					return false;
				}  
			} 
		}
		
	}); 

	var numchkstr="#receivedAmount,#nccReceivedAmount,#mobile,#nhif-received-amount,#land-received-amt,#moh-received-amt";
		numchkstr+=",#nhif-received-amount,#stl-received-amount,#serialNo";
		
	$(numchkstr).keypress(function (e) {
	 //if the letter is not digit then display error and don't type anything
	 if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		//display error message
		$("#"+$(this).attr('id')+"_err").html("&nbsp;&nbsp;Digits Only").show().fadeOut(2500);
			   return false;
		}
	});
	
}); 
	
function postData(form,paramString){
	$(form).attr("action", "serviceConfirmation.action")
			.attr("method", "post");
	
	var paramArray = paramString.split("&");
	 
	$(paramArray).each(function(indexTd,val) {
		var input = $("<input />").attr("type", "hidden").attr("name", val.split("=")[0]).val(val.split("=")[1]);
		$(form).append($(input));	 
	});	

	$(form).submit();	
}

	// Manual Validation 
	$.validator.addMethod(
      "regCheck",
      function (value, element) { 
        return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
      },
      "Please enter a date in the format dd/mm/yyyy"
    );
	
	$.validator.addMethod("date", function (value, element) { 
		var expDate = value.split('/'); 
		var today = new Date();  
		var currentMonth = today.getMonth();
		var currentDate = today.getDate();
		var currentYear = today.getFullYear(); 
		
		var date1 = parseInt(currentDate,10)+"/"+parseInt(currentMonth,10)+"/"+currentYear;
		var date2 = parseInt(expDate[0],10)+"/"+parseInt((expDate[1] - 1),10)+"/"+expDate[2];
		
 		if ( date1 == date2 )
			return true;
		else
			return false;
	});		

	function checkNum(val) {
		return (val < 10 ? "0"+val : val);
	}

	var hudumaNhifRules = {
			rules : {
				nhifservice : { required : true },
				CV0025 : { required :  true},
				CV0026 : { required :  true},
				CV0027 : { required :  true, regCheck: true},
				CV0028 : { required :  true, regCheck: true},
				CV0029 : { required :  true}
			},		
			messages : {
				nhifservice : { 
							required : "Please select NHIF Service."
						  } ,
				CV0025 : { 
							required : "Please input NHIF Member No."
					} ,
				CV0026 : { 
							required : "Please input Member No."
					} ,
				CV0027 : { 
							required : "Please input Received Amount.",
							regCheck : "Invalid From Date Format."
					} ,
				CV0028 : { 
							required : "Please input Received Amount.",
							regCheck : "Invalid To Date Format."
					} ,
				CV0029 : { 
							required : "Please input Received Amount."
					} 
			},
			errorElement: 'label'
	};  
	
	
	var hudumaMohRules = {
			rules : {
				selectmohservice : { required : true },
				CV0021 : { required :  true} 
			},		
			messages : {
				selectmohservice : { 
							required : "Please select MOH Service."
						  } ,
				CV0021 : { 
							required : "Please input Received Amount."
					}  
			},
			errorElement: 'label'
	};  
	
	var hudumaStateLawRules = {
			rules : {
				selectstatelawservice : { required : true },
				CV0023 : { required :  true} 
			},		
			messages : {
				selectstatelawservice : { 
							required : "Please select SATE LAW Service."
						  } ,
				CV0023 : { 
							required : "Please input Received Amount."
					}  
			},
			errorElement: 'label'
	};  
	
	var hudumaLandRules = {
			rules : {
				selectlandservice : { required : true },
				CV0018 : { required :  true} 
			},		
			messages : {
				selectlandservice : { 
							required : "Please select LAND Service."
						  } ,
				CV0018 : { 
							required : "Please input Received Amount."
					}  
			},
			errorElement: 'label'
	};  
	 
	var hudumaNccRules = {
			rules : {
				selectnccservice : { required : true },
				CV0015 : { required :  true},
				CV0016 : { required :  true}
			},		
			messages : {
				selectnccservice : { 
							required : "Please select Ncc Service."
						  } ,
				CV0015 : { 
							required : "Please input Number Plate."
					} ,
				CV0016 : { 
							required : "Please input Received Amount."
					}
			},
			errorElement: 'label'
	};  
	
	var hudumaKraRules = {
			rules : { 
				CV0005 : {  required :  true}, 
				CV0006 : { required : true } ,
				/*customer : { required : true } ,
				idNumber : { required : true } ,*/
				CV0009 : { required : true } ,
				CV0010 : { required : true,regCheck : true ,date:true} ,
  				/* CV0012 : { required : true  }, */
 				CV0013 : { required : true  }
			},		
			messages : { 
				CV0005 : { 
							required : "Please input Mobile Number."
						}, 
				CV0006 : { 
							required : "Please enter DL Number."
						  },  
				/*customer : { 
								required : "Please input Customer Name."
						},
				idNumber : { 
								required : "Please enter DL Number."
							},*/
				CV0009 : { 
								required : "Please enter Serial Name."
							},
				CV0010 : { 
								required : "Please Select Payment Date."  ,
								regCheck  : "Date Format Should Be(DD/MM/YYYY).", 
								date  : "Payment date should always be equal to system date." 
							}, 
				/* CV0012 : { 
								required : "Please Input Received Amount." 
							} , */
				CV0013 : {
							required : "Please select mode of payment." 
						}
			},
			errorElement: 'label'
		};   

</script> 
	   
</head>
 <body>  
	<div id="content" class="span10">
		<span>
			<ul class="breadcrumb">
				<li>
					<a href="home.action">Home</a> <span class="divider">&gt;&gt;</span>
				</li>
				<li><a href="#">Huduma Services</a>  
				</li> 
			</ul>
		</span> 

		<table height="2">
			<tr>
				<td colspan="3">
					<div class="messages" id="messages">
						<s:actionmessage />
					</div>
					<div class="errors" id="errors">
						<s:actionerror />
					</div>
				</td>
			</tr>
		</table>  
	
	<div class="row-fluid sortable" id="agency-details"> 
		<div class="box span12"> 
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>&nbsp;Agency Details 
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
			<div class="box-content"  id="agencyDetails"> 
				<fieldset>
					<table width="950" border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable ">
						<tr class="even">
							<td width="20%"><label for="Select Agency"><strong>Select Agency<font color="red">*</font></strong></label></td>
							<td colspan="3">
								<select  name="selectService" id="selectService" data-placeholder="Choose Agency..." 
									class="chosen-select" style="width: 220px; display: none;"  required=true >
									<!-- <option value="select">(choose one)</option> -->
									<option value="NHIF">NHIF</option>
									<option value="NCC">NCC</option>
									<option value="KRA" selected>KRA</option>
									<option value="STATE_LAW">STATE LAW</option>
									<option value="LAND">LAND</option>
									<option value="MOH">MOH</option> 
								</select>  &nbsp; <label id="agn-id" class="errors"></label>
							</td> 
						</tr>
					</table>
				 </fieldset> 
			</div>
		</div>
	</div>
 
	<form name="form1" id="form1" method="post" >  
		<div class="row-fluid sortable" id="kra-information"> 
			<div class="box span12">
									
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>&nbsp;KRA Information 
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					</div>
				</div> 		
				<div class="box-content">
					<fieldset>  
						<div id="kra-acctDetails"> 
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable " >
								<tr class="even">
									<td width="20%"><label for="select"><strong>Select KRA Service<font color="red">*</font></strong></label></td>
									<td colspan="3">
										<select  name="kraService" id="kra-service" data-placeholder="Choose a Service..." 
											class="chosen-select" style="width: 220px; display: none;" required=true>
											<option value="select">(choose one)</option>
											<option value="DLR1">Driving License Renewal(1YR)</option>
											<option value="DLR3">Driving License Renewal(3YR)</option>
										</select> &nbsp; <label id="ser-id" class="errors" ></label>
									</td>
								</tr>
							</table>
						</div>
						<div  id="kra-custDetails"> 
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr class="odd">
									<td width="20%"><label for="DL No"> <strong>DL No <font color="red">*</font></strong></label></td>
									<td width="30%"><input name="CV0006" autocomplete="off" type="text" class="field" id="dlNo" style="width: 120px" required="required"  />
									 <input type="button"  class="btn btn-primary" name="dl-search" id="dl-search" value="Search" /></td>
									<td width="20%"><label for="Customer"><strong>Customer <font color="red">*</font></strong></label></td>
									<td width="30%"><input name="customer" autocomplete="off" type="text" class="field" id="customer" required="required"  /> </td>
								</tr>
								<tr class="odd">
									<td><label for="ID Number"> <strong>ID Number<font color="red">*</font></strong></label></td>
									<td><input name="idNumber" type="text" autocomplete="off" class="field" id="idNumber" required="required" /></td>
									<td><label for="Serial No"><strong>Serial No <font color="red">*</font></strong></label></td>
									<td><input name="CV0009" type="text" autocomplete="off" class="field" id="serialNo"  required="required"  /></td>
								</tr>
								<tr class="odd">
									<td><label for="Payment  Date"> <strong>Payment  Date  <font color="red">*</font></strong></label></td>
									<td><input name="CV0010" type="text" autocomplete="off" class="field" id="paymentDate" required="required"  /></td>
									<td><label for="Expiry Date"><strong>Expiry Date</strong></label></td>
									<td><input name="expiryDate" type="text" autocomplete="off" class="field" id="expiryDate" disabled="disabled" /></td>
								</tr>
								<tr class="odd">
									<td><label for="Received Amount"><strong>Received Amount<font color="red">*</font></strong></label></td>
									<td><input name="receivedAmount" type="text" autocomplete="off" class="field" id="receivedAmount"  disabled="disabled" /></td>
									<td><label for="Mobile"><strong>Mobile<font color="red">*</font></strong></label></td>
									<td><input name="CV0005" type="text" autocomplete="off" class="field" id="mobile" required="required" maxlength="9" /><span id="mobile_err" class="errmsg"></span></td>	
								</tr> 
								<tr class="odd"> 
									<td><label for="Payment Mode"><strong>Payment Mode<font color="red">*</font></strong></label></td>
									<td colspan="3">
										<select  name="CV0013" id="select-payment-type" data-placeholder="Choose a Payment..." 
											class="chosen-select" style="width: 220px; display: none;" required="required"  >
											<option value="select">(choose one)</option>
											<option value="CARD">CARD</option>
											<option value="CASH">CASH</option>
											<option value="MPESA">MPESA</option>
										</select> &nbsp; <label id="payment-mode" class="errors" ></label>
									</td> 
								</tr> 
							</table> 
						</div> 
					</fieldset> 
			<div class="form-actions" id="form1-submit"> 
				<input type="button" name="btn-make-payment" class="btn btn-success" id="btn-make-payment" value="Make Payment" />
				&nbsp; <!-- <input type="button"  class="btn btn-warning" name="btn-load-serialno" id="btn-load-serialno" value="Load Serial NO(S)"  disabled="disabled"/>  -->
				<span id ="error_dlno" class="errors"></span>		 	
			</div>					
				</div>
			</div> 
		</div>  
		
		<input type="hidden" name="CV0011" id="CV0011" value=""/>
		<input type="hidden" name="CV0012" id="CV0012" value=""/>
		<input type="hidden" name="CV0007" id="CV0007" value=""/>
		<input type="hidden" name="CV0008" id="CV0008" value=""/> 
		<input type="hidden" name="CV0030" id="CV0030" value=""/>
	</form>
	<form name="form2" id="form2" method="post" > 
		<div class="row-fluid sortable" id="ncc-information" > 
			<div class="box span12">
									
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>&nbsp;NCC Information 
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					</div>
				</div> 		
				<div class="box-content">
					<fieldset>  
						<div id="ncc-details"> 
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="select"><strong>Select NCC Service<font color="red">*</font></strong></label></td>
									<td colspan="3">
										<select  name="selectnccservice" id="select-ncc-service" data-placeholder="Choose a Service..." 
											class="chosen-select" style="width: 220px; display: none;" required="required">
											<option value="select">(choose one)</option>
											<option value="SBP">Single Business Permit</option>
											<option value="ISNT">Issuance of Seasonal NCC Tickets</option>
										</select> &nbsp; <label id="ncc-id" class="errors" ></label>
									</td>
								</tr>
							</table>
						</div>
						<div  id="ncc-custdetails"> 
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-ncc-details" >
								<tr class="odd">
									<td width="20%"><label for="Number Plate"> <strong>Number Plate <font color="red">*</font></strong></label></td>
									<td width="30%"><input name="CV0015" autocomplete="off" type="text" class="field" id="numberPlate" required="required"  /> </td>
									<td width="20%"><label for="Received Amount"><strong>Received Amount <font color="red">*</font></strong></label></td>
									<td width="30%"><input name="CV0016" autocomplete="off" type="text" class="field" id="nccReceivedAmount" required="required"  /><span id="nccReceivedAmount_err" class="errmsg"></span> </td>
								</tr> 
							</table> 
						</div> 
					</fieldset>  
					<div class="form-actions" id="form2-sumbit"> 
						<input type="button" name="btn-make-payment" class="btn btn-success" id="btn-make-payment" value="Make Payment" /> 
					</div>
				</div>
			</div> 
		</div> 
		
	</form>   
	<form name="form3" id="form3" method="post" > 
		<div class="row-fluid sortable" id="state-land-information" style="display:none"> 
			<div class="box span12"> 
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>&nbsp;STATE LAND Information 
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					</div>
				</div> 		
				<div class="box-content">
					<fieldset>  
						<div id="state-law-details"> 
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="select"><strong>Select STATE LAND Service<font color="red">*</font></strong></label></td>
									<td colspan="3">
										<select  name="selectlandservice" id="select-land-service" data-placeholder="Choose a Service..." 
											class="chosen-select" style="width: 220px; display: none;" required="required">
											<option value="select">(choose one)</option>
											<option value="LNDRTS">Land Rates</option>
											<option value="APOSTD">Assessment &amp; Payment of Stamp Duty</option>
										</select> &nbsp; <label id="land-id" class="errors" ></label>
									</td>
								</tr>
							</table>
						</div>
						<div  id="state-law-custdetails"> 
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-state-land-details" >
								<tr class="odd">
									<td width="20%"><label for="Number Plate"><strong>Received Amount <font color="red">*</font></strong></label></td>
									<td colspan="3"><input name="CV0018" autocomplete="off" type="text" class="field" id="land-received-amt" required="required"  /> <span id="land-received-amt_err" class="errmsg"></span></td>
									 
								</tr> 
							</table> 
						</div> 
					</fieldset> 
					<div class="form-actions" id="form3-sumbit"> 
						<input type="button" name="btn-make-payment" class="btn btn-success" id="btn-make-payment" value="Make Payment" /> 
					</div>
				</div>
			</div>
		</div>
			
		
	</form> 
	<!--- ---> 
	<form name="form4" id="form4" method="post" > 
		<div class="row-fluid sortable" id="moh-information" style="display:none"> 
			<div class="box span12">
									
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>&nbsp;MOH Information 
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					</div>
				</div> 		
				<div class="box-content">
					<fieldset>  
						<div id="moh-details"> 
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="select"><strong>Select MOH Service<font color="red">*</font></strong></label></td>
									<td colspan="3">
										<select  name="selectmohservice" id="select-moh-service" data-placeholder="Choose a Service..." 
											class="chosen-select" style="width: 220px; display: none;" required="required">
											<option value="select">(choose one)</option>
											<option value="BMIASST">BMI Assessment</option>
										</select> &nbsp; <label id="moh-id" class="errors" ></label>
									</td>
								</tr>
							</table>
						</div>
						<div  id="moh-cust-details"> 
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-moh-details" >
								<tr class="odd">
									<td width="20%"><label for="Number Plate"> <strong>Received Amount <font color="red">*</font></strong></label></td>
									<td colspan="3"><input name="CV0021" autocomplete="off" type="text" class="field" id="moh-received-amt" required="required"  /> <span id="moh-received-amt_err" class="errmsg"></span> </td>
									 
								</tr> 
							</table> 
						</div> 
					</fieldset>  
			<div class="form-actions" id="form4-sumbit"> 
				<input type="button" name="btn-make-payment" class="btn btn-success" id="btn-make-payment" value="Make Payment" /> 
			</div>
				</div>
			</div>
		</div> 
	</form> 
	<form name="form5" id="form5" method="post" > 
		<div class="row-fluid sortable" id="state-law-information" style="display:none"> 
			<div class="box span12">
									
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>&nbsp;STATE LAW Information 
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					</div>
				</div> 		
				<div class="box-content">
					<fieldset>  
						<div id="state-law-details"> 
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable " >
								<tr class="even">
									<td width="20%"><label for="select"><strong>Select STATE LAW Service<font color="red">*</font></strong></label></td>
									<td colspan="3">
										<select  name="selectstatelawservice" id="select-statelaw-service" data-placeholder="Choose a Service..." 
											class="chosen-select" style="width: 220px; display: none;" required="required">
											<option value="select">(choose one)</option>
											<option value="ROWG">Registration of Welfare Groups</option>
											<option value="SOBN">Search of Business Names</option>
											<option value="ROBN">Registration of Business Names</option>
										</select> &nbsp; <label id="state-law-id" class="errors" ></label>
									</td>
								</tr>
							</table>
						</div>
						<div  id="state-law-custdetails"> 
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-state-law-details" >
								<tr class="odd">
									<td width="20%"><label for="Number Plate"><strong> Received Amount <font color="red">*</font></strong></label></td>
									<td colspan="3"><input name="CV0023" autocomplete="off" type="text" class="field" id="stl-received-amt" required="required"  /> <span id="stl-received-amt_err" class="errmsg"></span></td>
									 
								</tr> 
							</table> 
						</div> 
					</fieldset>  
			<div class="form-actions" id="form5-sumbit"> 
				<input type="button" name="btn-make-payment" class="btn btn-success" id="btn-make-payment" value="Make Payment" /> 
			</div>
				</div>
			</div>
		</div>
	
	</form> 
	
	<form name="form6" id="form6" method="post" > 
		<div class="row-fluid sortable" id="nhif-information"> 
			<div class="box span12"> 
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>&nbsp;NHIF Member Information 
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					</div>
				</div> 		
				<div class="box-content">
					<fieldset>  
						<div id="nhif-details"> 
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="select"><strong>Select NHIF Service<font color="red">*</font></strong></label></td>
									<td colspan="3">
										<select  name="nhifservice" id="nhif-service" data-placeholder="Choose a Service..." 
											class="chosen-select" style="width: 220px; display: none;" required="required">
											<option value="select">(choose one)</option>
											<option value="CRDREP">Card Reprint</option>
											<option value="NHIFPAY">NHIF Payment</option>
										</select> &nbsp; <label id="nhif-id" class="errors" ></label>
									</td>
								</tr>
							</table>
						</div>
						<div  id="nhif-cust-details"> 
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-nhif-details" >
								<tr class="odd">
									<td width="20%"><label for="NHIF Member No"><strong>NHIF Member No<font color="red">*</font></strong></label></td>
									<td width="30%" ><input name="CV0025" autocomplete="off" type="text" class="field" id="nhif-member-no" required="required"  /> </td>
									<td width="20%"><label for="Member No"><strong>Member No <font color="red">*</font></strong></label></td>
									<td width="30%"><input name="CV0026" autocomplete="off" type="text" class="field" id="MemberNo" required="required"  /> </td>
								</tr>
								<tr class="odd">
									<td><label for="From Date"><strong>From Date<font color="red">*</font></strong></label></td>
									<td><input name="CV0027" type="text" autocomplete="off" class="field" id="nhif-from-date" required="required" /></td>
									<td><label for="To Date"><strong>To Date<font color="red">*</font></strong></label></td>
									<td><input name="CV0028" type="text" autocomplete="off" class="field" id="nhif-to-date"  required="required"  /></td>
								</tr> 
								<tr class="odd">
									<td><label for="Received Amount"><strong>Received Amount<font color="red">*</font></strong></label></td>
									<td><input name="CV0029" type="text" autocomplete="off" class="field" id="nhif-received-amount"  /> <span id="nhif-received-amount_err" class="errmsg"></span></td>
									<td><strong>&nbsp;</td>
									<td>&nbsp;</td>	
								</tr>  
							</table> 
						</div> 
					</fieldset>  
				<div class="form-actions" id="form6-sumbit"> 
					<input type="button" name="btn-make-payment" class="btn btn-success" id="btn-make-payment" value="Make Payment" /> 
				</div> 
				</div>
			</div>
		</div> 
	</form>		
</div>
	 
</body> 
</html>
