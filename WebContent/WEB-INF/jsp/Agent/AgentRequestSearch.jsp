<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">

<link href="${pageContext.request.contextPath}/css/link/css1" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/link/screen.css">
<link href="${pageContext.request.contextPath}/css/link/sticky.css" rel="stylesheet" type="text/css">

<script type="text/javascript">

var createProductRules = {
		   rules : {
			   mnumber     : { required : true }
				
		   },  
		   messages : {
			   
		         mnumber : {
				 required :"Please Enter Payment Reference Number "
			     }
		    } 
		 };


	$.validator.addMethod("regex", function(value, element, regexpr) {          
		 return regexpr.test(value);
	   }, ""); 
	

$(function () { 
	 $("#form1").validate(createProductRules);
	$('#btn-submit').live('click', function () { 
	
		var queryString = "entity=${loginEntity}&method=validaterefsucrev&fname="+$('#mnumber').val();
		
		$.getJSON("postJson.action", queryString,function(data) {
			if(data.finalCount==0){
		
			var url="${pageContext.request.contextPath}/<%=appName %>/agentrequestdetails.action"; 
			$("#form1")[0].action=url;
			$("#form1").submit();
			return true;
			}else{
				$('#errormsg').text("Payment Reference Number "+$('#mnumber').val()+ " Pending for Authorization ");
			}
		});
	
	});
});
</script>
<s:set value="responseJSON" var="respData" />

</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Wallet Transaction Reversal Request</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
			
			 
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<!-- Customer Negotiated Rate Confirmation -->
						Search Details
						 <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
					
				 
					<div class="box-content" id="secondaryDetails">
						<fieldset>
						
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
							<tr class="odd" id="walletselect" >
								<td width="25%"><label for="Application"><strong>Request Option<font color="red">*</font>
										</strong></label></td>
									<td width="25%"><s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'SUCCESS_REV':'Success Transaction Reversal','REV_REV':'Reversed Transaction Reversal'}" 
							         name="application" 
							         id="application"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
							       
									</td>
									<td></td>
									<td></td>
									</tr>
							
							<tr class="odd" id="searchenter" >
									<td ><label for="fname"><strong>Payment Reference Number<font color="red">*</font></strong></label></td>
									<td ><input name="mnumber" autocomplete="off" type="text" class="field" id="mnumber"  value="" required=true   />
									
									</td>
									<td></td>
									<td></td>
								
								</tr>
							
							
								
								
							</table>
							
						</fieldset>
						
					</div>
					
				</div>
			</div>
			
			
			
			<div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Cancel" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Search" width="100"  ></input> 
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
	  	 
		 
		  
		/* $('#plasticCode').val(ses); 
		$('#plasticCode').trigger("liszt:updated");  */
	});
 </script>
</body>
</html>
