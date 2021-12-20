
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


<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js" type="text/javascript"></script>

<script type="text/javascript" >






	
$(function() {
	
	
		
	
		
		$('#btn-submit').live('click', function () {
		
		
			
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/loyalitypointsettings.action';
			$("#form1").submit();
			return true;
			
		});
		
		
		$('#btn-cancel').live('click', function () {
			
			
			
			
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
			$("#form1").submit();
			return true;
			
		});
 	
	});


</script>

<s:set value="responseJSON" var="respData"/> 
</head>

<body>	
	
		<div id="content" class="span10"> 
		    <div>
					<ul class="breadcrumb">
					  <li><a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li><a href="loyalitypointsettings.action">Loyalty Management</a> <span class="divider"> &gt;&gt; </span></li>
					   <li><a href="#">Loyalty Point Setting Modify Acknowledgement</a></li>
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
	<form name="form1" id="form1" method="post">
	
				
			
				<div class="box span12"> 
				
				
				
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Loyalty Point Setting
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>   
						
					<div class="box-content" id="terminalDetails"> 
					
					
					 <div><strong>Loyalty Setting Successfully Created,Authorized Pending</strong></div>
						
					
					</div> 		
		</div>
		
		<a  class="btn btn-success" href="#" id="btn-submit" >Next</a>
		&nbsp; <a  class="btn btn-danger" href="#"  id="btn-cancel" >Home</a>
			
		</form>
				
				
		<!-- Fee Settings End -->
				
						
	
	
			

	</div>			
	
	</div>
	
	
 
 
 
</body>
</html>
