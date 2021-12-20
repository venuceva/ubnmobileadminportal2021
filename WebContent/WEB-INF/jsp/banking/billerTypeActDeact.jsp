
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
 

$(document).ready(function(){ 
	
	$("#dialog").dialog({
	      autoOpen: false,
	      modal: true
     });
	 
	 
	$('#btn-submit').on('click',function() { 
		 $("#dialog").dialog({
		      buttons : {
		        "Confirm" : function() { 
		          $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/actDeactBillerDetailsAck.action";
				  $("#form1").submit();
		        },
		        "Cancel" : function() {
		           $(this).dialog("close");
		        }
		      }
		    });

		    $("#dialog").dialog("open"); 
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
				   <li><a href="#">Activate / Deactivate Biller Type</a></li>
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
					 <i class="icon-edit"></i>Biller Type Details  
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						
					</div>
				</div>
							
				<div class="box-content">
					<fieldset>
 					<table width="98%"  border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable " > 
						
							<tr > 
								<td width="20%"><label for="Select Institute"><strong>Select Core Banking</strong></label></td>
								<td width="30%" colspan=3> 
									<s:property value='payBillBean.institute' />  
	 							</td>								 
							</tr> 
							<tr > 
								<td><label for="Select Operator"><strong>Select Mobile Network Operator</strong></label></td>
								<td> <s:property value='payBillBean.operator' />  <input type="hidden" name="operator"  id="operator" required=true  value="<s:property value='payBillBean.billerType' />"   />  
									 
	 							</td> 								 
							</tr> 
							 <tr> 
								<td ><label for="Biller Type"><strong>Biller Type(Client Details)</strong></label></td>
								<td ><s:property value='payBillBean.billerType' />  <input type="hidden" name="billerType"  id="billerType" required=true  value="<s:property value='payBillBean.billerType' />"   />  
								<input type="hidden" name="id"  id="id" required=true  value="<s:property value='id' />"   />    </td>		 
							</tr> 
							<tr > 
								<td><label for="Biller Type Description"><strong>Biller Type Description</strong></label></td>
								<td><textarea name="billerTypeDescription"  id="billerTypeDescription"   required=true style="height: 69px; width: 453px;"  readonly><s:property value='payBillBean.billerTypeDescription' /></textarea> </td> 
								 
							</tr> 
							<tr> 
								<td><label for="Regular Expression"><strong>Regular Expression</strong></label></td>
								<td><s:property value='payBillBean.regex' />  <input type="hidden" name="regex"  id="regex" value="<s:property value='payBillBean.regex' />" readonly  />   </td>  
							</tr> 
							<tr >  
								<td><label for="Has Fixed Amount ?"><strong>Has Fixed Amount ?</strong></label></td>
								<td><input type="radio" id="fixed-amt-y" name="fixedamountcheck" value="Y" checked> Yes &nbsp;
									 <input type="radio" id="fixed-amt-n" name="fixedamountcheck" value="N"> No
									 <input type="hidden" name="fixedamountcheckval"  id="fixedamountcheckval" value="<s:property value='payBillBean.fixedamountcheckval' />"    />  
								</td>							
							</tr>  
							<tr id="fixed-amt-tr"> 
								<td><label for="Fixed Amount(ksh)"><strong>Fixed Amount(ksh)</strong></label></td>
								<td> <s:property value='payBillBean.amount' />  <input type="hidden" name="amount"  id="amount" value="<s:property value='payBillBean.amount' />" required=true   />   </td>  
							</tr> 
							<tr id="biller-len-tr"> 
								<td><label for="Biller ID Length"><strong>Biller ID Length </strong></label></td>
								<td> <s:property value='payBillBean.billerIdLen' />  <input type="hidden" name="billerIdLen"  id="billerIdLen" value="<s:property value='payBillBean.billerIdLen' />" required=true   />   </td>  
							</tr>  
							<tr > 
								<td><label for="System Integration Mode"><strong>System Integration Mode</strong></label></td>
								<td> <s:property value='payBillBean.systemmodes' /> <input type="hidden" name="systemmodes"  id="systemmodes" value="<s:property value='payBillBean.systemmodes' />"   /> 
	 							</td>  
							</tr>  
							<tr >  
								<td><label for="Has Biller Id ?"><strong>Has Biller Id ?</strong></label></td>
								<td><input type="radio" id="billerid-y" name="billeridcheck" value="Y" > Yes &nbsp;
									 <input type="radio" id="billerid-n" name="billeridcheck" value="N" checked> No
									 <input type="hidden" name="billeridcheckval"  id="billeridcheckval" value="<s:property value='payBillBean.billeridcheckval' />"    />  
								</td>							
							</tr>
							<tr class="acctbillertype">  
								<td><label for="Credit Account"><strong>Credit Account</strong></label></td>
								<td><s:property value='payBillBean.bfubCreditAccount' />  <input type="hidden" name="bfubCreditAccount"  id="bfubCreditAccount" value="<s:property value='payBillBean.bfubCreditAccount' />"   />  </td>							
							</tr> 
							<tr class="acctbillertype">  
								<td><label for="Debit Account"><strong>Debit Account</strong></label></td>
								<td><s:property value='payBillBean.bfubDebitAccount' />  <input type="hidden" name="bfubDebitAccount"  id="bfubDebitAccount" value="<s:property value='payBillBean.bfubDebitAccount' />"   /> 
 								</td>							
							</tr>
							<tr>  
								<td> <label for="Maker Id"><strong>Maker Id</strong></label></td>
								<td><s:property value='payBillBean.makerId' />  <input type="hidden" name="makerId"  id="makerId"   value="<s:property value='payBillBean.makerId' />"  /> </td>  
							</tr>  
							<tr>  
								  
								<td><label for="Maker Date"><strong>Maker Date </strong></label></td>
								<td><s:property value='payBillBean.makerDttm' />   <input type="hidden" name="makerDttm"  id="makerDttm"   value="<s:property value='payBillBean.makerDttm' />"  />
							</tr>  
							<tr>															
								<td> <label for="Authorized Id"><strong>Authorized Id</strong></label></td>
								<td><s:property value='payBillBean.authId' />   <input type="hidden" name="authId"  id="authId"   value="<s:property value='payBillBean.authId' />"  /></td>     
							</tr>
							<tr>   
								<td><label for="Authorized Date"><strong>Authorized Date</strong></label></td>
								<td><s:property value='payBillBean.authDttm' /> <input type="hidden" name="authDttm"  id="authDttm"   value="<s:property value='payBillBean.authDttm' />"  />
							</tr> 
							<tr>   
								<td><label for="Status"><strong>Status</strong></label></td>
								<td><s:property value='payBillBean.status' /> <input type="hidden" name="status"  id="status"   value="<s:property value='payBillBean.status' />"  />
							</tr> 
					</table>
				</fieldset>  
			</div>
		</div>
		</div>
	</form>
	
	<div id="dialog" title="Confirmation Required" style="display:none">
		   Proceed To Activate / De-Active Biller Type ?
	</div>
   	<div class="form-actions" >
         <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100" ></input>&nbsp;
         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input>&nbsp;
         <span id ="error_dlno" class="errors"></span>
       </div>  
               						 
	</div>
<script type="text/javascript">
$(function(){
	
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
    
    if($('#fixedamountcheckval').val() == 'Y'){
    	$('#fixed-amt-y').attr('checked','');
    	$('#fixed-amt-n').removeAttr('checked');
    	$('#fixed-amt-tr').show();
	} else {
		$('#fixed-amt-n').attr('checked','');
		$('#fixed-amt-y').removeAttr('checked');
		$('#fixed-amt-tr').hide();
	}
    clearVals();
    if($('#billeridcheckval').val() == 'Y' &&  $('#billerid-y').attr("checked")){
    	$('#billerid-div').show();
    	
    	$('.acctbillertype').hide();
    	$('#bfubCreditAccount').val('');
    	$('#bfubDebitAccount').val(''); 
    	$('#biller-len-tr').show();
	} else { 
		$('#billerid-div').hide();
		$('.acctbillertype').show();
		$('#biller-len-tr').hide();
	}
}); 
</script>
</body>
</html> 