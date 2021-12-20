
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
 
var userictadminrules = {
 		
 		rules : {
 			
 			userid : { required : true } ,
 		
 		},		
 		messages : {
 			
 		
 			userid : { 
		 				required : "Please Enter User Id.",
 						}
 			
 		} 
 	};
		
$(document).ready(function () {
	
	$( "#userid" ).keyup(function() {
		
		$( "#userid" ).val((this.value).toUpperCase());
		});

	
	/* var tds=new Array();
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
			
			
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
			"<td >"+colindex+"</td>"+
			"<td >"+USER_ID+"</span></td>"+
			"<td>"+MOBILE_NO+"</span></td>"+
			"<td><p><a id='B"+REF_NO+"' class='btn btn-success' href='agentregmodifydetails.action?refno="+REF_NO+"&actiontype=PROCESS' index='"+rowindex+"'>Registration</a></p></td>";
			"</tr>";
			i++;
			$("#merchantTBody").append(appendTxt);	
			rowindex = ++rowindex;
	}); 
	 */
	
}); 

function radioselect(){
	 $('#errormsg').text("");
}



function submitact(){
		$("#form1").validate(userictadminrules);
		
		 var queryString = "entity=${loginEntity}&method=transactionsearch&fname="+$('#userid').val();
 		
			$.getJSON("postJson.action", queryString,function(data) { 
				if(data.finalCount!=0){
					$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/dsareconciledetails.action';
					$("#form1").submit();
					return true;
				}else{ 
					$('#errormsg').text("No Reconcile Transaction are available");	
				 }
			});
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
				  <li> <a href="dsareconcile.action">Teller Reconcile</a> </li>
				</ul>
			</div>
			
			
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
	
        	<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12"> 
					<div class="box-header well" data-original-title>DSA Search
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
                  
				<div class="box-content"> 
					<fieldset>
						<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr class="odd">
									<td width="25%"><label for="fname"><strong>User Id <font color="red">*</font></strong></label></td>
									<td width="25%"><input name="userid" autocomplete="off" type="text" class="field" id="userid"  value="" required=true   />
									
									</td>
									
									<td width="25%"><input type="button" id="non-printable" class='btn btn-success' onclick="submitact();" value="Get Reconcile Transactions" /></td>
									<td width="25%"></td>
								
								</tr>
								
									
								
							</table>
					</fieldset>
					<input name="actiontype" autocomplete="off" type="hidden" class="field" id="actiontype"  value="PROCESS"   />
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
