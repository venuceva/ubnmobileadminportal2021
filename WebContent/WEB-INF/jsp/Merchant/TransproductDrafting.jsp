
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
	

	$('#btn-Cancel').live('click',function() {  
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/lifestyletracking.action";
		$("#form1").submit();					
	}); 
	
	$('#btn-Submit').live('click',function() {  
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/lifestyletrackingcofirm.action";
		$("#form1").submit();					
	}); 
	
});
</SCRIPT>  
<s:set value="responseJSON" var="respData"/>
</head> 
<body>
	<form name="form1" id="form1" method="post">
	  <div id="content" class="span10"> 
			 
		    <div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider">&gt;&gt; </span></li>
					<li><a href="#">LifeStyle Tracking</a></li>
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
				 <i class="icon-edit"></i>Order Drafting Confirmation  
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
									<td width="20%"><label for="merchantname"><strong>Merchant Name</strong></label></td>
									<td width="30%">${responseJSON.merchantname}
									<input type="hidden" name="merchantname" id="merchantname" value="${responseJSON.merchantname}"   /></td>
									<td width="20%"><label for="merchantid"><strong>Merchant Id</strong></label></td>
									<td width="30%">${responseJSON.merchantid}
									<input type="hidden" name="merchantid" id="merchantid" value="${responseJSON.merchantid}"  /></td>
								</tr>
								<tr>
									<td><label for="productctg"><strong>Product Category</strong></label></td>
									<td >${responseJSON.productctg}
									<input type="hidden" name="productctg" id="productctg"	value="${responseJSON.productctg}"  />
									</td>
		  							<td><label for="productsubctg"><strong>Product Sub-Category</strong></label></td>
									<td>${responseJSON.productsubctg}
								<input type="hidden" name="productsubctg" id="productsubctg"	value="${responseJSON.productsubctg}"  />
									</td>
								</tr>
								<tr>
									<td width="20%"><label for="productname"><strong>Product Name</strong></label></td>
									<td width="30%">${responseJSON.productname}
									<input type="hidden" name="productname" id="productname"	value="${responseJSON.productname}"  /></td>
									<td width="20%"><label for="productid"><strong>Product Id</strong></label></td>
									<td width="30%">${responseJSON.productid}
									<input type="hidden" name="productid" id="productid"	value="${responseJSON.productid}" /></td>
									
									
								</tr>
								
								<tr>
								<td width="20%"><label for="productprice"><strong>Product Price</strong></label></td>
									<td width="30%">${responseJSON.productprice}
									<input type="hidden" name="productprice" id="productprice" value="${responseJSON.productprice}" /></td>
									<td width="20%"><label for="producteffect"><strong>Offer Price</strong></label></td>
									<td width="30%">${responseJSON.offerprice}
									<input type="hidden" name="offerprice"	id="offerprice" value="${responseJSON.offerprice}" /></td>
									
								</tr>
								<tr>
								<td width="20%"><label for="quantitystock"><strong>Quantity</strong></label></td>
									<td width="30%">${responseJSON.quantity}
									<input type="hidden" name="quantity" id="quantity" value="${responseJSON.quantity}" /></td>
									<td width="20%"><label for="productdesc"><strong>Txn Amount</strong></label></td>
									<td width="30%">${responseJSON.txnamount}
									<input type="hidden" name="txnamount" id="txnamount"	value="${responseJSON.txnamount}" /></td>
								
								</tr>
								<tr>
								<td width="20%"><label for="quantitystock"><strong>Status</strong></label></td>
									<td width="30%">${responseJSON.deliverystatus}
									<input type="hidden" name="deliverystatus" id="deliverystatus" value="${responseJSON.deliverystatus}" /></td>
									<td width="20%"><label for="productdesc"><strong>Order Date</strong></label></td>
									<td width="30%">${responseJSON.txndate}
									<input type="hidden" name="txndate" id="txndate"	value="${responseJSON.txndate}" /></td>
								
								</tr>
									<tr>
								<td width="20%"><label for="quantitystock"><strong>User Id</strong></label></td>
									<td width="30%">${responseJSON.userid}
									<input type="hidden" name="userid" id="userid" value="${responseJSON.userid}" /></td>
									<td width="20%"><label for="productdesc"><strong>Account Number</strong></label></td>
									<td width="30%">${responseJSON.accountno}
									<input type="hidden" name="accountno" id="accountno"	value="${responseJSON.accountno}" /></td>
								
								</tr>
								<tr>
								<td width="20%"><label for="quantitystock"><strong>payment Reference No</strong></label></td>
									<td width="30%">${responseJSON.payrefno}
									<input type="hidden" name="payrefno" id="payrefno" value="${responseJSON.payrefno}" /></td>
									<td width="20%"><label for="productdesc"><strong>Mobile Numer</strong></label></td>
									<td width="30%">${responseJSON.mobnumber}
									<input type="hidden" name="mobnumber" id="mobnumber"	value="${responseJSON.mobnumber}" /></td>
								
								</tr>
								<tr>
								<td ><label for="quantitystock"><strong>Deliver Address</strong></label></td>
									<td >${responseJSON.deliveradd}
									<input type="hidden" name="deliveradd" id="deliveradd" value="${responseJSON.deliveradd}" /></td>
									<td width="20%"></td>
									<td width="30%"></td>
								</tr>
								
								
				</table>
				
				<input type="hidden" name="actionmap" id="actionmap"   value="drafting" />	
							
				
			</fieldset>  
		</div>
	</div>
	</div> 
	
     	<div class="form-actions" >
           
          <input type="button" class="btn btn-success" name="btn-Cancel" id="btn-Cancel" value="Next" width="100" ></input> &nbsp;
           <input type="button" class="btn btn-success" name="btn-Submit" id="btn-Submit" value="Confirm" width="100" ></input>
          </div>  
               						 
	</div> 
 </form>
</body>
</html> 