<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%
	String ctxstr = request.getContextPath();
%>
<%
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>

<style type="text/css">

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
$(document).ready(function() {

	var status = "${status}";


	if(status == 'serv') {
		$('.merchant').text('Service');
 	} else if(status == 'subserv') {
		$('.merchant').text('Sub Service');
 	} else if(status == 'proc') {
		$('.merchant').text('Processing Code');
 	}else if(status == 'BinAuth') {
		$('.merchant').text('Bin Authrisation');
 	}
 	else {
		$('.merchant').text('Fee ');
 	}

	var data = '${selectedUserText}';
	var msg = '${responseJSON.msg}';

	var bankfinalDatarows=data.split(",");
	var val = 1;
	for(var i=0;i<bankfinalDatarows.length;i++) {
		if(val % 2 == 0 ) {
			addclass = "even";
			val++;
		} else {
			addclass = "odd";
			val++;
		}

		var appendTxt = "<tr class="+addclass+"> "+
			 "<td>"+bankfinalDatarows[i]+"  </td>";
			 if(msg == 'FAIL') {
				appendTxt+="<td class='active'> <i class='icon icon-close icon-color32'></i> </td>";
			} else {
				appendTxt+="<td class='active'> <i class='icon icon-check icon-color32'></i> </td>";
			}
		 appendTxt+="</tr>";
		$("#tbody_data").append(appendTxt);
	}

	$('#btn-cancel').live('click', function () {
		var url="${pageContext.request.contextPath}/<%=appName %>/home.action";
		$("#form1")[0].action=url;
		$("#form1").submit();
	});

	$('#btn-submit').live('click', function () {
		var url="${pageContext.request.contextPath}/<%=appName %>/serviceMgmtAct.action";
		$("#form1")[0].action=url;
		$("#form1").submit();

	});

});
</script>
</head>
<body>
<form name="form1" id="form1" method="post">
	<div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
			 <li><a href="home.action">Home</a> <span class="divider">
							&gt;&gt; </span></li>
					<li><a href="serviceMgmtAct.action">Fee Management</a> <span class="divider">
							&gt;&gt; </span></li>
					<li><a href="#"><span id="header-data" class="merchant"> </span> Authorization Acknowledge</a></li>
			</ul>
		</div>

		<div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i> <span id="header-data1" class="merchant"> </span> Authorization Acknowledge
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i
							class="icon-cog"></i></a> <a href="#"
							class="btn btn-minimize btn-round"><i
							class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="box-content">
					<table  class="table table-striped table-bordered bootstrap-datatable "
						id="mytable" style="width: 100%;">
					  <thead>
							<tr>
								<th><span id="header-data2" class="merchant"> </span> Id</th>
								<th>Status</th>
							</tr>
					  </thead>
					  <tbody id="tbody_data">
					  </tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="form-actions">
			<input type="button" name="btn-submit" class="btn btn-primary" id="btn-submit" value="Back To Fee Management" />
			&nbsp;<input type="button" name="btn-cancel" class="btn" id="btn-cancel" value="Home"/>
		</div>
	</div>
</form>
</body>
</html>
