
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%> 		
	<style type="text/css">
#fade,#fade1 {
	display: none;
	position: absolute;
	top: 0%;
	left: 15%;
	width: 70%;
	height: 100%;
	background-color: black;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity = 80);
}

#light,#light1 {
	display: none;
	position: absolute;
	top: 0%;
	left: 15%;
	width: 70%;
	height: 100%;
	/* margin-left: -150px;
	margin-top: -100px; */
	/* border: 2px solid #FFF;*/
	background: #FFF; 
	z-index: 1002;
	overflow: visible;
}
</style> 
<script type="text/javascript" > 
  
 $(function(){
	 
	 if($('#application').val()=="Agent"){
			$('#tokenlimit1').css("display","none"); 
			  $('#tokenlimit2').css("display","none"); 
			  $('#tokenlimit3').css("display",""); 
			  $('#tokenlimit4').css("display",""); 
		}else{
			$('#tokenlimit1').css("display",""); 
			  $('#tokenlimit2').css("display",""); 
			  $('#tokenlimit3').css("display","none"); 
			  $('#tokenlimit4').css("display","none"); 
		}
	 
	    /*  var plasticCode = '${responseJSON}'; */
	    /* var binDataList=jQuery.parseJSON('${responseJSON.BINTYPE_LIST}'); */
	    //console.log("ViewDetails"+plasticData.get[0]);
	  
	   var viewBinGroup = '${responseJSON.bingroupcode}';
	   console.log("ViewBinGroup"+viewBinGroup);
	  
 });
 
function viewProduct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/createProductAct.action'; 
	$("#form1").submit();
	return true;
}


var userictadminrules = {
 		
 		rules : {
 			
 			Narration : { required : true } ,
 		},		
 		messages : {
 			
 		
 			Narration : { 
 							required : "Please Enter Message.",
 						}
 			
 		} 
 	};

			
			$(function () {
				
				buildbranchtable();
				
				if("${responseJSON.decision}"=="Reject"){
					$("#decision").val("Reject");
					jQuery("#select2").attr('checked', true);
				}else{
					$("#decision").val("Approval");
					jQuery("#select1").attr('checked', true);
				}
			
				  $("#form1").validate(userictadminrules); 
				
		        $('#btn-submit').live('click', function () { 
		    	  
		    				var url="${pageContext.request.contextPath}/<%=appName %>/AuthProductAck.action"; 
		    				$("#form1")[0].action=url;
		    				$("#form1").submit();
		    	    		
		        }); 
		    });


function decisionradio(v){

				
				$("#decision").val(v);

			}
				

function buildbranchtable()
{

	$("#tbody_data").empty();
	
	var htmlString="";
	var chdata=$("#chdata").val();
	var spidat=chdata.split("\|");
	
	for(i=0;i<spidat.length-1;i++){
		
			htmlString = htmlString + "<tr class='values' id="+i+">";
		
			htmlString = htmlString + "<td id='channellmt' name=channellmt >" + spidat[i].split("#")[0] + "</td>";
			htmlString = htmlString + "<td id='cperdaylmt' name=cperdaylmt >" + spidat[i].split("#")[1] + "</td>";
		
			htmlString = htmlString + "</tr>";
			

	
	}
	
	
	$("#tbody_data").append(htmlString);

}

</script>
	 
</head>
    <s:set value="responseJSON" var="respData"/>    
 
<body>
	<form name="form1" id="form1" method="post" action="">
		
			<div id="content" class="span10"> 
		 
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="createProductAct.action">Product Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">View Product Details</a></li>
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
							<i class="icon-edit"></i>Product Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

						</div>
					</div>  
						
						<div class="box-content" id="primaryDetails"> 
						 <fieldset>  
								<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="even">
										 <td width="20%"><label for="ProductCode"><strong>Product Code</strong></label></td>
										 <td width="30%"> <s:property value='#respData.productCode' /> 
										 <input type="hidden" value="<s:property value='#respData.productCode' />" id="productCode" name="productCode" /> </td>  
										 <td width="20%"><label for="Product Description"><strong>Product Description</strong></label></td>
										 <td width="30%"> <s:property value='#respData.productDesc' /> 
										 <input type="hidden" value="<s:property value='#respData.productDesc' />" id="productDesc" name="productDesc" />
										 </td>  
									</tr>
									<tr class="odd">
										<td ><label for="Product Currency"><strong>Product Currency</strong></label></td>
										<td width="30%"> <s:property value='#respData.binCurrency' /> 
										<input type="hidden" value="<s:property value='#respData.binCurrency' />" id="binCurrency" name="binCurrency" />
										<input type="hidden" id="refno" name="refno"  value="<s:property value='#respData.refno' />" />
										</td>
										<td><label for="Application"><strong>Application</strong></label> </td>
										<td><s:property value='#respData.application' /> 
										<input type="hidden" value="<s:property value='#respData.application' />" id="application" name="application" />
										 </td>
										 
										 <tr class="odd">
										<td  id="tokenlimit1"><label for="Product Currency"><strong>Token Limit Amount</strong></label></td>
										<td  id="tokenlimit2"> <s:property value='#respData.tokenval' /> </td>
										<td ><label for="Product Currency"><strong>Per Day Limit Amount</strong></label></td>
										<td > <s:property value='#respData.perdaylimit' /> </td>
										<td id="tokenlimit3" style="display:none"></td>
									<td id="tokenlimit4" style="display:none"></td>
								
									</tr> 	
									
										 <tr class="odd">
									
										
									  <td ><label for="Fee Charge "><strong>Fee Charge </strong></label></td>
										<td > <s:property value='#respData.feename' /> </td>
										<td></td>
										<td></td>
									</tr> 	
									
									
								</table>
								
								<input type="hidden" value="<s:property value='#respData.actiontype' />" id="actiontype" name="actiontype" />
								<input type="hidden"  id="chdata" name="chdata" value="<s:property value='#respData.channellimit' />" />
								<br>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										
										<th width="25%">Channel</th>
										<th width="25%">Per Day Limit Amount</th>
									
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							</table>	
								</fieldset>  
							</div>
							 
						</div> 
						</div>
						<div class="row-fluid sortable">
							<div class="box span12">
								<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Product Settings
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i
											class="icon-cog"></i></a> <a href="#"
											class="btn btn-minimize btn-round"><i
											class="icon-chevron-up"></i></a>
									</div>
								</div>
						<div class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"
									class="table table-striped table-bordered bootstrap-datatable ">
									
									<tr class="odd" >
									<td colspan="4" ><strong>Decision<font color="red">*</font></strong>&nbsp;&nbsp;
									<input type='radio' onclick='decisionradio(this.value)' id='select1' name='select' value='Approval' checked/><strong>Approval</strong>&nbsp;&nbsp;
									<input type='radio' onclick='decisionradio(this.value)' id='select2' name='select' value='Reject'/><strong>Reject</strong>&nbsp;&nbsp;
									</td>
									
								</tr>
								
								<tr class="even" >
									<td colspan="4" ><div><strong>Message<font color="red">*</font></strong></label></div>
									<textarea rows="10" cols="100" name="Narration" class="field" id="Narration" style="width:500px"  required=true >${responseJSON.Narration}</textarea></td>
									
								</tr>
									
									
								</table>
								<input type="hidden" id="decision" name="decision" value="Approval"/>
								
							</fieldset>
						</div>
						
						
					
						
				</div>		
						
		<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Back" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Confirm" width="100"  ></input> 
			</div>  

	</div>
		   
</form>
</body>
</html>
