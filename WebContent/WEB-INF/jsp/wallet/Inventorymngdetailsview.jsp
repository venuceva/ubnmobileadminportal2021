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
<link href="${pageContext.request.contextPath}/css/SheepToasts/body.css" rel="stylesheet" type="text/css">


<script type="text/javascript">
	
	
			
			$(function () { 
				
				if("${responseJSON.A_STATUS}"=="Active"){
					
					$("#status1").html("<div class='label label-success' >Active</div>");
				}else{
					$("#status1").html("<div class='label label-important' >Deactive</div>");
				}
				
				if("${responseJSON.T_STATUS}"=="Active"){
					
					$("#termilstatus").html("<div class='label label-success' >Active</div>");
				}else{
					$("#termilstatus").html("<div class='label label-important' >Deactive</div>");
				}
				
				if("${responseJSON.S_STATUS}"=="Active"){
					
					$("#storestatus").html("<div class='label label-success' >Active</div>");
				}else{
					$("#storestatus").html("<div class='label label-important' >Deactive</div>");
				}
				
				
		        $('#btn-submit').live('click', function () { 
		    		
		    				var url="${pageContext.request.contextPath}/<%=appName %>/inventorymng.action"; 
		    				$("#form1")[0].action=url;
		    				$("#form1").submit(); 
		        });			
		      
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
					 <li> <a href="#"> Inventory Management</a> </li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						Wallet Agent Details
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">  
							<tr class="even" id="accno" >
							<td width="25%"><label for="From Date"><strong>Core Bank Account Number</strong></label></td>
							<td width="25%">${responseJSON.ACCOUNT_NO}</td>
							<td width="25%"><strong>User Id</strong></td>
							<td width="25%">${responseJSON.MOBILE_NUMBER}
							</td>
							</tr>
							<tr class="even" id="accno" >
							<td width="25%"><label for="From Date"><strong>Wallet Account Number</strong></label></td>
							<td width="25%">${responseJSON.W_ACCT_NO}</td>
							<td ><strong>Agent Customer Id</strong></td>
							<td >${responseJSON.ID}
							</td>
							</tr>
					 	 <tr class="even">
							<td width="25%"><label for="From Date"><strong>Name</strong></label></td>
							<td width="25%">${responseJSON.FIRST_NAME}</td>
							<td><label for="Product"><strong>Status</strong></label></td>
							<td><div id="status1">  </td>
							</tr> 
				 </table>
				</fieldset> 
				
				<div class="row-fluid sortable">
						<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Store Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
									</div>
								</div>

								<div id="communicationDetails" class="box-content">
									<fieldset>
										<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
										   <tr class="odd">
										   <td width="25%"><label for="Address Line 1"><strong>Store Name</strong></label></td>
												<td width="25%">${responseJSON.STORE_NAME}
												</td>
										   <td width="25%"><label for="Address Line 1"><strong>Store Id</strong></label></td>
												<td width="25%">${responseJSON.STORE_ID}
											</td>
												
										     	
										   
												
											</tr>
											
											<tr class="odd">
										   <td width="25%"><label for="Address Line 1"><strong>Store Manager</strong></label></td>
												<td width="25%">${responseJSON.B_OWNER_NAME}
												</td>
										   <td width="25%"><label for="Address Line 1"><strong>Area</strong></label></td>
												<td width="25%">${responseJSON.T_AREA}
											</td>
												
										     	
										   
												
											</tr>
											
										<tr class="odd">
										   <td width="25%"><label for="Address Line 1"><strong>State</strong></label></td>
												<td width="25%">${responseJSON.A_R_STATE}
												</td>
										   <td width="25%"><label for="Address Line 1"><strong>Local Government</strong></label></td>
												<td width="25%">${responseJSON.A_RL_LGA}
											</td>
												
										     	
										   
												
											</tr>
											
											<tr class="odd">
										   <td width="25%"><label for="Address Line 1"><strong>Address</strong></label></td>
												<td width="25%">${responseJSON.ADDRESS}
												</td>
										   <td width="25%"><label for="Address Line 1"><strong>Status</strong></label></td>
												<td width="25%"><div id="storestatus"></div>
											</td>
												
										     	
										   
												
											</tr>
										  

									</table>
								</fieldset>
							</div>
						</div>
				</div> 
				
				<div class="row-fluid sortable">
						<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Terminal Information
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
									</div>
								</div>

								<div id="communicationDetails" class="box-content">
									<fieldset>
										<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
											
											
										   <tr class="even">
										    <td width="25%"><label for="Address Line 1"><strong>Terminal Id</strong></label></td>
												<td width="25%">${responseJSON.TERMINAL_ID}</td>
										   		<td width="25%"><label for="IDType"><strong>Terminal Make</strong></label></td>
												<td width="25%">${responseJSON.TERMINAL_MAKE}</td>
												
										   </tr>
										    

										   <tr class="odd">
										   <td width="25%"><label for="IDNumber"><strong>Model Number</strong></label>	</td>
											<td width="25%">${responseJSON.MODEL_NO}</td>
										   <td ><label for="Address Line 1"><strong>Serial Number</strong></label></td>
												<td >${responseJSON.SERIAL_NO}</td>
										</tr>
										<tr class="odd">		
										     	<td><label for="Address Line 1"><strong>Status</strong></label></td>
												<td><div id="termilstatus"></div>
												
												</td>
										   <td></td>
										    <td></td>
												
											</tr>
											
										
										  

									</table>
								</fieldset>
							</div>
						</div>
				</div>
						
						
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
