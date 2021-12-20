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
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modregcustinfoBack.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/wallettopupactCnfAct.action'; 
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
					  <li> <a href="#">TopUp Wallet Account</a>  </li> 
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
					<i class="icon-edit"></i>Customer TopUp Confirmation
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>  
				<div class="box-content">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
							<td width="25%"><label for="From Date"><strong>Wallet Customer Id</strong></label></td>
							<td width="30%"><s:property value='accBean.customercode' /><input type="hidden" name="customercode"  id="customercode"   value="<s:property value='accBean.customercode' />"   /></td>
							<td width="25%"><label for="From Date"><strong>Wallet Account Number</strong></label></td>
							<td width="30%"><s:property value='accBean.newAccountData' /><input type="hidden" name="newAccountData"  id="newAccountData"   value="<s:property value='accBean.newAccountData' />"   /></td>
								</tr>  
						<tr class="even">
						<td width="20%"><label for="To Date"><strong>Customer Name</strong></label> </td>
							<td width="30%" ><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='fullname' />"   />  </td>
					
							<td ><label for="To Date"><strong>Mobile Number</strong></label> </td>
							<td><s:property value='telephone' />
								<input type="hidden" value="<s:property value='accBean.telco' />" style="width: 95px;" readonly name="telco" id="telco" />&nbsp;
								<input type="hidden" value="234" style="width:25px;" readonly name="isocode" id="isocode"/>&nbsp;
								<input type="hidden" value='<s:property value='telephone' />' name="telephone" id="telephone" readonly style="width:95px;" />
 							</td>
							</tr>
						<tr class="even">
							<td><label for="From Date"><strong>Email ID</strong></label></td>
							<td><s:property value='accBean.email' /> <input type="hidden" name="email"  id="email"   value="<s:property value='email' />"   />  </td>
							<td  ><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value='accBean.idnumber' /> <input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='idnumber' />"   />  </td>
						
							
						</tr>
						<tr class="even">
							<td ><label for="balance"><strong>Balance</strong></label></td>
							<td ><s:property value='accBean.balance' />
							<input type="hidden" name="balance"  id="balance"   value="<s:property value='accBean.balance' />"  style="width:130px;" />
							</td>
							 <td><label for="amount"><strong>TopUp Amount</strong></label></td>
					       <td><s:property value='accBean.topup' />
					       <input type="hidden" name="topup"  id="topup"   value="<s:property value='accBean.topup' />"  style="width:130px;" />
					       </td>  
						</tr>					
				 </table>
				</fieldset> 
				</div>  
				
	  </div>
	  </div> 
	  
	
			
	
		<div >
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">&nbsp;Back </a>&nbsp;
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