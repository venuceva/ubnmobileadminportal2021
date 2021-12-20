
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
	function getGenerateLimitScreen(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/cashDepositLimitAuthMgmt.action';
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
				  <li> <a href="#">Home</a> <span class="divider">&gt;&gt; </span> </li>
				  <li><a href="#">Approve/Reject Store Cash Deposit Limit </a></li>
				</ul>
			</div>
			<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12">
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Approve/Reject Store Cash Deposit Limit Success 
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

							</div>
						</div> 
						<div id="primaryDetails" class="box-content" >
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable " >
								 <tr>
									<td></td>
									<td >Limit Amount of kshs ${responseJSON.storeLimit} /= successfully  ${responseJSON.approveReject} to Store ${responseJSON.storeId} of Merchant ${responseJSON.merchantID}.</td>
									<td ></td>	
								</tr> 
							</table>
						</div>  
				</div> 
			</div>
				<div class="form-actions">
					<a  class="btn btn-danger" href="#" onClick="getGenerateLimitScreen()">Next</a> &nbsp;&nbsp;
				</div> 
	</div> 
</form>
</body>
</html>
