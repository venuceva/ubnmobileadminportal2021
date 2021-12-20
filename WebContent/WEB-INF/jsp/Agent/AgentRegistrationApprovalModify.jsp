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
 			
 			Narration : { required : true } ,
 		},		
 		messages : {
 			
 		
 			Narration : { 
 							required : "Please Enter Message.",
 						}
 			
 		} 
 	};

			
			$(function () { 
				
				
				if($('#mnumber').val()!=$('#updatemobileno').val()){
					$('#updatemobileno1').html("<div class='label label-success'>"+$('#updatemobileno').val()+"</div>");
	    		}
				
				if($('#branchcode').val()!=$('#updatebranchdetails').val()){
					$('#updatebranchdetails1').html("<div class='label label-success'>"+$('#updatebranchdetails').val()+"</div>");
	    		}
				
				
				if("${responseJSON.decision}"=="Reject"){
					$("#decision").val("Reject");
					jQuery("#select2").attr('checked', true);
				}else{
					$("#decision").val("Approval");
					jQuery("#select1").attr('checked', true);
				}
			
			
				  $("#form1").validate(userictadminrules); 
				
		        $('#btn-submit').live('click', function () { 
		    	  
		    				var url="${pageContext.request.contextPath}/<%=appName %>/agentregapprovalmodifyconf.action"; 
		    				$("#form1")[0].action=url;
		    				$("#form1").submit();
		    	    		
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
					<li> <a href="ApprovalAll.action">Authorization</a> </li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						DSA Approval Details
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="userid"><strong>User ID</strong></label></td>
									<td width="25%">${responseJSON.userid}
									<input name="userid" autocomplete="off" type="hidden" class="field" id="userid"  value="${responseJSON.userid}"  /></td>
									<td width="25%"><label for="empno"><strong>Employee No</strong></label></td>
									<td width="25%">${responseJSON.empno}
									<input name="empno" autocomplete="off" type="hidden" class="field" id="empno"  value="${responseJSON.empno}" /></td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="fname"><strong>First Name</strong></label></td>
									<td width="25%">${responseJSON.fname}
									<input name="fname" autocomplete="off" type="hidden" class="field" id="fname"  value="${responseJSON.fname}"  /></td>
									<td width="25%"><label for="lname"><strong>Last Name</strong></label></td>
									<td width="25%">${responseJSON.lname}
									<input name="lname" autocomplete="off" type="hidden" class="field" id="lname"  value="${responseJSON.lname}" /></td>
								</tr>
								
								<tr class="odd">
									<td width="25%"><label for="dob"><strong>Job Title</strong></label></td>
									<td width="25%">${responseJSON.jtitle}
									<input type="hidden"   id="jtitle" name="jtitle" style="width: 200px;" value="${responseJSON.jtitle}" /></td>
									<td width="25%"><label for="mnumber"><strong>Mobile Number</strong></label></td>
									<td width="25%">${responseJSON.mnumber}
									<input name="mnumber" autocomplete="off" type="hidden" class="field" id="mnumber"  required=true value="${responseJSON.mnumber}" /></td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="mid"><strong>Mail Id</strong></label></td>
									<td width="25%">${responseJSON.mailid}
									<input name="mailid" autocomplete="off" type="hidden" class="field" id="mailid"  value="${responseJSON.mailid}"  /></td>
									<td width="25%"><label for="gender"><strong>Branch Code - Branch Name </strong></label></td>
									<td width="25%">${responseJSON.branchcode}
  									<input name="branchcode" autocomplete="off" type="hidden" class="field" id="branchcode"  value="${responseJSON.branchcode}" />
									</td>
								</tr>
								
								<tr class="odd">
								<td width="25%"><label for="mid"><strong>Product Code</strong></label></td>
									<td width="25%">${responseJSON.limitcode}
									<input name="limitcode" autocomplete="off" type="hidden" class="field" id="limitcode"  value="${responseJSON.limitcode}"  /></td>
									<td width="25%"><label for="mid"><strong>Product Description</strong></label></td>
									<td width="25%">${responseJSON.damtlmt}
									<input name="damtlmt" autocomplete="off" type="hidden" class="field" id="damtlmt"  value="${responseJSON.damtlmt}"  /></td>
									
								</tr>
								<tr class="even">
								<td width="25%"><label for="gender"><strong>Limit Code </strong></label></td>
									<td width="25%">${responseJSON.numoftran}
									<input name="numoftran" autocomplete="off" type="hidden" class="field" id="numoftran"  value="${responseJSON.numoftran}" />
									</td>
									<td width="25%"><label for="mid"><strong>Fee Code</strong></label></td>
									<td width="25%">${responseJSON.ptamt}
									<input name="ptamt" autocomplete="off" type="hidden" class="field" id="ptamt"  value="${responseJSON.ptamt}"  /></td>
									
								</tr>
								</table>
								<table>
								<tr >
									<td colspan="4" class='label label-warning' ><strong>Changed Fields </strong></td>
								</tr>
								</table>
								<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details1" >
								<tr class="odd">
								<td width="25%"><label for="mid"><strong>Mobile Number</strong></label></td>
									<td width="25%" id="updatemobileno1"></td>
									<td width="25%"><label for="mid"><strong>Branch Code - Branch Name</strong></label></td>
									<td width="25%" id="updatebranchdetails1"></td>
									
								</tr>
								
								
								<tr class="odd" >
									<td colspan="4" ><strong>Decision<font color="red">*</font></strong>&nbsp;&nbsp;
									<input type='radio' onclick='decisionradio(this.value)' id='select1' name='select' value='Approval' checked/><strong>Approval</strong>&nbsp;&nbsp;
									<input type='radio' onclick='decisionradio(this.value)' id='select2' name='select' value='Reject'/><strong>Reject</strong>&nbsp;&nbsp;
									</td>
									
								</tr>
								
								<tr class="even" >
									<td colspan="4" ><div><strong>Message<font color="red">*</font></strong></label></div>
									<textarea rows="10" cols="100" name="Narration" class="field" id="Narration" style="width:500px"  required=true >${responseJSON.Narration}</textarea></td>
									
								</tr>
															
							</table>
							
							
						
							
	
						</fieldset>
						<input name="updatemobileno" autocomplete="off" type="hidden" class="field" id="updatemobileno"  value="${responseJSON.updatemobileno}"  />
						<input name="updatebranchdetails" autocomplete="off" type="hidden" class="field" id="updatebranchdetails"  value="${responseJSON.updatebranchdetails}"  />
						<input type="hidden" id="decision" name="decision" value="Approval" />
						<input type="hidden" id="refno" name="refno" value="${responseJSON.refno}" />
						<input type="hidden" id="displayname" name="displayname" value="${responseJSON.displayname}" />
							
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
