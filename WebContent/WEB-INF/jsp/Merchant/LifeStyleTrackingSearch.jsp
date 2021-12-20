<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">

<link href="${pageContext.request.contextPath}/css/link/css1" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/link/screen.css">
<link href="${pageContext.request.contextPath}/css/link/sticky.css" rel="stylesheet" type="text/css">

<script type="text/javascript">



 function funcheck(){
	var v=0;
	if($('#application').val()=="MERCHANTID" || $('#application').val()=="PRODUCTID" || $('#application').val()=="ACCNO" || $('#application').val()=="PAYMENT_REF"){
		if($("#fname").val()!=""){
			v=1;
		}
	}else if($('#application').val()=="DATE"){
		if($("#fromdate").val()!=""){
			v=1;
		}
		if($("#todate").val()!=""){
			v=1;
		}
	}
	
	
	
	return v;
	
}

 var list = "fromdate,todate".split(",");
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
 
 
			   
var verror="";	
			
			$(function () { 
				

				$(list).each(function(i,val){
					$('#'+val).datepicker(datepickerOptions);
				}); 
				verror="Select Option";
				
			$('#application').on('change', function (e) {
				$('#errormsg').text("");
				
				$('#fname').val("");
					$("#searchenter").hide();
					$("#datewise").hide();
					verror="";
					if(this.value =='MERCHANTID')	
		    		 {
							$("#searchenter").show();
				    		$("#rettext").text('Enter Merchant id');
				    		verror="Enter Merchant id";
				    		
				    		
		    		 }
					else if(this.value =='PRODUCTID')	
		    		 {
							$("#searchenter").show();
				    		$("#rettext").text('Enter Product id');
				    		verror="Enter Product id";
				    		
				    		
		    		 }
					else if(this.value =='ACCNO')	
		    		 {
							$("#searchenter").show();
				    		$("#rettext").text('Enter Account Number');
				    		verror="Enter Account Number";
				    		
				    		
		    		 }
		    		else if(this.value =='PAYMENT_REF')
		    		{		
		    				$("#searchenter").show();
		    				$("#rettext").text('Enter Txn Reference No');
		    				verror="Enter Txn Reference No";
		    				
		    				
		    		}
		    		else if(this.value =='DATE')
		    		{		$("#searchenter").hide();
		    				$("#datewise").show();
		    				verror="Enter Date";
		    				
		    				
		    		}
				});
				
				
					$( "#fname" ).keyup(function() {
					
					$( "#fname" ).val((this.value).toUpperCase());
					});
					
			        $('#btn-submit').live('click', function () { 
			        	
			        	
			        	
			        	 if(funcheck()==1){
			        		 
			        		
			        		
			        			 var url="${pageContext.request.contextPath}/<%=appName %>/lifestyletrackingdetails.action"; 
				    				$("#form1")[0].action=url;
				    				$("#form1").submit();
				    				return true;
			        		
			        		 
			        		
			        		
			        	}else{
							$('#errormsg').text(verror);
						}		   
			    		
			        }); 
				
		    });  


			
			function redirectAct(){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
				$("#form1").submit();
				return true;
			}

			
function funval(v){
	$("#trans").val(v);
}

</script>
<s:set value="responseJSON" var="respData" />

<style>
div.gallery {
    margin: 5px;
    border: 1px solid #ccc;
    float: left;
    width: 180px;
}

div.gallery:hover {
    border: 1px solid #777;
}

div.gallery img {
    width: 100px;
    height: 100px;
}

div.desc {
    padding: 15px;
    text-align: center;
}
</style>

</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">LifeStyle Tracking</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
			
			 
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<!-- Customer Negotiated Rate Confirmation -->
						Search Details
						 <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
					
				 
					<div class="box-content" id="secondaryDetails">
						<fieldset>
						
						<table width="80%"  border="0" cellpadding="5" cellspacing="1" align="center" >
						<tr><td>
						<div class="gallery"  style="border:4px solid #00ADEF;border-style: hidden hidden solid hidden;" >
						  
						    <img src="${pageContext.request.contextPath}/images/lifestyle/Prepare.png" alt="Sales Order" width="300" height="200"><span><strong>${responseJSON.BILLER_COUNT1}</strong></span>
						  
						</div>
						</td><td>
						<div class="gallery" style="border:4px solid #00ADEF;border-style: hidden hidden solid hidden;">
						 
						    <img src="${pageContext.request.contextPath}/images/lifestyle/Merchant_Call.jpg" alt="Order Drafting" width="300" height="200"><span><strong>${responseJSON.BILLER_COUNT2}</strong></span>
						  
						</div>
						</td><td>
						<div class="gallery" style="border:4px solid #00ADEF;;border-style: hidden hidden solid hidden;">
						 
						    <img src="${pageContext.request.contextPath}/images/lifestyle/Dispatch.png" alt="Dispatched" width="300" height="200"><span><strong>${responseJSON.BILLER_COUNT3}</strong></span>
						  
						</div>
						</td><td>
						<div class="gallery" style="border:4px solid #00ADEF;;border-style: hidden hidden solid hidden;">
						
						    <img src="${pageContext.request.contextPath}/images/lifestyle/Delivered.png" alt="Delivered" width="300" height="200">
						  
						</div>
						</td><td>
						<div class="gallery" style="border:4px solid #00ADEF;;border-style: hidden hidden solid hidden;">
						 
						    <img src="${pageContext.request.contextPath}/images/lifestyle/Search.jpg" alt="Search" width="300" height="200">
						 
						</div>
						</td>
						</tr>
						<tr>
						<td colspan="5">
						<div class="section-inner" >
									<div class="segmented-control"  style="color: #00ADEF;font-weight: bold;font-size: 14px;">
					                    	<input type="radio" name="ser" id="Sales"  onclick="funval(this.id)" checked>
					                    	<input type="radio" name="ser" id="Drafting" onclick="funval(this.id)">
					                    	<input type="radio" name="ser" id="Dispatched"  onclick="funval(this.id)">
					                    	<input type="radio" name="ser" id="Delivered" onclick="funval(this.id)">
					                    	<input type="radio" name="ser" id="Search"  onclick="funval(this.id)">
					                     	<label for="Sales" data-value="Sales Order"><span  style="color: #000000;font-weight: bold;font-size: 12px;">Sales Order</span></label>
                    						<label for="Drafting" data-value="Order Drafting"  ><span  style="color: #000000;font-weight: bold;font-size: 12px;">Order Drafting</span></label>
                    						<label for="Dispatched" data-value="Dispatched" ><span  style="color: #000000;font-weight: bold;font-size: 12px;">Dispatched</span></label>
                    						<label for="Delivered" data-value="Delivered" ><span  style="color: #000000;font-weight: bold;font-size: 12px;">Delivered</span></label>
                    						<label for="Search" data-value="Search" ><span  style="color: #000000;font-weight: bold;font-size: 12px;">Search</span></label>
					                  </div>
						</div>
						
						</td>
						</tr>
						</table>
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd" >
								<td width="25%"><label for="Application"><strong>Select Option<font color="red">*</font>
										</strong></label></td>
									<td width="25%"><s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'MERCHANTID':'Merchant Id','PRODUCTID':'Product Id','ACCNO':'Account Number','PAYMENT_REF':'Txn Reference No','DATE':'Date'}" 
							         name="application" 
							         id="application"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
									</td>
									<td width="25%"></td>
									<td width="25%"></td>
							</tr>
								<tr class="odd" id="searchenter" style="display:none">
									<td ><label for="fname"><strong><span id="rettext"></span><font color="red">*</font></strong></label></td>
									<td ><input name="fname" autocomplete="off" type="text" class="field" id="fname"  value="" required=true   />
									
									</td>
									<td></td>
									<td></td>
								
								</tr>
								
								<tr class="even" id="datewise" style="display:none" >
									<td ><label for="Date"><strong>From Date</strong><font color="red">*</font></label></td>
									<td > 
										<input type="text" maxlength="10"  class="fromDate" id="fromdate" name="fromdate" required=true  />  							
									</td>
									<td ><label for="Date"><strong>To Date</strong><font color="red">*</font></label></td>
									<td > 
										<input type="text" maxlength="10"  class="fromDate" id="todate" name="todate" required=true  />  							
									</td>
									
								</tr>	
								
							</table> 
							
						</fieldset>
						<input name="trans"  type="hidden"  id="trans"  value="Sales"   />
					</div>
					
				</div>
			</div>
			
			
			
			<div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Search" width="100"  ></input> 
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
