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
		if(v.STATUS=="A"){
				vstatus="<div class='label label-success' >Active</div>";	
			}else{
				vstatus="<div class='label label-important' >Deactive</div>";	
			}
		
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+
		
		
				"<td >"+colindex+"</td>"+
				"<td >"+v.TEMPLATE_ID+"</span></td>"+
				"<td >"+v.MESSAGE+"</td>"+	
				"<td >"+v.ASS_TEMP_TYPE+"</span></td>"+
 				"<td >"+v.ASS_TEMP_VAL+"</span></td>"+
 				"<td >"+v.FROMTIME+"</span></td>"+
 				"<td >"+v.TOTIME+"</span></td>"+
 				"<td >"+v.CHANNEL+"</span></td>"+
 				"<td >"+v.MAKER_ID+"</span></td>"+
 				
 				"<td >"+vstatus+"</span></td>"+
 				/* "<td >"+v.STATUS+"</span></td>"+ */
				"</tr>";
			
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
	
			if(disabled_status == undefined) {  
				
				 if (v_id ==  "modify-camp") v_action="modifyCampAct";	
			else if (v_id ==  "view-camp") v_action="viewcampaignmgntact";  
			else if (v_id ==  "assiagn-camp") v_action="assigncampaignmgntact";  
			else if (v_id ==  "regester-Campaign") v_action="createcampaignmgntact";  

			$(searchTdRow).each(function(indexTd) {  
				 if (indexTd == 1) tempid=$(this).text().trim(); 
			else if(indexTd == 2)  template=$(this).text().trim();
			else if(indexTd == 3)  createdby=$(this).text().trim();
			else if(indexTd == 4)  createddate=$(this).text().trim();
						}); 

				$("#tempId").val(tempid);
				$("#template").val(template);
				$("#createdby").val(createdby);
				$("#createdate").val(createddate);
				
			
				$("#form1")[0].action="<%=ctxstr%>/<%=appName %>/"+v_action+".action";
				$("#form1").submit();


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
				  <li><a href="campaignmgntact.action">Template Asssigned</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Campaign Management</a></li>
				</ul>
			</div>  
			
      	<form name="form1" id="form1" method="post" action="">							  
              <div class="row-fluid sortable"><!--/span--> 
				<div class="box span12">
					  <div class="box-header well" data-original-title>Template Assigned Details
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
											<th style="width:25%;text-align:center">Message</th>
											<th style="width:10%;text-align:center">Assign Template To</th>
											<th style="width:10%;text-align:center">Value</th>
											<th style="width:10%;text-align:center">From Date</th>
											<th style="width:10%;text-align:center">To Date</th>
											<th style="width:10%;text-align:center">Channel</th>
											<th style="width:10%;text-align:center">Maker Id</th>
											<th style="width:10%;text-align:center">Status</th>
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
									
 			</div> 
 			
 			
		</form> 
	</div>
 <script src="${pageContext.request.contextPath}/js/jquery.cleditor.min.js"></script>
 </body>
</html>
