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
	
	 

var userictadminrules = {
 		
 		rules : {
 			
 			updatedamtlmt : { required : true,regex: /^[0-9]+$/ } ,
 			updatenumoftran : { required : true,regex: /^[0-9]+$/ } ,
 			updateptamt : { required : true,regex: /^[0-9]+$/ } ,
 		},		
 		messages : {
 			
 		
 			updatedamtlmt : { 
 				required : "Please Enter Daily Amount Limit.",
				regex : "Daily Amount Limit, can not allow character or special characters."
					},
			updatenumoftran : { 
		 			required : "Please Enter Daily Number of Transaction.",
					regex : "Daily Number of Transaction, can not allow character or special characters."
					},
			updateptamt : { 
			 		required : "Please Enter Per Transaction Amount.",
					regex : "Per Transaction Amount, can not allow character or special characters."
	 				}
 			
 		} 
 	};

			
			$(function () { 
				
			
				  $("#form1").validate(userictadminrules); 
				
		        $('#btn-submit').live('click', function () { 
		        	
		        	var queryString1 = "entity=${loginEntity}&method=searchAgent&link=AUTHPENDLMT&fname="+$('#limitcode').val()+"&mnumber="+$('#limitcode').val();
	        		
        			$.getJSON("postJson.action", queryString1,function(data) { 
        				
        				if(data.finalCount==0){
		    	  
		    				var url="${pageContext.request.contextPath}/<%=appName %>/accopenmodifyconf.action"; 
		    				$("#form1")[0].action=url;
		    				$("#form1").submit();
		    				return true;
        				}else{
        					$('#errormsg').text("This Limit Code Authorization Pending ");
        				}
        			});
		    	    		
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

	
	
	function calcfun(){
		
		
		if(!$("#updatedamtlmt").val()=="" && !$("#updatenumoftran").val()==""){
			
			$("#updateptamt").val($("#updatedamtlmt").val()/$("#updatenumoftran").val())
			
		}
		
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
					 <li> <a href="dsalimitmng.action">Account Set Limit</a> </li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						Limit Details
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="userid"><strong>Limit Code</strong></label></td>
									<td width="25%">${responseJSON.limitcode}
									<input name="limitcode" autocomplete="off" type="hidden" class="field" id="limitcode"  value="${responseJSON.limitcode}"  /></td>
									<td width="25%"><label for="empno"><strong>Limit Description</strong></label></td>
									<td width="25%">${responseJSON.limitdesc}
									<input name="limitdesc" autocomplete="off" type="hidden" class="field" id="limitdesc"  value="${responseJSON.limitdesc}" /></td>
								</tr>
							</table>
								<table>
								<tr >
									<td colspan="4" class='label label-warning' ><strong>Current Limit </strong></td>
								</tr>
								</table>
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details1" >	
								<tr class="odd">
									<td width="25%"><label for="mid"><strong>Daily Amount Limit</strong></label></td>
									<td width="25%">${responseJSON.damtlmt}
									<input name="damtlmt" autocomplete="off" type="hidden" class="field" id="damtlmt"  value="${responseJSON.damtlmt}"  /></td>
									<td width="25%"><label for="gender"><strong>Daily Number of Transaction </strong></label></td>
									<td width="25%">${responseJSON.numoftran}
									<input name="numoftran" autocomplete="off" type="hidden" class="field" id="numoftran"  value="${responseJSON.numoftran}" />
									</td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="mid"><strong>Per Transaction Amount</strong></label></td>
									<td width="25%">${responseJSON.ptamt}
									<input name="ptamt" autocomplete="off" type="hidden" class="field" id="ptamt"  value="${responseJSON.ptamt}"  /></td>
									<td width="25%"></td>
									<td width="25%"></td>
								</tr>
								</table>
								<table>
								<tr >
									<td colspan="4" class='label label-warning' ><strong>Change Limit </strong></td>
								</tr>
								</table>
								<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details1" >
								<tr class="odd">
									<td width="25%"><label for="mid"><strong>Daily Amount Limit</strong></label></td>
									<td width="25%">
									<input name="updatedamtlmt" autocomplete="off" type="text" class="field" id="updatedamtlmt"  value="${responseJSON.updatedamtlmt}" onblur="calcfun()" /></td>
									<td width="25%"><label for="gender"><strong>Daily Number of Transaction </strong></label></td>
									<td width="25%">
									<input name="updatenumoftran" autocomplete="off" type="text" class="field" id="updatenumoftran"  value="${responseJSON.updatenumoftran}" onblur="calcfun()" />
									</td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="mid"><strong>Per Transaction Amount</strong></label></td>
									<td width="25%">
									<input name="updateptamt" autocomplete="off" type="text" class="field" id="updateptamt"  value="${responseJSON.updateptamt}" readonly /></td>
									<td width="25%"></td>
									<td width="25%"></td>
								</tr>
								
								
								
								
															
							</table>
							
							
						
							
	
						</fieldset>
					
							
					</div>
				</div>
			</div>
			
			
	
		
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100"  ></input> 
			</div>  

			
			
		
			</div>
			

</form>

</body>
</html>
