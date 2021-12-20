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
	if(v=="ACCT_NO_OPEN"){		
		$('#accountopen').css("display","");
	}else{
		$('#transdt').css("display","");
	}
	var feefinaljsonobj = jQuery.parseJSON(feejson);
	
	
	buildtable(feefinaljsonobj,'FEE_TBody');
	

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
			htmlString = htmlString + "<td id=TXN_REF_NO name=TXN_REF_NO >" + jsonObject.TXN_REF_NO + "</td>";
			htmlString = htmlString + "<td id=PURCHASE_USER_NAME name=PURCHASE_USER_NAME >" + jsonObject.PURCHASE_USER_NAME + "</td>";
			htmlString = htmlString + "<td id=ACCOUNT_NO name=ACCOUNT_NO >" + jsonObject.ACCOUNT_NO + "</td>";
			htmlString = htmlString + "<td id=FINAL_AMOUNT name=FINAL_AMOUNT >" + jsonObject.FINAL_AMOUNT + "</td>";	
			htmlString = htmlString + "<td id=MERCHANT_ID name=MERCHANT_ID >" + jsonObject.MERCHANT_ID + "</td>";
			htmlString = htmlString + "<td id=PRODUCT_ID name=PRODUCT_ID >" + jsonObject.PRODUCT_ID + "</td>";
			/* htmlString = htmlString + "<td id=PRODUCT_PRICE name=PRODUCT_PRICE >" + jsonObject.PRODUCT_PRICE + "</td>";
			
			htmlString = htmlString + "<td id=DISCOUNT_PRICE name=DISCOUNT_PRICE >" + jsonObject.DISCOUNT_PRICE + "</td>"; */
			htmlString = htmlString + "<td id=TXN_DATE name=TXN_DATE >" + jsonObject.TXN_DATE + "</td>";
			htmlString = htmlString + "<td id=DELIVERY_STATUS name=DELIVERY_STATUS >" + jsonObject.DELIVERY_STATUS + "</td>";
			htmlString = htmlString + "<td id='' ><a class='btn btn-success' href='#' id='View'  index="+i+"  title='View' data-rel='tooltip'  > <i class='icon icon-book icon-white'></i></a>&nbsp;";

			if(jsonObject.DELIVERY_STATUS=="Sales Order"){
				
				htmlString = htmlString + "<a class='btn btn-primary' href='#' id='Drafting'  index="+i+"  title='Order Drafting' data-rel='tooltip'  > <i class='icon icon-gift icon-white'></i></a>";
	
			}else if(jsonObject.DELIVERY_STATUS=="Order Drafting"){
			
			htmlString = htmlString + "<a class='btn btn-danger' href='#' id='Dispatched'  index="+i+"  title='Dispatched' data-rel='tooltip'  > <i class='icon icon-shopping-cart icon-white'></i></a>";
			}else if(jsonObject.DELIVERY_STATUS=="Dispatched"){
				
				htmlString = htmlString + "<a class='btn btn-warning' href='#' id='Delivered'  index="+i+"  title='Delivered' data-rel='tooltip'  > <i class='icon icon-briefcase icon-white'></i></a>";
			}
			
			htmlString = htmlString + "</td></tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

 


$(document).on('click','a',function(event) {
	
    var $row = jQuery(this).closest('tr');
    var $columns = $row.find('td');

    $columns.addClass('row-highlight');
    var values = "";
     var btn=this.id;
    
    jQuery.each($columns, function(i, item) {
    	
    	if(i==0)
    		{
    		values =  item.innerHTML;
    		}else{
    			values = values +"$"+ item.innerHTML;
    		}
    });
   
  	
	var val = values;
	var code = "";
	var refno = "";
	if(val.match("$"))
		{
		var x = val.split("$");
		code = x[6];
		refno = x[1];
		}

	
	if(btn == "View")
	{
		$("#orgid").val(code);
		$("#refno").val(refno);
		$("#actionmap").val("View");
		
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/Transproductview.action";
	 	$("#form1").submit();
	  return true; 
	}

	if(btn == "Drafting")
		{
		$("#orgid").val(code);
		$("#refno").val(refno);
		$("#actionmap").val("Drafting");
		  $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/TransproductDrafting.action";
		  $("#form1").submit();
		  return true; 
		}
	
	if(btn == "Dispatched")
	{
	$("#orgid").val(code);
	$("#refno").val(refno);
	$("#actionmap").val("Dispatched");
	  $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/TransproductDispatched.action";
	  $("#form1").submit();
	  return true; 
	}
	
	if(btn == "Delivered")
	{
	$("#orgid").val(code);
	$("#refno").val(refno);
	$("#actionmap").val("Delivered");
	  $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/TransproductDelivered.action";
	  $("#form1").submit();
	  return true; 
	}
	
	

	
	
	
    
}); 
		  


		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/lifestyletracking.action';
	$("#form1").submit();
	return true;
}


 
</script> 
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"> LifeStyle Tracking</a></li>
				</ul>
			</div>
			
	
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Transaction Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				
				
				
				
				<div class="box-content"> 
				
				
				
					<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  
						id="DataTables_Table_1" >
						<thead>
							<tr id="transdt" >
								<th width="2%">SNo</th>
								 <th width="8%">Txn Reference No</th>
								 <th width="8%">User Id</th> 
								<th width="8%">Account Number</th>
								<th width="8%">Txn Amount</th>
								<th width="8%">Merchant</th>
								<th width="8%">Product</th>
								<!-- <th width="8%">Product Price</th>
							    <th width="8%">Offer Price</th>		 -->					    
							     <th width="8%">Transaction Date and Time</th> 
							      <th width="8%">Status</th> 
							      <th width="8%">Action</th>   
							</tr>
							
						</thead> 
						
						
						 <tbody id="FEE_TBody">
						
						</tbody>  
					</table>
						
				</div>
			</div>
		</div> 
	 <input type="hidden" id="refno" name="refno"/>
	 <input type="hidden" id="orgid" name="orgid"/>
	 <input type="hidden" id="actionmap" name="actionmap"/>
	 
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
			</div> 
			 
		</div> 
	 

  
   
  
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 