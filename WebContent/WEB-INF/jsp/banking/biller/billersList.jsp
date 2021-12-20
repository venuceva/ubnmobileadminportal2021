<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<script type="text/javascript" >

var val = 1;
var rowindex = 0;
var colindex = 0;
var bankAcctFinalData="";
var binstatus = "";
var v_message = "";
var jsonLinks = jQuery.parseJSON('${USER_LINKS}');
var linkIndex = new Array();
var linkName = new Array();
var linkStatus = new Array();

$(document).ready(function() {
	var btns=['en-ds-biller-id','modify-biller-type','view-biller-type'];
	$.each(btns, function(index, v) {
		$('#'+v).hide();
	});

	$.each(jsonLinks, function(index, v) {
		linkName[index] = v.name;
		$('#'+v.name).show();
	});

	$('div input[type=search]').each(function(){
		var txt_sr = $(this).attr('value');
		var parentId =$(this).parent().closest('table').attr('id');

		console.log("["+$(this).attr("aria-controls") +"] == ["+ parentId+"]");

		if($(this).attr("aria-controls") == parentId) {
			$(this).val(txt_sr);
			$(this).trigger("keyup");
		}
	});
	 $("#merchantTBody tbody tr td").live('mousedown',function(e) {
			 var indx = $(this).index();
		       if(indx == 5){
		    	var data = $(this).parents('tr');
		  		   var userstatus = $(data).find('td').eq(3).text();
		    	   }
		       if(indx == 1){
		    		var data = $(this).parents('tr');
 		  		   var userstatus = $(data).find('td').eq(3).text();
 		  		  //  alert(userstatus);
 		  		    if(userstatus == "Un-Authorize"){
 		  		    	$(this).click(false);
						}

		     }
     });

	 $("#datatable tbody tr td").live('mouseover', function(e) {
		       var indx = $(this).index();
		       if(indx == 3){
		    	 $(this).addClass("actiontd");
		       }
		       if(indx == 2){
			    	 $(this).addClass("actiontd");
			       }
			});
			$("#datatable tbody tr td").live('mouseout', function(e) {
		       var indx = $(this).index();
		       if(indx == 3){
		    	 $(this).removeClass("actiontd");
		       }
		       if(indx == 2){
		    	 $(this).removeClass("actiontd");
		       }
			});
			$(".actiontd").live('click', function(e) {
		       var indx = $(this).index();
		       if(indx == 3){
		    	var td = $(this).closest('td');
		    	$('#catId').val($(td).attr('cat'));
		   		$('#instId').val($(td).attr('role'));
		   		$('#billerId').val($(td).html());
		    	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/billerPackages.action";
		    	$("#form1").submit();
		    	return true;
		       }else if(indx == 2){
			    	var tr = $(this).closest('tr');
			    	$('#catId').val($(tr).attr('cat'));
			   		$('#instId').val($(tr).attr('role'));
			    	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/viewBillers.action";
			    	$("#form1").submit();
			    	return true;
			   }else{
		    	   e.preventdefault();
		    	   return false;
		       }

		    });

});

$(document).on('click','a',function(event) {
	var v_id=$(this).attr('id');
	var v_role = $(this).attr('role');
	if(v_id == 'SEARCH_NO') {

		var disabled_status= $(this).attr('disabled');
		var queryString = 'entity= ';
		var v_action = "NO";

	}else{
		$('#billerId').val($(this).attr('billerId'));
		$('#catId').val($(this).attr('cat'));

		$('#catText').val($(this).attr('catText'));
		$('#instText').val($(this).attr('instText'));
		if(v_id == 'biller-home'){
			v_action='mpesaAccManagement';
			}
		if(v_id == 'en-ds-biller-id'){
			v_action="enableCat";
		}
		if(v_id == 'modify-biller-type'){
			v_action="modifyCat";
		}
		if(v_id == 'view-biller-type'){
			v_action="viewCat";
		}
		if(v_id == 'create-biller-package'){
			v_action="createbillerpackage";
		}
		if(v_action != "NO") {
			var url="${pageContext.request.contextPath}/<%=appName %>/"+v_action+".action";
			$("#form1")[0].action=url;
			$("#form1").submit();
			return true;
		}
	}
});

</script>
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
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
  font-weight: bold;
  color: red;
  padding: 2px 8px;
  margin-top: 2px;
}
</style>
</head>
<body>
	<form name="form1" id="form1" method="post" action="">
	 <div id="form1-content" class="span10">
		  <div>
			 <ul class="breadcrumb">
				<li><a href="home.action">Home </a> <span class="divider"> &gt;&gt; </span></li>
				<li><a href="mpesaAccManagement.action">Biller Management</a></li>
				<li><a href="#">Biller List</a></li>
			</ul>
		 </div>

	<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Billers Information
					<div class="box-icon">
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
					</div>
				</div>
				<div class="box-content">
					<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"
						id="datatable" >
						<thead>
							<tr>
								<th>S No</th>
								<th>Channel Name</th>
  								<th>Category</th>
  								<th>billerId</th>
 								<th>Biller Name</th>
 								<th>Email</th>
 								<th>Contact</th>
 								<th>Status</th>
 								<th>Actions</th>
							</tr>
						</thead>
						<tbody id="tbody">
						<s:iterator value="%{billers}" var="obj" status="status">
						<tr class="<s:if test="%{#status.odd == true} ">odd</s:if> <s:else>even</s:else>" inst="<s:property value="instId" />" catId = "<s:property value="catId" />">
							<td><s:property value="%{#status.index+1}" /></td>
							<td><s:property value="instText" /></td>
							<td><s:property value="catText" /></td>
							<td><s:property value="billerId" /></td>
							<td><s:property value="billerName" /></td>
							<td><s:property value="supportEmail" /></td>
							<td><s:property value="supportContact" /></td>
							<td><s:property value="status" /></td>
							<td>
							<div style="display:inline;">
							<a class='btn btn-success' href='#' instText="<s:property value="instText" />" catText="<s:property value="catText" />" billerId="<s:property value="billerId" />" id='create-biller-package' title='Create Packages' data-rel='tooltip'><i class='icon icon-plus icon-white'></i></a>
							<a class='btn btn-warning' href='#' instText="<s:property value="instText" />" catText="<s:property value="catText" />" billerId="<s:property value="billerId" />"  id='modify-biller-type' title='Modify Biller' data-rel='tooltip'><i class='icon icon-edit icon-white'></i></a>&nbsp;
							<a class='btn btn-info' href='#' instText="<s:property value="instText" />" catText="<s:property value="catText" />" billerId="<s:property value="billerId" />"  id='view-biller-type' title='View Biller' data-rel='tooltip'><i class='icon icon-list icon-white'></i></a>&nbsp;
							<a class='btn btn-danger' href='#' instText="<s:property value="instText" />" catText="<s:property value="catText" />" billerId="<s:property value="billerId" />" id='en-ds-biller-id' title='Disable/Enable Biller' data-rel='tooltip'><i class='icon icon-lock icon-white'></i></a>&nbsp;
							</div>
							</td>
						</tr>
						</s:iterator>

						</tbody>
					</table>
				</div>
			</div>
		</div>
		<input type="hidden" name="instId" id="instId" value="">
		<input type="hidden" name="catId" id="catId" value="">
		<input type="hidden" name="instText" id="instText" value="">
		<input type="hidden" name="catText" id="catText" value="">
		<input type="hidden" name="billerId" id="billerId" value="">
		<span>
			<a href="#" class="btn btn-info" id="biller-home" title='Biller Home Screen' data-rel='popover'  data-content='Previus Screen.'>
			<i class="icon icon-web icon-white"></i>Back</a> &nbsp;
		</span>
	</div>

</form>
</body>
</html>
