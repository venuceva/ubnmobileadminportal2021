
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

  
<script type="text/javascript" >
 
function getPage(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/webLogin.action';
	$("#form1").submit();
	return true;
} 

</script>
	 
</head>

<body>
	<form name="form1" id="form1" method="post" action="">
 	 <div id="content" class="span10"> 
			  <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">User Locking System - Key</a>  </li> 
					 
					</ul>
				</div>  
	 
			 <div class="row-fluid sortable"> 
				<div class="box span12"> 
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>User Details
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

							</div>
						</div>  
					<div class="box-content">
						<fieldset> 
							 <table width="950" border="0" cellpadding="5" cellspacing="1" class="table">
									<tr class="odd">
										<td ><strong> User '${userId}', USLIDN Key is ${ulid}. </strong></td>
										<td > &nbsp;</td>
									</tr>
							</table>
			    		</fieldset>
					</div>  
				</div> 
			</div> 
        <div class="form-actions">
		   <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" onClick="getPage()" value="Next" width="100"  ></input> 
			&nbsp;<input type="button" class="btn" type="text"  name="btn-cancel" id="btn-cancel" value="Cancel" width="100" ></input> 
		</div>      
	</div><!--/#content.span10--> 
</form>
</body>
</html>
