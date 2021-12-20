
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>IMPERIAL</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 <script type="text/javascript" > 
function TerminateConfirm(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/storeTermnateConfirmAct.action';
	$("#form1").submit();
	return true;
}
function getGenerateScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateMerchantAct.action';
	$("#form1").submit();
	return true;
}
		
$(document).ready(function () {
	var tds=new Array();
	var merchantData ='${responseJSON.MerchantDashboard}';
	var json = jQuery.parseJSON(merchantData);
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";
	var totalfee="";
	var fee="";
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
		var serviceName=(v.serviceName == undefined) ? "" : v.serviceName;
		var subServiceName=(v.subServiceName == undefined) ? "" : v.subServiceName;
		var merchantname =(v.merchantname == undefined) ? "" : v.merchantname;
		var feeCode=(v.feeCode == undefined) ? "" : v.feeCode;
	    var slab_from=(v.slab_from == undefined) ? "" : v.slab_from;
		var slab_to=(v.slab_to == undefined) ? "" : v.slab_to;
		var bank_fee=(v.bank_fee == undefined) ? "" : v.bank_fee;
		var merchant_fee=(v.merchant_fee == undefined) ? "" : v.merchant_fee;
		var servicetax=(v.servicetax == undefined) ? "" : v.servicetax;
		var percentageplat=(v.percentageplat == undefined) ? "" : v.percentageplat;
		var fee_name=(v.fee_name == undefined) ? "" : v.fee_name; 
		
		/* fee =(parseFloat(bank_fee)+parseFloat(merchant_fee))*parseFloat(servicetax); */
	
		fee =parseFloat(bank_fee)*parseFloat(servicetax); 
	
	var tax=fee/100;
	tax=tax+(parseFloat(bank_fee));

		index=colindex-1;
 		var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
		"<td >"+colindex+"</td>"+
		"<td>"+serviceName+"</td>"+	
		"<td>"+subServiceName+"  </td>"+ 
		"<td>"+merchantname+"  </td>"+ 
		"<td>"+feeCode+" </td>"+ 
		"<td>"+fee_name+" </td>"+ 
		"<td>"+slab_from+"</td>"+
		"<td>"+slab_to+"</td>"+
		"<td>"+bank_fee+"</td>"+
		"<td>"+servicetax+"%"+"</td>"+
		"<td>"+tax+"</td>"+
		"<td>"+percentageplat+"</td>"+
		"<td>"+merchant_fee+"</td></tr>";
		$("#merchantTBody").append(appendTxt);	
		rowindex = ++rowindex;
	}); 
		
	$('#btn-cancel').live('click', function () { 
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/serviceMgmtAct.action';
		$("#form1").submit();
		return true;
	});
		
});
		
		
$(document).on('click','#MerchantView',function(event) {  
	index = $(this).attr('index');
	var searchRow = "DataTables_Table_0 tbody tr:eq("+(index)+") td"; 
	$('#'+searchRow).each(function (indexTd) {
		 if (indexTd == 1) {
			merchantId=$(this).text();
		 }   if(indexTd == 2) {
			merchantName=$(this).text();
		 }   if(indexTd == 3) {
			// email ids
		 }   if(indexTd == 4) {
		 }  
	}); 
	// $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantViewDashboardAct.action?merchantID='+merchantId;
	return true;
});

$(document).on('click','#StoreView',function(event) {  
	index = $(this).attr('index');
	var searchRow = "DataTables_Table_0 tbody tr:eq("+(index)+") td"; 
	$('#'+searchRow).each(function (indexTd) {
		 
		 if (indexTd == 1) {
		 }   if(indexTd == 2) {
			storeId=$(this).text();
		 }   if(indexTd == 3) {
		 }   if(indexTd == 4) {
		 }  
	}); 
	// $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/viewStoreDashboardAct.action?storeId='+storeId;
	return true;
});

$(document).on('click','#TerminalView',function(event) {  
	index = $(this).attr('index');
	var searchRow = "DataTables_Table_0 tbody tr:eq("+(index)+") td"; 
	$('#'+searchRow).each(function (indexTd) {
		 if (indexTd == 1) {
		 }   if(indexTd == 2) {
		 }   if(indexTd == 3) {
			terminalID=$(this).text();
		 }   if(indexTd == 4) {
		 }  
	}); 
	// $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/viewTerminalDashboardAct.action?terminalID='+terminalID;
	return true;
});

</script>
<style type="text/css" media="print">
    @media print
    {
    #non-printable { display: none; }
    #printable {
    display: block;
    width: 100%;
    height: 100%;
    }
    }
</style> 
</head> 
<body>
<form name="form1" id="form1" method="post" action="">	
	<div id="content" class="span10">  
		<div>
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="serviceMgmtAct.action">Fee Management</a> <span class="divider"> &gt;&gt; </span></li>
			  <li><a href="#">Fee Dashboard </a></li>
			</ul>
		</div> 
		<div class="row-fluid sortable"><!--/span--> 
			<div class="box span12">
                 <div class="box-header well" data-original-title>Fee Information
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
							<th>Service Name</th>
							<th>Merchant Code</th>
							<th>Merchant Name</th>
							<th>Fee Code</th>
							<th>Fee Name</th>
							<th>Slab From</th>
							<th>Slab To</th>
							<th>Bank Fee</th>
							<th>Service Tax</th>
							<th>Total Fee</th>
							<th>Percentage /Flat</th>
							<th>Merchant  Fee</th>
						</tr>
					</thead> 
					<tbody  id="merchantTBody">
					</tbody>
				</table>
			</div>
             </div>
           </div> 
			<div  class="form-actions"> 
 			  <input type="button" class="btn btn-danger " type="text"  name="btn-cancel" id="btn-cancel" value="Back" width="100" ></input>&nbsp;
 			<!-- <input type="button" id="non-printable" class="btn btn-primary" onclick="JavaScript:window.print();" value="print" /> &nbsp; -->
		</div>
	</div>
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
