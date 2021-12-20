
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
	
	 
	var userStatus = '${responseJSON.user_status}';
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
		
});

function wow(){
	
	var auth=$('#STATUS').val();

	if( auth == 'C' || auth == 'R')
		{
		$("#merchant-auth-data").hide();
		$("#btn-submit").hide();
		}		
	else
		{
		$("#merchant-auth-data").show();
		$("#btn-submit").show();
		}
	  
}

$('#btn-back').live('click',function() {
	
	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action";
	$("#form1").submit();	
	
	
});
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
				  <li> <a href="userGrpCreation.action">Bin Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Bin AuthConfirm</a></li>
				</ul>
			</div>  
		 	
		 	<div class="row-fluid sortable">
           
	<div class="box span12">
                            
		<div class="box-header well" data-original-title>
			 <i class="icon-edit"></i>Bin Details 
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
							<td width="25%" ><strong><label for="Bank Code"><strong>Bank Code</label></strong></td>
							<td width="25%" >${responseJSON.bankCode}<input type="hidden" name="CV0001"  id="bankCode" value="${responseJSON.bankCode}" /> </td>  
							<td width="25%" ><strong><label for="Bank Name"><strong>Bank Name</label></strong></td>
							<td width="25%" >${responseJSON.bankName}<input type="hidden" name="CV0002"  id="bankName" value="${responseJSON.bankName}" /></td>
						</tr>
						<tr > 
							<td><strong><label for="Bin"><strong>Bin</label></strong></td>
							<td>${responseJSON.bin} <input type="hidden" name="CV0003"  id="bin" value="${responseJSON.bin}" /></td> 
							<td><strong><label for="Bin Description"><strong>Bin Description</label></strong></td>
							<td> ${responseJSON.binDescription}<input type="hidden" name="CV0004"  id="binDescription" value="${responseJSON.binDescription}" /></td>
						</tr>
					<tr > 
							<td><strong><label for="Status"><strong>Status</label></strong></td>
							<td>${responseJSON.status} <input type="hidden" name="CV0003"  id="status" value="${responseJSON.status}" /></td> 
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
			

			</div>
		  
		 
              						 
	</div><!--/#content.span10-->
		  
 </form>
</body>
</html>

