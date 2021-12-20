<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString();%>
<%@taglib uri="/struts-tags" prefix="s"%> 

<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
input#abbreviation{text-transform:uppercase};
</style>
<s:set value="responseJSON" var="respData"/>
<link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.css' />	
<SCRIPT type="text/javascript">
var rowCount1=0;
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
				rmrk : { required : true },
				authradio : { required : true }
			},		
			messages : { 
				rmrk : { 	required : "Please Enter Remarks."	},
				authradio : { required : "Please Select One Option." }
				
					} 
			
		};
	
	
$(document).ready(function(){   
	
	 
 	$('#btn-submit').live('click',function() {
 		
 		var finalData = "";
 		 $("#form2").validate(remarkRules);
 		 $("#error_dlno").text(''); 
 		 
 		
 		var searchIDs="";
		$("div#auth-data input:radio:checked").each(function(index) {
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
	

});

</script> 


</head> 
<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
		
			<div id="content" class="span10">  
			    <div> 
				<ul class="breadcrumb">
				 <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				 <li> <a href="AuthorizationAll.action">Authorization</a> <span class="divider"> &gt;&gt; </span></li>
				 <li><a href="#"> ${type} Authorization Details </a></li>
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
					<i class="icon-edit"></i>Agent Customer Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>  
				<div class="box-content">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
							<td width="20%"><label for="From Date"><strong>Customer Id</strong></label></td>
							<td width="30%"><s:property value='#respData.custcode' /><input type="hidden" name="custcode"  id="custcode"   value="<s:property value='#respData.custcode' />"   />
							<td width="20%"><label for="To Date"><strong>Customer Name</strong></label><input type="hidden" name="institute"  id="institute"   value="<s:property value='#respData.institute' />"   /> </td>
							<td width="30%"><s:property value='#respData.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='#respData.fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<td ><label for="To Date"><strong>Mobile Number</strong></label> </td>
							<td ><s:property value='#respData.mobileno' />

								<input type="hidden" value="<s:property value='#respData.isocode' />" style="width:25px;" readonly name="isocode" id="isocode"/>&nbsp;
								<input type="hidden" value='<s:property value='#respData.mobileno' />' style="width: 80px;" maxlength="9" name="telephone" id="telephone" readonly style="width:130px;" />
 							</td>					
							
							<td><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value='#respData.nationalid' /><input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='#respData.nationalid' />"   />  </td>
						</tr>
						<tr class="even">
							<td><label for="From Date"><strong>Email ID</strong></label></td>
							<td><s:property value='#respData.email' /><input type="hidden" name="email"  id="email" readonly  value="<s:property value='#respData.email' />"   />  </td>
							<td><label for="To Date"><strong>Super Admin</strong></label> </td>
							<td><s:property value='#respData.language' /> <input type="hidden" name="langugae"  id="langugae"   value="<s:property value='#respData.language' />"   /> 
							
							 <input type="hidden" name="STATUS" id="STATUS" value="${STATUS}" />
  							 <input type="hidden" name="AUTH_CODE"  id="AUTH_CODE" value="${AUTH_CODE}"  />
							 <input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
							 <input type="hidden" name="DECISION" id="DECISION" />
							 <input type="hidden" name="remark" id="remark" />
							 <input type="hidden" name="type" id="type" value="${type}"/>
							 <input type="hidden" name="multiData" id="multiData"/>
							  </td>
						</tr>
					<tr class="even">
							<td width="20%"><label for="Product"><strong>Core Bank Account No</strong></label></td>
							<td width="30%"><s:property value='#respData.telco' /></td>
							<td width="20%"><label for="Description"><strong>Branch Code</strong></label></td>
							<td width="30%"><s:property value='#respData.isocode' /></td>
						</tr> 
						<tr class="even">
							<td width="20%"><label for="Product"><strong>Product</strong></label></td>
							<td width="30%"><s:property value='#respData.product' /></td>
							<td width="20%"><label for="Description"><strong>Description</strong></label></td>
							<td width="30%"><s:property value='#respData.prodesc' /></td>
						</tr> 
						<tr class="even">
							<td width="20%"><label for="Product"><strong>Status</strong></label></td>
							<td width="30%"><s:property value='#respData.status' /></td>
							<td width="20%"></td>
							<td width="30%"></td>
						</tr> 
				 </table>
				</fieldset> 
				</div>  
				
	  </div>
	  </div> 
	  
	</form>	  
	
<form name="form2" id="form2" method="post"> 	
	  
	  <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Wallet Accounts
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  
				
				
				<div class="box-content" id="biller-details" style="display:none">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
						
							<td width="20%"><label for="Account Number"><strong>Account Number<font color="red">*</font></strong></label></td>
							<td width="30%"><input type="text" maxlength="14"  class="field" id="accountNumber" name="accountNumber" required=true /></td>
							<td width="20%"><label for="Alias Name"><strong>Alias Name</strong></label> </td>
							<td width="30%">
									<input type="hidden" id="acctName" name="acctName" />
									<input type="hidden" id="branchCode" name="branchCode" />
									<input type="hidden" id="prdCode" name="prdCode" />
									<input type="hidden" id="acttype" name="acttype" value="${responseJSON.acttype}" />		 
								<input type="text" maxlength="20" class="field" id="aliasName" name="aliasName" />
									<!-- <input type="hidden" id="status" name="status"  value="Not Reg"/> -->
							</td>
						</tr>  
						<%-- <tr class="odd"> 
								<td colspan="4" align="center">
									<input type="button" class="btn btn-success" 
										name="add-account" id="add-account" value="Add Account" /> &nbsp;  
									<span id="billerMsg" class="errors"></span>
								</td> 
						</tr>  --%>
						<tr class="odd"> 
								<td colspan="4" align="center">
								 	<input type="button" class="btn btn-success" name="mod-biller" id="mod-biller" value="Update Account" style="display:none"/> &nbsp;
								 	<input type="button" class="btn btn-info" name="can-biller" id="can-biller" value="Cancel" style="display:none"/> &nbsp;
									<span id="billerMsg" class="errors"></span>
								</td> 
							</tr>
						
 				 </table>
				</fieldset> 
				</div> 
				
				
				
		  		<div class="box-content"  > 
		  			<span id="multi-row" name="multi-row" style="display:none"> </span> 
							<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
										id="ex-mytable" style="width: 100%;">
							  <thead>
									<tr>
										<th>Sno</th>
										<th>Account Number</th>
										<th>Account Name </th>
										<th>Branch Code</th>
										<th>Account Type</th>
										<th>Alias Name</th>
										<th id="h20" style="display:none">Actions</th>
 									</tr>
							  </thead>    
							  <tbody id="tbody_data">   
								  <s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{#respData.multiData}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
									</s:bean> 
									
									<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" > 
											<script type="text/javascript">
												var hiddenNames1 = "";
												$(function(){
													$('#biller-details').find('input[type=text],input[type=hidden],select,textarea').each(function(index){ 
														var nameInput = $(this).attr('name'); 
														if(nameInput != undefined) {
														  if(index == 0)  {
															hiddenNames1 = nameInput;
														  } else {
															hiddenNames1 += ","+nameInput; 
														  }  
														} 
													}); 
													var data1 = "<s:property />";
													data1 = data1.split(",");
													$("#multi-row").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
												});
												</script> 
											<s:if test="#mulDataStatus.even == true" > 
												<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:if>
											<s:elseif test="#mulDataStatus.odd == true">
												<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:elseif> 
										
											<td><s:property value="#mulDataStatus.index+1" /></td>
												<s:generator val="%{#mulData}"
													var="bankDat" separator="," >  
													<s:iterator status="itrStatus">  
															<td><s:property /></td> 
													</s:iterator>  
												</s:generator>
										<td id="hbut<s:property value="#mulDataStatus.index" />" style="display:none"><a class='btn btn-min btn-info' href='#' id='edit-biller' index='<s:property value="#mulDataStatus.index" />' title='Edit Account' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;
											<a class='btn btn-min btn-warning' href='#' id='row-cancel' index='<s:property value="#mulDataStatus.index" />' title='Reset Biller' data-rel='tooltip'> <i class='icon icon-undo icon-white'></i></a>&nbsp; 
											<a class='btn btn-min btn-danger' href='#' id='delete-biller' index='<s:property value="#mulDataStatus.index" />' title='Delete Account' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a>
										</td>
										</tr>
									</s:iterator>
								 </tbody>
							</table>
							

<%-- 				<div id="remarksInformation" class="box-content"> 								
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable " > 									  
								<tr > 
									<td width="15%"><label for="Remarks1" valign="middle"><strong>Enter Remarks<font id="rmk" name="rmk"  color="red">*</font></strong></label></td>
									<td><textarea class="cleditor1" name="rmrk" id="rmrk" ></textarea><div id="errors" class="errors"></div></td> 
									
								</tr> 
						</table>
						</div>	 --%>	
						
			</div>
		</div>	
		</div>  
			
		<div id="auth-data"> 
				<ul class="breadcrumb">
				 <li> <strong>Authorize&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio2"  class='center-chk' type='radio' value='A' />&nbsp&nbsp </li>
				 <li> <strong>Reject&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio1"  class='center-chk' type='radio' value='R' /> &nbsp&nbsp&nbsp</li>
				<li><div id="error_dlno" class="errors"></div></li>
				</ul>
				
				
				     <span id ="error_dlno" class="errors"></span>
	           
		</div>  
 					 
	 
 </form>
 
    	<div class="form-actions" >
	         <input type="button" class="btn btn-success"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>&nbsp;
	         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input> &nbsp;
	         
       </div>  
 
 <script src="${pageContext.request.contextPath}/js/jquery.cleditor.min.js"></script>
 <script type="text/javascript">
 
$(function(){
	
if($('#tbody_data > tr').length < 1){
	$("#regacc").hide();
	}

	var auth=$('#STATUS').val();

	if ( auth == 'C'){
		$("#auth-data").hide();
		$("#btn-submit").hide();
		$("#rmrk").prop('disabled', true);
		$("#rmk").hide();

	}else if ( auth == 'D'){
		$("#auth-data").hide();
		$("#btn-submit").hide();
		$("#rmrk").prop('disabled', true);
		$("#rmk").hide();

	}
	else{
		$("#remarksInformation").hide();
		$("#auth-data").show();
		$("#btn-edit").show();

		}
	});	  
</script>
</body> 
</html>