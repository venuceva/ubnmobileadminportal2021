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
	
	var lmtjson =  '${responseJSON.VIEW_LMT_DATA}';
	var feejson =  '${responseJSON.VIEW_FEE_DATA}';
	
	console.log("Welcome to pro");
	
	var finaljsonobj = jQuery.parseJSON(lmtjson);
	var feefinaljsonobj = jQuery.parseJSON(feejson);
	
	// console.log("Limit Json ["+finaljsonobj+"]");
	 // console.log("Fee Json ["+feefinaljsonobj+"]");
	
	buildtable(finaljsonobj,'LMT_TBody');
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
			htmlString = htmlString + "<td id=LMT_FEE_CODE name=LMT_FEE_CODE >" + jsonObject.LMT_FEE_CODE + "</td>";
			htmlString = htmlString + "<td id=LMT_FEE_DESC name=LMT_FEE_DESC >" + jsonObject.LMT_FEE_DESC + "</td>";	
			htmlString = htmlString + "<td id=INITIATOR_ID name=INITIATOR_ID >" + jsonObject.INITIATOR_ID + "</td>";
			htmlString = htmlString + "<td id=INITIATOR_DTTM name=INITIATOR_DTTM >" + jsonObject.INITIATOR_DTTM + "</td>";
			
			if(divid == "LMT_TBody")
				{
				htmlString = htmlString + "<td id='' ><a class='btn btn-min btn-success' href='#' id='limitview' index="+i+" val="+i+" title='View' data-rel='tooltip' onclick='viewfun("+i+")'> <i class=' icon icon-note icon-white'></i></a></td>";
				}else{
					htmlString = htmlString + "<td id='' ><a class='btn btn-min btn-success' href='#' id='feeview' index="+i+" val="+i+" title='View' data-rel='tooltip' onclick='viewfun("+i+")'> <i class='icon icon-note icon-white'></i></a></td>";	
				}
			
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

 function createLimitData(myaction){
	 
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
	var code = "";
	if(val.match("$"))
		{
		var x = val.split("$");
		code = x[1];
		
		}
	
	if(btn == 'limitview')
	{
		$('#limitCode').val(code);
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/limitfeesettingslimitdataview.action';
		$("#form1").submit();
			return true; 
	}else if(btn == 'feeview')
	{
		$('#feeCode').val(code);
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/limitfeesettingsfeedataview.action';
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
					<li><a href="#">Limit Fee Management</a></li>
				</ul>
			</div>
			
			 <div class="box-content" id="top-layer-anchor">
				 <div>
					<a href="#" class="btn btn-success" id="createProduct"   title='Limit Settings Creation' data-rel='popover'  data-content='Limit Settings Creation' onClick="createLimitData('limitsettings')"><i class='icon icon-plus icon-white'></i>&nbsp;Limit Settings Creation</a> &nbsp;
					<a href="#" class="btn btn-success" id="createProduct"   title='Fee Settings Creation' data-rel='popover'  data-content='Fee Settings Creation' onClick="createLimitData('feesettings')"><i class='icon icon-plus icon-white'></i>&nbsp;Fee Settings Creation</a> &nbsp;					
 				 </div>	
			</div>
			
								  
           <div class="row-fluid sortable"><!--/span-->
			<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Limit Settings Information
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
								<th>Limit Code</th>
								<th>Limit Description</th>
							    <th>Maker ID </th>  
								<th>Maker Dttm</th>
								<th>Actions</th>
							</tr>
						</thead> 
						 <tbody id="LMT_TBody">
							   
						</tbody>  
					</table>
					<input type="hidden" id="limitCode" name="limitCode"/>
				</div>
			</div>
		</div> 
		
		
		
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Fee Settings Information
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
								<th>Fee Code</th>
								<th>Fee Description</th>
							    <th>Maker ID </th>  
								<th>Maker Dttm</th>
								<th>Actions</th>	
							</tr>
						</thead> 
						 <tbody id="FEE_TBody">
						
						</tbody>  
					</table>
						<input type="hidden" id="feeCode" name="feeCode"/> 
				</div>
			</div>
		</div> 
		
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
 