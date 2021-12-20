
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.min.css' />
<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}

</style>

<script type="text/javascript">
var billerrules = {
		rules : {
			authradio : { required : true },
			},
		messages : {
			authradio : {
				required : "Please Select Atleast One Option."
		  	}
		},

	};



$(document).ready(function(){
	var status = '<s:property value="object.status"/>';
	if(status=='N'){
		$('#auth-data').show();
		$('#crt').show();
		$('#home').hide();
	}else{
		$('#auth-data').hide();
		$('#home').show();
		$('#crt').hide();
	}

	$('#btn-submit').on('click',function() {
		$("#form1").validate(billerrules);
		if($("#form1").valid()) {
			$("#form1").validate().cancelSubmit = true;
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/authBiller.action";
			$("#form1").submit();
		} else {
			return false;
		}
	});

	$("input[name='authradio']:radio").click(function() {
	    alert($(this+":checked").val());
	    $('#status').val($("input[name=authradio]:checked").val());
	});
	$('#btn-back').on('click',function() {
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action";
		$("#form1").submit();
	});
	$('#btn-home').on('click',function() {
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action";
		$("#form1").submit();
	});


});
</script>
<s:set name="object.status" value="state"/>
</head>
<body>
	  <div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="mpesaAccManagement.action">Biller Management</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Create New Biller</a></li>
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
	<form name="form1" id="form1" method="post" autocomplete="off" style="margin: 0px 0px 50px">
		<div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Biller Information
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>

				<div class="box-content">
					<fieldset>
					<table width="98%"  border="0" cellpadding="5" cellspacing="1"
						class="table table-striped table-bordered bootstrap-datatable " >
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Biller Category<font color="red">*</font></strong></label></td>
								<td width="30%"><s:property value="object.catText"/></td>
							</tr>
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Channel<font color="red">*</font></strong></label></td>
								<td width="30%" ><s:property value="object.instText"/></td>
							</tr>
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Biller Name<font color="red">*</font></strong></label></td>
								<td width="30%" ><s:property value="object.billerName"/></td>
							</tr>
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Account Number<font color="red">*</font></strong></label></td>
								<td width="30%" ><s:property value="object.accountNumber"/></td>
							</tr>
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Biller Description<font color="red">*</font></strong></label></td>
								<td width="30%" ><s:property value="object.billerDesc"/></td>
							</tr>
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Support Email<font color="red">*</font></strong></label></td>
								<td width="30%"><s:property value="object.supportEmail"/></td>
							</tr>
							<tr >
								<td width="20%"><label for="Support Contact"><strong>Support Contact<font color="red">*</font></strong></label></td>
								<td width="30%" ><s:property value="object.supportContact"/></td>
							</tr>
							<tr >
								<td width="20%"><label for="Address"><strong>Address<font color="red">*</font></strong></label></td>
								<td width="30%"><s:property value="object.address"/></td>
							</tr>
							<tr >
								<td width="20%"><label for="Address"><strong>Maker<font color="red">*</font></strong></label></td>
								<td width="30%"><s:property value="object.maker"/></td>
							</tr>
							<tr >
								<td width="20%"><label for="Address"><strong>Creation Date<font color="red">*</font></strong></label></td>
								<td width="30%"><s:property value="object.makerDttm"/></td>
							</tr>
					</table>
				</fieldset>
			</div>
		</div>

			<div id="auth-data">
				<ul class="breadcrumb">
				 <li> <strong>Authorize&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </strong><input  name="authradio" id="authradio"  class='center-chk' type='radio' value='A' />&nbsp&nbsp </li>
				 <li> <strong>Reject&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </strong><input  name="authradio" id="authradio"  class='center-chk' type='radio' value='R' /> &nbsp&nbsp&nbsp</li>
				</ul>
		   </div>
		</div>
		<input type="hidden" name="refNum" id="refNum" value='<s:property value="object.refNum" />'>
		<input type="hidden" name="status" id="status" value='<s:property value="object.status" />'>
	</form>

		<div class="form-actions" id="crt">
	         <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100" ></input>&nbsp;
	         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input>&nbsp;
        </div>
		<div class="form-actions" id="home">
	         <input type="button" class="btn btn-success" name="btn-home" id="btn-home" value="Home" width="100" ></input>&nbsp;
        </div>

	</div>
 </body>
</html>