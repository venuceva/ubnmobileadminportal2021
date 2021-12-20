<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
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
					   telephone : { required: true }
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
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/addNewAccountAct.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){ 
		var finalData = "";
		 var allVals = [];
		 
		//$('#billerMsg').text('');
		//$('#error_dlno').text(''); 
		$("#form1").validate(subrules);
		

		var queryString = "method=verifyMobile&mobile="+ $('#telephone').val();	
		$.getJSON("postJson.action", queryString,function(data) { 
			
			v_message = data.language;
			var v_cusid=v_message.split("-");
			var ccode=$('#customercode').val();
					
				
			if(v_message!=' ' && ccode!=v_cusid[0].trim()){
				
				alert("The Mobile Number Exists for Below Customer \n \n"+v_message +" \n \n Please Use Another");
				
			}
			else {
		
		
		 if($("#form1").valid() ) {  
			 

				 $("#dialog").dialog({
				      buttons : {
				        "Confirm" : function() { 
				        	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/walletregmodifydconf.action';
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
			}
		});
	});
});	




$(function() {  
	
	
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
		    
		    
		    $('#product1').on('change', function() {
		    	 // alert( this.value ); 
		    	  $('#desc').text((this.value).split("-")[1]);
		    	  $('#product').val((this.value).split("-")[0]);
		    	  $('#prodesc').val((this.value).split("-")[1]);
		    	});
		    
		    
		    var queryString = "";
    		
	    	$.getJSON("productajx.action", queryString, function(data) {
	 			if(data.region!=""){
	 			//	alert(data.region);
	 				var mydata=data.region;
	      			//var mydata=(data.region).substring(5,(data.region).length);
	      			var mydata1=mydata.split(":");
	      
	       			$.each(mydata1, function(i, v) {
	       				var options = $('<option/>', {value: (mydata.split(":")[i]).split("@")[0], text: (mydata.split(":")[i]).split("@")[1]}).attr('data-id',i);
	       				
	       				$('#product1').append(options);
	       				$('#product1').trigger("liszt:updated");
	       			});
	       			
	       			
	      		} 
	     	}); 
	  //alert($('#product').val());  	
	    	
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
							<td width="30%"><s:property value='accBean.customercode' /><input type="hidden" name="customercode"  id="customercode"   value="<s:property value='accBean.customercode' />"   /></td>
							<td width="25%"><label for="To Date"><strong>Customer Name</strong></label> </td>
							<td width="30%"><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<%-- <td ><label for="From Date"><strong>Branch Code</strong></label></td>
							<td ><s:property value='accBean.branchcode' /> <input type="hidden" name="branchcode"  id="branchcode"   value="<s:property value='accBean.branchcode' />"   />  </td> --%>
							<td><label for="To Date"><strong>Mobile Number <font color="red">*</font></strong></label> </td>
					       <td >
								<input type="hidden" value="<s:property value='accBean.isocode' />" style="width:25px;" readonly name="isocode" id="isocode"/>&nbsp;
								<input type="text" value='<s:property value='accBean.telephone' />' style="width: 80px;" name="telephone" id="telephone"  style="width:130px;" /> 
								
								<%-- <input type="text" value="<s:property value='accBean.telco' />" style="width:85px;" readonly name="telco" id="telco"/>&nbsp;
								<input type="text" value="<s:property value='accBean.isocode' />" style="width:25px;" readonly name="isocode" id="isocode"/>&nbsp;
								<input type="text" value='<s:property value='accBean.telephone' />' style="width: 80px;" maxlength="9" name="telephone" id="telephone" readonly style="width:130px;" /> --%>
 							</td>
								
 							</td>
							<td  ><label for="To Date"><strong>BVN</strong></label> </td>
							<td><s:property value='accBean.idnumber' />
							<input type="hidden" style="width:85px;"  name="idnumber"  id="idnumber"   value="<s:property value='accBean.idnumber' />"   />  </td>
					
						</tr>
						<tr class="even">
							<td ><label for="From Date"><strong>Email ID</strong></label></td>
							<td ><input type="text" name="email"  id="email"   value="<s:property value='accBean.email' />"   />  </td>
							<td ><label for="To Date"><strong>Preferred Language </strong></label> </td> 
							<td >English
							<input type="hidden" name="langugae"  id="langugae"   value="<s:property value='accBean.language' />"   />  
							
							         
							    <input type="hidden" name="institute"  id="institute"   value="<s:property value='accBean.	institute' />"   />
						       	<input type="hidden" name="newAccountData" id="newAccountData" value="<s:property value='accBean.newAccountData' />" />
								<input type="hidden" name="multiData"  id="multiData"   value="<s:property value='accBean.multiData' />"  />
								<input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
							</td>	  
						</tr>
						
						<tr class="even">
							<td ><label for="Product"><strong>Product</strong></label></td>
							<td >
							<select id="product1" name="product1" class="chosen-select-width" >
							 <option value="">Select</option>
							</select>
							</td>
							 <td><label for="Description"><strong>Description</strong></label></td>
					       <td><div id="desc"></div></td>  
						</tr>
						
						
				 </table>
				</fieldset> 
				</div>
				</div> 
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