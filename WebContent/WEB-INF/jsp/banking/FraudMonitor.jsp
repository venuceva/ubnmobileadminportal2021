
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
		var USERID=(v.USERID == undefined) ? "" : v.USERID;
		var TXN_AMOUNT=(v.TXN_AMOUNT == undefined) ? "" : v.TXN_AMOUNT;
		var TRANS_DATE=(v.TRANS_DATE == undefined) ? "" : v.TRANS_DATE;
		var MOBNUM=(v.MOBNUM == undefined) ? "" : v.MOBNUM;
		var REQUEST_CHANNEL=(v.REQUEST_CHANNEL == undefined) ? "" : v.REQUEST_CHANNEL;
		var ERROR_DESC=(v.ERROR_DESC == undefined) ? "" : v.ERROR_DESC;
		var ID=(v.ID == undefined) ? "" : v.ID;
		
		
		index=colindex-1;
		
		var tabData="DataTables_Table_0";
			
			
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
			"<td >"+colindex+"</td>"+
			"<td >"+USERID+"</span></td>"+
			"<td>"+TXN_AMOUNT+"</span></td>"+
			"<td>"+TRANS_DATE+"</span></td>"+
			"<td>"+MOBNUM+"</span></td>"+
			"<td >"+REQUEST_CHANNEL+"</span></td>"+
			"<td>"+ID+"</span></td>"+
			"<td>"+ERROR_DESC+"</span></td>"+
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
	 
    $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/channelmapping.action';
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
				  <li> <a href="channelmanagement.action">Fraud Monitoring</a> </li>
				</ul>
			</div>
			
			
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
	 
			
        	<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12"> 
					<div class="box-header well" data-original-title>Fraud Transaction
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
									<th>User Id/mobile no</th>
									<th>Txn Amount</th>
									<th>Txn Date</th>
									<th>Mobile No</th>
									<th>Channel</th>
									<th>Customer Id</th>
									<th>Fraud Description</th>
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
