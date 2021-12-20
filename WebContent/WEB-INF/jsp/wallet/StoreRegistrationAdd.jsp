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

var rules = {
		   rules : {
			   storename : { required : true},
			   boname : { required : true},
			   addressLine : { required : true},
			   localGovernment : { required : true},
			   state : { required : true},
			   country : { required : true},
			   mobileno : { required : true},
			   area : { required : true}
			   
		   },  
		   messages : {
			   storename : { 
			   		required : "Please Enter Store Name."
				},
				boname : { 
			   		required : "Please Enter Store Manager Name."
				},
			   addressLine : { 
			   		required : "Please Enter Address."
				},
				localGovernment : { 
			   		required : "Please Select Local Government."
				},
				state : { 
			   		required : "Please Select State."
				},
				country : { 
			   		required : "Please Enter Country."
				},
				mobileno : { 
			   		required : "Please Enter Mobile Number."
				},
				area : { 
			   		required : "Please Enter Area."
				}
				
		   } 
		 };

$(function() {  
	
	 
  
	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){
		var queryString = "entity=${loginEntity}&method=validationpos&fname="+$('#userid').val();
		$.getJSON("postJson.action", queryString,function(data) {
			if((data.message)==0){
			$("#form1").validate(rules);
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/storeRegistrationConf.action'; 
			$("#form1").submit();
			return true;
			}else{
				$("#messages").text("Mobile Number "+$('#userid').val()+" pending for authorization");
			}
		});
	}); 
});

$(function(){
	
	if("${responseJSON.status}"=="Active"){
		
		$("#status1").html("<div class='label label-success' >Active</div>");
	}else{
		$("#status1").html("<div class='label label-important' >Deactive</div>");
	}
	
	
	var queryString1 = "sid=STATE";
	
	$.getJSON("branchcodeajx.action", queryString1, function(data) {
			if(data.region!=""){
				var mydata=data.region;
  			var mydata1=mydata.split(":");
  
   			$.each(mydata1, function(i, v) {
   				
   				var options = $('<option/>', {value: mydata.split(":")[i], text: (mydata.split(":")[i]).split("-")[1]}).attr('data-id',i);
   				
   				$('#state').append(options);
   				$('#state').trigger("liszt:updated");
   			});
   			
   			
  		} 
 	});
	
	
	
	$('#state').on('change', function() {
		//alert($('#state').val());
		 var queryString1 = "sid=STATESEARCH&serialNumber="+($('#state').val()).split("-")[0];
		 /* $('#state').val(($('#state').val()).split("-")[1]); */
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

<style type="">
.label {
    padding: 1px 4px 2px;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
    width: 40px;
}
</style>
</head> 
<body>
<form name="form1" id="form1" method="post"> 
		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> Store Management <span class="divider"> &gt;&gt; </span> </li>
					   <li> <a href="#">Create Wallet Agent Store</a>  </li> 
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
					<i class="icon-edit"></i>Wallet Agent Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>  
				<div class="box-content">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">  
							<tr class="even" id="accno" >
							<td width="25%"><label for="From Date"><strong>Core Bank Account Number</strong></label></td>
							<td width="25%">${responseJSON.accountno}<input type="hidden" name="accountno"  id="accountno"   value="${responseJSON.accountno}"   /></td>
							<td width="25%"><strong>User Id</strong></td>
							<td width="25%">${responseJSON.userid}
							<input type="hidden" name="userid"  id="userid"   value="${responseJSON.userid}"   />
							</td>
							</tr>
							<tr class="even" id="accno" >
							<td width="25%"><label for="From Date"><strong>Wallet Account Number</strong></label></td>
							<td width="25%">${responseJSON.walletaccountno}<input type="hidden" name="walletaccountno"  id="walletaccountno"   value="${responseJSON.walletaccountno}"   /></td>
							<td ><strong>Agent Id</strong></td>
							<td >${responseJSON.id}
							<input type="hidden" name="id"  id="id"   value="${responseJSON.id}"   />
							</td>
							</tr>
					 	 <tr class="even">
							<td width="25%"><label for="From Date"><strong>Name</strong></label></td>
							<td width="25%">${responseJSON.fullname}<input type="hidden" name="fullname"  id="fullname"   value="${responseJSON.fullname}"   /></td>
							<td><label for="Product"><strong>Status</strong></label></td>
							<td > <div id="status1"></div><input type="hidden" name="status"  id="status"   value="${responseJSON.status}"   />  </td>
							</tr> 
									
				 </table>
				</fieldset> 
				<div class="row-fluid sortable">
						<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Store Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
									</div>
								</div>

								<div id="communicationDetails" class="box-content">
									<fieldset>
										<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
										   <tr class="odd">
										   <td width="25%"><label for="Address Line 1"><strong>Store Name<font color="red">*</font></strong></label></td>
												<td width="25%">
												<input  type="text"   name="storename" id="storename" class="field" value="" />
												</td>
										   <td width="25%"><label for="Address Line 1"><strong>Store Id</strong></label></td>
												<td width="25%">${responseJSON.storeid}
												<input  type="hidden"   name="storeid" id="storeid" class="field" value="${responseJSON.storeid}" /></td>
												
										     	
										   
												
											</tr>
											
										
										  

									</table>
								</fieldset>
							</div>
						</div>
				</div> 
				
				 <div class="row-fluid sortable">
						<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Store Communication Details
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
												<td width="25%"><label for="Nationality"><strong>Store Manager Name<font color="red">*</font></strong></label></td>
												<td width="25%"><input type="text" class="field" name="boname" id="boname"   /></td>
												<td width="25%"><label for="Nationality"><strong>Email</strong></label></td>
												<td width="25%"><input type="text" class="field" name="email" id="email"   /></td>
											</tr>

										   <tr class="odd">
										   <td><label for="Nationality"><strong>Mobile Number<font color="red">*</font></strong></label></td>
												<td><input type="text" name="isocode" id="isocode" style="width:25px;" value="234" disabled />
												<input type="text" class="field" name="mobileno" id="mobileno" maxlength="10" style="width:172px;"  /></td>
										   <td width="25%"><label for="Address Line 1"><strong>Address Line<font color="red">*</font></strong></label></td>
												<td width="25%"><input  type="text"  maxlength="50" name="addressLine" id="addressLine" class="field" value="${responseJSON.address}" /></td>
												
											</tr>
											<tr class="even">
											<td><label for="Nationality"><strong>Area<font color="red">*</font></strong></label></td>
												<td><input type="text" class="field" name="area" id="area"   /></td>
											
											<td><label for="State"><strong>State<font color="red">*</font></strong></label></td>
												<td> 
												
												<select id="state" name="state" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
															</select> 
										
												
												</td>	
												
												
											</tr>
											<tr class="even">
											
											<td width="25%"><label for="Local Government"><strong>Local Government<font color="red">*</font></strong></label></td>
												<td width="25%" >
												
												<select id="localGovernment" name="localGovernment" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
															</select> 
															
										
												</td>
											
												
												<td><label for="Nationality"><strong>Country<font color="red">*</font></strong></label></td>
												<td><input type="text" class="field" name="country" id="country"  value="${responseJSON.country}" /></td>
											</tr>
											<tr class="even">
												<td><label for="Nationality"><strong>Latitude</strong></label></td>
												<td><input type="text" class="field" name="latitude" id="latitude"   /></td>
												<td><label for="Nationality"><strong>Longitude</strong></label></td>
												<td><input type="text" class="field" name="longitude" id="longitude"   /></td>
											</tr>
											

									</table>
								</fieldset>
							</div>
						</div>
				</div> 
				
				
				
				
				
				</div>  
				
	  </div>
	  </div> 
	  
			
	
		<div >
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">&nbsp;Home </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Submit</a>					 
		</div> 
	</div> 	 
 </form>

</body> 
</html>