
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
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
</style>
<SCRIPT type="text/javascript">

	var toDisp = '${type}';

	$(document).ready(function(){

		var actionLink = "${responseJSON.error_flag}";

		if('${responseJSON.CV0014}' == 'MERTADM' || '${responseJSON.CV0014}' == 'MERCHTUSR' || '${responseJSON.CV0014}' == 'MERCNTSUPE')
		{
			$('#idtype').empty();
			$('#idtype').text('ID');
		}

		var userStatus = '${responseJSON.CV0013}';

		var text = "";
		var text1 = "";

		if( userStatus == 'Active') {
			text = "<a href='#' class='label label-success'  >"+userStatus+"</a>";
			text1 = "<a href='#'  class='label label-warning'  > De-Active</a>";
		} else if( userStatus == 'De-Active') {
			text = "<a href='#'  class='label label-warning'  >"+userStatus+"</a>";
			text1 = "<a href='#' class='label label-success'  >Active</a>";
		} else if( userStatus == 'InActive') {
			text = "<a href='#'  class='label label-info'  >"+userStatus+"</a>";
			text1 = "<a href='#'  class='label label-warning'  >De-Active</a>";
		} else if( userStatus == 'Un-Authorize') {
			text = "<a href='#'  class='label label-info'  >"+userStatus+"</a>";
			text1 = "<a href='#'  class='label label-warning'  >De-Active</a>";
		}

		$('#spn-user-status').append(text);
		$('#spn-update-user-status').append(text1);

		if(actionLink == 'error') {
			$("#spn-success").hide();
			$("#spn-fail").show();

		} else {
			$("#spn-success").show();
			$("#spn-fail").hide();
		}

		$('#btn-submit').live('click',function() {
			
			if($('#makerid').val()==$('#firstName').val()){
				
				$("#errors").text("Login User and Active/deactived change user same , so Active/deactived not allowed .");
				return false;	
			}else{
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/updateUserStatus.action";
			$("#form1").submit();
			}
		});

		$('#btn-Cancel').live('click',function() {

			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/userGrpCreation.action";
			$("#form1").submit();
		});

		$('#btn-next').live('click',function() {
			   $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/userGrpCreation.action";
			   $("#form1").submit();
			  });
	});
	//-->
</SCRIPT>

</head>

<body>
	<form name="form1" id="form1" method="post">
	  <div id="content" class="span10">

		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="userGrpCreation.action">User Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">User ${type} Confirmation</a></li>
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
				 <i class="icon-edit"></i>User ${type} Information
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
							<td width="20%" ><strong><label for="User Id">User Id</label></strong></td>
							<td width="30%" >${responseJSON.CV0001}<input type="hidden" name="CV0001"  id="firstName" value="${responseJSON.CV0001}" /> <input type="hidden" name="userId"  id="userId1" value="${responseJSON.CV0001}" /> </td>
							<td width="20%" ><strong><label for="ID/Driving License">Employee No</label></strong></td>
							<td width="30%" >${responseJSON.CV0002}<input type="hidden" name="CV0002"  id="lastName" value="${responseJSON.CV0002}" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="First Name">First Name</label></strong></td>
							<td>${responseJSON.CV0003} <input type="hidden" name="CV0003"  id="userId" value="${responseJSON.CV0003}" /></td>
							<td><strong><label for="Last Name">Last Name</label></strong></td>
							<td> ${responseJSON.CV0004}<input type="hidden" name="CV0004"  id="empNo" value="${responseJSON.CV0004}" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="Telephone Res">Telephone(Res)</label></strong></td>
							<td>${responseJSON.CV0005}<input type="hidden" name="CV0005"  id="telephoneRes" value="${responseJSON.CV0005}" /></td>
							<td><strong><label for="Telephone Off">Telephone(Off)</label></strong></td>
							<td>${responseJSON.CV0006}<input type="hidden" name="CV0006"  id="telephoneOff" value="${responseJSON.CV0006}" /></td>
						</tr>

						 <tr >
								<td><strong><label for="Mobile">Mobile</label></strong></td>
								<td>234-${responseJSON.CV0007}<input type="hidden" name="CV0007"  id="mobile" value="${responseJSON.CV0007}" /></td>
								
								<td><strong><label for="E-Mail">E-Mail</label></strong></td>
							<td>
								${responseJSON.CV0012} <input type="hidden" name="CV0012"  id="email" value="${responseJSON.CV0012}" />
							</td>
						 </tr>

						 <tr >
						 <%-- <td><strong><label for="User Level"><strong>User Level</label></strong></td>
						  <td>
							${responseJSON.CV0009} <input type="hidden" name="CV0009"  id="adminType" value="${responseJSON.CV0009}" />
						 </td>--%>
						 <td><strong><label for="Office Location">Branch</strong></td>
						  <td>
							${responseJSON.CV0010} <input type="hidden" name="CV0010"  id="officeLocation" value="${responseJSON.CV0010}" />
						   </td>
						   <td></td>
						   <td></td>
						</tr> 
						<%-- <tr >
							<td><strong><label for="Expiry Date">Expiry Date</label></strong></td>
							<td>
								${responseJSON.CV0011} <input type="hidden" name="CV0011"  id="expiryDate" value="${responseJSON.CV0011}" />
							</td> 
							<td><strong><label for="E-Mail">E-Mail</label></strong></td>
							<td>
								${responseJSON.CV0012} <input type="hidden" name="CV0012"  id="email" value="${responseJSON.CV0012}" />
							</td>
							<td></td><td></td>
						</tr> --%>
						<tr >
							<td><strong><label for="User Status">Current User Status</label></strong></td>
							<td>
								<span id="spn-user-status"></span>
							</td>
							<td><strong><label for="User Status">Updating User Status</label></strong></td>
							<td>
								<span id="spn-update-user-status"></span> <input type="hidden" name="CV0013"  id="user_status" value="${responseJSON.CV0013}" />
								<input type="hidden" name="user_status"  id="user_status1" value="${responseJSON.userStatus}" />
							</td>
						</tr>
						<%-- <tr id="MerchantInfo">
							<td><strong><label for="Merchant Id">Merchant Id</label></strong></td>
							<td>
								${responseJSON.CV0015}<input type="hidden" name="CV0015"  id="user_status" value="${responseJSON.CV0015}" />
							</td>
							<td><strong><label for="Store Id">Store Id</label></strong></td>
							<td>
								${responseJSON.CV0016}<input type="hidden" name="CV0016"  id="user_status" value="${responseJSON.CV0016}" />
							</td>
						</tr> --%>
				</table>
				<input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
			</fieldset>
		</div>
		</div>
		</div>
         	<div class="form-actions" id="spn-success">
              <input type="button" class="btn btn-success" type="text"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>
               &nbsp;<input type="button" class="btn" type="text"  name="btn-Cancel" id="btn-Cancel" value="Cancel" width="100" ></input>
              </div>

            <div class="form-actions" id="spn-fail">
               <input type="button" class="btn btn-primary" type="text"  name="btn-next" id="btn-next" value="Next" width="100" ></input>
		   </div>
	</div><!--/#content.span10-->
 </form>
</body>
</html>