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

downloadSBPApplication

function buildtable(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+"  >";
			
			
			htmlString = htmlString + "<td id=USER_ID"+ i + " name=USER_ID >" + jsonObject.USER_ID + "</td>";
			 htmlString = htmlString + "<td id=TXN_REFERNCE_NO"+ i + " name=TXN_REFERNCE_NO  >" + jsonObject.TXN_REFERNCE_NO + "</td>";
			htmlString = htmlString + "<td id=EXTREFERNCE_NO"+ i + " name=EXTREFERNCE_NO >" + jsonObject.EXTREFERNCE_NO + "</td>";
			htmlString = htmlString + "<td id=SERVICE_CODE"+ i + " name=SERVICE_CODE >" + jsonObject.SERVICE_CODE + "</td>";
			 htmlString = htmlString + "<td id=TXN_AMOUNT"+ i + " name=TXN_AMOUNT >" + jsonObject.TXN_AMOUNT + "</td>";
			 htmlString = htmlString + "<td id=ACTION"+ i + " name=ACTION >" + jsonObject.ACTION + "</td>";
			 htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

 
 function fun(v){
	//alert(v);
	$("#refno").val(v);
	if($("#application").val()=="FUNDING_COMPLETED"){
		
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fileuploadrequestapprovalinfo.action';
		$("#form1").submit();
		return true;
	}
	if($("#application").val()=="FUNDING_REJECTED"){
		
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fileuploadapprovaldetailsresultfailed.action';
		$("#form1").submit();
		return true;
	}
	
	
	
	
} 


		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/agentrequestapproval.action';
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
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Wallet Transaction Reversal Request Approval</a></li>
				</ul>
			</div>
			
			<div id="dialog" name="dialog" title="View" width="100" style="display:none">
  						<strong><div  id="result"><div id="pie"></div>
  						</div></strong>
					</div>
	
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Wallet Transaction Information
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
								<th width="10%">User id</th>
								 <th width="10%">txn Reference number</th>
								<th width="10%">Ext Txn Ref No</th>
								<th width="10%">Service Code</th>
							    <th width="10%" >Amount</th>
							    <th width="10%">Action</th>							   
							      
							          
							          
							</tr>
							
						</thead> 
						
						
						 <tbody id="FEE_TBody">
						
						</tbody>  
					</table>
						
				</div>
			</div>
		</div> 
	 <input type="hidden" id="refno" name="refno"/>
	<input name="application" type="hidden" id="application"   value="${responseJSON.application}"  />
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
				
			</div> 
			 
		</div> 
	 

  
   
  
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 