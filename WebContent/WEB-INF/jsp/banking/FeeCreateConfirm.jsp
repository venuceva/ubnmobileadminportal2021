
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>IMPERIAL</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
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
 <script type="text/javascript" >
$(document).ready(function() { 
	
	val = 1;
	rowindex = 1;
	colindex = 0;
	bankfinalData="${responseJSON.multiSlabFeeDetails}"; //200@MYR@A@112@1212@P@IMPL10000023@121@Pame00000000001@12121
	
	bankfinalDatarows = "";
	eachfieldArr = "";
	appendTxt = "";
	
	if(bankfinalData != "") {
		var bankfinalDatarows = bankfinalData.split("#");
		
		if(val % 2 == 0 ) {
			addclass = "even"; 
		} else {
			addclass = "odd"; 
		}   
		val++; 
		for(var i=0;i<bankfinalDatarows.length;i++){
			  eachrow=bankfinalDatarows[i];
			  eachfieldArr=eachrow.split("@"); 
			  appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
 				"<td>"+rowindex+"</td>"+
				"<td>"+eachfieldArr[0]+"</td>"+	
				"<td>"+eachfieldArr[1]+"</td>"+	
				"<td>"+(eachfieldArr[2] == 'A' ? 'Amount' : 'Count' )+"</td>"+	
				"<td>"+(eachfieldArr[3] )+"</td>"+	
				"<td>"+eachfieldArr[4]+" </td>"+ 
				"<td>"+(eachfieldArr[5] == 'P' ? 'Percentile' : 'Flat')+" </td>"+  
				"<td>"+eachfieldArr[6]+" </td>"+  
				"<td>"+eachfieldArr[7]+" </td>"+  
				"<td>"+eachfieldArr[8]+" </td>"+  
				/* "<td>"+eachfieldArr[9]+" </td>"+
				"<td>"+eachfieldArr[10]+" </td>"+
				"<td>"+eachfieldArr[11]+" </td>"+   */
				"</tr>";
				$("#tbody_data1").append(appendTxt);	  
			rowindex = ++rowindex;
			colindex = ++ colindex; 
		} 
	}
	
	val = 1;
	rowindex = 1;
	colindex = 0;
	bankfinalData="${responseJSON.offusTrnDetails}"; 
	bankfinalDatarows = "";
	eachfieldArr = "";
	appendTxt = "";
	if(bankfinalData != "") {
		var bankfinalDatarows = bankfinalData.split("#");
		
		if(val % 2 == 0 ) {
			addclass = "even"; 
		} else {
			addclass = "odd"; 
		}  
		
		val++; 
	 
		for(var i=0;i<bankfinalDatarows.length;i++){
			eachrow=bankfinalDatarows[i];
			eachfieldArr=eachrow.split(","); 
			appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
 				"<td>"+rowindex+"</td>"+
				"<td>"+eachfieldArr[0]+"</td>"+	
				"<td>"+eachfieldArr[1]+"</td>"+
				"</tr>";
				$("#tbody_data3").append(appendTxt);	  
			rowindex = ++rowindex;
			colindex = ++ colindex; 
		} 
	}
	
	if('${responseJSON.serviceTrans}' == 'ONUS') {
		$("#acq-bin-chk").hide();
		$("#disp1").hide();
		$("#disp2").hide();
	}
	
}); 

function getServiceScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/serviceMgmtAct.action';
	$("#form1").submit();
	return true;
}
function createSubService(){ 
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/insertFeeAct.action';
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
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt;  </span> </li>
			  <li> <a href="serviceMgmtAct.action">Fee Management</a> <span class="divider"> &gt;&gt;  </span></li>
			  <li> <a href="#">Create Fee</a> <span class="divider"> &gt;&gt;  </span></li>
			  <li><a href="#">Create Fee Confirmation</a></li>
			</ul>
		</div>
		<table height="3">
				 <tr>
					<td colspan="3">
						<div class="errors" id="messages">${responseJSON.actionmessage}</div>
						
					</td>
				</tr>
			 </table>
		<div class="row-fluid sortable"><!--/span--> 
			<div class="box span12"> 
				 
				<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Create Fee Confirmation
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
										<td width="20%"><label for="Service ID"><strong>Service Code</strong></label></td>
										<td width="30%"> ${responseJSON.serviceCode}
											<input name="serviceCode" type="hidden" id="serviceCode" class="field" value="${responseJSON.serviceCode}"  />
										</td>
										<td width="20%"> <label for="Service Name"><strong>Service Name</strong></label> </td>
										<td width="30%"> ${responseJSON.serviceName}
											<input name="serviceName" type="hidden"  id="serviceName" class="field"  value="${responseJSON.serviceName}" />
											<input name="subServiceCode"  type="hidden" id="subServiceCode" class="field" value="${responseJSON.serviceCode}">
											<input name="subServiceName"  type="hidden" id="subServiceName" class="field" value="${responseJSON.serviceName}" />
										</td>
									</tr>
									
									<tr class="even">
										<td > <label for="Fee Code"><strong>Fee Code</strong></label> </td>
										<td> ${responseJSON.feeCode}
											<input name="feeCode" type="hidden" id="feeCode" class="field" value="${responseJSON.feeCode}" />
										</td>
										<td> <label for="Service"><strong>Fee Name </strong></label> 	</td>
										<td> ${feename}
											<input name="feename"  type="hidden" id="feename" class="field" value="${responseJSON.feename}" />
										</td>  
									</tr> 
									<tr class="even">
										<td> <label for="Service"><strong>ON-US/OFF-US Flag </strong></label> 	</td>
										<td> ${responseJSON.serviceTrans}
											<input name="serviceTrans"  type="hidden" id="serviceTrans" class="field" value="${responseJSON.serviceTrans}" />
										</td> 
										<td><span id="disp1">OFF-US Fee</span></td>
										<td ><span id="disp2"> ${responseJSON.offUsFee} <input type="hidden" name="offUsFee" id="offUsFee" value="${responseJSON.offUsFee}"/></span></td>
									</tr> 
									
							</table>
						</fieldset>
				</div> 
			<div id="acq-bin-chk" class="box-content">
 				<fieldset>  
					<table id="mytable" width="950" border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable ">
						<thead>
							<tr>
								<th>Sno</th>
 								<th>Bin</th>
								<th>Acquirer Id</th>
							</tr>
						</thead>    
						<tbody id="tbody_data3"> 
						</tbody>
					</table> 
				<fieldset>  
			</div> 
			<div id="secondaryDetails" class="box-content">
				<fieldset> 
					<table id="mytable" width="950" border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable ">
						<thead>
							<tr>
								<th>Sno</th>
								<th>Frequency</th>
								<!-- <th>Channels</th> -->
								<th>Currency</th>
								<th>Condition</th>
								<th>From Amount/Count</th>
								<th>To Amount/Count</th> 
								<th>F/P</th> 
								
								<th>Bank Amount</th> 
						 
								<th>Agent Amount</th>
							
								<th>serviceTax</th>  
							</tr>
						</thead>    
						<tbody id="tbody_data1"> 
						</tbody>
					</table> 
				</fieldset> 
			</div>
		</div>
	</div>   
	<div class="form-actions"> 
		<a  class="btn btn-danger" href="#" onClick="">Back</a>
		<a  class="btn btn-success" href="#" onClick="createSubService()">Confirm</a>
	</div> 
</div>

<input type="hidden" name="partnerDetails" id="partnerDetails" value="${responseJSON.partnerDetails}" /> 
<input type="hidden" name="multiSlabFeeDetails" id="multiSlabFeeDetails" value="${responseJSON.multiSlabFeeDetails}" /> 
<input type="hidden" name="offusTrnDetails" id="offusTrnDetails" value="${responseJSON.offusTrnDetails}" /> 
</form>
</body>
</html>
