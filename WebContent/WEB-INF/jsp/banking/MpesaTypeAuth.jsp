
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
<%@taglib uri="/struts-tags" prefix="s"%> 

 <s:set value="responseJSON" var="respData"/>
 
<SCRIPT type="text/javascript"> 
var toDisp = '${type}';

$(document).ready(function(){
	 
	terminalConf();
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
		$("#form1").validate(remarkRules);
		var searchIDs="";
		$("#error_dlno").text('');
 		$("div#merchant-auth-data input:radio:checked").each(function(index) {
 	    searchIDs=$(this).val();
 	    $('#DECISION').val(searchIDs);
		});
		
		  if(searchIDs.length == 0) {
			  $("#error_dlno").text("Select Atleast One Option Authorize/Reject");
			} else {
			  $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthRecordconfirm.action";
			  $("#form1").submit();	
			}
	});  
	
    $('#btn-back').live('click',function() {  
			$("#form1").validate().cancelSubmit = true;
         $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action";
         $("#form1").submit();	
	});  
		
});

   var remarkRules= {
		rules : {
			remark : { required : true } 
		},
		messages : {
			remark : { required : "Please Enter Remark" } 
		}
	};

function terminalConf(){
	
	var auth=$('#STATUS').val();
	
		if( auth == 'C' )
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
				 <li> <a href="AuthorizationAll.action">Authorization</a> <span class="divider"> &gt;&gt; </span></li>
				 <li><a href="#"> ${type} Authorization Details </a></li>
				</ul>
			</div>  
		 	
		 	<div class="row-fluid sortable">
           
	<div class="box span12">
                            
		<div class="box-header well" data-original-title>
			 <i class="icon-edit"></i>M-Pesa Account Details
			<div class="box-icon">
				<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
			</div>
		</div>
						
		<div class="box-content">
			<fieldset> 

				<table width="98%"  border="0" cellpadding="5" cellspacing="1" 
					class="table table-striped table-bordered bootstrap-datatable " > 
						<tr > 
							<td width="25%" ><label for="Description"><strong>Biller Type Name</strong></label></td>
							<td width="25%" colspan="3"> <s:property value='#respData.biller_type'/>  </td>
							 
						</tr> 
						<tr > 
							<td><label for="Description"><strong>Description</strong></label></td>
							<td><textarea name="description" disabled id="description"  required=true style="height: 69px; width: 453px;"><s:property value='#respData.description' /></textarea> </td> 
							 
						</tr>  
						<tr >  
							<td><label for="BFUB Account"><strong>BFUB Account</strong></label></td>
							<td><input type="text" disabled name="bfubaccount"  id="bfubaccount" value="<s:property value='#respData.bfb_acct' />" required=true  />  </td>							
						</tr> 
				</table>
				
			</fieldset> 
			
              
		</div>
		</div>
		</div> 
		
		<div class="row-fluid sortable" id='remarks'><!--/span-->
					<div class="box span12">
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Remarks
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
								</div>
							</div>  
							<div id="remarksInformation" class="box-content"> 
								<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
										id="documentData" > 
										<tr > 
											<td><label for="Remarks"><strong>Remarks<font color="red">*</font></strong></label></td>
											<td> <input type="text" name="remark"  id="remark"  value="${responseJSON.remarks}" /></td> 
											<td></td>
											<td></td>
										</tr> 
								</table>
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
			
				<input type="button" class="btn btn-danger" name="btn-back"
						id="btn-back" value="Back"  />
				
				
				<input type="button" class="btn btn-success" name="btn-submit"
						id="btn-submit" value="Confirm"  />

				
				<span id ="error_dlno" class="errors"></span>

  			   <input name="STATUS" type="hidden" id="STATUS" value="${STATUS}" />
  				<input name="AUTH_CODE" type="hidden" id="AUTH_CODE" value="${AUTH_CODE}"  />
				<input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
				<input type="hidden" name="DECISION" id="DECISION" />
				<input type="hidden" name="type" id="type" value="${type}"/>
			

			</div>
		  
		 
              						 
	</div><!--/#content.span10-->
		  
 </form>
</body>
</html>

