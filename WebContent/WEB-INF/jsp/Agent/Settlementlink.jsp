
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
var tuserid = "Please select User Id";

var fromDateRules={required: true};


var fromDateMessages = {required: fromMessage};
var toDateMessages = {required: toMessage};

/* var valid = {

		rules :{
			fromDate : fromDateRules,
			todate : todateRules
		},
		messages :{

			fromDate: fromDateMessages,
			toDate: toDateMessages
		}
};  */

function getOption(key,value)
{
	return "<option value='"+value+"'>"+key+"</option>";
}




var list = "fromdate".split(",");
var datepickerOptions = {
				showTime: true,
				showHour: true,
				showMinute: true,
	  		dateFormat:'dd/mm/yy',
	  		alwaysSetTime: false,
	  		yearRange: '1910:2025',
			changeMonth: true,
			changeYear: true
};


$(function() {
	$( "#todate" ).datepicker({ 
	dateFormat:'dd/mm/yy',
	maxDate: new Date() });

	});	
	
$(function() {
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	});
});

var datavalidation = {
 		
 		rules : {
 			application	: { required : true},
 			servicecode : { required : true},
 			fromdate    : {required :true},
 			todate      : {required : true}
			
 		},		
 		messages : {
 			
 			application : { 
 							required : "Please Select Option.",
 							
					 },
					 
					 servicecode : { 
 							required : "Please Select servicecode.",
 							
					 },
			fromdate   :{
				required : "Please Select From Date.",
			},	
			
			todate   :{
				required : "Please Select To Date.",
			}
 		   
			
					 
 		} 
 };

$(document).ready(function() {
$('#btn-submit').click(function () {  
	 $("#form1").validate(datavalidation);
		if($('#application').val()=="SETTLEMENT_REPORT"){
		}  
		
		var url="${pageContext.request.contextPath}/<%=appName %>/settlemetreportinfo.action"; 
		$("#form1")[0].action=url;
		$("#form1").submit();
		return true;
	
});

});
if($("#dateradio").val()==""){
	
}
$(document).ready(function() {
	
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
	
	$('#btn-submit').click(function () {  
		 $("#form1").validate(datavalidation);
		 if($('#application').val()=="UNSETTLEMENT_REPORT"){
				
				var url="${pageContext.request.contextPath}/<%=appName %>/unsettlemetreportinfo.action"; 
				$("#form1")[0].action=url;
				$("#form1").submit();
				return true;
			} 
		});

		});
		

		
$(document).ready(function() {
	
	var lmtjson =  '${responseJSON.VIEW_RECON_DATA}';
	
	console.log("Welcome to pro");
	
	var finaljsonobj = jQuery.parseJSON(lmtjson);
	
	
	buildtable(finaljsonobj,'LMT_TBody');
	

});


function buildtable(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=sno >" + i + "</td>";
			htmlString = htmlString + "<td id=SERVICECODE name=SERVICECODE ><strong>" + jsonObject.SERVICECODE + "</strong></td>";
			htmlString = htmlString + "<td id=APPLICATION name=SERVICEDESC ><strong>" + jsonObject.SERVICEDESC + "</strong></td>";
			htmlString = htmlString + "<td id=COUNT name=COUNT  ><div class='label label-success' style='text-align:center'>" + jsonObject.COUNT + "</div></td>";
			htmlString = htmlString + "<td id=AMOUNT name=AMOUNT  ><div class='label label-important' style='text-align:center'>" + jsonObject.AMOUNT + "</div></td>";
			if(jsonObject.COUNT==0){
				htmlString = htmlString + "<td id='' ><a class='btn btn-success' href='#' id='view' index="+i+" val="+i+" title='View' data-rel='tooltip'  disabled=true> <i class='icon icon-book icon-white'></i></a></td>";
			}else{
				htmlString = htmlString + "<td id='' ><a class='btn btn-success' href='#' id='view' index="+i+" val="+i+" title='View' data-rel='tooltip' onclick=viewfun('"+jsonObject.SERVICECODE+"')> <i class='icon icon-book icon-white'></i></a></td>";	
			}

			
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

function viewfun(v){
	
	$("#servicecode").val(v);
	var url="${pageContext.request.contextPath}/<%=appName %>/unsettlemetreportinfo.action"; 
	$("#form1")[0].action=url;
	$("#form1").submit();
	return true;
}
		
</script>
<s:set value="responseJSON" var="respData" />
</head>

<body>
<form name="form1" id="form1" method="post">

			<div id="content" class="span10">
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Unsettlement Details </a>  </li>
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
					<i class="icon-edit"></i>Unsettlement Pending Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

				</div>
			</div>
		<div class="box-content">
			<fieldset>
				
		 
		 <table width='100%' class="table table-striped table-bordered bootstrap-datatable "  
						id="DataTables_Table_0" >
						<thead>
							<tr>
								<th>S No</th>
								<th>Service Code</th>
								<th>Service Name</th>
								<th>Txn Count</th>
								<th>Txn Amount</th>
								<th>Actions</th>
							</tr>
						</thead> 
						 <tbody id="LMT_TBody">
							   
						</tbody>  
					</table>
		 
		</fieldset>
	 	  </div>
	  </div>
	  </div>
	  <input type="hidden"  id="application" name="application" value="UNSETTLEMENT_REPORT"/>
	  <input type="hidden" id="servicecode" name="servicecode" />
	  
		<!-- <div class="form-actions">
	        	<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Cancel" />
				<input type="button"  class="btn btn-success"  name="btn-submit" id="btn-submit" value="Submit" ></input>
		</div> -->
	</div>
 </form>
</body>
</html>