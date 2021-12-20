
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%
String appName= " ";
String ctxstr = " ";
String checkTeller=" ";
try{
	
	ctxstr = request.getContextPath();
	appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); 
	checkTeller= session.getAttribute("userLevel").toString() == null ? "NO_VALUE" : session.getAttribute("userLevel").toString(); 
}
catch (Exception e) {
	appName=" ";
	ctxstr = " ";
	checkTeller=" ";
	
	System.out.println("Exception in Instant Reports JSP[" + e.getMessage()+ "]");
} 
 %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Create Entity</title>


<style type="text/css">
select#region,
select#headOffice,
select#Location,
select#userid,
select#bank,
select#reportname,
select#txnType {
	width : 290px;
 }
input#fromDate,
input#toDate,
input#userid1 {
	width : 280px;
 }
</style>

<script type="text/javascript">

var list = "fromDate,toDate".split(",");
var datepickerOptions = {
			showTime: false,
			showHour: false,
			showMinute: false,
  		dateFormat:'dd/mm/yy',
  		alwaysSetTime: false,
		changeMonth: true,
		changeYear: true
};

function getToday()
{
	var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1;
    var yyyy = today.getFullYear();

    if(dd<10){
        dd='0'+dd;
    }
    if(mm<10){
        mm='0'+mm;
    }

    return dd+"/"+mm+"/"+yyyy;
}


function setStringCond(str) {
	var strArr = str.split("\,");

	var changedString = "";
	for(var i=0;i<strArr.length;i++) {
			if(i == strArr.length ) changedString+=  "'"+strArr.split('-')[0]+"'";
			else changedString+= "'"+strArr.split('-')[0] +"',";
	}

	return	changedString;
}







var userdata = "";
var arrayData = {
				'region' : '${responseJSON.region}',
				'headOffice' :  '${responseJSON.headoffice}',
				'Location' :'${responseJSON.location}',
				'userid' : '${responseJSON.userid}',
				'bank' : '${responseJSON.bank}',
				'reportname' : '${responseJSON.reportname}',
				'uname' : '${responseJSON.uname}',
				'txnty' : '${responseJSON.txntype}',
				'chnl' : '${responseJSON.channel}'
		 };
$(function(){

	
	     
	     $('#tr-region').show();
	     $('#tr-branch').hide();
		 $('#tr-accid').hide();
		 $('#tr-mobNo').hide();
	     $('#tr-fromdate').hide();
	     $('#tr-mobNo1').hide();
		 $('#tr-txndet').hide();
		 $('#tr-stat').hide();
	     $('#t1').hide();
		 $('#t2').hide();
		 $('#t3').show();
		 $('#t4').show();
	     $('#report-case').hide();
	     $('#report-case1').hide();
	     $('#report-case2').hide();



	     
	     
	
		$(list).each(function(i,val){
			$('#'+val).datepicker(datepickerOptions);
		});

		 console.log(getToday());
		$('#fromDate').val(getToday());
		$('#toDate').val(getToday());

 		$.each(arrayData, function(selectKey, arrvalue ){
			var json = $.parseJSON(arrvalue);
			//console.log("jc"+arrvalue);
			var options = '';
			if(selectKey != 'userid') {
				options =$('<option/>', {value: 'Not Required', text: 'Not Required'});
				$('#'+selectKey).append(options);
			}

			 $.each(json, function(i, v) {
				if(selectKey == 'userid') {
					//console.log("index ==> "+ i);
					 if(i == 0)  userdata+=  "'"+v.key +"'";
						else userdata+= ",'"+v.key  +"'" ;
				}
				 options = $('<option/>', {value: v.key, text: v.val});
				 $('#'+selectKey).append(options);
			 });

			  if(selectKey == 'bank') {
				// To Iterate the Select box ,and setting the in-putted as selected
				$('#'+selectKey).find('option').each(function( i, opt ) {
				    if( opt.value === 'KCB' )
				        $(opt).attr('selected', 'selected');
				});
			 }

		});


 		
  
 		
 		$('#reportname').on('change', function() {
 			 
 			if( $('#reportname').val() =='01'){
 				
 				 $('#report-case').hide();
 			     $('#report-case1').hide();
 			     $('#report-case2').hide();
 				 $('#tr-branch').show();
				 $('#tr-accid').show();
				 $('#tr-mobNo').show();
			     $('#tr-fromdate').show();
			     $('#tr-mobNo1').hide();
				 $('#tr-txndet').hide();
			    // $('#tr-usrname').show(); 
			     $('#t1').show();
				 $('#t2').show();
				 $('#t3').hide();
				 $('#t4').hide();
				 
				 $('#t10').show();
				 $('#t11').show();
				 $('#t12').show();
				 $('#t13').show();
				 
 				}
 			else if( $('#reportname').val() =='02'){
 				 $('#report-case').hide();
 			     $('#report-case1').hide();
 			     $('#report-case2').hide();
 				 $('#tr-branch').hide();
				 $('#tr-accid').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-fromdate').show();
				 $('#tr-mobNo1').hide();
				 $('#tr-txndet').hide();
				 $('#t1').hide();
				 $('#t2').hide();
				 $('#t3').show();
				 $('#t4').show();
				 
				 $('#t10').hide();
				 $('#t11').hide();
				 $('#t12').hide();
				 $('#t13').hide();
 				 
 			 	
 			}
 			else if( $('#reportname').val() =='03'){
 				
				$('#tr-branch').hide();
				 $('#tr-accid').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-usrname').show();
				 $('#tr-mobNo').hide();
				 $('#tr-fromdate').show();
				 $('#tr-mobNo1').hide();
				 $('#tr-txndet').hide();
				 $('#t1').hide();
				 $('#t2').hide();
				 $('#t3').hide();
				 $('#t4').hide();
				 
				 $('#t10').hide();
				 $('#t11').hide();
				 $('#t12').hide();
				 $('#t13').hide();
				 
 				
 			}else if( $('#reportname').val() =='04'){
 				
 				 $('#report-case').hide();
 			     $('#report-case1').hide();
 			     $('#report-case2').hide();
				 $('#tr-branch').hide();
				 $('#tr-accid').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-usrname').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-fromdate').hide(); 
				 $('#tr-mobNo1').hide();
				 $('#tr-txndet').hide();
				 $('#t1').hide();
				 $('#t2').hide();
				 $('#t3').show();
				 $('#t4').show();
				 
				 $('#t10').hide();
				 $('#t11').hide();
				 $('#t12').hide();
				 $('#t13').hide();
				 
 				
 			}else if( $('#reportname').val() =='05'){
 				
 				 $('#report-case').hide();
 			     $('#report-case1').hide();
 			     $('#report-case2').hide();
				 $('#tr-branch').hide();
				 $('#tr-accid').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-fromdate').show();
				 $('#tr-mobNo1').hide();
				 $('#tr-txndet').hide();
				 $('#tr-stat').show();
				 $('#t1').show();
				 $('#t2').show();
				 $('#t3').hide();
				 $('#t4').hide();
				 
				 
				 $('#t10').hide();
				 $('#t11').hide();
				 $('#t12').hide();
				 $('#t13').hide();
				 
 				
 			}else if( $('#reportname').val() =='06'){
 				
 				 $('#report-case').hide();
 			     $('#report-case1').hide();
 			     $('#report-case2').hide();
				 $('#tr-branch').hide();
				 $('#tr-accid').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-fromdate').show();
				 $('#tr-mobNo1').hide();
				 $('#tr-txndet').hide();
				 $('#t1').show();
				 $('#t2').show();
				 $('#t3').hide();
				 $('#t4').hide();
				 
				 
				 $('#t10').hide();
				 $('#t11').hide();
				 $('#t12').hide();
				 $('#t13').hide();
				 
 				
 			}else if( $('#reportname').val() =='07'){
 				
 				 $('#report-case').hide();
 			     $('#report-case1').hide();
 			     $('#report-case2').hide();
				 $('#tr-branch').hide();
				 $('#tr-accid').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-fromdate').show();
				 $('#tr-mobNo1').hide();
				 $('#tr-txndet').hide();
				 $('#t1').show();
				 $('#t2').show();
				 $('#t3').hide();
				 $('#t4').hide();
				 
				 
				 $('#t10').hide();
				 $('#t11').hide();
				 $('#t12').hide();
				 $('#t13').hide();
				 
 				
 			}else if( $('#reportname').val() =='08'){
 				
 				 $('#report-case').hide();
 			     $('#report-case1').hide();
 			     $('#report-case2').hide();
				 $('#tr-branch').hide();
				 $('#tr-accid').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-fromdate').show();
				 $('#tr-mobNo1').hide();
				 $('#tr-txndet').hide();
				 $('#t1').show();
				 $('#t2').show();
				 $('#t3').hide();
				 $('#t4').hide();
				 
				 
				 $('#t10').hide();
				 $('#t11').hide();
				 $('#t12').hide();
				 $('#t13').hide();
	 
		
			}else if( $('#reportname').val() =='09'){
				
				 $('#report-case').hide();
				 $('#report-case1').hide();
				 $('#report-case2').hide();
				 $('#tr-branch').hide();
				 $('#tr-accid').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-fromdate').show();
				 $('#tr-mobNo1').hide();
				 $('#tr-txndet').hide();
				 $('#t1').show();
				 $('#t2').show();
				 $('#t3').hide();
				 $('#t4').hide();
				 
				 
				 $('#t10').hide();
				 $('#t11').hide();
				 $('#t12').hide();
				 $('#t13').hide();
				 
					
				}else if( $('#reportname').val() =='10'){
 				
				$('#tr-branch').show();
				 $('#tr-accid').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-mobNo1').show();
				 $('#tr-txndet').show();
				 $('#tr-fromdate').show();
				 
				 $('#t1').show();
				 $('#t2').show();
				 $('#t3').hide();
				 $('#t4').hide();
				 
				 $('#t10').hide();
				 $('#t11').hide();
				 $('#t12').show();
				 $('#t13').show();
				 
 				
 			}else if( $('#reportname').val() =='11'){
 				
 				 $('#report-case').hide();
 			     $('#report-case1').hide();
 			     $('#report-case2').hide();
 				 $('#tr-branch').hide();
 				 $('#tr-accid').hide();
 				 $('#tr-mobNo').hide();
 				 $('#tr-fromdate').show();
 				 $('#tr-mobNo1').hide();
 				 $('#tr-txndet').hide();
 				 $('#t1').show();
 				 $('#t2').show();
 				 $('#t3').hide();
 				 $('#t4').hide();
 				 
 				 
 				 $('#t10').hide();
 				 $('#t11').hide();
 				 $('#t12').hide();
 				 $('#t13').hide();
 				 
 					
 			}else if( $('#reportname').val() =='12'){
 				
 				 $('#report-case').hide();
 			     $('#report-case1').hide();
 			     $('#report-case2').hide();
 				 $('#tr-branch').hide();
 				 $('#tr-accid').hide();
 				 $('#tr-mobNo').hide();
 				 $('#tr-fromdate').show();
 				 $('#tr-mobNo1').show();
 				 $('#tr-txndet').hide();
 				 $('#t1').show();
 				 $('#t2').show();
 				 $('#t3').hide();
 				 $('#t4').hide();
 				 
 				 
 				 $('#t10').hide();
 				 $('#t11').hide();
 				 $('#t12').hide();
 				 $('#t13').hide();
 				 
 					
 				}
 			
 			else if( $('#reportname').val() =='13'){
 				 
 				 $('#report-case').hide();
 			     $('#report-case1').hide();
 			     $('#report-case2').hide();
 				 $('#tr-branch').hide();
				 $('#tr-accid').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-fromdate').show();
				 $('#tr-mobNo1').hide();
				 $('#tr-txndet').hide();
				 $('#t1').hide();
				 $('#t2').hide();
				 $('#t3').show();
				 $('#t4').show();
				 
				 $('#t10').hide();
				 $('#t11').hide();
				 $('#t12').hide();
				 $('#t13').hide();
 			}
 			
 			else if( $('#reportname').val() =='14'){
 				 
 			
 				 $('#report-case').show();
 			     $('#report-case1').show();
 			     $('#report-case2').show();
				 $('#tr-branch').hide();
				 $('#tr-accid').hide();
				 $('#tr-mobNo').hide();
				 $('#tr-fromdate').show();
				 $('#tr-mobNo1').hide();
				 $('#tr-txndet').hide();
				 $('#t1').hide();
				 $('#t2').hide();
				 $('#t3').show();
				 $('#t4').show();
				 
				 $('#t10').hide();
				 $('#t11').hide();
				 $('#t12').hide();
				 $('#t13').hide();
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
			<li>
				<a href="home.action">Home</a> <span class="divider">&gt;&gt;</span>
			</li>
			<li><a href="#">Reports</a>
			</li>

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

<div class="row-fluid sortable">
	<div class="box span12">

		<div class="box-header well" data-original-title>
			 <i class="icon-edit"></i>Select a Bank Reports To View
			<div class="box-icon">
				<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>
		</div>
		<div class="box-content" >
			<fieldset >
				<table width="100%" border="0"  cellpadding="5" cellspacing="5" id="table table-striped table-bordered bootstrap-datatable" >

				<tr id="tr-region">
					<td  width="15%"><label for="Region"><strong>Select Report </strong></label></td>
					<td  width="25%" class="regionTd">
					<select name="reportname" id="reportname" class="chosen" width="450px"></select><span class='region-spn'></span></td>

					<input type="hidden" value="${responseJSON.makerId}" id="mid" name="mid">
					<td width="15%" id="t1"><strong><label for="Head Office"><strong>Institute </strong></label></td>
					<td width="25%" id="t2" class="headOfficeTd"><strong><select name="headOffice" id="headOffice"  multiple class="chosen" width="450px">
					</select><span class='headOffice-spn'></span></strong></td>
					<td id="t3" style="display:none"></td>
					<td id="t4" style="display:none"></td>
					
				</tr>

				<tr id="tr-branch">
				
					<td id="t10" style="display:none" ><label for="Branch"><strong>Telecom</strong> </label></td>
					<td id="t11" style="display:none" class="LocationTd"><select name="Location" id="Location"  multiple class="chosen" width="450px">
					</select><span class='Location-spn'></span></td>
					<td id="t12" style="display:none" > <label for="Bank"><strong>Status</strong></label></td>
					<td id="t13" style="display:none" class="bankTd"><select name="bank" id="bank" class="chosen" width="450px">
					</select></td>
				</tr>

				<tr id="tr-fromdate" >
					<td> <label for="From Date"><strong>From Date</strong></label></strong></td>
					<td class="fromDateTd"><strong><input name="name" type="text"  id="fromDate" name="fromDate"  ></strong></td>
					<td> <label for="To Date"><strong>To Date</strong></label></strong></td>
					<td class="toDateTd"><strong> <input name="name" type="text"  id="toDate" name="toDate"  ></strong></td>
				</tr>
						
				<tr  id="tr-accid">

					<td><label for="Report Name"><strong>Account ID</strong></label></td>
					<td class="reportnameTd1"><input name="accid" id="accid"  type="text" style="width: 280px;" class="field" required=true maxlength="20" /></td>
					<td><label for="Report Name"><strong>MTS Reference</strong></label></td>
					<td class="reportnameTd2"><input name="refno" id="refno"  type="text"  style="width: 280px;" class="field" required=true maxlength="20" /></td>
				</tr>
				
				<tr  id="tr-mobNo">
					
					<td><label for="Report Name"><strong>Mobile Number</strong></label></td>
					<td class="reportnameTd3"><input name="mobno" id="mobno"  type="text"  style="width: 280px;" class="field" required=true maxlength="20" /></td>
					<td><label for="Report Name"><strong>Pay Bill Number</strong></label></td>
					<td><input name="paybillno" id="paybillno"  type="text"  style="width: 280px;" class="field" required=true maxlength="20" /></td>
				</tr>
				
				<tr  id="tr-usrname" style="display:none">
					
					<td><label for="Report Name"><strong>Select User</strong></label></td>
					<td width="25%" class="regionTd"><select name="uname" id="uname" class="chosen" width="450px"></select><span class='region-spn'></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				
				<tr  id="tr-mobNo1">
					
					<td><label for="Report Name"><strong>Mobile Number</strong></label></td>
					<td class="reportnameTd3"><input name="mobno1" id="mobno1"  type="text"  style="width: 280px;" class="field" required=true maxlength="20" /></td>
					<td><label for="Report Name"><strong>Reference Number</strong></label></td>
					<td><input name="rrno" id="rrno"  type="text"  style="width: 280px;" class="field" required=true maxlength="20" /></td>
				</tr>
				
				<tr  id="tr-txndet">
					
					<td><label for="Report Name"><strong>Transaction Type</strong></label></td>
					<td class="reportnameTd3"><select name="txnty" id="txnty" class="chosen" width="450px"></select><span class='region-spn'></span></td>
					<td><label for="Report Name"><strong>Channel</strong></label></td>
					<td><select name="chnl" id="chnl" class="chosen" width="450px"></select><span class='region-spn'></span></td>
				</tr>
				<tr  id="tr-stat">
				<td><label for="Auth Status"><strong>Select Status</strong></label></td>
					<td>
						<select id="authstat" name="authstat"  required='true' data-placeholder="Select Status" style="width:290px">
				       				<option value="SEL">Select</option>
						            <option value="C">Completed</option>
						            <option value="P">Pending</option>
						            <option value="R">Rejected</option>
						        </select> 
						        </td>
				<td><label for="Registartion Type"><strong>Select Registration Type</strong></label></td>
					<td>
						<select id="regtype" name="regtype"  required='true' data-placeholder="Select Telco." style="width:290px">
				       				<option value="SEL">Select</option>
						            <option value="REG">Registered</option>
						            <option value="ADD">Account Added</option>
						        </select> 
						        </td>
						        	</tr>
						        	
						        	
		 	<tr  id="report-case">
		 	
		 	        <td><label for="Account Number"><strong>Account Number</strong></label></td>
					<td class="accountnumber"><input name="accountnumber" id="accountnumber"  type="text" style="width: 280px;" class="field" required=true maxlength="20" /></td>
				<%-- 	<td><label for="Account Name"><strong>Account Name</strong></label></td>
					<td class="accountname"><input name="accountname" id="accountname"  type="text"  style="width: 280px;" class="field" required=true maxlength="20" /></td>
				 --%>
				 
				 <td></td>
				 <td></td>
				 
				
			</tr>

          	<tr  id="report-case1">
          	
          	        <td><label for="Amount"><strong>Amount</strong></label></td>
					<td class="amount"><input name="amount" id="amount"  type="text" style="width: 280px;" class="field" required=true maxlength="20" /></td>
					<td><label for="Teleophone Number"><strong>Teleophone Number</strong></label></td>
					<td class="telephoneNumber"><input name="telephoneNumber" id="telephoneNumber"  type="text"  style="width: 280px;" class="field" required=true maxlength="20" /></td>
				
			
			</tr>
			
			<tr  id="report-case2">
			
			        <td><label for="Transaction Number"><strong>Transaction Number</strong></label></td>
					<td class="transactionNumber"><input name="transactionNumber" id="transactionNumber"  type="text" style="width: 280px;" class="field" required=true maxlength="20" /></td>
						<td><label for="Status"><strong>Select Status</strong></label></td>
					<td>
						<select id="monstat" name="monstat"  required='true' data-placeholder="Select Status" style="width:290px">
				       				<option value="">Select</option>
						            <option value="N">New Case</option>
						            <option value="O">Open</option>
						            <option value="E">Escalated</option>
						            <option value="C">Closed</option>
						            
						        </select> 
						        </td>
			
			
			</tr>
				 
				
							        	
		 </table>
	</fieldset>



			<div class="form-actions" align="center">
				  <input type="button" class="btn btn-primary" type="text"  name="save" id="save" value="Generate Report" width="100" ></input>
				  <input type="button" class="btn" type="text"  name="save" id="save" value="Cancel" width="100" ></input>
			 </div>
		</div>
		</div>
		
		






 <script type="text/javascript"><!--

	function splitDate(date) {
		var arr = date.split('/');
		var validDate = arr[1] + "/" + arr[0] + "/" + arr[2];
		return validDate;
	}

	function setSelValues(id){
		var strPrep = "";
		var maxLen = $("#"+id +" > option:selected").length;
		var index = 0;

		$( "#"+id +" > option:selected").each(function() {
			index++;

			if( id == 'Location') {
				if(index == 1)  strPrep+=  "'"+this.value.split("\-")[0] +"'";
				else strPrep+= ",'"+this.value.split("\-")[0]  +"'" ;
			 } else {
				if(index == 1)  strPrep+=  "'"+this.value +"'";
				else strPrep+= ",'"+this.value  +"'" ;
			 }

				if(index == maxLen)  {index == 0;}

		});

		return strPrep;
	}

	$(function(){

		$('#toDate').keypress(function() {
			$('#toDate').removeAttr('style');
			$('td.toDateTd > span').text('');
		});

		$('#fromDate').keypress(function() {

		   $('#fromDate').removeAttr('style');
		   $('td.fromDateTd > span').text('');
		});

		$('#toDate').blur(function() {
			$('#toDate').removeAttr('style');
			$('td.toDateTd > span').text('');
		});

		$('#fromDate').blur(function() {
		   $('#fromDate').removeAttr('style');
		   $('td.fromDateTd > span').text('');
		});

		 $('#reportname').blur(function() {
		   $('#reportname').removeAttr('style');
		   $('td.reportnameTd > span').text('');
		});

		$(".selectTarget").change(function() {
			if( $('#reportname option:selected').text() != 'Not Required') {
				$('#reportname').removeAttr('style');
				$('td.reportnameTd > span').text('');
		   }
		});

		$('#save').live('click', function () {

			$('#toDate').removeAttr('style');
			$('td.toDateTd > span').text('');

			$('#fromDate').removeAttr('style');
			$('td.fromDateTd > span').text('');

			$('#reportname').removeAttr('style');
			$('td.reportnameTd > span').text('');

			var v_reportName = $("#reportname option:selected").text();
			//alert("00"+v_reportName);

			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();

			var fromDate1 = new Date(splitDate(fromDate));
			var toDate1 = new Date(splitDate(toDate));
			var sysDate = new Date();

			if(fromDate == "" )
			{
				 $('#fromDate').css({"border-color": "red",
									 "border-weight":"1px",
									 "border-style":"solid"});
				 $('td.fromDateTd').append("<span > &nbsp; Please input from date.</span>");
			}
			 if(toDate == "")
			{
				 $('#toDate').css({"border-color": "red",
								 "border-weight":"1px",
								 "border-style":"solid"});
				 $('td.toDateTd').append("<span > &nbsp; Please input to date.</span >");
			}

			if(v_reportName == 'Not Required' || v_reportName == '')
			{
				 $('#reportname').css({"border-color": "red",
								 "border-weight":"1px",
								 "border-style":"solid"});

				 $('td.reportnameTd').append("<span >&nbsp; Please select a report to generate.<span >");
			}

			if(fromDate1 > toDate1 ) {
				$('#toDate').css({"border-color": "red",
								 "border-weight":"1px",
								 "border-style":"solid"});
				 $('td.toDateTd').append("<span > &nbsp; ToDate should always be greater than From Date</span >");

			} else  {
				if( fromDate1 > sysDate ) {
					$('#fromDate').css({"border-color": "red",
									 "border-weight":"1px",
									 "border-style":"solid"});
					 $('td.fromDateTd').append("<span > &nbsp; From Date should always be less than sysdate.</span >");

				}

				if( toDate1 > sysDate) {
					$('#toDate').css({"border-color": "red",
									 "border-weight":"1px",
									 "border-style":"solid"});
					 $('td.toDateTd').append("<span > &nbsp; To Date should always be less than sysdate.</span >");

				} else if(fromDate1 <= toDate1
						&& fromDate!=""
						&& toDate!=""
						&& v_reportName != 'Not Required') {

					 var v_reportName_1 = "";
					 var flag = false;
					 var extraParam = "";


					 //var queryBuild = "MMT.BANK_TERM_ID=AU.TERMID  AND MMT.BANK_MID=SUBSTR(AU.MERCHANT_ID,0,8)  AND A.MERC_ID=B.MERC_ID AND MMT.STORE_ID=B.STORE_ID AND A.STORE_ID=B.STORE_ID AND PBM.OFFICE_CODE=A.BANK_SID AND AA.REGION_CODE=PBM.REGION_CODE and TRUNC(AU.TXNREQDTTIME)   between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')";
					 var dateCheck = "";
					 var userid = $("#mid").val();
					 
					// alert(userid1);
					 var queryBuild =  " ";
					 var subQueryBuild = "";
					 // extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@";
					//alert("11"+v_reportName);
					 var repArr = v_reportName.split("-");

					 v_reportName = repArr[0];
					 v_reportName_1 = repArr[1];

					/*
				    01		Customer Transaction Log
					02		Customer Successful transaction Report
					03		Customer Commission Report
					04		Customer Decliend Transaction
					05		Merchant Transaction Log
					06		Merchant Successful transaction Report
					07		Merchant Decliend Transaction
					08		Audit Report
					09		Bank Transaction Log
					10		Bank Commission Posting Report
					11		Bank Commission Report
					12		Bank Online Merchant Settlement Report
					13		Bank Online Fee settlement Report
					14		Modified Merchant/ Agents reports
					15		Transactions Agent/Branch
					16		Terminals set up report.
					17		Merchant settlement report-Summary
					18		New Registered Merchant/Agent Daily/Monthly/Yearly
					19      Service Tax Transaction Log
					 
						if(v_reportName == '01' ){
							//   01		Customer Transaction Log
						      var str1=ClientActivityReport();
							  queryBuild =str1+" and "+dateCheck+" ";
							  subQueryBuild=str1+" and "+dateCheck+" ";

								$('#QryKey').val('CUSTOMER_TRANS_LOG_REP');
								$('#JRPTCODE').val('CUSTOMER_TRANS_LOG');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@MPESA Transaction Log";
							    flag=true;

						}*/
						if(v_reportName == '01' ){
						      var str1=ClientActivityReport();
						      dateCheck= " (TRUNC(LT.DATE_CREATED)   between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
							  queryBuild =str1+" and "+dateCheck+" ";
							  subQueryBuild=str1+" and "+dateCheck+" ";

								$('#QryKey').val('NBK_TRANS_LOG_REP');
								$('#JRPTCODE').val('NBK_TRANS_LOG');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@MPESA Transaction Log";
								groupbycond=" ";
								flag=true;

						}else if(v_reportName == '02' )
						{
							var str1=ClientActivityReport();
							  dateCheck= " (TRUNC(A.MAKER_DTTM)   between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
							  queryBuild =str1+" and "+dateCheck+" and A.MAKER_ID='"+userid+"' and AM.AUTH_CODE=A.AUTH_CODE  GROUP BY AM.RELATION ";
							  subQueryBuild=str1+" and "+dateCheck+" ";

								$('#QryKey').val('USER_AUTHORIZATION_REP');
								$('#JRPTCODE').val('USER_AUTHORIZATION');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@USER AUTHORIZATION REPORT##UserID@@"+userid+"";
								groupbycond=" ";
								flag=true;
						}else if(v_reportName == '03' )
						{
							var str1=ClientActivityReport();
							  dateCheck= " (TRUNC(C.MAKER_DTTM)  between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
							  queryBuild =str1+" and "+dateCheck+" AND C.MAKER_ID in (Select LOGIN_USER_ID from user_login_credentials) GROUP BY C.MAKER_ID ";
							  subQueryBuild=str1+" and "+dateCheck+" ";

								$('#QryKey').val('ADMIN_AUTHORIZATION_REP');
								$('#JRPTCODE').val('ADMIN_AUTHORIZATION');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@ADMIN AUTHORIZATION REPORT";
								groupbycond=" ";
								flag=true;
						}else 	if(v_reportName == '04' ){
						      var str1=ClientActivityReport();
							  
								dateCheck= " (TRUNC(AP.MAKER_DTTM)  between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
								queryBuild =str1+"  ORDER BY RELATION ";
								subQueryBuild=str1+" and "+dateCheck+"  ";
								$('#QryKey').val('DAILY_AUTHORIZATIONS_CREATED_REP');
								$('#JRPTCODE').val('DAILY_AUTHORIZATIONS_CREATED');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@Authorization Reports";
								groupbycond=" ";
								flag=true;


						}else 	if(v_reportName == '05' ){
						      var str1=ClientActivityReport();
							  
								dateCheck= " (TRUNC(AP.MAKER_DTTM)  between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
								queryBuild =str1+"  and MA.ref_num=AP.REF_NUM and AC.CUST_ID=MA.ID and AC.REF_NUM=AD.DETAIL_3 order by TRANSCODE";
								subQueryBuild=str1+" and "+dateCheck+"  ";
								$('#QryKey').val('ONBOARDING_CUST_REP');
								$('#JRPTCODE').val('ONBOARDING_CUST');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@On Boarding Customer Reports";
								groupbycond=" ";
								flag=true;


						}else 	if(v_reportName == '06' ){
						      var str1=ClientActivityReport();
							  
								dateCheck= " (TRUNC(D.MAKER_DTTM)  between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
								queryBuild =str1+" and c.maker_id=d.maker_id ";
								subQueryBuild=str1+" and "+dateCheck+"  ";
								$('#QryKey').val('CHECKER_CNT_REP');
								$('#JRPTCODE').val('CHECKER_CNT');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@Checker Authorization Report";
								groupbycond=" ";
								flag=true;


						}else 	if(v_reportName == '07' ){
							var str1=ClientActivityReport();
								dateCheck= " (TRUNC(D.MAKER_DTTM)  between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
								queryBuild =str1+" and c.maker_id=d.maker_id ";
								subQueryBuild=str1+" and "+dateCheck+"  ";
								$('#QryKey').val('CUSTOMER_CNT_REP');
								$('#JRPTCODE').val('CUSTOMER_CNT');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@Customer Service Authorization Report";
								groupbycond=" ";
								flag=true;


						}else 	if(v_reportName == '08' ){
						      var str1=ClientActivityReport();
								dateCheck= " (TRUNC(D.MAKER_DTTM)  between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
								queryBuild =str1+" and c.maker_id=d.maker_id ";
								subQueryBuild=str1+" and "+dateCheck+"  ";
								$('#QryKey').val('SECURITY_CNT_REP');
								$('#JRPTCODE').val('SECURITY_CNT');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@SECURITY Authorization Report";
								groupbycond=" ";
								flag=true;


						}else 	if(v_reportName == '09' ){
						      var str1=ClientActivityReport();
							  
								dateCheck= " (TRUNC(D.MAKER_DTTM)  between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
								queryBuild =str1+" and c.maker_id=d.maker_id ";
								subQueryBuild=str1+" and "+dateCheck+"  ";
								$('#QryKey').val('MAKER_CNT_REP');
								$('#JRPTCODE').val('MAKER_CNT');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@Maker Authorization Report";
								groupbycond=" ";
								flag=true;


						
						}else 	if(v_reportName == '10' ){
						      var str1=ClientActivityReport();
							  
								dateCheck= "  (TRUNC(DATE_CREATED)  between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
								queryBuild =str1+" ORDER BY DATE_CREATED ";
								//subQueryBuild=str1+" and "+dateCheck+"  ";
								$('#QryKey').val('DAILY_TXN_REP');
								$('#JRPTCODE').val('DAILY_TXN');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@Daily Transaction Report";
								groupbycond=" ";
							    flag=true;


						}else 	if(v_reportName == '11' ){
						      var str1=ClientActivityReport();
							  
								dateCheck= "  (TRUNC(DATE_CREATED)  between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
								queryBuild =str1+"  ";
								//subQueryBuild=str1+" and "+dateCheck+"  ";
								$('#QryKey').val('TXN_TYPE_CNT_REP');
								$('#JRPTCODE').val('TXN_TYPE_CNT');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@Trasaction Report";
								groupbycond=" group by TXN_TYPE";
							    flag=true;


						}else 	if(v_reportName == '12' ){
						      var str1=ClientActivityReport();
							  
								dateCheck= " (TRUNC(DATE_CREATED)  between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
								//queryBuild =str1+" and  TXN_TYPE in ('MPESAC2B') order by DATE_CREATED ";
								queryBuild =str1+" and  TXN_TYPE in ('MPESAB2C') order by DATE_CREATED ";
								//subQueryBuild=str1+" and "+dateCheck+"  ";
								$('#QryKey').val('MPESA_TXN_REP');
								$('#JRPTCODE').val('MPESA_TXN');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@Mpesa Trasaction Report";
								groupbycond=" ";
							    flag=true;


						}else 	if(v_reportName == '13' ){
							  var str1=ClientActivityReport();
							   dateCheck= " (TRUNC(MAKER_DTTM)   between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
							  queryBuild =str1+" ";
							  subQueryBuild=str1+" and "+dateCheck+" ";

								$('#QryKey').val('BULK_ERROR_REP');
								$('#JRPTCODE').val('BULK_ERRORS');
								extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@BULK UPLOAD ERROR REPORT##UserID@@"+userid+"";
								groupbycond=" ";
								flag=true;
						}else 	if(v_reportName == '14' ){
						  var str1=ClientActivityReport();
							   dateCheck= "  FT.TRPK=TL.TRANLOG_PK and (TRUNC(FT.FRAUD_DTTM)   between  to_date('"+fromDate+"','DD/MM/YYYY') AND  to_date('"+toDate+"','DD/MM/YYYY')) ";
								  queryBuild =str1+" ";
								  subQueryBuild=str1+" and "+dateCheck+" ";
								  
									$('#QryKey').val('REPORT_CASE_RPT_REP');
									$('#JRPTCODE').val('REPORT_CASE_RPT');
									extraParam = "FROM_DATE@@"+fromDate+"##TO_DATE@@"+toDate+"##REPORT_TITLE@@REPORT CASE";
									groupbycond=" ";
									flag=true;

							
						}
						
						else{
							alert(' Please Enter All Mandatory Details.');
						}
				     if(flag) {
											$('#queryconditions').val(queryBuild);
											$('#dateCheck').val(dateCheck);
											$('#eparam').val(extraParam);
											$('#subQueryConditions').val(subQueryBuild);
											$('#groupbycond').val(groupbycond);
											$("#form1")[0].action="${pageContext.request.contextPath}/banking/reportsall.action";
											$('#form1').submit();
										}
				}
			}
		}); // End of OnClick Event
	}); // End Of Ready Function

	function ClientActivityReport(){
		 var v_region  = $("#region").text()  == null ? 'NO' :  setSelValues("region") ;
		 var v_headOffice  = $("#headOffice").text() == null ? 'NO' :  setSelValues("headOffice") ;
		 var v_Location = $("#Location").text() == null ? 'NO' :  setSelValues("Location") ;
		 var v_userid =  $("#userid").val() == null  ? 'NO' :  setSelValues("userid") ;
		 var v_bank = $("#bank option:selected").val();
		 var v_txnType =  $("#txnType").text()  == null ? 'NO' :  setSelValues("txnType") ;
		 var v_accid =  $("#accid").val();
		 var v_refno =  $("#refno").val();
		 var v_mobno =  $("#mobno").val();
		 var v_paybill =  $("#paybillno").val();
		 var v_mobno1 =  $("#mobno1").val();
		 var v_rrno 	=  $("#rrno").val();
		 var v_txnty =  $("#txnty").val();
		 var v_chnl =  $("#chnl").val();
 		 var v_mid 	   =  $("#mid").val();
		 var v_userid1 = $("#uname").val();
		 var v_regtype = $("#regtype").val();
		 var v_authstat = $("#authstat").val();
		 
		 var v_accountnumber = $("#accountnumber").val();
		 var v_accountname = $("#accountname").val();
		 var v_amount = $("#amount").val();
		 var v_telephoneNumber = $("#telephoneNumber").val();
		 var v_transactionNumber = $("#transactionNumber").val();
		 var v_monstat = $("#monstat").val();



	
		 


		 
		 
		 
		 v_region  = (v_region == '' || v_region.indexOf("Not Required") != -1 ) ? 'NO' :  v_region ;
		 v_headOffice  = (v_headOffice == '' || v_headOffice.indexOf("Not Required") != -1 ) ? 'NO' :  v_headOffice ;
		 v_Location = (v_Location == '' || v_Location.indexOf("Not Required") != -1 ) ? 'NO' :  v_Location ;
		 v_userid =  (v_userid == '' || v_userid.indexOf("Not Required") != -1 ) ? 'NO' :  v_userid ;
		 v_txnType = (v_txnType == '' || v_txnType.indexOf("Not Required") != -1 ) ? '-NO-' :  v_txnType ;
		 v_userid1 = (v_userid1 == '' || v_userid1.indexOf("Not Required") != -1 ) ? 'NO' :  v_userid1 ;



		//queryBuild =  " LT.TERMINALNUMBER=TM.TERMINAL_ID AND MM.MERCHANT_ID=LT.MERCHANTID AND MM.MERCHANT_ID=SM.MERCHANT_ID and SM.STORE_ID=TM.STORE_ID AND LT.APPROVEDBY=ULC.LOGIN_USER_ID AND ULC.COMMON_ID=UINFO.COMMON_ID AND PBM.OFFICE_CODE=SM.LOCATION  ";
		queryBuild =  " ";
		/*$('#QryKey').val('ALL_TRN_REP');
		$('#JRPTCODE').val('DAILY_ALL_POS_CLIENT_TRAN_RPT'); */

		 if(v_headOffice !='NO') { queryBuild +=" and INSTITUTE in ("+v_headOffice+")"; }
		 if(v_Location !='NO') { queryBuild +=" and OPERATOR in ("+v_Location+")"; }
		 if(v_bank !='Not Required') { queryBuild +=" and STATUS = '"+v_bank+"'"; }
		 if(v_accid !='') { queryBuild +=" and CREDIT_AC ='"+v_accid+"'"; }
		 if(v_refno !='') { queryBuild +=" and BILL_REF_NO ='"+v_refno+"'"; }
		 if(v_mobno !='') { queryBuild +=" and MSISDN ='"+v_mobno+"'"; }
		 if(v_paybill !='') { queryBuild +=" and PAYBILL_SHORTCODE ='"+v_paybill+"'"; }
		 if(v_userid1 !='NO') { queryBuild +=" and MAKER_ID ='"+v_userid1+"'"; }
		 
		 if(v_mobno1 !='') { queryBuild +=" and MSISDN ='"+v_mobno1+"'"; }
		 if(v_rrno !='') { queryBuild +=" and POSTING_RRN ='"+v_rrno+"'"; }
		 if(v_txnty !='Not Required') { queryBuild +=" and TXN_TYPE ='"+v_txnty+"'"; }
		 if(v_chnl !='Not Required') { queryBuild +=" and CHANNELID ='"+v_chnl+"'"; }
		 if(v_authstat !='SEL') { queryBuild +=" and AP.STATUS ='"+v_authstat+"'"; }
		 if(v_regtype!="SEL")
		 	{
			 if(v_regtype!="REG")
			 	{ 
				 	queryBuild +=" and upper(transcode) ='FETCHREGCUSTDET' AND detail_3<> ' '"; 
				}
			 else 
				 {
				 alert("");
				 	queryBuild +=" and upper(transcode) !='FETCHREGCUSTDET' AND detail_3<> ' '"; 
				 }
			}
		 
		 if(v_accountnumber !='') { queryBuild +=" and  TL.DEBIT_AC='"+v_accountnumber+"'"; }
		 if(v_amount !='') { queryBuild +=" and FT.TXN_AMOUNT='"+v_amount+"'"; }
		 if(v_telephoneNumber !='') { queryBuild +=" and   TL.MSISDN='"+v_telephoneNumber+"'"; }		
		 if(v_transactionNumber !='') { queryBuild +=" and   TL.TXN_ID='"+v_transactionNumber+"'"; }
		 if(v_monstat !='') { queryBuild +=" and  FT.MON_STATUS='"+v_monstat+"'"; }



		return queryBuild;
	}

</script>

		<input type="hidden" name="querymode" id="querymode" value="page" />
		<input type="hidden" name="JRPTCODE" id="JRPTCODE" value='' />
		<input type="hidden" name="mode" id="mode" value="pdf,xls,csv" />
		<!--<input type="hidden" name="mode" id="mode" value="pdf,html,xls,csv" />-->
		<input type="hidden" name="extrafields" id="extrafields" value="" />
		<input type="hidden" name="queryconditions"  id="queryconditions" value="" />
		<input type="hidden" name="QryKey" id="QryKey" value="" />
		<input type="hidden" name="eparam" id="eparam" value="" />
		<input type="hidden" name="dateCheck" id="dateCheck" value="" />
		<input type="hidden" name="subQueryConditions" id="subQueryConditions" value=" " />
		<input type="hidden" name="groupbycond" id="groupbycond" value=" " />

	</div><!--/#content.span10-->
</form>
</body>
</html>
