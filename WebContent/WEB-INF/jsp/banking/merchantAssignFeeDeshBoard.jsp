
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
	var merchantData ='${responseJSON.MERCHANT_ASSIGN_DASHBOARD}';
	var json = jQuery.parseJSON(merchantData);
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
		var rowCount = $('#bankTBody > tr').length;
		
		//rowindex = ++rowindex;
		colindex = ++ colindex; 
		/* var bankCode=(v.bankCode == undefined) ? "" : v.bankCode; */
		var Merchant_id=(v.Merchant_id == undefined) ? "" : v.Merchant_id;
		var TransactionType=(v.TransactionType == undefined) ? "" : v.TransactionType;
		var Feecode=(v.Feecode == undefined) ? "" : v.Feecode;
		var Makerid=(v.Makerid == undefined) ? "" : v.Makerid;
		var makerDttm=(v.makerDttm == undefined) ? "" : v.makerDttm;
		
		index=colindex-1;
 		var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
		"<td>"+colindex+"</td>"+
	"<td>"+Merchant_id+"</span></td>"+	 
		"<td>"+TransactionType+"</span></td>"+ 
		"<td>"+Feecode+"</span></td>"+ 
		"<td>"+Makerid+"</span></td>"+
		"<td>"+makerDttm+"</span></td></tr>";
		$("#bankTBody").append(appendTxt);	
		rowindex = ++rowindex;
	}); 
		
	$('#btn-cancel').live('click', function () { 
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantFeeAssign.action';
		$("#form1").submit();
		return true;
	});
		
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
					  <li><a href="#">Merchant  Dashboard </a></li>
			</ul>
		</div> 
		<div class="row-fluid sortable"><!--/span--> 
			<div class="box span12">
                 <div class="box-header well" data-original-title>Merchant Assign DashBoard
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
							<th>Transaction Type</th>
							<th>Fee Code</th>
							<th>Maker ID</th>
							<th>Maker Dttm</th>
						</tr>
					</thead> 
					<tbody  id="bankTBody">
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
