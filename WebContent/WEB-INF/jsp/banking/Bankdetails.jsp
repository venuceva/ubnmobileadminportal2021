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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<style type="text/css">
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
span.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
</style>

<script type="text/javascript" >

var val = 1;
var rowindex = 0;
var colindex = 0;
var flagAcc = true;
var userictadminrules = {
	rules : {
		//service : { required : true },
		bankAccount : { required : true } ,
		//openbalance : { required : true, digits: true } ,
		//closebalance : { required : true,digits: true } ,
		accounttype : { required : true },
		bin : { required : true }
	},
	messages : {
		/* service : {
					required : "Please Select Services."
				  },  */
				  bankAccount : {
						required : "Please Enter Account Number."
					},
		/* openbalance : {
						required : "Please Input Open Balance."
					}, */
		bin : {
						required : "Please select bin."
					},
		accounttype : {
						required : "Please Choose Office Location."
					}
	}
};
$(document).ready(function() {

 	var mydata ='${responseJSON.BANK_INFO}';
 	var json = jQuery.parseJSON(mydata);
 	$.each(json, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	    var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);
	    // append the options to job selectbox
	    $('#bin').append(options);
	});

	$('#dashboardd').click(function()
	{
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/bankDashboardact.action';
		$("#form1").submit();
	});

	$('#accounttype').live('change',function() {
		var accttype = $('#accounttype').val();
		var bankAccount = $('#bankAccount').val();

		if(accttype != '' && bankAccount != '')  {
		   var formInput='accounttype='+accttype +'&bankAccount='+bankAccount+'&method=checkTransactionType';

		   //$.getJSON('checkTransactionType.action', formInput,function(data) {
		   $.getJSON('postJson.action', formInput,function(data) {
				var resultCnt=data.responseJSON.RESULT_COUNT;
				if(resultCnt==1) {
					$("#error_dlno").text("Account Number Already Exists with "+$('#accounttype').val());
					flagAcc = false;
				} else {
					$("#error_dlno").text("");
					flagAcc = true;
				}
		   });
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

	// The below event is to delete the entire row on selecting the delete button
	$('#delete').live('click',function() {
		var delId = $(this).attr('index');
		$(this).parent().parent().remove();
		if($('#myTable > tbody  > tr').length == 0) { rowindex = 0; colindex=0; }
		var spanId = "";
		$('#multi-row-data > span').each(function(index){
			spanId =  $(this).attr('index');
			if(spanId == delId) {
				$(this).remove();
			}
		});
	});

   $('#save').live('click', function () {

		var rowCount = $('#tbody_data > tr').length;
		 $("#error_dlno").text('');

		if(rowCount > 0 && flagAcc) {
			$("#form1").validate().cancelSubmit = true;
			var specChar = "";
			var prevCount = "";
			$('#multi-row-data > span').each(function(index){
				if(index == 0)  finalData = $(this).text();
				else finalData += "#"+ $(this).text();
			});

			$('#bankMultiData').val(finalData);

			var url="${pageContext.request.contextPath}/<%=appName %>/BankDetialsConfirm.action";
			$("#form1")[0].action=url;
			$("#form1").submit();
		} else {
			$("#error_dlno").text("Please add atleast one service.");
		}
	});

  <%-- $('#btn-cancel').live('click', function () {
	   var url="${pageContext.request.contextPath}/<%=appName %>/home.action";
		$("#form1")[0].action=url;
		$("#form1").submit();
   }); --%>

	// Add Row Script.
	var finalData="";
	 $('#addCap').on('click', function () {
		$("#error_dlno").text('');


		$("#form1").validate(userictadminrules);

		if($("#form1").valid() && flagAcc && $("#form1").manualValidation()) {

		 	var service = $('#service').val() == undefined ? ' ' : $('#service').val();
			var accountname = $('#accountname').val() == undefined ? ' ' : $('#accountname').val();
			 var bin = $('#bin').val() == undefined ? ' ' : $('#bin').val();
			var bankAccount = $('#bankAccount').val() == undefined ? ' ' : $('#bankAccount').val();
			var accounttype = $('#accounttype').val() == undefined ? ' ' : $('#accounttype').val();
			var eachrow= accounttype+","+bankAccount+","+bin;

			var  hiddenInput ="";
			$('#user-details').find('input,select').each(function(index){
				var valToSpn = ($(this).attr('value') =='' ? ' ' :$(this).attr('value'));
				//alert("values:"+valToSpn);
				//console.log("Name ["+$(this).attr('name')+"] Values["+$(this).val()+"]");
				if($(this).attr('name') != undefined) {
				  if(index == 0) hiddenInput += valToSpn;
				  else hiddenInput += ","+valToSpn;
				}
			});

			 $("#multi-row-data").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ></span>");
			 $('#hidden_span_'+rowindex).append(hiddenInput);

			var addclass = "";

			if(val % 2 == 0 ){
				addclass = "even";
				val++;
			}
			else
			{
				addclass = "odd";
				val++;
			}
			colindex = ++ colindex;
			var appendTxt = "<tr class="+addclass+" align='center' id='"+rowindex+"' index='"+rowindex+"'>"+
					"<td class='col_"+colindex+"'> &nbsp;"+colindex+"   </td> "+
				/* 	"<td> "+service+" </td> "+ */
					/* "<td> "+accountname+" </td> "+ */
					"<td>"+accounttype+" </td> "+
					//" <td>"+closebalance+" </td> "+
					" <td>"+bankAccount+" </td> "+
					" <td>"+bin+" </td> "+
					" <td><input type='button' class='btn btn-info' name='delete' id='delete' value='Delete' index='"+rowindex+"' /></td></tr>";
			rowindex = ++rowindex;
			$("#mytable").append(appendTxt);
			$('#accounttype').val('');
			$('#bankAccount').val('');
			$('#bin').val('');
			//$('#closebalance').val('');

			var listid = "bankAccount,accounttype,bin".split(",");
			$(listid).each(function(index,val){
				$('#'+val).find('option').each(function( i, opt ) {
					if( opt.value === '' ) {
						$(opt).attr('selected', 'selected');
						$('#'+val).trigger("liszt:updated");
					}
				});
			});

			var rowCount = $('#myTable > tr').length;
			if(rowCount > 0 )  $("#error_dlno").text('');
		}
		else
		{
			return false;
		}
	});
});

$(document).on('change','select',function(event) { 

	if($('#'+$(this).attr('id')).next('label').text().length > 0) {
	 $('#'+$(this).attr('id')).next('label').text(''); 
	 $('#'+$(this).attr('id')).next('label').remove();
	}

	});



</script>
</head>
<body>
	<form name="form1" id="form1" method="post" action="">
	<!-- topbar ends -->


			<div id="content" class="span10">
             <!-- content starts -->
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
 				  <li><a href="#">Bank Account Creation</a></li>
				</ul>
			</div>
			<span class="box-content" id="top-layer-anchor" >
				<p>

  				    <a href="#"  id="dashboardd"  class="btn btn-success ajax-link" ><i class='icon icon-users icon-white'></i>&nbsp;Dashboard</a>

  				</p>
			</span>
		 <div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Bank Account Creation
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="box-content">
					<fieldset>
						<table width="950"  border="0" cellpadding="5" cellspacing="1" id="user-details"
							class="table table-striped table-bordered bootstrap-datatable " >


							<tr class="odd">

							<td><strong><label for="Bank Account">Bank Account Number<font color="red">*</font></label></strong></td>
								<td><input name="bankAccount"  customvalidation="max::13||min::13" type="text" id="bankAccount" required="required"  class="field"/> <span id="openbalance_err" class="errmsg"></span></td>

								<td><strong><label for="accounttype">Account Type<font color="red">*</font></label></strong></td>
								<td >
									<select  name="accounttype" id="accounttype" class="chosen-select-no-results" required="required">
										<option value="">select</option>
										<!-- <option value="T">T-Float</option> -->
										<option value="F">F-Commision</option>
										<!-- <option value="C">C-Collections</option> -->
										<option value="S">S-Service Tax</option>
										<option value="M">M-Mpesa</option>
										<option value="A">A-Airtel Mony</option>
									 </select>
								</td>


							</tr>
							<tr class="even">
								<td  width="25%" ><strong><label for="service">Bin<font color="red">*</font></label></strong></td>
								<td   width="25%">
									<select  name="bin" id="bin"  class="chosen-select" required="required"
										style="width:250px">
										<option value="">select</option>
									</select>
								</td>
								<td></td><td></td>
								</tr>
						<!-- 	 <tr class="odd">
								<td><strong><label for="Bank Account">Bank Account Number<font color="red">*</font></label></strong></td>
								<td><input name="bankAccount"  type="text" id="bankAccount" required="required"  class="field"/> <span id="openbalance_err" class="errmsg"></span></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr> -->

							<!-- <input name="closebalance" type="hidden" id="closebalance" class="field"  value="0" />    -->
						</table>
					</fieldset>
				</div>

				<div class="box-content">
					<table width="100%" class="table table-striped table-bordered bootstrap-datatable "
								id="mytable"  >
					  <thead>
							<tr >
								<th >Sno</th>
								<!-- <th >Services</th> -->
								<!-- <th  type="Hidden">Account Name</th> -->

							<th >Account Type</th>

								<th >Account Number</th>
								<th >Bin</th>
								<th  >Actions</th>
							</tr>
					  </thead>
					  <tbody  id="tbody_data">
					  </tbody>
					</table>
				</div>

				<span id="multi-row-data" class="multi-row-data" style="display:none"> </span>
				<input type="hidden" name="bankMultiData" id="bankMultiData" />
			</div>
		</div>

	<div class="form-actions">
		<input type="button" class="btn btn-success" name="addCap" id="addCap"  value="Add" />
		&nbsp;<input type="button" class="btn btn-success" name="save" id="save" value="save" width="100"  ></input>
		&nbsp;<!-- <input type="button" class="btn" type="text"  name="btn-cancel" id="btn-cancel" value="Cancel" width="100" ></input> -->
			<span id ="error_dlno" class="errors"></span>
	</div>
 </div>
 </form>
</body>
</html>