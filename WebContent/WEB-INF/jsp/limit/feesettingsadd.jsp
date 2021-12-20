
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  

<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>


<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js" type="text/javascript"></script>

<script type="text/javascript" >



var createfeeRules = {
		rules : {
		    feeCodeval  			: { required : true , minlength : 6 , maxlength : 6}, 
			feeDescriptionval			: { required : true },
			FeeTransaction			: { required : true },
		    FeeFrequency 	: { required : true },
		    channel 	: { required : true },
		    operators 	: { required : true },
			FlatPercentile 			: { required : true },
			Criteria 			: { required : true },
			FromValue 			: { required : true,number: true },
			FPValue 			: { required : true,number: true },
			ToValue 			: { required : true,number: true },
			Agent 			: { required : true,number: true },
			Ceva 			: { required : true,number: true },
			Bank 			: { required : true,number: true },
			VAT				: { required : true,number: true },
			SuperAgent 			: { required : true,number: true },
			subAgent				: { required : true,number: true },
			thirdparty				: { required : true,number: true }
		
	   },  
	   messages : {
		   feeCode : { 
		         required : "Please Enter FeeCode."
		       },  
			   feeDescription : {
				 required :"Please Enter FeeDescription."
			   },
			   
		       FeeTransaction : { 
		         required : "Please Select FeeTransaction."
		       },  
			   FeeFrequency : {
				 required :"Please Select FeeFrequency."
			   },  
			   FlatPercentile : { 
		         required : "Please Select FlatPercentile."
		       }, 
		       channel : { 
			         required : "Please Select Channel."
			   },
			   operators : { 
				         required : "Please Select Operators."
				},
			   Criteria : {
				 required :"Please Select Criteria."
			   }, 
			   FromValue : { 
		         required : "Please Enter FromValue."
		       },
			    FPValue : { 
		         required : "Please Enter Value."
		       },
			    ToValue : { 
		         required : "Please Enter ToValue."
		       },
		       Agent : { 
			         required : "Please Enter Agent."
			       },
			       Ceva : { 
				     required : "Please Enter Ceva."
				},
			Bank : { 
					 required : "Please Enter Bank."
				 },
			VAT : { 
					 required : "Please Enter VAT."
			 },
			 SuperAgent : { 
			     required : "Please Enter Super Agent."
			},
			 subAgent : { 
				 required : "Please Enter Sub Agent."
		 },thirdparty : { 
			 required : "Please Enter Third Party."
		 }
			   
  }
};

$(function() {
	
	document.getElementById("channel").length=1;
	var queryString = "entity=${loginentity}&product="+$('#productcode').val()+"&type=CHANNEL-FEE";

	
	$.getJSON("retriveServise.action", queryString, function(data) {
			if(data.region!=""){
				var mydata=data.region;
  			var mydata1=mydata.split(":");
  
   			$.each(mydata1, function(i, v) {
   				var options = $('<option/>', {value: (mydata.split(":")[i]), text: (mydata.split(":")[i])}).attr('data-id',i);
   				
   				$('#channel').append(options);
   				$('#channel').trigger("liszt:updated");
   			});
   			
   			
  		}else{
  			$('#channel').append("");
				$('#channel').trigger("liszt:updated");
  		} 
 	}); 
	
	
 $('#channel').on('change', function (e) {
		 
		 document.getElementById("operators").length=1;
	    	var queryString = "entity=${loginentity}&product="+$('#channel').val()+"&type=OPERATOR";

	    	
	    	$.getJSON("retriveServise.action", queryString, function(data) {
	 			if(data.region!=""){
	 				var mydata=data.region;
	      			var mydata1=mydata.split(":");
	      
	       			$.each(mydata1, function(i, v) {
	       				var options = $('<option/>', {value: (mydata.split(":")[i]), text: (mydata.split(":")[i])}).attr('data-id',i);
	       				
	       				$('#operators').append(options);
	       				$('#operators').trigger("liszt:updated");
	       			});
	       			
	       			
	      		}else{
	      			$('#operators').append("");
 				$('#operators').trigger("liszt:updated");
	      		} 
	     	}); 
	    	
	    	
	    	document.getElementById("FeeTransaction").length=1;
	    	var queryString = "entity=${loginentity}&product="+$('#productcode').val()+"-"+$('#channel').val()+"&type=FEE";

	    	
	    	$.getJSON("retriveServise.action", queryString, function(data) {
	 			if(data.region!=""){
	 				var mydata=data.region;
	      			var mydata1=mydata.split(":");
	      
	       			$.each(mydata1, function(i, v) {
	       				var options = $('<option/>', {value: (mydata.split(":")[i]), text: (mydata.split(":")[i])}).attr('data-id',i);
	       				
	       				$('#FeeTransaction').append(options);
	       				$('#FeeTransaction').trigger("liszt:updated");
	       			});
	       			
	       			
	      		}else{
	      			$('#FeeTransaction').append("");
    				$('#FeeTransaction').trigger("liszt:updated");
	      		} 
	     	}); 
	    	
	    	
	    }); 
	 
	 
	
});


var feehederdata="channel|selecttext#operators|selecttext#FeeTransaction|selecttext#FeeTransaction|selectvalue#FeeFrequency|selectvalue#FeeFrequency|selecttext#FlatPercentile|selecttext#FlatPercentile|selectvalue#FPValue|text#Criteria|selecttext#Criteria|selectvalue#FromValue|text#ToValue|text#Agent|text#subAgent|text#Ceva|text#Bank|text#SuperAgent|text#VAT|text#thirdparty|text";
var feejsonObj = [];



function buildfeetable(jsonArray)
{

	$("#Fee_tbody_data").empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
		var data = JSON.stringify(jsonObject);
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=Transaction >" + i + "</td>";
			htmlString = htmlString + "<td id='channel' name=channel >" + jsonObject.channel + "</td>";
			htmlString = htmlString + "<td id='operators' name=operators >" + jsonObject.operators + "</td>";
			htmlString = htmlString + "<td id='FeeTransaction' name=FeeTransaction >" + jsonObject.FeeTransaction + "</td>";
			htmlString = htmlString + "<td id='FeeFrequency' name=FeeFrequency >" + jsonObject.FeeFrequency + "</td>";	
			htmlString = htmlString + "<td id=FlatPercentile name=FlatPercentile >" + jsonObject.FlatPercentile + "</td>";
			htmlString = htmlString + "<td id=FPValue name=FPValue >" + jsonObject.FPValue + "</td>";
			htmlString = htmlString + "<td id=Criteria name=Criteria >" + jsonObject.Criteria + "</td>";
			htmlString = htmlString + "<td id=FromValue name=FromValue >" + jsonObject.FromValue + "</td>";
			htmlString = htmlString + "<td id=ToValue name=ToValue >" + jsonObject.ToValue + "</td>";
			htmlString = htmlString + "<td id=Agent name=Agent >" + jsonObject.Agent + "</td>";
			htmlString = htmlString + "<td id=subAgent name=subAgent >" + jsonObject.subAgent + "</td>";
			htmlString = htmlString + "<td id=Ceva name=Ceva >" + jsonObject.Ceva + "</td>";
			htmlString = htmlString + "<td id=Bank name=Bank >" + jsonObject.Bank + "</td>";
			htmlString = htmlString + "<td id=SuperAgent name=SuperAgent >" + jsonObject.SuperAgent + "</td>";
			htmlString = htmlString + "<td id=VAT name=VAT >" + jsonObject.VAT + "</td>";
			htmlString = htmlString + "<td id=thirdparty name=thirdparty >" + jsonObject.thirdparty + "</td>";
  			htmlString = htmlString + "<td id='' ><a class='btn btn-min btn-danger' href='#' id='DELETE' index="+i+" val="+i+" title='Delete' data-rel='tooltip' onclick='deletedata("+data+")'> <i class='icon-trash icon-white'></i></a></td>";
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$("#Fee_tbody_data").append(htmlString);

}



function deletedata(val){

	var finaljsonobj=[];

	var r = confirm("Do You Want To Remove?");
	
	if (r == true) {

	console.log("Final Value ["+val+"]");

	dataupdateval=val;
	
	var channel = dataupdateval.channel;
	var operators = dataupdateval.operators;
	var FeeTransaction  = dataupdateval.FeeTransaction;
	var FeeFrequency = dataupdateval.FeeFrequency;
	var FlatPercentile  = dataupdateval.FlatPercentile; 
	var FPValue = dataupdateval.FPValue;
	var Criteria  = dataupdateval.Criteria; 
	var FromValue = dataupdateval.FromValue;
	var ToValue  = dataupdateval.ToValue;
	var Agent = dataupdateval.Agent;
	var subAgent = dataupdateval.subAgent;
	var Ceva  = dataupdateval.Ceva; 
	var Bank = dataupdateval.Bank;
	var SuperAgent  = dataupdateval.SuperAgent; 
	var VAT  = dataupdateval.VAT; 
	var thirdparty = dataupdateval.thirdparty;
	
	
	$.each(feejsonObj, function(index,jsonObject){ 
		
		 
		var tabchannel = jsonObject.channel;
		var taboperators = jsonObject.operators;
		var tabFeeTransaction  = jsonObject.FeeTransaction;
		var tabFeeFrequency = jsonObject.FeeFrequency;
		var tabFlatPercentile  = jsonObject.FlatPercentile; 
		var tabFPValue = jsonObject.FPValue;
		var tabCriteria  = jsonObject.Criteria; 
		var tabFromValue = jsonObject.FromValue;
		var tabToValue  = jsonObject.ToValue;
		var tabAgent = jsonObject.Agent;
		var tabsubAgent = jsonObject.subAgent;
		var tabCeva  = jsonObject.Ceva; 
		var tabBank = jsonObject.Bank;
		var tabSuperAgent  = jsonObject.SuperAgent; 
		var tabVAT  = jsonObject.VAT; 
		var tabthirdparty  = jsonObject.thirdparty; 
		
	
		if((tabchannel==channel) && (taboperators==operators) && (tabFeeTransaction==FeeTransaction) && (tabFeeFrequency==FeeFrequency) && (tabFlatPercentile==FlatPercentile) && (tabFPValue==FPValue) && (tabCriteria==Criteria) && (tabFromValue==FromValue) && (tabToValue==ToValue) && (tabAgent==Agent) && (tabsubAgent==subAgent) &&(tabCeva==Ceva) && (tabBank==Bank) && (tabSuperAgent==SuperAgent) && (tabVAT==VAT) && (tabthirdparty==thirdparty))
		{
			
		}else{
			
			finaljsonobj.push(jsonObject);
		}
	
	});
	
	feejsonObj =[];
	feejsonObj = finaljsonobj;
	

	buildfeetable(feejsonObj);
		
	} 
	

}



$(function() {	
 
	
		
	$('#btn-fee-add').live('click', function () {
		
		console.log("Welcome to Fee program");
		
		$("#form2").validate(createfeeRules);
		
		if($("#form2").valid()) {
		
		var myval =  buildSingleRequestjson(feehederdata);
		var finalobj = jQuery.parseJSON(myval);
		
		var status = validateData(finalobj,feejsonObj,1);
		var total=parseFloat(finalobj.Agent)+parseFloat(finalobj.subAgent);
		total=parseFloat(total.toFixed(2))+parseFloat(finalobj.Ceva);
		total=parseFloat(total.toFixed(2))+parseFloat(finalobj.Bank);
		total=parseFloat(total.toFixed(2))+parseFloat(finalobj.VAT);
		total=parseFloat(total.toFixed(2))+parseFloat(finalobj.SuperAgent);
		total=parseFloat(total.toFixed(2))+parseFloat(finalobj.thirdparty);
		
		//var total=parseFloat(finalobj.Agent)+parseFloat(finalobj.subAgent)+parseFloat(finalobj.Ceva)+parseFloat(finalobj.Bank)+parseFloat(finalobj.VAT)+parseFloat(finalobj.SuperAgent)+parseFloat(finalobj.thirdparty);
		if(parseInt(finalobj.FromValue)>parseInt(finalobj.ToValue)){
			$("#errormsg").html("<font colour='red'>To Value should be greater than From Value</font>");
		}else if(total!=100 && finalobj.FlatPercentile=="P"){
			$("#errormsg").html("<font colour='red'>all commission should be equals to 100%</font>");
		}else if(total!=finalobj.FPValue && finalobj.FlatPercentile=="F"){
			$("#errormsg").html("<font colour='red'>all commission should be equals to Fee Amount</font>");
		}else if(parseInt(finalobj.FromValue)==0){
			$("#errormsg").html("<font colour='red'>From Value should be greater or equals to 1</font>");
		}else if(status){
			$("#errormsg").html("<font colour='red'>Transaction and Frequency between Criteria values already added </font>");
		}else {
		
		feejsonObj .push(finalobj);
		
		console.log("myval ["+feejsonObj +"]");
	
		buildfeetable(feejsonObj);
		$("#errormsg").html("");
		$("#finalbutton").show();
		
		$("#feeCodeval").prop('disabled',true);
		$("#feeDescriptionval").prop('disabled',true);
		
		$("#FeeTransaction").val('');
		$("#FeeFrequency").val('');
		$("#FlatPercentile").val('');
		$("#Criteria").val('');
		
	var listid = "FeeFrequency,FeeTransaction,FlatPercentile,Criteria,channel,operators".split(",");
		
		$(listid).each(function(index,val){
			$('#'+val).find('option').each(function( i, opt ) { 
				if( opt.value === '' ) {
					$(opt).attr('selected', 'selected');
					$('#'+val).trigger("liszt:updated");
				}
			}); 
		});
		
		
		$("#FPValue").val('');			
		$("#FromValue").val('');
		$("#ToValue").val('');
		$("#Agent").val('');
		$("#subAgent").val('');
		$("#Ceva").val('');
		$("#Bank").val('');
		$("#SuperAgent").val('');
		$("#VAT").val('');
		$("#thirdparty").val('');
		
		 return true;
		}
		} else {
		
				return false;
				
		} 
	
	});
		
		
		$('#btn-submit').live('click', function () {
			
			var feejsonstr = JSON.stringify(feejsonObj);
			$("#feefinaljson").val(feejsonstr);
			
			var feeCode = $("#feeCodeval").val();
			var feeDescription = $("#feeDescriptionval").val();
			
			$("#feeCode").val(feeCode);
			$("#feeDescription").val(feeDescription);
			var queryString = "entity=${loginEntity}&method=searchAuthPendinglf&fname="+$('#feeCode').val();
	 		
			$.getJSON("postJson.action", queryString,function(data) { 
				if(data.message=="SUCCESS"){
			
					$("#form3")[0].action='<%=request.getContextPath()%>/<%=appName %>/FeeconfirmAdd.action';
					$("#form3").submit();
					return true;
				}else{
					$('#messages').text("Fee Code "+$('#feeCode').val()+" "+data.message);
				}
			 });
			
		});
	});

function validateData(myval,myfinalobj,val)
{

	var status = false;
	
	var channel = myval.channel;
	var operators = myval.operators;
	var Transaction = myval.FeeTransaction;
	var Frequency = myval.FeeFrequency;
	var FromValue = myval.FromValue;
	var ToValue = myval.ToValue;

	
	$.each(myfinalobj, function(index,jsonObject)
	{ 
		var tabchannel = jsonObject.channel;
		var taboperators = jsonObject.operators;
		var tabTransaction = jsonObject.FeeTransaction;
			var tabFrequency  = jsonObject.FeeFrequency;
 			var tabFromValue  = jsonObject.FromValue;
 			var tabToValue  = jsonObject.ToValue;
 			
			
 			if((Transaction==tabTransaction && Frequency==tabFrequency && channel==tabchannel && operators==taboperators))
			{
				
				 if((parseInt(tabFromValue) <= parseInt(FromValue)) && (parseInt(tabToValue) >= parseInt(FromValue))){
					 status=true;	
				}
				
				
				/* if((parseInt(tabFromValue) < parseInt(FromValue)) && (parseInt(tabToValue) > parseInt(ToValue))){
					alert("2"); */
				
				/* } */
			}

	});
	
	return status;

} 

</script>
<s:set value="responseJSON" var="respData"/> 
</head>

<body>	
		<div id="content" class="span10"> 
		    <div>
					<ul class="breadcrumb">
					  <li><a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li><a href="binManagementAct.action">Fee Settings</a> <span class="divider"> &gt;&gt; </span></li>
					  <!-- <li><a href="#">Setting</a></li> -->
					</ul>
			</div>
			<div id="errormsg" class="errores"></div>
			       <table height="3">
						<tr>
							<td colspan="3">
								<div class="messages" id="messages"><s:actionmessage /></div>
								<div class="errors" id="errors"><s:actionerror /></div>
							</td>
						</tr>
					</table>
			
	
			<div class="row-fluid sortable"><!--/span--> 	
				<form name="form2" id="form2" method="post">
				
				<div class="box span12">  
				
				
				
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Fee Management
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>   
						
					<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
						<tr class="even">
								<td width="20%"><label for="Fee Code"><strong>Product Code</strong></label></td>
								<td width="30%"><s:property value='#respData.PRODUCT_CODE' /></span></td>
								 <td width="20%"><label for="Fee Description"><strong>Application</strong></label></td>
								 <td width="30%"><s:property value='#respData.APPLICATION' /></span> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Fee Code"><strong>Fee Code</strong></label></td>
								<td width="30%"><s:property value='#respData.FEE_CODE' />
								<input name="feeCodeval" type="hidden" maxlength ='6' id="feeCodeval" required="true" class="field"  value="<s:property value='#respData.FEE_CODE' />"  readonly /> <span id="bin_err" class="errmsg"></span></td>
								 <td width="20%"><label for="Fee Description"><strong>Fee Description</strong></label></td>
								 <td width="30%"><s:property value='#respData.FEE_DESC' />
								 <input name="feeDescriptionval"  type="hidden" maxlength ='25' id="feeDescriptionval" required="true"  class="field" value="<s:property value='#respData.FEE_DESC' />" /><span id="bin_err1" class="errmsg"></span> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Channel"><strong>Channel<font color="red">*</font></strong></label></td>
								<td width="30%"><select class="chosen-select" name="channel" 
										id="channel" >
										<option value="">Select</option>
								</select>
								</td>
								<td width="20%"><label for="Channel"><strong>Operators<font color="red">*</font></strong></label></td>
								<td width="30%"><select class="chosen-select" name="operators" 
										id="operators" >
										<option value="">Select</option>
								</select>
								</td>
							</tr>
						</table>
						<input type="hidden" id="productcode" name="productcode"  value="<s:property value='#respData.PRODUCT_CODE' />"  />
					<!-- 	<input type="hidden" id="channel" name="channel" value="ALL" /> -->
						
					</fieldset>
					
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="FeeTransaction"><strong>Transaction<font color="red">*</font></strong></label></td>
								<td width="30%">
								<select id="FeeTransaction" name="FeeTransaction" class="chosen-select-width" >
							 		<option value="">Select</option>
								</select>
								

								<span id="bin_err" class="errmsg"></span></td>
								 <td width="20%"><label for="FeeFrequency"><strong>Frequency<font color="red">*</font></strong></label></td>
								 <td width="30%">
								 
								 <s:select cssClass="chosen-select" 
										headerKey="" 
										headerValue="Select"
										list="#respData.FREQ_PERIOD" 
										name="FeeFrequency" 
										id="FeeFrequency" 
										requiredLabel="true" 
										theme="simple"
										data-placeholder="Choose Frequency Type..." 
										required="true" 
										 /> 
								 
								 <span id="bin_err1" class="errmsg"></span> 
								 </td>
							</tr>
							
							<tr class="even">
								<td width="20%"><label for="FlatPercentile"><strong>Flat/Percentile<font color="red">*</font></strong></label></td>
								<td width="30%"> <select name="FlatPercentile" id="FlatPercentile"  Class ="chosen-select" data-placeholder="Choose Flat/Percentile..." >
													<option value="">select</option>
													<option value="F" >Flat</option>
													<option value="P">Percentile</option>
								</select>
								<span id="bin_err" class="errmsg"></span>
								</td>
								 <td width="20%"><label for="FPValue"><strong>Value<font color="red">*</font></strong></label></td>
								 <td width="30%"><input name="FPValue" id="FPValue"  type="text" maxlength ='25'  required="true"  class="field" /><span id="bin_err1" class="errmsg"></span> </td>
							</tr>
							
							<tr class="even">
								<td width="20%"><label for="Criteria"><strong>Criteria<font color="red">*</font></strong></label></td>
								<td width="30%"> <select name="Criteria" id="Criteria" data-placeholder="Choose Criteria..."  Class ="chosen-select" >
													<option value="">select</option>
													<option value="C" >Count</option>
													<option value="A">Amount</option>
								</select>
								<span id="bin_err1" class="errmsg"></span></td>
								 <td width="20%"></td>
								<td width="20%"></td>
									</tr>
							
							<tr class="even">
							 <td width="20%"><label for="FromValue">
								 <strong>From Value<font color="red">*</font></strong></label></td>
								 <td width="30%"><input name="FromValue" id="FromValue"  type="text" maxlength ='25'  required="true"  class="field" /><span id="bin_err1" class="errmsg"></span></td>
							
								<td width="20%"><label for="To Value">
									<strong>To Value<font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="ToValue" id="ToValue"  type="text" maxlength ='25'  required="true"  class="field" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								 
							</tr>
							<tr class="even">
							<td width="20%"><label for="Agent">
									<strong>Super Agent commission <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="SuperAgent" id="SuperAgent"  type="text" maxlength ='25' value="0"   class="field" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								<td width="20%"><label for="Agent">
									<strong>Agent commission <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="Agent" id="Agent"  type="text" maxlength ='25'  required="true"  class="field" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								 
							</tr>
							
							<tr class="even">
							<td width="20%"><label for="Agent">
									<strong>Sub Agent commission <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="subAgent" id="subAgent"  type="text" maxlength ='25'  value="0"  class="field" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								<td width="20%"><label for="Ceva">
									<strong>Ceva commission <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="Ceva" id="Ceva"  type="text" maxlength ='25'  required="true"  class="field" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								 
							</tr>
							<tr class="even">
							<td width="20%"><label for="Bank">
									<strong>Bank commission  <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="Bank" id="Bank"  type="text" maxlength ='25'  required="true"  class="field" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								<td width="20%"><label for="Bank">
									<strong>VAT<font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="VAT" id="VAT"  type="text" maxlength ='25'  required="true"  class="field" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								 
							</tr>
							<tr class="even">
							<td width="20%"><label for="Bank">
									<strong>Third Party<font color="red">*</font></strong></label>
								</td>
								<td width="30%">
									<input name="thirdparty" id="thirdparty"  type="text" maxlength ='25'  required="true"  value="0" class="field" /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								<td width="20%"></td>
								<td width="30%"></td>
								 
							</tr>
							
						</table>
						
					</fieldset>
				
					
						<div class="form-actions">
				
							<a  class="btn btn-success" href="#" id="btn-fee-add"  >ADD</a>
							<!-- &nbsp;&nbsp; <a  class="btn btn-danger" href="#"  id="btn-cancel" >Cancel</a> -->
				
						</div>	
						
						<span id="multi-row" name="multi-row" style="display:none"> </span> 
					<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="documentTable" style="width: 100%;">
							  <thead>
									<tr>
										<th>Sno</th>
										<th >Channel</th>
										<th >Operator</th>
										<th>Transaction</th>
										<th>Frequency</th>
										<th>F/P</th>
										<th>F/P Value</th>
										<th>Criteria</th>
										<th>From Value</th>
										<th>To Value</th>
										<th>Agent</th>
										<th>Sub Agent</th>
										<th>Ceva</th>
										<th>Bank</th>
										<th>Super Agent</th>
										<th>VAT</th>
										<th>Third Party</th>
										<th>Action</th> 
									</tr>
							  </thead>    
							 <tbody id="Fee_tbody_data">
							
							 </tbody>
							 
						</table>
					</div>
					</div> 		
		
		
		</form>
		<!-- Fee Settings End -->
				
						
	<form name="form3" id="form3" method="post">
	 <input name="application"  type="hidden" maxlength ='25' id="application" required="true"  class="field" value="<s:property value='#respData.APPLICATION' />" />
 <input name="productcode" type="hidden" maxlength ='6' id="productcode" required="true" class="field"  value="<s:property value='#respData.PRODUCT_CODE' />"   />
 <input name="productdesc"  type="hidden"  id="productdesc" value=""  class="field" />
<input name="productcurr"  type="hidden" id="productcurr"  value=""  class="field" />
		
		<div class="form-actions" id="finalbutton" style="display:none;"> <!-- -->
				
				<a  class="btn btn-success" href="#" id="btn-submit" >Submit</a>
				&nbsp;&nbsp; <a  class="btn btn-danger" href="#"  id="btn-cancel" >Cancel</a>
		<input name="feefinaljson" id="feefinaljson"  type="hidden" />
		<input name="feeCode" type="hidden" maxlength ='6' id="feeCode" required="true" class="field"   />
		<input name="feeDescription"  type="hidden" maxlength ='25' id="feeDescription" required="true"  class="field" />
		
		</div>	
		
	
	</form> 		
  </div>	
  <script>
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
 </script>
 </div>

	
 
 
 
</body>
</html>
