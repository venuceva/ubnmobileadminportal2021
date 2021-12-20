<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
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
			//alert(data.message);
			if(data.message=="SUCCESS"){
		
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/tercreateConf.action'; 
				$("#form1").submit();
				return true;
			}else{ 
		    	  
				$('#errors').text(data.message);

			 }
		
		});
	}); 
});

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

<style type="">
.label {
    padding: 1px 4px 2px;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
    width: 40px;
}
</style>

</head> 
<body>
<form name="form1" id="form1" method="post"> 
		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					   <li> <a href="#">Wallet Agent Terminal Create</a>  </li> 
 					</ul>
				</div>  

				<table>
					<tr>
						<td colspan="3">
							<div class="messages" id="messages"><s:actionmessage /></div>
							<div class="errors" id="errors"><s:actionerror /></div>
						</td>
					</tr>
				</table>
			
  <div class="row-fluid sortable"> 
	<div class="box span12"> 
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Terminal Information
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>  
				<div class="box-content">
					
				
				<fieldset>
										<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
											
											
										   <tr class="even">
										     <td width="25%"><label for="Address Line 1"><strong>Terminal Id</strong></label></td>
												<td width="25%">${responseJSON.terminalid}
												<input  type="hidden"   name="terminalid" id="terminalid" class="field" value="${responseJSON.terminalid}" /></td>
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
												<td ><input  type="text"   name="serialnumber" id="serialnumber" class="field" value="" /></td>
												
										   
										   
												
											</tr>
											
										
										  

									</table>
								</fieldset>
				 
				
				</div>  
				
	  </div>
	  </div> 
	  
	
										<input type="hidden" name="langugae"  id="langugae"   value="English"  />
			
	
		<div >
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">&nbsp;Home </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Submit</a>					 
		</div> 
	</div> 	 
 </form>

</body> 
</html>