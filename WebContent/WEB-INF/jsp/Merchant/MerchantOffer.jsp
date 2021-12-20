
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">


<script type="text/javascript" src="${pageContext.request.contextPath}/js/image/jquery.canvasCrop.js" ></script>

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String ctxstr = request.getContextPath();
%>
<%
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>

<style type="text/css">
.errors {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}

input#abbreviation {
	text-transform: uppercase
}
;
</style>





<SCRIPT type="text/javascript"> 

var billerrules = {
		rules : {
			productctg : { required : true },
			productsubctg : { required : true },
			productname: { required : true },
			productprice: { required : true },
			producteffect: { required : true },
			quantitystock: { required : true },
			productdesc: { required : true }
		},		
		messages : {
			productctg : { 
				required : "Please select Product Category." 
			  }, 
			productsubctg : { 
					required : "Please select Product Sub Category." 
				},
			productname : { 
					required : "Please Enter Product Name." 
				},
			productprice : { 
					required : "Please Enter Product Price." 
				  }, 
		   producteffect : { 
						required : "Please select Product Effect Date." 
					},
		   quantitystock : { 
						required : "Please Enter Quantity." 
					},
		 productdesc : { 
						required : "Please Enter Product Description." 
					}
		},
		errorElement: 'label'
	};


$(document).ready(function(){   
	
	
	$('#doffer').keypress(function(e)
			{
		
			if (e.which != 8 && e.which != 0 && ((e.which < 48 || e.which > 57) && e.which != 46) ) {
		        e.preventDefault();
		    }
			
			});
	
	var list = "fromdate,enddate".split(",");
	var datepickerOptions = {
					showTime: true,
					showHour: true,
					showMinute: true,
		  		dateFormat:'dd/mm/yy',
		  		alwaysSetTime: false,
		  		yearRange: '1910:2020',
				changeMonth: true,
				changeYear: true
	};
	
	$(function() {
		$(list).each(function(i,val){
			$('.'+val).datepicker(datepickerOptions);
		});
	});
	
	
	
	var d = new Date();

	var month = d.getMonth()+1;
	var day = d.getDate();

	var output =(day<10 ? '0' : '')+ day +'/'+(month<10 ? '0' : '')+ month+'/'+d.getFullYear();
	
	$( "#fromdate" ).datepicker(
			
			{ 
				minDate: '0', 
				beforeShow : function()
				{
					jQuery( this ).datepicker('option','minDate', output );
				},
				altFormat: "dd/mm/yy", 
				dateFormat: 'dd/mm/yy'
				
			}
			
	);

	$( "#enddate" ).datepicker( 
	
			{
				minDate: '0', 
				beforeShow : function()
				{
					jQuery( this ).datepicker('option','minDate', jQuery('#fromdate').val() );
				} , 
				altFormat: "dd/mm/yy", 
				dateFormat: 'dd/mm/yy'
				
			}
			
	);


	

	var config = {
			'.chosen-select' : {},
			'.chosen-select-deselect' : {
				allow_single_deselect : true
			},
			'.chosen-select-no-single' : {
				disable_search_threshold : 10
			},
			'.chosen-select-no-results' : {
				no_results_text : 'Oops, nothing found!'
			},
			'.chosen-select-width' : {
				width : "95%"
			}
		};

		for ( var selector in config) {
			$(selector).chosen(config[selector]);
		}
	
	
	$('#btn-submit').live('click',function() { 
		$("#form1").validate(billerrules); 
 		if($("#form1").valid()) {
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName%>/productofferConfirm.action";
			$("#form1").submit();	
			return true;
		} else {
 			return false;
		} 	
	}); 
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName%>/merchantmenagenet.action";
		$("#form1").submit();
	});

});

function limitper(v){

	
	$("#timelimit").val(v);
	if(v=="Yes"){
		$("#limityes").css("display","");	
	}else{
		$("#limityes").css("display","none");	
	}
}

function offertypes(v){
	$("#offertype").val(v);
	
}

function Selectdis(v){

	
	$("#distype").val(v);
	$("#dd").text(v);
	$("#d1").css("display","");
	$("#d2").css("display","");
	$("#d3").css("display","none");
	$("#d4").css("display","none");
	
} 

	//For Closing Select Box Error Message_End
</SCRIPT>

<s:set value="responseJSON" var="respData"/> 
</head>
<body>
	<form name="form1" id="form1" method="post" autocomplete="off">
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
						<div class="messages" id="messages">
							<s:actionmessage />
						</div>
						<div class="errors" id="errors">
							<s:actionerror />
						</div>
					</td>
				</tr>
			</table>

			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Product Offer Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>

						</div>
					</div>

					<div class="box-content">
						<fieldset>

							<table width="98%" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr>
									<td width="20%"><label for="merchantname"><strong>Merchant Name</strong></label></td>
									<td width="30%">${responseJSON.MERCHANT_NAME}
									<input type="hidden" name="merchantname" id="merchantname" value="${responseJSON.MERCHANT_NAME}" /></td>
									<td width="20%"><label for="merchantid"><strong>Merchant Id</strong></label></td>
									<td width="30%">${responseJSON.MERCHANT_ID}
									<input type="hidden" name="merchantid" id="merchantid" value="${responseJSON.MERCHANT_ID}" /></td>
								</tr>
								<tr>
									<td><label for="productctg"><strong>Product Category</strong></label></td>
									<td >${responseJSON.CATEGORY_DESC}
									<input type="hidden" name="productctg" id="productctg" value="${responseJSON.CATEGORY_DESC}" />
									</td>
		  							<td><label for="productsubctg"><strong>Product Sub-Category</strong></label></td>
									<td>${responseJSON.SUB_CATEGORY_DESC}
									<input type="hidden" name="productsubctg" id="productsubctg" value="${responseJSON.SUB_CATEGORY_DESC}" />
									</td>
								</tr>
								<tr>
									<td width="20%"><label for="productname"><strong>Product Id</strong></label></td>
									<td width="30%">${responseJSON.orgname}
									<input type="hidden" name="productid" id="productid" value="${responseJSON.orgname}" />
									</td>
									<td width="20%"><label for="productprice"><strong>Product Price/Discount Codes</strong></label></td>
									<td width="30%">${responseJSON.PRODUCT_PRICE}
									<input type="hidden" name="productprice" id="productprice" value="${responseJSON.PRODUCT_PRICE}" /></td>
								</tr>
								
								<tr>
									<td width="20%"><label for="offertype"><strong>Select Type<font	color="red">*</font></strong></label></td>
									<td width="30%"><input type="radio" name="type" id="Offer" onclick="offertypes(this.id)" ><strong>  Offer </strong>&nbsp&nbsp&nbsp&nbsp
									<input type="radio" name="type" id="Deal" onclick="offertypes(this.id)" ><strong>  Deal</strong>
									<input type="hidden" name="offertype"	id="offertype" required=true /></td>
									<td width="20%"><label for="timelimit"><strong>is there a time limit ?<font color="red">*</font></strong></label></td>
									<td width="30%">
									<input type="radio" name="limit" id="No" onclick="limitper(this.id)" checked><strong>  No </strong>&nbsp&nbsp&nbsp&nbsp
									<input type="radio" name="limit" id="Yes" onclick="limitper(this.id)"><strong>  Yes</strong>
									
									<input type="hidden" name="timelimit" id="timelimit" value="No" /></td>
								</tr>
								<tr id="limityes" style="display:none">
									<td width="20%"><label for="fromdate"><strong>From Date<font	color="red">*</font></strong></label></td>
									<td width="30%"><input type="text" class="fromdate"  name="fromdate"	id="fromdate" required=true readonly/></td>
									<td width="20%"><label for="enddate"><strong>End Date<font color="red">*</font></strong></label></td>
									<td width="30%"><input type="text" class="enddate" name="enddate" id="enddate" required=true readonly/></td>
								</tr>
								
								<tr>
									<td width="20%"><label for="offertype"><strong>Select <font	color="red">*</font></strong></label></td>
									<td width="30%"><input type="radio" name="Select" id="Discount" onclick="Selectdis(this.id)"><strong>  Discount </strong>&nbsp&nbsp&nbsp&nbsp
									<%-- <input type="radio" name="Select" id="Cashback" onclick="Selectdis(this.id)" ><strong>  Cashback</strong> --%>
									<input type="hidden" name="distype"	id="distype" required=true /></td>
									<td width="20%" id="d1" style="display:none"><label for="timelimit"><strong><span id="dd"></span></strong></label></td>
									<td width="30%" id="d2" style="display:none">
									<input type="text" name="doffer" id="doffer" required=true /></td>
									<td width="20%" id="d3" ></td>
									<td width="30%" id="d4" ></td>
									
								</tr>
								
							
								
								
							</table>
							
						</fieldset>
					</div>
				</div>
			</div>

		
			<div class="form-actions">
				<input type="button" class="btn btn-primary" name="btn-submit"
					id="btn-submit" value="Submit" width="100"></input> &nbsp;<input
					type="button" class="btn " name="btn-Cancel" id="btn-Cancel"
					value="Back" width="100"></input>
			</div>

		</div>
	</form>
	
</body>
</html>
