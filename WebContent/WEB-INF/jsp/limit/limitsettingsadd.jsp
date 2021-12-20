
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

var createlmtRules = {
	      rules : {
		    limitCodeval  			: { required : true , minlength : 6 , maxlength : 6}, 
			Transaction			: { required : true },
			Frequency			: { required : true },
		    limitDescriptionval 	: { required : true },
			MaxCount 			: { required : true,digits: true },
			MinAmount 			: { required : true,digits: true },
			MaxAmount 			: { required : true,digits: true },
			channel 			: { required : true }
			
		
	   },  
	   messages : {
			   Transaction : { 
		         required : "Please Select Transaction."
		       },  
			   Frequency : {
				 required :"Please Select Frequency."
			   },
			   
		       limitCodeval : { 
		         required : "Please Enter Limit Code."
		       },  
			   limitDescriptionval : {
				 required :"Please Enter Limit Description."
			   },  
			   MaxCount : { 
		         required : "Please Enter MaxCount."
		       },  
			   MinAmount : {
				 required :"Please Enter MinAmount."
			   }, 
			   MaxAmount : { 
		         required : "Please Enter MaxAmount."
		       },
		       channel : { 
			         required : "Please Select Channel."
			   }
}
};





var headerdata="channel|selecttext#operators|selecttext#operator|selecttext#Transaction|selecttext#Transaction|selectvalue#Frequency|selecttext#Frequency|selectvalue#MaxCount|text#MinAmount|text#MaxAmount|text";
var jsonObj = [];


function buildtable(jsonArray)
{

	$("#tbody_data").empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
		var data = JSON.stringify(jsonObject);
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=Transaction >" + i + "</td>";
			htmlString = htmlString + "<td id='channel' name=channel >" + jsonObject.channel + "</td>";
			htmlString = htmlString + "<td id='operators' name=operators >" + jsonObject.operators + "</td>";
			htmlString = htmlString + "<td id='Transaction' name=Transaction >" + jsonObject.Transaction + "</td>";
			htmlString = htmlString + "<td id='Frequency' name=Frequency >" + jsonObject.Frequency + "</td>";	
			htmlString = htmlString + "<td id=MaxCount name=MaxCount >" + jsonObject.MaxCount + "</td>";
			htmlString = htmlString + "<td id=MinAmount name=MinAmount >" + jsonObject.MinAmount + "</td>";
			htmlString = htmlString + "<td id=MaxAmount name=MaxAmount >" + jsonObject.MaxAmount + "</td>";
			htmlString = htmlString + "<td id='' ><a class='btn btn-min btn-danger' href='#' id='DELETE' index="+i+" val="+i+" title='Delete' data-rel='tooltip' onclick='deletedata("+data+")'> <i class='icon-trash icon-white'></i></a></td>";
	    	htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$("#tbody_data").append(htmlString);

}

function deletedata(val){

	var finaljsonobj=[];

	var r = confirm("Do You Want To Remove?");
	
	if (r == true) {

	console.log("Final Value ["+val+"]");

	dataupdateval=val;
	
	var channel = dataupdateval.channel;
	var operators = dataupdateval.operators;
	var Transaction  = dataupdateval.Transaction;
	var Frequency = dataupdateval.Frequency;
	var MaxCount  = dataupdateval.MaxCount; 
	var MinAmount = dataupdateval.MinAmount;
	var MaxAmount  = dataupdateval.MaxAmount; 
	
	
	$.each(jsonObj, function(index,jsonObject){ 
		
		 
		var tabchannel = jsonObject.channel;
		var taboperators = jsonObject.operators;
		var tabTransaction  = jsonObject.Transaction;
		var tabFrequency = jsonObject.Frequency;
		var tabMaxCount = jsonObject.MaxCount; 
		var tabMinAmount = jsonObject.MinAmount;
		var tabMaxAmount = jsonObject.MaxAmount; 
		
	
		if((tabchannel==channel)  && (taboperators==operators) && (tabTransaction==Transaction) && (tabFrequency==Frequency) &&(tabMaxCount==MaxCount) && (tabMinAmount==MinAmount) &&(tabMaxAmount==MaxAmount))
		{
			
		}else{
			
			finaljsonobj.push(jsonObject);
		}
	
	});
	
	jsonObj =[];
	jsonObj = finaljsonobj;
	

	buildtable(jsonObj);
		
	} 
	

}

	
$(function() {
	
	
	document.getElementById("channel").length=1;
	    	var queryString = "entity=${loginentity}&product="+$('#productcode').val()+"&type=CHANNEL-LIMIT";

	    	document.getElementById("channel").length=1;
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
	    	
	    	
	    	document.getElementById("Transaction").length=1;
	    	var queryString = "entity=${loginentity}&product="+$('#productcode').val()+"-"+$('#channel').val()+"&type=LIMIT";

	    	
	    	$.getJSON("retriveServise.action", queryString, function(data) {
	 			if(data.region!=""){
	 				var mydata=data.region;
	      			var mydata1=mydata.split(":");
	      
	       			$.each(mydata1, function(i, v) {
	       				var options = $('<option/>', {value: (mydata.split(":")[i]), text: (mydata.split(":")[i])}).attr('data-id',i);
	       				
	       				$('#Transaction').append(options);
	       				$('#Transaction').trigger("liszt:updated");
	       			});
	       			
	       			
	      		}else{
	      			$('#Transaction').append("");
  				$('#Transaction').trigger("liszt:updated");
	      		} 
	     	}); 
	    	
	    	
	    }); 
	 


		$('#Frequency').on('change', function() {

			var val = this.value;
		
			console.log("Hellow World ["+val+"]");
			if(val=="Per Txn")
			{
				//console.log("yes");
				$("#MaxCount").val("1");
				$("#MaxCount").prop('disabled',true);
				
			}else{
				//console.log("no");
				$("#MaxCount").val("");
				$("#MaxCount").prop('disabled',false);
			}
			

		});
		
		
		
		
		$('#btn-add').live('click', function () {
			
			
			//console.log("Welcome to my program");
			
			$("#form1").validate(createlmtRules);
			
			if($("#form1").valid()) {
				
			var myval =  buildSingleRequestjson(headerdata);
			var finalobj = jQuery.parseJSON(myval);
			
			var status = validateData(finalobj,jsonObj,1); 
		
			
			if(status){
				
				$("#errormsg").html("<font colour='red'>Transaction and Frequency Already Added</font>");
			
			}else if(parseInt(finalobj.MinAmount)>parseInt(finalobj.MaxAmount)){
				$("#errormsg").html("<font colour='red'>maximum Amount should be greater than minimum Amount</font>");
			}else if(finalobj.Frequency=="Per Txn" && finalobj.MaxCount!="1"){
					$("#errormsg").html("<font colour='red'>Per Txn Frequency should be equal to 1 Count</font>");			
			}else {
				$("#errormsg").html("");
			jsonObj.push(finalobj);
			
			console.log("myval ["+jsonObj+"]");
		
			buildtable(jsonObj);
			$("#errormsg").html("");
			$("#finalbutton").show();
			
			$("#limitCodeval").prop('disabled', "true");
			$("#limitDescriptionval").prop('disabled', "true");
			
			
			$("#Frequency").val("");
			$("#Transaction").val("");
			
			var listid = "Frequency,Transaction,channel,operators".split(",");
			
			$(listid).each(function(index,val){
				$('#'+val).find('option').each(function( i, opt ) { 
					if( opt.value === '' ) {
						$(opt).attr('selected', 'selected');
						$('#'+val).trigger("liszt:updated");
					}
				}); 
			});
			
			
			$("#MinAmount").val('');
			$("#MaxAmount").val('');
			$("#MaxCount").val('');
			$("#MaxCount").prop('disabled',false);
			/* $('#channel').prop("disabled", "true"); */
			$('#Product').prop("disabled", "true");
			$('#channel').trigger("liszt:updated");
			$('#Product').trigger("liszt:updated");
			
			return true;
			}
			} else {
			
					return false;
					
			} 
		
		});
		
		$('#btn-submit').live('click', function () {
		
		
			var jsonstr = JSON.stringify(jsonObj);
			$("#finaljson").val(jsonstr);
			
			
			
			var limitCode = $("#limitCodeval").val();
			var limitDescription = $("#limitDescriptionval").val();
			
			
			
			$("#limitCode").val(limitCode);
			$("#limitDescription").val(limitDescription);
			
			
		//	console.log("limitCode ["+limitCode+"] limitDescription ["+limitDescription+"] feeCode ["+feeCode+"] feeDescription ["+feeDescription+"]");
		
		var queryString = "entity=${loginEntity}&method=searchAuthPendinglf&fname="+$('#limitCodeval').val();
  		
			$.getJSON("postJson.action", queryString,function(data) { 
				
				if(data.message=="SUCCESS"){
		
			$("#form3")[0].action='<%=request.getContextPath()%>/<%=appName %>/lmtConfirmActadd.action';
			$("#form3").submit();
			return true;
				}else{ 
			    	  
					$('#messages').text("Limit Code "+$('#limitCodeval').val()+" "+data.message);

				 }
			});
		});
		
	
	});
	
	
$('#btn-cancel').live('click', function () {
	$("#form3")[0].action='<%=request.getContextPath()%>/<%=appName %>/lmtConfirmActadd.action';
	$("#form3").submit();
	return true;
});

function validateData(myval,myfinalobj,val)
{

	var status = false;
	
	var Transaction = myval.Transaction;
	var Frequency = myval.Frequency;
	var channel = myval.channel;
	var operators = myval.operators;

	
	$.each(myfinalobj, function(index,jsonObject)
	{ 
			var tabTransaction = jsonObject.Transaction;
			var tabFrequency  = jsonObject.Frequency;
			var tabchannel  = jsonObject.channel;
			var taboperators  = jsonObject.operators;
			
			
			if((Transaction==tabTransaction && Frequency==tabFrequency && channel==tabchannel && operators==taboperators) )
			{
				status=true;
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
					  <li><a href="binManagementAct.action">Limit Settings</a> <span class="divider"> &gt;&gt; </span></li>
					  <!-- <li><a href="#">Setting</a></li> -->
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
	<div class="row-fluid sortable"><!--/span--> 		
	<form name="form1" id="form1" method="post">			
			 <div id="errormsg" class="errores"></div>	
				<div class="box span12">  
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Add Limit Settings 
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
								<td width="30%"><s:property value='#respData.PRODUCT_CODE' /></span>
								</td>
								 <td width="20%"><label for="Fee Description"><strong>Application</strong></label></td>
								 <td width="30%"><s:property value='#respData.APPLICATION' /></span> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Limit Code</strong></label></td>
								<td width="30%"><s:property value='#respData.LIMIT_CODE' />
								<input name="limitCodeval" type="hidden" maxlength ='6' id="limitCodeval" required="true" class="field"  value="<s:property value='#respData.LIMIT_CODE' />"  readonly/> <span id="bin_err" class="errmsg"></span></td>
								 <td width="20%"><label for="Limit Description"><strong>Limit Description</strong></label></td>
								 <td width="30%"><s:property value='#respData.LIMIT_DESC' />
								 <input name="limitDescriptionval"  type="hidden" maxlength ='25' id="limitDescriptionval" required="true"  class="field"  value="<s:property value='#respData.LIMIT_DESC' />"/><span id="bin_err1" class="errmsg"></span> </td>
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
						<!-- <input type="hidden" id="channel" name="channel" value="ALL" /> -->
					</fieldset>
					
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="Transaction"><strong>Transaction<font color="red">*</font></strong></label></td>
								<td width="30%">
								
							<select id="Transaction" name="Transaction" class="chosen-select-width" >
							 <option value="">Select</option>
							</select>
								<span id="bin_err" class="errmsg"></span></td>
								 <td width="20%"><label for="Frequency"><strong>Frequency<font color="red">*</font></strong></label></td>
								 <td width="30%">
								 
								 <s:select cssClass="chosen-select" 
										headerKey="" 
										headerValue="Select"
										list="#respData.FREQ_PERIOD" 
										name="Frequency" 
										id="Frequency" 
										requiredLabel="true" 
										theme="simple"
										data-placeholder="Choose Frequency Type..." 
										required="true" 
										 /> 
								 
								 <span id="bin_err1" class="errmsg"></span> 
								 </td>
							</tr>
							
							<tr class="even">
								<td width="20%"><label for="MaxCount"><strong>Max Count<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="MaxCount" id="MaxCount" type="text" maxlength ='6'  required="true" class="field"   /> <span id="bin_err" class="errmsg"></span></td>
								 <td width="20%"><label for="Min Amount"><strong>Min Amount <font color="red">*</font></strong></label></td>
								 <td width="30%"><input name="MinAmount" id="MinAmount"  type="text" maxlength ='25'    class="field" /><span id="bin_err1" class="errmsg"></span> </td>
							</tr>
							
							<tr class="even">
								<td width="20%"><label for="Max Amount"><strong>Max Amount<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="MaxAmount" id="MaxAmount" type="text" maxlength ='25'   class="field"   /> <span id="bin_err1" class="errmsg"></span></td>
								 <td width="20%"></td>
								 <td width="30%"></td>
							</tr>
							
						</table>
						
					</fieldset>
				
					
						<div class="form-actions">
				
							<a  class="btn btn-success" href="#" id="btn-add"  >ADD</a>
							
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
										<th>Max Count</th>
										<th>Min Amount</th>
										<th>Max Amount</th>
										<th>Action</th>
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 
							<%--  <s:iterator value="responseJSON['limitcodedetails2']" var="userGroups" status="userStatus"> 
								<s:if test="#userStatus.even == true" > 
									<tr class="even" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
								 </s:if>
								 <s:elseif test="#userStatus.odd == true">
      								<tr class="odd" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
   								 </s:elseif> 
									<td><s:property value="#userStatus.index+1" /></td>
									<!-- Iterating TD'S -->
									  <s:iterator value="#userGroups" status="status" > 
										<s:if test="#status.index == 2" >  
											<td> <s:property  value="value" /></td> 											
										</s:if>
										 <s:elseif test="#status.index == 3" >
											 <td ><s:property  value="value"  /></td>
										 </s:elseif> 
										 
 										 <s:elseif test="#status.index == 4" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 <s:elseif test="#status.index == 5" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 6" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 7" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  
									</s:iterator> 
									
 								  
							</s:iterator>    --%>
							 </tbody>
							 
						</table>
				
					</div>
			</div> 		
		
		</form>
				
				
		<!-- Fee Settings End -->
				
						
	<form name="form3" id="form3" method="post">
		 <input name="application"  type="hidden" maxlength ='25' id="application" required="true"  class="field" value="<s:property value='#respData.APPLICATION' />" />
 <input name="productcode" type="hidden" maxlength ='6' id="productcode" required="true" class="field"  value="<s:property value='#respData.PRODUCT_CODE' />"   />

		<div class="form-actions" id="finalbutton" style="display:none;"> <!-- -->
				
				<a  class="btn btn-success" href="#" id="btn-submit" >Submit</a>
				&nbsp;&nbsp; <a  class="btn btn-danger" href="#"  id="btn-cancel" >Cancel</a>
			<input name="finaljson" id="finaljson"  type="hidden" />
		<input name="limitCode" type="hidden" maxlength ='6' id="limitCode" required="true" class="field"   />
		<input name="limitDescription"  type="hidden" maxlength ='25' id="limitDescription" required="true"  class="field" />
		
		</div>	
		
	
	</form> 		

	</div>			
	
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
	  	 
		 
		  
		/* $('#plasticCode').val(ses); 
		$('#plasticCode').trigger("liszt:updated");  */
	});
 </script>
	
 
 
 
</body>
</html>
