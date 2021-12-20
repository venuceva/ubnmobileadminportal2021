
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

	
	var feefinaljson =  '${feefinaljson}';
	
	// var finaljsonobj = jQuery.parseJSON(finaljson);
	
	var feefinaljsonobj = jQuery.parseJSON(feefinaljson);
	
	// buildtable(finaljsonobj);
	
	buildfeetable(feefinaljsonobj);
	
	// console.log("finaljson ["+finaljson+"] feefinaljson ["+feefinaljson+"]");
	
});





function buildfeetable(jsonArray)
{

	$("#Fee_tbody_data").empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=Transaction >" + i + "</td>";
			htmlString = htmlString + "<td id='channel' name=channel  >" + jsonObject.channel + "</td>";
			htmlString = htmlString + "<td id='operators' name=operators >" + jsonObject.operators + "</td>";
			htmlString = htmlString + "<td id='FeeTransaction' name=FeeTransaction >" + jsonObject.FeeTransaction + "</td>";
			htmlString = htmlString + "<td id='FeeFrequency' name=FeeFrequency >" + jsonObject.FeeFrequency + "</td>";	
			htmlString = htmlString + "<td id=FlatPercentile name=FlatPercentile >" + jsonObject.FlatPercentile + "</td>";
			htmlString = htmlString + "<td id=FPValue name=FPValue >" + jsonObject.FPValue + "</td>";
			htmlString = htmlString + "<td id=Criteria name=Criteria >" + jsonObject.Criteria + "</td>";
			htmlString = htmlString + "<td id=FromValue name=FromValue >" + jsonObject.FromValue + "</td>";
			htmlString = htmlString + "<td id=ToValue name=ToValue >" + jsonObject.ToValue + "</td>";
			htmlString = htmlString + "<td id=Agent name=Agent >" + jsonObject.Agent + "</td>";
			htmlString = htmlString + "<td id=subAgent name=subAgent >" + jsonObject.subAgent + "</td>";
			htmlString = htmlString + "<td id=Ceva name=Ceva >" + jsonObject.Ceva + "</td>";
			htmlString = htmlString + "<td id=Bank name=Bank >" + jsonObject.Bank + "</td>";
			htmlString = htmlString + "<td id=SuperAgent name=SuperAgent >" + jsonObject.SuperAgent + "</td>";
			htmlString = htmlString + "<td id=VAT name=VAT >" + jsonObject.VAT + "</td>";
			htmlString = htmlString + "<td id=thirdparty name=thirdparty >" + jsonObject.thirdparty + "</td>";
			//htmlString = htmlString + "<td id='' ><a class='btn btn-min btn-danger' href='#' id='DELETE' index="+i+" val="+i+" title='Delete' data-rel='tooltip' onclick='deletefun("+i+")'> <i class='icon-trash icon-white'></i></a></td>";
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$("#Fee_tbody_data").append(htmlString);

}



	
$(function() {	
 

		
		$('#btn-submit').live('click', function () {
		
		
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/feeconfAck.action';
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
					  <li><a href="binManagementAct.action">Fee Settings</a> <span class="divider"> &gt;&gt; </span></li>
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
				
				<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12">
				
				  
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Fee Settings Details
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>   
						
					<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="Fee Code"><strong>Fee Code<font color="red">*</font></strong></label></td>
								<td width="30%">
								${feeCode}
								<input name="feeCode" type="hidden" maxlength ='6' id="feeCode" required="true" class="field"  value='${feeCode}' />
								</td>
								 <td width="20%"><label for="Fee Description"><strong>Fee Description<font color="red">*</font></strong></label></td>
								 <td width="30%">
								 ${feeDescription}
								 <input name="feeDescription"  type="hidden" maxlength ='25' id="feeDescription" required="true"  class="field"  value='${feeDescription}'/>
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
										<th>F/P</th>
										<th>F/P Value</th>
										<th>Criteria</th>
										<th>From Value</th>
										<th>To Value</th>
										<th>Agent</th>
										<th>Sub Agent</th>
										<th>Ceva</th>
										<th>Bank</th>
										<th >Super Agent</th>
										<th>VAT</th>
										<th>Third Party</th>
										<!-- <th>Action</th>  -->
									</tr>
							  </thead>    
							 <tbody id="Fee_tbody_data">
							 </tbody>
							 
						</table>
						
					</div> 
			</div> 		
		</div>
				
				
<div class="box-content" id="biller-details">				
					<div class="row-fluid sortable"><!--/span--> 
						
				<div class="form-actions" id="finalbutton" >
				
				<a  class="btn btn-success" href="#" id="btn-submit" >Confirm</a>
				&nbsp;&nbsp; <a  class="btn btn-danger" href="#"  id="btn-cancel" >Cancel</a>
				
				</div>	

		</div>	
	</div>
	</div>
	
	<input name="feefinaljson" id="feefinaljson"  type="hidden" value='${feefinaljson}'  />
	
 </form>
 
 
 
</body>
</html>
