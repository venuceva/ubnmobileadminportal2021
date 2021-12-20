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
function viewProduct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/createProductAct.action'; 
	$("#form1").submit();
	return true;
}


var userictadminrules = {
 		
 		rules : {
 			
 			Narration : { required : true } ,
 		},		
 		messages : {
 			
 		
 			Narration : { 
 							required : "Please Enter Message.",
 						}
 			
 		} 
 	};

			
			$(function () {
				
				if("${responseJSON.decision}"=="Reject"){
					$("#decision").val("Reject");
					jQuery("#select2").attr('checked', true);
				}else{
					$("#decision").val("Approval");
					jQuery("#select1").attr('checked', true);
				}
			
				  $("#form1").validate(userictadminrules); 
				
		        $('#btn-submit').live('click', function () { 
		    	  
		    				var url="${pageContext.request.contextPath}/<%=appName %>/AuthFeeAck.action"; 
		    				$("#form1")[0].action=url;
		    				$("#form1").submit();
		    	    		
		        }); 
		    });


function decisionradio(v){

				
				$("#decision").val(v);

			}

	
</script>


<s:set value="responseJSON" var="respData"/> 
<body class="fixed-top">
<form name="form1" id="form1" method="post" >
	
      <div id="content" class="span10"> 
	    <div> 
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="#"> Fee Code Details </a>  <span class="divider"> &gt;&gt; </span> </li>
			
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
					<i class="icon-edit"></i> Fee Details 
					
					
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
		
      				 
		<div class="box-content">
		
								
								<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="Product Code"><strong>Product Code</strong></label></td>
								<td width="30%"><s:property value='#respData.Feecodedetails.product' /></td>
								 <td width="20%"><label for="Application"><strong>Application</strong></label></td>
								 <td width="30%"><s:property value='#respData.Feecodedetails.application' /> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Fee Code"><strong>Fee Code</strong></label></td>
								<td width="30%"><s:property value='#respData.Feecodedetails.feeCode' /></td>
								 <td width="20%"><label for="Fee Description"><strong>Fee Description</strong></label></td>
								 <td width="30%"><s:property value='#respData.Feecodedetails.feeDesc' /> </td>
							</tr>
							</table>
								
							
                
                        </div>
                     <input type="hidden" value="<s:property value='#respData.Feecodedetails.feeCode' />" id="feeCode" name="feeCode" /> 
                     <input type="hidden" value="<s:property value='#respData.Feecodedetails.product' />" id="product" name="product" />
                    <input type="hidden" id="refno" name="refno"  value="<s:property value='#respData.Feecodedetails.refno' />" />

						   </div>	
						   
						   
			  </div>
			  
			  	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" >
					<i class="icon-edit"></i>   Fee Transaction Details 
					
					
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
								<th>Channel - Transaction Type</th>
								<th>Frequency</th>
								<th>Fee/Percentile</th>
								<th>Value</th>
								<th>Criteria</th>
								<th>From Value</th>
								<th>To Value</th>
								<th>Agent</th>
										<th>Ceva</th>
										<th>Bank</th>
										<th>Super Agent</th>
										<th>VAT</th>
							</tr>
						</thead> 
						 <tbody id="binTBody">
							  <s:iterator value="responseJSON['Feecodedetails2']" var="userGroups" status="userStatus"> 
								<s:if test="#userStatus.even == true" > 
									<tr class="even" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
								 </s:if>
								 <s:elseif test="#userStatus.odd == true">
      								<tr class="odd" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
   								 </s:elseif> 
									<td><s:property value="#userStatus.index+1" /></td>
									<!-- Iterating TD'S -->
									  <s:iterator value="#userGroups" status="status" > 
										<s:if test="#status.index == 6" >  
											<td> <s:property  value="value" /></td> 											
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
										 <s:elseif test="#status.index == 13" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 14" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 15" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 17" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 18" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
									
									</s:iterator>  
									
 								  
							</s:iterator>  
						</tbody>  
					</table>
								
							
                
                        </div>
                   

						   </div>
						   
						   
						   <div class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"
									class="table table-striped table-bordered bootstrap-datatable ">
									
									<tr class="odd" >
									<td colspan="4" ><strong>Decision<font color="red">*</font></strong>&nbsp;&nbsp;
									<input type='radio' onclick='decisionradio(this.value)' id='select1' name='select' value='Approval' checked/><strong>Approval</strong>&nbsp;&nbsp;
									<input type='radio' onclick='decisionradio(this.value)' id='select2' name='select' value='Reject'/><strong>Reject</strong>&nbsp;&nbsp;
									</td>
									
								</tr>
								
								<tr class="even" >
									<td colspan="4" ><div><strong>Message<font color="red">*</font></strong></label></div>
									<textarea rows="10" cols="100" name="Narration" class="field" id="Narration" style="width:500px"  required=true >${responseJSON.Narration}</textarea></td>
									
								</tr>
									
									
								</table>
								<input type="hidden" id="decision" name="decision" value="Approval" />
								
							</fieldset>
						</div>	   	
						   
						   
			  </div>
			  
			 <div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Back" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Confirm" width="100"  ></input> 
			</div>
			</div>
		  
		  </form>	
</body>
</html>
<!-- END PAGE -->