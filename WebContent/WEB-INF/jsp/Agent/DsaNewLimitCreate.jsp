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
 			limitcode : { required : true,regex: /^[a-zA-Z0-9]+$/ } ,
 			limitdesc : { required : true } ,
 			damtlmt : { required : true,regex: /^[0-9]+$/ } ,
 			numoftran : { required : true,regex: /^[0-9]+$/ } ,
 			ptamt : { required : true,regex: /^[0-9]+$/ } ,
 		},		
 		messages : {
 			limitcode : { 
 						required : "Please Enter Limit Code.",
						regex : "Limit Code, can not special characters."
					},
			limitdesc : { 
		 				required : "Please Enter Description.",
 						},
 		
 			damtlmt : { 
		 				required : "Please Enter Daily Amount Limit.",
						regex : "Daily Amount Limit, can not allow character or special characters."
 						},
 			numoftran : { 
 			 			required : "Please Enter Daily Number of Transaction.",
 						regex : "Daily Number of Transaction, can not allow character or special characters."
 	 					},
 	 		ptamt : { 
 	 			 		required : "Please Enter Per Transaction Amount.",
 	 					regex : "Per Transaction Amount, can not allow character or special characters."
 	 	 				}
 			
 		} 
 	};

			
			$(function () { 
				
				$( "#limitcode" ).keyup(function() {
					
					$( "#limitcode" ).val((this.value).toUpperCase());
					});
			
				  $("#form1").validate(userictadminrules); 
				
		        $('#btn-submit').live('click', function () { 
		        	
		        	var queryString = "entity=${loginEntity}&method=searchAgent&link=LIMITAP&fname="+$('#limitcode').val()+"&mnumber="+$('#limitcode').val();
	        		
        			$.getJSON("postJson.action", queryString,function(data) { 
        				if(data.finalCount==0){
        					var queryString1 = "entity=${loginEntity}&method=searchAgent&link=LIMIT&fname="+$('#limitcode').val()+"&mnumber="+$('#limitcode').val();
        	        		
                			$.getJSON("postJson.action", queryString1,function(data) { 
                				if(data.finalCount==0){
		        					var url="${pageContext.request.contextPath}/<%=appName %>/dsalimitnewcreationconf.action"; 
				    				$("#form1")[0].action=url;
				    				$("#form1").submit();
		    						return true;
                				}else{ 
                			    	  
                					$('#errormsg').text("Limit Code Already Exit");

                				 }
                			}); 
        				}else{ 
		    	  
        					$('#errormsg').text("Limit Code Already Exit");

        				 }
        			}); 
		        }); 
		    });  

			$.validator.addMethod("regex", function(value, element, regexpr) {          
				 return regexpr.test(value);
			   }, ""); 
			
			

			
function calcfun(){
				
				
				if(!$("#damtlmt").val()=="" && !$("#numoftran").val()==""){
					
					$("#ptamt").val($("#damtlmt").val()/$("#numoftran").val())
					
				}
				
			}
			
function redirectHome(){
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
					 <li> <a href="dsalimitmng.action">Account Set Limit</a> </li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						New Limit Creation Details
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="mid"><strong>Limit Code<font color="red">*</font></strong></label></td>
									<td width="25%"><input name="limitcode" maxlength="6" minlength="6" autocomplete="off" type="text" class="field" id="limitcode"  value="${responseJSON.limitcode}"  /></td>
									<td width="25%"><label for="gender"><strong>Description<font color="red">*</font></strong></label></td>
									<td width="25%"><input name="limitdesc" autocomplete="off" type="text" class="field" id="limitdesc" value="${responseJSON.limitdesc}" />
									</td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="mid"><strong>Daily Amount Limit<font color="red">*</font></strong></label></td>
									<td width="25%"><input name="damtlmt" autocomplete="off" type="text" class="field" id="damtlmt"  value="${responseJSON.damtlmt}" onblur="calcfun()" /></td>
									<td width="25%"><label for="gender"><strong>Daily Number of Transaction <font color="red">*</font></strong></label></td>
									<td width="25%"><input name="numoftran" autocomplete="off" type="text" class="field" id="numoftran" value="${responseJSON.numoftran}" onblur="calcfun()" />
									</td>
								</tr>
								<tr class="odd">
									<td width="25%"><label for="mid"><strong>Per Transaction Amount<font color="red">*</font></strong></label></td>
									<td width="25%"><input name="ptamt" autocomplete="off" type="text" class="field" id="ptamt"  value="${responseJSON.ptamt}"  readonly/></td>
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
