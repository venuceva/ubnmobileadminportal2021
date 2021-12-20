
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  

<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<s:set value="responseJSON" var="respData"/> 
<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js" type="text/javascript"></script>

<script type="text/javascript" >


function redirectfun()
{
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/productfeesettings.action';
	$("#form1").submit();
	return true; 
	}
	
	
$(function () { 
	
	 $('#btn-submit').live('click', function () { 
		 $("#form1").validate(createfeeRules);
			
			if($("#form1").valid()) {
				
			var FP=$('#flatpercentile').val();
			var pval=$('#fpValue').val();
			
			var total=parseFloat($('#agent').val())+parseFloat($('#subagent').val());
			total=parseFloat(total.toFixed(2))+parseFloat($('#ceva').val());
			total=parseFloat(total.toFixed(2))+parseFloat($('#bank').val());
			total=parseFloat(total.toFixed(2))+parseFloat($('#VAT').val());
			total=parseFloat(total.toFixed(2))+parseFloat($('#SuperAgent').val());
			total=parseFloat(total.toFixed(2))+parseFloat($('#thirdparty').val());
			
		 //var total=parseFloat($('#agent').val())+parseFloat($('#subagent').val())+parseFloat($('#thirdparty').val())+parseFloat($('#ceva').val())+parseFloat($('#bank').val())+parseFloat($('#VAT').val())+parseFloat($('#SuperAgent').val()));
		//alert(total+"===="+FP+"===="+pval);
		
		
			if(parseInt($('#fromvalue').val())>parseInt($('#tovalue').val())){
				$("#errormsg").html("<font colour='red'>To Value should be greater than From Value</font>");
				return false;
			}else if(total!=100  && FP=="P"){
				$("#errormsg").html("<font colour='red'>all commission should be equals to 100%</font>");
				return false;
			}else if(total!=pval  && FP=="F"){
				$("#errormsg").html("<font colour='red'>all commission should be equals to Fee Amount</font>");
				return false;
			}else {
				var queryString = "entity=${loginEntity}&method=searchAuthPendinglf&fname="+$('#feeCode').val();
		 		
				$.getJSON("postJson.action", queryString,function(data) { 
					if(data.message=="SUCCESS"){
	    
						var url="${pageContext.request.contextPath}/<%=appName %>/feesettingsmodifyack.action"; 
						$("#form1")[0].action=url;
						$("#form1").submit(); 
					}else{
						$('#messages').text("Fee Code "+$('#feeCode').val()+" "+data.message);
					}
				 });
			}
			}
    }); 
});


var createfeeRules = {
	      rules : {
		    feeCodeval  			: { required : true , minlength : 6 , maxlength : 6}, 
			feeDescriptionval			: { required : true },
			FeeTransaction			: { required : true },
		    FeeFrequency 	: { required : true },
			FlatPercentile 			: { required : true },
			Criteria 			: { required : true },
			fromvalue 			: { required : true,number: true },
			fpValue 			: { required : true,number: true },
			toValue 			: { required : true,number: true },
			agent 			: { required : true,number: true },
			ceva 			: { required : true,number: true },
			bank 			: { required : true,number: true },
			VAT				: { required : true,number: true },
			subagent				: { required : true,number: true },
			thirdparty				: { required : true,number: true }
		
		
	   },  
	   messages : {
			   feeCode : { 
		         required : "Please Enter FeeCode."
		       },  
			   feeDescription : {
				 required :"Please Enter FeeDescription."
			   },
			   
		       FeeTransaction : { 
		         required : "Please Select FeeTransaction."
		       },  
			   FeeFrequency : {
				 required :"Please Select FeeFrequency."
			   },  
			   FlatPercentile : { 
		         required : "Please Select FlatPercentile."
		       },  
			   Criteria : {
				 required :"Please Select Criteria."
			   }, 
			   fromvalue : { 
			         required : "Please Enter FromValue."
			       },
			       fpValue : { 
			         required : "Please Enter Value."
			       },
				    toValue : { 
			         required : "Please Enter ToValue."
			       },
			       agent : { 
				         required : "Please Enter Agent."
				       },
				       ceva : { 
					     required : "Please Enter Ceva."
					},
				bank : { 
						 required : "Please Enter Bank."
					 },
				VAT : { 
						 required : "Please Enter VAT."
				 },
				 subagent : { 
					 required : "Please Enter Sub Agent."
			 },
			 thirdparty : { 
				 required : "Please Enter Third Party."
			 }
			   
  }
};



$(function () { 
	
	var FlatPercentile="<s:property value='#respData.Feecodedetails.FLATPER' />";
	var Criteria="<s:property value='#respData.Feecodedetails.CRT' />";
	
	var FlatPercentilev="";
	if(FlatPercentile=="Flat"){
		FlatPercentilev="F";
	}else if(FlatPercentile=="Percentile"){
		FlatPercentilev="P";
	}
	
	var Criteriaval="";
	if(Criteria=="Count"){
		Criteriaval="C";
	}else if(Criteria=="Amount"){
		Criteriaval="A";
	}
	
	
	$("#flatpercentile option[value='"+FlatPercentilev+"']").attr("selected", true);
	$("#criteria option[value='"+Criteriaval+"']").attr("selected", true);
	
});



</script>

</head>

<body>	
<form name="form1" id="form1" method="post" >
		<div id="content" class="span10"> 
		    <div>
					<ul class="breadcrumb">
					  <li><a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li><a href="binManagementAct.action">Fee Settings</a> <span class="divider"> &gt;&gt; </span></li>
					  <!-- <li><a href="#">Setting</a></li> -->
					</ul>
			</div>
			<div id="errormsg" class="errores"></div>
			       <table height="3">
						<tr>
							<td colspan="3">
								<div class="messages" id="messages"><s:actionmessage /></div>
								<div class="errors" id="errors"><s:actionerror /></div>
							</td>
						</tr>
					</table>
			
	
			<div class="row-fluid sortable"><!--/span--> 	
				
				<div class="box span12">  
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Fee Management
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>   
						
					<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
						<tr class="even">
								<td width="20%"><label for="Fee Code"><strong>Product Code</strong></label></td>
								<td width="30%"><s:property value='#respData.productcode' />
								<input name="productcode" type="hidden" maxlength ='6' id="productcode" required="true" class="field"  value="<s:property value='#respData.productcode' />"  readonly /> <span id="bin_err" class="errmsg"></span></td>
								 <td width="20%"><label for="Fee Description"><strong>Application</strong></label></td>
								 <td width="30%"><s:property value='#respData.application' />
								 <input name="application"  type="hidden" maxlength ='25' id="application" required="true"  class="field"  value="<s:property value='#respData.application' />" /><span id="bin_err1" class="errmsg"></span> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Fee Code"><strong>Fee Code</strong></label></td>
								<td width="30%"><s:property value='#respData.feeCode' />
								<input name="feeCode" type="hidden" maxlength ='6' id="feeCode" required="true" class="field"  value="<s:property value='#respData.feeCode' />"  readonly /> <span id="bin_err" class="errmsg"></span></td>
								 <td width="20%"><label for="Fee Description"><strong>Fee Description</strong></label></td>
								 <td width="30%"><s:property value='#respData.feeDesc' />
								 <input name="feeDescription"  type="hidden" maxlength ='25' id="feeDescription" required="true"  class="field"  value="<s:property value='#respData.feeDesc' />" /><span id="bin_err1" class="errmsg"></span> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Fee Code"><strong>Channel</strong></label></td>
								<td width="30%"><s:property value='#respData.Feecodedetails.CHANNEL' />
								 <td width="20%"><label for="Fee Code"><strong>Operators</strong></label></td>
								 <td width="30%"><s:property value='#respData.Feecodedetails.OPERATORS' /></td>
							</tr> 
						</table>
							<input name="channel" type="hidden" id="channel" required="true" class="field"  value="<s:property value='#respData.Feecodedetails.CHANNEL' />"  readonly /> <span id="bin_err" class="errmsg"></span>
						
					</fieldset>
					
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="FeeTransaction"><strong>Transaction<font color="red">*</font></strong></label></td>
								<td width="30%"><s:property value='#respData.Feecodedetails.TXNNAME' />
								<input type="hidden" value="<s:property value='#respData.Feecodedetails.TXNNAME' />" name="transaction" id="transaction" />
								</td>
								 <td width="20%"><label for="FeeFrequency"><strong>Frequency<font color="red">*</font></strong></label></td>
								 <td width="30%"><s:property value='#respData.Feecodedetails.FREQ' />
								<input type="hidden" value="<s:property value='#respData.Feecodedetails.FREQ' />" name="frequency" id="frequency" />
								 </td>
							</tr>
							
							<tr class="even">
								<td width="20%"><label for="FlatPercentile"><strong>Flat/Percentile<font color="red">*</font></strong></label></td>
								<td width="30%"> <select name="flatpercentile" id="flatpercentile"  Class ="chosen-select" data-placeholder="Choose Flat/Percentile..." >
													<option value="">select</option>
													<option value="F" >Flat</option>
													<option value="P">Percentile</option>
								</select>
								<span id="bin_err" class="errmsg"></span>
								</td>
								 <td width="20%"><label for="FPValue"><strong>Value<font color="red">*</font></strong></label></td>
								 <td width="30%"><input name="fpValue" id="fpValue"  type="text" maxlength ='25'  required="true"  class="field"  value="<s:property value='#respData.Feecodedetails.FPVALUE' />"/><span id="bin_err1" class="errmsg"></span> </td>
							</tr>
							
							<tr class="even">
								<td width="20%"><label for="Criteria"><strong>Criteria<font color="red">*</font></strong></label></td>
								<td width="30%"> <select name="criteria" id="criteria" data-placeholder="Choose Criteria..."  Class ="chosen-select" >
													<option value="">select</option>
													<option value="C" >Count</option>
													<option value="A">Amount</option>
								</select>
								<span id="bin_err1" class="errmsg"></span></td>
									<td width="20%"></td>
								<td width="30%"></td>
								
							</tr>
							
							<tr class="even">
							 <td width="20%"><label for="FromValue">
								 <strong>From Value<font color="red">*</font></strong></label></td>
								 <td width="30%"><input name="fromvalue" id="fromvalue"  type="text" maxlength ='25'  required="true"  class="field" value="<s:property value='#respData.Feecodedetails.FRMVAL' />" /><span id="bin_err1" class="errmsg"></span></td>
								<td width="20%"><label for="To Value">
									<strong>To Value<font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="tovalue" id="tovalue"  type="text" maxlength ='25'  required="true"  class="field"  value="<s:property value='#respData.Feecodedetails.TOVAL' />" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
							
							</tr>
							
							
							<tr class="even">
								
								
								
								<td width="20%"><label for="Agent">
									<strong>Super Agent commission <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="SuperAgent" id="SuperAgent"  type="text" maxlength ='25'  required="true"  class="field"  value="<s:property value='#respData.Feecodedetails.SUPERAGENT' />" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								<td width="20%"><label for="Agent">
									<strong>Agent commission <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="agent" id="agent"  type="text" maxlength ='25'  required="true"  class="field"  value="<s:property value='#respData.Feecodedetails.AGENT' />" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
							</tr>
							
							<tr class="even">
							<td width="20%"><label for="Ceva">
									<strong>Sub Agent commission <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="subagent" id="subagent"  type="text" maxlength ='25'  required="true"  class="field"  value="<s:property value='#respData.Feecodedetails.SUBAGENT' />" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								<td width="20%"><label for="Ceva">
									<strong>Ceva commission <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="ceva" id="ceva"  type="text" maxlength ='25'  required="true"  class="field"  value="<s:property value='#respData.Feecodedetails.CEVA' />" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								 
							</tr>
							<tr>
							
							
							<td width="20%"><label for="Bank">
									<strong>Bank commission <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="bank" id="bank"  type="text" maxlength ='25'  required="true"  class="field"  value="<s:property value='#respData.Feecodedetails.BANK' />" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								<td width="20%"><label for="Bank">
									<strong>VAT<font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="VAT" id="VAT"  type="text" maxlength ='25'  required="true"  class="field" value="<s:property value='#respData.Feecodedetails.VAT' />" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
							
							</tr>
							
							<tr class="even">
							<td width="20%"><label for="Bank">
									<strong>Third Party<font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="thirdparty" id="thirdparty"  type="text" maxlength ='25'  required="true"  value="<s:property value='#respData.Feecodedetails.THIRDPARTY' />" class="field" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								<td width="20%"><label for="Bank">
									<%-- <strong>QT<font color="red">*</font></strong></label> --%>
								</td>
								<td width="30%">
									<input name="qt" id="qt"  type="hidden" maxlength ='25'  required="true"  value="<s:property value='#respData.Feecodedetails.QT' />" class="field" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								 
							</tr>
							
						</table>
<%-- 							<input name="SuperAgent" id="SuperAgent"  type="hidden" maxlength ='25'  required="true"  class="field"  value="0" /><span id="bin_err1" class="errmsg"></span>
 --%>						
					</fieldset>
				
					
						</div> 		
					</div>
					
					<input type="hidden" value="<s:property value='#respData.Feecodedetails.SEQ_NO' />" name="seqno" id="seqno" />
			  
			  <div class="form-actions" align="left"> 
				
				<a class="btn btn-min btn-success" href="#" onClick="redirectfun()">Next</a>
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100"  ></input> 
			</div>  
					
					
					</div> 		
		
		
			</div> 	

	
</form>
  <script>
 $(function() {
	 
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

</body>
</html>
