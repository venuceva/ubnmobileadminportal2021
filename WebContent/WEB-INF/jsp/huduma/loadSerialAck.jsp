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
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   

<style type="text/css">
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
  font-weight: bold;
  color: red;
  padding: 2px 8px;
  margin-top: 2px;
}
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
</style>  
<script type="text/javascript"> 
$(function(){	
	$('#btn-make-payment').live('click',function() {  
		$("#form1")[0].action="${pageContext.request.contextPath}/<%=appName %>/loadSerial.action";
		$('#form1').submit();  
	});
});
	 

</script> 
	 
<link rel="shortcut icon" href="images/favicon.ico"> 
</head>
 <body> 
 <form name="form1" id="form1" method="post" > 
	
	 <div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
				<li>
					<a href="home.action">Home</a> <span class="divider">&gt;&gt;</span>
				</li>
				<li><a href="loadSerial.action">Load Serial No</a> <span class="divider">&gt;&gt;</span>
				</li>
				 <li><a href="#">Load Serial No Acknowledgement</a>  
				</li>
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
	 
	<div class="row-fluid sortable" id="kra-information"> 
		<div class="box span12">
								
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>&nbsp;Load Serial No Acknowledgement
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 		
			<div class="box-content">
				<fieldset> 
					<table width="950"  border="0" cellpadding="5" cellspacing="1" 
					class="table table-striped table-bordered bootstrap-datatable " >
					  <tr > 
							<td> <strong>Serial Loaded Successfully.</strong> </td>
						</tr> 
				</table>
 				</fieldset>  
			</div>
		</div>
	</div>  
	<div class="form-actions"> 
		<input type="button" name="btn-make-payment" class="btn btn-success" id="btn-make-payment" value="Next" />
 		<span id ="error_dlno" class="errors"></span>		 	
	</div>  
	</div>
	 
	 
</form>	
</body> 
</html>
