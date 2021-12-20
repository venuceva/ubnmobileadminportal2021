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
			htmlString = htmlString + "<td id=MOBILE_NUMBER name=MOBILE_NUMBER >" + jsonObject.MOBILE_NUMBER + "</td>";
			htmlString = htmlString + "<td id=FIRST_NAME name=FIRST_NAME >" + jsonObject.FIRST_NAME + "</td>";
			htmlString = htmlString + "<td id=CNT_TXN name=CNT_TXN >" + jsonObject.CNT_TXN + "</td>";
			htmlString = htmlString + "<td id=SUM_TXN_AMT name=SUM_TXN_AMT >" + jsonObject.SUM_TXN_AMT + "</td>";
			htmlString = htmlString + "<td id=r_state name=r_state >" + jsonObject.r_state + "</td>";
			htmlString = htmlString + "<td id=RL_LGA name=RL_LGA >" + jsonObject.RL_LGA + "</td>";	
			
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

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
				<div class="box-header well" data-original-title>Top 5 Agents Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				
				
				
				
				<div class="box-content"> 
				
				<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
								
								<tr class="even"  >
								
									<td width="25%"><label for="Service ID"><strong>State</strong></label></td>
									<td width="25%" >${responseJSON.state}</td>	 
	 							     <td width="25%"><label for="Service ID"><strong>Local Government</strong></label></td>
									<td width="25%" >${responseJSON.localGovernment}</td>							
								
								</tr>
								<tr class="even" id="tabdate"  >
									<td ><label for="Date"><strong>From Date</strong></label></td>
									<td >${responseJSON.fromdate}</td>
									<td ><label for="Date"><strong>To Date</strong></label></td>
									<td >${responseJSON.todate}</td>
									
								</tr>
								<tr class="even" >
								
									<td width="25%"><label for="Service ID"><strong>Filter By</strong></label></td>
									<td width="25%" ></td>	
	 							<td width="50%" colspan="2">${responseJSON.status} </td> 								
								</tr>									
							</table>
				
				
					<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  
						id="DataTables_Table_1" >
						<thead>
							<tr>
								<th width='3%'>S No</th>
								<th width='10%'>Mobile Number</th>
								<th width='10%'>Name</th>
								<th width='10%'>No of Transaction</th>
								<th width='10%'>Transaction Value</th>
								<th width='10%'>State</th>
							    <th width='10%'>Local Government</th>
							</tr>
						</thead> 
						 <tbody id="FEE_TBody">
						
						</tbody>  
					</table>
						
						<div id="pie"></div>
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
 