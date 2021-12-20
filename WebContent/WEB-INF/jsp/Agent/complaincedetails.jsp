 <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>CEVA </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
	
	
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
<script type="text/javascript" >
binDetails = "";
var prdIndex = new Array();

$(document).ready(function() {
	
	var feejson =  '${responseJSON.VIEW_LMT_DATA}';
	
	console.log("Welcome to pro");
	var v="${responseJSON.trans}";
	
	var feefinaljsonobj = jQuery.parseJSON(feejson);
	
	
	buildtable(feefinaljsonobj,'FEE_TBody');
	

});



function buildtable(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+"  >";
			htmlString = htmlString + "<td id="+ i + " name="+ i + " style='display:none'> "+ i +  "</td>";
			htmlString = htmlString + "<td id=CREDITPAYMENTREFERENCE"+ i + " name=CREDITPAYMENTREFERENCE >" + jsonObject.CREDITPAYMENTREFERENCE + "</td>";
			 htmlString = htmlString + "<td id=ACCOUNTNO"+ i + " name=ACCOUNTNO  >" + jsonObject.ACCOUNTNO + "</td>";
			htmlString = htmlString + "<td id=CREDITAMOUNT"+ i + " name=CREDITAMOUNT >" + jsonObject.CREDITAMOUNT + "</td>";
			htmlString = htmlString + "<td id=CREDITCREDITINDICATOR"+ i + " name=CREDITCREDITINDICATOR >" + jsonObject.CREDITCREDITINDICATOR + "</td>";
			 htmlString = htmlString + "<td id=BATCHID"+ i + " name=BATCHID >" + jsonObject.BATCHID + "</td>";				
			htmlString = htmlString + "<td id=CHANNEL"+ i + " name=CHANNEL >" + jsonObject.CHANNEL + "</td>";
			htmlString = htmlString + "<td id=USERID"+ i + " name=USERID >" + jsonObject.USERID + "</td>";
			htmlString = htmlString + "<td id=TRANS_DATE"+ i + " name=TRANS_DATE >" + jsonObject.TRANS_DATE + "</td>";
			htmlString = htmlString + "<td id=BEN_PAYBILL_CODE"+ i + " name=BEN_PAYBILL_CODE style='display:none' >" + jsonObject.BEN_PAYBILL_CODE + "</td>";
			htmlString = htmlString + "<td id=BEN_CUST_NAME"+ i + " name=BEN_CUST_NAME style='display:none' >" + jsonObject.BEN_CUST_NAME+ "</td>";
			htmlString = htmlString + "<td id=BEN_PAYBILL_ACTNO"+ i + " name=BEN_PAYBILL_ACTNO style='display:none' >" + jsonObject.BEN_PAYBILL_ACTNO + "</td>";  
			htmlString = htmlString + "<td id=STATUS"+ i + " name=STATUS >" + jsonObject.STATUS + "</td>";
			htmlString = htmlString + "<td id=RESPONSEMESSAGE"+ i + " name=RESPONSEMESSAGE style='display:none' >" + jsonObject.RESPONSEMESSAGE + "</td>";
 			htmlString = htmlString + "<td id=CREDITACCCOUNTNUMBER"+ i + " name=CREDITACCCOUNTNUMBER style='display:none' >" + jsonObject.CREDITACCCOUNTNUMBER + "</td>";
 			htmlString = htmlString + "<td id=sno name=sno ><a id=sno class='btn btn-info' href='#' onclick=fun(" + i + ") title='View Transaction Details' data-rel='tooltip'><i class='icon icon-book icon-white'></i></a></td>";
			
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

 
 


		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/complaince.action';
	$("#form1").submit();
	return true;
}

$(function() {
$( "#dialog" ).dialog(
		{
		autoOpen: false,
		modal: true,
	    draggable: false,
	    resizable: false,
	    show: 'blind',
	    hide: 'blind',
		width: 700, 
	    height: 650,
	    buttons: {
	        "OK": function() {
	            $(this).dialog("close");
	        }
	    }
		}
	);
});

function fun(v){
	var queryString="type="+$( "#BATCHID"+v ).text()+"&channel="+$( "#CHANNEL"+v ).text()+"&sid="+$( "#CREDITPAYMENTREFERENCE"+v ).text();
	$.getJSON("trnstionajx.action", queryString, function(data) {
	var htmlString1="";
	htmlString1 = htmlString1 + "<table class='table table-striped table-bordered bootstrap-datatable datatable'>";
	htmlString1 = htmlString1 + "<tr ><td>Account Number</td><td>"+$( "#ACCOUNTNO"+v ).text()+"</td></tr>";
	htmlString1 = htmlString1 + "<tr ><td>Txn Amount</td><td>"+$( "#CREDITAMOUNT"+v ).text()+"</td></tr>";
	htmlString1 = htmlString1 + "<tr ><td>CREDIT/DEBIT</td><td>"+$( "#CREDITCREDITINDICATOR"+v ).text()+"</td></tr>";
	htmlString1 = htmlString1 + "<tr ><td>Transaction Type</td><td>"+$( "#BATCHID"+v ).text()+"</td></tr>";
	htmlString1 = htmlString1 + "<tr ><td>Payment Reference Number</td><td>"+$( "#CREDITPAYMENTREFERENCE"+v ).text()+"</td></tr>";
	htmlString1 = htmlString1 + "<tr ><td>Channel</td><td>"+$( "#CHANNEL"+v ).text()+"</td></tr>";
	htmlString1 = htmlString1 + "<tr ><td>User Id/Mobile No</td><td>"+$( "#USERID"+v ).text()+"</td></tr>";
	htmlString1 = htmlString1 + "<tr ><td>Transaction Date and Time</td><td>"+$( "#TRANS_DATE"+v ).text()+"</td></tr>";
	htmlString1 = htmlString1 + "<tr ><td>Core Bank Status</td><td>"+$( "#STATUS"+v ).text()+"</td></tr>";
	/* htmlString1 = htmlString1 + "<tr ><td>Core Bank Response Message</td><td>"+$( "#RESPONSEMESSAGE"+v ).text()+"</td></tr>"; */
	
	
	
	if(($( "#BATCHID"+v ).text())=="Fund Transfer to Other Banks"){
		
	
	htmlString1 = htmlString1 + "<tr ><td>Beneficiary Bank</td><td>"+$( "#BEN_PAYBILL_CODE"+v ).text()+"</td></tr>";
	htmlString1 = htmlString1 + "<tr ><td>Beneficiary Name</td><td>"+$( "#BEN_CUST_NAME"+v ).text()+"</td></tr>";
	htmlString1 = htmlString1 + "<tr ><td>Credit Account</td><td>"+$( "#BEN_PAYBILL_ACTNO"+v ).text()+"</td></tr>";
	
	}
	
	if(($( "#BATCHID"+v ).text())=="Fund Transfer"){
		
		
		htmlString1 = htmlString1 + "<tr ><td>Debit Account</td><td>"+$( "#ACCOUNTNO"+v ).text()+"</td></tr>";
		htmlString1 = htmlString1 + "<tr ><td>Credit Account</td><td>"+$( "#CREDITACCCOUNTNUMBER"+v ).text()+"</td></tr>";
		
	}
	
	//alert(data.region);
	if(data.region!=""){
		//alert(data.region);
		htmlString1 = htmlString1 +data.region;
	}
	
	htmlString1 = htmlString1 + "</table>";
	
	
	 $( "#dialog" ).dialog( "option", "title", "Transaction Details for Reference No "+$( "#CREDITPAYMENTREFERENCE"+v ).text());
		$( "#pie" ).html(htmlString1);	  
	   $("#dialog").dialog("open");
	});
}
 
</script> 
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"> Transaction Status</a></li>
				</ul>
			</div>
			
			<div id="dialog" name="dialog" title="View" width="100" style="display:none">
  						<strong><div  id="result"><div id="pie"></div>
  						</div></strong>
					</div>
	
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Transaction Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				
				
				
				
				<div class="box-content"> 
				
				
				
					<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  
						id="DataTables_Table_1" >
						<thead>
							<tr >
							<th width="1%" style="display:none">No</th>
															<th width="10%">Payment Reference Number</th>
							
								
								 <th width="10%">Account Number</th>
								<th width="10%">Amount</th>
								<th width="10%">Cr/Dr</th>
								<th width="10%">Transaction Type</th>
							    <th width="10%">Channel</th> 
							    <th width="10%">User Id/Mobile No</th> 
							    <th width="10%">Transaction Date and Time</th>
							    <th width="10%" style="display:none">Beneficiary bank</th> 
							   <th width="10%" style="display:none">Beneficiary name</th> 
							    <th width="10%" style="display:none">Beneficiary acc</th> 
							    <th width="5%">Status</th>
							    <th width="5%" style="display:none">Response Message</th>
							    <th width="5%" style="display:none">Response Message</th> 
							    <th width="2%">View</th>      
							          
							          
							</tr>
							
						</thead> 
						
						
						 <tbody id="FEE_TBody">
						
						</tbody>  
					</table>
						
				</div>
			</div>
		</div> 
	 <input type="hidden" id="refno" name="refno"/>
	 <input type="hidden" id="userid" name="userid"/>
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
			</div> 
			 
		</div> 
	 

  
   
  
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 