<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString();%>
<%@taglib uri="/struts-tags" prefix="s"%> 

<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
input#abbreviation{text-transform:uppercase};

</style>
<s:set value="responseJSON" var="respData"/>
 <SCRIPT type="text/javascript">
 
 




$(document).ready(function(){   
 
 	$('#btn-submit').live('click',function() { 
 		
 		 $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/assignCampDetailsinsertfileAct.action";
		 $("#form1").submit();
	}); 
	
    $('#btn-Cancel').live('click',function() {  
		
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/campaignmgntact.action';		
		
		$("#form1").submit();		
	}); 
	
});






</script> 


</head> 
<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
	  <div id="content" class="span10"> 
			 
		    <div> 
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="campaignmgntact.action">Campaign Management</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Assign Campaign Management</a></li>
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
		 <div class="row-fluid sortable"> 
			<div class="box span12">  
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Assign Campaign Management
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
					  	    <td><label for="Template Name"><strong>Template Name</strong></label></td>
							<td>${templateName}
							<input name="templateName" id="templateName" type="hidden"  value="${templateName}"/></td>
					  </tr>
					  
					<tr > 
							<td > <label for="From Date"><strong>Date And Time</strong></label></strong></td>
								<td >${datetime}
								<input type="hidden"   id="datetime" name="datetime" value="${datetime}" /></td>
					</tr>
					<tr class="odd">
									<td>
									<strong><label for="file"><strong>Upload File Name</strong></label></strong></td>
									<td>${responseJSON.filename}
									<input name="filename" autocomplete="off" type="hidden" class="field" id="ptamt"  value="${responseJSON.filename}"  /></td>
									 </tr>
					  
					<tr >
									<td><label for="file"><strong>Total Records</strong></label></strong></td>
									<td>${responseJSON.records}
									<input name="records" autocomplete="off" type="hidden" class="field" id="records"  value="${responseJSON.records}"  /></td>
								</tr> 
				</table>
				</fieldset>
			</div>
		</div>
		</div>
 				 	
 
    	<div class="form-actions" >
	         <input type="button" class="btn btn-success"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>&nbsp;
	         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input> &nbsp;
       </div>  
 </div>  
 </form> 
 
  <script src="${pageContext.request.contextPath}/js/autosize.js"></script>
  <script type="text/javascript">
 
$(function(){
	
	autosize(document.querySelectorAll('textarea'));

	});	  
</script>
</body> 
</html>