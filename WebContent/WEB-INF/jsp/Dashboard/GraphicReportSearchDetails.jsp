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

<script src="<%=ctxstr%>/js/dashboard/d3.min.js"></script>
<script src="<%=ctxstr%>/js/dashboard/d3pie.js"></script>

   <link rel="stylesheet" href="<%=ctxstr%>/css/material-charts.css">
  <script type='text/javascript' src="<%=ctxstr%>/js/material-charts.js"></script>
  
	
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
	
	
	
	
	

});



function buildtable(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	var htmlString="";
	
	var webv=0;
	var ussdv=0;
	var posv=0;
	var mobilev=0;
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=CHANNEL name=CHANNEL >" + jsonObject.CHANNEL + "</td>";
			htmlString = htmlString + "<td id=COUNT name=COUNT >" + jsonObject.COUNT + "</td>";
			htmlString = htmlString + "<td id=AMOUNT name=AMOUNT >" + jsonObject.AMOUNT + "</td>";
			
			htmlString = htmlString + "</tr>";
			
			if(jsonObject.CHANNEL=="MOBILE"){
				mobilev=parseInt(jsonObject.COUNT);
			}
			
			if(jsonObject.CHANNEL=="USSD"){
				ussdv=parseInt(jsonObject.COUNT);
			}
			
			if(jsonObject.CHANNEL=="POS"){
				posv=parseInt(jsonObject.COUNT);
			}
			
			if(jsonObject.CHANNEL=="WEB"){
				webv=parseInt(jsonObject.COUNT);
			}
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);
	
	
	
	var pie = new d3pie("pie", {
		header: {
			title: {
				text: "Contributions of all channels "
			}
		},
		data: {
			content: [
				
				
				{ label: "USSD", value: ussdv },
				{ label: "MOBILE", value: mobilev },
				{ label: "POS", value: posv},
				{ label: "WEB", value: webv}
			]
		}
	});

}

 
 

$(document).on('click','a',function(event) {
	
    var $row = jQuery(this).closest('tr');
    var $columns = $row.find('td');

    $columns.addClass('row-highlight');
    var values = "";
     var btn=this.id;
    
    jQuery.each($columns, function(i, item) {
    	
    	if(i==0)
    		{
    		values =  item.innerHTML;
    		}else{
    			values = values +"$"+ item.innerHTML;
    		}
    });
   
  	
	var val = values;
	var code = "";
	var userid = "";
	if(val.match("$"))
		{
		var x = val.split("$");
		userid = x[7];
		
		//alert(userid);
		}

	if(btn == 'recview')
	{
		//alert(userid);
		$('#storeid').val(userid);
		 $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/inventorymngdetailsview.action';
		 $("#form1").submit();
			return false; 
	}
	
    
}); 
		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
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
					<li><a href="#"> Graphical Reports</a></li>
				</ul>
			</div>
			
	
			
		
		
		
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Channels Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				
				
				
				
				<div class="box-content"> 
				
				
				
				
					<table width='100%' class="table-striped table-bordered bootstrap-datatable datatable"  
						id="DataTables_Table_1" >
					<tr class="even" id="tabdate"  >
									<td ><label for="Date"><strong>From Date</strong></label></td>
									<td >${responseJSON.fromdate}</td>
									<td ><label for="Date"><strong>To Date</strong></label></td>
									<td >${responseJSON.todate}</td>
									
								</tr>
					
					<tr ><td colspan="2">
					<div id="pie"  ></div>
					</td>
					<td colspan="2">
					<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  
						id="DataTables_Table_2" >
						<thead>
							<tr>
								
								<th width='10%'>Channel</th>
								<th width='10%'>No of Transaction</th>
								<th width='10%'>Transaction Value</th>
								
							</tr>
						</thead> 
						 <tbody id="FEE_TBody">
						
						</tbody>  
					</table>
					</td>
					</tr>	
						</table>
				</div>
			</div>
		</div> 
		
	 <input type="hidden" id="storeid" name="storeid"/>
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Home" />
			</div> 
			 
		</div> 
	 

  
   
  
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 