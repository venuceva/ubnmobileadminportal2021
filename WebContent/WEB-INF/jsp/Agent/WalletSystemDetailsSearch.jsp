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

var datavalidation = {
 		
 		rules : {
 			application	: { required : true}
			
 		},		
 		messages : {
 			
 			application : { 
 							required : "Please Select Option.",
					 }
			
					 
 		} 
 };



 var list = "fromdate,todate".split(",");
 var datepickerOptions = {
 				showTime: true,
 				showHour: true,
 				showMinute: true,
 	  		dateFormat:'dd/mm/yy',
 	  		alwaysSetTime: false,
 	  		yearRange: '1910:2020',
 			changeMonth: true,
 			changeYear: true
 };
 
 
			   
var verror="";	
			
			$(function () { 
				
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
				
				

				$(list).each(function(i,val){
					$('#'+val).datepicker(datepickerOptions);
				}); 
				
				
				
				
			$('#application').on('change', function (e) {
				$('#errormsg').text("");
				$('#fname').val("");
					$("#searchenter").hide();
					$("#datewise").hide();
					verror="";
					
		    		if(this.value =='BALANCE_SHEET')
		    		{		$("#searchenter").hide();
		    				$("#datewise").show();
		    				verror="Enter Date";
		    				
		    				
		    		}
				});
			
			$('#btn-submit').live('click', function () { 
				
				$("#form1").validate(datavalidation);
				
				if($('#application').val()=="WALLET_ACCT_BALANCE"){
					var url="${pageContext.request.contextPath}/<%=appName %>/walletaccinfo.action"; 
					$("#form1")[0].action=url;
					$("#form1").submit();
					return true;
				}
				
				if($('#application').val()=="BALANCE_SHEET"){
					if($('#fromdate').val()==""){
						$('#errormsg').text("Please Enter Date");
						return false;
					}
					var url="${pageContext.request.contextPath}/<%=appName %>/walletbalancesheet.action"; 
					$("#form1")[0].action=url;
					$("#form1").submit();
					return true;
				}
				
			});
				
		    });  


			
			function redirectAct(){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
				$("#form1").submit();
				return true;
			}
			
			

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
					<li><a href="#">Wallet System Details</a></li>
					
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
								
								<tr class="odd" id="mobileselect" >
								<td width="25%"><label for="Application"><strong>Select Option<font color="red">*</font>
										</strong></label></td>
									<td width="25%"><s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'WALLET_ACCT_BALANCE':'Wallet Account Balance','BALANCE_SHEET':'Balance Sheet'}" 
							         name="application" 
							         id="application"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
							       
									</td>
									<td width="25%"></td>
									<td width="25%"></td>
							</tr>
							
							
							
							
								<tr class="odd" id="searchenter" style="display:none">
									<td ><label for="fname"><strong><span id="rettext"></span><font color="red">*</font></strong></label></td>
									<td ><input name="fname" autocomplete="off" type="text" class="field" id="fname"  value="" required=true   />
									
									</td>
									<td></td>
									<td></td>
								
								</tr>
								
								<tr class="even" id="datewise" style="display:none" >
									<td ><label for="Date"><strong>Date</strong><font color="red">*</font></label></td>
									<td > 
										<input type="text" maxlength="10"  class="fromDate" id="fromdate" name="fromdate" required=true  />  							
									</td>
									<td ></td>
									<td ></td>
									
								</tr>	
								
							</table>
							<input type="hidden" id="trans" name="trans" value="TRANSACTION"  /> 
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

	
</body>
</html>
