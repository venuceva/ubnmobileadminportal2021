
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Banking</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%> 	
 <s:set value="bulkBean.responseJSON" var="respData"/>
<script type="text/javascript" > 
var usersList = new Array();
var groupsList = new Array(); 
var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);
var linkIndex = new Array();
var linkName = new Array();
var linkStatus = new Array();


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

$(document).ready(function () {
	
	//console.log(userGroupData);
	$.each(jsonLinks, function(index, v) {
		linkIndex[index] = index;
		linkName[index] = v.name;
		linkStatus[index] = v.status;
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
	
	if(v_id != 'SEARCH_NO') {
		var disabled_status= $(this).attr('disabled'); 
		var queryString = ''; 
		var v_action = "NO";
		var orgId = "";
		var orgName = "";
		 
		/* var index1 = $(this).attr('index'); */  
		var index1 = $(this).parent().closest('tr').attr('index');
		
		var parentId =$(this).parent().closest('tbody').attr('id'); 
		var searchTrRows = parentId+" tr"; 
		var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
				 
		if(disabled_status == undefined) {  
			
			//console.log(v_id);
			// Anchor Tag ID Should Be Equal To TR OF Index
			$(searchTdRow).each(function(indexTd) {  
				if (indexTd == 0) { 
					orgId=$(this).text().trim();
				 }
				if (indexTd == 1) { 
					orgName=$(this).text().trim();
				 }
			}); 
			
			queryString += 'orgId='+orgId;  
			
			if (v_id ==  "view-organization" ) {  
 				v_action = "viewBulkOrgDetails"; 
			} else if (v_id ==  "edit-organization" ) {  
 				v_action = "editBulkOrgDetails"; 
			} else if(v_id ==  "account-credit") {  
				queryString += '&orgName='+orgName;   
				v_action="orgCashDeposit";  
			} else if(v_id ==  "add-organization") { 
				v_action="bulkPaymentRegistration";  
			} else if (v_id ==  "edit-profile" ) { 
				queryString = 'id='+orgId;   
 				v_action = "editProfileDetails"; 
			} else if (v_id ==  "view-profile" ) { 
				queryString = 'id='+orgId;   
 				v_action = "viewProfileDetails"; 
			}
			
		} else { 
			// No Rights To Access The Link 
		}
		
		if(v_action != "NO") {
			postData(v_action+".action",queryString);
		}
	} else {
		// The below code is for quick searching.
		var txt_sr = $(this).attr('value');
		var parentId =$(this).parent().closest('table').attr('id');  
		
		$('div input[type=text]').each(function(){
			if($(this).attr("aria-controls") == parentId) { 
				$(this).val(txt_sr);
				$(this).trigger("keyup");
			}  
		});
	}
}); 
</script>  
</head>

<body>
<form name="form1" id="form1" method="post" >	
	<div id="content" class="span10"> 
	  <div>
		 <ul class="breadcrumb">
			<li><a href="home.action">Home </a> <span class="divider"> &gt;&gt; </span></li>
 			<li><a href="#">P-Wallet</a> </li>
		</ul>
	</div> 
	
		
	<div class="box-content" id="top-layer-anchor">
		 <div>
			<a href="#" class="btn btn-success" id="add-organization" title='Add New Organization' data-rel='popover' data-content='Creating a new P-Wallet.'><i class='icon icon-plus icon-white'></i>&nbsp;</a>
		</div>	
	</div>
			
		<table height="3">
			<tr>
				<td colspan="3">
					<div class="messages" id="messages">
						<s:actionmessage />
					</div>
					<div class="errors" id="errors">
						<s:actionerror />
					</div>
				</td>
			</tr>
	  </table> 	
      					 
    <div class="row-fluid sortable"><!--/span--> 
		<div class="box span12" id="groupInfo">
                  <div class="box-header well" data-original-title>P-Wallet DashBoard 
					<div class="box-icon"> <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					 <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> </div>
				</div>
           		<div class="box-content">
 					<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable" 
						id="DataTables_Table_0"  >
								<thead>
									<tr> 
										<th>Organization Id</th>
										<th>Organization Name</th>
										<th>Abbreviation</th>
										<th>Issued By</th>										
										<th>Issued Date</th>
										<th>Actions</th> 
 									</tr>
								</thead> 
								<tbody  id="orgTBody">
   									<s:iterator value="#respData.ORG_DATA" var="bulkData" status="bulkDataStatus">
 										<s:if test="#userDetStatus.even == true" > 
											<s:set value="%{'even'}" var="flag"/> 
 										 </s:if>
										 <s:elseif test="#userStatus.odd == true">
		      								<s:set value="%{'odd'}" var="flag"/> 	
		   								 </s:elseif> 
		   								 	<tr class="<s:property value='#flag' />" index="<s:property value='#bulkDataStatus.index' />"  id="<s:property value='#bulkDataStatus.index' />">
 											<td class='hidden-phone'><s:property value="#bulkData['ORG_ID']" /></td>
											<td class='hidden-phone'><s:property value="#bulkData['ORG_NAME']" /> </td>
 											<td class='hidden-phone'><s:property value="#bulkData['ORG_ABBR']" /> </td>
  											<td class='hidden-phone'><s:property value="#bulkData['MAKER_ID']" /></td>
											<td class='hidden-phone'><s:property value="#bulkData['MAKER_DTTM']" />  </td>
											<td> 
												 <p>
													 <a class='btn btn-warning' href='#' id='edit-organization' index="<s:property value='#bulkDataStatus.index' />" title='Edit Organization' data-rel='tooltip'><i class='icon icon-edit icon-white'></i></a>&nbsp;
													 <a class='btn btn-info' href='#' id='view-organization' index="<s:property value='#bulkDataStatus.index' />" title='View Organization' data-rel='tooltip'><i class='icon icon-page icon-white'></i></a>  &nbsp;
													 <a class='btn btn-warning' href='#' id='account-credit' index="<s:property value='#bulkDataStatus.index' />" title='Account Credit' data-rel='tooltip'><i class='icon icon-page icon-white'></i></a>
 												</p>
											</td> 
			                      		</tr>
			                      	</s:iterator> 	 
								</tbody>
							</table>
                    </div>
          </div>
		</div>  
	 
	 <div class="row-fluid sortable"><!--/span--> 
		<div class="box span12" id="groupInfo">
                  <div class="box-header well" data-original-title>Profile DashBoard 
					<div class="box-icon"> <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					 <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> </div>
				</div>
           		<div class="box-content">
 					<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable" 
						id="DataTables_Table_0"  >
								<thead>
									<tr> 
										<th>Profile Id</th>
										<th>Profile Name</th>
										<th>Mobile No</th>
 										<th>Issued By</th>										
										<th>Issued Date</th>
										<th>Actions</th> 
 									</tr>
								</thead> 
								<tbody  id="profileTBody">
   									<s:iterator value="#respData.PROFILE_DATA" var="bulkData" status="bulkDataStatus">
 										<s:if test="#userDetStatus.even == true" > 
											<s:set value="%{'even'}" var="flag"/> 
 										 </s:if>
										 <s:elseif test="#userStatus.odd == true">
		      								<s:set value="%{'odd'}" var="flag"/> 	
		   								 </s:elseif> 
		   								 	<tr class="<s:property value='#flag' />" index="<s:property value='#bulkDataStatus.index' />"  id="<s:property value='#bulkDataStatus.index' />">
 											<td class='hidden-phone'><s:property value="#bulkData['ID']" /></td>
											<td class='hidden-phone'><s:property value="#bulkData['PRF_NAME']" /> </td>
 											<td class='hidden-phone'><s:property value="#bulkData['MOBILE']" /> </td>
  											<td class='hidden-phone'><s:property value="#bulkData['MAKER_ID']" /></td>
											<td class='hidden-phone'><s:property value="#bulkData['MAKER_DTTM']" />  </td>
											<td> 
												 <p>
													 <a class='btn btn-warning' href='#' id='edit-profile' index="<s:property value='#bulkDataStatus.index' />" title='Edit Profile' data-rel='tooltip'><i class='icon icon-edit icon-white'></i></a>&nbsp;
													 <a class='btn btn-info' href='#' id='view-profile' index="<s:property value='#bulkDataStatus.index' />" title='View Profile' data-rel='tooltip'><i class='icon icon-page icon-white'></i></a>  &nbsp;
													 <a class='btn btn-warning' href='#' id='profile-account-credit' index="<s:property value='#bulkDataStatus.index' />" title='Account Credit' data-rel='tooltip'><i class='icon icon-page icon-white'></i></a>
 												</p>
											</td> 
			                      		</tr>
			                      	</s:iterator> 	 
								</tbody>
							</table>
                    </div>
       			</div>
		</div> 
		
</div><!--/#content.span10--> 
</form> 
<form name="form2" id="form2" method="post">

</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
