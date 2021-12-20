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


$(document).ready(function() {
	
	if("${responseJSON.requesttype}"=="Reject"){
		$('#reasontg').css("display", "");
	}
	
	$('#btn-submit').live('click', function () { 
		$('#btn-submit').prop('disabled', true);
		$('#myProgress').css('display', "");
		
		ajaxcnt();
	  $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fileuploadinfoapprovalack.action';
	$("#form1").submit();
	return true;  
	});
});



		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/agentrequestapproval.action';
	$("#form1").submit();
	return true;
}

function ajaxcnt(){
	
	if("${responseJSON.filetype}"!="PREENROLMENT"){
		
	
	
	var queryString1 = "method=fileuploadrec&refno="+$('#filerefno').val();
	
	$.getJSON("postJson.action", queryString1,function(data) {
		$('#cnt').text(data.finalCount);
	});
	
	setTimeout(ajaxcnt, 5000);
	}
}



 
</script> 

<style>
#myProgress {
  width: 100%;
  background-color: #ddd;
}

#myBar {
  
  height: 30px;
  background-color: #4CAF50;
  text-align: center;
  line-height: 30px;
  color: white;
}
</style>
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">File Upload Approval Confirmation</a></li>
				</ul>
			</div>
			
		
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Transaction Confirmation
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				
				
				
				
				<div class="box-content"> 
				
				
				<table width="950"  border="0" cellpadding="5" cellspacing="1"
					class="table table-striped table-bordered bootstrap-datatable " >
					  <tr >
							<td width="20%" ><label for="Customer ID"><strong>File Reference Number</strong></label></td>
							<td width="30%" >${responseJSON.filerefno}
							<input type="hidden" name="filerefno"  id="filerefno" value="${responseJSON.filerefno}" />
							<td width="20%" ><label for="Customer Name"><strong>File Type</strong></label></td>
							<td width="30%" >${responseJSON.filetype}
							<input type="hidden" name="filetype"  id="filetype" value="${responseJSON.filetype}" /></td>
							
						</tr>
						<tr >
							<td width="20%" ><label for="Customer ID"><strong>File Name</strong></label></td>
							<td width="30%" >${responseJSON.fname}
							<input type="hidden" name="fname"  id="fname" value="${responseJSON.fname}" />
							<td width="20%" ><label for="Customer Name"><strong>No of Records</strong></label></td>
							<td width="30%" >${responseJSON.noofrecords}
							<input type="hidden" name="noofrecords"  id="noofrecords" value="${responseJSON.noofrecords}" /></td>
							
						</tr>
						 <tr >
							<td width="20%" ><label for="Customer ID"><strong>Upload Date</strong></label></td>
							<td width="30%" >${responseJSON.uploaddt}
							<input type="hidden" name="uploaddt"  id="uploaddt" value="${responseJSON.uploaddt}" />
							<td width="20%" ><label for="Customer Name"><strong>Maker Id</strong></label></td>
							<td width="30%" >${responseJSON.makerid}
							<input type="hidden" name="makerid"  id="makerid" value="${responseJSON.makerid}" /></td>
							
						</tr>
						<tr >
								
								<td colspan="4">
								<strong>${responseJSON.requesttype}</strong>
								</td>
								
						 </tr>
						 
						  <tr style="display:none" id="reasontg">
								<td><strong><label for="Date Created"><strong>Reason</strong></label></strong></td>
								<td>
								<textarea rows="10" cols="5" name="reason"  id="reason" readonly>${responseJSON.reason}</textarea>
								</td>
								<td></td>
								<td></td>
						 </tr>
						
				</table>
				
				
				<input type="hidden" id="requesttype" name="requesttype" value="${responseJSON.requesttype}"/>
					
						
				</div>
			</div>
		</div> 
	
	<input name="application" type="hidden" id="application"   value="${responseJSON.application}"  />
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Confirm" width="100"  >
			</div> 
			  <div id="myProgress" style="display:none">
			  <div id="myBar" >Please wait records are processing ,Total completed processing records <span id="cnt">0</span></div>
			</div>
		</div> 
	

  
   
  
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 