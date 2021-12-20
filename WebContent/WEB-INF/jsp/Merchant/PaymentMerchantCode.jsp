
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String ctxstr = request.getContextPath();
%>
<%
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>




<SCRIPT type="text/javascript"> 




$(document).ready(function(){  
	
	
	

	
	$('#btn-submit').live('click',function() { 
		$("#form1").validate(billerrules); 
 		if($("#form1").valid()) {
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName%>/MerchantGroupConfirm.action";
			$("#form1").submit();	
			return true;
		} else {
 			return false;
		} 	
	}); 
	
	$('#btn-Cancel').live('click',function() { 
	
			
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName%>/merchantmenagenet.action";
		$("#form1").submit(); 
	});

});



	//For Closing Select Box Error Message_End
	
	
	



</SCRIPT>

<s:set value="responseJSON" var="respData"/> 
</head>
<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
		<div id="content" class="span10">

			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider">&gt;&gt; </span></li>
					<li><a href="#"> Payment Merchant</a></li>
				</ul>
			</div>

			<table height="3">
				<tr>
					<td colspan="3">
						<div class="messages" id="messages">
							<s:actionmessage />
						</div>
						<div class="errors" id="errors">
							<s:actionerror />
						</div>
					</td>
				</tr>
			</table>

			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Payment Merchant Code
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>

						</div>
					</div>

					<div class="box-content">
						<fieldset>
							<table width="98%" border="0" cellpadding="5" cellspacing="1">
							<tr>
									<td colspan="4" align="center">
									<table>
									<tr>
									<td colspan="2" align="center"><img style="width: 180px; height: 70px;" src="${pageContext.request.contextPath}/images/BANK_logo.png" /></td>
									
								</tr>
								<tr>
									<td ><Strong>Merchant Id</Strong> </td><td>:</td><td> <Strong>${responseJSON.orgid}</Strong></td>
									
								</tr>
								<tr>
									<td  ><Strong>Merchant Name</Strong> </td><td>:</td><td> <Strong>${responseJSON.orgname}</Strong></td>
									
								</tr>
								</table>
							</td></tr>	
								<tr>
									<td colspan="4" align="center"><img  src="${responseJSON.SERVICE_CODE}" ></td>
									
								</tr>
								<tr>
									<td colspan="4" align="center"><Strong><h2>${responseJSON.SERVICE_NUMBER}</h2></Strong></td>
									
								</tr>
								

							</table>
							
							
							
						</fieldset>
					</div>
				</div>
			</div>

		
			<!-- <div class="form-actions">
				<input type="button" class="btn btn-primary" name="btn-submit"
					id="btn-submit" value="Submit" width="100"></input> &nbsp;<input
					type="button" class="btn " name="btn-Cancel" id="btn-Cancel"
					value="Back" width="100"></input>

			</div> -->

		</div>
	</form>

</body>
</html>
