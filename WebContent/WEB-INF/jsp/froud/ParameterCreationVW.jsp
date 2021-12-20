<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>BackOffice</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%> 
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/lightbox.css"  type="text/css" />

<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js"></script>
 
<script type="text/javascript" > 

function redirectAct()
{
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
	$("#form1").submit();
	return true;
} 

function subitReq()
{
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fraudactconfiguration.action';
	$("#form1").submit();
	return true;
} 





	
</script>
<s:set value="responseJSON" var="respData" />
</head>

<body>
<form name="form1" id="form1" method="post" action="">
	<div id="content" class="span10">  
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="#">Fraud Monitor Configuration</a></li>
				</ul>
			</div>
		
	
		 
		  <div class="row-fluid sortable"><!--/span-->
			<div class="row-fluid sortable">
					
					<div id="primaryDetails" class="box-content">
						
							<div class="box span12"> 
					<div class="box-header well" data-original-title>Fraud Monitor Configuration View
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
			<div class="box-content"> 				
			<fieldset>
			
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							   class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
							   					<tr class="odd">
														<td width="20%"><label for="Channel"><strong>Fraud Id</strong></label></td>
														<td width="30%">${responseJSON.fraudcode}</td>											
														<td width="20%"><label for="Services"><strong>Fraud Description </strong></label></td>
														<td width="30%">${responseJSON.frauddesc}</td>	
														
													</tr>
													<tr class="odd">
														<td width="20%"><label for="custsms"><strong>Customer</strong></label></td>
														<td width="30%" colspan="3"><span><strong>SMS</strong></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>${responseJSON.Customersms}</span>
														<span><strong>E-MAIL</strong></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>${responseJSON.Customeremail}</span>
														
														</td>											
														
														
													</tr>
													<tr class="odd">
													<td width="20%"><label for="Services"><strong>Contact Mail Id </strong></label></td>
														<td width="30%" colspan="3">${responseJSON.contcentermailid}</td>	
													</tr>
													<tr class="odd">
														<td width="20%"><label for="custsms"><strong>Decision</strong></label></td>
														<td width="30%">${responseJSON.decisions}</td>											
														<td width="20%"></td>
														<td width="30%"></td>	
														
													</tr>
													<tr class="odd">
														<td width="20%"><label for="rule"><strong>Rule</strong></label></td>
														<td colspan="3">${responseJSON.ruledesc}</td>	
														
													</tr>
													
												
													
						</table>
							
			</fieldset>
			
			</div> 
			
			</div> </div> 
			<input type="hidden" name="contcentermailid" id="contcentermailid"    value="${responseJSON.contcentermailid}"  />
			<input type="hidden" name="frauddesc" id="frauddesc"   value="${responseJSON.frauddesc}"  />
			<input type="hidden" name="fraudcode" id="fraudcode"    value="${responseJSON.fraudcode}"  />
			
			<input type="hidden"  name="Customersms" id="Customersms"   value="${responseJSON.Customersms}" />
			<input type="hidden"  name="Customeremail" id="Customeremail"   value="${responseJSON.Customeremail}" />
			<input type="hidden"  name="ruledesc" id="ruledesc"  value="${responseJSON.ruledesc}"/>
			<input type="hidden"  name="rulecode" id="rulecode"  value="${responseJSON.rulecode}" />
			<input type="hidden"  name="decisions" id="decisions"  value="${responseJSON.decisions}" />
			
			<span id="ruledetaails1"></span>
			
		<div class="form-actions" id="submitdata" > 
				
				
				
				<input type="button" id="non-printable" class="btn btn-success" onclick="redirectAct();" value="Home" />
				<input type="button" id="non-printable" class="btn btn-success" onclick="subitReq();" value="Next" />
		</div>
		
		
		
		</div>
		
		</div>
		
		
		 
		
 
	</div>
</form>	
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 

</body>
</html>
