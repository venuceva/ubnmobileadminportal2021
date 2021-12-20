<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>NBK Salary Processing</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%> 
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
</style>
<script type="text/javascript">
function redirectfun()
{
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/dsareconcile.action';
	$("#form1").submit();
	return true; 
	}
	

function radioselect(v,name){
	 $('#errormsg').text("");
	
	 var amt=v.split("-")[1];
	 var crdr=v.split("-")[2];
	 
	  if($('#' + name).is(":checked")){
		 if(crdr=="C"){
			 var vs=$("#selcr").text();
			 $("#selcr").text(parseFloat(amt)+parseFloat(vs)); 
		 }else if(crdr=="D"){
			 var vs=$("#seldr").text();
			 $("#seldr").text(parseFloat(amt)+parseFloat(vs)); 
		 }
	  }else{
		  if(crdr=="C"){
				 var vs=$("#selcr").text();
				 $("#selcr").text(parseFloat(vs)-parseFloat(amt)); 
			 }else if(crdr=="D"){
				 var vs=$("#seldr").text();
				 $("#seldr").text(parseFloat(vs)-parseFloat(amt)); 
			 } 
	  }
	  
	  var selcr=$("#selcr").text();
	  var seldr=$("#seldr").text();
	  $("#selbl").text(parseFloat(seldr)-parseFloat(selcr));
	  
	 
}

function allselect(v){
	 if($('#' + v).is(":checked")){
	 $("#selcr").text($("#selcr1").text());
	 $("#seldr").text($("#seldr1").text());
	 $("#selbl").text($("#selbl1").text());
	 
	 }else{
		 $("#selcr").text("0");
		 $("#seldr").text("0");
		 $("#selbl").text("0"); 
		
	 }
}

$(function () { 
	
	$("#ckbCheckAll").click(function () {
		$(".checkBoxClass").attr('checked', this.checked);
    });
	
	

	$('#btn-submit').live('click', function () { 
		

	  var val=false;
	  var ckval="";
		 $(':checkbox:checked').each(function(i){
			 val=true;
			 
			 if($(this).val()=="0000" && i==0){
				 val=false; 
			 }else{
				 val=true; 
			 }
			 
			if(i==0){
				ckval=(($(this).val()).split("-")[0]);	
			}else{
				
			 ckval=ckval+","+(($(this).val()).split("-")[0]);	
		 	}
	       });
		 $("#rno").val(ckval);
		//alert(ckval);
		 if(val==true){
			 $("#vselcr").val($("#selcr").text());
			 $("#vseldr").val($("#seldr").text());
			 $("#vselbl").val($("#selbl").text());
			
			  $('#btn-submit').attr("disabled", "disabled");
			   $('#errormsg').text("Please Wait .....");
				var url="${pageContext.request.contextPath}/<%=appName %>/dsareconciledetailsconf.action"; 
				$("#form1")[0].action=url;
				$("#form1").submit(); 
				  
		 }else{
			 $('#errormsg').text("Please Select Atleast One Check box");	
			 }	
	}); 
});


	
</script>


<s:set value="responseJSON" var="respData"/> 
<body class="fixed-top">
<form name="form1" id="form1" method="post" >
	
      <div id="content" class="span10"> 
	    <div> 
			<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="dsareconcile.action">Teller Reconcile</a> </li>
				</ul>
		</div>  
	 	<table height="3">
				 <tr>
					<td colspan="3">
						<div class="messages" id="messages"> <s:actionmessage /></div>
						<div class="errors" id="errors"> <s:actionerror /></div>
						<div id="errormsg" class="errores"></div>
			 			<div  class="screenexit"></div>
						
					</td>
				</tr>
			 </table> 
				
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" >
					<i class="icon-edit"></i>User Details 
					
					
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
		
      				 
		<div class="box-content">
		
								
								<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
								<td width="20%"><label for="User Id"><strong>User Id</strong></label></td>
								<td width="30%"><s:property value='#respData.dsaReconcileDetails.USERID' />
								<input name="userid"  type="hidden"  id="userid"  value="<s:property value='#respData.dsaReconcileDetails.USERID' />"    />
								</td>
								 <td width="20%"><label for="User Name"><strong>User Name</strong></label></td>
								 <td width="30%"><s:property value='#respData.dsaReconcileDetails.DISPLAYNAME' /> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Branch Details"><strong>Branch Details</strong></label></td>
								<td width="30%"><s:property value='#respData.dsaReconcileDetails.BRANCH_DETAILS' /></td>
								 <td width="20%"><label for="Mobile Number"><strong>Mobile Number</strong></label></td>
								 <td width="30%"><s:property value='#respData.dsaReconcileDetails.MOBILE_NO' /> </td>
							</tr>
							</table>
									<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
								<td width="50%" colspan="2"><label for="Credit" align="center"><strong>Reconcile Transaction</strong></label></td>
								<td width="50%" colspan="2"><label for="Credit" align="center"><strong>Selected Transaction</strong></label></td>
								</tr>
								<tr class="even">
								<td width="20%"><label for="Credit"><strong>Total Cash Withdrawal ( &#8358; ) </strong></label></td>
								<td width="30%"><div id="selcr1"><s:property value='#respData.dsaReconcileDetails.CREDIT' /></div></td>
								<td width="20%"><label for="Credit"><strong>Total Cash Withdrawal ( &#8358; ) </strong></label></td>
								<td width="30%"><div id="selcr">0</div></td>
								</tr>
							<tr class="even">
								
								 <td width="20%"><label for="Debit"><strong>Total Cash Deposit ( &#8358; ) </strong></label></td>
								 <td width="30%"><div id="seldr1"><s:property value='#respData.dsaReconcileDetails.DEBIT' /></div> </td>
								  <td width="20%"><label for="Debit"><strong>Total Cash Deposit ( &#8358; ) </strong></label></td>
								 <td width="30%"><div id="seldr">0</div> </td>
							</tr>
							<tr class="even">
								<td width="20%"><label for="Branch Details"><strong>Balance ( &#8358; ) </strong></label></td>
								<td width="30%"><div id="selbl1"><s:property value='#respData.dsaReconcileDetails.BALANCEAMOUNT' /></div></td>
								<td width="20%"><label for="Branch Details"><strong>Balance ( &#8358; ) </strong></label></td>
								<td width="30%"><div id="selbl">0</div></td>
							</tr>
							</table>	
							<input name="vselcr"  type="hidden"  id="vselcr"  value="0"    />
							<input name="vseldr"  type="hidden"  id="vseldr"  value="0"    />
							<input name="vselbl"  type="hidden"  id="vselbl"  value="0"    />
                
                        </div>
                   

						   </div>	
						   
						   
			  </div>
			  
			  	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" >
					<i class="icon-edit"></i>   User Transaction Details 
					
					
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 
		
      				 
		<div class="box-content">
		
								
								<table width='100%' class="table table-striped table-bordered bootstrap-datatable "  id="DataTables_Table_0" >
						<thead>
							<tr>
							<th><input type='checkbox' id='ckbCheckAll' name='ckbCheckAll' value='0000' onclick="allselect(this.id)"></th>
								<th>Payment Reference No</th>
								<th>Account No </th>
								<th>Amount</th>
								<th>Credit / Debit </th>
								<th>Suspense Account No</th>
								<th>Service Name</th>
								<th>Channel</th>
								<th>Transaction Date</th>
								
								
							</tr>
						</thead> 
						 <tbody id="binTBody">
							  <s:iterator value="responseJSON['dsaReconcileDetails2']" var="userGroups" status="userStatus"> 
								<s:if test="#userStatus.even == true" > 
									<tr class="even" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
								 </s:if>
								 <s:elseif test="#userStatus.odd == true">
      								<tr class="odd" index="<s:property value='#userStatus.index' />"  id="<s:property value='#userStatus.index' />">
   								 </s:elseif> 
									<%-- <td><s:property value="#userStatus.index+1" /></td> --%>
									<!-- Iterating TD'S -->
									  <s:iterator value="#userGroups" status="status" > 
									   <s:if test="#status.index == 7" >  
											<td><input type='checkbox' class='checkBoxClass' onclick='radioselect(this.value,this.name)' id='<s:property value="#userStatus.index+1" />' name='<s:property value="#userStatus.index+1" />' value='<s:property  value="value" />' /> </td> 											
										</s:if>
									  <s:if test="#status.index == 8" >  
											<td><s:property  value="value" /></td> 											
										</s:if>
										<s:if test="#status.index == 9" >  
											<td><s:property  value="value" /></td> 											
										</s:if>
										 <s:elseif test="#status.index == 10" >
											 <td ><s:property  value="value"  /></td>
										 </s:elseif> 
										 
 										 <s:elseif test="#status.index == 11" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 <s:elseif test="#status.index == 12" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 13" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										  <s:elseif test="#status.index == 14" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 <s:elseif test="#status.index == 15" >
											 <td ><s:property value="value" /></td>
										 </s:elseif>
										 
									</s:iterator>  
									
 								  
							</s:iterator>  
						</tbody>  
					</table>
								
							
                
                        </div>
                   

						   </div>	
						   
						   
			  </div>
			  
			<input type="hidden" id="rno" name="rno"  />
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectfun();" value="Back" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100"  ></input> 
			</div>  
			</div>
		  
		  </form>	
</body>
</html>
<!-- END PAGE -->