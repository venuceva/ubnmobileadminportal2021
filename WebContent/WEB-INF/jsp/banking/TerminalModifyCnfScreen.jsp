
<!DOCTYPE html>
<html lang="en">
<%@taglib uri="/struts-tags" prefix="s"%> 
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%>
 
<script type="text/javascript" > 

function getMerchantScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modifyTerminalScreenAct.action';   
	$("#form1").submit();
	return true;
}

function modifyTerminal(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modifyTerminalAct.action';
	$("#form1").submit();
	return true;
}


 </script>

<s:set value="responseJSON" var="respData" />

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
</style > 
	 
</head>

<body>
	<form name="form1" id="form1" method="post" action=""> 
		<div id="content" class="span10">  
			 
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Modify Terminal</a></li>
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
									<i class="icon-edit"></i>Terminal Information
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
								</div>
							</div> 
							
					<div id="terminalDetails" class="box-content">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"  
											class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="Merchant ID"><strong>Merchant ID<font color ="red">*</font></strong></label></td>
									<td width="30%">	
										<s:property value='merchantID' />
										<input name="merchantID" type="hidden"  id="merchantID" class="field"  value="<s:property value='merchantID' />" >
								     </td>
									<td width="20%"><label for="Store ID"><strong>Store ID<font color ="red">*</font></strong></label></td>
									<td width="30%">	
										<s:property value='storeId'/>
										<input name="storeId" type="hidden"  id="storeId" class="field"  value="<s:property value='storeId' />" >
								    </td> 
								 		
								</tr>
								<tr class="odd">
									<td><label for="Terminal ID"><strong>Terminal ID<font color ="red">*</font></strong></label></td>
									<td>  <s:property value='terminalID' />
										  <input name="terminalID" type="hidden" id="terminalID" class="field" value="<s:property value='terminalID' />"  maxlength="8" readonly>
									</td>
									<td><label for="Terminal Usage"><strong>Terminal Usage<font color ="red">*</font></strong></label></td>
									<td> <s:property value='terminalUsage' />
										 <input name="terminalUsage" type="hidden" id="terminalUsage" class="field" value="<s:property value='terminalUsage' />" >
									</td>
											
								</tr>
								<tr class="even">
									<td ><label for="Terminal Make"><strong>Terminal Make<font color ="red">*</font> </strong></label></td>
									<td > <s:property value='terminalMake' />
										  <input name="terminalMake" type="hidden" id="terminalMake" class="field" value="<s:property value='terminalMake'  />" >
									</td>
									<td ><label for="Model Number "><strong>Model Number<font color ="red">*</font></strong></label></td>
									<td >   <s:property value='modelNumber' />
											<input name="modelNumber" type="hidden" id="modelNumber" class="field" value="<s:property value='modelNumber' />">
									</td>		
								</tr>
										
								 <tr  class="odd">
									<td><label for="Serial Number"><strong>Serial Number<font color ="red">*</font></strong></label></td>
									<td > <s:property value='serialNumber' />
										  <input name="serialNumber" type="hidden" id="serialNumber" class="field" value="<s:property value='serialNumber'  />">
									</td>
									<td></td>
									<td></td>
									</tr>
									 
								 <tr  class="even">
									<td><label for="Valid From"><strong>Valid From (dd-mon-yyyy)<font color ="red">*</font></strong></label></td>
									<td > <s:property value='validFrom' />
										 <input name="validFrom" type="hidden" id="validFrom" class="field" value="<s:property value='validFrom' />">
									</td>
									<td ><label for="Valid Thru"><strong>Valid Thru (dd-mon-yyyy)<font color ="red">*</font></strong></label></td>
									<td > <s:property value='validThru' />
										  <input name="validThru" type="hidden" id="validThru" class="field" value="<s:property value='validThru'  />">
									</td>	
								</tr> 
							</table>
						</fieldset>	 
					</div>
				</div>
			</div>
			<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12"> 
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>TMK Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

						</div>
					</div> 
					<div id="feeDetails" class="box-content">
						<fieldset>
							 <table width="950" border="0" cellpadding="5" cellspacing="1"  
									class="table table-striped table-bordered bootstrap-datatable " >
								<tr class="even">
									<td width="20%"><label for="Status"><strong>Status<font color ="red">*</font></strong></label></td>
									<td colspan=3> 
									      <s:property value='status' />
				   			 			  <input name="status" type="hidden" id="status" class="field" value="<s:property value='status' />">
									</td>
										  
								</tr>
								<tr class="odd">
									<td><label for="date"><strong>Date<font color ="red">*</font></strong></label></td>
									 <td >  <s:property value='terminalDate' />
										<input name="terminalDate" type="hidden" id="terminalDate" class="field" value="<s:property value='terminalDate' /> ">
									</td>
								</tr>
							</table>
						</fieldset>
					</div>  
				</div>
			</div>
		<div class="form-actions">
			<a  class="btn btn-danger" href="#" onClick="getMerchantScreen()">Back</a> &nbsp;&nbsp;
			<a  class="btn btn-success" href="#" onClick="modifyTerminal()">Modify</a>
		</div>	
	</div> 
</form> 
<script src="${pageContext.request.contextPath}/js/jquery.chosen.min.js"></script>
 
  
</body>
</html>
