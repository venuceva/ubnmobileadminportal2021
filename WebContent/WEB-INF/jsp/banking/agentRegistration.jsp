<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
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
<script type="text/javascript" > 


$(document).ready(function () {
	var tds=new Array();
	 var storeData ='${responseJSON.AgentData}';
		//alert(storeData);
	 	var json = jQuery.parseJSON(storeData); 
	//	alert(json);
		var val = 1;
		var rowindex = 0;
		var colindex = 0;
		var addclass = "";
		$.each(json, function(i, v) {
			if(val % 2 == 0 ) {
				addclass = "even";
				val++;
			}
			else {
				addclass = "odd";
				val++;
			}  
			var rowCount = $('#agentTBody > tr').length;
			
			colindex = ++ colindex; 
			
			var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+
					"<td >"+colindex+"</td>"+
					"<td >"+v.terminalid+"</span></td>"+	
					"<td class='hidden-phone'>"+v.branch+"</span> </td>"+ 
	 				"<td>"+v.zone+"</span></td>"+
					"<td class='hidden-phone'>"+v.physicalpremis+"</span></td>"+
					"<td class='center hidden-phone'>"+v.makerid+"</span></td>"+
					"<td class='hidden-phone'>"+v.makerdate+"</span></td>"+
					"<td><p><a id='agent-modify' class='btn btn-success' href='#' index='"+rowindex+"'>Modify</a> &nbsp;<a id='agentView' class='btn btn-warning' href='#' index='"+rowindex+"'>View</a></p></tr>";
				
				$("#agentTBody").append(appendTxt);	
				rowindex = ++rowindex;
		});
		
	
		
});

var agentrules = {
		rules : {
			terminalID : { required : true }, 
			branch : { required : true },
			zone : { required : true },
			physicalpremis : { required : true },
			agentbankacnumber : { required : true },
			numberofoutlets : { required : true },
			comercialActivity : { required : true },
			dob : { required : true },
			registrationDate : { required : true }
		},		
		messages : {
			terminalID : { 	required : "Please Select Terminal ID."  },
			branch : { 	required : "Please Select Branch."  },
			zone : { 	required : "Please Select Zone."  },
			physicalpremis : { 	required : "Please Physical permis."  },
			agentbankacnumber : { 	required : "Please Enter Agent Bank account Number"  } ,
			numberofoutlets : { 	required : "Please Enter Number of Outlets"  } ,
			comercialActivity : { 	required : "Please Enter CoercialActivity."  } ,
						dob : { 	required : "Please Enter DOB."  } ,
			registrationDate : { 	required : "Please Enter Registration Date."  } 

		} 
	};

var list = "dob,registrationDate".split(",");
var datepickerOptions = {
			showTime: false,
			showHour: false,
			showMinute: false,
  		dateFormat:'dd/mm/yy',
  		alwaysSetTime: false,
		changeMonth: true,
		changeYear: true
};	
	  
 $(function(){ 
	
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	}); 
 }); 
$(document).ready(function() {

	var mydata ='${responseJSON.TERMINAL_ID}';

	if(mydata != '') {
    	var json = jQuery.parseJSON(mydata);
    	$.each(json, function(i, v) {
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    $('#terminalID').append(options);
    	});
    	 	
	}	
	
	var branchdata ='${responseJSON.BRANCH_DETAILS}';

	if(branchdata != '') {
    	var json = jQuery.parseJSON(branchdata);
    	$.each(json, function(i, v) {
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    $('#branch').append(options);
    	});
    	 	
	}	
	
	var zonedata ='${responseJSON.ZONE_DETAILS}';

	if(zonedata != '') {
    	var json1 = jQuery.parseJSON(zonedata);
    	$.each(json1, function(i, v) {
    	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);    
    	    $('#zone').append(options);
    	});
    	 	
	}	
	
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
	    
$("#agentRegistration").hide();
	   
}); 

$('#announcement-create').live('click', function () { 
	
		$("#agentRegistration").show();
	
});


$('#btn-submit').live('click', function () { 
	if($('#form1').validate(agentrules)){
  $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/InsertAgentDetails.action';
	$("#form1").submit(); 
	return true; 
	} else {
		return false;
	} 
});




$(document).on('click','a',function(event) {
	var v_id=$(this).attr('id');
	var disabled_status= $(this).attr('disabled'); 
	var queryString = '?'; 
	var v_action = "NO";
	
	 
	var index1 = $(this).attr('index');  
	var parentId =$(this).parent().closest('tbody').attr('id'); 
	var searchTrRows = parentId+" tr"; 
	var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
			 
 	if(disabled_status == undefined) {  
		  if (v_id ==  "agent-modify" 
				|| v_id ==  "agentView" )
				 { 
 			 
			 // Anchor Tag ID Should Be Equal To TR OF Index
			$(searchTdRow).each(function(indexTd) {  
				 if (indexTd == 1) {
					 terminalid =$(this).text();
				 }
			});  
			 
			queryString += 'terminalid='+terminalid;  
			if(v_id ==  "agent-modify") {  
				v_action = "agentModifyAct";
 			}else if(v_id == "agentView"){
				v_action = "agentRegistrationView";
			}
		
			
		} 
			
	} else {
	
		// No Rights To Access The Link 
	}
	
	if(v_action != "NO") {
		$("#form1")[0].action="<%=ctxstr%>/<%=appName %>/"+v_action+".action"+queryString;
		$("#form1").submit();
	}
});  
</script>

</head> 
<body>
	<form name="form1" id="form1" method="post" action=""> 
			<div id="content" class="span10"> 
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="#">Agent registration</a></li>
				
				</ul>
			</div>
			
			<div class="box-content" id="top-layer-anchor">
				 <div>
					<a href="#" class="btn btn-success" id="announcement-create">
						<i class="icon-note icon-white"></i>Create New Agent Registration</a> &nbsp; 
				 </div>	
			</div>	
			<div class="row-fluid sortable" id="agentRegistration"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Agent Registration
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

							</div>
						</div>

						<div id="primaryDetails" class="box-content">
							<fieldset>   
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd">
										<td width="20%"><strong><label for="Terminal ID">Terminal ID<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="terminalID" name="terminalID" class="chosen-select"
											    required="required" >
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Branch">Branch<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="branch" name="branch" class="chosen-select"
											    required="required" >
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Zone">Zone<font color="red">*</font></label></strong></td>
										<td width="30%">
										<select id="zone" name="zone" class="chosen-select"
											    required="required" >
														<option value="">Select</option>
										</select>
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="odd">
										<td width="20%"><strong><label for="Physical premis">Physical premis<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="physicalpremis" type="text" class="field" id="physicalpremis" value=""  />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Agent Bank Account Number">Agent Bank Account Number<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="agentbankacnumber" type="text" class="field" id="agentbankacnumber" value=""  />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Number of Outlets">Number of Outlets<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="numberofoutlets" type="text" class="field" id="numberofoutlets" value=""  />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Comercial Activity">Comercial Activity<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="comercialActivity" type="text" class="field" id="comercialActivity" value=""  />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Date of Birth">Date of Birth<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="dob" type="text" class="field" id="dob" value=""  />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									<tr class="even">
										<td width="20%"><strong><label for="Registration Date">Registration Date<font color="red">*</font></label></strong></td>
										<td width="30%">	<input name="registrationDate" type="text" class="field" id="registrationDate" value=""  />						
										<span id="bankCode_err" class="errmsg"></span>
										</td>	
									</tr>
									
									 
								</table>
							 </fieldset> 
							 
							 <div class="form-actions">
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" name="btn-submit" id="btn-submit" class="btn btn-success" value="Create" />
								</div>  
							</div>
							 
						</div>
					</div> <!-- end of span -->
					</form>
					<form name="form2" id="form2" method="post" action="">		
			<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12">
					  <div class="box-header well" data-original-title>Agent Registration Details
						  <div class="box-icon"> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
						</div>
					  </div>  
						<div class="box-content"> 
							<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable"  	id="DataTables_Table_0">
				
								<thead>
									<tr>
										<th>S No</th>
										<th>TERMINALID</th>
										<th >BRANCH</th>
 										<th>ZONE</th>
										<th >PHYSICALPREMIS</th>
										<th >MAKER_ID</th>
										<th >MAKERDTTM</th>
										<th >Actions</th>
									</tr>
									
								</thead> 
								
								<tbody  id="agentTBody">
								</tbody>
							</table>
						</div> 
					</div> 
 			</div> 
				 </form>
			<span id="multi-row-data" name="multi-row-data" class="multi-row-data" style="display:none" ></span>
			<div class="form-actions"> 
				
				<input type="button" class="btn btn-info" name="btn-submit" id="btn-submit" value="Submit" />&nbsp;
				<input type="button" class="btn" name="btn-back" id="btn-back" value="Back" /> 
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div> 
	</div> 

</body>
</html>
