<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title> </title>
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

$(document).ready(function () { 
			
		
	var storeData ='${responseJSON1.BILLER_LIST}';
	//alert(mydata);
	var json = jQuery.parseJSON(storeData);
	//alert(json);
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
		//var rowCount = $('#storeTBody > tr').length;
		
		//rowindex = ++rowindex;
		colindex = ++ colindex; 
		
		var appendTxt = "<tr class="+addclass+" index='"+rowindex+"' id='"+rowindex+"' > "+
		"<td >"+colindex+"</td>"+
		"<td><a href='#' id='SEARCH_NO' value='BILLER@"+v.ORGANIGATION_ID+"' aria-controls='DataTables_Table_0'>"+v.ORGANIGATION_ID+"</span></td>"+	
		"<td>"+v.ORGANIGATION_NAME+"</span> </td>"+ 
		 "<td>"+v.MERCHANT_TYPE+"</span> </td>"+
		"<td>"+v.MAKER_ID+"</span></td>"+
		"<td>"+v.MAKER_DT+"</span></td>"+
		"<td>"+
		"<a id='merchant-view' class='btn btn-info' href='#' index="+colindex+" title='Merchant  View' data-rel='tooltip'><i class='icon icon-book icon-white'></i></a>&nbsp; "+
	 	"<a id='merchant-active' class='btn btn-info' href='#' index="+colindex+" title='Active OR Deactive' data-rel='tooltip'><i class='icon icon-lock icon-white'></i></a>&nbsp; </td></tr>"; 
	 	
			$("#merchantTBody").append(appendTxt);	
			rowindex = ++rowindex;
			colindex = ++colindex;
	});

	
		//console.log(userGroupData);
		$.each(jsonLinks, function(index, v) {
			linkIndex[index] = index;
			linkName[index] = v.name;
			linkStatus[index] = v.status;
		}); 
		
		$("input[type=search]").live('keyup',function() {
			
			var ariaControlsval=$(this).attr('aria-controls');
			var contVal=$(this).val();
			//alert(ariaControlsval+"------------"+contVal)
			if(ariaControlsval=="DataTables_Table_0" && contVal=="" ) {
				$("#stores").hide();
				$("#terminals").hide();
			} 
			else if(ariaControlsval=="DataTables_Table_Store" && contVal=="" ) {
				$("#terminals").hide();
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
	
	if(v_id != 'SEARCH_NO') {
		var disabled_status= $(this).attr('disabled'); 
		var queryString = ''; 
		var v_action = "NO";
		var accountNumner  ="";
		var billerCode = "";
		/* var index1 = $(this).attr('index'); */  
		var index1 = $(this).parent().closest('tr').attr('index');
		
		var parentId =$(this).parent().closest('tbody').attr('id'); 
		var searchTrRows = parentId+" tr"; 
		var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
				 
		if(disabled_status == undefined) {  
			if (v_id ==  "merchant-view"
						|| v_id ==  "merchant-active") {
					
					//console.log($(searchTdRow))
					 // Anchor Tag ID Should Be Equal To TR OF Index
						$(searchTdRow).each(function(indexTd) {  
							if (indexTd == 1) {
								// Frequency
								//console.log($(this).text());
								 accountNumner=$(this).text().trim();
							 } 
						}); 

						//queryString += 'billerCode='+billerCode+'&abbreviation='+accountNumner;
					
						if(v_id ==  "merchant-view") { 
							queryString += 'accNumbers='+accountNumner+'&cmd=VIEW';
							v_action = "merchantcreationview";
						}  else if(v_id ==  "merchant-active") { 
							queryString += 'accNumbers='+accountNumner+'&cmd=ACTIVEDEACTIVE';
							v_action = "merchantcreationstatus";
						} 

			}  else if (v_id ==  "biller-account-modify" 
					|| v_id ==  "biller-account-view" ) {
					
					 // Anchor Tag ID Should Be Equal To TR OF Index
						$(searchTdRow).each(function(indexTd) {  
							 if (indexTd == 1) {
								// Frequency
								billerCode=$(this).text().trim();
								//alert(storeId);
							 } else if(indexTd == 2) {
								// Time or Date
								accountNumner=$(this).text().trim();
								//alert(storeName);
							 } else if(indexTd == 3) {
							 } else if(indexTd == 4) {
							 } 
						}); 

						queryString += 'billerCode='+billerCode+'&accountNumber='+accountNumner;
						if(v_id ==  "biller-account-view") { 
							v_action="billerAccountViewAct";  
						} else if(v_id == "biller-account-modify"){
							v_action="billerAccountModifyAct";  
						}
						
			} else if(v_id ==  "merchant-reg") { 
				v_action="merchantcreation";  
			}else if(v_id ==  "discountcode-reg") { 
				v_action="discountpartners";  
			} else if(v_id ==  "merchant-product-offer") { 
				v_action="productoffer";  
			}   
			
		} else { 
			// No Rights To Access The Link 
		}
		
		if(v_action != "NO") {
			//postData(v_action+".action",queryString);
			$("#form1")[0].action="<%=ctxstr%>/<%=appName %>/"+v_action+".action"+queryString;
			$("#form1").submit();
		}
	} else {
		// The below code is for quick searching.
		var txt_sr = $(this).attr('value');
		var parentId =$(this).parent().closest('table').attr('id');  
		txt_sr=txt_sr.split("@")[1];
		$('div input[type=search]').each(function(){
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
	<form name="form1" id="form1" method="post" action="">	
	<!-- topbar ends --> 
	<div id="form1-content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"> Merchant Management</a></li>
				</ul>
			</div>
			
			<div class="box-content" id="top-layer-anchor">
				 <div>
				 
				 <a href="#" class="btn btn-success" id="merchant-reg"   title='Merchant Creation' data-rel='popover'  data-content='Creating a Merchant Creation'><i class='icon icon-plus icon-white'></i>&nbsp;Merchant Creation</a>
				  <a href="#" class="btn btn-success" id="discountcode-reg"   title='Lifestyle Coupon Merchant Creation' data-rel='popover'  data-content='Creating a Lifestyle Coupon Merchant Creation'><i class='icon icon-plus icon-white'></i>&nbsp;Lifestyle Coupon Merchant Creation</a>
				 
					<!-- <a href="#" class="btn btn-success" id="merchant-group"   title='Merchant Group Creation' data-rel='popover'  data-content='Creating a new Merchant Group Creation'><i class='icon icon-plus icon-white'></i>&nbsp;New Merchant Group Creation</a> &nbsp; 
 					<a href="#" class="btn btn-success" id="merchant-product-offer"   title='Offers' data-rel='popover'  data-content='Offers'><i class='icon icon-plus icon-white'></i>&nbsp;Offers</a> &nbsp; 
 				 -->
 				 </div>	
			</div>
								  
           <div class="row-fluid sortable"><!--/span-->
			<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Biller Information
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
								<th>Organization Id</th>
								<th class="hidden-phone">Organization Name</th>
								<th class="hidden-phone">Merchant Type</th>							
								<th>Created By</th>
								<th class="hidden-phone">Date Created</th>
								<th>Actions</th>
							</tr>
						</thead> 
						<tbody id="merchantTBody">
		
						</tbody>
					</table>
				</div>
			</div>
		</div> 
		
		<!-- Loading Stores -->
	 	<div  id="stores"  style="display:none">  
             <div class="row-fluid sortable" id='<s:property  value="key" />'>
				<div class="box span12">
					<div class="box-header well" data-original-title>Biller Account Information
						<div class="box-icon"> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
						</div>
					</div> 
					<div class="box-content"  > 
						<table  width='100%'   
							class='table table-striped table-bordered bootstrap-datatable datatable' 
							id='DataTables_Table_Store'>
							<thead>
								<tr>
									<th>S No</th>
									<th>Biller ID</th>
									<th class='hidden-phone'>Account Number</th>
									<th class='center hidden-phone'>Account Name</th>
									<th>Created By </th>
									<th class='hidden-phone'>Created Date</th>
									<th>Actions</th>
								</tr>
							</thead> 
							<tbody id="storeTBody"> 
		                    </tbody>
						</table>
					</div>
				</div>
			</div>  
		</div>
		
	</div> 
	 
	
</div>  
</form>
<form name="form2" id="form2" method="post">

</form>	

<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
