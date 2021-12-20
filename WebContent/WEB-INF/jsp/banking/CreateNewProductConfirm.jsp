
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

</style>
<script type="text/javascript" >


$(function() {  
	
	
	
	val = 1;
	rowindex = 1;
	colindex = 0;
	bankfinalData="<s:property value='limitData' />";
	
	bankfinalDatarows = "";
	eachfieldArr = "";
	appendTxt = "";
	
	if(bankfinalData != "") {
		var bankfinalDatarows = bankfinalData.split("#");
		
		if(val % 2 == 0 ) {
			addclass = "even"; 
		} else {
			addclass = "odd"; 
		}   
		val++; 
		for(var i=0;i<bankfinalDatarows.length;i++){
			  eachrow=bankfinalDatarows[i];
			  eachfieldArr=eachrow.split(","); 
			  serviceArr=eachfieldArr[1].split("-"); 
			  spackArr=eachfieldArr[0].split("-"); 
			  freqArr=eachfieldArr[2].split("-"); 
			  appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
 				"<td>"+rowindex+"</td>"+
				"<td>"+spackArr[1]+"</td>"+	
				"<td>"+serviceArr[1]+"</td>"+	
				"<td>"+freqArr[1]+"</td>"+	
				"<td>"+(eachfieldArr[3] == 'A' ? 'Amount' : 'Count' )+"</td>"+	
				"<td>"+(eachfieldArr[4] )+"</td>"+	
				"<td>"+eachfieldArr[5]+" </td>"+ 
				//"<td>"+(eachfieldArr[6] == 'P' ? 'Percentile' : 'Flat')+" </td>"+  
				"</tr>";
				$("#tbody_data").append(appendTxt);	  
			rowindex = ++rowindex;
			colindex = ++ colindex; 
		} 
	}
	
	 var selval = "<s:property value='subproductData' />";
	 var selval1 = "<s:property value='limitData' />";
	 var inst = "<s:property value='institute' />";
	 inst=inst.split("-");
	 //alert(inst);
	 $("#institute").val(inst[1]);  
	 
	 //alert(selval);
	 //alert(selval1);
	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/createProductAct.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/createProductActack.action'; 
		$("#form1").submit();
		return true;
	}); 
});

</script>
</head>

<body>
<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Create New Product Confirmation</a>  </li> 
 					</ul>
				</div>  

				<table>
					<tr>
						<td colspan="3">
							<div class="messages" id="messages"><s:actionmessage /></div>
							<div class="errors" id="errors"><s:actionerror /></div>
						</td>
					</tr>
				</table>
<form name="form1" id="form1" method="post"> 
		<div class="row-fluid sortable"> 
			<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Product Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
					
				<div class="box-content">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="Product ID"><strong>Product ID</strong></label></td>
									<td width="30%"><s:property value='productid' /> <input	name="productid" type="hidden" id="productid" value="<s:property value='productid' />" class="field"/></td>
									<%-- <td width="20%"><label for="Product Description"><strong>Product Description</strong></label></td>
									<td width="30%"><s:property value='prddesc' /> <input	name="prddesc" type="hidden" id="prddesc" value="<s:property value='prddesc' />" class="field"/></td>
								</tr>

								<tr class="odd"> --%>
									
									<td width="20%"><label for="Menu Level"><strong>Institute<font color="red">*</font></strong></label></td>
									<td width="30%"><s:property value='institute' /> <input	name="institute" type="hidden" id="institute" class="field"/>
									
									<input type="hidden" name="subproductData" id="subproductData" value="<s:property value='subproductData' />" />
									<input type="hidden" name="limitData"  id="limitData" value="<s:property value='limitData' />"  /> 
									</td>
									<!-- <td><label for="User Id"><strong></strong></label>
									</td>
									<td>
									
									</td> -->
								</tr>

							</table>
				</fieldset> 
				</div>
				</div> 
				
				
		</div> 	
</form>	

<form name="form2" id="form2" method="post"> 	
		<div class="row-fluid sortable" > 
		  <%--	<div class="box span12" > 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Add Sub Product
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
					
					
						  <div class="row-fluid sortable"> 
	
		 		<div class="box-content"> 
							<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
										id="ex-mytable" style="width: 100%;">
							  <thead>
									<tr>
									<th>Sno</th>
									<th>Sub Product ID</th>
									<th>Sub Product Description </th>
									
 									</tr>
							  </thead>    
							  <tbody id="tbody_data1">   
								  <s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{subproductData}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
									</s:bean> 
									<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" > 
											<s:if test="#mulDataStatus.even == true" > 
												<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:if>
											<s:elseif test="#mulDataStatus.odd == true">
												<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
											</s:elseif> 
										
											<td><s:property value="#mulDataStatus.index+1" /></td>
												<s:generator val="%{#mulData}"	var="bankDat" separator="," >  
													<s:iterator status="itrStatus"> 
							                		<td><s:property /></td> 
													</s:iterator>  
												</s:generator> 
 										</tr>  
									</s:iterator> 
							  </tbody>
							</table> 
						</div>  
			</div>
		</div>	--%>
			
	<div class="row-fluid sortable" id="add-new-act"> 
		<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Limits Added
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>  		
			<div class="box-content">
					<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
								id="mytable" style="width: 100%;">
					  <thead>
							<tr>
								<th>Sno</th>
	 							<th>Service Pack</th>
								<th>Service</th>
								<th>Frequency</th>
								<th>Condition</th>
								<th>From Amount/Count</th>
								<th>To Amount/Count</th>
								<!-- <th>F/P</th> -->
							</tr>
					  </thead>    
 					<tbody id="tbody_data"></tbody>
					</table> 
					
					
		 	  </div> 
		 </div>
	 </div>
	 
	 </div>
	 
					

		</form> 



			<div>
				<a class="btn btn-danger" href="#"
					onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp; <a
					class="btn btn-success" href="#" id="btn-submit">Submit</a>
					<span id ="error_dlno" class="errors"></span>
			</div>
			
			
</body>

</html>