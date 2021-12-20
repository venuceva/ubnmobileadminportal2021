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
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<s:set value="responseJSON" var="respData"/>
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
</style> 
<script type="text/javascript" > 

$('#btn-back').live('click', function () { 
	
	  $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/agentRegistration.action';
		$("#form1").submit(); 
		return true;
	});


</script>

</head> 
<body>
	<form name="form1" id="form1" method="post" action=""> 
			<div id="content" class="span10"> 
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="#">Agent registration view</a></li>
				
				</ul>
			</div>
			
			
			<div class="row-fluid sortable" id="agentRegistration"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Agent Registration view
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

							</div>
						</div>

						<div id="primaryDetails" class="box-content">
							<fieldset>   
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd">
										<td width="20%"><strong><label for="Terminal ID">Terminal ID</label></strong></td>
										<td width="30%">${responseJSON.terminalid}</td>			
											
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Branch">Branch</label></strong></td>
										<td width="30%">${responseJSON.branch}</td>
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Zone">Zone</label></strong></td>
									<td width="30%">${responseJSON.zone}</td>
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Physical premis">Physical premis</label></strong></td>
										<td width="30%">${responseJSON.physicalpremis}</td>
									
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Agent Bank Account Number">Agent Bank Account Number</label></strong></td>
										<td width="30%">${responseJSON.agentbankacnumber}</td>
										
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Number of Outlets">Number of Outlets</label></strong></td>
										<td width="30%">${responseJSON.numberofoutlets}</td>
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Comercial Activity">Comercial Activity</label></strong></td>
										<td width="30%">${responseJSON.comercialActivity}</td>	
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Date of Birth">Date of Birth</label></strong></td>
										<td width="30%">${responseJSON.dob}</td>
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Registration Date">Registration Date</label></strong></td>
										<td width="30%">${responseJSON.registrationDate}</td>
									</tr>
									
									 
								</table>
							 </fieldset> 
							 
							</div>
							 
						</div>
					</div> <!-- end of span -->
			
				 
			<span id="multi-row-data" name="multi-row-data" class="multi-row-data" style="display:none" ></span>
			<div class="form-actions"> 
				<input type="button" class="btn" name="btn-back" id="btn-back" value="Back" /> 
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div> 
	</div> 
</form>
</body>
</html>
