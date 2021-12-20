<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>IMPERIAL</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%> 
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js"></script>
 
<script type="text/javascript" > 

function next()
{
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/channelmanagement.action';
	$("#form1").submit();
	return true;
} 

$(function() {
	 
	buildbranchtable();
	 
 
 }); 
 

function buildbranchtable()
{

	$("#tbody_data").empty();
	
	var htmlString="";
	var chdata=$("#fruadrules").val();
	var spidat=chdata.split(",");
	
	for(i=0;i<=spidat.length-1;i++){
		
			htmlString = htmlString + "<tr class='values' id="+i+">";
		
			htmlString = htmlString + "<td id='channellmt' name=channellmt >" + spidat[i].split("=")[0] + "</td>";
			htmlString = htmlString + "<td id='cperdaylmt' name=cperdaylmt >" + spidat[i].split("=")[1] + "</td>";
		
			htmlString = htmlString + "</tr>";
			

	
	}
	
	
	$("#tbody_data").append(htmlString);

}




</script>

</head>

<body>
	<form name="form1" id="form1" method="post" action="">
	<div id="content" class="span10">
            			<!-- content starts -->
		<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="channelmanagement.action">Service Mapping</a></li>
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
		 
		 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
			  <div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Product Details  
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>
						</div>
					</div>


					<div class="box-content" id="terminalDetails">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="Product Code"><strong>Product Code</strong></label></td>
									<td width="30%">${responseJSON.productcode}
									<input name="productcode" 
										type="hidden" id="productcode" value="${responseJSON.productcode}"/>
									</td>
									<td width="20%"><label for="Product Description"><strong>Product Description</strong></label></td>
									<td width="30%">${responseJSON.productdesc}
									<input name="productdesc" 
										type="hidden" id="productdesc" value="${responseJSON.productdesc}" />
									</td>
								</tr>
								<tr class="even">
									
									<td><label for="Product Currency"><strong>Product Currency</strong></label></td>
									<td>${responseJSON.productcurr}
									<input name="productcurr" 
										type="hidden" id="productcurr" value="${responseJSON.productcurr}" /></td>
									<td><label for="Application"><strong>Application</strong></label></td>
									<td>${responseJSON.application}
									<input name="application" 
										type="hidden" id="application" value="${responseJSON.application}" /></td>
									
								</tr>
								

								
							</table>
							


						</fieldset>
					</div>
				</div>
				</div>
			 <input name="fruadrules" type="hidden" id="fruadrules" value="${responseJSON.fruadrules}" />
		<div class="row-fluid sortable"><!--/span--> 
			<div class="box span12">  
			
					<div class="box-header well" data-original-title>Fraud Rules Confirm
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>			
			
	<div  class="box-content" >	
	
				<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										<th>Fraud Id</th>
										<th>Fraud Description</th>
										
									</tr>
							  </thead>    
							
							 <tbody id="tbody_data">
							 </tbody>
							 
					</table>
		
		</div> 
		
		<div class="form-actions" id="submitdata" > 
				
				
				
				<input type="button" id="non-printable" class="btn btn-success" onclick="next();" value="Next" />
				
		</div> 
		
		</div>
		
		</div>
		
	</div>
</form>
</body>
</html>
