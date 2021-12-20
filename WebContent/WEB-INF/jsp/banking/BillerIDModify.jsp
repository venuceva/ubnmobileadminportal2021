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
 
var billerrules = {
		rules : {
		 
			billerIdDescription : { required : true  },
			bfubCreditAccount : {    required : false,  notEqualTo: ['#bfubDebitAccount'], regexchk: /^([A-Z0-9]{14,14})$/i  },
			bfubDebitAccount : {    required : false, notEqualTo: ['#bfubCreditAccount'], regexchk: /^([A-Z0-9]{14,14})$/i }
			
		},
		messages : {
			
			  billerIdDescription : { 
				required : "Please Select Core Banking." 
		  	},
		  	  bfubCreditAccount : {
				regexchk :   "Only alpha with caps and numeric of 14 characters allowed." 
			},
			  bfubDebitAccount : {
				regexchk :   "Only alpha with caps and numeric of 14 characters allowed.." 
			}
	   },
	   
		errorPlacement: function(error, element) {
		    if ( element.is( ':radio' ) || element.is( ':checkbox' ) )
				error.appendTo( element.parent() );
		    else if ( element.is( ':password' ) )
				error.hide();
		    else
				error.insertAfter( element );
		}
			
 };
	 

$(document).ready(function(){ 
	
	jQuery.validator.addMethod("notEqualTo",
			function(value, element, param) {
			    var notEqual = true;
			    value = $.trim(value);
			    for (i = 0; i < param.length; i++) {
			        if (value == $.trim($(param[i]).val())) { notEqual = false; }
			    }
			    return this.optional(element) || notEqual;
			},
			"Please enter a diferent value."
			);
	
  
	$.validator.addMethod("regexchk", function(value, element, regexpr) { 
		 return this.optional(element) || regexpr.test(value); 
	   }, ""); 
	
	 
	$('#error_dlno').text(' ');
	
	$('#btn-submit').on('click',function() { 
		$('#billerMsg').text('');
		$('#error_dlno').text(' ');
		
		$("#form1").validate(billerrules); 
		
		if($("#form1").valid()) {
			
			$("#form1").validate().cancelSubmit = true;
		 				
			var data='${payBillBean.billerIdType}';
			$('#billerIdType').val(data);
		 
			
			var queryString ="billerIdType="+$('#billerIdType').val()+"&bfubCreditAccount="+$('#bfubCreditAccount').val()+"&method=checkBfubAction";
			 
			
			 $.getJSON("checkCreditDebitAccount.action", queryString,function(data) { 
				 
				 v_message = data.message; 
			 
				 if(v_message == "SUCCESS") {
		        	    
		    	    	 $('#error_dlno').text("Please Enter New  Account");
		    	    	  return false;
		    			
		    	    }else{
		        	     $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/modifyBillerIDConfirm.action";
					     $("#form1").submit();
					     return false;
		    	    }
			  });
		     }      
	    }); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/mpesaAccManagement.action";
		$("#form1").submit();					
	}); 
    
	
});

//For Closing Select Box Error Message_Start
$(document).on('change','select',function(event) {  
	 if($('#'+$(this).attr('id')).next('label').text().length > 0) {
		  $('#'+$(this).attr('id')).next('label').text(''); 
		  $('#'+$(this).attr('id')).next('label').remove();
	 }
 
});
//For Closing Select Box Error Message_End 
</SCRIPT>  
 
 

 
</head> 
<body> 
	  <div id="content" class="span10">  
		    <div> 
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="mpesaAccManagement.action">Mpesa A/C Management</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Modify Biller ID</a></li>
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
								<td><label for="Biller Type"><strong>Biller ID(Client Details) </strong></label></td>
								<td><input type="text" name="billerId"  id="billerId" required=true  readonly value="<s:property value='payBillBean.billerId' />"   />   </td>
							 </tr> 
							 
						     <tr> 
								<td><label for="Biller Type"><strong>Biller Type ID(Client Details) </strong></label></td>
								<td><input type="text" name="billerIdName"  id="billerIdName" required=true readonly  value="<s:property value='payBillBean.billerIdName' />"   />   </td>
							</tr> 							 
						 
							 
							<tr > 
								<td><label for="Biller Type Description"><strong>Biller ID Description<font color="red">*</font></strong></label></td>
					            <td><input type="text" name="billerIdDescription"  id="billerIdDescription"  required=true  value="<s:property value='payBillBean.billerIdDescription' />"   />   </td>
							</tr> 
							  
							<tr class="acctbillertype">  
							
								<td><label for="Credit Account"><strong>Credit Account<font color="red">*</font></strong></label></td>
								<td><input type="text" name="bfubCreditAccount"  id="bfubCreditAccount" value="<s:property value='payBillBean.bfubCreditAccount' />"   />  </td>							
							
							</tr> 
							
							<tr class="acctbillertype">  
								
								<td><label for="Debit Account"><strong>Debit Account<font color="red">*</font></strong></label></td>
								<td><input type="text" name="bfubDebitAccount"  id="bfubDebitAccount" value="<s:property value='payBillBean.bfubDebitAccount' />"   /> 
								</td>							
							
							</tr>
							
							<%-- <tr>  
								 <td><label for="Maker Id"><strong>Maker Id</strong></label></td>
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
								<td><s:property value='payBillBean.authDttm' />   
								<input type="hidden" name="authDttm"  id="authDttm"   value="<s:property value='payBillBean.authDttm' />"  />
								<input type="hidden" name="id"  id="id" value="<s:property value='payBillBean.id' />"  />
							</tr>  --%>
					</table>
				</fieldset>  
			</div>
			<input type="hidden" id="billerIdType" name="billerIdType">
 		</div>
		</div>
	</form>
 
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
    
     
});	  
</script>
</body>
</html> 