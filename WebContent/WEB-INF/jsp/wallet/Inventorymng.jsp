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
				   status : { required : true}
				   
			   },  
			   messages : {
				   status : { 
				   		required : "Please Select Status ."
					}
					
			   } 
			 };
		

			
		
		

		    
	 
	$(document).ready(function(){
		
		
		
		var queryString1 = "sid=STATE";
		
		$.getJSON("branchcodeajx.action", queryString1, function(data) {
				if(data.region!=""){
					var mydata=data.region;
	  			var mydata1=mydata.split(":");
	  
	   			$.each(mydata1, function(i, v) {
	   				
	   				var options = $('<option/>', {value: mydata.split(":")[i], text: (mydata.split(":")[i]).split("-")[1]}).attr('data-id',i);
	   				
	   				$('#state').append(options);
	   				$('#state').trigger("liszt:updated");
	   			});
	   			
	   			
	  		} 
	 	});
		
		
		
		$('#state').on('change', function() {
			//alert($('#state').val());
			 var queryString1 = "sid=STATESEARCH&serialNumber="+($('#state').val()).split("-")[0];
			 /* $('#state').val(($('#state').val()).split("-")[1]); */
			$.getJSON("branchcodeajx.action", queryString1, function(data) {
					if(data.region!=""){
						var mydata=data.region;
		  			var mydata1=mydata.split(":");
		  			
		  			
		  			$('#localGovernment').empty();
					$('#localGovernment').trigger("liszt:updated");
					
		   			$.each(mydata1, function(i, v) {
		   				
		   				var options = $('<option/>', {value: mydata.split(":")[i], text: mydata.split(":")[i]}).attr('data-id',i);
		   				
		   				$('#localGovernment').append(options);
		   				$('#localGovernment').trigger("liszt:updated");
		   			});
		   			
		   			
		  		} 
		 	});  
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
			    
			    $("#state").prop('disabled', 'disabled');
			    $('#state').trigger("liszt:updated");
			    
			    $("#localGovernment").prop('disabled', 'disabled');
			    $('#localGovernment').trigger("liszt:updated");
			    
			    $('[type=radio]').on('click', function () {
			    	
			    	$("#telephone").attr("readonly", "readonly");	
			    	$("#storeid").attr("readonly", "readonly");
			    	$("#terminalid").attr("readonly", "readonly");
			    	$("#serialnumber").attr("readonly", "readonly");
			    	
			    	$("#state").val("Select");
			    	$("#state").prop('disabled', 'disabled');
				    $('#state').trigger("liszt:updated");
				    
				    $("#localGovernment").val("Select");
				    $("#localGovernment").prop('disabled', 'disabled');
				    $('#localGovernment').trigger("liszt:updated");
				    
				    $("#searchid").val(this.id);
				    
			        if(this.id=="mobilenumber1"){
			        	$("#telephone").removeAttr("readonly");	
			        }
			        
			        if(this.id=="storeid1"){
			        	$("#storeid").removeAttr("readonly");	
			        }
			        
			        if(this.id=="terminalid1"){
			        	$("#terminalid").removeAttr("readonly");	
			        }
			        
			        if(this.id=="serialnumber1"){
			        	$("#serialnumber").removeAttr("readonly");	
			        }
			        
			        if(this.id=="state1"){
			        	$("#state").removeAttr("disabled");
					    $('#state').trigger("liszt:updated");
					    
					    $("#localGovernment").removeAttr("disabled");
					    $('#localGovernment').trigger("liszt:updated");
			        }
			        
			       
			    })
		
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
				
 				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/inventorymngdetails.action'; 
				$('#form1').submit();
				 return true; 
			
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
				  <li><a href="#">Inventory  Management</a></li>
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
						<i class="icon-edit"></i>Search Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
						
						
						
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
								<tr class="even" >
								
									<td width="25%"><label for="Service ID"><input type="radio" name="search" id="mobilenumber1" /><strong>Mobile Number</strong></label></td>
									<td width="25%" > 
									<span><input type="text" name="isocode" id="isocode" style="width:25px;" value="234" disabled /></span><input type="text" name="telephone"  id="telephone"  maxlength="10" readonly style="width:172px;" /> 
	 							</td>	
	 							<td width="50%" colspan="2"> </td>  								
								</tr>
								<tr class="even" >
								
									<td width="25%"><label for="Service ID"><input type="radio" name="search" id="storeid1"  /><strong>Store Name</strong></label></td>
									<td width="25%" > 
									<input type="text" name="storeid"  id="storeid"  readonly /> 
	 							</td>	
	 							<td width="50%" colspan="2"> </td>   								
								</tr>
								<tr class="even"  >
								
									<td width="25%"><label for="Service ID"><input type="radio" name="search" id="terminalid1" /><strong>Terminal Id</strong></label></td>
									<td width="25%" > 
									<input type="text" name="terminalid"  id="terminalid"  readonly /> 
	 							</td>	
	 							<td width="50%" colspan="2"> </td>   								
								</tr>
								<tr class="even" >
								
									<td width="25%"><label for="Service ID"><input type="radio" name="search" id="serialnumber1" /><strong>Serial Number</strong></label></td>
									<td width="25%" > 
									<input type="text" name="serialnumber"  id="serialnumber"  readonly /> 
	 							</td>	
	 							<td width="50%" colspan="2"> </td> 								
								</tr>
								<tr class="even"  >
								
									<td width="25%"><label for="Service ID"><input type="radio" name="search" id="state1" /><strong>State</strong></label></td>
									<td width="25%" >
									
									<select id="state" name="state" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
															</select> 
															 
									
	 							</td>	 
	 							<td width="25%"><label for="Service ID"><strong>Local Government</strong></label></td>
									<td width="25%" > 
									<select id="localGovernment" name="localGovernment" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
															</select> 
									
									
	 							</td>							
								</tr>
								<tr class="even" >
								
									<td width="25%"><label for="Service ID"><input type="radio" name="search" id="all" checked /><strong>ALL</strong></label></td>
									<td width="75%" colspan="3"> 
									
	 													
								</tr>
								<tr class="even" >
								
									<td width="25%"><label for="Service ID"><strong>Status</strong><font color="red">*</font></label></td>
									<td width="25%" > 
									<select id="status" name="status" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
																<option value="ALL">ALL</option>
																<option value="A">Active</option>
																<option value="D">Deactive</option>
																<option value="R">Retrival</option>
															</select>
	 							</td>	
	 							<td width="50%" colspan="2"> </td> 								
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
