
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  

<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>


<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js" type="text/javascript"></script>

<script type="text/javascript" >

$(document).ready(function(){

	var finaljson =  '${finaljson}';
	var finaljsonobj = jQuery.parseJSON(finaljson);
	
	buildtable(finaljsonobj);
	
	
});



function buildtable(jsonArray)
{

	$("#tbody_data").empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=Transaction >" + i + "</td>";
			htmlString = htmlString + "<td id='channel' name=channel  >" + jsonObject.channel + "</td>";
			htmlString = htmlString + "<td id='operators' name=operators >" + jsonObject.operators + "</td>";
			htmlString = htmlString + "<td id='Transaction' name=Transaction >" + jsonObject.Transaction + "</td>";
			htmlString = htmlString + "<td id='Frequency' name=Frequency >" + jsonObject.Frequency + "</td>";	
			htmlString = htmlString + "<td id=MaxCount name=MaxCount >" + jsonObject.MaxCount + "</td>";
			htmlString = htmlString + "<td id=MinAmount name=MinAmount >" + jsonObject.MinAmount + "</td>";
			htmlString = htmlString + "<td id=MaxAmount name=MaxAmount >" + jsonObject.MaxAmount + "</td>";
			//htmlString = htmlString + "<td id='' ><a class='btn btn-min btn-danger' href='#' id='DELETE' index="+i+" val="+i+" title='Delete' data-rel='tooltip' onclick='deletefun("+i+")'> <i class='icon-trash icon-white'></i></a></td>";
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$("#tbody_data").append(htmlString);

}

$(function() {	
 

		
		$('#btn-submit').live('click', function () {
		
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/productlimitsettings.action';
			$("#form1").submit();
			return true;
			
		});
		
 	
	});

  
</script>

<s:set value="responseJSON" var="respData"/> 
</head>

<body>	
	<form name="form1" id="form1" method="post">
		<div id="content" class="span10"> 
		    <div>
					<ul class="breadcrumb">
					  <li><a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li><a href="binManagementAct.action">Limit Settings</a> <span class="divider"> &gt;&gt; </span></li>
					  <!-- <li><a href="#">Setting</a></li> -->
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
									<td width="30%">${productcode}
									<input name="productcode" 
										type="hidden" id="productcode" value="${productcode}"/>
									</td>
									<td width="20%"><label for="Product Description"><strong>Product Description</strong></label></td>
									<td width="30%">${productdesc}
									<input name="productdesc" 
										type="hidden" id="productdesc" value="${productdesc}" />
									</td>
								</tr>
								<tr class="even">
									
									<td><label for="Product Currency"><strong>Product Currency</strong></label></td>
									<td>${productcurr}
									<input name="productcurr" 
										type="hidden" id="productcurr" value="${productcurr}" /></td>
									<td><label for="Application"><strong>Application</strong></label></td>
									<td>${application}
									<input name="application" 
										type="hidden" id="application" value="${application}" /></td>
									
								</tr>
								

								
							</table>
							


						</fieldset>
					</div>
				</div>
				</div>			
			<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12">  
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Limit Settings Details
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>   
						
					<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="Limit Code"><strong>Limit Code<font color="red">*</font></strong></label></td>
								<td width="30%">${limitCode}
								</td>
								 <td width="20%"><label for="Limit Description"><strong>Limit Description<font color="red">*</font></strong></label></td>
								 <td width="30%">
								 ${limitDescription}
								 
								 </td>
							</tr>
						</table>
						
					</fieldset>
					 
					
					<span id="multi-row" name="multi-row" style="display:none"> </span> 
					<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="documentTable" style="width: 100%;">
							  <thead>
									<tr>
										<th>Sno</th>
										<th  >Channel</th>
										<th >Operator</th>
										<th>Transaction</th>
										<th>Frequency</th>
										<th>Max Count</th>
										<th>Min Amount</th>
										<th>Max Amount</th>
										<!-- <th>Action</th>  -->
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							 
						</table>
						<div><strong>successfully Created, proceed authorization</strong></div>
					</div>
			</div> 		
				</div>
				
				
<div class="box-content" id="biller-details">				
					<div class="row-fluid sortable"><!--/span--> 
						
				<div class="form-actions" id="finalbutton" >
				
				<a  class="btn btn-success" href="#" id="btn-submit" >Next</a>
				
				</div>	

		</div>	
	</div>
	</div>
	
	
 </form>
 
 
 
</body>
</html>
