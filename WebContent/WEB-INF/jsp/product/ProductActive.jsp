
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
	 border: 2px solid #000;
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
	  buildbranchtable();
	   var viewBinGroup = '${responseJSON.bingroupcode}';
	   console.log("ViewBinGroup"+viewBinGroup);
	  
 });
 
function viewProduct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/createProductAct.action'; 
	$("#form1").submit();
	return true;
}

function confirmProduct(){
	if($("#custcnt").val()=="0"){
		
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/insertProductActive.action'; 
	$("#form1").submit();
	return true;
	
	}else{		
		$('#errors').text("This Product Using Customers.");
		return false;
	}
}




$('#view1').live('click', function () {
	
	
	var vals = ($("#feeCode").val()).split("-")[0];
	if(vals=="" || vals==" " || vals==null || vals=="Not yet Configure")
		{
		$('#selalrtmsg').text("Please Select Fee Code");
		$('#selalrtmsg1').empty();
		}else{
			$('#selalrtmsg').empty();
			$('#selalrtmsg1').empty();
			
	
			 $.ajax({
				    url : '<%=request.getContextPath()%>/<%=appName %>/getfeecodepopupData.action',
				    async: false,
				    type: "POST",
				    data : "feeCode="+ vals ,
				    success: function(data, textStatus, jqXHR)
				    {
				
				$('#feecodedata').text(data.Feecodedetails.feeCode);
				$('#feedescdata').text(data.Feecodedetails.feeDesc);							
				lightbox_open();
				tablegridfill(data.Feecodedetails2);
				     },
				    error: function (jqXHR, textStatus, errorThrown)
				    {
				    }
				   });
			 
			 
				 
			 }
	
	
});

function lightbox_open()
{
 window.scrollTo(0,0);
 document.getElementById('light').style.display='block';
 document.getElementById('fade').style.display='block';
}

function lightbox_close()
{
 document.getElementById('light').style.display='none';
 document.getElementById('fade').style.display='none';
}

function tablegridfill(data)
{
	var json3 = data;
	$('#searchBody2').empty();
	var totalrows2 =  json3.length;
	
	 var htmlString2 = "";
	 var i2;

	 for(i2=0;i2<totalrows2;i2++)
	 {

	      
	   htmlString2 = htmlString2 + "<tr class='values' id='"+(i2+1)+"'>";   
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].SNO + "</td>";
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].TXNNAME + "</td>";
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].FREQ + "</td>";
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].FLATPER + "</td>";
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].FPVALUE + "</td>";
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].CRT + "</td>";	  			   
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].FRMVAL + "</td>";
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].TOVAL + "</td>";  
	   htmlString2 = htmlString2 + "</tr>";

	   $('#searchBody2').append(htmlString2);

	   htmlString2="";
	  
	 }
	
}

$('#view').live('click', function () {
	
	
	var vals = ($("#limitCode").val()).split("-")[0];
	if(vals=="" || vals==" " || vals==null || vals=="Not yet Configure")
		{
		$('#selalrtmsg1').text("Please Select Limit Code");
		$('#selalrtmsg').empty();
		}else{
			$('#selalrtmsg1').empty();
			$('#selalrtmsg').empty();
			
			
			$.ajax({
			    url : '<%=request.getContextPath()%>/<%=appName %>/getlimitcodepopupData.action',
			    async: false,
			    type: "POST",
			    data : "limitCode="+ vals ,
			    success: function(data, textStatus, jqXHR)
			    {
			
			$('#limitcodedata').text(data.limitcodedetails.limitCode);
			$('#limitdescdata').text(data.limitcodedetails.limitDesc);							
			lightbox_open1();
			tablegridfill1(data.limitcodedetails2);
			     },
			    error: function (jqXHR, textStatus, errorThrown)
			    {
			    }
			   });
		
		}
	
});



function lightbox_open1()
{
 window.scrollTo(0,0);
 document.getElementById('light1').style.display='block';
 document.getElementById('fade1').style.display='block';
}

function lightbox_close1()
{
 document.getElementById('light1').style.display='none';
 document.getElementById('fade1').style.display='none';
}

function tablegridfill1(data)
{
	var json3 = data;
	$('#searchBody22').empty();
	var totalrows2 =  json3.length;
	
	 var htmlString2 = "";
	 var i2;

	 for(i2=0;i2<totalrows2;i2++)
	 {

	      
	   htmlString2 = htmlString2 + "<tr class='values' id='"+(i2+1)+"'>";   
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].SNO + "</td>";
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].TXNNAME + "</td>";
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].FREQ + "</td>";
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].MAX_CNT + "</td>";
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].MIN_AMT + "</td>";
	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].MAX_AMT + "</td>";	
	   htmlString2 = htmlString2 + "</tr>";

	   $('#searchBody22').append(htmlString2);

	   htmlString2="";
	  
	 }
	
}

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
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Product Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

						</div>
					</div>  
						
						<div class="box-content" id="primaryDetails"> 
						 <fieldset>  
								<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="even">
								 <td width="20%"><label for="Product Code"><strong>Product Code </strong></label></td>
								 <td>  <s:property value='#respData.productCode' />  <input type="hidden" id="productCode" name="productCode" value="<s:property value='#respData.productCode' />" /></td>
								 <td width="20%"><label for="Product Description"><strong>Product Description </strong></label></td>
								 <!-- <td width="30%"><input name="productDesc"  type="text" id="productDesc" class="field"> </td> -->
								 <td>  <s:property value='#respData.productDesc' />  <input type="hidden" id="productDesc" name="productDesc" value="<s:property value='#respData.productDesc' />" /></td>
							</tr>
							<tr class="odd">
										<td ><label for="Product Currency"><strong>Product Currency</strong></label></td>
										<td width="30%"> <s:property value='#respData.binCurrency' /> 
										<input type="hidden" id="binCurrency" name="binCurrency" value="<s:property value='#respData.binCurrency' />" /></td>
										<td ><label for="Application"><strong>Application</strong></label></td>
										<td width="30%"> <s:property value='#respData.application' /> 
										<input type="hidden" id="application" name="application" value="<s:property value='#respData.application' />" /></td>
									</tr> 
							
							
							<tr class="even" style="display:none">
									
									<td><label for="ToKenLimit"><strong>Token Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td><s:property value='#respData.tokenval' />
									<input type="hidden" id="tokenlimit" name="tokenlimit" maxlength='25' value="<s:property value='#respData.tokenval' />" class="field" /></td>
									<td><label for="ToKenLimit"><strong>Per Day Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td><s:property value='#respData.perdaylimit' />
									<input type="hidden" id="perdaylimit" name="perdaylimit" maxlength='25' value="<s:property value='#respData.perdaylimit' />" class="field" /></td>
									
								</tr>	
									
								</table>
								<input type="hidden"  id="custcnt" name="custcnt" value="<s:property value='#respData.custcnt' />" />
								<input type="hidden"  id="chdata" name="chdata" value="<s:property value='#respData.channellimit' />" />
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
						</div>
						<div class="row-fluid sortable">
							<div class="box span12">
								<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Product Settings
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i
											class="icon-cog"></i></a> <a href="#"
											class="btn btn-minimize btn-round"><i
											class="icon-chevron-up"></i></a>
									</div>
								</div>
						<div class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td><label for="Limit Code"><strong>Limit Code </strong></label></td>
										<td><s:property value='#respData.limitCodeVal' />&nbsp; &nbsp; &nbsp; &nbsp; 
										<input type="hidden" value="<s:property value='#respData.limitCodeVal' />" id="limitCode" name="limitCode" /> 
												<input type="button" class="btn btn-success" onclick="" id="view" name="view" value="View" /></td>
										<td><label for="Fee Code"><strong>Fee Code </strong></label></td>
										<td><s:property value='#respData.feeCodeVal' />&nbsp;&nbsp; &nbsp; &nbsp;
										<input type="hidden" value="<s:property value='#respData.feeCodeVal' />" id="feeCode" name="feeCode" /> 
											    <input type="button" class="btn btn-success" onclick="" id="view1" name="view1" value="View" /></td>
									</tr>
								</table>
							</fieldset>
						</div>
						
						
						<div id="light" >
						<br></br> <br>
						
					<div align="center">
							<a class="btn btn-danger" href="#" onClick="lightbox_close()">Close</a>
						</div>

						<div class="row-fluid sortable">
							<div class="box span12">
								<div class="box-header well">
									<i class="icon-edit"></i> Fee Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i
											class="icon-cog"></i></a> <a href="#"
											class="btn btn-minimize btn-round"><i
											class="icon-chevron-up"></i></a>
									</div>
								</div>
								<div class="box-content">
									<table width="950" border="0" cellpadding="5" cellspacing="1"
										class="table table-striped table-bordered bootstrap-datatable ">
										<tr class="even">
											<td width="20%"><label for="Fee Code"><strong>Fee
														Code</strong></label></td>
											<td width="30%"><span id="feecodedata"></span></td>
											<td width="20%"><label for="Fee Description"><strong>Fee
														Description</strong></label></td>
											<td width="30%"><span id="feedescdata"></span></td>
										</tr>
									</table>
								</div>
							</div>
						</div>

						<div class="row-fluid sortable">
							<div class="box span12">
								<div class="box-header well">
									<i class="icon-edit"></i> Fee Transaction Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i
											class="icon-cog"></i></a> <a href="#"
											class="btn btn-minimize btn-round"><i
											class="icon-chevron-up"></i></a>
									</div>
								</div>
								<div class="box-content">

									<table width="100%"  class="table table-striped table-bordered bootstrap-datatable datatable"  id="DataTables_Table_0"  >
										<thead id="policyUWHeader">
											<tr   id="maintr2">

												<th>S No</th>
												<th>Transaction</th>
												<th>Frequency</th>
												<th>Fee/Percentile</th>
												<th>Value</th>
												<th>Criteria</th>
												<th>From Value</th>
												<th>To Value</th>
											</tr>
										</thead>

										<tbody id="searchBody2" >

										</tbody>
									</table>

								</div>
							</div>
						</div>

						
					</div>

					<div id="fade"></div>
					<div id="light1">
						<br></br> <br>
						
						<div align="center">
							<a class="btn btn-danger" href="#" onClick="lightbox_close1()">Close</a>
						</div>

						<div class="row-fluid sortable">
							<div class="box span12">
								<div class="box-header well">
									<i class="icon-edit"></i> Limit Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i
											class="icon-cog"></i></a> <a href="#"
											class="btn btn-minimize btn-round"><i
											class="icon-chevron-up"></i></a>
									</div>
								</div>
								<div class="box-content">
									<table width="950" border="0" cellpadding="5" cellspacing="1"
										class="table table-striped table-bordered bootstrap-datatable ">
										<tr class="even">
											<td width="20%"><label for="Limit Code"><strong>Limit
														Code</strong></label></td>
											<td width="30%"><span id="limitcodedata"></span></td>
											<td width="20%"><label for="Limit Description"><strong>Limit
														Description</strong></label></td>
											<td width="30%"><span id="limitdescdata"></span></td>
										</tr>
									</table>
								</div>
							</div>
						</div>

						<div class="row-fluid sortable">
							<div class="box span12">
								<div class="box-header well">
									<i class="icon-edit"></i> Limit Transaction Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i
											class="icon-cog"></i></a> <a href="#"
											class="btn btn-minimize btn-round"><i
											class="icon-chevron-up"></i></a>
									</div>
								</div>
								<div class="box-content">

									<table width="100%"  class="table table-striped table-bordered bootstrap-datatable datatable"  id="DataTables_Table_0"  >
										<thead id="policyUWHeader2">
											<tr   id="maintr22">

												<th>S No</th>
												<th>Transaction </th>
												<th>Frequency</th>
												<th>Maximum Count</th>
												<th>Minimum Amount</th>
												<th>Maximum Amount</th>
											</tr>
										</thead>

										<tbody id="searchBody22" >

										</tbody>
									</table>
								</div>
								<input type="hidden" name="newVal"     id="newVal" /> 
				                <input type="hidden" name="oldVal"     id="oldVal" />
				                <input type="hidden" name="columnVal"  id="columnVal"  value="Plastic Code,Limit Code,Fee Code" />
							</div>
						</div>

						
					</div>
					<div id="fade1"></div>
						
						
				</div>		
						
		<div class="form-actions">
				<a  class="btn btn-info" href="#" onClick="viewProduct()">Next</a>
				<a  class="btn btn-info" href="#" onClick="confirmProduct()">Confirm</a>
				
		</div>

	</div>
		   
</form>
</body>
</html>
