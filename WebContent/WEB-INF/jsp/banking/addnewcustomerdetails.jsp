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

var subrules = {
		   rules : {
			   staffid : { required : true}
			   
		   },  
		   messages : {
			   staffid : { 
			   		required : "Please Enter Staff Id."
				}
				
		   } 
		 };
		 

$(document).ready(function(){
	$('#btn-submit').click(function(){ 
		$("#form1").validate(subrules);
		 $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/addnewcustomerconfirm.action'; 
			$('#form1').submit();
			 return true; 
	});
	
	$('#btn-back').click(function(){ 
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/addnewcustomer.action'; 
			$('#form1').submit();
			
	});
	
});
	 
</script>

</head> 

<body>

		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Customer Registration</a>  </li> 
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
<form name="form1" id="form1" method="post"> 
		<div class="row-fluid sortable"> 
			<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Customer Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
					
				<div class="box-content">
					<fieldset> 
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details"> 
						<tr class="even" >
							<td width="25%"><label for="From Date"><strong>Account Number</strong></label></td>
							<td width="25%"><s:property value='accBean.accountno' /><input type="hidden" name="accountno"  id="accountno"   value="<s:property value='accBean.accountno' />"   /></td>
							<td width="25%"><strong>Customer Id</strong></td>
							<td width="25%"><s:property value='accBean.customercode' /><input type="hidden" name="customercode"  id="customercode"   value="<s:property value='accBean.customercode' />"   /></td>
							</tr>   
						<tr class="even">
							<td width="25%"><label for="From Date"><strong>First Name</strong></label></td>
							<td width="25%"><s:property value='accBean.fullname' /><input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   /></td>
							
							<td  width="25%"><label for="From Date"><strong>Branch Code</strong></label></td>
							<td  width="25%">
							<s:property value='accBean.branchcode' /> 
							<input type="hidden" name="branchcode"  id="branchcode"   value="<s:property value='accBean.branchcode' />"   />  </td>
							</tr>
						<tr class="even">
						<td  ><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value="accBean.dob"/>
							<input type="hidden"   class="dob" id="dob" name="accBean.dob" required=true  readonly="readonly" value="<s:property value="accBean.dob"/>"/>
							</td>
						
							<td ><label for="From Date"><strong>Email ID</strong></label></td>
							<td ><s:property value='accBean.email' />
							<input type="hidden" name="email"  id="email"   value="<s:property value='accBean.email' />"   />  </td>
							  
						</tr>
						<tr class="even">
						
							
						   <td><label for="To Date"><strong>Mobile Number</strong></label> </td>
					       <td ><s:property value='accBean.telephone' />
					    
								<input type="hidden" value='<s:property value='accBean.telephone' />' class="field"  name="telephone" id="telephone"  />
 							
						     
						   </td> 
						     <td><strong>Gender</strong></td>
						      <td> <s:property value='accBean.gender' />
						    
						      <input type="hidden" name="gender"  id="gender"  class="field"  value="<s:property value='accBean.gender' />"   />
						      </td>
 						</tr>
 						
 						<tr class="even">
						
							
						   <td><label for="To Date"><strong>Account Type</strong></label> </td>
					       <td ><s:property value='accBean.accDetails' />
					    
								<input type="hidden" value='<s:property value='accBean.accDetails' />' class="field" name="accDetails" id="accDetails"  />
 							
						     
						   </td> 
						     <td><strong>Product Name</strong></td>
						      <td> <s:property value='accBean.product' />
						    
						      <input type="hidden" name="product"  id="product"  class="field"  value="<s:property value='accBean.product' />"   />
						      </td>
 						</tr>
 						
 						<tr class="even">
						
							
						   <td><label for="To Date"><strong>staff ID</strong></label> </td>
					       <td ><input type="text"  class="field"  name="staffid" id="staffid"  />
 							
						     
						   </td> 
						     <td></td>
						      <td></td>
 						</tr>
 						
 						
 												
				 </table>
				 
				 <input type="hidden" value='<s:property value='accBean.apptype' />' name="apptype" id="apptype"  />
				  
				 
				 
				 
				 
				</fieldset> 
				</div>
				</div> 
				
				 	
				
		</div> 	
</form>	

<div id="dialog" title="Confirmation Required" style="display:none">
		   Proceed ? <div id="dia1"></div><font color="red"><div id="dia2"></div></font>
		</div>
		<div > 
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">Back </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Submit</a>	
			<span id ="error_dlno" class="errors"></span>	 
		</div> 
		
	</div> 
 <script type="text/javascript">
$(function(){
	
	
	
	$('#accountNumber').live('keypress',function(){
		//console.log($(this).length);
		if($(this).length == 0){
			$('#billerMsg').text('');
		}
	});
	
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
</body> 
</html>