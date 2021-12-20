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
			htmlString = htmlString + "<td id=TXN_STIME"+ i + " name=TXN_STIME >" + jsonObject.TXN_STIME + "</td>";
			 htmlString = htmlString + "<td id=TXN_ETIME"+ i + " name=TXN_ETIME  >" + jsonObject.TXN_ETIME + "</td>";
			htmlString = htmlString + "<td id=TRUNUPTIME"+ i + " name=TRUNUPTIME >" + jsonObject.TRUNUPTIME + "</td>";
			htmlString = htmlString + "<td id=BATCHID"+ i + " name=BATCHID >" + jsonObject.BATCHID + "</td>";
			 htmlString = htmlString + "<td id=PAYMENTREFERENCE"+ i + " name=PAYMENTREFERENCE >" + jsonObject.PAYMENTREFERENCE + "</td>";				
			htmlString = htmlString + "<td id=SERVICE_ID"+ i + " name=SERVICE_ID >" + jsonObject.SERVICE_ID + "</td>";
			htmlString = htmlString + "<td id=ACCOUNTNO"+ i + " name=ACCOUNTNO >" + jsonObject.ACCOUNTNO + "</td>";
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


 
</script> 
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">RAAS Information</a></li>
				</ul>
			</div>
			
			<div id="dialog" name="dialog" title="View" width="100" style="display:none">
  						<strong><div  id="result"><div id="pie"></div>
  						</div></strong>
					</div>
	
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>RAAS Information
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
							
								
								 <th width="10%"></th>
								<th width="10%">ETime</th>
								<th width="10%">Trunuptime</th>
								<th width="10%">Batch Id</th>
							    <th width="10%">Payment Reference No</th> 
							    <th width="10%">Service Id</th> 
							    <th width="10%">Account No</th> 
							</tr>
							
						</thead> 
						
						
						 <tbody id="FEE_TBody">
						
						</tbody>  
					</table>
					
							<strong>Download</strong>			<a   href='downloadSBPApplication.action?fileName=Rass_timeout_information.xlsx' ><img src='${pageContext.request.contextPath}/images/xlsx.jpg' alt='pdf' width='40' height='40' ></img></a>
					
					
						
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
 