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
				

				$(list).each(function(i,val){
					$('#'+val).datepicker(datepickerOptions);
				}); 
				
				$('#application').on('change', function (e) {
					
					$("#searchenter").hide();
					$("#trans").val("");
					$('#fname').val("");
					
						if(this.value =='CUST_REQUEST')	
			    		 {
							$("#searchenter").show();
					    		verror="Customer ";
					    		$("#trans").val("CUST_REQUEST");	
					    		
			    		 }
						else if(this.value =='CUST_CANCELLED')	
			    		 {
							$("#searchenter").show();
					    		verror="Customer ";
					    		$("#trans").val("CUST_CANCELLED");	
					    		
			    		 }
						else if(this.value =='DSA_ACCPT')	
			    		 {
							$("#searchenter").show();
					    		verror="DSA ";
					    		$("#trans").val("DSA_ACCPT");		
					    		
			    		 }
			    		else if(this.value =='CUST_TIMEOUT')
			    		{		
			    			$("#searchenter").show();
			    				verror="Customer ";
			    				$("#trans").val("CUST_TIMEOUT");	
			    				
			    		}
						$('#rettext').text(verror);
			    		
					});
			

				
		    });  


			
function redirectAct(){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
				$("#form1").submit();
				return true;
}

			


function funsubmit(v){
	//alert(v);
	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/"+v+".action";
	$("#form1").submit();
	return true;
}

function funsubmit1(v){
	$("#trans").val(v);
	
	//alert(v);
	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/locatorinformation.action";
	$("#form1").submit();
	return true;
}


function funsubmit2(){
	
	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/locatorinformation.action";
	$("#form1").submit();
	return true;
}

</script>
<s:set value="responseJSON" var="respData" />

<style>
div.gallery {
    margin: 5px;
    
    float: left;
    width: 50px;
    height: 50px;
    cursor: pointer;
}


div.gallery img {

    width: 50px;
    height: 50px;
}

div.desc {
    padding: 15px;
    text-align: center;
}


.numberCircle {
    border-radius: 100%;
    behavior: url(PIE.htc); /* remove if you don't care about IE8 */

    width: 50px;
    height: 50px;
    padding: 8px;

    background: #fff;
    /* border: 2px solid #666; */
    color: #666;
    text-align: center;

    font: 16px Arial, sans-serif;
}
</style>

</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Locator Details</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
			
			 
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<!-- Customer Negotiated Rate Confirmation -->
						Locator Dashboard
						 <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
					
				 
					<div class="box-content" id="secondaryDetails">
						<fieldset>
						
						<table width="100%"  border="0" cellpadding="5" cellspacing="1" align="center" >
						<tr><td width="30%">
						<table border="0" width="100%" ><tr><td colspan="2" >
						<span  style="color: #000000;font-weight: bold;font-size: 10px;">DSA Logged in</span>
						</td></tr>
						<tr><td width="30%" >
						<div class="gallery"  id="locatoragentlogin" onclick=funsubmit(this.id) >
						 
						   <img src="${pageContext.request.contextPath}/images/Agent/agent_logged.png" alt="DSA Logged in" width="100" height="100">
						   
						  </div>
						  </td><td width="70%">
						 <span class="numberCircle" ><strong>${responseJSON.DSA_LOGIN}</strong></span>
						 </td> </tr></table>
						</td><td width="30%">
						
						
						<table border="0" width="100%" ><tr><td colspan="2" >
						<span  style="color: #000000;font-weight: bold;font-size: 10px;">Total Call Today</span>
						</td></tr>
						<tr><td width="30%" >
						<div class="gallery"  id="locatortotcalls" onclick=funsubmit1(this.id) >
						 
						   <img src="${pageContext.request.contextPath}/images/Agent/total_calls.png" alt="Total Call Today" width="100" height="100">
						   
						  </div>
						  </td><td width="70%">
						 <span class="numberCircle" ><strong>${responseJSON.TOTAL_CALLS}</strong></span>
						 </td> </tr></table>
						
						
						</td>
						
						
						<td width="30%">
						
						<table border="0" width="100%" ><tr><td colspan="2" >
						<span  style="color: #000000;font-weight: bold;font-size: 10px;">Customer Call Time Out</span>
						</td></tr>
						<tr><td width="30%" >
						<div class="gallery"  id="locatorcalltimeout" onclick=funsubmit1(this.id) >
						 
						   <img src="${pageContext.request.contextPath}/images/Agent/time_out.png" alt="Customer Call Time Out" width="100" height="100">
						   
						  </div>
						  </td><td width="70%">
						 <span class="numberCircle" ><strong>${responseJSON.CUST_TIMEOUT}</strong></span>
						 </td> </tr></table>
						
						
						
						</td>
						</tr><tr>
						<td>
						
						<table border="0" width="100%" ><tr><td colspan="2" >
						<span  style="color: #000000;font-weight: bold;font-size: 10px;">DSA accepted</span>
						</td></tr>
						<tr><td width="30%" >
						<div class="gallery"  id="locatoragentaccept" onclick=funsubmit1(this.id) >
						 
						   <img src="${pageContext.request.contextPath}/images/Agent/agent_accepted.png" alt="DSA accepted" width="100" height="100">
						   
						  </div>
						  </td><td width="70%">
						 <span class="numberCircle" ><strong>${responseJSON.AGENT_ACCEPT}</strong></span>
						 </td> </tr></table>
						
						
						
						</td>
						<td>
						
						
						<table border="0" width="100%" ><tr><td colspan="2" >
						<span  style="color: #000000;font-weight: bold;font-size: 10px;">Customer Call Canceled</span>
						</td></tr>
						<tr><td width="30%" >
						<div class="gallery"  id="locatorcallcanceled" onclick=funsubmit1(this.id) >
						 
						   <img src="${pageContext.request.contextPath}/images/Agent/cancelled.png" alt="Customer Call Canceled" width="100" height="100">
						   
						  </div>
						  </td><td width="70%">
						 <span class="numberCircle" ><strong>${responseJSON.CUST_CANCEL}</strong></span>
						 </td> </tr></table>
						
						
						</td><td>
						
						
						<table border="0" width="100%" ><tr><td colspan="2" >
						<span  style="color: #000000;font-weight: bold;font-size: 10px;">Customer Calling</span>
						</td></tr>
						<tr><td width="30%" >
						<div class="gallery"  id="locatorcustomercall" onclick=funsubmit1(this.id) >
						 
						   <img src="${pageContext.request.contextPath}/images/Agent/customer_calling.png" alt="Customer Calling" width="100" height="100">
						   
						  </div>
						  </td><td width="70%">
						 <span class="numberCircle" ><strong>${responseJSON.CUST_TIMEOUT}</strong></span>
						 </td> </tr></table>
						
						
						</td>
					
						</table>
						
						<input type="hidden" name="trans" id="trans" />
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd" >
								<td width="25%"><label for="Application"><strong>Select Option<font color="red">*</font>
										</strong></label></td>
									<td width="25%"><s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'CUST_REQUEST':'Customer','DSA_ACCPT':'DSA User Id'}" 
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
								<tr class="odd" id="searchenter"  style="display:none">
									<td ><label for="fname"><strong><span id="rettext"></span>User Id<font color="red">*</font></strong></label></td>
									<td ><input name="fname" autocomplete="off" type="text" class="field" id="fname"  value="" required=true   />
									
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
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Search" width="100" onclick="funsubmit2(this.id)" ></input> 
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
