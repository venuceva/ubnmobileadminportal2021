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
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<s:head />
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
	
	var productRules = {
			   rules : {
				   'agent.accountNumbers' : { required : true, regexchk: /^([0-9]{10,10})$/i }
			   },
			   messages : {
				   'agent.accountNumbers' : {
				   required : "Please enter Super Agent Account Number.",
				   regexchk :   "Required 10 Numeric digits."
					}
			   }
			 };

	/* $('#super-agent-creation').click(function(){
		$('#list').hide();
		$('#create').show();
		$('#super-agent-creation').hide();
	}); */
	$.validator.addMethod("regexchk", function(value, element, regexpr) {
		 return this.optional(element) || regexpr.test(value);
	   }, "");
	
	
	$('#srchcriteria').on('change', function (e) {
		 var optionSelected = $("option:selected", this);
		    var valueSelected = this.value;
		    
		    if(valueSelected!=""){
		    	
		    	 if(valueSelected =='DIRECT')	
	    		 {
			    		 $("#agenttypeacc").show();
	    		 }
				else if(valueSelected =='INDIRECT')
				{	
			 			$("#agenttypeacc").hide();
				}
		    }
	});

	$('#btn-back').click(function(){
		$('#list').show();
		$('#create').hide();
		$('#super-agent-creation').show();
	});

		$('#btn-submit').live('click', function () {
			/* $("#form1").validate(productRules);
			console.log($("#form1").validate(productRules));
			$('#errormsg').text(""); */
			
				
				/* $('#errormsg1').text("Please Wait Account Number verified from Core Bank");	
				 var queryString = "entity=${loginEntity}&method=validateaccount&accountno="+$('#accountNumbers').val();
			 		
					$.getJSON("postJson.action", queryString,function(data) { 
						//alert(data.finalCount);
						if(data.finalCount==0){ */
							var url="${pageContext.request.contextPath}/<%=appName %>/superagentconfirm.action";
							$("#form1")[0].action=url;
							$("#form1").submit();
							 return true;
						/* }else if(data.finalCount==100){
							$('#errormsg').text("Account Number already exist in Agency Banking"); 
						}else{
							$('#errormsg').text("Invalid Account Number");	
						 } 
						$('#errormsg1').text("");	
					});*/
				
			

		});

});

var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);
var linkIndex = new Array();
var linkaction = new Array();
var linkStatus = new Array();

var num="";

$(document).ready(function () { 
	
	
	
	$.each(jsonLinks, function(index, v) {
	linkIndex[index] = index;
	linkaction[index] = v.name;
	linkStatus[index] = v.status;
	
	$("#"+v.name+"1").removeAttr("disabled");
	$("#"+v.name+"1").attr("id", v.name);
	
	num=index;
	

	
});  
}); 



var val = 1;
var rowindex = 0;
var colindex = 0;
var bankAcctFinalData="";
var binstatus = "";
var v_message = "";


$(document).ready(function() {
	
	var lmtjson =  '${responseJSON.VIEW_AGNT_DATA}';
	
	console.log("Welcome to pro");
	
	var finaljsonobj = jQuery.parseJSON(lmtjson);
		
	buildtable(finaljsonobj,'LMT_TBody');
	
	
});



function buildtable(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	
	var htmlString="";
	$.each(jsonArray, function(index,jsonObject){
		
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=sno >" + i + "</td>";
			htmlString = htmlString + "<td id=UBNAGENTID name=UBNAGENTID >" + jsonObject.UBNAGENTID + "</td>";
			htmlString = htmlString + "<td id=ACCOUNTNAME name=ACCOUNTNAME >" + jsonObject.ACCOUNTNAME + "</td>";
			htmlString = htmlString + "<td id=TELCO_TYPE name=TELCO_TYPE >" + jsonObject.TELCO_TYPE + "</td>";
			htmlString = htmlString + "<td id=PRODUCT name=PRODUCT >" + jsonObject.PRODUCT + "</td>";
			htmlString = htmlString + "<td id=EMAIL name=EMAIL >" + jsonObject.EMAIL + "</td>";
			if( jsonObject.STATUS == 'A') {
			htmlString = htmlString + "<td id=STATUS name=STATUS  ><div class='label label-success'>Active</div></td>";
			}else{
			htmlString = htmlString + "<td id=STATUS name=STATUS ><div  class='label label-info' >Deactive</div></td>";	
			}
			
				htmlString = htmlString + "<td id='' ><a class='btn btn-success' href='#' id='agentview_"+i+"' index="+i+" val="+i+" title='View Details' data-rel='tooltip'  disabled> <i class='icon icon-book icon-white'></i></a>";
				htmlString = htmlString + "&nbsp&nbsp<a class='btn btn-danger' href='#' id='agentstatus_"+i+"' index="+i+" val="+i+" title='Active OR Deactive' data-rel='tooltip'  disabled> <i class='icon icon-lock icon-white'></i></a></td>";

			htmlString = htmlString + "</tr>";
			
			
			 
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	$('#'+divid).append(htmlString);
	

	for(ii=0;ii<=num;ii++){
		for(j=1;j<=i;j++){
			var v=linkaction[ii]+"_"+(j);
			$('#'+v).removeAttr("disabled");
			$('#'+v).attr("id", linkaction[ii]);
		}
		
		 
	 }  
}

 function createLimitData(myaction){
	 $('#linkmode').val("NEW");
	    $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/'+myaction+'.action';
		$("#form1").submit();
		return true;
 }
 

$(document).on('click','a',function(event) {
	
    var $row = jQuery(this).closest('tr');
    var $columns = $row.find('td');

    $columns.addClass('row-highlight');
    var values = "";
     var btn=this.id;
    
    jQuery.each($columns, function(i, item) {
    	
    	if(i==0)
    		{
    		values =  item.innerHTML;
    		}else{
    			values = values +"$"+ item.innerHTML;
    		}
    });
   
  	
	var val = values;
	var code = "";
	if(val.match("$"))
		{
		var x = val.split("$");
		code = x[1];
		
		}

	if(btn == 'agentview')
	{
		$('#accNumbers').val(code);
		$('#cmd').val('VIEW');
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/superView.action';
		$("#form1").submit();
			return true; 
	}else if(btn == 'agentstatus')
	{
		$('#cmd').val('ACTIVEDEACTIVE');
		$('#accNumbers').val(code);
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/activedeactiveview.action';
		$("#form1").submit();
			return true; 
	}else if(btn == 'super-agent-creation')
	{
		$('#list').hide();
		$('#create').show();
		$('#super-agent-creation').hide();
	}
	
    
}); 
		 


</script>

</head>
<body>
	<form name="form1" id="form1" method="post" action="">
		<div class="span10" id="create" style="display:none;">
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="superagent.action">Super Agent Management</a> <span class="divider"> &gt;&gt; </span></li>
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 <div class="row-fluid sortable"><!--/span-->
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Super Agent Management
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							</div>
						</div>
						
						<div id="primaryDetails" class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd" id="agenttype">
										<td width="20%"><strong><label for="Account Number">Super Agent Type<font color="red">*</font></label></strong></td>
										<td width="30%">
										 <s:select cssClass="chosen-select" 
								         headerKey="" 
								         headerValue="Select"
								         list="#{'DIRECT':'Direct Super Agent','INDIRECT':'Indirect Super Agent'}" 
								         name="srchcriteria" 
								         id="srchcriteria"
								         requiredLabel="true" 
								         theme="simple"
								         data-placeholder="Choose Account Type..." 
								           />
										
										</td>
										<td></td>
										<td></td>
									</tr>
									<tr class="odd" id="agenttypeacc" style="display:none">
										<td width="20%"><strong><label for="Account Number">Account Number<font color="red">*</font></label></strong></td>
										<td width="30%"><input type="text" id="accountNumbers" class="field" name="agent.accountNumbers" value='${product.accountNumbers}' maxlength="45" required="true"></td>
										<td></td>
										<td></td>
									</tr>
								</table>
							 </fieldset>
							 <div id="errormsg1" ></div>
							</div>
						</div>
					</div> 
			<span id="multi-row-data" name="multi-row-data" class="multi-row-data" style="display:none" ></span>
			<div class="form-actions">
			<a href="#" type="button" class="btn btn-danger" id="btn-back"/>Back</a>&nbsp;
				<a href="#" class="btn btn-success" id="btn-submit"/>Submit</a>
				
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div>
	</div>
	<div class="span10" id="list" >
		<div>
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="superagent.action">Super Agent Management</a> <span class="divider"> &gt;&gt; </span></li>
			</ul>
		</div>
		<div class="row-fluid sortable"><!--/span-->
		
		<a href="#" class="btn btn-success" id="super-agent-creation1"    title='Create New Super Agent' data-rel='popover'  data-content='Create New Super Agent'  disabled><i class='icon icon-plus icon-white'></i>&nbsp;Create New Super Agent</a> &nbsp;					
			<div class="row-fluid sortable">
				
				
				<div class="box span12">
					<div class="box-header well" data-original-title>
						Create Super Agent 
					</div>
				
				<div class="box-content">
					<fieldset>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable" id="products"  >
							  <thead>
							<tr>
								<th>S No</th>
								<th>Super Agent Id</th>
								<th>Name</th>
								<th>Telco Type</th>
								<th>Product</th>
								<th>Email</th>
							    <th>Status </th>  
								<th>Actions</th>
							</tr>
						</thead> 
						 <tbody id="LMT_TBody">
							   
						</tbody>  
						</table>
					</fieldset>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="email" id="email">
	<input type="hidden" name="accountNumbers" id="accountNumbers" >
	<input type="hidden" name="accNumbers" id="accNumbers" >
	<input type="hidden" name="mobile" id="mobile">
	<input type="hidden" name="status" id="status">
	<input type="hidden" name="cmd" id="cmd"/>
</div>
</form>
<script type="text/javascript">
$(function(){
	
	var config = {
      '.chosen-select'           : {},
      '.chosen-select-deselect'  : {allow_single_deselect:true},
      '.chosen-select-no-single' : {disable_search_threshold:10},
      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chosen-select-width'     : {width:"95%"}
    };
	
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }  
});  
</script>
</body>

<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</html>
