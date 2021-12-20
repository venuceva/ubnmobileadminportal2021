
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
<%
	String ctxstr = request.getContextPath();
%>
<%
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css" media="screen"
	href='${pageContext.request.contextPath}/css/jquery.cleditor.min.css' />
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

</style>
<SCRIPT type="text/javascript"> 

var billerrules = {
		rules : {
			uploadDisb : {required : true },
			extension: "xls|csv"
		},		
		messages : {
			uploadDisb : { 
				required : "Please select a file to upload." 
			  } 
		},
		errorElement: 'label'
	};

$(document).ready(function(){  

	$( "#dialog-message" ).dialog({
	    	autoOpen: false, 
	    	width : 1000 ,
	    	show: {
	            effect: "blind",
	            duration: 500
	          },
	          hide: {
	            effect: "explode",
	            duration: 500
	          },
	        modal: true,
	        buttons: {
	          Ok: function() {
	            $( this ).dialog( "close" );
	          }
	        }
	      });
	
	
	var textObject = {	
			delay : 300,
			effect : 'replace',
			classColour : 'blue',
			flash : function(obj, effect, delay) {
				if (obj.length > 0) {
					if (obj.length > 1) {
						jQuery.each(obj, function() {
							effect = effect || textObject.effect;
							delay = delay || textObject.delay;
							textObject.flashExe($(this), effect, delay);					
						});
					} else {
						effect = effect || textObject.effect;
						delay = delay || textObject.delay;
						textObject.flashExe(obj, effect, delay);
					}
				}
			},
			flashExe : function(obj, effect, delay) {
				var flash = setTimeout(function() {
					switch(effect) {
						case 'replace':
						obj.toggle();
						break;
						case 'colour':
						obj.toggleClass(textObject.classColour);
						break;
					}
					textObject.flashExe(obj, effect, delay);
				}, delay);
			}
		}; 
	
	
	
	
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
						<i class="icon-edit"></i>Upload Image
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>

						</div>
					</div>

					<div id="uploadgrid" class="box-content">
						<fieldset>

							<table width="98%" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr>
									<td width="20%" colspan=4><s:file name="uploadfile" id="uploadfile"	label="Bulk Upload" /></td>
								</tr>
							</table>
						</fieldset>
					</div>
				</div>
			</div>
			
			
					<div id="secondaryDetails" class="box-content" style="display:none">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td style="text-align: center;"><strong> Image has Been uploaded Successfully and Stored with name <font color="red"> <s:property value='accBean.fullname' />.csv</font> , Please click Proceed to Continue With Further Process</strong></td> 
									</tr> 
								</table>
							<fieldset>
						</div>
						
				<div id="dialog-message" title="Details">
	  <table   class="table table-striped table-bordered bootstrap-datatable " 	id="DataTables_Table_11">
	   	 
		</table>
		 <table   class="table table-striped table-bordered bootstrap-datatable " id="DataTables_Table_12">
						<thead>
					<tr>
						<th>CustomerID/Mobile Number</th>
						<th>Menu Name</th> 
						<th>Message</th> 
					</tr>
				</thead> 
				<tbody id="DataTables-tbody"></tbody>
	   	 
		</table>
		 
	</div> 
	
			<div class="form-actions">
				
				<input type="button" class="btn btn-danger" name="btn-Cancel"	id="btn-Cancel" value="Back" width="100"></input>&nbsp;&nbsp;
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100" style="display:none"></input>&nbsp;&nbsp;
				<input type="button" class="btn btn-info" name="btn-upload"	id="btn-upload" value="Upload" width="100"></input>
					
				<input type="hidden" id="customerStatus" name="customerStatus" value="<s:property value='accBean.customerstatus' />"  />   
				<input type="hidden" id="fullname" name="fullname" value="<s:property value='accBean.fullname' />"  />   
		</div>
	</form>
  <script type="text/javascript">
 
/* $(function(){
	
	function undefinedcheck(data){
		return data == undefined  ? " " :  data;
	}
	var datato = "";
	var st=$('#customerStatus').val();
	var cf=0;
	var mf=0;
	var ter=0;
	
	if (st=="U")
	{
		//v_message
		v_message = "<s:property value='accBean.customerId' />";
		
		console.log("v_message data from action ::::::::::::::::::"+v_message);
		console.log("v_message data from action ::::::::::::::::::"+v_message.length);
		//v_message=undefinedcheck(v_message);
		
		//if(v_message!=" " ){
		if(v_message.length>0 ){
			
		$('#DataTables-tbody').empty();
		$('#DataTables_Table_11').html('');
		var req = undefinedcheck(v_message).split("#");
		
		
		 ter=req.length;
		if(req.length > 0){
			for(var index=0;index<req.length;index++){
				var dat1 = " ";
				var dat2 = " ";
				var dat3 = " ";
				try {
					  dat1 = req[index].split(",")[0];
					  dat2 = req[index].split(",")[1];
					  dat3 = req[index].split(",")[2];
					  if(dat3=="Customer ID Found"){
						 cf=cf+1; 
					  }
					  if(dat3=="Mobile Number Already Exists"){
							 mf=mf+1; 
						  }
					  
				}catch(e){
					
				}
	   				datato+="<tr><td> "+dat1+"</td>  ";
					datato+="<td> "+dat2+"</td><td> "+dat3+"</td> </tr> ";
					
			} 
			//	datato+="<tr><td>Total Customer ID Issues "+cf+"</td><td> Mobile Number Found "+mf+"</td> </tr> ";
			
		}
		
		$('#DataTables-tbody').append(datato);
		
		$( "#dialog-message" ).dialog( "open" );
		console.log("final table created "+datato);
		
//		}
//		else{
			
		
		}
		
		$("#secondaryDetails").css({"display":""});
		$("#statDetails").css({"display":""});
		$("#uploadgrid").css({"display":"none"});
		$("#btn-upload").css({"display":"none"});
		$("#btn-submit").css({"display":""});
	}
		
	$('#is1').text(cf);
	$('#is2').text(mf);
	$('#is0').text(ter);

}); */

</script>
</body>
</html>
