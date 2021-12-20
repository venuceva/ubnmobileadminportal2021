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
	
	
	buildtable(feefinaljsonobj,'FEE_TBody');
	

});



function buildtable(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+"  >";
			htmlString = htmlString + "<td id=REQUEST_ID"+ i + " name=REQUEST_ID >" + jsonObject.REQUEST_ID + "</td>";
			htmlString = htmlString + "<td id=CUSTOMER_NAME"+ i + " name=CUSTOMER_NAME >" + jsonObject.CUSTOMER_NAME + "</td>";
			 htmlString = htmlString + "<td id=ACCOUNT_NUMBER"+ i + " name=ACCOUNT_NUMBER  >" + jsonObject.ACCOUNT_NUMBER + "</td>";
			htmlString = htmlString + "<td id=PHONE_NUMBER"+ i + " name=PHONE_NUMBER >" + jsonObject.PHONE_NUMBER + "</td>";
			 htmlString = htmlString + "<td id=CARD_TYPE"+ i + " name=CARD_TYPE >" + jsonObject.CARD_TYPE + "</td>";				
			htmlString = htmlString + "<td id=CHANNEL"+ i + " name=CHANNEL >" + jsonObject.CHANNEL + "</td>";
			htmlString = htmlString + "<td id=DATE_OF_REQUEST"+ i + " name=DATE_OF_REQUEST >" + jsonObject.DATE_OF_REQUEST + "</td>";
			htmlString = htmlString + "<td id=FINAL_STATUS"+ i + " name=FINAL_STATUS >" + jsonObject.FINAL_STATUS + "</td>";
		 	htmlString = htmlString + "<td id='' ><a class='btn btn-warning' href='#' id='limitview' index="+i+" val="+i+" title='View' data-rel='tooltip' > View</a></td>";

			htmlString = htmlString + "</tr>";
	
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
	var codedesc = "";
	if(val.match("$"))
		{
		var x = val.split("$");
		code = x[0];
		}

	if(btn == 'limitview')
	{
		//alert($('#status').val());
		$('#requestid').val(code);
		if($('#status').val()=="Open"){
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/debitviewDetailsopen.action';
			$("#form1").submit();
				return true; 
		}else{
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/debitviewDetailsclosed.action';
			$("#form1").submit();
				return true; 
		}
	}
	

	
});  


		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/debitcardrequest.action';
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
					<li><a href="#"> Debit Card Requests</a></li>
				</ul>
			</div>
			
			<div id="dialog" name="dialog" title="View" width="100" style="display:none">
  						<strong><div  id="result"><div id="pie"></div>
  						</div></strong>
					</div>
	
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Debit Card Requests ${responseJSON.status} Details
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
								 <th width="10%">Request Id</th>
								<th width="10%">Customer Name</th>
								<th width="10%">Account Number</th>
								<th width="10%">Phone Number</th>
							    <th width="10%">Card Type</th> 
							    <th width="10%">Channel</th> 
							    <th width="10%">Date Of Request</th>
							    <th width="10%" >Status</th>  
							     <th width="10%" >Action</th>     
							          
							</tr>
							
						</thead> 
						
						
						 <tbody id="FEE_TBody">
						
						</tbody>  
					</table>
					<input type="hidden" name="status" id="status" value="${responseJSON.status}"/>
					<input type="hidden" name="requestid" id="requestid" />
						
				</div>
			</div>
		</div> 
	 <input type="hidden" id="refno" name="refno"/>
	 <input type="hidden" id="userid" name="userid"/>
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
			</div> 
			 
		</div> 
	 

  
   
  
</form>

 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 