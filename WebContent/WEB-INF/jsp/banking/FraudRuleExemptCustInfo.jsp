<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
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
.modal-content {
  background-color: #0480be;
}
.modal-body {
  background-color: #fff;
}
</style>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/bootstrap-2.3.2.min.js'></script>
<link href="${pageContext.request.contextPath}/css/link/css1" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/link/screen.css">
<link href="${pageContext.request.contextPath}/css/link/sticky.css" rel="stylesheet" type="text/css">
 
<script type="text/javascript" > 

var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);

$(document).ready(function(){
	
	
	
	
	
	var myApp;
	myApp = myApp || (function () {
	    var pleaseWaitDiv = $('<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false"><div class="modal-header"><h1>Processing...</h1></div><div class="modal-body"><div class="progress progress-striped active"><div class="bar"  style="width: 100%;"></div></div></div></div>');
	    return {
	        showPleaseWait: function() {
	            pleaseWaitDiv.modal();
	        },
	        hidePleaseWait: function () {
	            pleaseWaitDiv.modal('hide');
	        },

	    };
	})();
	
	  $("#t3").css({"display":"none"});
	 // $("#t3").css({"display":"none"});
	  
	  
	$('#srchcriteria').on('change', function (e) {
	    var optionSelected = $("option:selected", this);
	    var valueSelected = this.value;
	    $('#customerId').val("");
	    $("#errors").text(" ");
	    $("#ex-mytable").css({"display":"none"});
	    if(valueSelected!=""){
	    	 $("#trow1").show();

			    	 if(valueSelected =='CUSTOMER_CODE')	
			    		 {
					    		 $("#isocode").hide();
					    		 $("#rettext").text('Enter Customer ID');
			    		 }
				else if(valueSelected =='ACCT_NO')
	    				{	
					 			$("#isocode").hide();
								$("#rettext").text('Enter Account Number');
	    				}
				else if(valueSelected =='USER_ID')
				{	
			 			$("#isocode").hide();
						$("#rettext").text('Enter User Id');
				}
			    else if(valueSelected =='DOCID')
			    		{
			    	 			$("#isocode").hide();
			    				$("#rettext").text('Enter BVN');
			    		}
			    else if(valueSelected =='MOBILE_NUMBER')
			    		{
						    	$("#isocode").show();
						    	$("#rettext").text('Enter Mobile Number');			    	
			    		}
			    	 
			    	 $("#billerMsg").text(" ");
			    	 
	    }
	    else{
					    	 	$("#trow1").hide();
					    	 	$("#isocode").hide();
	    	 	
	    }
	    
	});
	
	var subrules = {
			   rules : {
				   customerId : { required : true}
				   
			   },  
			   messages : {
				   customerId : { 
				   		required : "Please Enter Values."
					}
					
			   } 
			 };
		

			
		
		
		$('#btn-srch').live('click',function() {  
			
			//$("#form1").validate(acctRules);
			
			$("#form1").validate(subrules);
			//alert($("#form1").valid());
			if($("#form1").valid()==true)
				{
		
				var vval="";
				var vid=$('#srchcriteria').val();
				
				if(vid =='MOBILE_NUMBER')
	    		{
					vval=$('#isocode').val()+""+$('#customerId').val();
	    		}else{
	    			vval=$('#customerId').val();
	    		}
				
			if(vval!=null){
				myApp.showPleaseWait();
				
var queryString1 = "method=primaryaccountdetails&refno="+vid+"&custcode="+vval+"&makerid="+ $('#makerid').val()+"&apptype="+$('#apptype').val();

				$.getJSON("postJson.action", queryString1,function(data) {
					//alert(data.finalCount);
					if(data.finalCount==0){
						myApp.hidePleaseWait();
						$("#ex-mytable").css({"display":"none"});
						$("#billerMsg").text(" This Customer need to set Primary Account No. Please Use USSD, set Primary Account No");
					}else if(data.finalCount==-1){
						myApp.hidePleaseWait();
						$("#ex-mytable").css({"display":"none"});
						$("#billerMsg").text(" This Customer Not Found in Mobile Banking ");
					}else{
				
				
				var queryString9 = "method=fetchAccData&refno="+vid+"&custcode="+vval+"&makerid="+ $('#makerid').val()+"&apptype="+$('#apptype').val();
			
			$.getJSON("postJson.action", queryString9,function(data) {
				myApp.hidePleaseWait();
					var custid = data.custcode; 
					var accno = data.accNumber; 
					var fullname = data.accname; 
					var mobile = data.mobile; 
					var nationalid = data.language; 
					var v_message = data.message;
					var v_stat=data.status;
					var v_telco=data.telco;
					var mb=data.mobiles;
					var wl=data.wallet;
					var userid=data.userId;
					
					//alert(v_message);
					//alert(v_stat);
					$("#mt").css({"display":"none"});
					$("#wt").css({"display":"none"});
					if (v_message =='SUCCESS')
					{
						$("#statusmessage").text(fullname+" User Status is "+v_telco);
					
						if(mb=="MOBILE"  && v_telco!="Txn Pin is Pending"){
						
						$("#custid").text(custid);  
						$("#customercodem").val(custid); 
						$("#custname").text(fullname);  
						$("#mobile").text(mobile);  
						/* $("#accno").text(accno);  */ 
						$("#natid").text(nationalid);
						$("#userid").text(userid);
						/* $("#operator").text(v_telco); */
						$("#appname").html("Mobile Banking");
						
						$("#actiontype").html("<a class='btn btn-warning' href='#' id='btn-submitwalletm' index='1' val='1' title='Modify Customer' data-rel='tooltip' > View</i></a>");
						$("#mt").css({"display":""});	
					}else{
						$("#mt").css({"display":"none"});
					}
						
					if(wl=="WALLET"  && v_telco!="Txn Pin is Pending"){
							
							$("#custidw").text(custid);  
							$("#customercodew").val(custid);  
							$("#custnamew").text(fullname);  
							$("#mobilew").text(mobile);  
							/* $("#accno").text(accno);  */ 
							$("#natidw").text(nationalid);
							$("#useridw").text(userid);
							/* $("#operatorw").text(v_telco); */
							$("#appnamew").text("Wallet");
							
							$("#actiontypew").html("<a class='btn btn-warning' href='#' id='btn-submitwalletmw' index='2' val='2' title='Modify Customer' data-rel='tooltip' > <i class='icon icon-edit icon-white'></i></a>");
						$("#wt").css({"display":""});
					}else{
						$("#wt").css({"display":"none"});
						}
					
					$("#t3").css({"display":""});
					$("#billerMsg").text('');
					$("#ex-mytable").css({"display":""});
					$("#btn-submit").css({"display":""});
					}
					else 
						{
						if($('#apptype').val()=="MOBILE"){
							$("#billerMsg").text(v_stat+" Not Found in Mobile Banking ");
						}
						if($('#apptype').val()=="WALLET"){
							$("#billerMsg").text(v_stat+" Not Found in Wallet ");
						}
						}
			
						
			});
					 }
				   });
			}
			}else
				{
				$("#billerMsg").text(v_stat+" Not Found in Database ");
				}
				

		}); 
		    
	 
	
	
<%-- 	$('#btn-submit').click(function(){ 
			//$("#form1").validate(acctRules);
			if($("#form1").valid()) { 
				myApp.showPleaseWait();
 				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modregcustinfoDet.action'; 
				$('#form1').submit();
				 return true; 
			} else { 
				return false;
			}
	}); --%>
	
	$('#actiontype').click(function(){ 
		//$("#form1").validate(acctRules);
		$("#apptype").val("MOBILE");
		$("#customercode").val($("#customercodem").val());
		
		if($("#form1").valid()) { 
			myApp.showPleaseWait();
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fraudruleexemptdet.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		}
});



$('#btn-back').click(function(){ 
	//$("#form1").validate(acctRules);
	
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
		$('#form1').submit();
		
});

});



</script>

</head>

<body>
	<form name="form1" id="form1" method="post" action="">
		<div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="#">Customer Limit Enhancement </a></li>
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
		<div class="row-fluid sortable"><!--/span-->
			<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Customer Details Search
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
						
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
								
								<tr class="even">
									<td width="20%"><label for="Service ID"><strong>Select Search Criteria<font color="red">*</font></strong></label></td>
									<td width="30%" > 
									   <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'USER_ID':'User ID','CUSTOMER_CODE':'Customer ID','ACCT_NO':'Account Number','MOBILE_NUMBER':'Mobile Number'}" 
							         name="srchcriteria" 
							         id="srchcriteria"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
								
	 							</td>
	 							<td></td>	
	 							<td></td>			
								</tr>
								<tr class="odd" id="trow1" name="trow1" style="display:none">
									<td><label><strong><span id="rettext" name="rettext"></span><font color="red">*</font></strong></label></td>
									<td><input type="text" name="isocode" id="isocode" style="display:none; width:25px;" value="234" readonly><input type="text" name="customerId"  id="customerId" required=true  value="<s:property value='customerId' />"   />  
									<input type="hidden" id="customercode" name="customercode">
									<input type="hidden" id="customercodem" name="customercodem">
									<input type="hidden" id="customercodew" name="customercodew">
									<input type="hidden" id="apptype" name="apptype" value="MOBILE" >
									<input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
									 </td>	
									 <td><a class="btn btn-success" href="#" id="btn-srch" name="btn-submit">Search</a></td>
									 <td></td>	
								</tr>
								
								
							</table>
							</fieldset>
					</div>
			</div>
		</div>
		
		
				<div class="row-fluid sortable" id="t3" name="t3"> 
			
						<div class="box span12"> 
					<div class="box-header well" data-original-title >
							<i class="icon-edit"></i>Customer Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							
							<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false">
        <div class="modal-header">
            <h1>Processing...</h1>
        </div>
        <div class="modal-body">
            <div class="progress progress-striped active">
                <div class="bar" style="width: 100%;"></div>
            </div>
        </div>
    </div>
						</div>
					</div>  
					<div class="box-content" id="ex-mytable"  style="display:none"> 
						<table class="table table-striped table-bordered bootstrap-datatable "  style="width: 100%;" >
						  <thead>
								<tr>
									<!-- <th>Sno</th> -->
									<th>Customer ID</th>
									<th>Customer Name </th>
									<th>Mobile Number</th>
									<!-- <th>Operator</th> -->
									<th>Date Of Birth</th>
									<th>User Id</th>
									<th>Application Type</th>
									<th>Modify Action</th>
									<!--<th>Status</th>
         							 <th><input type="checkbox" class="checkbox" name="select-all" id="select-all" title="Select All"/></th> -->
								</tr>
						  </thead>    
						  <tbody > 
							  <tr id="mt" style="display:none" >
							  		
						  			<td><span id="custid" name="custid" ></span></td>
						  			<td><span id="custname" name="custname"></span></td>
						  			<td><span id="mobile" name="mobile"></span></td>
						  			<%-- <td><span id="operator" name="operator"></span></td> --%>
						  			<%-- <td><span id="accno" name="accno"></span></td> --%>
						  			<td><span id="natid" name="natid"></span></td>
						  			<td><span id="userid" name="userid"></span></td>
						  			<td><span id="appname" name="appname" class='label label-success'  ></span></td>
						  			<td><span id="actiontype" name="actiontype"></span></td>
	
							  </tr>
							   <tr id="wt" style="display:none" >
							  		
						  			<td><span id="custidw" name="custidw" ></span></td>
						  			<td><span id="custnamew" name="custnamew"></span></td>
						  			<td><span id="mobilew" name="mobile"></span></td>
						  			<%-- <td><span id="operatorw" name="operatorw"></span></td> --%>
						  			<%-- <td><span id="accno" name="accno"></span></td> --%>
						  			<td><span id="natidw" name="natidw"></span></td>
						  			<td><span id="useridw" name="useridw"></span></td>
						  			<td><span id="appnamew" name="appnamew" class='label label-info'  ></span></td>
						  			<td><span id="actiontypew" name="actiontypew"></span></td>
	
							  </tr>
						  </tbody>
						</table>
						<br>
						<div id="statusmessage" style="color:#0000ff;font-size: 15px;"></div> 
					</div> 
		</div> 
		</div> 
		
		
		
	<div class="form-actions">
		<a  class="btn btn-danger" href="#" id="btn-back" name="btn-back">Home</a>
		<!-- <a  class="btn btn-success" href="#" id="btn-submit" name="btn-submit" style="display:none">Proceed</a> -->
		<span id="billerMsg" class="errors"></span>
	</div>
</div> 
</form>
<script type="text/javascript">
$(function(){
	
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
});  
</script>
</body>
</html>
