
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String ctxstr = request.getContextPath();
%>
<%
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>

<style type="text/css">
.errors {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}

input#abbreviation {
	text-transform: uppercase
}
;
</style>
<SCRIPT type="text/javascript"> 

var billerrules = {
		rules : {
 			abbreviation : { required : true },
			name : { required : true }
		},		
		messages : {
			abbreviation : { 
				required : "Please Enter Abbreviation." 
			  }, 
			name : { 
					required : "Please Enter Name." 
				}
		},
		errorElement: 'label'
	};


$(document).ready(function(){ 
	
	
	
	$('#agencyType').on('change', function (e) {
    	var valueSelected = this.value;
    	//alert(valueSelected);
    	
    	
    	
    	if((this.value)==""){
    		
	    	document.getElementById("Transaction").length=1;
    	}else{
    	if(valueSelected=="Biller Service"){
    		valueSelected="Biller_Service";
    	}
    	
    	
    	var queryString = "entity=${loginentity}&type="+valueSelected;

    	document.getElementById("Transaction").length=1;
    	$.getJSON("retrivebillerServise.action", queryString, function(data) {
 			if(data.region!=""){
 				var mydata=data.region;
      			var mydata1=mydata.split(":");
      
       			$.each(mydata1, function(i, v) {
       				var options = $('<option/>', {value: (mydata.split(":")[i]), text: (mydata.split(":")[i]).split('-')[1]}).attr('data-id',i);
       				
       				$('#Transaction').append(options);
       				$('#Transaction').trigger("liszt:updated");
       			});
       			
       			
      		} 
     	}); 
    	}	
    	
    });
	
	$('#Transaction').on('change', function (e) {
    	//$('#abbreviation').val(this.value);
    	
    	var queryString = "entity=${loginentity}&type=BILLERID";

    	$.getJSON("retrivebillerServise.action", queryString, function(data) {
 			if(data.region!=""){
 				var mydata=data.region;
       			$('#billerCode').val("BG"+mydata);
      		} 
     	}); 
    	
	});
	
	$('#btn-submit').live('click',function() { 
 		if($("#form1").valid()) {
 			$('#abbreviation').val($('#abbreviation').val().toUpperCase());
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName%>/billerRegistrationSubmitAct.action";
			$("#form1").submit();	
			return true;
		} else {
 			return false;
		} 	
	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName%>/newPayBillAct.action";
		$("#form1").submit();
	});

});


</SCRIPT>
</head>
<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
		<div id="content" class="span10">

			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider">&gt;&gt; </span></li>
					<li><a href="#">Biller Management</a></li>
				</ul>
			</div>

			<table height="3">
				<tr>
					<td colspan="3">
						<div class="messages" id="messages">
							<s:actionmessage />
						</div>
						<div class="errors" id="errors">
							<s:actionerror />
						</div>
					</td>
				</tr>
			</table>

			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Biller Category Creation Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>

						</div>
					</div>

					<div class="box-content">
						<fieldset>

							<table width="98%" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr>
									<td width="20%"><label for="Abbreviation"><strong>Select Services<font
												color="red">*</font></strong></label></td>
									<td width="30%">
									<s:select cssClass="chosen-select" headerKey=""
											headerValue="Select"
											list="#{'Biller Service':'Biller Service','Other':'Other'}"
											name="agencyType" value="billerBean.agencyType"
											id="agencyType" requiredLabel="true" theme="simple"
											data-placeholder="Choose Agency Type..." />
									</td>
									<td width="20%"><label for="Abbreviation"><strong>Services Category<font
												color="red">*</font></strong></label></td>
									<td width="30%">
									<select id="Transaction" name="Transaction" class="chosen-select-width" >
							 			<option value="">Select</option>
									</select>
									</td>
								</tr>
								<tr>
									<td width="20%"><label for="Abbreviation"><strong>Biller Category Name<font
												color="red">*</font></strong></label></td>
									<td width="30%"><input type="text" name="abbreviation"
										id="abbreviation"
										value="<s:property value='billerBean.abbreviation' />"
										required=true /></td>
									<td width="20%"><label for="Code"><strong>Biller Category Id</strong></label></td>
									<td width="30%"><input type="text" name="billerCode" id="billerCode"  readonly/></td>
								</tr>
								<tr>
									<td><label for="Name"><strong>Description<font
												color="red">*</font></strong></label></td>
									<td ><input type="text" name="name" id="name"
										required=true value="<s:property value='billerBean.name' />"
										width="30%" /></td>
		  							<td></td>
									<td></td>
								</tr>
							</table>
						</fieldset>
					</div>
				</div>
			</div>

		<%-- 	<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Contact Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>

						</div>
					</div>

					<div class="box-content">
						<fieldset>

							<table width="98%" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr>
									<td width="20%"><label for="Address"><strong>Address<font
												color="red">*</font></strong></label></td>
									<td width="30%"><input type="text" name="address"
										id="address" required=true
										value="<s:property value='billerBean.address' />" /></td>
									<td width="20%">&nbsp;</td>
									<td width="30%">&nbsp;</td>
								</tr>
								<tr>
									<td><label for="Telephone"><strong>Telephone<font
												color="red">*</font></strong></label></td>
									<td colspan=3><input type="text" name="telephone"
										id="telephone" required=true
										value="<s:property value='billerBean.telephone' />" /></td>

								</tr>
								<tr>
									<td><label for="Contact Person"><strong>Contact
												Person<font color="red">*</font>
										</strong></label></td>
									<td colspan=3><input type="text" name="contactPerson"
										id="contactPerson" required=true
										value="<s:property value='billerBean.contactPerson' />" />
									</td>
								</tr>
								<tr>
									<td><label for="Email"><strong>Email<font
												color="red">*</font></strong></label></td>
									<td colspan=3><input type="text" name="email" id="email"
										required=true value="<s:property value='billerBean.email' />" />
									</td>
								</tr>
							</table>
						</fieldset>
					</div>
				</div>
			</div> 

			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Commissions
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>
						</div>
					</div>

					<div class="box-content">
						<fieldset>
							<table width="98%" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr>
									<td width="20%"><label for="Commission Type"><strong>Commission
												Type<font color="red">*</font>
										</strong></label></td>
									<td width="30%"><s:select cssClass="chosen-select"
											headerKey="" headerValue="Select"
											list="#{'Rate':'Rate','Amount':'Amount'}"
											name="commissionType" value="billerBean.commissionType"
											id="commissionType" requiredLabel="true" theme="simple"
											data-placeholder="Choose Commission Type..." />
										&nbsp; <label id="commission-id" class="errors"></label></td>
									<td width="20%">&nbsp;</td>
									<td width="30%">&nbsp;</td>
								</tr>
								<tr id="comm-amt">
									<td><label for="Amount"><strong>Amount<font
												color="red">*</font></strong></label></td>
									<td colspan="3"><input type="text" name="amount"
										id="amount" required=true
										value="<s:property value='billerBean.amount' />" /></td>
								</tr>
								<tr id="comm-rate">
									<td><label for="Name"><strong>Rate<font
												color="red">*</font></strong></label></td>
									<td colspan="3"><input type="text" name="rate" id="rate"
										required=true value="<s:property value='billerBean.rate' />" />
									</td>
								</tr>

							</table>
						</fieldset>
					</div>
				</div>
			</div> --%>
			<div class="form-actions">
				<input type="button" class="btn btn-primary" name="btn-submit"
					id="btn-submit" value="Submit" width="100"></input> &nbsp;<input
					type="button" class="btn " name="btn-Cancel" id="btn-Cancel"
					value="Back" width="100"></input>
			</div>

		</div>
	</form>
	<script type="text/javascript">
		$(function() {

			var config = {
				'.chosen-select' : {},
				'.chosen-select-deselect' : {
					allow_single_deselect : true
				},
				'.chosen-select-no-single' : {
					disable_search_threshold : 10
				},
				'.chosen-select-no-results' : {
					no_results_text : 'Oops, nothing found!'
				},
				'.chosen-select-width' : {
					width : "95%"
				}
			};

			for ( var selector in config) {
				$(selector).chosen(config[selector]);
			}

			if ($('select#commissionType option:selected').val() == 'Rate') {
				$('#comm-amt').hide();
				$('#comm-rate').show();
			} else if ($('select#commissionType option:selected').val() == 'Amount') {
				$('#comm-amt').show();
				$('#comm-rate').hide();
			} else {
				$('#comm-amt').hide();
				$('#comm-rate').hide();
			}

		});
	</script>
</body>
</html>
