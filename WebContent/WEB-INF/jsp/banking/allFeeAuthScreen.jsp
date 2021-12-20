<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
 <%@taglib uri="/struts-tags" prefix="s"%>
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

.messages {
	font-weight: bold;
	color: green;
	padding: 2px 8px;
	margin-top: 2px;
}

.errors {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}
</style>
<script type="text/javascript">

$(function() {

$("#btn-submit").click(function(event) {
		event.preventDefault();
		var searchIDs = [] ;
		var data = "";
		var mulData = "";
		var innerMulData = "";

		$("input[type=radio][checked]").each( function() {
			   console.log("Name ==> "+ $(this) .attr('name') );
			   console.log("Id ==> "+ $(this) .attr('id') );
			   console.log("value ==> "+ $(this) .attr('value') );
			}
		);

 		 var radio_groups = {}
			$(":radio").each(function(){
				radio_groups[this.name] = true;
			});

			for(group in radio_groups) {
				 if (!!$(":radio[name="+group+"]:checked").length) {
						$(":radio[name="+group+"]").css('box-shadow', '0px 0px 0px #FF0000');
						searchIDs.push($(":radio[name="+group+"]:checked").val());
					}
					else{
						hasError = true;
						$(":radio[name="+group+"]").css('box-shadow', '0px 0px 4px #FF0000');
				 }
			 }
		console.log(searchIDs);
		if(searchIDs.length == 0) {
			$("#error_dlno").text("Please check atleast one record.");
		} else {
			$("#service").val(searchIDs[0]);

			$("#error_dlno").text("");
			var url="${pageContext.request.contextPath}/<%=appName %>/feeAuthorization.action";
			$("#form1")[0].action=url;
			$("#form1").submit();

		}
	});


});
</script>
</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="serviceMgmtAct.action">Fee Management</a> <span class="divider"> &gt;&gt; </span> </li>
					<li><a href="#">Fee Authorization</a></li>
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
					<div class="box-header well" data-original-title> Select Type Of Authorization
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a> <a href="#"
								class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable " >
								<tr class="even">
									<td  colspan="4"><input type="radio" name="srv" id="service-rad" value="serv"/> &nbsp; New Service Authorization </td>
								</tr>
								<tr>
									<td  colspan="4"><input type="radio" name="srv" id="sub-service-rad" value="subserv"/> &nbsp; New Sub Service Authorization </td>
								<tr>
										<td colspan="4"><input type="radio" name="srv" id="proccode-reg-rad" value="proc"/> &nbsp; New Processing Code Authorization </td>
								</tr>
								<tr>
									<td  colspan="4"><input type="radio" name="srv" id="fee-rad" value="fee"/> &nbsp; New Fee Code Authorization</td>

									<input type="hidden" name="service" id="service" value=""/>
								</tr>
								<tr>
									<td  colspan="4"><input type="radio" name="srv" id="Bin-rad" value="BinAuth"/> &nbsp; Bin  Authorization</td>


								</tr>
							</table>
						</fieldset>
					</div>

				</div>
			</div>
			<div class="form-actions">
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Check Records"  />
				<input type="button" class="btn btn-danger" name="btn-back" id="btn-back" value="Back"  />

				<span id ="error_dlno" class="errors"></span>


			</div>
			<!--/#content.span10-->
	</div>
</form>
</body>
</html>
