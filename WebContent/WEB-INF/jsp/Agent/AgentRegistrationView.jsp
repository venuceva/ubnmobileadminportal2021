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
				//alert(userStatus);
				var text = "";
				var text1 = "";
				
				if( userStatus == 'UnLock') {
					text = "<a href='#' class='label label-success'  >"+userStatus+"</a>";
					text1 = "<a href='#'  class='label label-warning'  > Lock</a>";
				} else if( userStatus == 'Lock') {
					text = "<a href='#'  class='label label-warning'  >"+userStatus+"</a>";
					text1 = "<a href='#' class='label label-success'  >UnLock</a>";
				}else if( userStatus == 'Active') {
					text = "<a href='#'  class='label label-warning'  >"+userStatus+"</a>";
					text1 = "<a href='#' class='label label-success'  >Active</a>";
				}else if( userStatus == 'Checker Pending') {
					text = "<a href='#'  class='label label-warning'  >"+userStatus+"</a>";
					text1 = "<a href='#' class='label label-success'  >Checker Pending</a>";
				} 
				
				$('#spn-user-status').append(text);
				$('#spn-update-user-status').append(text1);
				
				
				
		    });  

			
			
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
					<li><a href="agentregmodify.action">Agent Registration Modify</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						View Agent Details
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="fname"><strong>First Name</strong></label></td>
									<td width="25%">${responseJSON.fname}
									<input name="fname" autocomplete="off" type="hidden" class="field" id="fname"  value="${responseJSON.fname}"  /></td>
									<td width="25%"><label for="lname"><strong>Last Name</strong></label></td>
									<td width="25%">${responseJSON.lname}
									<input name="lname" autocomplete="off" type="hidden" class="field" id="lname"  value="${responseJSON.lname}" /></td>
								</tr>
								
								<tr class="even">
									<td width="25%"><label for="dob"><strong>Date Of Birth</strong></label></td>
									<td width="25%">${responseJSON.dob}
									<input type="hidden"    id="dob" name="dob" style="width: 200px;" value="${responseJSON.dob}" /></td>
									<td width="25%"><label for="mnumber"><strong>Mobile Number</strong></label></td>
									<td width="25%">${responseJSON.mnumber}
									<input name="mnumber" autocomplete="off" type="hidden" class="field" id="mnumber"  required=true value="${responseJSON.mnumber}" /></td>
								</tr>
								<tr class="odd">
									<td width="25%"><label for="mid"><strong>Mail Id</strong></label></td>
									<td width="25%">${responseJSON.mailid}
									<input name="mailid" autocomplete="off" type="hidden" class="field" id="mailid"  value="${responseJSON.mailid}"  /></td>
									<td width="25%"><label for="gender"><strong>Gender</strong></label></td>
									<td width="25%">${responseJSON.gender}
  									<input name="gender" autocomplete="off" type="hidden" class="field" id="gender"  value="${responseJSON.gender}" />
									</td>
								</tr>
								
								<tr class="even">
									<td width="25%"  ><label for="officeaddress"><strong>Office Address</strong></label></td>
									<td width="75%" colspan="3" >
									<textarea rows="4" cols="50" >${responseJSON.offaddress}</textarea>
									<input name="offaddress" autocomplete="off" type="hidden" class="field" id="offaddress"  value="${responseJSON.offaddress}" />
									</td>
								</tr>
								<tr class="odd">
									<td width="25%"  ><label for="permanentaddress"><strong>Permanent Address</strong></label></td>
									<td width="75%" colspan="3" >
									<textarea rows="4" cols="50" >${responseJSON.offaddress}</textarea>
									<input name="peraddress" autocomplete="off" type="hidden" class="field" id="peraddress"  value="${responseJSON.peraddress}" />

									</td>
								</tr>
								
								<tr>
									<td><strong><label for="User Status"><strong>Current Agent Status</strong></label></strong></td>
									<td>
										<span id="spn-user-status"></span> 
										<input type="hidden" name="status"  id="status" value="${responseJSON.status}" /> 
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
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Home" />
				</input> 
			</div>  

			
			
		
			</div>
			

</form>

</body>
</html>
