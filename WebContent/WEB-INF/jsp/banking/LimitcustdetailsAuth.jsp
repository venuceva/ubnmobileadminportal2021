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
	buildbranchtable();
	 
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


function buildbranchtable()
{

	$("#tbody_data").empty();
	
	var htmlString="";
	var chdata=$("#chlimit").val();
	var spidat=chdata.split("\|");
	
	for(i=0;i<spidat.length-1;i++){
		
			htmlString = htmlString + "<tr class='values' id="+i+">";
		
			htmlString = htmlString + "<td id='channellmt' name=channellmt >" + spidat[i].split("#")[0] + "</td>";
			htmlString = htmlString + "<td id='cperdaylmt' name=cperdaylmt >" + spidat[i].split("#")[1] + "</td>";
		
			htmlString = htmlString + "</tr>";
			

	
	}
	
	
	$("#tbody_data").append(htmlString);

}

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
							<td width="20%"><label for="From Date"><strong>Customer Id</strong></label></td>
							<td width="30%"><s:property value='#respData.custcode' /><input type="hidden" name="custcode"  id="custcode"   value="<s:property value='#respData.custcode' />"   />
							<td width="20%"><label for="To Date"><strong>Customer Name</strong></label><input type="hidden" name="institute"  id="institute"   value="<s:property value='#respData.institute' />"   /> </td>
							<td width="30%"><s:property value='#respData.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='#respData.fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<td ><label for="To Date"><strong>Mobile Number</strong></label> </td>
							<td ><s:property value='#respData.mobileno' />

						        
						      <%--   <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'Safaricom':'Safaricom','Airtel':'Airtel'}" 
							         name="telco" 
							         value="#respData.telco" 
							         id="telco" 
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							         style="width: 95px;"
							         disabled="true"
							         
							           /> --%>
								
								<input type="hidden" value="<s:property value='#respData.isocode' />" style="width:25px;" readonly name="isocode" id="isocode"/>&nbsp;
								<input type="hidden" value='<s:property value='#respData.mobileno' />' style="width: 80px;" maxlength="9" name="telephone" id="telephone" readonly style="width:130px;" />
 							</td>					
							
							<td><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value='#respData.nationalid' /><input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='#respData.nationalid' />"   />  </td>
						</tr>
						<tr class="even">
							<td><label for="From Date"><strong>Email ID</strong></label></td>
							<td><s:property value='#respData.email' /><input type="hidden" name="email"  id="email" readonly  value="<s:property value='#respData.email' />"   />  </td>
							<td><label for="To Date"><strong>Preferred Language</strong></label> </td>
							<td><s:property value='#respData.language' /> <input type="hidden" name="langugae"  id="langugae"   value="<s:property value='#respData.language' />"   /> 
							
							 <input type="hidden" name="STATUS" id="STATUS" value="${STATUS}" />
  							 <input type="hidden" name="AUTH_CODE"  id="AUTH_CODE" value="${AUTH_CODE}"  />
							 <input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
							 <input type="hidden" name="DECISION" id="DECISION" />
							 <input type="hidden" name="remark" id="remark" />
							 <input type="hidden" name="type" id="type" value="${type}"/>
							 <input type="hidden" name="multiData" id="multiData"/>
							 <input type="hidden" id="acttype" name="acttype" value="${responseJSON.acttype}" />		 
							  </td>
						</tr>
						<tr class="even">
							<td><label for="Product"><strong>Product</strong></label></td>
							<td><s:property value='#respData.product' /> <input type="hidden" name="product"  id="product"   value="<s:property value='#respData.product' />"   />  </td>
							<td><label for="Description"><strong>Description</strong></label> </td>
							<td><s:property value='#respData.prodesc' />
							<input type="hidden" value="<s:property value='#respData.prodesc' />" name="prodesc" id="prodesc" readonly style="width:130px;" /> </td>
						</tr>
						<tr class="even">
							<td><label for="Product"><strong>Customer Per Day Limit Amount</strong></label></td>
							<td><s:property value='#respData.pdaylimit' /> <input type="hidden" name="pdaylimit"  id="pdaylimit"   value="<s:property value='#respData.pdaylimit' />"   />  </td>
							<td></td>
							<td></td>
						</tr>
					
				 </table>
				 <input type="hidden" name="chlimit"  id="chlimit"   value="<s:property value='#respData.chlimit' />"   />
				 <br>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										
										<th width="25%">Channel</th>
										<th width="25%">Customer Per Day Limit Amount</th>
									
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							</table>	
				 
				</fieldset> 
				</div>  
				
	  </div>
	  </div> 
	  
	</form>	  
	
	<form name="form2" id="form2" method="post"> 	
	  
	  <div class="row-fluid sortable"> 
		<div class="box span12" id="regacc"> 
				<div id="remarksInformation" class="box-content"> 								
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable " > 									  
								<tr > 
									<td width="15%"><label for="Remarks1" valign="middle"><strong>Enter Remarks<font id="rmk" name="rmk"  color="red">*</font></strong></label></td>
									<td><textarea class="cleditor1" name="rmrk" id="rmrk" ></textarea><div id="errors" class="errors"></div></td> 
									
								</tr> 
						</table>
						</div>		
						
			
		</div>	
		</div>  
			
		<div id="auth-data"> 
				<ul class="breadcrumb">
				 <li> <strong>Authorize&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio2"  class='center-chk' type='radio' value='A' />&nbsp&nbsp </li>
				 <li> <strong>Reject&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp </strong><input  name="authradio" id="authradio1"  class='center-chk' type='radio' value='R' /> &nbsp&nbsp&nbsp</li>
				<li><div id="error_dlno" class="errors"></div></li>
				</ul>
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

	}else{
		$("#remarksInformation").hide();
		$("#auth-data").show();
		$("#btn-edit").show();

		}
	});	  
</script>
</body> 
</html>