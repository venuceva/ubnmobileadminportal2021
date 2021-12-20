<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %> 
 <script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery-ui-1.8.21.custom.min.js'></script> 
 <link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery-ui-1.8.21.custom.min.css' />  
 

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
<script type="text/javascript">
var acctRules = {
		   rules : {
			   accountNumber : { required : true},
			   aliasName : { required : false}
			   
		   },  
		   messages : {
			   accountNumber : { 
			   		required : "Please enter Account Number."
				},
				aliasName : { 
			   		required : "Please enter Alias Name."
				}
		   } 
		 };
		
		var subrules = {
				   rules : {
					   langugae : { required : true},
					   telephone : { required: true },
					  
				   },  
				   messages : {
					   langugae : { 
					   		required : "Please Select Language."
						},
						telephone : { 
					   		required : "Please Input Mobile Number."
						}
				   } 
				 };
		
 
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
		 
		//$('#billerMsg').text('');
		//$('#error_dlno').text(''); 
		$("#form1").validate(subrules);
		
		
		
		
		 if($("#form1").valid() ) {  
			 var v=parseInt($("#custperdaylimit").val());
			 var vm=parseInt($("#MOBILE").val());
			 var vu=parseInt($("#USSD").val());
			 
			 if(v<=vm){
				  
				 $('#errors').text('Customer Per Day Limit Amount should less than equals to MOBILE Channel Limit ');
				 return false;
			 }
			 
			 if(v<=vu){
				  
				 $('#errors').text('Customer Per Day Limit Amount should less than equals to USSD Channel Limit ');
				 return false;
			 }
			
			 
			 submitfun();
				 $("#dialog").dialog({
				      buttons : {
				        "Confirm" : function() { 
				        	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/enhancementregcustinfodetconfirm.action';
							$("#form1").submit();
				        },
				        "Cancel" : function() {
				           $(this).dialog("close");
				        }
				      }
				    });
				  
				  //  $("#dialog").dialog("open");  
				 
				 return true; 
			  
			 
		 } else {
			 //alert("in else");
			 return false;
		 }
			
	});
});	




$(function() {  
	buildbranchtable();
	
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
		    
		    
		   
	  //alert($('#product').val());  	
	    	
});

function buildbranchtable()
{

	$("#tbody_data").empty();
	
	var htmlString="";
	var chdata=$("#plimitchannel").val();
	var chdata1=$("#climitchannel").val();
	var spidat=chdata.split("\|");
	var spidat1=chdata1.split("\|");
	
	for(i=0;i<spidat.length-1;i++){
		
			htmlString = htmlString + "<tr class='values' id="+i+">";
		
			htmlString = htmlString + "<td id='channellmt' name=channellmt >" + spidat[i].split("#")[0] + "</td>";
			htmlString = htmlString + "<td id='channellmt1' name=channellmt1 ><input type='hidden' id='"+spidat[i].split("#")[0]+"1' name='"+spidat[i].split("#")[0]+"1' value='" + spidat[i].split("#")[1] + "' />" + spidat[i].split("#")[1] + "</td>";
			htmlString = htmlString + "<td id='cperdaylmt' name=cperdaylmt ><input type='text' id='"+spidat[i].split("#")[0]+"' name='"+spidat[i].split("#")[0]+"' value='" + spidat1[i].split("#")[1] + "' readonly /></td>";
			htmlString = htmlString + "<td id='channellmt1' name=channellmt1 ><div id = 'slider-"+spidat[i].split("#")[0]+"'></div></td>";
			htmlString = htmlString + "</tr>";
			

	
	}
	
	
	$("#tbody_data").append(htmlString);
	
}

function submitfun(){
	
	var val1="";
	var chdata=$("#plimitchannel").val();
	var chdataspt=chdata.split("\|");
	for(i=0;i<chdataspt.length-1;i++){
		
		val1=val1+chdataspt[i].split("#")[0]+"#"+$("#"+chdataspt[i].split("#")[0]).val()+"|"
	}
	
	$("#plimitchannel").val(val1)
}

$(function() {
		 var v=parseInt($("#custperdaylimit").val());
		 var v1=0;
		 var v2=$("#pdaylimitamt").val();
		 var v3=parseInt($("#pdaylimitamt").val())/200;
		 
			$( "#slider-1" ).slider({
			 value:v,
			 min: v1,
			 max: v2,
			 step: v3,
			 slide: function( event, ui ) {
			   $( "#custperdaylimit" ).val(ui.value );
			  
			 }
			});
			
			
			 /* var vsf=parseInt($("#secfalimit").val());
			 var vsf1=0;
			 var vsf2=$("#USSD1").val();
			 var vsf3=parseInt($("#USSD1").val())/200;
			 
				$( "#slider-2" ).slider({
				 value:vsf,
				 min: vsf1,
				 max: vsf2,
				 step: vsf3,
				 slide: function( event, ui ) {
				   $( "#secfalimit" ).val(ui.value );
				  
				 }
				}); */
		       
	       
       var vm=parseInt($("#MOBILE").val());
  	   var v1m=0;
  	   var v2m=$("#MOBILE1").val();
  	   var v3m=parseInt($("#MOBILE1").val())/200;
       
       
	       $( "#slider-MOBILE" ).slider({
	    	   value:vm,
	    	   min: v1m,
	    	   max: v2m,
	    	   step: v3m,
	    	   slide: function( event, ui ) {
	    	     $( "#MOBILE" ).val(ui.value );
	    	    
	    	   }
	    	  });
       
       	var vu=parseInt($("#USSD").val());
       	var v1u=0;
    	var v2u=$("#USSD1").val();
    	var v3u=parseInt($("#USSD1").val())/200;
       
	       $( "#slider-USSD" ).slider({
	    	   value:vu,
	    	   min: v1u,
	    	   max: v2u,
	    	   step: v3u,
	    	   slide: function( event, ui ) {
	    	     $( "#USSD" ).val(ui.value );
	    	    
	    	   }
	    	  });
    });
	 
</script>

</head> 

<body>

		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Modify Customer Details</a>  </li> 
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
							<td width="25%"><s:property value='accBean.customercode' /><input type="hidden" name="customercode"  id="customercode"   value="<s:property value='accBean.customercode' />"   /></td>
							<td width="25%"><label for="To Date"><strong>Customer Name</strong></label> </td>
							<td width="25%"><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<%-- <td ><label for="From Date"><strong>Branch Code</strong></label></td>
							<td ><s:property value='accBean.branchcode' /> <input type="hidden" name="branchcode"  id="branchcode"   value="<s:property value='accBean.branchcode' />"   />  </td> --%>
							<td><label for="To Date"><strong>Mobile Number</strong></label> </td>
					       <td ><s:property value='accBean.telephone' />
					       <input type="hidden" value='<s:property value='accBean.telephone' />'  name="telephone" id="telephone" /> 
								
								<input type="hidden" value="<s:property value='accBean.telco' />" style="width:85px;"  name="telco" id="telco"/>&nbsp;
								<input type="hidden" value="<s:property value='accBean.isocode' />" style="width:25px;"  name="isocode" id="isocode"/>&nbsp;
								 
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
							<td ><label for="Product"><strong>Current Product</strong></label></td>
							<td ><s:property value='accBean.product' />
							</td>
							 <td><label for="Description"><strong>Description</strong></label></td>
					       <td><s:property value='accBean.prodesc' /></td>  
						</tr>
						
						<tr class="even">
									
									<td><label for="ToKenLimit"><strong>Token Limit Amount
										</strong></label></td>
									<td><s:property value='accBean.tokenamt' />
									<input type="hidden" id="tokenamt" name="tokenamt" maxlength='25' value="<s:property value='accBean.tokenamt' />" class="field" /></td>
									<td><label for="ToKenLimit"><strong>Product Per Day Limit Amount
										</strong></label></td>
									<td><s:property value='accBean.pdaylimitamt' />
									<input type="hidden" id="pdaylimitamt" name="pdaylimitamt" maxlength='25' value="<s:property value='accBean.pdaylimitamt' />" class="field" /></td>
									
								</tr>
								<tr class="even">
									
									<td><label for="ToKenLimit"><strong>Customer Per Day Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td><input type="text" id="custperdaylimit" name="custperdaylimit" maxlength='25' value="<s:property value='accBean.custperdaylimit' />" class="field" readonly /></td>
									<td colspan="2"><div id = "slider-1" ></div>
	
									</td>
									
									
								</tr>
								
								<%-- <tr class="even">
									
									<td><label for="ToKenLimit"><strong>USSD 2FA Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td><input type="text" id="secfalimit" name="secfalimit" maxlength='25' value="<s:property value='accBean.secfalimit' />" class="field" readonly /></td>
									<td colspan="2"><div id = "slider-2" ></div>
	
									</td>
									
									
								</tr> --%>
						
				 </table>
				 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details1">
				 <thead>
									<tr>
										
										<th width="25%">Channel</th>
										<th width="25%">Product Per Day Limit Amount</th>
										<th width="25%">Customer Per Day Limit Amount</th>
										<th width="25%"></th>
									
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
				 </table>
				 
				 <input type="hidden"   name="apptype"  id="apptype"   value="<s:property value='accBean.apptype' />"   />
				</fieldset> 
				
				</div>
				</div> 
				<input type="hidden"  name="plimitchannel" id="plimitchannel"  value="<s:property value='accBean.plimitchannel' />" />
				<input type="hidden"  name="climitchannel" id="climitchannel"  value="<s:property value='accBean.climitchannel' />" />
					<input type="hidden"  name="product" id="product"  value="<s:property value='accBean.product' />" />
				 	<input type="hidden"  name="prodesc" id="prodesc"  value="<s:property value='accBean.prodesc' />" />
				 	
				
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
 <script type="text/javascript">
$(function(){
	
	
	
	$('#accountNumber').live('keypress',function(){
		//console.log($(this).length);
		if($(this).length == 0){
			$('#billerMsg').text('');
		}
	});
	
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