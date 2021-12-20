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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/image/jquery.canvasCrop.js" ></script>

<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js"></script>
<script type="text/javascript">

 
$(function() {   
	
/* 	$("#dialog").dialog({
			autoOpen: false,
			modal: false,

	      
   }); */
	
   
   $('#telco').val('<s:property value="accBean.telco" />');
   $('#telco').trigger("liszt:updated");

   $('#langugae').val('<s:property value="accBean.langugae" />');
   $('#langugae').trigger("liszt:updated");
   
   
  
		
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
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modregcustinfo.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){ 
		var finalData = "";
		 var allVals = [];
		 
		 var queryString = "method=searchValidation&fname="+ $('#customercode').val();	
			$.getJSON("postJson.action", queryString,function(data) { 
				
				
			if(data.message!="SUCCESS"){
				
				
				$('#errors').text(data.message);
			}else{
		
		
			 	subitReq();
			 	if($("#multiData1").val()=="" || $("#multiData1").val()=="[]"){
	 				$("#errormsg").html("<font colour='red'>Please atleast one new Account</font>");
	 				
	 			}else{ 
			 

				 $("#dialog").dialog({
				      buttons : {
				        "Confirm" : function() { 
				        	  //subitReq();
				        	  
				 		
				        	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/addnewaccountDetConfirm.action';
							$("#form1").submit();
							 return true; 
				 			
				 			 
				        },
				        "Cancel" : function() {
				           $(this).dialog("close");
				        }
				      } 
				    });
				  
				
	 			} 
				
			  
			}			
			});
		
	});
});	




$(function() {  
	
	
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
		    
		    
		   /*  $('#product1').on('change', function() {
		    	 // alert( this.value ); 
		    	  $('#desc').text((this.value).split("-")[1]);
		    	  $('#product').val((this.value).split("-")[0]);
		    	  $('#prodesc').val((this.value).split("-")[1]);
		    	});
		    
		    
		    var queryString = "";
    		
	    	$.getJSON("productajx.action", queryString, function(data) {
	 			if(data.region!=""){
	 			//	alert(data.region);
	 				var mydata=data.region;
	      			//var mydata=(data.region).substring(5,(data.region).length);
	      			var mydata1=mydata.split(":");
	      
	       			$.each(mydata1, function(i, v) {
	       				if((mydata.split(":")[i]).split("@")[2]=="Mobile Banking"){
	       				var options = $('<option/>', {value: (mydata.split(":")[i]).split("@")[0], text: (mydata.split(":")[i]).split("@")[1]}).attr('data-id',i);
	       				}
	       				$('#product1').append(options);
	       				$('#product1').trigger("liszt:updated");
	       			});
	       			
	       			
	      		} 
	     	});  */
	  //alert($('#product').val());  	
	    	
});
	 
</script>
<script type="text/javascript"> 
/** Form2 Add,Modify Starts*/
var val = 1; 
var rowindex = 0;
var colindex = 0;
var userstatus = "";
var masterstatus = "";
var v_message = "";
var listid = "".split(",");
var headerList = "accountNumber,aliasName,acctName,branchCode,prdCode".split(",");
var tabArry ; 
 
var index1 = "";
var searchTdRow = "";
var searchTrRows = "";
var selTextList = ""; 
var rowCount = 0;
 
function clearVals(){ 
	$('#accountNumber').val('');
	$('#aliasName').val(''); 
	
	 
	rowCount = $('#tbody_data > tr').length; 
	if(rowCount > 0 )  $("#error_dlno").text('');
}

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
		data1 = commonData('#account-details','ADD');
		 
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
		
		colindex = ++ colindex;
		
		$(tabArry).each(function(index){ 
			tabArrText+="<td>"+tabArry[index].trim()+"</td> ";
		});
		
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



 

$(function() { 
	
	
	
	$('#accountNumbers').on('change', function() {
		
		
		var conval=true;
		var accno=this.value;
		$('#ex-mytable tr').each(function() {
		    var customerId = $(this).find("td").eq(1).html();  
		   // alert(customerId);
		   if(customerId!=null){
			   if(customerId==accno){
				   conval=false;
			    		
			    } 
		   }
		});

		$("#accountName").val("");
		$("#branchCode").val("");
		$("#acctCurrCode").val("");
		$("#mobile").val("");
		$("#acctype").val("");
   	if(conval){  
   		$('#wt').html("Please Wait ..");
	var queryString9 = "method=fetchAddNewAccDatafromCore&accNumber="+this.value;
		
		$.getJSON("postJson.action", queryString9,function(data) {
			var message = data.message;
			if(message=="success"){
				var custid = data.custcode; 			
				var fullname = data.accname; 
				var mobile = data.mobile; 
				var v_message = data.service;
				var branchCode = data.brcode;
				var acctype = data.email;
				
				
				$("#accountName").val(fullname);
				$("#branchCode").val(branchCode);
				$("#acctCurrCode").val(v_message);
				$("#mobile").val(mobile);
				$("#acctype").val(acctype);
				$('#errormsg').text("");
				$('#wt').text("");
			}else{
				$('#errormsg').text(message);
			}	
				
					
		});
   	}else{
		$('#wt').html("This Account Already Exists");
	}
   
   	});
	
	var custid=$("#customercode").val();
	 var queryString = "sid="+custid;
		
 	$.getJSON("accountajx.action", queryString, function(data) {
			if(data.region!=""){
			//	alert(data.region);
				var mydata=data.region;
   			//var mydata=(data.region).substring(5,(data.region).length);
   			var mydata1=mydata.split(":");
   
    			$.each(mydata1, function(i, v) {
    				var options = $('<option/>', {value: mydata.split(":")[i], text: mydata.split(":")[i]}).attr('data-id',i);
    			
    				$('#accountNumbers').append(options);
    				$('#accountNumbers').trigger("liszt:updated");
    			});
    			
    			
   		} 
  	}); 	
});


/* var acctRules = {
		   rules : {
			   accountNumbers : { required : true},
			   accountName : { required : true},
			   acctCurrCode : { required : true},
			   branchCode : { required : true},
			   acctype : { required : true}
		   },  
		   messages : {
			   accountNumbers : { 
			   		required : "Please enter Account Number."
				},
				accountName : { 
			   		required : "required Account Name."
				},
				acctCurrCode : { 
			   		required : "required Currency Code."
				},
				branchCode : { 
			   		required : "required Branch Code."
				},
				acctype : { 
			   		required : "required Account type."
				}
		   } 
		 };
		 */
	


var branchdata="accountNumbers|selecttext#accountName|text#acctCurrCode|text#branchCode|text#acctype|text#mobile|text#"; 

var branchdatajsonObj = [];


function addrow()
{

		/* $("#form1").validate(acctRules);
		if($("#form1").valid()) { */
		console.log("Addrow >>>> ");
		var myval =  buildSingleRequestjson(branchdata);
		var finalobj = jQuery.parseJSON(myval);
		
		if(finalobj.accountNumbers!="" && finalobj.accountName!="") { 
		
			
			
			var status = validateData(finalobj,branchdatajsonObj,1); 
			

				if(status){
					
						$("#errormsg").html("<font colour='red'>Same Account Already Exists</font>");
					
					}else {
						//alert(finalobj);
						branchdatajsonObj.push(finalobj);
						$("#errormsg").empty();
						makeempty();
					
					}
		
			 
			var totval = JSON.stringify(branchdatajsonObj);	
			console.log("Final Json Object ["+totval+"]");
		
			
			buildfeetable(branchdatajsonObj);
			
			$("#submitdata").show();
		
			return true;
		
		  }else{
			 $("#errormsg").html("<font colour='red'>reqired all details</font>");
			return false;
		
		}  
		/* }else{
			return false;
		} */
		
 
 }
 
 

function validateData(myval,myfinalobj,val)
{

	var status = false;
	
	var accountNumbers = myval.accountNumbers;
	

	
	$.each(myfinalobj, function(index,jsonObject)
	{ 
			var tabaccountNumbers = jsonObject.accountNumbers;
			
			
			
			if((accountNumbers==tabaccountNumbers) )
			{
				status=true;
			}

	});
	
	return status;

}
var listid = "accountNumbers,".split(","); 
function makeempty()
{
	$(listid).each(function(index,val){ 
		$('#'+val).find('option').each(function( i, opt ) { 
			if( opt.value == '' ) {
				$(opt).attr('selected', 'selected');
				$('#'+val).trigger("liszt:updated");
			}
		}); 
	});		

//	$("#accountNumbers").val("");
	$("#accountName").val("");
	$("#acctCurrCode").val("");
	$("#branchCode").val("");
	$("#acctype").val("");
	$("#mobile").val("");
} 
 

function buildfeetable(jsonArray)
{

	$("#tbody_data").empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
		
			var data = JSON.stringify(jsonObject);
			console.log("Data ["+data+"]");
			
			
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=Transaction >" + i + "</td>";
			htmlString = htmlString + "<td id='accountNumbers' name=accountNumbers >" + jsonObject.accountNumbers + "</td>";
			htmlString = htmlString + "<td id='accountName' name=accountName >" + jsonObject.accountName + "</td>";
			htmlString = htmlString + "<td id='acctCurrCode' name=acctCurrCode >" + jsonObject.acctCurrCode + "</td>";
			htmlString = htmlString + "<td id='branchCode' name=branchCode >" + jsonObject.branchCode + "</td>";	
			htmlString = htmlString + "<td id='acctype' name=acctype >" + jsonObject.acctype + "</td>";
			htmlString = htmlString + "<td id='mobile' name=mobile >" + jsonObject.mobile + "</td>";	
			htmlString = htmlString + "<td><a id='remove' class='btn btn-info'  href='#'   title='Remove' data-rel='tooltip' ><i class='icon-remove'  onclick='deletedata("+data+")' ></i></a>&nbsp</td>";
			
			htmlString = htmlString + "</tr>";

	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	if(htmlString=='')
		$("#submitdata").hide();
	
	$("#tbody_data").append(htmlString);

}


var dataupdateval;

function upatedata(myval)
{

	$("#ADD").hide();
	$("#UPDATE").show();
	

	
	$("#accountNumbers").val(myval.accountNumbers);
	$("#accountName").val(myval.accountName);
	$("#acctCurrCode").val(myval.acctCurrCode);
	$("#branchCode").val(myval.branchCode);
	$("#acctype").val(myval.acctype);
	$("#mobile").val(myval.mobile);
	
	dataupdateval=myval; 
	
}

function adddeletedata(val)
{

	console.log("Val ["+val+"]");
	fillsingledata(branchdata,val);
	dataupdateval=val;
	viewdata(val);
	
	// $("#Remove").show();
	
}

function deletedata(val){

	var finaljsonobj=[];

	var r = confirm("Do You Want To Remove?");
	
	if (r == true) {

	console.log("Final Value ["+val+"]");

	dataupdateval=val;
	
	var accountNumbers = dataupdateval.accountNumbers;
	
	
	$.each(branchdatajsonObj, function(index,jsonObject){ 
		
		 	var tabaccountNumbers = jsonObject.accountNumbers;
		
			if((tabaccountNumbers==accountNumbers))
			{
				
			}else{
				
				finaljsonobj.push(jsonObject);
			}
			

	});
	
	branchdatajsonObj =[];
	branchdatajsonObj = finaljsonobj;
	
	// acqjsonObj.push(finalobj);
	
	buildfeetable(branchdatajsonObj);
		
	} 

	makeempty();	

}

function updaterow()
{
	/* $("#form1").validate(datavalidation);
	if($("#form1").valid()) {  */
	checkEqual('U');
	/* makeempty(); */
	
	/* $("#ADD").show();
	$("#UPDATE").hide(); */
	/* } */
	
}


function checkEqual(val)
{

	var finaljsonobj=[];

	var myval =  buildSingleRequestjson(branchdata); 
	var newupdateddataobj = jQuery.parseJSON(myval);
	
	var accountNumbers = dataupdateval.accountNumbers;
	 $("#errormsg").html("");
	//alert(newupdateddataobj.state);
	if(newupdateddataobj.state!="Select") { 
	$.each(branchdatajsonObj, function(index,jsonObject)
	{ 
		 	// alert(jsonObject.Services);
		
		 	var tabaccountNumbers = jsonObject.accountNumbers;
		
			
			// && (branchaddress==tabbranchaddress)
		
			
			 if((accountNumbers==tabaccountNumbers))
				{ 
					if(val=='U'){
						finaljsonobj.push(newupdateddataobj);
						
					}
					
				}else{
				
					finaljsonobj.push(jsonObject);
				
				}
			 
			
			

	});
	
	branchdatajsonObj =[];
	branchdatajsonObj = finaljsonobj;
	
	//acqjsonObj.push(finalobj);
	
	buildfeetable(branchdatajsonObj);
	makeempty();
	$("#ADD").show();
	$("#UPDATE").hide();
	
	}else{
		 $("#errormsg").html("<font colour='red'>Please Select State</font>");
		return false;
	
	} 

}



function subitReq()
{   

		var totval = JSON.stringify(branchdatajsonObj);	 
	//	alert(totval);
			
		var myEscapedJSONString = totval.replace(/\\n/g, " ")
                                      .replace(/\\'/g, " ")
                                      .replace(/\\"/g, ' ')
                                      .replace(/\\&/g, " ")
                                      .replace(/\\r/g, " ")
                                      .replace(/\\t/g, " ")
                                      .replace(/\\b/g, " ")
                                      .replace(/\\f/g, " ");
									  
		console.log("After Final Json Object ["+totval+"]");

		console.log("myEscapedJSONString ["+myEscapedJSONString+"]");
		
		totval = myEscapedJSONString;
		
		$("#multiData1").val(totval);
		
		
} 



/** Form2 Add Row Validation Ends*/
</script>
</head> 

<body>

		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Add New Account</a>  </li> 
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
				<div id="errormsg" class="errores"></div>
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
						<tr class="even">
							<td width="25%"><label for="From Date"><strong>Customer Id</strong></label></td>
							<td width="30%"><s:property value='accBean.customercode' /><input type="hidden" name="customercode"  id="customercode"   value="<s:property value='accBean.customercode' />"   /></td>
							<td width="25%"><label for="To Date"><strong>Customer Name</strong></label> </td>
							<td width="30%"><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<%-- <td ><label for="From Date"><strong>Branch Code</strong></label></td>
							<td ><s:property value='accBean.branchcode' /> <input type="hidden" name="branchcode"  id="branchcode"   value="<s:property value='accBean.branchcode' />"   />  </td> --%>
							<td><label for="To Date"><strong>Mobile Number<font color="red">*</font></strong></label> </td>
					       <td ><s:property value='accBean.telephone' />
					       <input type="hidden" value='<s:property value='accBean.telephone' />'  name="telephone" id="telephone" /> 
								
								<input type="hidden" value="<s:property value='accBean.telco' />" style="width:85px;"  name="telco" id="telco"/>&nbsp;
								<input type="hidden" value="<s:property value='accBean.isocode' />" style="width:25px;"  name="isocode" id="isocode"/>&nbsp;
								<input type="hidden" value='<s:property value='accBean.telephone' />' style="width: 80px;"  name="telephone" id="telephone" style="width:130px;" /> 
 							</td>
								
 							</td>
							<td  ><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value='accBean.idnumber' />
							<input type="hidden" style="width:85px;"  name="idnumber"  id="idnumber"   value="<s:property value='accBean.idnumber' />"   />  </td>
					
						</tr>
						<tr class="even">
							<td ><label for="From Date"><strong>Email ID</strong></label></td>
							<td ><s:property value='accBean.email' />
							<input type="hidden" name="email"  id="email"   value="<s:property value='accBean.email' />"   />  </td>
							<td ><label for="To Date"><strong>Preferred Language </strong></label> </td> 
							<td ><s:property value="accBean.langugae" />
							<input type="hidden" name="langugae"  id="langugae"   value="<s:property value='accBean.language' />"   />  
							
							         
							    <input type="hidden" name="institute"  id="institute"   value="<s:property value='accBean.	institute' />"   />
						       	<input type="hidden" name="newAccountData" id="newAccountData" value="<s:property value='accBean.newAccountData' />" />
								<input type="hidden" name="multiData"  id="multiData"   value="<s:property value='accBean.multiData' />"  />
								<input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
							</td>	  
						</tr>
						<tr class="even">
							<td ><label for="Product"><strong>Product</strong></label></td>
							<td ><s:property value='accBean.product' />
							</td>
							 <td><label for="Description"><strong>Description</strong></label></td>
					       <td><s:property value='accBean.prodesc' /></td>  
						</tr>
						
						
						
				 </table>
				 <input type="hidden"   name="apptype"  id="apptype"   value="<s:property value='accBean.apptype' />"   />
				</fieldset> 
				</div>
				</div> 
					<input type="hidden"  name="product" id="product"  value="<s:property value='accBean.product' />" />
				 	<input type="hidden"  name="prodesc" id="prodesc"  value="<s:property value='accBean.prodesc' />" />
				 	
				<div class="row-fluid sortable"> 
			
						<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Existing Accounts
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
				<div class="box-content"> 
						<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
									id="ex-mytable" style="width: 100%;">
						  <thead>
								<tr>
									<th>Sno</th>
									<th>Account Number</th>
									<th>Account Name </th>
									<th>Branch Code</th>
									<th>Account Product</th>
									<th>Account Currency</th>
								
         							
								</tr>
						  </thead>    
						  <tbody id="tbody_data1"> 
						  
						  		<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{accBean.multiData}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
								</s:bean> 
								<s:iterator value="#jsonToList.data" var="mulData1"  status="mulDataStatus" > 
										<s:if test="#mulDataStatus.even == true" > 
											<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
										</s:if>
										<s:elseif test="#mulDataStatus.odd == true">
											<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
										</s:elseif> 
									
										<td><s:property value="#mulDataStatus.index+1" /></td>
											<s:generator val="%{#mulData1}"
												var="bankDat" separator="," >  
												<s:iterator status="itrStatus">  
														<td><s:property /></td> 
												</s:iterator>  
											</s:generator> 
										 
									</tr> 
								</s:iterator>
						  
						  </tbody>
						</table> 
					</div> 
					
					<div class="row-fluid sortable"><!--/span-->
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Add New Account
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							</div>
						</div>
						<div id="primaryDetails" class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd">
										<td width="20%"><label for="Super Agent Name"><strong>Account Number</strong><font color="red">*</font></label></td>
										<td width="30%">
										<select id="accountNumbers" name="accountNumbers" class="chosen-select-width" >
										 <option value="">Select</option>
										</select>
										<span id="accountNumbersMsg"></span>
										</td>
										<td width="20%"><label for="Super Agent Name"><strong>Account Name</strong></label></td>
										<td><input type="text" name="accountName" id="accountName" readonly></td>
									</tr>
									<tr class="even">
										<td><label for="Account Currency Code"><strong>Account Currency Code</strong></label></td>
										<td><input type="text" name="acctCurrCode" id="acctCurrCode" readonly></td>
										<td><label for="Branch Code"><strong>Branch Code</strong></label></td>
										<td><input name="branchCode" id="branchCode" type="text" readonly></td>
									</tr>
									<tr class="odd">
										<td><label for="Email"><strong>Account Type</strong></label></td>
										<td><input type="text" name="acctype" id="acctype" readonly></td>
										<td ><label for="Mobile"><strong>Mobile</strong></label></td>
										<td><input name="mobile" id="mobile" type="text"  readonly></td>
									</tr>
									
									
									

								</table>
							 </fieldset>
							 
							 
							 <div  class="box-content" >	
	
	<input type="hidden" id="reqjson" name="reqjson"  />
		<input type="button" id="ADD" class="btn btn-success" onclick="addrow();" value="Add" /> &nbsp;&nbsp;<span id="wt" style="color:red"></span>
		<input type="button" id="UPDATE" class="btn btn-success" onclick="updaterow();" value="Update" style="display:none"/>
			<br/>
		
			<br/>	
		
		
		
		<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										<th width="5%">Sno</th>
										<th width="12%">Account Number</th>
										<th width="12%">Account Name</th>
										<th width="12%">Currency Code</th>
										<th width="12%">Branch Code</th>
										<th width="12%">Account Type</th>
										<th width="12%">Mobile</th>								
										<th width="12%">Action</th>
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							 
		</table>
		
		<input type="hidden" name="multiData1"  id="multiData1"     />
		
		</div> 
							</div>
				</div>
				</div>	
		</div> 
		</div> 
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

	<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body> 
</html>