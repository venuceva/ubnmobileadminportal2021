
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<SCRIPT type="text/javascript">
var toDisp = '${type}';

$(document).ready(function(){
	
	
	$('#chname').on('change', function (e) {
	    var optionSelected = $("option:selected", this);
	    var valueSelected = this.value;
	  var tcnt=$('#Terminalid option').size(); 
	    
	    if(valueSelected=="POS"){
		$('#posdiv').show();
		$('#mobdiv').hide();
		if(tcnt=='1'){
		alert("Terminals Not Found.Please Create Terminals To Assign User to the Terminal Under this Store.");
		}
		
		//alert(tcnt);
	    }
	    else if(valueSelected=="Mobile"){
	    	$('#posdiv').hide();
			$('#mobdiv').show();	
	    }
});

	var actionLink = "";

	if('${responseJSON.CV0014}' == 'MERTADM' || '${responseJSON.CV0014}' == 'MERCHTUSR' || '${responseJSON.CV0014}' == 'MERCNTSUPE')
	{
		$('#idtype').empty();
		$('#idtype').text('ID');
	}

	var userStatus = '${responseJSON.CV0013}';
	var text = "";

	if( userStatus == 'Active')
		text = "<a href='#' class='label label-success'  >"+userStatus+"</a>";
	else if( userStatus == 'De-Active')
		text = "<a href='#'  class='label label-warning' >"+userStatus+"</a>";
	else if( userStatus == 'InActive')
		text = "<a href='#'  class='label label-info'  >"+userStatus+"</a>";
	else if( userStatus == 'Un-Authorize')
		text = "<a href='#'  class='label label-primary'   >"+userStatus+"</a>";

	$('#spn-user-status').append(text);

	$('#btn-submit').live('click', function () {
		 
		 var rowCount = $('#tbody_data1 > tr').length;
		 $("#error_dlno").text('');
		 
		if(rowCount > 0) {
			$("#form1").validate().cancelSubmit = true;

			$('#multi-row-data > span').each(function(index) {
				//console.log("index ["+$(this).attr('index')+"] name ["+$(this).attr('name')+"]  value["+$(this).val()+"] ");
				if(index == 0)  finalData = $(this).text();
				else finalData += "#"+ $(this).text();
			});

			var offVal = '';

			// To Get The Location's From Add-Row
			$('#tbody_data1 > tr > td:nth-child(6)').each(function(indx){
				if(indx == 0) {
					offVal+= $(this).text();
				} else {
					offVal+=","+ $(this).text();
				}
			});
			$("#multiData").val(finalData);
			$('#officeLocation').val(offVal);
			
			var channelData="";
			
			 $('#multi-row-data1 > span').each(function(index) {  
	 				if(index == 0)  {channelData = $(this).text();}
					else {channelData += "#"+ $(this).text();}
				 }); 

			 $('#channeldata').val(channelData);
			 
			 console.log("channeldata valueee"+$("#channeldata").val());
	 
	 			var url="${pageContext.request.contextPath}/<%=appName %>/merchantCreatePageConfirm.action";
				$("#form1")[0].action=url;
				$("#form1").submit();
			 }else {
				$("#error_dlno").text("Please add atleast one user.");
			}
	});

	$("#MerchantInfo").hide();
	var userType = '${responseJSON.CV0017}';

	if(userType=="MS" || userType=="MU"){
		$("#MerchantInfo").show();
	}
});
	//-->
	
	
		$('#chname').on('change', function (e) {
	    var optionSelected = $("option:selected", this);
	    var valueSelected = this.value;
	  var tcnt=$('#Terminalid option').size(); 
	    
	    if(valueSelected=="POS"){
		$('#posdiv').show();
		$('#mobdiv').hide();
		if(tcnt=='1'){
		alert("Terminals Not Found.Please Create Terminals To Assign User to the Terminal Under this Store.");
		}
		
		//alert(tcnt);
	    }
	    else if(valueSelected=="Mobile"){
	    	$('#posdiv').hide();
			$('#mobdiv').show();	
	    }
});
		
		

</SCRIPT>



<script type="text/javascript"> 
/** Form2 Add,Modify Starts*/
var val = 1; 
var rowindex1 = 0;
var colindex1 = 0;
var userstatus1 = "";
var masterstatus1 = "";
var v_message1 = "";

var tabArry ; 
 
var index1 = "";
var searchTdRow1 = "";
var searchTrRows1 = "";
var selTextList1 = ""; 
var rowCount1 = 0;
 
function clearVals(){ 
	$('#chname').val('');
	$('#mobval').val(''); 
	
	 
	rowCount1 = $('#tbody_data1 > tr').length; 
	if(rowCount1 > 0 )  $("#error_dlno").text('');
}

function commonData1(id,type){
	var hiddenInput ="";
	var hiddenNames = "";
	var tabarrindex = 0;
	tabArry = new Array();
	modTabArry = new Array(); 
	try {
 		
		$(id).find('input[type=text],input[type=hidden],select').each(function(indxf){ 
			var nameInput = "";
			var valToSpn =  "";
			try {
				  nameInput = $(this).attr('name'); 
				  valToSpn = $(this).attr('value').length == 0 ? " " : $(this).attr('value');
				  
				 // console.log("nameInput   "+nameInput);
				  //console.log("valToSpn   "+valToSpn);
			} catch(e1) {
				console.log('The Exception Stack is :: '+ e1);
			}  
			 var Code = $("#chname").val();
			 var kk='';
			  if(Code=="POS") kk="Terminalid";
			  else kk="mobval";
			  
			
			if(nameInput != undefined) {
			  if(indxf == 0)  {
				hiddenInput += valToSpn;
				hiddenNames += nameInput;
			  } else {
				  if(nameInput==kk){
					  //alert(valToSpn);
				hiddenInput += ","+valToSpn; 
				hiddenNames += ","+nameInput;
				} 
			  }
			  
			  console.log("hidden names   [ "+hiddenNames +" ]hidden input ["+hiddenInput );
			  
			 
			
			  if(Code=="POS"){
				  //alert(Code);
			  var listid1 = "".split(",");
			  var headerList1 = "chname,Terminalid".split(",");
			  }else if(Code=="Mobile"){
				  //alert(Code);
			  var listid1 = "".split(",");
			  var headerList1 = "chname,mobval".split(",");
			  }
			  
			  
			   if(jQuery.inArray(nameInput, headerList1) != -1){  
					tabArry[tabarrindex] = valToSpn; 
 				  tabarrindex++;
 				  console.log("valToSpn"+valToSpn);
			  }
			} 			 
		}); 
 		
	} catch (e) {
		console.log(e);
	}  
	return hiddenInput+"@@"+hiddenNames;
}

var addAccountInfo1 = function (clickType) {
	 // add custom behaviour
	 var appendTxt = "";
	 var tabArrText = ""; 
	 var data1 = "";
	try { 
		data1 = commonData1('#account-details','ADD');
		 
		rowindex1 = $("#multi-row-data1 > span") .length ;
		
		$("#multi-row-data1").append("<span id='hidden_span_"+rowindex1+"' index="+rowindex1+" ind-id='"+tabArry[0]+"' hid-names='"+data1.split("@@")[1]+"' ></span>");
		$('#multi-row-data1 > span#hidden_span_'+rowindex1).append(data1.split("@@")[0]);
		console.log("row indexxxxxxxxxxx"+tabArry[0] +"assssssssssss"+data1);
		var addclass = ""; 
		
		if(val % 2 == 0 ){
			addclass = "even";
			val++;
		} else {
			addclass = "odd";
			val++;
		}
		
		colindex1 = ++ colindex1;
		
		$(tabArry).each(function(index){ 
			tabArrText+="<td>"+tabArry[index].trim()+"</td> ";
		});
		//alert(tabArrText);
		appendTxt = "<tr class="+addclass+" align='center' id='tr-"+rowindex1+"' index='"+rowindex1+"'>"+
				"<td class='col_"+colindex1+"'>"+colindex1+"</td>"+ tabArrText+ 
 				"<td> <a class='btn btn-min btn-danger' href='#' id='delete-account' index='"+rowindex1+"' title='Delete Account' data-rel='tooltip'> <i class='icon-trash icon-white'></i></a></td></tr>";
 		console.log("hiiiiiiiiii"+appendTxt);
	} catch(e) {
		console.log(e);
	}   
	return appendTxt; 
};
 
/** Form2 Add,Modify Ends*/

function alignSerialNo1(serialId) {  
	if($(serialId).length > 0) {
		$(serialId +' > td:first-child').each(function(index){  
			$(this).text(index+1);
		}); 
	}
}

function recordCheck1(idtocheck,billerId,tid){ 
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
		
		var acctRules = {
		   rules : {
			   chname : { required : true},
			   chval : { required : false}
		   },  
		   messages : {
			   chname : { 
			   		required : "Please Enter Channel Name"
				},
				chval : { 
			   		required : "Please Enter Channel Value."
				}
		   } 
		 };
	
		 $('#add-account').live('click', function(){
			 
			 
	 		$("#error_dlno").text('');	 
			$("#form2").validate(acctRules); 
			 var textMess = "#tbody_data1 > tr";  
			 var flag = false; 
			 
			  if($("#form2").valid() ) {  
					// Is To Check Account Exist's or not 
					var dat = recordCheck1("#tbody_data1 > tr",$('#chname').val(),'2');
 					
						if(dat == 'NO'){
							 
							   
								var appendTxt = addAccountInfo1("ADD");
								//alert(appendTxt);
								console.log("in append "+appendTxt);
		 						$("#tbody_data1").append(appendTxt);  
		 						clearVals();
								alignSerialNo1(textMess); 
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
			$('#multi-row-data1 > span').each(function(index){  
				spanId =  $(this).attr('index'); 
				if(spanId == delId) {
					$(this).remove();
				}
			}); 
			
			clearVals();
			// Aligning the serial number
			alignSerialNo1("#tbody_data1 > tr"); 
		 
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
</script>



</head>

<body>
	<form name="form1" id="form1" method="post">
	<!-- topbar ends -->
	 <div id="content" class="span10">

		    <div>
				<ul class="breadcrumb">
				 <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="userGrpCreation.action">User Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#"> ${type} User </a></li>
				</ul>
			</div>

		 	<div class="row-fluid sortable">

	<div class="box span12">

		<div class="box-header well" data-original-title>
			 <i class="icon-edit"></i>User Details
			<div class="box-icon">
				<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>
		</div>

		<div class="box-content">
			<fieldset>
				<table width="950"  border="0" cellpadding="5" cellspacing="1"
					class="table table-striped table-bordered bootstrap-datatable " >
					 <tr >
							<td width="25%" ><strong><label for="User Id"><strong>User Id</label></strong></td>
							<td>${responseJSON.CV0001} <input type="hidden" name="CV0001"  id="firstName" value="${responseJSON.CV0001}" /></td>
							<td width="25%" ><strong><label for="ID/Driving License"><strong>Employee No</label></strong></td>
							<td> ${responseJSON.CV0002}<input type="hidden" name="CV0002"  id="lastName" value="${responseJSON.CV0002}" /></td>
						</tr>
						<tr >
							<td><strong><label for="First Name"><strong>First Name</label></strong></td>
							<td width="25%" >${responseJSON.CV0003}<input type="hidden" name="CV0003"  id="userId" value="${responseJSON.CV0003}" /> </td>
							<td><strong><label for="Last Name"><strong>Last Name</label></strong></td>
							<td width="25%" >${responseJSON.CV0004}<input type="hidden" name="CV0004"  id="empNo" value="${responseJSON.CV0004}" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="Telephone Res"><strong>Telephone(Res)</label></strong></td>
							<td>${responseJSON.CV0005}<input type="hidden" name="CV0005"  id="telephoneRes" value="${responseJSON.CV0005}" /></td>
							<td><strong><label for="Telephone Off"><strong>Telephone(Off)</label></strong></td>
							<td>${responseJSON.CV0006}<input type="hidden" name="CV0006"  id="telephoneOff" value="${responseJSON.CV0006}" /></td>
						</tr>

						 <tr >
								<td><strong><label for="Mobile"><strong>Mobile</label></strong></td>
								<td>234-${responseJSON.CV0007}<input type="hidden" name="CV0007"  id="mobile" value="${responseJSON.CV0007}" /></td>
								<td><!-- <strong><label for="Fax"><strong>Fax</label></strong> --></td>
								<td><input type="hidden" name="CV0008"  id="fax" value="${responseJSON.CV0008}" /></td>
						 </tr>

						<tr >
						  <td><strong><label for="User Level"><strong>User Level</label></strong></td>
						  <td>
							${responseJSON.CV0009} <input type="hidden" name="CV0009"  id="adminType" value="${responseJSON.CV0009}" />
						 </td>
						 <td><strong><label for="Office Location"><strong>Office Location</label></strong></td>
						  <td>
							${responseJSON.CV0018} <input type="hidden" name="CV0018"  id="officeLocation" value="${responseJSON.CV0018}" />
						   </td>
						</tr>
						<tr >
							<td><strong><label for="E-Mail"><strong>E-Mail</label></strong></td>
							<td>
								${responseJSON.CV0012} <input type="hidden" name="CV0012"  id="email" value="${responseJSON.CV0012}" />
							</td>
							<td><strong><label for="User Status"><strong>User Status</label></strong></td>
							<td>
								<span id="spn-user-status"></span> <input type="hidden" name="CV0013"  id="user_status" value="${responseJSON.CV0013}" />
							</td>
						
						</tr>
					
						<tr id="MerchantInfo">
							<td><strong><label for="Merchant Id"><strong>Merchant Id</label></strong></td>
							<td>
								${responseJSON.CV0015}
							</td>
							<td><strong><label for="Store Id"><strong>Store Id</label></strong></td>
							<td>
								${responseJSON.CV0016}
								<input type="hidden" id="acttype" name="acttype" value="${responseJSON.acttype}" />
							</td>
						</tr>
				</table>
			</fieldset>


		</div>
		</div>
		</div>
		

	<!--/#content.span10-->

 </form>
 <form name="form2" id="form2" method="post"> 	
		<div class="row-fluid sortable" id="adnwac"> 
			<div class="box span12" > 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Add Channel
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
					
				<div class="box-content" id="account-details">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
						
							<td width="20%"><label for="Account Number"><strong>Channel Name<font color="red">*</font></strong></label></td>
							<td width="30%">
							
							<select id="chname" name="chname" style="width: 240px;" class="chosen-select-width" required =true>
									<option value="Select">Select</option>
									<option value="POS">POS</option>
									<option value="Mobile">Mobile</option>
								</select> 
							 </td>
							<td width="20%"><label for="Channel value"><strong>Channel Value</strong></label> </td>
							<td width="30%"><div id="posdiv" style="display:none">
							<select id="Terminalid" name="Terminalid" data-placeholder="Choose Terminal..."
										class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option>
						</select></div>
						<div id="mobdiv" ><input name="mobval" id="mobval"  type="text" class="field" required=true  maxlength="50" /></div>
							</td>
						</tr>  
						<tr class="odd"> 
								<td colspan="4" align="center">
									<input type="button" class="btn btn-success" 
										name="add-account" id="add-account" value="Add Channel" /> &nbsp;  
									<span id="billerMsg" class="errors"></span>
								</td> 
						</tr> 
 				 </table>
				</fieldset> 
				</div> 
				
			<div class="box-content"> 
						<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
									id="mytable" style="width: 100%;">
						  <thead>
								<tr>
									<th>Sno</th>
									<th>Channel Name</th>
									<th>Channel Value </th>
									<th>Actions</th> 
								</tr>
						  </thead>    
							 <tbody id="tbody_data1"> 
							 	<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
									<s:param name="jsonData" value="%{accBean.newAccountData}"/>  
									<s:param name="searchData" value="%{'#'}"/>  
								</s:bean> 
								<s:iterator value="#jsonToList.data" var="mulData2"  status="mulDataStatus" >   
											<script type="text/javascript">
												var hiddenNames1 = "";
												$(function(){
													$('#account-details').find('input[type=text],input[type=hidden]').each(function(index){ 
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
												<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:if>
											<s:elseif test="#mulDataStatus.odd == true">
												<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
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
			
					<span id="multi-row-data1" class="multi-row-data1" style="display:none"> </span>
			  </div>
		</div>  
		
		<div id="dialog" title="Confirmation Required" style="display:none">
		   Proceed ? 
		</div>
		
</form>	

<div class="form-actions">
		   <input type="button" class="btn btn-primary" type="text"  name="btn-submit" id="btn-submit" value="Next"   ></input>
		  </div>
 
 </div>
</body>
</html>

