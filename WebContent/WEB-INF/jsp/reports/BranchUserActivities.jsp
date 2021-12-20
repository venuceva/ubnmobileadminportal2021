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
var tbranch = "Please select Branch";
var tuserid = "Please select User Id";

var fromDateRules={required: true};
var toDateRules={required: true};
var branchRule={required: true};
var userIDRule={required: true};

var fromDateMessages = {required: fromMessage};
var toDateMessages = {required: toMessage};
var branchMessages = {required: tbranch};
var useridMessages = {required: tuserid};

var valid = {

		rules :{
			fromDate : fromDateRules,
			toDate : toDateRules,
			branch : branchRule,
			userID : userIDRule
		},
		messages :{

			fromDate: fromDateMessages,
			toDate: toDateMessages,
			branch: branchMessages,
			userID: useridMessages
		}
};
function queryUser()
{
	$("#form1").validate(valid);
	if($("#form1").valid())
	{
		event.preventDefault();
          $(this).prop('disabled', true);
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/queryDeails.action';
			$("#form1").submit();

			return true;
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




var list = "fromDate,toDate".split(",");
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
		
	$('#branch').on('change', function(e) {
		
		var branchcode=this.value;
	
	var queryString = "entity=${loginentity}&product="+branchcode+"&type=BRANCH-ACTIVATIES";

	document.getElementById("userID").length=1;
	$.getJSON("retriveServise.action", queryString, function(data) {
			if(data.region!=""){
				var mydata=data.region;
  			var mydata1=mydata.split(":");
  
   			$.each(mydata1, function(i, v) {
   				var options = $('<option/>', {value: (mydata.split(":")[i]), text: (mydata.split(":")[i])}).attr('data-id',i);
   				
   				$('#userID').append(options);
   				$('#userID').trigger("liszt:updated");
   			});
   			
   			
  		}else{
  			$('#userID').append("");
				$('#userID').trigger("liszt:updated");
  		}  
 	});  
	});
	
});
</script>
<s:set value="responseJSON" var="respData" />
</head>


<body>
<form name="form1" id="form1" method="post">

			<div id="content" class="span10">
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Branch User Activities </a>  </li>
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
					<i class="icon-edit"></i>Branch User Activities
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
					<td width="20%" ><label for="User Id"><span id="enname">Branch</span> <font color="red">*</font></label></td>
					<td width="30%" >
					
						
						<s:select cssClass="chosen-select" headerKey=""
															headerValue="Select" list="#respData.PRODUCT_TEMP"
															name="branch" id="branch" requiredLabel="true"
															theme="simple" data-placeholder="Choose User ID"
															required="true" />  
						
					</td>

					<td width="20%" ><span id="enname">User ID</span> <font color="red">*</font></label></td>
					<td width="30%" >
					<select class="chosen-select" name="userID" 
										id="userID" >
										<option value="">Select</option>
								</select>
					</td>
				</tr>
				<tr class="even">
					<td ><label for="From Date">From Date<font color="red">*</font></label></td>
					<td >
						<input type="text" maxlength="10"  class="fromDate" id="fromDate" name="fromDate" required=true  readonly="readonly"/>
					</td>
					<td  ><label for="To Date"><span id="enadd">To Date</span> <font color="red">*</font></label> </td>
					<td  >
						<input type="text" maxlength="10"   class="toDate" id="toDate" name="toDate" required=true readonly="readonly"/>
					</td>

				</tr>
		 </table>
		</fieldset>
	 	  </div>
	  </div>
	  </div>
		<div class="form-actions">
				<input type="button"  class="btn btn-success"  name="save" id="save" value="Get Audit Report" onClick="queryUser()"></input>
		</div>
	</div>
 </form>
</body>
</html>