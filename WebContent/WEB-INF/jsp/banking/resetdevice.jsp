
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Banking</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript" >
var usersList = new Array();
var groupsList = new Array();
function postData(actionName,paramString){
		$('form').attr("action", actionName)
				.attr("method", "post");

		var paramArray = paramString.split("&");

		$(paramArray).each(function(indexTd,val)
		{
			//console.log("Index : "+indexTd+" Value : "+val);
			var input = $("<input />").attr("type", "hidden").attr("name", val.split("=")[0]).val(val.split("=")[1]);
			$('form').append($(input));
		});

		$('form').submit();
}

$(document).ready(function () {

	
	var userGroupData ='${responseJSON.DEVICES}';
	var json = jQuery.parseJSON(userGroupData);

	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";

	$.each(json, function(index, v) {

		if(val % 2 == 0 ) {
			addclass = "even";
			val++;
		}
		else {
			addclass = "odd";
			val++;
		}
		var rowCount = $('#userGroupTBody > tr').length;

		colindex = ++ colindex;

		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+
		"<td ><input class='center-chk' type='radio' onclick='' id='IMEI' name='IMEI' value='" + v.IMEI + "' /></td>"+
		"<td><a href='#' index='"+rowindex+"' id='group-view' >"+v.IMEI_REF_ID+"</a></span></td>"+
		"<td><a href='#' index='"+rowindex+"' id='row-view' >"+v.IMEI+"</a></span></td>"+
		"<td>"+v.MOBILE_NO+"</span> </td>"+
        "<td>"+v.TRAN_DTTM+"</span> </td>"+
		"<td> "+v.IP+"&"+v.PORT +" </span></td>"+
		"<td> "+v.ATTEMPTS_COUNT+" </span></td>"+
		"<td> "+v.DEVISE_STATUS+" </span></td></tr>";

		$("#userGroupTBody").append(appendTxt);
		rowindex = ++rowindex;

		
	});
	
	
	$("#submit").click(function(event) {
		event.preventDefault();
		var searchIDs="";

 		$("tbody#userGroupTBody input:radio:checked").each(function(index) {
 			searchIDs=$(this).val();
			
 		   $('#IMEI').val(searchIDs);
 		   
 		  	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/changeDeviceStatus.action'; 
			$('#form1').submit();
			 return true;
		});
	});
});


</script>



</head>

<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
	  <div>
		 <ul class="breadcrumb">
			<li><a href="#">Home </a> <span class="divider"> &gt;&gt; </span></li>
			<li><a href="userGrpCreation.action">Reset Device</a> <%-- <span class="divider"> &gt;&gt; </span>  --%></li>
			<!-- <li><a href="#"></a> </li> -->
		</ul>
	</div>

		<table height="3">
			<tr>
				<td colspan="3">
					<div class="messages" id="messages">
						<s:actionmessage />
					</div>
					<div class="errors" id="errors">
						<s:actionerror />
					</div>
				</td>
			</tr>
	  </table>

    <div class="row-fluid sortable"><!--/span-->
		<div class="box span12" id="groupInfo">
                  <div class="box-header well" data-original-title>Locked Devices
					<div class="box-icon"> <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> </div>
				</div>
           		<div class="box-content">
 					<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable"
						id="DataTables_Table_0"  >
								<thead>
									<tr >
										<th >S No</th>
										<th>Reference Id</th>
										<th>IMEI</th>
										<th>Mobile No </th>
										<th>Date</th>
										<th>Transaction IP&PORT</th>
										<th>Invalid Attempts</th>
										<th>Device Status</th>
 									</tr>
								</thead>
								<tbody  id="userGroupTBody">
								</tbody>
							</table>
                    </div>
                    
                   
          </div>
		</div>
		<div class="form-actions">
					<a  class="btn btn-primary" href="#" id="submit" name="submit">Unlock</a> &nbsp;&nbsp;
		</div>
</div><!--/#content.span10-->


</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script>
</body>
</html>
