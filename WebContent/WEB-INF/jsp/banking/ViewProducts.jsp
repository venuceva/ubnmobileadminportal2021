<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@taglib uri="/struts-tags" prefix="s"%>
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
$(document).ready(function(){
	
	
	$('#institute').on('change', function (e) {
		
		var sellist=$('#prdlist').val();
		row1='<option value=" "> Select </option>';
		
		if(sellist=" ")
			{
			$('#prdlist').empty();
			$('#prdlist').append(row1);
			}
			
	    var optionSelected = $("option:selected", this);
	    var valueSelected = this.value;
	    valueSelected=valueSelected.split('-');

			
			var queryString9 = "method=fetchproducts&institute="+valueSelected[0];
			$.getJSON("postJson.action", queryString9,function(data) {				
										
					var json =data.responseJSON ;
					var options = '';
					var options1 = '';
					
 					$.each(json, function(i, d) {
						console.log("i value"+i +"d value" +d);
						   var row=' ';
						   row+='<option name="'+i+'" id="'+i+'" value=' + i + '>' + d + '</option>';
						   
						   $('#prdlist').append(row);
						});
					
			});
	 
	});
	
	
	$('#prdlist').on('change', function (e) {
		
					
	    var optionSelected1 = $("option:selected", this);
	    var valueSelected1 = this.value;

			$("#tbody_data").empty();
			$("#tbody_data1").empty();
			var queryString91 = "method=fetchPrdDetails&prdid="+valueSelected1;
			$.getJSON("postJson.action", queryString91,function(data) {				
										
					var json1 =data.responseJSON ;
					console.log(json1);
					console.log(json1.subprddata);
					console.log(json1.status);
					
					val = 1;
					rowindex = 1;
					colindex = 0;
					bankfinalData=json1.subprddata;
					
					bankfinalDatarows = "";
					eachfieldArr = "";
					appendTxt = "";
					
					if(bankfinalData != "") {
						var bankfinalDatarows = bankfinalData.split("#");
						
						if(val % 2 == 0 ) {
							addclass = "even"; 
						} else {
							addclass = "odd"; 
						}   
						val++; 
						for(var i=0;i<bankfinalDatarows.length;i++){
							  eachrow=bankfinalDatarows[i];
							  eachfieldArr=eachrow.split(","); 
							  appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
							  	"<td>"+rowindex+"</td>"+
								"<td>"+eachfieldArr[0]+" </td>"+ 
								"<td>"+eachfieldArr[1]+" </td>"+ 
								"</tr>";
								$("#tbody_data").append(appendTxt);	  
							rowindex = ++rowindex;
							colindex = ++ colindex; 
						} 
					}
					
			});
	 
	});
	
	
	
$('#spack').on('change', function (e) {
		
		var sellist=$('#servicelist').val();
		row1='<option value=" "> Select </option>';
		
		if(sellist=" ")
			{
			$('#servicelist').empty();
			$('#servicelist').append(row1);
			}
			
	    var optionSelected = $("option:selected", this);
	    var valueSelected = this.value;
	    valueSelected=valueSelected.split('-');

			
			var queryString9 = "method=fetchlimiservices&menucode="+valueSelected[0];
			$.getJSON("postJson.action", queryString9,function(data) {				
										
					var json =data.responseJSON ;
					var options = '';
					var options1 = '';
					
 					$.each(json, function(i, d) {
						console.log("i value"+i +"d value" +d);
						   var row=' ';
						   row+='<option name="'+i+'" id="'+i+'" value=' + i + '>' + d + '</option>';
						   
						   $('#servicelist').append(row);
						});
					
			});
	 
	});
	 
	var acctRules = {
		   rules : {
			   customerId : { required : true,digits: true},
			   institute:{ required : true}
		   },  
		   messages : {
			   customerId : { 
			   required : "Please enter Customer Id."
				},
			   institute:{
			   required : "Please select Institute"
			   }
		   } 
		}; 
	
	var form1Rules = {
			   rules : {
				   prdlist : { required : true},
				   institute : { required : true}
			   },  
			   messages : {
				   prdlist : { 
				   		required : "Please Select Product ID."
					},
					
					institute : { 
				   		required : "Please Select Institute."
					}
			   } 
			 };
	
	$('#btn-submit').click(function(){ 
		
		var finalData= ' ';
		var finalData1= ' ';
			 
			 
			 $('#limit-row-data > span').each(function(index) {  
					if(index == 0)  finalData1 = $(this).text();
					else finalData1 += "#"+ $(this).text();
				 }); 
				 $("#limitData").val(finalData1);  
				 $("#productid").val($('#prdlist').val()); 
				 
				 console.log("finalData1"+finalData1);

				 
				 
				 $("#form1").validate(form1Rules);
				 if ($('#tbody_data1 > tr').length > 0)
				 {
			if($("#form1").valid()) { 
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/createProductActcnf.action'; 
				$('#form1').submit();
				 return true; 
			} else { 
				return false;
			} }
				 else{ $('#error_dlno').text('Please add atleast one limit'); }
		});
	
	
	var form2Rules = {
		     rules : {
		    	 
					spack : {  required: true   },
					servicelist : {  required: true   },
					frequency : {  required: true   },
					currency : { required: true    },
					condition : {   required: true   },
					fromRange : {
								required: {
									depends: function(element) {
									if (($("#condition").val() == 'C')||($("#condition").val() == 'A')){
										return true;
									}  else {
										return false;
								   }
								}
							 },
							 number : {
								depends: function(element) {
									  if (($("#condition").val() == 'C')||($("#condition").val() == 'A')){
										return true;
									}  else {
										return false;
								   }
								}
							 }
						 },
					toRange : {
								required: {
									depends: function(element) {
									  if (($("#condition").val() == 'C')||($("#condition").val() == 'A')){
										return true;
									}  else {
										return false;
								   }
								}
							 },
							 number : {
								depends: function(element) {
								      if (($("#condition").val() == 'C')||($("#condition").val() == 'A')){
										return true;
									}  else {
										return false;
								   }
								}
							 }
						 }

						
		    },
		    messages : {
					spack : {   required: "Please Select Service Pack."    },
					servicelist : {   required: "Please Select Service."    },
					frequency : {   required: "Please Select Frequency."    },
					currency : {  required: "Please Select Currency."    },
					condition : {   required: "Please Select Condition."   },
					fromRange : {   required: "Please Enter From Amount.", number : "Please Input Number From [0-9]"   },
					toRange : { required: "Please Enter To Amount.", number : "Please Input Number From [0-9]"    }
		    }
		  };
	
			var val = 1; 
			var rowindex = 0;
			var colindex = 0;
			var userstatus = "";
			var masterstatus = "";
			var v_message = "";
			
			var headerList = "subprdid,subprddesc".split(",");
			var selTextListFee = "spack,servicelist,frequency,currency,condition,bankAccount,agentAccount";
			var headerListFee = "spack,servicelist,frequency,currency,condition,fromRange,toRange,bankAmount,agentAmount,serviceTax".split(",");
			var tabArry ; 
			 
			var index1 = "";
			var searchTdRow = "";
			var searchTrRows = "";
			var selTextList = ""; 
			var rowCount = 0;
			
			
			
	
	$('#addlimit').live('click', function() {

		var fromamount=$("#fromRange").val();
		var toamount=$("#toRange").val();
		var spk=$("#spack").val().split('-');
		
		var textMess = "#tbody_data1 > tr";
		userIdToSend = spk[1];
		if(parseInt(fromamount)>parseInt(toamount))
		{
			alert("Sorry, toAmount should always Greater then from Amount");
			return false;
		}
		else
			{
		if(recordCheck(textMess,userIdToSend)) {
		$("#bankAcctMsg").text('');
 		$("#error_dlno").text('');

		$("#form3").validate(form2Rules);
		var textMess = "#tbody_data1 > tr";
		if($("#form3").valid()) {

				var appendTxt = addAgentInfo("ADD");
				var service=$("#serviceTax").val();
				
				$("#tbody_data1").append(appendTxt);
				alignSerialNo(textMess);
				resetnextfromamount();
				$("#bankAcctMsg").text('');
				$('#error_dlno').text('');
 				loadToolTip();
 				clearValsFee();

		} else {

			$('#error_dlno').text('');
			return false;
		}}else{
			$('#error_dlno').text('You Cannot add multiple Service Packs to One Product,Please Select the services from Already added service Pack');
		
			}
		
			}
	});
	
	
	function recordCheck(idtocheck,userIdInput){
		var check = false;
		var userIdRecords = new Array();
		
		try {
			if($(idtocheck).length > 0) {
				$(idtocheck+' > td:nth-child(2)').each(function(indx){
					userIdRecords[indx]=$(this).text().trim();
				});
				if(jQuery.inArray(userIdInput.trim(), userIdRecords) == -1){
					check = false;
				}
				else check = true;
			} else check = true;
		} catch(e){
			console.log(e);
		}
		
		return check;
	}

	// Update Edited-Row Starts Here.
	$('#updatelimit').live('click', function () {
		$("#error_dlno").text('');
		$("#form2").validate(form2Rules);
		if($("#form2").valid()) {
 			var spanValues = modAgentAccVals("MODIFY");
 			searchTdRow = '#'+searchTrRows+"#tr-"+index2 +' > td';
			var textToSearch = "";

			$(searchTdRow).each(function(index,value) {
					if(index == 1){	$(this).text(modTabArry[0]);	}
					if(index == 2){	$(this).text(modTabArry[1]);	}
					if(index == 3){	$(this).text(modTabArry[2]);	}
					if(index == 4){	$(this).text(modTabArry[3]);	} 
					if(index == 5){ $(this).text(modTabArry[4]);	}
					if(index == 6){	$(this).text(modTabArry[5]);	}
					if(index == 7){ $(this).text(modTabArry[6]);	}
					if(index == 8){ $(this).text(modTabArry[7]);	}
					if(index == 9){ $(this).text(modTabArry[8]);	}
					if(index == 10){ $(this).text(modTabArry[9]);	}
					if(index == 11){ $(this).text(modTabArry[10]);	}
					if(index == 12){ $(this).text(modTabArry[11]);	}
			});

			$("#limit-row-data span").each(function(index,value) {
 				// if($(this).attr("index") == index2 ) {
				console.log( $(this).attr("index")  +" <====>" + globalIndex);
 				 if( $(this).attr("index")  == globalIndex ) {
					 $(this).attr("hid-names",modHeaderBodyArry[0]);
					 $(this).text(modHeaderBodyArry[1]);
					 return;
				 }
			});
			clearValsFee();
			alignSerialNo("#tbody_data1 > tr");

			//Hide Update Button and Display Add Button
			$('#updatelimit').hide();
			$('#addlimit').show();

		} else {
			return false;
		}
	});
	
	// The below event is to Edit row on selecting the delete button
	$('#editDatFee').live('click',function() {
		$("#form3").validate().cancelSubmit = true;

		index2 = $(this).attr('index');
		var parentId =$(this).parent().closest('tbody').attr('id');
		searchTrRows = parentId+" tr";
		searchTdRow = '#'+searchTrRows+"#tr-"+index2 +' > td';

		var idSearch = "";
		var hidnames = "";
		var text_val = "";

		//console.log("searchTdRow ==> "+searchTdRow);

		var spanData = $("#limit-row-data > span#hidden_span_"+index2);
		hidnames =  $("#limit-row-data > span#hidden_span_"+index2).attr('hid-names');
		text_val =  $("#limit-row-data > span#hidden_span_"+index2).text();

		globalIndex = $(this).attr('index');

		var hidarr=hidnames.split(",");
		var textarr=text_val.split("@");   //Spliting span values


		$(hidarr).each(function(index,value) {
			 if(jQuery.inArray( value, listid ) != -1){
				$('#'+value).find('option').each(function( i, opt ) {
					if( opt.value == textarr[index] ) {
						$(opt).attr('selected', 'selected');
						$('#'+value).trigger("liszt:updated");
					}
				});
			} else {
				//$("#"+value).val(textarr[index].trim());
				$("#"+value).val(textarr[index] == undefined ? " " : textarr[index].trim());
			}
		});
		//Hide Add Button and Display Update Button
		$('#updatelimit').show();
		$('#addlimit').hide();

	});

	// The below event is to delete the entire row on selecting the delete button
	$("#delete-fee").live('click',function() {
		var delId = $(this).attr('index');
		$(this).parent().parent().remove();

		var spanId = "";
		$('#limit-row-data > span').each(function(index){
			spanId =  $(this).attr('index');
			if(spanId == delId) {
				$(this).remove();
			}
		});

		clearValsFee();
		// Aligning the serial number
		alignSerialNo("#tbody_data1 > tr");

		//Show Add Button and Hide Update Button
		$('#updatelimit').hide();
		$('#addlimit').show();
	});

	// Clear Form Records Row Starts Here.
	$('#row-cancel-fee').live('click', function () {
		$("#error_dlno").text('');
		 clearValsFee();

		//Show Add Button and Hide Update Button
		$('#updatelimit').hide();
		$('#addlimit').show();
	});




function alignSerialNo(serialId) {  
	if($(serialId).length > 0) {
		$(serialId +' > td:first-child').each(function(index){  
			$(this).text(index+1);
		}); 
	}
}

function loadToolTip(){
	$('[rel="tooltip"],[data-rel="tooltip"]').tooltip({"placement":"bottom",delay: { show: 400, hide: 200 }});
}

/** Form3 Add Row Validation Agent Starts */
var addAgentInfo = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	 var tabArrText = "";
	 var data1 = "";
	try {
		data1 = commonData1('#secondaryDetails','ADD');
		rowindex = $("#limit-row-data > span").length ;
		var indData = tabArry[0]+"-"+rowindex;
		$("#limit-row-data").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+indData+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
		$('#limit-row-data > span#hidden_span_'+rowindex).append(data1.split("@@")[0]);

		var addclass = "";

		if(val % 2 == 0 ){
			addclass = "even";
			val++;
		} else {
			addclass = "odd";
			val++;
		}
		$('#mytable1').show();

		colindex = ++ colindex;

		$(tabArry).each(function(index){
			tabArrText+="<td>"+tabArry[index].trim()+"</td> ";
		});

		
		appendTxt = "<tr class="+addclass+" align='center' id='tr-"+rowindex+"' index='"+rowindex+"'>"+
				"<td class='col_"+colindex+"'>"+colindex+"</td>"+ tabArrText+
 				"<td><a class='btn btn-min btn-danger' href='#' id='delete-fee' index='"+rowindex+"' title='Delete' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";
		//alert(appendTxt);
	} catch(e) {
		console.log(e);
	}
	return appendTxt;
};


function resetnextfromamount(){
	  var cnd= $('#condition').val();
	  $('#toRange').val();
	  if( cnd =="A" ) {

		  $('#fromRange').val(parseInt($('#toRange').val())+1);
		  $('#toRange').val("")
	  }

}


function commonData1(id,type){
	var hiddenInput ="";
	var hiddenNames = "";
	var tabarrindex = 0;
	tabArry = new Array();
	modTabArry = new Array();

	try {
 		 var channels = "";
		 $('.chkChannel:checked').each(function(i){
			var values = $(this).val();
			if(i == 0 ) {
				channels += values;
			} else {
				channels += ","+values;
			}
		});

		$(id).find('input[type=text],input[type=hidden],select').each(function(indxf) {
			var nameInput = "";
			var valToSpn =  "";
			try {
				  nameInput = $(this).attr('name') ;
				  valToSpn = ($(this).attr('value') =='' ? ' ' :$(this).attr('value'));
			} catch(e1) {
				console.log('The Exception Stack is :: '+ e1);
			}
			if(nameInput != undefined) {
			  if(indxf == 0)  {
				hiddenInput += valToSpn;
				hiddenNames += nameInput;
			  } else {
				hiddenInput += ","+valToSpn;
				hiddenNames += ","+nameInput;
			  }

			   if(jQuery.inArray(nameInput, headerListFee) != -1){
				  if(selTextListFee.indexOf(nameInput) != -1) {
					  tabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
					modTabArry[tabarrindex] = ($('select#'+nameInput+' option:selected').text().trim() == "Select" ? " " : $('select#'+nameInput+' option:selected').text().trim());
				  }  else {
					tabArry[tabarrindex] = valToSpn;
					modTabArry[tabarrindex] = valToSpn;
				  }
				  tabarrindex++;
			  }
			}
		});
	} catch (e) {
		console.log(e);
	}
	return hiddenInput+"@@"+hiddenNames;
}

var modAgentAccVals = function (clickType) {
	var tabArrText = "";
	var appendTxt = "";

	var data1 = "";
	try {
		modHeaderBodyArry = new Array();
		data1 = commonData1('#secondaryDetails','MODIFY');
		modHeaderBodyArry[0]=data1.split("@@")[1];
		modHeaderBodyArry[1]=data1.split("@@")[0];
	} catch(e)  {
		console.log(e);
	}
	return "";
};




$("#condition").live('change',function(e){
	var cond = $(this).val();
	if(cond == 'C') {
			$('.rngdata').text('Count');
		$('#rng-chk').show();
	} else if( cond == 'A') {
		$('.rngdata').text('Amount');
		$('#rng-chk').show();
	}else {
		$('#rng-chk').hide();
		$('.rngdata').text('');
	}
});
	
});

function clearValsFee() {
	$('#frequency').val('');
	$('#currency').val('');
	$('#condition').val('');
	$('#fromRange').val('');
	$('#toRange').val('');
	$('#spack').val('');
	$('#servicelist').val('');
	//$('#flatPercent').val('');
	//alert();
	
	var listid = "spack,servicelist,condition,frequency".split(",");

	$(listid).each(function(index,val){
		$('#'+val).find('option').each(function( i, opt ) {
			if( opt.value == '' ) {
				$(opt).attr('selected', 'selected');
				$('#'+val).trigger("liszt:updated");
			}
		});
	});
}



</script>

</head>

<body>
	
		<div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="#">Product List</a></li>
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
		
<form name="form1" id="form1" method="post" action="">
		<div class="row-fluid sortable"><!--/span-->
			<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Product List
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
															
									<tr class="even">
								<td width="20%"><label  for="Frequency"><strong>Select Core Banking<font color="red">*</font></strong></label></td>
								<td width="30%">
											 <s:select 
												headerKey=""
												headerValue="Select"
												list="#{'INSTID1-BFUB':'BFUB','INSTID2-IMAL':'IMAL'}" 
												name="institute"
												id="institute"
												requiredLabel="true"
												theme="simple"
												data-placeholder="Choose Frequency..."
												 />
								</td>
								<td width="20%"><label for="Networks"><strong>Select Product<font color="red">*</font></strong></label></td>
								 <td width="30%"><select id="prdlist" name="prdlist"><option value=" " selected>Select</option></select>
								 
								 <input type="hidden" name="subproductData" id="subproductData" />
									<input type="hidden" name="limitData"  id="limitData" />
									<input type="hidden" name="productid"  id="productid" />
									</td>
									
							</tr>
								
								
								
							</table>
							</fieldset>
					</div>
			</div>
		</div>
		
			<div class="row-fluid sortable" id="add-new-act"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Sub Products 
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  		
			<div class="box-content">
					<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
								id="mytable" style="width: 100%;">
					  <thead>
							<tr>
								<th>Sno</th>
	 							<th>Sub Product ID</th>
								<th>Sub Product Description</th>
								
							</tr>
					  </thead>    
 					<tbody id="tbody_data"></tbody>
					</table> 
					
					
		 	  </div> 
		 </div>
	 </div>
	 
<!-- 	 	<div class="row-fluid sortable" id="add-new-act"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Limits Added
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  		
			<div class="box-content">
					<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
								id="mytable" style="width: 100%;">
					  <thead>
							<tr>
								<th>Sno</th>
	 							<th>Service Pack</th>
								<th>Service</th>
								<th>Frequency</th>
								<th>Condition</th>
								<th>From Amount/Count</th>
								<th>To Amount/Count</th>
								<th>F/P</th>
							</tr>
					  </thead>    
 					<tbody id="tbody_data1"></tbody>
					</table> 
					
					
		 	  </div> 
		 </div>
	 </div> -->
	

</form>

<form name="form3" id="form3" method="post" action="">

<div class="row-fluid sortable" > 
			<div class="box span12" > 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Add Limits
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
			<div id="secondaryDetails" class="box-content">
				<fieldset>
					<table width="950" border="0" cellpadding="5" cellspacing="1"
							class="table table-striped table-bordered bootstrap-datatable ">
							
							
							<tr class="even">
								<td width="20%"><label  for="Frequency"><strong>Select Service Pack<font color="red">*</font></strong></label></td>
								<td width="30%">
											 <s:select cssClass="chosen-select"
												headerKey=""
												headerValue="Select"
												list="responseJSON.MENULISTS"
												name="spack"
												id="spack"
												requiredLabel="true"
												theme="simple"
												data-placeholder="Choose Frequency..."
												 />
								</td>
								<td width="20%"><label for="Networks"><strong>Select Service<font color="red">*</font></strong></label></td>
								 <td width="30%"><select id="servicelist" name="servicelist"><option value=" " selected>Select</option></select></td>
							</tr>
							
							
							
							<tr class="even">
								<td width="20%"><label  for="Frequency"><strong>Frequency<font color="red">*</font></strong></label></td>
								<td width="30%">
											 <s:select cssClass="chosen-select"
												headerKey=""
												headerValue="Select"
												list="responseJSON.FREQ_DATA"
												name="frequency"
												id="frequency"
												requiredLabel="true"
												theme="simple"
												data-placeholder="Choose Frequency..."
												 />
								</td>
								<td><label  for="Condition"><strong>Condition<font color="red">*</font></strong></label></td>
								<td><select name="condition" id="condition"  	required='true' data-placeholder="Choose Condition..."
											class="chosen-select" style="width: 220px;">
											<option value="">Select</option>
											<option value="A">Amount</option>
											<option value="C">Count</option>
										</select>
								</td>
								
							</tr>

							<tr class="even" id="rng-chk" style="display:none">
								<td><label  for="From Amount"><strong>From <span class="rngdata"></span> <font color="red">*</font></strong></label></td>
								<td><input name="fromRange" id="fromRange" class="field" type="text"  required='true'/></td>
								<td><label  for="To Amount"><strong>To <span class="rngdata"></span> <font color="red">*</font> </strong></label></td>
								<td><input name="toRange" id="toRange" class="field" type="text"  required='true' /></td>
							</tr>
							

							<tr class="odd" align="center">
								<td colspan="4">
									<input type="button" class="btn btn-success" name="addlimit" id="addlimit" value="Add Limits" />
									<input type="button" class="btn btn-success" name="updatelimit" id="updatelimit" value="Update Fee Slabs" style="display:none" />
									&nbsp; <span id ="error_dlno1" class="errors"></span>
								</td>
							</tr>
					</table>
				</fieldset>
			</div>
			<div id="secondaryDetails-row" class="box-content" >
				<table id="mytable1" width="950" border="0" cellpadding="5" cellspacing="1"
					class="table table-striped table-bordered bootstrap-datatable " style="display:none">
					<thead>
						<tr>
 							<th>Sno</th>
 							<th>Service Pack</th>
 							<!-- <th>Channels</th> -->
							<th>Service</th>
							<th>Frequency</th>
							<th>Condition</th>
							<th>From Amount/Count</th>
							<th>To Amount/Count</th>
							<!-- <th>F/P</th> -->
						
							<th  width="17%">Actions</th>
						</tr>
					</thead>
					<tbody id="tbody_data1">
					</tbody>
				</table>
			</div>
			<span id="limit-row-data" class="limit-row-data" style="display:none"> </span>
	</div>
</div>
		</form> 
		
		<div>
				<a class="btn btn-danger" href="#"
					onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp; <a
					class="btn btn-success" href="#" id="btn-submit">Submit</a>
					<span id ="error_dlno" class="errors"></span>
			</div>
			
			<div>
				
			</br>
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
    };
	
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }  
});  
</script>
</body>
</html>
