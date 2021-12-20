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
var TerminalMigrationrules = {
		rules : {
			merchantID : { required : true }, 
			storeID : { required : true },
			terminalID : { required : true },
			updatemerchantID : { required : true },
			updatestoreID : { required : true }
			
		},		
		messages : {
			merchantID : { 	required : "Please Select Merchant ID"  },
			storeID : { 	required : "Please Store ID."  },
			terminalID : { 	required : "Please Select Terminal ID."  },
			updatemerchantID : { 	required : "Please Select Migration Merchant ID"  },
			updatestoreID : { 	required : "Please Select Migration StoreID"  } 
		

		} 
	};

$(document).ready(function() {
	$("#terminalDetails").hide();
	var mydata ='${responseJSON.MERCHANT_ID}';
	if(mydata != '') {
    	var json = jQuery.parseJSON(mydata);
    	$.each(json, function(i, v) {
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    $('#merchantID').append(options);
    	});
    	
    	$.each(json, function(i, v) {
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    $('#updatemerchantID').append(options);
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

function getnewStoreID()
{

	var updatemerchantID=$('#updatemerchantID').val();

	var formInput='updatemerchantID='+updatemerchantID;
	
	$.getJSON('retrivenewStoresIDAct.action', formInput,function(data) {
	
		var json = data.responseJSON.STORE_LIST;
		console.log(json);
	//	alert(json);
	//	$('#storeId').find('option:not(:first)').remove();
		//$('#storeId').trigger("liszt:updated");
		 $('#updatestoreID').find('option:not(:first)').remove();
		$('#updatestoreID').trigger("liszt:updated"); 
		$.each(json, function(i, v) {
			var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);  
			$('#updatestoreID').append(options);
			 $('#updatestoreID').trigger("liszt:updated"); 
			  
		});
		
		
		
	});
}



function getTerminalID()
{
	var storeID=$('#storeID').val();
	//alert("storeID:"+storeID);
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

function getTerminalDetails()
{
	var terminalID=$('#terminalID').val();
	//alert("terminalID:"+terminalID);
	var formInput='terminalID='+terminalID;
	
	$.getJSON('retriveTerminalDeatils.action', formInput,function(data) {
	
		var terminalDetails = data.responseJSON.TERMINAL_DETAILS;
		if(terminalDetails.length>0)
			{
			//console.log("assignUserData:::"+assignUserData);
			//var bankData ='${responseJSON.MERCHANTfEEDETAILS}';
			//var json = jQuery.parseJSON(terminalDetails);
			console.log(terminalDetails);
			var val = 1;
			var rowindex = 0;
			var colindex = 0;
			var addclass = "";
			$("#merchantTBody").empty();
			$.each(terminalDetails, function(index, v) {

				if(val % 2 == 0 ) {
					addclass = "even";
					val++;
				}
				else {
					addclass = "even";
					val++;
				}
				
				var rowCount = $('#merchantTBody > tr').length;

					colindex = ++ colindex;
				
				var appendTxt = "<tr bgcolor='#FFFFFF' id='"+rowindex+"'index='"+rowindex+"'> "+
				"<td   >"+colindex+"</td>"+
				"<td>"+v.merchantID+"</span> </td>"+
				"<td>"+v.storeID+"</span> </td>"+
				"<td>"+v.terminalID+"</span></td>"+
				"<td>"+v.termianlMake+"</span></a></td>"+
				"<td>"+v.makerid+"</span></a></td>"+
				"<td>"+v.makerdatetime+"</span></a></td></tr>";
				$("#merchantTBody").append(appendTxt);
				rowindex = ++rowindex;
			});
			
			$("#terminalDetails").show();
		
				
			}else
				{
				$("#terminalDetails").hide();
				}
		
	});
}
$('#btn-submit').live('click', function () { 
	if($('#form1').validate(TerminalMigrationrules)){
  $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/TerminalMicrationConf.action';
	$("#form1").submit(); 
	return true; 
	} else {
		return false;
	} 
});

$('#btn-cancel').live('click', function () { 
	//if($('#form1').validate(TerminalMigrationrules)){
  $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
	$("#form1").submit(); 
	return true; 
});


$(document).on('change','select',function(event) { 

	if($('#'+$(this).attr('id')).next('label').text().length > 0) {
	 $('#'+$(this).attr('id')).next('label').text(''); 
	 $('#'+$(this).attr('id')).next('label').remove();
	}

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
				  <li><a href="#">Terminal Migration</a></li>
				</ul>
			</div>
			<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i> Current Terminal Details
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
										<td width="20%"><strong><label for="Merchant ID">Merchant ID<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="merchantID" name="merchantID" class="chosen-select"
											    required="required" onChange="getStoreID()">
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Store ID">Store ID<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="storeID" name="storeID" class="chosen-select"
											    required="required" onChange="getTerminalID()">
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Terminal ID">Terminal ID<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="terminalID" name="terminalID" class="chosen-select"
											    required="required" onChange="getTerminalDetails()">
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
			<div class="row-fluid sortable" id="terminalDetails"><!--/span--> 
			<div class="box span12">
                 <div class="box-header well" data-original-title>Terminal Information
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
							<th>Terminal Make</th>
							<th>MakerID</th>
							<th>Maker Data and time</th>
						</tr>
					</thead> 
					<tbody  id="merchantTBody">
					</tbody>
				</table>
			</div>
             </div>
           </div> 	 <!--/end span--> 
           
           
           <div class="row-fluid sortable" ><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Migrating To 
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
										<td width="20%"><strong><label for="Merchant ID">Merchant ID<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="updatemerchantID" name="updatemerchantID" class="chosen-select"
											    required="required" onChange="getnewStoreID()">
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Store ID">Store ID<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="updatestoreID" name="updatestoreID" class="chosen-select"
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
				 
			<span id="multi-row-data" name="multi-row-data" class="multi-row-data" style="display:none" ></span>
			<div class="form-actions"> 
				
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" />&nbsp;
				<input type="button" class="btn" name="btn-home" id="btn-cancel" value="Cancel" /> 
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div> 
	</div> 
</form>
</body>
</html>
