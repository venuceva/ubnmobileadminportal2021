<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>ST.PAUL UNIVERSITY</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<script type="text/javascript" > 
$(document).ready(function() { 

	var val = 1;
	var rowindex = 1; 
	var bankfinalData="${bankMultiData}";
	var bankfinalDatarows=bankfinalData.split("#");
	if(val % 2 == 0 ) {
		addclass = "even";
		val++;
	}
	else {
		addclass = "odd";
		val++;
	} 
	
	for(var i=0;i<bankfinalDatarows.length;i++)
	{
		var eachrow=bankfinalDatarows[i];
		var eachfieldArr=eachrow.split(",");
		var professorID=eachfieldArr[0];
		var classid=eachfieldArr[1];

		var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
			 "<td >"+rowindex+"</td>"+ 
			 "<td >"+professorID+"</td>"+
			 "<td >"+classid+"</td>"+
		 "</tr>";
		$("#tbody_data").append(appendTxt);  
		rowindex = ++rowindex;
	}
});

 
function paymentConfirm(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/insertProfessorClassMapDetAct.action';
	$("#form1").submit();
	return true;
}

function back(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/assignProfessiorViewAct.action';
	$("#form1").submit();
	return true;
}
 </script>

<style type="text/css">

.hide
.hide2
{
	display:none;
}

</style> 

</head>

<body>
	<form name="form1" id="form1" method="post" >
		<div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider">&gt;&gt;</span> </li>
 				  <li><a href="#">Class Mapping</a></li>
				</ul>
			</div>
			
		 <div class="row-fluid sortable"> 
			<div class="box span12">
									
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Class Professor Mapping
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					</div>
				</div> 		
				<div class="box-content">
					<fieldset>   
						<input type="hidden" name="bankMultiData" id="bankMultiData" value="${bankMultiData}"></input> 

						<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable " >
							<thead>
								<tr >
									<th  >Sno</th>
									<th   >Professor Id</th>
									<th  >Class Id</th>
									
								</tr>
							</thead>
							 <tbody  id="tbody_data">
							 </tbody>
						</table>
					</fieldset>   
				</div>  
				</div>  			 
			</div>  
		<div class="form-actions">
		&nbsp;<input type="button" class="btn btn-danger" name="save" id="save" value="Cancel" width="100" onClick="back()" ></input>
			<input type="button" class="btn btn-success" type="text"  name="btn-confirm" id="btn-confirm" value="Confirm" width="100" onClick="paymentConfirm()" ></input>
			 							 
		  </div>
	</div> 
 </form>
</body>
</html>
