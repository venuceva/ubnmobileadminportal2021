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
var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);

var verror="";	

 function funcheck(){
	var v=0;
	if($('#application').val()=="ACCT_NO" || $('#application').val()=="PAYMENT_REF"){
		if($("#fname").val()!=""){
			v=1;
			if($('#searchval').val() =="olddate"){
				if($("#fromdate").val()==""){
					v=0;
					verror="Enter Date Range";
				}
				if($("#todate").val()==""){
					v=0;
					verror="Enter Date Range";
				}
			}
		}
	}
		
	
	
	
	if($('#wapplication').val()=="MOBILE_NO" || $('#wapplication').val()=="PAYMENT_REF" || $('#wapplication').val()=="WALLET_REF" || $('#wapplication').val()=="POSRRN"){
		if($("#fname").val()!=""){
			v=1;
			if($('#wapplication').val()=="POSRRN"){
				
				 if($("#terminalid").val()==""){
					
					v=0;
					verror="Enter Terminal Id";
				} 
			}
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
 
 
			   

			
			$(function () { 
				
				$("#mobileselect").css("display", "none");
				$("#walletselect").css("display", "none");
				
				$("#WALLET").prop("disabled", true);
				$("#MOBILE").prop("disabled", true);
				$("#WALLET").prop("checked", true);
				$('#trans').val("WALLET");
				$.each(jsonLinks, function(index, v) {
					
				
					
					if(v.name=="wtransactionstatus"){
						
						$("#WALLET").prop("disabled", false);
						$("#walletselect").css("display", "");
					}
					if(v.name=="mtransactionstatus"){
						$('#trans').val("MOBILE");
						$("#MOBILE").prop("disabled", false);
						$("#MOBILE").prop("checked", true);
						$("#WALLET").prop("checked", false);
						$("#mobileselect").css("display", "");
					}
					
				});
				
				
				

				$(list).each(function(i,val){
					$('#'+val).datepicker(datepickerOptions);
				}); 
				
				var selValue = $('input[name=ecust]:checked').val();
				
				//alert(selValue);
				funval(selValue);
				
				
			$('#application').on('change', function (e) {
				$('#errormsg').text("");
				$('#fname').val("");
					$("#searchenter").hide();
					$("#searchenter1").hide();
					$("#datewise").hide();
					verror="";					
					if(this.value =='ACCT_NO')	
		    		 {
							$("#searchenter").show();
				    		$("#rettext").text('Enter Account Number');
				    		verror="Enter Account Number";
				    		
				    		
		    		 }
			
		    		else if(this.value =='PAYMENT_REF')
		    		{		
		    				$("#searchenter").show();
		    				$("#rettext").text('Enter Payment Reference No');
		    				verror="Enter Payment Reference No";
		    				
		    				
		    		}
		    		else if($('#searchval').val() =='olddate')
		    		{		if($('#todate').val()==""){
		    					verror="Enter To Date";
		    				}
				    		if($('#fromdate').val()==""){
		    					verror="Enter To From Date";
		    				}
		    				
		    				
		    		}
				});
			
			
			
			$('#wapplication').on('change', function (e) {
				$('#errormsg').text("");
				$('#fname').val("");
					$("#searchenter").hide();
					$("#searchenter1").hide();
					
					$("#datewise").hide();
					verror="";
					if(this.value =='MOBILE_NO')	
		    		 {
							$("#searchenter").show();
				    		$("#rettext").text('Enter Mobile Number');
				    		verror="Enter Mobile Number";
				    		
				    		
		    		 }
			
		    		else if(this.value =='PAYMENT_REF')
		    		{		
		    				$("#searchenter").show();
		    				$("#rettext").text('Enter Payment Reference No');
		    				verror="Enter Payment Reference No";
		    				
		    				
		    		}
		    		else if(this.value =='WALLET_REF')
		    		{		
		    				$("#searchenter").show();
		    				$("#rettext").text('Enter Wallet Reference No');
		    				verror="Enter Wallet Reference No";
		    				
		    				
		    		}
		    		else if(this.value =='POSRRN')
		    		{		
		    				$("#searchenter").show();
		    				$("#searchenter1").show();
		    				$("#rettext").text('Enter POS RRN');
		    				verror="Enter POS RRN";
		    				
		    				
		    		}
		    		
				});
				
				
					$( "#fname" ).keyup(function() {
					
					$( "#fname" ).val((this.value).toUpperCase());
					});
					
			        $('#btn-submit').live('click', function () { 
			        	
			        	//alert($('#trans').val());
			        	//alert(funcheck());
			        	 if(funcheck()==1){
			        		 
			        		 if($('#trans').val()=="MOBILE"){
			        			 
			        		//alert("jai1");
			        		 var url="${pageContext.request.contextPath}/<%=appName %>/complaincedetails.action"; 
				    				$("#form1")[0].action=url;
				    				$("#form1").submit();
				    				return true;
			        		 
			        		 }
			        		 
			        		 
			        		 if($('#trans').val()=="WALLET"){
			        			 
			        			 //alert("jai2");
				        		   var url="${pageContext.request.contextPath}/<%=appName %>/walletcomplaincedetails.action"; 
					    				$("#form1")[0].action=url;
					    				$("#form1").submit();
					    				return true;
				        		 
				        		 }
			        		
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
				
				$("#mobileselect").css("display", "none");
				$("#walletselect").css("display", "none");
				$("#selectdt").css("display", "none");
				$("#selectolddt").css("display", "none");
				$("#datewise").css("display", "none");
				$("#currdate").prop("checked", true);
				
				if(v=="MOBILE"){
					$("#mobileselect").css("display", "");	
					$("#selectdt").css("display", "");
					$("#selectolddt").css("display", "");
					$('#trans').val("MOBILE");
				}
				if(v=="WALLET"){
					$("#walletselect").css("display", "");	
					
					$('#trans').val("WALLET");
				}
				
			}
			
			
			function funval1(v){
				
				$("#fromdate").val("");
				$("#todate").val("");
				
				$("#searchval").val(v);
				if(v=="currdate"){
					$("#datewise").css("display", "none");		
				}else{
					$("#datewise").css("display", "");
				}
			}

</script>
<s:set value="responseJSON" var="respData" />

</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Transaction Status</a></li>
					
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
						<div class="section-inner">
									<div class="segmented-control" style="width: 600px; color: #00ADEF;font-weight: bold;font-size: 14px;">
					                    	<input type="radio" name="ecust" id="MOBILE" value="MOBILE" onclick="funval(this.id)">
					                    	<input type="radio" name="ecust" id="WALLET" value="WALLET" onclick="funval(this.id)">
					                     	<label for="MOBILE" data-value="Mobile Customer Transactions" ><img src='${pageContext.request.contextPath}/images/Phone.png' align='left' style='width: 40px; height: 40px;'><span style="color: #000000;font-weight: bold;font-size: 12px;">Mobile Customer Transactions</span></label>
                    						<label for="WALLET" data-value="Wallet Customer Transactions"  ><img src='${pageContext.request.contextPath}/images/Wallet.png' align='left' style='width: 40px; height: 40px;'><span style="color: #000000;font-weight: bold;font-size: 12px;">Wallet Customer Transactions</span></label>
					                  </div>
									</div>
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd" id="mobileselect" style="display:none">
								<td width="25%"><label for="Application"><strong>Select Option<font color="red">*</font>
										</strong></label></td>
									<td width="25%"><s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'ACCT_NO':'Account Number','PAYMENT_REF':'Payment Reference No'}" 
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
							
							<tr class="odd" id="walletselect" style="display:none">
								<td width="25%"><label for="Application"><strong>Select Option<font color="red">*</font>
										</strong></label></td>
									<td width="25%"><s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'MOBILE_NO':'Mobile Number','PAYMENT_REF':'Payment Reference No','WALLET_REF':'Wallet Reference No','POSRRN':'POS RRN'}" 
							         name="wapplication" 
							         id="wapplication"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
							       
									</td>
									<td></td>
									<td></td>
									<%-- <td width="25%"><label for="Application"><strong>Select Wallet Type<font color="red">*</font>
										</strong></label></td>
									<td width="25%">
									<s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'AGENT':'Agent','CUSTOMER':'Customer'}" 
							         name="wagenttype" 
							         id="wagenttype"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
									
									</td> --%>
							</tr>
							
							
								<tr class="odd" id="searchenter" style="display:none">
									<td ><label for="fname"><strong><span id="rettext"></span><font color="red">*</font></strong></label></td>
									<td ><input name="fname" autocomplete="off" type="text" class="field" id="fname"  value="" required=true   />
									
									</td>
									<td></td>
									<td></td>
								
								</tr>
								<tr class="odd" id="searchenter1" style="display:none">
									
									<td><strong>Terminal Id<font color="red">*</font></strong></td>
									<td><input name="terminalid" autocomplete="off" type="text" class="field" id="terminalid"  value="" required=true   /></td>
									<td></td>
									<td></td>
								
								</tr>
								
								<tr class="even" id="selectdt" style="display:none" >
									
									<td ><label for="Date"><input type="radio" id="currdate" name="rbutt" onclick=funval1(this.id) checked>
									<strong>New Transactions</strong></label></td>
									<td ></td>
									<td ></td>
									<td ></td>
									
								</tr>
								<tr class="even" id="selectolddt" style="display:none" >
									
									<td ><label for="Date"><input type="radio" id="olddate" name="rbutt" onclick=funval1(this.id)>
									<strong>Old Transactions</strong></label></td>
									<td ></td>
									<td ></td>
									<td ></td>
									
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
							<input type="hidden" id="trans" name="trans" value="TRANSACTION"  /> 
							<input type="hidden" id="searchval" name="searchval" value="currdate" /> 
						</fieldset>
						
					</div>
					
				</div>
			</div>
			
			
			
			<div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Cancel" />
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
