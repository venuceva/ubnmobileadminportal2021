<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>BackOffice</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%> 
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/lightbox.css"  type="text/css" />

<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js"></script>
 
<script type="text/javascript" > 

function redirectAct()
{
	$("#form2")[0].action='<%=request.getContextPath()%>/<%=appName %>/channelmanagement.action';
	$("#form2").submit();
	return true;
} 

var finaljson; 

$(document).ready(function () {
	
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
		    
		    $('#Product').on('change', function (e) {
		    	var valueSelected = this.value;
		    	//alert(valueSelected);
		    	
		    	$('#prddes').text(valueSelected.split("-")[1]);
		    	$('#prdcry').text(valueSelected.split("-")[2]);
		    	$('#appl').text(valueSelected.split("-")[3]);
		    	
		    	$('#productcode').val(valueSelected.split("-")[0]);
		    	$('#productdesc').val(valueSelected.split("-")[1]);
		    	$('#productcurr').val(valueSelected.split("-")[2]);
		    	$('#application').val(valueSelected.split("-")[3]);
		    	
		    	
		    	
		    	document.getElementById("FeeTransaction").length=1;
		    	var queryString = "entity=${loginentity}&product="+valueSelected.split("-")[0]+"&type=FEE";

		    	
		    	$.getJSON("retriveServise.action", queryString, function(data) {
		 			if(data.region!=""){
		 				var mydata=data.region;
		      			var mydata1=mydata.split(":");
		      
		       			$.each(mydata1, function(i, v) {
		       				var options = $('<option/>', {value: (mydata.split(":")[i]), text: (mydata.split(":")[i])}).attr('data-id',i);
		       				
		       				$('#FeeTransaction').append(options);
		       				$('#FeeTransaction').trigger("liszt:updated");
		       			});
		       			
		       			
		      		}else{
		      			$('#FeeTransaction').append("");
	       				$('#FeeTransaction').trigger("liszt:updated");
		      		} 
		     	}); 
		    	
		    	
		    });

	/* var branchs ='${responseJSON.BRANCHS}';
	finaljson = jQuery.parseJSON(branchs);
	console.log(finaljson); */
	
});

 
var datavalidation = {
 		
 		rules : {
 			
 			branchcode : { required : true ,digits: true } ,
			branchname : { required : true} ,
			branchaddress	: { required : true} 
			// branchaccno	: { required : true}
			
 		},		
 		messages : {
 			
			branchcode : { 
 							required : "Please Enter Branch Code.",
 							digits : "Branch Code , can not allow character or special characters."
					 },
 			branchname : { 
 							required : "Please Enter Branch Name."
					 },
			branchaddress : { 
 							required : "Please Enter Branch Address."
					 }
			/** branchaccno : { 
 							required : "Please Enter Branch Account Number."
					 }
			**/
					 
 		} 
 };
 
 
var branchdata="Channel|selecttext#FeeTransaction|selecttext#account|text#"; //branchaccno|text#

var branchdatajsonObj = [];
 
function addrow()
{
 
		$("#form1").validate(datavalidation);
		
		console.log("Addrow >>>> ");

		
		if($("#form1").valid()) { 
		
			var myval =  buildSingleRequestjson(branchdata);
			var finalobj = jQuery.parseJSON(myval);
			
			/* var status = validateData(finalobj,branchdatajsonObj,1);
			
			var status2 = validateData(finalobj,finaljson,2);
			
			
			console.log("status  ["+status+"] status2 ["+status2+"]" );
			
				if(status){
					
						$("#errormsg").html("<font colour='red'>Branch Code Allready Exists</font>");
					
					}else if(status2){

						$("#errormsg").html("<font colour='red'>Branch Code Allready Exists in system</font>");
					
					}else {
						
						branchdatajsonObj.push(finalobj);
						$("#errormsg").empty();
						makeempty();
					
					}
			 */
			 branchdatajsonObj.push(finalobj);
				$("#errormsg").empty();
				makeempty();
			 
			var totval = JSON.stringify(branchdatajsonObj);	
			console.log("Final Json Object ["+totval+"]");
		
			
			buildfeetable(branchdatajsonObj);
			
			$("#submitdata").show();
		
			return true;
		
		}else{
		
			return false;
		
		}
		
 
 }
 
 

function validateData(myval,myfinalobj,val)
{

	var status = false;
	
	var branchcode = myval.branchcode;
	var branchname  = myval.branchname;
	// var branchaccno  = myval.branchaccno;

	//console.log("myfinalobj >> ["+myfinalobj+"]")
	console.log("val >>>>> ["+val+"]");
	
	$.each(myfinalobj, function(index,jsonObject)
	{ 
			var tabbranchcode = jsonObject.branchcode;
			var tabbranchname  = jsonObject.branchname;
			// var tabbranchaccno = jsonObject.branchaccno;
			
			console.log("tabbranchcode ["+tabbranchcode+"] branchcode ["+branchcode+"]");
			
			if((branchcode==tabbranchcode) ) //&& (branchname==tabbranchname) && (branchaccno==tabbranchaccno) 
			{
				status=true;
			}

	});
	
	return status;

}

var listid = "Channel,FeeTransaction".split(","); 
function makeempty()
{

	$(listid).each(function(index,val){ 
		$('#'+val).find('option').each(function( i, opt ) { 
			if( opt.value === '' ) {
				$(opt).attr('selected', 'selected');
				$('#'+val).trigger("liszt:updated");
			}
		}); 
	});		
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
			htmlString = htmlString + "<td id='Channel' name=Channel >" + jsonObject.Channel + "</td>";
			htmlString = htmlString + "<td id='FeeTransaction' name=FeeTransaction >" + jsonObject.FeeTransaction + "</td>";	
			htmlString = htmlString + "<td id='account' name='account' >" + jsonObject.account + "</td>";
			htmlString = htmlString + "<td><a class='btn btn-warning' href='#' id='modify'   title='Modify' data-rel='tooltip'  onclick='upatedata("+data+")' >"+ 
				"<i class='icon icon-edit icon-white'></i></a>&nbsp;"+
				// "<a id='view' class='btn btn-success'  href='#' title='View' data-rel='tooltip' onclick='viewdata("+data+")'><i class='icon icon-book icon-white'></i></a>&nbsp;"+
				"<a id='remove' class='btn btn-info'  href='#'   title='Remove' data-rel='tooltip' ><i class='icon-remove'  onclick='deletedata("+data+")' ></i></a>&nbsp</td>";
			
			htmlString = htmlString + "</tr>";

	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	if(htmlString=='')
		$("#submitdata").hide();
	
	$("#tbody_data").append(htmlString);

}


function subitReq()
{   

		var totval = JSON.stringify(branchdatajsonObj);	 
			
			
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
		//alert(totval);
		$("#finaljsonarray").val(totval);
		
		$("#form2")[0].action='<%=request.getContextPath()%>/<%=appName %>/finaccountmappingconfirm.action';
		$("#form2").submit();
		return true; 
			
} 


var dataupdateval;

function upatedata(myval)
{

	$("#ADD").hide();
	$("#UPDATE").show();
	
	/* console.log("Val ["+myval.Channel+"] ");
	console.log("Val ["+myval.Services+"] ");
	console.log("Val ["+myval.Limit+"] ");
	console.log("Val ["+myval.Fee+"] "); */
	//fillsingledata(branchdata,val);
	
	/* var totval = JSON.stringify(val);	
			console.log("Final Json Object ["+totval+"]"); */
	//alert(totval);
			
	//console.log(listid);
	
	$(listid).each(function(index,val){
		$('#'+val).find('option').each(function( i, opt ) { 
			if(val=="Services"){
				
				if((opt.value).split("-")[1] == myval.Services) {
					$(opt).attr('selected', 'selected');
					$('#'+val).trigger("liszt:updated");
				}
			}
			
			if(val=="Channel"){
				
				if((opt.value).split("-")[1] == myval.Channel) {
					$(opt).attr('selected', 'selected');
					$('#'+val).trigger("liszt:updated");
				}
			}
			
			/* if(val=="Limit"){
				if( opt.value == myval.Limit) {
					$(opt).attr('selected', 'selected');
					$('#'+val).trigger("liszt:updated");
				}
			}
			
			if(val=="Fee"){
				if( opt.value == myval.Fee) {
					$(opt).attr('selected', 'selected');
					$('#'+val).trigger("liszt:updated");
				}
			} */
			
				
		});
		
	});	
	
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

	//dataupdateval=val;
	
/* 	var Channel = dataupdateval.Channel;
	var Services  = dataupdateval.Services;
	var Limit = dataupdateval.Limit;
	var Fee  = dataupdateval.Fee; */
			
	$.each(branchdatajsonObj, function(index,jsonObject){ 
		
		/* var tabChannel = jsonObject.Channel;
		var tabServices  = jsonObject.Services;
		var tabLimit = jsonObject.Limit;
		var tabFee = jsonObject.Fee; */
			
			// && (branchaddress==tabbranchaddress)
			
			
			
				finaljsonobj.push(jsonObject);
		
			

	});
	
	branchdatajsonObj =[];
	branchdatajsonObj = finaljsonobj;
	
	// acqjsonObj.push(finalobj);
	
	buildfeetable(branchdatajsonObj);
		
	} 

	

}

function updaterow()
{

	checkEqual('U');
	makeempty();
	
	$("#ADD").show();
	$("#UPDATE").hide();
	
	
}


function checkEqual(val)
{

	var finaljsonobj=[];

	var myval =  buildSingleRequestjson(branchdata); 
	var newupdateddataobj = jQuery.parseJSON(myval);
	
	var Channel = dataupdateval.Channel;
	var FeeTransaction  = dataupdateval.FeeTransaction;
	var account = dataupdateval.account;
	
	$.each(branchdatajsonObj, function(index,jsonObject)
	{ 
		 	// alert(jsonObject.Services);
		
		 	var tabChannel = jsonObject.Channel;
			var tabFeeTransaction  = jsonObject.FeeTransaction;
			var tabaccount = jsonObject.account;
			
			// && (branchaddress==tabbranchaddress)
			
			 if((Channel==tabChannel) && (FeeTransaction==tabFeeTransaction)  && (account==tabaccount))
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


}



function viewdata(val){

	console.log("Val ["+val+"]");

	//var jsondata = jQuery.parseJSON(val);
	
	 var viewdata="branchcode|text#branchname|text#branchaddress|text#branchaccno|text#";
				
	fillsingledata(viewdata,val);
	$("#Remove").hide();
	
	//lightbox_open();
				 
}


	
</script>
<s:set value="responseJSON" var="respData" />
</head>

<body>
	
	<div id="content" class="span10">  
			<div>
				<ul class="breadcrumb">
				 <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="finaccountmapping.action">Financial Account Mapping</a> </li>
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
		 <form name="form1" id="form2" method="post" action="">
		 <div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Product Details  
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>
						</div>
					</div>


					<div class="box-content" id="terminalDetails">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="Product Code"><strong>Product Code<font color="red">*</font></strong></label></td>
									<td width="30%">
									<s:select cssClass="chosen-select" headerKey=""
															headerValue="Select" list="#respData.PRODUCT_TEMP"
															name="Product" id="Product" requiredLabel="true"
															theme="simple" data-placeholder="Choose Channel"
															required="true" /> 
															
									<input name="productcode" maxlength='25'
										type="hidden" id="productcode" class="field" />
									</td>
									<td width="20%"><label for="Product Description"><strong>Product Description</strong></label></td>
									<td width="30%"><div id="prddes"></div>
									<input name="productdesc" maxlength='25'
										type="hidden" id="productdesc" class="field" />
									</td>
								</tr>
								<tr class="even">
									
									<td><label for="Product Currency"><strong>Product Currency</strong></label></td>
									<td><div id="prdcry"></div>
									<input name="productcurr" maxlength='25'
										type="hidden" id="productcurr" class="field" /></td>
									<td><label for="Application"><strong>Application</strong></label></td>
									<td><div id="appl"></div>
									<input name="application" maxlength='25'
										type="hidden" id="application" class="field" /></td>
									
								</tr>
								

								
							</table>
							


						</fieldset>
					</div>
				</div>
				</div>
				<input type="hidden" id="finaljsonarray" name="finaljsonarray" >
		 </form>
		 
		<div class="row-fluid sortable"><!--/span--> 
			<div class="box span12">  
					<form name="form1" id="form1" method="post" action="">
					<div id="primaryDetails" class="box-content">
						
							<div class="box span12"> 
					<div class="box-header well" data-original-title>Financial Account Mapping
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
			<div class="box-content"> 				
			<fieldset>
			
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							   class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
													
													<tr class="odd">
														<td width="20%"><label for="Channel"><strong>Channel<font color="red">*</font></strong></label></td>
														<td width="30%">
														<s:select cssClass="chosen-select" headerKey=""
															headerValue="Select" list="#respData.CHANNEL_MASTER"
															name="Channel" id="Channel" requiredLabel="true"
															theme="simple" data-placeholder="Choose Channel"
															required="true" /> 
														
														</td>											
														<td width="20%"></td>
														<td width="30%"></td>	
														
													</tr>
								
													<tr class="even">
													
														<td width="20%"><label for="FeeTransaction"><strong>Transaction</strong></label></td>
														<td width="30%">
															<select id="FeeTransaction" name="Transaction" class="chosen-select-width" >
								 								<option value="">Select</option>
															</select>
														</td>	
														
														<td width="20%"><label for="Fee"><strong>Account Number</strong></label> </td>
														<td width="30%">
														<input type="text" id="account" name="account" required="true"  class="field" />
														 </td>
													
													</tr>
													
													
						</table>
							
			</fieldset>
			
			</div> 
			
			</div> </div> 
			
			</form>

			<br/>	
				
			
	<div  class="box-content" >	
	
	<input type="hidden" id="reqjson" name="reqjson"  />
		<input type="button" id="ADD" class="btn btn-success" onclick="addrow();" value="Add" /> &nbsp;&nbsp;
		<input type="button" id="UPDATE" class="btn btn-success" onclick="updaterow();" value="Update" style="display:none"/>
			<br/>
			<div id="errormsg" class="errores"></div>
			<br/>	
		
		
		
		<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										<th>Sno</th>
										<th>Channel</th>
										<th>Transaction</th>
										<th>Account Number</th>
										<th>Action</th>
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							 
		</table>
		
		 
		
		</div> 
		
		
		<form name="form2" id="form2" method="post" action="">
		<div class="form-actions" id="submitdata" style="display:none"> 
				
				
				
				<input type="button" id="non-printable" class="btn btn-success" onclick="redirectAct();" value="Back" />
				<input type="button" id="non-printable" class="btn btn-success" onclick="subitReq();" value="Submit" />
		</div>
		
		</form>
		
		</div>
		
		</div>
		
		
		 
		
 
	</div>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 

</body>
</html>
