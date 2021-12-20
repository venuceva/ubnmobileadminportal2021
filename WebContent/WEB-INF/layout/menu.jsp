
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="robots" content="all" /> 
 
<link href="<%=ctxstr %>/css/agency-app.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	
	
function unique(list) {
	var result = [];
	$.each(list, function(i, e) {
		if ($.inArray(e, result) == -1) result.push(e);
	});
	return result;
}

function checkExists(sel) {
	var status = false;
	if ($(sel).length) status = true;
	return status;
} 

</script> 

</head>
<body> 
	
	<div class="span2 main-menu-span">
		<div class="well nav-collapse sidebar-nav" id="cssmenu"> 
			<ul class="accordion"  id="accordion-2" > 
				<s:set value="#session['MENU_DATA']" var="menuData"/>
				<s:iterator value="#menuData['menudata']" var="innermenu" status="status" >  
			   			<li id='TAB-<s:property value="#innermenu['id']" />'>
			   				<a href='<%=ctxstr %>/<s:property value="#innermenu['applName']" />/<s:property value="#innermenu['menuaction']" />.action?pid=<%=session.getAttribute("session_refno").toString() %><s:property value="#innermenu['id']" />'  class="ajax-link"> 
			   					<span><i class='icon  <s:property value="#innermenu['title']" /> icon-white' ></i> 
			   						<span class="hidden-tablet"><s:property value="#innermenu['menuName']" /></span>
			   					</span>
			   				</a>
			   		   </li> 
				</s:iterator>
			</ul> 
		</div>
	</div> 
	
</body>
</html>
