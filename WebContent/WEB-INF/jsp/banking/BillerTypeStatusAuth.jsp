
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
 
<SCRIPT type="text/javascript"> 
 

$(document).ready(function(){   
 
 
 
	
 	$('#btn-submit').live('click',function() {   
 		
 		 $("#form1").validate(remarkRules);
 		 
 		var searchIDs="";
		$("div#auth-data input:radio:checked").each(function(index) {
			searchIDs=$(this).val();	
			//alert(searchIDs);
			 $('#DECISION').val(searchIDs);
		});
		
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthRecordconfirm.action";
		$("#form1").submit();	 
	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true;
		
		
		
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/getBillerTypeDetails.action";
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
				   <li><a href="#">Biller Type  Status </a></li>
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
						 
					<table width="98%"  border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable " > 
						
							<tr > 
								<td width="20%"><label for="Select Institute"><strong>Select Core Banking</strong></label></td>
								<td width="30%" colspan=3><s:property value='#respData.institute' /> <span id="inssp"></span>  <input type="hidden" name="institute"  id="institute"   <s:property value='#respData.institute' />  />  
	 							</td> 
								 
							</tr> 
							<tr > 
								<td><label for="Select Operator"><strong>Select Mobile Network Operator</strong></label></td>
								<td>  <span id="opesp"></span><s:property value='#respData.operator' /><input type="hidden" name="operator"  id="operator"   value="<s:property value='#respData.operator' />"   />  
	 							</td> 
								 
							</tr> 
							 <tr> 
								<td ><label for="Biller Type"><strong>Biller Type(Client Details)</strong></label></td>
								<td ><s:property value='#respData.billerType' /><input type="hidden" name="billerType"  id="billerType"   value="<s:property value='#respData.billerType' />"   />   </td>		 
							</tr> 
							<tr > 
								<td><label for="Biller Type Description"><strong>Biller Type Description</strong></label></td>
								<td><textarea name="billerTypeDescription"  id="billerTypeDescription" style="height: 69px; width: 453px;" readonly><s:property value='#respData.billerTypeDescription' /></textarea> </td> 
								 
							</tr> 
							<tr> 
								<td><label for="Regular Expression"><strong>Regular Expression</strong></label></td>
								<td> <s:property value='#respData.regex' />   <input type="hidden" name="regex"  id="regex" value="<s:property value='#respData.regex' />" /></td>  
							</tr> 
							<tr >  
								<td><label for="Has Fixed Amount ?"><strong>Has Fixed Amount ?</strong></label></td>
								<td><input type="radio" id="fixed-amt-y" name="fixedamountcheck" value="Y" disabled /> Yes &nbsp;
									 <input type="radio" id="fixed-amt-n" name="fixedamountcheck" value="N" disabled /> No  
									 <input type="hidden" name="fixedamountcheckval"  id="fixedamountcheckval" value="<s:property value='fixedamountcheckval' />"    />   </td>							
							</tr> 
							
							<tr id="fixed-amt-tr"> 
								<td><label for="Fixed Amount(ksh)"><strong>Fixed Amount(ksh)</strong></label></td>
								<td> <s:property value='#respData.amount' />  <input type="hidden" name="amount"  id="amount" value="<s:property value='#respData.amount' />"    />   </td>  
							</tr> 
							<tr id="biller-len-tr"> 
								<td><label for="Biller ID Length"><strong>Biller ID Length </strong></label></td>
								<td> <s:property value='#respData.billerIdLen' />  <input type="hidden" name="billerIdLen"  id="billerIdLen" value="<s:property value='#respData.billerIdLen' />"    />   </td>  
							</tr>  
							<tr > 
								<td><label for="System Mode"><strong>System Integration Mode</strong></label></td>
								<td> <s:property value='#respData.systemmodes' /> <span id="syssp"></span>  <input type="hidden" name="systemmodes"  id="systemmodes" value="<s:property value='#respData.systemmodes' />"   />  
	 							</td>  
							</tr> 
							<tr >  
								<td><label for="Has Biller Id ?"><strong>Has Biller Id ?</strong></label></td>
								<td><input type="radio" id="billerid-y" name="billeridcheck" value="Y" disabled> Yes &nbsp;
									 <input type="radio" id="billerid-n" name="billeridcheck" value="N" disabled> No
									 <input type="hidden" name="billeridcheckval"  id="billeridcheckval" value="<s:property value='billeridcheckval' />"    />  
								</td>							
							</tr>
							<tr class="acctbillertype">  
								<td><label for="BFUB Account"><strong>Credit Account </strong></label></td>
								<td><s:property value='#respData.bfubCreditAccount' /> <input type="hidden" name="bfubCreditAccount"  id="bfubCreditAccount" value="<s:property value='#respData.bfubCreditAccount' />"   />  </td>							
							</tr> 
							<tr class="acctbillertype">  
								<td><label for="BFUB Account"><strong>Debit Account  </strong></label></td>
								<td><s:property value='#respData.bfubDebitAccount' /> <input type="hidden" name="bfubDebitAccount"  id="bfubDebitAccount" value="<s:property value='#respData.bfubDebitAccount' />"   />  
								<input type="hidden" name="multiData"  id="multiData"   value="<s:property value='multiData' />"  /></td>							
							</tr> 
							<tr >  
							
								<td><label for="Status"><strong>Status</strong></label></td>
							    <td><s:property value='#respData.billlerTypeStatus' /> <input type="hidden" name="billlerTypeStatus"  id="billlerTypeStatus" value="<s:property value='#respData.billlerTypeStatus' />"   />  </td>  
								<%-- <td class='hidden-phone'><a href='#' class='label <s:property value='#respData.billlerTypeStatus' />' ><input type="hidden" name="billlerTypeStatus"  id="billlerTypeStatus" value="<s:property value='#respData.billlerTypeStatus' />"   /></a>   </td> --%>
						    </tr> 
					</table>
				</fieldset>  
			</div>
		</div>
		</div>
		
		<div class="row-fluid sortable" id="billerid-div"> 
			<div class="box span12">  
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Biller Id Details
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						
					</div>
				</div>
							
				 <div class="box-content" id="biller-details">  
						<table  class="table table-striped table-bordered bootstrap-datatable " 
							id="documentTable" style="width: 100%;">
								  <thead>
										<tr>
											<th>Sno</th>
											<th>Biller ID</th>
											<th>Biller ID Description</th>
											<th>Credit Account</th>
											<th>Debit Account</th>
 										</tr>
								  </thead>    
								 <tbody id="tbody_data">
								 	
								 	<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{multiData}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
									</s:bean> 
									<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" > 
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
											 
										</tr>
									</s:iterator>
								 </tbody>
							</table>
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
 				<input type="hidden" name="multiData" id="multiData" value="#respData['multiData']"/>	
 				<input type="hidden" id="acttype" name="acttype" value="${responseJSON.acttype}" />		 	 
	</div> 
 </form> 
 <script type="text/javascript">
$(function(){
	if($('#fixedamountcheckval').val() == 'Y'){
    	$('#fixed-amt-y').attr('checked','');
    	$('#fixed-amt-n').removeAttr('checked');
    	$('#fixed-amt-tr').show();
	} else {
		$('#fixed-amt-n').attr('checked','');
		$('#fixed-amt-y').removeAttr('checked');
		$('#fixed-amt-tr').hide();
	}
	

    if($('#billeridcheckval').val() == 'Y'){
    	$('#billerid-div').show();
    	$('.acctbillertype').hide();
    	
    	$('#billerid-y').attr('checked','');
    	$('#billerid-n').removeAttr('checked');
    	
    	$('#biller-len-tr').show(); 
    	
	} else {
		$('#billerid-div').hide();
		$('.acctbillertype').show();
		
		$('#billerid-y').removeAttr('checked');
		$('#billerid-n').attr('checked','');
	
		$('#biller-len-tr').hide();
	}
    
});	  
</script>
</body>
</html> 