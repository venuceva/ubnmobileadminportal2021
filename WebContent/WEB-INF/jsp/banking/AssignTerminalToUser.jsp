
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




<script type="text/javascript" >
$(document).ready(function() {
	$('#supervisor').change(function(){
		var valu = $(this).val();
		if(valu == ''){
			
		}else{
			$('#supervisior-id').text('');
		}
		
	});
	var mydata ='${responseJSON.USERS_LIST}';

	var json = jQuery.parseJSON(mydata);

	$.each(json, function(i, v) {
	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);
	    $('#selectUsers').append(options);
	});

	var mydata ='${responseJSON.ADMIN_TYPE_LIST}';

	json = jQuery.parseJSON(mydata);

	$.each(json, function(i, v) {
	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);
		$('#supervisor').append(options);
	});

	$.each(json, function(i, v) {
	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);
		$('#admin').append(options);
	});

	mydata='${responseJSON.exist_users}';
	json = jQuery.parseJSON(mydata);

	$.each(json, function(ind,e) {

		$("#selectUsers option[value]").each(function(index,val){
			//console.log($(this).attr('value') +"=="+ e.key.trim());

			if($(this).attr('value') == e.key.trim()) {
				$(this).attr("selected",true) ;
			}
		});
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

function getGenerateMerchantScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateMerchantAct.action';
	$("#form1").submit();
	return true;
}
//For Closing Select Box Error Messages_Start
$(document).on('change','select',function(event) { 

if($('#'+$(this).attr('id')).next('label').text().length > 0) {
 $('#'+$(this).attr('id')).next('label').text(''); 
 $('#'+$(this).attr('id')).next('label').remove();
}

});
//For Closing Select Box Error Messages_End


var assignUserToTerminalRules = {
   rules : {
	selectUsers : { required : true },
		supervisor : { required : true }/* ,
		admin : { required : true } */
   },
   messages : {
	   selectUsers : {
		      required : "Please Select User"
		 },
	supervisor : {
			required : "Please select Supervisor."
        }
		 /* ,
	admin : {
			required : "Please select Admin."
        } */
   }
};


function assignUserToTerminal(){

	var data=$("#supervisor option:selected").val();
var selectusers=$("#selectUsers option:selected").val();

/* 	if(data == '')
		{
		$('#supervisior-id').text('Please select Supervisor.');
		
		}
	
	 if(selectusers == '')
		{
			$('#select-user').text('Please select atleast One User.');
		}
	else{ */
		
	$("#form1").validate(assignUserToTerminalRules);
	var selectedUserText="";
	if($("#form1").valid()){
		var selectdata=$('#selectUsers').val();
		if(selectdata.length==0){
			alert("Please select atleast one user.");
			return false;
		}else{
			if(selectdata.length<6){
				$("#selectUsers option:selected").each(function () {
				   var $this = $(this);
				   if ($this.length) {
					var selText = $this.text();
					selectedUserText += "#"+selText;
				   }
				});

				selectedUserText=selectedUserText.slice(1);
				$("#selectedUserText").val(selectedUserText);
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/assignUserTerminalCfmScreenAct.action';
				$("#form1").submit();
				return true;
		}
		
			else
				{
				alert("Please select only five users ");
				return false;
				}
		}
	}
	
		else{
			return false;
	}
//	}
	/* 	}
	}); */
}

/* $(function(){ 
	$('#supervisor').live('change',function(){   
		
		
		$('#supervisior-id').text(''); 

		var val = $(this).val();
		if(val != 'select') {
			$('#supervisior-id').text('');
		
		} else {
			$('#supervisior-id').text('Please Select KRA Service.');
		} 
	});
	
}); */
/*
function errormessage()
{
	alert("hai");
}*/
</script>
<style type="text/css">
	label.error {
	   font-weight: bold;
	   color: red;
	   padding: 2px 8px;
	   margin-top: 2px;
	}
	div.errors {
	   font-weight: bold;
	   color: red;
	   padding: 2px 8px;
	   margin-top: 2px;
	}
</style>


</head>

<body>
	<form name="form1" id="form1" method="post" action="">
			<div id="content" class="span10">
			    <div>
						<ul class="breadcrumb">
						  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
						  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
						  <li><a href="#">Assign Users to the Terminal</a></li>
						</ul>
				</div>
				<div class="row-fluid sortable"><!--/span-->
					<div class="box span12">
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Terminal Information
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>
							<div class="box-content" id="terminalDetails">
								 <fieldset>
									 <table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
										<tr class="even">
											<td width="20%"><label  for="Merchant ID"><strong>Merchant ID</strong></label></td>
											<td width="30%"> ${responseJSON.merchantID}
												<input name="merchantID" type="hidden" id="merchantID" class="field" value=" ${responseJSON.merchantID}">
											</td>
											<td width="20%"><label  for="Store ID"><strong>Store ID</strong></label></td>
											<td width="30%"> ${responseJSON.storeId}
												<input name="storeId"  type="hidden" id="storeId" class="field"  value=" ${responseJSON.storeId}" readonly  >
											</td>
										</tr>
										<tr class="odd">
											<td><label  for="Terminal ID"><strong>Terminal ID</strong></label></td>
											<td colspan=3> ${responseJSON.terminalID}
												<input name="terminalID" type="hidden"  id="terminalID" class="field" value="${responseJSON.terminalID}"  maxlength="8">
											</td>
										</tr>

									</table>
								 </fieldset>
							</div>
						 `
							<div class="box-content"  id="userDetails">
								 <fieldset>
									 <table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
										<tr class="even">
											<td width="20%"><label  for="Select Users"><strong>Select Users<font color="red">*</font></strong></label></td>
											<td colspan=3  >
												<div align="left">
													<select multiple id="selectUsers" name="selectUsers"  required=true
														data-placeholder="Choose Users to Assign Terminals..."  class="chosen-select" style="width: 300px;">
													</select><label id="select-user" class="errors" ></label>
												</div>
											</td>
										</tr>
									   </table>
								</fieldset>
							</div>
							<div class="box-content"  id="userDetails">
								 <fieldset>
								   <table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
										<tr class="even">
											<td width="20%"><label  for="Supervisor">Supervisor<font color="red">*</font></strong></label></td>
											<td colspan=3>
												<select id="supervisor" name="supervisor" data-placeholder="Choose terminal type..."
													class="chosen-select" style="width: 300px;" required>
													<option value="">Select</option>
												</select>&nbsp; <label id="supervisior-id" class="errors" ></label>
											</td>
										</tr>
										<!-- <tr class="odd">
											<td width="20%"><label  for="Admin">Admin<font color="red">*</font></strong></label></td>
											<td colspan=3>
												<select id="admin" name="admin" data-placeholder="Choose terminal type..."
													class="chosen-select" style="width: 300px;" required>
													<option value="">Select</option>
												</select>
											</td>
										</tr> -->
										<input type="hidden" id="admin" name="admin" value=""/>

									</table>
								</fieldset>
							</div>
						<input type="hidden" name="token" id="token" value="${responseJSON.token}"/>
						<input type="hidden" name="selectedUserText" id="selectedUserText" value="${responseJSON.selectedUserText}"/>
					</div>
                </div>
				<div align="left">
						<a  class="btn btn-danger" href="generateMerchantAct.action" onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp;
						<a  class="btn btn-success" href="#" onClick="assignUserToTerminal()">Submit</a>
						<span id ="error_dlno" class="errors"></span>
				</div>
		</div><!--/#content.span10-->
</form>
<script src="${pageContext.request.contextPath}/js/jquery.chosen.min.js"></script>
</body>
</html>
