
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 
 	 
    
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
</style>
<SCRIPT type="text/javascript">  
	
	var toDisp = '${type}';

	$(document).ready(function(){
		 
		var actionLink = "";
		
		 
		var userStatus = '${responseJSON.CV0013}';
		var text = "";
		var text1 = "";
		
		if( userStatus == 'Active') {
			text = "<a href='#' class='label label-success' id='activ-deactiv-user'  >"+userStatus+"</a>";
			text1 = "<a href='#'  class='label label-warning' id='activ-deactiv-user'  > De-Active</a>";
		} else if( userStatus == 'De-Active') {
			text = "<a href='#'  class='label label-warning' id='activ-deactiv-user'  >"+userStatus+"</a>";
			text1 = "<a href='#' class='label label-success' id='activ-deactiv-user'  >Active</a>";
		} else if( userStatus == 'InActive') {
			text = "<a href='#'  class='label label-info' id='activ-deactiv-user'  >"+userStatus+"</a>";
			text1 = "<a href='#'  class='label label-warning' id='activ-deactiv-user'  >De-Active</a>";
		}
		
		$('#spn-user-status').append(text);
		$('#spn-update-user-status').append(text1);
	  
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
	<!-- topbar ends -->
	 <div id="content" class="span10">  
		<div> 
			<ul class="breadcrumb">
			  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="#">User Management</a> <span class="divider"> &gt;&gt; </span></li>
				  <li><a href="#">User ActiveDeactive Acknowledgement</a></li>
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
		 	
	<div class="row-fluid sortable"> 
		<div class="box span12"> 
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>User ActiveDeactive Acknowledgement
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					
				</div>
			</div>
						
		<div class="box-content">
			<fieldset>
				 
				<table width="950"  border="0" cellpadding="5" cellspacing="1" 
					class="table table-striped table-bordered bootstrap-datatable " >
					  <tr > 
							<td width="20%" ><strong><label for="User Id"> User Id</label></strong></td>
							<td width="30%" >${responseJSON.CV0001}<input type="hidden" name="CV0001"  id="firstName" value="${responseJSON.CV0001}" /> </td>
							<td width="20%" ><strong><label for="ID/Driving License">Employee No</label></strong></td>
							<td width="30%" >${responseJSON.CV0002}<input type="hidden" name="CV0002"  id="lastName" value="${responseJSON.CV0002}" /></td>
						</tr>
						<tr > 
							<td><strong><label for="First Name"> First Name</label></strong></td>
							<td>${responseJSON.CV0003} <input type="hidden" name="CV0003"  id="userId" value="${responseJSON.CV0003}" /></td>
							<td><strong><label for="Last Name"> Last Name</label></strong></td>
							<td> ${responseJSON.CV0004}<input type="hidden" name="CV0004"  id="empNo" value="${responseJSON.CV0004}" /></td>
							
							
						</tr>
						<tr > 
							<td><strong><label for="Telephone Res"> Telephone(Res)</label></strong></td>
							<td>${responseJSON.CV0005}<input type="hidden" name="CV0005"  id="telephoneRes" value="${responseJSON.CV0005}" /></td> 
							<td><strong><label for="Telephone Off"> Telephone(Off)</label></strong></td>
							<td>${responseJSON.CV0006}<input type="hidden" name="CV0006"  id="telephoneOff" value="${responseJSON.CV0006}" /></td>
						</tr>

						 <tr > 
								<td><strong><label for="Mobile"> Mobile</label></strong></td>
								<td>234-${responseJSON.CV0007}<input type="hidden" name="CV0007"  id="mobile" value="${responseJSON.CV0007}" /></td> 
								<td><strong><label for="E-Mail"> E-Mail</label></strong></td>
							<td>
								${responseJSON.CV0012} <input type="hidden" name="CV0012"  id="email" value="${responseJSON.CV0012}" />
							</td>
								<td><%-- <strong><label for="Fax"> Fax</label></strong> --%> </td>
								<td><%-- ${responseJSON.CV0008}<input type="hidden" name="CV0008"  id="fax" value="${responseJSON.CV0008}" /> --%></td>
						 </tr>

						 <tr > 
						  <%-- <td><strong><label for="User Level"> User Level</label></strong></td>
						  <td>
							${responseJSON.CV0009} <input type="hidden" name="CV0009"  id="adminType" value="${responseJSON.CV0009}" />
						 </td>  --%>
						 <td><strong><label for="Office Location"> Branch</label></strong></td>
						  <td>
							${responseJSON.CV0010} <input type="hidden" name="CV0010"  id="officeLocation" value="${responseJSON.CV0010}" />
						   </td>
						   <td></td>
						   <td></td>
						</tr> 
						<%-- <tr > 
							<td><strong><label for="Expiry Date"> Expiry Date</label></strong></td>
							<td>
								${responseJSON.CV0011} <input type="hidden" name="CV0011"  id="expiryDate" value="${responseJSON.CV0011}" />
							</td> 
							<td><strong><label for="E-Mail"> E-Mail</label></strong></td>
							<td>
								${responseJSON.CV0012} <input type="hidden" name="CV0012"  id="email" value="${responseJSON.CV0012}" />
							</td>
							<td></td>
							<td></td>
						</tr>  --%>
						<tr >  
							<td><strong><label for="User Status"> Current User Status</label></strong></td>
							<td>
								<span id="spn-update-user-status"></span> <input type="hidden" name="CV0013"  id="user_status" value="${responseJSON.userStatus}" />
							</td> 
							<td><strong><label for="User Status">Previous User Status</label></strong></td>
							<td>
								<span id="spn-user-status"></span>  
							</td> 
						</tr> 
						<%-- <tr id="MerchantInfo">
							<td><strong><label for="Merchant Id">Merchant Id</label></strong></td>
							<td>
								${responseJSON.CV0015}
							</td>
							<td>
							    ${responseJSON.CV0015} <input type="hidden" name="CV0015"  id="adminType" value="${responseJSON.CV0015}" />
						    </td>
							<td><strong><label for="Store Id">Store Id</label></strong></td>
							<td>
								${responseJSON.CV0016}<input type="hidden" name="CV0016"  id="adminType" value="${responseJSON.CV0016}" />
							</td>
						</tr> --%>
				</table>
			</fieldset>  
		</div>
		</div>
	</div> 
    <div class="form-actions">
	<input type="button" class="btn btn-primary" type="text"  name="btn-submit" id="btn-submit" value="Next" width="100" ></input>
   </div>  
</div><!--/.fluid-container--> 
</form>
</body>
</html>

