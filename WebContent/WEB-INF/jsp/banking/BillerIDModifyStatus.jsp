<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%> 
<link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.min.css' /> 
<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}

input#billerType{text-transform:uppercase};
input#billerId{text-transform:uppercase}; 
</style>

<script type="text/javascript"> 
  
 $(function(){
	  
	var ses= '${payBillBean.id}';
	$('#id').val(ses);
	
	
	var userStatus = '${payBillBean.status}';
         
	var text = "";
	 

	if( userStatus == 'Active') {
		text = "<a href='#' class='label label-success'  >"+userStatus+"</a>";
		 
	} else if( userStatus == 'Inactive') {
		text = "<a href='#'  class='label label-warning'  >"+userStatus+"</a>";
		 
	}
	
	$('#spn-user-status').append(text);
 
	$('#btn-submit').on('click',function() { 
      	     $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/modifyBillerIDAck.action";
	         $("#form1").submit();
	         return true;
	 });
	
	$('#btn-Cancel').live('click',function() {  
		
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/mpesaAccManagement.action";
		$("#form1").submit();			
		
	}); 
    
	
});

 
</SCRIPT>  
 
 

 
</head> 
<body> 
	  <div id="content" class="span10">  
		    <div> 
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="mpesaAccManagement.action">Mpesa A/C Management</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Active / Deactive Modify Biller ID </a></li>
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
	<form name="form1" id="form1" method="post" autocomplete="off" style="margin: 0px 0px 50px">	 	
		<div class="row-fluid sortable"> 
			<div class="box span12">  
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Biller ID Details  
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						
					</div>
				</div>
							
				<div class="box-content">
					<fieldset>
 					<table width="98%"  border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable " > 
							 
							 <tr> 
								<td width="20%"><label for="Biller Type"><strong>Biller ID(Client Details) </strong></label></td>
								<td width="30%"><s:property value='payBillBean.billerId' /><input type="hidden" name="billerId"  id="billerId" required=true  readonly value="<s:property value='payBillBean.billerId' />"   />   </td>
							 </tr> 
							 
						     <tr> 
								<td><label for="Biller Type"><strong>Biller Type ID(Client Details) </strong></label></td>
								<td><s:property value='payBillBean.billerIdName' /><input type="hidden" name="billerIdType"  id="billerIdType" required=true readonly  value="<s:property value='payBillBean.billerIdType' />"   />   </td>
							</tr> 							 
							 
							<tr > 
								<td><label for="Biller Type Description"><strong>Biller ID Description </strong></label></td>
					            <td><s:property value='payBillBean.billerIdDescription' /><input type="hidden" name="billerIdDescription"  id="billerIdDescription"  required=true  value="<s:property value='payBillBean.billerIdDescription' />"   />   </td>
							</tr> 
							  
							<tr >  
								<td><label for="Credit Account"><strong>Credit Account </strong></label></td>
								<td><s:property value='payBillBean.bfubCreditAccount' /><input type="hidden" name="bfubCreditAccount"  id="bfubCreditAccount" value="<s:property value='payBillBean.bfubCreditAccount' />"   />  </td>							
							</tr> 
							
							<tr >  
								
								<td><label for="Debit Account"><strong>Debit Account </strong></label></td>
								<td>  <s:property value='payBillBean.bfubDebitAccount' /><input type="hidden" name="bfubDebitAccount"  id="bfubDebitAccount" value="<s:property value='payBillBean.bfubDebitAccount' />"   /> 
								</td>							
							
							</tr>
							
						    <tr >  
								
								<td><label for=" Status"><strong>Status</strong></label></td>
								<td> 
								
								 <span id="spn-user-status"></span>
								 
								 <input type="hidden" name="status"  id="status" value="<s:property value='payBillBean.status' />"   /> 
								</td>							
							
							</tr>
							
							 
					</table>
				</fieldset>  
			</div>
			
		    <input type="hidden" name='id' id='id' />
		 
 		</div>
		</div>
	</form>
 
   	<div class="form-actions" >
         <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100" ></input>&nbsp;
         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input>&nbsp;
         <span id ="error_dlno" class="errors"></span>
       </div>  
               						 
	</div>
  
</body>
</html> 