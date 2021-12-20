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
var acctRules = {
		   rules : {
			   gender : { required : true},
			   branchdetails : { required : true},
			   product : { required : true}
		   },  
		   messages : {
			   gender : { 
			   		required : "Please Select Gender."
				},
				branchdetails : { 
			   		required : "Please Select Branch."
				},
				product : { 
			   		required : "Please Select Product."
				}
		   } 
		 };
		
		
		
 
$(function() {   
	
/* 	$("#dialog").dialog({
			autoOpen: false,
			modal: false,

	      
   }); */
	
   
  /*  $('#telco').val('<s:property value="accBean.telco" />');
   $('#telco').trigger("liszt:updated");

   $('#langugae').val('<s:property value="accBean.langugae" />');
   $('#langugae').trigger("liszt:updated");
    */
   
  
		
	// add multiple select / deselect functionality
	$("#select-all").click(function () {
		$("#error_dlno").text("");
		$('.center-chk').attr('checked', this.checked);
	});

	// if all checkbox are selected, check the selectall checkbox
	// and viceversa
	$(".center-chk").click(function(){
		$("#error_dlno").text("");
		if($(".center-chk").length == $(".center-chk:checked").length) {
			$("#select-all").attr("checked", "checked");
		} else {
			$("#select-all").removeAttr("checked");
		}
	});
	
	
	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/walletregistration.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){ 
		var finalData = "";
		 var allVals = [];
		 
		//$('#billerMsg').text('');
		//$('#error_dlno').text('');
		if($('#authStatus').val()!="ACCOUNTNO"){
		 $("#form1").validate(acctRules);
		}

		
		 if($("#form1").valid() ) {  
			 

				 $("#dialog").dialog({
				      buttons : {
				        "Confirm" : function() { 
				        	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/accountopenServicecallconf.action';
							$("#form1").submit();
				        },
				        "Cancel" : function() {
				           $(this).dialog("close");
				        }
				      }
				    });
				  
				
				 
				 return true; 
			  
			 
		 } else {
			 //alert("in else");
			 return false;
		 }
			
	});
});	



$(function() { 
	

if($('#authStatus').val()!="ACCOUNTNO"){
	$('#tgender').css("display","");
	$('#tbranchdetails').css("display","");
	$('#tproduct').css("display","");
	
	$('#tgender1').css("display","none");
	$('#tbranchdetails1').css("display","none");
	$('#tproduct1').css("display","none");
	
	$('#tgender2').css("display","");
	$('#tbranchdetails2').css("display","");
	$('#tproduct2').css("display","");
}
	
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
		    
		    
		    
		    $('#branchdetails').on('change', function() {
		    	 // alert( this.value ); 
		    	  $('#rmcode1').text((this.value).split("-")[1]);
		    	  $('#rmcode').val((this.value).split("-")[1]);
		    	 
		    	});
		   
		    
		    var queryString = "";
		    $.getJSON("branchajx.action", queryString, function(data) {
		    	 			if(data.region!=""){
		    	 			//	alert(data.region);
		    	 				var mydata=data.region;
		    	      			//var mydata=(data.region).substring(5,(data.region).length);
		    	      			var mydata1=mydata.split(":");
		    	      
		    	       			$.each(mydata1, function(i, v) {
		    	       				var options = $('<option/>', {value: mydata.split(":")[i], text: (mydata.split(":")[i]).split("-")[0]}).attr('data-id',i);
		    	       				$('#branchdetails').append(options);
		    	       				$('#branchdetails').trigger("liszt:updated");
		    	       			});
		    	       			
		    	       			
		    	      		} 
		    	     	});     
		    
		    
		    
		  
	  //alert($('#product').val());  	
	    	
});

$(function() {
	$(list).each(function(i,val){
		$('.'+val).datepicker(datepickerOptions);
	});
});

function funa(v){
	$('#gender').val(v);
}
	 
</script>

</head> 

<body>

		
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
						   
						   <td width="25%"><label for="Service ID"><strong>Gender<font color="red" style="display:none" id="tgender2">*</font></strong></label></td>
									<td width="25%"  style="display:none" id="tgender"> 
									   <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'MALE':'Male','FEMALE':'Female'}" 
							         name="gender" 
							         id="gender"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
	 							</td>
						    <td id="tgender1"></td>
						   
 						</tr>
 						
 						<tr class="even">
						
							
						   <td><label for="To Date"><strong>Branch Code<font color="red" style="display:none" id="tbranchdetails2">*</font></strong></label> </td>
					       <td style="display:none" id="tbranchdetails" >
					       <select id="branchdetails" name="branchdetails" class="chosen-select-width" >
							 <option value="">Select</option>
							</select>
 							
						     
						   </td>
						   <td id="tbranchdetails1"></td>
						   <td width="25%"><label for="Service ID"><strong>RM Code</strong></label></td>
						   <td width="25%" ><div id="rmcode1"></div>
						   <input type="hidden"  name="rmcode" id="rmcode"  />
						   </td>
						    
						   
 						</tr>
 						
 						<tr class="even">
						
							
						 
						   <td width="25%"><label for="Service ID"><strong>products<font color="red" style="display:none" id="tproduct2">*</font></strong></label></td>
									<td width="25%"   style="display:none" id="tproduct"> 
									   <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'SA_006':'Union Save','SA_007':'Union Save More'}" 
							         name="product" 
							         id="product"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
	 							</td>
						     <td id="tproduct1"></td>
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
						    
						     <td><label for="Service ID"><strong>Count</strong></label></td>
					       <td ><s:property value='accBean.message' />
					       <input type="hidden" value='<s:property value='accBean.message' />' class="field"  name="count" id="count"  /></td>
					       </td>
						   
 						</tr>
 												
				 </table>
				 <input type="hidden" value='<s:property value='accBean.authStatus' />' class="field"  name="authStatus" id="authStatus"  />
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