
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  

<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<s:set value="responseJSON" var="respData"/> 
<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js" type="text/javascript"></script>

<script type="text/javascript" >


function redirectfun()
{
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/productfeesettings.action';
	$("#form1").submit();
	return true; 
	}
	
	
$(function () { 
	 $('#btn-submit').live('click', function () { 
	    
				var url="${pageContext.request.contextPath}/<%=appName %>/feesettingsmodifyack.action"; 
				$("#form1")[0].action=url;
				$("#form1").submit(); 
				
    }); 
});







</script>

</head>

<body>	
<form name="form1" id="form1" method="post" >
		<div id="content" class="span10"> 
		    <div>
					<ul class="breadcrumb">
					  <li><a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li><a href="binManagementAct.action">Fee Settings</a> <span class="divider"> &gt;&gt; </span></li>
					  <!-- <li><a href="#">Setting</a></li> -->
					</ul>
			</div>
			       <table height="3">
						<tr>
							<td colspan="3">
								<div class="messages" id="messages"><s:actionmessage /></div>
								<div class="errors" id="errors"><s:actionerror /></div>
							</td>
						</tr>
					</table>
			
	
			<div class="row-fluid sortable"><!--/span--> 	
				
				<div class="box span12">  
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Fee Management
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>   
						
					<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						
						<div><strong>successfully Updated, proceed authorization</strong></div>
					</fieldset>
				
					<input name="SuperAgent" id="SuperAgent"  type="hidden" maxlength ='25'  required="true"  class="field"  value="<s:property value='#respData.Feecodedetails.SUPERAGENT' />" /><span id="bin_err1" class="errmsg"></span>
					
						</div> 		
					</div>
					
					<input type="hidden" value="<s:property value='#respData.Feecodedetails.SEQ_NO' />" name="seqno" id="seqno" />
			  
			  <div class="form-actions" align="left"> 
				
				<a class="btn btn-min btn-success" href="#" onClick="redirectfun()">Next</a></input> 
			</div>  
					
					
					</div> 		
		
		
			</div> 	

	
</form>
</body>
</html>
