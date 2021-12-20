 <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>CEVA </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
	
	
<style type="text/css">
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
  font-weight: bold;
  color: red;
  padding: 2px 8px;
  margin-top: 2px;
}
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
 
</style>    
<script type="text/javascript" >
binDetails = "";
var prdIndex = new Array();

$(document).ready(function() {
	
	var lmtjson =  '${responseJSON.VIEW_LMT_DATA}';
	var feejson =  '${responseJSON.VIEW_FEE_DATA}';
	
	console.log("Welcome to pro");
	
	var finaljsonobj = jQuery.parseJSON(lmtjson);
	var feefinaljsonobj = jQuery.parseJSON(feejson);
	
	// console.log("Limit Json ["+finaljsonobj+"]");
	 // console.log("Fee Json ["+feefinaljsonobj+"]");
	
	buildtable(finaljsonobj,'LMT_TBody');
	//buildtable(feefinaljsonobj,'FEE_TBody');
	

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
			var new_string = (jsonObject.RULE_DESC).replace('AMOUNT','<font color="red">AMOUNT</font>');
			new_string=new_string.replace('DAYS','<font color="red">DAYS</font>');
			new_string=new_string.replace('CHANNEL','<font color="red">CHANNEL</font>');
			new_string=new_string.replace('USING','<font color="red">USING</font>');
			new_string=new_string.replace('HOUR','<font color="red">HOUR</font>');
			new_string=new_string.replace('ACCOUNTNO','<font color="red">ACCOUNTNO</font>');
			new_string=new_string.replace('USERID','<font color="red">USERID</font>');
			new_string=new_string.replace('MOBILE_NUMBER','<font color="red">MOBILE_NUMBER</font>');
			new_string=new_string.replace('COUNT','<font color="red">COUNT</font>');
			htmlString = htmlString + "<td id=RULE_CODE name=RULE_CODE ><input type='radio' name='rulecodes' id='" + i + "' name='" + i + "' onclick=funcc('"+jsonObject.RULE_CODE+"') /></td>";
			htmlString = htmlString + "<td id=RULE_DESC name=RULE_DESC >" + new_string + "<div id='"+jsonObject.RULE_CODE+"' style='display:none'>" + jsonObject.RULE_DESC + "</div></td>";
			
			
			
			htmlString = htmlString + "</tr>";
	
	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

 function createLimitData(){
	 
	    $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/FraudCreationconfigDetails.action';
		$("#form1").submit();
		return true;
 }
 
 function homedir(){
	 
	    $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
		$("#form1").submit();
		return true;
}
 
function funcc(v){
	//alert($("#"+v).text());
	
	$("#rulecode").val(v+"-"+$("#"+v).text());
}
 
		 

 
</script> 
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Fraud Configuration</a></li>
				</ul>
			</div>
			
			
			
								  
           <div class="row-fluid sortable"><!--/span-->
			<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Fraud Monitor Configuration Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				<div class="box-content"> 
					<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  
						id="DataTables_Table_0" >
						<thead>
							<tr>
								<th width="5%">S No</th>
								
								<th width="5%" >Select</th> 
							    <th width="65%">Rule</th>  
								
							</tr>
						</thead> 
						 <tbody id="LMT_TBody">
							   
						</tbody>  
					</table>
					<input type="hidden" id="refno" name="refno"/>
				</div>
			</div>
		</div> 
		
		<input type="hidden"  name="rulecode" id="rulecode"  />
		<input type="button" id="non-printable" class="btn btn-success" onclick="homedir();" value="Back" />
		<input type="button" id="non-printable" class="btn btn-success" onclick="createLimitData();" value="Submit" />
	
				<input type="hidden" id="feeCode" name="feeCode"/> 
				<input type="hidden" id="feeDescription" name="feeDescription"/> 
		<input type="hidden" id="linkmode" name="linkmode"/>
		
		  <script type="text/javascript"> 
			  $(document).ready(function () {
				  binDetails = binDetails.split(",");
					$.each(binDetails, function(indexLink, val) {	
						prdIndex[indexLink]=val;	 
					}); 					
			  }); 
		</script>  
		<s:set value="responseJSON" var="respData"/> 
		<s:set value="%{'_STORES'}" var="searchKey"/> 
		
		<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
		  	<s:param name="jsonData" value="#respData"/>  
 		    <s:param name="searchData" value="#searchKey"/>  
		</s:bean>
		<!-- Loading Stores -->
	 	 
			 
		</div> 
	</div> 
	 

   
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 