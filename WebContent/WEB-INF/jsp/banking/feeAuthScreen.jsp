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
var parentId = "merchant-auth-data > tr";
var subParent = "";
var checkedCheckbox = false;

$(function() {
	var status = "${responseJSON.status}";
	var header = "${responseJSON.headerData}";


	if(status == 'serv') {
		$('.merchant').text('Service');
 	} else if(status == 'subserv') {
		$('.merchant').text('Sub Service');
 	} else if(status == 'proc') {
		$('.merchant').text('Processing Code');
 	} else if(status == 'BinAuth') {
		$('.merchant').text('Bin Authrisation');
 	}
 	else {
		$('.merchant').text('Fee ');
 	}

	var storeData = '${responseJSON.agentMultiData}';
 	var json = jQuery.parseJSON(storeData);
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";
	var appendTxt = "";

	if(json.length == 0) {
		$('#primaryDetails').show();
		$('#merch-details').hide();

		$('#btn-submit').hide();
		$('#btn-back').show();
	} else {

		$('#primaryDetails').hide();
		$('#merch-details').show();

		$('#btn-submit').show();
		$('#btn-back').show();

		var headerList = header.split("#");
		var headerData = "";

		if( header.indexOf("#") != -1 ) {

			$('#merchant-auth-header').append("<tr>");

			$(headerList).each(function(index,val){
				//console.log(val)
				if(index == 0) {
					$('#merchant-auth-header').append("<th><input type=\"checkbox\" name=\"select-all\" id=\"select-all\" /></th>");
				}
 				$('#merchant-auth-header').append("<th>"+val+"</th>");
			});

			$('#merchant-auth-header').append("</tr>");
 			//console.log(headerData);
 		}

		$ .each(json, function(i, v) {
			if (val % 2 == 0) {
				addclass = "even";
				val++;
			} else {
				addclass = "odd";
				val++;
			}

			colindex = ++colindex;

			appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'>" ;


					if(status == 'serv') {
						appendTxt +=	 "<td ><input class='center-chk' type='checkbox' value='" + v.serviceCode + "' /></td>"
									+"<td>"+ v.serviceCode + "</td>"
									+"<td>"+ v.serviceName + "</td>" ;
					} else if(status == 'subserv') {
						 appendTxt += "<td ><input class='center-chk' type='checkbox' value='" + v.subServiceCode + "' /></td>"
											+"<td>"+ v.subServiceCode + "</td>"
											+"<td>"+ v.subServiceName + "</td>"
											+"<td>"+ v.serviceCode + "</td>"
											+"<td>"+ v.serviceName + "</td>" ;
					} else if(status == 'proc') {
							 appendTxt += "<td ><input class='center-chk' type='checkbox' value='" + v.processingCode + "' /></td>"
											+ "<td>"+ v.processingCode + "</td>"
											+"<td>"+ v.processingName + "</td>" ;
					}else if(status == 'BinAuth') {
						 appendTxt += "<td ><input class='center-chk' type='checkbox' value='" + v.bin + "' /></td>"
							+ "<td>"+ v.binCode + "</td>"
							+"<td>"+ v.bankName + "</td>"
						 	+"<td>"+ v.bin + "</td>"
						 	+"<td>"+ v.binDesc + "</td>" ;
						}
					else {
							 appendTxt += "<td ><input class='center-chk' type='checkbox' value='" + v.feeCode + "' /></td>"
											+"<td>"+ v.feeCode + "</td>"
											+"<td>"+ v.serviceCode + "</td>"
											+"<td>"+ v.subServiceCode + "</td>" ;
					}

 			appendTxt += "<td>"+ v.makerId + "</td>"
					+ "<td class='center hidden-phone'>"+ v.makerDate + "</td>"
					+ "<td class='hidden-phone'><a href='#' class='label label-info' index='"+rowindex+"'>"+ v.status + "</a> </td></tr>";

			$("#merchant-auth-data").append(appendTxt);
			rowindex = ++rowindex;
		});
	}


	// add multiple select / deselect functionality
	$("#select-all").click(function () {
		$("#error_dlno").text("");
		$('.center-chk').attr('checked', this.checked);
	});

	// if all checkbox are selected, check the selectall checkbox
	// and viceversa
	$(".center-chk").click(function(){
		$("#error_dlno").text("");
		if($(".center-chk").length == $(".center-chk:checked").length) {
			$("#select-all").attr("checked", "checked");
		} else {
			$("#select-all").removeAttr("checked");
		}
	});

	$("#btn-back").click(function(event) {
		event.preventDefault();
		var url="${pageContext.request.contextPath}/<%=appName %>/generateMerchantAct.action";
		$("#form1")[0].action=url;
		$("#form1").submit();
	});

	$("#btn-submit").click(function(event) {
		event.preventDefault();
		var searchIDs = [] ;
		var data = "";
		var mulData = "";
		var innerMulData = "";

 		$("tbody#merchant-auth-data input:checkbox:checked").each(function(index) {
			searchIDs.push($(this).val());
			if(index == 0) {
				data += $(this).val().trim();
			} else {
				data += ","+$(this).val().trim();
			}
		});


		$("#"+parentId).each(function(indexTr){
			subParent = parentId + "#"+$(this).attr("id")+" td";
 			innerMulData = "";
			$("#"+subParent).each(function(indexTd){
 				if(indexTd == 0) {
					checkedCheckbox = $("#"+subParent+" > .center-chk").is(":checked");
				}
 				if(checkedCheckbox) {
					if(indexTd != 0) {
						if(indexTd == 1) {
							innerMulData += $(this).text().trim();
						} else {
							innerMulData += ","+$(this).text().trim();
						}
					}
				}
			});

			if(innerMulData.indexOf(",") !=-1) {
				mulData += innerMulData +"#";
			}
		});

		if(searchIDs.length == 0) {
			$("#error_dlno").text("Please check atleast one record.");
		} else {
			$("#selectedUserText").val(data);
			$("#bankMultiData").val(mulData.slice(0,-1));
			$("#error_dlno").text("");
			var url="${pageContext.request.contextPath}/<%=appName %>/feeAuthorizationCnf.action";
			$("#form1")[0].action=url;
			$("#form1").submit();

		}
	});

	$('#btn-back').live('click', function () {
		var url="${pageContext.request.contextPath}/<%=appName %>/allFeeAuthorization.action";
		$("#form1")[0].action=url;
		$("#form1").submit();
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
					<li><a href="serviceMgmtAct.action">Merchant Management</a> <span class="divider"> &gt;&gt; </span> </li>
					<li><a href="#"><span id="header-data" class="merchant"> </span> Authorization</a></li>
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
						<span id="header-data" class="merchant"> </span> Information
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
									<td colspan="4"> No records to authorize, please press back to Fee Management.</td>
								</tr>
							</table>
						</fieldset>
					</div>
					<div class="box-content" id="merch-details">
						<fieldset>
							<table width="100%" class="table table-striped table-bordered bootstrap-datatable "
								id="DataTables_Table_0">
								<thead id="merchant-auth-header">
								</thead>
								<tbody id="merchant-auth-data">
								</tbody>
							</table>
						</fieldset>
					</div>
				</div>
			</div>
			<div class="form-actions">
				<input type="button" class="btn btn-success" name="btn-submit"
						id="btn-submit" value="Authorize"  />

				<input type="button" class="btn btn-danger" name="btn-back"
						id="btn-back" value="Back"  />

				<span id ="error_dlno" class="errors"></span>

  				<input name="selectedUserText" type="hidden" id="selectedUserText" value="<s:property value="responseJSON['selectedUserText']" />" />
  				<input name="status" type="hidden" id="status" value="<s:property value="responseJSON['status']" />" />
				<input name="bankMultiData" type="hidden" id="bankMultiData" value="<s:property value="responseJSON['bankMultiData']" />" />
				<input name="headerData" type="hidden" id="headerData" value="<s:property value="responseJSON['headerData']" />" />
				<input type="hidden" name="service" id="service" value="${service}"/>
				<input name="agentMultiData" type="hidden" id="agentMultiData" value='<s:property value="responseJSON['agentMultiData']" />'  />


			</div>
			<!--/#content.span10-->
	</div>
</form>
</body>
</html>
