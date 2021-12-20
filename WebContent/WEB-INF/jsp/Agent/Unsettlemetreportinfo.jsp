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
	
	var feefinaljsonobj = jQuery.parseJSON(feejson);
	
	
	buildtable(feefinaljsonobj,'FEE_TBody');
	
	$('#btn-back').click(function(){ 
		//$("#form1").validate(acctRules);
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
			$('#form1').submit();
			
	});

});



function buildtable(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
		++i;
		htmlString = htmlString + "<tr class='values' id="+i+">";
		htmlString = htmlString + "<td id=sno name=sno >" + i + "</td>";
		htmlString = htmlString + "<td id=USERID name=USERID >" + jsonObject.USERID + "</td>";
		htmlString = htmlString + "<td id=TXNREF name=TXNREF >" + jsonObject.TXNREF + "</td>";
		htmlString = htmlString + "<td id=EXTTXNREF name=EXTTXNREF >" + jsonObject.EXTTXNREF + "</td>";	
		htmlString = htmlString + "<td id=SERVICECODE name=SERVICECODE >" + jsonObject.SERVICECODE + "</td>";
		htmlString = htmlString + "<td id=TXNAMOUNT name=TXNAMOUNT >" + jsonObject.TXNAMOUNT + "</td>";
		htmlString = htmlString + "<td id=TXNDATE name=TXNDATE>" + jsonObject.TXNDATE + "</td>";
		htmlString = htmlString + "<td id=CHANNEL name=CHANNEL>" + jsonObject.CHANNEL + "</td>";
		htmlString = htmlString + "<td id=SETDRCR name=SETDRCR>" + jsonObject.SETDRCR + "</td>";
		
			
		htmlString = htmlString + "<td id='' ><a class='btn btn-success' href='#' id='recview' onclick=fun('" + jsonObject.TXNREF + "','" + jsonObject.SERVICECODE + "') index="+i+" val="+i+" title='View' data-rel='tooltip' > <i class='icon icon-book icon-white'></i></a></td>";

			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

function fun(v,vv){
	//alert(v+"hhhh"+vv);
	
	$("#refno").val(v);
	$("#requesttype").val(vv);
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/settlementrequestinfo.action';
	$("#form1").submit();
	return true;
}

function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/Settlementlink.action';
	$("#form1").submit();
	return true;
}
 
</script> 
		
</head>

<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"> Unsettlement Report</a></li>
				</ul>
			</div>
			
			
	
		<div class="row-fluid sortable">
		
		
		<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Total Unsettlement Balance
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
			<div class="box-content"> 
			<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr class="odd" >
									<td width="10%"><label for="fname"><strong>Total Amount</strong></label></td>
									<td width="10%">${responseJSON.TOT_BALANCE}</td>
									
									<td width="10%"><label for="name"><strong>Count</strong></label></td>
									<td width="10%">${responseJSON.COUNT}</td>
								
								</tr>
								<tr class="odd" >
									<td width="10%"><label for="fname"><strong>Unsettlement Report</strong></label></td>
									<td width="10%" colspan="3"><a   href='downloadSBPApplication.action?fileName=${responseJSON.SETTLEMET}.csv' ><img src='${pageContext.request.contextPath}/images/csv.gif' alt='csv' width='20' height='20' ></img></a></td>
									
									
								
								</tr>
								</table>
								</div>
								</div>
								</div>
								<br>
			<div class="row-fluid sortable">					
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Unsettlement Report Information
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
							
								<th>S No</th>
								<th>User Id</th>
								<th style="width:7%">Txn Ref</th>
								<th style="width:7%">Ext Txnref</th>
							    <th style="width:7%">Service Code</th>
							    <th>Txn Amt</th>
							    <th>Txn Date</th> 
							    <th>Channel</th>   
								<th>Cr/Dr Flag</th>
								<th>Action</th>
							          
							          
							</tr>
							
						</thead> 
						
						
						 <tbody id="FEE_TBody">
						
						</tbody>  
					</table>
						
				</div>
			</div>
		</div> 
		 <input type="hidden" id="refno" name="refno"/>
	 <input type="hidden" id="requesttype" name="requesttype" />
	 
	
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
			</div> 
			 
		</div> 
	 

  
   
  
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>

</html>
 