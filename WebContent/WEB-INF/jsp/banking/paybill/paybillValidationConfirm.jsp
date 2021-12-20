
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 <%@taglib uri="/struts-tags" prefix="s"%> 
 <link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.min.css' /> 
<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
input#abbreviation{text-transform:uppercase};
</style>
<SCRIPT type="text/javascript"> 

var billerrules = {
		rules : {
			billerId : {required : true },
			billerTypeName : { required : true },
			description : { required : true } ,
			bfubaccount : {required : true }
		},		
		messages : {
			billerId : { 
				required : "Please Enter Biller Id." 
			  }, 
			billerTypeName : { 
				required : "Please Select Biller Type Name." 
			  }, 
			  description : { 
					required : "Please Enter Description." 
				} , 
				bfubaccount : { 
						required : "Please Enter BFUB Account." 
					}
		},
		errorElement: 'label'
	};

$(document).ready(function(){  
	
	$("#form1").validate(billerrules); 
	$('#btn-submit').live('click',function() {  
		if($("#form1").valid()) {
 			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/addBillerIdConfirm.action";
			$("#form1").submit();	
			return true;
		} else {
			return false;
		}
						
	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/mpesaAccManagement.action";
		$("#form1").submit();					
	});	
	 

    $("input[name=billertype]:radio").change(function () {
        if ($("#biller-type").attr("checked")) {
        	$('#biller-type-tr').show();
        	$('#biller-id-tr').hide();
         }
        if ($("#biller-id").attr("checked")){
        	$('#biller-id-tr').show();
        	$('#biller-type-tr').hide();
        }
        
    })
	
});

//For Closing Select Box Error Message_Start
$(document).on('change','select',function(event) {  
	 if($('#'+$(this).attr('id')).next('label').text().length > 0) {
		  $('#'+$(this).attr('id')).next('label').text(''); 
		  $('#'+$(this).attr('id')).next('label').remove();
	 }
 
});
//For Closing Select Box Error Message_End



</SCRIPT>  
</head> 
<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
	  <div id="content" class="span10"> 
			 
		    <div> 
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="mpesaAccManagement.action">Mpesa A/C Management</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Add Biller Id Confirmation</a></li>
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
				 <i class="icon-edit"></i>Basic Details  
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					
				</div>
			</div>
						
			<div class="box-content">
				<fieldset> 
					<table width="98%"  border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable " > 
						 <tr> 
							<td width="20%"><label for="Biller Type Name"><strong>Biller is</strong></label></td>
							<td width="30%" colspan=3>    
							<s:property value='payBillBean.billertype' /><input type="hidden" name="billertype"  id="billertype"   value="<s:property value='payBillBean.billertype' />"   />
									<!-- <input type="radio" id="biller-type" name="billertype" value="BILLER_TYPE"> Biller Type &nbsp;
									<input type="radio" id="biller-id" name="billertype" value="BILLER_ID" checked> Biller Id -->
							</td>		 
						</tr> 
						<tr > 
							<td><label for="Description"><strong>Select Biller Type Name</strong></label></td>
							<td>   <s:property value='payBillBean.billerTypeName' /><input type="hidden" name="billerTypeName"  id="billerTypeName"   value="<s:property value='payBillBean.billerTypeName' />"   /> 
								<%-- <s:select cssClass="chosen-select" 
									headerKey="" 
									headerValue="Select"
									list="responseJSON.billerTypeName" 
									name="billerTypeName" 
									value="payBillBean.billerTypeName" 
									id="billerTypeName" 
									requiredLabel="true" 
									theme="simple"
									data-placeholder="Choose Biller Type Name..." 
 									 />  --%>
 							 </td>  
						</tr> 
						 
						<tr> 
							<td><label for="Regular Expression"><strong>Regular Expression</strong></label></td>
							<td> <s:property value='payBillBean.regex' /> <input type="hidden" name="regex"  id="regex" value="<s:property value='payBillBean.regex' />"    />   </td>  
						</tr> 
						 
						<tr> 
							<td><label for="System Mode"><strong>System Mode</strong></label></td>
							<td> <s:property value='payBillBean.systemmodes' /> <input type="hidden" name="systemmodes"  id="systemmodes" value="<s:property value='payBillBean.systemmodes' />"    />  
							
								<%-- <s:select cssClass="chosen-select" 
									headerKey="" 
									headerValue="Select"
									list="responseJSON.systemmodes" 
									name="systemMode" 
 									id="systemMode" 
									requiredLabel="true" 
									theme="simple"
									data-placeholder="Choose System Mode..." 
 									 />  &nbsp;   --%>
 							</td>  
						</tr>  
						<tr >  
							<td><label for="Has Fixed Amount ?"><strong>Has Fixed Amount ?</strong></label></td>
							<td><!-- <input type="radio" name="fixedamount" value="Y"> Yes &nbsp;
								 <input type="radio" name="fixedamount" value="N"> No 	 -->		
								 <s:property value='payBillBean.fixedamountcheck' /> <input type="hidden" name="fixedamountcheck"  id="fixedamountcheck" value="<s:property value='payBillBean.fixedamountcheck' />"    />   
								 </td>				
						</tr> 
						
						<tr id="biller-tr"> 
							<td><label for="Fixed Amount(ksh)"><strong>Fixed Amount(ksh)</strong></label></td>
							<td> <s:property value='payBillBean.fixedamount' /> <input type="hidden" name="fixedAmount"  id="fixedAmount" value=" <s:property value='payBillBean.fixedamount' />"    />   </td>  
						</tr> 
				</table>
			</fieldset>  
		</div>
	</div>
	</div>   
   	<div class="form-actions" >
         <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100" ></input>&nbsp;
         &nbsp;<input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input>
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