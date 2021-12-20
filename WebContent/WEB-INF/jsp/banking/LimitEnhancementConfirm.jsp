<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %> 
 

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
</style>
<script type="text/javascript">

$(function() {  
	buildbranchtable();
	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/enhancementregcustinfodetAct.action'; 
		$("#form1").submit();
		
		 $(this).prop('disabled', true);
			$("#btn-submit").hide();
		return true;
	}); 
});


function buildbranchtable()
{

	$("#tbody_data").empty();
	
	var htmlString="";
	var chdata=$("#plimitchannel").val();
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
<body>
<form name="form1" id="form1" method="post"> 
		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Modify Customer Details</a>  </li> 
 					</ul>
				</div>  

				<table>
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
					<i class="icon-edit"></i>Customer Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>  
				<div class="box-content">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
							<td width="25%"><label for="From Date"><strong>Customer Id</strong></label></td>
							<td width="25%"><s:property value='accBean.customercode' /> 
									<input type="hidden" name="customercode"  id="customercode"   value="<s:property value='accBean.customercode' />"   />
									<input type="hidden" name="institute"  id="institute"   value="<s:property value='institute' />"   /></td>
							<td width="25%"><label for="To Date"><strong>Customer Name</strong></label> </td>
							<td width="25%" ><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<td ><label for="To Date"><strong>Mobile Number</strong></label> </td>
							<td><s:property value='telephone' />
								<input type="hidden" value="<s:property value='accBean.telco' />" style="width: 95px;" readonly name="telco" id="telco" />&nbsp;
								<input type="hidden" value="234" style="width:25px;" readonly name="isocode" id="isocode"/>&nbsp;
								<input type="hidden" value='<s:property value='telephone' />' name="telephone" id="telephone" readonly style="width:95px;" />
 							</td>
							<td  ><label for="To Date"><strong>Date Of Bith</strong></label> </td>
							<td><s:property value='accBean.idnumber' /> <input type="hidden" name="idnumber"  id="idnumber"   value="<s:property value='idnumber' />"   />  </td>
						</tr>
						<tr class="even">
							<td><label for="From Date"><strong>Email ID</strong></label></td>
							<td><s:property value='accBean.email' /> <input type="hidden" name="email"  id="email"   value="<s:property value='email' />"   />  </td>
							<td><label for="To Date"><strong>Preferred Language</strong></label> </td>
							<td><s:property value='langugae' /> <input type="hidden" name="langugae"  id="langugae"   value="<s:property value='langugae' />"   />  </td>
						</tr>
						<tr class="even">
							<td><label for="Product"><strong>Product</strong></label></td>
							<td><s:property value='accBean.product' /> <input type="hidden" name="product"  id="product"   value="<s:property value='product' />"   />  </td>
							<td><label for="Description"><strong>Description</strong></label> </td>
							<td><s:property value='prodesc' />
							<input type="hidden" value='<s:property value='prodesc' />' name="prodesc" id="prodesc" readonly style="width:130px;" /> </td>
						</tr>
						<tr class="even">
									
									<td><label for="ToKenLimit"><strong>Token Limit Amount
										</strong></label></td>
									<td><s:property value='tokenamt' />
									<input type="hidden" id="tokenamt" name="tokenamt" maxlength='25' value="<s:property value='tokenamt' />" class="field" /></td>
									<td><label for="ToKenLimit"><strong>Product Per Day Limit Amount
										</strong></label></td>
									<td><s:property value='pdaylimitamt' />
									<input type="hidden" id="pdaylimitamt" name="pdaylimitamt" maxlength='25' value="<s:property value='pdaylimitamt' />" class="field" /></td>
									
								</tr>
								<tr class="even">
									
									<td><label for="ToKenLimit"><strong>Customer Per Day Limit Amount
										</strong></label></td>
									<td><s:property value='custperdaylimit' />
									<input type="hidden" id="custperdaylimit" name="custperdaylimit" maxlength='25' value="<s:property value='custperdaylimit' />" class="field" /></td>
									<td></td>
									<td></td>
									
								</tr>
								<%-- <tr class="even">
									
									<td><label for="ToKenLimit"><strong>USSD 2FA Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td><s:property value='secfalimit' />
									<input type="hidden" id="secfalimit" name="secfalimit" maxlength='25' value="<s:property value='secfalimit' />" class="field" /></td>
									<td></td>
									<td></td>
									
								</tr> --%>
														
				 </table>
				 <input type="hidden" id="secfalimit" name="secfalimit" maxlength='25' value="" class="field" />
				  <input type="hidden"   name="apptype"  id="apptype"   value="<s:property value='accBean.apptype' />"   />
				  <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details1">
				 <thead>
									<tr>
										
										<th width="25%">Channel</th>
										<th width="25%">Customer Per Day Limit Amount</th>
									
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
				 </table>
				</fieldset> 
				</div>  
				
	  </div>
	  </div> 
	  
	 <input type="hidden"  name="plimitchannel" id="plimitchannel"  value="<s:property value='plimitchannel' />" />
	
		<div >
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">&nbsp;Home </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Confirm</a>					 
		</div> 
	</div> 	 
 </form>
 <script type="text/javascript">
$(function(){
	
	var actlen = $('#tbody_data > tr').length;
	
	if(actlen == 0){
		$('#add-new-act').hide();
	}else {
		$('#add-new-act').show();
	}
	 
});  
</script>
</body> 
</html>