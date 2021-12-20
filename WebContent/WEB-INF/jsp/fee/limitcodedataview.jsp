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
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/productlimitsettings.action';
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
			  <li> <a href="#">Limit Code Details </a>  <span class="divider"> &gt;&gt; </span> </li>
			
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
					<i class="icon-edit"></i>Limit Code Details 
					
					
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
		
      				 
		<div class="box-content">
		
								
								<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Product Code</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.product' /></td>
								 <td width="20%"><label for="Limit Description"><strong>Application</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.application' /> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Limit Code</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.limitCode' /></td>
								 <td width="20%"><label for="Limit Description"><strong>Limit Description</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.limitDesc' /> </td>
							</tr>
							</table>
								
							
                
                        </div>
                   

						   </div>	
						   
						   
			  </div>
			  
			  	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" >
					<i class="icon-edit"></i>   Limit Transaction Details 
					
					
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
		
      				 
		<div class="box-content">
		
								
								<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  id="DataTables_Table_0" >
						<thead>
							<tr>
								<th>S No</th>
								<th>Channel - Transaction </th>
								<th>Frequency</th>
								<th>Maximum Count</th>
								<th>Minimum Amount</th>
								<th>Maximum Amount</th>
								
							</tr>
						</thead> 
						 <tbody id="binTBody">
							  <s:iterator value="responseJSON['limitcodedetails2']" var="userGroups" status="userStatus"> 
								<s:if test="#userStatus.even == true" > 
									<tr class="even" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
								 </s:if>
								 <s:elseif test="#userStatus.odd == true">
      								<tr class="odd" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
   								 </s:elseif> 
									<td><s:property value="#userStatus.index+1" /></td>
									<!-- Iterating TD'S -->
									  <s:iterator value="#userGroups" status="status" > 
										<s:if test="#status.index == 5" >  
											<td> <s:property  value="value" /></td> 											
										</s:if>
										 <s:elseif test="#status.index == 6" >
											 <td ><s:property  value="value"  /></td>
										 </s:elseif> 
										 
 										 <s:elseif test="#status.index == 7" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 <s:elseif test="#status.index == 8" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 9" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 
									</s:iterator>  
									
 								  
							</s:iterator>  
						</tbody>  
					</table>
								
							
                
                        </div>
                   

						   </div>	
						   
						   
			  </div>
			  
			  <div align="center" >
				<a class="btn btn-min btn-success" href="#" onClick="redirectfun()">Next</a>
				
			</div>
			</div>
		  
		  </form>	
</body>
</html>
<!-- END PAGE -->