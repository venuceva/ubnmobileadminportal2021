<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<%String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString();%>
<%@taglib uri="/struts-tags" prefix="s"%> 
<script type="text/javascript" >

function authmainpage(){
 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/customeServicesAct.action';
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
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="customeServicesAct.action">Customer Services</a> <span class="divider"> &gt;&gt; </span> </li>
					 <li><a href="#">Device Replacement</a></li>
				</ul>
				</div>
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i> Device Replacement Acknowledge
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>

			
						<div id="secondaryDetails" class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong> IMEI <font color="red"><s:property value='customercode' /></font> STATUS HAS BEEN CHANGED SUCCESSFULLY  SENT TO CHECKER AUTHORIZATION </strong></td> 
									</tr> 
								</table>
							<fieldset>
						</div>
      			    </div>
                </div>
		<div class="form-actions">
			<a  class="btn btn-primary" href="#" onClick="authmainpage()">Next</a>
		</div>
	</div>  
</form>
</body>
</html>
