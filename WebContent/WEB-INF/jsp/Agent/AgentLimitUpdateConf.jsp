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
		    		
		    				var url="${pageContext.request.contextPath}/<%=appName %>/dsalimitupdateack.action"; 
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
					 <li><a href="dsalimitupdate.action">DSA Product Update</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						DSA Product Update Confirmation 
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="userid"><strong>User ID</strong></label></td>
									<td width="25%">${responseJSON.userid}
									<input name="userid" autocomplete="off" type="hidden" class="field" id="userid"  value="${responseJSON.userid}"  /></td>
									<td width="25%"><label for="empno"><strong>Employee No</strong></label></td>
									<td width="25%">${responseJSON.empno}
									<input name="empno" autocomplete="off" type="hidden" class="field" id="empno"  value="${responseJSON.empno}" /></td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="fname"><strong>First Name</strong></label></td>
									<td width="25%">${responseJSON.fname}
									<input name="fname" autocomplete="off" type="hidden" class="field" id="fname"  value="${responseJSON.fname}"  /></td>
									<td width="25%"><label for="lname"><strong>Last Name</strong></label></td>
									<td width="25%">${responseJSON.lname}
									<input name="lname" autocomplete="off" type="hidden" class="field" id="lname"  value="${responseJSON.lname}" /></td>
								</tr>
								
								<tr class="odd">
									<td width="25%"><label for="dob"><strong>Job Title</strong></label></td>
									<td width="25%">${responseJSON.jtitle}
									<input type="hidden"    id="jtitle" name="jtitle" style="width: 200px;" value="${responseJSON.jtitle}" /></td>
									<td width="25%"><label for="mnumber"><strong>Mobile Number</strong></label></td>
									<td width="25%">${responseJSON.mnumber}
									<input name="mnumber" autocomplete="off" type="hidden" class="field" id="mnumber"  required=true value="${responseJSON.mnumber}" /></td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="mid"><strong>Mail Id</strong></label></td>
									<td width="25%">${responseJSON.mailid}
									<input name="mailid" autocomplete="off" type="hidden" class="field" id="mailid"  value="${responseJSON.mailid}"  /></td>
									<td width="25%"><label for="gender"><strong>Branch Code - Branch Name </strong></label></td>
									<td width="25%">${responseJSON.branchcode}
  									<input name="branchcode" autocomplete="off" type="hidden" class="field" id="branchcode"  value="${responseJSON.branchcode}" />
									</td>
								</tr>
							
								<tr class="odd">
								<td><strong><label for="User Status"><strong>Current Status</strong></label></strong></td>
									<td>
										<span id="spn-user-status"></span>  
										<input type="hidden" name="status"  id="status"  value="${responseJSON.status}" />
									</td> 
									<td width="25%"></td>
									<td width="25%"></td>
								</tr>
							
								<tr class="even">
								<td width="25%"><label for="mid"><strong>Current Product Code</strong></label></td>
									<td width="25%">${responseJSON.currlimitcode}
									<input name="currlimitcode" autocomplete="off" type="hidden" class="field" id="currlimitcode"  value="${responseJSON.currlimitcode}"  /></td>
									<td width="25%"><label for="mid"><strong>Product Description</strong></label></td>
									<td width="25%">${responseJSON.damtlmt}
									<input name="damtlmt" autocomplete="off" type="hidden" class="field" id="damtlmt"  value="${responseJSON.damtlmt}"  /></td>
									
								</tr>
								<tr class="odd">
								<td width="25%"><label for="gender"><strong>Current Limit Code </strong></label></td>
									<td width="25%">${responseJSON.numoftran}
									<input name="numoftran" autocomplete="off" type="hidden" class="field" id="numoftran"  value="${responseJSON.numoftran}" />
									</td>
									<td width="25%"><label for="mid"><strong>Current Fee Code</strong></label></td>
									<td width="25%">${responseJSON.ptamt}
									<input name="ptamt" autocomplete="off" type="hidden" class="field" id="ptamt"  value="${responseJSON.ptamt}"  /></td>
									
									
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
							
							
						
							
	
						</fieldset>
						<input type="hidden"   id="refno" name="refno"   value="${responseJSON.refno}" />	
						<input type="hidden"   id="displayname" name="displayname"   value="${responseJSON.displayname}" />	
					</div>
				</div>
			</div>
			
			
	
		
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Confirm" width="100"  ></input> 
			</div>  

			
			
		
			</div>
			

</form>

</body>
</html>
