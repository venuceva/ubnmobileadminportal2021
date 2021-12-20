<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
  
    
 <link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.css' />  

<script type="text/javascript" >

$(document).ready(function () { 

	var storeData ='${responseJSON.CAMP_INFO}';
	//alert(storeData);
 	var json = jQuery.parseJSON(storeData);
 
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";
	$.each(json, function(i, v) {
		if(val % 2 == 0 ) {
			addclass = "even";
			val++;
		}
		else {
			addclass = "odd";
			val++;
		}  
		var rowCount = $('#semesterTBody > tr').length;
		
		colindex = ++ colindex; 
		var vstatus="";
		if(v.STATUS=="P"){
			vstatus="<div class='label label-info' >Not yet Assign</div>";	
		}else if(v.STATUS=="A"){
			vstatus="<div class='label label-success' >Active</div>";	
		}else{
			vstatus="<div class='label label-important' >Deactive</div>";	
		}
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+
				"<td >"+colindex+"</td>"+
				"<td >"+v.TEMPLATE_ID+"</td>"+	
				"<td >"+v.TEMPLATE+"</span></td>"+
				"<td >"+v.CREATEDBY+"</span></td>"+
 				"<td >"+v.CREATEDDATE+"</span></td>"+
 				"<td >"+vstatus+"</span></td>"+
				"<td ><a class='btn btn-warning' href='#' id='modify-camp' index='"+rowindex+"' title='Modify Campaign' data-rel='tooltip'> <i class='icon icon-edit icon-white'></i></a>&nbsp;&nbsp;"+
				"<a class='btn btn-info' title='View Campaign' data-rel='tooltip' href='#' id='view-camp' index='"+rowindex+"' > <i class='icon icon-book icon-white'></i></a>&nbsp;&nbsp;"+
				"<a class='btn btn-success' title='Assign Campaign' data-rel='tooltip' href='#' id='assiagn-camp' index='"+rowindex+"' > <i class='icon icon-plus icon-white'></i></a>&nbsp;&nbsp;"+
				"<a class='btn btn-success' title='File Upload' data-rel='tooltip' href='#' id='assiagn-file' index='"+rowindex+"' > <i class='icon icon-file icon-white'></i></a>&nbsp;&nbsp;"+
				"<a class='btn btn-warning' href='#' id='active-camp' index='"+rowindex+"' title='Active/Deactive Campaign' data-rel='tooltip'> <i class='icon icon-lock icon-white'></i></a></td></tr>";
			
			$("#semesterTBody").append(appendTxt);	
			rowindex = ++rowindex;
	});
		
});  

$('#regester-Campaign').click(function(){
	 $("#rege").val('R');
	 $("#form")[0].action='<%=request.getContextPath()%>/<%=appName %>/createcampaignmgntact.action'; 
		$('#form').submit();
		 return true; 
	
});
$('#assign-Campaign').click(function(){
	 $("#rege").val('R');
	 $("#form")[0].action='<%=request.getContextPath()%>/<%=appName %>/campaignassind.action'; 
		$('#form').submit();
		 return true; 
	
});
	
	
	$(document).on('click',"a",function(event) {
	
	var v_id=$(this).attr('id');
	var disabled_status= $(this).attr('disabled'); 
	var queryString = '?'; 
	var v_action = "NO";
	 
	var index1 = $(this).attr('index');  
	var parentId =$(this).parent().closest('tbody').attr('id'); 
	var searchTrRows = parentId+" tr"; 
	var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
	var tempid = "";		
	var template = "";
	var createdby = "";
	var createddate = "";
	var status = "";
	
			if(disabled_status == undefined) {  
				
				 if (v_id ==  "modify-camp") v_action="modifyCampAct";	
			else if (v_id ==  "view-camp") v_action="viewcampaignmgntact";  
			else if (v_id ==  "assiagn-camp") v_action="assigncampaignmgntact";
			else if (v_id ==  "assiagn-file") v_action="assigncampaignmgntactfile"; 
			else if (v_id ==  "regester-Campaign") v_action="createcampaignmgntact";  
			else if (v_id ==  "assign-Campaign") v_action="campaignassind"; 
			else if (v_id ==  "active-camp") v_action="activecampaignassind";

			$(searchTdRow).each(function(indexTd) {  
				 if (indexTd == 1) tempid=$(this).text().trim(); 
			else if(indexTd == 2)  template=$(this).text().trim();
			else if(indexTd == 3)  createdby=$(this).text().trim();
			else if(indexTd == 4)  createddate=$(this).text().trim();
			else if(indexTd == 5)  status=$(this).text().trim();
						}); 

				$("#tempId").val(tempid);
				$("#template").val(template);
				$("#createdby").val(createdby);
				$("#createdate").val(createddate);
				$("#status").val(status);
				
			if(v_action!="NO"){
				
				$("#form1")[0].action="<%=ctxstr%>/<%=appName %>/"+v_action+".action";
				$("#form1").submit();
				
			}

				}
});  
</script>
<style type="text/css">
label.error {
  font-weight: bold;
  color: red;
  padding: 2px 8px;
  margin-top: 2px;
}
.errmsg {
color: red;
}
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
</style>  
<link rel="shortcut icon" href="images/favicon.ico">		
</head> 
<body>  
		<div id="content" class="span10">  
			  <div> 
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="campaignmgntact.action">Campaign Management</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Campaign Management</a></li>
				</ul>
			</div>  
			
      		<div class="box-content" id="top-layer-anchor"> 
				 <div>
					<a href="#" class="btn btn-success" id="regester-Campaign">Create Campaign</a> &nbsp;&nbsp;
					<a href="#" class="btn btn-warning" id="assign-Campaign">Templates Assigned</a> &nbsp;
				 </div>	
			</div>	
		<form name="form1" id="form1" method="post" action="">							  
              <div class="row-fluid sortable"><!--/span--> 
				<div class="box span12">
					  <div class="box-header well" data-original-title>Campaign Details
						  <div class="box-icon"> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
						</div>
					  </div>  
						<div class="box-content"> 
							<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  id="DataTables_Table_0" >
								<thead>
									<tr>
											<th style="width:5%;text-align:center">Sno</th>
											<th style="width:10%;text-align:center">Template ID</th>
											<th style="width:35%;text-align:center">Message</th>
											<th style="width:10%;text-align:center">Created By</th>
											<th style="width:10%;text-align:center">Created Date</th>
											<th style="width:10%;text-align:center">Status</th>
											<th style="width:25%;text-align:center">Actions</th>
									</tr>
								</thead> 
								<tbody  id="semesterTBody">
								</tbody>
							</table>
						</div> 
					</div> 
									<input name="tempId" type="hidden" class="field" id="tempId"   />
									<input name="template" type="hidden" class="field" id="template"   />
									<input name="createdby" type="hidden" class="field" id="createdby"   />
									<input name="createdate" type="hidden" class="field" id="createdate"   />
									<input name="status" type="hidden" class="field" id="status"   />
									
 			</div> 
 			
 			
		</form> 
	</div>
 <script src="${pageContext.request.contextPath}/js/jquery.cleditor.min.js"></script>
 </body>
</html>
