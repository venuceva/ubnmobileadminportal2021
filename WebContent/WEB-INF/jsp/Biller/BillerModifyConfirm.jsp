
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 <%@taglib uri="/struts-tags" prefix="s"%> 
<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
</style>
<SCRIPT type="text/javascript">  
$(document).ready(function(){ 
	

	/* $('#billerType').change(function(){
		var bnk = $('#billerType').val();
		if(bnk =='BANK'){
			$('#bnkName').show();
			$('#bankName').show();
		}else{
			$('#bnkName').hide();
			$('#bankName').hide();
		}
	}) */
	
	
  
	$('#btn-submit').live('click',function() {  
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/billerRegModifyConfirmAct.action";
		$("#form1").submit();					
	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/newPayBillAct.action";
		$("#form1").submit();					
	}); 
	
});
</SCRIPT>  

</head> 
<body>
	<form name="form1" id="form1" method="post">
	  <div id="content" class="span10"> 
			 
		    <div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider">&gt;&gt; </span></li>
					<li><a href="#">Biller Registration Modify</a></li>
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
		 	
	<div class="row-fluid sortable"> 
		<div class="box span12">  
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>Biller Category Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					
				</div>
			</div>
						
			<div class="box-content">
				<fieldset>
					 
				<table width="98%"  border="0" cellpadding="5" cellspacing="1" 
					class="table table-striped table-bordered bootstrap-datatable " > 
					<tr>
									<td width="20%"><label for="Abbreviation"><strong>Services</strong></label></td>
									<td width="30%"> ${responseJSON1.servicetype}
										<input type="hidden" name="servicetype"	id="servicetype"	value="${responseJSON1.servicetype}"	 />
									</td>
									<td width="20%"><label for="Code"><strong>Services Category</strong></label></td>
									<td width="30%"> ${responseJSON1.subservicecat} <input type="hidden" name="subservicecat"
										id="subservicecat"
										value="${responseJSON1.subservicecat}"	/></td>
								</tr>
						<tr>
							<td width="20%" ><label for="Abbreviation"><strong>Biller Category Name</strong></label></td>
							<td width="30%" >  ${responseJSON1.abbreviation} 
							<input type="hidden" name="abbreviation"  id="abbreviation" value=" ${responseJSON1.abbreviation}"  /></td> 
							<td width="20%" ><label for="Code"><strong>Biller Category Id</strong></label></td>
							<td width="30%" > ${responseJSON1.billerCode} <input type="hidden" name="billerCode"  id="billerCode"  value="${responseJSON1.billerCode}"   /></td>							
						</tr>
					  	<tr> 
							<td><label for="Name"><strong>Description</strong></label></td>
							<td >  ${responseJSON1.name} <input type="hidden" name="name"  id="name"   value="${responseJSON1.name}"  width="400px"  />   </td>
							<td></td>
							<td>
							</td> 												 
						</tr>
				</table>
			</fieldset>  
		</div>
	</div>
	</div> 
	
	<%-- <div class="row-fluid sortable"> 
		<div class="box span12">  
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>Contact Details  
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					
				</div>
			</div>
						
			<div class="box-content">
				<fieldset>
					 
				<table width="98%"  border="0" cellpadding="5" cellspacing="1" 
					class="table table-striped table-bordered bootstrap-datatable " > 
						<tr> 
							<td width="20%" ><label for="Address"><strong>Address</strong></label></td>
							<td width="30%">${responseJSON1.address} <input type="hidden" name="address"  id="address"   value="${responseJSON1.address}"    />   </td>  
							
						</tr>
					  	<tr> 
							<td><label for="Telephone"><strong>Telephone</strong></label></td>
							<td colspan=3>${responseJSON1.telephone} 
							<input type="hidden" name="telephone"  id="telephone"   value="${responseJSON1.telephone}"    />   </td>  
							 
						</tr>
						<tr> 
							<td><label for="Contact Person"><strong>Contact Person</strong></label></td>
							<td colspan=3> 
								${responseJSON1.contactPerson} 
								<input type="hidden" name="contactPerson"  id="contactPerson"   value="${responseJSON1.contactPerson}"    />   
							</td>
						</tr>
						<tr>
							<td><label for="Email"><strong>Email</strong></label></td>
							<td colspan=3> 
								${responseJSON1.email} <input type="hidden" name="email"  id="email"   value="${responseJSON1.email}"    />   
							</td>
						</tr> 
				</table>
			</fieldset>  
		</div>
	</div>
	</div> 
	
	<div class="row-fluid sortable"> 
		<div class="box span12">  
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>Commissions
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>					
				</div>
			</div>
						
			<div class="box-content">
				<fieldset>					 
				<table width="98%"  border="0" cellpadding="5" cellspacing="1" 
					class="table table-striped table-bordered bootstrap-datatable " >
						<tr > 							 
							<td width="20%" ><label for="Commission Type"><strong>Commission Type</strong></label></td>
							<td width="30%">  ${responseJSON1.commissionType}
							 <input type="hidden" name="commissionType"  id="commissionType"   value="${responseJSON1.commissionType}"/>    </td>
						 <td width="20%" >&nbsp;</td>
						 <td width="30%" >&nbsp;
						 	<input type="hidden" name="amount"  id="amount"   value="${responseJSON1.amount}"/>
						 	<input type="hidden" name="rate"  id="rate"   value="${responseJSON1.rate}"   />  </td>
						</tr> 
						 
						<s:set value="billerBean.commissionType" var="commType"/> 
						
						<s:if test="#commType == 'Amount' " > 
							<tr> 
								<td ><label for="Amount"><strong>Amount</strong></label></td>
								<td colspan="3">${responseJSON1.amount}    </td>  
							</tr>
						</s:if>
						<s:else>
   							<tr> 
								<td><label for="Name"><strong>Rate</strong></label></td>
								<td colspan="3">${responseJSON1.rate}    </td>  
							</tr>  
						</s:else>  
				</table>
			</fieldset>  
		</div>
	</div>
	</div> --%>
     	<div class="form-actions" >
           <input type="button" class="btn btn-primary" type="hidden"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>
           &nbsp;<input type="button" class="btn " name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input>
          </div>  
               						 
	</div> 
 </form>
</body>
</html> 