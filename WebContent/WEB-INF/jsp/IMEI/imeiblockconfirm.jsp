 <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>CEVA </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
	
	
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
<script type="text/javascript" >
binDetails = "";
var prdIndex = new Array();


 
 


		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/imeiblock.action';
	$("#form1").submit();
	return true;
}

function redirectActAck(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/imeidetailsack.action';
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
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"> IMEI ${responseJSON.imeistatus}</a></li>
				</ul>
			</div>
			
			
	
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>IMEI ${responseJSON.imeistatus} Confirmation
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				
				
				
				
				<div class="box-content"> 
				<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="File"><strong>IMEI Number</strong></label></td>
									<td width="25%">${responseJSON.imeinumber}</td>
								
									<td width="25%"></td> 
									<td width="25%">
									
									</td>
									
									</tr>
									<tr class="odd">
									<td width="25%"><label for="File"><strong>Reason<font color="red">*</font></strong></label></td>
									<td width="25%">
									<textarea rows="5" cols="100" id="reason" name="reason"></textarea>
									
									</td>
								
									<td width="25%"></td> 
									<td width="25%">
									
									</td>
									
									</tr>
									
				</table>
				
				<input type="hidden" name="imeinumber"  id="imeinumber"  value="${responseJSON.imeinumber}" />  
				<input type="hidden" name="imeistatus"  id="imeistatus"  value="${responseJSON.imeistatus}" />  
					
					
				</div>
			</div>
		</div> 
	
		 <div class="form-actions" align="left"> 
		        <input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectActAck();" value="Confirm" />
			</div> 
			 
		</div> 
	 

  
   
  
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 