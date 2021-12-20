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
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

  
 <script type="text/javascript" > 
 function getAnnouneManagementScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/announceMgmtAct.action';
	$("#form1").submit();
	return true;
}
</script>
<style type="text/css">
label.error {
  font-weight: bold;
  color: red;
  padding: 2px 8px;
  margin-top: 2px;
}
.errmsg {
color: red;
}
.errors {
color: red;
}
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
  font-weight: bold;
  color: red;
  padding: 2px 8px;
  margin-top: 2px;
}
</style> 
</head> 
<body>
<form name="form1" id="form1" method="post" action="">	
	<div id="content" class="span10">  
			<div>
				<ul class="breadcrumb">
				  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="#">Announcement</a>  </li>
				</ul>
			</div>
			<div class="row-fluid sortable"><!--/span-->
				<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Information
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						</div>
					</div>  
					<div class="box-content"  id="userDetails">
						 <fieldset>
							 <table width="950" border="0" cellpadding="5" cellspacing="1"  
									class="table table-striped table-bordered bootstrap-datatable">
								<tr class="even">
									<td width="20%"> <strong><label for="Reference No">Reference No</label></strong></td> 
									<td >${responseJSON.TXN_REF_NO}</td>
								</tr> 
								<tr class="even">
									<td> <strong><label for="Reference No">Level of Setting</label></strong></td> 
									<td >${responseJSON.TYPE_OF_SETTING}</td>
								</tr> 
								<tr class="even">
									<td> <strong><label for="Reference No">ID</label></strong></td> 
									<td >${responseJSON.ID}</td>
								</tr> 
								<tr class="even">
									<td> <strong><label for="Reference No">Message</label></strong></td> 
									<td >${responseJSON.MESSAGE}</td>
								</tr> 
								<tr class="even">
									<td> <strong><label for="Reference No">Status</label></strong></td> 
									<td >${responseJSON.STATUS}</td>
								</tr> 
								
								</table>
						</fieldset> 
					</div>
				</div>
			</div> 
			<div class="form-action">
				<input type="button" name="btn-back" id="btn-back" class="btn btn-danger"  value="Next" onClick="getAnnouneManagementScreen()"/> &nbsp;&nbsp;
			</div>	 
		</div>
</form>
</body>
</html>
