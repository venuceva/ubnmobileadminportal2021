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
  
    
 <link rel="stylesheet" type="text/css" media="screen" href='<%= ctxstr %>/css/jquery.cleditor.css' />  

   
<script type="text/javascript" >

var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);
var linkIndex = new Array();
var linkName = new Array();
//var linkStatus = new Array();


$(document).ready(function () { 

	//alert(userLinkData);
	$('.cleditor').cleditor();
	
	var selectedRadVal="";
	$("input[type=radio]").on("click",function(){
		var radId = "";
		radId =$(this).attr("id"); 
		selectedRadVal=$(this).attr("value");
		$('input[type=radio]').each(function(){
			if($(this).attr("id") != radId) {
				$("#"+$(this).attr("id")+"_select").prop('disabled', 'disabled');
				$("#"+$(this).attr("id")+"_select option").each(function(){
					if($(this).attr('value') == "") {
						$(this).attr('selected',true);
					}					
				});
			} else {
				$("#"+radId+"_select").attr('disabled', false);
			}
		});
		 
	}); 
	
	 
	
	
	
 	$.each(jsonLinks, function(index, v) {
		linkIndex[index] = index;
		linkName[index] = v.name;
	//	linkStatus[index] = v.status;
	});  
	
	var storeData ='${responseJSON.CLASS_PROF_LIST}';
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
		var rowCount = $('#announceTBody > tr').length;
		
		colindex = ++ colindex; 
		
		
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+
				"<td >"+colindex+"</td>"+
				"<td >"+v.PROF_IDS+"</span></td>"+	
				"<td class='hidden-phone'>"+v.CLASS_ID+"</span> </td>"+ 
 				"<td>"+v.MAKER_ID+"</span></td>"+
				"<td class='center hidden-phone'>"+v.MAKER_DATE+"</span></td>"+
				"<td><p><a id='class-professor-modify' class='btn btn-success' href='#' index='"+rowindex+"'>Modify</a> &nbsp;</p></tr>";
			$("#announceTBody").append(appendTxt);	
			rowindex = ++rowindex;
	});
		
		
	// Search For Top Layer
	$('#top-layer-anchor').find('a').each(function(index) {
		var anchor = $(this);   
		var flagToDo = false;
		 
		$.each(linkIndex, function(indexLink, v) {	 
				if(linkName[indexLink] == anchor.attr('id'))  {
					flagToDo = true;
				} 
		});
		
		if(!flagToDo) {
			anchor.attr("disabled","disabled");
		} else {
			anchor.removeAttr("disabled");
		} 
	});
	
	//Search For The Links That Are Assigned To TABLE Level
	 $('table > tbody').find('a').each(function(index) {
		var anchor = $(this);   
		var flagToDo = false;
		 
		$.each(linkIndex, function(indexLink, v) {	 
		//console.log(linkName[indexLink] +" === "+ anchor.attr('id') +" >" + (linkName[indexLink] == anchor.attr('id')));
			if(linkName[indexLink] == anchor.attr('id'))  {
				flagToDo = true;
			} 
		}); 
		if(!flagToDo) {
			anchor.attr("disabled","disabled");
		} else {
			anchor.removeAttr("disabled");
		} 
		 
	}); 
	
	
	// for announcement create
	var mydata ='${responseJSON.USER_GROUPS}';

	var json = jQuery.parseJSON(mydata);
	
	$.each(json, function(i, v) {
		var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
		$('#s_group_select').append(options);
	});
	
	

	var config = {
      '.chosen-select'           : {},
      '.chosen-select-deselect'  : {allow_single_deselect:true},
      '.chosen-select-no-single' : {disable_search_threshold:10},
      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chosen-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
	
}); 
	
$(document).on('click','a',function(event) {
	var v_id=$(this).attr('id');
	var disabled_status= $(this).attr('disabled'); 
	var queryString = '?'; 
	var v_action = "NO";
	var storeId ="";
	var merchantId ="";
	var terminalId ="";
	var merchantName  ="";
	 
	var index1 = $(this).attr('index');  
	var parentId =$(this).parent().closest('tbody').attr('id'); 
	var searchTrRows = parentId+" tr"; 
	var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
			 
 	if(disabled_status == undefined) {  
		  if (v_id ==  "class-professor-modify" ) { 
 			 
			 // Anchor Tag ID Should Be Equal To TR OF Index
			$(searchTdRow).each(function(indexTd) {  
				 if (indexTd == 1) {
					professorId=$(this).text();
				 }
			});  
			 
			queryString += 'professorId='+professorId;  
			if(v_id ==  "class-professor-modify") {  
				v_action = "assignProfessiorModifyAct";
 			}
			
		} else if(v_id ==  "Class-Professor-mapping-create") { 
			v_action="assignProfessiorAct"; 
		} 
			
	} else {
	
		// No Rights To Access The Link 
	}
	
	if(v_action != "NO") {
		$("#form1")[0].action="<%=ctxstr%>/<%=appName %>/"+v_action+".action"+queryString;
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
.errors {
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
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Class-Professor Mapping</a></li>
				</ul>
			</div>
			
      		<div class="box-content" id="top-layer-anchor">
				 <div>
					<a href="#" class="btn btn-success" id="Class-Professor-mapping-create">
						<i class="icon-note icon-white"></i>Create Class-Professor Mapping</a> &nbsp; 
				 </div>	
			</div>	
		
		<form name="form1" id="form1" method="post" action="">							  
              <div class="row-fluid sortable"><!--/span--> 
				<div class="box span12">
					  <div class="box-header well" data-original-title>Class-Professor Mapping Details
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
										<th>S No</th>
										<th>Professor Id</th>
										<th class="hidden-phone">Class Id</th>
										<th class="center hidden-phone">Generated By</th>
										<th class="hidden-phone">Generation Date</th>
										<th >Actions</th>
									</tr>
								</thead> 
								<tbody  id="announceTBody">
								</tbody>
							</table>
						</div> 
					</div> 
 			</div> 
		</form> 
	</div>
 <script src="<%= ctxstr %>/js/jquery.cleditor.min.js"></script>
 <script type="text/javascript" src='<%= ctxstr %>/js/jquery.dataTables.min.js'></script>
 <script type="text/javascript" src='<%= ctxstr %>/js/datatable-add-scr.js'></script> 
 </body>
</html>
