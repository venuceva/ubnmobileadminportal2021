
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
	
	
	if($("#merchanttype").val()=="Lifestyle_Coupon"){
		$("#prdprice").text("Discount Codes");
		$("#quantiy").text("Webpage/Link");
	}
	
	if($("#merchanttype").val()=="Lifestyle"){
		$("#prdprice").text("Product Price");
		$("#quantiy").text("Quantity in Stock");
	}
	
	 var jsoarray = '${responseJSON.FINAL_JSON}';
		
		console.log("jsoarray >>> ["+jsoarray+"]");
		
		 var finaldata =  jQuery.parseJSON(jsoarray);
		 buildbranchtable(finaldata);
		 

			if($("#merchanttype").val()=="Lifestyle Coupon"){
				$("#prdprice").text("Discount Codes");
				$("#quantiy").text("Webpage/Link");
			}
			
			if($("#merchanttype").val()=="Lifestyle"){
				$("#prdprice").text("Product Price");
				$("#quantiy").text("Quantity in Stock");
			}
		 

	$('#billerType').change(function(){
		var bnk = $('#billerType').val();
		if(bnk =='BANK'){
			$('#bnkName').show();
			$('#bankName').show();
		}else{
			$('#bnkName').hide();
			$('#bankName').hide();
		}
	})
  
	$('#btn-submit').live('click',function() {  
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/MerchantGroupAct.action";
		$("#form1").submit();					
	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/addNewMerchantGroup.action";
		$("#form1").submit();					
	}); 
	
});


function buildbranchtable(jsonArray)
{

	$("#tbody_data").empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=Transaction >" + i + "</td>";
			htmlString = htmlString + "<td id='state' name=state >" + jsonObject.state + "</td>";
			htmlString = htmlString + "<td id='city' name=city >" + jsonObject.city + "</td>";	
			htmlString = htmlString + "</tr>";
			

	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$("#tbody_data").append(htmlString);

}

</SCRIPT>  
<s:set value="responseJSON" var="respData"/>
</head> 
<body>
	<form name="form1" id="form1" method="post">
	  <div id="content" class="span10"> 
			 
		    <div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider">&gt;&gt; </span></li>
					<li><a href="#"> Life Style Management</a></li>
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
				 <i class="icon-edit"></i>${responseJSON.merchanttype} Merchant Product Creation Details Confirmation  
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
								<td width="20%"><label for="productprice"><strong><span id="prdprice"></span></strong></label></td>
									<td width="30%">${responseJSON.productprice}
									<input type="hidden" name="productprice" id="productprice" value="${responseJSON.productprice}" /></td>
									<td width="20%"><label for="producteffect"><strong>Product Effect Date</strong></label></td>
									<td width="30%">${responseJSON.producteffect}
									<input type="hidden" name="producteffect"	id="producteffect" value="${responseJSON.producteffect}" /></td>
									
								</tr>
								<tr>
								<td width="20%"><label for="quantitystock"><strong><span id="quantiy"></span></strong></label></td>
									<td width="30%">${responseJSON.quantitystock}
									<input type="hidden" name="quantitystock" id="quantitystock" value="${responseJSON.quantitystock}" /></td>
									<td width="20%"><label for="productdesc"><strong>Product Description</strong></label></td>
									<td width="30%">${responseJSON.productdesc}
									<input type="hidden" name="productdesc" id="productdesc"	value="${responseJSON.productdesc}" /></td>
								
								</tr>
				</table>
				<input type="hidden" name="merchanttype" id="merchanttype"	value="${responseJSON.merchanttype}" required=true />
				<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Product delivery States
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i
										class="icon-cog"></i></a> <a href="#"
										class="btn btn-minimize btn-round"><i
										class="icon-chevron-up"></i></a>
		
								</div>
							</div>
				<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										<th width="10%">Sno</th>
										<th width="40%">State</th>
										<th width="40%">City</th>								
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							 
		</table>
				
				<table width="98%" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr><td width="20%"><strong>Main Image</strong></td>
							<td width="20%" ><strong>Image 1</strong></td>
							<td width="20%"><strong>Image 2</strong></td>
							<td width="20%"><strong>Image 3</strong></td>
							<td width="20%"><strong>Image 4</strong></td>
							</tr>
							<tr><td id="img1" width="20%"><img  src="${responseJSON.imgdata1}"></td>
							<td id="img2" width="20%"><img  src="${responseJSON.imgdata2}"></td>
							<td id="img3" width="20%"><img  src="${responseJSON.imgdata3}"></td>
							<td id="img4" width="20%"><img  src="${responseJSON.imgdata4}"></td>
							<td id="img5" width="20%"><img  src="${responseJSON.imgdata5}">
																		
							
							</td>
							
							</tr>
							</table>
							
							<input type="hidden" name="imgdata1" id="imgdata1" value="${responseJSON.imgdata1}" />
							<input type="hidden" name="imgdata2" id="imgdata2" value="${responseJSON.imgdata2}" />
							<input type="hidden" name="imgdata3" id="imgdata3"  value="${responseJSON.imgdata3}" />
							<input type="hidden" name="imgdata4" id="imgdata4" value="${responseJSON.imgdata4}" />
							<input type="hidden" name="imgdata5" id="imgdata5"  value="${responseJSON.imgdata5}" />
							
							
							<input type="hidden" name="pimg1" id="pimg1" value="${responseJSON.pimg1}"/>
							<input type="hidden" name="pimg2" id="pimg2" value="${responseJSON.pimg2}"/>
							<input type="hidden" name="pimg3" id="pimg3"  value="${responseJSON.pimg3}"/>
							<input type="hidden" name="pimg4" id="pimg4" value="${responseJSON.pimg4}"/>
							<input type="hidden" name="pimg5" id="pimg5"  value="${responseJSON.pimg5}"/>
							<input type="hidden" id="finaljsonarray" name="finaljsonarray" value='${responseJSON.finaljsonarray}'>
				
			</fieldset>  
		</div>
	</div>
	</div> 
	
     	<div class="form-actions" >
           <input type="button" class="btn btn-primary" type="hidden"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>
           &nbsp;<input type="button" class="btn " name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input>
          </div>  
               						 
	</div> 
 </form>
</body>
</html> 