<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
 <%@taglib uri="/struts-tags" prefix="s"%> 
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<s:set value="responseJSON" var="respData"/>
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
</style> 
<script type="text/javascript" > 

$('#btn-submit').live('click', function () { 

	  $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/InsertTerminalMigrationDetails.action';
		$("#form1").submit(); 
		return true;
	});
	
	
$('#btn-back').live('click', function () { 

	  $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/terminalMigration.action';
		$("#form1").submit(); 
		return true;
	});

</script>

</head> 
<body>
	<form name="form1" id="form1" method="post" action=""> 
			<div id="content" class="span10"> 
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="#">Merchant  Management</a> <span class="divider"> &gt;&gt; </span></li>
				  <li><a href="#">Terminal Migration Confirm</a></li>
				</ul>
			</div>
			<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i> Current Terminal Details
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

							</div>
						</div>

						<div id="primaryDetails" class="box-content">
							<fieldset>   
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd">
										<td width="20%"><strong><label for="Merchant ID">Merchant ID<font color="red">*</font></label></strong></td>
										<td> ${merchantID}
										<input name="merchantID" type="hidden" id="merchantID" class="field"  maxlength="15" value='${merchantID}' >
										</td>
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Store ID">Store ID<font color="red">*</font></label></strong></td>
										<td> ${storeID}
										<input name="storeID" type="hidden" id="storeID" class="field"  maxlength="15" value='${storeID}' >
										</td>
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Terminal ID">Terminal ID<font color="red">*</font></label></strong></td>
										<td> ${terminalID}
										<input name="terminalID" type="hidden" id="terminalID" class="field"  maxlength="15" value='${terminalID}' >
										</td>
									</tr>
									
									
									 
								</table>
							 </fieldset> 
							</div>
							 
						</div>
					</div> <!-- end of span -->
					<!--/end span--> 
           
           
           <div class="row-fluid sortable" ><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Migrating To 
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

							</div>
						</div>

						<div id="primaryDetails" class="box-content">
							<fieldset>   
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd">
										<td width="20%"><strong><label for="Merchant ID">Merchant ID<font color="red">*</font></label></strong></td>
										<td> ${updatemerchantID}
										<input name="updatemerchantID" type="hidden" id="updatemerchantID" class="field"  maxlength="15" value='${updatemerchantID}' >
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Store ID">Store ID<font color="red">*</font></label></strong></td>
										<td> ${updatestoreID}
										<input name="updatestoreID" type="hidden" id="updatestoreID" class="field"  maxlength="15" value='${updatestoreID}' >
										</td>	
									</tr>
									
									
									 
								</table>
							 </fieldset> 
							</div>
							 
						</div>
					</div> <!-- end of span -->
				 
			<span id="multi-row-data" name="multi-row-data" class="multi-row-data" style="display:none" ></span>
			<div class="form-actions"> 
				
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" />&nbsp;
				<input type="button" class="btn btn-danger" name="btn-back" id="btn-back" value="Back" /> 
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div> 
	</div> 
</form>
</body>
</html>
