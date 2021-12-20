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
			htmlString = htmlString + "<td id=ref_num"+ i + " name=ref_num >" + jsonObject.ref_num + "</td>";
			 htmlString = htmlString + "<td id=REPORT_NAME"+ i + " name=REPORT_NAME  >" + jsonObject.REPORT_NAME + "</td>";
			htmlString = htmlString + "<td id=SEARCH_DATA"+ i + " name=SEARCH_DATA >" + jsonObject.SEARCH_DATA + "</td>";
			htmlString = htmlString + "<td id=MAKER_DT"+ i + " name=MAKER_DT >" + jsonObject.MAKER_DT + "</td>";
			if(jsonObject.status=="P"){
				 htmlString = htmlString + "<td id=status"+ i + " name=status style='color:#FF0000'><b>Process</b></td>";				
			}
			if(jsonObject.status=="I"){
				 htmlString = htmlString + "<td id=status"+ i + " name=status style='color:blue'><b>Processing Started</b></td>";				
			}
			if(jsonObject.status=="C"){
				 htmlString = htmlString + "<td id=status"+ i + " name=status style='color:green'><b>Completed</b></td>";				
			}
			htmlString = htmlString + "<td id=maker_id"+ i + " name=maker_id >" + jsonObject.maker_id + "</td>";
			if(jsonObject.status=="C"){
			htmlString =htmlString+"<td><a   href='filedownloadSBPApplication.action?fileName="+jsonObject.f_name+"_"+jsonObject.ref_num+".csv' ><img src='${pageContext.request.contextPath}/images/csv.gif' alt='csv' width='20' height='20' ></img></a>&nbsp;&nbsp;&nbsp;&nbsp;   <a   href='filedownloadSBPApplication.action?fileName="+jsonObject.f_name+"_"+jsonObject.ref_num+".pdf' ><img src='${pageContext.request.contextPath}/images/pdf.gif' alt='csv' width='20' height='20' ></img></a></td>";	
			}else{
				htmlString =htmlString+"<td></td>";	
	
			}
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

 
 


		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/bulkuploadresult.action';
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
					<li><a href="#"> File Uploaded Result</a></li>
				</ul>
			</div>
			
		
	
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>File Uploaded Result Information
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
								 <th width="10%">file Ref No</th>
								<th width="10%">File Name</th>
								<th width="30%">Request Details</th>
								<th width="10%">Request Date</th>
							    <th width="10%">Process Status</th> 
							    <th width="10%">Maker Id</th> 
							    <th width="10%">Download</th> 
							          
							          
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
 