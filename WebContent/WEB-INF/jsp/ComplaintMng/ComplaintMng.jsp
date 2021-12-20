<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
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
.modal-content {
  background-color: #0480be;
}
.modal-body {
  background-color: #fff;
}
</style>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/bootstrap-2.3.2.min.js'></script>

 
<script type="text/javascript" > 

var list = "fromdate,todate".split(",");
var datepickerOptions = {
				showTime: true,
				showHour: true,
				showMinute: true,
	  		dateFormat:'dd/mm/yy',
	  		alwaysSetTime: true,
	  		yearRange: '1910:2050',
			changeMonth: true,
			changeYear: true
};	

$(document).ready(function(){
	
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	});
	
	var myApp;
	myApp = myApp || (function () {
	    var pleaseWaitDiv = $('<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false"><div class="modal-header"><h1>Processing...</h1></div><div class="modal-body"><div class="progress progress-striped active"><div class="bar"  style="width: 100%;"></div></div></div></div>');
	    return {
	        showPleaseWait: function() {
	            pleaseWaitDiv.modal();
	        },
	        hidePleaseWait: function () {
	            pleaseWaitDiv.modal('hide');
	        },

	    };
	})();
	
	  $("#t3").css({"display":"none"});
	 // $("#t3").css({"display":"none"});
	  
	  
	$('#srchcriteria').on('change', function (e) {
	    var optionSelected = $("option:selected", this);
	    var valueSelected = this.value;
	    $("#errors").text(" ");
	    $("#fromdate").val("");
	    $("#todate").val("");
	    $("#accno").val("");
	   
	    
	    if(valueSelected!=""){
	    	

			    	 if(valueSelected =='DATE')	
			    		 {
					    		 $("#tabdate").show();
					    		 $("#tabacc").hide();
					    		 $("#tabstatus").show();
					    		
			    		 }
				else if(valueSelected =='ACCT_NUMBER')
	    				{	
					 			$("#tabdate").hide();
					 			$("#tabacc").show();
					 			$("#tabstatus").hide();
							
	    				}
			    else if(valueSelected =='STATUS')
			    		{
			    	 			$("#tabstatus").show();
			    	 			$("#tabdate").hide();
			    	 			$("#tabacc").hide();
			    				
			    		}
			    else if(valueSelected =='SHOW_ALL')
			    		{
						    	$("#tabstatus").hide();
			    	 			$("#tabdate").show();
			    	 			$("#tabacc").hide();
						    		    	
			    		}
			    	 
			    	
			    	 
	    }
	    
	    
	});
	
	var subrules = {
			   rules : {
				   customerId : { required : true, digits : true}
				   
			   },  
			   messages : {
				   customerId : { 
				   		required : "Please Enter Values."
					}
					
			   } 
			 };
		

			
		
		
		
		    
	 
	
	
	$('#btn-submit').click(function(){ 
			//$("#form1").validate(acctRules);
			/* if($("#form1").valid()) {  */
				myApp.showPleaseWait();
				
 				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/complaintmngdetails.action'; 
				$('#form1').submit();
				 return true; 
				
				
			/* } else { 
				return false;
			} */
	});
	
	
	$('#btn-back').click(function(){ 
		//alert($("#raas").val());
		
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
			$('#form1').submit();
	
	});
	
	

});

function fun(v){
	$('#checkoption').val(v);
	if(v=="Registration"){
		
		
	$('#trowa1').css("display","");
	$('#trowa2').css("display","none");
	}
	
	if(v=="xpxissue"){
		
		
		$('#trowa1').css("display","");
		$('#trowa2').css("display","none");
		}
	
	if(v=="Raastimeout"){
		$('#trowa2').css("display","");
		$('#trowa1').css("display","none");
		$('#trow1').css("display","none");
		
	}
}


</script>

</head>

<body>
	<form name="form1" id="form1" method="post" action="">
		<div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="#">Complaint Management</a></li>
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
		<div class="row-fluid sortable"><!--/span-->
			<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Search Complaints
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
								
								<tr class="even"  >
					
					
					
					
				</tr> 
								
								<tr class="even" id="trowa1" name="trowa1" >
									<td width="25%"><label for="Service ID"><strong>Select Search Criteria<font color="red">*</font></strong></label></td>
									<td width="75%" colspan=3> 
									   <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'DATE':'Date','ACCT_NUMBER':'Account Number','STATUS':'Status','SHOW_ALL':'Show All'}" 
							         name="srchcriteria" 
							         id="srchcriteria"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
								
	 							</td>	
	 							
								</tr>
								
								<tr class="even" id="tabdate" style="display:none" >
									<td width="25%"><label for="Date"><strong>From Date</strong><font color="red">*</font></label></td>
									<td width="25%" > 
										<input type="text" maxlength="10"  class="fromDate" id="fromdate" name="fromdate" required=true  />  							
									</td>
									<td width="25%"><label for="Date"><strong>To Date</strong><font color="red">*</font></label></td>
									<td width="25%"> 
										<input type="text" maxlength="10"  class="fromDate" id="todate" name="todate" required=true  />  							
									</td>
									
								</tr>
								
								<tr class="even" id="tabacc" style="display:none" >
									<td width="25%"><label for="Date"><strong>Account Number</strong><font color="red">*</font></label></td>
									<td width="75%" colspan=3> 
										<input type="text"   class="fromDate" id="accno" name="accno" required=true  />  							
									</td>
									
									
								</tr>
								
								<tr class="even" id="tabstatus" style="display:none" >
									<td width="25%"><label for="Date"><strong>Status</strong><font color="red">*</font></label></td>
									<td width="75%" colspan=3> 
										<s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'OPEN':'Open','CLOSED':'Closed'}" 
							         name="status" 
							         id="status"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Status" 
							           />  							
									</td>
									
									
								</tr>
								
								
							</table>
							</fieldset>
					</div>
			</div>
		</div>
		
		
				
		
		
		
	<div class="form-actions">
		<a  class="btn btn-danger" href="#" id="btn-back" name="btn-back">Home</a>
		<a  class="btn btn-success" href="#" id="btn-submit" name="btn-submit" >Proceed</a>
		<span id="billerMsg" class="errors"></span>
	</div>
</div> 
</form>
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
