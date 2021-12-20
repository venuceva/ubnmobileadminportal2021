
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 <%@taglib uri="/struts-tags" prefix="s"%> 
 
 <style type="text/css">
 
input#abbreviation{text-transform:uppercase};

 
label.error {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}

.errmsg {
	color: red;
}

.messages {
	font-weight: bold;
	color: green;
	padding: 2px 8px;
	margin-top: 2px;
}

.errors {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}
</style>

  <script type="text/javascript">
  
$(function(){
	var auth=$('#STATUS').val();
	
	if ( auth == 'C'){
		$("#auth-data").hide();
		$("#btn-submit").hide();
		$("#remark").prop('disabled', true);
		$("#remarks").hide();
	}else{		
		$("#remarks").show();
	}
});	  

</script>
 
<SCRIPT type="text/javascript"> 
 

$(document).ready(function(){   
	
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
 		
 		 $("#form1").validate(remarkRules);
 		 
 		var searchIDs="";
		$("div#auth-data input:radio:checked").each(function(index) {
			searchIDs=$(this).val();	
			 
			 $('#DECISION').val(searchIDs);
		});
		
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthRecordconfirm.action";
		$("#form1").submit();	 
	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true;
		var actype= $('#acttype').val(); 		
		if (actype=="REG"){
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/authreglist.action';		
		}
		else{
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action';
		}
		$("#form1").submit();					
	}); 
	
	 var selData = "<s:property value='selectBoxData' />";
	 
	 $('#inssp').text(selData.split('##')[0]);
	 $('#opesp').text(selData.split('##')[1]);
	 $('#syssp').text(selData.split('##')[2]);
	
});

 


var remarkRules = {
		 errorElement: "div",
		 errorPlacement: function(error, element) {

         if ( element.is(":radio") ) {
        	 error.appendTo( "div#errors1" );
         }
         else{
        	 error.appendTo( "div#errors" );
              }
     },
			rules : { 
				remark : { required : false },
				authradio : { required : true }
			},		
			messages : { 
				remark : { 	required : "Please Enter Remarks."	},
				authradio : { required : "Please Select One Option." }
				
					} 
			
		};
//For Closing Select Box Error Message_End



</SCRIPT>  
<s:set value="responseJSON" var="respData"/> 
</head> 

<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
	  <div id="content" class="span10"> 
			 
		    <div> 
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="mpesaAccManagement.action">Mpesa A/C Management</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Account Active /Deactive </a></li>
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
					 <i class="icon-edit"></i>Biller Type Details
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
							<td width="30%" >${responseJSON.custcode}<input type="hidden" name="customercode"  id="customercode" value="<s:property value='accBean.customercode' />" />
							<td width="20%" ><strong><label for="Customer Name">Customer Name</label></strong></td>
							<td width="30%" >${responseJSON.isocode}<input type="hidden" name="accountname"  id="accountname" value="<s:property value='accBean.accountname' />" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="Account Number">Account Number</label></strong></td>
							<td>${responseJSON.fullname}<input type="hidden" name="accountid"  id="accountid" value="<s:property value='accBean.accountid' />" /></td>
							<td><strong><label for="Alias Name">Alias Name</label></strong></td>
							<td>${responseJSON.telco}<input type="hidden" name="fullname"  id="fullname" value="<s:property value='accBean.fullname' />" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="Account Type">Account Type</label></strong></td>
							<td>${responseJSON.nationalid}<input type="hidden" name="productid"  id="productid" value="<s:property value='accBean.productid' />" /></td>
							<td><strong><label for="Institute">Core Bank</label></strong></td>
							<td>${responseJSON.email}<input type="hidden" name="institute"  id="institute" value="<s:property value='accBean.institute' />" /></td>
						</tr>

						 <tr >
								<td><strong><label for="Date Created">Date Created</label></strong></td>
								<td>${responseJSON.authDttm}<input type="hidden" name="authDttm"  id="authDttm" value="<s:property value='accBean.authDttm' />" /></td>
								<td><strong><label for="branchcode">Branch Code</label></strong></td>
								<td>${responseJSON.mobileno}<input type="hidden" name="branchcode"  id="branchcode" value="<s:property value='accBean.branchcode' />" /></td>
						 </tr>

						<tr >
							<td><strong><label for="User Status">Current Account Status</label></strong></td>
							<td>
								<span id="spn-update-user-status"></span> <input type="hidden" name="CV0013"  id="user_status" value="" />
								<input type="hidden" name="user_status"  id="user_status1" value="${responseJSON.status}" />
							</td>
							
							<td><strong><label for="User Status">Updating Account Status</label></strong></td>
							<td>
								<span id="spn-user-status"><input type="hidden" name="stat" id="stat" value="${responseJSON.status}"/> </span>
							</td>
							
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
	 	
	 	  <div id="auth-data"> 
				<ul class="breadcrumb">
				 <li> <strong>Authorize&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio"  class='center-chk' type='radio' value='A' />&nbsp&nbsp </li>
				 <li> <strong>Reject&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio"  class='center-chk' type='radio' value='R' /> &nbsp&nbsp&nbsp</li>
				<li><div id="errors1" class="errors"></div></li>
				</ul>
		   </div> 
		   
   		<div class="form-actions" >
	         <input type="button" class="btn btn-success"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>&nbsp;
	         &nbsp;<input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input>
       </div>   			
       
               <input name="STATUS" type="hidden" id="STATUS" value="${STATUS}" />
  				<input name="AUTH_CODE" type="hidden" id="AUTH_CODE" value="${AUTH_CODE}"  />
				<input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
				<input type="hidden" name="DECISION" id="DECISION" />
				<input type="hidden" name="type" id="type" value="${type}"/>
				<input type="hidden" name="remark" id="remark" value="OK"/>
 				<input type="hidden" name="multiData" id="multiData" value="#respData['multiData']"/>		 
 				<input type="hidden" id="acttype" name="acttype" value="${responseJSON.acttype}" />		 
	</div> 
 </form> 

</body>
</html> 