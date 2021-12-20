
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">

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
		var RELATION=(v.RELATION == undefined) ? "" : v.RELATION;
		var RELATION_CODE=(v.RELATION_CODE == undefined) ? "" : v.RELATION_CODE;
		var RELATION_ACTION=(v.RELATION_ACTION == undefined) ? "" : v.RELATION_ACTION;
		var PENDING=(v.PENDING == undefined) ? "" : v.PENDING;
		var AUTHORIZED=(v.AUTHORIZED == undefined) ? "" : v.AUTHORIZED;
		var REJECTED=(v.REJECTED == undefined) ? "" : v.REJECTED;
		var DELETED=(v.DELETED == undefined) ? "" : v.DELETED;
		var POSITION=(v.POSITION == undefined) ? "" : v.POSITION;
		var STATUS=(v.STATUS == undefined) ? "" : v.STATUS;
		
		
		index=colindex-1;
		
		var tabData="DataTables_Table_0";
		var appendTxt="";
		if(POSITION=="MAIN"){
			$('#non-printable').val("Home");
			appendTxt="<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
			"<td ><a id='R"+RELATION_CODE+"'  href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=ALL' index='"+rowindex+"'>"+RELATION+"</a></td>"+
			"<td ><a class='label label-success' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=PENDING' >"+PENDING+"</a></td>"+
			"<td ><a class='label label-info' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=AUTHORIZED' >"+AUTHORIZED+"</a></td>"+
			"<td ><a class='label label-inverse' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=REJECTED' >"+REJECTED+"</a></td>"+
			"<td ><a class='label label-warning' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=DELETED' >"+DELETED+"</a></td>"+
			"</tr>";
		}
		
		if(POSITION=="SUB"){
			$('#non-printable').val("Back");
			if(STATUS=="ALL"){
				appendTxt="<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
				"<td >"+RELATION+"</td>"+
				"<td ><a class='label label-success' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=PENDING' index='"+rowindex+"' >"+PENDING+"</a></td>"+
				"<td ><a class='label label-info' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=AUTHORIZED' index='"+rowindex+"' >"+AUTHORIZED+"</a></td>"+
				"<td ><a class='label label-inverse' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=REJECTED' index='"+rowindex+"' >"+REJECTED+"</a></td>"+
				"<td ><a class='label label-warning' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=DELETED' index='"+rowindex+"' >"+DELETED+"</a></td>"+
				"</tr>";	
			}
			if(STATUS=="PENDING"){
				appendTxt="<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
				"<td >"+RELATION+"</td>"+
				"<td ><a class='label label-success' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=PENDING' index='"+rowindex+"' >"+PENDING+"</a></td>"+
				"<td ></td>"+
				"<td ></td>"+
				"<td ></td>"+
				"</tr>";	
			}
			if(STATUS=="AUTHORIZED"){
				appendTxt="<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
				"<td >"+RELATION+"</td>"+
				"<td ></td>"+
				"<td ><a class='label label-info' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=AUTHORIZED' index='"+rowindex+"' >"+AUTHORIZED+"</a></td>"+
				"<td ></td>"+
				"<td ></td>"+
				"</tr>";	
			}
			if(STATUS=="REJECTED"){
				appendTxt="<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
				"<td >"+RELATION+"</td>"+
				"<td ></td>"+
				"<td ></td>"+
				"<td ><a class='label label-inverse' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=REJECTED' index='"+rowindex+"' >"+REJECTED+"</a></td>"+
				"<td ></td>"+
				"</tr>";	
			}
			if(STATUS=="DELETED"){
				appendTxt="<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
				"<td >"+RELATION+"</td>"+
				"<td ></td>"+
				"<td ></td>"+
				"<td ></td>"+
				"<td ><a class='label label-warning' href='"+RELATION_ACTION+"?actiontype="+RELATION_CODE+"&status=DELETED' index='"+rowindex+"' >"+DELETED+"</a></td>"+
				"</tr>";	
			}
			
		}
		
	
			i++;
			$("#merchantTBody").append(appendTxt);	
			rowindex = ++rowindex;
	}); 
	
	
}); 

function radioselect(){
	 $('#errormsg').text("");
}









 $(document).on('click','a',function(event) {
	//alert($(this).text());
	if ($(this).text()=="0"){
		return false;
	}
}); 


	


function redirecthome(v){
	
	if(v.value=="Home"){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
		$("#form1").submit();
		return true;
	}else{
		
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/ApprovalAll.action';
		$("#form1").submit();
		return true;
	}
	
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
				  <li> <a href="ApprovalAll.action">Authorization</a> </li>
				</ul>
			</div>
			
			
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
	
        	<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12"> 
					<div class="box-header well" data-original-title>Authorization Details
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
                  
				<div class="box-content"> 
					<fieldset>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
							id="DataTables_Table_0">
							<thead>
								<tr >
									<th width="20%" >Checker Name</th>
									<th width="10%">Pending Authorise</th>
									<th width="10%">Authorized</th>
									<th width="10%">Rejected</th>
									<th width="10%">Deleted</th>
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
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirecthome(this);" value="Home" />
			</div> 
			
			
	</div> 
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
