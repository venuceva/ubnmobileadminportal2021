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
	

var list = "dob".split(",");
var datepickerOptions = {
				showTime: true,
				showHour: true,
				showMinute: true,
	  		dateFormat:'dd/mm/yy',
	  		alwaysSetTime: false,
	  		yearRange: '2000:2050',
			changeMonth: true,
			changeYear: true
};

				   
				   var userictadminrules = {
				    		
				    		rules : {
				    			fname : { required : true,regex: /^[a-zA-Z]+$/} ,
				    			lname : { required : true,regex: /^[a-zA-Z]+$/} ,
				    			dob : { required : true } ,
				    			mnumber : { required : true,regex: /^[0-9]+$/} , 
				    			mailid : { required : true,regex: /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/} ,
				    			offaddress : { required : true} , 
				    			peraddress : { required : true} ,
				    		},		
				    		messages : {
				    			
				    						fname : { 
				    							required : "Please Enter First Name.",
				    							regex : "First Name, can not allow Number or special characters."
				    						},
				    						lname : { 
				    							required : "Please Enter First Name.",
				    							regex : "First Name, can not allow Number or special characters."
				    						},
				    			
				    						dob : { 
				    							required : "Please Select Date Of Birth.",
				    						},
				    						mnumber : { 
				    							required : "Please Enter Mobile Number.",
				    							regex : "Mobile Number, can not allow characters or special characters."
				    						},
				    						mailid : { 
				    							required : "Please Enter Mail Id.",
				    							regex : "Mail Id, can not allow this format example : abcdef@xyz.com."
				    						},
				    						offaddress : { 
				    							required : "Please Enter Office Address.",
				    						},
				    						peraddress : { 
				    							required : "Please Enter Permanent Address.",
				    						}
				    			
				    		} 
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
			$('#gen').live('click', function () { 
				$('#gender').val($(this).val());
			 }); 
			
			if($('#gender').val()==""){
				$('#gender').val("Male");
			}else{
				$("input[name=gen][value="+$('#gender').val()+"]").prop("checked",true);
			} 
			
			
				
		        $('#btn-submit').live('click', function () { 
		    	    $("#form1").validate(userictadminrules); 
		    	    var queryString = "entity=${loginEntity}&method=validMobileno&mobileno="+$('#mnumber').val()+"&refno=REG";
	        		
        			$.getJSON("postJson.action", queryString,function(data) { 
        				if(data.finalCount==0){
        					var url="${pageContext.request.contextPath}/<%=appName %>/agentregistrationconf.action"; 
		    				$("#form1")[0].action=url;
		    				$("#form1").submit(); 
        				}else{
        					$('#errormsg').text("This Mobile Number already registrated.");

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

				
</script>

</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="agentregistration.action">Agent Registration</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						Agent Registration
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="fname"><strong>First Name<font color="red">*</font></strong></label></td>
									<td width="25%"><input name="fname" autocomplete="off" type="text" class="field" id="fname"  required=true  value="${responseJSON.fname}" /></td>
									<td width="25%"><label for="lname"><strong>Last Name<font color="red">*</font></strong></label></td>
									<td width="25%"><input name="lname" autocomplete="off" type="text" class="field" id="lname"  required=true  value="${responseJSON.lname}" /></td>
								</tr>
								
								<tr class="even">
									<td width="25%"><label for="dob"><strong>Date Of Birth<font color="red">*</font></strong></label></td>
									<td width="25%"><input type="text"   class="fromDate" id="dob" name="dob" style="width: 200px;" required=true  value="${responseJSON.dob}" readonly /></td>
									<td width="25%"><label for="mnumber"><strong>Mobile Number<font color="red">*</font></strong></label></td>
									<td width="25%">+234<input name="mnumber" autocomplete="off" type="text" class="field" id="mnumber"  required=true value="${responseJSON.mnumber}" /></td>
								</tr>
								<tr class="odd">
									<td width="25%"><label for="mid"><strong>Mail Id<font color="red">*</font></strong></label></td>
									<td width="25%"><input name="mailid" autocomplete="off" type="text" class="field" id="mailid"  required=true value="${responseJSON.mailid}" /></td>
									<td width="25%"><label for="gender"><strong>Gender<font color="red">*</font></strong></label></td>
									<td width="25%">
									<input type="radio" name="gen" id="gen" value="Male" checked> Male
  									<input type="radio" name="gen" id="gen" value="Female"> Female
  									<input type="radio" name="gen" id="gen" value="Other"> Other
  									<input name="gender" autocomplete="off" type="hidden" class="field" id="gender"  required=true  value="${responseJSON.gender}" />
									</td>
								</tr>
								
								<tr class="even">
									<td width="25%"  ><label for="officeaddress"><strong>Office Address<font color="red">*</font></strong></label></td>
									<td width="75%" colspan="3" >
									
									<textarea rows="4" cols="50" name="offaddress" id="offaddress">${responseJSON.offaddress}</textarea>
									</td>
								</tr>
								<tr class="odd">
									<td width="25%"  ><label for="permanentaddress"><strong>Permanent Address<font color="red">*</font></strong></label></td>
									<td width="75%" colspan="3" >
									<textarea rows="4" cols="50" name="peraddress" id="peraddress">${responseJSON.peraddress}</textarea>
									</td>
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
