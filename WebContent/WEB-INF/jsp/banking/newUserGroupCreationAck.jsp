
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Ceva</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="Another one from the CodeVinci">
	<meta name="author" content="Team">
	<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
	<%String ctxstr = request.getContextPath(); %>
	<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
	 
	<SCRIPT type="text/javascript"> 

		$(document).ready(function(){ 
			//$('#treeDemo > li').attr('disabled','disabled');
			$('#btn-submit').live('click',function() {  
				  
				$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/userGrpCreation.action";
				$("#form1").submit();				
			});
			 
		});
		//--> 
	</SCRIPT>
    
 
 
		
</head>

<body>
	<form name="form1" id="form1" method="post">
	 
			<div id="content" class="span10">
 
			
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript> 
             			<!-- content starts -->
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">User Management</a> <span class="divider"> &gt;&gt; </span></li>
 					  <li><a href="#">New Group Creation Acknowledge </a></li>
					</ul>
				</div>  
		<div class="row-fluid sortable">

			<div class="box span12">

				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Group Definition Acknowledge
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i
							class="icon-cog"></i></a> <a href="#"
							class="btn btn-minimize btn-round"><i
							class="icon-chevron-up"></i></a>

					</div>
				</div>
						
		<div class="box-content">
		 <fieldset> 
				<table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable "> 
						 <tr>
							<td></td>
							<td >'${groupID }' Group Creation Done Successfully. To get the group details, please click on next to process. </td>
							<td ></td>	
						</tr>
				</table>  
		</fieldset> 
		
		 <div class="form-actions">
				<input type="button" name="btn-submit" class="btn btn-primary" id="btn-submit" value="Next" />
 				<!-- <button class="btn">Cancel</button>  -->
				<input type="hidden" name="jsonVal"  id="jsonVal" value='${jsonVal}' />
				<input type="hidden" name="keyVal"  id="keyVal" value="" />
				<input type="hidden" name="userRights"  id="userRights" value="" />
			  </div>  
		</div>
		</div>
		</div> 
                 
              <!--/row--><!--/row-->
              
              						<!-- content ends -->
			</div><!--/#content.span10-->
		 
 </form>
</body>
</html>

