
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
<%@taglib uri="/struts-tags" prefix="s"%>
<SCRIPT type="text/javascript">
var toDisp = '${type}';

$(document).ready(function(){


	/* var dataobject = '${responseJSON.object.IDNumber}';

	alert(dataobject);
 */
	 var merchanttype=$('#merchanttype').val();
	 if(merchanttype=="Lifestyle_Coupon"){
		 $("#dfg").hide(); 
	 }
	var auth=$('#STATUS').val();

	if( auth == 'C')
	{
		$("#merchant-auth-data").hide();
		$("#btn-submit").hide();
		$("#remarks").hide();
	}


	 $("#authradio").click(function()
		 {
		    $("#error_dlno").text(" ");
		 });

	 $("#merchauth").hide();

	 var actionLink = "";
	 var userStatus = '${responseJSON.STATUS}';
	 var text = "";
	// alert(userStatus);
	 if( userStatus == 'Active'){
		$('#status').val('A');
	 }else{
		$('#status').val('D');
	 }
	if( userStatus == 'Active')

		text = "<a href='#' class='label label-success'  >"+userStatus+"</a>";
	else if( userStatus == 'De-Active')
		text = "<a href='#'  class='label label-warning' >"+userStatus+"</a>";
	else if( userStatus == 'InActive')
		text = "<a href='#'  class='label label-info'  >"+userStatus+"</a>";
	else if( userStatus == 'Un-Authorize')
		text = "<a href='#'  class='label label-primary'   >"+userStatus+"</a>";

	$('#spn-user-status').append(text);

	

});


$('#btn-back').live('click',function() {

	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/merchantmenagenet.action";
	$("#form1").submit();


});

$('#btn-deactive-cnf').live('click', function () {
	$("form").validate().cancelSubmit = true;
	var url="${pageContext.request.contextPath}/<%=appName %>/activedeactiveconfirm.action";
	$("#form1")[0].action=url;
	$("#form1").submit();

});


	//-->
</SCRIPT>

<s:set value="responseJSON" var="respData"/>

</head>

<body>
	<form name="form1" id="form1" method="post" action="">
			<div id="content" class="span10">
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Merchant Management </a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#"> Merchant Active / Deactivate Confirmation </a></li>
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

					<div class="row-fluid sortable"><!--/span-->

					<div class="box span12">
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Merchant Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
						<table width="950" border="0" cellpadding="5" cellspacing="1"
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">

								   <tr class="even">
								     <td width="20%"><label for="Super Agent Name"><strong>Merchant A/C Number</strong></label></td>
								     <td width="30%"> <s:property value="#respData.ACCOUNTNUMBER"/>
								     <input type="hidden" id="accNumbers" name="accNumbers" value='<s:property value="#respData.ACCOUNTNUMBER"/>' />
								     
 								     </td>
								     <td width="20%"><label for="Super Agent Name"><strong>Merchant Name</strong></label></td>
								     <td width="30%"> <s:property value="#respData.ACCOUNTNAME"/>
 								     </td>

							       </tr>

							        <tr class="even">
								     <td width="20%"><label for="Account Currency Code"><strong>Account Currency Code</strong></label></td>
								     <td width="30%"> <s:property value="#respData.ACCTCURRCODE"/>
 								     </td>
								     <td width="20%"><label for="Branch Code"><strong>Branch Code</strong></label></td>
								     <td width="30%"> <s:property value="#respData.BRANCHCODE"/>
 								     </td>

							       </tr>

							       <tr class="even">
								     <td width="20%"><label for="Email"><strong>Email</strong></label></td>
								     <td width="30%"> <s:property value="#respData.EMAIL"/></td>
								     <td width="20%"><label for="Mobile"><strong>Mobile</strong></label></td>
								     <td width="30%"> <s:property value="#respData.MOBILE"/>
 								     </td>

							       </tr>

							       <tr class="even">
								     <td width="20%"><label for="SchemeDesc"><strong>Merchant Id</strong></label></td>
								     <td width="30%"> <s:property value="#respData.ORGANIGATION_ID"/></td>
								     <td width="20%"><label for="SchemeType"><strong>Merchant Type</strong></label></td>
								     <td width="30%"> <s:property value="#respData.MERCHANT_TYPE"/>
 								     </td>

							       </tr>


							       <tr class="even">
								     <td width="20%"><label for="SubProductCode"><strong>Status</strong></label></td>
								     <td width="30%"> <s:property value="#respData.STATUS"/></td>
								    <td></td>
									<td></td>
							       </tr>
							   </table>
						</fieldset>
						</div>
					</div>
				</div>


<input type="hidden" id="merchanttype" name="merchanttype" value="<s:property value="#respData.MERCHANT_TYPE"/>" />

<div id="dfg">


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
					<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable ">
				           <tr class="even">
								<td width="20%"><label for="Business Owner"><strong>Business Owner</strong></label></td>
								<td width="30%"> <s:property value="#respData.MANAGERNAME"/>
 								</td>
								<td></td>
								<td></td>
							</tr>

							 <tr class="odd">
								<td width="20%"><label for="Date Of Birth"><strong>Date Of Birth</strong></label></td>
								<td width="30%"> <s:property value="#respData.DOB"/>
 								</td>


 								<td width="20%"><label for="Gender"><strong>Gender</strong></label></td>
								<td width="30%"> <s:if test="%{#respData.GENDER== 1}">
                                                    Male
                                                  </s:if>
                                               <s:else>
                                                Female
                                                  </s:else>

							 </tr>
							 <tr class="even">


 								<td width="20%"><label for="ID Number"><strong>ID Type</strong></label></td>
								<td width="30%"> <s:if test="%{#respData.ID_TYPE== 1}">
                                                    National ID
                                                  </s:if>
                                                  <s:elseif test="%{#respData.ID_TYPE== 2}">
                                                  Driving Lisence
                                                   </s:elseif>
                                                    <s:elseif test="%{#respData.ID_TYPE== 3}">
                                                    Voter Card
                                                   </s:elseif>
                                               <s:else>
                                                Passprt
                                                  </s:else>

 								</td>



								<td width="20%"><label for="ID Number"><strong>ID Number</strong></label></td>
								<td width="30%"> <s:property value="#respData.ID_NUMBER"/></td>
						    </tr>

						<%-- 	<tr class="odd">

							 <td width="20%"><label for="ID Number"><strong>Telco Type</strong></label></td>
								<td width="30%"> <s:property value="#respData.TELCO_TYPE"/>
 								</td>

 								<td ><label for="Telephone Number 1"><strong>Telephone Number 1</strong></label></td>
								<td> <s:property value="#respData.TELEPHONE_NUM1"/>
 								</td>

						    </tr> --%>

						      <tr class="odd">

								<td ><label for="Telephone Number 2 "><strong>Telephone Number</strong></label>	</td>
								<td > <s:property value="#respData.TELEPHONE_NUM2"/>
 								</td>
								<td width="20%"><label for="ID Number"><strong>Nationality</strong></label></td>
								<td width="30%"><s:property value="#respData.NATIONALITY"/>

 								</td>


						   </tr>


							<tr class="even">
								<td ><label for="Address Line 1"><strong>Address Line 1</strong></label></td>
								<td > <s:property value="#respData.ADDRESSLINE1"/>
 								</td>
								<td ><label for="Address Line 2 "><strong>Address Line 2 </strong></label></td>
								<td > <s:property value="#respData.ADDRESSLINE2"/>
 								</td>
							</tr>

							<tr class="odd">
								<td ><label for="Local Government"><strong>Local Government</strong></label></td>
								<td > <s:property value="#respData.LOCALGOVERNMENT"/>
 								</td><td ><label for="State"><strong>State</strong></label></td>
								<td > <s:property value="#respData.STATE"/>
 								</td>

							</tr>
							<tr class="even">
								<td ><label for="County"><strong>Country</strong></label></td>
								<td > <s:property value="#respData.COUNTRY"/>
 								</td>
								<td><label for="City"><strong>City/Town</strong></label></td>
								<td > <s:property value="#respData.CITY"/>
 								</td>
							</tr>
						  <tr class="even">
								<td ><label for="Longitude"><strong>Longitude</strong></label></td>
								<td > <s:property value="#respData.LONGITUDE"/>
 								</td>
								<td><label for="Latitude"><strong>Latitude</strong></label></td>
								<td > <s:property value="#respData.LATITUDE"/>
 								</td>
							</tr>
					</table>
				</fieldset>
			</div>

		</div>
		</div>
</div>
		       
		<input type="hidden" name="status" id="status"/>

		<div class="form-actions">

				<input type="button" class="btn btn-danger" name="btn-back" id="btn-back" value="Back"  />
				<input type="button" class="btn btn-success" name="btn-deactive-cnf" id="btn-deactive-cnf" value="Confirm" />
				<span id ="error_dlno" class="errors"></span>

  			

				  <input type="hidden" name="formName" id="formName" value="Merchant"/>
			</div>
	</div>

</form>
</body>
</html>
