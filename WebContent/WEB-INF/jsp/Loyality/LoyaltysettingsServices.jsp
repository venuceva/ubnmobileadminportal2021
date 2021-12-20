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
		
	<%-- 	$('#btn-back').live('click', function () { 
			$("form").validate().cancelSubmit = true; 
			var url="${pageContext.request.contextPath}/<%=appName %>/newPayBillAct.action"; 
			$("#form1")[0].action=url;
			$("#form1").submit(); 
		}); --%>
		
		
			
		$('#btn-submit').live('click', function () { 
			$('input, select, textarea').each(function() {
			    $(this).rules('remove');
			});
			
			var rowCount = $('#tbody_data > tr').length; 
			 $("#error_dlno").text('');
			if(rowCount > 0) {
				//$("#form1").validate().cancelSubmit = true;
				var specChar = "";
				var prevCount = "";
				//alert(rowCount);
				$('#multi-row-data > span').each(function(index){  
					if(index == 0)  finalData = $(this).text();
					else finalData += "#"+ $(this).text();
				}); 
				//alert(finalData);
				$('#bankMultiData').val(finalData);
				
 				var url="${pageContext.request.contextPath}/<%=appName %>/loyalitypointsettingsservicesconfirm.action"; 
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
			   servicetype : { required : true},
			   txnamount : { required : true,number :true },
			   Noofpoints : { required : true,number :true }, 
		   },  
		   messages : {
			   servicetype : { 
			   		required : "Please Select Service Type."
				},
				txnamount : { 
			   		required : "Please enter Txn Amount."
				},
				Noofpoints : { 
			   		required : "Please enter No of Points."
				}
		   } 
		 }; 
		 
		 $("#form1").validate(registerBinRules);
		if($("#form1").valid()) { 
			var rowCount = $('#bankAcctData > tbody > tr').length; 
			
						var servicetype = $('#servicetype').val() == undefined ? ' ' : $('#servicetype').val();
						var txnamount = $('#txnamount').val() == undefined ? ' ' : $('#txnamount').val();
						var Noofpoints = $('#Noofpoints').val() == undefined ? ' ' : $('#Noofpoints').val();
						
					
						var  hiddenInput ="";
						
						$('#bank-details').find('input,select').each(function(index){ 
							var nameInput = $(this).attr('name');
							var valToSpn = ($(this).attr('value') =='' ? ' ' :$(this).attr('value')); 
							
							if(nameInput != undefined) {
							  if(index == 0) hiddenInput += valToSpn;
							  else hiddenInput += ","+valToSpn  ; 
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
									"<td>"+servicetype+"</td>"+
									"<td>"+txnamount+"</td>"+	
									"<td>"+Noofpoints+"</td>"+										
									"<td class='center'>  <a class='btn btn-min btn-danger' href='#' id='delete' index='"+rowindex+"'> <i class='icon-trash icon-white'></i> </a></td></tr>";
						rowindex = ++rowindex;						
						$("#tbody_data").append(appendTxt);	 
						$('#servicetype').val('');
						$('#txnamount').val('');
						$('#Noofpoints').val('');
						
						//bankAcctFinalData=bankAcctFinalData+"#"+eachrow; 
						
						 $('#servicetype').find('option').each(function( i, opt ) { 
							if( opt.value == "" ) {
								$(opt).attr('selected', 'selected');
								$('#servicetype').trigger("liszt:updated");
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
					<li><a href="#">Loyalty Management</a></li>
				</ul>
			</div>
			<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Assign Service for Loyalty
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
										<td width="20%"><strong><label for="Bank Code"><strong>Product Code</strong></label></strong></td>
										<td width="30%"> ${responseJSON.productcode} 
											<input name="productcode" type="hidden"  id="productcode" class="field" value='${responseJSON.productcode}' >
										</td>
									
										<td width="20%"><strong><label for="Bank Code"><strong>Application</strong></label></strong></td>
										<td width="30%"> ${responseJSON.application} 
											<input name="application" type="hidden"  id="application" class="field" value='${responseJSON.application}' >
										</td>
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Bank Code"><strong>Loyalty Code</strong></label></strong></td>
										<td width="30%"> ${responseJSON.loyaltycode} 
											<input name="loyaltycode" type="hidden"  id="loyaltycode" class="field" value='${responseJSON.loyaltycode}' >
										</td>
									
										<td width="20%"><strong><label for="Bank Code"><strong>Loyalty Description</strong></label></strong></td>
										<td width="30%"> ${responseJSON.loyaltydesc} 
											<input name="loyaltydesc" type="hidden"  id="loyaltydesc" class="field" value='${responseJSON.loyaltydesc}' >
										</td>
									</tr>
									
									
									 <tr class="even">
								<td width="20%"><label for="Transaction"><strong>Service Type<font color="red">*</font></strong></label></td>
									<td width="30%">
											<s:select cssClass="chosen-select" headerKey=""
															headerValue="Select" list="#respData.SERVICE_MASTER"
															name="servicetype" id="servicetype" requiredLabel="true"
															theme="simple" data-placeholder="Choose Channel"
															required="true" /> 
											
									</td>
								<td width="20%"></td>
								<td width="30%"></td>
								</tr>
							
							<tr class="even">
							<td width="20%"><label for="MaxCount"><strong>Txn Amount<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="txnamount" id="txnamount" type="text" maxlength ='6'  required="true" class="field"   /> <span id="bin_err" class="errmsg"></span></td>
								
								 <td width="20%"><label for="Max Amount"><strong>No Of Points<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="Noofpoints" id="Noofpoints" type="text" maxlength ='6'   class="field"   /> <span id="bin_err1" class="errmsg"></span></td>
								
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
												<th>Service Type</th>
												<th>Txn Amount</th>
												<th>No Of Points</th>
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
				
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div> 
	</div> 
</form>
</body>
</html>
