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
					   telephone : { required: true }
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
	
/* 	$("#dialog").dialog({
			autoOpen: false,
			modal: false,

	      
   }); */
	
   
   $('#telco').val('<s:property value="accBean.telco" />');
   $('#telco').trigger("liszt:updated");

   $('#langugae').val('<s:property value="accBean.langugae" />');
   $('#langugae').trigger("liszt:updated");
   
   
  
		
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
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modregcustinfo.action'; 
		$("#form1").submit();
		return true;
	});

	$('#btn-submit').on('click', function(){ 
		var finalData = "";
		
		 var allVals = [];
		 
		//$('#billerMsg').text('');
		//$('#error_dlno').text(''); 
		$("#form1").validate(subrules);
		
		
		
		
		
		var queryString = "method=searchAuth&fname="+ $('#customercode').val()+"&mnumber=WMODCUSTDETAUTH";	
		$.getJSON("postJson.action", queryString,function(data) { 
			
			
		if(data.message!="SUCCESS"){
			
			
			$('#errors').text(data.message);
		}else{
		

		
		
		
		 if($("#form1").valid() ) {  
			 
			 
			
			
				 $("#dialog").dialog({
				      buttons : {
				        "Confirm" : function() { 
				        	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modregwalletcustinfoSubAct.action';
							$("#form1").submit();
				        },
				        "Cancel" : function() {
				           $(this).dialog("close");
				        }
				      }
				    });
				  
				  //  $("#dialog").dialog("open");  
				 
				 return true; 
				
				
			 
		 } else {
			 //alert("in else");
			 return false;
		 }
			 
		}
	 });
	
	});
	
});	




$(function() { 
	
	
	/* $('#'+$('#smstoken').val()).attr ( "checked" ,"checked" );
	$('#Yes').on('click', function() {
		$('#smstoken').val("Yes");
	});
	
	$('#No').on('click', function() {
		$('#smstoken').val("No");
	}); */
	
	$('#chproduct').on('click', function() {
	if($("#chproduct").is(':checked')){
		$("#chproduct1").css("display","");
		$("#changeproduct").val("YES");
		
	}else{
		$("#chproduct1").css("display","none");
		$("#changeproduct").val("NO");
	}
	
	});
	
	
	if($("#userid").val()!=" "){
		$("#chuserid2").css("display","");
	$('#chuserid').on('click', function() {
		if($("#chuserid").is(':checked')){
			$("#chuserid1").css("display","");
			$("#changenewuserid").val("YES");
		}else{
			$("#chuserid1").css("display","none");
			$("#changenewuserid").val("NO");
		}
		
		});
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
	       				if((mydata.split(":")[i]).split("@")[2]=="Mobile Banking"){
	       				var options = $('<option/>', {value: (mydata.split(":")[i]).split("@")[0], text: (mydata.split(":")[i]).split("@")[1]}).attr('data-id',i);
	       				}
	       				$('#product1').append(options);
	       				$('#product1').trigger("liszt:updated");
	       			});
	       			
	       			
	      		} 
	     	}); 
	  //alert($('#product').val());  	
	    	
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
var headerList = "accountNumber,aliasName,acctName,branchCode,prdCode".split(",");
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
								
								var appendTxt = addAccountInfo("ADD");
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
					  <li> <a href="#">Modify Customer Details</a>  </li> 
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
							<td width="25%"><s:property value='accBean.customercode' /><input type="hidden" name="customercode"  id="customercode"   value="<s:property value='accBean.customercode' />"   /></td>
							<td width="25%"><label for="To Date"><strong>Customer Name</strong></label> </td>
							<td width="25%"><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<%-- <td ><label for="From Date"><strong>Branch Code</strong></label></td>
							<td ><s:property value='accBean.branchcode' /> <input type="hidden" name="branchcode"  id="branchcode"   value="<s:property value='accBean.branchcode' />"   />  </td> --%>
							<td><label for="To Date"><strong>Mobile Number<font color="red">*</font></strong></label> </td>
					       <td ><s:property value='accBean.telephone' /><input type="hidden" value='<s:property value='accBean.telephone' />'  name="telephone" id="telephone" /> 
								
								<input type="hidden" value="<s:property value='accBean.telco' />" style="width:85px;"  name="telco" id="telco"/>&nbsp;
								<input type="hidden" value="<s:property value='accBean.isocode' />" style="width:25px;"  name="isocode" id="isocode"/>&nbsp;
								 
 							</td>
								
 							</td>
							<td  ><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value='accBean.idnumber' />
							<input type="hidden" style="width:85px;"  name="idnumber"  id="idnumber"   value="<s:property value='accBean.idnumber' />"   />  </td>
					
						</tr>
						<tr class="even">
							<td ><label for="From Date"><strong>Email ID</strong></label></td>
							<td ><input type="text" name="email"  id="email"   value="<s:property value='accBean.email' />"   />  </td>
							<td ><label for="To Date"><strong>Preferred Language </strong></label> </td> 
							<td ><s:property value="accBean.langugae" />
							<input type="hidden" name="langugae"  id="langugae"   value="<s:property value='accBean.language' />"   />  
							
							         
							    <input type="hidden" name="institute"  id="institute"   value="<s:property value='accBean.	institute' />"   />
						       	<input type="hidden" name="newAccountData" id="newAccountData" value="<s:property value='accBean.newAccountData' />" />
								<input type="hidden" name="multiData"  id="multiData"   value="<s:property value='accBean.multiData' />"  />
								<input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
							</td>	  
						</tr>
						<tr class="even">
							<td ><label for="Product"><strong>Current Product</strong></label></td>
							<td ><s:property value='accBean.product' />
							</td>
							 <td><label for="Description"><strong>Description</strong></label></td>
					       <td><s:property value='accBean.prodesc' /></td>  
						</tr>
						
						<%-- <tr class="even">
							<td ><label for="Product"><strong>SMS Token</strong></label></td>
							<td ><input type="radio" name="sms" id="Yes" /><strong>Yes</strong> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
							<input type="radio" name="sms" id="No" /><strong>No</strong>
							</td>
							<td></td>
					       	<td></td>  
						</tr> --%>
						
						
						<tr class="even">
							<td width="25%"><label for="Product"><strong>Product</strong></label></td>
							<td width="25%">
							<select id="product1" name="product1" class="chosen-select-width" >
							 <option value="">Select</option>
							</select>
							</td>
							 <td width="25%"><label for="Description"><strong>Description</strong></label></td>
					       <td width="25%"><div id="desc"></div></td>  
						</tr> 
						
				 </table>
				 <input type="hidden"   name="apptype"  id="apptype"   value="<s:property value='accBean.apptype' />"   />
				 <input type="hidden"   name="smstoken"  id="smstoken"   value="<s:property value='accBean.smstoken' />"   />
				</fieldset> 
				</div>
				
				
			
				
				
				
				
				</div> 
				
				
				
				
				
					<input type="hidden"  name="product" id="product"  value="<s:property value='accBean.product' />" />
				 	<input type="hidden"  name="prodesc" id="prodesc"  value="<s:property value='accBean.prodesc' />" />
				 	
				 	<input type="hidden"  name="changeproduct" id="changeproduct"  value="NO" />
				 	<input type="hidden"  name="changenewuserid" id="changenewuserid"  value="NO" />
				 	
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
														<td><s:property /></td> 
												</s:iterator>  
											</s:generator> 
										 
									</tr> 
								</s:iterator>
						  
						  </tbody>
						</table> 
					</div> 
		</div> 
		</div> 
		</div> 	
</form>	


<div id="dialog" title="Confirmation Required" style="display:none">
		   Proceed ? <div id="dia1"></div><font color="red"><div id="dia2"></div></font>
		</div>
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
</script>
</body> 
</html>