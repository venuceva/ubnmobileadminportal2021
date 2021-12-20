
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
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

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


   #sortable2 {
    border: 1px solid #eee;
    width: 300px;
    height: 300px;
    list-style-type: none;
    margin: 0;
    padding: 5px 0 0 0;
    align: center;
    margin-right: 10px;
  /*   page-align:center; */
    text-align:left;
    overflow-y:auto;
    /*overflow-x:hidden;*/  
 
  }
  #sortable2 li {
    margin: 0 5px 5px 5px;
    padding: 5px;
    font-size: 1 em;
    width: 200px;
  }

</style>
<script type="text/javascript" >
$(document).ready(function() {
	
 		var selData = "<s:property value='seltext' />";
 		var selval = "<s:property value='selval' />";
 		console.log(selval);
	    var users = $('#listtable');
	    users.html('');
		for (var i = 0; i < selData.split('%').length-1; i++) {
			users.append('<tr><td>'+selData.split('%')[i]+'</td></tr>');
	    }
});


function listfwd(){
	var allVals = [];
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/createProductAct.action';
	$("#form1").submit();
	return true;
}

</script>

</head>

<body>

<body>
<form name="form1" id="form1" method="post" action="">
			<div id="content" class="span10">
			    <div>
					<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="serviceMgmtAct.action">Product Management</a> <span class="divider"> &gt;&gt; </span> </li>
					<li><a href="#"><span id="header-data" class="merchant"> </span> Product Creation Acknowledgement</a></li>
				</ul>
				</div>
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i> Product Management Acknowledge
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>

						<div id="primaryDetails" class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong> Product Limits Has been Created Successfully <font color="red"><s:property value='accBean.telephone' /> </font> </strong>
										</td> 
									</tr> 
								</table>
							<fieldset>
						</div>
						
		    			    </div>
                </div>
		<div class="form-actions">
			<a  class="btn btn-primary" href="#" onClick="listfwd()">Next</a>
		</div>
	</div>  
</form>
</body>
</html>