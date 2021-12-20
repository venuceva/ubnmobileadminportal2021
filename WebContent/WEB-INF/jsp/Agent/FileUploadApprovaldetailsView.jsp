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
			
			var newfilepath = "<%=request.getContextPath()%>/<%=appName %>/fileDownload.action?fileName="+jsonObject.f_name;
			
			htmlString = htmlString + "<td id=REF_NUM"+ i + " name=REF_NUM >" + jsonObject.REF_NUM + "</td>";
			 htmlString = htmlString + "<td id=f_name"+ i + " name=f_name  >" + jsonObject.f_name + "</td>";
			htmlString = htmlString + "<td id=NUM_OF_RECORD"+ i + " name=NUM_OF_RECORD >" + jsonObject.NUM_OF_RECORD + "</td>";
			htmlString = htmlString + "<td id=UPLOAD_DATE"+ i + " name=UPLOAD_DATE >" + jsonObject.UPLOAD_DATE + "</td>";
			 htmlString = htmlString + "<td id=MAKER_ID"+ i + " name=MAKER_ID >" + jsonObject.MAKER_ID + "</td>";
			 htmlString = htmlString + "<td id=download"+ i + " name=download ><a style='text-indent: em;  color: Blue' href="+newfilepath+">Download</a></span></td>";
 			htmlString = htmlString + "<td id=sno name=sno ><a id=sno class='btn btn-info' href='#' onclick=fun('" + jsonObject.REF_NUM + "') title='Request Approval' data-rel='tooltip'><i class='icon icon-book icon-white'></i></a></td>";
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

 
 function fun(v){
	//alert(v);
	$("#refno").val(v);
	if($("#application").val()=="FUNDING_PENDING" || $("#application").val()=="REVSUCC_PENDING" || $("#application").val()=="TERMINAL_PENDING" || $("#application").val()=="PREENROLMENT_PENDING"){
		
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fileuploadrequestapprovalinfo.action';
		$("#form1").submit();
		return true;
	}
	if($("#application").val()=="FUNDING_COMPLETED" || $("#application").val()=="REVSUCC_COMPLETED" || $("#application").val()=="TERMINAL_COMPLETED" || $("#application").val()=="PREENROLMENT_COMPLETED"){
		
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/agentrequestapprovalcomplatedinfo.action';
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
					<li><a href="#">File Upload Approval</a></li>
				</ul>
			</div>
			
			<div id="dialog" name="dialog" title="View" width="100" style="display:none">
  						<strong><div  id="result"><div id="pie"></div>
  						</div></strong>
					</div>
	
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Upload File Information
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
								<th width="10%">File Reference Number</th>
								 <th width="10%">File Name</th>
								<th width="10%">No of Records</th>
								<th width="10%">file upload Date</th>
							    <th width="10%" >Maker Id</th>
							    <th width="10%" >Download</th>
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
 