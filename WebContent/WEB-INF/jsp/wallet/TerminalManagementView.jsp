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
function redirectfun()
{
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/terminalmanagement.action';
	$("#form1").submit();
	return true; 
	}
</script>


<s:set value="responseJSON" var="respData"/> 
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
								<td width="30%"><s:property value='#respData.limitcodedetails.TERMINAL_ID' /></td>
								 <td width="20%"><label for="Limit Description"><strong>Terminal Make</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.TERMINAL_MAKE' /> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Model Number</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.MODEL_NO' /></td>
								 <td width="20%"><label for="Limit Description"><strong>Serial Number</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.SERIAL_NO' /> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Status</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.STATUS' /></td>
								<td width="20%"><label for="Limit Code"><strong>Created Date</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.MAKER_DTTM' /></td>
							</tr>
							</table>
								
							
                
                        </div>
                   

						   </div>	
						   
						   
			  </div>
			  
			  
			  <div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" >
					<i class="icon-edit"></i>   Terminal Assigned Details 
					
					
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
		
      				 
		<div class="box-content">
		
								
							<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>User Id</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.USER_ID' /></td>
								 <td width="20%"><label for="Limit Description"><strong>Store Name</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.STORE_NAME' /> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Store Id</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.STORE_ID' /></td>
								 <td width="20%"><label for="Limit Description"><strong>Area</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.AREA' /> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>State</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.STATE' /></td>
								 <td width="20%"><label for="Limit Code"><strong>Local Government</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.LOC_GOV' /></td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Country</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.COUNTRY' /></td>
								<td width="20%"><label for="Limit Code"><strong>Assigned Date</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.ASS_MAKER_DTTM' /></td>
							</tr>
							</table>	
								
							
                
                        </div>
                   

						   </div>	
						   
						   
			  </div>
			  
			  
			  
			  <div  >
				<a class="btn btn-min btn-success" href="#" onClick="redirectfun()">Next</a>
				
			</div>
			</div>
		  
		  </form>	
</body>
</html>
<!-- END PAGE -->