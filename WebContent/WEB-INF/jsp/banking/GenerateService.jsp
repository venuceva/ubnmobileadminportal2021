<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title> </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 <%@taglib uri="/struts-tags" prefix="s"%>  
 
<script type="text/javascript" >
		
var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);
var linkIndex = new Array();
var linkName = new Array();
var linkStatus = new Array();
var serviceSerachKey=""; 

var subServiceList = new Array(); 
var feeTables=new Array();
var dataFee = "";
var dataFee1 = "";	
function postData(actionName,paramString) {
		$('#form2').attr("action", actionName)
				.attr("method", "post"); 
		var paramArray = paramString.split("&");
		 
		 if(paramArray != "" ) {
			$(paramArray).each(function(indexTd,val) {
				var input = $("<input />").attr("type", "hidden").attr("name", val.split("=")[0]).val(val.split("=")[1].trim());
				$('#form2').append($(input));	 
			}); 
		}
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
			var merchantIdSearchKey = $("#DataTables_Table_0_filter >label >input").attr('value').toUpperCase(); 
			// The below code is to check the main textbox 
 			if(merchantIdSearchKey.length == 0 ) { 
				for(var i=0;i<subServiceList.length;i++){
					reqUser=subServiceList[i];
					$("#"+reqUser).hide();
				} 
				for(var i=0;i<feeTables.length;i++){
					reqUser=feeTables[i];
					$("#"+reqUser).hide();
				} 
			} else {
				// Checking the text other than master text box is equal to zero or not.
				 if(storeSearchKey.length == 0) { 
					for(var i=0;i<feeTables.length;i++){
						reqUser=feeTables[i];
						$("#"+reqUser).hide();
					} 
				} else {   
						// Checking the Dependency of the master in the child Dashboard.
						if(noOfRows == 1 ) {
							var index=1;
							$('#'+tabData+' tbody tr').each(function () {
								$('td',this).each(function (){
								 tds[index]=$(this).text().trim();
								 index++;
								});
							});  
						 
							var usersTableSrch=(storeSearchKey+"_SUBSERVICE").toUpperCase();
							var rowFetFromTab = tds[2].toUpperCase()+"_SUBSERVICE";
							//console.log(rowFetFromTab+"<==>"+ usersTableSrch);
 							
							for(var i=0;i<subServiceList.length;i++) { 
								try {
									reqUser = subServiceList[i].toUpperCase(); 
									//console.log("[" +reqUser+"<==>"+ usersTableSrch +"] [" +rowFetFromTab+"<==>"+ reqUser+"]");
									 
									if( reqUser == usersTableSrch 
										|| rowFetFromTab == reqUser) {
										$("#"+subServiceList[i]).show();
									} else {
										//TODO: Here to hide the current Table GRID.
										//$("#"+subServiceList[i]).hide();
									}
								} catch(e) {
									console.log(e);
								}  
							}  
							var userRightsSrc = (storeSearchKey.replace(".","_")+"_FEE").toUpperCase();
							rowFetFromTab = tds[2].replace(".","_").toUpperCase()+"_FEE";
						 	 
							for(var i=0;i<=feeTables.length;i++){
								//console.log("[" +rowFetFromTab+"<==>"+ reqUser +"]");
								try {
									if(feeTables[i] != undefined) { reqUser=feeTables[i].toUpperCase(); }
									 
									if( reqUser == rowFetFromTab) {
										$("#"+feeTables[i]).show();
									} else $("#"+feeTables[i]).hide();
								} catch(e) {
									console.log(e);
								} 
							}  
						} else { 
 								for(var i=0;i<feeTables.length;i++){
									reqUser=feeTables[i];
									$("#"+reqUser).hide();
								}   
						}
					}  
			 }
		}); 
	
		 
	// Search For Top Layer
	$('#top-layer-anchor').find('a').each(function(index) {
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
}); 
	
	 
$(document).on('click','a',function(event) {
	 var v_id=$(this).attr('id');
	 
	if(v_id != 'SEARCH_NO') {
		var disabled_status= $(this).attr('disabled'); 
		var queryString = ''; 
		var v_action = "NO";
		var serviceCode ="";
		var serviceName ="";
		var subServiceCode ="";
		var feeCode ="";
		var feename="";
		 
		var index1 = $(this).attr('index');  
		var parentId =$(this).parent().closest('tbody').attr('id'); 
		var searchTrRows = parentId+" tr"; 
		var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
		
		if(disabled_status == undefined) {  
			if( v_id == 'sub-service-create'
				|| v_id == 'service-view' ) {
			
				 // Anchor Tag ID Should Be Equal To TR OF Index
				$(searchTdRow).each(function(indexTd) { 
					 if (indexTd == 1) {
						// Frequency
						serviceCode=$(this).text().trim();
					 }   if(indexTd == 2) {
						serviceName=$(this).text().trim();
					 }   if(indexTd == 3) {
					 }   if(indexTd == 4) {
					 }   
				}); 
				queryString+='serviceCode='+serviceCode;
				
				if(v_id == 'sub-service-create') {
					queryString+='&serviceName='+serviceName;
					v_action="subServiceCreateScreenAct";
				} else {
					v_action="serviceViewAct";
				} 
				
			}  else if( v_id == 'fee-create' 
						|| v_id == 'sub-service-view') {
						
				// Anchor Tag ID Should Be Equal To TR OF Index
				$(searchTdRow).each(function(indexTd) {  
					if(indexTd == 1) {
						subServiceCode=$(this).text().trim();
					 } else if(indexTd == 2) {
					 } else if (indexTd == 3) {
						serviceCode=$(this).text().trim();
					 } else if(indexTd == 4) {
						feeCode=$(this).text().trim();
					 } 
				});
			
				//queryString+='serviceCode='+serviceCode+'&subServiceCode='+subServiceCode+'&feeCode='+feeCode; 
				queryString+='serviceCode='+serviceCode+'&subServiceCode='+subServiceCode; 
				
				if( v_id == 'sub-service-view') 
					v_action="viewSubServiceAct";
				else v_action="createFeeAct";
				
			} else if( v_id == 'fee-view') {
				// Anchor Tag ID Should Be Equal To TR OF Index
				$(searchTdRow).each(function(indexTd) {  
					if (indexTd == 1) {
						// Frequency
						feeCode=$(this).text().trim();
						//alert(terminalId);
					 } else if(indexTd == 2) {
					 } else if(indexTd == 3) {
					 } else if(indexTd == 4) {
					 } 
				}); 
			
				queryString+='feeCode='+feeCode;
				v_action="viewFeeDetailsAct";
			} else if( v_id == 'fee-dashboard') {  
				v_action="getFeeDashboardAct";
			} else if( v_id == 'add-new-service') {  
				v_action="merchantFeeAssign";
			} else if( v_id == 'register-bin') {  
				v_action="registerBinAct";
			} else if( v_id == 'merchant-fee-assigning') {  
				v_action="merchantFeeAssign";		
			}
			else if( v_id == 'register-process-code') {  
				v_action="registerProcessingCodeAct";
			} else if( v_id == 'register-huduma-service') {  
				v_action="registerHudumaServiceAct";
			} else if( v_id == 'view-register-bin') {  
				v_action="getBinViewDetAct";
			}else if( v_id == 'view-register-process-code') {  
				v_action="getProcessingCodeViewDetAct";
			}else if( v_id == 'view-register-huduma-service') {  
				v_action="getHudumaServiceViewDetAct";
			} else if( v_id == 'fee-authorization') {  
				v_action="allFeeAuthorization";
				
			}else if( v_id == 'fee-Code-Modify') {  
				$(searchTdRow).each(function(indexTd) {  
					if (indexTd == 1) {
						// Frequency
						feeCode=$(this).text().trim();
					
						//alert(terminalId);
					 } else if(indexTd == 2) {
						feename=$(this).text().trim();
					
					 } else if(indexTd == 3) {
					 } else if(indexTd == 4) {
					 } 
				}); 
			
				queryString+='feeCode='+feeCode+'&feename='+feename;
				v_action="FeeCodeModyact";
			}  
		} else { 
			// No Rights To Access The Link 
		}
		
		if(v_action != "NO") {
			postData(v_action+".action",queryString);
			 
			//$("#form1")[0].action="<%=ctxstr%>/<%=appName %>/"+v_action+".action"+queryString;
			//$("#form1").submit();
		} 
	} else {
		// The below code is for quick searching.
		var txt_sr = $(this).text().trim();
		var parentId =$(this).parent().closest('table').attr('id');  
		
		$('div input[type=text]').each(function(){
			if($(this).attr("aria-controls") == parentId)  {
				$(this).val(txt_sr);
				$(this).trigger("keyup");
			}  
		});
	} 

}); 
		
	
function getBinView(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/getBinViewDetAct.action';
	$("#form1").submit();
	return true;
}

function getProcessingCodeView(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/getProcessingCodeViewDetAct.action';
	$("#form1").submit();
	return true;
}

function getHudumaCodeView(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/getHudumaServiceViewDetAct.action';
	$("#form1").submit();
	return true;
}


/* $(document).ready(function () { 
	$('#terminalSearch').keyup(function(){
		
		var feeCodeSearch=$('#feeCodeSearch').val();
		if(feeCodeSearch.length > 1){
			var queryString = "method=feeCodeSearchDetails&feeCodeSearch="+feeCodeSearch;	
			$.getJSON("postJson.action", queryString,function(data) { 
				var terminal = data.requestJSON.terminal;
				console.log(terminal);
				var message = data.requestJSON.message;
				if(message=='SUCCESS'){
				//console.log(terminal);
				var detailedRow="";
				detailedRow+='<tr class="popuptr"><td>Merchant ID :</td><td>'+terminal.MERCHANT_ID+'</td></tr>'+
								'<tr class="popuptr"><td>Store Name:</td><td>'+terminal.STORE_NAME+'</td></tr>'+
								'<tr class="popuptr"><td>Terminal Id :</td><td>'+terminal.TERMINAL_ID+'</td></tr>'+
								'<tr class="popuptr"><td>Status:</td><td>'+terminal.STATUS+'</td></tr>'+
								'<tr class="popuptr"><td>Model No :</td><td>'+terminal.MODEL_NO+'</td></tr>'+
								'<tr class="popuptr"><td>Serial No :</td><td>'+terminal.SERIAL_NO+'</td></tr>'+
								'<tr class="popuptr"><td>Maker Date :</td><td>'+terminal.MAKER_DATE+'</td></tr>'+
								'<tr class="popuptr"><td>Merchant Name:</td><td>'+terminal.MERCHANT_NAME+'</td></tr>'+
								'<tr class="popuptr"><td>Store ID :</td><td>'+terminal.STORE_ID+'</td></tr>';
								
					$('#terminalInfo').empty();
					$('#terminalInfo').attr('title', "Searching For :"+terminal.TERMINAL_ID);
					$('#terminalInfo').append(detailedRow);
					$('#terminalInfo').dialog();
				}else{
					alert(message);
				}
			});	
		}
	});
	}); */
	
</script>

	 
</head>

<body>
 <form name="form1" id="form1" method="post" action="">	
	<!-- topbar ends -->
	 <div id="form1-content" class="span10">   
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="serviceMgmtAct.action">Fee Management</a></li>
				</ul>
			</div>  
			<!-- <div class="searchbox"><input type="text" name="feeCodeSearch" id="feeCodeSearch" placeholder="FeeCodeSearch"></div>  -->
			<span class="box-content" id="top-layer-anchor" > 
				<p> 
					<a href="#" id="add-new-service" class="btn btn-info ajax-link"  ><i class='icon icon-plus icon-white'></i>&nbsp;Merchant Fee Assigning</a>  
					<!-- <a href="#" id="register-bin" class="btn btn-info ajax-link"  ><i class='icon icon-plus icon-white'></i>&nbsp;Register Bin</a>  
					<a href="#" id="view-register-bin" class="btn btn-warning ajax-link"  ><i class='icon icon-page icon-white'></i>&nbsp;View Register Bin</a>   -->
					<!--  <a href="#" id="view-register-process-code"  class="btn btn-warning ajax-link"   ><i class='icon icon-star-on icon-white'></i>&nbsp;View Transaction Code</a>-->  
  				    <a href="#" id="fee-dashboard"  class="btn btn-success ajax-link"  ><i class='icon icon-users icon-white'></i>&nbsp;Dashboard</a> 
  				<!--     <a href="#" id="merchant-fee-assigning" class="btn btn-info ajax-link"  ><i class='icon icon-plus icon-white'></i>&nbsp;Merchant Fee Assigning</a>   -->
				    
  				</p>
			</span>
								  
        <div class="row-fluid sortable"><!--/span--> 
			<div class="box span12">
				<div class="box-header well" data-original-title>Service Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				<div class="box-content">
					<fieldset>
						<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  
							id="DataTables_Table_0" >
							<thead>
								<tr>
									<th>S No</th>
									<th>Service Code</th>
									<th>Service Name </th>
 									<th>Actions</th>
								</tr>
							</thead> 
							<tbody id="merchantTBody">
									<s:iterator value="responseJSON['ServiceInfo']" var="serviceGroups" status="serviceStatus"> 
								<s:if test="#serviceStatus.even == true" > 
									<tr class="even" index="<s:property value='#serviceStatus.index' />"  id="<s:property value='#serviceStatus.index' />">
								 </s:if>
								 <s:elseif test="#serviceStatus.odd == true">
      								<tr class="odd" index="<s:property value='#serviceStatus.index' />"  id="<s:property value='#serviceStatus.index' />">
   								 </s:elseif> 
									<td><s:property value="#serviceStatus.index+1" /></td>
									<!-- Iterating TD'S -->
									<s:iterator value="#serviceGroups" status="status" > 
										<s:if test="#status.index == 0" >  
											<td> <a href='#' id='SEARCH_NO' value='<s:property value="value" />'><s:property value="value" /></a></td> 
											<script type="text/javascript"> 
  												if(<s:property value='#serviceStatus.index' /> == 0){
 													dataFee = '<s:property value="value" />_SUBSERVICE';
 												} else {
 													dataFee += ',<s:property value="value" />_SUBSERVICE';
 												} 
 											</script>
										</s:if> 
										<s:elseif test="#status.index == 4" >  
										</s:elseif> 
 										 <s:else>
											 <td class='hidden-phone'><s:property value="value" /></td>
										 </s:else>
									</s:iterator> 
									 <td> 
											<a id='fee-create' class='btn btn-info' href='#' index='<s:property value='#serviceStatus.index' />' title='Create Fee' data-rel='tooltip'><i class='icon icon-plus icon-white'></i></a>&nbsp;
									</td>
 								</tr> 
							</s:iterator>
							</tbody>
						</table>
					</fieldset>
				</div>
			</div>
        </div> 
		<script type="text/javascript"> 
			  $(document).ready(function () {
				  dataFee = dataFee.split(",");
					$.each(dataFee, function(indexLink, val) {	
						 subServiceList[indexLink]=val;	 
					}); 					
			  }); 
			 
		</script>
		<s:set value="subServiceJSON" var="subServiceData"/> 
		<s:set value="%{'_SUBSERVICE'}" var="searchKey"/> 
		
		<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
		  	<s:param name="jsonData" value="#subServiceData"/>  
 		    <s:param name="searchData" value="#searchKey"/>  
		</s:bean>
		<div  id="subservices">
			<s:set value="#jsonToList.data" var="userDetails" />  
		 	<s:iterator value="#userDetails" var="userInDetails" status="userInDetStatus"> 
             <div class="row-fluid sortable" id='<s:property  value="key" />' style="display:none">
				<div class="box span12">
					<div class="box-header well" data-original-title>Fee Information
						<div class="box-icon"> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
						</div>
					</div> 
					<div class="box-content" id='UserTable<s:property value="#userInDetStatus.index+1" />' > 
						<table style = 'border: 1px solid #d7d7d7; font-family: Arial, Helvetica, sans-serif;font-size: 12px; color: #000000; font-weight: normal;' width='100%'   
							class='table table-striped table-bordered bootstrap-datatable datatable' 
							id='DataTables_Table_S_<s:property value="#userInDetStatus.index+1" />'>
							<thead>
								<tr>
									<th>S No</th>
									<th>Fee Code</th>
									<th>Fee Name</th> 
 									<th class='hidden-phone'>Service Code</th>
									<th>Actions</th>
								</tr>
							</thead> 
							<tbody id="SubServiceTBody<s:property  value="#userInDetStatus.index+1" />"> 
			                      	<s:set value="value" var="arrayData"/> 
			                      	<s:iterator value="#arrayData" var="userInDetails1" status="userInDetStatus1">
			                      		<!--  Starting iterator for Store DETAILS  -->  
										<s:if test="#userDetStatus.even == true" > 
											<s:set value="%{'even'}" var="flag"/> 
 										 </s:if>
										 <s:elseif test="#userStatus.odd == true">
		      								<s:set value="%{'odd'}" var="flag"/> 	
		   								 </s:elseif> 
		   								 	<tr class="<s:property value='#flag' />" index="<s:property value='#userInDetStatus1.index' />"  id="<s:property value='#userInDetStatus1.index' />">
											<td><s:property value="#userInDetStatus1.index+1" /></td> 
											<td><a href='#' id='SEARCH_NO' value='<s:property value='#userInDetails1["serviceCode"]' />' ><s:property value='#userInDetails1["serviceCode"]' /></a></td>  
											
											<td class='hidden-phone'><s:property value="#userInDetails1['serviceName']" /></td> 
											<td class='hidden-phone'><s:property value="#userInDetails1['subServiceCode']" /></td>  
											<td> 
												  <a  class='btn btn-warning' href='#' id='fee-Code-Modify' index="<s:property value='#userInDetStatus1.index' />" title='FeeCodeModify' data-rel='tooltip'><i class='icon icon-edit icon-white'></i></a>
												  <a  class='btn btn-success' href='#' id='fee-view' index="<s:property value='#userInDetStatus1.index' />" title='View' data-rel='tooltip'><i class='icon icon-book icon-white'></i></a>
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
		<script type="text/javascript"> 
			  $(document).ready(function () {
 				dataFee1 = dataFee1.split(",")
 					$.each(dataFee1, function(indexLink, val) {
						if(val != "")
						feeTables[indexLink]=val;	 
					});  
			  }); 
		</script>
		
		
</form>	
<form name="form2" id="form2" method="post" action="">	
</form>	
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
