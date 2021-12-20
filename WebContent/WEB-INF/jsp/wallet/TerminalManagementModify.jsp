<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>NBK Salary Processing</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%> 
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

.messages {
	font-weight: bold;
	color: green;
	padding: 2px 8px;
	margin-top: 2px;
}

.errors {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}
</style>
<script type="text/javascript">


var rules = {
		   rules : {
			  
			   terminalmake : { required : true},
			   modelnumber : { required : true},
			   serialnumber : { required : true}
			   
		   },  
		   messages : {
			   
				
				terminalmake : { 
			   		required : "Please Select Terminal Make."
				},
				modelnumber : { 
			   		required : "Please Select Model Number."
				},
				serialnumber : { 
			   		required : "Please Enter Serial Number."
				},
				
		   } 
		 };
		 
$(function() {  
	
	 

	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){
		$("#form1").validate(rules);
		
		var queryString = "entity=${loginEntity}&method=searchTerminalSerial&fname="+$('#serialnumber').val();
		
		$.getJSON("postJson.action", queryString,function(data) { 
			if(data.message=="SUCCESS"){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/terminalmanagementmodifyConf.action'; 
				$("#form1").submit();
				return true;
			}else{ 
		    	  
				$('#errors').text(data.message);

			 }
		
		}); 
	}); 
});

function redirectfun()
{
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/terminalmanagement.action';
	$("#form1").submit();
	return true; 
	}
	
	
	
$(function(){
	
	


	if("<s:property value='#respData.limitcodedetails.STATUS' />"=="Active"){
		
		$("#tstatus1").html("<div class='label label-success' >Active</div>");
	}else if("<s:property value='#respData.limitcodedetails.STATUS' />"=="Not yet Assign"){
		
		$("#tstatus1").html("<div class='label label-warning' >Not yet Assign</div>");
	}else{
		$("#tstatus1").html("<div class='label label-important' >Deactived</div>");
	}
		
	
		
		$("#terminalmake").val("<s:property value='#respData.limitcodedetails.TERMINAL_MAKE' />");
		$('#terminalmake').trigger("liszt:updated");
		
		$("#modelnumber").val("<s:property value='#respData.limitcodedetails.MODEL_NO' />");
		$('#modelnumber').trigger("liszt:updated");
		
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



<body class="fixed-top">
<form name="form1" id="form1" method="post" >
	
      <div id="content" class="span10"> 
	    <div> 
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="#">Terminal Management </a>  <span class="divider"> &gt;&gt; </span> </li>
			
 			</ul>
		</div>  
	 	<table height="3">
				 <tr>
					<td colspan="3">
						<div class="messages" id="messages"> <s:actionmessage /></div>
						<div class="errors" id="errors"> <s:actionerror /></div>
					</td>
				</tr>
			 </table> 
				
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" >
					<i class="icon-edit"></i>Terminal Information
					
					
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
		
      				 
		<div class="box-content">
		
								
								<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
											
											
										   <tr class="even">
										    <td width="25%"><label for="Address Line 1"><strong>Terminal Id</strong></label></td>
												<td width="25%"><s:property value='#respData.limitcodedetails.TERMINAL_ID' />
												<input  type="hidden"   name="terminalid" id="terminalid" class="field" value="<s:property value='#respData.limitcodedetails.TERMINAL_ID' />" /></td>
										   		<td width="25%"><label for="IDType"><strong>Terminal Make<font color="red">*</font></strong></label></td>
												<td width="25%">
												<s:select cssClass="chosen-select"
														headerKey=""
														headerValue="Select"
														list="terminal"
														name="terminalmake"
														listKey="govId"
														listValue="govName"
														id="terminalmake"
														requiredLabel="true"
														theme="simple"
														data-placeholder="Choose Government ..."
														 /> 
												</td>
												
										   </tr>
										    

										   <tr class="odd">
										   <td width="25%"><label for="IDNumber"><strong>Model Number<font color="red">*</font></strong></label>	</td>
												<td width="25%">
												<s:select cssClass="chosen-select"
														headerKey=""
														headerValue="Select"
														list="model"
														name="modelnumber"
														listKey="govId"
														listValue="govName"
														id="modelnumber"
														requiredLabel="true"
														theme="simple"
														data-placeholder="Choose Government ..."
														 /> 
												</td>
										   <td ><label for="Address Line 1"><strong>Serial Number<font color="red">*</font></strong></label></td>
												<td ><input  type="text"   name="serialnumber" id="serialnumber" class="field" value="<s:property value='#respData.limitcodedetails.SERIAL_NO' />" /></td>
										</tr>
										<tr class="odd">		
										     	<td><label for="Address Line 1"><strong>Status</strong></label></td>
												<td><div id="tstatus1"></div>
												<input  type="hidden"   name="termilstatus" id="termilstatus" class="field" value="<s:property value='#respData.limitcodedetails.STATUS' />" />
												</td>
										   <td></td>
										    <td></td>
												
											</tr>
											
										
										  

									</table>
								
							
                
                        </div>
                   

						   </div>	
						   
						   
			  </div>
			  
			  
			  
			  
			  
			  
			 <div >
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">&nbsp;Home </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Submit</a>					 
		</div> 
			</div>
		  
		  </form>	
</body>
</html>
<!-- END PAGE -->