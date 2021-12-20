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

<%-- function redirectAct()
{
	$("#form2")[0].action='<%=request.getContextPath()%>/<%=appName %>/channelmanagement.action';
	$("#form2").submit();
	return true;
}  --%>

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
		    
		    $('#Product').on('change', function (e) {
		    	if((this.value)==""){
		    		$('#prddes').text("");
			    	$('#prdcry').text("");
			    	$('#appl').text("");
			    	
			    	$('#productcode').val("");
			    	$('#productdesc').val("");
			    	$('#productcurr').val("");
			    	$('#application').val("");
		    	}
		    	var valueSelected = this.value;
		    	//alert(valueSelected);
		    	
		    	$('#prddes').text(valueSelected.split("-")[1]);
		    	$('#prdcry').text(valueSelected.split("-")[2]);
		    	$('#appl').text(valueSelected.split("-")[3]);
		    	
		    	$('#productcode').val(valueSelected.split("-")[0]);
		    	$('#productdesc').val(valueSelected.split("-")[1]);
		    	$('#productcurr').val(valueSelected.split("-")[2]);
		    	$('#application').val(valueSelected.split("-")[3]);
		    	
		    	
		    	var queryString = "entity=${loginentity}&product="+valueSelected.split("-")[0];

		    	
		    	$.getJSON("retrivefraud.action", queryString, function(data) {
		 			if(data.region!=""){
		 				var mydata=data.region;
		      			var mydata1=mydata.split(":");
		      
		       			$.each(mydata1, function(i, v) {
		       				//alert(mydata.split(":")[i]);
		       				$("#"+mydata.split(":")[i]).attr('checked', true);
		       				
		       			});
		       			
		       			
		      		}else{
		      			
		      		}  
		     	}); 
		    	
		    	
		    	
		    });

	/* var branchs ='${responseJSON.BRANCHS}';
	finaljson = jQuery.parseJSON(branchs);
	console.log(finaljson); */
	
});

 
var datavalidation = {
 		
 		rules : {
 			Product	: { required : true},
 			Channel : { required : true} ,
 			Services : { required : true} ,
 			Limit	: { required : true}, 
 			Fee	: { required : true}
			
 		},		
 		messages : {
 			
 			Product : { 
 							required : "Please Select Product.",
					 },
			Channel : { 
 							required : "Please Select Channel."
					 },
			Services : { 
 							required : "Please Select Services."
					 },
			Limit : { 
 							required : "Please Select Limit ."
					 },
			Fee : { 
							required : "Please Select Fee."
					 }
			
					 
 		} 
 };
 
 

function subitReq()
{   
	
	$(':checkbox:checked').each(function(i){
		 val=true;
		 
		 if($(this).val()=="0000" && i==0){
			 val=false; 
		 }else{
			 val=true; 
		 }
		 
		if(i==0){
			ckval=($(this).val());	
		}else{
			
		 ckval=ckval+","+($(this).val());
	 	}
      });
	$("#fruadrules").val(ckval);
	//alert(ckval);
	
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fraudactassignproductconfirm.action';
	$("#form1").submit();
	return true;
	//alert(ckval);

} 


	
</script>
<s:set value="responseJSON" var="respData" />
</head>

<body>
<form name="form1" id="form1" method="post" action="">	
	<div id="content" class="span10">  
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="channelmanagement.action">Service Mapping</a></li>
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
		 	
		
		 <div id="errormsg" class="errores"></div>
		 <div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Product Details  
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
									<td width="20%"><label for="Product Code"><strong>Product Code<font color="red">*</font></strong></label></td>
									<td width="30%">
									<s:select cssClass="chosen-select" headerKey=""
															headerValue="Select" list="#respData.PRODUCT_TEMP"
															name="Product" id="Product" requiredLabel="true"
															theme="simple" data-placeholder="Choose Channel"
															required="true" /> 
															
									<input name="productcode" maxlength='25'
										type="hidden" id="productcode" class="field" />
									</td>
									<td width="20%"><label for="Product Description"><strong>Product Description</strong></label></td>
									<td width="30%"><div id="prddes"></div>
									<input name="productdesc" maxlength='25'
										type="hidden" id="productdesc" class="field" />
									</td>
								</tr>
								<tr class="even">
									
									<td><label for="Product Currency"><strong>Product Currency</strong></label></td>
									<td><div id="prdcry"></div>
									<input name="productcurr" maxlength='25'
										type="hidden" id="productcurr" class="field" /></td>
									<td><label for="Application"><strong>Application</strong></label></td>
									<td><div id="appl"></div>
									<input name="application" maxlength='25'
										type="hidden" id="application" class="field" /></td>
									
								</tr>
								

								
							</table>
							


						</fieldset>
					</div>
				</div>
				</div>
				
		
		 
		<div class="row-fluid sortable"><!--/span--> 
			<div class="box span12">  
					
					<div id="primaryDetails" class="box-content">
						
							<div class="box span12"> 
					<div class="box-header well" data-original-title>Fraud Rules
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
			<div class="box-content"> 				
			<fieldset>
			
					<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										<th>Select</th>
										<th>Fraud Id</th>
										<th>Fraud Description</th>
										
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 <s:iterator value="responseJSON.SERVICE_MASTER" id="list">
							 <tr><td><input type="checkbox" id=<s:property   value="#list.key"/> value="<s:property   />" /></td><td><s:property   value="#list.key"/></td><td><s:property   value="#list.value"/></td></tr>
							 </s:iterator>
							 </tbody>
							 
					</table>
						

							
			</fieldset>
		
		<input name="fruadrules"  id="fruadrules" type="hidden"   />
		
			
			</div> 
			
			</div> </div> 
			
		

			<br/>	
				
			
	
		
		
	
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
