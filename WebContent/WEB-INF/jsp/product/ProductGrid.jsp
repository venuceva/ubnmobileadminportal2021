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
	
	$(document).on('click','a',function(event) {
		var v_id=$(this).attr('id');
		
		if(v_id != 'SEARCH_NO') {
			var disabled_status= $(this).attr('disabled'); 
			var queryString = ''; 
			var v_action = "NO";
			var productCode ="";
		 
			/* var index1 = $(this).attr('index'); */  
			var index1 = $(this).parent().closest('tr').attr('index');
			
			var parentId =$(this).parent().closest('tbody').attr('id'); 
			var searchTrRows = parentId+" tr"; 
			var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
					 
			if(disabled_status == undefined) {  
				if     (v_id ==  "Product-modify" 
						|| v_id ==  "Product-view"  ) {
						
						 //console.log($(searchTdRow))
						 // Anchor Tag ID Should Be Equal To TR OF Index
							$(searchTdRow).each(function(indexTd) {  
								if (indexTd == 1) {
									// Frequency
									//console.log($(this).text());
									  productCode=$(this).text();
									  productCode=$(this).text().trim();
									  
								 }   if(indexTd == 2) {
									// Time or Date
									  binDesc=$(this).text().trim();
								 }   if(indexTd == 3) {
									// email ids
								 }   if(indexTd == 4) {
								 }  
							}); 

							queryString +='productCode='+productCode; 

							 
							  if(v_id == "Product-modify") { 
								v_action="ProductModifyAct";  
							  } else if(v_id == "Product-view") {  
								 v_action ="ProductView";
								 alert(productCode);
							  }	 
							 
				      }   
				}        
			} else { 
				// No Rights To Access The Link 
			}
	
			if(v_action  != "NO") {
				//postData(v_action+".action",queryString);
				v_action=v_action+".action?"+queryString;
				//alert(queryString);
				  $("#form1")[0].action="<%=ctxstr%>/<%=appName %>/"+v_action;
				 $("#form1").submit();
			 
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

});
 function createBin(){
	    $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/binCreationAct.action';
		$("#form1").submit();
		return true;
 }
		 

 
</script> 
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	<!-- topbar ends --> 
	<div id="form1-content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"> Bin Group Management</a></li>
				</ul>
			</div>
			
			 
								  
           <div class="row-fluid sortable"><!--/span-->
			<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Bin Group Information
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
								<th>PRODUCT CODE</th>
								<th>PRODUCT DESCRIPTION</th>
							    <th>PRODUCT CURRENCY</th>  
								<th>Actions</th>
							</tr>
						</thead> 
						 <tbody id="binTBody">
							  <s:iterator value="responseJSON['VIEW_PRODUCT']" var="userGroups" status="userStatus"> 
								<s:if test="#userStatus.even == true" > 
									<tr class="even" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
								 </s:if>
								 <s:elseif test="#userStatus.odd == true">
      								<tr class="odd" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
   								 </s:elseif> 
									<td><s:property value="#userStatus.index+1" /></td>
									
									<!-- Iterating TD'S -->
									  <s:iterator value="#userGroups" status="status" > 
										<s:if test="#status.index == 0" >  
											<td> <a href='#' id='SEARCH_NO' value='<s:property value="value" />'><s:property value="value" /></a></td> 
											 
											<script type="text/javascript">
											  if(<s:property value='#userStatus.index' /> == 0){
													binDetails += '<s:property value="value" />_BINGROUP';
												} else {
													binDetails += ',<s:property value="value" />_BINGROUP';
												} 
											   
 											</script>
										</s:if>
										 <s:elseif test="#status.index == 1" >
											 <td class='hidden-phone'><s:property value="value"  /></td>
										 </s:elseif> 
										  
										  <s:elseif test="#status.index == 2" >
												<s:set var="binStatus" value="value" />
												<s:if test="#binStatus == 'Active'" > 
												<s:set value="%{'false'}" var="labelstatus" scope="page"/>
													 <s:set value="%{'label-success'}" var="binlabel" />
													 <s:set value="%{'btn btn-danger'}" var="statusclass" /> 
													 <s:set value="%{'Deactivate'}" var="text1" /> 
													 <s:set value="%{'icon-unlocked'}" var="text" /> 
												</s:if>
											
											<td class='hidden-phone'><s:property value="value"  /></td>										
 										 	
 										 </s:elseif> 
 										 <s:elseif test="#status.index == 3" >
											 <td class='hidden-phone'><s:property value="value" /></td>
										 </s:elseif>
									</s:iterator>   
									<td><a id='Create-binGroup' class='btn btn-success' href='#' index="<s:property value='#userInDetStatus1.index' />" title='Create BinGroup' data-rel='tooltip'><i class='icon icon-plus icon-white'></i></a>&nbsp;
										<a id='Product-modify' class='btn btn-warning' href='#' index="<s:property value='#userInDetStatus1.index' />" title='Product Modify' data-rel='tooltip'><i class='icon icon-edit icon-white'></i></a>&nbsp;
										<a id='Product-view' class='btn btn-info' href='#' index="<s:property value='#userInDetStatus1.index' />" title='View Product' data-rel='tooltip'><i class='icon icon-note icon-white'></i></a>&nbsp;
										<a id='binGroup-GridScreen' class='btn btn-info' href='#' index="<s:property value='#userInDetStatus1.index' />" title='BinGroup-GridScreen' data-rel='tooltip'><i class='icon icon-note icon-white'></i></a>&nbsp;	
 									    <s:if test="#attr.labelstatus != 'true'" > 
											<a id='merchant-terminate' class='btn <s:property value="#statusclass" />' href='#' index="<s:property value='#userInDetStatus1.index' />" title='<s:property value="#text1" />' data-rel='tooltip' ><i class='icon <s:property value="#text" /> icon-white'></i></a>&nbsp; 
										</s:if>
										
									</td>
 								  
							</s:iterator>  
						</tbody>  
					</table>
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
	 
	<div  id="users"> 
	</div> 
   
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 