<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 
<script type="text/javascript">

/* window.setTimeout( update, 25000);

function update() { 
  var json;
  var json2;
  var formInput="method=getSwitchStatus";
  $.getJSON('getSwitchStatus.action', formInput,function(data) {
		json = data.responseJSON.SWITCH_BANK_DATA; 
		json2 = data.SWITCH_BANK_DATA; 
   });
   
   window.setTimeout( update, 25000);
} */

 $(document).ready(function () {
	   
	//setInterval(update, 30000); 
	//update(); 

	var bankData ='${responseJSON.MERCHANTfEEDETAILS}';
	var json = jQuery.parseJSON(bankData);
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

		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"'index='"+rowindex+"'> "+
		"<td   >"+colindex+"</td>"+
		"<td>"+v.merchantID+"</span> </td>"+
		"<td>"+v.makerID+"</span> </td>"+
		"<td>"+v.makerDttm+"</span></td>"+
		"<td>"+v.txnType+"</span></a></td>"+
		"<td><a id='viewfeecodedetails' href='#' index='"+rowindex+"' >"+v.feeCode+"</span></a></td>"+
		"<td>"+v.feename+"</span></a></td></tr>";
		$("#userGroupTBody").append(appendTxt);
		rowindex = ++rowindex;
	});


	$(document).on('click','#viewfeecodedetails',function(event) {
		var index = $(this).attr('index');
		var searchRow = "DataTables_Table_0 tbody tr#"+index+" td";
			  $('#'+searchRow).each(function (indexTd) {
				 if (indexTd == 1) {
					 merchantID=$(this).text();
				 }   if(indexTd == 2) {
					 makerID=$(this).text();
				 }   if(indexTd == 3) {
					 mid=$(this).text();
				 }if(indexTd==5)
					 {
					 feeCode=$(this).text();
					 
					 } 

			}); 
 
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/merchantFeeDetails.action?feeCode="+feeCode;
			$("#form1").submit();
			return true;

	}); 

});

$(document).on('click','#switch-status',function(event) {
	var method="getSwitchStatus";
	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/getSwitchStatus.action?method="+method;
	$("#form1").submit();
	return true;
}); 
$(document).on('click','#group-creation',function(event) {
 	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/switchBankCreation.action";
	$("#form1").submit();
	return true;
}); 

</script> 
</head>
<body> 
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
				<li><a href="home.action">Home </a> <span class="divider">
						&gt;&gt; </span></li>
				<li><a href="#">Merchant Assigning</a></li>
			</ul>
		</div>

		

			<div class="row-fluid sortable">
				<div class="box span12" id="groupInfo">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Merchant Assigning 
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<fieldset>
							<table width="100%"
								class="table table-striped table-bordered bootstrap-datatable datatable"
								id="DataTables_Table_0" >
								<thead>
									<tr>
										<th>Slno</th>
										<th>Merchant ID</th>
										<th>MakerID</th>
										<th>MakerDttm</th>
										<th>Txntype</th>
										<th>Fee Code</th>
										<th>Fee Name</th>
									</tr>
								</thead>
								<tbody id="userGroupTBody">
								</tbody>
								
								<input type="hidden" name="mrcode" id="mrcode" value="${mrcode}" />
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