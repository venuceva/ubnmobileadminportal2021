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
	$("#form2")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantmenagenet.action';
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
		    
		    $( "#adminName" ).keyup(function() {
				
				$( "#adminName" ).val((this.value).toUpperCase());
			});	
		    
 			$( "#adminType" ).keyup(function() {
				
				$( "#adminType" ).val((this.value).toUpperCase());
			});	
	
});

 
var datavalidation = {
 		
 		rules : {
 			adminType	: { required : true},
 			adminName : { required : true}
			
 		},		
 		messages : {
 			
 		 adminType : { 
 							required : "Please Select Merchant Id",
					 },
		 adminName : { 
 							required : "Please Select Merchant Name."
					 }
			
					 
 		} 
 };
 
 
var branchdata="adminType|text#adminName|text#"; //branchaccno|text#

var branchdatajsonObj = [];
 
function addrow()
{
 
		$("#form1").validate(datavalidation);
		
		console.log("Addrow >>>> ");

		
		if($("#form1").valid()) { 
		
			var myval =  buildSingleRequestjson(branchdata);
			var finalobj = jQuery.parseJSON(myval);
			
			 var status = validateData(finalobj,branchdatajsonObj,1); 
			 if(status){
					
					$("#errormsg").html("<font colour='red'>Same Merchant Id Allready Exists</font>");
				
				}
			
			// var status2 = validateData(finalobj,finaljson,2); 
			
			
			// console.log("status  ["+status+"] status2 ["+status2+"]" );
		
			var queryString = "entity=${loginEntity}&method=validatemerchant&transaction="+finalobj.adminType;
	
	$.getJSON("postJson.action", queryString,function(data) { 
		
		if(data.finalCount==0){	
				if(status){
					
						$("#errormsg").html("<font colour='red'>Allready Exists</font>");
					
					}else { 
						
						branchdatajsonObj.push(finalobj);
						$("#errormsg").empty();
						makeempty();
						
							
					 } 
						
			/*  branchdatajsonObj.push(finalobj);
				$("#errormsg").empty();
				makeempty(); */
			 
			var totval = JSON.stringify(branchdatajsonObj);	
			console.log("Final Json Object ["+totval+"]");
		
			
			buildfeetable(branchdatajsonObj);
			
			$("#submitdata").show();
		
			return true;
		}else{
			$("#errormsg").html("<font colour='red'>Allready Exists</font>");
		}
	});
	
		}else{
		
			return false;
		
		}
		
 
 }
 
 

function validateData(myval,myfinalobj,val)
{

	var status = false;
	
	var adminType = myval.adminType;
	var adminName = myval.adminName;

	
	
	
	
	
	
			$.each(myfinalobj, function(index,jsonObject)
					{ 
							var tabadminType = jsonObject.adminType;
							var tabadminName = jsonObject.adminName;
							
							
							if((adminType==tabadminType))
							{
								status=true;
							}
							
							

					});
		
	
	return status;

}

function makeempty()
{
	$("#adminType").val("");
	$("#adminName").val("");
		
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
			htmlString = htmlString + "<td id='adminType' name=adminType >" + jsonObject.adminType + "</td>";
			htmlString = htmlString + "<td id='adminName' name=adminName >" + jsonObject.adminName + "</td>";	
			
			htmlString = htmlString + "<td><a class='btn btn-warning' href='#' id='modify'   title='Modify' data-rel='tooltip'  onclick='upatedata("+data+")' >"+ 
				"<i class='icon icon-edit icon-white'></i></a>&nbsp;"+
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
		//alert($("#Product").val());
		//alert(totval);
		$("#finaljsonarray").val(totval);
		
		
		$("#form2")[0].action='<%=request.getContextPath()%>/<%=appName %>/discountpartnersconfirm.action';
		$("#form2").submit();
		return true; 
			
} 


var dataupdateval;

function upatedata(myval)
{

	$("#ADD").hide();
	$("#UPDATE").show();
	
	console.log("Val ["+myval.adminType+"] ");
	console.log("Val ["+myval.adminName+"] ");
	
	
	/* var totval = JSON.stringify(val);	
			console.log("Final Json Object ["+totval+"]"); */
	//alert(totval);
			
	//console.log(listid);
	$('#adminType').val(myval.adminType);
	$('#adminName').val(myval.adminName);	
	
	
	
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
	
	var adminType = dataupdateval.adminType;
	var adminName  = dataupdateval.adminName;

	
	
	$.each(branchdatajsonObj, function(index,jsonObject){ 
		
		 	var tabadminType = jsonObject.adminType;
			var tabadminName  = jsonObject.adminName;
		
			
			console.log("tabadminType ["+tabadminType+"] tabadminName ["+tabadminName+"]");
		
			if((tabadminType==adminType) &&(tabadminName==adminName) )
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
	$("#form1").validate(datavalidation);
	if($("#form1").valid()) { 
	checkEqual('U');
	makeempty();
	
	$("#ADD").show();
	$("#UPDATE").hide();
	}
	
}


function checkEqual(val)
{

	var finaljsonobj=[];

	var myval =  buildSingleRequestjson(branchdata); 
	var newupdateddataobj = jQuery.parseJSON(myval);
	
	var adminType = dataupdateval.adminType;
	var adminName  = dataupdateval.adminName;
	
	
	$.each(branchdatajsonObj, function(index,jsonObject)
	{ 
		 	// alert(jsonObject.Services);
		
		 	var tabadminType = jsonObject.adminType;
			var tabadminName  = jsonObject.adminName;
			
			// && (branchaddress==tabbranchaddress)
			
			 if((adminType==tabadminType) && (adminName==tabadminName))
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
				  <li><a href="#">Branch Creation</a></li>
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
		 <div id="errormsg" class="errores"></div>
		 
				<input type="hidden" id="finaljsonarray" name="finaljsonarray" >
		 </form>
		 
		<div class="row-fluid sortable"><!--/span--> 
			<div class="box span12">  
					<form name="form1" id="form1" method="post" action="">
					<div id="primaryDetails" class="box-content">
						
							<div class="box span12"> 
					<div class="box-header well" data-original-title>Branch Creation Configuration
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
														<td width="20%"><label for="Channel"><strong>Merchant Id<font color="red">*</font></strong></label></td>
														<td width="30%">
														<input name="adminType" id="adminType"  type="text"  class="field" />
														</td>											
														<td width="20%"><label for="Services"><strong>Merchant Name <font color="red">*</font></strong></label></td>
														<td width="30%"><input name="adminName" id="adminName"  type="text"  class="field" /></td>	
														
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
		
			<br/>	
		
		
		
		<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										<th width="5%">Sno</th>
										<th width="35%">Merchant Id</th>
										<th width="35%">Merchant Name</th>
										<th width="35%">Action</th>
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
