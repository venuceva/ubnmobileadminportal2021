<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%
	String ctxstr = request.getContextPath();
%>
<%
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
 
<script type="text/javascript">
$(document).ready(function() {
	var data = '${responseJSON.USER_DETAILS}'; 
	
	if (data.indexOf("Registered") > -1)
	{
		$('#errormsg').text(data);
		
		$("#acktbl").css({"display":"none"});
		$("#errtbl").css({"display":""});
		$("#errormsg").css({"display":""});
	
	}
	else {
	var bankfinalDatarows=data.split("#");
	var val = 1;
	for(var i=0;i<bankfinalDatarows.length;i++) {
		var eachrow=bankfinalDatarows[i];
		var eachfieldArr=eachrow.split("~");
		var service=eachfieldArr[0]; 
		var closebalance = eachfieldArr[1]; 
		
		if(val % 2 == 0 ) {
			addclass = "even";
			val++;
		}
		else {
			addclass = "odd";
			val++;
		}  

		var appendTxt = "<tr class="+addclass+"> "+ 
			 "<td class=' '>"+service+"  </td>"+
			 "<td class='active'> <i class='icon icon-check icon-color32'></i> </td>"+ 
		 "</tr>";
		$("#tbody_data").append(appendTxt);  
		//alert("USER IS AUTHORIZED TO GENERATE PASSWORD");
	}
	}
		
	$('#btn-cancel').live('click', function () {  
		var url="${pageContext.request.contextPath}/<%=appName %>/generateMerchantAct.action"; 
		$("#form1")[0].action=url;
		$("#form1").submit();  
	});
		
	$('#btn-submit').live('click', function () {  
		var url="${pageContext.request.contextPath}/<%=appName %>/generateMerchantAct.action"; 
		$("#form1")[0].action=url;
		$("#form1").submit(); 
		 
	}); 
		
}); 
</script> 

</head>
<body>

<form name="form1" id="form1" method="post"> 
	<div id="content" class="span10"> 
		<div> 
			<ul class="breadcrumb">
			 <li><a href="home.action">Home</a> <span class="divider">
							&gt;&gt; </span></li>
					<li><a href="userGrpCreation.action">User Management</a> <span class="divider">
							&gt;&gt; </span></li>
					<li><a href="#">Merchant User Creation Acknowledge</a></li>
			</ul>
		</div>  
		<div class="row-fluid sortable"> 
			<div class="box span12"> 
				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>User Creation Acknowledge
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i
							class="icon-cog"></i></a> <a href="#"
							class="btn btn-minimize btn-round"><i
							class="icon-chevron-up"></i></a>

					</div>
				</div> 	
				
				
				<div class="box-content" id="acktbl">
					<fieldset>
						<table  class="table table-striped table-bordered bootstrap-datatable " 
							id="mytable"  style="width: 100%;">
						  <thead>
								<tr> 
									<th>User Id</th>
									<th>Status</th>
								</tr>
						  </thead>    
						  <tbody id="tbody_data"> 
						  </tbody>
						</table> 
					</fieldset>
				</div> 
				<div id="errtbl" style="display:none">
				<font color="red"><b> User ID <span id='errormsg' style="display:none"></span></b></font>
				</div> 
			</div>
		</div>
		
			<div class="row-fluid sortable"> 
		
					<div id="errtbl" style="display:none"> 
					    <font color="red"><b> User ID <span id='errormsg' style="display:none"></span></b></font>
			</div> 
			
		</div>
		
		<div class="form-actions">
			<input type="button" name="btn-submit" class="btn btn-primary" id="btn-submit" value="Next" />
			&nbsp;<input type="button" name="btn-cancel" class="btn" id="btn-cancel" value="Home"/> 
		</div> 
	</div>  
</form> 
</body>
</html>
