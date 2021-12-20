<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %> 
 

<style type="text/css">
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
.errors {
color: red;
}
</style>
<script type="text/javascript">

$(function() {  
	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modregcustinfoBack.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modregcustinfoCnfAct.action'; 
		$("#form1").submit();
		
		 $(this).prop('disabled', true);
			$("#btn-submit").hide();
		return true;
	}); 
});

</script> 
</head> 
<body>
<form name="form1" id="form1" method="post"> 
		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Modify Customer Details</a>  </li> 
 					</ul>
				</div>  

				<table>
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
							<td width="30%"><s:property value='accBean.customercode' /> 
									<input type="hidden" name="customercode"  id="customercode"   value="<s:property value='accBean.customercode' />"   />
									<input type="hidden" name="institute"  id="institute"   value="<s:property value='institute' />"   /></td>
							<td width="20%"><label for="To Date"><strong>Customer Name</strong></label> </td>
							<td width="30%" ><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<td ><label for="To Date"><strong>Mobile Number</strong></label> </td>
							<td><s:property value='telephone' />
								<input type="hidden" value="<s:property value='accBean.telco' />" style="width: 95px;" readonly name="telco" id="telco" />&nbsp;
								<input type="hidden" value="234" style="width:25px;" readonly name="isocode" id="isocode"/>&nbsp;
								<input type="hidden" value='<s:property value='telephone' />' name="telephone" id="telephone" readonly style="width:95px;" />
 							</td>
							<td  ><label for="To Date"><strong>Date Of Bith</strong></label> </td>
							<td><s:property value='accBean.idnumber' /> <input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='idnumber' />"   />  </td>
						</tr>
						<tr class="even">
							<td><label for="From Date"><strong>Email ID</strong></label></td>
							<td><s:property value='accBean.email' /> <input type="hidden" name="email"  id="email"   value="<s:property value='email' />"   />  </td>
							<td><label for="To Date"><strong>Preferred Language</strong></label> </td>
							<td><s:property value='langugae' /> <input type="hidden" name="langugae"  id="langugae"   value="<s:property value='langugae' />"   />  </td>
						</tr>
						<tr class="even">
							<td><label for="Product"><strong>Product</strong></label></td>
							<td><s:property value='accBean.product' /> <input type="hidden" name="product"  id="product"   value="<s:property value='product' />"   />  </td>
							<td><label for="Description"><strong>Description</strong></label> </td>
							<td><s:property value='prodesc' />
							<input type="hidden" value='<s:property value='prodesc' />' name="prodesc" id="prodesc" readonly style="width:130px;" /> </td>
						</tr>
						
												
				 </table>
				  <input type="hidden"   name="apptype"  id="apptype"   value="<s:property value='accBean.apptype' />"   />
				  <input type="hidden"   name="changeproduct"  id="changeproduct"   value="<s:property value='changeproduct' />"   />
				  <input type="hidden"   name="changenewuserid"  id="changenewuserid"   value="<s:property value='changenewuserid' />"   />
				</fieldset> 
				</div>  
				
	  </div>
	  </div> 
	  
	  <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Existing Accounts
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  
		  		<div class="box-content"> 
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
									<!-- 	<th>Status</th> -->
 									</tr>
							  </thead>    
							  <tbody id="tbody_data1">   
								  <s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{accBean.multiData}"/>  
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
												<s:generator val="%{#mulData}"	var="bankDat" separator="," >  
													<s:iterator status="itrStatus"> <td><s:property /></td></s:iterator>  
												</s:generator> 
 										</tr>  
									</s:iterator> 
							  </tbody>
							</table> 
						</div> 
			</div>
		</div>	
			
	<div class="row-fluid sortable" id="add-new-act"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Add New Account
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  		
			<div class="box-content">
					<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
								id="mytable" style="width: 100%;">
					  <thead>
							<tr>
								<th>Sno</th>
								<th>Account Number</th>
								<th>Account Name </th>
								<th>Branch Code</th>
								<th>Account Type</th>
								<th>Alias Name</th>
							</tr>
					  </thead>    
					  <tbody id="tbody_data"> 
					  	<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
							<s:param name="jsonData" value="%{accBean.newAccountData}"/>  
							<s:param name="searchData" value="%{'#'}"/>  
						</s:bean> 
						<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus">   
								<s:if test="#mulDataStatus.even == true" > 
									<tr class="even" align="center">
								</s:if>
								<s:elseif test="#mulDataStatus.odd == true">
									<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
								</s:elseif>  
								<td><s:property value="#mulDataStatus.index+1" /></td>
									<s:generator val="%{#mulData}" var="bankDat" separator="," > 
										<s:iterator status="itrStatus"> 
												<td><s:property /></td> 
										</s:iterator>  
									</s:generator> 
								</tr>
						</s:iterator>
					  </tbody>
					</table> 
					
					<%-- <input type="hidden" name="newAccountData" id="newAccountData" value="<s:property value='newAccountData' />" /> --%>
					<input type="hidden" name="multiData"  id="multiData" value="<s:property value='multiData' />"  /> 
		 	  </div> 
		 </div>
	 </div>
		<div >
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">&nbsp;Back </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Confirm</a>					 
		</div> 
	</div> 	 
 </form>
 <script type="text/javascript">
$(function(){
	
	var actlen = $('#tbody_data > tr').length;
	
	if(actlen == 0){
		$('#add-new-act').hide();
	}else {
		$('#add-new-act').show();
	}
	 
});  
</script>
</body> 
</html>