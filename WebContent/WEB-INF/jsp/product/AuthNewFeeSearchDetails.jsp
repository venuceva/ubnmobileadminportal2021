
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
		var PRD_CODE=(v.PRD_CODE == undefined) ? "" : v.PRD_CODE;
		var PRD_NAME=(v.PRD_NAME == undefined) ? "" : v.PRD_NAME;
		var REQUEST_TYPE=(v.REQUEST_TYPE == undefined) ? "" : v.REQUEST_TYPE;
		
		
		index=colindex-1;
		
		var tabData="DataTables_Table_0";
		var text="";
		var actiontype="";
			if( REQUEST_TYPE == 'New Fee') {
				text = "<div class='label label-success'  align='center' >"+REQUEST_TYPE+"</div>";
				actiontype="NEW_FEE";
			}else if( REQUEST_TYPE == 'Modify Fee') {
				text = "<div class='label label-info' align='center' >"+REQUEST_TYPE+"</div>";
				actiontype="MODIFY_FEE";
			}else if( REQUEST_TYPE == 'Change Status') {
				text = "<div class='label label-info' align='center' >"+REQUEST_TYPE+"</div>";
				actiontype="STATUS_FEE";
			}
			
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
		"<td ><input class='center-chk' type='radio' id='radbutt' name='"+colindex+"' value='"+PRD_CODE+","+actiontype+","+REF_NO+"' />    </td>"+
			"<td >"+PRD_CODE+"</span></td>"+
			"<td>"+PRD_NAME+"</span></td>"+
			"<td>"+text+"</span></td>"+
/* 			"<td><p><a id='B"+REF_NO+"' class='btn btn-success' href='AuthFeesettingsdataview.action?feeCode="+PRD_CODE+"&actiontype="+actiontype+"' index='"+rowindex+"'>Authorise</a></p></td>";
 */			"</tr>";
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



$(function() {
	
	$("#btn-submit").click(function(event) {
		event.preventDefault();
		var searchIDs="";
	 
 		$("input:radio:checked").each(function(index) {
 			searchIDs=$(this).val(); 
 			//alert(searchIDs);
 		    $('#refno').val(searchIDs.split(",")[2]);
 		  	$('#actiontype').val(searchIDs.split(",")[1]); 
 		 	$('#feeCode').val(searchIDs.split(",")[0]); 
		});

        if(searchIDs.length == 0) {
			$("#error_dlno").text("Please check atleast one record.");
		} else {
			
			var url="${pageContext.request.contextPath}/<%=appName %>/AuthFeesettingsdataview.action";
			$("#form1")[0].action=url;
			$("#form1").submit(); 

		}
	});
	
	
	$("#btn-view").click(function(event) {
		event.preventDefault();
		var searchIDs="";
	 
 		$("input:radio:checked").each(function(index) {
 			searchIDs=($(this).val()).split(",")[2]; 
 		  
		});

        if(searchIDs.length == 0) {
			$("#error_dlno").text("Please check atleast one record.");
		} else {
		var queryString9 = "method=authactionview&fname="+searchIDs;
		var htmlString="";
			$.getJSON("postJson.action", queryString9,function(data) {
				var feefinaljsonobj = jQuery.parseJSON(data.message);
			
					
					htmlString = htmlString + "<table width=\"950\"  border=\"0\" cellpadding=\"5\" cellspacing=\"1\" class=\"table table-striped table-bordered bootstrap-datatable\" id=\"check-kra-details\" >";
					htmlString = htmlString + "<tr class=\"odd\">";
						htmlString = htmlString + "<td width=\"25%\"><label for=\"File\"><strong>Maker Id</strong></label></td>";
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.MAKER_ID + "</td>";
					
						htmlString = htmlString + "<td width=\"25%\"><label for=\"Client\"><strong>Maker Date</strong></label></td>"; 
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.MAKER_DTTM+  "</td>";
						
						htmlString = htmlString + "</tr>";
						htmlString = htmlString + "<tr class=\"even\">";
						htmlString = htmlString + "<td width=\"25%\"><label for=\"File\"><strong>Checker Id</strong></label></td>";
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.CHECKER_ID+ "</td>";
					
						htmlString = htmlString + "<td width=\"25%\"><label for=\"Client\"><strong>Checker Date</strong></label></td>"; 
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.CHECKER_DTTM+ "</td>";
						
						htmlString = htmlString + "</tr>";
						htmlString = htmlString + "<tr class=\"even\">";
						htmlString = htmlString + "<td width=\"25%\"><label for=\"File\"><strong>Main Menu</strong></label></td>";
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.MAIN_MENU+ "</td>";
					
						htmlString = htmlString + "<td width=\"25%\"><label for=\"Client\"><strong>Action Link</strong></label></td>"; 
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.ACTION_MENU+ "</td>";
						
						htmlString = htmlString + "</tr>";	
						htmlString = htmlString + "<tr class=\"even\">";
						htmlString = htmlString + "<td width=\"25%\"><label for=\"File\"><strong>Status</strong></label></td>";
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.STATUS+ "</td>";
					
						htmlString = htmlString + "<td width=\"25%\"></td>"; 
						htmlString = htmlString + "<td width=\"25%\"></td>"; 
						
						htmlString = htmlString + "</tr>";
						htmlString = htmlString + "<tr class=\"even\">";
						htmlString = htmlString + "<td width=\"25%\"><label for=\"File\"><strong>Action Details</strong></label></td>";
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.ACTION_DETAILS+ "</td>";
					
						htmlString = htmlString + "<td width=\"25%\"><label for=\"Client\"><strong>Reason</strong></label></td>"; 
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.REASON+ "</td>";
						
						htmlString = htmlString + "</tr>";			
				
					
					htmlString = htmlString + "</table>";
					
					
					
			
				
				
				 $( "#dialog" ).dialog( "option", "title", "Maker Id ::   "+feefinaljsonobj.MAKER_ID);
					$( "#pie" ).html(htmlString);	  
				   $("#dialog").dialog("open");
			/* var htmlString1="";
			htmlString1 = htmlString1 + "<table class='table table-striped table-bordered bootstrap-datatable datatable'>";
			htmlString1 = htmlString1 + "<tr ><td>Account Number</td><td>"+data.message+"</td></tr>";
			
			
			htmlString1 = htmlString1 + "</table>"; */
			
			
			
			});
		}
	}); 
	
	
});



$(function() {
	$( "#dialog" ).dialog(
			{
			autoOpen: false,
			modal: true,
		    draggable: false,
		    resizable: false,
		    show: 'blind',
		    hide: 'blind',
			width: 800, 
		    height: 400,
		    buttons: {
		        "OK": function() {
		            $(this).dialog("close");
		        }
		    }
			}
		);
	});
	



	
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
			
			<div id="dialog" name="dialog" title="Maker Action View" width="100" style="display:none">
  						<strong><div  id="result"><div id="pie"></div>
  						</div></strong>
					</div>
			
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
	
        	<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12"> 
					<div class="box-header well" data-original-title>Fee Approval Details
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
									<th width="20%">Fee Code</th>
									<th width="20%">Describution</th>
									<th width="20%"><div align="center">Approval Status</div></th>
								</tr>
								
							</thead>

							<tbody  id="merchantTBody">
							</tbody>
							
						</table>
					</fieldset>
              </div>
             </div>
            </div>
            
             <input type="hidden" name="refno" id="refno" /> 
		    <input type="hidden" name="feeCode" id="feeCode" />
		    <input type="hidden" name="actiontype" id="actiontype" />
            
           
			   <div class="form-actions" align="left"> 
				
				<input type="button" class="btn btn-info" name="btn-back" id="btn-back" value="Back" onclick="redirectAct();" /> 
				<input type="button" class="btn btn-success" name="btn-view"	id="btn-view" value="Maker Action View"   /> 
				<input type="button" class="btn btn-success" name="btn-submit"	id="btn-submit" value="Next"  /> 
				<span id ="error_dlno" class="errors"></span> 
			</div> 
			
			
	</div> 
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
