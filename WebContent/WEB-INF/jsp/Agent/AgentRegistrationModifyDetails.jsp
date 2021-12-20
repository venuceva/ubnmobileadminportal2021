<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
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
 			
 			branchcode : { required : true } ,
 			mnumber : { required : true,number: true} 
 		},		
 		messages : {
 			
 		
 
			branchcode : { 
		 		required : "Please Select Branch Name.",
					},
			mnumber : { 
		 		required : "Please Enter Mobile Number.",
		 		number : "Mobile Number, can not allow character or special characters."
 					}
 			
 		} 
 	};
	
		
			$(function () { 
				$('#branchcode').val("${responseJSON.branchcode}").prop('selected', true);
				$("#form1").validate(userictadminrules); 
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
				

		        $('#btn-submit').live('click', function () { 
		        	
		    		if($('#oldno').val()==$('#mnumber').val() && $('#oldbranch').val()==$('#branchcode').val()){
		    			$('#errormsg').text("Please Change Mobile Number or Branch Name");	
		    			return false;
		    		}
		    		
		    		var queryString1 = "entity=${loginEntity}&method=searchAgent&link=AUTHPEND&fname="+$('#userid').val()+"&mnumber=DSA";
	        		
        			$.getJSON("postJson.action", queryString1,function(data) { 
        				
        				if(data.finalCount==0){
		    		
		    		var queryString = "entity=${loginEntity}&method=validatemobileno&mnumber=234"+$('#mnumber').val();
	        		
        			$.getJSON("postJson.action", queryString,function(data) { 
        				if(data.finalCount==0){
		    		
		    				var url="${pageContext.request.contextPath}/<%=appName %>/agentmodifydetailsconf.action"; 
		    				$("#form1")[0].action=url;
		    				$("#form1").submit(); 
        				}else{ 
        					if($('#oldno').val()==$('#mnumber').val() ){
        						var url="${pageContext.request.contextPath}/<%=appName %>/agentmodifydetailsconf.action"; 
    		    				$("#form1")[0].action=url;
    		    				$("#form1").submit(); 
        		    		}else{
        						$('#errormsg').text("Mobile Number already Exit, Please Change Mobile Number");	
        		    		}
        				 }
        			}); 
		    			
		    			 
        				}else{
        					$('#errormsg').text("This User id Authorization Pending ");
        				}
        			});
		        }); 
				 
			      
		    });  

		
			
			function redirectHome(){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
				$("#form1").submit();
				return true;
			}

				
</script>
<s:set value="responseJSON" var="respData" />
</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">DSA Registration Modify</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						DSA Registration Details
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
									<input type="hidden"    id="jtitle" name="jtitle" style="width: 200px;" value="${responseJSON.jtitle}" /></td>
									<td width="25%"><label for="mnumber"><strong>Mobile Number<font color="red">*</font></strong></label></td>
									<td width="25%">+234
									<input name="mnumber" autocomplete="off" type="text" class="field" maxlength="10" minlength="10" id="mnumber"  required=true value="${responseJSON.mnumber}" />
									
									</td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="mid"><strong>Mail Id</strong></label></td>
									<td width="25%">${responseJSON.mailid}
									<input name="mailid" autocomplete="off" type="hidden" class="field" id="mailid"  value="${responseJSON.mailid}"  /></td>
									<td width="25%"><label for="gender"><strong>Branch Name <font color="red">*</font></strong></label></td>
									<td width="25%">
									<s:select cssClass="chosen-select" headerKey=""
												headerValue="Select" list="#respData.BRANCH_CODE"
												name="branchcode" id="branchcode" requiredLabel="true"
												theme="simple" data-placeholder="Choose Limit Code..."
												required="true" /> 
								
									</td>
								</tr>
								<tr class="odd">
							
									<td width="25%"><label for="gender"><strong>Product Code </strong></label></td>
									<td width="25%">${responseJSON.limitcode}
									<input name="limitcode" autocomplete="off" type="hidden" class="field" id="limitcode"  value="${responseJSON.limitcode}"  /></td>
									<td width="25%"><label for="gender"><strong>Product Description </strong></label></td>
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
							
							
						<input type="hidden" id="oldno" name="oldno" value="${responseJSON.mnumber}" />
						<input type="hidden" id="oldbranch" name="oldbranch" value="${responseJSON.branchcode}" />
							
	
						</fieldset>
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
