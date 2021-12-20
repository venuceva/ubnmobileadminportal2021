<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
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
.modal-content {
  background-color: #0480be;
}
.modal-body {
  background-color: #fff;
}
</style>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/bootstrap-2.3.2.min.js'></script>
<link href="${pageContext.request.contextPath}/css/link/css1" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/link/screen.css">
<link href="${pageContext.request.contextPath}/css/link/sticky.css" rel="stylesheet" type="text/css">

 
<script type="text/javascript" > 

var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);

$(document).ready(function(){
	
	
	
	
	$("#AGENTWALLET").prop("disabled", true);
	$("#CUSTWALLET").prop("disabled", true);
	$("#AGENTWALLET").prop("checked", true);
	$('#apptype').val("AGENTWALLET");
	
	$( "#customerId" ).keyup(function() {
		
		$( "#customerId" ).val((this.value).toUpperCase());
		});
	
	$.each(jsonLinks, function(index, v) {
		
		
		if(v.name=="custwalletregistration"){
			
			$("#AGENTWALLET").prop("disabled", false);
			
			
		}
		if(v.name=="agentwalletregistration"){
			$('#apptype').val("CUSTWALLET");
			$("#CUSTWALLET").prop("disabled", false);
			$("#CUSTWALLET").prop("checked", true);
			$("#AGENTWALLET").prop("checked", false);
		}
	});
	
	
	
	
	var myApp;
	myApp = myApp || (function () {
	    var pleaseWaitDiv = $('<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false"><div class="modal-header"><h1>Processing...</h1></div><div class="modal-body"><div class="progress progress-striped active"><div class="bar"  style="width: 100%;"></div></div></div></div>');
	    return {
	        showPleaseWait: function() {
	            pleaseWaitDiv.modal();
	        },
	        hidePleaseWait: function () {
	            pleaseWaitDiv.modal('hide');
	        },

	    };
	})();
	
	  $("#t3").css({"display":"none"});
	 // $("#t3").css({"display":"none"});
	  
	  
	/* $('#srchcriteria').on('change', function (e) {
	    var optionSelected = $("option:selected", this);
	    var valueSelected = this.value;
	    $('#mobileno').val("");
	    $("#errors").text(" ");
	    if(valueSelected!=""){
	    	 $("#trow1").show();
	    	 $("#isocode").show();
			  
			    	 
	    }
	    else{
					    	 	$("#trow1").hide();
					    	 	$("#isocode").hide();
	    	 	
	    }
	    
	}); */
	
	
	
	
	$('#srchcriteria').on('change', function (e) {
	    var optionSelected = $("option:selected", this);
	    var valueSelected = this.value;
	    $('#mobileno').val("");
	    $("#errors").text(" ");
	    
	    if(valueSelected!=""){
	    	 $("#trow1").show();

			    	 if(valueSelected =='AIRTEL')	
			    		 {
					    		 $("#isocode").show();
					    		 $("#rettext").text('Enter Airtel Mobile No');
			    		 }
				else if(valueSelected =='UNION_BANK_CUSTOMER')
	    				{	
					 			$("#isocode").hide();
								$("#rettext").text('Enter Account Number');
	    				}
				else if(valueSelected =='BVN')
				{	
			 			$("#isocode").hide();
						$("#rettext").text('Enter BVN Number');
				}
			    else if(valueSelected =='OTHER')
			    		{
			    	 			$("#isocode").show();
			    				$("#rettext").text('Enter Mobile No');
			    		}
			    
			    	 
			    	 $("#billerMsg").text(" ");
			    	 
	    }
	    else{
					    	 	$("#trow1").hide();
					    	 	$("#isocode").hide();
	    	 	
	    }
	    
	});
	
	var subrules = {
			   rules : {
				   customerId : { required : true}
				   
			   },  
			   messages : {
				   customerId : { 
				   		required : "Please Enter Values."
					}
					
			   } 
			 };
		

			
		
		

		    
	 
	
	
	$('#btn-submit').click(function(){ 
			//$("#form1").validate(acctRules);
			
			if($("#apptype").val()=="AGENTWALLET"){
				
				if($("#supercriteria").val()==""){
					$("#errors").text('Please Select Super Agent');
					 return false; 
				}
				if($("#accountno").val()==""){
					$("#errors").text('Please Enter Account Number');
					 return false; 
				}
				
				myApp.showPleaseWait();
 				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/waletfetchServiceAct.action'; 
				$('#form1').submit();
				 return true; 
			}
			
			if($("#apptype").val()=="CUSTWALLET"){
				
				if($("#srchcriteria").val()==""){
					$("#errors").text('Please Select Criteria');
					 return false; 
				}
				
				if($("#srchcriteria").val()!=""){
					
					if($("#srchcriteria").val() =='AIRTEL')	
				    	{
							if($("#mobileno").val()==""){
								$("#errors").text('Please Enter Mobile No');
								 return false; 
							}
				    	}
					else if($("#srchcriteria").val() =='UNION_BANK_CUSTOMER')
		   				{	
							if($("#mobileno").val()==""){
								$("#errors").text('Enter Account Number');
								 return false; 
							}
		   				}
					else if($("#srchcriteria").val() =='BVN')
					{	
						if($("#mobileno").val()==""){
							$("#errors").text('Enter BVN Number');
							 return false; 
						}
					}
				}
				
				
				myApp.showPleaseWait();
 				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/waletcustfetchServiceAct.action'; 
				$('#form1').submit();
				 return true; 
			}
			
	});
	
	
	$('#btn-back').click(function(){ 
		//$("#form1").validate(acctRules);
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
			$('#form1').submit();
			
	});

});



function funval(v){
	
	$("#apptype").val(v);
	if(v=="AGENTWALLET"){
		
	$("#trow22").css({"display":""});
	$("#trow1").css({"display":"none"});
	$("#trow12").css({"display":"none"});
	$("#trow").css({"display":"none"});
	$("#btn-submit").css({"display":""});
	}else{
		$("#trow1").css({"display":"none"});
		$("#btn-submit").css({"display":"none"});
		$("#trow").css({"display":""});
		$("#t3").css({"display":"none"});
		$("#trow12").css({"display":"none"});
		
		$("#trow22").css({"display":"none"});
		$("#btn-submit").css({"display":""});
	}
	
}
</script>

</head>

<body>
	<form name="form1" id="form1" method="post" action="">
		<div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="#">Wallet Registration </a></li>
				</ul>
			</div>
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
						<i class="icon-edit"></i>Wallet Registration Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
						<div class="section-inner">
									<div class="segmented-control" style="width: 600px; color: #00ADEF;font-weight: bold;font-size: 14px;">
					                    	<input type="radio" name="ecust" id="CUSTWALLET"  onclick="funval(this.id)">
					                    	<input type="radio" name="ecust" id="AGENTWALLET" onclick="funval(this.id)">
					                     	<label for="CUSTWALLET" data-value="Wallet Customer Registration" ><img src='${pageContext.request.contextPath}/images/Wallet.png' align='left' style='width: 40px; height: 40px;'><span style="color: #000000;font-weight: bold;font-size: 12px;">Wallet Customer Registration</span></label>
                    						<label for="AGENTWALLET" data-value="Wallet Agent Registration"  ><img src='${pageContext.request.contextPath}/images/Wallet.png' align='left' style='width: 40px; height: 40px;'><span style="color: #000000;font-weight: bold;font-size: 12px;">Wallet Agent Registration</span></label>
					                  </div>
									</div>
						
						
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
								
								
								
								
								<tr class="even" id="trow" name="trow" >
									<td width="25%"><label for="Service ID"><strong>Select Criteria<font color="red">*</font></strong></label></td>
									<td width="25%" > 
									   <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'AIRTEL':'Airtel','UNION_BANK_CUSTOMER':'Union Bank Customer','BVN':'BVN'}" 
							         name="srchcriteria" 
							         id="srchcriteria"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
	 							</td>
	 							<td width="25%"></td>	
	 							<td width="25%"></td>		
								</tr>
								
								<tr class="even" id="trow1" name="trow1" style="display:none">
									<td width="25%"><label for="Service ID"><strong><span id="rettext"></span><font color="red">*</font></strong></label></td>
									<td width="25%" ><input type="text" name="isocode" id="isocode" style="display:none; width:25px;" value="234" readonly> 
									<input type="text" name="mobileno"  id="mobileno"   /> 
	 							</td>
	 							<td width="25%"></td>	
	 							<td width="25%"></td>		
								</tr>
								
								
								<tr class="even" id="trow22" name="trow22" style="display:none">
								<td width="25%"><label for="Service ID"><strong>Select Super Agent<font color="red">*</font></strong></label></td>
									<td width="25%" > 
									   <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'AIRTEL':'Airtel','UNION_BANK_CUSTOMER':'Union Bank Customer','ALEDIN_AGENCY':'Aledin Agency'}" 
							         name="supercriteria" 
							         id="supercriteria"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
	 							</td>
									<td width="25%"><label for="Service ID"><strong>Enter Core Bank Account No<font color="red">*</font></strong></label></td>
									<td width="25%" > 
									<input type="text" name="accountno"  id="accountno"   /> 
	 							</td>
	 								<!-- ,'UNION_BANK_CUSTOMER':'Union Bank Customer' -->	
								</tr>
								
								
								
								
							</table>
							</fieldset>
					</div>
			</div>
		</div>
		
		
		<input type="hidden" name="apptype"  id="apptype"   /> 
		
	<div class="form-actions">
		<a  class="btn btn-danger" href="#" id="btn-back" name="btn-back">Home</a>
		<a  class="btn btn-success" href="#" id="btn-submit" name="btn-submit" >Proceed</a>
		<span id="billerMsg" class="errors"></span>
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
    };
	
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }  
});  
</script>
</body>
</html>
