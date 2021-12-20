<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@taglib uri="/struts-tags" prefix="s"%> 
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>

<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">


<script type="text/javascript">
	
		
			
			$(function () { 
				
				var userStatus = '${responseJSON.status}';
				
				var text = "";
				var text1 = "";
				
				if( userStatus == 'Deactive') {
					text = "<a href='#' class='label label-success'  >"+userStatus+"</a>";
					text1 = "<a href='#'  class='label label-warning'  > Active</a>";
				} else if( userStatus == 'Active') {
					text = "<a href='#'  class='label label-warning'  >"+userStatus+"</a>";
					text1 = "<a href='#' class='label label-success'  >Deactive</a>";
				} 
				
				
				$('#spn-user-status').append(text);
				$('#spn-update-user-status').append(text1);
				
				
				
		        $('#btn-submit').live('click', function () { 
		    		
		    				var url="${pageContext.request.contextPath}/<%=appName %>/agentregistrationDeviceConf.action"; 
		    				$("#form1")[0].action=url;
		    				$("#form1").submit(); 
		    				
		        }); 
		    });  

			
			
			function redirectHome(){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
				$("#form1").submit();
				return true;
			}

				
</script>

</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li> <a href="agentreglock.action">Agent Services</a> </li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						Updating Agent Device Replacement Confirmation
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="userid"><strong>User ID</strong></label></td>
									<td width="25%">${responseJSON.userid}
									<input name="userid" autocomplete="off" type="hidden" class="field" id="userid"  value="${responseJSON.userid}"  /></td>
									<td width="25%"><label for="empno"><strong>Employee No</strong></label></td>
									<td width="25%">${responseJSON.empno}
									<input name="empno" autocomplete="off" type="hidden" class="field" id="empno"  value="${responseJSON.empno}" /></td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="fname"><strong>First Name</strong></label></td>
									<td width="25%">${responseJSON.fname}
									<input name="fname" autocomplete="off" type="hidden" class="field" id="fname"  value="${responseJSON.fname}"  /></td>
									<td width="25%"><label for="lname"><strong>Last Name</strong></label></td>
									<td width="25%">${responseJSON.lname}
									<input name="lname" autocomplete="off" type="hidden" class="field" id="lname"  value="${responseJSON.lname}" /></td>
								</tr>
								
								<tr class="odd">
									<td width="25%"><label for="dob"><strong>Job Title</strong></label></td>
									<td width="25%">${responseJSON.jtitle}
									<input type="hidden"    id="jtitle" name="jtitle" style="width: 200px;" value="${responseJSON.jtitle}" /></td>
									<td width="25%"><label for="mnumber"><strong>Mobile Number</strong></label></td>
									<td width="25%">${responseJSON.mnumber}
									<input name="mnumber" autocomplete="off" type="hidden" class="field" id="mnumber"  required=true value="${responseJSON.mnumber}" /></td>
								</tr>
								<tr class="even">
									<td width="25%"><label for="mid"><strong>Mail Id</strong></label></td>
									<td width="25%">${responseJSON.mailid}
									<input name="mailid" autocomplete="off" type="hidden" class="field" id="mailid"  value="${responseJSON.mailid}"  /></td>
									<td width="25%"><label for="gender"><strong>Branch Code - Branch Name </strong></label></td>
									<td width="25%">${responseJSON.branchcode}
  									<input name="branchcode" autocomplete="off" type="hidden" class="field" id="branchcode"  value="${responseJSON.branchcode}" />
									</td>
								</tr>
								
								<tr>
									<td><strong><label for="User Status"><strong>Current Status</strong></label></strong></td>
									<td>
										<span id="spn-user-status"></span>  
									</td> 
									<td><strong><label for="Updating User Status"><strong>Updating Status</strong></label></strong></td>
									<td>
										<span id="spn-update-user-status"></span>
										<input type="hidden" name="status"  id="status"  value="${responseJSON.status}" />
									</td>
							</tr>
															
							</table>
						 <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>IMEI Details
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  	
						<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
										id="ex-mytable" style="width: 100%;">
							  <thead>
									<tr>
										<th >Sno</th>
										<th >MAC Address</th>
										<th >Device IP </th>
										<th >IMEI</th>
										<th >SERIAL NO</th>
										<th >S/W Version</th>
										<th >Device Type</th>
										<th >Device OS</th>
										<th >Status</th>

 									</tr>
							  </thead>    
							  <tbody id="tbody_data3">  
							  <s:iterator value="responseJSON['IMEI_DATA']" var="userGroups" status="userStatus"> 
								<s:if test="#userStatus.even == true" > 
									<tr class="even" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
								 </s:if>
								 <s:elseif test="#userStatus.odd == true">
      								<tr class="odd" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
   								 </s:elseif> 
									<td><s:property value="#userStatus.index+1" /></td>
									<!-- Iterating TD'S -->
									  <s:iterator value="#userGroups" status="status" > 
										<s:if test="#status.index == 0" >  
											<td> <s:property  value="value" /></td> 											
										</s:if>
										 <s:elseif test="#status.index == 1" >
											 <td ><s:property  value="value"  /></td>
										 </s:elseif> 
										 
 										 <s:elseif test="#status.index == 2" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 <s:elseif test="#status.index == 3" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 4" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 
										  <s:elseif test="#status.index == 5" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 
										  <s:elseif test="#status.index == 6" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 
										  <s:elseif test="#status.index == 7" >
											 <td ><a href='#' class='label label-success'  ><s:property value="value" /></a></td>
										 </s:elseif>
										 
									</s:iterator>  
									
 								  
							</s:iterator>  
							  
							   </tbody>
							  </table>
							</div>
							</div>
	
						</fieldset>
						<input type="hidden"    id="refno" name="refno"   value="${responseJSON.refno}" />	
						<input type="hidden" id="decision" name="decision"  value=""  />
						<input type="hidden" id="Narration" name="Narration"  value=""  />
					</div>
				</div>
			</div>
			
			
	
		
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Confirm" width="100"  ></input> 
			</div>  

			
			
		
			</div>
			

</form>

</body>
</html>
