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
	

				
				
				
				
$(function () { 
				
				
				        $('#btn-submit').live('click', function () { 
				        	
				        	if($("#result").val()=="fail"){
				        		$("#errormsg").text("Please check uploaded file , Occured Error in this file ");
				        		return false;
				        	}else{
				        	
				    		
				    				var url="${pageContext.request.contextPath}/<%=appName %>/bulkdsaregprocessack.action"; 
				    				$("#form1")[0].action=url;
				        		$("#form1").submit(); 
				        		return true;
				        	}	
					    			
				        }); 
	    	     
		    });  

			$.validator.addMethod("regex", function(value, element, regexpr) {          
				 return regexpr.test(value);
			   }, ""); 
			
			
			function redirectHome(){
				
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
				$("#form1").submit();
				return true;
			}
			
			
	

			 $(function() {
				 
					var config = {
				      '.chosen-select'           : {},
				      '.chosen-select-deselect'  : {allow_single_deselect:true},
				      '.chosen-select-no-single' : {disable_search_threshold:10},
				      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				      '.chosen-select-width'     : {width:"95%"}
				    }
					
				    for (var selector in config) {
				      $(selector).chosen(config[selector]);
				    } 
				  	 
					 
					
				});			
</script>
<s:set value="responseJSON" var="respData" />
</head>
<body>
<form name="form1" id="form1" method="post" enctype="multipart/form-data" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					 <li><a href="bulkdsareg.action">File Upload</a></li>
					
				</ul>
			</div>
			
			
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						File Upload Confirmation
						<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								
								<tr class="even">
								<td width="25%"><label for="gender"><strong>File Upload Type </strong></label></td>
									<td width="25%">${responseJSON.limitcode}
									<input name="limitcode" autocomplete="off" type="hidden" class="field" id="limitcode"   value="${responseJSON.limitcode}" /></td>
									<td width="25%"></td>
									<td width="25%"></td>
									
								</tr>
								
								
								<tr class="odd">
									<td>
									<strong><label for="file"><strong>Upload File Name</strong></label></strong></td>
									<td>${responseJSON.filename}
									<input name="filename" autocomplete="off" type="hidden" class="field" id="ptamt"  value="${responseJSON.filename}"  /></td>
									<td><label for="file"><strong>Total Records</strong></label></strong></td>
									<td>${responseJSON.records}
									<input name="records" autocomplete="off" type="hidden" class="field" id="records"  value="${responseJSON.records}"  /></td>
								</tr>
								
								
															
							</table>
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								${responseJSON.Files_List.status}	
								</table>
						
							
						
	<br><br>
						
					<input name="jsondata" autocomplete="off" type="hidden" class="field" id="jsondata"    />
						</fieldset>
						
					</div>
				</div>
			</div>
			
			
	
		
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Confirm" width="100"  ></input> 
			</div>  

			
			
		
			</div>
			

</form>

</body>
</html>
