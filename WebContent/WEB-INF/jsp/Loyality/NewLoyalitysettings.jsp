
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

<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>


<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js" type="text/javascript"></script>

<script type="text/javascript" >




var createlmtRules = {
	      rules : {
		    Product			: { required : true },
			limitDescriptionval 	: { required : true },
			MinPoints 			: { required : true,number :true },
			UnitSize 			: { required : true,number :true },
			redemptionpoints 			: { required : true,number :true },
			redemptionamount 			: { required : true,number :true }
		
		
	   },  
	   messages : {
		   		Product : { 
		         required : "Please Select Product."
		       },  
		       limitDescriptionval : {
				 required :"Please Enter Loyalty Description."
			   },  
			   MinPoints : { 
		         required : "Please Enter Redemption Min Points."
		       },  
		       UnitSize : {
				 required :"Please Enter Redemption Unit Size."
			   }, 
			   redemptionpoints : { 
		         required : "Please Enter Redemption Points."
		       },
		       redemptionamount : { 
			         required : "Please Enter Redemption Amount."
			       }
  }
};








	
$(function() {
	
	
	 $('#Product').on('change', function (e) {
	    	var valueSelected = this.value;
	    	//alert(valueSelected);
	    	
	    	$('#prddes').text(valueSelected.split("-")[1]);
	    	$('#prdcry').text(valueSelected.split("-")[2]);
	    	$('#appl').text(valueSelected.split("-")[3]);
	    	
	    	$('#productcode').val(valueSelected.split("-")[0]);
	    	$('#productdesc').val(valueSelected.split("-")[1]);
	    	$('#productcurr').val(valueSelected.split("-")[2]);
	    	$('#application').val(valueSelected.split("-")[3]); 
	    	
	    });
 

		
		
		
		
	
		
		$('#btn-submit').live('click', function () {
		
			$("#form1").validate(createlmtRules); 
	 		if($("#form1").valid()) {
			
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/loyalityconf.action';
			$("#form1").submit();
			return true;
	 		} else {
	 			return false;
			} 
			
		});
		
});
	


</script>

<s:set value="responseJSON" var="respData"/> 
</head>

<body>	
	
		<div id="content" class="span10"> 
		    <div>
					<ul class="breadcrumb">
					  <li><a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li><a href="loyalitypointsettings.action">Loyalty Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <!-- <li><a href="#">Setting</a></li> -->
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
	<form name="form1" id="form1" method="post">
	
				
			
				<div class="box span12"> 
				
				
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
															
									
									</td>
									<td width="20%"><label for="Product Description"><strong>Product Description</strong></label></td>
									<td width="30%"><div id="prddes"></div>
									
									</td>
								</tr>
								<tr class="even">
									
									<td><label for="Product Currency"><strong>Product Currency</strong></label></td>
									<td><div id="prdcry"></div>
									</td>
									<td><label for="Application"><strong>Application</strong></label></td>
									<td><div id="appl"></div>
									</td>
									
								</tr>
								

								
							</table>
							


						</fieldset>
					</div>
				</div>
				</div>
				
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Loyalty Point Setting
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>   
						
					<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Loyalty Code<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="limitCodeval" type="text" maxlength ='6' id="limitCodeval" required="true" class="field"  value="<s:property value='#respData.LIMIT_CODE' />"  readonly/> <span id="bin_err" class="errmsg"></span></td>
								 <td width="20%"><label for="Limit Description"><strong>Loyalty Description<font color="red">*</font></strong></label></td>
								 <td width="30%"><input name="limitDescriptionval"  type="text" maxlength ='25' id="limitDescriptionval" required="true"  class="field"  /><span id="bin_err1" class="errmsg"></span> </td>
							</tr>
							
							<tr class="even">
								<td width="20%"><label for="Min Points"><strong>Redemption Min Points<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="MinPoints" type="text" maxlength ='6' id="MinPoints" required="true" class="field"  value="" /></td>
								 <td width="20%"><label for="Unit Size"><strong>Redemption Unit Size<font color="red">*</font></strong></label></td>
								 <td width="30%"><input name="UnitSize"  type="text" maxlength ='25' id="UnitSize" required="true"  class="field"  value=""/> </td>
							</tr>
							
						</table>
						
					</fieldset>
					
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							
								
							
							<tr class="even">
								<td width="20%"><label for="Min Points"><strong>Redemption Points<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="redemptionpoints" type="text" maxlength ='6' id="redemptionpoints" required="true" class="field"  value="" /></td>
								 <td width="20%"><label for="Unit Size"><strong>Redemption Amount<font color="red">*</font></strong></label></td>
								 <td width="30%"><input name="redemptionamount"  type="text" maxlength ='25' id="redemptionamount" required="true"  class="field"  value=""/> </td>
							</tr>
							
						
							
							
							
						</table>
						
					</fieldset>
				
					<input name="productcode"  type="hidden" id="productcode" class="field" />
					<input name="productdesc" type="hidden" id="productdesc" class="field" />
					<input name="productcurr" type="hidden" id="productcurr" class="field" />
					<input name="application" type="hidden" id="application" class="field" />
						
					
					</div> 		
		</div>
		
		<a  class="btn btn-success" href="#" id="btn-submit" >Submit</a>
		&nbsp; <a  class="btn btn-danger" href="#"  id="btn-cancel" >Cancel</a>
			
		</form>
				
				
		<!-- Fee Settings End -->
				
						
	
	
			

	</div>			
	</div>
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
	 
	});
 </script>
 
 
 
</body>
</html>
