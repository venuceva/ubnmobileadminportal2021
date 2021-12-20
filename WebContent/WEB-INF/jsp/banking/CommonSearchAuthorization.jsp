<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
 <%@taglib uri="/struts-tags" prefix="s"%>
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

.messages {
	font-weight: bold;
	color: green;
	padding: 2px 8px;
	margin-top: 2px;
}

.errors {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}
</style>

<s:set value="responseJSON" var="respData"/>
<script type="text/javascript">
var parentId = "merchant-auth-data > tr";
var subParent = "";
var checkedCheckbox = false;

$(function() {
	
	
	
	var auth_code = "<s:property value='#respData.auth_code' />";
	
	var status = "<s:property value='#respData.status' />";
	//alert(status);
	/*  if(auth_code=="NEWACCAUTH" && status=="P"){
		 $('#srcdiv').html("<select  name=\"srchval\" id=\"srchval\"  class=\"chosen-select-width\" required=\"required\" style=\"width:250px\"><option value=\" \">select</option></select>"); 
	 }else if(auth_code=="NEWACCAUTH" && status=="C"){
		 $('#srcdiv').html("<input type=\"text\"  name=\"srchval\" id=\"srchval\"  >"); 
	 } */
	    
	    
	
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
	 
	$('#srchcriteria').on('change', function (e) {
	    var optionSelected = $("option:selected", this);
	    var valueSelected = this.value;
	    $("#errors").text(" ");
	    if(valueSelected!=""){
	    	 //$("#trow1").show();
					 if (valueSelected.indexOf("CUSTOMER_CODE") >= 0)
			    	 //if(valueSelected =='CUSTOMER_CODE')	
			    		 {
					    		 $("#isocode").hide();
					    		 $("#rettext").text('Enter Customer ID');
			    		 }
					else if(valueSelected.indexOf("ACCT_NO") >=0)
	    				{	
					 			$("#isocode").hide();
								$("#rettext").text('Enter Account Number');
	    				}
			    
			    	else if(valueSelected.indexOf("DOCID") >=0)
			    		{
			    	 			$("#isocode").hide();
			    				$("#rettext").text('Enter BVN');
			    		}
			    
			    	else if(valueSelected.indexOf("MOBILE_NUMBER") >=0)
			    		{
						    	$("#isocode").show();
						    	$("#rettext").text('Enter Mobile Number');			    	
			    		}
			    	else if(valueSelected.indexOf("FILENAME") >=0)
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
 

	$("#btn-back").click(function(event) {
		event.preventDefault();
		<%-- var url="${pageContext.request.contextPath}/<%=appName %>/AuthorizationAll.action"; --%>
		var actype= $('#acttype').val(); 		
		if (actype=="REG"){
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/authreglist.action';		
		}
		else{
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action';
		}
		/* $("#form1")[0].action=url; */
		$("#form1").submit();
	});

	$("#btn-submit").click(function(event) {
		event.preventDefault();
		var searchIDs="";
		var srchval=$('#srchcriteria').val();
		var srchdata=$('#srchval').val();
		if (srchdata.length=='0')
			//alert(srchdata.length);
		{
			$("#error_dlno").text("Please Enter Values");
		}
		else{
		$('#SRCHTRIA').val(srchval);
		$('#SRCHDATA').val(srchdata);
			var url="${pageContext.request.contextPath}/<%=appName %>/commonAuthListAct.action";
			$("#form1")[0].action=url;
			$("#form1").submit();
		}
		
	}); 

});


function getfilename(val){
	//alert(val);
	var auth_code = "<s:property value='#respData.auth_code' />";
	var status = "<s:property value='#respData.status' />";
	
	document.getElementById("srchval").length=1;
    	var queryString = "authcode="+auth_code+"&status="+status+"&actionname="+val;
     		
    	$.getJSON("authvalidsajx.action", queryString, function(data) {
 			if(data.region!=""){
      			var mydata=(data.region).substring(5,(data.region).length);
      			var mydata1=mydata.split(":");
      			
       			$.each(mydata1, function(i, v) {
       				var options = $('<option/>', {value: mydata.split(":")[i], text: mydata.split(":")[i]}).attr('data-id',i);
       				
       				$('#srchval').append(options);
       				$('#srchval').trigger("liszt:updated");
       			});
       			
       			
      		} 
     	}); 
	
}


</script>
</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">All Authorization</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"><span id="header-data" class="merchant"> </span> Confirmation </a></li>
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
						<span id="header-data" class="merchant"> </span> Search Authorization Records
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a> <a href="#"
								class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
						
								<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
								<tr class="even">
									<td width="20%"><label for="Service ID"><strong>Select Search Criteria<font color="red">*</font></strong></label></td>
									<td width="30%" colspan=3> 
									  <select id="srchcriteria" name="srchcriteria" class="chosen-select-width" >
									   <s:if test="#respData.auth_code == 'NEWACCAUTH' " >	   
									   <option value="MD.ACCT_NO">Account Number</option>
									   <option value="MA.CUSTOMER_CODE">Customer ID</option>
									   <option value="MT.MOBILE_NUMBER">Mobile Number</option>
									   <option value="MA.DOCID">BVN</option>
									   
									   </s:if> 
									   <s:elseif test="#respData.auth_code == 'ACCACTDCT' " >	
									   
									   <option value="MA.ACCT_NO">Account Number</option>

									   </s:elseif>
									   <s:elseif test="#respData.auth_code == 'ACCTPINRESET' " >	
									   
									   <option value="MA.CUSTOMER_CODE">Customer ID</option>

									   </s:elseif>
									   <s:elseif test="#respData.auth_code == 'MODCUSTDETAUTH' " >	
									   
									   <option value="MA.CUSTOMER_CODE">Customer ID</option>
									   <option value="MT.MOBILE_NUMBER">Mobile Number</option>
									   <option value="MA.DOCID">BVN</option>

									   </s:elseif>
									   <s:elseif test="#respData.auth_code == 'DELACCAUTH' " >	
									   
									   <option value="MA.CUSTOMER_CODE">Customer ID</option>
									   <option value="MT.ACCT_NO">Account Number</option>

									   </s:elseif>
									   <s:elseif test="#respData.auth_code == 'BULKREGAUTH' " >	
									   
									   <option value="MA.FILENAME">File Name</option>

									   </s:elseif>
									   </select>
									  
	 							</td>		
								</tr>
														
								
								<tr class="odd" id="trow1" name="trow1">
									<td><label><strong><span id="rettext" name="rettext"></span><font color="red">*</font></strong></label></td>
									<td><div id="srcdiv"><input type="text"  name="srchval" id="srchval"  ></div>
									
									
									</td>	
								</tr>
								
								
								
								
							</table>
						</fieldset>
					</div>
					
				</div>
			</div>
			<div class="form-actions"> 
				<input type="button" class="btn btn-info" name="btn-back" id="btn-back" value="Back"  /> 
				<input type="button" class="btn btn-success" name="btn-submit"	id="btn-submit" value="Next"  /> 
			
				<span id ="error_dlno" class="errors"></span> 
  				<input name="STATUS" type="hidden" id="STATUS" value="<s:property value="#respData.status" />" />
  				<input name="AUTH_CODE" type="hidden" id="AUTH_CODE" value="<s:property value="#respData.auth_code" />"  />
				<input type="hidden" name="REF_NO" id="REF_NO" /> 
				<input type="hidden" name="MID" id="MID" />
				<input type="hidden" name="SRCHTRIA" id="SRCHTRIA" />
				<input type="hidden" name="SRCHDATA" id="SRCHDATA" />
				<input type="hidden" id="acttype" name="acttype" value="<s:property value="#respData.acttype" />" />
			</div>
 	</div>
 	
</form>
<form name="form2" id="form2" method="post">
</form>
<script type="text/javascript"> 


var valueSelected = $("#srchcriteria").val();
if (valueSelected.indexOf("CUSTOMER_CODE") >= 0)
	 //if(valueSelected =='CUSTOMER_CODE')	
		 {
	    		 $("#isocode").hide();
	    		 $("#rettext").text('Enter Customer ID');
		 }
	else if(valueSelected.indexOf("ACCT_NO") >=0)
		{	
	 			$("#isocode").hide();
				$("#rettext").text('Enter Account Number');
		}

	else if(valueSelected.indexOf("DOCID") >=0)
		{
	 			$("#isocode").hide();
				$("#rettext").text('Enter National ID');
		}

	else if(valueSelected.indexOf("MOBILE_NUMBER") >=0)
		{
		    	$("#isocode").show();
		    	$("#rettext").text('Enter Mobile Number');			    	
		}
	else if(valueSelected.indexOf("FILENAME") >=0)
	{
	    	$("#isocode").show();
	    	$("#rettext").text('Enter File Name');			    	
	}
</script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
