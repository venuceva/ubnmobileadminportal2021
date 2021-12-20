<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
  
 
<script type="text/javascript"> 
 	$(document).ready(function() {
		var mydata = '${resJSON.reportsList}';
 		var json = jQuery.parseJSON(mydata);
		var htmlString = "";
		var i;
		$.each(json, function(i, v) {
			  htmlString += "<tr>"; 
			  htmlString += "<td>" + v.channel + "</td>";
			  htmlString += "<td>" + v.netID + "</td>";
			  htmlString += "<td>" + v.action + "</td>";
			  htmlString += "<td>" + v.datetime + "</td>";
			  htmlString += "<td>" + v.accessingIP + "</td>";
			  htmlString += "<td>" + v.message + "</td>";
			  htmlString += "</tr>";
				
				$('#searchTBody').append(htmlString);
				
				htmlString=""; 
		});
		
 	});

</script>
</head> 
<body> 
	<div id="content" class="span10">  
		<div>
			<ul class="breadcrumb">
				<li>
					<a href="home.action">Home</a> <span class="divider">&gt;&gt;</span>
				</li>
				<li><a href="#">Audit Trail</a>  
				</li> 
			</ul>
		</div>
		 
		    <table  height="3">
				<tr>
					<td colspan="3">
						<div class="messages" id="messages"><s:actionmessage /></div>
						<div class="errors" id="errors"><s:actionerror /></div>
					</td>
				</tr>
			</table> 
			<div class="row-fluid sortable">  
				<div class="box span12">
					
					  <div class="box-header well" data-original-title>User Information
						<div class="box-icon"> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
						</div>
					</div>
					   
						<div class="box-content">
							<fieldset>
								<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable"
									id="DataTables_Table_0"  >
									<thead>
										<tr>
 											<th style="width:8%">Channel</th>
											<th style="width:10%">User ID </th>
											<th style="width:12%">Action Description </th>
											<th style="width:10%">Date & Time</th>
											<th style="width:10%">Accessing IP</th> 
											<th style="width:60%">Action Performed</th>
										</tr>
									</thead> 
									<tbody id="searchTBody"> 
									</tbody> 
								</table>
							</fieldset>
						</div>
			   </div>
			 </div>  
     </div> 
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>