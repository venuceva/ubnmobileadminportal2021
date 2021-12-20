
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
		var LIMIT_CODE=(v.LIMIT_CODE == undefined) ? "" : v.LIMIT_CODE;
		var LIMIT_DESC=(v.LIMIT_DESC == undefined) ? "" : v.LIMIT_DESC;
		var CREATE_DT=(v.CREATE_DT == undefined) ? "" : v.CREATE_DT;
		
		
		index=colindex-1;
		
		var tabData="DataTables_Table_0";
		
			
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
			"<td >"+colindex+"</td>"+
			"<td >"+LIMIT_CODE+"</span></td>"+
			"<td>"+LIMIT_DESC+"</span></td>"+
			"<td>"+CREATE_DT+"</span></td>"+
			"<td><p><a id='B"+LIMIT_CODE+"' class='btn btn-success' href='dsalimitdetailssearch.action?limitcode="+LIMIT_CODE+"&actiontype=VIEW' index='"+rowindex+"' title='View' data-rel='tooltip'><i class='icon icon-book icon-white'></i></a> &nbsp;&nbsp; "+
			"<a id='B"+LIMIT_CODE+"' class='btn btn-warning' href='dsalimitdetailssearch.action?limitcode="+LIMIT_CODE+"&actiontype=MODIFY' index='"+rowindex+"' title='Modify' data-rel='tooltip' ><i class='icon icon-edit icon-white'></i></a> &nbsp;&nbsp; "+
			/* "<a id='B"+LIMIT_CODE+"' class='btn btn-info' href='dsalimitdetailssearch.action?limitcode="+LIMIT_CODE+"&actiontype=HISTORY' index='"+rowindex+"' title='History' data-rel='tooltip' ><i class='icon icon-plus icon-white'></i></a></p>"; */
			"</td></tr>";
			i++;
			$("#merchantTBody").append(appendTxt);	
			rowindex = ++rowindex;
	}); 
	
	
}); 

function radioselect(){
	 $('#errormsg').text("");
}












	


function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
	$("#form1").submit();
	return true;
}


$.validator.addMethod("regex", function(value, element, regexpr) {          
return regexpr.test(value);
}, ""); 



	
</script>


	 
</head>


<body>
<form name="form1" id="form1" method="post" action="">	
<!-- topbar ends -->

	
		<div id="content" class="span10">  
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="dsalimitmng.action">Account Set Limit</a> </li>
				</ul>
			</div>
			
			
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 <span>
				<a href="dsalimitdetailssearch.action?limitcode=NO_DATA&actiontype=NEW" class="btn btn-info" id="group-creation" title='Create New Limit' data-rel='popover'  data-content='Creating a New Limit.'>
				<i class="icon icon-plus icon-white"></i>&nbsp;New Limit Creation</a> &nbsp;							
			</span>
	
        	<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12"> 
					<div class="box-header well" data-original-title>Account Set Limit Details
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
									<th width="10%" >SNo</th>
									<th width="20%">Limit Code</th>
									<th width="20%">Limit description</th>
									<th width="20%">Date</th>
									<th width="20%">Action</th>
								</tr>
								
							</thead>

							<tbody  id="merchantTBody">
							</tbody>
							
						</table>
					</fieldset>
              </div>
             </div>
            </div>
           
			  <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Home" />
			</div> 
			
			
	</div> 
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
