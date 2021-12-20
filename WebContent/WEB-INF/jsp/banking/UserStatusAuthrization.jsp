
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
		$("#authradio").click(function(){
			$('#error_dlno').text('');
		})
		wow();
		var actionLink = "${responseJSON.user_status}";
		
		 
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
		
			$("#spn-success").show();
			$("#spn-fail").hide();
		}
	  
	<%-- 	$('#btn-submit').live('click',function() {  
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/updateUserStatus.action";
			$("#form1").submit();					
		}); 
		
		$('#btn-Cancel').live('click',function() {  
		
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/userGrpCreation.action";
			$("#form1").submit();					
		}); --%>
		
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
		
		

		if ( auth == 'C' || auth == 'R'){
			$("#merchant-auth-data").hide();
			$("#btn-submit").hide();
		}

		  
	}
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
					  <li><a href="#">User Active/Deactive Authorization Confirmation </a></li>
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
				 <i class="icon-edit"></i>User Active/Deactive Authorization
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
							<td>${responseJSON.USERID} <input type="hidden" name="CV0001"  id="firstName" value="${responseJSON.USERID}" /></td>
							<td width="20%" ><strong><label for="ID/Driving License">Employee No</label></strong></td>
							<td> ${responseJSON.CV0002}<input type="hidden" name="CV0002"  id="lastName" value="${responseJSON.CV0002}" /></td>
						</tr>
						<tr > 
							<td><strong><label for="First Name">First Name</label></strong></td>
							<td width="30%" >${responseJSON.CV0003}<input type="hidden" name="CV0003"  id="userId" value="${responseJSON.CV0003}" /> <input type="hidden" name="userId"  id="userId1" value="${responseJSON.CV0003}" /> </td>	 
							<td><strong><label for="Last Name">Last Name</label></strong></td>
							<td width="30%" >${responseJSON.CV0004}<input type="hidden" name="CV0004"  id="empNo" value="${responseJSON.CV0004}" /></td>
							
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
						<%--  <td><strong><label for="User Level">User Level</label></strong></td>
						  <td>
							${responseJSON.CV0009} <input type="hidden" name="CV0009"  id="adminType" value="${responseJSON.CV0009}" />
						 </td> --%>
						 <td><strong><label for="Office Location">Branch</label></strong></td>
						  <td>
							${responseJSON.CV0010} <input type="hidden" name="CV0010"  id="officeLocation" value="${responseJSON.CV0010}" />
						   </td>
						   <td></td>
						   <td></td>
						</tr> 
						<%-- <tr > 
							
							<td><strong><label for="E-Mail">E-Mail</label></strong></td>
							<td>
								${responseJSON.CV0012} <input type="hidden" name="CV0012"  id="email" value="${responseJSON.CV0012}" />
							</td>
							<td></td>
							<td></td>
						</tr>  --%>
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
								${responseJSON.merchantId}<input type="hidden" name="merchantId"  id="merchantId" value="${responseJSON.merchantId}" />
							</td>
							<td><strong><label for="Store Id">Store Id</label></strong></td>
							<td>
								${responseJSON.storeId}<input type="hidden" name="storeId"  id="storeId" value="${responseJSON.storeId}" />
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

				<input type="button" class="btn btn-danger" name="btn-back"
						id="btn-back" value="Back"  />

				<span id ="error_dlno" class="errors"></span>

  			   <input name="STATUS" type="hidden" id="STATUS" value="${STATUS}" />
  				<input name="AUTH_CODE" type="hidden" id="AUTH_CODE" value="${AUTH_CODE}"  />
				<input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
				<input type="hidden" name="DECISION" id="DECISION" />
				<input type="hidden" name="remark" id="remark" />
				
				<input type="hidden" name="formName" id="formName" value="User ActiveDeactive"/>
			

			</div>     						 
	</div><!--/#content.span10--> 
 </form>
</body>
</html> 