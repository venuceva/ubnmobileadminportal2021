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
<link href="<%=ctxstr%>/css/bootstrap-united.css"	rel="stylesheet">
<link href="<%=ctxstr%>/css/bootstrap-responsive.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=ctxstr%>/css/jquery-ui-1.8.21.custom.css" />
<link href="<%=ctxstr%>/css/agency-app.css" rel="stylesheet" />
<link href="<%=ctxstr%>/css/core.css" rel="stylesheet" />
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
.modal-content {
  background-color: #0480be;
}
.modal-body {
  background-color: #fff;
}
</style>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/bootstrap-2.3.2.min.js'></script>

 
<script type="text/javascript" > 


$(document).ready(function(){
	 //$("#rege").val('F');
	$("#btn-submit").hide();
	
	$('#btn-Generate').click(function(){
		 $("#rege").val('R');
		 $("#form")[0].action='<%=request.getContextPath()%>/<%=appName %>/keymgmtAct.action'; 
			$('#form').submit();
			 return true; 
		
	});
	
	$('#btn-Cancel').click(function(){
		
		 $("#form")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
			$('#form').submit();
			 return true; 
		
			 
	});
	
	$('#btn-submit').click(function(){ 
			
		 var locDat = "";
		 var mulDat = "";
		 var locData = [];
		 
		 $('#tbody_data > tr').each(function(ind){  
				 
					 var v_id= '#tbody_data > tr#'+$(this).attr('id');  
					 //alert("vid "+v_id);
					 $(v_id+' > td').each(function(index){
						 //alert();
						 console.log("vid"+v_id); 
						 if(index == 1 ){
							locDat=$(this).text().trim();
						 } else {
							locDat+=","+$(this).text().trim(); 
						}
 				 });  
					 
					locData.push(locDat); 
					 console.log(locData);
		 }); 
		 
		 for(ind=0;ind < locData.length ;ind ++){
			 if(ind ==0){
				 mulDat=locData[ind];
			 }else {
				 mulDat+="#"+locData[ind];
			 }
			 console.log("multi data  [   "+mulDat);
		 } 
		 
		 $("#multiData").val(mulDat);
		console.log("muladasdsadsa"+mulDat);
		
			if($("#form").valid()) { 
 			 $("#form")[0].action='<%=request.getContextPath()%>/<%=appName %>/keyinsact.action'; 
				$('#form').submit();
				 return true;
			} else { 
				return false;
			}
	});
});


$(function() {

	$("#nk").hide();
	
	var val = 0;
	var rowindex = 1;
	var bankfinalData="${accBean.status}";	
	console.log("asdadadasdas    "+bankfinalData);
	var bankfinalDatarows=bankfinalData.split("#");

	var addclass = "";
	var offArr = '${officeLocation}'.split(",");

	for(var i=0;i<bankfinalDatarows.length;i++) {

			if(val % 2 == 0 ){
				addclass = "even";
				val++;
			} else {
				addclass = "odd";
				val++;
			}

		var eachrow=bankfinalDatarows[i];
		console.log("in next page "+eachrow);
		var eachfieldArr=eachrow.split(",");
		var userid=eachfieldArr[2];
		var fname=eachfieldArr[0];
		var lname=eachfieldArr[1];
		var uid=eachfieldArr[3];
		var mobno = eachfieldArr[4];
		//alert(utype);
var regval="${accBean.authStatus}";

if(regval=='R'){
	$("#nk").show();
	$("#btn-submit").show();
		var appendTxt = "<tr class="+addclass+" index='"+rowindex+"' id='"+rowindex+"'> "+
						 "<td>"+rowindex+"</td>"+
						 "<td>"+fname+"</td>"+
						 "<td>"+userid+"</td>"+
						 "<td>"+lname+"</td>"+
						 "<td>"+uid+"</td>"+
						 
		 "</tr>";
}

else{
	var appendTxt = "<tr class="+addclass+" index='"+rowindex+"' id='"+rowindex+"'> "+
	 "<td>"+rowindex+"</td>"+
	 "<td>"+fname+"</td>"+
	 "<td>"+lname+"</td>"+
	 "<td>"+uid+"</td>"+
	 
"</tr>";
}

		$("#tbody_data").append(appendTxt);
		rowindex++;
	}

	

});


</script>

</head>

<body>
	<form name="form" id="form" method="post" action="">
		<div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="#">Key Management</a></li>
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
		
			<div class="box-content">
				<fieldset>
					<table width="100%" class="table table-striped table-bordered bootstrap-datatable "
								id="mytable">
					  <thead>
						<tr>
							<th>Sno</th>
							<th>Channel Name</th>
							<th id="nk" style="display:none">New Key</th>
							<th>Existing Key</th>
							<th>Last Updated</th>
							
						</tr>
					  </thead>
					  <tbody id="tbody_data">
					  </tbody>
					</table>
				</fieldset>
			</div>
			<input type="hidden" name="multiData"  id="multiData" value="<s:property value='multiData' />"  />
			<input type="hidden" name="rege"  id="rege" value="<s:property value='accBean.authStatus' />"/>
			
</form>
<div class="form-actions" >
         <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100" style="display:none"></input>&nbsp;
         <input type="button" class="btn btn-danger" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input>&nbsp;
         <input type="button" class="btn btn-info" name="btn-Generate" id="btn-Generate" value="Re-Generate" width="100" ></input>&nbsp;
         
         <span id ="error_dlno" class="errors"></span>
       </div>  
       </div>
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
</html>
