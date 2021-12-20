
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 
     
<style type="text/css">
input.button_add {
    background-image: url(<%=ctxstr%>/images/Left.png); /* 16px x 16px */
    background-color: transparent; /* make the button transparent */
    background-repeat: no-repeat;  /* make the background image appear only once */
    background-position: 0px 0px;  /* equivalent to 'top left' */
    border: none;           /* assuming we don't want any borders */
    cursor: pointer;        /* make the cursor like hovering over an <a> element */
    height: 30px;            /*make this the size of your image */
    padding-left: 30px;      /*make text start to the right of the image */
    vertical-align: middle; /* align the text vertically centered */
}


input.button_add2 {
    background-image: url(<%=ctxstr%>/images/Right.png); /* 16px x 16px */
    background-color: transparent; /* make the button transparent */
    background-repeat: no-repeat;  /* make the background image appear only once */
    background-position: 0px 0px;  /* equivalent to 'top left' */
    border: none;           /* assuming we don't want any borders */
    cursor: pointer;        /* make the cursor like hovering over an <a> element */
    height: 30px;           /* make this the size of your image */
    padding-left: 30px;     /* make text start to the right of the image */
    vertical-align: middle; /* align the text vertically centered */
}
</style>

<script type="text/javascript" >
$(document).ready(function() {

	var serviceList ='${responseJSON.SERVICE_LIST}'; 
	var json = jQuery.parseJSON(serviceList);
	 
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";
	
	$.each(json, function(index, v) {
	
		if(val % 2 == 0 ) {
			addclass = "even";
			val++;
		} else {
			addclass = "odd";
			val++;
		}  
		
		colindex = ++ colindex; 
		rowindex = ++rowindex; 
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+
			"<td>"+colindex+"</td>"+
			"<td>"+v.service+" </td>"+	
			"</tr>";
			
		$("#tbody_data").append(appendTxt);	
		
	}); 
});
 

function assignServiceToTerminal(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateMerchantAct.action';
	$("#form1").submit();
	return true;
}


</script>

	 
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	 
			<div id="content" class="span10">  
			 
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
				  <li><a href="#"> View Services </a></li>
				</ul>
			</div>
			
			<div class="row-fluid sortable">
				<div class="box span12">  
						<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Terminal Information
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
						</div>	
					<div id="terminalDetails" class="box-content">
						<fieldset>	
							<table width="950" border="0" cellpadding="5" cellspacing="1"  
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label  for="Merchant ID"><strong>Merchant ID</strong></label></td>
									<td width="30%"> ${merchantID}
										<input name="merchantID" type="hidden" id="merchantID" class="field" value=" ${merchantID}">
									</td>
									<td width="20%"><label  for="Store ID"><strong>Store ID</strong></label></td>
									<td width="30%"> ${storeId}
										<input name="storeId"  type="hidden" id="storeId" class="field"  value=" ${storeId}" readonly  > 
									</td>
								</tr>
								<tr class="odd">
									<td><label  for="Terminal ID"><strong>Terminal ID</strong></label></td>
									<td colspan=3> ${terminalID}
										<input name="terminalID" type="hidden"  id="terminalID" class="field" value="${terminalID}"  maxlength="8">
									</td> 
								</tr> 							
							</table>
						</fieldset>
					</div>
				<div id="bankAccountInformation" class="box-content">
					<fieldset>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
								id="bankAcctData" >
							<thead>
									<tr >
										<th>Sno</th>
 										<th>Sub Service Code - Description</th>
									</tr>
							</thead>    
							<tbody  id="tbody_data">
							</tbody>
						</table>
					</fieldset>
				</div> 
			</div>
				<input type="hidden" name="selectedServices" id="selectedServices" value='${selectedServices}' ></input>
		</div>
		
			<div class="form-actions">
					<a  class="btn btn-primary " href="#" onClick="assignServiceToTerminal()">Next</a>
			</div>	
		</div>
			 
</form>
</body>
</html>
