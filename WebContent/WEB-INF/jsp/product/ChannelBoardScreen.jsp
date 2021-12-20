
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
		var REF_NO=(v.REF_NO == undefined) ? "" : v.REF_NO;
		var CHANNEL_NAME=(v.CHANNEL_NAME == undefined) ? "" : v.CHANNEL_NAME;
		var SERVICE_NAME=(v.SERVICE_NAME == undefined) ? "" : v.SERVICE_NAME;
		var ASSIGN_LIMIT=(v.ASSIGN_LIMIT == undefined) ? "" : v.ASSIGN_LIMIT;
		var ASSIGN_FEE=(v.ASSIGN_FEE == undefined) ? "" : v.ASSIGN_FEE;
		var PRODUCT=(v.PRODUCT == undefined) ? "" : v.PRODUCT;
		var APPLICATION=(v.APPLICATION == undefined) ? "" : v.APPLICATION;
		var ASSIGN_LOYALTY=(v.ASSIGN_LOYALTY == undefined) ? "" : v.ASSIGN_LOYALTY;
		
		
		index=colindex-1;
		
		var tabData="DataTables_Table_0";
			
			
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
			"<td >"+colindex+"</td>"+
			"<td >"+PRODUCT+"</span></td>"+
			"<td>"+APPLICATION+"</span></td>"+
			"<td>"+CHANNEL_NAME+"</span></td>"+
			"<td>"+SERVICE_NAME+"</span></td>"+
			"<td >"+ASSIGN_LIMIT+"</span></td>"+
			"<td>"+ASSIGN_FEE+"</span></td>"+
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
				  <li> <a href="channelmanagement.action">Service Mapping</a> </li>
				</ul>
			</div>
			
			
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
	 	<div class="box-content" id="top-layer-anchor">
				 <div>
					<a href="#" class="btn btn-success" id="assignchannel"   title='Assign Channel' data-rel='popover'  data-content='Assign Channel' onClick="createChannelData()"><i class='icon icon-plus icon-white'></i>&nbsp;Assign Channel</a> &nbsp; 
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
									<th>Application</th>
									<th>Channel</th>
									<th>Service</th>
									<th>Set Limit</th>
									<th>Set Fee</th>
									
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
