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

<% String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<script type="text/javascript" >

$(function() {
	$('#balancemax1').css("display","none"); 
	$('#limitperday').css("display","none"); 
	
	if($('#application').val()=="Agent"){
		$('#tokenlimit1').css("display","none"); 
		  $('#tokenlimit2').css("display","none"); 
		  $('#tokenlimit3').css("display",""); 
		  $('#tokenlimit4').css("display",""); 
		  $('#limitperday').css("display",""); 
		  $('#balancemax1').css("display","none"); 
	}else{
		$('#tokenlimit1').css("display",""); 
		  $('#tokenlimit2').css("display",""); 
		  $('#tokenlimit3').css("display","none"); 
		  $('#tokenlimit4').css("display","none"); 
		  $('#limitperday').css("display","");
		  $('#balancemax1').css("display","none"); 
	}
	
	
	 $('#balancemax').css("display","none"); 
	  if($('#application').val()=="Wallet"){
		  $('#balancemax').css("display","");  
		  $('#balancemax1').css("display","none");  
	  }
	 
	 var jsoarray = '${responseJSON.FINAL_JSON}';
	console.log("jsoarray >>> ["+jsoarray+"]");
	
	 var finaldata =  jQuery.parseJSON(jsoarray);
	 
	 
	 buildbranchtable(finaldata);
	 

}); 


function buildbranchtable(jsonArray)
{

	$("#tbody_data").empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=Transaction >" + i + "</td>";
			htmlString = htmlString + "<td id='channellmt' name=channellmt >" + jsonObject.channellmt + "</td>";
			htmlString = htmlString + "<td id='cperdaylmt' name=cperdaylmt >" + jsonObject.cperdaylmt + "</td>";
		
			htmlString = htmlString + "</tr>";
			

	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$("#tbody_data").append(htmlString);

}


function productScreenCnf(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
	$("#form1").submit();
	return true;
}
	
function createProductCnf(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/CreateProductAck.action';
		$("#form1").submit();
		return true;
	 
}	

</script>

<s:set value="responseJSON" var="respData"/>

</head>

<body>
	<form name="form1" id="form1" method="post">
		<div id="content" class="span10"> 
		    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li> <a href="#">Product Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Product Creation</a></li>
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
					
			<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12">  
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Product Creation  Confirmation
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>  
					<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								 <td width="20%"><label for="Product Code"><strong>Product Code </strong></label></td>
								 <td width="30%">${productText}<input type="hidden" name="productText" id="productText" value="${productText}" /></td>
								 <td width="20%"><label for="Product Description"><strong>Product Description </strong></label></td>
								 <td width="30%">${productDesc}<input name="productDesc"  type="hidden" id="productDesc"  value="${productDesc}"> </td>
							</tr>
							<tr class="even">
								 <td width="20%"><label for="Product Currency"><strong>Product Currency </strong></label></td>
								 <td width="30%">${binCurrency}<input name="binCurrency"  type="hidden" id="binCurrency" class="field" value="${binCurrency}"> </td>
								<td width="20%"><label for="Application"><strong>Application </strong></label></td>
								 <td width="30%">${application}<input name="application"  type="hidden" id="application" class="field" value="${application}"> </td>
							</tr>
							
						
							<tr class="even" id="limitperday" style="display:none">
									
									<td id="tokenlimit1"><label for="ToKenLimit"><strong>Token Limit Amount</strong></label></td>
									<td id="tokenlimit2">${tokenlimit}<input name="tokenlimit"  type="hidden" id="tokenlimit" class="field" value="${tokenlimit}"></td>
									<td><label for="ToKenLimit"><strong>Per Day Limit Amount</strong></label></td>
									<td>${perdaylimit}<input name="perdaylimit"  type="hidden" id="perdaylimit" class="field" value="${perdaylimit}"></td>
									<td id="tokenlimit3" style="display:none"><strong>Super Agent</strong></td>
									<td id="tokenlimit4" style="display:none">${agent}<input name="agent"  type="hidden" id="agent" class="field" value="${agent}"></td>
									
								</tr>
								
								<tr class="even" id="balancemax" style="display:none">
									
									<td id="tokenlimit1"><label for="ToKenLimit"><strong>Maximum Balance Amount</strong></label></td>
									<td id="tokenlimit2">${ussdinilmt}<input name="ussdinilmt"  type="hidden" id="ussdinilmt" class="field" value="${ussdinilmt}"></td>
									<%-- <td><label for="ToKenLimit"><strong>USSD 2FA Limit Amount</strong></label></td>
									<td>${ussdsecfalmt}<input name="ussdsecfalmt"  type="hidden" id="ussdsecfalmt" class="field" value="${ussdsecfalmt}"></td> --%>
									 <td></td>
									 <td></td>
									
								</tr>
								
								<tr class="even" id="balancemax1" >
									
									<td ><label for="ToKenLimit"><strong>USSD Initial Limit
										</strong></label></td>
									<td >${ussdinitallmt}
									<input type="hidden" id="ussdinitallmt" name="ussdinitallmt" maxlength='25' value="${ussdinitallmt}" class="field" /></td>
									<td><label for="ToKenLimit"><strong>USSD 2FA Limit Amount
										</strong></label></td>
									<td>${ussdsecfalmt}
									<input type="hidden" id="ussdsecfalmt" name="ussdsecfalmt" maxlength='25' value="${ussdsecfalmt}" class="field" /></td>
									 
									 
								</tr>
								
										<tr class="even">
																
									 <td><label for="Feename"><strong>Fee Charge to 
										</strong></label></td>
									 <td>${feename}
									 <input name="feename"  type="hidden" id="feename" class="field" value="${feename}">
									 </td>
									 
								<td> </td>
								<td></td>
								</tr>
								
							
						</table>
						<br>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										<th width="5%">Sno</th>
										<th width="25%">Channel</th>
										<th width="25%">Per Day Limit Amount</th>
									
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							 
					</table>
						 <input name="pinEntry" type="hidden" class="field" id="pinEntry"  value="NO"  />
						 <input name="binTypeData"  type="hidden" id="binTypeData" >	
						 
						 
						</fieldset>
						</div> 
					</div> 
				</div>
				
				<input type="hidden" id="finaljsonarray" name="finaljsonarray" value='${responseJSON.FINAL_JSON}'>
				
			<div class="form-actions">
				<a  class="btn btn-danger" href="#" onClick=" productScreenCnf()">Home</a> &nbsp;&nbsp;
				<a  class="btn btn-success" href="#" onClick=" createProductCnf()">Confirm</a>
			</div>	 
	</div>
 </form>
</body>
</html>
