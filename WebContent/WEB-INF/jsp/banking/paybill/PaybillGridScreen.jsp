<!DOCTYPE html>
<html lang="en">
<head>
<%@taglib uri="/struts-tags" prefix="s"%>
<meta charset="utf-8">
<title> </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>

<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<style type="text/css">
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

label.error {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}

.errmsg {
	color: red;
}

.dataTables_filter {
	float: right;
	margin-top: -30px;
}
</style>
<script type="text/javascript" >
	var billerIdDetails = "";
 	var billerList = new Array();
 	var userTableRights=new Array();
	var userLinkData ='${USER_LINKS}';
	var jsonLinks = jQuery.parseJSON(userLinkData);
	var linkIndex = new Array();
	var linkName = new Array();
	var linkStatus = new Array();

	function postData(actionName,paramString) {
		$('#form2').attr("action", actionName)
				.attr("method", "post");

		var paramArray = paramString.split("&");

		$(paramArray).each(function(indexTd,val) {
			var input = $("<input />").attr("type", "hidden").attr("name", val.split("=")[0]).val(val.split("=")[1].trim());
			$('#form2').append($(input));
		});
		$('#form2').submit();
	}
var btns=['view-services', 'view-channel-transactions'];
    $(document).ready(function () {

    	$.each(jsonLinks, function(index, v) {
			linkIndex[index] = index;
			linkName[index] = v.name;
			linkStatus[index] = v.status;
		});
//console.log(JSON.stringify(jsonLinks));
    	$(function () {

  	      var accesbtn = "";
  		  var btns = "";
  		  var rejectview = "";


	  	 table = $('#datatable').dataTable({
	  	    "bPaginate": true,
	  	    "iDisplayStart":0,
	  	    "bProcessing" : true,
	  	    "bServerSide" : true,
	  	    "bDestroy": true,
	  	    "sPaginationType": "full_numbers",
	  	    "sAjaxSource" : "<%=ctxstr%>/<%=appName %>/merchantpagination.action?qid=130041&service=paybill",
	  	  "columnDefs": [ {
	            "targets": -1,
	            "data": null,
	            "defaultContent": '<a href="#" id="view-services" class="btn btn-success"   href="#" title="View Services" data-rel="tooltip" ><i class="icon icon-book icon-white"></i></a> &nbsp;&nbsp;'
	            					+ '<a href="#" id="view-channel-transactions" class="btn btn-primary"   href="#" title ="View Channel Transactions" data-rel="tooltip" ><i class="icon icon-hdd icon-white"></i></a>'
	        } ]
  	 });

  	 $("#datatable tbody tr td").live('mouseover', function(e) {
  	       var indx = $(this).index();
  	       if(indx == 1){
  	    	 $(this).addClass("actiontd");
  	       }
  		});
  		$("#datatable tbody tr td").live('mouseout', function(e) {
  		       var indx = $(this).index();
  		       if(indx == 1){
  		    	 $(this).removeClass("actiontd");
  		       }
  		});
  		$(".actiontd").live('click', function(e) {
  	       var indx = $(this).index();
  	       if(indx == 1){
  	    	  	var cellText = $(this).html().trim();
  	    	 	$("#institute").val(cellText);
  	    		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/viewchannelServices.action";
  		    	$("#form1").submit();
  	       }else{
  	    	   e.preventdefault();
  	    	   return false;
  	       }

  	    });

  $('#datatable tbody').on('click','a', function(){
  	try{

  		var data = $(this).parents('tr');
  		$("#institute").val($(data).find('td').eq(1).text());
  		var ac_id=$(this).attr('id');
  		$("#instId").val($(data).find('td').eq(1).text());

  		if(ac_id == "view-services")
  		{
  			  $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/viewChannelCategory.action";
  			  $("#form1").submit();
  		}
  		if(ac_id == "view-channel-transactions")
  		{
  			  $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/viewTransactions.action";
  			  $("#form1").submit();
  		}

  	}catch (e) {
  		console.log(e);
  	}

  });

  });
});

$(document).on('click','a',function(event) {
	var v_id=$(this).attr('id');
	var v_role = $(this).attr('role');

	if(v_id != 'SEARCH_NO') {

		var disabled_status= $(this).attr('disabled');
		var queryString = 'entity= ';
		var v_action = "NO";

		var index1 = $(this).attr('index');
		var parentId =$(this).parent().closest('tbody').attr('id');
		var searchTrRows = parentId+" tr";
		var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
		var id = "";
		var billerType = "";
		var billerId ="";


		if(disabled_status == undefined) {
				if(v_id ==  "add-biller-type") {
					v_action="addBillerType";
				} else if(v_id == "create-biller-id"
							|| v_id == "modify-biller-type"
							|| v_id == "view-biller-type"
							|| v_id == "en-ds-biller-type"){
 					 // Anchor Tag ID Should Be Equal To TR OF Index
						$(searchTdRow).each(function(indexTd) {
							if (indexTd == 1) {
								billerType=$(this).text().trim();
							 }   if(indexTd == 2) {
							 }   if(indexTd == 3) {
 							 }   if(indexTd == 4) {
							 }
						});

						queryString += '&id='+v_role+'&billerType='+billerType;

						if(v_id ==  "create-biller-id") {
							v_action = "addBillerId";
 						} else if(v_id ==  "modify-biller-type") {
							v_action="getBillerTypeDetails";
							queryString += '&method=modify'
						} else if(v_id ==  "view-biller-type") {
							v_action = "viewBillerTypeDetails";
							queryString += '&method=view'
						} else if(v_id ==  "en-ds-biller-type") {
							v_action="actDeactBillerDetails";
							queryString += '&method=actdeact';
						}

				} else if(v_id == "modify-biller-id"
						|| v_id == "view-biller-id"
 						|| v_id == "en-ds-biller-id"){

					$(searchTdRow).each(function(indexTd) {
						if (indexTd == 1) {
							billerId=$(this).text().trim();
						 }   if(indexTd == 2) {
 						 }   if(indexTd == 3) {
						 }   if(indexTd == 4) {
						 }
					});

					queryString +='&billerId='+billerId;

					if( v_id ==  "modify-biller-id") {
						v_action = "modifyBillerId";
						} else if(v_id ==  "view-biller-id") {
						v_action="viewBillerId";
						//queryString += '&method=modify'
					}   else if(v_id ==  "en-ds-biller-id") {
						v_action="modifyBillerIdAcDc";
					}
				}

		} else {

			// No Rights To Access The Link
		}

		console.log(queryString);
		if(v_action != "NO") {
			postData(v_action+".action",queryString);
		}
	} else {

		var txt_sr = $(this).attr('value');
		var parentId =$(this).parent().closest('table').attr('id');
		$('div input[type=text]').each(function(){
			console.log("["+$(this).attr("aria-controls") +"] == ["+ parentId+"]");

			if($(this).attr("aria-controls") == parentId) {
				$(this).val(txt_sr);
				$(this).trigger("keyup");
			}
		});
	}


});

</script>


</head>

<body>
<form name="form1" id="form1" method="post" >
	<div id="form1-content" class="span10">
		  <div>
			 <ul class="breadcrumb">
				<li><a href="home.action">Home </a> <span class="divider"> &gt;&gt; </span></li>
				<li><a href="#">Biller Management</a></li>
			</ul>
		 </div>
		<div class="box-content" id="top-layer-anchor">
			<span>
				<a href="#" class="btn btn-info" id="add-biller-type" title='Add Categories' data-rel='popover'  data-content='It Maps Categories to Channels.'>
				<i class="icon icon-web icon-white"></i>&nbsp;Add Categories</a> &nbsp;
			</span>
			<%-- <span>
				<a href="#" class="btn btn-success" id="add-biller-category" title='Add Biller Category' data-rel='popover'  data-content='Create New Biller Category.'>
				<i class="icon icon-web icon-white"></i>&nbsp;Add Biller Category</a> &nbsp;
			</span> --%>
		</div>

	<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Biller Type Information
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
								<th>Channel Id</th>
  								<th>Channel Name</th>
 								<th>Channle Description</th>
 								<th>Actions</th>
							</tr>
						</thead>
						<tbody id="tbody">

						</tbody>
					</table>
				</div>
			</div>
		</div>
<input type="hidden" name="institute" id="institute" value="">
<input type="hidden" name="instId" id="instId" value="">
	</div>
</form>
<form name="form2" id="form2" method="post">
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script>
</body>
</html>