
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

	
	
$(document).ready(function(){   
	
	
 	
 	$('#disablecust').click(function(){ 
		if($("#form1").valid()) {
			
			var custid5=$('#custcode').val();
			
			
			
			$('#accountid').val(custid5);
			
				$('#closed').val('resetpin');	
		
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/walletresendpin.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		}
});
 	
 	
 	
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true; 
		
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/customeServicesAct.action";
		$("#form1").submit();		
	}); 
	
 
});

</script> 



</head> 
<body>

<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Customer Services</a> &gt;&gt; </span> </li> 
					   <li> <a href="#">Pin Reset</a>  </li> 
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
	<form name="form1" id="form1" method="post" autocomplete="off">
		
  <div class="row-fluid sortable"> 
	<div class="box span12"> 
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Pin Reset Confirmation
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
							<td width="30%"><s:property value='accBean.customercode' /><input type="hidden" name="custcode"  id="custcode"   value="<s:property value='accBean.customercode' />" /></td>
							<td width="20%"><label for="To Date"><strong>Customer Name</strong></label><input type="hidden" name="institute"  id="institute"   value="<s:property value='accBean.institute' />"   /> </td>
							<td width="30%"><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<td><label for="To Date"><strong>Mobile Number</strong></label> </td>
							<td><s:property value='accBean.telephone' />
							<input type="hidden" value="<s:property value='accBean.telco' />" style="width:85px;" readonly name="telco" id="telco"/>&nbsp;
								<input type="hidden" value="<s:property value='accBean.isocode' />" style="width:35px;" readonly name="isocode" id="isocode"/>&nbsp;
								<input type="hidden" value='<s:property value='accBean.telephone' />' style="width: 80px;"  name="telephone" id="telephone" readonly style="width:130px;" />
 							</td>
							<td><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value='accBean.idnumber' /><input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='accBean.nationalid' />"   />  </td>
						</tr>
						<tr class="even">
							<td><label for="From Date"><strong>Email ID</strong></label></td>
							<td><s:property value='accBean.email' /><input type="hidden" name="email"  id="email" readonly  value="<s:property value='accBean.email' />"   />  </td>
							<td><label for="To Date"><strong>Preferred Language</strong></label> </td>
							<td><s:property value='accBean.langugae' /> <input type="hidden" name="langugae"  id="langugae"   value="<s:property value='accBean.langugae' />"   />  
							
									 <input type="hidden" name="STATUS" id="STATUS" value="${STATUS}" />
		  							 <input type="hidden" name="AUTH_CODE"  id="AUTH_CODE" value="${AUTH_CODE}"  />
									 <input type="hidden" name="REF_NO" id="REF_NO" value="${REF_NO}"/>
									 <input type="hidden" name="DECISION" id="DECISION" />
									 <input type="hidden" name="remark" id="remark" />
									 <input type="hidden" name="type" id="type" value="${type}"/>
									 <input type="hidden" name="makerid" id="makerid" value="${makerId}"/>
									 <input type="hidden" name="MID" id="MID" value="${MID}"/>
									 <input type="hidden" name="multiData" id="multiData"/>
									 <input type="hidden" name="check" id="check"/>
									 <input type="hidden" id=closed name="closed" />
									 <input type="hidden" id="accountid" name="accountid" />
									 <input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
							</td>
						</tr>
						<tr class="even">
							<td width="20%"><label for="From Date"><strong>On-Boarded Date</strong></label></td>
							<td width="30%"><s:property value='accBean.authDttm' /><input type="hidden" name="authDttm"  id="authDttm"   value="<s:property value='accBean.authDttm' />" /></td>
							<td width="20%"><%-- <label for="From Date"><strong>Institute</strong></label> --%></td>
							<td width="30%"><input type="hidden" name="institute"  id="institute"   value="<s:property value='accBean.institute' />" /></td>
						</tr> 
						<tr class="even">
							<td width="20%"><label for="Customer Status"><strong>Customer Status</strong></label></td>
							<td width="30%"><s:property value='accBean.status' /><input type="hidden" name="status"  id="status"   value="<s:property value='accBean.status' />" /></td>
							<td width="20%"><label for="From Date"><strong>Pin Status</strong></label></td>
							<td width="30%"><s:property value='accBean.clearedbalance' /><input type="hidden" name="clearedbalance"  id="clearedbalance"   value="<s:property value='accBean.clearedbalance' />" /></td>
						</tr> 
						<tr class="even">
							<td width="20%"><label for="Product"><strong>Product</strong></label></td>
							<td width="30%"><s:property value='accBean.product' /></td>
							<td width="20%"><label for="Description"><strong>Description</strong></label></td>
							<td width="30%"><s:property value='accBean.prodesc' /></td>
						</tr> 
					
				 </table>
				</fieldset> 
				</div>  
				
	  </div>
	  </div> 
	  

	
	
	

 
 
 <span id ="error_dlno" class="errors"></span>
 
 
    	<div class="form-actions" >
    		<input type="button" class="btn btn-info" name="disablecust" id="disablecust" value="Confirm" width="100" ></input>&nbsp;
	         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input> 
	          
	         
       </div>  
 </form>	  
 <script src="${pageContext.request.contextPath}/js/jquery.cleditor.min.js"></script>
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
	    
	    var pinstat=$('#clearedbalance').val();
	    //alert(pinstat);
	    
	    if (pinstat=='Locked Active Pin (max tries)')
	    	{
	    	$("#pinunblock").show();
	    	}
	    
	    var pinstat1=$('#status').val();
	    //alert(pinstat);
	    
	    if (pinstat1=='Active')
	    	{
	    	$("#disablecust").show();
	    	}
	    else{
	    	$("#enablecust").show();
	    }
	    
	    
	}); 
 
</script>
</body> 
</html>