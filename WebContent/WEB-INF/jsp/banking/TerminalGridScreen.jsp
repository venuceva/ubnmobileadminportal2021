<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
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
function postData(actionName,paramString){
	$('#form2').attr("action", actionName)
			.attr("method", "post");
	
	var paramArray = paramString.split("&");
	var input = "" ;
	$(paramArray).each(function(indexTd,val) {
		if(val != "") {
			input = $("<input />").attr("type", "hidden").attr("name", val.split("=")[0]).val(val.split("=")[1].trim());
			$('form').append($(input));	 
		}
	});	

	$('form').submit();	
} 
var storeDetails	= "";
var terminalDetails	= "";

var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);
var linkIndex = new Array();
var linkName = new Array();
var linkStatus = new Array();
var terminalTables=new Array(); //  store_TERMINALS;
var storeList = new Array(); // merchantid_STORES



$(document).on('click','a',function(event) {
	var v_id=$(this).attr('id');

	var disabled_status= $(this).attr('disabled'); 
		var queryString = ''; 
		var v_action = "NO";
		var storeId ="";
		var merchantId ="";
		var terminalId ="";
		var merchantName  ="";
		var storeName  ="";
		 
		/* var index1 = $(this).attr('index'); */  
		var index1 = $(this).parent().closest('tr').attr('index');
		
		var parentId =$(this).parent().closest('tbody').attr('id'); 
		var searchTrRows = parentId+" tr"; 
		var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
				  
			    if (v_id ==  "terminal-view" 
					|| v_id ==  "terminal-terminate"
					|| v_id ==  "terminal-modify" 
					|| v_id == "terminal-assign-services"
					|| v_id ==  "terminal-assign-user" 
					|| v_id ==  "terminal-create" 
					|| v_id == "terminal-services-view") {
					
					 // Anchor Tag ID Should Be Equal To TR OF Index
					$(searchTdRow).each(function(indexTd) {  
						 if (indexTd == 1) {
							// Terminal
							terminalId=$(this).text().trim();
						 } else if(indexTd == 2) {
							 // Serial No
						 } else if(indexTd == 3) {
							// Time or Date
							storeId=$(this).text().trim();
						 } else if(indexTd == 4) {
							merchantId=$(this).text().trim();
						 } else if(indexTd == 5) {
							merchantName=$(this).text().trim();
						 } 
					}); 

					queryString += 'terminalID='+terminalId+'&storeId='+storeId+'&merchantID='+merchantId;

					if(v_id ==  "terminal-create") {  
						v_action = "createTerminalAct";
					} else if(v_id ==  "terminal-terminate") { 
						v_action="terminateTerminalScreenAct";  
					} else if(v_id ==  "terminal-modify") {  
						v_action = "modifyTerminalScreenAct";
					} else if(v_id ==  "terminal-assign-services") { 
						v_action="assignUserServiceScreenAct";  
					} else if(v_id ==  "terminal-assign-user") {  
						v_action = "assignUserTerminalScreenAct";
					} else if(v_id ==  "terminal-services-view") { 
						v_action="viewAssignedServicesAct";  
					}   else if(v_id ==  "terminal-view") { 
						v_action="viewTerminalScreenAct";  
					} /* else if(v_id ==  "terminal-create") {  
						v_action = "createTerminalAct";
					} */
			}       
	
		
		if(v_action != "NO") {
			postData(v_action+".action",queryString);
			//$("#form1")[0].action="<%=ctxstr%>/<%=appName %>/"+v_action+".action"+queryString;
			//$("#form1").submit();
		}

}); 
 $(document).ready(function () { 
$('#serialNoSearch').keyup(function(){
	
	var terminalSearch=$('#serialNoSearch').val();
	if(terminalSearch.length == 8){
		var queryString = "method=terminalDetails&terminalSearch="+terminalSearch;	
		$.getJSON("postJson.action", queryString,function(data) { 
			var terminal = data.requestJSON.terminal;
			console.log(terminal);
			var status=terminal.STATUS;
			if(status=='A')
				{
				status="Active";
				}
			var message = data.requestJSON.message;
			if(message=='SUCCESS'){
			//console.log(terminal);
			var detailedRow="";
			detailedRow+='<tr class="popuptr"><td>Merchant ID :</td><td>'+terminal.MERCHANT_ID+'</td></tr>'+
							'<tr class="popuptr"><td>Store Name:</td><td>'+terminal.STORE_NAME+'</td></tr>'+
							'<tr class="popuptr"><td>Terminal Id :</td><td>'+terminal.TERMINAL_ID+'</td></tr>'+
							'<tr class="popuptr"><td>Status:</td><td>'+status+'</td></tr>'+
							'<tr class="popuptr"><td>Model No :</td><td>'+terminal.MODEL_NO+'</td></tr>'+
							'<tr class="popuptr"><td>Serial No :</td><td>'+terminal.SERIAL_NO+'</td></tr>'+
							'<tr class="popuptr"><td>Maker Date :</td><td>'+terminal.MAKER_DATE+'</td></tr>'+
							'<tr class="popuptr"><td>Merchant Name:</td><td>'+terminal.MERCHANT_NAME+'</td></tr>'+
							'<tr class="popuptr"><td>Store ID :</td><td>'+terminal.STORE_ID+'</td></tr>';
							
				$('#terminalInfo').empty();
				$('#terminalInfo').attr('title', "Searching For :"+terminal.SERIAL_NO);
				$('#terminalInfo').append(detailedRow);
				$('#terminalInfo').dialog();
			}else{
				//alert(message);
			}
		});	
	}
});
});
</script> 
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	<!-- topbar ends --> 
	<div id="form1-content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"> Terminal Management</a></li>
				</ul>
			</div>
			<!-- <div class="searchbox"><input type="text" name="serialNoSearch" id="serialNoSearch" placeholder="SerialNoSearch"></div> --> 
			<div class="box-content" id="top-layer-anchor">
				 <div>
		<a href="#" class="btn btn-info" id="terminal-create"   title='Add New Terminal' data-rel='popover'  data-content='Creating a new Terminal'><i class='icon icon-plus icon-white'></i>&nbsp;Add New Terminal</a> &nbsp; 
 					<!-- <a href="#" class="btn btn-success" id="merchant-dashboard"   title='Dashboard' data-rel='popover'  data-content='Viewing the Merchants,Stores & Terminals.'><i class='icon icon-users icon-white'></i>&nbsp;Merchant Dashboard</a> &nbsp; -->
			<!--    <a href="#" class="btn btn-warning" id="merchant-users"   title='Merchant Users' data-rel='popover'  data-content='Viewing the Merchants & Users.'><i class='icon icon-users icon-white'></i>&nbsp;Merchant Users</a> &nbsp; -->
			<!--    <a href="#" class="btn btn-success" id="testscreen"   title='testscreen' data-rel='popover'  data-content='testscreen'><i class='icon icon-plus icon-white'></i>&nbsp;testlink</a>   -->
 				 </div>	
			</div>
								  
           <div class="row-fluid sortable"><!--/span-->
			<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Terminal Information 
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
									<th>Terminal ID</th>
									<th>Serial No</th>
									<th class='hidden-phone'>Store ID </th>
									<th class='center hidden-phone'>Merchant ID</th>
									<th class='center hidden-phone'>Assigned To</th>
									<th>Status</th>
									<th class='hidden-phone'>Date Created</th>
									<th style="width: 250px;">Actions</th>
								</tr>
						</thead> 
						<tbody id="TerminalTable<s:property  value="#userInDetStatus.index+1" />"> 
									
			                      	<s:iterator value="responseJSON['TERMINAL_LIST']" var="userInDetails1" status="userInDetStatus1">
			                      		<!--  Starting iterator for Store DETAILS  --> 
										<s:if test="#userDetStatus.even == true" > 
											<s:set value="%{'even'}" var="flag"/> 
 										 </s:if>
										 <s:elseif test="#userStatus.odd == true">
		      								<s:set value="%{'odd'}" var="flag"/> 	
		   								 </s:elseif> 
		   								 	<tr class="<s:property value='#flag' />" index="<s:property value='#userInDetStatus1.index' />"  id="<s:property value='#userInDetStatus1.index' />">
											<td><s:property value="#userInDetStatus1.index+1" /></td> 
											<td><s:property value="#userInDetails1['terminalID']" /></td>  
											<td><s:property value="#userInDetails1['serialNo']" /></td> 
											<td class='hidden-phone'><s:property value="#userInDetails1['storeId']" /></td> 
											<td class='center hidden-phone'><s:property value="#userInDetails1['merchantID']" /></td>
											<td class='center hidden-phone'><s:property value="#userInDetails1['userId']" /></td>  
												<s:if test="#userInDetails1['status'] == 'Active'" > 
													 <s:set value="%{'label-success'}" var="merchStatus" />
													 <s:set value="%{'btn btn-danger'}" var="statusclass"/> 
													 <s:set value="%{'Deactive'}" var="text1"/> 
													 <s:set value="%{'icon-unlocked'}" var="text" /> 
												</s:if>
												<s:elseif test="#userInDetails1['status'] == 'Inactive'" >
														  <s:set value="%{'label-warning'}" var="merchStatus" />
														 <s:set value="%{'btn btn-success'}" var="statusclass"/> 
														 <s:set value="%{'Activate'}" var="text1"/> 
														 <s:set value="%{'icon-locked'}" var="text"/>
													 </s:elseif>
													 
												 <s:elseif test="#userInDetails1['status'] == 'Un-Authorize'" >
													  <s:set value="%{'label-primary'}" var="merchStatus" />
													 <s:set value="%{' '}" var="statusclass"/> 
													 <s:set value="%{' '}" var="text1"/> 
													 <s:set value="%{' '}" var="text"/>
												 </s:elseif> 
											<td><a href='#' class='label <s:property value="#merchStatus" />' index="<s:property value='#userInDetStatus1.index' />" ><s:property value="#userInDetails1['status']" /></a></td>									  
											<td class='hidden-phone'><s:property value="#userInDetails1['makerDate']" /></td> 
											<td>
												<p>
												<s:if test="#userInDetails1['status'] == 'Active'" > 
													<%-- <a class='btn btn-info' href='#' id='terminal-assign-services' index="<s:property value='#userInDetStatus1.index' />" title='Assign Services' data-rel='tooltip'><i class='icon icon-plus icon-white'></i></a>&nbsp;
													<a class='btn btn-success' href='#' id='terminal-services-view' index="<s:property value='#userInDetStatus1.index' />" title='View Services' data-rel='tooltip'><i class='icon icon-book icon-white'></i></a>&nbsp;
													<a class='btn btn-info' href='#' id='terminal-assign-user' index="<s:property value='#userInDetStatus1.index' />" title='Assign Users' data-rel='tooltip'><i class='icon icon-plus icon-white'></i></a> --%>
													<a class='btn btn-warning' href='#' id='terminal-modify' index="<s:property value='#userInDetStatus1.index' />" title='Terminal Modify' data-rel='tooltip'><i class='icon icon-edit icon-white'></i></a>&nbsp;
													</s:if>
													<a class='btn btn-success' href='#' 	id='terminal-view' 	index="<s:property value='#userInDetStatus1.index' />" title='View' data-rel='tooltip'><i class='icon icon-book icon-white'></i></a>&nbsp;
													<s:if test="#userInDetails1['status'] != 'Un-Authorize'" >
												    <a class='btn <s:property value="#statusclass" />' href='#' id='terminal-terminate' index="<s:property value='#userInDetStatus1.index' />" title='<s:property value="#text1" />' data-rel='tooltip'><i class='icon icon-retweet  icon-white'></i></a>
												 </s:if> 
												</p>
												 
											</td> 
									</tr>
								</s:iterator> 
		                    </tbody>
					</table>
				</div>
			</div>
		</div> 
	
	</div> 
	 
	<div  id="users"> 
	</div> 
	
	<div > 
		<div class="terminalSearch" id="terminal_search" style="display:none;">
		<table id="terminalInfo">
		</table>
		</div>
	</div>
</div>  
</form>
<form name="form2" id="form2" method="post">

</form>	
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
