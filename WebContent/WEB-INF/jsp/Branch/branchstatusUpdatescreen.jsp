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
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/branchStatus.action';
	$("#form1").submit();
	return true; 
	}
	
function redirectfunsubmit()
{
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/branchactdctconfirm.action';
	$("#form1").submit();
	return true; 
	}
	
$(document).ready(function() {
	
	if($("#status").val()=="Active"){
		$("#statuss").html("<div class='label label-success' >Active</div>");
		$("#updatedstatuss").html("<div class='label label-important' >Deactive</div>");
		
	}else{
		$("#statuss").html("<div class='label label-important' >Deactive</div>");
		$("#updatedstatuss").html("<div class='label label-success' >Active</div>");
	}
	

});
	
</script>


<s:set value="responseJSON" var="respData"/> 
<body class="fixed-top">
<form name="form1" id="form1" method="post" >
	
      <div id="content" class="span10"> 
	    <div> 
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			   <li> <a href="#">Branch Status </a>  <span class="divider"> &gt;&gt; </span> </li>
			 <li> <a href="#">Status Change </a>  <span class="divider"> </span> </li>
			
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
					<i class="icon-edit"></i>Modify Branch Status 
					
					
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
		
      				 
		<div class="box-content">
		

								<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Branch Id</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.FCUBS_BRANCH_CODE' /></td>
								<td width="20%"><label for="Limit Description"><strong>Branch Name</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.BRANCH_NAME' /> </td>
							</tr>
							<tr class="even">
						<td width="20%"><label for="Limit Description"><strong>State</strong></label></td>
						<td width="30%"><s:property value='#respData.limitcodedetails.STATE' /> </td>
						<td width="20%"><label for="Limit Description"><strong>Local Government</strong></label></td>
						<td width="30%"><s:property value='#respData.limitcodedetails.LOCAL_GOVERNMENT' /> </td>
						</tr>
							<tr class="even">
							<td width="20%"><label for="Limit Description"><strong>Zone</strong></label></td>
						<td width="30%"><s:property value='#respData.limitcodedetails.ZONE' /> </td>
						<td width="20%"><label for="Limit Description"><strong>Branch Address</strong></label></td>
						<td width="30%"><s:property value='#respData.limitcodedetails.BRANCH_ADDRESS' /> </td>

							</tr>
							<tr class="even">
						<td width="20%"><label for="Limit Description"><strong>Opening Time</strong></label></td>
						<td width="30%"><s:property value='#respData.limitcodedetails.OPENING_TIME' /> </td>
						<td width="20%"><label for="Limit Description"><strong>Closing Time</strong></label></td>
						<td width="30%"><s:property value='#respData.limitcodedetails.CLOSING_TIME' /> </td>


							</tr>
							<tr class="even">
						<td width="20%"><label for="Limit Description"><strong>Branch Current Status</strong></label></td>
						<td width="30%"><div id="statuss"></div> </td>
						<td width="20%"><label for="Limit Description"><strong>Branch Update Status</strong></label></td>
						<td width="30%"><div id="updatedstatuss"></div>  </td>


							</tr>
							
							</table>								
                
                        </div>
	
                        </div>
                   

						   </div>
						   <input type="hidden" id="branchcode" name="branchcode" value="<s:property value='#respData.limitcodedetails.FCUBS_BRANCH_CODE' />"/> 
						   <input type="hidden" id="status" name="status" value="<s:property value='#respData.limitcodedetails.BRANCH_STATUS' />"/>
						    <div align="left" >
				<a class="btn btn-min btn-success" href="#" onClick="redirectfun()">Back</a>&nbsp&nbsp
				<a class="btn btn-min btn-success" href="#" onClick="redirectfunsubmit()">Confirm</a>
				
			</div>	
						<!--   <div align="center" >
				<a class="btn btn-min btn-success" href="#" onClick="redirectfun()">Next</a>
				
			 </div>  -->
						   
			  </div>

			  
			  
			
		  
		  </form>	
</body>
</html>
<!-- END PAGE -->