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

$(function() {  
	
	 
    
	
	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/accountopenServicecallAct.action'; 
		$("#form1").submit();
		
		 $(this).prop('disabled', true);
			$("#btn-submit").hide();
		return true;
	}); 
});

</script> 
</head> 
<body>
<form name="form1" id="form1" method="post"> 
		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					   <li> <a href="#">Account Opening Details</a>  </li> 
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
					<i class="icon-edit"></i>Account Opening Confirmation
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>  
				<div class="box-content">
					<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details"> 
						 <tr class="even">
							<td width="25%"><label for="From Date"><strong>BVN Number</strong></label></td>
							<td width="25%"><s:property value='accBean.IDNumber' />
							<input type="hidden" name="IDNumber"  id="IDNumber"   value="<s:property value='accBean.IDNumber' />"   /></td>
							<td width="25%"><strong>Reference No</strong></td>
							<td width="25%"><s:property value='accBean.referenceno' />
							<input type="hidden" name="referenceno"  id="referenceno"   value="<s:property value='accBean.referenceno' />"   />
							</td>
							
							</tr>
						<tr class="even">
							<td width="25%"><label for="From Date"><strong>First Name</strong></label></td>
							<td width="25%"><s:property value='accBean.fullname' />
							<input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   /></td>
							<td width="25%"><label for="From Date"><strong>Middle Name</strong></label></td>
							<td width="25%"><s:property value='accBean.middlename' />
							<input type="hidden" name="middlename"  id="middlename"   value="<s:property value='accBean.middlename' />"   /></td>
							
							</tr>
						<tr class="even">
						<td width="25%"><label for="From Date"><strong>Last Name</strong></label></td>
							<td width="25%"><s:property value='accBean.lastname' />
							<input type="hidden" name="lastname"  id="lastname"   value="<s:property value='accBean.lastname' />"   /></td>
						<td  ><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value="accBean.dob"/>
							<input type="hidden" maxlength="10"  class="dob" id="dob" name="accBean.dob" required=true  readonly="readonly" value="<s:property value="accBean.dob"/>"/>
							</td>
						
							
							  
						</tr>
						<tr class="even">
						
							
						   <td><label for="To Date"><strong>Mobile Number</strong></label> </td>
					       <td ><s:property value='accBean.telephone' />
					    
								<input type="hidden" value='<s:property value='accBean.telephone' />' class="field" maxlength="13" name="telephone" id="telephone"  />
 							
						     
						   </td> 
						     <td><strong>Gender</strong></td>
						      <td> <s:property value='accBean.gender' />
						    
						      <input type="hidden" name="gender"  id="gender"  class="field"  value="<s:property value='accBean.gender' />"   />
						      </td>
 						</tr>
 						<tr class="even">
						<td width="25%"><label for="From Date"><strong>Branch Code</strong></label></td>
							<td width="25%"><s:property value='accBean.branchdetails' />
							<input type="hidden" name="branchdetails"  id="branchdetails"   value="<s:property value='accBean.branchdetails' />"   /></td>
						<td  ><label for="To Date"><strong>RM Code</strong></label> </td>
							<td><s:property value="accBean.rmcode"/>
							<input type="hidden"  id="rmcode" name="rmcode" required=true   value="<s:property value="accBean.rmcode"/>"/>
							</td>
						
							
							  
						</tr>
						
						<tr class="even">
						
							
						 
						   <td width="25%"><label for="Service ID"><strong>products</strong></label></td>
							<td width="25%" >
							<s:property value='accBean.product' />
					    
								<input type="hidden" value='<s:property value='accBean.product' />' class="field"  name="product" id="product"  />
							</td>
						    
						     <td><strong>Paystack Debit Amount</strong></td>
					       <td ><s:property value='accBean.balance' />
					    
								<input type="hidden" value='<s:property value='accBean.balance' />' class="field"  name="balance" id="balance"  /></td>
						   
 						</tr>
 						<tr class="even">
						
						   <td width="25%"><label for="Service ID"><strong>Txn Date</strong></label></td>
						   <td width="25%" ><s:property value='accBean.stdate' />
					    
								<input type="hidden" value='<s:property value='accBean.stdate' />' class="field"  name="stdate" id="stdate"  /></td>
						    
						     <td><strong>Status</strong></td>
					       <td ><s:property value='accBean.customerstatus' />
					    
								<input type="hidden" value='<s:property value='accBean.customerstatus' />' class="field"  name="customerstatus" id="customerstatus"  /></td>
						   
 						</tr>
 						<tr class="even">
						
						   <td width="25%"><label for="Service ID"><strong>Account Number</strong></label></td>
						   <td width="25%" ><s:property value='accBean.idnumber' />
					    
								<input type="hidden" value='<s:property value='accBean.idnumber' />' class="field"  name="idnumber" id="idnumber"  /></td>
						    
						     <td></td>
					       <td ></td>
						   
 						</tr>
											
				 </table>
				 <input type="hidden" value='<s:property value='accBean.authStatus' />' class="field"  name="authStatus" id="authStatus"  />
				  <input type="hidden" name="apptype" id="apptype" value='<s:property value='accBean.apptype' />'   />
				
				
				
				 
				
				
				
				</div>  
				
	  </div>
	  </div> 
	  
	
									
			
	
		<div >
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">&nbsp;Home </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Confirm</a>					 
		</div> 
	</div> 	 
 </form>
 <script type="text/javascript">
$(function(){
	
	var actlen = $('#tbody_data > tr').length;
	
	if(actlen == 0){
		$('#add-new-act').hide();
	}else {
		$('#add-new-act').show();
	}
	 
});  
</script>
</body> 
</html>