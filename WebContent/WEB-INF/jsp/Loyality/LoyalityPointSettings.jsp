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
	
	var lmtjson =  '${responseJSON.VIEW_LOYALITY_DATA}';
	
	console.log("Welcome to pro");
	
	var finaljsonobj = jQuery.parseJSON(lmtjson);
	
	// console.log("Limit Json ["+finaljsonobj+"]");
	 // console.log("Fee Json ["+feefinaljsonobj+"]");
	
	buildtable(finaljsonobj,'LMT_TBody');
	//buildtable(feefinaljsonobj,'FEE_TBody');
	

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
			htmlString = htmlString + "<td id=LOYALTY_CODE name=LOYALTY_CODE ><a href='#' id='SEARCH_NO' >" + jsonObject.LOYALTY_CODE + "</a></td>";
			htmlString = htmlString + "<td id=LOYALTY_CODE name=LOYALTY_CODE style='display:none'>" + jsonObject.LOYALTY_CODE + "</td>";
			htmlString = htmlString + "<td id=LOYALTY_DESC name=LOYALTY_DESC >" + jsonObject.LOYALTY_DESC + "</td>";	
			htmlString = htmlString + "<td id=PRODUCT name=PRODUCT >" + jsonObject.PRODUCT + "</td>";
			htmlString = htmlString + "<td id=APPLICATION name=APPLICATION >" + jsonObject.APPLICATION + "</td>";			
			htmlString = htmlString + "<td id=INITIATOR_ID name=INITIATOR_ID >" + jsonObject.INITIATOR_ID + "</td>";
			htmlString = htmlString + "<td id=INITIATOR_DTTM name=INITIATOR_DTTM >" + jsonObject.INITIATOR_DTTM + "</td>";
			
			if(divid == "LMT_TBody")
				{
				htmlString = htmlString + "<td id='' ><a class='btn btn-success' href='#' id='limitview' index="+i+" val="+i+" title='View' data-rel='tooltip' > <i class='icon icon-book icon-white'></i></a>";
				htmlString = htmlString + "&nbsp&nbsp<a class='btn btn-warning' href='#' id='limitmodify' index="+i+" val="+i+" title='modify' data-rel='tooltip' > <i class='icon icon-edit icon-white'></i></a>";
				htmlString = htmlString + "&nbsp&nbsp<a class='btn btn-warning' href='#' id='setservices' index="+i+" val="+i+" title='Set Services' data-rel='tooltip' > <i class='icon icon-tag icon-white'></i></a></td>";
/* 				htmlString = htmlString + "&nbsp&nbsp<a class='btn btn-warning' href='#' id='SEARCH_NO' index="+i+" val="+i+" title='Set Services' data-rel='tooltip' > <i class='icon icon-tag icon-white'></i></a>";
 */
				}
			
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}




 function createLimitData(myaction){
	 //$('#linkmode').val("NEW");
	    $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/'+myaction+'.action';
		$("#form1").submit();
		return true;
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
	var prdcode = "";
	var applicaton = "";
	var loyaltycode = "";
	var loyaltydesc = "";
	if(val.match("$"))
		{
		var x = val.split("$");
		prdcode = x[4];
		applicaton = x[5];
		loyaltycode=x[2];
		loyaltydesc=x[3];
		
		}

	if(btn == 'limitview')
	{
		$('#productcode').val(prdcode);
		$('#loyaltycode').val(loyaltycode);
		$('#application').val(applicaton);
		$('#loyaltydesc').val(loyaltydesc);
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/loyalityView.action';
		$("#form1").submit();
			return true; 
	}else if(btn == 'limitmodify')
	{
		$('#productcode').val(prdcode);
		$('#loyaltycode').val(loyaltycode);
		$('#application').val(applicaton);
		$('#loyaltydesc').val(loyaltydesc);
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/loyalityModify.action';
		$("#form1").submit();
			return true; 
	}else if(btn == 'setservices')
	{
		$('#productcode').val(prdcode);
		$('#loyaltycode').val(loyaltycode);
		$('#application').val(applicaton);
		$('#loyaltydesc').val(loyaltydesc);
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/loyalitypointsettingsservices.action';
		$("#form1").submit();
		return true; 
	}else if(btn == 'SEARCH_NO')
	{
		$('#productcode').val(prdcode);
		$('#loyaltycode').val(loyaltycode);
		$('#application').val(applicaton);
		$('#loyaltydesc').val(loyaltydesc);
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/AssignLoyaltyDetails.action';
		$("#form1").submit();
			return true; 
	}
	
    
}); 
		 

 
</script> 
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Loyalty Management</a></li>
				</ul>
			</div>
			
			 <div class="box-content" id="top-layer-anchor">
				 <div>
					<a href="#" class="btn btn-success" id="loyality"   title='Loyalty Point Setting' data-rel='popover'  data-content='Loyalty Point Setting' onClick="createLimitData('loyality')"><i class='icon icon-plus icon-white'></i>&nbsp;Loyalty Point Setting</a> &nbsp;
					
			 </div>	
			</div>
			
								  
           <div class="row-fluid sortable"><!--/span-->
			<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Loyalty Point Setting Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				<div class="box-content"> 
					<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  
						id="DataTables_Table_0" >
						<thead>
							<tr>
								<th>S No</th>
								<th style="display:none">Loyalty Id</th>
								<th>Loyalty Id</th>
								<th>Loyalty Name</th>
								<th>Product Code</th>
								<th>Application</th>								
							    <th>Maker ID </th>  
								<th>Maker Dttm</th>
								<th>Actions</th>
							</tr>
						</thead> 
						 <tbody id="LMT_TBody">
							   
						</tbody>  
					</table>
				
				</div>
			</div>
		</div> 
		
		
				<input type="hidden" id="productcode" name="productcode"/> 
				<input type="hidden" id="loyaltycode" name="loyaltycode"/> 
				<input type="hidden" id="application" name="application"/> 
				<input type="hidden" id="loyaltydesc" name="loyaltydesc"/> 
		<input type="hidden" id="linkmode" name="linkmode"/>
		
		  <script type="text/javascript"> 
			  $(document).ready(function () {
				  binDetails = binDetails.split(",");
					$.each(binDetails, function(indexLink, val) {	
						prdIndex[indexLink]=val;	 
					}); 					
			  }); 
		</script>  
		<s:set value="responseJSON" var="respData"/> 
		<s:set value="%{'_STORES'}" var="searchKey"/> 
		
		<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
		  	<s:param name="jsonData" value="#respData"/>  
 		    <s:param name="searchData" value="#searchKey"/>  
		</s:bean>
		<!-- Loading Stores -->
	 	 
			 
		</div> 
	</div> 
	 

   
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 