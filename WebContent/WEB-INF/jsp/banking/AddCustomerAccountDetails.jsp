<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %> 
 

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
var acctRules = {
		   rules : {
			   accountNumber : { required : true},
			   aliasName : { required : false}
		   },  
		   messages : {
			   accountNumber : { 
			   		required : "Please enter Account Number."
				},
				aliasName : { 
			   		required : "Please enter Alias Name."
				}
		   } 
		 };
		
		var subrules = {
				   rules : {
					   langugae : { required : true},
					   telephone : { required: true ,number : true, maxlength: 9, minlength:9}
				   },  
				   messages : {
					   langugae : { 
					   		required : "Please Select Language."
						},
						telephone : { 
					   		required : "Please Input Mobile Number."
						}
				   } 
				 };

		
		
		
		$(function() {  
			
			
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
				    
				    
				    $('#product1').on('change', function() {
				    	 // alert( this.value ); 
				    	  $('#desc').text((this.value).split("-")[1]);
				    	  $('#product').val((this.value).split("-")[0]);
				    	  $('#prodesc').val((this.value).split("-")[1]);
				    	});
				    
				    
				    var queryString = "";
		     		
			    	$.getJSON("productajx.action", queryString, function(data) {
			 			if(data.region!=""){
			 			//	alert(data.region);
			 				var mydata=data.region;
			      			//var mydata=(data.region).substring(5,(data.region).length);
			      			var mydata1=mydata.split(":");
			      
			       			$.each(mydata1, function(i, v) {
			       				var options = $('<option/>', {value: (mydata.split(":")[i]).split("@")[0], text: (mydata.split(":")[i]).split("@")[1]}).attr('data-id',i);
			       				
			       				$('#product1').append(options);
			       				$('#product1').trigger("liszt:updated");
			       			});
			       			
			       			
			      		} 
			     	}); 
				    
				    
});
 
$(function() {   
	
	
	// add multiple select / deselect functionality
	$("#select-all").click(function () {
		$("#error_dlno").text("");
		$('.center-chk').attr('checked', this.checked);
	});

	// if all checkbox are selected, check the selectall checkbox
	// and viceversa
	$(".center-chk").click(function(){
		$("#error_dlno").text("");
		if($(".center-chk").length == $(".center-chk:checked").length) {
			$("#select-all").attr("checked", "checked");
		} else {
			$("#select-all").removeAttr("checked");
		}
	});
	
	
	$('#btn-back').on('click', function(){
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/addNewAccountAct.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){ 
		var finalData = "";
		 var allVals = [];
		 
		$('#billerMsg').text('');
		$('#error_dlno').text(''); 
		$("#form1").validate(subrules);
		
		

		
		var queryString = "method=verifyMobile&mobile="+ $('#telephone').val();	
		$.getJSON("postJson.action", queryString,function(data) { 
			
			v_message = data.language;
			var v_cusid=v_message.split("-");
			var ccode=$('#customercode').val();
					
				
			if(v_message!=' ' && ccode!=v_cusid[0].trim()){
				
				alert("The Mobile Number Exists for Below \n \n"+v_message +" \n \n Please link this Account to the above Customer ID");
				
			}
			else{
				if($("#form1").valid()) {  
					 					
					 $('.center-chk:checked').each(function(){ 
						 allVals.push($(this).attr('id'));
						// alert(""+allVals);
					 }); 
					 
					 if ($('.center-chk:checked').length > 0 || $('#tbody_data > tr').length > 0)
					 {
					 
		 				 $('#multi-row-data > span').each(function(index) {  
			 				if(index == 0)  finalData = $(this).text();
							else finalData += "#"+ $(this).text();
						 }); 
							 			   
						 $("#newAccountData").val(finalData);   
					 
						 var locDat = "";
						 var mulDat = "";
						 var locData = [];
						 
						 $('#tbody_data1 > tr').each(function(ind){  
		  					 
			 				if(jQuery.inArray(ind.toString(),allVals) != -1){
			 					
			 					 var v_id= '#tbody_data1 > tr#'+$(this).attr('id');  
			 					 $(v_id+' > td').each(function(index){
			 						 console.log(v_id); 
			 						 if(index == 1 ){
			 							locDat=$(this).text().trim();
			  						 } else {
			  							locDat+=","+$(this).text().trim(); 
			 						}
				 				 });  
			 					 
			 					locData.push(locDat); 
			 					 console.log(locData);
				 			}  
						 }); 
						 
						 for(ind=0;ind < locData.length ;ind ++){
							 if(ind ==0){
								 mulDat=locData[ind];
							 }else {
								 mulDat+="#"+locData[ind];
							 }
							 console.log("multi data  [   "+mulDat);
						 } 
						 
						 $("#multiData").val(mulDat);
						 
						 if($("#product1").val()==""){
							 $('#error_dlno').text('Please select Product '); 
							 return false; 
						 }
						 
						 $("#dialog").dialog({
						      buttons : {
						        "Confirm" : function() { 
						        	
						        	$("#telco").attr("disabled", false);
							 		$("#telephone").prop("readonly", false);
							 		$("#langugae").attr("disabled", false);
							 		
						        	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/addNewAccountSubAct.action';
									$("#form1").submit();
						        },
						        "Cancel" : function() {
						           $(this).dialog("close");
						        }
						      }
						    });
						  
						   // $("#dialog").dialog("open");  
						 
						 return true; 
					  } else {
						 $('#error_dlno').text('Please select atleast one account.'); 
						 return false;
					 } 
					 
				 } else {
					 return false;
				 }
			}
		});
		
		
		 
		
	});
});	
	 
</script>
<script type="text/javascript"> 
/** Form2 Add,Modify Starts*/
var val = 1; 
var rowindex = 0;
var colindex = 0;
var userstatus = "";
var masterstatus = "";
var v_message = "";
var listid = "".split(",");
var headerList = "accountNumber,aliasName,acctName,branchCode,prdCode,acctype,institutesel".split(",");
var tabArry ; 
 
var index1 = "";
var searchTdRow = "";
var searchTrRows = "";
var selTextList = ""; 
var rowCount = 0;
 
function clearVals(){ 
	$('#accountNumber').val('');
	$('#aliasName').val(''); 
	
	 
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
 		
		$(id).find('input[type=text],input[type=hidden]').each(function(indxf){ 
			var nameInput = "";
			var valToSpn =  "";
			try {
				  nameInput = $(this).attr('name'); 
				  valToSpn = $(this).attr('value').length == 0 ? " " : $(this).attr('value'); 
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
					tabArry[tabarrindex] = valToSpn; 
 				  tabarrindex++;
			  }
			} 			 
		}); 
 		
	} catch (e) {
		console.log(e);
	}  
	return hiddenInput+"@@"+hiddenNames;
}

var addAccountInfo = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	 var tabArrText = ""; 
	 var data1 = "";
	try { 
		data1 = commonData('#account-details','ADD');
		 
		rowindex = $("#multi-row-data > span") .length ;
		$("#multi-row-data").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+tabArry[0]+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
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
		//alert(tabArrText);
		appendTxt = "<tr class="+addclass+" align='center' id='tr-"+rowindex+"' index='"+rowindex+"'>"+
				"<td class='col_"+colindex+"'>"+colindex+"</td>"+ tabArrText+ 
 				"<td> <a class='btn btn-min btn-danger' href='#' id='delete-account' index='"+rowindex+"' title='Delete Account' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";
 		
	} catch(e) {
		console.log(e);
	}   
	return appendTxt; 
};
 
/** Form2 Add,Modify Ends*/

function alignSerialNo(serialId) {  
	if($(serialId).length > 0) {
		$(serialId +' > td:first-child').each(function(index){  
			$(this).text(index+1);
		}); 
	}
}

function recordCheck(idtocheck,billerId,tid){ 
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
			 
			 var institutesel1=$('#institute').val();
			 if(institutesel1=='INSTID1')
				 {
				 institutesel1='FCUBS';
				 }
			 else if(institutesel1=='INSTID2')
				 { institutesel1='FCUBS';}
			 
			 $("#billerMsg").text('');
	 		$("#error_dlno").text('');	 
			$("#form2").validate(acctRules); 
			 var textMess = "#tbody_data > tr";  
			 var flag = false; 
			 
			  if($("#form2").valid() ) {  
					// Is To Check Account Exist's or not 
					var dat1 = recordCheck("#tbody_data1 > tr",$('#accountNumber').val(),'1');
					var dat = recordCheck("#tbody_data > tr",$('#accountNumber').val(),'2');
 					
					if(dat1 == 'NO'){
						if(dat == 'NO'){
						var queryString = "method=addnewaccounts&accNumber="+ $('#accountNumber').val()+"&custcode="+ $('#customercode').val()+"&institute="+ $('#institute').val()+"&makerid="+ $('#makerid').val();	
						$.getJSON("postJson.action", queryString,function(data) { 
							 
							/* <input type="hidden" id="acctName" name="acctName" />
							<input type="hidden" id="branchCode" name="branchCode" />
							<input type="hidden" id="prdCode" name="prdCode" /> */
							
							v_message = data.message;  
							   
							if(v_message == "SUCCESS") { 
								$("#acctName").val(data.accname);  
								$("#branchCode").val(data.brcode);  
								$("#prdCode").val(data.prdid);
								$("#acctype").val(data.accounttype); 
								$("#institutesel").val(institutesel1); 
								
								//alert(data.brcode);
								
								
								var appendTxt = addAccountInfo("ADD");
								//alert(appendTxt);
		 						$("#tbody_data").append(appendTxt);  
								clearVals();
								alignSerialNo(textMess); 
									$('#error_dlno').text('');
								loadToolTip();  
							} else {
								$('#billerMsg').text(v_message);
							} 
						});   
						} 
						else {
							$('#billerMsg').text(dat);
							} 
						} else {
						$('#billerMsg').text(dat1);
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
			$('#multi-row-data > span').each(function(index){  
				spanId =  $(this).attr('index'); 
				if(spanId == delId) {
					$(this).remove();
				}
			}); 
			
			clearVals();
			// Aligning the serial number
			alignSerialNo("#tbody_data > tr"); 
		 
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
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Mobile Banking Customer Registration</a>  </li> 
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
<form name="form1" id="form1" method="post"> 
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
							<td width="25%"><label for="From Date"><strong>Customer Id</strong></label></td>
							<td width="30%"><s:property value='accBean.customercode' /><input type="hidden" name="customercode"  id="customercode"   value="<s:property value='accBean.customercode' />"   /></td>
							<td width="25%"><label for="To Date"><strong>Customer Name</strong></label> </td>
							<td width="30%"><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<td ><label for="From Date"><strong>Branch Code</strong></label></td>
							<td ><s:property value='accBean.branchcode' /> <input type="hidden" name="branchcode"  id="branchcode"   value="<s:property value='accBean.branchcode' />"   />  </td>
							<td  ><label for="To Date"><strong>BVN</strong></label> </td>
							<td><s:property value='accBean.idnumber' /> <input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='accBean.idnumber' />"   />  </td>
						</tr>
						<tr class="even">
							<td ><label for="From Date"><strong>Email ID</strong></label></td>
							<td ><s:property value='accBean.email' /> <input type="hidden" name="email"  id="email"   value="<s:property value='accBean.email' />"   />  </td>
							<td ><label for="To Date"><strong>Preferred Language</strong></label> </td> 
							<td >
								<select id="langugae" name="langugae"  required='true' data-placeholder="Select Language." style="width: 220px;" class="chosen-select-width" >
								<option value="Sel">Select</option>
								<option value="English" Selected>English</option>
								</select> 
							</td>	  
						</tr>
						<tr class="even">
						   <td><label for="To Date"><strong>Mobile Number<font color="red">*</font></strong></label> </td>
					       <td >
						       	<input type="text" value="+234" readonly style="width:35px;" name="isocode" id="isocode"/>&nbsp;
						       	<input type="text" style="width:130px;" name="telephone"  id="telephone" maxlength="9" value="<s:property value='accBean.telephone' />"/> 
						        <input type="hidden" name="institute"  id="institute"   value="<s:property value='institute' />"   />
						       	<input type="hidden" name="newAccountData" id="newAccountData" value="<s:property value='accBean.newAccountData' />" />
								<input type="hidden" name="multiData"  id="multiData"   value="<s:property value='accBean.multiData' />"  />
								<input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
						   </td> 
						    <td><label for="Operator"><strong>Operator</strong></label></td>
					       <td>
					       		<select id="telco" name="telco"  required='true' data-placeholder="Select Telco."   class="chosen-select-width" >
						            <option value="" Selected>Select</option>
						            <option value="Airtel">Airtel</option>
						            <option value="Etisalat (EMTS)" >Etisalat (EMTS)</option>
						            <option value="Globacom">Globacom</option>
						            <option value="Starcomms" >Starcomms</option>
						            <option value="MTN Nigeria">MTN Nigeria</option>
						            <option value="Mtel (NITEL)" >Mtel (NITEL)</option>
						            <option value="Multilinks (Telkom)">Multilinks (Telkom)</option>
						             <option value="Visafone" >Visafone</option>
						            <option value="ZoomMobile">ZoomMobile</option>
						        </select> </td>  
 						</tr>
 						<tr class="even">
							<td ><label for="Product"><strong>Product<font color="red">*</font></strong></label></td>
							<td >
							<select id="product1" name="product1" class="chosen-select-width" >
							 <option value="">Select</option>
							</select>
							</td>
							 <td><label for="Description"><strong>Description</strong></label></td>
					       <td><div id="desc"></div></td>  
						</tr>
 												
				 </table>
				  <input type="hidden"  name="product" id="product" />
				 <input type="hidden"  name="prodesc" id="prodesc"  />
				</fieldset> 
				</div>
				</div> 
				
				
				
				<div class="row-fluid sortable"> 
			
						<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Existing Accounts
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
				<div class="box-content"> 
						<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
									id="ex-mytable" style="width: 100%;">
						  <thead>
								<tr>
									<th>Sno</th>
									<th>Account Number</th>
									<th>Account Name </th>
									<th>Branch Code</th>
									<th>Account Type</th>
									<th>Alias Name</th>
									<th>Status</th>
         							<th><input type="checkbox" class="checkbox" name="select-all" id="select-all" title="Select All"/></th>
								</tr>
						  </thead>    
						  <tbody id="tbody_data1"> 
						  
						  		<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{accBean.multiData}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
								</s:bean> 
								<s:iterator value="#jsonToList.data" var="mulData1"  status="mulDataStatus" > 
										<s:if test="#mulDataStatus.even == true" > 
											<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
										</s:if>
										<s:elseif test="#mulDataStatus.odd == true">
											<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
										</s:elseif> 
									
										<td><s:property value="#mulDataStatus.index+1" /></td>
											<s:generator val="%{#mulData1}"
												var="bankDat" separator="," >  
												<s:iterator status="itrStatus">  
												
												<s:if test="#itrStatus.count <= 6">   
							                	<td id='<s:property />'><s:property /></td> 
							                	</s:if>
							                	<s:if test="#itrStatus.count == 7">   
							                	<td style="display:none" id='<s:property />'><s:property /></td> 
							                	</s:if>
							                	<s:if  test="#itrStatus.count == 8">   
							                	<td style="display:none" id='<s:property />'><s:property /></td> 
							                	</s:if>
												</s:iterator>  
											</s:generator> 
							           <td ><input type="checkbox"  class='center-chk' name="<s:property value="#mulDataStatus.index" />"  id="<s:property  value="#mulDataStatus.index" />"/></td> 
									</tr> 
								</s:iterator>
						  
						  </tbody>
						</table> 
					</div> 
		</div> 
		</div> 
		</div> 	
</form>	
<form name="form2" id="form2" method="post"> 	
		<div class="row-fluid sortable" id="adnwac"> 
			<div class="box span12" > 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Add New Account
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
						
							<td width="20%"><label for="Account Number"><strong>Account Number<font color="red">*</font></strong></label></td>
							<td width="30%"><input type="text" maxlength="14"  class="field" id="accountNumber" name="accountNumber" required=true /></td>
							<td width="20%"><%-- <label for="Alias Name"><strong>Alias Name</strong></label>  --%></td>
							<td width="30%">
									<input type="hidden" id="acctName" name="acctName" />
									<input type="hidden" id="branchCode" name="branchCode" />
									<input type="hidden" id="prdCode" name="prdCode" />
									<input type="hidden" id="acctype" name="acctype" />
								<input type="hidden" maxlength="20" class="field" id="aliasName" name="aliasName" />
								<input type="hidden" name="institutesel"  id="institutesel" />
									<!-- <input type="hidden" id="status" name="status"  value="Not Reg"/> -->
							</td>
						</tr>  
						<tr class="odd"> 
								<td colspan="4" align="center">
									<input type="button" class="btn btn-success" 
										name="add-account" id="add-account" value="Add Account" /> &nbsp;  
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
									<th>Account Number</th>
									<th>Account Name </th>
									<th>Branch Code</th>
									<th>Account Type</th>
									<th>Product ID</th>
									<th>Alias Name</th>
 									<th>Core Banking</th>
									<th>Actions</th> 
								</tr>
						  </thead>    
							 <tbody id="tbody_data"> 
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
			
					<span id="multi-row-data" class="multi-row-data" style="display:none"> </span>
			  </div>
		</div>  
		
		<div id="dialog" title="Confirmation Required" style="display:none">
		   Proceed ? <!-- <div id="dia1"></div><font color="red"><div id="dia2"></div> --></font>
		</div>
		
</form>	
		<div > 
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">Back </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Submit</a>	
			<span id ="error_dlno" class="errors"></span>	 
		</div> 
		
	</div> 
 <script type="text/javascript">
$(function(){
	
	$('#accountNumber').live('keypress',function(){
		//console.log($(this).length);
		if($(this).length == 0){
			$('#billerMsg').text('');
		}
	});
	
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
$(function(){

	
	 $('#tbody_data1 > tr ').each(function() {
		 
		  var Registered = $.trim($(this).find("#Registered").html());
		  //alert('value::::::::'+Registered );
		  
		  if(Registered=='Registered')
			  {
			 		var myid = $(this).find("input:checkbox").attr("id");
			 		$("#"+myid).prop("disabled", true);
			 		$("#"+myid).removeClass("center-chk");
			 		$("#adnwac").hide();
			 		$("#telco").attr("disabled", true);
			 		$("#telephone").prop("readonly", true);
			 		$("#langugae").attr("disabled", true);
			 		
			  }
		  
		var Pending = $.trim($(this).find("#Pending").html());
		  
		  if(Pending=='Pending')
			  {
			 		var myid = $(this).find("input:checkbox").attr("id");
			 		$("#"+myid).prop("disabled", true);
			 		$("#"+myid).removeClass("center-chk");
			 		$("#adnwac").hide();
			 		
			 		
			  }
		  
		var Rejected = $.trim($(this).find("#Rejected").html());
		  
		  if(Rejected=='Rejected')
			  {
			 		var myid = $(this).find("input:checkbox").attr("id");
			 		$("#"+myid).prop("disabled", true);
			 		$("#"+myid).removeClass("center-chk");
			 		$("#adnwac").hide();
			 		
			 		
			  }
		  
	}); 
});

</script>
</body> 
</html>