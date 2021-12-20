<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
 <%@taglib uri="/struts-tags" prefix="s"%> 
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

  
<script type="text/javascript" > 
  
var val = 1;
var rowindex = 0;
var colindex = 0;
var bankAcctFinalData="";
var binstatus = "";
var v_message = "";

$(document).ready(function() {
	$("#accountTypeseraa").css({"display":"none"});
	$("#accountType").css({"display":"none"});
	$('#billerName').attr('readonly', true);
	$('#billerCode').attr('readonly', true);

	if("${responseJSON.servicecode}"=="CUSTPAYBILL"){
		$("#accountType").css({"display":"none"});
		$("#accountTypeseraa").css({"display":""});
		$('#billerName').attr('readonly', true);
		$('#billerCode').attr('readonly', true);
		
	}else{
		$("#accountType").css({"display":""});
		$("#accountTypeseraa").css({"display":"none"});
		$('#billerName').attr('readonly', false);
		$('#billerCode').attr('readonly', false);
	}
	
	
	$('#accountTypeser').on('change', function (e) {
		var valueSelected = this.value;
		//alert(valueSelected);
		
		$('#billerCode').val(valueSelected.split("-")[1]);
		$('#billerName').val(valueSelected.split("-")[2]);
		$('#accountType').val(valueSelected.split("-")[0]);
		
		
	});	
	
	
		
	// The below event is to delete the entire row on selecting the delete button 
		$('#delete').live('click',function() {  
			var delId = $(this).attr('index');
			$(this).parent().parent().remove();
			if($('#bankAcctData > tbody  > tr').length == 0) { rowindex = 0; colindex=0; }
			
			//console.log(delId );
			var spanId = "";
			$('#multi-row-data > span').each(function(index){  
				spanId =  $(this).attr('index');
				if(spanId == delId) {
					$(this).remove();
				}
			}); 
		}); 
		
		
		// The below event is to Edit row on selecting the edit button 
		$('#editDat').live('click',function() { 
			$("form").validate().cancelSubmit = true; 
			var v_id=$(this).attr('id');
			
			var index1 = $(this).attr('index');  
 			var parentId =$(this).parent().closest('tbody').attr('id'); 
			var searchTrRows = parentId+" tr"; 
			var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
			
			$(searchTdRow).each(function(index) {  
				 
			}); 
			
		}); 
		
		$('#btn-back').live('click', function () { 
			$("form").validate().cancelSubmit = true; 
			var url="${pageContext.request.contextPath}/<%=appName %>/newPayBillAct.action"; 
			$("#form1")[0].action=url;
			$("#form1").submit(); 
		});
		
		
			
		$('#btn-submit').live('click', function () {  
			var rowCount = $('#tbody_data > tr').length; 
			 $("#error_dlno").text('');
			if(rowCount > 0) {
				$("#form1").validate().cancelSubmit = true;
				var specChar = "";
				var prevCount = "";
				$('#multi-row-data > span').each(function(index){  
					if(index == 0)  finalData = $(this).text();
					else finalData += "#"+ $(this).text();
				}); 
				//alert(finalData);
				$('#bankMultiData').val(finalData);
 				var url="${pageContext.request.contextPath}/<%=appName %>/createBillerAccountSubmitAct.action"; 
				$("#form1")[0].action=url;
				$("#form1").submit(); 
			} else { 
				$("#error_dlno").text("Please add atleast one record.");
			}
		});
		
		

	$('#addCap2').on('click', function () {
		
		$("#error_dlno").text('');	
			
		 var registerBinRules = {
		   rules : {
			   billerId : { required : true},
			   accountNumber : { required : true },
			   accountName : { required : true }, 
			   accountType : { required : true },
			   billerCode : { required : true},
			   billerName : { required : true}
		   },  
		   messages : {
			    billerId : { 
			   		required : "Biller Id Missing."
				},
				accountNumber : { 
			   		required : "Please enter Account Number."
				},
				accountName : { 
			   		required : "Please enter Account Name."
				},
				accountType : { 
			   		required : "Please enter Account Type."
				},
				billerCode : { 
			   		required : "Please enter Biller Id."
				},
				billerName : { 
			   		required : "Please enter Biller Name."
				}
		   } 
		 };
		 
		$("#form1").validate(registerBinRules);
		if($("#form1").valid()) {
			var rowCount = $('#bankAcctData > tbody > tr').length; 
			/* var data= $( "#bankCode option:selected" ).val();
			var bankCode=data.split("-")[0]; */
			var queryString = "entity=${loginEntity}&method=searchBillerAccount&billerId="+$('#billerId').val()+"&accountNumber="+$('#accountNumber').val()+"&billerCode="+$('#billerCode').val();
			
			$.getJSON("postJson.action", queryString,function(data) {   
					binstatus = data.status; 
					v_message = data.message; 
					//console.log('binstatus ===> '+binstatus);
					//console.log('v_message ===> '+v_message);
					if(binstatus == 'FOUND') {
						if(v_message == 'BIN_BANK_DUP') {
							$('#error_dlno').text('Bin with Bank Code Exists.');
						} else if(v_message == 'BIN_DUP') {
							$('#error_dlno').text('Bin Already Exists.');
						} else if(v_message == 'BANK_CODE_DUP') {
							$('#error_dlno').text('Bank Code Exists.');
						}
					} else {  
					
						var billerId = $('#billerId').val() == undefined ? ' ' : $('#billerId').val();
						var accountNumber = $('#accountNumber').val() == undefined ? ' ' : $('#accountNumber').val();
						var accountName = $('#accountName').val() == undefined ? ' ' : $('#accountName').val();
						var accountType = $('#accountType').val() == undefined ? ' ' : $('#accountType').val();
						var billerCode = $('#billerCode').val() == undefined ? ' ' : $('#billerCode').val();
						var billerName = $('#billerName').val() == undefined ? ' ' : $('#billerName').val();
					
						var  hiddenInput ="";
						
						$('#bank-details').find('input,select').each(function(index){
							if($(this).attr('name')!="accountTypeser"){
								
							
							var nameInput = $(this).attr('name');
							var valToSpn = ($(this).attr('value') =='' ? ' ' :$(this).attr('value')); 
							
							if(nameInput != undefined) {
							  if(index == 0) hiddenInput += valToSpn;
							  else hiddenInput += ","+valToSpn  ; 
							}
							}
						}); 
						
						 $("#multi-row-data").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ></span>");
 						 $('#hidden_span_'+rowindex).append(hiddenInput);
						var addclass = "";
						if(val % 2 == 0 ) {
							addclass = "even";
							val++;
						}
						else {
							addclass = "odd";
							val++;
						}  
						var rowCount = $('#tbody_data > tr').length;  
						colindex = ++ colindex;   
						var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
									"<td>"+colindex+"</td>"+
									"<td>"+billerId+"</td>"+
									"<td>"+billerName+"</td>"+	
									"<td>"+billerCode+"</td>"+										
									"<td>"+accountNumber+"</td>"+	
									"<td>"+accountName+"</td>"+	
									"<td>"+accountType+" </td>"+ 
									"<td class='center'>  <a class='btn btn-min btn-danger' href='#' id='delete' index='"+rowindex+"'> <i class='icon-trash icon-white'></i> </a></td></tr>";
						rowindex = ++rowindex;						
						$("#tbody_data").append(appendTxt);	 
						$('#accountNumber').val('');
						$('#accountName').val('');
						$('#accountType').val('');
						$('#billerCode').val('');
						$('#billerName').val('');
						
						//bankAcctFinalData=bankAcctFinalData+"#"+eachrow; 
						
						 $('#accountTypeser').find('option').each(function( i, opt ) { 
							if( opt.value == "" ) {
								$(opt).attr('selected', 'selected');
								$('#accountTypeser').trigger("liszt:updated");
							}
						});  
					}
			});   
		}  else {
			return false;				
		}  
	});   
	
	
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
<s:set value="responseJSON" var="respData" />

</head> 
<body>
	<form name="form1" id="form1" method="post" action="">
	
		
			<div id="content" class="span10"> 
			<div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"> Biller Account Creation</a></li>
				</ul>
			</div>
			<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Biller Account Creation
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

							</div>
						</div>

						<div id="primaryDetails" class="box-content">
							<fieldset>   
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd">
										<td width="20%"><strong><label for="Bank Code"><strong>Biller Category Id</strong></label></strong></td>
										<td width="30%"> ${responseJSON.billerCode} 
											<input name="billerId" type="hidden"  id="billerId" class="field" value='${responseJSON.billerCode}' >
										</td>
									</tr>
									<tr class="odd">
										<td width="20%"><strong><label for="Bank Code"><strong>Biller Category Name</strong></label></strong></td>
										<td width="30%"> ${responseJSON.abbreviation} 
											<input name="name" type="hidden"  id="name" class="field" value='${responseJSON.abbreviation}' >
										</td>
									</tr>
									<tr>
										<td width="20%"><strong><label for="Bank Name"><strong>Biller Name</strong><font color="red">*</font></label></strong></td>
										<td width="30%"><input name="billerName" type="text"  id="billerName" class="field" value=''  maxlength="50" required='true' ></td>
									</tr>
									<tr>
										<td width="20%"><strong><label for="Bank Name"><strong>Biller id</strong><font color="red">*</font></label></strong></td>
										<td width="30%"><input name="billerCode" type="text"  id="billerCode" class="field" value=''  maxlength="50" required='true' ></td>
									</tr>
									<tr>
									
									<td><label for="Services"><strong>Biller Code<font color="red">*</font></strong></label></td>
									<td><div  id="accountTypeseraa" >
														
													<s:select cssClass="chosen-select" headerKey=""
															headerValue="Select" list="#respData.SERVICE_MASTER"
															name="accountTypeser" id="accountTypeser" requiredLabel="true"
															theme="simple" data-placeholder="Choose Services"
															required="true" />  </div>
									<input name="accountType" type="text"  id="accountType"  value=''   >					
									</td>	
									</tr>
									<tr>
										<td width="20%"><strong><label for="Bank Name"><strong>Account Number</strong><font color="red">*</font></label></strong></td>
										<td width="30%"><input name="accountNumber" type="text"  id="accountNumber" class="field" value='${responseJSON.accountNumber}'  maxlength="50" required='true' ></td>
									</tr>
									<tr class="even">
										<td ><strong><label for="Bin"><strong>Field Name</strong><font color="red">*</font></label></strong></td>
										<td><input name="accountName" type="text" id="accountName" class="field"   value='${responseJSON.accountName}' required='true' ></td>
									</tr>
									
									 
								</table>
							 </fieldset> 
							</div>
							
								<input type="hidden" name="bankMultiData" id="bankMultiData" value=""></input>
							<table width="100%" class="table table-striped table-bordered bootstrap-datatable "
								id="bankAcctData"  >
									  <thead>
											<tr >
												<th>Sno</th>
												<th>Biller Category Id</th>
												<th>Biller Name</th>
												<th>Biller id</th>
												<th>Account Number</th>
												<th>Field Name</th>
												<th>Biller Code</th>
												<th>Action</th>
											</tr>
									  </thead>    
									 <tbody  id="tbody_data">
									 </tbody>
							</table> 
							 
						</div>
					</div>
						 
				 
			<span id="multi-row-data" class="multi-row-data" style="display:none"> </span>
			<div class="form-actions"> 
				<input type="button" class="btn btn-success" name="addCap2" id="addCap2" value="Add" />&nbsp;
				<input type="button" class="btn btn-info" name="btn-submit" id="btn-submit" value="Submit" />&nbsp;
				<input type="button" class="btn" name="btn-back" id="btn-back" value="Back" />
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div> 
	</div> 
</form>
</body>
</html>
