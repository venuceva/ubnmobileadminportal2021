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

function redirectAct()
{
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fraudactconfiguration.action';
	$("#form1").submit();
	return true;
} 

function subitReq()
{
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fconfigurmodifyconfirm.action';
	$("#form1").submit();
	return true;
} 


$(document).ready(function () {
	
	
	$("#SMS").click(function() {
        if (this.checked) {
        	$("#Customersms").val("YES");
        }else{
        	$("#Customersms").val("NO");
        }
		    
	 });
	
	$("#EMAIL").click(function() {
        if (this.checked) {
        	$("#Customeremail").val("YES");
        }else{
        	$("#Customeremail").val("NO");
        }
		    
	 });
	    	  
	    	 
	    	  
	 
});


function decisionfun(v){
	$('#decisions').val(v);
}

$(function(){ // DOM ready

	  // ::: TAGS BOX

	  $("#contcentermailid1").on({
		 
	    focusout : function() {
	      var txt = this.value.replace(/[^a-z0-9\+\-\.\#]/ig,''); // allowed characters
	      
	      if(txt) $('#tags').append("<span>"+(this.value).replace(",","")+",</span>");
	      this.value = "";
	    },
	    keyup : function(ev) {
	      // if: comma|enter (delimit more keyCodes with | pipe)
	      if(/(188|13)/.test(ev.which)) $(this).focusout(); 
	   
	    }
	   
	  });
	  $('#tags').on('click', 'span', function() {
	    if(confirm("Remove "+ $(this).text() +"?")) $(this).remove(); 
	  });

	});
	
</script>

<style>
#tags{
  float:left;
  /* border:1px solid #ccc; */
  padding:5px;
  font-family:Arial;
}
#tags > span{
  cursor:pointer;
  display:block;
  float:left;
  color:#000000;
  font-weight: bold;
  background:#ffffff;
  padding:5px;
  padding-right:25px;
  margin:4px;
}
#tags > span:hover{
  opacity:0.7;
}
#tags > span:after{
 position:absolute;
 content:"X";
 color:white;
    border:1px solid; 
    background:red; 
/*  padding:2px 5px;
 margin-left:3px; */
 font-size:16px;
}
#tags > input{
 
  border:0;
  margin:4px;
  padding:7px;
  width:auto;
}
</style>


<s:set value="responseJSON" var="respData" />
</head>

<body>
<form name="form1" id="form1" method="post" action="">
	<div id="content" class="span10">  
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="#">Fraud Monitor Configuration</a></li>
				</ul>
			</div>
		
	
		 
		  <div class="row-fluid sortable"><!--/span-->
			<div class="row-fluid sortable">
					
					<div id="primaryDetails" class="box-content">
						
							<div class="box span12"> 
					<div class="box-header well" data-original-title>Fraud Monitor Configuration Modify
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
			<div class="box-content"> 				
			<fieldset>
			
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							   class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
							   					<tr class="odd">
														<td width="20%"><label for="Channel"><strong>Fraud Id</strong></label></td>
														<td width="30%">${responseJSON.fraudcode}</td>											
														<td width="20%"><label for="Services"><strong>Fraud Description </strong></label></td>
														<td width="30%"><input name="frauddesc" id="frauddesc"  type="text"  class="field"  value="${responseJSON.frauddesc}" /></td>	
														
													</tr>
													<tr class="odd">
														<td width="20%"><label for="custsms"><strong>Customer</strong></label></td>
														<td width="30%" colspan="3">
														<input type="checkbox" id="SMS" name="SMS" value="YES" checked>SMS</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 							<input type="checkbox" id="EMAIL" name="EMAIL" value="NO" >E-MAIL</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														
														</td>											
														
														
													</tr>
													<tr class="odd">
													<td width="20%"><label for="Services"><strong>Contact Mail Id </strong></label></td>
														<td width="30%" colspan="3">
															<div id="tags">
														<input name="contcentermailid1" id="contcentermailid1"  type="text"  class="field" placeholder="Enter Mail Id"  value="${responseJSON.contcentermailid}"/>
														</div>
														</td>	
													</tr>
													<tr class="odd">
														<td width="20%"><label for="custsms"><strong>Decision</strong></label></td>
														<td width="30%" colspan="3">
														<input type="radio" id="Automatically" name="Decision" value="Automatically" onclick="decisionfun(this.id)" checked>Automatically Disable Customer</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 							<input type="radio" id="Manually" name="Decision" value="Manually" onclick="decisionfun(this.id)" >Manually</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														
														</td>											
														<td width="20%"></td>
														<td width="30%"></td>	
														
													</tr>
													<tr class="odd">
														<td width="20%"><label for="rule"><strong>Rule</strong></label></td>
														<td colspan="3">${responseJSON.ruledesc}</td>	
														
													</tr>
													
												
													
						</table>
							
			</fieldset>
			
			</div> 
			
			</div> </div> 
			<input type="hidden" name="contcentermailid" id="contcentermailid"    value="${responseJSON.contcentermailid}"  />
			<input type="hidden" name="frauddesc" id="frauddesc"   value="${responseJSON.frauddesc}"  />
			<input type="hidden" name="fraudcode" id="fraudcode"    value="${responseJSON.fraudcode}"  />
			
			<input type="hidden"  name="Customersms" id="Customersms"   value="${responseJSON.Customersms}" />
			<input type="hidden"  name="Customeremail" id="Customeremail"   value="${responseJSON.Customeremail}" />
			<input type="hidden"  name="ruledesc" id="ruledesc"  value="${responseJSON.ruledesc}"/>
			<input type="hidden"  name="rulecode" id="rulecode"  value="${responseJSON.rulecode}" />
			<input type="hidden"  name="decisions" id="decisions"  value="${responseJSON.decisions}" />
			<input type="hidden" id="refno" name="refno"  value="${responseJSON.fraudcode}" />
			<span id="ruledetaails1"></span>
			
		<div class="form-actions" id="submitdata" > 
				
				
				
				<input type="button" id="non-printable" class="btn btn-success" onclick="redirectAct();" value="Next" />
				<input type="button" id="non-printable" class="btn btn-success" onclick="subitReq();" value="Confirm" />
		</div>
		
		
		
		</div>
		
		</div>
		
		
		 
		
 
	</div>
</form>	
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 

</body>
</html>
