
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
 
var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);
var linkIndex = new Array();
var linkaction = new Array();
var linkStatus = new Array();

var num="";

$(document).ready(function () { 
	

	
	$.each(jsonLinks, function(index, v) {
	linkIndex[index] = index;
	linkaction[index] = v.name;
	linkStatus[index] = v.status;
	

	
	num=index;

	
});  
}); 

		
$(document).ready(function () {
	var tds=new Array();
	var merchantData ='${responseJSON.Files_List}';
	

var app="${responseJSON.application}";

$("#srdt").text(app);
$("#srdt1").text(app);

	var json = jQuery.parseJSON(merchantData);
	console.log(json);
	
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";
	var i=0;
	
	$("#"+app).css({"display":""});
	

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
		var DATE_CREATED=(v.DATE_CREATED == undefined) ? "" : v.DATE_CREATED;
		var MOBILE_NO=(v.MOBILE_NO == undefined) ? "" : v.MOBILE_NO;
		var ACCT_NO=(v.ACCT_NO == undefined) ? "" : v.ACCT_NO;
		var USER_ID=(v.USER_ID == undefined) ? "" : v.USER_ID;
		var STATUS=(v.STATUS == undefined) ? "" : v.STATUS;
		var FIRST_NAME=(v.FIRST_NAME == undefined) ? "" : v.FIRST_NAME;
		var AGENTID=(v.AGENTID == undefined) ? "" : v.AGENTID;
		
		var STORE_NAME=(v.STORE_NAME == undefined) ? "" : v.STORE_NAME;
		var STORE_ID=(v.STORE_ID == undefined) ? "" : v.STORE_ID;
		var ADDRESS=(v.ADDRESS == undefined) ? "" : v.ADDRESS;
		var LOC_GOV=(v.LOC_GOV == undefined) ? "" : v.LOC_GOV;
		var STATE=(v.STATE == undefined) ? "" : v.STATE;
		var SSTATUS=(v.SSTATUS == undefined) ? "" : v.SSTATUS;
		var COREACCNO=(v.COREACCNO == undefined) ? "" : v.COREACCNO;
		
		index=colindex-1;
		
		var tabData="DataTables_Table_0";
			
		$('#agname').text(FIRST_NAME);
		$('#agid').text(AGENTID);
		$('#walaccno').text(ACCT_NO);
		$('#coreaccno').text(COREACCNO);
		$('#mobileno').text(MOBILE_NO);
		
		var vstatus="";
		if(STATUS=="ACTIVE"){
			
			vstatus="<div class='label label-success' >Active</div>";
		}else{
			vstatus="<div class='label label-important' >Deactive</div>";
		}
		$('#agnststus').html(vstatus);
		
		var vsstatus="";
		
		if(SSTATUS=="ACTIVE"){
			
			vsstatus="<div class='label label-success' >Active</div>";
		}else{
			vsstatus="<div class='label label-important' >Deactive</div>";
		}
		
		
		$("#userid").val(REF_NO);
		$("#storename").val(STORE_NAME);
		$("#storeid").val(STORE_ID);
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
			"<td >"+colindex+"</td>"+
			"<td style='display:none'>"+REF_NO+"</span></td>"+
			"<td >"+STORE_NAME+"</span></td>"+
			"<td>"+STORE_ID+"</span></td>"+
			"<td>"+ADDRESS+"</span></td>"+
			"<td >"+LOC_GOV+"</span></td>"+
			"<td >"+STATE+"</span></td>"+
			"<td >"+vsstatus+"</span></td>"+
			
			"<td><p>"+ /* <a  class='btn btn-danger' id='luck_"+i+"' href='#' index='"+rowindex+"' title='Block' data-rel='tooltip' disabled><i class='icon icon-lock icon-white'></i></a> &nbsp; */
			"<a  class='btn btn btn-danger' id='addpos_"+i+"' href='#' index='"+rowindex+"' title='Create Terminal' data-rel='tooltip' disabled><i class='icon icon-plus icon-white'></i></a> &nbsp;"+
			/*  "<a  class='btn btn btn-danger' id='viewpos_"+i+"' href='#' index='"+rowindex+"' title='View Details' data-rel='tooltip' disabled><i class='icon icon-book icon-white'></i></a> &nbsp;"+
 			"<a  class='btn btn btn-danger' id='modifypos_"+i+"' href='#' index='"+rowindex+"' title='POS Modify' data-rel='tooltip' disabled><i class='icon icon-edit icon-white'></i></a> &nbsp;"+
			"<a  class='btn btn btn-danger' id='retrivepos_"+i+"' href='#' index='"+rowindex+"' title='Retrieve' data-rel='tooltip' disabled><i class='icon icon-hdd icon-white'></i></a></td>";
			 */ "</tr>";
			i++;
			$("#seachdetails").append(appendTxt);	
			rowindex = ++rowindex;
			
			 for(ii=0;ii<=num;ii++){
					//alert(linkaction[ii]+(i-1)); 
					var v=linkaction[ii]+"_"+(i-1);
					$('#'+v).removeAttr("disabled");
					$('#'+v).attr("id", linkaction[ii]);
					 
				 }  
			
	}); 
	

	

	
}); 


$(document).ready(function () {
	var tds=new Array();
	var merchantData1 ='${responseJSON.Files_List1}';
	
//alert(merchantData1);

	var json1 = jQuery.parseJSON(merchantData1);
	console.log(json1);
	
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";
	var i=0;
	

	

	$.each(json1, function(i, v) {
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
		
		
		var TERMINAL_ID=(v.TERMINAL_ID == undefined) ? "" : v.TERMINAL_ID;
		var SERIAL_NO=(v.SERIAL_NO == undefined) ? "" : v.SERIAL_NO;
		var TERMINAL_MAKE=(v.TERMINAL_MAKE == undefined) ? "" : v.TERMINAL_MAKE;
		var MODEL_NO=(v.MODEL_NO == undefined) ? "" : v.MODEL_NO;
		var MAKER_DTTM=(v.MAKER_DTTM == undefined) ? "" : v.MAKER_DTTM;
		var STATUS=(v.STATUS == undefined) ? "" : v.STATUS;
		
		index=colindex-1;
		
		var tabData="DataTables_Table_1";
		
		var vtstatus="";
		
		if(STATUS=="A"){
			
			vtstatus="<div class='label label-success' >Active</div>";
		}else{
			vtstatus="<div class='label label-important' >Deactive</div>";
		}
			
		
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
			"<td >"+colindex+"</td>"+
			"<td >"+TERMINAL_ID+"</span></td>"+
			"<td>"+TERMINAL_MAKE+"</span></td>"+
			"<td>"+MODEL_NO+"</span></td>"+
			"<td >"+SERIAL_NO+"</span></td>"+
			"<td >"+MAKER_DTTM+"</span></td>"+
			"<td >"+vtstatus+"</span></td>"+
			
			"<td><p>"+ /* <a  class='btn btn-danger' id='luck_"+i+"' href='#' index='"+rowindex+"' title='Block' data-rel='tooltip' disabled><i class='icon icon-lock icon-white'></i></a> &nbsp; 
			"<a  class='btn btn btn-danger' id='viewpos_"+i+"' href='#' index='"+rowindex+"' title='View Users' data-rel='tooltip' disabled><i class='icon icon-book icon-white'></i></a> &nbsp;"+*/
			"<a  class='btn btn btn-danger' id='modifypos_"+i+"' href='#' index='"+rowindex+"' title='Modify Terminal' data-rel='tooltip' disabled><i class='icon icon-edit icon-white'></i></a> &nbsp;"+
			"<a  class='btn btn btn-danger' id='retrivepos_"+i+"' href='#' index='"+rowindex+"' title='Retrieve Terminal' data-rel='tooltip' disabled><i class='icon icon-hdd icon-white'></i></a></td>";
			 "</tr>";
			i++;
			
			$("#terminaldetails").append(appendTxt);	
			rowindex = ++rowindex;
			
			 for(ii=0;ii<=num;ii++){
					//alert(linkaction[ii]+(i-1)); 
					var v=linkaction[ii]+"_"+(i-1);
					$('#'+v).removeAttr("disabled");
					$('#'+v).attr("id", linkaction[ii]);
					 
				 }  
			
	}); 
	

	

	
}); 

function radioselect(){
	 $('#errormsg').text("");
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
	var name = "";
	var storeid = "";
	if(val.match("$"))
		{
		var x = val.split("$");
		code = x[1];
		name = x[2];
		storeid = x[3];
		}

	
	<%-- if(btn == "luck")
	{
		$("#userid").val(code);
		$("#actiontype").val("${responseJSON.application}BLOCK");
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/agentregmodifydetails.action";
	 	$("#form1").submit();
	  return true; 
	} --%>

	if(btn == "addpos")
	{
		
		$("#userid").val(code);
		$("#storename").val(name);
		$("#storeid").val(storeid);
		$("#actiontype").val("${responseJSON.application}ADD");
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/posregistrationdetails.action";
	 	$("#form1").submit();
	  return true; 
	}
	
	 if(btn == "viewpos")
	{
		$("#terminalid").val(code);
		$("#actiontype").val("${responseJSON.application}VIEW");
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/posregistrationdetails.action";
	 	$("#form1").submit();
	  return true; 
	}
	
	 if(btn == "modifypos")
	{
		$("#terminalid").val(code);
		$("#actiontype").val("${responseJSON.application}MODIFY");
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/posregistrationdetails.action";
	 	$("#form1").submit();
	  return true; 
	}
	
	if(btn == "retrivepos")
	{
		$("#terminalid").val(code);
		$("#actiontype").val("${responseJSON.application}STATUS");
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/posregistrationdetails.action";
	 	$("#form1").submit();
	  return true; 
	} 
	
	

	
	
	
    
}); 
		 







	


function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/posregistration.action';
	$("#form1").submit();
	return true;
}


$.validator.addMethod("regex", function(value, element, regexpr) {          
return regexpr.test(value);
}, ""); 



	
</script>

<style type="">
.label {
    padding: 1px 4px 2px;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
    width: 40px;
}
</style>
	 
</head>


<body>
<form name="form1" id="form1" method="post" action="">	
<!-- topbar ends -->

	
		<div id="content" class="span10">  
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="agentreglock.action">Terminal Management</a> </li>
				</ul>
			</div>
			
			
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
	
        	<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12"> 
					<div class="box-header well" data-original-title>Wallet Agent Details
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
                  
				<div class="box-content"> 
					<fieldset>
					<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
								
								<tr class="even">
								<td width="25%"><label for="Service ID"><strong>Agent Name</strong></label></td>
								<td width="25%"><div id="agname"></div></td>
								<td width="25%"><label for="Service ID"><strong>Agent Id</strong></label></td>
								<td width="25%"><div id="agid"></div></td>
								</tr>
								<tr class="even">
								<td><label for="Service ID"><strong>Wallet Account No</strong></label></td>
								<td><div id="walaccno"></div></td>
								<td><label for="Service ID"><strong>Core Bank Account No</strong></label></td>
								<td><div id="coreaccno"></div></td>
								</tr>
								<tr class="even">
								<td><label for="Service ID"><strong>Mobile Number</strong></label></td>
								<td><div id="mobileno"></div></td>
								<td><label for="Service ID"><strong>Agent Status</strong></label></td>
								<td><div id="agnststus"></div></td>
								</tr>
					</table>
					<div class="box-header well" data-original-title>Store Details
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable dataTable" 
							id="DataTables_Table_0" >
							<thead  >
								<tr >
									<th width="5%">SNo</th>
									<th style="display:none">refno</th>	
									<th width="10%">Store Name</th>							
									<th width="10%">Store Id</th>
									<th width="10%">Address</th>
									<th width="10%">Local Government</th>
									<th width="10%">State</th>
									<th width="10%">Store Status</th>
									
									<th width="20%">Action</th>
								</tr>
								
							</thead>
							
						

							<tbody  id="seachdetails">
							</tbody>
							
						</table>
						<br>
						<div class="box-header well" data-original-title>Terminal Details
						  <div class="box-icon"> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
						  </div>
						</div>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable dataTable" 
							id="DataTables_Table_1" >
							<thead  >
								<tr >
									<th width="5%">SNo</th>
									<th width="10%">Terminal Id</th>
									<th width="10%">Terminal Manufacturer</th>							
									<th width="10%">Model Number</th>
									<th width="10%">Serial Number</th>
									<th width="10%">Created Date</th>
									<th width="10%">Status</th>
									
									<th width="20%">Action</th>
								</tr>
								
							</thead>
							
						

							<tbody  id="terminaldetails">
							</tbody>
							
						</table>
						
						
						
					</fieldset>
              </div>
             </div>
            </div>
           				<input type="hidden"    id="userid" name="userid"  /> 
           				<input type="hidden"    id="storename" name="storename"  />
           				<input type="hidden"    id="storeid" name="storeid"  />
           				<input type="hidden"    id="terminalid" name="terminalid"  />
						<input type="hidden"    id="actiontype" name="actiontype"  /> 
			  <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
			</div> 
			
			
	</div> 
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
