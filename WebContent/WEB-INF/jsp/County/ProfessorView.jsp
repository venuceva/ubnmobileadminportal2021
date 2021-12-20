 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Create Entity</title>
 
 
<script type="text/javascript">

    $('#btn-back').live('click', function () {  
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/studentViewAct.action';
			$("#form1").submit();
			return true; 
    }); 
	
	$('#btn-submit').live('click', function () { 
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/studentViewAct.action';
		$("#form1").submit();
		return true; 
	});
	
</script>

<style type="text/css">
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
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
 
</style>  
</head>
<body>
<form name="form1" id="form1" method="post">  
	<div id="content" class="span10"> 
 		<div> 
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="#">Professor View</a> </li> 
			</ul>
		</div>  
		<table height="3">
				<tr>
					<td colspan="3">
						<div class="messages" id="messages">
							<s:actionmessage />
						</div>
						<div class="errors" id="errors">
							<s:actionerror />
						</div>
					</td>
				</tr>
			</table> 
		<div class="row-fluid sortable"> 
		<div class="box span12">
					
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Professor Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

				</div>
			</div>  
		<div class="box-content">
			<fieldset>
				<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable " id="user-details">
					<tr class="even">
						
					</tr> 
				</table>
				
				<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable " id="prof-details">
					<tr class="even">
						<td ><strong><label for="User ID">Professor ID</label></strong></td>
						<td > ${responseJSON.LECTID}	</td>
						<td ><strong><label for="User ID">Professor Name</label></strong></td>
						<td ><span id="profName"> ${responseJSON.FULLNAME} </span></td>
					</tr> 
					<tr class="even">
						<td ><strong><label for="User ID">Academic Year</label></strong></td>
						<td ><span id="doj"> ${responseJSON.ACADEMICYEAR} </span></td>
						<td ><strong><label for="User ID">Semister </label></strong></td>
						<td ><span id="profStaus"> ${responseJSON.SEMSESSIONNAME} </span></td>
					</tr> 
					<tr class="even">
						<td ><strong><label for="User ID">Section</label></strong></td>
						<td ><span id="doj"> ${responseJSON.SECTIONNAME} </span></td>
						<td ><strong><label for="User ID">Campus Name</label></strong></td>
						<td ><span id="doj"> ${responseJSON.CAMPUSNAME} </span></td>
					</tr> 
				</table>
			</fieldset> 
		</div>
	</div>
	</div>
	
	
	 <div class="form-actions">
			<input type="button" name="btn-back" class="btn btn-success" id="btn-back" value="Back" /> 
			<span id ="error_dlno" class="errors"></span>
	</div>  
</div> 
</form> 
</body> 
</html>