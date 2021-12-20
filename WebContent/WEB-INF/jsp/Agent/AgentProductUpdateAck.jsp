<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
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
	

$(function () { 
				
				var userStatus = '${responseJSON.status}';
				
				
				
				$('#spn-user-status').append("<a href='#' class='label label-success'  >"+userStatus+"</a>");
				
				
				
		        $('#btn-submit').live('click', function () { 
		    		
		    				var url="${pageContext.request.contextPath}/<%=appName %>/dsalimitupdate.action"; 
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

				
</script>

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
						Agent Product Update Acknowledgment 
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
								<td width="25%"><label for="gender"><strong>Product Code </strong></label></td>
									<td width="25%">${responseJSON.limitcode}
									<input name="limitcode" autocomplete="off" type="hidden" class="field" id="limitcode"   value="${responseJSON.limitcode}" /></td>
									<td width="25%"><label for="mid"><strong>Product Description</strong></label></td>
									<td width="25%">${responseJSON.updatedamtlmt}
									<input name="updatedamtlmt" autocomplete="off" type="hidden" class="field" id="updatedamtlmt"   value="${responseJSON.updatedamtlmt}" /></td>
									
								</tr>
								<tr class="odd">
								<td width="25%"><label for="gender"><strong>Limit Code</strong></label></td>
									<td width="25%">${responseJSON.updatenumoftran}
									<input name="updatenumoftran" autocomplete="off" type="hidden" class="field" id="updatenumoftran" value="${responseJSON.updatenumoftran}" />
									</td>
									<td width="25%"><label for="mid"><strong>Fee Code</strong></label></td>
									<td width="25%">${responseJSON.updateptamt}
									<input name="updateptamt" autocomplete="off" type="hidden" class="field" id="updateptamt"  value="${responseJSON.updateptamt}"  /></td>
									
								</tr>
															
							</table>
							
							<div><strong>${responseJSON.displayname} have been successfully Updated, proceed authorization </strong></div>
						
							
	
						</fieldset>
						<input type="hidden"   id="refno" name="refno"   value="${responseJSON.refno}" />	
					</div>
				</div>
			</div>
			
			
	
		
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Next" width="100"  ></input> 
			</div>  

			
			
		
			</div>
			

</form>

</body>
</html>
