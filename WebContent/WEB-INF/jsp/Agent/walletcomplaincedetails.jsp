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
			htmlString = htmlString + "<td id=TXN_REF_NO"+ i + " name=TXN_REF_NO >" + jsonObject.TXN_REF_NO + "</td>";
			htmlString = htmlString + "<td id=WALLET_REF_NO"+ i + " name=WALLET_REF_NO >" + jsonObject.WALLET_REF_NO + "</td>";
			 htmlString = htmlString + "<td id=USER_ID"+ i + " name=USER_ID  >" + jsonObject.USER_ID + "</td>";
			/* htmlString = htmlString + "<td id=DR_ACCOUNT"+ i + " name=DR_ACCOUNT >" + jsonObject.DR_ACCOUNT + "</td>";
			htmlString = htmlString + "<td id=CR_ACCOUNT"+ i + " name=CR_ACCOUNT >" + jsonObject.CR_ACCOUNT + "</td>"; */
			 htmlString = htmlString + "<td id=SERVICECODE"+ i + " name=SERVICECODE >" + jsonObject.SERVICECODE + "</td>";
			 htmlString = htmlString + "<td id=TXN_AMT"+ i + " name=TXN_AMT >" + jsonObject.TXN_AMT + "</td>";
			 htmlString = htmlString + "<td id=FEE_AMT"+ i + " name=FEE_AMT >" + jsonObject.FEE_AMT + "</td>";
			htmlString = htmlString + "<td id=AMOUNT"+ i + " name=AMOUNT >" + jsonObject.AMOUNT + "</td>";
			htmlString = htmlString + "<td id=CHANNEL"+ i + " name=CHANNEL >" + jsonObject.CHANNEL + "</td>";
			htmlString = htmlString + "<td id=TXN_DTTM"+ i + " name=TXN_DTTM >" + jsonObject.TXN_DTTM + "</td>";
			htmlString = htmlString + "<td id=STATUS"+ i + " name=STATUS >" + jsonObject.STATUS + "</td>";
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
	
	
	   
	var queryString="sid="+$( "#TXN_REF_NO"+v ).text()+"-"+$( "#SERVICECODE"+v ).text()+"-"+$( "#STATUS"+v ).text()+"-"+$( "#WALLET_REF_NO"+v ).text();
	$.getJSON("wallettrnstionajx.action", queryString, function(data) {
	
	 $( "#dialog" ).dialog( "option", "title", "Transaction Details for Reference No "+$( "#TXN_REF_NO"+v ).text());
		$( "#pie" ).html(data.region);	  
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
							<th width="10%">Payment Number</th>
							<th width="10%">Wallet Number</th>
								 <th width="10%">User id</th>
								<!-- <th width="10%">Debit Account nO</th>
								<th width="10%">Credit Account No</th> -->
								<th width="10%">Transaction Type</th>
								<th width="10%">Txn Amount</th>
								<th width="10%">Fee Amount</th>
								<th width="10%">total Amount</th>
								
							    <th width="10%">Channel</th> 
							    <th width="10%">Transaction Date and Time</th>	
							    <th width="10%">Status</th>						   
 							    <th width="10%">View</th>      
							          
							          
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
 