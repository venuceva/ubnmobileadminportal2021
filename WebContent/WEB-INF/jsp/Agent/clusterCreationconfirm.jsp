<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>IMPERIAL</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%> 
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/datafetchfillinng.js"></script>
 
<script type="text/javascript" > 

function redirectAct()
{
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
	$("#form1").submit();
	return true;
} 

$(function() {
 
	 var jsoarray = '${responseJSON.FINAL_JSON}';
	
	console.log("jsoarray >>> ["+jsoarray+"]");
	
	 var finaldata =  jQuery.parseJSON(jsoarray);
	 
	 
	 buildbranchtable(finaldata);
	 
 
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
			htmlString = htmlString + "<td id='tabchannel' name=tabchannel >" + jsonObject.adminType + "</td>";
			htmlString = htmlString + "<td id='tabservices' name=tabservices >" + jsonObject.adminName + "</td>";
		
			htmlString = htmlString + "</tr>";
			

	});
	
	console.log("Final HtmlString ["+htmlString+"]");
	
	$("#tbody_data").append(htmlString);

}


function confirmAct()
{   

		
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/clusterCreationack.action';
		$("#form1").submit();
		return true;
			
} 



</script>

</head>

<body>
	<form name="form1" id="form1" method="post" action="">
	<div id="content" class="span10">
            			<!-- content starts -->
		<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Branch Creation</a></li>
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
		 
		 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
			
			 
			 
		<div class="row-fluid sortable"><!--/span--> 
			<div class="box span12">  
			
					<div class="box-header well" data-original-title>Branch Creation Configuration Confirm
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>			
			
	<div  class="box-content" >	
	
		<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										<th width="10%">Sno</th>
										<th width="45%">Branch Id</th>
										<th width="45%">Branch Name</th>
									
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							 
		</table>
		
		</div> 
		
		<div class="form-actions" id="submitdata" > 
				
				<input type="hidden" id="finaljsonarray" name="finaljsonarray" value='${responseJSON.FINAL_JSON}'>
				
				<input type="button" id="non-printable" class="btn btn-success" onclick="redirectAct();" value="Home" />
				<input type="button" id="non-printable" class="btn btn-success" onclick="confirmAct();" value="Confirm" />
				
		</div> 
		
		</div>
		
		</div>
		
	</div>
</form>
</body>
</html>
