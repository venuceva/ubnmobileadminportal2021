<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">

<script type="text/javascript">



 function funcheck(){
	var v=0;
	if($("#fname").val()!=""){
		v=1;
	}
	if($("#lname").val()!=""){
		v=1;
	}
	if($("#mnumber").val()!=""){
		v=1;
	}
	if($("#dob").val()!=""){
		v=1;
	}
	
	
	
	return v;
	
}


				   
				   
				   var list = "dob".split(",");
				   var datepickerOptions = {
				   				showTime: true,
				   				showHour: true,
				   				showMinute: true,
				   	  		dateFormat:'dd/mm/yy',
				   	  		alwaysSetTime: false,
				   	  		yearRange: '1910:2020',
				   			changeMonth: true,
				   			changeYear: true
				   };
			
			
			$(function () { 
				
			
				
    		
    		
				
			
				
				$(list).each(function(i,val){
					$('#'+val).datepicker(datepickerOptions);
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
				
			        $('#btn-submit').live('click', function () { 
			        	
			        	
			        	
			        	if(funcheck()==1){
			        		var queryString = "entity=${loginEntity}&method=searchAgent&fname="+$('#fname').val()+"&lname="+$('#lname').val()+"&mnumber="+$('#mnumber').val()+"&dob="+$('#dob').val();
			        		
			        			$.getJSON("postJson.action", queryString,function(data) { 
			        				if(data.finalCount==0){
			        					$('#errormsg').text("No records available");
			        				}else{ 
					    				var url="${pageContext.request.contextPath}/<%=appName %>/agentregmodifysearch.action"; 
					    				$("#form1")[0].action=url;
					    				$("#form1").submit();
					    				return true;
			        				 }
			        			}); 
			        	}else{
							$('#errormsg').text("Please select any one of the option");
						}		  
			    		
			        }); 
				
		    });  


			
			function redirectAct(){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
				$("#form1").submit();
				return true;
			}


</script>


</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Agent Registration Modify</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
			
			 
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<!-- Customer Negotiated Rate Confirmation -->
						Search Agent Details
						 <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
					
				 
					<div class="box-content" id="secondaryDetails">
						<fieldset>
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr class="odd">
									<td width="25%"><label for="fname"><strong>First Name</strong></label></td>
									<td width="25%"><input name="fname" autocomplete="off" type="text" class="field" id="fname"  value="" required=true   />
									
									</td>
									
									<td width="25%"><label for="lname"><strong>Last Name</strong></label></td>
									<td width="25%"><input name="lname" autocomplete="off" type="text" class="field" id="lname" value="" required=true  /></td>
									
								</tr>
								<tr class="even">	
									<td width="25%"><label for="mnumber"><strong>Mobile Number</strong></label></td>
									<td width="25%"><input name="mnumber" autocomplete="off" type="text" class="field" id="mnumber" value="" required=true /></td>
								
									<td width="25%"><label for="dob"><strong>Date Of Birth</strong></label></td>
									<td width="25%"><input type="text"   class="fromDate" id="dob" name="dob" style="width: 200px;" required=true readonly /> 
									
									</td>
									
									</tr>
									
								
							</table>
							
							
						</fieldset>
						
					</div>
					
				</div>
			</div>
			
			
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Cancel" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Search" width="100"  ></input> 
			</div>  

	
			
			</div>
			

</form>

</script>
</body>
</html>
