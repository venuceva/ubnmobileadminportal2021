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

var createProductRules = {
		   rules : {
         requesttype : { required : true}
		   },  
		   messages : {
			
		         requesttype : { 
			         required : "Please check Authorize or Reject Option. "
			         }  
		    } 
		 };


	$.validator.addMethod("regex", function(value, element, regexpr) {          
		 return regexpr.test(value);
	   }, ""); 


$(document).ready(function() {
	
	
	$('#btn-submit').live('click', function () { 
		$("#form1").validate(createProductRules);
		if($('#requesttype').val()=="Reject" && $('#reason').val()==""){
			$("#errormsg").html("<font colour='red'>Please Enter Reason </font>");
			return false;
		}else{
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fileuploadrequestapprovalinfoconfirm.action';
			$("#form1").submit();
			return true;
		}
	});
});



		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/agentrequestapproval.action';
	$("#form1").submit();
	return true;
}


function fun(id){
	//alert(id);
	$('#reasontg').css("display", "none");
	$("#requesttype").val(id);
	if(id=="Reject"){
		$('#reasontg').css("display", "");
	}
	
}

 
</script> 
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">File Upload Approval</a></li>
				</ul>
			</div>
			<div id="errormsg" class="errores"></div>	
		
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Transaction Information
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
							<td width="30%" >${responseJSON.VIEW_LMT_DATA.REF_NUM}
							<input type="hidden" name="filerefno"  id="filerefno" value="${responseJSON.VIEW_LMT_DATA.REF_NUM}" />
							<td width="20%" ><label for="Customer Name"><strong>File Type</strong></label></td>
							<td width="30%" >${responseJSON.VIEW_LMT_DATA.F_TYPE}
							<input type="hidden" name="filetype"  id="filetype" value="${responseJSON.VIEW_LMT_DATA.F_TYPE}" /></td>
							
						</tr>
						
						 <tr >
							<td width="20%" ><label for="Customer ID"><strong>File Name</strong></label></td>
							<td width="30%" >${responseJSON.VIEW_LMT_DATA.f_name}
							<input type="hidden" name="fname"  id="fname" value="${responseJSON.VIEW_LMT_DATA.f_name}" />
							<td width="20%" ><label for="Customer Name"><strong>No of Records</strong></label></td>
							<td width="30%" >${responseJSON.VIEW_LMT_DATA.NUM_OF_RECORD}
							<input type="hidden" name="noofrecords"  id="noofrecords" value="${responseJSON.VIEW_LMT_DATA.NUM_OF_RECORD}" /></td>
							
						</tr>
						 <tr >
							<td width="20%" ><label for="Customer ID"><strong>Upload Date</strong></label></td>
							<td width="30%" >${responseJSON.VIEW_LMT_DATA.UPLOAD_DATE}
							<input type="hidden" name="uploaddt"  id="uploaddt" value="${responseJSON.VIEW_LMT_DATA.UPLOAD_DATE}" />
							<td width="20%" ><label for="Customer Name"><strong>Maker Id</strong></label></td>
							<td width="30%" >${responseJSON.VIEW_LMT_DATA.MAKER_ID}
							<input type="hidden" name="makerid"  id="makerid" value="${responseJSON.VIEW_LMT_DATA.MAKER_ID}" /></td>
							
						</tr>
						
						<tr >
								
								<td colspan="4">
								<strong>Approval</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="radio" name="request" id="Approval" onclick="fun(this.id)"> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<strong>Reject</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="radio" name="request" id="Reject" onclick="fun(this.id)">
								</td>
								
						 </tr>
						 
						  <tr style="display:none" id="reasontg">
								<td><strong><label for="Date Created"><strong>Reason</strong><font color="red">*</font></label></strong></td>
								<td>
								<textarea rows="10" cols="5" name="reason"  id="reason"></textarea>
								</td>
								<td></td>
								<td></td>
						 </tr>
						
						
				</table>
				
				
				<input type="hidden" id="requesttype" name="requesttype" />
					
						
				</div>
			</div>
		</div> 
	
	<input name="application" type="hidden" id="application"   value="${responseJSON.application}"  />
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100"  >
			</div> 
			 
		</div> 
	 

  
   
  
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 