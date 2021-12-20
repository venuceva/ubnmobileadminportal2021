<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<%
	String ctxstr = request.getContextPath();
%>
<%
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>

<script>
function myFunction(feecharge) {
	  document.getElementById("feename").value = feecharge;
	}
</script>

<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js"></script>

<script type="text/javascript">

 
	  
  
//For Closing Select Box Error Message_Start
  $(document).on('change','select',function(event) { 
   
	   if($('#'+$(this).attr('id')).next('label').text().length > 0) {
		    $('#'+$(this).attr('id')).next('label').text(''); 
		    $('#'+$(this).attr('id')).next('label').remove();
	    }
  });
  
  
  var createProductRules = {
		   rules : {
			   	productText     : { required : true, minlength :6,maxlength :6 ,regex: /^[a-zA-Z0-9]+$/ },  
				productDesc     : { required : true },
				binCurrency		: { required : true },
			
				application				: { required : true }
				
		   },  
		   messages : {
			   productText : { 
		         required : "Please Enter Product Code ",
					regex : "Product Code, can not allow special characters."
		         },  
			   productDesc : {
				 required :"Please Enter Product Description "
			     },
		      binCurrency : { 
			     required : "Please Select Product Currency"
			     },
			   application : { 
				     required : "Please Select Application"
				}
		    } 
		 };
  
  
  
  
  var mobileProductRules = {
		   rules : {
			   	productText     : { required : true, minlength :6,maxlength :6 ,regex: /^[a-zA-Z0-9]+$/ },  
				productDesc     : { required : true },
			
				binCurrency		: { required : true },
				tokenlimit		: { required : true,number: true},
				perdaylimit		: { required : true,number: true},
				application				: { required : true },
				ussdinitallmt				: { required : true,number: true },
				ussdsecfalmt				: { required : true,number: true }
				
				
		   },  
		   messages : {
			   productText : { 
		         required : "Please Enter Product Code ",
					regex : "Product Code, can not allow special characters."
		         },  
			   productDesc : {
				 required :"Please Enter Product Description "
			     },
		      binCurrency : { 
			     required : "Please Select Product Currency"
			     },
			  tokenlimit : {
					 required :"Please Enter Token Limit Amount ",
					 regex : "Token Limit, can not allow special characters."
				 },
			  perdaylimit : {
					 required :"Please Enter Per Day Limit Amount ",
					 regex : "Per Day Limit, can not allow special characters."
				 },
			   application : { 
				     required : "Please Select Application"
				},
				ussdinitallmt : {
					 required :"Please Enter USSD Initial Limit ",
					 regex : "USSD Initial Limit, can not allow special characters."
				 },
				 ussdsecfalmt : {
					 required :"Please Enter USSD 2FA Limit Amount ",
					 regex : "USSD 2FA Limit Amount, can not allow special characters."
				 }
		    } 
		 };
  
  
  var walletProductRules = {
		   rules : {
			   	productText     : { required : true, minlength :6,maxlength :6 ,regex: /^[a-zA-Z0-9]+$/ },  
				productDesc     : { required : true },
				binCurrency		: { required : true },
				tokenlimit		: { required : true,number: true},
				perdaylimit		: { required : true,number: true},
				application				: { required : true },
			
				ussdinilmt				: { required : true,number: true }
				
				
		   },  
		   messages : {
			   productText : { 
		         required : "Please Enter Product Code ",
					regex : "Product Code, can not allow special characters."
		         },  
			   productDesc : {
				 required :"Please Enter Product Description "
			     },
		      binCurrency : { 
			     required : "Please Select Product Currency"
			     },
			  tokenlimit : {
					 required :"Please Enter Token Limit Amount ",
					 regex : "Token Limit, can not allow special characters."
				 },
			  perdaylimit : {
					 required :"Please Enter Per Day Limit Amount ",
					 regex : "Per Day Limit, can not allow special characters."
				 },
			   application : { 
				     required : "Please Select Application"
				},
				ussdinilmt : {
					 required :"Please Enter Maximum Balance Amount ",
					 regex : "Maximum Balance Amount, can not allow special characters."
				 }
		    } 
		 };
  
  var agentProductRules = {
		   rules : {
			   	productText     : { required : true, minlength :6,maxlength :6 ,regex: /^[a-zA-Z0-9]+$/ },  
				productDesc     : { required : true },
				binCurrency		: { required : true },
				perdaylimit		: { required : true,number: true},
				application				: { required : true },
				agent		: { required : true },
				feename          : { required : true },
				ussdinitallmt				: { required : false,number: true },
				ussdsecfalmt				: { required : false,number: true }
				
				
		   },  
		   messages : {
			   productText : { 
		         required : "Please Enter Product Code ",
					regex : "Product Code, can not allow special characters."
		         },  
			   productDesc : {
				 required :"Please Enter Product Description "
			     },
		      binCurrency : { 
			     required : "Please Select Product Currency"
			     },
			  perdaylimit : {
					 required :"Please Enter Per Day Limit Amount ",
					 regex : "Per Day Limit, can not allow special characters."
				 },
			   application : { 
				     required : "Please Select Application"
				},
				
				 agent : { 
					     required : "Please Select Agent"
					},
					ussdinitallmt : {
						 required :"Please Enter USSD Initial Limit ",
						 regex : "USSD Initial Limit, can not allow special characters."
					 },
					 ussdsecfalmt : {
						 required :"Please Enter USSD 2FA Limit Amount ",
						 regex : "USSD 2FA Limit Amount, can not allow special characters."
					 },
					 feename: { 
					     required : "Please Select One Option (Agent/Customer)."
					}
		    } 
		 };
  
  
	$.validator.addMethod("regex", function(value, element, regexpr) {          
		 return regexpr.test(value);
	   }, ""); 
	
  
	      
		function getProductScreen(){
			$("#form1").validate().cancelSubmit = true;
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName%>/createProductAct.action';
			$("#form1").submit();
			return true;
		}
		
		$(function(){
			
			
			
			
			$('#balancemax1').css("display","none"); 
			$('#limitperday').css("display","none"); 
			$('#feename1').css("display","none"); 
			$('#application').on('change', function() {
				
				document.getElementById("channellmt").length=1;
				$('#channellmt').trigger("liszt:updated");
				
				if(this.value=="Mobile Banking"){
					$('#balancemax1').css("display",""); 
					$('#feename1').css("display",""); 
					var channels = ["MOBILE", "USSD"];
					var option = '';
					for (var i=0;i<channels.length;i++){
					   option += '<option value="'+ channels[i] + '">' + channels[i] + '</option>';
					}
					
					$('#channellmt').append(option);
					$('#channellmt').trigger("liszt:updated");
				}
				
				  if(this.value=="Agent"){
					  
					  $('#balancemax1').css("display","none"); 
					
					  
					  $('#tokenlimit1').css("display","none"); 
					  $('#tokenlimit2').css("display","none"); 
					  $('#tokenlimit3').css("display",""); 
					  $('#tokenlimit4').css("display",""); 
					  $('#limitperday').css("display",""); 
					  $('#feename1').css("display",""); 
					  
					 // $('#channellimitperday').css("display","none"); 
					//  $('#channellimitperday1').css("display","none"); 
					  $('#tokenlimit').val("0");
					 // $('#perdaylimit').val("0");
					 // $("#finaljsonarray").val("MOBILE#0|USSD#0|");
					//  $('#balancemax1').css("display",""); 
					  
					 /*  var channels = ["MOBILE", "USSD", "WEB", "POS"]; */
					 var channels = ["MOBILE", "USSD"];
						var option = '';
						for (var i=0;i<channels.length;i++){
						   option += '<option value="'+ channels[i] + '">' + channels[i] + '</option>';
						}
						
						$('#channellmt').append(option);
						$('#channellmt').trigger("liszt:updated");
					  
				  }else {
					  
					  $('#balancemax1').css("display",""); 

					  $('#tokenlimit1').css("display",""); 
					  $('#tokenlimit2').css("display",""); 
					  $('#tokenlimit3').css("display","none"); 
					  $('#tokenlimit4').css("display","none"); 
					  $('#feename1').css("display","none"); 
					  $('#limitperday').css("display",""); 
					  $('#channellimitperday').css("display",""); 
					  $('#channellimitperday1').css("display","");
					  $('#tokenlimit').val("");
					  $('#perdaylimit').val("");
					  $("#finaljsonarray").val("");
					  
					 
				  }
				  $('#balancemax').css("display","none"); 
				  if(this.value=="Wallet"){
					  
					  var channels = ["MOBILE", "USSD"];
						var option = '';
						for (var i=0;i<channels.length;i++){
						   option += '<option value="'+ channels[i] + '">' + channels[i] + '</option>';
						}
						
						$('#channellmt').append(option);
						$('#channellmt').trigger("liszt:updated");
					  
					  $('#balancemax').css("display","");  
					  $('#balancemax1').css("display","none"); 
					  $('#feename1').css("display","none"); 
				  }
				  
				})
			
			
			$( "#productText" ).keyup(function() {
				
				$( "#productText" ).val((this.value).toUpperCase());
				});
			
			$('#productDesc').live('blur', function () {
		 		
		 	 	 var ses=$('#productDesc').val();
		 	 	 
		 		 if($.isNumeric(ses))
					{ 
				      $('#bin_err1').html('Product Description Should Be Alphanumaric');
				      return false;
					} else{
						$('#bin_err1').empty();
					}
		 	});
		
			
		});
	
		function createProduct(){
			
			var queryString = "entity=${loginEntity}&method=searchProduct&fname="+$('#productText').val();
    		
			$.getJSON("postJson.action", queryString,function(data) { 
				
				if(data.message=="SUCCESS"){
		 
			   var issuingCountrytype=$( "#issuingCountry option:selected" ).text();
			   $('#issuingData').val(issuingCountrytype);
			   
			  // $("#form1").validate(createProductRules); 
			  $("#errormsg").text("");
			   if ($('#application').val()==""){
					$("#errormsg").html("<font colour='red'>Please select Application</font>");
	 				return false;	
				}else{
					$('#application').prop("disabled", true); 
					$('#application').trigger("liszt:updated");
				}
			  
			   if($('#application').val()=="Mobile Banking"){
				   $("#form1").validate(mobileProductRules); 
			   }
			   
			   if($('#application').val()=="Wallet"){
				   $("#form1").validate(walletProductRules); 
			   }
			  
			   if($('#application').val()=="Agent"){
				   $("#form1").validate(agentProductRules); 
			   }
			  
			   
			if($("#form1").valid()){
				
				if($('#application').val()=="Mobile Banking" || $('#application').val()=="Wallet"){
					
				
					subitReq();
					var v=$("#finaljsonarray").val();
					
					if (v.indexOf("MOBILE")==-1){
						$("#errormsg").html("<font colour='red'>Please Add Per Day Limit for Channel Mobile</font>");
		 				return false;	
					}
					if (v.indexOf("USSD")==-1){
						$("#errormsg").html("<font colour='red'>Please Add Per Day Limit for Channel USSD</font>");
		 				return false;
					}
				}else if($('#application').val()=="Agent"){
					subitReq();
					var v=$("#finaljsonarray").val();
					
					if (v.indexOf("MOBILE")==-1){
						$("#errormsg").html("<font colour='red'>Please Add Per Day Limit for Channel Mobile</font>");
		 				return false;	
					}
					if (v.indexOf("USSD")==-1){
						$("#errormsg").html("<font colour='red'>Please Add Per Day Limit for Channel USSD</font>");
		 				return false;
					}
					/* if (v.indexOf("WEB")==-1){
						$("#errormsg").html("<font colour='red'>Please Add Per Day Limit for Channel WEB</font>");
		 				return false;
					}
					if (v.indexOf("POS")==-1){
						$("#errormsg").html("<font colour='red'>Please Add Per Day Limit for Channel POS</font>");
		 				return false;
					} */
				}
				
				$('#application').prop("disabled", false); 
				$('#application').trigger("liszt:updated");
				
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName%>/productConfirmAct.action';
				$("#form1").submit();
				return true;
			}else{
				return false;
			}
				}else{ 
			    	  
					$('#errormsg').text(data.message);

				 }
			}); 
		}
	
		
		
		 $(document).ready(function() {
	            
	            //attach keypress to input
	            $('#cperdaylmt').keydown(function(event) {
	                // Allow special chars + arrows 
	                if (event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 
	                    || event.keyCode == 27 || event.keyCode == 13 
	                    || (event.keyCode == 65 && event.ctrlKey === true) 
	                    || (event.keyCode >= 35 && event.keyCode <= 39)){
	                        return;
	                }else {
	                    // If it's not a number stop the keypress
	                    if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
	                        event.preventDefault(); 
	                    }   
	                }
	            });
	            
	        });
		
	
		var finaljson; 

		

		 
	
		 
		 
		var branchdata="channellmt|selecttext#cperdaylmt|text#"; //branchaccno|text#

		var branchdatajsonObj = [];
		 
		function addrow()
		{
		 
				
				
				console.log("Addrow >>>> ");

				
				
				
					var myval =  buildSingleRequestjson(branchdata);
					var finalobj = jQuery.parseJSON(myval);
					
					if(finalobj.channellmt=="Select") { 
						 $("#errormsg").html("<font colour='red'>Please Select Channel</font>");
							return false;
					}
					if(finalobj.cperdaylmt=="") { 
						 $("#errormsg").html("<font colour='red'>Please Enter Amount</font>");
							return false;
					}
					
					 var status = validateData(finalobj,branchdatajsonObj,1); 
					
					
					// var status2 = validateData(finalobj,finaljson,2); 
					
					
					// console.log("status  ["+status+"] status2 ["+status2+"]" );
					if(status){
						
						$("#errormsg").html("<font colour='red'>Same Channel Allready Exists</font>");
					
					}else {
						branchdatajsonObj.push(finalobj);
						$("#errormsg").empty();
						makeempty();
					}
						var totval = JSON.stringify(branchdatajsonObj);	
						console.log("Final Json Object ["+totval+"]");
					
						
						buildfeetable(branchdatajsonObj);
						
						$("#submitdata").show();
				
		 
		 }
		 
		 

		function validateData(myval,myfinalobj,val)
		{

			var status = false;
			
			var channellmt = myval.channellmt;
			var cperdaylmt = myval.cperdaylmt;

			
			$.each(myfinalobj, function(index,jsonObject)
			{ 
					var tabchannellmt = jsonObject.channellmt;
					var tabcperdaylmt = jsonObject.cperdaylmt;
					
					
					if((channellmt==tabchannellmt))
					{
						status=true;
					}

			});
			
			return status;

		}

		var listid = "channellmt,".split(","); 
		function makeempty()
		{
			$("#cperdaylmt").val("");
			$(listid).each(function(index,val){ 
				$('#'+val).find('option').each(function( i, opt ) { 
					if( opt.value === '' ) {
						$(opt).attr('selected', 'selected');
						$('#'+val).trigger("liszt:updated");
					}
				}); 
			});
			$("#errormsg").html("");
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
					htmlString = htmlString + "<td id='channellmt' name=channellmt >" + jsonObject.channellmt + "</td>";
					htmlString = htmlString + "<td id='cperdaylmt' name=cperdaylmt >" + jsonObject.cperdaylmt + "</td>";	
					
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
				
				
				
					
		} 


		var dataupdateval;

		function upatedata(myval)
		{

			$("#ADD").hide();
			$("#UPDATE").show();
			
			console.log("Val ["+myval.channellmt+"] ");
			console.log("Val ["+myval.cperdaylmt+"] ");
			
			
			/* var totval = JSON.stringify(val);	
					console.log("Final Json Object ["+totval+"]"); */
			//alert(totval);
					
			//console.log(listid);
			
			
			
			$(listid).each(function(index,val){
				$('#'+val).find('option').each(function( i, opt ) { 
					if(val=="channellmt"){
						
						if(opt.value == myval.channellmt) {
							$(opt).attr('selected', 'selected');
							$('#'+val).trigger("liszt:updated");
						}
					}
					
					$('#cperdaylmt').val(myval.cperdaylmt);	
						
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

			dataupdateval=val;
			
			var channellmt = dataupdateval.channellmt;
			var cperdaylmt  = dataupdateval.cperdaylmt;
			
			
			$.each(branchdatajsonObj, function(index,jsonObject){ 
				
				 	var tabchannellmt = jsonObject.channellmt;
					var tabcperdaylmt  = jsonObject.cperdaylmt;
				
					
					console.log("tabchannellmt ["+tabchannellmt+"] tabcperdaylmt ["+tabcperdaylmt+"]");
				
					if((tabchannellmt==channellmt) && (tabcperdaylmt==cperdaylmt))
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
			
			checkEqual('U');
			
			
			
			
			
		}


		function checkEqual(val)
		{

			var finaljsonobj=[];

			var myval =  buildSingleRequestjson(branchdata); 
			var newupdateddataobj = jQuery.parseJSON(myval);
			
			if(newupdateddataobj.channellmt=="Select") { 
				 $("#errormsg").html("<font colour='red'>Please Select Channel</font>");
					return false;
			}
			if(newupdateddataobj.cperdaylmt=="") { 
				 $("#errormsg").html("<font colour='red'>Please Enter Amount</font>");
					return false;
			}
			
			$("#ADD").show();
			$("#UPDATE").hide();
			makeempty();
			
			var channellmt = dataupdateval.channellmt;
			var cperdaylmt  = dataupdateval.cperdaylmt;
			
			
			$.each(branchdatajsonObj, function(index,jsonObject)
			{ 
				 	// alert(jsonObject.Services);
				
				 	var tabchannellmt = jsonObject.channellmt;
					var tabcperdaylmt  = jsonObject.cperdaylmt;
					
					// && (branchaddress==tabbranchaddress)
					
					 if((channellmt==tabchannellmt) && (cperdaylmt==tabcperdaylmt))
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
	<form name="form1" id="form1" method="post">
		<div id="content" class="span10">
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider">
							&gt;&gt;</span></li>
					<li><a href="createProductAct.action">Product Management</a> <span
						class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Product Creation</a></li>
				</ul>
			</div>
			<div id="errormsg" class="errores"></div>
			<table height="3">
				<tr>
					<td colspan="3">
						<div class="messages" id="messages">
							<s:actionmessage />
						</div>
						<div class="errors" id="errors">
							<s:actionerror />
						</div>
					</td>
				</tr>
			</table>

			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Product Creation  
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
									<td width="20%"><label for="Product Code"><strong>Product
												Code<font color="red">*</font>
										</strong></label></td>
									<td width="30%"><input name="productText" type="text"
										id="productText" class="field"  value="${responseJSON.productText}"
										 /> <%-- value="${responseJSON.productText} " --%> <span
										id="productCode_err" class="errmsg"> </span></td>
									<td width="20%"><label for="Product Description"><strong>Product
												Description<font color="red">*</font>
										</strong></label></td>
									<td width="30%"><input name="productDesc" maxlength='25'
										type="text" id="productDesc"
										value="${responseJSON.productDesc}" class="field" /></td>
								</tr>
								<tr class="even">
									
									<td><label for="Product Currency"><strong>Product
												Currency<font color="red">*</font>
										</strong></label></td>
									<td><s:select cssClass="chosen-select" headerKey=""
											headerValue="Select" list="#respData.CURRENCY_CODE"
											name="binCurrency" id="binCurrency" requiredLabel="true"
											theme="simple" data-placeholder="Choose Product Currency..." />
									</td>
									<td><label for="Application"><strong>Application<font color="red">*</font>
										</strong></label></td>
									<td><s:select cssClass="chosen-select" headerKey=""
											headerValue="Select" list="#respData.APPLICATION_CODE"
											name="application" id="application" requiredLabel="true"
											theme="simple" data-placeholder="Choose Product Currency..." />
									</td>
									
								</tr>
							
								
								<tr class="even" id="limitperday" style="display:none">
									
									<td id="tokenlimit1"><label for="ToKenLimit"><strong>Token Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td id="tokenlimit2"><input type="text" id="tokenlimit" name="tokenlimit" maxlength='25' value="${responseJSON.tokenlimit}" class="field" /></td>
									<td><label for="ToKenLimit"><strong>Per Day Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td><input type="text" id="perdaylimit" name="perdaylimit" maxlength='25' value="${responseJSON.perdaylimit}" class="field" /></td>
									<td id="tokenlimit3" style="display:none"><strong>Super Agent</strong><font color="red">*</font></td>
									<td id="tokenlimit4" style="display:none">
									<select name="agent" id="agent" Class="chosen-select">
													<option value="">Select</option>
													<!-- <option value="AIRTEL">AIRTEL</option> -->
													<option value="UNION_BANK_CUSTOMER">UNION BANK</option>
													<!-- <option value="ALEDIN_AGENCY">ALEDIN AGENCY AGENT</option> -->
													
												</select>
									</td>
								</tr>
								
								<tr class="even" id="balancemax" style="display:none">
									
									<td ><label for="ToKenLimit"><strong>Maximum Balance Amount<font color="red">*</font>
										</strong></label></td>
									<td ><input type="text" id="ussdinilmt" name="ussdinilmt" maxlength='25' value="${responseJSON.ussdinilmt}" class="field" /></td>
									<%-- <td><label for="ToKenLimit"><strong>USSD 2FA Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td><input type="hidden" id="ussdsecfalmt" name="ussdsecfalmt" maxlength='25' value="${responseJSON.ussdsecfalmt}" class="field" /></td>
									 --%>
									 <td></td>
									 <td></td>
								</tr>
								
								<tr class="even" id="balancemax1" >
									
									<td ><label for="ToKenLimit"><strong>USSD Initial Limit<font color="red">*</font>
										</strong></label></td>
									<td ><input type="text" id="ussdinitallmt" name="ussdinitallmt" maxlength='25' value="${responseJSON.ussdinitallmt}" class="field" /></td>
									<td><label for="ToKenLimit"><strong>USSD 2FA Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td><input type="text" id="ussdsecfalmt" name="ussdsecfalmt" maxlength='25' value="${responseJSON.ussdsecfalmt}" class="field" /></td>
									 
									
								</tr>
								
							<tr class="even" id="feename1" >
										<td width="20%"><label for="Bank">
									<strong>Fee Charge to <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
								<input type="radio" name="feecharge" id="Customer" onclick="myFunction(this.value)" required="true" value="Customer"/>&nbsp;<strong>Customer</strong>&nbsp;&nbsp;&nbsp;
								<input type="radio" name="feecharge" id="Agent" onclick="myFunction(this.value)" required="true" value="Agent" />&nbsp;<strong>Agent</strong>
							
									<input name="feename" id="feename"  type="hidden" maxlength ='25'  required="true"  class="field"  readonly /><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
									
							<td></td>
							<td></td>
								</tr> 
								
							</table>
							


						</fieldset>
					 
					
					<div id="primaryDetails" class="box-content" >
						
							<div class="box span12" id="channellimitperday"> 
					<div class="box-header well" data-original-title>Channel Per Day Limit
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
			<div class="box-content"> 				
			
			
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							   class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
													
													<tr class="odd">
														<td width="20%"><label for="Channel"><strong>Channel<font color="red">*</font></strong></label></td>
														<td width="30%">
														<select id="channellmt" name="channellmt" data-placeholder="Choose User Level..."
																		class="chosen-select" style="width: 220px;" required=true  >
															<option value="">Select</option>
															
							
														</select>
														
														
														</td>											
														<td width="20%"><label for="Services"><strong>Per Day Limit Amount<font color="red">*</font></strong></label></td>
														<td width="30%"><input name="cperdaylmt" id="cperdaylmt"  type="text"  class="field" /></td>	
														
													</tr>
								
													
						</table>
							
			
			
			</div> 
			
			</div> </div> 
			
		

			<br/>	
				
			
	<div  class="box-content" id="channellimitperday1">	
	
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
										<th width="25%">Channel</th>
										<th width="25%">Per Day Limit Amount</th>
										<th width="10%">Action</th>
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							 
		</table>
		
		 
		
		</div> 
			
					</div>
				</div>
				





			</div>
<input type="hidden" id="finaljsonarray" name="finaljsonarray" >
			<div class="form-actions">
				<a class="btn btn-danger" href="#" onClick="getProductScreen()">Back</a>
				&nbsp;&nbsp; <a class="btn btn-success" href="#"
					onClick="createProduct()">Submit</a>
			</div>
		</div>
	</form>

	<script>
	
 $(function() {
	 
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
	  	 
		 
		  
		/* $('#plasticCode').val(ses); 
		$('#plasticCode').trigger("liszt:updated");  */
	});
 </script>

</body>
</html>
