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
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fraudactconfiguration.action';
	$("#form1").submit();
	return true;
} 

function subitReq()
{
	$("#ruledesc").val($("#rd").text());
	//$("#rulecode").val($("#ruledetaails1").text());
	var rulecodes="";
	
	var str="${responseJSON.ruledesc}";
	
	if (str.indexOf("AMOUNT") >= 0){
		rulecodes=rulecodes+"AMOUNT:"+$('#amount').val()+",";
		if($('#amount').val()==""){
			$('#errormsg').text("Please Enter Amount ");
			return false;
		}
	}
	
	if (str.indexOf("DAYS") >= 0){
		rulecodes=rulecodes+"DAYS:"+$('#days').val()+",";
		if($('#days').val()==""){
			$('#errormsg').text("Please Enter Days ");
			return false;
		}
	}
	if (str.indexOf("CHANNEL") >= 0){
		rulecodes=rulecodes+"CHANNEL:"+$('#channel').val()+",";
		if($('#channel').val()==""){
			$('#errormsg').text("Please Select Channel ");
			return false;
		}
	}
	if (str.indexOf("USING") >= 0){
		rulecodes=rulecodes+"USING:"+$('#using').val()+",";
		if($('#using').val()==""){
			$('#errormsg').text("Please Select Using ");
			return false;
		}
	}
	if (str.indexOf("HOUR") >= 0){
		rulecodes=rulecodes+"HOUR:"+$('#hours').val()+",";
		if($('#hours').val()==""){
			$('#errormsg').text("Please Enter Hours ");
			return false;
		}
	}
	if (str.indexOf("ACCOUNTNO") >= 0){
		rulecodes=rulecodes+"ACCOUNTNO:"+$('#accno').val()+",";
		if($('#accno').val()==""){
			$('#errormsg').text("Please Enter Account No ");
			return false;
		}
	}
	if (str.indexOf("USERID") >= 0){
		rulecodes=rulecodes+"USERID:"+$('#userid').val()+",";
		if($('#userid').val()==""){
			$('#errormsg').text("Please Enter User Id ");
			return false;
		}
	}
	if (str.indexOf("MOBILE_NUMBER") >= 0){
		rulecodes=rulecodes+"MOBILE_NUMBER:"+$('#mobileno').val()+",";
		if($('#mobileno').val()==""){
			$('#errormsg').text("Please Enter Mobile Number ");
			return false;
		}
	}
	if (str.indexOf("COUNT") >= 0){
		rulecodes=rulecodes+"COUNT:"+$('#count').val()+",";
		if($('#count').val()==""){
			$('#errormsg').text("Please Enter count ");
			return false;
		}
	}
	
	$("#rulecode").val(rulecodes);
	
	$("#contcentermailid").val($('#tags').text());
	$("#form1").validate(datavalidation);
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/FraudCreationconfigConfig.action';
	$("#form1").submit();
	return true;
} 



var finaljson; 

$(document).ready(function () {
	
	var str="${responseJSON.ruledesc}";
	var ruledesc=str;
	$('#rd').text(ruledesc);
	
	$('#amount').on('blur', function() {
		ruledesc=$('#rd').text();
		ruledesc=ruledesc.replace("AMOUNT",this.value);
		$('#rd').text(ruledesc);
	});
	
	$('#days').on('blur', function() {
		ruledesc=$('#rd').text();
		ruledesc=ruledesc.replace("DAYS",this.value);
		$('#rd').text(ruledesc);
	});
	
	$('#hours').on('blur', function() {
		ruledesc=$('#rd').text();
		ruledesc=ruledesc.replace("HOUR",this.value);
		$('#rd').text(ruledesc);
	});
	
	$('#accno').on('blur', function() {
		ruledesc=$('#rd').text();
		ruledesc=ruledesc.replace("ACCOUNTNO",this.value);
		$('#rd').text(ruledesc);
	});
	
	$('#userid').on('blur', function() {
		ruledesc=$('#rd').text();
		ruledesc=ruledesc.replace("USERID",this.value);
		$('#rd').text(ruledesc);
	});
	
	$('#mobileno').on('blur', function() {
		ruledesc=$('#rd').text();
		ruledesc=ruledesc.replace("MOBILE_NUMBER",this.value);
		$('#rd').text(ruledesc);
	});
	
	$('#using').on('change', function() {
		ruledesc=$('#rd').text();
		ruledesc=ruledesc.replace("USING",this.value);
		$('#rd').text(ruledesc);
	});
	
	$('#channel').on('change', function() {
		ruledesc=$('#rd').text();
		ruledesc=ruledesc.replace("CHANNEL",this.value);
		$('#rd').text(ruledesc);
	});
	
	$('#count').on('blur', function() {
		ruledesc=$('#rd').text();
		ruledesc=ruledesc.replace("COUNT",this.value);
		$('#rd').text(ruledesc);
	});
	
	
	if (str.indexOf("AMOUNT") >= 0){
		$('#AMOUNT').css("display","");
	}
	if (str.indexOf("DAYS") >= 0){
		$('#DAYS').css("display","");
	}
	if (str.indexOf("CHANNEL") >= 0){
		$('#CHANNEL').css("display","");
	}
	if (str.indexOf("USING") >= 0){
		$('#USING').css("display","");
	}
	if (str.indexOf("HOUR") >= 0){
		$('#HOUR').css("display","");
	}
	if (str.indexOf("ACCOUNTNO") >= 0){
		$('#ACCOUNTNO').css("display","");
	}
	if (str.indexOf("USERID") >= 0){
		$('#USERID').css("display","");
	}
	if (str.indexOf("MOBILE_NUMBER") >= 0){
		$('#MOBILE_NUMBER').css("display","");
	}
	if (str.indexOf("COUNT") >= 0){
		$('#COUNT').css("display","");
	}
	
	
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
	
	
	var queryString = "";	
	$.getJSON("FraudCreationCountJSONAct.action", queryString,function(data) { 
		var billerCount =data.responseJSON.BILLER_COUNT;
		$('#fraudcode').val(billerCount);
	});
	
	$("#SMS").click(function() {
        if (this.checked) {
        	$("#Customersms").val("YES");
        }else{
        	$("#Customersms").val("NO");
        }
		    
	 });
	
	$("#EMAIL").click(function() {
        if (this.checked) {
        	$("#Customeremail").val("YES");
        }else{
        	$("#Customeremail").val("NO");
        }
		    
	 });
	    	  
	    	 
	    	  
	 
});



 
var datavalidation = {
 		
 		rules : {
 			frauddesc	: { required : true},
 			contcentermailid : { required : true},
			
 		},		
 		messages : {
 			
 			frauddesc : { 
 							required : "Please Enter Fraud Description.",
					 },
			contcentermailid : { 
 							required : "Please Enter Contact Mail Id."
					 }
			
					 
 		} 
 };
 
 

function decisionfun(v){
	$('#decisions').val(v);
}


$(function(){ // DOM ready

	  // ::: TAGS BOX

	  $("#contcentermailid1").on({
		 
	    focusout : function() {
	      var txt = this.value.replace(/[^a-z0-9\+\-\.\#]/ig,''); // allowed characters
	      
	      if(txt) $('#tags').append("<span>"+(this.value).replace(",","")+",</span>");
	      this.value = "";
	    },
	    keyup : function(ev) {
	      // if: comma|enter (delimit more keyCodes with | pipe)
	      if(/(188|13)/.test(ev.which)) $(this).focusout(); 
	   
	    }
	   
	  });
	  $('#tags').on('click', 'span', function() {
	    if(confirm("Remove "+ $(this).text() +"?")) $(this).remove(); 
	  });

	});
	
</script>
<style>
#tags{
  float:left;
  /* border:1px solid #ccc; */
  padding:5px;
  font-family:Arial;
}
#tags > span{
  cursor:pointer;
  display:block;
  float:left;
  color:#000000;
  font-weight: bold;
  background:#ffffff;
  padding:5px;
  padding-right:25px;
  margin:4px;
}
#tags > span:hover{
  opacity:0.7;
}
#tags > span:after{
 position:absolute;
 content:"X";
 color:white;
    border:1px solid; 
    background:red; 
/*  padding:2px 5px;
 margin-left:3px; */
 font-size:16px;
}
#tags > input{
 
  border:0;
  margin:4px;
  padding:7px;
  width:auto;
}
</style>
<s:set value="responseJSON" var="respData" />
</head>

<body>
<form name="form1" id="form1" method="post" action="">

	<div id="content" class="span10">  
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="#">Fraud Monitor Configuration</a></li>
				</ul>
			</div>
		
	<div id="errormsg" class="errores"></div>
		 
		  <div class="row-fluid sortable"><!--/span-->
			<div class="row-fluid sortable">
					
					<div id="primaryDetails" class="box-content">
						
							<div class="box span12"> 
					<div class="box-header well" data-original-title>Fraud Monitor Configuration
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
														<td width="20%"><label for="Channel"><strong>Fraud Id<font color="red">*</font></strong></label></td>
														<td width="30%"><input name="fraudcode" id="fraudcode"  type="text"  class="field" readonly/></td>											
														<td width="20%"><label for="Services"><strong>Fraud Description <font color="red">*</font></strong></label></td>
														<td width="30%"><input name="frauddesc" id="frauddesc"  type="text"  class="field" /></td>	
														
													</tr>
													<tr class="odd">
														<td width="20%"><label for="custsms"><strong>Customer<font color="red">*</font></strong></label></td>
														<td width="30%" colspan="3">
														<input type="checkbox" id="SMS" name="SMS" value="YES" checked>SMS</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 							<input type="checkbox" id="EMAIL" name="EMAIL" value="NO" >E-MAIL</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														</td>											
														
														
													</tr>
													<tr class="odd">
														<td width="20%"><label for="Services"><strong>Contact Mail Id <font color="red">*</font></strong></label></td>
														<td width="30%" colspan="3">
														<div id="tags">
														<input name="contcentermailid1" id="contcentermailid1"  type="text"  class="field" placeholder="Enter Mail Id" />
														</div>
														<input name="contcentermailid" id="contcentermailid"  type="hidden"   />
														</td>	
													</tr>
													<tr class="odd">
														<td width="20%"><label for="custsms"><strong>Decision<font color="red">*</font></strong></label></td>
														<td width="30%" colspan="3">
														<input type="radio" id="Automatically" name="Decision" value="Automatically" onclick="decisionfun(this.id)" checked>Automatically Disable Customer</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 							<input type="radio" id="Manually" name="Decision" value="Manually" onclick="decisionfun(this.id)" >Manually</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														</td>											
														
														
													</tr>
													 <tr class="odd">
														<td width="20%"><label for="rule"><strong>Rule<font color="red">*</font></strong></label></td>
														<td colspan="3">${responseJSON.ruledesc}
														</td>	
														
													</tr>
										</table>
										<table>
													<tr class="odd" id="AMOUNT" style="display:none">
														<td width="20%"><label for="rule"><strong>Amount<font color="red">*</font></strong></label></td>
														<td colspan="3"><input input type="text" id="amount" name="amount" />
														</td>	
														
													</tr>
													<tr class="odd" id="DAYS" style="display:none">
														<td width="20%"><label for="rule"><strong>Days<font color="red">*</font></strong></label></td>
														<td colspan="3"><input input type="text" id="days" name="days" />
														</td>	
														
													</tr>
													<tr class="odd" id="CHANNEL" style="display:none">
														<td width="20%"><label for="rule"><strong>Channel<font color="red">*</font></strong></label></td>
														<td colspan="3">
														<select id="channel" name="channel" class="chosen-select-width">
														<option value="">Select</option>
														<option value="ALL">ALL</option>
														<option value="USSD">USSD</option>
														<option value="MOBILE">MOBILE</option>
														</select>
														</td>	
														
													</tr>
													<tr class="odd" id="HOUR" style="display:none">
														<td width="20%"><label for="rule"><strong>Hours<font color="red">*</font></strong></label></td>
														<td colspan="3"><input input type="text" id="hours" name="hours" />
														</td>	
														
													</tr>
													<tr class="odd" id="ACCOUNTNO" style="display:none">
														<td width="20%"><label for="rule"><strong>Account No<font color="red">*</font></strong></label></td>
														<td colspan="3"><input input type="text" id="accno" name="accno" />
														</td>	
														
													</tr>
													<tr class="odd" id="USERID" style="display:none">
														<td width="20%"><label for="rule"><strong>User Id<font color="red">*</font></strong></label></td>
														<td colspan="3"><input input type="text" id="userid" name="userid" />
														</td>	
														
													</tr>
													<tr class="odd" id="MOBILE_NUMBER" style="display:none">
														<td width="20%"><label for="rule"><strong>Mobile Number<font color="red">*</font></strong></label></td>
														<td colspan="3"><input input type="text" id="mobileno" name="mobileno" />
														</td>	
														
													</tr>
													<tr class="odd" id="COUNT" style="display:none">
														<td width="20%"><label for="rule"><strong>Count<font color="red">*</font></strong></label></td>
														<td colspan="3"><input input type="text" id="count" name="count" />
														</td>	
														
													</tr>
													<tr class="odd" id="USING" style="display:none">
														<td width="20%"><label for="rule"><strong>Using<font color="red">*</font></strong></label></td>
														<td colspan="3">
														<select id="using" name="using" class="chosen-select-width">
														<option value="">Select</option>
														<option value="ANY">ANY</option>
														<option value="DEBIT CARD">DEBIT CARD</option>
														<option value="BRANCH">BRANCH</option>
														</select>
														</td>	
														
													</tr>
										 			
												<tr class="odd"  >
														<td width="20%"><label for="rule"><strong>Rule Desc</strong></label></td>
														<td colspan="3"><div id="rd"></div>
														</td>	
														
													</tr>
													
						</table>
						
			</fieldset>
			
			</div> 
			
			</div> </div> 
			<input type="hidden"  name="Customersms" id="Customersms"  value="YES"/>
			<input type="hidden"  name="Customeremail" id="Customeremail"  value="NO" />
			<input type="hidden"  name="ruledesc" id="ruledesc" value="${responseJSON.ruledesc}" />
			<input type="hidden"  name="rulecode" id="rulecode"   />
			<input type="hidden"  name="ruletype" id="ruletype" value="${responseJSON.rulecode}" />
			<input type="hidden"  name="decisions" id="decisions"  value="Automatically"/>
			
			<span id="ruledetaails1" style="display:none" ></span> <!-- style="display:none" -->
			
		<div class="form-actions" id="submitdata" > 
				
				
				
				<input type="button" id="non-printable" class="btn btn-success" onclick="redirectAct();" value="Back" />
				<input type="button" id="non-printable" class="btn btn-success" onclick="subitReq();" value="Submit" />
		</div>
		
		
		
		</div>
		
		</div>
		
		
		 
		
 
	</div>
</form>	
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 


</body>
</html>
