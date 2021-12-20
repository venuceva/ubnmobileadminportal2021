<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>


<style type="text/css">
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
.errors {
color: red;
}
</style>
<script type="text/javascript">

var fromMessage = "Enter from Date";
var toMessage = "Enter to Date";
var accdetailsmsg = "Enter Mobile No";
var wtype="Select Wallet Type";

var fromDateRules={required: true};
var toDateRules={required: true};
var accdetails={required: true};
var wtype1={required: true};

var fromDateMessages = {required: fromMessage};
var toDateMessages = {required: toMessage};

var valid = {

		rules :{
			branchid: accdetails,
			fromdate : fromDateRules,
			todate : toDateRules,
			type : wtype1
		},
		messages :{
			branchid: accdetailsmsg,
			fromdate: fromDateMessages,
			todate: toDateMessages,
			type: wtype
		}
};
function queryUser()
{
	$("#form1").validate(valid);
	if($("#form1").valid())
	{
		var queryString = "entity=${loginEntity}&method=validationstatement&fname="+$('#branchid').val()+"&service="+$('#type').val()+"&frmdate="+$('#fromdate').val()+"&todate="+$('#todate').val();
		
		$.getJSON("postJson.action", queryString,function(data) { 
			if(data.finalCount==0){
				$('#messages').text("No Transaction available ");
			}else if(data.finalCount==50){
				$('#messages').text("234"+$('#branchid').val()+" Invalid Mobile Number .");
			}else{ 
				event.preventDefault();
		          $(this).prop('disabled', true);
					$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/downloadStatement.action';
					$("#form1").submit();
		
					return true;
			}
		});
	}
	else
	{
			return false;
	}
}

function getOption(key,value)
{
	return "<option value='"+value+"'>"+key+"</option>";
}

/* $(document).ready(function(){

	var json = '${resJSON.ACTIONLIST}';
	var jsonObj = $.parseJSON(json);
	var i;
	var html="";
	html = html + getOption('SELECT','');
	for(i=0;i<jsonObj.length;i++)
	{
			html = html + getOption(jsonObj[i].TEXT,jsonObj[i].VALUE);
	}
	$('#actionname').append(html);
	
	



}); */


var list = "fromdate,todate".split(",");
var datepickerOptions = {
				showTime: true,
				showHour: true,
				showMinute: true,
	  		dateFormat:'dd/mm/yy',
	  		alwaysSetTime: false,
	  		yearRange: '1910:2050',
			changeMonth: true,
			changeYear: true
};
$(function() {
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	});
});


$(document).ready(function(){
	
	var config = {
		      '.chosen-select'           : {},
		      '.chosen-select-deselect'  : {allow_single_deselect:true},
		      '.chosen-select-no-single' : {disable_search_threshold:10},
		      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		      '.chosen-select-width'     : {width:"95%"}
		    }
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    } 
	
});
</script>

</head>


<body>
<form name="form1" id="form1" method="post">

			<div id="content" class="span10">
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Wallet Statement</a>  </li>
 					</ul>
				</div>

				<table>
					<tr>
						<td colspan="3">
							<div class="messages" id="messages"><s:actionmessage /></div>
							<div class="errors" id="errors"><s:actionerror /></div>
						</td>
					</tr>
				</table>

  <div class="row-fluid sortable">
	<div class="box span12">
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Wallet Statement
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

				</div>
			</div>
		<div class="box-content">
			<fieldset>
				 <table width="950" border="0" cellpadding="5" cellspacing="1"
							class="table table-striped table-bordered bootstrap-datatable " id="user-details">
				<tr class="even">
				<td width="20%" ><label for="User Id"><span id="enname">Wallet Type</span> <font color="red">*</font></label></td>
					<td width="30%" >
						<select id="type" name="type" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
																<option value="CUSTOMER">Customer</option>
																<option value="AGENT">Agent</option>
																<option value="AGENTCOMM">Agent Commission</option>
															</select>
					</td>
					<td width="20%" ><label for="User Id"><span id="enname">Mobile No</span> <font color="red">*</font></label></td>
					<td width="30%" >
						<span><input type="text" name="isocode" id="isocode" style="width:25px;" value="234" disabled /></span><input type="text" class="userID" id="branchid" name="branchid" />
					</td>

				</tr>
				<tr class="even">
					<td ><label for="From Date">From Date<font color="red">*</font></label></td>
					<td >
						<input type="text" maxlength="10"  class="fromdate" id="fromdate" name="fromdate" required=true  readonly="readonly"/>
					</td>
					<td  ><label for="To Date"><span id="enadd">To Date</span> <font color="red">*</font></label> </td>
					<td  >
						<input type="text" maxlength="10"   class="todate" id="todate" name="todate" required=true readonly="readonly"/>
					</td>

				</tr>
		 </table>
		</fieldset>
	 	  </div>
	  </div>
	  </div>
		<div class="form-actions">
				<input type="button"  class="btn btn-success"  name="save" id="save" value="Download Statement" onClick="queryUser()"></input>
		</div>
	</div>
 </form>
</body>
</html>