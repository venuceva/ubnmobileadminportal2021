<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>

<%
  String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString();

  String userLevel="";
  String location="";
  String logintime="";
  String lastLogin="";
  
  try{
	userLevel= session.getAttribute("userLevel").toString();
	//System.out.println("User Level::::"+userLevel);
    location = session.getAttribute("location").toString();
    lastLogin= session.getAttribute("lastLogin").toString();
    logintime= session.getAttribute("loginTime").toString();
    
  } catch(Exception e) {
	userLevel=" ";
    location=" ";
   logintime=" ";
   }
     %>
     
<html>
<head>

<meta charset="utf-8" />
<title>NBK</title>
<link href="${pageContext.request.contextPath}/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">

<link href="${pageContext.request.contextPath}/css/jquery-ui-1.8.21.custom.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/bootstrap-united.css" rel="stylesheet" type="text/css" />


<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/rightclick_script.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sha256.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SheepToasts/animate.min.css">

<style>

body{
  margin      : 0;
  padding     : 0em;
  font-family : Utopia,Charter,serif;
}

.btn-success{
 background-image: url("${pageContext.request.contextPath}/images/userloginbg.gif");

 }
 
  .btn-info{
 background-image: url("${pageContext.request.contextPath}/images/userloginbg.gif");
 }
 
 .btn-primary{
 background-image: url("${pageContext.request.contextPath}/images/userloginbg.gif");
 }
 .btn-warning{
 background-image: url("${pageContext.request.contextPath}/images/userloginbg.gif");
 }
 .btn-danger{
 background-image: url("${pageContext.request.contextPath}/images/userloginbg.gif");
 }
 .btn-inverse{
 background-image: url("${pageContext.request.contextPath}/images/userloginbg.gif");
 } 


.box-header.well {
   
     background-image: url("${pageContext.request.contextPath}/images/banner.png");
    color:white;
}





.breadcrumb {
    	border-width: 5px;
border-style: solid;
border-radius: 10px;
-webkit-border-radius: 10px;
-moz-border-radius: 10px;
-webkit-box-shadow: 0 1px 1px 
hsla(0, 0%, 0%, 0.08), inset 0 1px 
hsla(0, 100%, 100%, 0.3);
-moz-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.08), inset 0 1px rgba(255, 255, 255, 0.3);
box-shadow: 0 1px 1px 
hsla(0, 0%, 0%, 0.08), inset 0 1px 
hsla(0, 100%, 100%, 0.3);
  
}

.breadcrumb11 {
 background-image: url("${pageContext.request.contextPath}/images/userloginbg.gif");
 border-width: 0px;
 border-style: solid;
border-radius: 10px;
-webkit-border-radius: 7px;
-moz-border-radius: 10px;
-webkit-box-shadow: 0 1px 1px 
hsla(10, 0%, 10%, 0.08), inset 0 1px 
hsla(10, 100%, 100%, 0.3);
-moz-box-shadow: 10 10px 10px rgba(10, 10, 10, 10.08), inset 0 1px rgba(255, 255, 255, 0.3);
box-shadow: 10 10px 10px 
hsla(0, 0%, 0%, 0.08), inset 0 1px 
hsla(0, 100%, 100%, 0.3);
}

.notification11 {

    height: 100px;
    background-image: url("${pageContext.request.contextPath}/images/userloginbg.gif");
    color:white;
    position: relative;
     border-style: solid;
    border-radius: 5px;
-webkit-border-radius: 7px;
-moz-border-radius: 10px;
-webkit-box-shadow: 0 1px 1px 
hsla(10, 0%, 10%, 0.08), inset 0 1px 
hsla(10, 100%, 100%, 0.3);
-moz-box-shadow: 10 10px 10px rgba(10, 10, 10, 10.08), inset 0 1px rgba(255, 255, 255, 0.3);
box-shadow: 10 10px 10px 
hsla(0, 0%, 0%, 0.08), inset 0 1px 
hsla(0, 100%, 100%, 0.3);
    
}

<%-- .span10{

width:<%=session.getAttribute("windowsize").toString()%>;
}
 --%>
</style>

<script type="text/javascript">

document.onkeydown = function (e) {

	/* disable the F12 , Ctrl + Shift + I, Ctrl + Shift + J and Ctrl + U keys */
    // disable F12 key
    if(e.keyCode == 123) {
        return false;
    }

    // disable I key
    if(e.ctrlKey && e.shiftKey && e.keyCode == 73){
        return false;
    }

    // disable J key
    if(e.ctrlKey && e.shiftKey && e.keyCode == 74) {
        return false;
    }

    // disable U key
    if(e.ctrlKey && e.keyCode == 85) {
        return false;
    }
}
	
	
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

 $(function () {
	
	 /* $('.span10').addClass('span12');
	 $('.span10').removeClass('span10');  */
	 
	var v=<%=session.getAttribute("pid").toString()%>;
	$("#TAB-"+v).addClass("active");
	
 	
/* alert($( window ).width()-400); */
	

	
	/* 
	
	
	
	
	
	
	if(v==1){
		SheepToasts.warning("Are you sure?", null, {
			  yes: {
			    value: "&#39;Yes&#39;",
			    callback: function(){
			        alert("You are sure!");
			      SheepToasts.close(this.toastId);
			    }
			  },
			  no: {
			    value: "&#39;No&#39;",
			    callback: function(){
			        SheepToasts.close(this.toastId);
			    }
			  }
			});
	}  */
	 
	
	
}); 
 
 /* window.setTimeout("beginrefresh()",5000);
 function beginrefresh(){	
		var queryString9 = "method=fetchNotification";
		
		$.getJSON("postJson.action", queryString9,function(data) {
		var custid = data.custcode;
		var arr = $.parseJSON(custid); 
		
		 
		 
		 for(i=0;i<arr.length;i++){
			 //alert(arr[i]); 
			
			  SheepToasts.warning(arr[i], null, {
				 
				  no: {
				    value:"OK" ,
				    callback: function(){
				    	//alert(this.toastId+"---"+this.innerHTML);
				    	//fundelete(this.innerHTML);
				        SheepToasts.close(this.toastId);
				    }
				  }
				});   
			 
		 }
		
		});
setTimeout("beginrefresh()",5000)
 }  */


$(function(){
	 
	 $("form").submit(function(){
		 //alert($(this).attr('id'));
		 var totval="";
		 var totkey=""; 
		  
		
		 $('#'+$(this).attr('id')+' input:not(:disabled),#'+$(this).attr('id')+' select:not(:disabled),#'+$(this).attr('id')+' textarea:not(:disabled)').each(
				    function(index){  
				        var input = $(this);
				        if(typeof(input.attr('name'))  === "undefined"){
				        	
				        } else{
				        
				       // if(input.attr('type')!="button" && input.attr('type')!="radio" && input.attr('type')!="checkbox"  && input.attr('type')!="file" && input.attr('name')!="adminType"){
				        	if(input.attr('type')!="button" && input.attr('type')!="radio" && input.attr('type')!="checkbox"  && input.attr('type')!="file"){
				        	//alert('Type: ' + input.attr('type') + '-----Name: ' + input.attr('name') + '-----Value: ' + input.val());
				        	totkey=totkey+""+input.attr('name')+",";
				        	totval=totval+""+input.val()+",";
				        	}
				        }
				        
				      
				    }
				);
		 <%-- var salt = "<%=session.getAttribute("SALT").toString() %>";  --%>
		//alert(salt);
		
		var input1 = $("<input />").attr("type", "hidden").attr("name", "keyvalue").val(b64_sha256(totval+"<%=session.getAttribute("SALT").toString() %>"));
		var input2 = $("<input />").attr("type", "hidden").attr("name", "keys").val(totkey);
			
			$('#'+$(this).attr('id')).append(input1);
			$('#'+$(this).attr('id')).append(input2);
		 
		
		
		 
		});
	 
	 
 });
 



</script>

</head>

<body>

	 <div class="navbar">
	 
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
						data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar" />
					<span class="icon-bar" />
					<span class="icon-bar" />
				</a>
				<!--  <a href="dashboardhome.action" style="margin-top: 50px;">  -->
				   <img alt="Logo" align="left" style="margin-left: 2px; margin-top: 1px; width: 200px; height: 80px;" src="${pageContext.request.contextPath}/images/logo-main.png" /> <!-- </a> -->
				
				<!-- user dropdown starts -->
				<span class="btn-group pull-right" style="margin-top: 33px;margin-right: -30px; margin-top: 0px;">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<!-- <i class="icon-user"></i> -->
						<span class="hidden-phone"><strong><%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %> </strong></span>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/<%=appName %>/userprofile.action">Profile</a></li>
						<li class="divider"></li>
						<li><a href="${pageContext.request.contextPath}/logout.action" target="_parent">Logout</a></li>
						<!--  <li><a href="${pageContext.request.contextPath}/changePwd.action" target="_parent">Change Password</a></li>-->
					</ul>
				</span> 
				<span class="menu-class pull-right"  style="margin-top: 73px; margin-right: -76px; font-size:1.1em; margin-top: 70px; color: #1a1b1c;font-size:13px;font-weight: bold;"  >  Last Login Time:&nbsp; <%=session.getAttribute("lastLogin").toString() %> &nbsp;|&nbsp;  Current Login Time:&nbsp; <%=session.getAttribute("loginTime").toString() %>&nbsp;|&nbsp;  User Level:&nbsp; <%=session.getAttribute("userLevelname").toString() %> &nbsp;|&nbsp;  Branch:&nbsp; <%=session.getAttribute("location").toString() %> </span>  
				 <!-- <span class="menu-class pull-right" style="margin-top: 73px; margin-right: -76px; font-size:1.1em; margin-top: 70px; color: #490a00;" >Last Login &nbsp;: &nbsp;01-MAR-2016 03:00:00 PM &nbsp;|&nbsp;HARAMBE AVENUE|&nbsp;  CEVA </span> -->
				<div class="top-nav nav-collapse"></div><!--/.nav-collapse -->
			</div>
		</div>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/SheepToasts/sheeptoasts.js"></script>
<script>
var SheepToasts = new SheepToasts();

</script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-dropdown.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/manual-validation.js"></script>
</body>
</html>
