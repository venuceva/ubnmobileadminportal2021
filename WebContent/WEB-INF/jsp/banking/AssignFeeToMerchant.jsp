
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
<link href="<%=ctxstr%>/css/lightbox.css"	rel="stylesheet" />

 <%@taglib uri="/struts-tags" prefix="s"%>
 <style type="text/css">
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}

.errmsg
{
	color: red;
}
.errors
{
	color: red;
}

.round-button
{
    display:block;
    width:32px;
    height:32px;
    line-height:32px;
    border: 0px solid #f5f5f5;
    border-radius: 50%;
    color:#f5f5f5;
    text-align:center;
    text-decoration:none;
    background: #464646;
    box-shadow: 0 0 1px gray;
    font-size:10px;
    font-weight:bold;
    padding:0px 0px 0px 0px;
    margin:0px 0px 0px 0px;
}

.round-button:hover {
    background: #262626;
}

#gradlight
{
	background-image: -ms-linear-gradient(top, #7700A6 0%, #D473FA 100%);
	background-image: -moz-linear-gradient(top, #7700A6 0%, #D473FA 100%);
	background-image: -o-linear-gradient(top, #7700A6 0%, #D473FA 100%);
	background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0, #7700A6), color-stop(1, #D473FA));
	background-image: -webkit-linear-gradient(top, #7700A6 0%, #D473FA 100%);
	background-image: linear-gradient(to bottom, #7700A6 0%, #D473FA 100%);
}

input.button_add {
    background-image: url(<%=ctxstr%>/images/Left.png); /* 16px x 16px */
    background-color: transparent; /* make the button transparent */
    background-repeat: no-repeat;  /* make the background image appear only once */
    background-position: 0px 0px;  /* equivalent to 'top left' */
    border: none;           /* assuming we don't want any borders */
    cursor: pointer;        /* make the cursor like hovering over an <a> element */
    height: 30px;            /*make this the size of your image */
    padding-left: 30px;      /*make text start to the right of the image */
    vertical-align: middle; /* align the text vertically centered */
}


input.button_add2 {
    background-image: url(<%=ctxstr%>/images/Right.png); /* 16px x 16px */
    background-color: transparent; /* make the button transparent */
    background-repeat: no-repeat;  /* make the background image appear only once */
    background-position: 0px 0px;  /* equivalent to 'top left' */
    border: none;           /* assuming we don't want any borders */
    cursor: pointer;        /* make the cursor like hovering over an <a> element */
    height: 30px;           /* make this the size of your image */
    padding-left: 30px;     /* make text start to the right of the image */
    vertical-align: middle; /* align the text vertically centered */
}
</style>
<s:set value="responseJSON" var="respData"/>
<script type="text/javascript" >

function lightbox_open()
{
	window.scrollTo(0,0);
	document.getElementById('light').style.display='block';
	document.getElementById('fade').style.display='block';
}
function lightbox_close()
{
	document.getElementById('light').style.display='none';
	document.getElementById('fade').style.display='none';
}

var form1Rules = {
    rules : {
		merchantId : {
					required:   true
				} ,
		subservice : {
					required:   true
				}
    },
    messages : {
		merchantId : {
			required : "Please Select  Merchant Id."
			 } ,
		subservice : {
			required : "Please Select  Sub Service."
			 }
    } ,
	errorPlacement: function(error, element) {
	    if ( element.is( ':radio' ) || element.is( ':checkbox' ) )
			error.appendTo( element.parent() );
	    else if ( element.is( ':password' ) )
			error.hide();
	    else
			error.insertAfter( element );
	}
};

$(document).ready(function() {


	$('#viewmerchant').hide();

	$('#B1').click(function(e) {
		var selectedOpts = $('#userlist1 option:selected');
		 var j=0;

		      var txncode=selectedOpts.text().split('-');


			  var length= $('#userlist1').children('option').length;

			  if (selectedOpts.length == 0)
			  {
			    alert("Nothing to move.");
				e.preventDefault();
			  }


		       $('#userlist2').find('option').each(function(i)
		    	{
					 selectUsers=$(this).text();
					 var txncode1=selectUsers.split('-');
					// alert(txncode[0]+' === '+txncode1[0]);
					 if (txncode[0] == txncode1[0]) {
						 alert("Fee Code Existed for this transaction type .");
						 j=1;
				 	}

				});

		 	 if(j==0)
			 {

					   	$('#userlist2').append($(selectedOpts).clone());
						$(selectedOpts).remove();
						e.preventDefault();
			 }





    });

    $('#B2').click(function(e) {

		        var selectedOpts = $('#userlist2 option:selected');
		        if (selectedOpts.length == 0) {
		            alert("Nothing to move.");
		            e.preventDefault();
		        }

		        $('#userlist1').append($(selectedOpts).clone());
		        $(selectedOpts).remove();
		        e.preventDefault();


	});



    $('#view').click(function(e) {

    	var selectedOpts = ($('#userlist1 option:selected').val() || $('#userlist2 option:selected').val());

		//	alert("selectedOpts:"+selectedOpts);
 			if(selectedOpts.lenght==0)
			{
				//$("#error_dlno").text("Please select atlease one sub service code.");
				alert("Please select atlease one Fee Name");
			}
 			else
			{
				//alert($("#modifyfeecode").val(selectedOpts));
  				<%-- $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantFeeDetails.action';
		    	$("#form1").submit(); --%>

		    	$.ajax({
				    url : '<%=request.getContextPath()%>/<%=appName %>/merchantFeeDetails.action',
				    type: "POST",
				    data : "modifyfeecode="+selectedOpts,
				    success: function(data, textStatus, jqXHR)
				    {
				    	console.log(data);

						$('#serviceid').text(data.serviceCode);
						$('#servicename').text(data.serviceName);
						$('#subservicecodesp').text(data.subServiceCode);
						$('#subservicename').text(data.subServiceName);
						$('#feecode').text(data.feeCode);
						$('#onusoffus').text(data.ONUS_OFFUS_FLAG);

						var slab = data.SLAB;
						var i,j,htmlstr="";
						var rows = slab.split('#');
						for(i=0;i<rows.length;i++)
						{
							htmlstr = htmlstr + "<tr>";
							var cols = rows[i].split('@');
							var freq = cols[0];

							if(freq == "200") {
								freq = "Per Txn";
							  } else if(freq == "205"){
								freq = "Daily";
							  } else if(freq == "210"){
								freq = "Weekly";
							  } else if(freq == "215"){
								freq = "Monthly";
							  } else if(freq == "220"){
								freq = "Yearly";
							  } else if(freq == "225"){
								freq = "LifeTime";
							  }
							htmlstr = htmlstr + "<td>"+(i+1)+"</td>";
						htmlstr = htmlstr + "<td>"+freq+"</td>"; 
							for(j=1;j<9;j++)
							{
								htmlstr = htmlstr + "<td>"+cols[j]+"</td>";
							}

							htmlstr = htmlstr + "</tr>";
						}
						console.log(htmlstr);
						$('#tbody_data1').empty();
						$('#tbody_data1').append(htmlstr);
				    	lightbox_open();
				    },
				    error: function (jqXHR, textStatus, errorThrown)
				    {
				    }
				});
		    	return true;
			}

    });


$('#merchantId').live("change",function(e) {

	//alert("alert");
   	 getmerchatid();



});

});

function getmerchatid()
{
	//	alert("janaki");

		var merchantId = $('#merchantId option:selected').val();

		 var formInput='merchantId='+merchantId;


			var queryString = "method=searchTxnType&txnType="+$('#merchantId option:selected').val();
			$.getJSON("postJson.action", queryString,function(data) {

	     		var v_message = data.responseJSON.NOT_ASSIGNED;
	     		datalength=v_message.length;

		  		   $('#userlist1').find('option').remove();
		     		 $('#userlist2').find('option').remove();
	     		$.each(v_message, function(x, val) {
	     			console.log(v_message[x]);
	    			 var options = $('<option/>', {value: v_message[x].val, text: v_message[x].key});
	    		    $('#userlist1').append(options);

	    		});

	     		var v_message1 = data.responseJSON.ASSIGNED;
	     		$.each(v_message1, function(x, val) {
	     			console.log(v_message1[x]);
	    			 var options = $('<option/>', {value: v_message1[x].val, text: v_message1[x].key});
	    		    $('#userlist2').append(options);

	    		});


			});


}

$('#viewmerchant').click(function(e) {


	var mrcode = $('#merchantId option:selected').val();

    	$("#mrcode").val(mrcode);
    	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/feemerchantScreen.action';
    	$("#form1").submit();
    	return true;

    });


function getGenerateMerchantScreen(){
	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/home.action";
	$("#form1").submit();
	return true;

}

function assignDashtoGroup()
{
	var selectUsers = "";
	var selectUsersText = "";
	var data=$("#subservicecode").val();
	if($('#userlist2').find('option').length == 0) {
		$("#error_dlno").text("Please select atlease one sub service code.");
	}
	else if($('#userlist2').find('option').length > 0)
	{
		$('#userlist2').find('option').each(function(i)
		{
			 if(i == 0 )
			 {
				 selectUsers=$(this).val();
			 }
			 else
			 {
				 selectUsers+=","+$(this).val();
			 }
		});

		$('#userlist2').find('option').each(function(i){
			 if(i == 0 ){
				 selectUsersText=$(this).text();
			 } else {
				 selectUsersText+=","+$(this).text();
			 }
		});

	    $('#selectUsers').val(selectUsers);

	   	$('#selectUsersText').val(selectUsersText);

	   	console.log("Select Users : "+selectUsers+"  "+selectUsersText);

		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/merchantFeeAssignCnf.action";
		$("#form1").submit();
		return true;
	}
}


function AssignFeeMerchant()
{
	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/getMerchantAssignServices.action";
	$("#form1").submit();
	return true;
}

</script>
</head>
<body >
<form name="form1" id="form1" method="post" action="">
	<div id="content" class="span10">
	    <div>
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="#">Merchant Fee Assigning</a>   </li>
 			</ul>
		</div>
		
	
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Merchant Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>
		<div class="box-content">
			<fieldset>
				<table width="950" border="0" cellpadding="5" cellspacing="1"
					class="table table-striped table-bordered bootstrap-datatable ">
					<tr class="even">
						<td   width="30%"><label for="Merchant Id"><strong>Merchant Id<font color="red">*</font></strong></label></td>
						<td  width="50%">
							<s:select cssClass="chosen-select-width"
								style="width: 250px;"
								headerKey=""
								headerValue="Select"
								list="#respData.MERCHANT_DATA"
								name="merchantId"
								id="merchantId"
								requiredLabel="true"
								theme="simple"
								data-placeholder="Choose Merchant..."
							 />
							 &nbsp&nbsp&nbsp


						</td>
					</tr>
					<%-- <tr class="odd">
						<td><label for="Select Sub Service"><strong>Select Sub Service<font color="red">*</font></strong></label></td>
						<td>
							<s:select cssClass="chosen-select-width"
									style="width: 250px;"
									headerKey=""
									headerValue="Select"
									list="#respData.TXN_LIST"
									name="subservice"
									id="subservice"
									requiredLabel="true"
									theme="simple"
									data-placeholder="Choose Sub Service..."
							 />
						</td>
					</tr>  --%>
				</table>
			</fieldset>
		</div>
		<input type="hidden" name="selectUsers" id="selectUsers" ></input>
		<input type="hidden" name="selectUsersText" id="selectUsersText" ></input>
		<input type="hidden" name="subservicecode" id="subservicecode" ></input>
         </div>
	</div><!--/#content.span10-->

			<div id="userDetails">
					 <table width="950" border="5" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">

			         &nbsp;&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;
			         			&nbsp;&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;
			         			&nbsp;&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;
			         			&nbsp;&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	<span><storng>List of Fee Names</storng></span>	&nbsp;
			         			&nbsp;&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;
			         			&nbsp;&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;
			         			&nbsp;&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;
			         			&nbsp;&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;
			         			&nbsp;&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;
			         			&nbsp;&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;

			         	<span><storng>Assigned Fee Names</storng></span>
						<tr class="even">

							<td >
								<div align="right">
									<select multiple style="width:300px;height:150px;" size="10" name="userlist1" id="userlist1">
									</select>
								</div>
							</td>
							<td class="odd" width="30px;"><br></br>
							 <a  class="round-button" href="#" id="view"   name="view"  value="view" style=" margin-top: -20px; margin-bottom: 5%;">View</a>
								<input type="button" id="B1" class="button_add2"   name="B1" style="margin-top:6px;"/><br>
								<input type="button" id="B2"  class="button_add" name="B2" style="margin-top:8px;"/>
								<input type="hidden" name="mrcode" id="mrcode" value="" />
								<input type="hidden" name="mrfeeCode" id="mrfeeCode" value="" />
								<input type="hidden" name="modifyfeecode" id="modifyfeecode" value="" />
								<input type="hidden" name="merchantID" id="merchantID" value="" />
						   </td>
							<td>
								<select multiple style="width:300px;height:150px;" size="10" name="userlist2" id="userlist2">
								</select>
							</td>
						</tr>
				</table>


				<!-- Added by vijay -->

				<div id="light"><br><br>
								<div class="box span12" style="width:95%;">

									<div class="box-header well" style="background:#7700A6;" id="gradlight" data-original-title>
										<!-- <i class="icon-edit"></i> --><font color="white">Fee Details</font>
										<div class="box-icon">
											<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
											<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
										</div>
									</div>
										<div id="primaryDetails" class="box-content" style="background:white";>
											<fieldset>
												<table width="950" border="0" cellpadding="5" cellspacing="1"
													class="table table-striped table-bordered bootstrap-datatable " >
														<tr class="even">
															<td width="20%"><label for="Service ID"><strong>Service Code</strong></label></td>
															<td width="30%"> <span id="serviceid"></span><%-- <s:property value="#respData.serviceCode" /> --%>  </td>
															<td width="20%"> <label for="Service Name"><strong>Service Name</strong></label> </td>
															<td width="30%"> <span id="servicename"></span><%-- <s:property value="#respData.serviceName" /> --%>  </td>
														</tr>
														<tr class="odd">
															<td > <label for="Sub Service Code"><strong>Sub Service Code</strong></label> </td>
															<td > <span id="subservicecodesp"></span> <%-- <s:property value="#respData.subServiceCode" /> --%> 	</td>
															<td> <label for="Sub Service Name"><strong>Sub Service Name</strong></label> </td>
															<td> <span id="subservicename"></span> <%-- <s:property value="#respData.subServiceName" /> --%> 	</td>
														</tr>
														<tr class="even">
															<td> <label for="Fee Code"><strong>Fee Code</strong></label> </td>
															<td> <span id="feecode"></span> <%-- <s:property value="#respData.feeCode" /> --%> 	</td>
															<td> <label for="Service"><strong>ON-US/OFF-US Flag </strong></label> 	</td>
															<td> <span id="onusoffus"></span> <%-- <s:property value="#respData.ONUS_OFFUS_FLAG" /> --%>  </td>
					 									</tr>

												</table>
											</fieldset>
									</div>

								<div id="secondaryDetails" class="box-content" style="background:white">
									<fieldset>
										<table id="mytable" width="950" border="0" cellpadding="5" cellspacing="1"
											class="table table-striped table-bordered bootstrap-datatable ">
											<thead>
												<tr >
													<th>Sno</th>
													<th>Frequency</th>
													<!-- <th>Channels</th> -->
													<th>Currency</th>
													<th>Condition</th>
													<th>From Amount/Count</th>
													<th>To Amount/Count</th>
													<th>F/P</th>
													<!-- <th>Bank Account</th> -->
													<th>Bank Amount</th>
													<!-- <th>Agent Account</th> -->
													<th>Agent Amount</th>
													<th>Service Tax</th>
												</tr>
											</thead>
											<tbody id="tbody_data1">
											</tbody>
											<input type="hidden" name="mrcode" id="mrcode" value="${mrcode}" />
										</table>
									</fieldset>
								</div>
							</div>


						<div class="form-actions" style="text-align:center;">
							<a  class="btn btn-success" href="#" onClick="lightbox_close()">CLOSE</a>
						</div>
				</div>

				<div id="fade">

				</div>

			</div>



 		<div class="form-actions">
				<a  class="btn btn-danger" href="#" onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp;
				<a  class="btn btn-success" href="#" onClick="assignDashtoGroup()">Submit</a> &nbsp;
				<span id ="error_dlno" class="errors"></span>
		</div>
</div>
<script type="text/javascript">
$(function(){
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
</form>
</body>
</html>
