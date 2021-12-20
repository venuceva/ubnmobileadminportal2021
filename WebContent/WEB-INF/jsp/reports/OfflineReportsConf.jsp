<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 
<script type="text/javascript"> 
 
 	
 	
 	$(function() {
		var storeData = '${responseJSON.CLAIMS2_LIST}';
	
	var json = jQuery.parseJSON(storeData);
		
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	//var addclass = "";
	//var i=0;
	$.each(json, function(i, v) {
		if(val % 2 == 0 ) {
			addclass = "even";
			val++;
		}
		else {
			addclass = "odd";
			val++;
		}  
	
		var rowCount = $('#searchTBody > tr').length;

		
		colindex = ++ colindex; 
		var ReportName=(v.ReportName == undefined) ? "" : v.ReportName;
		var JrxmlName=(v.JrxmlName == undefined) ? "" : v.JrxmlName;
		var Formats=(v.Formats == undefined) ? "" : v.Formats;
		var Gdate=(v.Gdate == undefined) ? "" : v.Gdate;
		
		index=colindex-1;
		
			
			
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+
			"<td >"+colindex+"</td>"+
			"<td>"+ReportName+"</span></td>"+	
			"<td>"+Gdate+"</span> </td>"+
			"<td>"+Formats+"</span> </td>";
			"</tr>";
			
			$('#searchTBody').append(appendTxt);	
			rowindex = ++rowindex;
	
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
				<li><a href="#">Reports Information</a>  
				</li> 
			</ul>
		</div>
		 
 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">  
				<div class="box span12">
					
					  <div class="box-header well" data-original-title>Reports Information
						<div class="box-icon"> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
						</div>
					</div>
					   
						<div class="box-content">
							<fieldset>
								<table class="table table-striped table-bordered bootstrap-datatable datatable"
									id="DataTables_Table_0"  >
									<thead>
										<tr>
										<td width="1%"><strong>SNO </strong></td>
 											<td width="20%"><strong>Report Name </strong></td>
											<td width="20%"><strong>Report Date</strong></td>					
											<td width="20%" align="center"><strong>Status</strong></td>	
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