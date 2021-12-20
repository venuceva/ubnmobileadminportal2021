<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 
<script type="text/javascript">

window.setTimeout( update, 25000);

function update() { 
  var json;
  var json2;
  var formInput="method=getSwitchStatus";
  $.getJSON('getSwitchStatus.action', formInput,function(data) {
		json = data.responseJSON.SWITCH_BANK_DATA; 
		json2 = data.SWITCH_BANK_DATA; 
   });
   
   window.setTimeout( update, 25000);
}

 $(document).ready(function () {
	   
	//setInterval(update, 30000); 
	//update(); 

	var bankData ='${responseJSON.LIVE_TRANLOG}';
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
		"<td> "+v.txn_date+"</span></td>"+
		"<td>"+v.tran_type+"</span> </td>"+
		"<td>"+v.mid+"</span></td>"+
		"<td>"+v.tid+"</span></a></td>"+
		"<td><a id='bank-modify-group' href='#' index='"+rowindex+"' >"+v.RRN+"</span></a></td>"+
		"<td>"+v.amount+' Kes/-'+"</span></td></tr>" ;
		

		$("#userGroupTBody").append(appendTxt);
		rowindex = ++rowindex;
	});


	$(document).on('click','#bank-modify-group',function(event) {
		var index = $(this).attr('index');
		var searchRow = "DataTables_Table_0 tbody tr#"+index+" td";
			  $('#'+searchRow).each(function (indexTd) {
				 if (indexTd == 1) {
					txn_date=$(this).text();
				 }   if(indexTd == 2) {
					tran_type=$(this).text();
				 }   if(indexTd == 3) {
					 mid=$(this).text();
				 }if(indexTd==5)
					 {
					 RRN=$(this).text();
					 } 

			}); 
 
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/rrnumberAction.action?txn_date="+txn_date+"&tran_type="+tran_type+"&RRN="+RRN;
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


$(document).ready(function () { 
	$('#RRNNoSearch').keyup(function(){
		
		var rrnSearch=$('#RRNNoSearch').val();
		if(rrnSearch.length == 12){
			var queryString = "method=RRNDetailsSearch&rrnSearch="+rrnSearch;	
			///alert(queryString);
			$.getJSON("postJson.action", queryString,function(data) { 
				var RRnDetails = data.requestJSON.RRN;
		
				var status=RRnDetails.STATUS;
			console.log(status);
				var message = data.requestJSON.message;
				if(message=='SUCCESS'){
				//console.log(terminal);
				var detailedRow="";
				detailedRow+='<tr class="popuptr"><td>RRN :</td><td>'+RRnDetails.RRN+'</td></tr>'+
							
								'<tr class="popuptr"><td>Amount :</td><td>'+RRnDetails.amt+'</td></tr>'+
								'<tr class="popuptr"><td>Merchant ID:</td><td>'+RRnDetails.mid+'</td></tr>'+
								'<tr class="popuptr"><td>Terminal ID :</td><td>'+RRnDetails.tid+'</td></tr>'+
								'<tr class="popuptr"><td>Card Number :</td><td>'+RRnDetails.maskedcard+'</td></tr>'+
								'<tr class="popuptr"><td>Authorized By :</td><td>'+RRnDetails.authid+'</td></tr>'+
								'<tr class="popuptr"><td>Service  Name:</td><td>'+RRnDetails.bankname+'</td></tr>'+
								'<tr class="popuptr"><td>Branch Location:</td><td>'+RRnDetails.location+'</td></tr>'+
								'<tr class="popuptr"><td>Transaction Type:</td><td>'+RRnDetails.txntype+'</td></tr>'+
								'<tr class="popuptr"><td>Status:</td><td>'+RRnDetails.responseCode+'</td></tr>'+
								
								'<tr class="popuptr"><td>POSRRN :</td><td>'+RRnDetails.posrrn+'</td></tr>';
								
					$('#RRNIFO').empty();
					$('#RRNIFO').attr('title', "Searching For :"+RRnDetails.RRN);
					$('#RRNIFO').append(detailedRow);
					$('#RRNIFO').dialog();
				}else{
					alert(message);
				}
			});	
		}
	});
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
				<li><a href="#">All Transactions</a></li>
				
				<div > <input type="text" name="RRNNoSearch" id="RRNNoSearch" placeholder="RRNSearch" style="float: right; margin-top: -24px;	margin-right: -15px;" ></div>
			</ul>
		</div>

		
<!-- <div class="searchbox"><input type="text" name="RRNNoSearch" id="RRNNoSearch" placeholder="RRNSearch"></div>  -->
			<div class="row-fluid sortable">
				<div class="box span12" id="groupInfo">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Bank Information
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
										<th>TNDATE</th>
										<th>TRANTYPE</th>
										<th>MID</th>
										<th>TID</th>
										<th>POSRRN</th>
										<th>AMOUNT</th>
										
									</tr>
								</thead>
								<tbody id="userGroupTBody">
								</tbody>
							</table>
						</fieldset>
					</div>
				</div>
			</div>
	</div>
		<div > 
		<div class="RRnSearch" id="RRn_search" style="display:none;">
		<table id="RRNIFO">
		</table>
		</div>
	</div>
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>