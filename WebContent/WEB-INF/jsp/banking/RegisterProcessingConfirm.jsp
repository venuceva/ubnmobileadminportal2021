
<!DOCTYPE html>

<%@taglib uri="/struts-tags" prefix="s"%> 

<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
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
</style> 
 	 
<script type="text/javascript" >
$(document).ready(function() {
 
	
	var val = 1;
	var rowindex = 1;
	var colindex = 0;
	var bankacctfinalData="${responseJSON.BANK_MULTI_DATA}";
	//bankacctfinalData=bankacctfinalData.slice(1);
	var bankacctfinalDatarows=bankacctfinalData.split("#");
	if(val % 2 == 0 ) {
	addclass = "even";
	val++;
	}
	else {
	addclass = "odd";
	val++;
	}  
	var rowCount = $('#tbody_data > tr').length;

	
		for(var i=0;i<bankacctfinalDatarows.length;i++){
			var eachrow=bankacctfinalDatarows[i];
			var eachfieldArr=eachrow.split(",");
			var processingCode=eachfieldArr[0];
			var processingDesc=eachfieldArr[1];
			
				var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
				"<td >"+rowindex+"</td>"+
				"<td><input type='hidden' name='frequencies' value='"+processingCode+"' />"+processingCode+"</td>"+	
				"<td><input type='hidden' name='dateTime' value='"+processingDesc+"' />"+processingDesc+" </td>"+ 
				"</tr>";
				
				$("#tbody_data1").append(appendTxt);	  
			rowindex = ++rowindex;
			colindex = ++ colindex; 
		}
		
		$('#btn-submit').live('click', function () { 
			 $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/inserProcessingCodeAct.action';
			$("#form1").submit();
			return true; 
		});
		
		$('#btn-cancel').live('click', function () { 
 			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/serviceMgmtAct.action';
			$("#form1").submit();
			return true;
		});
		
});
  
</script>
 

</head>

<body>
	<form name="form1" id="form1" method="post" action="">
	 
		<div id="content" class="span10">
            			<!-- content starts -->
			    <div>
						<ul class="breadcrumb">
						  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
						  <li> <a href="#">Fee Management</a> <span class="divider"> &gt;&gt; </span></li>
						  <li><a href="#">Register Transaction Processing Code</a></li>
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
			 
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						 
							<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Register Transaction Processing Code
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>

							<div id="primaryDetails" class="box-content"> 
								<fieldset>
									<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
											id="bankAcctData" >
									  <thead>
											<tr>
												<th>Sno</th>
												<th>Transaction Processing Code</th>
												<th>Transaction Processing Description</th>
											</tr>
									  </thead>    
									 <tbody  id="tbody_data1">
									 </tbody>
									</table>
								</fieldset>
							</div>
						</div>
					</div>
		<input type="hidden" name="bankMultiData" id="bankMultiData" value="${responseJSON.BANK_MULTI_DATA}"></input>   

		<div  class="form-actions"> 
 			  <input type="button" class="btn btn-info" type="text"  name="btn-cancel" id="btn-cancel" value="Back" width="100" ></input>&nbsp;
			<input type="button" class="btn btn-success" type="text"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>
		</div> 
	</div>  
</form>
</body>
</html>
