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
<link href="${pageContext.request.contextPath}/css/SheepToasts/body.css" rel="stylesheet" type="text/css">


<script type="text/javascript">
	

				   
				   var userictadminrules = {
				    		
				    		rules : {
				    			
				    			Narration : { required : true } ,
				    		},		
				    		messages : {
				    			
				    		
				    			Narration : { 
				    							required : "Please Enter Reply Message.",
				    						}
				    			
				    		} 
				    	};
				   
				   
				 
			
			
			$(function () { 
				
				
				
		        $('#btn-submit').live('click', function () { 
		    	     $("#form1").validate(userictadminrules); 
		    		
		    				var url="${pageContext.request.contextPath}/<%=appName %>/customerqueriesreplay.action"; 
		    				$("#form1")[0].action=url;
		    				$("#form1").submit(); 
		    				
		        }); 
		    });  

			$.validator.addMethod("regex", function(value, element, regexpr) {          
				 return regexpr.test(value);
			   }, ""); 
			
			
			function redirectHome(){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/customerqueries.action';
				$("#form1").submit();
				return true;
			}
			
			function decisionradio(v){

				
				$("#decision").val(v);

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
					 <li> <a href="#">Customer Queries</a> </li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						Customer Chatting Details 
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr class="odd">
									<td width="25%"><label for="File"><strong>Customer Id</strong></label></td>
									<td width="25%">${responseJSON.CUSTOMER_CODE}</td>
								
									<td width="25%"><label for="Client"><strong>User Id</strong></label></td> 
									<td width="25%">${responseJSON.USER_ID}</td>
									
									</tr>
								<tr class="even">
									<td width="25%"><label for="File"><strong>File Name</strong></label></td>
									<td width="25%">${responseJSON.FIRST_NAME}</td>
								
									<td width="25%"><label for="Client"><strong>Gender</strong></label></td> 
									<td width="25%">${responseJSON.GENDER}</td>
									
									</tr>	
							<tr class="even">
									<td width="25%"><label for="File"><strong>Subject</strong></label></td>
									<td width="75%" colspan="3">${responseJSON.SUBJECT}</td>
								
									
									</tr>
								
							</table>
							<div style="width: 100%; height: 300px; overflow-y: scroll;" >${responseJSON.HISTORY}</div>
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details"  >
								<tr class="odd" >
									<td align="center" ><div><strong>Reply Message<font color="red">*</font></strong></label></div>
									<textarea rows="10" cols="100" name="Narration" class="field" id="Narration" style="width:500px"  required=true ></textarea></td>
								</tr>
								
							</table>
							
						</fieldset>
						
						
					</div>
				</div>
			</div>
			
			<input type="hidden" name="refno"    id="refno"  value='${responseJSON.ID}'   />
			<input type="hidden" name="userid"   id="userid"  value='${responseJSON.USER_ID}'   />
			<input type="hidden" name="filename"    id="filename"  value='${responseJSON.SUBJECT}'   /> 
	
		
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Back" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Reply" width="100"  ></input> 
			</div>  

			
			
		
			</div>
			

</form>

</body>
</html>
