<!DOCTYPE html>
<html lang="en">
<head>
<%@taglib uri="/struts-tags" prefix="s"%> 
<meta charset="utf-8">
<title> </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
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


 

	var billerIdDetails = "";
 	var billerList = new Array();
 	var userTableRights=new Array(); 
 	
 	
  
	var userLinkData ='${USER_LINKS}';
	var jsonLinks = jQuery.parseJSON(userLinkData);
	var linkIndex = new Array();
	var linkName = new Array();
	var linkStatus = new Array();
	
	function postData(actionName,paramString) {
		$('#form2').attr("action", actionName)
				.attr("method", "post");
		
		var paramArray = paramString.split("&");
		 
		$(paramArray).each(function(indexTd,val) {
			var input = $("<input />").attr("type", "hidden").attr("name", val.split("=")[0]).val(val.split("=")[1].trim());
			$('#form2').append($(input));	 
		}); 
		$('#form2').submit();	
	}
	
    $(document).ready(function () { 
 		$.each(jsonLinks, function(index, v) {
			linkIndex[index] = index;
			linkName[index] = v.name;
			linkStatus[index] = v.status;
		}); 
		 
 		
 		$("input[type=text]").live('keyup',function() { 
 			
			var tabData=$(this).attr('aria-controls');
			var storeSearchKey = $(this).val(); 
			var noOfRows=$('#'+tabData+' tbody tr').length;
			var tds = new Array();
 			var reqUser = "";
 			
			//console.log("TabData ["+$(this).parents().eq(7).attr('id')+"]");
			 
			var merchantIdSearchKey = $("#DataTables_Table_0_filter >label >input").attr('value').toUpperCase(); 
			// The below code is to check the main textbox 
 			if(merchantIdSearchKey.length == 0 ) { 
				for(var i=0;i<billerList.length;i++){
					reqUser=billerList[i];
					console.log(reqUser);
					$("#"+reqUser).hide();
				}  
			} else {
				// Checking the text other than master text box is equal to zero or not.
				 if(storeSearchKey.length == 0) { 
					 
				} else {   
						// Checking the Dependency of the master in the child Dashboard.
						 
							var index=1;
							$('#'+tabData+' tbody tr').each(function () {
								$('td',this).each(function (){
								 tds[index]=$(this).text().trim();
								 index++;
								});
							});  
						 
							var usersTableSrch=(storeSearchKey+"_BILLER_IDS").toUpperCase();
							var rowFetFromTab = tds[2].toUpperCase()+"_BILLER_IDS";
						 
							
							for(var i=0;i<billerList.length;i++) {
								reqUser = billerList[i].toUpperCase();
								 
								//console.log("asdasdada"+billerList[i].toUpperCase());
								console.log("[" +reqUser+"<==>"+ usersTableSrch +"] [" +rowFetFromTab+"<==>"+ reqUser+"]");
								 
								if( reqUser == usersTableSrch 
									|| rowFetFromTab == reqUser) {
									$("#"+billerList[i]).show();
								} else {
									//$("#"+billerList[i]).hide();
								}
							}  
						 
						/* } else {
							
 								  for(var i=0;i<userTableRights.length;i++){
									reqUser=userTableRights[i];
									$("#"+reqUser).hide();
								}    
						} */
					}  
			 }
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
}); 

$(document).on('click','a',function(event) {
	var v_id=$(this).attr('id'); 
	var v_role = $(this).attr('role'); 
	
	if(v_id != 'SEARCH_NO') {
		
		var disabled_status= $(this).attr('disabled'); 
		var queryString = 'entity= '; 
		var v_action = "NO";
		
		var groupId = "";  
		var userId = "";   
		 
		var index1 = $(this).attr('index');  
		var parentId =$(this).parent().closest('tbody').attr('id'); 
		var searchTrRows = parentId+" tr"; 
		var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
		var id = "";
		var billerType = "";
		var billerId ="";
		
 		
		if(disabled_status == undefined) {   
				if(v_id ==  "add-biller-type") {
					v_action="addBillerType";
				} else if(v_id == "create-biller-id"
							|| v_id == "modify-biller-type"
							|| v_id == "view-biller-type"
							|| v_id == "en-ds-biller-type"){
 					 // Anchor Tag ID Should Be Equal To TR OF Index
						$(searchTdRow).each(function(indexTd) {  
							if (indexTd == 1) {  
								billerType=$(this).text().trim();
							 }   if(indexTd == 2) { 
							 }   if(indexTd == 3) {
 							 }   if(indexTd == 4) {
							 }  
						}); 

						queryString += '&id='+v_role+'&billerType='+billerType;  
						
						if(v_id ==  "create-biller-id") {  
							v_action = "addBillerId";
 						} else if(v_id ==  "modify-biller-type") { 
							v_action="getBillerTypeDetails";  
							queryString += '&method=modify'
						} else if(v_id ==  "view-biller-type") {  
							v_action = "viewBillerTypeDetails";
							queryString += '&method=view'
						} else if(v_id ==  "en-ds-biller-type") { 
							v_action="actDeactBillerDetails";  
							queryString += '&method=actdeact';
						} 
 						 
				} else if(v_id == "modify-biller-id"
						|| v_id == "view-biller-id"
 						|| v_id == "en-ds-biller-id"){
					
					$(searchTdRow).each(function(indexTd) {  
						if (indexTd == 1) { 
							billerId=$(this).text().trim();
						 }   if(indexTd == 2) {
 						 }   if(indexTd == 3) {
						 }   if(indexTd == 4) {
						 }  
					}); 
					
					queryString +='&billerId='+billerId;
					
					if( v_id ==  "modify-biller-id") {  
						v_action = "modifyBillerId";
						} else if(v_id ==  "view-biller-id") { 
						v_action="viewBillerId";  
						//queryString += '&method=modify'
					}   else if(v_id ==  "en-ds-biller-id") { 
						v_action="modifyBillerIdAcDc";  
					}
				}				
			 
		} else {
		
			// No Rights To Access The Link 
		}
		
		console.log(queryString);
		if(v_action != "NO") {
			postData(v_action+".action",queryString);  
		}
	} else {
	
		// The below code is for quick searching.
		var txt_sr = $(this).attr('value');
		var parentId =$(this).parent().closest('table').attr('id'); 
		//console.log("The val ["+txt_sr+"] " ); 
		 
	//$('div input[type=text]').each(function(){});
		$('div input[type=text]').each(function(){
			console.log("["+$(this).attr("aria-controls") +"] == ["+ parentId+"]");
			
			if($(this).attr("aria-controls") == parentId) {
				//console.log("Val ["+$(this).text()+"]"); 
				//$(this).val('');
				$(this).val(txt_sr);
				$(this).trigger("keyup");
			} /*else {
				if(parentId !='DataTables_Table_0') {
					$(this).val('');
				} 
					
			}*/
		});
	}
}); 

</script>

 
</head>

<body>
<form name="form1" id="form1" method="post" >	
	<!-- topbar ends --> 
	<div id="form1-content" class="span10">   
			 
		<!-- content starts -->
		  <div>
			 <ul class="breadcrumb">
				<li><a href="home.action">Home </a> <span class="divider"> &gt;&gt; </span></li>
				<li><a href="#">Mpesa A/C Management</a></li>
			</ul>
		 </div>
		<div class="box-content" id="top-layer-anchor">
			<span>
				<a href="#" class="btn btn-info" id="add-biller-type" title='Add Biller Type' data-rel='popover'  data-content='Creating a new biller type.'>
				<i class="icon icon-web icon-white"></i>&nbsp;Add Biller Type</a> &nbsp;							
			</span> 
		</div> 
						  
	<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Biller Type Information
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
								<!-- <th>Id</th> -->
								<th>Biller Type</th>
  								<th>Description</th>
 								<th>Created By</th>
 								<th>Create Date</th>
 								<th>Credit Account</th>
								<th>Debit Account</th>
								<th>Status</th>
 								<th>Actions</th>
							</tr>
						</thead> 
						<tbody id="userGroupTBody">
 								<s:iterator value="responseJSON['MerchantDashboard']" var="billerData" status="billerDataStatus"> 
 			                      		<!--  Starting iterator for USER DETAILS  --> 
										<s:if test="#userDetStatus.even == true" > 
											<s:set value="%{'even'}" var="flag"/> 
 										 </s:if>
										 <s:elseif test="#userStatus.odd == true">
		      								<s:set value="%{'odd'}" var="flag"/> 	
		   								 </s:elseif> 
		   								 	<tr class="<s:property value='#flag' />" index="<s:property value='#billerDataStatus.index' />"  id="<s:property value='#billerDataStatus.index' />">
		   								 	<td class='hidden-phone'><s:property value='#billerDataStatus.index+1' /></td>
 											
											<!-- Iterating TD'S -->
											<s:iterator value="#billerData" status="billerStatus" >
											 <s:if test="#billerStatus.index == 0" > 
											 	<s:set value="value" var="billerIdVal"/>
											 </s:if>
												 
												<s:if test="#billerStatus.index == 1" >  
													<td> <a href='#' id='SEARCH_NO'  value='<s:property value="value" />' role="<s:property value="#billerIdVal" />" class="searchtd"><s:property value="value" /></a></td> 
													<script type="text/javascript">
														if(<s:property value='#billerDataStatus.index' /> == 0){
		 													billerIdDetails += '<s:property value="value" />_BILLER_IDS';
		 												} else {
		 													billerIdDetails += ',<s:property value="value" />_BILLER_IDS';
		 												} 
		 											</script>
												</s:if>
												 <s:elseif test="#billerStatus.index == 2" >
													 <td class='hidden-phone'><s:property value="value" /></td>
												 </s:elseif>
												  <s:elseif test="#billerStatus.index == 3" >
													 <td class='center hidden-phone'><s:property value="value" /></td>
												 </s:elseif>
												 <s:elseif test="#billerStatus.index == 7" >  
														 	<s:set value="value" var="billerStat1"/>
		 													 <s:if test="#billerStat1 == 'Active'" > 
																 <s:set value="%{'label-success'}" var="userstatus"/> 
																 <s:set value="%{'btn btn-primary'}" var="statusclass"/>
																  <s:set value="%{'De-Active'}" var="text1"/> 
																 <s:set value="%{'icon-locked'}" var="text"/>
															</s:if>
															<s:elseif test="#billerStat1 == 'De-Active'" >
																<s:set value="%{'label-warning'}" var="userstatus"/> 
																 <s:set value="%{'btn btn-success'}" var="statusclass"/> 
																 <s:set value="%{'Activate'}" var="text1"/> 
																 <s:set value="%{'icon-unlocked'}" var="text" /> 
																
															 </s:elseif>
															 <s:elseif test="#billerStat1 == 'Un-Authorize'" >
																  <s:set value="%{'label-primary'}" var="userstatus"/>
																 <s:set value="%{' '}" var="statusclass"/> 
																 <s:set value="%{' '}" var="text1"/> 
																 <s:set value="%{' '}" var="text"/>
															 </s:elseif>
															 <s:elseif test="#billerStat1 == 'InActive'" >
																  <s:set value="%{'label-info'}" var="userstatus"/>
																 <s:set value="%{' '}" var="statusclass"/> 
																 <s:set value="%{' '}" var="text1"/> 
																 <s:set value="%{' '}" var="text"/>
															 </s:elseif>
															 <td ><a href='#' class='label <s:property value="#userstatus" />' index="<s:property value='#userInDetStatus1.index' />" ><s:property value="value" /></a></td>
												 </s:elseif>
												  <s:elseif test="#billerStatus.index == 0" > 
												 </s:elseif>
												  <s:else>
														<td><s:property value="value" /></td>
												 </s:else> 
											</s:iterator> 
											<td>  
												<p>
													<a class='btn btn-info' href='#' role="<s:property value="#billerIdVal" />"  id='create-biller-id' index="<s:property value='#billerDataStatus.index' />"  title='Create Biller Id' data-rel='tooltip'><i class='icon icon-plus icon-white'></i></a>&nbsp;
												 	<a class='btn btn-warning' href='#' role="<s:property value="#billerIdVal" />"  id='modify-biller-type' index="<s:property value='#billerDataStatus.index' />" title='Edit Biller Type' data-rel='tooltip'><i class='icon icon-edit icon-white'></i></a>
												</p>
												<p> 
													 <a class='btn btn-success' href='#' role="<s:property value="#billerIdVal" />"  id='view-biller-type' index="<s:property value='#billerDataStatus.index' />" title='View Biller Type' data-rel='tooltip'><i class='icon icon-book icon-white'></i></a>&nbsp;
	  												 <a class='btn <s:property value="#statusclass" />' role="<s:property value="#billerIdVal" />"  id='en-ds-biller-type'  href='#' index="<s:property value='#billerDataStatus.index' />" title='<s:property value="#text1" />' data-rel='tooltip' ><i class='icon <s:property value="#text" /> icon-white'></i></a>&nbsp;
  												 </p> 
 											</td> 
			                      		</tr>
			                      	</s:iterator> 	 
						</tbody>
					</table>
				</div>
			</div>
		</div> 
		
		<script type="text/javascript"> 
			  $(document).ready(function () {
				  console.log(billerIdDetails);
				  billerIdDetails = billerIdDetails.split(",");
					$.each(billerIdDetails, function(indexLink, val) {	
						 billerList[indexLink]=val.replace(".","_");	 
					}); 
			  }); 
		</script>  
		<s:set value="responseJSON" var="respData"/> 
		<s:set value="%{'_BILLER_IDS'}" var="searchKey"/> 
		
		<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
		  	<s:param name="jsonData" value="#respData"/> 
  		     <s:param name="searchData" value="#searchKey"/>  
		</s:bean>
	 
	 	<div  id="biller-grp">  
	 		<s:set value="#jsonToList.data" var="billerDetails" />  
		 	<s:iterator value="#billerDetails" var="billerInDetails" status="userInDetStatus"> 
		 	
             <div class="row-fluid sortable" id='<s:property  value="#billerInDetails.key" />'  style="display:none">
				<div class="box span12">
					<div class="box-header well" data-original-title>Biller Id Information
						<div class="box-icon"> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
						</div>
					</div> 
					<div class="box-content" id='UserTable<s:property value="#userInDetStatus.index+1" />' > 
						<table style = 'border: 1px solid #d7d7d7; font-family: Arial, Helvetica, sans-serif;font-size: 12px; color: #000000; font-weight: normal;' width='100%'   
							class='table table-striped table-bordered bootstrap-datatable datatable' id='DataTables_Table_<s:property value="#userInDetStatus.index+1" />'>
							<thead>
								<tr>
									<th>Sno</th>
									<th>Biller ID</th>
									<th>Biller ID Description</th>
									<th>Created By</th>
 									<th>Create Date</th>
									<th>Credit Account</th>
									<th>Debit Account</th>
									<th>Status</th>
									<th>Actions</th>
								</tr>
							</thead> 
							<tbody id="biller-tbody-<s:property  value="#userInDetStatus.index+1" />"> 
			                      	<s:set value="value" var="arrayData"/> 
			                      	<s:iterator value="#arrayData" var="billerInDetails1" status="billerInDetStatus1">
			                      		<!--  Starting iterator for Biller Details --> 
										<s:if test="#userDetStatus.even == true" > 
											<s:set value="%{'even'}" var="flag"/> 
 										 </s:if>
										 <s:elseif test="#userStatus.odd == true">
		      								<s:set value="%{'odd'}" var="flag"/> 	
		   								 </s:elseif> 
		   								 	<tr class="<s:property value='#flag' />" index="<s:property value='#billerInDetStatus1.index' />"  id="<s:property value='#billerInDetStatus1.index' />">
											<td><s:property value="#billerInDetStatus1.index+1" /></td>
											<td class='hidden-phone'><a href='#' id='SEARCH_NO1' value='<s:property value="#billerInDetails1['billerId']" />' role="<s:property value="#billerInDetails1['id']" />" class="searchtd"><s:property value="#billerInDetails1['billerId']" /></a></td>
											<td> <s:property value="#billerInDetails1['description']" /> </td>   
											<td><s:property value="#billerInDetails1['createdby']" /></td> 
											<td class='hidden-phone'><s:property value="#billerInDetails1['createddate']" /></td>
											<td class='center hidden-phone'><s:property value="#billerInDetails1['bfubcract']" /></td>
											<td class='center hidden-phone'><s:property value="#billerInDetails1['bfubdract']" /></td> 
 			 	  							 <s:if test="#billerInDetails1['status'] == 'Active'" > 
												 <s:set value="%{'label-success'}" var="userstatus"/>
												 <s:set value="%{'btn btn-primary'}" var="statusclass"/> 
												 <s:set value="%{'De-Active'}" var="text1"/> 
												 <s:set value="%{'icon-locked'}" var="text" /> 
											</s:if>
											<s:elseif test="#billerInDetails1['status'] == 'De-Active'" >
												  <s:set value="%{'label-warning'}" var="userstatus"/>
												 <s:set value="%{'btn btn-success'}" var="statusclass"/> 
												 <s:set value="%{'Activate'}" var="text1"/> 
												 <s:set value="%{'icon-unlocked'}" var="text"/>
											 </s:elseif>
											 <s:elseif test="#billerInDetails1['status'] == 'Un-Authorize'" >
												  <s:set value="%{'label-primary'}" var="userstatus"/>
												 <s:set value="%{' '}" var="statusclass"/> 
												 <s:set value="%{' '}" var="text1"/> 
												 <s:set value="%{' '}" var="text"/>
											 </s:elseif>
											 <s:elseif test="#billerInDetails1['status'] == 'InActive'" >
												   <s:set value="%{'label-info'}" var="userstatus"/>
												 <s:set value="%{' '}" var="statusclass"/> 
												 <s:set value="%{' '}" var="text1"/> 
												 <s:set value="%{' '}" var="text"/>
											 </s:elseif>
											 <td ><a href='#' class='label <s:property value="#userstatus" />' index="<s:property value='#billerDataStatus.index' />" ><s:property value="#billerInDetails1['status']" /></a></td>
						 	  				 <td>  
												 <a class='btn btn-warning' href='#'   id='modify-biller-id' index="<s:property value='#billerDataStatus.index' />" title='Modify Biller Id' data-rel='tooltip'><i class='icon icon-edit icon-white'></i></a>&nbsp;
												 <a class='btn btn-info' href='#' id='view-biller-id' index="<s:property value='#billerDataStatus.index' />" title='View Biller Id' data-rel='tooltip'><i class='icon icon-page icon-white'></i></a>&nbsp;
 												 <a class='btn <s:property value="#statusclass" />' href='#' id='en-ds-biller-id' index="<s:property value='#billerDataStatus.index' />" title='<s:property value="#text1" />' data-rel='tooltip' ><i class='icon <s:property value="#text" /> icon-white'></i></a>&nbsp; 
 											</td>
			                      		</tr>
			                      	</s:iterator> 
		                    </tbody>
						</table>
					</div>
				</div>
			</div>  
		 	</s:iterator> 
		</div>
	</div>
</form>
<form name="form2" id="form2" method="post">
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>