
<!DOCTYPE html>
<html lang="en">
<%@taglib uri="/struts-tags" prefix="s"%> 
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
 
<script type="text/javascript" >

$(document).ready(function() { 
	
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
		  $('#balancemax1').css("display",""); 
	}
	
	
	 $('#balancemax').css("display","none"); 
	  if($('#application').val()=="Wallet"){
		  $('#balancemax').css("display","");  
		  $('#balancemax1').css("display","none");  
	  }
	  
	
});
 

function modifyGrpBck(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/ProductModifyAct.action';
	$("#form1").submit();
	return true;
}

function modifyProductCnf(){
	 
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/insertProductModify.action';
	$("#form1").submit();
	return true;
}

  $(function() {
	  buildbranchtable();
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
	
});
  
  function buildbranchtable()
  {

  	$("#tbody_data").empty();
  	
  	var htmlString="";
  	var chdata=$("#chdata").val();
  	var spidat=chdata.split("\|");
  	
  	for(i=0;i<spidat.length-1;i++){
  		
  			htmlString = htmlString + "<tr class='values' id="+i+">";
  		
  			htmlString = htmlString + "<td id='channellmt' name=channellmt >" + spidat[i].split("#")[0] + "</td>";
  			htmlString = htmlString + "<td id='cperdaylmt' name=cperdaylmt >" + spidat[i].split("#")[1] + "</td>";
  		
  			htmlString = htmlString + "</tr>";
  			

  	
  	}
  	
  	
  	$("#tbody_data").append(htmlString);

  }
  
</script>

<%-- <s:set value="responseJSON.BIN_INFO" var="respData" /> --%>

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
.errors {
	color: red;
}
</style > 
	 
</head>

<body>
	<form name="form1" id="form1" method="post" action=""> 
		<div id="content" class="span10">  
			 
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Product Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li> <a href="#">Product Modify </a></li>
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
									<i class="icon-edit"></i>Product Modify Confirmation
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
								</div>
							</div> 
							
					<div id="terminalDetails" class="box-content">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"  
											class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="odd">
									<td width="25%"><label for="Product Code"><strong>Product Code</strong></label></td>
									<td width="25%">  ${productCode} <input type="hidden" id="productCode" name="productCode" value="${productCode}"/></td>
									<td width="25%"><label for="Product Description"><strong>Product Description </strong></label></td>
									<td width="25%">  ${productDesc} <input type="hidden" id="productDesc" name="productDesc" value="${productDesc}"/></td>
								</tr>
								<tr class="odd">
									<td width="25%"><label for="Product Currency"><strong>Product Currency</strong></label></td>
								    <td width="25%">  ${binCurrency} <input type="hidden" id="binCurrency" name="binCurrency" value="${binCurrency}"/></td>
									<td width="25%"><label for="Application"><strong>Application</strong></label> </td>
									<td width="25%"> ${application} <input type="hidden" id="application" name="application" value="${application}"/></td>
								</tr>
								
							
								<tr class="even" id="limitperday" style="display:none">
									
									<td id="tokenlimit1" style="display:none"><label for="ToKenLimit"><strong>Token Limit Amount
										</strong></label></td>
									<td id="tokenlimit2" style="display:none" >${tokenlimit}
									<input type="hidden" id="tokenlimit" name="tokenlimit" maxlength='25' value="${tokenlimit}" class="field" /></td>
									<td><label for="ToKenLimit"><strong>Per Day Limit Amount
										</strong></label></td>
									<td>${perdaylimit}
									<input type="hidden" id="perdaylimit" name="perdaylimit" maxlength='25' value="${perdaylimit}" class="field" /></td>
									
									<td id="tokenlimit3" style="display:none"><strong>Super Agent</strong></td>
									<td id="tokenlimit4" style="display:none">${agent}
									<input name="agent"  type="hidden" id="agent" class="field" value="${agent}"></td>
									
							
	
								</tr>
								
								<tr class="even" id="balancemax" style="display:none">
									
									<td id="tokenlimit1"><label for="ToKenLimit"><strong>Maximum Balance Amount</strong></label></td>
									<td id="tokenlimit2">${ussdinilmt}<input name="ussdinilmt"  type="hidden" id="ussdinilmt" class="field" value="${ussdinilmt}"></td>
									
									 <td></td>
									 <td></td>
									
								</tr>
								
								<tr class="even" id="balancemax1" >
									
									<td id="tokenlimit1"><label for="ToKenLimit"><strong>USSD intial Limit Amount</strong></label></td>
									<td id="tokenlimit2">${ussdinitallmt}<input name="ussdinitallmt"  type="hidden" id="ussdinitallmt" class="field" value="${ussdinitallmt}"></td>
									<td><label for="ToKenLimit"><strong>USSD 2FA Limit Amount</strong></label></td>
									<td>${ussdsecfalmt}<input name="ussdsecfalmt"  type="hidden" id="ussdsecfalmt" class="field" value="${ussdsecfalmt}"></td>
									
									
								</tr>	
								
										<tr class="even" >
										<td><label for="feename"><strong>Fee Charge to
										</strong></label></td>
									<td>${feename}
									
								
									<input type="hidden" id="feename" name="feename" maxlength='25' value="${feename}" class="field" /></td>
										
									<td></td>
									<td></td>	
										</tr>
										
								
							 </table>
							 <input type="hidden"  id="chdata" name="chdata" value="${chdata}" />
								<br>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										
										<th width="25%">Channel</th>
										<th width="25%">Per Day Limit Amount</th>
									
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							 
					</table>
									 
						</fieldset>	 
					</div>
				</div>
				
				 <input type="hidden" id="newVal" name="newVal" value="${newVal}"/> 
				 <input name="oldVal"  type="hidden"  id="oldVal" value="${oldVal}" />
				 <input name="columnVal"  type="hidden"  id="columnVal" value="Plastic Code" />
			</div>
			 
		
		
		<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12">  
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Product Settings 
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>  
						
					
							
					<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								 <td><label for="Limit Code"><strong>Limit Code </strong></label></td>
								 <td>${limitCode} <input type="hidden" class="field" name="limitCode" id="limitCode" value="${limitCode}" ></td>
								 <td><label for="Fee Code"><strong>Fee Code</strong></label></td>
								 <td>${feeCode} <input name="feeCode"  type="hidden" class="field" id="feeCode"  value="${feeCode}" > </td>
							</tr>
						 
						</table>
						  
						</fieldset>
						</div> 
					</div> 
				</div>
				
			<div class="form-actions">
			<a  class="btn btn-danger" href="#" onClick= "modifyGrpBck()">Back</a> &nbsp;&nbsp;
			<a  class="btn btn-success" href="#" onClick="modifyProductCnf()">Confirm</a>
		</div>	
	</div>  
</form> 
<script src="${pageContext.request.contextPath}/js/jquery.chosen.min.js"></script>
 
</body>
</html>
