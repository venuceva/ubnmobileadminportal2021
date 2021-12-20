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
	
var createlmtRules = {
	      rules : {
		   
	    	  custaddress 			: { required : true},
	    	  agentname 			: { required : true},
			
		
	   },  
	   messages : {
			  
		   custaddress : { 
		         required : "Please Enter Customer Address.",
		       },  
		       agentname : {
				 required :"Please Enter Agent Name.",
			   }
}
};

$.validator.addMethod("regex", function(value, element, regexpr) {          
	 return regexpr.test(value);
}, ""); 
			
			$(function () { 
				 $('#btn-submit').live('click', function () { 
				$("#form1").validate(createlmtRules);
				
				if($("#form1").valid()) {
				
			       
			    		
			    				var url="${pageContext.request.contextPath}/<%=appName %>/debitcardrequestconfirm.action"; 
			    				$("#form1")[0].action=url;
			    				$("#form1").submit(); 
			       		
				} 
		    });  

			 });	
			
			function redirectHome(){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
				$("#form1").submit();
				return true;
			}
			
function funccc(v){
				$("#callerstatus").val(v);
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
					 <li> <a href="#">Debit Card Requests</a> </li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						Debit Card Requests ${responseJSON.FINAL_STATUS} Details
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr class="odd">
									<td width="25%"><label for="File"><strong>Request Id</strong></label></td>
									<td width="25%">${responseJSON.REQUEST_ID}
									<input type="hidden" name="requestid" id="requestid" value="${responseJSON.REQUEST_ID}"/>
									</td>
								
									<td width="25%"><label for="Client"><strong>Customer Name</strong></label></td> 
									<td width="25%">${responseJSON.CUSTOMER_NAME}
									<input type="hidden" name="customername" id="customername" value="${responseJSON.CUSTOMER_NAME}"/>
									</td>
									
									</tr>
								<tr class="even">
									<td width="25%"><label for="File"><strong>Account Number</strong></label></td>
									<td width="25%">${responseJSON.ACCOUNT_NUMBER}
									<input type="hidden" name="accountnumber" id="accountnumber" value="${responseJSON.ACCOUNT_NUMBER}"/>
									</td>
								
									<td width="25%"><label for="Client"><strong>Phone Number</strong></label></td> 
									<td width="25%">${responseJSON.PHONE_NUMBER}
									<input type="hidden" name="phonenumber" id="phonenumber" value="${responseJSON.PHONE_NUMBER}"/>
									</td>
									
									</tr>
									<tr class="even">
									<td width="25%"><label for="File"><strong>Card Type</strong></label></td>
									<td width="25%">${responseJSON.CARD_TYPE}
									<input type="hidden" name="cardtype" id="cardtype" value="${responseJSON.CARD_TYPE}"/>
									</td>
								
									<td width="25%"><label for="Client"><strong>Date Of Request</strong></label></td> 
									<td width="25%">${responseJSON.DATE_OF_REQUEST}
									<input type="hidden" name="dateofrequest" id="dateofrequest" value="${responseJSON.DATE_OF_REQUEST}"/>
									</td>
									
									</tr>	
									<tr class="even">
									<td width="25%"><label for="File"><strong>Status</strong></label></td>
									<td width="25%">${responseJSON.FINAL_STATUS}</td>
									<td width="25%"></td>
									<td width="25%"></td>
									</tr>
									</table>
									
									<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
									<tr>
									<td width="25%"><label for="File"><strong>Customer Called?</strong></label></td>
									<td width="25%" colspan="3">
									<input type="radio" name="custcalled" id="YES"	 onclick=funccc("YES") checked/><strong>YES </strong> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
									<input type="radio" name="custcalled" id="NO"	 onclick=funccc("NO") /> <strong> NO </strong>
									<input type="hidden" name="callerstatus" id="callerstatus" value="YES"/>
									</td>
								
									
									</tr>
										
									<tr class="even">
									<td width="25%"><label for="File"><strong>Customer Address<font color="red">*</font></strong></label></td>
									<td width="25%" colspan="3">
									<input type="text" name="custaddress" id="custaddress" style="width:400px"/>
									</td>
								
									
									</tr>
									<tr class="even">
									<td width="25%"><label for="File"><strong>Agent Name</strong></label></td>
									<td width="25%" colspan="3">
									<input type="text" name="agentname" id="agentname" value="${responseJSON.makerid}" readonly/>
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
