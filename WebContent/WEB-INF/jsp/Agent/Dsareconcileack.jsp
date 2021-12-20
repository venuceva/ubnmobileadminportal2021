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
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
	$("#form1").submit();
	return true; 
	}
	

$(function () { 
	
	

	$('#btn-submit').live('click', function () { 
		

			 
				var url="${pageContext.request.contextPath}/<%=appName %>/dsareconcile.action"; 
				$("#form1")[0].action=url;
				$("#form1").submit(); 
		
	}); 
});


	
</script>


<s:set value="responseJSON" var="respData"/> 
<body class="fixed-top">
<form name="form1" id="form1" method="post" >
	
      <div id="content" class="span10"> 
	    <div> 
			<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="dsareconcile.action">Teller Reconcile </a> </li>
				</ul>
		</div>  
	 	<table height="3">
				 <tr>
					<td colspan="3">
						<div class="messages" id="messages"> <s:actionmessage /></div>
						<div class="errors" id="errors"> <s:actionerror /></div>
						<div id="errormsg" class="errores"></div>
			 			<div  class="screenexit"></div>
						
					</td>
				</tr>
			 </table> 
		<div style="color:green"><b><s:property value='#respData.message.status_message' />	</b></div>			
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" >
					<i class="icon-edit"></i>User Details 
					
					
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
		
      			 
		<div class="box-content">
		
								
								<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
								<td width="20%"><label for="User Id"><strong>User Id</strong></label></td>
								<td width="30%"><s:property value='#respData.dsaReconcileDetails.USERID' />
								<input name="userid"  type="hidden"  id="userid"  value="<s:property value='#respData.dsaReconcileDetails.USERID' />"    />
								</td>
								 <td width="20%"><label for="User Name"><strong>User Name</strong></label></td>
								 <td width="30%"><s:property value='#respData.dsaReconcileDetails.DISPLAYNAME' /> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Branch Details"><strong>Branch Details</strong></label></td>
								<td width="30%"><s:property value='#respData.dsaReconcileDetails.BRANCH_DETAILS' /></td>
								 <td width="20%"><label for="Mobile Number"><strong>Mobile Number</strong></label></td>
								 <td width="30%"><s:property value='#respData.dsaReconcileDetails.MOBILE_NO' /> </td>
							</tr>
							</table>
									<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
								
								<td width="50%" colspan="2"><label for="Credit" align="center"><strong>Selected Transaction</strong></label></td>
								<td width="50%" colspan="2"></td>
								</tr>
								<tr class="even">
								
								<td width="20%"><label for="Credit"><strong>Total Cash Withdrawal ( &#8358; ) </strong></label></td>
								<td width="30%"><div id="selcr"><s:property value='#respData.vselcr' /></div></td>
								<td width="50%" colspan="2"></td>
								</tr>
							<tr class="even">
								
								 
								  <td width="20%"><label for="Debit"><strong>Total Cash Deposit ( &#8358; ) </strong></label></td>
								 <td width="30%"><div id="seldr"><s:property value='#respData.vseldr' /></div> </td>
								 <td width="50%" colspan="2"></td>
							</tr>
							<tr class="even">
								
								<td width="20%"><label for="Branch Details"><strong>Balance ( &#8358; ) </strong></label></td>
								<td width="30%"><div id="selbl"><s:property value='#respData.vselbl' /></div></td>
								<td width="50%" colspan="2"></td>
							</tr>
							</table>	
							<input name="vselcr"  type="hidden"  id="vselcr"  value="<s:property value='#respData.vselcr' />"    />
							<input name="vseldr"  type="hidden"  id="vseldr"  value="<s:property value='#respData.vseldr' />"    />
							<input name="vselbl"  type="hidden"  id="vselbl"  value="<s:property value='#respData.vselbl' />"    /> 
                
                        </div>
                   

						   </div>	
						   
						   
			  </div>
			  
			  	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" >
					<i class="icon-edit"></i>   User Transaction Acknowledgment 
					
					
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
		
      				 
		<div class="box-content">
		
								
								<table width='100%' class="table table-striped table-bordered bootstrap-datatable "  id="DataTables_Table_0" >
						<thead>
							<tr>
							
								<th>Payment Reference No</th>
								<th>Account No </th>
								<th>Amount</th>
								<th>Credit / Debit </th>
								<th>Suspense Account No</th>
								<th>Service Name</th>
								<th>Channel</th>
								<th>Transaction Date</th>
								
							</tr>
						</thead> 
						 <tbody id="binTBody">
							  <s:iterator value="responseJSON['dsaReconcileDetails2']" var="userGroups" status="userStatus"> 
								<s:if test="#userStatus.even == true" > 
									<tr class="even" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
								 </s:if>
								 <s:elseif test="#userStatus.odd == true">
      								<tr class="odd" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
   								 </s:elseif> 
									<%-- <td><s:property value="#userStatus.index+1" /></td> --%>
									<!-- Iterating TD'S -->
									  <s:iterator value="#userGroups" status="status" > 
									  
									  <s:if test="#status.index == 5" >  
											<td><s:property  value="value" /></td> 											
										</s:if>
										<s:if test="#status.index == 6" >  
											<td><s:property  value="value" /></td> 											
										</s:if>
										 <s:elseif test="#status.index == 7" >
											 <td ><s:property  value="value"  /></td>
										 </s:elseif> 
										 
 										 <s:elseif test="#status.index == 8" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 <s:elseif test="#status.index == 9" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 10" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 11" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										   <s:elseif test="#status.index == 12" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 
									</s:iterator>  
									
 								  
							</s:iterator>  
						</tbody>  
					</table>
								
							
                
                        </div>
                   

						   </div>	
					   
						   
			  </div>
			  
			<input type="hidden" id="rno" name="rno"  value="<s:property value='#respData.rno' />" />
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectfun();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Next" width="100"  ></input> 
			</div>  
			</div>
		  
		  </form>	
</body>
</html>
<!-- END PAGE -->