
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
<SCRIPT type="text/javascript">


$(document).ready(function(){   
	
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true; 
		
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/searchcustomeract.action";
		$("#form1").submit();		
	}); 
	
 
});

</script> 

</head> 
<body>

<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Search Customer</a>  </li> 
 					</ul>
				</div>  


	<form name="form1" id="form1" method="post" autocomplete="off">
		
  <div class="row-fluid sortable"> 
	<div class="box span12"> 
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Customer Details
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
							<td width="30%"><s:property value='accBean.customercode' /><input type="hidden" name="custcode"  id="custcode"   value="<s:property value='accBean.customercode' />" /></td>
							<td width="20%"><label for="To Date"><strong>Customer Name</strong></label><input type="hidden" name="institute"  id="institute"   value="<s:property value='accBean.institute' />"   /> </td>
							<td width="30%"><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<td><label for="To Date"><strong>Mobile Number</strong></label> </td>
							<td><s:property value='accBean.telephone' />
							<input type="hidden" value="<s:property value='accBean.telco' />" style="width:85px;" readonly name="telco" id="telco"/>&nbsp;
								<input type="hidden" value="<s:property value='accBean.isocode' />" style="width:25px;" readonly name="isocode" id="isocode"/>&nbsp;
								<input type="hidden" value='<s:property value='accBean.telephone' />' style="width: 80px;"  name="telephone" id="telephone" readonly style="width:130px;" />
 							</td>
							<td><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value='accBean.idnumber' /><input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='accBean.nationalid' />"   />  </td>
						</tr>
						<tr class="even">
							<td><label for="From Date"><strong>Email ID</strong></label></td>
							<td><s:property value='accBean.email' /><input type="hidden" name="email"  id="email" readonly  value="<s:property value='accBean.email' />"   />  </td>
							<td><label for="To Date"><strong>Primary Account No</strong></label> </td>
							<td><s:property value='accBean.accountno' /> <input type="hidden" name="langugae"  id="langugae"   value="<s:property value='accBean.langugae' />"   />  
							
									 <input type="hidden" name="STATUS" id="STATUS" value="${STATUS}" />
		  							 <input type="hidden" name="AUTH_CODE"  id="AUTH_CODE" value="${AUTH_CODE}"  />
									 <input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
									 <input type="hidden" name="DECISION" id="DECISION" />
									 <input type="hidden" name="remark" id="remark" />
									 <input type="hidden" name="type" id="type" value="${type}"/>
									 <input type="hidden" name="makerid" id="makerid" value="${makerId}"/>
									 <input type="hidden" name="MID" id="MID" value="${MID}"/>
									 <input type="hidden" name="multiData" id="multiData"/>
									 <input type="hidden" name="check" id="check"/>
									 <input type="hidden" id=closed name="closed" />
									 <input type="hidden" id="accountid" name="accountid" />
									 <input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
							</td>
						</tr>
						<tr class="even">
							<td width="20%"><label for="From Date"><strong>On-Boarded Date</strong></label></td>
							<td width="30%"><s:property value='accBean.authDttm' /><input type="hidden" name="authDttm"  id="authDttm"   value="<s:property value='accBean.customercode' />" /></td>
							<td width="20%"><label for="From Date"><strong>Customer Status</strong></label></td>
							<td width="30%"><s:property value='accBean.status' /><input type="hidden" name="status"  id="status"   value="<s:property value='accBean.status' />" /></td>
						</tr> 
						<tr class="even">
							<td width="20%"><label for="Product"><strong>Product</strong></label></td>
							<td width="30%"><s:property value='accBean.product' /></td>
							<td width="20%"><label for="Description"><strong>Description</strong></label></td>
							<td width="30%"><s:property value='accBean.prodesc' /></td>
						</tr>
					 </table>
					</fieldset>
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Debit Card Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">    
						<tr class="even">
							<td width="50%" colspan="2"><label for="Product"><strong>For Limit Increase Using Debit Card</strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:property value='accBean.smsTemplate' />  </label></td>
							
							<td width="20%" ><label for="Description"><strong>Date</strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:property value='accBean.stdate' /></label></td>
							<td width="30%" ><label for="Description"><strong>Channel</strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:property value='accBean.sttime' /></label></td>
						</tr>
				 </table>
				</fieldset> 
				</div>  
				
	  </div>
	  </div> 
	  
	  
	   <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Customer Using Channels
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div> 
				
			 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
							<td width="20%"><label for="user id"><strong>USSD</strong></label></td>
							<td width="30%"><s:property value='accBean.ussdstatus' /><input type="hidden" name="ussdstatus"  id="ussdstatus"   value="<s:property value='accBean.ussdstatus' />" /></td>
							<td width="20%"><label for="From Date"><strong>MOBILE</strong></label></td>
							<td width="30%"><s:property value='accBean.mobilestatus' /><input type="hidden" name="mobilestatus"  id="mobilestatus"   value="<s:property value='accBean.mobilestatus' />" /></td>
							
						</tr>
						
				</table>	
		</div>
		</div>

	</form>	  
	
	
	
	
	
	
	<form name="form2" id="form2" method="post"> 	
	  
	  <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Registered Accounts
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
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
										<th>Application Type</th>
										<th>Status</th>
										
 									</tr>
							  </thead>    
							  <tbody id="tbody_data">   
								  <s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{accBean.multiData}"/>  
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
											
											 <s:set value="" var="userstatus"   />
											 <s:set value="" var="statusclass"   /> 
											 <s:set value="" var="text1"  /> 
											 <s:set value="" var="text"  /> 
											 
											 <s:property value="#userstatus" />
										
											<td><s:property value="#mulDataStatus.index+1" /></td>
											
												<s:generator val="%{#mulData}"
													var="bankDat" separator="," converter="%{myConverter}"  >   
													
													<s:iterator status="itrStatus" var="itrVar"  > 
														<s:if test="#itrStatus.index == 6" > 
															<s:set var="indexVal" value="%{#attr.itrVar}" /> 
															<%-- <s:property value="#indexVal" /> --%>
															<s:if test="#indexVal == 'Active'" >  
																 <s:set value="%{'label-success'}" var="userstatus"   />
																 <s:set value="%{'btn btn-danger'}" var="statusclass"   /> 
																 <s:set value="%{'De-Active'}" var="text1"   /> 
																 <s:set value="%{'icon-unlocked'}" var="text" />  
																
															 </s:if> 
															 <s:if test="#indexVal == 'De-Active'" > 
																 <s:set value="%{'label-warning'}" var="userstatus"/>
																 <s:set value="%{'btn btn-success'}" var="statusclass"/> 
																 <s:set value="%{'Activate'}" var="text1"/> 
																 <s:set value="%{'icon-locked'}" var="text"/>
															 </s:if> 
															 <td><a href='#' class='label <s:property value="#userstatus" />' index="<s:property value='#itrStatus.index' />" ><s:property value="#indexVal" /></a></td> 
															
														</s:if> 
														<s:elseif test="#itrStatus.index == 5" >
														<s:set var="indexVal" value="%{#attr.itrVar}" /> 
																<s:if test="#indexVal == 'MOBILE'" >  
																 <s:set value="%{'label-success'}" var="userstatus"   />
																
																
															 </s:if> 
															 <s:if test="#indexVal == 'WALLET'" > 
																 <s:set value="%{'label-warning'}" var="userstatus"/>
				
															 </s:if> 
															 <td><a href='#' class='label <s:property value="#userstatus" />' index="<s:property value='#itrStatus.index' />" ><s:property value="#indexVal" /></a></td> 
														</s:elseif>
														<s:else>
																<td><s:property  /></td>
														</s:else>
															
													</s:iterator>  
												</s:generator>
										
											
										</tr>
									</s:iterator>
								 </tbody>
							
							</table>
			</div>
		</div>	
		</div>  
			
			
			  <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Last 5 Transactions Details
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  
				
	
		  		<div class="box-content"  > 
		  			<span id="multi-row" name="multi-row" style="display:none"> </span> 
							<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
										id="ex-mytable" style="width: 100%;">
							  <thead>
									<tr>
										<th style="text-align:center;vertical-align:middle">Sno</th>
										<th style="text-align:center;vertical-align:middle">Account Number</th>
										<th style="text-align:center;vertical-align:middle">Transaction Amount </th>
										<th style="text-align:center;vertical-align:middle">Credit/Debit</th>
										<th style="text-align:center;vertical-align:middle">Transaction Type</th>
										<th style="text-align:center;vertical-align:middle">Payment Reference</th>
<!-- 										<th style="text-align:center;vertical-align:middle">Batch Id</th>
 -->										<th style="text-align:center;vertical-align:middle">Channel</th>
										<th style="text-align:center;vertical-align:middle">Transaction Time</th>
										<th style="text-align:center;vertical-align:middle">Response Message</th>

 									</tr>
							  </thead>    
							  <tbody id="tbody_data2">   
								  <s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{accBean.accDetails}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
									</s:bean> 
									
									<s:iterator value="#jsonToList.data" var="mulData2"  status="mulDataStatus" >   
											<script type="text/javascript">
												var hiddenNames1 = "";
												$(function(){
													$('#account-details').find('input[type=text],input[type=hidden]').each(function(index){ 
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
													$("#multi-row-data").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
												});
												</script> 
											
											<s:if test="#mulDataStatus.even == true" > 
												<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:if>
											<s:elseif test="#mulDataStatus.odd == true">
												<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:elseif> 
									
										<td><s:property value="#mulDataStatus.index+1" /></td>
											<s:generator val="%{#mulData2}"
												var="bankDat" separator="," >  
												<s:iterator status="itrStatus">  
														<td><s:property /></td> 
												</s:iterator>  
											</s:generator>
									
									</tr>
								</s:iterator>
								 </tbody>
								 <tr>
							</table>
			</div>
		</div>	
		</div>  
			  <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Last 5  Action Details
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  
				
	
		  		<div class="box-content"  > 
		  			<span id="multi-row" name="multi-row" style="display:none"> </span> 
							<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
										id="ex-mytable" style="width: 100%;">
							  <thead>
									<tr>
										<th style="text-align:center;vertical-align:middle">Sno</th>
										<th style="text-align:center;vertical-align:middle">Account Number/Customer ID</th>
										<th style="text-align:center;vertical-align:middle">Account Name /Customer Name </th>
										<th style="text-align:center;vertical-align:middle">Action</th>
										<th style="text-align:center;vertical-align:middle">Status</th>
										<th style="text-align:center;vertical-align:middle">Created By</th>
										<th style="text-align:center;vertical-align:middle">Approved by</th>
										<th style="text-align:center;vertical-align:middle">Created Date</th>
										<th style="text-align:center;vertical-align:middle">Approved Date</th>
										<th style="text-align:center;vertical-align:middle">Branch Code</th>

 									</tr>
							  </thead>    
							  <tbody id="tbody_data2">   
								  <s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{accBean.multiData1}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
									</s:bean> 
									
									<s:iterator value="#jsonToList.data" var="mulData2"  status="mulDataStatus" >   
											<script type="text/javascript">
												var hiddenNames1 = "";
												$(function(){
													$('#account-details').find('input[type=text],input[type=hidden]').each(function(index){ 
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
													$("#multi-row-data").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
												});
												</script> 
											
											<s:if test="#mulDataStatus.even == true" > 
												<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:if>
											<s:elseif test="#mulDataStatus.odd == true">
												<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:elseif> 
									
										<td><s:property value="#mulDataStatus.index+1" /></td>
											<s:generator val="%{#mulData2}"
												var="bankDat" separator="," >  
												<s:iterator status="itrStatus">  
														<td><s:property /></td> 
												</s:iterator>  
											</s:generator>
									
									</tr>
								</s:iterator>
								 </tbody>
								 <tr>
							</table>
			</div>
		</div>	
		</div> 
		
		
		  <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Last 5 Device IMEI Details
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  
				
	
		  		<div class="box-content"  > 
		  			<span id="multi-row" name="multi-row" style="display:none"> </span> 
							<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
										id="ex-mytable" style="width: 100%;">
							  <thead>
									<tr>
										<th style="text-align:center;vertical-align:middle">Sno</th>
										<th style="text-align:center;vertical-align:middle">MAC Address</th>
										<th style="text-align:center;vertical-align:middle">Device IP </th>
										<th style="text-align:center;vertical-align:middle">IMEI</th>
										<th style="text-align:center;vertical-align:middle">SERIAL NO</th>
										<th style="text-align:center;vertical-align:middle">S/W Version</th>
										<th style="text-align:center;vertical-align:middle">Device Type</th>
										<th style="text-align:center;vertical-align:middle">Device OS</th>
										<th style="text-align:center;vertical-align:middle">Status</th>
										<!-- <th style="text-align:center;vertical-align:middle">Actions</th> -->

 									</tr>
							  </thead>    
							  <tbody id="tbody_data3">   
								  <s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{accBean.salutation}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
									</s:bean> 
									
									<s:iterator value="#jsonToList.data" var="mulData3"  status="mulDataStatus" >   
											<script type="text/javascript">
												var hiddenNames1 = "";
												$(function(){
													$("#actdeactimei").prop('disabled', false);
													$('#account-details').find('input[type=text],input[type=hidden]').each(function(index){ 
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
													$("#multi-row-data").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
												});
												</script> 
											
											<s:if test="#mulDataStatus.even == true" > 
												<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:if>
											<s:elseif test="#mulDataStatus.odd == true">
												<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:elseif> 
									
										 <s:set value="" var="userstatus"   />
											 <s:set value="" var="statusclass"   /> 
											 <s:set value="" var="text1"  /> 
											 <s:set value="" var="text"  /> 
											 
											 <s:property value="#userstatus" />
										
											<td><s:property value="#mulDataStatus.index+1" /></td>
											
												<s:generator val="%{#mulData3}"	var="bankDat" separator="," converter="%{myConverter}"  >   
													
													<s:iterator status="itrStatus" var="itrVar"  > 
														<s:if test="#itrStatus.index == 7" > 
															<s:set var="indexVal1" value="%{#attr.itrVar}" /> 
															
															<s:if test="#indexVal1 == 'Active'" >  
																 <s:set value="%{'label-success'}" var="userstatus1"   />
																 <s:set value="%{'btn btn-danger'}" var="statusclass1"   /> 
																 <s:set value="%{'Deactivate'}" var="text11"   /> 
																 <s:set value="%{'icon-ok-sign'}" var="text1" />  
																
															 </s:if> 
															 <s:if test="#indexVal1 == 'Deactivate'" > 
																 <s:set value="%{'label-warning'}" var="userstatus1"/>
																 <s:set value="%{'btn btn-success'}" var="statusclass1"/> 
																 <s:set value="%{'Activate'}" var="text11"/> 
																 <s:set value="%{'icon-lock'}" var="text1"/>
															 </s:if> 
															  <td><a href='#' class='label <s:property value="#userstatus1" />' index="<s:property value='#itrStatus.index' />" ><s:property value="#indexVal1" /></a></td> 
															 <%-- <td id="hbut<s:property value="#mulDataStatus.index" />" > 
																<a class='<s:property value="#statusclass1" />' href='#' id='actdeactimei' index="<s:property value='#mulDataStatus.index' />" title='<s:property value="#text11" />'  data-rel='tooltip' > <i class='icon <s:property value="#text1" /> icon-white'></i> </a>&nbsp;
															</td> --%>
														</s:if> 
														<s:else>
																<td><s:property  /></td>
														</s:else>
															
													</s:iterator>  
												</s:generator>
									
									</tr>
								</s:iterator>
								 </tbody>
								
							</table>
			</div>
			
			
	       
		</div>	
		</div>  
		
		 
	 
 </form>
 <form id="form3" name="form3"></form>
 
 <span id ="error_dlno" class="errors"></span>
    	<div class="form-actions" >
	         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input> &nbsp;
	         
       </div>  
 
 <script src="${pageContext.request.contextPath}/js/jquery.cleditor.min.js"></script>
 <script type="text/javascript">
 $(function(){
		
		$('#accountNumber').live('keypress',function(){
			//console.log($(this).length);
			if($(this).length == 0){
				$('#billerMsg').text('');
			}
		});
		
		var config = {
	      '.chosen-select'           : {},
	      '.chosen-select-deselect'  : {allow_single_deselect:true},
	      '.chosen-select-no-single' : {disable_search_threshold:10},
	      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	      '.chosen-select-width'     : {width:"95%"}
	    };
		
	    for (var selector in config) {
	      $(selector).chosen(config[selector]);
	    }  
	}); 
 
</script>
</body> 
</html>