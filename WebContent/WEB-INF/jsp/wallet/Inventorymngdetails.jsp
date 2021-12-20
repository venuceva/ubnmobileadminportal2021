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
			htmlString = htmlString + "<td id=mobile_number name=mobile_number >" + jsonObject.mobile_number + "</td>";
			htmlString = htmlString + "<td id=first_name name=first_name >" + jsonObject.first_name + "</td>";
			htmlString = htmlString + "<td id=store_id name=store_id >" + jsonObject.store_id + "</td>";
			htmlString = htmlString + "<td id=state name=state >" + jsonObject.state + "</td>";
			htmlString = htmlString + "<td id=loc_gov name=loc_gov >" + jsonObject.loc_gov + "</td>";
			htmlString = htmlString + "<td id=terminal_id name=terminal_id >" + jsonObject.terminal_id + "</td>";	
			htmlString = htmlString + "<td id=serial_no name=serial_no >" + jsonObject.serial_no + "</td>";
			
			
			if(jsonObject.STATUS=="A"){
				htmlString = htmlString + "<td id=STATUS name=STATUS ><div class='label label-success' >Active</div></td>";
			}else{
				htmlString = htmlString + "<td id=STATUS name=STATUS ><div class='label label-important' >Deactive</div></td>";
			}
			
			
			
			
			
			htmlString = htmlString + "<td id='' ><a class='btn btn-success' href='#' id='recview' index="+i+" val="+i+" title='View' data-rel='tooltip' > <i class='icon icon-book icon-white'></i></a></td>";

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
					<li><a href="#"> Inventory Management</a></li>
				</ul>
			</div>
			
	
			
		
		
		
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				
				
				
				
				<div class="box-content"> 
				
				
				
					<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  
						id="DataTables_Table_1" >
						<thead>
							<tr>
								<th width='3%'>S No</th>
								<th width='10%'>Mobile Number</th>
								<th width='10%'>Name</th>
								<th width='10%'>Address</th>
								<th width='10%'>State</th>
								<th width='10%'>Local Government</th>
							    <th width='10%'>Terminal Id</th>
							    <th width='10%'>Serial No</th>
							    <th width='10%'>Status</th> 
								<th width='10%'>Actions</th>	
							</tr>
						</thead> 
						 <tbody id="FEE_TBody">
						
						</tbody>  
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
 