
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
  
	var data='${responseJSON.tpkKey}';
	var data1=data.substr(0,4);
	var data2= data.substr(data.length - 4);
	var cData=data1+"***************************"+data2;
	$("#tmkData").append(cData);
});
 

function getGenerateMerchantScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/TerminalGrid.action';
	$("#form1").submit();
	return true;
}

$(document).ready(function () {
	var tds=new Array();
	var userData ='${responseJSON.USER_LIST}';
	console.log(userData);
	var json = jQuery.parseJSON(userData);
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";
	$.each(json, function(i, v) {
		if(val % 2 == 0 ) {
			addclass = "even";
			val++;
		}
		else {
			addclass = "odd";
			val++;
		}  
		var rowCount = $('#UserTBody > tr').length;
		
		//rowindex = ++rowindex;
		colindex = ++ colindex; 
		var TerminalID=(v.TerminalID == undefined) ? "" : v.TerminalID;
		var UserID=(v.UserID == undefined) ? "" : v.UserID;
		var StoreID=(v.StoreID == undefined) ? "" : v.StoreID;
		var MerchantID=(v.MerchantID == undefined) ? "" : v.MerchantID;
		var MakerID=(v.MakerID == undefined) ? "" : v.MakerID;
		var MakerDttm=(v.MakerDttm == undefined) ? "" : v.MakerDttm;
		index=colindex-1;
		//alert(index);
			var tabData="DataTables_Table_0";
			
			
		//alert(tds);
		var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
		"<td >"+colindex+"</td>"+
		"<td> "+TerminalID+" </span></td>"+	
		"<td>  "+UserID+"  </span> </td>"+ 
		"<td>  "+StoreID+" </span> </td>"+ 
		"<td>"+MerchantID+"</span></td>"+
		"<td>"+MakerID+"</span></td>"+
		"<td>"+MakerDttm+"</span></td></tr>";
			
		$("#UserTBody").append(appendTxt);	
		rowindex = ++rowindex;
	});
	
});

</script> 
</head> 
<body>
	<form name="form1" id="form1" method="post">	
	
	 <div id="content" class="span10">  
		 
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Terminal View </a></li>
					</ul>
				</div>
				 
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12">  
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Terminal Information
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>  
								
						<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="Merchant ID"><strong>Merchant ID</strong></label></td>
								<td width="30%"><input name="merchantID" type="text" id="merchantID" class="field" value=" ${responseJSON.merchantID}" readonly></td>
							<!-- 	<td width="20%">
								
								<select id="merchantID" name="merchantID"  data-placeholder="Choose  Merchant Id..." class="chosen-select-width" style="width: 220px;" required=true >
								<option value="">Select</option>
								</select></td> -->
								
								<td width="20%"><label for="Store ID"><strong>Store ID</strong></label></td>
								<td width="30%"><input name="storeId"  type="text" id="storeId" class="field"  value="${responseJSON.storeId}" readonly  > </td>
								<!-- <td>
								<select id="storeId" name="storeId" data-placeholder="Choose office Store Id..." class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option></select>
							</td> -->
							</tr>
							<tr class="odd">
								<td><label for="Terminal ID"><strong>Terminal ID</strong></label></td>
								<td><input name="terminalID" type="text"  id="terminalID" class="field" value="${responseJSON.terminalID}" readonly></td> 
								<td ><label for="Terminal Make"><strong>Terminal Make</strong></label></td>
								<td ><input name="terminalMake" type="text" class="field" id="terminalMake" value=" ${responseJSON.terminalMake}" /></td>
								
							</tr> 		
							 <tr  class="even">
								<td><label for="MSIDN Number"><strong>MSIDN Number</strong></label></td>
								<td><input name="MsisdnNumber" id="MsisdnNumber" class="field" value="" type="text" maxlength="8" /> <span id="MsisdnNumber_err" class="errmsg"> </span></td>		
								<td ><label for="Serial Number"><strong>Serial Number</strong></label></td>
								<td ><input name="serialNumber" id="serialNumber" class="field" value="" type="text" maxlength="8" /> <span id="serialNumber_err" class="errmsg"> </span></td>
								
							</tr>
							
							<tr class="odd">
								<!-- <td><strong><label for="Terminal Usage">Terminal Usage<font color="red">*</font></label></strong></td>
								<td ><input name="terminalUsage" type="text" class="field" id="terminalUsage" value="${terminalUsage}" /></td> -->
								<td ><label for="Model Number "><strong>Model Number</strong></label></td>
								<td ><input name="modelNumber"  type="text" id="modelNumber" class="field" value=" ${responseJSON.modelNumber}"  /> </td>
								
								<td></td>
								<td></td>
							</tr> 
							 <tr  class="even" style="display:none">
								<td><label for="Valid From"><strong>Valid From (dd-mon-yyyy)</strong></label></td>
								<td><input name="validFrom" id="validFrom" class="field" value="${validFrom}" type="text"  readonly/></td>
								<td><label for="Valid Thru"><strong>Valid Thru (dd-mon-yyyy)</strong></label></td>
								<td><input name="validThru" type="text" class="field" id="validThru"   value="" /></td>	
							</tr>
							<tr class="odd">
								<td width="20%"><label for="Status"><strong>Status</strong></label></td>
								<td><input name="validFrom" id="validFrom" class="field" value=" ${responseJSON.status}" type="text"  /></td>
								<td width="20%"><label for="Date Created"><strong>Date Created</strong></label></td>
								<td ><input name="validFrom" id="validFrom" class="field" value=" ${responseJSON.TERMINAL_DATE}" type="text" readonly /> </td>
							</tr>
							
						</table>
						<input name="pinEntry" type="hidden" class="field" id="pinEntry"  value="NO"  />
						</fieldset>
						</div> 
						 
						</div> 
						</div>
				<div class="row-fluid sortable"><!--/span-->

					<div class="box span12">
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>TMK Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

						</div>
					</div>  
					 
					<div id="feeDetails" class="box-content"> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1"  
							class="table table-striped table-bordered bootstrap-datatable" >
							<tr class="even">
								<!-- <td width="20%"><strong><label for="Status">TMK Index</label></strong></td> -->
								<td width="20%"><label for="TMK Index"><strong>TMK Index</strong></label></td>
								<td width="30%"> ${responseJSON.tmkIndex}</td>
								<td width="20%"><label for="TMK Key"><strong>TMK Key</strong></label></td>
								<td width="30%"> <div id="tmkData"></div>
								 </td>
							</tr>
							
						</table> 
					</div>
					</div>
					</div>
					
					<div class="row-fluid sortable"><!--/span-->
        
			<!-- <div class="box span12">
        
                  <div class="box-header well" data-original-title>User Terminal Information
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
				  </div>
                  
            <div class="box-content">
				<fieldset>
					<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable" 
						id="DataTables_Table_0" >
						<thead>
							<tr >
								<th >S No</th>
								<th>Terminal ID</th>
								<th>User ID</th>
								<th>Store ID</th>
								<th>Merchant ID</th>
								<th>Created By </th>
								<th>Created Date</th>
							</tr>
						</thead> 
						<tbody  id="UserTBody">
						</tbody>
					</table>
				</fieldset> 
		</div>
		</div> -->
		</div>
					 
					
			<div class="form-actions">
					<!-- <a  class="btn btn-danger" href="#" onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp; -->
					<a  class="btn btn-danger" href="#" onClick="getGenerateMerchantScreen()">Next</a>
			</div> 
	</div> 
</form>	
</body>
</html>
