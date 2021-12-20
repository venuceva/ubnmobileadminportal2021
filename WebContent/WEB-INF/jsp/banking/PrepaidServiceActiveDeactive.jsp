
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

		//var actionLink = 
		var userStatus = $("#stat").val();


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

		$('#btn-submit').live('click',function() {
			//alert();
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/insertPrepaidServiceActDeact.action";
			$("#form1").submit();
		});

		$('#btn-Cancel').live('click',function() {

			//$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/fetchPrepaidServiceAct.action";
			//$("#form1").submit();
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
				  <li> <a href="userGrpCreation.action">Prepaid Card Services</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Prepaid Card Services Confirmation</a></li>
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
				 <i class="icon-edit"></i>Prepaid Card Services Information
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
							<td width="20%" ><strong><label for="Customer ID">Customer ID</label></strong></td>
							<td width="30%" ><s:property value='prepaidBean.customercode' /><input type="hidden" name="customercode"  id="customercode" value="<s:property value='prepaidBean.customercode' />" />
							<td width="20%" ><strong><label for="Customer Name">Customer Name</label></strong></td>
							<td width="30%" ><s:property value='prepaidBean.fullname' /><input type="hidden" name="fullname"  id="fullname" value="<s:property value='prepaidBean.fullname' />" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="Account Number">Card Number</label></strong></td>
							<td><s:property value='prepaidBean.accountid' /> <input type="hidden" name="accountid"  id="accountid" value="<s:property value='prepaidBean.accountid' />" /></td>
							<td><strong><label for="Date Created">Date Created</label></strong></td>
							<td><s:property value='prepaidBean.makerDttm' /><input type="hidden" name="makerDttm"  id="makerDttm" value="<s:property value='prepaidBean.makerDttm' />" /></td>
						</tr>
						<tr >
							<td><strong><label for="User Status">Current User Status</label></strong></td>
							<td>
								<span id="spn-user-status"><input type="hidden" name="stat" id="stat" value="<s:property value='prepaidBean.status' />"/> </span>
							</td>
							<td><strong><label for="User Status">Updating User Status</label></strong></td>
							<td>
								<span id="spn-update-user-status"></span> <input type="hidden" name="CV0013"  id="user_status" value="" />
								<input type="hidden" name="user_status"  id="user_status1" value="${responseJSON.userStatus}" />
							</td>
						</tr>
						
				</table>
			</fieldset>
		</div>
		</div>
		</div>
         	<div class="form-actions" id="spn-success">
              <input type="button" class="btn btn-success" type="text"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>
               &nbsp;<input type="button" class="btn" type="text"  name="btn-Cancel" id="btn-Cancel" value="Cancel" width="100" ></input>
              </div>

            </div><!--/#content.span10-->
 </form>
</body>
</html>