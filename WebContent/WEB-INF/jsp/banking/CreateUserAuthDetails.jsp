
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
	
	$('#authradio').live('click',function() {

			$("#error_dlno").text(' ');
	
	});
	var actionLink = "";


	var userStatus = '${responseJSON.user_status}';
	var text = "";



	if( userStatus == 'Active')
		text = "<a href='#' class='label label-success'  >"+userStatus+"</a>";
	else if( userStatus == 'De-Active')
		text = "<a href='#'  class='label label-warning' >"+userStatus+"</a>";
	else if( userStatus == 'InActive')
		text = "<a href='#'  class='label label-info'  >"+userStatus+"</a>";
	else if( userStatus == 'Not Authorized')
		text = "<a href='#'  class='label label-primary'   >"+userStatus+"</a>";

	$('#spn-user-status').append(text);




	$('#btn-submit').live('click',function() {

		var searchIDs="";

 		$("div#merchant-auth-data input:radio:checked").each(function(index) {
 			searchIDs=$(this).val();
 			 $('#DECISION').val(searchIDs);
		});

		  if(searchIDs.length == 0) {
				$("#error_dlno").text("Please check atleast one record.");
			} else {
						$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthRecordconfirm.action";
						$("#form1").submit();
			}
	});

  $('#btn-back').live('click',function() {


						$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action";
						$("#form1").submit();

	});

});

function wow(){

	var auth=$('#STATUS').val();



//	if ( auth == 'C' || auth == 'R'){
	if ( auth == 'C'){
		$("#merchant-auth-data").hide();
		$("#btn-submit").hide();
	}


}
	//-->
</SCRIPT>



</head>

<body onload="wow()">
	<form name="form1" id="form1" method="post">
	<!-- topbar ends -->
	 <div id="content" class="span10">

		    <div>
				<ul class="breadcrumb">
				 <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="userGrpCreation.action">User Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#"> User Creation Authorization Confirmation </a></li>
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
							<td width="25%" >${responseJSON.user_id}<input type="hidden" name="CV0001"  id="userId" value="${responseJSON.user_id}" /> </td>
							<td width="25%" ><strong><label for="ID/Driving License"><strong>Employee No</label></strong></td>
							<td width="25%" >${responseJSON.emp_id}<input type="hidden" name="CV0002"  id="empNo" value="${responseJSON.emp_id}" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="First Name"><strong>First Name</label></strong></td>
							<td>${responseJSON.first_name} <input type="hidden" name="CV0003"  id="firstName" value="${responseJSON.first_name}" /></td>
							<td><strong><label for="Last Name"><strong>Last Name</label></strong></td>
							<td> ${responseJSON.last_name}<input type="hidden" name="CV0004"  id="lastName" value="${responseJSON.last_name}" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="Telephone Res"><strong>Telephone(Res)</label></strong></td>
							<td>${responseJSON.res_no}<input type="hidden" name="CV0005"  id="telephoneRes" value="${responseJSON.res_no}" /></td>
							<td><strong><label for="Telephone Off"><strong>Telephone(Off)</label></strong></td>
							<td>${responseJSON.off_no}<input type="hidden" name="CV0006"  id="telephoneOff" value="${responseJSON.off_no}" /></td>
						</tr>

						 <tr >
								<td><strong><label for="Mobile"><strong>Mobile</label></strong></td>
								<td>234-${responseJSON.mobile_no}<input type="hidden" name="CV0007"  id="mobile" value="${responseJSON.mobile_no}" /></td>
								<td><strong><label for="E-Mail"><strong>E-Mail</label></strong></td>
							<td>
								${responseJSON.email} <input type="hidden" name="CV0012"  id="email" value="${responseJSON.email}" />
							</td> </tr>

						<tr >
						 
						 <td><strong><label for="User Status"><strong>User Status</label></strong></td>
							<td>
								<span id="spn-user-status"></span> <input type="hidden" name="CV0013"  id="user_status" value="${responseJSON.CV0013}" />
							</td>
						 <td><strong><label for="Office Location"><strong>Branch</label></strong></td>
						  <td>
							${responseJSON.location} <input type="hidden" name="CV0010"  id="officeLocation" value="${responseJSON.location}" />
						   </td>
						</tr>
						<%-- <tr >
							 <td><strong><label for="User Level"><strong>User Level</label></strong></td>
						  <td>
							${responseJSON.user_level} <input type="hidden" name="CV0009"  id="adminType" value="${responseJSON.user_level}" />
						 </td>
							<td></td>
							<td></td>
						</tr>
						<tr id="MerchantInfo">
							<td><strong><label for="Merchant Id"><strong>Merchant Id</label></strong></td>
							<td>
								${responseJSON.merchantId}<input type="hidden" name="merchantId"  id="user_status" value="${responseJSON.merchantId}" />
							</td>
							<td><strong><label for="Store Id"><strong>Store Id</label></strong></td>
							<td>
								${responseJSON.storeId}<input type="hidden" name="storeId"  id="user_status" value="${responseJSON.storeId}" />
							</td>
						</tr> --%>
						
				</table>
			</fieldset>

		
		</div>
		</div>
		</div>

 		

		 <div id="merchant-auth-data">
				<ul class="breadcrumb">
				 <li> <strong>Authorize&nbsp&nbsp&nbsp&nbsp&nbsp</label></strong><input  name="authradio" id="authradio"  class='center-chk' type='radio' value='A' />&nbsp&nbsp </li>
				 <li> <strong>Reject&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label></strong><input  name="authradio" id="authradio"  class='center-chk' type='radio' value='R' /> &nbsp&nbsp&nbsp</li>
				</ul>
		</div>

		<div class="form-actions">

				<input type="button" class="btn btn-success" name="btn-submit"
						id="btn-submit" value="Confirm"  />

				<input type="button" class="btn btn-danger" name="btn-back"	id="btn-back" value="Back"  />
				<span id ="error_dlno" class="errors"></span>
  			    <input name="STATUS" type="hidden" id="STATUS" value="${STATUS}" />
  				<input name="AUTH_CODE" type="hidden" id="AUTH_CODE" value="${AUTH_CODE}"  />
				<input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
				<input type="hidden" name="DECISION" id="DECISION" />
				<input type="hidden" name="remark" id="remark" />
				<input type="hidden" name="formName" id="formName" value="User "/>
				<input type="hidden" id="acttype" name="acttype" value="${responseJSON.acttype}" />
				


			</div>



	</div><!--/#content.span10-->

 </form>
</body>
</html>

