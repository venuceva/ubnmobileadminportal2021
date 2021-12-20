 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Create Entity</title>

<script type="text/javascript">


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

var subrules = {
		   rules : {
			   accountNumber : { required : true, digits : true},
			   monstatus: { required : true }
		   },  
		   messages : {
			  accountNumber : { required : "Please Enter Values"},
		   	  monstatus		: {	required : "Please select an Option"}
		   } 
		 };


$(function () {
	
	$('#fromDate').val(getToday());
	$('#toDate').val(getToday());

	$("#accountnumber").hide();
	$("#accountname").hide();
	$("#amountamt").hide();
	$("#telephonenumber").hide();
	$("#transactionnumber").hide();
	
	$("#status").hide();

	
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	});
	

	$('#filter').live('change', function(){
		
		var filter=$('#filter').val();
		//alert(filter);
		$("#errors").text("");
		$("#crieteria").show();
		 if(filter == "ACCNUM"){
			 $("#reqtype").text('Account Number');
		}else if(filter == "TRANSNUM"){
			 $("#reqtype").text('Payment Reference Number');
		}
	});

    $('#btn-submit').live('click', function () {
    	
    	if ($('#filter').val()=="")
    		{
    		$("#errors").text('Please Select Any Search Criteria');
    		}
    	else{
		if($('#form1').validate(subrules)){
			
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/searchCasesfraudactionAct.action';
			$("#form1").submit();
			return true;
		} else {
			return false;
		}
    	}
    });
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

<style type="text/css">
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
  font-weight: bold;
  color: red;
  padding: 2px 8px;
  margin-top: 2px;
}
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}

</style>
</head>
<body>
<form name="form1" id="form1" method="post">
	<div id="content" class="span10">
 		<div>
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="#">Fruad Monitor</a> </li>
			</ul>
		</div>
	
	  <div class="row-fluid sortable">
		<div class="box span12">

			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Fruad Monitor Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

				</div>
			</div>
		<div class="box-content">
			<fieldset>
				<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " id="user-details">
					<tr class="odd">
						<td width="25%" ><strong><label for="Filter"><strong>Filter</strong><font color="red">*</font></label></strong></td>
						<td width="25%" >
							<select id="filter" name="filter" class="chosen-select" data-placeholder="Choose Student Number..." style="width: 220px;" required=true  >
								<option value="">Select</option>
								<option value="ACCNUM">Account Number</option>
								<option value="TRANSNUM">Payment Reference Number</option>
								
							 </select>
						</td>
						<td width="25%" ></td>
						<td width="25%" ></td>
						
						
					</tr>
					
					<tr class="odd">
						<td><label for="FROM FRAUD DATE"><strong>From fraud date<font color="red">*</font></strong></label></td>
						<td><input name="fromDate" id="fromDate"  type="text" class="field" required=true  maxlength="50" /></td>
						<td><label for="FROM FRAUD DATE"><strong>To fraud date<font color="red">*</font></strong></label></td>
						<td><input name="toDate" id="toDate"  type="text" class="field" required=true  maxlength="50" /></td>
					</tr>
					<tr id="crieteria" style="display:none">
							<td><label for="ACCOUNT NUMBER"><strong><strong><span id='reqtype' name='reqtype'></span></strong><font color="red">*</font></strong></label></td>
							<td><input name="accountNumber" id="accountNumber"  type="text" class="field" required=true  maxlength="50" /></td>
							<td></td>
							<td></td>
						
					</tr>
					</table>
				<div class="form-actions">
					<input type="button" name="btn-submit" class="btn btn-success" id="btn-submit" value="Submit" /> &nbsp&nbsp&nbsp&nbsp&nbsp <span class="errors" id="errors"></span>
				
				
				</div>
			</fieldset>

		</div>

	</div>

	</div>

</div>
</form>
</body>
</html>