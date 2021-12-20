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

var fromDateRules={required: true};
var toDateRules={required: true};

var fromDateMessages = {required: fromMessage};
var toDateMessages = {required: toMessage};

var valid = {

		rules :{
			fromdate : fromDateRules,
			todate : toDateRules
		},
		messages :{

			fromdate: fromDateMessages,
			todate: toDateMessages
		}
};
function queryUser()
{
	
	var filetype=$("#application").val();
	//alert(filetype);
		if(filetype=="RAASFILE-RAAS File"){
			
			$("#form1").validate(valid);
			if($("#form1").valid())
			{
				event.preventDefault();
		        $(this).prop('disabled', true);
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/uploadedinformation.action';
				$("#form1").submit();

			return true;
			}else{
				return false;	
			}
			
		}
		
		
		if(filetype=="OFFLINE_REPORTS-Offline Reports"){
			
			$("#form1").validate(valid);
			if($("#form1").valid())
			{
			event.preventDefault();
	          $(this).prop('disabled', true);
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/reportuploadedinformation.action';
				$("#form1").submit();

				return true;
			}else{
				return false;	
			}
				
		}
		
		if(filetype=="RAASFILE-Not Found RAAS"){
			
			var vvv=($("#raas").val()).split('\n');
			var vv="";
			
			for(i=0;i<vvv.length;i++){
				
				vv=vv+","+vvv[i];
			}
			
			$("#customercode").val(vv);
			
			if($("#customercode").val()==","){
				$("#errors").text("Please Enter Payment Reference No ");
				
			}else{
			event.preventDefault();
	          $(this).prop('disabled', true);
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/nonraasformation.action';
				$("#form1").submit();

				return true;
			}	
		}
		
	
	
}

function getOption(key,value)
{
	return "<option value='"+value+"'>"+key+"</option>";
}




var list = "fromdate,todate".split(",");
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
	
	$('#application').change(function() {
	  
		$('#trowa2').css("display","none");
		$('#searchdate').css("display","");
		
	   if($(this).val()=="RAASFILE-Not Found RAAS"){
		    
		   $('#trowa2').css("display","");
		   $('#searchdate').css("display","none");
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
<s:set value="responseJSON" var="respData" />
</head>


<body>
<form name="form1" id="form1" method="post">

			<div id="content" class="span10">
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">File Uploaded Result </a>  </li>
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
					<i class="icon-edit"></i>File Uploaded Result Search
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
								<td width="25%"><label for="gender"><strong>File Upload Type <font color="red">*</font></strong></label></td>
									<td width="25%">
									<s:select cssClass="chosen-select" headerKey=""
												headerValue="Select" list="#respData.LIMIT_CODE"
												name="application" id="application" requiredLabel="true"
												theme="simple" data-placeholder="Choose Limit Code..."
												required="true" /> 
									</td>
									<td width="25%"></td>
									<td width="25%"></td>
									
								</tr>
				
				
				<tr class="even" id="searchdate" name="searchdate">
					<td ><label for="From Date">From Date<font color="red">*</font></label></td>
					<td >
						<input type="text" maxlength="10"  class="fromDate" id="fromdate" name="fromdate" required=true  readonly="readonly"/>
					</td>
					<td  ><label for="To Date"><span id="enadd">To Date</span> <font color="red">*</font></label> </td>
					<td  >
						<input type="text" maxlength="10"   class="toDate" id="todate" name="todate" required=true readonly="readonly"/>
					</td>

				</tr>
				
				<tr id="trowa2" name="trowa2" style="display:none">
								<td width="20%"><label for="Service ID"><strong>Enter Payment Reference No<font color="red">*</font></strong></label></td>
								<td>
								<textarea rows="4" cols="50" id="raas" name="raas"></textarea>
								</td>
								 <td></td>
								  <td></td>
				</tr>
		 </table>
		</fieldset>
	 	  </div>
	  </div>
	  </div>
	  <input type="hidden" id="customercode" name="customercode">
		<div class="form-actions">
				<input type="button"  class="btn btn-success"  name="save" id="save" value="Get Uploaded Report" onClick="queryUser()"></input>
		</div>
	</div>
 </form>
</body>
</html>