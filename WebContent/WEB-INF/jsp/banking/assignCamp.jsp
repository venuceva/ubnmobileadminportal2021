<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString();%>
<%@taglib uri="/struts-tags" prefix="s"%> 

<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
input#abbreviation{text-transform:uppercase};

</style>
<s:set value="responseJSON" var="respData"/>
 <SCRIPT type="text/javascript">
 
 

 var list = "fromdate,todate,pushdate".split(",");
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
 $(function(){
	 
	
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	});

	 console.log(getToday());
	$('#fromdate').val(getToday());
	$('#todate').val(getToday());
	$('#pushdate').val(getToday());

 });

 function setStringCond(str) {
 	var strArr = str.split("\,");

 	var changedString = "";
 	for(var i=0;i<strArr.length;i++) {
 			if(i == strArr.length ) changedString+=  "'"+strArr.split('-')[0]+"'";
 			else changedString+= "'"+strArr.split('-')[0] +"',";
 	}

 	return	changedString;
 }
 
var rowCount1=0;
var remarkRules = {
		
			rules : { 
				chooseName : { required : true },
				cmpMessage : { required : true },
				fromdate : { required : true },
				todate : { required : true }
			},		
			messages : { 
				chooseName : { 	required : "Please Select Assign Template To."	},
				cmpMessage : { required : "Please Select Channel." },
				fromdate : { required : "Please Select From Date." },
				todate : { required : "Please Select To Date." }
					} 
		};
		
var remarkRules1 = {
		
		rules : { 
			pushdate : { required : true },
			hours : { required : true },
			minites : { required : true }
		},		
		messages : { 
			pushdate : { 	required : "Please Select Date"	},
			hours : { required : "Please Select Hours." },
			minites : { required : "Please Select Time." }
				} 
	};
	
$(document).ready(function(){
	
	for(j=0;j<=23;j++){
		
		if(j<=9){
		j="0"+j;
		}
		
		options = $('<option/>', {value: j, text: j }).attr('data-id',j);
	    $('#hours').append(options);
	}
	
	for(i=0;i<=59;i++){
		
		if(i<=9){
		i="0"+i;
		}
		
		options = $('<option/>', {value: i, text: i }).attr('data-id',i);
	    $('#minites').append(options);
	}
	 
	
	
	$("#18").show();
	$("#19").show();
		$('#chooseName').on('change', function() {
			
			var selector = $("#chooseName").val();

			if(selector=="prd_name"){
				$("#12").show();
				$("#13").show();
				$("#14").hide();
				$("#15").hide();
				$("#16").hide();
				$("#17").hide();
				$("#18").hide();
				$("#19").hide();
			
				$("#campaignFilter").val(selector);
			}else if(selector=="cust_id"){
				$("#12").hide();
				$("#13").hide();
				$("#14").hide();
				$("#15").hide();
				$("#16").show();
				$("#17").show();
				$("#18").hide();
				$("#19").hide();
				$("#campaignFilter").val(selector);
			
			}else{
				$("#12").hide();
				$("#13").hide();
				$("#14").show();
				$("#15").show();
				$("#16").hide();
				$("#17").hide();
				$("#18").hide();
				$("#19").hide();
				$("#campaignFilter").val(selector);
			}
		}); 
		
	//$("#rmrk").append("${template}");
	var json = '${resJSON.ACTIONLIST}';

	var json = jQuery.parseJSON(json);
	 var options = "";
	$.each(json, function(i, v) {
	      options = $('<option/>', {value: v.VALUE, text: v.TEXT }).attr('data-id',i);
	    $('#productName').append(options);
	}); 
	
	$('#filter').live('change', function(){
		var filter=$('#filter').val();
		$("#rmrk").append(filter);
		
	});

});


$(document).ready(function(){   
 
 	$('#btn-submit').live('click',function() {  
 		var message = $("#rmrk").val();
 		
 		$("#datetime").val($("#pushdate").val()+" "+$("#hours").val()+":"+$("#minites").val());

		//$('#cmpMessage').val(message);
		
		
		/* var vv="" 
			 if ($('#SMS').is(":checked"))
			 {
				 vv=vv+"SMS,";
			 }
			 if ($('#EMAIL').is(":checked"))
			 {
				 vv=vv+"EMAIL,";
			 }
			 if ($('#NOTIFICATION').is(":checked"))
			 {
				 vv=vv+"NOTIFICATION,";
			 }
			 $("#cmpMessage").val(vv); */
			
	
 	       var finalData = "";
 		 $("#error_dlno").text(''); 
		if($("#msgtype").val()=="NOTIFICATION"){
			$("#form1").validate(remarkRules);
		}
		if($("#msgtype").val()=="PUSHNOTIFICATION"){
			$("#form1").validate(remarkRules1);
		}
		
			if ($("#form1").valid()==true)
				{
				 $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/assignCampDetailsinsertAct.action";
				 $("#form1").submit();
				} 
	}); 
	
    $('#btn-Cancel').live('click',function() {  
		
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/campaignmgntact.action';		
		
		$("#form1").submit();		
	}); 
	
});


$(function() {
	 
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

function notification(val){
	
	
	if(val=="PUSHNOTIFICATION"){
		$("#tr-assignto").hide();
		$("#tr-channel").hide();
		$("#tr-fromdate").hide();
		$("#tr-fushfromdate").show();
		$("#msgtype").val("PUSHNOTIFICATION");
	}
	if(val=="NOTIFICATION"){
		$("#tr-assignto").show();
		$("#tr-channel").show();
		$("#tr-fromdate").show();
		$("#tr-fushfromdate").hide();
		$("#msgtype").val("NOTIFICATION");
	}
}


</script> 


</head> 
<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
	  <div id="content" class="span10"> 
			 
		    <div> 
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="campaignmgntact.action">Campaign Management</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Assign Campaign Management</a></li>
				</ul>
			</div>  
			
			<table height="3">
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
					 <i class="icon-edit"></i>Assign Campaign Management
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						
					</div>
				</div>
							
			<div class="box-content">
				<fieldset>

				<table width="950"  border="0" cellpadding="5" cellspacing="1"
					class="table table-striped table-bordered bootstrap-datatable " >
					  <tr >
					  	    <td><label for="Template Name"><strong>Template Name</strong></label></td>
							<td><input name="templateName" id="templateName" readonly type="text" class="field" required=true maxlength="50" value="${tempId}"/></td>
					  </tr>
					  <tr > 
							<td width="15%"><label for="Remarks1" valign="middle"><strong>Template Description</strong></label></td>
							<td><textarea disabled style="margin: 0px 0px 9px; width: 712px; height: 77px;"  name="rmrk" id="rmrk" >${template}</textarea><div id="errors" class="errors"></div></td> 
					</tr> 
				</table>
				</fieldset>
			</div>
		</div>
		</div>
 				 	
            
  				


 <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Campaign Settings
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  
		  		 
				<div id="remarksInformation1" class="box-content"> 								
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable " > 	
						<tr>
						<td width="25%" ><strong>Message Type</strong></td>
						<td  width="25%" >
						<input type="radio" id="NOTIFICATION" name="NOTIFICATION" value="NOTIFICATION" onclick="notification(this.value)" checked>Notification</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" id="NOTIFICATION" name="NOTIFICATION" value="PUSHNOTIFICATION" onclick="notification(this.value)" >Push Notification</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="hidden"  id="msgtype" name="msgtype" value="NOTIFICATION" />
						</td>
						<td width="25%"></td>
						<td width="25%"></td>
						
						</tr>								  
							<tr id="tr-assignto"> 
									<td style="width:25%"><label for="Campaign Filter"><strong>Assign Template To <font color="red">*</font></strong></label></td>
								<td style="width:25%">
									<select id="chooseName" name="chooseName" class="chosen-select" data-placeholder="Choose User Designation..." style="width: 220px;" required=true  >
										<option value="">Select</option>
										<option value="prd_name">Product Name</option>
										<option value="mob_no">Mobile Number</option>
										<option value="cust_id">Customer ID</option>
									</select>
								</td> 
									
							
								<td id='12' style="display:none" style="width:25%"><label for="Product Name"><strong>Product Name<font color="red">*</font></strong></label></td>
								<td id='13'  style="display:none" style="width:25%"><select id="productName" name="productName" class="chosen-select" data-placeholder="Choose User Designation..." style="width: 220px;" required=true  >
										<option value="">Select</option></select></td> 
						 		<td id='14'  style="display:none" style="width:25%"><label for="Mobile Number"><strong>Mobile Number<font color="red">*</font></strong></label></td>
						 		<td id='15'  style="display:none" style="width:25%">+234<input name="mobileNumber" id="mobileNumber"  type="text" class="field" required=true /></td>
						 		<td id='16'  style="display:none" style="width:25%"><label for="Mobile Number"><strong>Customer ID<font color="red">*</font></strong></label></td>
						 		<td id='17'  style="display:none" style="width:25%"><input name="customerid" id="customerid"  type="text" class="field" required=true /></td>
						 		<td id='18' style="width:25%"></td>
						 		<td id='19'style="width:25%" ></td>
							</tr>
							<tr class="even" id="tr-channel" >
						 		<td style="width:25%"><label for="Mobile Number"><strong>Channel<font color="red">*</font></strong></label></td>
						 		<td style="width:25%" colspan="3">
							 		<!-- <input type="checkbox" id="SMS" name="SMS" value="SMS" >SMS</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 							 		<input type="checkbox" id="EMAIL" name="EMAIL" value="EMAIL" >EMAIL</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 		<input type="checkbox" id="NOTIFICATION" name="NOTIFICATION" value="NOTIFICATION" >NOTIFICATION</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 		<input  type="hidden" name="cmpMessage" id="cmpMessage"   />  -->
							 		<input type="hidden" name="campaignFilter"  id="campaignFilter" />
							 		
							 		<select id="cmpMessage" name="cmpMessage" class="chosen-select" data-placeholder="Choose User Designation..." style="width: 220px;" required=true  >
										<option value="">Select</option>
										<option value="ALL">ALL</option>
										<option value="USSD">USSD</option>
										<option value="MOBILE">MOBILE</option>
									</select>
							 		
							 		
						 		</td>
						 		
						 		
						 		
							</tr>
							<tr id="tr-fromdate" >
								<td> <label for="From Date"><strong>From Date</strong></label></strong></td>
								<td class="fromDateTd"><strong><input type="text"   id="fromdate" name="fromdate"  /></strong></td>
								<td> <label for="To Date"><strong>To Date</strong></label></strong></td>
								<td class="toDateTd"><strong> <input type="text"  id="todate" name="todate"  /></strong></td>
							</tr>
							<tr id="tr-fushfromdate" style="display:none">
								<td> <label for="From Date"><strong>Date</strong></label></strong></td>
								<td class="fromDateTd"><strong><input type="text"   id="pushdate" name="pushdate"  /></strong></td>
								<td><strong>Time</strong></td>
								<td>
								<select  id="hours" name="hours"  class="chosen-select" data-placeholder="Choose User Designation..." style="width: 90px;" required=true >
								
								</select>
								
								<select  id="minites" name="minites"  class="chosen-select" data-placeholder="Choose User Designation..." style="width: 90px;" required=true >
								
								</select>
								<input type="hidden"   id="datetime" name="datetime"  />
								</td>
							</tr>
						</table>
				</div>		
			</div>
</div>	


    	<div class="form-actions" >
	         <input type="button" class="btn btn-success"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>&nbsp;
	         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input> &nbsp;
       </div>  
 </div>  
 </form> 
 
  <script src="${pageContext.request.contextPath}/js/autosize.js"></script>
  <script type="text/javascript">
 
$(function(){
	
	autosize(document.querySelectorAll('textarea'));

	});	  
</script>
</body> 
</html>