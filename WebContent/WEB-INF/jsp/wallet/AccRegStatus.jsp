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
<link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.css' />	
<SCRIPT type="text/javascript">
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
	
	
$(document).ready(function(){   
	
	 
    $("#rmrk").cleditor();
    $("#comment").cleditor()[0].disable(true)

 
	
 	$('#btn-submit').live('click',function() {  
 		
 		var finalData = "";
 		
 		 $("#error_dlno").text(''); 
 		 
 		var rmrk= $('#rmrk').val(); 		
 		 $('#remark').val(rmrk);
 		var chk=$('#check').val();
 		
 		$('#multi-row > span').each(function(index) {  
				if(index == 0)  finalData = $(this).text();
				else finalData += "@"+ $(this).text();
			}); 
		 
		$('#multiData').val(finalData); 
 		//alert(finalData);
 		 
 		var searchIDs="";
		$("div#auth-data input:radio:checked").each(function(index) {
			searchIDs=$(this).val();			
			$('#DECISION').val(searchIDs);
		});
		
		
		if(chk=='E'){
			
					if(finalData.length > 0){
						
					var queryString9 = "method=updateAccAuthData&refno="+ $('#REF_NO').val()+"&custcode="+ $('#custcode').val()+"&institute="+$('#institute').val()+"&makerid="+ $('#makerid').val()+"&multidata="+ $('#multiData').val()+"&email="+ $('#email').val()+"&telco="+ $('#telco').val()+"&isocode="+ $('#isocode').val()+"&telephone="+ $('#telephone').val()+"&auth_code="+ $('#AUTH_CODE').val()+"&language="+ $('#langugae').val();
					
					$.getJSON("postJson.action", queryString9,function(data) {	v_message = data.message;	});
											
					}
				
					else {
					//$('#error_dlno1').text('Atleast One record should be present for confirmation');
					
					 $("#dialog").dialog({
				     			 buttons : {
					        "Confirm" : function() { 
					        	
					        	
			       	var queryString9 = "method=updateAccAuthData&refno="+ $('#REF_NO').val()+"&custcode="+ $('#custcode').val()+"&institute="+$('#institute').val()+"&makerid="+ $('#makerid').val()+"&multidata="+ $('#multiData').val()+"&email="+ $('#email').val()+"&telco="+ $('#telco').val()+"&isocode="+ $('#isocode').val()+"&telephone="+ $('#telephone').val()+"&auth_code="+ $('#AUTH_CODE').val()+"&language="+ $('#langugae').val();
			   		$.getJSON("postJson.action", queryString9,function(data) {v_message = data.message;}); 	
			   		
			   		$('#DECISION').val("D");
			   		
			   	 	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthRecordconfirm.action";
				 	$("#form1").submit();	
					        						},
					        "Cancel" : function() {
					           $(this).dialog("close");
					        					}
					      					}
					    				});
					
						}
				}
		if(finalData.length > 0){
			
			$("#form2").validate(remarkRules);
			if ($("#form2").valid()==true)
				{
				 $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthRecordconfirm.action";
				 $("#form1").submit();
				}
		}		

	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true; 
		
		
		var actype= $('#acttype').val(); 		
	if (actype=="REG"){
	 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/authreglist.action';		
	}
	else{
	 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action';
	}
		$("#form1").submit();		
	}); 
	
	$('#btn-edit').live('click',function() {  
		//alert();
		rowCount1 = $('#tbody_data > tr').length; 
		
		//alert(rowCount1);
		$('#check').val('E'); 
		for(var k=0;k<rowCount1;k++)
			{
			$("#hbut"+k).show();
			}
	
	$('#h20').show();
	$('#telco').removeAttr('disabled');
	$('#isocode').removeAttr('readonly');
	$('#telephone').removeAttr('readonly');
	$('#email').removeAttr('readonly');
	
	//vrefno,auth_code,v_MakerId,v_instituteid,mobil,multidata,v_email,language
	
		
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
var headerList = "accountNumber,aliasName,acctName,branchCode,prdCode,acctype".split(",");
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

	
		// Update Edited-Row Starts Here.
		$('#mod-biller').live('click', function () {
			$("#error_dlno").text('');	 
			//$("#form2").validate(form2Rules); 
			 var textMess = "#tbody_data > tr";  
			var flag = true;  
			if($("#form2").valid()) { 
				// Is To Check Account Exist's or not  
				//var dat1 = recordCheck(textMess,$('#billerId').val(),$('#bfubCreditAcctBillerId').val(),$('#bfubDebitAcctBillerId').val());
				//alert("modbiller");
				//if(dat1 == 'NO') { 
					var queryString = "method=addnewaccounts&accNumber="+ $('#accountNumber').val()+"&custcode="+ $('#custcode').val()+"&institute="+ $('#institute').val()+"&makerid="+ $('#makerid').val();	
				$.getJSON("postJson.action", queryString,function(data) {
						v_message = data.message;  
						
						//alert(v_message);
						
							if(v_message == "SUCCESS") {
								if(userstatus != null && masterstatus != null ) {   
										
									$("#acctName").val(data.accname);  
								    $("#branchCode").val(data.brcode);  
									$("#prdCode").val(data.prdid);
									$("#acctype").val(data.accounttype); 
									
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
	<form name="form1" id="form1" method="post" autocomplete="off">
		
			<div id="content" class="span10">  
			    <div> 
				<ul class="breadcrumb">
				 <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				 <li> <a href="AuthorizationAll.action">Authorization</a> <span class="divider"> &gt;&gt; </span></li>
				 <li><a href="#"> ${type} Authorization Details </a></li>
				</ul>
				</div>  

			<table height="3">
					<tr>
						<td colspan="3">
							<div class="messages" id="messages"><s:actionmessage /></div>
							<%-- <div class="errors" id="errors"><s:actionerror /></div> --%>
						</td>
					</tr>
				</table>
			
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
							<td width="20%"><label for="From Date"><strong>Customer Id</strong></label></td>
							<td width="30%"><s:property value='#respData.custcode' /><input type="hidden" name="custcode"  id="custcode"   value="<s:property value='#respData.custcode' />"   />
							<td width="20%"><label for="To Date"><strong>Customer Name</strong></label><input type="hidden" name="institute"  id="institute"   value="<s:property value='#respData.institute' />"   /> </td>
							<td width="30%"><s:property value='#respData.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='#respData.fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<td ><label for="To Date"><strong>Mobile Number</strong></label> </td>
							<td ><s:property value='#respData.mobileno' />
								<%-- <input type="text" value="<s:property value='#respData.telco' />" style="width: 60px;" readonly name="telco" id="telco" /> --%>
								<%-- <select id="telco" name="telco"  required='true' data-placeholder="Select Telco." style="width: 95px;" value="<s:property value='#respData.telco' />">
						            <option value="Sel">Select</option>
						            <option value="Safaricom" >Safaricom</option>
						            <option value="Airtel">Airtel</option>
						        </select>&nbsp; --%>
						        
						       <%--  <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'Safaricom':'Safaricom','Airtel':'Airtel'}" 
							         name="telco" 
							         value="#respData.telco" 
							         id="telco" 
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							         style="width: 95px;"
							         disabled="true"
							         
							           /> --%>
								
								<input type="hidden" value="<s:property value='#respData.isocode' />" style="width:25px;" readonly name="isocode" id="isocode"/>&nbsp;
								<input type="hidden" value='<s:property value='#respData.mobileno' />' style="width: 80px;" maxlength="9" name="telephone" id="telephone" readonly style="width:130px;" />
 							</td>					
							<%-- <td><label for="From Date"><strong>Branch Code</strong></label></td>
							<td><s:property value='#respData.institute' /><input type="hidden" name="branchcode"  id="branchcode"   value="<s:property value='branchcode' />"   />  </td> --%>
							<td><label for="To Date"><strong>BVN</strong></label> </td>
							<td><s:property value='#respData.nationalid' /><input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='#respData.nationalid' />"   />  </td>
						</tr>
						<tr class="even">
							<td><label for="From Date"><strong>Email ID</strong></label></td>
							<td><s:property value='#respData.email' />
							<input type="hidden" name="email"  id="email"   value="<s:property value='#respData.email' />"   />  </td>
							<td><label for="To Date"><strong>Preferred Language</strong></label> </td>
							<td><s:property value='#respData.language' /> <input type="hidden" name="langugae"  id="langugae"   value="<s:property value='#respData.language' />"   /> 
							
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
							 <input type="hidden" id="acttype" name="acttype" value="<s:property value='#respData.acttype' />" />
							  </td>
						</tr>
						<tr class="even">
							<td><label for="Product"><strong>Product</strong></label></td>
							<td><s:property value='#respData.product' /> <input type="hidden" name="product"  id="product"   value="<s:property value='#respData.product' />"   />  </td>
							<td><label for="Description"><strong>Description</strong></label> </td>
							<td><s:property value='#respData.prodesc' />
							<input type="hidden" value="<s:property value='#respData.prodesc' />" name="prodesc" id="prodesc" readonly style="width:130px;" /> </td>
						</tr>
					
				 </table>
				</fieldset> 
				</div>  
			<div id="dialog" title="Confirmation Required" style="display:none"> On Confirmation This Record will be Moved to Deleted List.</div>	
	  </div>
	  </div> 
	  
	</form>	  
	
	<form name="form2" id="form2" method="post"> 	
	  
	  <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Registered Accounts
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  
				
				
				<div class="box-content" id="biller-details" style="display:none">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
						
							<td width="20%"><label for="Account Number"><strong>Account Number<font color="red">*</font></strong></label></td>
							<td width="30%"><input type="text" maxlength="14"  class="field" id="accountNumber" name="accountNumber" required=true /></td>
							<td width="20%"><label for="Alias Name"><strong>Alias Name</strong></label> </td>
							<td width="30%">
									<input type="hidden" id="acctName" name="acctName" />
									<input type="hidden" id="branchCode" name="branchCode" />
									<input type="hidden" id="prdCode" name="prdCode" />
									<input type="hidden" id="acctype" name="acctype" />
								<input type="text" maxlength="20" class="field" id="aliasName" name="aliasName" />
									<!-- <input type="hidden" id="status" name="status"  value="Not Reg"/> -->
							</td>
						</tr>  
						<%-- <tr class="odd"> 
								<td colspan="4" align="center">
									<input type="button" class="btn btn-success" 
										name="add-account" id="add-account" value="Add Account" /> &nbsp;  
									<span id="billerMsg" class="errors"></span>
								</td> 
						</tr>  --%>
						<tr class="odd"> 
								<td colspan="4" align="center">
								 	<input type="button" class="btn btn-success" name="mod-biller" id="mod-biller" value="Update Account" style="display:none"/> &nbsp;
								 	<input type="button" class="btn btn-info" name="can-biller" id="can-biller" value="Cancel" style="display:none"/> &nbsp;
									<span id="billerMsg" class="errors"></span>
								</td> 
							</tr>
						
 				 </table>
				</fieldset> 
				</div> 
				
				
				
		  		<div class="box-content"  > 
		  			<span id="multi-row" name="multi-row" style="display:none"> </span> 
							<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
										id="ex-mytable" style="width: 100%;">
							  <thead>
									<tr>
										<th>Sno</th>
										<th>Account Number</th>
										<th>Account Name </th>
										<th>Branch Code</th>
										<th>Account Type</th>
										<th>Product ID</th>
										<th>Alias Name</th>
										<th id="h20" style="display:none">Actions</th>
 									</tr>
							  </thead>    
							  <tbody id="tbody_data">   
								  <s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{#respData.multiData}"/>  
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
										<td id="hbut<s:property value="#mulDataStatus.index" />" style="display:none"><a class='btn btn-min btn-info' href='#' id='edit-biller' index='<s:property value="#mulDataStatus.index" />' title='Edit Account' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;
											<a class='btn btn-min btn-warning' href='#' id='row-cancel' index='<s:property value="#mulDataStatus.index" />' title='Reset Account' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp; 
											<a class='btn btn-min btn-danger' href='#' id='delete-biller' index='<s:property value="#mulDataStatus.index" />' title='Delete Account' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a>
										</td>
										</tr>
									</s:iterator>
								 </tbody>
							</table>
							
					<div id="remarksInformation" class="box-content"> 								
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable " > 									  
								<tr > 
									<td width="15%"><label for="Remarks" valign="middle"><strong>Comments History</strong></label></td>
									<td width="85%"><textarea readonly  class="cleditor" name="comment" id="comment" ><s:property value="#respData.remarks" /></textarea></td> 
									
								</tr> 
						</table>
						</div>
				<div id="remarksInformation1" class="box-content"> 								
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable " > 									  
								<tr > 
									<td width="15%"><label for="Remarks1" valign="middle"><strong>Enter Remarks<font id="rmk" name="rmk"  color="red">*</font></strong></label></td>
									<td><textarea class="cleditor1" name="rmrk" id="rmrk" ></textarea><div id="errors" class="errors"></div></td> 
									
								</tr> 
						</table>
						</div>		
						
			</div>
		</div>	
		</div>  
			
		<div id="auth-data"> 
				<ul class="breadcrumb">
				 <li> <strong>Authorize&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio2"  class='center-chk' type='radio' value='A' />&nbsp&nbsp </li>
				 <li> <strong>Reject&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio1"  class='center-chk' type='radio' value='R' /> &nbsp&nbsp&nbsp</li>
				<li><div id="errors1" class="errors"></div></li>
				</ul>
				
				
				     <span id ="error_dlno" class="errors"></span>
	           
		</div>  
 					 
	 
 </form>
 <span id ="error_dlno1" class="errors"></span>
 
 
    	<div class="form-actions" >
	         <input type="button" class="btn btn-success"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>&nbsp;
	         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input> &nbsp;
	         <input type="button" class="btn btn-danger" name="btn-edit" id="btn-edit" value="Edit" width="100" style="display:none" ></input> &nbsp;
       </div>  
 
 <script src="${pageContext.request.contextPath}/js/jquery.cleditor.min.js"></script>
 <script type="text/javascript">
 
$(function(){
	

	var auth=$('#STATUS').val();
	var mid=$('#MID').val();
	var makrid=$('#makerid').val();
	//alert(mid+" other "+makrid);\
	//alert(auth);
	if ( auth == 'C'){
		$("#auth-data").hide();
		$("#btn-submit").hide();
		$("#rmrk").prop('disabled', true);
		// $("#remark").hide(); 
		// $("#remarkDetails").hide(); 
		$("#rmk").hide();
		$("#remarksInformation1").hide();
	}
	else if ( auth == 'D'){
		$("#auth-data").hide();
		$("#btn-submit").hide();
		$("#rmrk").prop('disabled', true);
		$("#rmk").hide();
		$("#remarksInformation1").hide();
	}
	
	else{
		if(mid == makrid )
		{			
			$("#auth-data").hide();
			$("#btn-edit").show();
			 $('#authradio1').attr('checked','checked');
			}
		$("#rmk").show();

	}
});	  
</script>
</body> 
</html>