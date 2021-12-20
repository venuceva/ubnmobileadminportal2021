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
	
	
	if("${responseJSON.VIEW_LMT_DATA_CNT.CNT}"=="0"){
		
		$("#imeiblock").css("display","");
		$("#imeiunblock").css("display","none");
		$("#imeistatus").val("Block");
		
	}else{
		$("#imeiblock").css("display","none");
		$("#imeiunblock").css("display","");
		$("#imeistatus").val("Unblock");
	}

});



function buildtable(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+"  >";
			htmlString = htmlString + "<td id="+ i + " name="+ i + " > "+ i +  "</td>";
			htmlString = htmlString + "<td id=DEVICE_IP"+ i + " name=DEVICE_IP >" + jsonObject.DEVICE_IP + "</td>";
			 htmlString = htmlString + "<td id=IMEI_NO"+ i + " name=IMEI_NO  >" + jsonObject.IMEI_NO + "</td>";
			htmlString = htmlString + "<td id=USER_ID"+ i + " name=USER_ID >" + jsonObject.USER_ID + "</td>";
			htmlString = htmlString + "<td id=FIRST_NAME"+ i + " name=FIRST_NAME >" + jsonObject.FIRST_NAME + "</td>";
			 htmlString = htmlString + "<td id=ACCT_NO"+ i + " name=ACCT_NO >" + jsonObject.ACCT_NO + "</td>";				
			htmlString = htmlString + "<td id=DEVICE_TYPE"+ i + " name=DEVICE_TYPE >" + jsonObject.DEVICE_TYPE + "</td>";
			htmlString = htmlString + "<td id=VERSION"+ i + " name=VERSION >" + jsonObject.VERSION + "</td>";
			htmlString = htmlString + "<td id=TRANS_DTTM"+ i + " name=TRANS_DTTM >" + jsonObject.TRANS_DTTM + "</td>";
			htmlString = htmlString + "<td id=STATUS"+ i + " name=STATUS >" + jsonObject.STATUS + "</td>";
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

 
 


		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/imeiblock.action';
	$("#form1").submit();
	return true;
}

function imeiblock(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/imeidetailsconf.action';
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
					<li><a href="#"> IMEI Block</a></li>
				</ul>
			</div>
			
			
	
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>IMEI Details
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
							<th width="1%" >No</th>
							<th width="10%">Device Ip</th>
								 <th width="10%">IMEI Number</th>
								<th width="10%">User Id</th>
								<th width="10%">Name</th>
								<th width="10%">Account Number</th>
							    <th width="10%">Device Type</th> 
							    <th width="10%">Version</th> 
							    <th width="10%">Transaction Date and Time</th>							   
							    <th width="5%">Status</th>
							</tr>
							
						</thead> 
						
						
						 <tbody id="FEE_TBody">
						
						</tbody>  
					</table>
					<input type="hidden" name="imeinumber"  id="imeinumber"  value="${responseJSON.imeinumber}" /> 
					<input type="hidden" name="imeistatus"  id="imeistatus"  />
					<div style="display:none" id="imeiblock">
					<input type="button" id="non-printable" class="btn btn-info" onclick="imeiblock();" value="IMEI Number Block" />
					</div> 
					<div style="display:none" id="imeiunblock">
					<input type="button" id="non-printable" class="btn btn-info" onclick="imeiblock();" value="IMEI Number Unblock" />
					</div>	
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
 