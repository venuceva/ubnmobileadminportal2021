
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

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
	
	
	$('#viewprdlist').on('click', function(){
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/viewServicepack.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#editprd').on('click', function(){
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/editServicepack.action'; 
		$("#form1").submit();
		return true;
	});
	
	
	$('#mytable').hide();
	$('#spack').on('change', function (e) {
		
		//console.log("service list ["+$('#servicelist').val()+"]");
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
	   // alert(valueSelected[0]);

			
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
	
	var form1Rules = {
			   rules : {
				   productid : { required : true},
				   prddesc : { required : true},
				   institute : { required : true}
			   },  
			   messages : {
				   productid : { 
				   		required : "Please enter Product ID."
					},
					prddesc : { 
				   		required : "Please enter Product Description."
					},
					institute : { 
				   		required : "Please Select Institute."
					}
			   } 
			 };
	
$('#btn-submit').click(function(){ 
	
	//alert();
	var finalData= ' ';
	var finalData1= ' ';
		 $('#multi-row-data > span').each(function(index) {  
				if(index == 0)  finalData = $(this).text();
				else finalData += "#"+ $(this).text();
			 }); 
				 			   
		 console.log("finalData1"+finalData);
		 $("#subproductData").val(finalData);  
		 
		 
		 $('#limit-row-data > span').each(function(index) {  
				if(index == 0)  finalData1 = $(this).text();
				else finalData1 += "#"+ $(this).text();
			 }); 
			 $("#limitData").val(finalData1);  
			 console.log("finalData1"+finalData1);

			 
			 $("#form1").validate(form1Rules);
		if($("#form1").valid()) { 
			//alert($("#limitData").val());
			//alert($("#subproductData").val());
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/createProductActcnf.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		} 
	});



});


/** Form2 Add,Modify Starts*/
var val = 1; 
var rowindex = 0;
var colindex = 0;
var userstatus = "";
var masterstatus = "";
var v_message = "";
var listid = "".split(",");
var headerList = "subprdid,subprddesc".split(",");
var selTextListFee = "spack,servicelist,frequency,currency,condition,bankAccount,agentAccount";
var headerListFee = "spack,servicelist,frequency,currency,condition,fromRange,toRange,bankAmount,agentAmount,serviceTax".split(",");
var tabArry ; 
 
var index1 = "";
var searchTdRow = "";
var searchTrRows = "";
var selTextList = ""; 
var rowCount = 0;
 
function clearVals(){ 
	$('#subprdid').val('');
	$('#subprddesc').val(''); 
	
	 
	rowCount = $('#tbody_data > tr').length; 
	if(rowCount > 0 )  $("#error_dlno").text('');
}

var acctRules = {
		   rules : {
			   subprdid : { required : true},
			   subprddesc : { required : true}
		   },  
		   messages : {
			   subprdid : { 
			   		required : "Please enter Sub Product ID."
				},
				subprddesc : { 
			   		required : "Please enter Sub Product Description."
				}
		   } 
		 };

function commonData(id,type){
	var hiddenInput ="";
	var hiddenNames = "";
	var tabarrindex = 0;
	tabArry = new Array();
	modTabArry = new Array(); 
	try {
 		
		$(id).find('input[type=text],input[type=hidden]').each(function(indxf){ 
			var nameInput = "";
			var valToSpn =  "";
			try {
				  nameInput = $(this).attr('name'); 
				  valToSpn = $(this).attr('value').length == 0 ? " " : $(this).attr('value'); 
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
			  
			   if(jQuery.inArray(nameInput, headerList) != -1){  
					tabArry[tabarrindex] = valToSpn; 
 				  tabarrindex++;
			  }
			} 			 
		}); 
 		
	} catch (e) {
		console.log(e);
	}  
	return hiddenInput+"@@"+hiddenNames;
}

var addAccountInfo = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	 var tabArrText = ""; 
	 var data1 = "";
	try { 
		data1 = commonData('#subprd-details','ADD');
		 
		rowindex = $("#multi-row-data > span") .length ;
		$("#multi-row-data").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ind-id='"+tabArry[0]+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
		$('#multi-row-data > span#hidden_span_'+rowindex).append(data1.split("@@")[0]);
	 
		var addclass = ""; 
		
		if(val % 2 == 0 ){
			addclass = "even";
			val++;
		} else {
			addclass = "odd";
			val++;
		}
		$('#mytable').show();
		colindex = ++ colindex;
		
		$(tabArry).each(function(index){ 
			tabArrText+="<td>"+tabArry[index].trim()+"</td> ";
		});
		//alert(tabArrText);
		appendTxt = "<tr class="+addclass+" align='center' id='tr-"+rowindex+"' index='"+rowindex+"'>"+
				"<td class='col_"+colindex+"'>"+colindex+"</td>"+ tabArrText+ 
 				"<td> <a class='btn btn-min btn-danger' href='#' id='delete-account' index='"+rowindex+"' title='Delete Account' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";
 		
	} catch(e) {
		console.log(e);
	}   
	return appendTxt; 
};
 
/** Form2 Add,Modify Ends*/

function alignSerialNo(serialId) {  
	if($(serialId).length > 0) {
		$(serialId +' > td:first-child').each(function(index){  
			$(this).text(index+1);
		}); 
	}
}

function recordCheck(idtocheck,billerId,tid){ 
	var check = false; 
	var userIdRecords = new Array();
	var data = '';
	 
	try { 
		if($(idtocheck).length > 0) {
			// To Check The Record Exists In The Add-Row
			$(idtocheck+' > td:nth-child(2)').each(function(indx){ 
				//console.log($(this).text());
 				userIdRecords[indx]=$(this).text().trim(); 
			}); 
			
			if(jQuery.inArray(billerId.toUpperCase().trim(), userIdRecords) != -1){ 
				//check = true; 
				if(tid=='1'){
				data = 'Account exists in the above table, please input another.';
				}
				else
					{ data = 'Account already added.'; }
			}  else {
				data =  'NO';
			} 
			
		} else {
			return 'NO';
		} 
		
	} catch(e){
		console.log(e);
		return 'Error while adding biller id.';
	} 
	 
	return data;
}




function loadToolTip(){
	$('[rel="tooltip"],[data-rel="tooltip"]').tooltip({"placement":"bottom",delay: { show: 400, hide: 200 }});
}
$(function(){

	/** Form2 Add Row Validation Starts*/
		// Add Row Starts Here.
		 $('#add-subprd').live('click', function(){
			 			 
			$("#billerMsg").text('');
	 		$("#error_dlno").text('');	 
			$("#form2").validate(acctRules); 
			 var textMess = "#tbody_data > tr";  
			 var flag = false; 
			 
			  if($("#form2").valid() ) { 
				  //alert();
					// Is To Check Account Exist's or not 
					var dat = recordCheck("#tbody_data > tr",$('#subprdid').val(),'1');
					
						if(dat == 'NO'){
								var appendTxt = addAccountInfo("ADD");
		 						$("#tbody_data").append(appendTxt);  
								clearVals();
								alignSerialNo(textMess); 
								$('#error_dlno').text('');
								loadToolTip();  
						} 
						else {
							$('#billerMsg').text(dat);
							} 
						
				 } else { 
					 return false;
				 }  
		}); 
		 
		
		// The below event is to delete the entire row on selecting the delete button 
		$("#delete-account").live('click',function() { 
			var delId = $(this).attr('index');
			$(this).parent().parent().remove();
	 		
			var spanId = "";
			$('#multi-row-data > span').each(function(index){  
				spanId =  $(this).attr('index'); 
				if(spanId == delId) {
					$(this).remove();
				}
			}); 
			
			clearVals();
			// Aligning the serial number
			alignSerialNo("#tbody_data > tr"); 
		 
			$('.tooltip').remove();
			$('#billerMsg').text('');
			$('#error_dlno').text('');
		}); 
		
		// Clear Form Records Row Starts Here.
		$('#row-cancel').live('click', function () {
			$("#error_dlno").text(''); 
			 clearVals();  
		});  
		
}); 

/** Form2 Add Row Validation Ends*/


/** Form2 Add,Modify Ends*/
$(function(){
	
	var form2Rules = {
		     rules : {
					frequency : {  required: true   },
					currency : { required: true    },
					condition : {   required: true   },
					fromRange : {
								required: {
									depends: function(element) {
									//if ($("#condition").val() == 'C'){
									if (($("#condition").val() == 'C')||($("#condition").val() == 'A')){
										return true;
									}  else {
										return false;
								   }
								}
							 },
							 number : {
								depends: function(element) {
									//if ($("#condition").val() == 'C'){
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
									//if ($("#condition").val() == 'C'){
									  if (($("#condition").val() == 'C')||($("#condition").val() == 'A')){
										return true;
									}  else {
										return false;
								   }
								}
							 },
							 number : {
								depends: function(element) {
									//if ($("#condition").val() == 'C'){
								      if (($("#condition").val() == 'C')||($("#condition").val() == 'A')){
										return true;
									}  else {
										return false;
								   }
								}
							 }
						 }
							 //,

						//flatPercent : { required : true }

						
		    },
		    messages : {
					frequency : {   required: "Please Select Frequency."    },
					currency : {  required: "Please Select Currency."    },
					condition : {   required: "Please Select Condition."   },
					fromRange : {   required: "Please Enter From Amount.", number : "Please Input Number From [0-9]"   },
					toRange : { required: "Please Enter To Amount.", number : "Please Input Number From [0-9]"    }
					//,
					//flatPercent : {required : "Please Select Flat/Percentile"		 }
		    }
		  };
	
	// Add Row Starts Here.
	$('#addlimit').live('click', function() {

		var fromamount=$("#fromRange").val();
		var toamount=$("#toRange").val();

		if(parseInt(fromamount)>parseInt(toamount))
		{
			alert("Sorry, toAmount should always Greater then from Amount");
			return false;
		}
		else
			{
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
		}
			}
	});

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
		$("#form2").validate().cancelSubmit = true;

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

});




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
 				"<td><a class='btn btn-min btn-info' href='#' id='editDatFee' index='"+rowindex+"' title='Edit' data-rel='tooltip'> <i class='icon-edit icon-white'></i></a> &nbsp;<a class='btn btn-min btn-danger' href='#' id='delete-fee' index='"+rowindex+"' title='Delete' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";
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

function clearValsFee() {
	$('#frequency').val('');
	$('#currency').val('');
	$('#condition').val('');
	$('#fromRange').val('');
	$('#toRange').val('');
	$('#spack').val('');
	$('#servicelist').val('');
	//$('#flatPercent').val('');
	

	$(listid).each(function(index,val){
		$('#'+val).find('option').each(function( i, opt ) {
			if( opt.value == '' ) {
				$(opt).attr('selected', 'selected');
				$('#'+val).trigger("liszt:updated");
			}
		});
	});
}


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



</script>
</head>

<body>
<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Product Management</a>  </li> 
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

	<div class="box-content" id="top-layer-anchor">
		<span>
				<a href="#" class="btn btn-info" id="viewprdlist" title='View Users' data-rel='popover'  data-content='Viewing the Existing Products.'>
				<i class="icon icon-book icon-white"></i>&nbsp;View Existing Products</a> &nbsp; 
			</span>
			<span>
				<a href="#" class="btn btn-info" id="editprd" title='View Users' data-rel='popover'  data-content='Viewing the Existing Products.'>
				<i class="icon icon-book icon-white"></i>&nbsp;Edit Products</a> &nbsp; 
			</span>  			 
		</div> 

		<div class="row-fluid sortable"> 
			<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Product Details
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
									<td width="20%"><label for="Product ID"><strong>Product ID</strong></label></td>
									<td width="30%"><input	name="productid" type="text" id="productid" class="field"/></td>
									<td width="20%"><label for="Product Description"><strong>Product Description</strong></label></td>
									<td width="30%"><input name="prddesc" type="text" id="prddesc" class="field"/>
									<input type="hidden" name="subproductData" id="subproductData" />
									<input type="hidden" name="limitData"  id="limitData" />
									</td>
								</tr>

								<tr class="odd">
									
									<td><label for="Menu Level"><strong>Institute<font color="red">*</font></strong></label></td>
									<td>
										<s:select cssClass="chosen-select" 
												 headerKey="" 
												 headerValue="Select"
												 list="#{'BFUB':'BFUB','IMAL':'IMAL'}" 
												 name="institute" 
												 id="institute" 
												 requiredLabel="true" 
												 theme="simple"
												 data-placeholder="Choose Menu Level..." 
										   /> 
									</td>
									<td><label for="User Id"><strong></strong></label></td>
									<td></td>
								</tr>

							</table>
				</fieldset> 
				</div>
				</div> 
				
				
		</div> 	
</form>	

<form name="form2" id="form2" method="post"> 	
		<div class="row-fluid sortable" > 
			<div class="box span12" > 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Add Sub Product
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
					
				<div class="box-content" id="subprd-details">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
						
							<td width="20%"><label for="Account Number"><strong>Sub Product ID<font color="red">*</font></strong></label></td>
							<td width="30%"><input type="text" maxlength="14"  class="field" id="subprdid" name="subprdid" required=true /></td>
							<td width="20%"><label for="Alias Name"><strong>Sub Product Description</strong></label> </td>
							<td width="30%"><input type="text" maxlength="14"  class="field" id="subprddesc" name="subprddesc" required=true /></td>
						</tr>  
						<tr class="odd"> 
								<td colspan="4" align="center">
									<input type="button" class="btn btn-success" 
										name="add-subprd" id="add-subprd" value="Add Sub Product" /> &nbsp;  
									<span id="billerMsg" class="errors"></span>
								</td> 
						</tr> 
 				 </table>
				</fieldset> 
				</div> 
				
				<div class="box-content"> 
						<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
									id="mytable" style="width: 100%;" style="display:none">
						  <thead>
								<tr>
									<th><center>Sno</center></th>
									<th><center>Sub Product ID</center></th>
									<th><center>Sub Product Description</center> </th>
									<th><center>Action</center> </th>
								</tr>
						  </thead>    
							 <tbody id="tbody_data"> 
							 	<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
									<s:param name="jsonData" value="%{accBean.newAccountData}"/>  
									<s:param name="searchData" value="%{'#'}"/>  
								</s:bean> 
								<s:iterator value="#jsonToList.data" var="mulData2"  status="mulDataStatus" >   
											<script type="text/javascript">
												var hiddenNames1 = "";
												$(function(){
													$('#subprd-details').find('input[type=text],input[type=hidden]').each(function(index){ 
														var nameInput = $(this).attr('name'); 
														if(nameInput != undefined) {
														  if(index == 0)  {
															hiddenNames1 = nameInput;
														  } else {
															hiddenNames1 += ","+nameInput; 
														  }  
														} 
													}); 
													var data1 = "<s:property />";
													data1 = data1.split(",");
													$("#multi-row-data").append("<span id='hidden_span_<s:property value="#mulDataStatus.index" />' index='<s:property value="#mulDataStatus.index" />' ind-id='"+data1[0]+"' hid-names='"+hiddenNames1+"' ><s:property value="#mulData" /></span>");
												});
												</script> 
											
											<s:if test="#mulDataStatus.even == true" > 
												<tr class="even" style="text-align: center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:if>
											<s:elseif test="#mulDataStatus.odd == true">
												<tr class="odd" style="text-align: center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:elseif> 
									
										<td><s:property value="#mulDataStatus.index+1" /></td>
											<s:generator val="%{#mulData2}"
												var="bankDat" separator="," >  
												<s:iterator status="itrStatus">  
														<td><s:property /></td> 
												</s:iterator>  
											</s:generator>
										<td>  
											<a class='btn btn-min btn-danger' href='#' id='delete-biller' index='<s:property value="#mulDataStatus.index" />' title='Delete Biller' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a>
										</td>
									</tr>
								</s:iterator>
							 </tbody>
						</table> 
					</div>   
			
					<span id="multi-row-data" class="multi-row-data" style="display:none"> </span>
			  </div>
		</div>  
		
		<div id="dialog" title="Confirmation Required" style="display:none">
		   Proceed ? <!-- <div id="dia1"></div><font color="red"><div id="dia2"></div> --></font>
		</div>
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
							<%-- <tr class="odd">
								<td ><label  for="Flat/Percentile"><strong>Flat/Percentile <font color="red">*</font></strong></label></td>
								<td ><select name="flatPercent" id="flatPercent" type="select"  required='true'
										data-placeholder="Choose Type..." 	class="chosen-select" style="width: 220px;">
										<option value="">Select</option>
										<option value="P">Percentile</option>
										<option value="F">Flat</option>
									</select></td>
								<td></td>
								<td></td>
							</tr> --%>

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
			
			</div>
</body>

</html>