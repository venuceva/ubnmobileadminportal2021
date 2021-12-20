<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%> 
<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">

<script type="text/javascript">



var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);
var linkIndex = new Array();
var linkaction = new Array();
var linkStatus = new Array();
var linkname = new Array();
var linpid = new Array();
var menulist = new Array();
var vpid="";

$(document).ready(function () { 
		$.each(jsonLinks, function(index, v) {
		linkIndex[index] = index;
		linkaction[index] = v.name;
		linkStatus[index] = v.status;
		linkname[index] = v.linkname;
		linpid[index] = v.pid;
		//alert(v.name+"--"+v.status+"---"+v.linkname+"---"+v.pid);
		vpid=v.pid;
	});  
		
		<s:set value="#session['MENU_DATA']" var="menuData"/>
		<s:iterator value="#menuData['menudata']" var="innermenu" status="status" > 
		menulist['<s:property value="#innermenu['id']" />'] = '<s:property value="#innermenu['menuName']" />';
		</s:iterator>
	//alert(menulist[vpid]);	
	
	$("#title").append(menulist[vpid]);
	$("#title1").append(menulist[vpid]);
});  


$(function () { 
	

	
		var builthtml="<table width=\"100%\"  id=\"DataTables_Table_0\">";	
		//builthtml=builthtml+"<thead><tr ><th>Action</th></tr ></thead>";
		builthtml=builthtml+"<tr>";
	$.each(linkIndex, function(indexLink, v) {	 
		
		//alert(linkName[indexLink]);
		 
		
		builthtml=builthtml+"<td><a  href='"+linkaction[indexLink]+".action?pid="+linpid[indexLink]+"' index='"+indexLink+"' ><img  style=\"width: 60px; height: 40px;\" src=\"${pageContext.request.contextPath}/images/iconslinks/"+linkaction[indexLink]+".png\" /></img><strong>"+linkname[indexLink]+"</strong></a></td>"; 
		
	}); 		
	builthtml=builthtml+"</tr>"; 
	$("#rights").html(builthtml);			
});  


			
function redirectAct(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
		$("#form1").submit();
		return true;
}


</script>


</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"><div id="title"></div></a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
			
			 
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<!-- Customer Negotiated Rate Confirmation -->
						<span id="title1"></span> Actions
						 <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
					
				 
					<div class="box-content" id="secondaryDetails">
						<fieldset>
							
						<div id="rights"></div>
							
						</fieldset>
						
					</div>
					
				</div>
			</div>
			
			
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Home" />
			</div>  

	
			
			</div>
			

</form>

</script>
</body>
</html>
