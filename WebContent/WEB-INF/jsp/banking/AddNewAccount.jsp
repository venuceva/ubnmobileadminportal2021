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
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
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
$(document).ready(function(){
	
	$("#customerId").prop("readonly",false);
	$("#accountno").prop("readonly",true);
	 
	var acctRules = {
		   rules : {
			   customerId : { required : true},
			   institute:{ required : true}
		   },  
		   messages : {
			   customerId : { 
			   required : "Please enter Customer Id."
				},
			   institute:{
			   required : "Please select Institute"
			   }
		   } 
		}; 
	
	$('#btn-submit').click(function(){ 
			$("#form1").validate(acctRules);
			if($("#form1").valid()) { 
 				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fetchCustomerInfoAct.action'; 
				$('#form1').submit();
				 return true; 
			} else { 
				return false;
			}
	});
	
	
	
});


function radioclick(val){
	if(val=="custid"){
		$("#customerId").prop("readonly",false);
		$("#accountno").prop("readonly",true);
	}else{
		$("#customerId").prop("readonly",true);
		$("#accountno").prop("readonly",false);
	}
	
}


</script>




</head>

<body>
	<form name="form1" id="form1" method="post" action="">
		<div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="#">Mobile Banking Customer Registration </a></li>
				</ul>
			</div>
		<table height="3">
			 <tr>
				<td colspan="3">
					<div class="messages" id="messages"><s:actionmessage /></div>
					<div class="errors" id="errors"><s:actionerror /></div>
				</td>
			</tr>
		</table>
		<div class="row-fluid sortable"><!--/span-->
			<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Add New Account
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
								<%-- <tr class="even">
									<td width="20%"><label for="Service ID"><strong>Select Core Banking<font color="red">*</font></strong></label></td>
									<td width="30%" colspan=3> 
									
									 <s:select 
										style="width: 220px;"
										headerKey="" 
										headerValue="Select"
										list="responseJSON.institutesel" 
										name="institute" 
	 									id="institute" 
	 									value="institute" 
										requiredLabel="true" 
										theme="simple"
										cssClass="chosen-select"
										data-placeholder="Choose Core Banking..." 
	 									 />  &nbsp; <label id="institute-id" class="errors" ></label> 
	 							</td>		
								</tr> --%>
								<tr class="odd">
									<td ><label for="Service ID">
									<strong>Customer Id<font color="red">*</font></strong></label></td>
									<td ><input type="text" name="customerId"  id="customerId" required=true  value="<s:property value='customerId' />"   />   </td>	
								</tr>
								
								
							</table>
							<input type="hidden" id="institute" name="institute" value="INSTID2"/>
							</fieldset>
					</div>
			</div>
		</div>
	<div class="form-actions">
		<a  class="btn btn-success" href="#" id="btn-submit" name="btn-submit">Submit</a>
	</div>
</div> 
</form>
<script type="text/javascript">
$(function(){
	
	var config = {
      '.chosen-select'           : {},
      '.chosen-select-deselect'  : {allow_single_deselect:true},
      '.chosen-select-no-single' : {disable_search_threshold:10},
      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chosen-select-width'     : {width:"95%"}
    };
	
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }  
});  
</script>
</body>
</html>
