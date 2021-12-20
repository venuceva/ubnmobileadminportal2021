
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
  
function getGenerateMerchantScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/getDashboardAct.action';
	$("#form1").submit();
	return true;
}
function createStore(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/getDashboardAct.action';
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
						  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
						  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
						  <li><a href="#">Store View </a></li>
						</ul>
				</div>
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12">   
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Store Primary Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

									</div>
							</div> 
							<div id="primaryDetails" class="box-content">
								<fieldset>
									<table width="950" border="0" cellpadding="5" cellspacing="1" 
										class="table table-striped table-bordered bootstrap-datatable " >
										<tr class="even">
											<td width="20%"><strong><label for="Merchant Name">Merchant Name</label></strong></td>
											<td width="30%"> ${responseJSON.merchantName}
											</td>
											<td width="20%"><strong><label for="Merchant ID">Merchant ID</label></strong></td>
											<td width="30%"> ${responseJSON.merchantID}
											</td>
										</tr>
										<tr class="odd">
											<td><strong><label for="Store Name">Store Name</label></strong></td>
											<td> ${responseJSON.storeName}
											</td>
											<td ><strong><label for="Store ID">Store ID</label></strong></td>
											<td > ${responseJSON.storeId}										</td>	
										</tr>
										<tr class="even">
											<td ><strong><label for="Location">Location</label></strong></td>
											<td > ${responseJSON.locationName}
											</td>
											<td ><strong><label for="KRA PIN">KRA PIN</label></strong></td>
											<td > ${responseJSON.KRA_PIN}
											</td>	
										</tr>    
									</table>
								</fieldset>
							</div>
					</div>
				</div>
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12">    
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Communication Details
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>  
							<div id="communicationDetails" class="box-content">
								<fieldset>
						   		 	<table width="950" border="0" cellpadding="5" cellspacing="1" 
										class="table table-striped table-bordered bootstrap-datatable " >
											<tr class="even">
												<td width="20%"><strong><label for="Manager Name">Manager Name</label></strong></td>
												<td width="30%"> ${responseJSON.managerName}
												</td>
												<td width="20%"><strong><label for="Email">Email</label></strong></td>
							                    <td width="30%"> ${responseJSON.email}
												</td>
											</tr>
											<tr class="odd">
												<td ><strong><label for="Address Line 1">Address Line 1</label></strong></td>
												<td > ${responseJSON.addressLine1}
												</td>
												<td ><strong><label for="Address Line 2 ">Address Line 2 </label></strong></td>
												<td > ${responseJSON.addressLine2}
												</td>
											</tr>
											<tr class="even">
												<td ><strong><label for="Address Line 3 ">Address Line 3 </label></strong></td>
												<td > ${responseJSON.addressLine3}
												</td>
												<td><strong><label for="City">City</label></strong></td>
												<td > ${responseJSON.city}
												</td>
											</tr>
											<tr class="odd">
												<td ><strong><label for="P.O. Box Number ">P.O. Box Number</label></strong></td>
												<td > ${responseJSON.poBoxNumber}
												</td>
												<td ><strong><label for="Telephone Number 1">Telephone Number 1</label></strong></td>
												<td>	${responseJSON.telephoneNumber1}
												</td>
											</tr>
							               <tr class="even">
												<td ><strong><label for="Telephone Number 2 ">Telephone Number 2</label></strong>	</td>
												<td > ${responseJSON.telephoneNumber2}
												</td>
												<td ><strong><label for="Mobile Number">Mobile Number</label></strong></td>
												<td > ${responseJSON.mobileNumber}
												</td>
										   </tr>
							               <tr class="odd">
												<td ><strong><label for="Fax Number ">Fax Number </label></strong></td>
												<td > ${responseJSON.faxNumber}
												</td>
												<td ></td>
												<td > </td>
										   </tr>
										   	<tr class="even">
											<td ><strong><label for="Primary Contact Person">Primary Contact Person</label></strong></td>
											<td > ${responseJSON.prmContactPerson}
											</td>
											<td><strong><label for="Primary Contact Number">Primary Contact Number</label></strong></td>
											<td > ${responseJSON.prmContactNumber}
											</td>
										</tr>
									</table>
								</fieldset>
							</div>
						</div>
					</div> 
		<div class="form-actions"> 
			<a  class="btn btn-primary" href="#" onClick="getGenerateMerchantScreen()">Next</a>
		</div>  
</div>  
</form>
</body>
</html>
