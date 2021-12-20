
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Banking</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Brian Kiptoo">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<!-- The styles -->
<link href="<%=ctxstr%>/css/bootstrap-united.css" rel="stylesheet"/>
<link href="<%=ctxstr%>/css/charisma-app.css" rel="stylesheet"/>  
<link href="<%=ctxstr%>/css/bootstrap-responsive.css" rel="stylesheet"/>

<!-- jQuery -->  
<script src="<%=ctxstr%>/js/jquery-1.7.2.min.js"></script> 
 
<!--  Scripts --> 
<script type="text/javascript" src="<%=ctxstr%>/js/dropdown.js"></script> 
<script type="text/javascript" src="<%=ctxstr%>/js/jquery.navgoco.js"></script>
<script type="text/javascript" > 

    function IctAdminViewDetails(){
    	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/getMerchantCreateScreenAct.action";
    	$("#form1").submit();
    	return true;
    } 

    function getNewUserCreateScreen(){ 

    	$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/geTICTAdmincreatePgae.action?entity=<%=session.getAttribute("loginEntity").toString()%>";
    	$("#form1").submit();
    	return true;
    } 
    $(document).ready(function () {

		$("#StoreData").hide();
		var merchantData ='${responseJSON.MERCHANT_LIST}';
		//alert(mydata);
		var json = jQuery.parseJSON(merchantData);
		//alert(json);
		var val = 1;
		var rowindex = 0;
		var colindex = 0;
		var addclass = "";
		$.each(json, function(i, v) {
			if(val % 2 == 0 ) {
				addclass = "even";
				val++;
			}
			else {
				addclass = "odd";
				val++;
			}
			var rowCount = $('#merchantTBody > tr').length;

			//rowindex = ++rowindex;
			colindex = ++ colindex;

			var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
			"<td class=' sorting_1' >"+colindex+"</td>"+
			"<td class=''><span class='a0488m96' id='a0488m96_3'>"+v.userid+"</span></td>"+
			"<td class=''><span class='a0488m96' id='a0488m96_3'>"+v.username+"</span> </td>"+
			"<td class=''><span class='a0488m96' id='a0488m96_3'>"+v.employeeNo+"</span></td>"+
			"<td class=''><span class='a0488m96' id='a0488m96_3'>"+v.email+"</span></td>"+
			"<td class='center '><a  id='IctAminView' class='btn btn-success'  href='#' index='"+rowindex+"' >View</a> &nbsp; <a  id='ictUserAdminModify' class='btn btn-warning' href='#' index='"+rowindex+"'  class='btn btn-warning' >Modify</a> &nbsp; <a  class='btn btn-info' href='#' >Reset Password</a> &nbsp; <a id='StoreCreate' class='btn btn-primary'  href='#' >Deactive</a></td></tr>";

				$("#merchantTBody").append(appendTxt);
				rowindex = ++rowindex;

		}); 
	}); 
	</script> 
	<script>

	$(document).on('click','#IctAminView',function(event) {
		 index = $(this).attr('index');
		//alert("hellow"+index);
		var searchRow = "DataTables_Table_0 tbody tr:eq("+(index)+") td";
			//alert(searchRow);
			$('#'+searchRow).each(function (indexTd) {
				 //alert("index posting in loop is ==> "+indexTd)

				 if (indexTd == 1) {
					// Frequency
					userid=$(this).text();
				 }   if(indexTd == 2) {
					// Time or Date
					username=$(this).text();
				 }   if(indexTd == 3) {
					// email ids
					 email=$(this).text();
				 }

			});
 
			$("#form1")[0].action='<%=request.getContextPath()%>'+"/"+'<%=appName %>'+'/ictAdminViewAct.action?userid='+userid;
			$("#form1").submit();
			return true;
	});


	$(document).on('click','#ictUserAdminModify',function(event) {
		 index = $(this).attr('index');
		alert("hellow"+index);
		var searchRow = "DataTables_Table_0 tbody tr:eq("+(index)+") td";
			//alert(searchRow);
			$('#'+searchRow).each(function (indexTd) {
				 //alert("index posting in loop is ==> "+indexTd)

				 if (indexTd == 1) {
					// Frequency
					userid=$(this).text();
				 }   if(indexTd == 2) {
					// Time or Date
					username=$(this).text();
				 }   if(indexTd == 3) {
					// email ids
					 email=$(this).text();
				 }

			});
 
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/ictAdminModifyAct.action?userid="+userid;
			$("#form1").submit();
			return true;
	});

	</script>

	<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

	<!-- The fav icon -->
	<link rel="shortcut icon" href="img/favicon.ico">

</head>

<body>
<form name="form1" id="form1" method="post" action="">
	<!-- topbar ends -->
		<div class="container-fluid" style="width: 78%; float: left; margin-left: 3%;">
		<div class="row-fluid" style="margin-left: 3%;">
			<div id="content" class="span12"> 

			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>

             			<!-- content starts -->
			              <div>
      							 <ul class="breadcrumb">
									<li><a href="#">Home </a> <span class="divider">&gt;&gt;</span></li>
									<li><a href="#">User Registration</a></li>
								</ul>
      					</div>

      					<div class="box-content">
								 <div>
									<a href="#" class="btn btn-primary ajax-link" onClick="getNewUserCreateScreen()">New User Creation</a> &nbsp;

								 </div>
						</div>

<div class="row-fluid sortable"><!--/span-->

	<div class="box span12">
                  <div class="box-header well" data-original-title>User Information
              <div class="box-icon"> <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> </div>
          </div>
                  <div class="box-content">
                    <div class="box-content">
                    </div>
					<div class="box-content"> 
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable dataTable" id="DataTables_Table_0" aria-describedby="DataTables_Table_0_info" style="width: 100%;">
							<thead>
								<tr role="row">
									<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-sort="ascending" aria-label="#: activate to sort column descending" style="width: 25px;">S No</th>
									<th width="93" class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Service: activate to sort column ascending" style="width: 100px;">User Id</th>
									<th width="93" class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Group: activate to sort column ascending" style="width: 150px;">User Name </th>
									<th width="105" class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Date Created: activate to sort column ascending" style="width: 105px;">Email Id</th>
									<th width="93" class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Service: activate to sort column ascending" style="width: 100px;">Employee No</th>
									<th width="283" class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Actions: activate to sort column ascending" style="width: 283px;">Actions</th>
								</tr>
							</thead> 
							<tbody role="alert" aria-live="polite" aria-relevant="all" id="merchantTBody">
							</tbody>
						</table>
                    </div>
          </div>
                </div>
        <!--/span--><!--/span-->
      </div>





              						<!-- content ends -->
			</div><!--/#content.span10-->
				</div><!--/fluid-row-->
				</div> 
				</form>
		<hr>

		<div class="modal hide fade" id="myModal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>Settings</h3>
			</div>
			<div class="modal-body">
				<p>Here settings can be configured...</p>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">Close</a>
				<a href="#" class="btn btn-primary">Save changes</a>
			</div>
		</div>


	<!--/.fluid-container-->

	<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster --> 

	<!-- transition / effect library -->
	<script src="<%=ctxstr%>/js/bootstrap-transition.js"></script>  <!--  Not Required -->
 
	<!-- modal / dialog library -->
	<script src="<%=ctxstr%>/js/bootstrap-modal.js"></script>		<!--  Not Required -->
	<!-- custom dropdown library -->
	<script src="<%=ctxstr%>/js/bootstrap-dropdown.js"></script> 	<!--  Not Required -->
	<!-- button enhancer library -->
	<script src="<%=ctxstr%>/js/bootstrap-button.js"></script> 
	<!-- data table plugin -->
	<script src='<%=ctxstr%>/js/jquery.dataTables.min.js'></script>   
	 
	<!-- application script for Charisma demo -->
	<script src="<%=ctxstr%>/js/charisma.js"></script>
    <script type="text/javascript" src="<%=ctxstr%>/js/jquery.nyroModal.custom.js"></script>

<!--[if IE 6]>
	<script type="text/javascript" src="<%=ctxstr%>/js/jquery.nyroModal-ie6.min.js"></script>
<![endif]-->
<script type="text/javascript">
$(function() {
  $('.nyroModal').nyroModal();
});
</script>

</body>
</html>
