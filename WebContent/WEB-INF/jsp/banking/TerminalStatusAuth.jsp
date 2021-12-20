
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
	 
	wow();
	var actionLink = "";
	
	 
	var userStatus = '${responseJSON.status}';

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
	
	
		if( auth == 'C' || auth == 'R' )
			{
				
				$("#merchant-auth-data").hide();
				$("#btn-submit").hide();
				$("#remarks").hide();
			}
	
 
	  
}
	//--> 
</SCRIPT>
    
  
		
</head>

<body >
	<form name="form1" id="form1" method="post">
	<!-- topbar ends -->
	 <div id="content" class="span10"> 
			 
		    <div> 
				<ul class="breadcrumb">
				 <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="userGrpCreation.action">Termina Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#"> Terminal Active/Deactive Authorization Confirmation </a></li>
				</ul>
			</div>  
		 	
		 	<div class="row-fluid sortable">
           
	<div class="box span12">
                            
		<div class="box-header well" data-original-title>
			 <i class="icon-edit"></i>Terminal Active/Deactive Authorization Confirmation 
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
							<td width="25%" ><strong><label for="Merchant ID"><strong>Merchant ID</label></strong></td>
							<td width="25%" >${responseJSON.merchantID}<input type="hidden" name="CV0001"  id="merchantID" value="${responseJSON.merchantID}" /> </td>  
							<td width="25%" ><strong><label for="Store ID"><strong>Store ID</label></strong></td>
							<td width="25%" >${responseJSON.StoreID}<input type="hidden" name="CV0002"  id="StoreID" value="${responseJSON.StoreID}" /></td>
						</tr>
						<tr > 
							<td><strong><label for="Terminal ID"><strong>Terminal ID</label></strong></td>
							<td>${responseJSON.TerminalID} <input type="hidden" name="CV0003"  id="TerminalID" value="${responseJSON.TerminalID}" /></td> 
							<td><strong><label for="Model Number"><strong>Model Number</label></strong></td>
							<td> ${responseJSON.ModelNumber}<input type="hidden" name="CV0004"  id="ModelNumber" value="${responseJSON.ModelNumber}" /></td>
						</tr>
						<tr > 
							<td><strong><label for="Serial Number"><strong>Serial Number</label></strong></td>
							<td>${responseJSON.SerialNumber}<input type="hidden" name="CV0005"  id="SerialNumber" value="${responseJSON.SerialNumber}" /></td> 
							<td><strong><label for="Terminal Make"><strong>Terminal Make</label></strong></td>
							<td>${responseJSON.TerminalMake}<input type="hidden" name="CV0006"  id="TerminalMake" value="${responseJSON.TerminalMake}" /></td>
						</tr>

						 <tr > 
								<td><strong><label for="Create Date"><strong>Create Date</label></strong></td>
								<td>${responseJSON.terminaldate}<input type="hidden" name="CV0007"  id="terminaldate" value="${responseJSON.terminaldate}" /></td> 
								<td><strong><label for="Status"><strong>Status</label></strong></td>
								<td>${responseJSON.status}<input type="hidden" name="CV0007"  id="status" value="${responseJSON.status}" /></td> 
						 </tr>
						  <tr id="remarks"> 
								
								<td><strong><label for="Remarks"><strong>Remarks</label></strong></td>
								<td><input type="text" name="remark"  id="remark"  /></td>
								<td></td>
								<td></td>
						 </tr>
						 	
						
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
				<!-- <input type="hidden" name="remark" id="remark" /> -->
				
				 <input type="hidden" name="formName" id="formName" value="Terminal Active/Deactive"/>
			

			</div>
		  
		 
              						 
	</div><!--/#content.span10-->
		  
 </form>
</body>
</html>

