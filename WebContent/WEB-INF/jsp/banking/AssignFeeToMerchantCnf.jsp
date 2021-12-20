
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

	 
<script type="text/javascript" >
$(document).ready(function () { 
	//	var data="${responseJSON.selectUsers}"; 
		var data="${responseJSON.selectUsersText}";
		
		var arr = data.split(','); 
		for ( var i = 0, l = arr.length; i < l; i++ ) {
				option=new Option()
				option.text=arr[i];
				
				option.value=arr[i];
				document.getElementById("userlist1").add(option);
		}  
});
     
function getDashBoardScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantFeeAssign.action';
	$("#form1").submit();
	return true;
} 

function assignDashBoardLinksAck(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantFeeAssignAck.action';
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
					<li><a href="home.action">Home</a> <span class="divider">
							&gt;&gt; </span></li> 
					<li><a href="#">Merchant Fee Assigning Confirmation</a></li> 
				</ul>
			</div> 
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Merchant Fee Assigning Confirmation
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a> 
						</div>
					</div>
					<div class="box-content">
						<fieldset>  
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td><label for="Group Id"><strong>merchant Id</strong></label></td>
									<td>${responseJSON.merchantId} <input
										name="merchantId" type="hidden" id="merchantId" class="field"
										value="${responseJSON.merchantId}"> 
									</td> 
								</tr> 
								<%-- <tr>
									<td><label for="Sub Service"><strong>Sub Service</strong></label></td>
									<td>${responseJSON.subservice} <input
										name="subservice" type="hidden" id="subservice" class="grpname"
										value="${responseJSON.subservice}" />
									</td>
								</tr>  --%>
							</table>
						</fieldset>
					</div>
						<div id="userDetails">
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td>
										<div align="center">
											<select multiple style="width: 300px; height: 150px;"
												size="10" name="userlist1" id="userlist1" disabled>
											</select>
										</div>
									</td>

								</tr>
							</table>
 						</div>
						<input type="hidden" name="selectUsers" id="selectUsers"
							value="${responseJSON.selectUsers}"></input>
							
							<input type="hidden" name="selectUsersText" id="selectUsersText"
							value="${responseJSON.selectUsersText}"></input>


					</div>
				</div>
 			<div >
				<a class="btn btn-danger " href="#" onClick="getDashBoardScreen()">Back</a>
				&nbsp;&nbsp; <a class="btn btn-success" href="#"
					onClick="assignDashBoardLinksAck()">Confirm</a>
			</div>

		</div>

	</form>	 
</body>
</html>
    

