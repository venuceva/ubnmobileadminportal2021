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
<link href="${pageContext.request.contextPath}/css/link/css1" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/link/screen.css">
<link href="${pageContext.request.contextPath}/css/link/sticky.css" rel="stylesheet" type="text/css">

 
<script type="text/javascript" > 


	

	
	
	
	var subrules = {
			   rules : {
				   
				   fromdate : { required : true},
				   todate : { required : true}
				   
			   },  
			   messages : {
				 
					fromdate : { 
				   		required : "Please Select From Date ."
					},
					todate : { 
				   		required : "Please Select To Date ."
					}
					
			   } 
			 };
		

			
		
		

		    
	 
	$(document).ready(function(){
		
		
		
		
		
		
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
		
		
	
	$('#btn-submit').click(function(){ 
		
			$("#form1").validate(subrules);
			
			
			
			
			//myApp.showPleaseWait();
				
 				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/graphicalreportchanneldetails.action'; 
				$('#form1').submit();
				 return true; 
			
	});
	
	$('#btn-back').click(function(){ 
		//$("#form1").validate(acctRules);
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
			$('#form1').submit();
			
	});

});


	var list = "fromdate,todate".split(",");
	var datepickerOptions = {
					showTime: true,
					showHour: true,
					showMinute: true,
		  		dateFormat:'dd/mm/yy',
		  		alwaysSetTime: false,
		  		yearRange: '1910:2030',
				changeMonth: true,
				changeYear: true
	};
	$(function() {
		$(list).each(function(i,val){
			$('#'+val).datepicker(datepickerOptions);
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
				  <li><a href="#">Graphical Reports</a></li>
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
						<i class="icon-edit"></i>Channel Wise Graphical Reports
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
						
						
						
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
								
								
								<tr class="even" id="tabdate"  >
									<td ><label for="Date"><strong>From Date</strong><font color="red">*</font></label></td>
									<td > 
										<input type="text" maxlength="10"  class="fromDate" id="fromdate" name="fromdate" required=true  />  							
									</td>
									<td ><label for="Date"><strong>To Date</strong><font color="red">*</font></label></td>
									<td > 
										<input type="text" maxlength="10"  class="fromDate" id="todate" name="todate" required=true  />  							
									</td>
									
								</tr>
														
							</table>
							</fieldset>
					</div>
			</div>
		</div>
		
		
		<input type="hidden" name="searchid"  id="searchid"   /> 
		
	<div class="form-actions">
		<a  class="btn btn-danger" href="#" id="btn-back" name="btn-back">Home</a>
		<a  class="btn btn-success" href="#" id="btn-submit" name="btn-submit" >Submit</a>
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
