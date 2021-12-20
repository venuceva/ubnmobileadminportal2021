
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString();%>
<%@taglib uri="/struts-tags" prefix="s"%> 

<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
input#abbreviation{text-transform:uppercase};
</style>
<s:set value="responseJSON" var="respData"/>
<SCRIPT type="text/javascript">


var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);



var rowCount1=0;
var remarkRules = {
		 errorElement: "div",
		 errorPlacement: function(error, element) {

          if ( element.is(":radio") ) {
         	 error.appendTo( "div#errors1" );
          }
          else{
         	 error.appendTo( "div#errors" );
               }
      },
			rules : { 
				rmrk : { required : true },
				authradio : { required : true }
			},		
			messages : { 
				rmrk : { 	required : "Please Enter Remarks."	},
				authradio : { required : "Please Select One Option." }
				
					} 
			
		};
		
function postData(actionName,paramString) {
	
	console.log("actionName [ "+actionName+" ]" );
	console.log("paramString [ "+paramString+" ]" );
		
	$('#form2').attr("action", actionName).attr("method", "post");
	
	var paramArray = paramString.split("&");
	 
	$(paramArray).each(function(indexTd,val) {
		var input = $("<input />").attr("type", "hidden").attr("name", val.split("=")[0]).val(val.split("=")[1].trim());
		$('#form2').append($(input));	 
	});
	$('#form2').submit();	
}
	
	
$(document).ready(function(){   
	
	
 	$('#btn-submit').live('click',function() {
 		
 		var finalData = "";
 		 $("#form2").validate(remarkRules);
 		 $("#error_dlno").text(''); 
 		 
 		var rmrk= $('#rmrk').val(); 		
 		 $('#remark').val(rmrk);
 		var chk=$('#check').val();
 		
 		$('#multi-row > span').each(function(index) {  
				if(index == 0)  finalData = $(this).text();
				else finalData += "@"+ $(this).text();
			}); 
		 
		$('#multiData').val(finalData); 
 		 
 		var searchIDs="";
		$("div#auth-data input:radio:checked").each(function(index) {
			searchIDs=$(this).val();			
			$('#DECISION').val(searchIDs);
		});
		if(chk=='E'){

		if(finalData.length > 0){
			
			var queryString9 = "method=updateAccAuthData&refno="+ $('#REF_NO').val()+"&custcode="+ $('#custcode').val()+"&institute="+$('#institute').val()+"&makerid="+ $('#makerid').val()+"&multidata="+ $('#multiData').val()+"&email="+ $('#email').val()+"&telco="+ $('#telco').val()+"&isocode="+ $('#isocode').val()+"&telephone="+ $('#telephone').val()+"&auth_code="+ $('#AUTH_CODE').val()+"&language="+ $('#langugae').val();
		
		$.getJSON("postJson.action", queryString9,function(data) {
		
			v_message = data.message;  
		});
		}
		
		else {
			$('#error_dlno').text('Atleast One record should be present for Confirmation');
			
		}
		}

		if(finalData.length > 0){
		 $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthRecordconfirm.action";
		 $("#form1").submit();	 
		}
		else {
			$('#error_dlno').text('Atleast One record should be present for confirmation');
			
		}
	}); 
 	
 	
 	$('#pinreset').click(function(){ 
		if($("#form1").valid()) {
			
			var custid1=$('#custcode').val();
			
			//alert(custid1);
			$('#accountid').val(custid1);
			$('#closed').val('resetpin');
			
			
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/pinresetstatus.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		}
});
 
 	$('#pinresend').click(function(){ 
		if($("#form1").valid()) {
			
			var custid2=$('#custcode').val();
			
			//alert(custid2);
			
			$('#accountid').val(custid2);
			$('#closed').val('resendpin');
			
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/resendpin.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		}
});
 	$('#pinunblock').click(function(){ 
		if($("#form1").valid()) {
			
			var custid3=$('#custcode').val();
			
			//alert(custid2);
			
			$('#accountid').val(custid3);
			$('#closed').val('unblockpin');
			
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/resendpin.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		}
});
 	$('#disablecust').click(function(){ 
		if($("#form1").valid()) {
			
			var custid5=$('#custcode').val();
			
			//alert(custid2);
			
			$('#accountid').val(custid5);
			$('#closed').val('enabledisablecust');
			
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/enabledesablecustomerstatus.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		}
});
 	
 	
	$('#chanelenbdisussd').click(function(){ 
		if($("#form1").valid()) {
			
			var custid5=$('#custcode').val();
			
			//alert(custid2);
			var vv="";
			
			var ussdstatus=$('#ussdstatus').val();
			if (ussdstatus=='Active')
	    	{
				vv="chanelenbdisussddisable";
	    	}
		    else{
		    	vv="chanelenbdisussdenable";
		    }
			
			$('#accountid').val(custid5);
			$('#closed').val(vv);
			
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/customerchanelenbdis.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		}
});
	$('#chanelenbdismb').click(function(){ 
		if($("#form1").valid()) {
			
			var custid5=$('#custcode').val();
			
			//alert(custid2);
			var vv="";
			var mobilestatus=$('#mobilestatus').val();
			if (mobilestatus=='Active')
	    	{
				vv="chanelenbdismbdisable";	
	    	}
		    else{
		    	vv="chanelenbdismbenable";
		    }
			
			$('#accountid').val(custid5);
			$('#closed').val(vv);
			
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/customerchanelenbdis.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		}
});
	
 	$('#passwordreset').click(function(){ 
		if($("#form1").valid()) {
			
			var custid6=$('#custcode').val();
			
			//alert(custid2);
			
			$('#accountid').val(custid6);
			$('#closed').val('passwordreset');
			
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/passwordreset.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		}
});
 	
 	$('#enablecust').click(function(){ 
		if($("#form1").valid()) {
			
			var custid6=$('#custcode').val();
			
			//alert(custid6);
			
			$('#accountid').val(custid6);
			$('#closed').val('enablecust');
			
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/enabledesablecustomerstatus.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		}
});
 	
 	
 	$('#actdeactimei').click(function(){ 
 		var queryString = "method=ValidationDevice&fname="+ $('#custcode').val();	
		$.getJSON("postJson.action", queryString,function(data) { 
			
			
		if(data.message!="SUCCESS"){
			
			
			$('#errors').text(data.message);
		}else{
 	
		if($("#form1").valid()) {
			
			var custid6=$('#custcode').val();
			
		
			var v_action = "fetchDeactIMEI";
			var queryString = 'accountid='+custid6+'&closed=actdeactimei'; 
			postData(v_action+".action",queryString);
		
			 return true; 
		} else { 
			return false;
		}
	}
 });
});
 	
 	
 	
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true; 
		
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/customeStatusUpdate.action";
		$("#form1").submit();		
	}); 
	
 
});

</script> 


<script type="text/javascript"> 

/** ********************************************************************************************************** */
/**  Add,Modify Starts*/
var val = 1; 
var rowindex = 0;
var colindex = 0;
var userstatus = "";
var masterstatus = "";
var v_message = "";
var listid = "".split(",");
var headerList = "accountNumber,aliasName,acctName,branchCode,prdCode".split(",");
var tabArry ; 
var modTabArry ;  
var modHeaderBodyArry ;  
var index1 = "";
var searchTdRow = "";
var searchTrRows = "";
var selTextList = ""; 

var rowCount = 0;
function clearVals(){ 
	$('#accountNumber').val('');
	$('#aliasName').val('');
	/* $('#bfubCreditAcctBillerId').val('');
	$('#bfubDebitAcctBillerId').val(''); */
	
	 
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

/**  Add,Modify Ends*/

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
	
	 var rowCount2 = $('#tbody_data2 > tr').length;
	 if (rowCount2==0)
		 {
		 $('#tbody_data2').hide();
		 
		 }
	 

	
		// Update Edited-Row Starts Here.
		$('#mod-biller').live('click', function () {
			$("#error_dlno").text('');	 
			 var textMess = "#tbody_data > tr";  
			var flag = true;  
			if($("#form2").valid()) { 
				// Is To Check Account Exist's or not  
				//var dat1 = recordCheck(textMess,$('#billerId').val(),$('#bfubCreditAcctBillerId').val(),$('#bfubDebitAcctBillerId').val());
				//if(dat1 == 'NO') { 
					var queryString = "method=addnewaccounts&accNumber="+ $('#accountNumber').val()+"&custcode="+ $('#custcode').val()+"&institute="+ $('#institute').val()+"&makerid="+ $('#makerid').val();	
				$.getJSON("postJson.action", queryString,function(data) {
						v_message = data.message;  
						
							if(v_message == "SUCCESS") {
								if(userstatus != null && masterstatus != null ) {   
										
									$("#acctName").val(data.accname);  
								    $("#branchCode").val(data.brcode);  
									$("#prdCode").val(data.prdid);  
									
										if(flag) { 
											
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
												}if(index == 5){
													$(this).text(modTabArry[4]);
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
											$('#biller-details').hide();
										
										} else {
											 $('#billerMsg').text('');
											 $('#error_dlno').text('');
										} 
									}
							} else {
								$('#billerMsg').text(v_message);
							} 
					}); 
				/* } else {
					  
					$("#error_dlno").text('');
					$('#billerMsg').text(dat1);
			 	}  */
			} else {
				return false;				
			}  
		}); 
		
		// The below event is to Edit row on selecting the delete button 
		$('#edit-biller').live('click',function() { 
			$("#form2").validate().cancelSubmit = true;
			
			
			
			$('#biller-details').show();
			
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
			
			//alert(hidnames);
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
			$('#can-biller').show();
			
			
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
			$('#biller-details').hide();
			//can-biller
			
			
			$('.tooltip').remove();
			$('#billerMsg').text('');
			//$('#error_dlno').text('');
		}); 
		
		// Clear Form Records Row Starts Here.
		$('#row-cancel').live('click', function () {
			$("#error_dlno").text(''); 
			 clearVals(); 
			 
			//Show Add Button and Hide Update Button
			$('#mod-biller').show();
		
		}); 
		
		$('#can-biller').live('click', function () {
			$("#error_dlno").text(''); 
			 clearVals(); 
			 
			//Show Add Button and Hide Update Button
			$('#mod-biller').hide();
			$('#biller-details').hide();

		}); 
		
		
		// To do regular expression
		
		//([a-zA-Z0-9_-]*) 
		$('#billerType').live('focus',function (e) {
 			$('#regex').val(regPrepare($(this).val().toUpperCase())); 
		}).live('blur',function(e) { 
			$('#regex').val(regPrepare($(this).val().toUpperCase()));
		});
		
		//edit biller
		
			
			$(document).on('click','a',function(event) {
			
			var v_id=$(this).attr('id');
			var queryString = ''; 
			var v_action ="";
			var index1 = $(this).attr('index');  
			var parentId =$(this).parent().closest('tbody').attr('id'); 
			var searchTrRows = parentId+" tr"; 
			var searchTdRow = '#'+searchTrRows+"#tr-"+index1 +' > td';
			console.log(searchTdRow);
			var disabled_status= $(this).attr('disabled');  
			
			if(disabled_status == undefined) { 
				
				if(v_id =="actdeactacc") {
						$(searchTdRow).each(function(indexTd) {  
							if (indexTd == 1) {  
								billerType=$(this).text().trim();
								console.log(billerType);
								v_action = "fetchServiceActDeact";
								queryString += 'accountid='+billerType+'&closed='+v_id; 
							 }   
						}); 
 					  
				} 			
				else if(v_id ==  "resendpin" ) {
						$(searchTdRow).each(function(indexTd) {  
							
							if (indexTd == 1) {  
								billerType=$(this).text().trim();
								v_action = "resendpin";
								queryString += 'accountid='+billerType+'&closed='+v_id; 
							 }   
						}); 
 					  
				} 			
				else if(v_id ==  "resetpin" ) {
						$(searchTdRow).each(function(indexTd) {  
							
							if (indexTd == 1) {  
								billerType=$(this).text().trim();
								v_action = "resetpin";
								queryString += 'accountid='+billerType+'&closed='+v_id; 
							 }   
						}); 
 					  
				} 			
				else if(v_id ==  "actdeactimei" ) {
						$(searchTdRow).each(function(indexTd) {  
							
							if (indexTd == 1) {  
								billerType=$('#custcode').val();
								v_action = "fetchDeactIMEI";
								queryString += 'accountid='+billerType+'&closed='+v_id; 
							 }   
						}); 
 					  
				} 			
			 
				} else {
				
					// No Rights To Access The Link 
				}
			
			if(v_action != "NO") {
				
				console.log("v_action  ["+v_action);
				console.log("queryString  []"+queryString);
				
				postData(v_action+".action",queryString);
				
				
			}
		
			
		}); 
			
		
});

function regPrepare(val){
	var str=")([a-zA-Z0-9_-]*)";
	var str1="(";
	
	return str1+val+str;	
}




/**  Add Row Validation Ends*/
/** ********************************************************************************************************** */
 
</script>


</head> 
<body>

<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Customer Status Update</a>  </li> 
 					</ul>
				</div>  

				<table>
					<tr>
						<td colspan="3">
							<div class="messages" id="messages"><s:actionmessage /></div>
							<div class="errors" id="errors"><s:actionerror /></div>
						</td>
					</tr>
				</table>
	<form name="form1" id="form1" method="post" autocomplete="off">
		
  <div class="row-fluid sortable"> 
	<div class="box span12"> 
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Customer Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>  
				<div class="box-content">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
							<td width="20%"><label for="user id"><strong>User Id</strong></label></td>
							<td width="30%"><s:property value='accBean.userid' /><input type="hidden" name="userid"  id="userid"   value="<s:property value='accBean.userid' />" /></td>
							<td width="20%"><label for="From Date"><strong>Customer Id</strong></label></td>
							<td width="30%"><s:property value='accBean.customercode' /><input type="hidden" name="custcode"  id="custcode"   value="<s:property value='accBean.customercode' />" /></td>
							
						</tr>
						<tr class="even">
							<td width="20%"><label for="To Date"><strong>Customer Name</strong></label><input type="hidden" name="institute"  id="institute"   value="<s:property value='accBean.institute' />"   /> </td>
							<td width="30%"><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   />  </td>
						<td><label for="To Date"><strong>Mobile Number</strong></label> </td>
							<td><s:property value='accBean.telephone' />
							<input type="hidden" value="<s:property value='accBean.telco' />" style="width:85px;" readonly name="telco" id="telco"/>&nbsp;
								<input type="hidden" value="<s:property value='accBean.isocode' />" style="width:35px;" readonly name="isocode" id="isocode"/>&nbsp;
								<input type="hidden" value='<s:property value='accBean.telephone' />' style="width: 80px;"  name="telephone" id="telephone" readonly style="width:130px;" />
 							</td>
						</tr>  
						<tr class="even">
							
							<td><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value='accBean.idnumber' /><input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='accBean.nationalid' />"   />  </td>
							<td><label for="From Date"><strong>Email ID</strong></label></td>
							<td><s:property value='accBean.email' /><input type="hidden" name="email"  id="email" readonly  value="<s:property value='accBean.email' />"   />  </td>
							
						</tr>
						<tr class="even">
							<td><label for="To Date"><strong>Primary Account No</strong></label> </td>
							<td><s:property value='accBean.accountno' /> <input type="hidden" name="langugae"  id="langugae"   value="<s:property value='accBean.langugae' />"   />  
							
									 <input type="hidden" name="STATUS" id="STATUS" value="${STATUS}" />
		  							 <input type="hidden" name="AUTH_CODE"  id="AUTH_CODE" value="${AUTH_CODE}"  />
									 <input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
									 <input type="hidden" name="DECISION" id="DECISION" />
									 <input type="hidden" name="remark" id="remark" />
									 <input type="hidden" name="type" id="type" value="${type}"/>
									 <input type="hidden" name="makerid" id="makerid" value="${makerId}"/>
									 <input type="hidden" name="MID" id="MID" value="${MID}"/>
									 <input type="hidden" name="multiData" id="multiData"/>
									 <input type="hidden" name="check" id="check"/>
									 <input type="hidden" id=closed name="closed" />
									 <input type="hidden" id="accountid" name="accountid" />
									 <input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
							</td>
							<td width="20%"><label for="From Date"><strong>On-Boarded Date</strong></label></td>
							<td width="30%"><s:property value='accBean.authDttm' /><input type="hidden" name="authDttm"  id="authDttm"   value="<s:property value='accBean.authDttm' />" />
							<input type="hidden" name="institute"  id="institute"   value="<s:property value='accBean.institute' />" />
							</td>
							
						</tr>
						
						<tr class="even">
							<td width="20%"><label for="Customer Status"><strong>Customer Status</strong></label></td>
							<td width="30%"><s:property value='accBean.status' /><input type="hidden" name="status"  id="status"   value="<s:property value='accBean.status' />" /></td>
							<td width="20%"><label for="From Date"><strong>Pin Status</strong></label></td>
							<td width="30%"><s:property value='accBean.clearedbalance' /><input type="hidden" name="clearedbalance"  id="clearedbalance"   value="<s:property value='accBean.clearedbalance' />" /></td>
						</tr> 
						<tr class="even">
							<td width="20%"><label for="Product"><strong>Product</strong></label></td>
							<td width="30%"><s:property value='accBean.product' /></td>
							<td width="20%"><label for="Description"><strong>Description</strong></label></td>
							<td width="30%"><s:property value='accBean.prodesc' /></td>
						</tr>
						  </table>
						  
						  <input type="button" class="btn btn-danger" name="disablecust" id="disablecust" value="Disable Customer" width="100" style="display:none" ></input> &nbsp;
	         					<input type="button" class="btn btn-success" name="enablecust" id="enablecust" value="Enable Customer" width="100" style="display:none" ></input> &nbsp;
					</fieldset>
					
				</div>  
				
	  </div>
	  </div> 
	  

  <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Customer Using Channels
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div> 
				
			 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
							<td width="20%"><label for="user id"><strong>USSD</strong></label></td>
							<td width="30%"><s:property value='accBean.ussdstatus' /><input type="hidden" name="ussdstatus"  id="ussdstatus"   value="<s:property value='accBean.ussdstatus' />" /></td>
							<td width="20%"><label for="From Date"><strong>MOBILE</strong></label></td>
							<td width="30%"><s:property value='accBean.mobilestatus' /><input type="hidden" name="mobilestatus"  id="mobilestatus"   value="<s:property value='accBean.mobilestatus' />" /></td>
							
						</tr>
						<tr class="even">
						<td colspan="4">
						<input type="button" class="btn btn-success" name="chanelenbdisussd" id="chanelenbdisussd"  width="100" ></input> &nbsp;&nbsp;&nbsp;
						<input type="button" class="btn btn-success" name="chanelenbdismb" id="chanelenbdismb"  width="100" ></input> &nbsp;
						
						</td>
						</tr>
				</table>	
		</div>
		</div>
	</form>	  
	
	
	
	
	
	
	
 <form id="form3" name="form3"></form>
 
 <span id ="error_dlno" class="errors"></span>
 
 
    	<div class="form-actions" >
	         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input> &nbsp;
	         
       </div>  
 
 <script src="${pageContext.request.contextPath}/js/jquery.cleditor.min.js"></script>
 <script type="text/javascript">
 $(function(){
		
		$('#accountNumber').live('keypress',function(){
			//console.log($(this).length);
			if($(this).length == 0){
				$('#billerMsg').text('');
			}
		});
		
		var ussdstatus=$('#ussdstatus').val();
		
		if (ussdstatus=='Active')
    	{
			$('#chanelenbdisussd').val("Disable USSD");	
    	}
	    else{
	    	$('#chanelenbdisussd').val("Enable USSD");	
	    }
		
		var mobilestatus=$('#mobilestatus').val();
		if (mobilestatus=='Active')
    	{
			$('#chanelenbdismb').val("Disable MOBILE");	
    	}
	    else{
	    	$('#chanelenbdismb').val("Enable MOBILE");	
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
	    
	    var pinstat=$('#clearedbalance').val();
	    //alert(pinstat);
	    
	    if (pinstat=='Locked Active Pin (max tries)')
	    	{
	    	$("#pinunblock").show();
	    	}
	    //alert($('#status').val());
	    var pinstat1=$('#status').val();
	    //alert(pinstat1);
	    
	    if (pinstat1=='Active')
	    	{
	    	$("#disablecust").show();
	    	}
	    else{
	    	$("#enablecust").show();
	    }
	    
	    $.each(jsonLinks, function(index, v) {
	    	$("#enablecust").css("display", "none");
			$("#disablecust").css("display", "none"); 
			
			$("#hbut0").css("display", "none");
			
			
			//alert(v.name); 
	    	$("#"+v.name).css("display", "");
	    	
	    	if(v.name="disablecust"){
	    		if (pinstat1=='Active')
		    	{
		    	$("#disablecust").show();
		    	}
			    else{
			    	$("#enablecust").show();
			    } 
	    	} 
	    		
	    	
	    	
	    	if(v.name="actdeactacc12"){
	    		$("#actdeactacc12").css("display", "");
	    		for(i=0;i<=100;i++){
	    			$("#hbut"+i).css("display", "");
	    		}
	    		
	    		
	    		
	    	}
	    });
	    
	    
	});
 
 
 
</script>
</body> 
</html>