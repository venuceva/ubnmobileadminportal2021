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
		    	  
		    				var url="${pageContext.request.contextPath}/<%=appName %>/AuthAccountOpenAck.action"; 
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
			  <li> <a href="#">Account Open Details  </a>  <span class="divider"> &gt;&gt; </span> </li>
			
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
					<i class="icon-edit"></i>Account Open Details 
					
					
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
		
      				 
		<div class="box-content">
		
								
								<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
								<td width="20%"><label for="Limit Description"><strong>BVN Number</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.BVN' /> 
								 <input type="hidden" id="bvn" name="bvn" value='<s:property value='#respData.limitcodedetails.BVN' />' /></td>
								 <td width="20%"><label for="Limit Code"><strong>Reference No</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.TXNREFNO' />
								<input type="hidden" id="referenceno" name="referenceno" value='<s:property value='#respData.limitcodedetails.TXNREFNO' />' />
								</td>
								
								
							</tr>
							<tr class="even">
							<td width="20%"><label for="Limit Code"><strong>First Name</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.F_NAME' /></td>
							 <td width="20%"><label for="Limit Description"><strong>Middle Name</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.M_NAME' /> </td>
								
								 
							</tr>
							<tr class="even">
							<td width="20%"><label for="Limit Code"><strong>Last Name</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.L_NAME' /></td>
							 <td width="20%"><label for="Limit Description"><strong>Gender</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.GENDER' /> </td>
								
								 
							</tr>
							<tr class="even">
							<td width="20%"><label for="Limit Code"><strong>Date Of Birth</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.DATEOFBIRTH' /></td>
							 <td width="20%"><label for="Limit Description"><strong>Product</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.PRODUCTCODE' /> </td>
								
								 
							</tr>
							<tr class="even">
							<td width="20%"><label for="Limit Code"><strong>Branch</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.BRANCH' /></td>
							 <td width="20%"><label for="Limit Description"><strong>RM Code</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.RMCODE' /> </td>
								
								 
							</tr>
							<tr class="even">
							<td width="20%"><label for="Limit Code"><strong>Paystack debit amount</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.AMOUNT' /></td>
							 <td width="20%"><label for="Limit Description"><strong>Mobile Number</strong></label></td>
								 <td width="30%"><s:property value='#respData.limitcodedetails.MOBILENO' /> </td>
								
								 
							</tr>
							<tr class="even">
							
								<td width="20%"><label for="Limit Code"><strong>Status</strong></label></td>
								<td width="30%"><s:property value='#respData.limitcodedetails.CUST_STATUS' />
								<input type="hidden" id="custstatus" name="custstatus" value='<s:property value='#respData.limitcodedetails.CUST_STATUS' />' />
								</td>
								 <td></td>
								 <td></td>
							</tr>
							</table>
								
							<input type="hidden" id="refno" name="refno" value='<s:property value='#respData.limitcodedetails.AUTH_REFERENCENO' />' />
                
                        </div>
                 

						   </div>	
						   
						   
			  </div>
			  
			  	<div class="row-fluid sortable">
		
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