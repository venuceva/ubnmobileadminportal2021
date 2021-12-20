<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en" >
<!--<![endif]-->
<head>
 <%@ include file="css.jsp" %> 

<%
	String ctxstr = request.getContextPath();

%>
<%
	String appName = "";
	String random = "";
	String salt = "";
	String iv = "";
	String passwordkey = "";
	try {
		appName = (session.getAttribute(
				CevaCommonConstants.ACCESS_APPL_NAME).toString() == null
				? "banking"
				: session.getAttribute(
						CevaCommonConstants.ACCESS_APPL_NAME)
						.toString());
		random = session.getAttribute("RANDOM_VALUE").toString();
		salt = session.getAttribute("SALT").toString();
		iv = session.getAttribute("IV").toString();
		passwordkey=session.getAttribute("PASSWORD").toString();
		if (random == null) //session.setAttribute("RANDOM_VALUE", System.currentTimeMillis());
			response.sendRedirect(ctxstr + "/logout.action");

	} catch (Exception ee) {
		response.sendRedirect(ctxstr + "/cevaappl.action");
	}
%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><%=application.getInitParameter("pageTitle") %></title>

<link href="${pageContext.request.contextPath}/css/loginstyle.css" rel="stylesheet" media="screen" />
<link href="${pageContext.request.contextPath}/css/style_ceva.css" rel="stylesheet" type='text/css' />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/pbkdf2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/aes.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/rightclick_script.js"></script>

<style type="text/css">
.errorMessage {
	font-weight: bold;
	color: white;
	padding: 2px 8px;
	margin-top: 2px;
}

.errors {
	background-color: #FFCCCC;
	border: 0px solid #CC0000;
	width: 400px;
	margin-bottom: 8px;
}

.error {
	background-color: #FFCCCC;
	border: 0px solid #CC0000;
	width: 400px;
	margin-bottom: 8px;
}

.errors li {
	list-style: none;
}

body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

.full-spn-right {
	margin-left: 1200px;
}
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
 
function encryptPassword(password){
	var appPassword;
	var encryptPass;
	var finalPass;
	var randomNumber= '<%=random%>';
	//alert("RANDOM_VALUE>>>>"+randomNumber);
//	var password=$("#password").val();
	encryptPass = b64_sha256(password);	
	appPassword = encryptPass.concat(randomNumber);
	console.log(encryptPass);
	$("#encryptPassword").val(encryptPass);
	
}
function testPassword(pwString) {
    var strength = 0;

    strength += /[A-Z]+/.test(pwString) ? 1 : 0;
    strength += /[a-z]+/.test(pwString) ? 1 : 0;
    strength += /[0-9]+/.test(pwString) ? 1 : 0;
    strength += /[\W]+/.test(pwString) ? 1 : 0;

    switch(strength) {
        case 2:
            // its's medium!
            break;
        case 3:
            // it's strong!
            break;
        default:
            // it's weak!
            break;
    }
	return strength;
}

var loginvalidationRules = {
   rules : {
    userid : { required : true },
	password : { required : true, minlength:7,maxlength:16 }
   },  
   messages : {
    userid : { 
			required : "User Id Required"
        },
	password : { 
			required : "Password Required",
			minlength : "Password required minimum 7 characters",
			maxlength : "Password allows maximum 16 characters"
        }
   } 
 };
 
function submit(){
	
	var password = "<%=passwordkey%>";
	var salt = "<%=salt%>";

	var plainText = $("#password").val();
	var iv = "<%=iv%>";
	
	var key = CryptoJS.PBKDF2(password, CryptoJS.enc.Hex.parse(salt), {keySize: 128/32, iterations: 10});

	var a = CryptoJS.AES.encrypt(plainText, key, {iv: CryptoJS.enc.Hex.parse(iv)}).ciphertext.toString(CryptoJS.enc.Base64);
	
		if(true){
		
			var password=$("#password").val();
			//var level=testPassword(password); 
			$('#hiddenPassword').val(a);
			$("#password").val('');
			encryptPassword(password);
			console.log(encryptPassword(password));
			$("#form1")[0].action='<%=request.getContextPath()%>/weblogin.action';
			$("#form1").submit();
			return true;
		
	} else {
			return false;
		}

	}


	$(document).ready(function() {
		
		
		$('#windowsize').val($( window ).width()-250);

		$('#password').bind('keypress', function(e) {
			if (e.keyCode == 13) {
				submit();
			}
		});

		$('#login').on('click', function() {
			
			if($('#userid').val()==""){
				$('#errormsg').text("Please Enter Username ")
				return false;
			}
			if($('#password').val()==""){
				$('#errormsg').text("Please Enter Password ")
				return false;
			} 
			if($('#token').val()==""){
				$('#errormsg').text("Please Enter Token ")
				return false;
			}
			submit();
		});

		$('#userid').on('keyup', function() {
			// var id = $(this).attr('id');
			//var v_val = $(this).val(); 
			$("#userid").val($(this).val().toUpperCase());
		});
	});
</script>
</head>
<body >
	<form name="form1" id="form1" method="post" action="" autocomplete="off">
	<!-- hiding save passwords -->
	<input type="password" style="display:none"/>
		
	
	
		 <div class="demo" >
    <div class="login">
     <div style="
     padding: 30px 40px;
		  text-align:center;
     font-size: 20px;
		  color:#FFFFFF;
		  font-weight:bold;
		  /* background: url(images/pattern-ttl111.png); */
     "><!-- Mobile Banking --></div>
     <div style="padding: 1px 1px;transition: opacity 0.1s, -webkit-transform 0.3s cubic-bezier(0.17, -0.65, 0.665, 1.25);"></div>
     <div style="background-color:#00AEEF;">
				<section class="display:table-cell">
					<img src="images/logo-main.png" width="200" height="80" style="padding-left:20px;margin-right: -100px; text-align: right; margin-bottom: 30px; margin-left: -6px;"
						alt="logo">
				</section>
			
				</div> 
				
				
				<span style="
			
		  margin-top:100px;
		  padding: 10px 20px;
		  text-align:center;
		  border: none;
		  border-radius: 10px;
		  color: rgba(0, 0, 0, 0.7);
		  position: relative;
		  font-size: 2rem;
		  font-weight:bold;"><s:actionmessage cssClass="" cssErrorClass="" />
				<s:actionerror />
				<div id="errormsg" style="color:white;" ></div>
				</span>
				
				
      <div class="login__form" >
        <div class="login__row">
          <svg class="login__icon name svg-icon" viewBox="0 0 20 20">
            <path d="M0,20 a10,8 0 0,1 20,0z M10,0 a4,4 0 0,1 0,8 a4,4 0 0,1 0,-8" />
          </svg>
         <input type="text" class="login__input name" name="userid" id="userid" autocomplete="off"
						required="required" value="" placeholder="Username ">
        </div>
        <div class="login__row">
          <svg class="login__icon pass svg-icon" viewBox="0 0 20 20">
            <path d="M0,20 20,20 20,8 0,8z M10,13 10,16z M4,8 a6,8 0 0,1 12,0" />
          </svg>
          <input type="password" class="login__input pass" name="password" id="password"
						autocomplete="off" required="required" placeholder="Password">
        </div>
        
       <div class="login__row">
          <svg class="login__icon pass svg-icon" viewBox="0 0 20 20">
            <path d="M20,10 15.36,15.36 a9,9 0 0,1 -12.72,-12.72 a 9,9 0 0,1 12.72,12.72" />
          </svg> 
          <input type="text" class="login__input pass" name="token" id="token"
						autocomplete="off" required="required" placeholder="Token" value="">
       </div>  
        
        <p class="submit">
        <input type="button" class="login__submit"  name="login" id="login" value="Login">
        </p>
        
        <div >
			<a href="http://www.cevaltd.com/" target="_blank"><img width="80" height="40" alt="logo" style="margin-right: -195px; text-align: right; margin-bottom: 10px" src="images/logo_ceva.png"></img></a>
		</div >
      </div>
       
    </div>
  
   </div>

				<input type="hidden" name="encryptPassword" id="encryptPassword"/>
				<input type="hidden" name="hiddenPassword" id="hiddenPassword" value="">
				<input type="hidden" name="windowsize" id="windowsize" value="">
		<script src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
	</form>
</body>
</html>
