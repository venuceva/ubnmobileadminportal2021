

<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery-jesse.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-jesse.js"></script>
    

   
    
<%
	String ctxstr = request.getContextPath();
System.out.println("context String"+ctxstr);
%>
<%
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<%@taglib uri="/struts-tags" prefix="s"%>

<style type="text/css">
.errors {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
}
li {
    line-height: 0px;
}
#sortable1 {max-width:1000px; margin:20px auto;}
</style>

<script type="text/javascript">
	$(function(){
		$('#sortable1').jesse();
	});
	</script>
    
    
<SCRIPT type="text/javascript"> 

$(document).ready(function(){  

	var arrayData = {'sortable1' : '${responseJSON.IMAGENAMES}'};
	var userdata = "";
	 
		$.each(arrayData, function(selectKey, arrvalue ){
			var json = $.parseJSON(arrvalue);
			var options = '';
			var options1 = '';
	
			
			console.log("parsed json values "+json);
		
			 $.each(json, function(i, v) {
				 
							
							options = $('<li/>', {id:v.key}); 
							$('#'+selectKey).append(options);
							
							console.log('${pageContext.request.contextPath}'+v.key);
							$("ul li#"+v.key).append("<img class='jq-jesse__item' src='${pageContext.request.contextPath}/Uploads/"+v.key+".jpg' />");
		
				 });
			 
			});
		
		
	
	
	$('#btn-submit').on('click', function(){ 
		var finalData = "";
		 var allVals = [];
		 var datato = "";
		
		$('#billerMsg').text('');
		$('#error_dlno').text(''); 
		
		if($("#form1").valid()) {  
	       	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/insertFileData.action';
			$("#form1").submit();
			}
		});
	
	$('#btn-Cancel').live('click',function() {  
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName%>/viewOrganization.action";
		 $("#form1").submit();
 	});
	
	$('#btn-upload').live('click',function() {  
		//$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/uploadimage.action';
		$("#form1").submit();
 	});

 });

 
</SCRIPT>
</head>
<body>
	<form name="form1" id="form1" method="post" autocomplete="off" enctype="multipart/form-data">
		<div id="content" class="span10">

			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider">
							&gt;&gt; </span></li>
					<li><a href="viewOrganization.action">Configure Advertizing Screens</a><span
						class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Configure Advertizing Screens</a></li>
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
						<i class="icon-edit"></i>Image Added Successfully
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>

						</div>
					</div>


				</div>
			</div>
			<ul id="sortable1" class="jq-jesse"></ul>
			
						 <div id="userDetails">
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
									<tr >
								<td ><strong>List Of Services</strong></td>
								</tr>
								<tr class="even">
									<td></td>
								</tr>
								
							</table>
							</br>
				</div>

			<div class="form-actions">
				
				<input type="button" class="btn btn-danger" name="btn-Cancel"	id="btn-Cancel" value="Back" width="100"></input>&nbsp;&nbsp;
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100" style="display:none"></input>&nbsp;&nbsp;
				<input type="button" class="btn btn-info" name="btn-upload"	id="btn-upload" value="Upload" width="100"></input>
		</div>
	</form>

</body>

<script type="text/javascript">



</script>
</html>
