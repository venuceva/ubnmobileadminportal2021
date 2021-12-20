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
<link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.css' />	
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
	
	 
 	$('#btn-submit').live('click',function() {
 		
 		var finalData = "";
 		 $("#form2").validate(remarkRules);
 		 $("#error_dlno").text(''); 
 		 
 		
 		var searchIDs="";
		$("div#auth-data input:radio:checked").each(function(index) {
			searchIDs=$(this).val();			
			$('#DECISION').val(searchIDs);
		});
		if(searchIDs.length == 0) {
			$("#error_dlno").text("Please check atleast one record."); 
		} else {
		 $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/commonAuthRecordconfirm.action";
		 $("#form1").submit();	 
		}
		
		
	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true; 
		
		var actype= $('#acttype').val(); 		
		if (actype=="REG"){
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/authreglist.action';		
		}
		else{
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action';
		}
		$("#form1").submit();		
	}); 
	

});

</script> 


</head> 
<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
		
			<div id="content" class="span10">  
			    <div> 
				<ul class="breadcrumb">
				 <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				 <li> <a href="AuthorizationAll.action">Authorization</a> <span class="divider"> &gt;&gt; </span></li>
				 <li><a href="#"> ${type} Authorization Details </a></li>
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
					<i class="icon-edit"></i>Terminal Details
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
							<td width="20%"><label for="From Date"><strong>Terminal Id</strong></label></td>
							<td width="30%"><s:property value='#respData.terminal_id' /><input type="hidden" name="custcode"  id="custcode"   value="<s:property value='#respData.terminal_id' />"   />
							<td width="20%"><label for="To Date"><strong>Model Number</strong></label><input type="hidden" name="institute"  id="institute"   value="<s:property value='#respData.institute' />"   /> </td>
							<td width="30%"><s:property value='#respData.MODEL_NO' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='#respData.MODEL_NO' />"   />  </td>
						</tr>  
						 <tr class="even">
							<td ><label for="To Date"><strong>Terminal Make</strong></label> </td>
							<td ><s:property value='#respData.TERMINAL_MAKE' />

								</td>					
							
							<td><label for="To Date"><strong>Serial Number</strong></label> </td>
							<td><s:property value='#respData.SERIAL_NO' /><input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='#respData.SERIAL_NO' />"   />  </td>
						</tr> 
						<tr class="even">
							<td ><label for="To Date"><strong>Status</strong></label> </td>
							<td ><s:property value='#respData.STATUS' />

								</td>					
							
							<td><label for="To Date"><strong>Created Date</strong></label> </td>
							<td><s:property value='#respData.MAKER_DTTM' /><input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='#respData.MAKER_DTTM' />"   />  </td>
						</tr> 
						
				 </table>
				</fieldset> 
				</div>  
				
	  </div>
	  </div> 
	  
	   <input type="hidden" name="STATUS" id="STATUS" value="${STATUS}" />
  							 <input type="hidden" name="AUTH_CODE"  id="AUTH_CODE" value="${AUTH_CODE}"  />
							 <input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
							 <input type="hidden" name="DECISION" id="DECISION" />
							 <input type="hidden" name="remark" id="remark" />
							 <input type="hidden" name="type" id="type" value="${type}"/>
							 <input type="hidden" name="multiData" id="multiData"/>
	  
	</form>	  
	
<form name="form2" id="form2" method="post"> 	
	  
	  
			
		<div id="auth-data"> 
				<ul class="breadcrumb">
				 <li> <strong>Authorize&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio2"  class='center-chk' type='radio' value='A' />&nbsp&nbsp </li>
				 <li> <strong>Reject&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio1"  class='center-chk' type='radio' value='R' /> &nbsp&nbsp&nbsp</li>
				<li><div id="error_dlno" class="errors"></div></li>
				</ul>
				
				
				     <span id ="error_dlno" class="errors"></span>
	           
		</div>  
 					 
	 
 </form>
 
    	<div class="form-actions" >
	         <input type="button" class="btn btn-success"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>&nbsp;
	         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input> &nbsp;
	         
       </div>  
 
 <script src="${pageContext.request.contextPath}/js/jquery.cleditor.min.js"></script>
 <script type="text/javascript">
 
$(function(){
	
if($('#tbody_data > tr').length < 1){
	$("#regacc").hide();
	}

	var auth=$('#STATUS').val();

	if ( auth == 'C'){
		$("#auth-data").hide();
		$("#btn-submit").hide();
		$("#rmrk").prop('disabled', true);
		$("#rmk").hide();

	}else if ( auth == 'D'){
		$("#auth-data").hide();
		$("#btn-submit").hide();
		$("#rmrk").prop('disabled', true);
		$("#rmk").hide();

	}
	else{
		$("#remarksInformation").hide();
		$("#auth-data").show();
		$("#btn-edit").show();

		}
	});	  
</script>
</body> 
</html>