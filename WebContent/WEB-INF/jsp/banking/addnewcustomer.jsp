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
$(document).ready(function(){
	
	
	
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
	  
	  
	
	
	var subrules = {
			   rules : {
				   accountno : { required : true}
				   
			   },  
			   messages : {
				   accountno : { 
				   		required : "Please Enter Account Number."
					}
					
			   } 
			 };
		

			
		
		
		$('#btn-srch').live('click',function() {  
			
			//$("#form1").validate(acctRules);
			
			$("#form1").validate(subrules);
			//alert($("#form1").valid());
			if($("#form1").valid()==true)
				{
				
				myApp.showPleaseWait();
			var queryString9 = "method=fetchAccDatafromCorenew&accNumber="+$("#accountno").val();
			
			$.getJSON("postJson.action", queryString9,function(data) {
				myApp.hidePleaseWait();
					var custid = data.custcode; 
					var accno = data.accountNumber; 
					var fullname = data.accname; 
					var mobile = data.mobile; 
					var nationalid = data.brcode; 
					var product = data.accounttype; 
					var v_message = data.message;
					
					if (v_message =='success')
					{
					
					$("#custid").text(custid);  
					$("#customercode").val(custid);  
					$("#mobileno").val(mobile);  
					$("#custname").text(fullname);  
					$("#mobile").text(mobile);  
					$("#accno").text(accno);  
					$("#natid").text(nationalid);
					$("#operator").text(product);
					
					
					$("#t3").css({"display":""});
					$("#billerMsg").text('');
					$("#ex-mytable").css({"display":""});
					$("#btn-submit").css({"display":""});
					
					}else{
						$("#customercode").val("");  
						$("#mobileno").val("");  
						$("#t3").css({"display":"none"});
						$("#ex-mytable").css({"display":"none"});
						$("#btn-submit").css({"display":"none"});
						
						$("#billerMsg").text(v_message);
						}
			
						
			});
		
			}

		}); 
		    
	 
	
	
	$('#btn-submit').click(function(){ 
			//$("#form1").validate(acctRules);
			//alert($("#customercode").val());
			if($("#form1").valid()) { 
				
				var queryString9 = "method=addCustomerValidation&custcode="+$("#customercode").val()+"&mobile="+$("#mobileno").val();
				
				$.getJSON("postJson.action", queryString9,function(data) {
					
					if(data.finalCount==0){
						myApp.showPleaseWait();
						 $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/addnewcustomerdetails.action'; 
						$('#form1').submit();
						 return true; 
					}else{
						$("#billerMsg").text(data.message);
						return false;
					} 
				
 				
				});
			} else { 
				return false;
			}
	});
	
	
	$('#btn-back').click(function(){ 
		//$("#form1").validate(acctRules);
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
			$('#form1').submit();
			
	});

});




</script>

</head>

<body>
	<form name="form1" id="form1" method="post" action="">
		<div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="#">Add New Customer </a></li>
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
						<i class="icon-edit"></i>Customer Details Search
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
								
								
								<tr class="odd" id="trow1" name="trow1" >
									<td width="25%"><label><strong>Account No<font color="red">*</font></strong></label></td>
									<td width="25%"><input type="text" name="accountno"  id="accountno" required=true   />  
									</td>	
									 <td width="25%"><a class="btn btn-success" href="#" id="btn-srch" name="btn-submit">Search</a></td>
									 <td width="25%"></td>
								</tr>
								
								
							</table>
							<input type="hidden" name="customercode"  id="customercode" required=true   />
							<input type="hidden" name="mobileno"  id="mobileno" required=true   />
							
							</fieldset>
					</div>
			</div>
		</div>
		
		
				<div class="row-fluid sortable" id="t3" name="t3"> 
			
						<div class="box span12"> 
					<div class="box-header well" data-original-title >
							<i class="icon-edit"></i>Customer Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							
							<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false">
        <div class="modal-header">
            <h1>Processing...</h1>
        </div>
        <div class="modal-body">
            <div class="progress progress-striped active">
                <div class="bar" style="width: 100%;"></div>
            </div>
        </div>
    </div>
						</div>
					</div>  
				<div class="box-content"> 
						<table class="table table-striped table-bordered bootstrap-datatable dataTable" id="ex-mytable" style="width: 100%; display:none" >
						  <thead>
								<tr>
									<!-- <th>Sno</th> -->
									<th>Customer ID</th>
									<th>Customer Name </th>
									<th>Mobile Number</th>
									<th>Account Number</th>
									<th>Branch Code</th>
									<th>Account Type</th> 
								</tr>
						  </thead>    
						  <tbody > 
							  <tr>
							  		
						  			<td><span id="custid" name="custid"></span></td>
						  			<td><span id="custname" name="custname"></span></td>
						  			<td><span id="mobile" name="mobile"></span></td>
						  			<td><span id="accno" name="accno"></span></td>
						  			<td><span id="natid" name="natid"></span></td>
									<td><span id="operator" name="operator"></span></td>
							  </tr>
						  </tbody>
						</table> 
					</div> 
		</div> 
		</div> 
		
		
		
	<div class="form-actions">
		<a  class="btn btn-danger" href="#" id="btn-back" name="btn-back">Home</a>
		<a  class="btn btn-success" href="#" id="btn-submit" name="btn-submit" style="display:none">Proceed</a>
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
