
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">


<script type="text/javascript" src="${pageContext.request.contextPath}/js/image/jquery.canvasCrop.js" ></script>

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String ctxstr = request.getContextPath();
%>
<%
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>

<style type="text/css">
.errors {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}

input#abbreviation {
	text-transform: uppercase
}
;
</style>





<SCRIPT type="text/javascript"> 


$(document).ready(function(){   
	

	$('#btn-submit').live('click',function() { 
		
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName%>/lifestylemenagenet.action";
			$("#form1").submit();	
		
	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName%>/home.action";
		$("#form1").submit();
	});

});



	//For Closing Select Box Error Message_End
</SCRIPT>

<s:set value="responseJSON" var="respData"/> 
</head>
<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
		<div id="content" class="span10">

			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider">&gt;&gt; </span></li>
					<li><a href="#"> Life Style Management</a></li>
				</ul>
			</div>

			<table height="3">
				<tr>
					<td colspan="3">
						<div class="messages" id="messages">
							<s:actionmessage />
						</div>
						<div class="errors" id="errors">
							<s:actionerror />
						</div>
					</td>
				</tr>
			</table>

			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Product Offer Acknowledgment
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>

						</div>
					</div>

					<div class="box-content">
						<fieldset>

							<table width="98%" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr>
									<td width="20%"><label for="merchantname"><strong>Merchant Name</strong></label></td>
									<td width="30%">${responseJSON.merchantname}
									<input type="hidden" name="merchantname" id="merchantname" value="${responseJSON.merchantname}" /></td>
									<td width="20%"><label for="merchantid"><strong>Merchant Id</strong></label></td>
									<td width="30%">${responseJSON.merchantid}
									<input type="hidden" name="merchantid" id="merchantid" value="${responseJSON.merchantid}" /></td>
								</tr>
								<tr>
									<td><label for="productctg"><strong>Product Category</strong></label></td>
									<td >${responseJSON.productctg}
									<input type="hidden" name="productctg" id="productctg" value="${responseJSON.productctg}" />
									</td>
		  							<td><label for="productsubctg"><strong>Product Sub-Category</strong></label></td>
									<td>${responseJSON.productsubctg}
									<input type="hidden" name="productsubctg" id="productsubctg" value="${responseJSON.productsubctg}" />
									</td>
								</tr>
								<tr>
									<td width="20%"><label for="productid"><strong>Product Id</strong></label></td>
									<td width="30%">${responseJSON.productid}
									<input type="hidden" name="productid" id="productid" value="${responseJSON.productid}" />
									</td>
									<td width="20%"><label for="productprice"><strong>Product Price/Discount Codes</strong></label></td>
									<td width="30%">${responseJSON.productprice}
									<input type="hidden" name="productprice" id="productprice" value="${responseJSON.productprice}" /></td>
								</tr>
								
								<tr>
									<td width="20%"><label for="offertype"><strong>Type<font	color="red">*</font></strong></label></td>
									<td width="30%">${responseJSON.offertype}
									<input type="hidden" name="offertype"	id="offertype" value="${responseJSON.offertype}" /></td>
									<td width="20%"><label for="timelimit"><strong>is there a time limit ?<font color="red">*</font></strong></label></td>
									<td width="30%">${responseJSON.timelimit}
									<input type="hidden" name="timelimit" id="timelimit" value="${responseJSON.timelimit}" /></td>
								</tr>
								<tr id="limityes" style="display:none">
									<td width="20%"><label for="fromdate"><strong>From Date<font	color="red">*</font></strong></label></td>
									<td width="30%">${responseJSON.fromdate}
									<input type="hidden" class="fromdate"  name="fromdate" id="fromdate" value="${responseJSON.fromdate}"/></td>
									<td width="20%"><label for="enddate"><strong>End Date<font color="red">*</font></strong></label></td>
									<td width="30%">${responseJSON.enddate}
									<input type="hidden" class="enddate" name="enddate" id="enddate" value="${responseJSON.enddate}"/></td>
								</tr>
								
								<tr>
									<td width="20%"><label for="offertype"><strong>Select <font	color="red">*</font></strong></label></td>
									<td width="30%">${responseJSON.distype}
									<input type="hidden" name="distype"	id="distype" value="${responseJSON.distype}" /></td>
									<td width="20%" id="d1" style="display:none"><label for="timelimit"><strong><span >${responseJSON.distype}</span></strong></label></td>
									<td width="30%" id="d2" style="display:none">${responseJSON.doffer}
									<input type="hidden" name="doffer" id="doffer" value="${responseJSON.doffer}" /></td>
									<td width="20%" id="d3" ></td>
									<td width="30%" id="d4" ></td>
									
								</tr>
								
							
								
								
							</table>
							
						</fieldset>
					</div>
				</div>
			</div>

		
			<div class="form-actions">
				<input type="button" class="btn btn-primary" name="btn-submit"
					id="btn-submit" value="Next" width="100"></input> &nbsp;<input
					type="button" class="btn " name="btn-Cancel" id="btn-Cancel"
					value="Home" width="100"></input>
			</div>

		</div>
	</form>
	
</body>
</html>
