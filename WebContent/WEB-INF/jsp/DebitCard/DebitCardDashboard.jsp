<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">

<link href="${pageContext.request.contextPath}/css/link/css1" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/link/screen.css">
<link href="${pageContext.request.contextPath}/css/link/sticky.css" rel="stylesheet" type="text/css">

<script type="text/javascript">





 

	var verror="";		
			


			
function redirectAct(){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
				$("#form1").submit();
				return true;
}

			


function funsubmit(v){
	$("#status").val(v);
	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/debitcardinformation.action";
	$("#form1").submit();
	return true;
}





</script>
<s:set value="responseJSON" var="respData" />

<style>
div.gallery {
    margin: 5px;
    
    float: left;
    width: 50px;
    height: 50px;
    cursor: pointer;
}


div.gallery img {

    width: 50px;
    height: 50px;
}

div.desc {
    padding: 15px;
    text-align: center;
}


.numberCircle {
    border-radius: 100%;
    behavior: url(PIE.htc); /* remove if you don't care about IE8 */

    width: 50px;
    height: 50px;
    padding: 8px;

    background: #fff;
    /* border: 2px solid #666; */
    color: #666;
    text-align: center;

    font: 16px Arial, sans-serif;
}
</style>

</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Debit Card Requests</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
			
			 
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<!-- Customer Negotiated Rate Confirmation -->
						Debit Card Requests Dashboard
						 <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
					
				 
					<div class="box-content" id="secondaryDetails">
						<fieldset>
						
						<table width="100%"  border="0" cellpadding="5" cellspacing="1" align="center" >
						<tr>
						<td width="50%">
						<table border="0" width="100%" ><tr><td colspan="2" >
						<span  style="color: #000000;font-weight: bold;font-size: 10px;">Debit Card Requests Open</span>
						</td></tr>
						<tr><td width="30%" >
						<div class="gallery"  id="Open" onclick=funsubmit(this.id) >
						   <img src="${pageContext.request.contextPath}/images/mail_open.png" alt="Open" width="100" height="100">
						   
						  </div>
						  </td><td width="70%">
						 <div><strong>Total Requests Open</strong><span class="numberCircle" ><strong>${responseJSON.REQUEST_OPEN}</strong></span></div>
						 <br>
						 <div><strong>Today Requests Open</strong><span class="numberCircle" ><strong>${responseJSON.TODAY_REQUEST_OPEN}</strong></span></div>
						 </td> </tr></table>
						 
						</td><td width="50%">
						
						
						<table border="0" width="100%" ><tr><td colspan="2" >
						<span  style="color: #000000;font-weight: bold;font-size: 10px;">Debit Card Requests Closed</span>
						</td></tr>
						<tr><td width="30%" >
						<div class="gallery"  id="Closed" onclick=funsubmit(this.id) >
						 
						   <img src="${pageContext.request.contextPath}/images/mail_closed.png" alt="Closed" width="100" height="100">
						   
						  </div>
						  </td><td width="70%">
						 <div><strong>Total Requests Closed</strong><span class="numberCircle" ><strong>${responseJSON.REQUEST_CLOSED}</strong></span></div>
						  <br>
						 <div><strong>Today Requests Closed</strong><span class="numberCircle" ><strong>${responseJSON.TODAY_REQUEST_CLOSED}</strong></span></div>
						 </td> </tr></table>
						
						
						</td>
						
						
						
						</tr>
					
						</table>
						
						<input type="hidden" name="status" id="status" />
							
							 
							
						</fieldset>
						
					</div>
					
				</div>
			</div>
			
			
			
			<div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Home" />
			</div>  

	
			
			</div>
			

</form>

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
	  	 
		 
		  
		/* $('#plasticCode').val(ses); 
		$('#plasticCode').trigger("liszt:updated");  */
	});
 </script>
</body>
</html>
