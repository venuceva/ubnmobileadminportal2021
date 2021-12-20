
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
 
var list = "frdate,todate".split(",");
var datepickerOptions = {
				showTime: true,
				showHour: true,
				showMinute: true,
	  		dateFormat:'dd/mm/yy',
	  		alwaysSetTime: false,
	  		yearRange: '1910:2020',
			changeMonth: true,
			changeYear: true
};
		
$(document).ready(function () {
	
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	}); 
	
	
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
		var USER_ID=(v.USER_ID == undefined) ? "" : v.USER_ID;
		var MOBILE_NO=(v.MOBILE_NO == undefined) ? "" : v.MOBILE_NO;
		
		index=colindex-1;
		
		var tabData="DataTables_Table_0";
			
		/* &nbsp;&nbsp; <a id='C"+REF_NO+"' class='btn btn-success' href='#' onclick='lightbox_open(\""+USER_ID+"\",\""+MOBILE_NO+"\",\""+REF_NO+"\")' index='"+rowindex+"'>View Transaction</a>
		 */	
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
			"<td >"+colindex+"</td>"+
			"<td >"+USER_ID+"</span></td>"+
			"<td>"+MOBILE_NO+"</span></td>"+
			"<td><p><a id='C"+REF_NO+"' class='btn btn-success' href='agentregmodifydetails.action?userid="+REF_NO+"&actiontype=VIEW' index='"+rowindex+"'>View Details</a> </p></td>";
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

function lightbox_open(uid,pno,ref)
{
	
 window.scrollTo(0,0);
$('#usrid').text(uid);
 $('#phno').text(pno);	
 document.getElementById('light').style.display='block';
}
function lightbox_close()
{
 document.getElementById('light').style.display='none';
}

	
</script>

<style type="text/css">


#light {
	display: none;
	position: absolute;
	top: 10%;
	left: 15%;
	width: 70%;
	height: 50%;
	/* margin-left: -150px;
	margin-top: -100px; */
	/* border: 2px solid #FFF;*/
	background: #FFF; 
	z-index: 1002;
	overflow: visible;
	
	border-width: 1px;
border-style: solid;
border-radius: 10px;
-webkit-border-radius: 10px;
-moz-border-radius: 10px;
-webkit-box-shadow: 0 1px 1px 
hsla(0, 0%, 0%, 0.08), inset 0 1px 
hsla(0, 100%, 100%, 0.3);
-moz-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.08), inset 0 1px rgba(255, 255, 255, 0.3);
box-shadow: 0 1px 1px 
hsla(0, 0%, 0%, 0.08), inset 0 1px 
hsla(0, 100%, 100%, 0.3);
	
}






</style>
	 
</head>


<body>
<form name="form1" id="form1" method="post" action="">	
<!-- topbar ends -->

	
		<div id="content" class="span10">  
			<div>
				<ul class="breadcrumb">
				  <li> <a  href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="agentregmodify.action">DSA Registration View</a></li>
				</ul>
			</div>
			
			
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
	
        	<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12"> 
					<div class="box-header well"  data-original-title>Search DSA Details
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
									<th width="10%">SNo</th>
									<th width="30%">User Id</th>
									<th width="30%">Mobile Number</th>
									<th width="30%">Action</th>
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
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
			</div> 
			<div id="light">
						<div class="row-fluid sortable">
							<div class="box span12">
								<div class="box-header well">
									<i class="icon-edit"></i> Please Enter Between Date Transaction 
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i
											class="icon-cog"></i></a> <a href="#"
											class="btn btn-minimize btn-round"><i
											class="icon-chevron-up"></i></a>
									</div>
								</div>
								<div class="box-content">
									<fieldset> 
										 <table width="950" border="0" cellpadding="5" cellspacing="1" 
													class="table table-striped table-bordered bootstrap-datatable " id="user-details">
													
										<tr class="even">
											<td width="25%" ><label for="User Id"><strong>User Id</strong></label></td>
											<td width="25%" ><div id="usrid"></div> 
												<input type="hidden" maxlength="10"   id="userid" name="userid" required=true />  							
											</td>
											
									
											<td width="25%" ><label for="Phone Number"><strong>Phone Number</strong></label></td>
											<td width="25%" ><div id="phno"></div> 
												<input type="hidden" maxlength="10"  id="phnumber" name="phnumber" required=true />  							
											</td>
											
										</tr>     
										<tr class="odd">
											<td width="25%" ><label for="Date"><strong>From Date</strong><font color="red">*</font></label></td>
											<td width="25%" > 
												<input type="text" maxlength="10"  class="fromDate" id="frdate" name="frdate" required=true />  							
											</td>
											
									
											<td width="25%" ><label for="Date"><strong>To Date</strong><font color="red">*</font></label></td>
											<td width="25%" > 
												<input type="text" maxlength="10"  class="fromDate" id="todate" name="todate" required=true />  							
											</td>
											
										</tr>  
								 </table>
								</fieldset> 
							</div>
						</div>
						<div align="center">
							<a class="btn btn-danger" href="#" onClick="lightbox_close()">Close</a> &nbsp;&nbsp; <a class="btn btn-success" href="#" onClick="lightbox_close()">Search</a>
						</div>
						</div>
	</div> 
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
