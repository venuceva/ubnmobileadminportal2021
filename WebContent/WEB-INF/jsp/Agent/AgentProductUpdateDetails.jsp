<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>

<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">


<script type="text/javascript">
	
var userictadminrules = {
 		
 		rules : {
 			
 			limitcode : { required : true } ,
 		},		
 		messages : {
 			
 		
 			limitcode : { 
		 				required : "Please Select Limit Code.",
 						}
 			
 		} 
 	};
			
			$(function () { 
				
				 $('#limitcode').on('change', function() {
					 var v=this.value; 
					  
					  $('#updatedamtlmt1').text(v.split("-")[1]);
					  $('#updatenumoftran1').text(v.split("-")[2]);
					  $('#updateptamt1').text(v.split("-")[3]);
					  
					  
					  $('#updatedamtlmt').val(v.split("-")[1]);
					  $('#updatenumoftran').val(v.split("-")[2]);
					  $('#updateptamt').val(v.split("-")[3]);
					  
				 });
				
				
				var userStatus = '${responseJSON.status}';
				
				
				
				$('#spn-user-status').append("<a href='#' class='label label-success'  >"+userStatus+"</a>");
				
				 $("#form1").validate(userictadminrules); 
				
		        $('#btn-submit').live('click', function () { 
		    		
		    				var url="${pageContext.request.contextPath}/<%=appName %>/agentproductupdateconfirm.action"; 
		    				$("#form1")[0].action=url;
		    				$("#form1").submit(); 
		    				
		        }); 
		    });  

			$.validator.addMethod("regex", function(value, element, regexpr) {          
				 return regexpr.test(value);
			   }, ""); 
			
			
			function redirectHome(){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
				$("#form1").submit();
				return true;
			}
			
			
			function calcfun(){
				
				
				if(!$("#updatedamtlmt").val()=="" && !$("#updatenumoftran").val()==""){
					
					$("#updateptamt").val($("#updatedamtlmt").val()/$("#updatenumoftran").val())
					
				}
				
			}

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
<s:set value="responseJSON" var="respData" />
</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="dsalimitupdate.action">Agent Product Update</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						Agent Details
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="userid"><strong>Agent ID</strong></label></td>
									<td width="25%">${responseJSON.MERCHANT_ID}
									<input name="MERCHANT_ID" autocomplete="off" type="hidden" class="field" id="MERCHANT_ID"  value="${responseJSON.MERCHANT_ID}"  /></td>
									<td width="25%"><label for="empno"><strong>Agent Name</strong></label></td>
									<td width="25%">${responseJSON.MERCHANT_NAME}
									<input name="MERCHANT_NAME" autocomplete="off" type="hidden" class="field" id="MERCHANT_NAME"  value="${responseJSON.MERCHANT_NAME}" /></td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="fname"><strong>Account Number</strong></label></td>
									<td width="25%">${responseJSON.ACCOUNT_NUMBER}
									<input name="ACCOUNT_NUMBER" autocomplete="off" type="hidden" class="field" id="ACCOUNT_NUMBER"  value="${responseJSON.ACCOUNT_NUMBER}"  /></td>
									<td width="25%"><label for="lname"><strong>Email ID</strong></label></td>
									<td width="25%">${responseJSON.EMAIL}
									<input name="EMAIL" autocomplete="off" type="hidden" class="field" id="EMAIL"  value="${responseJSON.EMAIL}" /></td>
								</tr>
								
								<tr class="odd">
									<td width="25%"><label for="dob"><strong>Mobile No</strong></label></td>
									<td width="25%">${responseJSON.TELEPHONE_NO}
									<input type="hidden"    id="TELEPHONE_NO" name="TELEPHONE_NO" style="width: 200px;" value="${responseJSON.TELEPHONE_NO}" /></td>
									<td width="25%"><label for="mnumber"><strong>Super Agent</strong></label></td>
									<td width="25%">${responseJSON.SUPER_AGENT}
									<input name="SUPER_AGENT" autocomplete="off" type="hidden" class="field" id="SUPER_AGENT"  required=true value="${responseJSON.SUPER_AGENT}" /></td>
								</tr>
								
							
								<tr class="even">
								<td width="25%"><label for="mid"><strong>Current Product Code</strong></label></td>
									<td width="25%">${responseJSON.PRODUCT}
									<input name="PRODUCT" autocomplete="off" type="hidden" class="field" id="PRODUCT"  value="${responseJSON.PRODUCT}"  /></td>
									<td width="25%"></td>
									<td width="25%"></td>
									
								</tr>
								<tr class="even">
								<td width="25%"><label for="gender"><strong>Product Code <font color="red">*</font></strong></label></td>
									<td width="25%">
									<s:select cssClass="chosen-select" headerKey=""
												headerValue="Select" list="#respData.LIMIT_CODE"
												name="limitcode" id="limitcode" requiredLabel="true"
												theme="simple" data-placeholder="Choose Limit Code..."
												required="true" /> 
									</td>
									<td width="25%"><label for="mid"><strong>Product Description</strong></label></td>
									<td width="25%"><div id="updatedamtlmt1"></div>
									<input name="updatedamtlmt" autocomplete="off" type="hidden" class="field" id="updatedamtlmt"    /></td>
									
								</tr>
								<tr class="odd">
									<td width="25%"><label for="gender"><strong>Limit Code </strong></label></td>
									<td width="25%"><div id="updatenumoftran1"></div>
									<input name="updatenumoftran" autocomplete="off" type="hidden" class="field" id="updatenumoftran"  />
									</td>
									<td width="25%"><label for="mid"><strong>Fee Code </strong></label></td>
									<td width="25%"><div id="updateptamt1"></div>
									<input name="updateptamt" autocomplete="off" type="hidden" class="field" id="updateptamt"    /></td>
									
								</tr>
															
							</table>
							
							
						
							
	
						</fieldset>
						<input type="hidden"   id="refno" name="refno"   value="${responseJSON.refno}" />
						<input type="hidden"   id="displayname" name="displayname"   value="${responseJSON.displayname}" />	
					</div>
				</div>
			</div>
			
			
	
		
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100"  ></input> 
			</div>  

			
			
		
			</div>
			

</form>

</body>
</html>
