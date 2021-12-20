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
		    	  
		    				var url="${pageContext.request.contextPath}/<%=appName %>/dsalimitmng.action"; 
		    				$("#form1")[0].action=url;
		    				$("#form1").submit();
		    	    		
		        }); 
		    });  

		
			
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
						New Limit Creation Acknowledgment
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="mid"><strong>Limit Code</strong></label></td>
									<td width="25%">${responseJSON.limitcode}
									<input name="limitcode" type="hidden" class="field" id="limitcode"  value="${responseJSON.limitcode}"  /></td>
									<td width="25%"><label for="gender"><strong>Description</strong></label></td>
									<td width="25%">${responseJSON.limitdesc}
									<input name="limitdesc"  type="hidden" class="field" id="limitdesc" value="${responseJSON.limitdesc}" />
									</td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="mid"><strong>Daily Amount Limit</strong></label></td>
									<td width="25%">${responseJSON.damtlmt}
									<input name="damtlmt" autocomplete="off" type="hidden" class="field" id="damtlmt"  value="${responseJSON.damtlmt}"  /></td>
									<td width="25%"><label for="gender"><strong>Daily Number of Transaction</strong></label></td>
									<td width="25%">${responseJSON.numoftran}
									<input name="numoftran" autocomplete="off" type="hidden" class="field" id="numoftran" value="${responseJSON.numoftran}" />
									</td>
								</tr>
								<tr class="odd">
									<td width="25%"><label for="mid"><strong>Per Transaction Amount</strong></label></td>
									<td width="25%">${responseJSON.ptamt}
									<input name="ptamt" autocomplete="off" type="hidden" class="field" id="ptamt"  value="${responseJSON.ptamt}"  /></td>
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
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Next" width="100"  ></input> 
			</div>  

			
			
		
			</div>
			

</form>

</body>
</html>
