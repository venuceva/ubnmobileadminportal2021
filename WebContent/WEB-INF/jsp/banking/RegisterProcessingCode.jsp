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
 
var serviceCreateRules= {
	rules : {
		processingCode : {required : true},
		processingDescription : {required : true}, 
 	},
	messages : {
		processingCode : {required : "Please Enter Processing Code." },
		processingDescription : {required :  "Please Enter Processing Description."} 
	}
};
var val = 1;
var rowindex = 1;
var colindex = 0;
var bankAcctFinalData="";
$(function(){

		$('#btn-submit').live('click', function () { 
			bankAcctFinalData=bankAcctFinalData.slice(1);
			if(bankAcctFinalData==""){
				$("#error_dlno").text("Please add atleast one 'Processing Code' ");
				return false;
			} else {
				$("#form1").validate().cancelSubmit = true;
				$("#error_dlno").text("");
				$("#bankMultiData").val(bankAcctFinalData);
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/registerProcessingCodeSubmitAct.action';
				$("#form1").submit();
				return true;
			}
		});
		
		$('#btn-cancel').live('click', function () { 
			$("#form1").validate().cancelSubmit = true;
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/serviceMgmtAct.action';
			$("#form1").submit();
			return true;
		});
		
		
	 $('#addCap2').on('click', function () {
		$("#error_dlno").text("");	 
		$("#form1").validate(serviceCreateRules);
			if($("#form1").valid()) { 
			
				var processingCode = $('#processingCode').val() == undefined ? ' ' : $('#processingCode').val();
				var processingDescription = $('#processingDescription').val() == undefined ? ' ' : $('#processingDescription').val();
						
				var queryString = "method=searchProcessingCode&processingCode="+processingCode;	
				
				$.getJSON("postJson.action", queryString,function(data) { 
					userstatus = data.status; 
					v_message = data.message; 
					if(userstatus == "FOUND") { 
						if(v_message != "SUCCESS") {
							$('#error_dlno').text(v_message);
						}  
					} else { 					 
					
						var eachrow=processingCode+","+processingDescription;
												
							var addclass = "";
							 
								if(val % 2 == 0 ) {
									addclass = "even";
									val++;
								}
								else {
									addclass = "odd";
									val++;
								}    
									var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
										"<td>"+rowindex+"</td>"+
										"<td> "+processingCode+"</td>"+	
										"<td>"+processingDescription+" </td>"+ 
										"<td> <a class='btn btn-min btn-info' href='#' id='editDat'> <i class='icon-edit icon-white'></i></a>  <a class='btn btn-min btn-danger' href='#' id='deleteemail'> <i class='icon-trash icon-white'></i> </a></td></tr>";
											
									$("#tbody_data1").append(appendTxt);	 
									$('#processingCode').val('');
									$('#processingDescription').val('');
									bankAcctFinalData += "#"+eachrow;
									
									rowindex = ++rowindex;
									colindex = ++ colindex;  
					}  
					 
				});    
			 
			} else {
				return false;
			}
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
						  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
						  <li> <a href="#">Fee Management</a> <span class="divider"> &gt;&gt; </span></li>
						  <li><a href="#"> Register Transaction Processing Code</a></li>
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
								<i class="icon-edit"></i> Register Transaction Processing Code
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
						</div>

					<div id="primaryDetails"  class="box-content">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable " >
								
								<tr class="even">
									<td ><label for="Bin"><strong>Transaction Processing Code<font color="red">*</font></strong></label></td>
									<td><input name="processingCode" type="text" id="processingCode" class="field"  maxlength="6"  value='${responseJSON.processingCode}'></td>
									<td ><label for="processingCode Desc"><strong>Transaction Processing Description<font color="red">*</font></strong></label></td>
									<td><input name="processingDescription" type="text"  id="processingDescription" class="field" value='${responseJSON.processingDescription}'  maxlength="50" ></td>
								</tr>
								<tr class="even">
										<td colspan="4" align="center"><input type="button" class="btn btn-success" name="addCap2" id="addCap2" value="Add" ></input></td>
								</tr>
							</table>
						</fieldset> 
			<input name="subServiceText" type="hidden" id="subServiceText" class="field"  />	 
			<input type="hidden" name="bankMultiData" id="bankMultiData" value="" />
			<fieldset>
				<table width="100%" class="table table-striped table-bordered bootstrap-datatable "
					id="bankAcctData">
					  <thead>
							<tr >
								<th>Sno</th>
								<th>processingCode</th>
								<th>processing Description</th>
								<th>Action</th>
							</tr>
					  </thead>    
					 <tbody  id="tbody_data1">
					 </tbody>
				</table>
			</fieldset>		
										
			</div>
		</div><!--/#content.span10-->
	</div><!--/fluid-row-->  
	<div class="form-actions">
	      <input type="button" class="btn btn-info" type="text"  name="btn-cancel" id="btn-cancel" value="Cancel" width="100" ></input>&nbsp;
		  <input type="button" class="btn btn-success" type="text"  name="btn-submit" id="btn-submit" value="Submit" width="100" ></input>
		 &nbsp;<span id ="error_dlno" class="errors"></span>
	</div>  
</div> 
</form>
</body>
</html>
