
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<SCRIPT type="text/javascript">
var toDisp = '${type}';

$(document).ready(function(){

	var actionLink = "";


	var userStatus = '${responseJSON.CV0013}';
	var text = "";

	if( userStatus == 'Active')
		text = "<a href='#' class='label label-success'  >"+userStatus+"</a>";
	else if( userStatus == 'De-Active')
		text = "<a href='#'  class='label label-warning' >"+userStatus+"</a>";
	else if( userStatus == 'InActive')
		text = "<a href='#'  class='label label-info'  >"+userStatus+"</a>";
	else if( userStatus == 'Un-Authorize')
		text = "<a href='#'  class='label label-primary'   >"+userStatus+"</a>";

	$('#spn-user-status').append(text);

	$('#btn-submit').live('click',function() {
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/userGrpCreation.action";
		$("#form1").submit();
	});

	$("#MerchantInfo").hide();
	var userType = '${responseJSON.CV0017}';

	if(userType=="MS" || userType=="MU"){
		$("#MerchantInfo").show();
	}
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
				 <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="userGrpCreation.action">User Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#"> ${type} User </a></li>
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
				<table width="950"  border="0" cellpadding="5" cellspacing="1"
					class="table table-striped table-bordered bootstrap-datatable " >
					 <tr >
							<td width="25%" ><strong><label for="User Id"><strong>User Id</label></strong></td>
							<td width="25%" >${responseJSON.CV0001}<input type="hidden" name="CV0001"  id="userId" value="${responseJSON.CV0001}" /> </td>
							<td width="25%" ><strong><label for="Employee No"><strong>ID</label></strong></td>
							<td width="25%" >${responseJSON.CV0002}<input type="hidden" name="CV0002"  id="empNo" value="${responseJSON.CV0002}" /></td>
						</tr>
						<tr >
							<td><strong><label for="First Name"><strong>First Name</label></strong></td>
							<td>${responseJSON.CV0003} <input type="hidden" name="CV0003"  id="firstName" value="${responseJSON.CV0003}" /></td>
							<td><strong><label for="Last Name"><strong>Last Name</label></strong></td>
							<td> ${responseJSON.CV0004}<input type="hidden" name="CV0004"  id="lastName" value="${responseJSON.CV0004}" /></td>
						</tr>
						<tr >
							<td><strong><label for="Telephone Res"><strong>Telephone(Res)</label></strong></td>
							<td>${responseJSON.CV0005}<input type="hidden" name="CV0005"  id="telephoneRes" value="${responseJSON.CV0005}" /></td>
							<td><strong><label for="Telephone Off"><strong>Telephone(Off)</label></strong></td>
							<td>${responseJSON.CV0006}<input type="hidden" name="CV0006"  id="telephoneOff" value="${responseJSON.CV0006}" /></td>
						</tr>

						 <tr >
								<td><strong><label for="Mobile"><strong>Mobile</label></strong></td>
								<td>234-${responseJSON.CV0007}<input type="hidden" name="CV0007"  id="mobile" value="${responseJSON.CV0007}" /></td>
								<td><!-- <strong><label for="Fax"><strong>Fax</label></strong> --></td>
								<td><input type="hidden" name="CV0008"  id="fax" value="${responseJSON.CV0008}" /></td>
						 </tr>

						<tr >
						  <td><strong><label for="User Level"><strong>User Level</label></strong></td>
						  <td>
							${responseJSON.CV0009} <input type="hidden" name="CV0009"  id="adminType" value="${responseJSON.CV0009}" />
						 </td>
						 <td><strong><label for="Office Location"><strong>Office Location</label></strong></td>
						  <td>
							${responseJSON.CV0010} <input type="hidden" name="CV0010"  id="officeLocation" value="${responseJSON.CV0010}" />
						   </td>
						</tr>
						<tr >
							<td><strong><label for="E-Mail"><strong>E-Mail</label></strong></td>
							<td>
								${responseJSON.CV0012} <input type="hidden" name="CV0012"  id="email" value="${responseJSON.CV0012}" />
							</td>
							<td><strong><label for="Expiry Date"><strong></label></strong></td>
							<td>
								<%-- ${responseJSON.CV0011} --%> <input type="hidden" name="CV0011"  id="expiryDate" value="03/05/2014" />
							</td>
						</tr>
						<tr >
							<td><strong><label for="User Status"><strong>User Status</label></strong></td>
							<td>
								<span id="spn-user-status"></span> <input type="hidden" name="CV0013"  id="user_status" value="${responseJSON.CV0013}" />
							</td>
							<td> &nbsp;</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr id="MerchantInfo">
							<td><strong><label for="Merchant Id"><strong>Merchant Id</label></strong></td>
							<td>
								${responseJSON.CV0015}
							</td>
							<td><strong><label for="Store Id"><strong>Store Id</label></strong></td>
							<td>
								${responseJSON.CV0016}
							</td>
						</tr>
				</table>
			</fieldset>


		</div>
		</div>
		</div>
		<div class="form-actions">
		   <input type="button" class="btn btn-primary" type="text"  name="btn-submit" id="btn-submit" value="Next"   ></input>
		  </div>

	</div><!--/#content.span10-->

 </form>
</body>
</html>

