
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
$(document).ready(function() {
	
	
	var auth=$('#STATUS').val();
	
	if( auth == 'C' )
	{
		$("#merchant-auth-data").hide();
		$("#remarks").hide();
		$("#btn-submit").hide();
	}
	
	
  $("#authradio").live('click',function() {  
	  $("#error_dlno").text(" ");
  });
   
   var agentMultidata='${responseJSON.agentmultidate}';
	
	
	var json=jQuery.parseJSON(agentMultidata);
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";
	var i = 0;
	var img="";

	for(i=0;i<json.length;i++)
	{
		if(val%2 == 0)
		{
			addclass = "even";
			val++;
		}
		else
		{
			addclass = "odd";
			val++;
		}
		var rowCount = $('#tbody_data2 > tr').length;
		colindex = ++ colindex;

		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+
		"<td class='sorting_1' >"+colindex+"</td>"+
		"<td ><span class='a0488m96' id='a0488m96_3'>"+json[i].bankagentno+"</span></td>"+
		"<td ><span class='a0488m96' id='a0488m96_3'>"+json[i].mpesaagentno+"</span> </td>"+
		"<td ><span class='a0488m96' id='a0488m96_3'>"+json[i].airtelagentno+"</span></td>"+
		"<td ><span class='a0488m96' id='a0488m96_3'>"+json[i].orangeagentno+"</span></td>"+
		"<td ><span class='a0488m96' id='a0488m96_3'>"+json[i].makerid+"</span></td>"+
		"</tr> ";

		$("#tbody_data1").append(appendTxt);
		rowindex = ++rowindex;

	
	}
	 
   
   var address=' ${responseJSON.address}';
   address1=address.split("#");
   $("#address1").text(address1[0]);
   $("#address2").text(address1[1]);
   $("#address3").text(address1[2]);
   var telephnnum=' ${responseJSON.telephonenum}';
   telephn=telephnnum.split("#");
   $("#telephonenum1").text(telephn[0]);
   $("#telephonenum2").text(telephn[1]);
   
  
  
	var bankfinalData='${responseJSON.BankmultData}';
	
	
	
	var json=jQuery.parseJSON(bankfinalData);
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";
	var i = 0;
	var img="";

	for(i=0;i<json.length;i++)
	{
		if(val%2 == 0)
		{
			addclass = "even";
			val++;
		}
		else
		{
			addclass = "odd";
			val++;
		}
		var rowCount = $('#tbody_data2 > tr').length;
		colindex = ++ colindex;

		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+
		"<td class='sorting_1' >"+colindex+"</td>"+
		"<td ><span class='a0488m96' id='a0488m96_3'>"+json[i].accountNO+"</span></td>"+
		"<td ><span class='a0488m96' id='a0488m96_3'>"+json[i].accountDesc+"</span> </td>"+
		"<td ><span class='a0488m96' id='a0488m96_3'>"+json[i].bankcode+"</span></td>"+
		"<td ><span class='a0488m96' id='a0488m96_3'>"+json[i].branccode+"</span></td>"+
		"</tr> ";

		$("#tbody_data2").append(appendTxt);
		rowindex = ++rowindex;

	
	}
	
	
	
	
});




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



</script>
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	<!-- topbar ends --> 
			<div id="content" class="span10">  
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Store Modify Authorization Confirmation </a></li>
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
								
						<div class="box-content" id="primaryDetails"> 
						 <fieldset>  
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " >
									<tr class="even">
										<td width="20%"><label for="Merchant Name"><strong>Merchant Name</strong></label></td>
										<td width="30%"> ${responseJSON.merchantName}
										</td>
										<td width="20%"><label for="Merchant ID"><strong>Merchant ID</strong></label></td>
										<td width="30%"> ${responseJSON.merchantID}
										</td>
									</tr>
									<tr class="odd">
										<td><label for="Store Name"><strong>Store Name</strong></label></td>
										<td> ${responseJSON.StoreName}
										</td>
										<td ><label for="Store ID"><strong>Store ID</strong></label></td>
										<td > ${responseJSON.StoreID}										</td>	
									</tr>
									<tr class="even">
										<td ><label for="Location"><strong>Location</strong></label></td>
										<td > ${responseJSON.location}
										</td>
										<td ><label for="KRA PIN"><strong>KRA PIN</strong></label></td>
										<td > ${responseJSON.kra_pin}
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
											<td width="20%"><label for="Manager Name"><strong>Manager Name</strong></label></td>
											<td width="30%"> ${responseJSON.managername}
											</td>
											<td width="20%"><label for="Email"><strong>Email</strong></label></td>
											<td width="30%"> ${responseJSON.email}
											</td>
										</tr>
										<tr class="odd">
											<td ><label for="Address Line 1"><strong>Address Line 1</strong></label></td>
											<td ><span id="address1"></span></td>
											<td ><label for="Address Line 2 "><strong>Address Line 2 </strong></label></td>
												<td ><span id="address2"></span></td>
										</tr>
										<tr class="even">
											<td ><label for="Address Line 3 "><strong>Address Line 3 </strong></label></td>
												<td ><span id="address3"></span></td>
												<td><label for="Country"><strong>Country</strong></label></td>
											<td > ${responseJSON.merchanttest}
											</td>
											
										</tr>
										<tr class="odd">
										<td ><label for="County "><strong>County </strong></label></td>
                                            <td > ${responseJSON.area}
                                            </td>
                                            <td><label for="City/Town"><strong>City/Town</strong></label></td>
                                           <td > ${responseJSON.city}
                                             </td>
                                             </tr>
                                             <tr>
                                             <td><label for="Postal Code"><strong>Postal Code</strong></label></td>
                                             <td > ${responseJSON.postalcode}
                                             </td>
											 <td ><label for="P.O. Box Number "><strong>P.O. Box Number</strong></label></td>
											<td > ${responseJSON.poboxnum}
											</td> 
											
										</tr>
									   <tr class="even">
									   <td ><label for="Telephone Number 1"><strong>Telephone Number 1</strong></label></td>
											<td><span id="telephonenum1"></span></td>
											<td ><label for="Telephone Number 2 "><strong>Telephone Number 2</strong></label>	</td>
											<td><span id="telephonenum2"></span></td>
											
											
									   </tr>
									   <tr class="odd">
									   <td ><label for="Mobile Number"><strong>Mobile Number</strong></label></td>
											<td >${responseJSON.mobile}
											</td>
											<td ><label for="Fax Number "><strong>Fax Number </strong></label></td>
											<td > ${responseJSON.fax}
											</td>
											
									   </tr>
										<tr class="even">
										<td ><label for="L/R Number "><strong>L/R Number</strong></label></td>
                                         <td > ${responseJSON.lrnumber}
                                           </td>
										<td ><label for="Primary Contact Person"><strong>Primary Contact Person</strong></label></td>
										<td > ${responseJSON.pricontactname}
										</td>
										</tr>
										<tr>
										<td><label for="Primary Contact Number"><strong>Primary Contact Number</strong></label></td>
										<td > ${responseJSON.pricontactnum}
										</td>
										<td ></td>
											<td > </td>
									</tr>
								</table>
							</fieldset> 
						</div>
						</div>
					</div> 
					
					<div class="row-fluid sortable"><!--/span-->
        
							<div class="box span12">
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Bank acoount information
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>  
							<div id="documentInformation" class="box-content"> 
 								
								<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
										id="documentData" > 
										  <thead>
												<tr>
													<th >Sno</th>
													<th >Account Number</th>
													<th>Account Description</th>
													<th>Bank Code</th>
													<th>Branch Code</th>
 												</tr>
										  </thead>    
										 <tbody id="tbody_data2">
										 </tbody>
								</table>
										
						</div>
				</div>
		</div> 
		
		<div class="row-fluid sortable"><!--/span-->
        
							<div class="box span12">
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Agent Based Information
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>  
							<div id="documentInformation" class="box-content"> 
 								
								<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
										id="documentData" > 
										  <thead>
												<tr>
													<th>Sno</th>
													<th>Bank Agent Number</th>
													<th>MPesa Agent Number</th>
													<th>Airtel Money Agent Number</th>
													<th>Orange Money Agent Number</th>
													<th>Maker ID</th>
 												</tr>
										  </thead>    
										 <tbody id="tbody_data1">
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
										<tr> 
											<td><label for="Remarks"><strong>Remarks<font color="red">*</font></strong></label></td>
											<td><input type="text" name="remark"  id="remark"  value="${responseJSON.remarks}" /></td> 
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
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Confirm"  />
				<input type="button" class="btn btn-danger" name="btn-back" id="btn-back" value="Back"  />
				<span  id ="error_dlno" class="errors"></span>
  			    <input name="STATUS" type="hidden" id="STATUS" value="${STATUS}" />
  				<input name="AUTH_CODE" type="hidden" id="AUTH_CODE" value="${AUTH_CODE}"  />
				<input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
				<input type="hidden" name="DECISION" id="DECISION" />
			</div>
	</div> 
</form>
</body>
</html>
