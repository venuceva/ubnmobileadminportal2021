
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


$('#btn-back').live('click',function() {

	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthListAct.action";
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
					  <li> <a href="#">All Authorization </a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#"> Super Agent Authorization Confirmation </a></li>
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
							<i class="icon-edit"></i>Super Agent Creation
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
								     <td width="30%"> <s:property value="#respData.SCHEMEDESC"/></td>
								     <td width="20%"><label for="SchemeType"><strong>Merchant Type</strong></label></td>
								     <td width="30%"> <s:property value="#respData.SCHEMETYPE"/>
 								     </td>

							       </tr>


							       <tr class="even">
								     <td width="20%"><label for="SubProductCode"><strong>Status</strong></label></td>
								     <td width="30%"> <s:property value="#respData.SUBPRODUCTCODE"/></td>
								    <td></td>
									<td></td>
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
                                                    NationalID
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

							<tr class="odd">

							 <td width="20%"><label for="ID Number"><strong>Telco Type</strong></label></td>
								<td width="30%"> <s:property value="#respData.TELCO_TYPE"/>
 								</td>

 								<td ><label for="Telephone Number 1"><strong>Telephone Number 1</strong></label></td>
								<td> <s:property value="#respData.TELEPHONE_NUM1"/>
 								</td>

						    </tr>

						      <tr class="odd">

								<td ><label for="Telephone Number 2 "><strong>Telephone Number 2</strong></label>	</td>
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

								<td ><label for="Langitude"><strong>Longitude</strong></label></td>
								<td > <s:property value="#respData.LONGITUDE"/>
 								</td>
							    <td><label for="latitude"><strong>Latitude</strong></label></td>
								<td ><s:property value="#respData.LATITUDE"/>
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
				 <li> <strong>Authorize&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </strong><input  name="authradio" id="authradio"  class='center-chk' type='radio' value='A' />&nbsp;&nbsp; </li>
				 <li> <strong>Reject&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </strong><input  name="authradio" id="authradio"  class='center-chk' type='radio' value='R' /> &nbsp;&nbsp;&nbsp;</li>
				</ul>
		</div>

		<div class="form-actions">
				<input type="button" class="btn btn-danger" name="btn-back" id="btn-back" value="Back"  />
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Confirm"  />
				
				<span id ="error_dlno" class="errors"></span>

  			   <input name="STATUS" type="hidden" id="STATUS" value="${STATUS}" />
  				<input name="AUTH_CODE" type="hidden" id="AUTH_CODE" value="${AUTH_CODE}"  />
				<input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
				<input type="hidden" name="DECISION" id="DECISION" />

				  <input type="hidden" name="formName" id="formName" value="Super Agent"/>
			</div>
	</div>

</form>
</body>
</html>
