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
var acctRules = {
		   rules : {
			   nationality : { required : true},
			   addressLine : { required : true},
			   localGovernment : { required : true},
			   stated : { required : true},
			   country : { required : true},
			   telephone : { required: true },
			   dob : { required: false },
			   gender : { required: true },
			   agnbranch : { required: true },
		   },  
		   messages : {
			   nationality : { 
			   		required : "Please enter Nationality."
				},
				addressLine : { 
			   		required : "Please enter Address."
				},
				localGovernment : { 
			   		required : "Please Select Local Government."
				},
				stated : { 
			   		required : "Please Select State."
				},
				country : { 
			   		required : "Please enter Country."
				},
				telephone : { 
			   		required : "Please enter Mobile Number."
				},
				dob : { 
			   		required : "Please Select Date Of Birth."
				},
				gender : { 
			   		required : "Please Select Gender."
				},
				agnbranch : { 
			   		required : "Please Select Branch."
				}
		   } 
		 };
		
	/* 	var subrules = {
				   rules : {
					   telephone : { required: true }
				   },  
				   messages : {
					   
						telephone : { 
					   		required : "Please Input Mobile Number."
						}
				   } 
				 };
		 */
 
$(function() {   
	
/* 	$("#dialog").dialog({
			autoOpen: false,
			modal: false,

	      
   }); */
	
   
  /*  $('#telco').val('<s:property value="accBean.telco" />');
   $('#telco').trigger("liszt:updated");

   $('#langugae').val('<s:property value="accBean.langugae" />');
   $('#langugae').trigger("liszt:updated");
    */
   
  
		
	// add multiple select / deselect functionality
	$("#select-all").click(function () {
		$("#error_dlno").text("");
		$('.center-chk').attr('checked', this.checked);
	});

	// if all checkbox are selected, check the selectall checkbox
	// and viceversa
	$(".center-chk").click(function(){
		$("#error_dlno").text("");
		if($(".center-chk").length == $(".center-chk:checked").length) {
			$("#select-all").attr("checked", "checked");
		} else {
			$("#select-all").removeAttr("checked");
		}
	});
	
	
	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/walletregistration.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){ 
		var finalData = "";
		 var allVals = [];
		 
		//$('#billerMsg').text('');
		//$('#error_dlno').text(''); 
		//$("#form1").validate(subrules);
		
		
		/* var queryString = "method=verifyMobile&mobile="+ $('#telephone').val();	
		$.getJSON("postJson.action", queryString,function(data) { 
			
			v_message = data.language;
			var v_cusid=v_message.split("-");
			var ccode=$('#customercode').val();
					
				
			if(v_message!=' ' && ccode!=v_cusid[0].trim()){
				
				alert("The Mobile Number Exists for Below Customer \n \n"+v_message +" \n \n Please Use Another");
				
			}
			else { */
		
		$("#form1").validate(acctRules);
		 if($("#form1").valid() ) {  
		//alert($("#form1").valid());
			
if($("#product1").val()==""){
	$("#errors").text('Please select Agent Product');
}else{
	var aledinagency=$("#aledinagency").val();
	var subaledinagency=$("#subaledinagency").val();
	if("<s:property value='accBean.supercriteria' />"=="UNION_BANK_CUSTOMER" && $("#staffid").val()==""){
			$("#errors").text('Please Enter RM Code');
		
	}else if("<s:property value='accBean.supercriteria' />"=="ALEDIN_AGENCY" && aledinagency1==""){
		$("#errors").text('Please Select Agent Type');
		
	}else if("<s:property value='accBean.supercriteria' />"=="ALEDIN_AGENCY" && aledinagency1=="SUB_AGENT" && subaledinagency==""){
		$("#errors").text('Please Select Agent');
		
	}else{
				 $("#dialog").dialog({
				      buttons : {
				        "Confirm" : function() { 
				        	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/waletfetchServiceconf.action';
							$("#form1").submit();
				        },
				        "Cancel" : function() {
				           $(this).dialog("close");
				        }
				      }
				    });
				  
				  //  $("#dialog").dialog("open");  
				 
				 return true; 
	}
}  
			 
		 } else {
			 //alert("in else");
			 return false;
		 }
		
			/* } 
		});*/
	});
});	



$(function() { 
	
	
	
	if("<s:property value='accBean.supercriteria' />"=="UNION_BANK_CUSTOMER"){
		$("#rmtbl").css({"display":""});
		$("#rmtbl1").css({"display":"none"});
		$("#rmtbl2").css({"display":"none"});
		$("#rmtbl3").css({"display":"none"});
	}
	
	if("<s:property value='accBean.supercriteria' />"=="ALEDIN_AGENCY"){
		$("#rmtbl").css({"display":"none"});
		$("#rmtbl1").css({"display":""});
	}
	
	 if($('#apptype').val()=="AGENTWALLET"){
	    	
	    	$("#superadmin").css({"display":""});
	    	$("#accno").css({"display":""});
	    }
	 
	 
	 $('#aledinagency1').on('change', function() {
    	 // alert( this.value );
    	 $("#rmtbl2").css({"display":"none"});
    	 $("#rmtbl3").css({"display":"none"});
    	 if(this.value=="SUB_AGENT"){
    		 $("#rmtbl2").css({"display":""}); 
    		 $("#rmtbl3").css({"display":""});
    	 }
    	 $('#aledinagency').val($('#aledinagency1').val()); 
    	});
	
	
	 var config = {
		      '.chosen-select'           : {},
		      '.chosen-select-deselect'  : {allow_single_deselect:true},
		      '.chosen-select-no-single' : {disable_search_threshold:10},
		      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		      '.chosen-select-width'     : {width:"95%"}
		    };
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    }
		    
		    if("<s:property value='accBean.supercriteria' />"=="ALEDIN_AGENCY"){	
				var queryString1 = "sid=AGENCY";
				
				$.getJSON("superagentajx.action", queryString1, function(data) {
				if(data.region!=""){
				//	alert(data.region);
					var mydata=data.region;
						//var mydata=(data.region).substring(5,(data.region).length);
						var mydata1=mydata.split(":");
				
						$.each(mydata1, function(i, v) {
							var options = $('<option/>', {value: (mydata.split(":")[i]).split("@")[0], text: (mydata.split(":")[i]).split("@")[1]}).attr('data-id',i);
							$('#subaledinagency').append(options);
							$('#subaledinagency').trigger("liszt:updated");
						});
						
						
					} 
				}); 
		    }
		    
		    $('#subaledinagency').on('change', function() {
		    	 // alert( this.value ); 
		    	  $('#aaname').text((this.value).split("-")[2]);
		    	  $('#aamobileno').text((this.value).split("-")[1]);
		    	  $('#aacustid').text((this.value).split("-")[0]);
		    	  
		    	  $('#agencyname').val((this.value).split("-")[2]);
		    	  $('#agencymobileno').val((this.value).split("-")[1]);
		    	  $('#agencycustid').val((this.value).split("-")[0]);
		    	  
		    	}); 
		    
		    
		    $('#product1').on('change', function() {
		    	 // alert( this.value ); 
		    	  $('#desc').text((this.value).split("-")[1]);
		    	  $('#product').val((this.value).split("-")[0]);
		    	  $('#prodesc').val((this.value).split("-")[1]);
		    	});
		    
		    
		    var queryString = "sid=SUPERADMIN";
    		
	    	$.getJSON("superagentajx.action", queryString, function(data) {
	 			if(data.region!=""){
	 			//alert(data.region);
	 				var mydata=data.region;
	      			//var mydata=(data.region).substring(5,(data.region).length);
	      			var mydata1=mydata.split(":");
	      
	       			$.each(mydata1, function(i, v) {
	       				if("<s:property value='accBean.supercriteria' />"==((mydata.split(":")[i]).split("@")[0]).split("-")[0]){
	       					//alert((mydata.split(":")[i]).split("@")[0]);
							var options = $('<option/>', {value: (mydata.split(":")[i]).split("@")[0], text: (mydata.split(":")[i]).split("@")[1]}).attr('data-id',i);
		       				
		       				$('#product1').append(options);
		       				$('#product1').trigger("liszt:updated");
	       				}
	       					
	       				
	       			});
	       			
	       			
	      		} 
	     	}); 
	  //alert($('#product').val());  	
	    	
});

var list = "dob".split(",");
var datepickerOptions = {
				showTime: true,
				showHour: true,
				showMinute: true,
	  		dateFormat:'dd-mm-yy',
	  		alwaysSetTime: false,
	  		yearRange: '1910:2020',
			changeMonth: true,
			changeYear: true
};

$(function() {
	$(list).each(function(i,val){
		$('.'+val).datepicker(datepickerOptions);
	});
	
	var queryString = "sid=BRANCH";
	
	$.getJSON("branchcodeajx.action", queryString, function(data) {
			if(data.region!=""){
				var mydata=data.region;
  			var mydata1=mydata.split(":");
  
   			$.each(mydata1, function(i, v) {
   				
   				var options = $('<option/>', {value: mydata.split(":")[i], text: (mydata.split(":")[i]).split("@")[3]}).attr('data-id',i);
   				
   				$('#agnbranch').append(options);
   				$('#agnbranch').trigger("liszt:updated");
   			});
   			
   			
  		} 
 	}); 
	
	
	var queryString1 = "sid=STATE";
	
	$.getJSON("branchcodeajx.action", queryString1, function(data) {
			if(data.region!=""){
				var mydata=data.region;
  			var mydata1=mydata.split(":");
  
   			$.each(mydata1, function(i, v) {
   				
   				var options = $('<option/>', {value: mydata.split(":")[i], text: (mydata.split(":")[i]).split("-")[1]}).attr('data-id',i);
   				
   				$('#stated').append(options);
   				$('#stated').trigger("liszt:updated");
   			});
   			
   			
  		} 
 	}); 
	
	
	$('#agnbranch').on('change', function() {
   	
		//alert($('#agnbranch').val());
		
		$('#agbranch').val(($('#agnbranch').val()).split("@")[3]);
		
		$('#clusterid').text(($('#agnbranch').val()).split("@")[0]);
		$('#cluster').val(($('#agnbranch').val()).split("@")[0]);
		
		$('#area').text(($('#agnbranch').val()).split("@")[1]);
		$('#idnumber').val(($('#agnbranch').val()).split("@")[1]);
		
		
   	});
	
	
	$('#stated').on('change', function() {
		//alert($('#state').val());
		 var queryString1 = "sid=STATESEARCH&serialNumber="+($('#stated').val()).split("-")[0];
		 $('#state').val(($('#stated').val()).split("-")[1]);
		$.getJSON("branchcodeajx.action", queryString1, function(data) {
				if(data.region!=""){
					var mydata=data.region;
	  			var mydata1=mydata.split(":");
	  			
	  			
	  			$('#localGovernment').empty();
				$('#localGovernment').trigger("liszt:updated");
				
	   			$.each(mydata1, function(i, v) {
	   				
	   				var options = $('<option/>', {value: mydata.split(":")[i], text: mydata.split(":")[i]}).attr('data-id',i);
	   				
	   				$('#localGovernment').append(options);
	   				$('#localGovernment').trigger("liszt:updated");
	   			});
	   			
	   			
	  		} 
	 	});  
	});
	
	
});

function funa(v){
	$('#gender').val(v);
}
	 
</script>

</head> 

<body>

		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Wallet Customer Registration</a>  </li> 
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
<form name="form1" id="form1" method="post"> 
		<div class="row-fluid sortable"> 
			<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Customer Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
					
				<div class="box-content">
					<fieldset> 
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details"> 
						<tr class="even" id="accno" style="display:none">
							<td width="25%"><label for="From Date"><strong>Core Bank Account Number</strong></label></td>
							<td width="25%"><s:property value='accBean.accountno' /><input type="hidden" name="accountno"  id="accountno"   value="<s:property value='accBean.accountno' />"   /></td>
							<td  width="25%"><label for="From Date"><strong>Branch Code</strong></label></td>
							<td  width="25%">
							<s:property value='accBean.branchcode' /> <input type="hidden" name="branchcode"  id="branchcode"   value="<s:property value='accBean.branchcode' />"   />  </td>
							</tr>   
						<tr class="even">
							<td width="25%"><label for="From Date"><strong>Full Name</strong></label></td>
							<td width="25%"><s:property value='accBean.fullname' />
							<input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   /></td>
							<td></td>
						    <td></td>
							
							</tr>
						<tr class="even">
						<td  ><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><%-- <s:property value="accBean.dob"/> --%>
							<input type="text" maxlength="10"  class="dob" id="dob" name="dob"  readonly="readonly" />
							</td>
						
							<td ><label for="From Date"><strong>Email ID</strong></label></td>
							<td ><input type="text" name="email"  id="email"   value="<s:property value='accBean.email' />"   />  </td>
							  
						</tr>
						<tr class="even">
						
							
						   <td><label for="To Date"><strong>Mobile Number</strong></label> </td>
					       <td ><s:property value='accBean.telephone' />
					    
								<input type="hidden" value='<s:property value='accBean.telephone' />' class="field" maxlength="13" name="telephone" id="telephone"  />
 							
						     
						   </td> 
						     <td><strong>Gender<font color="red">*</font></strong></td>
						      <td>
						      <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'Male':'Male','Female':'Female'}" 
							         name="gender" 
							         id="gender"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
						    
						      </td>
 						</tr>
 						<tr class="even" id="superadmin" name="superadmin" style="display:none">
							<td ><label for="Product"><strong>Agent Product<font color="red">*</font></strong></label></td>
							<td >
							<select id="product1" name="product1" class="chosen-select-width" >
							 <option value="">Select</option>
							</select>
							</td>
							 <td><label for="Description"><strong>Product</strong></label></td>
					       <td><div id="desc"></div>
					       </td>  
						</tr>
						
						<tr class="even" id="rmtbl" style="display:none">
						
							
						   <td><label for="To Date"><strong>RM Code<font color="red">*</font></strong></label> </td>
					       <td >
					    
								<input type="text"  class="field" maxlength="13" name="staffid" id="staffid"  />
 							
						     
						   </td> 
						     <td>
						     
						     
						     </td>
						     <td></td>
 						</tr>
 						<tr class="even" id="rmtbl1" style="display:none">
						
							
						   <td><label for="To Date"><strong>Select Agency Type</strong></label> </td>
					       <td >
					    
								 <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'AGENT':'Agent','SUB_AGENT':'Sub Agent'}" 
							         name="aledinagency1" 
							         id="aledinagency1"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
 							<input type="hidden" value="AGENT" name="aledinagency" id="aledinagency" />
						     
						   </td> 
						     <td></td>
						     <td></td>
 						</tr>
 						<tr class="even" id="rmtbl2" style="display:none">
						
							
						   
						     <td>
						     <label for="To Date"><strong>Select Aledin Agency</strong></label> 
						     
						     </td>
						     <td>
						     
						     <select id="subaledinagency" name="subaledinagency" class="chosen-select-width" >
							 <option value="">Select</option>
							</select>
						     
						    
						     
						     </td>
						     <td><label for="To Date"><strong>Aledin Agency Name</strong></label>  </td>
					       <td ><div id="aaname"></div>
					       <input type="hidden"  class="field"  name="agencyname" id="agencyname"  />
					       </td> 
 						</tr>
 						<tr class="even" id="rmtbl3" style="display:none">
						
							
						   
						     <td>
						     <label for="To Date"><strong>Aledin Agency Mobile No</strong></label> 
						     
						     </td>
						     <td><div id="aamobileno"></div>
						      <input type="hidden"  class="field"  name="agencymobileno" id="agencymobileno"  />
						     </td>
						     <td><label for="To Date"><strong>Aledin Agency Customer Id</strong></label>  </td>
					       <td ><div id="aacustid"></div>
					       <input type="hidden"  class="field"  name="agencycustid" id="agencycustid"  />
					       </td> 
 						</tr>
 												
				 </table>
				 
				 <input type="hidden" value='<s:property value='accBean.apptype' />' name="apptype" id="apptype"  />
				  
				 
				 <div class="row-fluid sortable">
						<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Communication Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
									</div>
								</div>

								<div id="communicationDetails" class="box-content">
									<fieldset>
										<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
											
											
										   <tr class="even">
										   		
												
											   
												 <td width="25%"><label for="Address Line 1"><strong>Address<font color="red">*</font></strong></label></td>
												<td width="25%">
												<textarea rows="4" cols="50" name="addressLine" id="addressLine" >
												</textarea></td>
												 <td><label for="Nationality"><strong>Nationality<font color="red">*</font></strong></label></td>
												<td><%-- <s:property value="accBean.nationality"/> --%>
												<input type="text" name="nationality" id="nationality" class="field"     required='true' value="<s:property value="accBean.nationality"/>" />
	
												</td>
										   </tr>
										    
										    <tr class="odd">
										        <td width="25%"><label for="IDNumber"><strong>Branch<font color="red">*</font></strong></label>	</td>
												<td width="25%">
												<select id="agnbranch" name="agnbranch" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
															</select>
															<input name="agbranch" type="hidden" class="field" id="agbranch"  />
												</td>
												<td><label for="Local Government"><strong>Cluster</strong></label></td>
												<td><div id="clusterid"></div>
												<input name="cluster" type="hidden" class="field" id="cluster"  /></td>
												</td>
											</tr>

										   <tr class="odd">
										   <td width="25%"><label for="IDNumber"><strong>Area</strong></label>	</td>
												<td width="25%"><div id="area"></div>
												<input name="idnumber" type="hidden" class="field" id="idnumber"  /></td>
										  
												<td><label for="State"><strong>State<font color="red">*</font></strong></label></td>
												<td>
												
												<select id="stated" name="stated" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
															</select> 
											 <input name="state" type="hidden" class="field" id="state"  />
														 </td>
										    
											
											</tr>
										   <tr class="odd">
												
														 
												<td><label for="Local Government"><strong>Local Government<font color="red">*</font></strong></label></td>
												<td> 
												 <select id="localGovernment" name="localGovernment" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
															</select> 
												 
													 </td>
										
												<td><label for="Nationality"><strong>Country<font color="red">*</font></strong></label></td>
												<td><%-- <s:property value="accBean.country"/> --%>
											<input type="text" name="country" id="country" class="field"     required='true' value="<s:property value="accBean.country"/>" />
												
												</td>
												
										   </tr>
										   
										 
										  

									</table>
								</fieldset>
							</div>
						</div>
				</div>
				 
				 
				 
				  
						        <input type="hidden" name="institute"  id="institute"   value="<s:property value='institute' />"   />
								<input type="hidden" name="multiData"  id="multiData"   value="<s:property value='accBean.multiData' />"  />
								<input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
								<input type="hidden" name="langugae"  id="langugae"   value="English"  />
								<%-- <input type="hidden" name="superadmin"  id="superadmin"   value="<s:property value='accBean.supercriteria' />"  /> --%>
								<input type="hidden"  class="field" value="<s:property value='accBean.supercriteria' />"  name="supercriteria" id="supercriteria"  />
				</fieldset> 
				</div>
				</div> 
					<input type="hidden"  name="product" id="product"  value="<s:property value='accBean.product' />" />
				 	<input type="hidden"  name="prodesc" id="prodesc"  value="<s:property value='accBean.prodesc' />" />
				 	
				
		</div> 	
</form>	

<div id="dialog" title="Confirmation Required" style="display:none">
		   Proceed ? <div id="dia1"></div><font color="red"><div id="dia2"></div></font>
		</div>
		<div > 
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">Back </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Submit</a>	
			<span id ="error_dlno" class="errors"></span>	 
		</div> 
		
	</div> 
 <script type="text/javascript">
$(function(){
	
	
	
	$('#accountNumber').live('keypress',function(){
		//console.log($(this).length);
		if($(this).length == 0){
			$('#billerMsg').text('');
		}
	});
	
	var config = {
      '.chosen-select'           : {},
      '.chosen-select-deselect'  : {allow_single_deselect:true},
      '.chosen-select-no-single' : {disable_search_threshold:10},
      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chosen-select-width'     : {width:"95%"}
    };
	
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }  
});  
</script>
</body> 
</html>