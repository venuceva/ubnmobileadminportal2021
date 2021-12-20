
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%> 		
	<style type="text/css">
#fade,#fade1 {
	display: none;
	position: absolute;
	top: 0%;
	left: 15%;
	width: 70%;
	height: 100%;
	background-color: black;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity = 80);
}

#light,#light1 {
	display: none;
	position: absolute;
	top: 0%;
	left: 15%;
	width: 70%;
	height: 100%;
	/* margin-left: -150px;
	margin-top: -100px; */
	/* border: 2px solid #FFF;*/
	background: #FFF; 
	z-index: 1002;
	overflow: visible;
}
</style> 
<script type="text/javascript" > 
  
 $(function(){
	 
	    /*  var plasticCode = '${responseJSON}'; */
	    /* var binDataList=jQuery.parseJSON('${responseJSON.BINTYPE_LIST}'); */
	    //console.log("ViewDetails"+plasticData.get[0]);
	  
	   var viewBinGroup = '${responseJSON.bingroupcode}';
	   console.log("ViewBinGroup"+viewBinGroup);
	  
 });
 
function viewProduct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/ApprovalAll.action'; 
	$("#form1").submit();
	return true;
}



</script>
	 
</head>
    <s:set value="responseJSON" var="respData"/>    
 
<body>
	<form name="form1" id="form1" method="post" action="">
		
			<div id="content" class="span10"> 
		 
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="createProductAct.action">Product Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">View Product Details</a></li>
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
				<div id="primaryDetails" class="box-content">
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="odd">
								<td ><strong><s:property value='#respData.decision' /></strong></td>
							</tr> 
						</table>
					</div> 
						
					
					<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="viewProduct();" value="Next" />
			</div> 	
				</div>		
						
		 

	</div>
		   
</form>
</body>
</html>
