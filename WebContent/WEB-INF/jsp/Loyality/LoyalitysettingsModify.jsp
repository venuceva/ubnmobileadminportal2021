
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
			  
				MinPoints 			: { required : true,number :true },
				UnitSize 			: { required : true,number :true },
				redemptionpoints 			: { required : true,number :true },
				redemptionamount 			: { required : true,number :true }
			
			
		   },  
	   messages : {
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
	
	
		$('#btn-submit').live('click', function () {
		
			$("#form1").validate(createlmtRules); 
	 		if($("#form1").valid()) {
			
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/loyalityModifyconf.action';
			$("#form1").submit();
			return true;
	 		} else {
	 			return false;
			} 
		});
		
	$('#btn-cancel').live('click', function () {
			
			
			
			
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
			$("#form1").submit();
			return true;
			
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
					  <li><a href="loyalitypointsettings.action">Loyalty Management</a> </li>
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
									<td width="30%"> ${responseJSON1.PRODUCT} 
									<input name="productcode"  type="hidden" id="productcode" value="${responseJSON1.PRODUCT}" />
									
									<td><label for="Application"><strong>Application</strong></label></td>
									<td> ${responseJSON1.APPLICATION} 
									<input name="application"  type="hidden" id="application" value="${responseJSON1.APPLICATION}" />
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
								<td width="20%"><label for="Limit Code"><strong>Loyalty Code</strong></label></td>
								<td width="30%">${responseJSON1.LOYALTY_CODE}
								<input name="limitCodeval"  type="hidden" id="limitCodeval" value="${responseJSON1.LOYALTY_CODE}" />
								</td>
								 <td width="20%"><label for="Limit Description"><strong>Loyalty Description</strong></label></td>
								 <td width="30%">${responseJSON1.LOYALTY_DESC}
								 <input name="limitDescriptionval"  type="hidden" id="limitDescriptionval" value="${responseJSON1.LOYALTY_DESC}" />
								 </td>
							</tr>
							
							<tr class="even">
								<td width="20%"><label for="Min Points"><strong>Redemption Min Points<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="MinPoints" type="text" maxlength ='6' id="MinPoints" required="true" class="field"  value="${responseJSON1.R_MIN_POINTS}" /></td>
								 <td width="20%"><label for="Unit Size"><strong>Redemption Unit Size<font color="red">*</font></strong></label></td>
								 <td width="30%"><input name="UnitSize"  type="text" maxlength ='25' id="UnitSize" required="true"  class="field"  value="${responseJSON1.R_UNIT_SIZE}"/> </td>
							</tr>
							
						</table>
						
					</fieldset>
					
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							
								
							
							<tr class="even">
								<td width="20%"><label for="Min Points"><strong>Redemption Points<font color="red">*</font></strong></label></td>
								<td width="30%"><input name="redemptionpoints" type="text" maxlength ='6' id="redemptionpoints" required="true" class="field"  value="${responseJSON1.R_AMOUNT}" /></td>
								 <td width="20%"><label for="Unit Size"><strong>Redemption Amount<font color="red">*</font></strong></label></td>
								 <td width="30%"><input name="redemptionamount"  type="text" maxlength ='25' id="redemptionamount" required="true"  class="field"  value="${responseJSON1.R_MIN_POINTS}"/> </td>
							</tr>
							
						
							
							
						</table>
						
					</fieldset>
				
					
				
						
					
					</div> 		
		</div>
		
		<a  class="btn btn-success" href="#" id="btn-submit" >Submit</a>
		&nbsp; <a  class="btn btn-danger" href="#"  id="btn-cancel" >Home</a>
			
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
