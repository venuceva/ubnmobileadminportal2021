
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/image/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/image/canvasCrop.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/image/jquery.canvasCrop.js" ></script>

<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js"></script>

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


<style>
    
    .tools{
        margin-top: 10px;
        
    }
    .tools span{
        float: left;
        display: inline-block;
        padding: 2px 2px;
        /* background-color: #f40; */
        color: #fff;
        cursor: pointer;
        margin-bottom: 5px;
        margin-right: 5px;
    }
    .clearfix {
        *zoom: 1;
    }
    .clearfix:before{
        content: " ";
        display: table;
    }
    .clearfix:after{
        content: " ";
        display: table;
        clear: both;
    }
    .cropPoint{
        position: absolute;
        height: 8px;
        width: 8px;
        background-color: rgba(255,255,255,0.7);
        cursor: pointer;
    }
    .upload-wapper{
        position: relative;
        float: left;
       /*  height: 26px; 
        line-height: 26px;*/
        /*width: 132px;
         background-color: #f40; */
        color: #fff;
        text-align: center;
        overflow: hidden;
        cursor: pointer;
    }
    #upload-file{
        position: absolute;
        left: 0;
        top: 0;
        opacity: 0;
        filter: alpha(opacity=0);
        width: 132px;
        height: 26px;
        cursor: pointer;
    }
</style>


<SCRIPT type="text/javascript"> 

var billerrules = {
		rules : {
			productctg : { required : true },
			productsubctg : { required : true },
			productname: { required : true },
			productprice: { required : true },
			producteffect: { required : true },
			quantitystock: { required : true },
			productdesc: { required : true},
			imgdata1: { required : true },
			finaljsonarray: { required : true }
		},		
		messages : {
			productctg : { 
				required : "Please select Product Category." 
			  }, 
			productsubctg : { 
					required : "Please select Product Sub Category." 
				},
			productname : { 
					required : "Please Enter Product Name." 
				},
			productprice : { 
					required : "mandatory." 
				  },  
		   producteffect : { 
						required : "Please select Product Effect Date." 
					},
		    quantitystock : { 
						required : "mandatory." 
					}, 
		 productdesc : { 
						required : "Please Enter Product Description."
					},
		imgdata1 : { 
							required : "Please Add Image." 
		},
		finaljsonarray : { 
			required : "Please Add Product Dilvery State." 
		}
		
		},
		errorElement: 'label'
	};
var datepickerOptions = {
		showTime: true,
		showHour: true,
		showMinute: true,
		dateFormat:'dd/mm/yy',
		alwaysSetTime: false,
		yearRange: '1910:2020',
	changeMonth: true,
	changeYear: true
};




$(document).ready(function(){  
	
	
	//alert($("#merchanttype").val());
	
	if($("#merchanttype").val()=="Lifestyle_Coupon"){
		$("#offertype").css("display","");
		$("#prdprice").text("Discount Codes");
		$("#quantiy").text("Webpage/Link");
	}
	
	if($("#merchanttype").val()=="Lifestyle"){
		$("#offertype").css("display","none");
		$("#prdprice").text("Product Price");
		$("#quantiy").text("Quantity in Stock");
	}
	
	
	$("#offertypes").on('change', function() {
		 // alert( this.value );
		  //$("#prdprice").text(this.value);
		  if(this.value=="Union Bank Offer"){
			  $("#productprice").val("UNIONBANK_OFFER");  
			  $("#productprice").attr('readonly', true);
		  }else{
			  $("#productprice").val(""); 
			  $("#productprice").attr('readonly', false);
		  }
		})
	
	
	var list = "producteffect".split(",");
	
	var d = new Date();

	var month = d.getMonth()+1;
	var day = d.getDate();

	var output =(day<10 ? '0' : '')+ day +'/'+(month<10 ? '0' : '')+ month+'/'+d.getFullYear();
	
	$(function() {
		
		$( "#producteffect" ).datepicker(
				
				{ 
					minDate: '0', 
					beforeShow : function()
					{
						jQuery( this ).datepicker('option','minDate', output );
					},
					altFormat: "dd/mm/yy", 
					dateFormat: 'dd/mm/yy'
					
				}
				
		);
		
		
		$(list).each(function(i,val){			
			$('.'+val).datepicker(datepickerOptions);
		});
	});
	
	
	
	
	
	$("#productname")
	  .focusout(function() {  
		  if($('#productname').val().length  == 0){
			 
			  $('#productid').val('');
		  }
				
	  }) 
	   .blur(function() {
		   if($('#productname').val().length > 0){
			   	$('#productid').val('');
				var queryString = "";	
				//alert(queryString);
				$.getJSON("lifestyleCountJSONAct.action", queryString,function(data) { 
					var billerCount =data.responseJSON.BILLER_COUNT;
					//alert(billerCount);
					$('#productid').val(billerCount);
				});
			  } else {
				  $('#productid').val('');
			  }
	  }).keypress(function() {
		   if($('#productname').val().length == 0){  
				  $('#productid').val('');
			  }
	  });  
	

	 $('#productctg').on('change', function (e) {
	    	var valueSelected = this.value;
	    	//alert(valueSelected);
	    	
	    	
	    	
	    	if((this.value)==""){
	    		
		    	document.getElementById("productsubctg").length=1;
	    	}else{
	    	
	    	
	    	
	    	var queryString = "entity=${loginentity}&product="+valueSelected.split("-")[0];

	    	document.getElementById("productsubctg").length=1;
	    	$.getJSON("subcategories.action", queryString, function(data) {
	 			if(data.region!=""){
	 				var mydata=data.region;
	      			var mydata1=mydata.split(":");
	      
	       			$.each(mydata1, function(i, v) {
	       				var options = $('<option/>', {value: ((mydata.split(":")[i])).split(",")[0], text: ((mydata.split(":")[i])).split(",")[1]}).attr('data-id',i);
	       				
	       				$('#productsubctg').append(options);
	       				$('#productsubctg').trigger("liszt:updated");
	       			});
	       			
	       			
	      		} 
	     	}); 
	    	}	
	    	
	    });
	
	
	$('#btn-submit').live('click',function() { 
		subitReq();
		
		$("#form1").validate(billerrules); 
 		if($("#form1").valid()) {
 			 if($("#finaljsonarray").val()=="" || $("#finaljsonarray").val()=="[]"){
 				$("#errormsg").html("<font colour='red'>Please Add Product delivery State</font>");
 				return false;
 			}else{ 
 			
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName%>/MerchantGroupConfirm.action";
			$("#form1").submit();	
			return true;
 			}
		} else {
 			return false;
		} 	
	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName%>/merchantmenagenet.action";
		$("#form1").submit();
	});

});




	
	
var branchdata="state|selecttext#city|text#"; 

var branchdatajsonObj = [];


function addrow()
{
 //alert("dddd");
		//$("#form1").validate(datavalidation);
		
		console.log("Addrow >>>> ");
		var myval =  buildSingleRequestjson(branchdata);
		var finalobj = jQuery.parseJSON(myval);
		
		if(finalobj.state!="Select") { 
		
			
			
			var status = validateData(finalobj,branchdatajsonObj,1); 
			

				if(status){
					
						$("#errormsg").html("<font colour='red'>Same State and City Allready Exists</font>");
					
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
			 $("#errormsg").html("<font colour='red'>Please Select State</font>");
			return false;
		
		} 
		
 
 }
 
 

function validateData(myval,myfinalobj,val)
{

	var status = false;
	
	var state = myval.state;
	var city = myval.city;

	
	$.each(myfinalobj, function(index,jsonObject)
	{ 
			var tabstate = jsonObject.state;
			var tabcity  = jsonObject.city;
			
			
			if((state==tabstate && city==tabcity) )
			{
				status=true;
			}

	});
	
	return status;

}

var listid = "state,".split(","); 
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
	$("#city").val("");
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
			htmlString = htmlString + "<td id='state' name=state >" + jsonObject.state + "</td>";
			htmlString = htmlString + "<td id='city' name=city >" + jsonObject.city + "</td>";	
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


var dataupdateval;

function upatedata(myval)
{

	$("#ADD").hide();
	$("#UPDATE").show();
	
	console.log("Val ["+myval.state+"] ");
	
	$(listid).each(function(index,val){
		$('#'+val).find('option').each(function( i, opt ) { 
			
			if(val=="state"){
				
				if(opt.value == myval.state) {
					$(opt).attr('selected', 'selected');
					$('#'+val).trigger("liszt:updated");
				}
			}
			
			
				
		});
		
	});	
	$("#city").val(myval.city);
	
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
	
	var state = dataupdateval.state;
	var city  = dataupdateval.city;
	
	
	console.log("state ["+state+"] city ["+city+"]");
	
	$.each(branchdatajsonObj, function(index,jsonObject){ 
		
		 	var tabstate = jsonObject.state;
			var tabcity  = jsonObject.city;
			
			console.log("state ["+state+"] city ["+city+"]");
			
			if((tabstate==state) && (tabcity==city))
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
	
	var state = dataupdateval.state;
	var city  = dataupdateval.city;
	 $("#errormsg").html("");
	//alert(newupdateddataobj.state);
	if(newupdateddataobj.state!="Select") { 
	$.each(branchdatajsonObj, function(index,jsonObject)
	{ 
		 	// alert(jsonObject.Services);
		
		 	var tabstate = jsonObject.state;
			var tabcity  = jsonObject.city;
		
			
			// && (branchaddress==tabbranchaddress)
		
			
			 if((state==tabstate) && (city==tabcity))
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
		
} 


</SCRIPT>

<s:set value="responseJSON" var="respData"/> 
</head>
<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
		<div id="content" class="span10">

			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider">&gt;&gt; </span></li>
					<li><a href="#"> Life Style Management</a></li>
				</ul>
			</div>

			<div id="errormsg" class="errores"></div>

			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>${responseJSON.merchanttype} Merchant Product Creation Details
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
									<td width="20%"><label for="merchantname"><strong>Merchant Name</strong></label></td>
									<td width="30%">${responseJSON.orgname}
									<input type="hidden" name="merchantname" id="merchantname" value="${responseJSON.orgname}" /></td>
									<td width="20%"><label for="merchantid"><strong>Merchant Id</strong></label></td>
									<td width="30%">${responseJSON.orgid}
									<input type="hidden" name="merchantid" id="merchantid" value="${responseJSON.orgid}" /></td>
								</tr>
								<tr>
									<td><label for="productctg"><strong>Product Category<font color="red">*</font></strong></label></td>
									<td >
									<s:select cssClass="chosen-select" headerKey=""
															headerValue="Select" list="#respData.PRODUCT_CTG"
															name="productctg" id="productctg" requiredLabel="true"
															theme="simple" data-placeholder="Choose Channel"
															required="true" /> 
									
									</td>
		  							<td><label for="productsubctg"><strong>Product Sub-Category<font color="red">*</font></strong></label></td>
									<td>
									<select id="productsubctg" name="productsubctg" class="chosen-select-width" >
										 <option value="">Select</option>
									</select>
									</td>
								</tr>
								<tr>
									<td width="20%"><label for="productname"><strong>Product Name<font color="red">*</font></strong></label></td>
									<td width="30%"><input type="text" name="productname" id="productname"	required=true /></td>
									<td width="20%"><label for="productid"><strong>Product Id<font color="red">*</font></strong></label></td>
									<td width="30%"><input type="text" name="productid" id="productid"	required=true readonly/></td>
									
								</tr>
								
								<tr id="offertype" style="display:none">
									<td width="20%"><label for="productprice"><strong>Offer Type<font color="red">*</font></strong></label></td>
									<td width="30%">
									<select id="offertypes" name="offertypes" class="chosen-select-width" >
										 <option value="Discount Codes" checked>Discount Codes</option>
										 <option value="Union Bank Offer">Union Bank Offer</option>
									</select>
									</td>
									<td width="20%"></td>
									<td width="30%"></td>
									
								</tr>
								
								<tr>
									<td width="20%"><label for="productprice"><strong><span id="prdprice"></span><font color="red">*</font></strong></label></td>
									<td width="30%"><input type="text" name="productprice" id="productprice" required=true /></td>
									<td width="20%"><label for="producteffect"><strong>Product Effect Date<font	color="red">*</font></strong></label></td>
									<td width="30%"><input type="text" maxlength="10"  class="producteffect" id="producteffect" name="producteffect" required=true   /></td>
									
								</tr>
								<tr>
								<td width="20%"><label for="quantitystock"><strong><span id="quantiy"></span><font color="red">*</font></strong></label></td>
									<td width="30%"><input type="text" name="quantitystock" id="quantitystock" required=true /></td>
									<td width="20%"><label for="productdesc"><strong>Product Description<font
												color="red">*</font></strong></label></td>
									<td width="30%"><input type="text" name="productdesc" id="productdesc"	required=true /></td>
									
								</tr>
								
								
								
							</table>
							
							<input type="hidden" name="merchanttype" id="merchanttype"	value="${responseJSON.merchanttype}" required=true />
							
							<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Product delivery States
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i
										class="icon-cog"></i></a> <a href="#"
										class="btn btn-minimize btn-round"><i
										class="icon-chevron-up"></i></a>
		
								</div>
							</div>
							
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
							   class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
													
													<tr class="odd">
														<td width="20%"><label for="State"><strong>State<font color="red">*</font></strong></label></td>
														<td width="30%">
														<s:select cssClass="chosen-select" headerKey=""
															headerValue="Select" list="#respData.STATE_NAME"
															name="state" id="state" requiredLabel="true"
															theme="simple" data-placeholder="Choose Channel"
															required="true" /> 
														
														</td>											
														<td width="20%"><label for="City"><strong>City</strong></label></td>
														<td width="30%">
														<input type="text" name="city" id="city"	required=true />
														</td>	
														
													</tr>
							</table>
							
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
										<th width="10%">Sno</th>
										<th width="40%">State</th>
										<th width="40%">City</th>								
										<th width="10%">Action</th>
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							 
		</table>
		
		 <input type="hidden" id="finaljsonarray" name="finaljsonarray" >
		
		</div> 
							
							<table width="98%" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
							<tr><td colspan="5">
								
								
								<div class="container">
								  <div class="imageBox" >
								    <!--<div id="img" ></div>-->
								    <!--<img class="cropImg" id="img" style="display: none;" src="images/avatar.jpg" />-->
								    <div class="mask"></div>
								    <div class="thumbBox"></div>
								    <div class="spinner" style="display: none">Loading...</div>
								  </div>
								  <div class="tools clearfix" >
								    <span id="rotateLeft" ><img width="20px" height="20px" src="${pageContext.request.contextPath}/images/cards/Rotate_left_arrow.png"></span>
								    <span id="rotateRight" ><img width="20px" height="20px" src="${pageContext.request.contextPath}/images/cards/Rotate_right_arrow.png"></span>
								    <span id="zoomIn" ><img width="20px" height="20px" src="${pageContext.request.contextPath}/images/cards/zoom-out.png"></span>
								    <span id="zoomOut" ><img width="20px" height="20px" src="${pageContext.request.contextPath}/images/cards/zoom-in.png"></span>
<%-- 								    <span id="crop" ><img width="20px" height="20px" src="${pageContext.request.contextPath}/images/cards/crop.png"></span>
 --%>								    <span id="alertInfo" ><img width="20px" height="20px" src="${pageContext.request.contextPath}/images/cards/clear.png"></span>
								    <span class="upload-wapper">
								         <img width="20px" height="20px" src="${pageContext.request.contextPath}/images/cards/upload.png">
								        <input type="file" id="upload-file" value="Upload" />
								    </span>
								  </div>
								</div>
								<input type="hidden" name="imgdata1" id="imgdata1" />
								</td>
								</tr>
								<tr><td width="20%"><span><strong>Main Image</strong></span> 
								<span style="float: right;">
								<a class='btn btn-warning' href='#' id='img1Add'   title='Add' data-rel='tooltip'   ><i class='icon icon-picture icon-white'></i></a>
								<a class='btn btn-warning' href='#' id='img1Delete'   title='Delete' data-rel='tooltip'   ><i class='icon icon-remove icon-white'></i></a>
								</span>
								
								</td>
							<td width="20%" ><strong>Image 1</strong>
							<span style="float: right;">
								<a class='btn btn-warning' href='#' id='img2Add'   title='Add' data-rel='tooltip'   ><i class='icon icon-picture icon-white'></i></a>
								<a class='btn btn-warning' href='#' id='img2Delete'   title='Delete' data-rel='tooltip'   ><i class='icon icon-remove icon-white'></i></a>
								</span>
							</td>
							<td width="20%"><strong>Image 2</strong>
							<span style="float: right;">
								<a class='btn btn-warning' href='#' id='img3Add'   title='Add' data-rel='tooltip'   ><i class='icon icon-picture icon-white'></i></a>
								<a class='btn btn-warning' href='#' id='img3Delete'   title='Delete' data-rel='tooltip'   ><i class='icon icon-remove icon-white'></i></a>
								</span>
							</td>
							<td width="20%"><strong>Image 3</strong>
							<span style="float: right;">
								<a class='btn btn-warning' href='#' id='img4Add'   title='Add' data-rel='tooltip'   ><i class='icon icon-picture icon-white'></i></a>
								<a class='btn btn-warning' href='#' id='img4Delete'   title='Delete' data-rel='tooltip'   ><i class='icon icon-remove icon-white'></i></a>
								</span>
							</td>
							<td width="20%"><strong>Image 4</strong>
							<span style="float: right;">
								<a class='btn btn-warning' href='#' id='img5Add'   title='Add' data-rel='tooltip'   ><i class='icon icon-picture icon-white'></i></a>
								<a class='btn btn-warning' href='#' id='img5Delete'   title='Delete' data-rel='tooltip'   ><i class='icon icon-remove icon-white'></i></a>
								</span>
							</td>
							</tr>
							<tr><td id="img1" width="20%"><img  src="${pageContext.request.contextPath}/images/cards/UploadDark.png"></td>
							<td id="img2" width="20%"><img  src="${pageContext.request.contextPath}/images/cards/UploadDark.png"></td>
							<td id="img3" width="20%"><img  src="${pageContext.request.contextPath}/images/cards/UploadDark.png"></td>
							<td id="img4" width="20%"><img  src="${pageContext.request.contextPath}/images/cards/UploadDark.png"></td>
							<td id="img5" width="20%"><img  src="${pageContext.request.contextPath}/images/cards/UploadDark.png"></td>
							</tr>
							</table>
							
							
							<input type="hidden" name="imgdata2" id="imgdata2" />
							<input type="hidden" name="imgdata3" id="imgdata3"  />
							<input type="hidden" name="imgdata4" id="imgdata4" />
							<input type="hidden" name="imgdata5" id="imgdata5"  />
							
							
							<input type="hidden" name="pimg1" id="pimg1" value=""/>
							<input type="hidden" name="pimg2" id="pimg2" value=""/>
							<input type="hidden" name="pimg3" id="pimg3"  value=""/>
							<input type="hidden" name="pimg4" id="pimg4" value=""/>
							<input type="hidden" name="pimg5" id="pimg5"  value=""/>
							
						</fieldset>
					</div>
				</div>
			</div>

		
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
		
		
		var v=1;
		 $(function(){
		        var rot = 0,ratio = 1;
		        var CanvasCrop = $.CanvasCrop({
		            cropBox : ".imageBox",
		            imgSrc : "${pageContext.request.contextPath}/images/cards/UploadDark.png",
		            limitOver : 2
		        });
		        
		        
		        $('#upload-file').on('change', function(){
		            var reader = new FileReader();
		            reader.onload = function(e) {
		                CanvasCrop = $.CanvasCrop({
		                    cropBox : ".imageBox",
		                    imgSrc : e.target.result,
		                    limitOver : 2
		                });
		                rot =0 ;
		                ratio = 1;
		            }
		            reader.readAsDataURL(this.files[0]);
		            this.files = [];
		        });
		        
		        $("#rotateLeft").on("click",function(){
		            rot -= 90;
		            rot = rot<0?270:rot;
		            CanvasCrop.rotate(rot);
		        });
		        $("#rotateRight").on("click",function(){
		            rot += 90;
		            rot = rot>360?90:rot;
		            CanvasCrop.rotate(rot);
		        });
		        $("#zoomIn").on("click",function(){
		            ratio =ratio*0.9;
		            CanvasCrop.scale(ratio);
		        });
		        $("#zoomOut").on("click",function(){
		            ratio =ratio*1.1;
		            CanvasCrop.scale(ratio);
		        });
		        $("#alertInfo").on("click",function(){
		            var canvas = document.getElementById("visbleCanvas");
		            var context = canvas.getContext("2d");
		            context.clearRect(0,0,canvas.width,canvas.height);
		        });
		        
		        $("#crop").on("click",function(){
		        	$("#form1").validate(billerrules); 
		     		if($("#form1").valid()) {
		            
			            var src = CanvasCrop.getDataURL("jpeg");
						//alert(src);
			            //$("body").append("<div style='word-break: break-all;'>"+src+"</div>");  
			            //$(".container").append("<img src='"+src+"' />");
			          
			             $("#imgdata"+v).val(src);
			             $("#pimg"+v).val($("#productid").val()+"_"+v);
			            $("#img"+v).html("<img src='"+src+"' />");
			          
			           v=v+1; 
		     		}
		        });
		        
		        
		        $("#img1Add").on("click",function(){
		        /* 	$("#form1").validate(billerrules); 
		     		if($("#form1").valid()) { */
		            
			            var src = CanvasCrop.getDataURL("jpeg");
		     		if(src!="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgICAgMCAgIDAwMDBAYEBAQEBAgGBgUGCQgKCgkICQkKDA8MCgsOCwkJDRENDg8QEBEQCgwSExIQEw8QEBD/2wBDAQMDAwQDBAgEBAgQCwkLEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBD/wAARCADGAMYDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD8qqKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAP/2Q=="){
			             $("#imgdata1").val(src);
			             $("#pimg1").val($("#productid").val()+"_1");
			            $("#img1").html("<img  src='"+src+"' />");
		     		}else{
		     			alert("Please select Image");
		     		} 
			      
		     		/* } */
		        });
		        
		        $("#img1Delete").on("click",function(){
		        /* 	$("#form1").validate(billerrules); 
		     		if($("#form1").valid()) { */
		            
						
			             $("#imgdata1").val("");
			             $("#pimg1").val("");
			            $("#img1").html("<img src='${pageContext.request.contextPath}/images/cards/UploadDark.png' />");
			          
			      
		     		/* } */
		        });
		        
		        $("#img2Add").on("click",function(){
		        	/* $("#form1").validate(billerrules); 
		     		if($("#form1").valid()) { */
		            
			            var src = CanvasCrop.getDataURL("jpeg");
		     		if(src!="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgICAgMCAgIDAwMDBAYEBAQEBAgGBgUGCQgKCgkICQkKDA8MCgsOCwkJDRENDg8QEBEQCgwSExIQEw8QEBD/2wBDAQMDAwQDBAgEBAgQCwkLEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBD/wAARCADGAMYDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD8qqKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAP/2Q=="){
	
			             $("#imgdata2").val(src);
			             $("#pimg2").val($("#productid").val()+"_2");
			            $("#img2").html("<img src='"+src+"' />");
		     		} else{
			     			alert("Please select Image");
			     		} 
			      
		     		/* } */
		        });
		        
		        $("#img2Delete").on("click",function(){
		        	/* $("#form1").validate(billerrules); 
		     		if($("#form1").valid()) { */
		            
						
			             $("#imgdata2").val("");
			             $("#pimg2").val("");
			            $("#img2").html("<img src='${pageContext.request.contextPath}/images/cards/UploadDark.png' />");
			          
			      
		     		/* } */
		        });
		        
		        $("#img3Add").on("click",function(){
		        	/* $("#form1").validate(billerrules); 
		     		if($("#form1").valid()) { */
		            
			            var src = CanvasCrop.getDataURL("jpeg");
		     		if(src!="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgICAgMCAgIDAwMDBAYEBAQEBAgGBgUGCQgKCgkICQkKDA8MCgsOCwkJDRENDg8QEBEQCgwSExIQEw8QEBD/2wBDAQMDAwQDBAgEBAgQCwkLEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBD/wAARCADGAMYDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD8qqKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAP/2Q=="){
	
			             $("#imgdata3").val(src);
			             $("#pimg3").val($("#productid").val()+"_3");
			            $("#img3").html("<img src='"+src+"' />");
		     		} else{
		     			alert("Please select Image");
		     		}   
			      
		     		/* } */
		        });
		        
		        $("#img3Delete").on("click",function(){
		        /* 	$("#form1").validate(billerrules); 
		     		if($("#form1").valid()) {
		             */
						
			             $("#imgdata3").val("");
			             $("#pimg3").val("");
			            $("#img3").html("<img src='${pageContext.request.contextPath}/images/cards/UploadDark.png' />");
			          
			      
		     		/* } */
		        });
		        
		        $("#img4Add").on("click",function(){
		        	/* $("#form1").validate(billerrules); 
		     		if($("#form1").valid()) { */
		            
			            var src = CanvasCrop.getDataURL("jpeg");
		     		if(src!="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgICAgMCAgIDAwMDBAYEBAQEBAgGBgUGCQgKCgkICQkKDA8MCgsOCwkJDRENDg8QEBEQCgwSExIQEw8QEBD/2wBDAQMDAwQDBAgEBAgQCwkLEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBD/wAARCADGAMYDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD8qqKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAP/2Q=="){

			             $("#imgdata4").val(src);
			             $("#pimg4").val($("#productid").val()+"_4");
			            $("#img4").html("<img src='"+src+"' />");
			          
		     		} else{
		     			alert("Please select Image");
		     		}
		     		/* } */
		        });
		        
		        $("#img4Delete").on("click",function(){
		        	/* $("#form1").validate(billerrules); 
		     		if($("#form1").valid()) { */
		            
						
			             $("#imgdata4").val("");
			             $("#pimg4").val("");
			            $("#img4").html("<img src='${pageContext.request.contextPath}/images/cards/UploadDark.png' />");
			          
			      
		     		/* } */
		        });
		        
		        $("#img5Add").on("click",function(){
		        	/* $("#form1").validate(billerrules); 
		     		if($("#form1").valid()) {
		             */
			            var src = CanvasCrop.getDataURL("jpeg");
			     		if(src!="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgICAgMCAgIDAwMDBAYEBAQEBAgGBgUGCQgKCgkICQkKDA8MCgsOCwkJDRENDg8QEBEQCgwSExIQEw8QEBD/2wBDAQMDAwQDBAgEBAgQCwkLEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBD/wAARCADGAMYDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD8qqKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAP/2Q=="){

			             $("#imgdata5").val(src);
			             $("#pimg5").val($("#productid").val()+"_5");
			            $("#img5").html("<img src='"+src+"' />");
			     		} else{
			     			alert("Please select Image");
			     		}
			      
		     		/* } */
		        });
		        
		        $("#img5Delete").on("click",function(){
		        	/* $("#form1").validate(billerrules); 
		     		if($("#form1").valid()) { */
		            
						
			             $("#imgdata5").val("");
			             $("#pimg5").val("");
			            $("#img5").html("<img src='${pageContext.request.contextPath}/images/cards/UploadDark.png' />");
			          
			      
		     		/* } */
		        });
		        
		        
		        console.log("ontouchend" in document);
		    })
		
		
	</script>
	<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
