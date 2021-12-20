<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>

<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">

<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>

<link href="${pageContext.request.contextPath}/css/bootstrap-colorpicker.css" rel="stylesheet">
  <script src="${pageContext.request.contextPath}/js/bootstrap-colorpicker.js"></script>
  <link href="${pageContext.request.contextPath}/css/multi-select.css" rel="stylesheet" type="text/css">
  


<style>

 ul.tree, ul.tree ul {
    list-style: none;
     margin: 0;
     padding: 0;
   } 
   ul.tree ul {
     margin-left: 10px;
   }
   ul.tree li {
     margin: 0;
     padding: 0 7px;
     line-height: 20px;
     color: #369;
     font-weight: bold;
     /* border-left:3px solid rgb(100,100,100); */
     

   }
   ul.tree li:last-child {
       border-left:none;
   }
   ul.tree li:before {
      position:relative;
      top:-0.3em;
      height:2em;
      width:12px;
      color:white;
     /*  border-bottom:3px solid rgb(100,100,100); */
      content:"";
      display:inline-block;
      left:-7px;
   }
   ul.tree li:last-child:before {
       /* border-left:3px solid rgb(100,100,100);  */  
       
   }
   
   .spantag { 
  
	  color: #0066FF; 
	  font-family:arial; 
	  font-size: 16px; 
	  border-bottom:5px solid #000000;
	}

</style>

<script type="text/javascript">


 
 function confirmProduct(){
		
		
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/segmentsack.action'; 
		$("#form1").submit();
		return true; 
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
					<li><a href="agentregistration.action">Segments Creation</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						Segments Creation
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="fname"><strong>Segments</strong></label></td>
									<td width="25%">${responseJSON.segments}
									<input type="hidden" id="segments" name="segments"  value="${responseJSON.segments}" />
									
									</td>
									<td width="25%"><label for="fname"><strong>Sub Segments</strong></label></td>
									<td width="25%">${responseJSON.subsegments}
									<input type="hidden" id="subsegments" name="subsegments"  value="${responseJSON.subsegments}" />
									</td>
								</tr>
								
								
															
							</table>
							
							
							
							
	
						</fieldset>
						 
						<div id="colorSelector"><div style="background-color: #0000ff"></div></div>
						<ul class="tree" ><strong>Segments Settings</strong>
						
						    <li>
						    
						       <ul>
						        <li class="last" id="dcolours" name="dcolours" >
								<div class="box-header well" data-original-title>
									Colours Creation
								</div>
						         <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr>
								<td width="50%">Set colours </td>
								<td width="50%">${responseJSON.colors}
								
								
								  <input type="hidden" id="colors" name="colors"  value="${responseJSON.colors}" />
								 
								</td>
								</tr>
								</table>
								
						        </li>
						       </ul>
						      </li>
						      
						      <li>
						      
						    
						       <ul>
						        <li class="last" id="dservices" name="dservices"  >
						        <div class="box-header well" data-original-title>
									Select services
								</div>
  								<table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
									<tr>
									<td >${responseJSON.services}
									<%-- <div align="center">
										<select id='pre-selected-options' name='pre-selected-options' multiple='multiple' >
									   
									 </select>
									</div> --%>
									<input type="hidden"  id="services" name="services"  valuee="${responseJSON.services}"/>
									</td>
									</tr>
									
								</table>
								</li>
						       </ul>
						      </li>
						      
						      <li>
						      
						     
						       <ul>
						        <li class="last" id="dcampaigns" name="dcampaigns" >
						        <div class="box-header well" data-original-title>
									campaigns
								</div>
						         <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr>
								<td width="50%">campaigns </td>
								<td width="50%">${responseJSON.campaigndis}
								<input type="hidden"  id="campaigndis" name="campaigndis" value="${responseJSON.campaigndis}" /> 
								</td>
								</tr>
								</table>
						        </li>
						         
						       </ul>
						      </li>
						      
						      <li>
						      
						       <ul>
						        <li class="last" id="dlifestyle" name="dlifestyle" >
						        <div class="box-header well" data-original-title>
									life style
								</div>
						        <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
									<tr>
									<td >${responseJSON.lifestyle}
									
									
									<input type="hidden"  id="lifestyle" name="lifestyle"  value="${responseJSON.lifestyle}" />
									 </td>
									</tr>
									
								</table>
						        </li>
						       </ul>
						      </li>
						      
						      <li>
						      
						       <ul>
						        <li class="last" id="dadvertised" name="dadvertised" >
						         <div class="box-header well" data-original-title>
									partners/ benefits advertised
								</div>
						        <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr>
								<td width="50%">Image Preview </td>
								<td width="50%"><img alt="" src="${responseJSON.dvPreviews}"></img>
								
								<input type="hidden"  id="dvPreviews" name="dvPreviews"  value="${responseJSON.dvPreviews}"/>
								 </td>
								</tr>
								</table>
						        </li>
						       </ul>
						      </li>
						      
						       <li>
						      
						       
						       <ul>
						        <li class="last" id="dtonevoice" name="dtonevoice"  >
						        <div class="box-header well" data-original-title>
									Tone of Voice
								</div>
						        <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr>
								<td width="50%">Enter Tone of Voice </td>
								<td width="50%">${responseJSON.tonev}
								<input type="hidden" id="tonev" name="tonev" value="${responseJSON.tonev}"/> </td>
								</tr>
								</table>
								
						        
						        </li>
						       </ul>
						      </li>
						      
						       <li>
						       
						        
						       <ul>
						        <li class="last" id="dfaqs" name="dfaqs"   >
						        <div class="box-header well" data-original-title>
									FAQs
								</div>
						        <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr>
								<td width="50%">Enter FAQs </td>
								<td width="50%">${responseJSON.faqs}
								
								
								<input type="hidden" id="faqs" name="faqs" value="${responseJSON.faqs}"/></td>
								</tr>
								</table>
						        
						        </li>
						       </ul>
						      </li>
						      
						       <li>
						       
						       
						       <ul>
						        <li class="last" id="dcpersons" name="dcpersons"  >
						        <div class="box-header well" data-original-title>
									bank contact persons (RM)
								</div>
						        <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr>
								<td width="50%">Enter bank contact persons </td>
								<td width="50%">${responseJSON.rms}
								<input type="hidden" id="rms" name="rms" value="${responseJSON.rms}"/> </td>
								</tr>
								</table>
						        
						        </li>
						       </ul>
						      </li>
						      
						   </ul>
						
						
					</div>
				</div>
			</div>
			
			
	
		
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Confirm"  onClick="confirmProduct()" ></input> 
			</div>  

			
			
		
			</div>
			

</form>
<script>
  $(function () {
    $('#cp2').colorpicker();
  
  });
</script>
<script src="${pageContext.request.contextPath}/js/jquery.multi-select.js"></script>
</body>
</html>
