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
	 var status="${status}";
	//alert(status);
	
	if(status=="DEAL-UN-AUTH" || status=="DEAL-AUTH" || status=="DEAL-REJECT"){
 		$("#offers").show();
 		$("#product").hide();
 		buildtable1(feefinaljsonobj,'FEE_TBody2');
	
	}else{
		buildtable(feefinaljsonobj,'FEE_TBody1');
	}
	

});



function buildtable(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=sno >" + i + "</td>";
			htmlString = htmlString + "<td id=PRODUCT_ID name=PRODUCT_ID ><a href='#' id=" + jsonObject.PRODUCT_ID + " >" + jsonObject.PRODUCT_ID + "</a></td>";
			htmlString = htmlString + "<td id=PRODUCT_NAME name=PRODUCT_NAME >" + jsonObject.PRODUCT_NAME + "</td>";
			htmlString = htmlString + "<td id=PRODUCT_PRICE name=PRODUCT_PRICE >" + jsonObject.PRODUCT_PRICE + "</td>";
			htmlString = htmlString + "<td id=CATEGORY_ID name=CATEGORY_ID >" + jsonObject.CATEGORY_ID + "</td>";
			htmlString = htmlString + "<td id=SUB_CATEGORY_ID name=SUB_CATEGORY_ID >" + jsonObject.SUB_CATEGORY_ID + "</td>";	
			htmlString = htmlString + "<td id=STATUS name=STATUS >" + jsonObject.STATUS + "</td>";
			htmlString = htmlString + "<td id=MAKER_ID name=MAKER_ID >" + jsonObject.MAKER_ID + "</td>";
			htmlString = htmlString + "<td id=MAKER_DTTM name=MAKER_DTTM >" + jsonObject.MAKER_DTTM + "</td>";
			
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

function buildtable1(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=sno >" + i + "</td>";
			htmlString = htmlString + "<td id=ID name=ID ><a href='#' id=" + jsonObject.ID + " >" + jsonObject.ID + "</a></td>";
			htmlString = htmlString + "<td id=PRODUCT_ID name=PRODUCT_ID >" + jsonObject.PRODUCT_ID + "</td>";
			htmlString = htmlString + "<td id=PRODUCT_NAME name=PRODUCT_NAME >" + jsonObject.PRODUCT_NAME + "</td>";
			htmlString = htmlString + "<td id=PRODUCT_PRICE name=PRODUCT_PRICE >" + jsonObject.PRODUCT_PRICE + "</td>";
			htmlString = htmlString + "<td id=OFFER_TYPE name=OFFER_TYPE >" + jsonObject.OFFER_TYPE + "</td>";
			htmlString = htmlString + "<td id=DISCOUNT_CASHBACK name=DISCOUNT_CASHBACK >" + jsonObject.DISCOUNT_CASHBACK + "</td>";	
			htmlString = htmlString + "<td id=STATUS name=STATUS >" + jsonObject.STATUS + "</td>";
			htmlString = htmlString + "<td id=MAKER_ID name=MAKER_ID >" + jsonObject.MAKER_ID + "</td>";
			htmlString = htmlString + "<td id=MAKER_DTTM name=MAKER_DTTM >" + jsonObject.MAKER_DTTM + "</td>";
			
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}
 


$(document).on('click','a',function(event) {
	var status=$("#status").val();
	//alert(status);
	var productId=$(this).attr('id') ;
	//alert(productId);
	var queryString = 'status='+status; 
	
	if(status=="UN-AUTH"){
		queryString += '&productId='+productId; 
		postData("productsApproveAct.action",queryString);
	}else if(status=="AD-AUTH"){
		queryString += '&productId='+productId; 
		postData("productsApproveActiveAct.action",queryString);
		
	}else if(status=="AUTH" || status=="REJECT"){
		queryString += '&productId='+productId; 
		postData("productsInfoViewAct.action",queryString);
		
	}else if(status=="DEAL-UN-AUTH"){
		queryString += '&offerId='+productId; 
		postData("offersApproveAct.action",queryString);
	}else if(status=="DEAL-AUTH" || status=="DEAL-REJECT"){
		queryString += '&offerId='+productId; 
		postData("offersInfoViewAct.action",queryString);
		
	}
	
});


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
 
</script> 
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Markets</a></li>
				</ul>
			</div>
			
	
		<div class="row-fluid sortable" id="product" >
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Product Information
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
								 <th>S No</th>
								     <th>Product Id</th>
								     <th>Product Name</th>
								     <th>Price</th>
								     <th>Category</th>
								     <th>Sub Category</th>
								     <th>Status</th>
								     <th>Maker Id</th>
								     <th>Maker Date</th>  
							</tr>
							
						</thead> 
						
						
						 <tbody id="FEE_TBody1">
						
						</tbody>  
					</table>
						
				</div>
			</div>
		</div> 
		
		
		<div class="row-fluid sortable" id="offers" style="display: none;" >
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Deal/Offers Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				
				
				
				
				<div class="box-content"> 
				
				
				
					<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  
						id="DataTables_Table_2" >
						<thead>
							<tr >
								 <th>S No</th>
									 <th>Offer Id</th>
								     <th>Product Id</th>
								     <th>Product Name</th>
								     <th>Product Price</th>
								     <th>Deal/Offer</th>
								     <th>Discount/Cash Back</th>
								     <th>Status</th>
								     <th>Maker Id</th>
								     <th>Maker Date</th> 
							</tr>
							
						</thead> 
						
						
						 <tbody id="FEE_TBody2">
						
						</tbody>  
					</table>
						
				</div>
			</div>
		</div> 
		
		
		
	 
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
			</div> 
			 
		</div> 
	 

  <input type="hidden" name="status" id="status" value="${status}" /> 
   
  
</form>
 <form name="form2" id="form2" method="post">
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 