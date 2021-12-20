<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
 <%@taglib uri="/struts-tags" prefix="s"%> 
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<s:set value="responseJSON" var="respData"/>
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
<script type="text/javascript" > 
$(document).ready(function() {
	
	var mydata ='${responseJSON.MERCHANT_ID}';
	if(mydata != '') {
    	var json = jQuery.parseJSON(mydata);
    	$.each(json, function(i, v) {
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    $('#merchantID').append(options);
    	});
	}	
	
var config = {
	      '.chosen-select'           : {},
	      '.chosen-select-deselect'  : {allow_single_deselect:true},
	      '.chosen-select-no-single' : {disable_search_threshold:10},
	      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	      '.chosen-select-width'     : {width:"95%"}
	    }
	    for (var selector in config) {
	      $(selector).chosen(config[selector]);
	    }
	    

}); 

function getStoreID()
{

	var merchantId=$('#merchantID').val();

	var formInput='merchantId='+merchantId;
	
	$.getJSON('retriveStoresIDAct.action', formInput,function(data) {
	
		var json = data.responseJSON.STORE_LIST;
		console.log(json);
	//	alert(json);
	//	$('#storeId').find('option:not(:first)').remove();
		//$('#storeId').trigger("liszt:updated");
		 $('#storeID').find('option:not(:first)').remove();
		$('#storeID').trigger("liszt:updated"); 
		$.each(json, function(i, v) {
			var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);  
			$('#storeID').append(options);
			 $('#storeID').trigger("liszt:updated"); 
		});
		
	});
}

function getTerminalID()
{
	var storeID=$('#storeID').val();
	alert("storeID:"+storeID);
	var formInput='storeID='+storeID;
	
	$.getJSON('retriveTerminalIDAct.action', formInput,function(data) {
	
		var json = data.responseJSON.TERMINAL_LIST;
		console.log(json);
	//	alert(json);
	//	$('#storeId').find('option:not(:first)').remove();
		//$('#storeId').trigger("liszt:updated");
		 $('#terminalID').find('option:not(:first)').remove();
		$('#terminalID').trigger("liszt:updated"); 
		$.each(json, function(i, v) {
			var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);  
			$('#terminalID').append(options);
			 $('#terminalID').trigger("liszt:updated"); 
		});
		
	});
}

function getUsers()
{
	var terminalID=$('#terminalID').val();
	alert("terminalID:"+terminalID);
	var formInput='terminalID='+terminalID;
	
	$.getJSON('retriveUsersAct.action', formInput,function(data) {
	
		var json = data.responseJSON.USERS_LIST;
		console.log(json);
	//	alert(json);
	//	$('#storeId').find('option:not(:first)').remove();
		//$('#storeId').trigger("liszt:updated");
		 $('#assignUsers').find('option:not(:first)').remove();
		$('#assignUsers').trigger("liszt:updated"); 
		$.each(json, function(i, v) {
			var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);  
			$('#assignUsers').append(options);
			 $('#assignUsers').trigger("liszt:updated"); 
		});
		
	});
}
$('#btn-submit').live('click', function () { 
	
  $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/InsertAssignTerminalUsers.action';
	$("#form1").submit(); 
	return true;
});

$(document).ready(function () {
	var tds=new Array();
	var assignUserData ='${responseJSON.USER_DETAILS}';

	var json = jQuery.parseJSON(assignUserData);
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
		var rowCount = $('#merchantTBody > tr').length;
		
		//rowindex = ++rowindex;
		colindex = ++ colindex; 
		var merchantID=(v.merchantID == undefined) ? "" : v.merchantID;
		var storeID=(v.storeID == undefined) ? "" : v.storeID;
		var terminalID=(v.terminalID == undefined) ? "" : v.terminalID;
		var Userslist=(v.Userslist == undefined) ? "" : v.Userslist;
		var Maker_id=(v.Maker_id == undefined) ? "" : v.Maker_id;
		var Maker_Dttm=(v.Maker_Dttm == undefined) ? "" : v.Maker_Dttm;
		index=colindex-1;
 		var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
		"<td >"+colindex+"</td>"+
		"<td>"+merchantID+"</span></td>"+
		"<td>"+storeID+"</span></td>"+
		"<td>"+terminalID+"</span></td>"+
		"<td>"+Userslist+"</span></td>"+
		"<td>"+Maker_id+"</span></td>"+
		"<td>"+Maker_Dttm+"</span></td></tr>";
		$("#merchantTBody").append(appendTxt);	
		rowindex = ++rowindex;
	}); 
		
	
		
});
</script>

</head> 
<body>
	<form name="form1" id="form1" method="post" action=""> 
			<div id="content" class="span10"> 
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="#">Merchant  Management</a> <span class="divider"> &gt;&gt; </span></li>
				  <li><a href="#">Assign Terminal</a></li>
				</ul>
			</div>
			<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Assign Terminal
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

							</div>
						</div>

						<div id="primaryDetails" class="box-content">
							<fieldset>   
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd">
										<td width="20%"><strong><label for="Bank Code">Merchant ID<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="merchantID" name="merchantID" class="chosen-select"
											    required="required" onChange="getStoreID()">
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Bank Code">Store ID<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="storeID" name="storeID" class="chosen-select"
											    required="required" onChange="getTerminalID()">
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Bank Code">Terminal ID<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="terminalID" name="terminalID" class="chosen-select"
											    required="required" onChange="getUsers()">
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Bank Code">Assign Users<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="assignUsers" name="assignUsers" class="chosen-select"
											    required="required" >
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									 
								</table>
							 </fieldset> 
							</div>
							 
						</div>
					</div> <!-- end of span -->
			<div class="row-fluid sortable"><!--/span--> 
			<div class="box span12">
                 <div class="box-header well" data-original-title>Assign Terminal User Information
				  <div class="box-icon"> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
				  </div>
			</div>
                 
			<div class="box-content"> 
				<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable" 
					id="DataTables_Table_0">
					<thead>
						<tr >
							<th >S No</th>
							<th>Merchant ID</th>
							<th>Store ID</th>
							<th>Terminal ID</th>
							<th>Users</th>
							<th>MakerID</th>
							<th>Maker Data and time</th>
						</tr>
					</thead> 
					<tbody  id="merchantTBody">
					</tbody>
				</table>
			</div>
             </div>
           </div> 	 
				 
			<span id="multi-row-data" name="multi-row-data" class="multi-row-data" style="display:none" ></span>
			<div class="form-actions"> 
				
				<input type="button" class="btn btn-info" name="btn-submit" id="btn-submit" value="Submit" />&nbsp;
				<input type="button" class="btn" name="btn-back" id="btn-back" value="Back" /> 
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div> 
	</div> 
</form>
</body>
</html>
