<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString();%>
<%@taglib uri="/struts-tags" prefix="s"%> 

<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
input#abbreviation{text-transform:uppercase};

</style>
<s:set value="responseJSON" var="respData"/>
 <SCRIPT type="text/javascript">
var rowCount1=0;
var remarkRules = {
		 errorElement: "div",
		 errorPlacement: function(error, element) {

          if ( element.is(":radio") ) {
         	 error.appendTo( "div#errors1" );
          }
          else{
         	 error.appendTo( "div#errors" );
               }
      },
			rules : { 
				rmrk : { required : true },
				authradio : { required : true }
			},		
			messages : { 
				rmrk : { 	required : "Please Enter Remarks."	},
				authradio : { required : "Please Select One Option." }
				
					} 
			
		};
	
	
	
	

$(document).ready(function(){
	


	//$("#rmrk").append("${template}");
	var json = '${resJSON.ACTIONLIST}';

	
	var json = jQuery.parseJSON(json);
	 var options = "";
	$.each(json, function(i, v) {
	      options = $('<option/>', {value: v.VALUE, text: v.TEXT }).attr('data-id',i);
	    $('#productName').append(options);
	}); 
	

	$('#filter').live('change', function(){

			 var filter=$('#filter').val();
			 $("#rmrk").val($("#rmrk").val() + filter);
	});


});


$(document).ready(function(){   
	
 	$('#btn-submit').live('click',function() {  
 		
 		
 		
 		var message = $("#rmrk").val();
 	
 	 alert(message);

		$('#cmpMessage').val(message);
	
 	       var finalData = "";
 		 $("#error_dlno").text(''); 
 		 
 		
 	
		
			$("#form1").validate(remarkRules);
			if ($("#form1").valid()==true)
				{
				 $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/insertModifyCampDetailsAct.action";
				 $("#form1").submit();
				} 
	 
	 
		
	}); 
	
	 
    $('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true; 
		 $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/campaignmgntact.action";

		$("#form1").submit();		
	}); 
	
});

</script> 


</head> 
<body>
	<form name="form1" id="form1" method="post" >
	  <div id="content" class="span10"> 
			 
		    <div> 
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="campaignmgntact.action">Campaign Management</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">View Campaign Management</a></li>
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
		 <div class="row-fluid sortable"> 
			<div class="box span12">  
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>View Campaign Management
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						
					</div>
				</div>
							
			<div class="box-content">
				<fieldset>

				<table width="950"  border="0" cellpadding="5" cellspacing="1"
					class="table table-striped table-bordered bootstrap-datatable " >
					  <tr >
					  	    <td><label for="Template Name"><strong>Template Name</strong></label></td>
							<td><input name="templateName" id="templateName"  type="text" class="field" required=true maxlength="50" value="${tempId}" readonly/></td>
 							
 						
					  
					  </tr>
					

					
						
						
				</table>
			</fieldset>
		</div>
		</div>
		
		
		
		</div>
		
		
		 			
 			
 			
 			
 			
 			
 		 	
               <input name="STATUS" type="hidden" id="STATUS" value="${STATUS}" />
  				<input name="AUTH_CODE" type="hidden" id="AUTH_CODE" value="${AUTH_CODE}"  />
				<input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
				<input type="hidden" name="DECISION" id="DECISION" />
				<input type="hidden" name="type" id="type" value="${type}"/>
				<input type="hidden" name="cmpMessage" id="cmpMessage" value=""/>
 				<input type="hidden" name="multiData" id="multiData" value="#respData['multiData']"/>
 				<input type="hidden" id="acttype" name="acttype" value="${responseJSON.acttype}" />		 		 



 <div class="row-fluid sortable"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Campaign Template
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  
				
				
				
				
				
				
			<div id="remarksInformation1" class="box-content"> 								
				<table width="950"  border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable " > 									  
					<tr > 
						<td><label for="Remarks1" valign="middle"><strong>Template Message<font id="rmk" name="rmk"  color="red">*</font></strong></label></td>
						<td><textarea style="margin: 0px 0px 9px; width: 712px; height: 77px;" name="rmrk" id="rmrk" readonly>${template}</textarea><div id="errors" class="errors"></div></td> 
					</tr> 
				</table>
			</div></div>
		</div>	
    	<div class="form-actions" >
	         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Next" width="100" ></input> &nbsp;
       </div>  
</div>

 </form> 
 
	
  <script src="${pageContext.request.contextPath}/js/autosize.js"></script>
  <script type="text/javascript">
 
$(function(){
	
	autosize(document.querySelectorAll('textarea'));

	});	  
</script>
</body> 
</html>