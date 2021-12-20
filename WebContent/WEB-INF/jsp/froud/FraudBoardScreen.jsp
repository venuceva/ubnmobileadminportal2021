
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>IMPERIAL</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">	
	 
   	 
<script type="text/javascript" >
 

		
$(document).ready(function () {
	var tds=new Array();
	var merchantData ='${responseJSON.Files_List}';

	var json = jQuery.parseJSON(merchantData);
	console.log(json);
	
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";
	var i=0;
	$.each(json, function(i, v) {
		if(val % 2 == 0 ) {
			addclass = "even";
			val++;
		}
		else {
			addclass = "odd";
			val++;
		}  
		var rowCount = $('#merchantTBody > tr').length;

		
		colindex = ++ colindex; 
		var PRODUCT_CODE=(v.PRODUCT_CODE == undefined) ? "" : v.PRODUCT_CODE;
		var FRAUD_DESC=(v.FRAUD_DESC == undefined) ? "" : v.FRAUD_DESC;
		var MAKER_ID=(v.MAKER_ID == undefined) ? "" : v.MAKER_ID;
		var MAKER_DT=(v.MAKER_DT == undefined) ? "" : v.MAKER_DT;
		
		
		index=colindex-1;
		
		var tabData="DataTables_Table_0";
			
			
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
			"<td >"+colindex+"</td>"+
			"<td >"+PRODUCT_CODE+"</span></td>"+
			"<td>"+FRAUD_DESC+"</span></td>"+
			"<td>"+MAKER_ID+"</span></td>"+
			"<td >"+MAKER_DT+"</span></td>"+
			"</tr>";
			i++;
			$("#merchantTBody").append(appendTxt);	
			rowindex = ++rowindex;
	}); 
	
	
}); 

function radioselect(){
	 $('#errormsg').text("");
}












	


function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/agentregmodify.action';
	$("#form1").submit();
	return true;
}


$.validator.addMethod("regex", function(value, element, regexpr) {          
return regexpr.test(value);
}, ""); 


function createChannelData(){
	 
    $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fraudactassignproduct.action';
	$("#form1").submit();
	return true;
}


	
</script>


	 
</head>


<body>
<form name="form1" id="form1" method="post" action="">	
<!-- topbar ends -->

	
		<div id="content" class="span10">  
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="channelmanagement.action">Service Mapping</a> </li>
				</ul>
			</div>
			
			
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
	 	<div class="box-content" id="top-layer-anchor">
				 <div>
					<a href="#" class="btn btn-success" id="assignchannel"   title='Assign Rules to Product' data-rel='popover'  data-content='Assign Rules to Product' onClick="createChannelData()"><i class='icon icon-plus icon-white'></i>&nbsp;Assign Rules to Product</a> &nbsp; 
 				 </div>	
			</div>
			
        	<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12"> 
					<div class="box-header well" data-original-title>Service Mapping Details
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
                  
				<div class="box-content"> 
					<fieldset>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable" 
							id="DataTables_Table_0">
							<thead>
								<tr >
									<th>SNo</th>
									<th>Product</th>
									<th>Fraud Description</th>
									<th>Maker Id</th>
									<th>Maker Date</th>
								</tr>
								
							</thead>

							<tbody  id="merchantTBody">
							</tbody>
							
						</table>
					</fieldset>
              </div>
             </div>
            </div>
           
			
			
			
	</div> 
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
