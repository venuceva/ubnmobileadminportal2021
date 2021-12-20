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
			   termilstatus : { required : true}
			   
		   },  
		   messages : {
			   termilstatus : { 
			   		required : "Please Select Status."
				}
				
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
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/terminalmanagementstatusConf.action'; 
		$("#form1").submit();
		
		
		return true;
	}); 
});

function redirectfun()
{
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/terminalmanagement.action';
	$("#form1").submit();
	return true; 
	}
	
	
	
$(function() {
	
	
	$("#termilstatus").val("<s:property value='#respData.limitcodedetails.STATUS' />");
	$('#termilstatus').trigger("liszt:updated");
		 
		var config = {
	      '.chosen-select'           : {},
	      '.chosen-select-deselect'  : {allow_single_deselect:true},
	      '.chosen-select-no-single' : {disable_search_threshold:10},
	      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	      '.chosen-select-width'     : {width:"95%"}
	    }
		
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
		
								
								<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Terminal Id</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.TERMINAL_ID' />
								<input  type="hidden"   name="terminalid" id="terminalid" class="field" value="<s:property value='#respData.limitcodedetails.TERMINAL_ID' />" /></td>
								 <td width="20%"><label for="Limit Description"><strong>Terminal Make</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.TERMINAL_MAKE' /> 
								 <input  type="hidden"   name="terminalmake" id="terminalmake" class="field" value="<s:property value='#respData.limitcodedetails.TERMINAL_MAKE' />" /></td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Model Number</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.MODEL_NO' />
								<input  type="hidden"   name="modelnumber" id="modelnumber" class="field" value="<s:property value='#respData.limitcodedetails.MODEL_NO' />" /></td>
								 <td width="20%"><label for="Limit Description"><strong>Serial Number</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.SERIAL_NO' /> 
								 <input  type="hidden"   name="serialnumber" id="serialnumber" class="field" value="<s:property value='#respData.limitcodedetails.SERIAL_NO' />" /></td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Status</strong><font color="red">*</font></label></td>
								<td width="30%">
								<s:select cssClass="chosen-select" 
											         headerKey="" 
											         headerValue="Select"
											         list="#{'Active':'Active','Deactive':'Deactive','Retrive':'Retrive'}" 
											         name="termilstatus" 
											         id="termilstatus"
											         requiredLabel="true" 
											         theme="simple"
											         data-placeholder="Choose Account Type..." 
											           />
								</td>
								<td width="20%"></td>
								<td width="30%"></td>
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