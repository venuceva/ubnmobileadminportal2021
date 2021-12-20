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
	$("#ruledesc").val($("#ruledetaails").text());
	$("#rulecode").val($("#ruledetaails1").text());
	$("#contcentermailid").val($('#tags').text());
	$("#form1").validate(datavalidation);
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/FraudCreationconfigConfig.action';
	$("#form1").submit();
	return true;
} 



var finaljson; 

$(document).ready(function () {
	
	/* var AMOUNT="<input type='text' />";
	var DAYS="<input type='text' />";
	var CHANNEL="<input type='text' />";
	
	var str="Transaction sum amount equals to N${AMOUNT} in ${DAYS} consecutive days via ${CHANNEL}";
	
	
	
	str=str+"<div></div>";
	
	
	$("#kailash").html(str);
	 */
	funnmain();
	
	
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

var fieldval="";
var fieldval1="";

function funnmain(){
	
	var ruled=$('#ruledetaails').text();
	$('#ruledetaails').text("");
	var queryString = "type=MAIN-MAIN";
	$.getJSON("fraudjx.action", queryString, function(data) {
			if(data.region!=""){
			//	alert(data.region);
				var mydata=data.region;
  			//var mydata=(data.region).substring(5,(data.region).length);
  			var mydata1=mydata.split(":");
  	  		$('#bank-details').append("<tr><td><strong>Rule<font color=\"red\">*</font></strong></td><td colspan=\"3\"><select id=\"rules\" name=\"rules\" class=\"chosen-select-width\" style=\"width:400px\" onchange=funParameter(this.value,\"\",this.id)><option value=\"\">Select</option></select></td></tr>");
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
  
   			$.each(mydata1, function(i, v) {
   				
   				var options = $('<option/>', {value: (mydata.split(":")[i]).split("-")[0], text: (mydata.split(":")[i]).split("-")[1]}).attr('data-id',i);
   				
   				$('#rules').append(options);
   				$('#rules').trigger("liszt:updated");
   			});
   			
   			
  		} 
 	}); 
	
}

function funParameter(v,vv,id){
	
	 $('#'+id).prop('disabled', 'disabled');
	 $('#'+id).trigger("liszt:updated");
	 $('#'+fieldval).prop('disabled', 'disabled');
	
	if(vv!="COMPLETED"){
		
	
	var ruled=$('#ruledetaails').text();
	if(vv==""){
		
		$('#ruledetaails').text($('#ruledetaails').text()+" "+v);
		if(v!="TRANSACTION"){
		$('#ruledetaails1').text($('#ruledetaails1').text()+"#"+v);
		}
		$('#ruletype').val(v);
		
	}else{
		if(fieldval==""){
			$('#ruledetaails').text($('#ruledetaails').text()+" "+vv);
			$('#ruledetaails1').text($('#ruledetaails1').text()+"#"+vv);
		}else{
			$('#ruledetaails').text($('#ruledetaails').text()+" "+$('#'+fieldval).val()+" "+vv);
			$('#ruledetaails1').text($('#ruledetaails1').text()+"#"+$('#'+fieldval).val()+"#"+vv);
		}
		fieldval="";
	}
	
	var selectids="parameter"+$('#form1').find("select").length;
	  var queryString = "type=PARAMETER-"+v;
	
	$.getJSON("fraudjx.action", queryString, function(data) {
			if(data.region!=""){
			//	alert(data.region);
				var mydata=data.region;
				//alert(mydata);
				if(mydata!=":-"){
					
				
  			//var mydata=(data.region).substring(5,(data.region).length);
  			var mydata1=mydata.split(":");
  			
  		$('#bank-details').append("<tr><td><strong>"+v+"<font color=\"red\">*</font></strong></td><td colspan=\"3\"><select id='"+selectids+"' name='"+selectids+"' class=\"chosen-select-width\" style=\"width:400px\" onchange=funadd(this.value,this.id)><option value=\"\">Select</option></select></td></tr>");
  
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
  		
  		
   			$.each(mydata1, function(i, v) {
   				
   				var options = $('<option/>', {value: (mydata.split(":")[i]).split("-")[0], text: (mydata.split(":")[i]).split("-")[1]}).attr('data-id',i);
   				
   				$('#'+selectids).append(options);
   				$('#'+selectids).trigger("liszt:updated");
   			});
   			
   			
  		} 
			}
 	}); 
	}else{
		
		if(fieldval!=""){
		$('#ruledetaails').text($('#ruledetaails').text()+" "+$('#'+fieldval).val());
		$('#ruledetaails1').text($('#ruledetaails1').text()+"#"+$('#'+fieldval).val());
		fieldval="";
		}
	}
	
}


 function funadd(v,id){
	
	 $('#'+id).prop('disabled', 'disabled');
	 $('#'+id).trigger("liszt:updated");
	
	 var ruled=$('#ruledetaails').text();
		$('#ruledetaails').text($('#ruledetaails').text()+" "+v);
		$('#ruledetaails1').text($('#ruledetaails1').text()+"@"+v);
		
	 var selectids="condition"+$('#form1').find("select").length;
	
		 
		  var queryString = "type=CONDITION-"+v;
 		
	    	$.getJSON("fraudjx.action", queryString, function(data) {
	 			if(data.region!=""){
	 			//	alert(data.region);
	 				var mydata=data.region;
	      			//var mydata=(data.region).substring(5,(data.region).length);
	      			var mydata1=mydata.split(":");
	      			
 	      		$('#bank-details').append("<tr><td><strong>"+v+"<font color=\"red\">*</font></strong></td><td colspan=\"3\"><select id='"+selectids+"' name='"+selectids+"' class=\"chosen-select-width\" style=\"width:400px\" onchange=funadd1(this.value,this.id)><option value=\"\">Select</option></select></td></tr>");

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
	  			    
	       			$.each(mydata1, function(i, v) {
	       				
	       				var options = $('<option/>', {value: (mydata.split(":")[i]).split("-")[0], text: (mydata.split(":")[i]).split("-")[1]}).attr('data-id',i);
	       				
	       				$('#'+selectids).append(options);
	       				$('#'+selectids).trigger("liszt:updated");
	       			});
	       			
	       			
	      		} 
	     	}); 
	
	 
 }
 
 function funadd1(v,id){
	
	 $('#'+id).prop('disabled', 'disabled');
	 $('#'+id).trigger("liszt:updated");
	 
	 var ruled=$('#ruledetaails').text();
		$('#ruledetaails').text($('#ruledetaails').text()+" "+v);
		$('#ruledetaails1').text($('#ruledetaails1').text()+"#"+v);
		
	 var selectids="where"+$('#form1').find("select").length;
	 var fieldids="field"+$('#form1').find("select").length;
	
	 var vv=$('#rules').val();
		
	 var queryString = "type=FIELD-"+v+"-"+vv;
		
   	$.getJSON("fraudjx.action", queryString, function(data) {
			if(data.region!=""){
			//	alert(data.region);
				var mydata=data.region;
				//alert(mydata);
				
     			if((mydata.split(":")[1]).split("-")[0]!="NO"){
     				
 	      		$('#bank-details').append("<tr><td><strong>"+(mydata.split(":")[1]).split("-")[0]+"<font color=\"red\">*</font></strong></td><td colspan=\"3\"><input name='"+fieldids+"' id='"+fieldids+"'  type=\"text\"  class=\"field\" /></td></tr>");
 	      		fieldval=fieldids;
 	      		fieldval1=fieldids;
     			}
     			
     			if((mydata.split(":")[1]).split("-")[2]!="NO"){
     			
 	      		 $('#bank-details').append("<tr><td><strong>Condition<font color=\"red\">*</font></strong></td><td colspan=\"3\"><select id='"+selectids+"' name='"+selectids+"' class=\"chosen-select-width\" style=\"width:400px\" onchange=funParameter('"+vv+"',this.value,this.id)><option value=\"\">Select</option><option value=\"AND\">AND</option><option value=\"COMPLETED\">RULE COMPLETED</option></select></td></tr>");

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
     			}
     		} 
    	}); 


}

 
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
														<td colspan="3"><span id="ruledetaails"></span>
														</td>	
														
													</tr>
													
												
													
						</table>
						<span id="kailash"></span>	
			</fieldset>
			
			</div> 
			
			</div> </div> 
			<input type="hidden"  name="Customersms" id="Customersms"  value="YES"/>
			<input type="hidden"  name="Customeremail" id="Customeremail"  value="NO" />
			<input type="hidden"  name="ruledesc" id="ruledesc"  />
			<input type="hidden"  name="rulecode" id="rulecode"  />
			<input type="hidden"  name="ruletype" id="ruletype"  />
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
