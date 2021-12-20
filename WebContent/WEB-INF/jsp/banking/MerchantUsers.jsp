 <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
  
    
 <link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.css' />  
 
<style type="text/css">
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
  font-weight: bold;
  color: red;
  padding: 2px 8px;
  margin-top: 2px;
}
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
 
</style>  
 
<script type="text/javascript">

$(document).ready(function () {
	
	$('#merstu_details').hide();
	
	var merchantInfo = '${responseJSON.MERCHANT_LIST}';
	var jsonArray = $.parseJSON(merchantInfo);
    console.log(jsonArray);
	$.each(jsonArray, function(i, v) {
		var options = $('<option/>', {value: v.key, text: v.key}).attr('data-id',i);  
		$('#merchantID').append(options);
		$('#merchantID').trigger("liszt:updated");
	});

	
 $('#merchantID').live('change', function () { 
	var merchantId=$('#merchantID').val();
	$("#merstu_details").show();
	   var formInput='merchantId='+merchantId; 
	   
	   $.getJSON('FillMerchUsersAct.action', formInput,function(data) {
		    var json = data.responseJSON.MERCH_USER_LIST;
		  console.log("JSON Values:"+json);
			var val = 1;
			var rowindex = 0;
			var colindex = 0;
			var addclass = "";
			$("#userGroupTBody").empty();
		   
			$.each(json, function(i, v) {
				
				//$("#merchantID").empty();
				$("#userId").empty();
				$("#userName").empty();
				$("#status").empty();
				$("#dateOfCreation").empty();
				$("#makerId").empty();
				var merchantID=v.MERCH_ID;
				var userId=v.USER_ID;
				var userName=v.USER_NAME;
				var status=v.STATUS;
				var dateOfCreation=v.DATE_OF_CREATION;
				var makerId=v.MAKER_ID;
				
				if(val % 2 == 0 ) {
					addclass = "even";
					val++;
				}
				else {
					addclass = "odd";
					val++;
				}  
				var rowCount = $('#userGroupTBody > tr').length;

				colindex = ++ colindex; 
				
				index=colindex-1;
				i++; 
				var tabData="DataTables_Table_0";
				
				var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+

				"<td >"+i+"</td>"+
				"<td >"+merchantID+"</td>"+
				"<td>"+userId+"</span></td>"+	
				"<td>"+userName+"</span> </td>"+
				"<td>"+status+"</span> </td>"+
				"<td>"+dateOfCreation+"</span> </td>"+
				"<td>"+makerId+"</span> </td></tr>";
				
				$("#userGroupTBody").append(appendTxt);	
				rowindex = ++rowindex;
		});
	   });  
    });
});



$(function(){ 
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

</script>

</head>
<body>
<form name="form1" id="form1" method="post">  
	<div id="content" class="span10"> 
 		<div> 
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="#">Merchant Users View</a> </li> 
			</ul>
		</div>  
		<table height="3">
				<tr>
					<td colspan="3">
						<div class="messages" id="messages">
							<s:actionmessage />
						</div>
						<div class="errors" id="errors">
							<s:actionerror />
						</div>
					</td>
				</tr>
			</table> 	
	  <div class="row-fluid sortable"> 
		<div class="box span12">
					
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Merchant Information
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

				</div>
			</div>  
		<div class="box-content">
			<fieldset>
				  <table width="950" border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable " id="user-details">
					
					        <tr class="odd">
										<td width="20%"><strong><label for="Merchant ID">Merchant ID<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="merchantID" name="merchantID" class="chosen-select"
											    required="required" onChange="">
												<option value="">Select</option>
										</select>
										<span id="merchantID_err" class="errmsg"></span>
										</td>
						   </tr>  	
				  </table>
			</fieldset> 
		</div>
	</div>
  </div>
</div> 

<div id="merstu_details" class="span10"> 
 		 
		<table height="3">
				<tr>
					<td colspan="3">
						<div class="messages" id="messages">
							<s:actionmessage />
						</div>
						<div class="errors" id="errors">
							<s:actionerror />
						</div>
					</td>
				</tr>
			</table> 	
	  <div class="row-fluid sortable" > 
		<div class="box span12">
			 <div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Merchant-Users Information
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>  
		<div class="box-content" >
			<fieldset>
				<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable" 
							id="DataTables_Table_0">
							<thead>
								<tr>
								    <th>S No</th>
									<th>Merchant ID</th>
									<th>User Id</th>
									<th>user Name</th>
									<th>Status</th>
									<th>Date Of Creation</th>
									<th>Maker Id</th>	
								</tr>
							</thead>

							<tbody  id="userGroupTBody">
							</tbody>	
				  </table>
			</fieldset> 
		</div>
	</div>
  </div>
</div> 

</form> 

 <script type="text/javascript" src='<%=ctxstr %>/js/jquery.dataTables.min.js'></script>
 <script type="text/javascript" src='<%=ctxstr %>/js/datatable-add-scr.js'></script> 
 
</body> 
</html>