<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  

<% String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<style type="text/css">
#fade,#fade1 {
	display: none;
	position: absolute;
	top: 0%;
	left: 15%;
	width: 70%;
	height: 100%;
	background-color: black;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity = 80);
}

#light,#light1 {
	display: none;
	position: absolute;
	top: 0%;
	left: 15%;
	width: 70%;
	height: 100%;
	/* margin-left: -150px;
	margin-top: -100px; */
	/* border: 2px solid #FFF;*/
	background: #FFF; 
	z-index: 1002;
	overflow: visible;
}
</style>


<script type="text/javascript" >



var createProductRules = {
		   rules : {
			   	
				tokenlimit		: { required : true,number: true},
				perdaylimit		: { required : true,number: true}
				
		   },  
		   messages : {
			  
			  tokenlimit : {
					 required :"Please Enter Token Limit Amount ",
					 regex : "Token Limit, can not allow special characters."
				 },
			  perdaylimit : {
					 required :"Please Enter Per Day Limit Amount ",
					 regex : "Per Day Limit, can not allow special characters."
				 }
		    } 
		 };


	$.validator.addMethod("regex", function(value, element, regexpr) {          
		 return regexpr.test(value);
	   }, ""); 
	
	
	
	
	

  $(function() {
	  //$('#balancemax1').css("display","none"); 
		$('#limitperday').css("display","none"); 
		
		if($('#application').val()=="Mobile Banking"){
			$('#balancemax1').css("display",""); 	
		}
		
	  if($('#application').val()=="Agent"){
			$('#tokenlimit1').css("display","none"); 
			  $('#tokenlimit2').css("display","none"); 
			  $('#tokenlimit3').css("display",""); 
			  $('#tokenlimit4').css("display",""); 
			  $('#limitperday').css("display",""); 
			 // $('#balancemax1').css("display","none"); 	
		}else{
                        //$('#balancemax1').css("display","none"); 
			$('#tokenlimit1').css("display",""); 
			  $('#tokenlimit2').css("display",""); 
			  $('#tokenlimit3').css("display","none"); 
			  $('#tokenlimit4').css("display","none"); 
			  
			  $('#limitperday').css("display",""); 
			 
		}
	  
	  $('#balancemax').css("display","none"); 
	  if($('#application').val()=="Wallet"){
		  $('#balancemax').css("display","");  
		  $('#balancemax1').css("display","none"); 
	  }
	  
	  buildbranchtable();
		    var  Cards='${responseJSON.plasticCode}';
		    var  Cards1='${responseJSON.limitCodeVal}';
		    var  Cards2='${responseJSON.feeCodeVal}';
		    
		    var Card3=Cards+","+Cards1+","+Cards2;
		    
		    $('#oldVal').val(Card3);
		    
		 
		    var binGrDataArr = Cards.replace(' ','').split(",");
		    console.log(binGrDataArr[i]);
		    
		    
		    for(var i=0;i<binGrDataArr.length;i++){
				 //alert("Value is:"+binGrDataArr[i].trim());
				 //alert('#binGroup option[value="' + binGrDataArr[i] + '"]');
				 $('#plasticCode option[value="'+binGrDataArr[i]+'"]').prop('selected', true);
			 }
		    
		    
	/*   var pcode1='${responseJSON.plasticCode}';
	  alert(pcode1); */
	  
	// Back Button Start 
	
	      var pcode='${plasticCode}';
	      var plasticGrDataArr = pcode.split(",");
	      for(var i=0;i<plasticGrDataArr.length;i++){
			   $('#plasticCode option[value="'+plasticGrDataArr[i].trim()+'"]').prop('selected', true);
		  }
	     
	// Back Button Ending 
	      
	   
	       $("#plasticCode").change(function(){ 
		   var countries1 = new Array();
		   $.each($("#plasticCode option:selected"), function(){            
		       countries1.push($(this).text());
		   });
		   
		   var pData = countries1.join(",");
		   $('#plasticCodeText').val(pData);
		   });
	   
        
 
		   var config = {
			      '.chosen-select'           : {},
			      '.chosen-select-deselect'  : {allow_single_deselect:true},
			      '.chosen-select-no-single' : {disable_search_threshold:10},
			      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			      '.chosen-select-width'     : {width:"95%"}
			    }
				
			    for (var selector in config) {
			      $(selector).chosen(config [selector]);
			    }
   });  
  
  
 //For Closing Select Box Error Message_Start
	  $(document).on('change','select',function(event) { 
	   
		   if($('#'+$(this).attr('id')).next('label').text().length > 0) {
			    $('#'+$(this).attr('id')).next('label').text(''); 
			    $('#'+$(this).attr('id')).next('label').remove();
		    }
	  });
  
	/*   var createProductRules = {
			   rules   : {
				  		    plasticCode     : { required : true} 
			             },
	   		  messages : {
	   						plasticCode : { 
					        required : "Please Enter Plastic Code "
					        }
			             },
	       }; */
	
	function getProductScreen(){
		
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/createProductAct.action';
		$("#form1").submit();
		return true;
	}
	       
	       
	       var mobileProductRules = {
	    		   rules : {
	    			   
	    				tokenlimit		: { required : true,number: true},
	    				perdaylimit		: { required : true,number: true},
	    				ussdinitallmt				: { required : true,number: true },
	    				ussdsecfalmt				: { required : true,number: true }
	    				
	    				
	    		   },  
	    		   messages : {
	    			   
	    			  tokenlimit : {
	    					 required :"Please Enter Token Limit Amount ",
	    					 regex : "Token Limit, can not allow special characters."
	    				 },
	    			  perdaylimit : {
	    					 required :"Please Enter Per Day Limit Amount ",
	    					 regex : "Per Day Limit, can not allow special characters."
	    				 },
	    				ussdinitallmt : {
	    					 required :"Please Enter USSD Initial Limit ",
	    					 regex : "USSD Initial Limit, can not allow special characters."
	    				 },
	    				 ussdsecfalmt : {
	    					 required :"Please Enter USSD 2FA Limit Amount ",
	    					 regex : "USSD 2FA Limit Amount, can not allow special characters."
	    				 }
	    		    } 
	    		 };
	      
	      
	      var walletProductRules = {
	    		   rules : {
	    			   	
	    				tokenlimit		: { required : true,number: true},
	    				perdaylimit		: { required : true,number: true},
	    				ussdinilmt				: { required : true,number: true }
	    				
	    				
	    		   },  
	    		   messages : {
	    			   
	    			  tokenlimit : {
	    					 required :"Please Enter Token Limit Amount ",
	    					 regex : "Token Limit, can not allow special characters."
	    				 },
	    			  perdaylimit : {
	    					 required :"Please Enter Per Day Limit Amount ",
	    					 regex : "Per Day Limit, can not allow special characters."
	    				 },
	    				ussdinilmt : {
	    					 required :"Please Enter Maximum Balance Amount ",
	    					 regex : "Maximum Balance Amount, can not allow special characters."
	    				 }
	    		    } 
	    		 };
	      
	      var agentProductRules = {
	    		   rules : {
	    			   
	    				perdaylimit		: { required : true,number: true},
	    				agent		: { required : true },
	    				ussdinitallmt				: { required : true,number: true },
	    				ussdsecfalmt				: { required : true,number: true }
	    				
	    				
	    		   },  
	    		   messages : {
	    			  
	    			  perdaylimit : {
	    					 required :"Please Enter Per Day Limit Amount ",
	    					 regex : "Per Day Limit, can not allow special characters."
	    				 },
	    			   
	    				 agent : { 
	    					     required : "Please Select Agent"
	    					},
	    					ussdinitallmt : {
	   						 required :"Please Enter USSD Initial Limit ",
	   						 regex : "USSD Initial Limit, can not allow special characters."
	   					 },
	   					 ussdsecfalmt : {
	   						 required :"Please Enter USSD 2FA Limit Amount ",
	   						 regex : "USSD 2FA Limit Amount, can not allow special characters."
	   					 }
	    		    } 
	    		 };
	
function createProductModify(){
	
	        //$("#form1").validate(createProductRules);
	        
			 if($('#application').val()=="Mobile Banking"){
				   $("#form1").validate(mobileProductRules); 
				   
				   if($('#MOBILE').val()==""){
					   $("#errormsg").html("<font colour='red'>Per Day Limit is Mandatory for Channel Mobile </font>");
				 
					   return false;
					}
					
					if($('#USSD').val()==""){
						$("#errormsg").html("<font colour='red'>Per Day Limit is Mandatory for Channel USSD</font>");
						return false;
					} 
					
			   }
			   
			   if($('#application').val()=="Wallet"){
				   $("#form1").validate(walletProductRules); 
				   
				   if($('#MOBILE').val()==""){
					   $("#errormsg").html("<font colour='red'>Per Day Limit is Mandatory for Channel Mobile </font>");
				 
					   return false;
					}
					
					if($('#USSD').val()==""){
						$("#errormsg").html("<font colour='red'>Per Day Limit is Mandatory for Channel USSD</font>");
						return false;
					} 
					
			   }
			   
			   if($('#application').val()=="Agent"){
				   $("#form1").validate(agentProductRules); 
				   
				   if($('#MOBILE').val()==""){
					   $("#errormsg").html("<font colour='red'>Per Day Limit is Mandatory for Channel Mobile </font>");
				 
					   return false;
					}
					
					if($('#USSD').val()==""){
						$("#errormsg").html("<font colour='red'>Per Day Limit is Mandatory for Channel USSD</font>");
						return false;
					} 
					
					/* if($('#WEB').val()==""){
						$("#errormsg").html("<font colour='red'>Per Day Limit is Mandatory for Channel WEB</font>");
						return false;
					} 
					
					if($('#POS').val()==""){
						$("#errormsg").html("<font colour='red'>Per Day Limit is Mandatory for Channel POS</font>");
						return false;
					}  */
			   }
			   
			   
	       
			submitfun();
			
			
	 
			/* var btype=$( "#plasticCode option:selected" ).text();
			$('#plasticData').val(btype);   */
			 
	       //$("#plasticCode").change(function(){ 
	    	   
		   var countries1 = new Array();
		   var countries2 = new Array();
		   
		   $.each($("#plasticCode option:selected"), function(){            
		       countries1.push($(this).text());
		       countries2.push($(this).val());
		   });
		   var pData = countries1.join(",");
		   $('#plasticCodeText').val(pData);
		   
		   var pData2 = countries2.join(",");
		   
		   var limitVal=$( "#limitCode option:selected" ).val();
		   
		   var feeVal=$( "#feeCode option:selected" ).val();
		   
		   var newVal=pData2+","+limitVal+","+feeVal;
		   
		   $('#newVal').val(newVal);
	 		 
		 if($("#form1").valid()){
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/productModifyConfirmAct.action';
			$("#form1").submit();
			return true;
		}else{
			return false;
		}
    }	
    
    
    


    $('#view1').live('click', function () {
    	
    	
    	var vals = ($("#feeCode").val()).split("-")[0];
    	if(vals=="" || vals==" " || vals==null || vals=="Not yet Configure")
    		{
    		$('#selalrtmsg').text("Please Select Fee Code");
    		$('#selalrtmsg1').empty();
    		}else{
    			$('#selalrtmsg').empty();
    			$('#selalrtmsg1').empty();
    			
    	
    			 $.ajax({
    				    url : '<%=request.getContextPath()%>/<%=appName %>/getfeecodepopupData.action',
    				    async: false,
    				    type: "POST",
    				    data : "feeCode="+ vals ,
    				    success: function(data, textStatus, jqXHR)
    				    {
    				
    				$('#feecodedata').text(data.Feecodedetails.feeCode);
    				$('#feedescdata').text(data.Feecodedetails.feeDesc);							
    				lightbox_open();
    				tablegridfill(data.Feecodedetails2);
    				     },
    				    error: function (jqXHR, textStatus, errorThrown)
    				    {
    				    }
    				   });
    			 
    			 
    				 
    			 }
    	
    	
    });

    function lightbox_open()
    {
     window.scrollTo(0,0);
     document.getElementById('light').style.display='block';
     document.getElementById('fade').style.display='block';
    }

    function lightbox_close()
    {
     document.getElementById('light').style.display='none';
     document.getElementById('fade').style.display='none';
    }

    function tablegridfill(data)
    {
    	var json3 = data;
    	$('#searchBody2').empty();
    	var totalrows2 =  json3.length;
    	
    	 var htmlString2 = "";
    	 var i2;

    	 for(i2=0;i2<totalrows2;i2++)
    	 {

    	      
    	   htmlString2 = htmlString2 + "<tr class='values' id='"+(i2+1)+"'>";   
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].SNO + "</td>";
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].TXNNAME + "</td>";
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].FREQ + "</td>";
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].FLATPER + "</td>";
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].FPVALUE + "</td>";
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].CRT + "</td>";	  			   
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].FRMVAL + "</td>";
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].TOVAL + "</td>";  
    	   htmlString2 = htmlString2 + "</tr>";

    	   $('#searchBody2').append(htmlString2);

    	   htmlString2="";
    	  
    	 }
    	
    }

    $('#view').live('click', function () {
    	
    	
    	var vals = ($("#limitCode").val()).split("-")[0];
    	if(vals=="" || vals==" " || vals==null || vals=="Not yet Configure")
    		{
    		$('#selalrtmsg1').text("Please Select Limit Code");
    		$('#selalrtmsg').empty();
    		}else{
    			$('#selalrtmsg1').empty();
    			$('#selalrtmsg').empty();
    			
    			
    			$.ajax({
    			    url : '<%=request.getContextPath()%>/<%=appName %>/getlimitcodepopupData.action',
    			    async: false,
    			    type: "POST",
    			    data : "limitCode="+ vals ,
    			    success: function(data, textStatus, jqXHR)
    			    {
    			
    			$('#limitcodedata').text(data.limitcodedetails.limitCode);
    			$('#limitdescdata').text(data.limitcodedetails.limitDesc);							
    			lightbox_open1();
    			tablegridfill1(data.limitcodedetails2);
    			     },
    			    error: function (jqXHR, textStatus, errorThrown)
    			    {
    			    }
    			   });
    		
    		}
    	
    });



    function lightbox_open1()
    {
     window.scrollTo(0,0);
     document.getElementById('light1').style.display='block';
     document.getElementById('fade1').style.display='block';
    }

    function lightbox_close1()
    {
     document.getElementById('light1').style.display='none';
     document.getElementById('fade1').style.display='none';
    }

    function tablegridfill1(data)
    {
    	var json3 = data;
    	$('#searchBody22').empty();
    	var totalrows2 =  json3.length;
    	
    	 var htmlString2 = "";
    	 var i2;

    	 for(i2=0;i2<totalrows2;i2++)
    	 {

    	      
    	   htmlString2 = htmlString2 + "<tr class='values' id='"+(i2+1)+"'>";   
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].SNO + "</td>";
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].TXNNAME + "</td>";
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].FREQ + "</td>";
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].MAX_CNT + "</td>";
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].MIN_AMT + "</td>";
    	   htmlString2 = htmlString2 + "<td align='center'>" + json3[i2].MAX_AMT + "</td>";	
    	   htmlString2 = htmlString2 + "</tr>";

    	   $('#searchBody22').append(htmlString2);

    	   htmlString2="";
    	  
    	 }
    	
    }

function buildbranchtable()
{

	$("#tbody_data").empty();
	
	var htmlString="";
	var chdata=$("#chdata").val();
	var spidat=chdata.split("\|");
	
	for(i=0;i<spidat.length-1;i++){
		
			htmlString = htmlString + "<tr class='values' id="+i+">";
		
			htmlString = htmlString + "<td id='channellmt' name=channellmt >" + spidat[i].split("#")[0] + "</td>";
			htmlString = htmlString + "<td id='cperdaylmt' name='cperdaylmt' ><input type='text' id='"+spidat[i].split("#")[0]+"' name='"+spidat[i].split("#")[0]+"' value='" + spidat[i].split("#")[1] + "' /></td>";
		
			htmlString = htmlString + "</tr>";
			

	
	}
	
	
	$("#tbody_data").append(htmlString);
	
}

function submitfun(){
	
	var val1="";
	var chdata=$("#chdata").val();
	var chdataspt=chdata.split("\|");
	for(i=0;i<chdataspt.length-1;i++){
		
		val1=val1+chdataspt[i].split("#")[0]+"#"+$("#"+chdataspt[i].split("#")[0]).val()+"|"
	}
	
	$("#chdata").val(val1)
}


$(document).ready(function() {
    
    //attach keypress to input
    $('#MOBILE').keydown(function(event) {
        // Allow special chars + arrows 
        if (event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 
            || event.keyCode == 27 || event.keyCode == 13 
            || (event.keyCode == 65 && event.ctrlKey === true) 
            || (event.keyCode >= 35 && event.keyCode <= 39)){
                return;
        }else {
            // If it's not a number stop the keypress
            if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
                event.preventDefault(); 
            }   
        }
    });
    
    $('#USSD').keydown(function(event) {
        // Allow special chars + arrows 
        if (event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 
            || event.keyCode == 27 || event.keyCode == 13 
            || (event.keyCode == 65 && event.ctrlKey === true) 
            || (event.keyCode >= 35 && event.keyCode <= 39)){
                return;
        }else {
            // If it's not a number stop the keypress
            if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
                event.preventDefault(); 
            }   
        }
    });
    
});

$(document).ready(function() {
	var x=document.getElementById("feename").value;
	
	
	if(x=="Agent")
		{
		document.getElementById("Agent1").checked=true;
		}
			
	else
		{
		document.getElementById("Customer1").checked=true;
		
		}

}); 

function myFunction(feecharge) {
	  document.getElementById("feename").value = feecharge;

}

</script>

<s:set value="responseJSON" var="respData"/>

</head>

<body>
	<form name="form1" id="form1" method="post">
		<div id="content" class="span10"> 
		    <div>
				 <ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider">&gt;&gt;</span> </li>
					  <li> <a href="#">Product Management</a> <span class="divider">&gt;&gt;</span></li>
					  <li><a href="#">Product Modify</a></li>
				 </ul>
			</div>
			<div id="errormsg" class="errores"></div>
			       <table height="3">
						<tr>
							<td colspan="3">
								<div class="messages" id="messages"><s:actionmessage /></div>
								<div class="errors" id="errors"><s:actionerror /></div>
							</td>
						</tr>
					</table>
			<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12">  
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Product Modify  
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>  
					<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								 <td width="20%"><label for="Product Code"><strong>Product Code </strong></label></td>
								 <td>  <s:property value='#respData.productCode' />  <input type="hidden" id="productCode" name="productCode" value="<s:property value='#respData.productCode' />" /></td>
								 <td width="20%"><label for="Product Description"><strong>Product Description </strong></label></td>
								 <!-- <td width="30%"><input name="productDesc"  type="text" id="productDesc" class="field"> </td> -->
								 <td>  <s:property value='#respData.productDesc' />  <input type="hidden" id="productDesc" name="productDesc" value="<s:property value='#respData.productDesc' />" /></td>
							</tr>
							<tr class="odd">
										<td ><label for="Product Currency"><strong>Product Currency</strong></label></td>
										<td width="30%"> <s:property value='#respData.binCurrency' /> 
										<input type="hidden" id="binCurrency" name="binCurrency" value="<s:property value='#respData.binCurrency' />" /></td>
										<td ><label for="Application"><strong>Application</strong></label></td>
										<td width="30%"> <s:property value='#respData.application' /> 
										<input type="hidden" id="application" name="application" value="<s:property value='#respData.application' />" /></td>
									</tr> 
							
									
							
							
							<tr class="even" id="limitperday" style="display:none">
									
									<td id="tokenlimit1" style="display:none"><label for="ToKenLimit"><strong>Token Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td id="tokenlimit2" style="display:none"><input type="text" id="tokenlimit" name="tokenlimit" maxlength='25' value="<s:property value='#respData.tokenval' />" class="field" /></td>
									<td><label for="ToKenLimit"><strong>Per Day Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td><input type="text" id="perdaylimit" name="perdaylimit" maxlength='25' value="<s:property value='#respData.perdaylimit' />" class="field" /></td>
									<td id="tokenlimit3" style="display:none"><strong>Super Agent</strong></td>
									<td id="tokenlimit4" style="display:none"><s:property value='#respData.agent' />
									<input name="agent"  type="hidden" id="agent" class="field" value="<s:property value='#respData.agent' />"></td>
								</tr>
								
								
								<!--<tr class="even" id="balancemax1" >
									
									<td ><label for="ToKenLimit"><strong>USSD intial Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td ><input type="text" id="ussdinitallmt" name="ussdinitallmt" maxlength='25' value="<s:property value='#respData.ussdinitallmt' />" class="field" /></td>
									<td><label for="ToKenLimit"><strong>USSD 2FA Limit Amount<font color="red">*</font>
										</strong></label></td>
									<td><input type="text" id="ussdsecfalmt" name="ussdsecfalmt" maxlength='25' value="<s:property value='#respData.ussdsecfalmt' />" class="field" /></td>
									
								</tr>-->

															<tr class="even">
								<td width="20%"><label for="Bank">
									<strong>Fee Charge to <font color="red">*</font></strong></label>
								</td>
								<td width="30%">
								<input type="radio" name="feecharge" id="Customer1" onclick="myFunction(this.value)" required="true" value="Customer"/>&nbsp;<strong>Customer</strong>&nbsp;&nbsp;&nbsp;
								<input type="radio" name="feecharge" id="Agent1" onclick="myFunction(this.value)" required="true" value="Agent" />&nbsp;<strong>Agent</strong>
								
								<input name="feename" id="feename"  type="hidden" maxlength ='25'  required="true"  class="field"  value="< s:property value='#respData.feename' />"  onkeyup="Redirect()"/><span id="bin_err1" class="errmsg"></span>
								<span id="bin_err1" class="errmsg"></span></td>
								
				
								 <td></td>
								 <td></td>
							</tr>	
							
						</table>
						<input type="hidden"  id="chdata" name="chdata" value="<s:property value='#respData.channellimit' />" />
								<br>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable" 
						id="acqdetails" style="width: 100%;" >
							  <thead>
									<tr>
										
										<th width="25%">Channel</th>
										<th width="25%">Per Day Limit Amount</th>
									
									</tr>
							  </thead>    
							 <tbody id="tbody_data">
							 </tbody>
							 
					</table>
						 <input name="pCode" type="hidden" class="field" id="pCode" value ='${pCode}'/>
						 <input name="binTypeData"  type="hidden" id="binTypeData" />
						 <input name="plasticData" type="hidden" id="plasticData" />
						 <input name="issuingData" type="hidden" id="issuingData" />
						 <input name="binGroupData" type="hidden" id="binGroupData" />
						 <input name="plasticCodeText" type="hidden" id="plasticCodeText" />
						 <!-- <imput name="plasticCode" type="hidden" id="plasticCode" /> -->
						 
						 
						 
						 
						</fieldset>
						</div> 
					</div> 
				</div>
				
				
				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Product Settings
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a>
							</div>
						</div>
						
						<div class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td><label for="Limit Code"><strong>Limit Code </strong></label></td>
										<td><s:property value='#respData.limitCodeVal' />&nbsp; &nbsp; &nbsp; &nbsp; 
										<input type="hidden" value="<s:property value='#respData.limitCodeVal' />" id="limitCode" name="limitCode" /> 
												<input type="button" class="btn btn-success" onclick="" id="view" name="view" value="View" /></td>
										<td><label for="Fee Code"><strong>Fee Code </strong></label></td>
										<td><s:property value='#respData.feeCodeVal' />&nbsp;&nbsp; &nbsp; &nbsp;
										<input type="hidden" value="<s:property value='#respData.feeCodeVal' />" id="feeCode" name="feeCode" /> 
											    <input type="button" class="btn btn-success" onclick="" id="view1" name="view1" value="View" /></td>
									</tr>
								</table>
							</fieldset>
						</div>

						<%-- <div class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td><label for="Limit Code"><strong>Limit Code<font color="red">*</font> </strong></label></td>
										<td><s:select cssClass="chosen-select" 
										        headerKey=""
										        headerValue="Select" 
										        list="#respData.LIMIT_CODE" 
										        value="#respData.limitCodeVal" 
												name="limitCode" 
												id="limitCode" 
												requiredLabel="true"
												theme="simple" 
												data-placeholder="Choose Limit Code..."
												required="true" /> &nbsp; &nbsp; &nbsp; 
												<span id="selalrtmsg1" style="color: red; font-weight: bold;"></span>&nbsp; &nbsp; &nbsp; &nbsp; 
												<input type="button" class="btn btn-success" onclick="" id="view" name="view" value="View" /></td>
										<td><label for="Fee Code"><strong>Fee Code<font color="red">*</font> </strong></label></td>
										<td><s:select cssClass="chosen-select" 
										                headerKey=""
												        headerValue="Select" 
												        list="#respData.FEE_CODE" 
												        value="#respData.feeCodeVal"
														name="feeCode" 
														id="feeCode" 
														requiredLabel="true"
														theme="simple" 
														data-placeholder="Choose Fee Code..." required="true" /> &nbsp; &nbsp; &nbsp; <span id="selalrtmsg" style="color: red; font-weight: bold;"></span>&nbsp;
													    &nbsp; &nbsp; &nbsp; 
											    <input type="button" class="btn btn-success" onclick="" id="view1" name="view1" value="View" /></td>
									</tr>
								</table>
							</fieldset>
						</div> --%>
					</div>
					
					<div id="light" >
						<br></br> <br>
						<br>
						<br>
						<br>
						<br>
					

						<div class="row-fluid sortable">
							<div class="box span12">
								<div class="box-header well">
									<i class="icon-edit"></i> Fee Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i
											class="icon-cog"></i></a> <a href="#"
											class="btn btn-minimize btn-round"><i
											class="icon-chevron-up"></i></a>
									</div>
								</div>
								<div class="box-content">
									<table width="950" border="0" cellpadding="5" cellspacing="1"
										class="table table-striped table-bordered bootstrap-datatable ">
										<tr class="even">
											<td width="20%"><label for="Fee Code"><strong>Fee
														Code</strong></label></td>
											<td width="30%"><span id="feecodedata"></span></td>
											<td width="20%"><label for="Fee Description"><strong>Fee
														Description</strong></label></td>
											<td width="30%"><span id="feedescdata"></span></td>
										</tr>
									</table>
								</div>
							</div>
						</div>

						<div class="row-fluid sortable">
							<div class="box span12">
								<div class="box-header well">
									<i class="icon-edit"></i> Fee Transaction Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i
											class="icon-cog"></i></a> <a href="#"
											class="btn btn-minimize btn-round"><i
											class="icon-chevron-up"></i></a>
									</div>
								</div>
								<div class="box-content">

									<table width="100%"  class="table table-striped table-bordered bootstrap-datatable datatable"  id="DataTables_Table_0"  >
										<thead id="policyUWHeader">
											<tr   id="maintr2">

												<th>S No</th>
												<th>Transaction</th>
												<th>Frequency</th>
												<th>Fee/Percentile</th>
												<th>Value</th>
												<th>Criteria</th>
												<th>From Value</th>
												<th>To Value</th>
											</tr>
										</thead>

										<tbody id="searchBody2" >

										</tbody>
									</table>

								</div>
							</div>
						</div>

						<div align="center">
							<a class="btn btn-danger" href="#" onClick="lightbox_close()">Close</a>
						</div>
					</div>

					<div id="fade"></div>
					<div id="light1">
						<br></br> <br>
						<br>
						<br>
						<br>
						<br>
						

						<div class="row-fluid sortable">
							<div class="box span12">
								<div class="box-header well">
									<i class="icon-edit"></i> Limit Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i
											class="icon-cog"></i></a> <a href="#"
											class="btn btn-minimize btn-round"><i
											class="icon-chevron-up"></i></a>
									</div>
								</div>
								<div class="box-content">
									<table width="950" border="0" cellpadding="5" cellspacing="1"
										class="table table-striped table-bordered bootstrap-datatable ">
										<tr class="even">
											<td width="20%"><label for="Limit Code"><strong>Limit
														Code</strong></label></td>
											<td width="30%"><span id="limitcodedata"></span></td>
											<td width="20%"><label for="Limit Description"><strong>Limit
														Description</strong></label></td>
											<td width="30%"><span id="limitdescdata"></span></td>
										</tr>
									</table>
								</div>
							</div>
						</div>

						<div class="row-fluid sortable">
							<div class="box span12">
								<div class="box-header well">
									<i class="icon-edit"></i> Limit Transaction Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i
											class="icon-cog"></i></a> <a href="#"
											class="btn btn-minimize btn-round"><i
											class="icon-chevron-up"></i></a>
									</div>
								</div>
								<div class="box-content">

									<table width="100%"  class="table table-striped table-bordered bootstrap-datatable datatable"  id="DataTables_Table_0"  >
										<thead id="policyUWHeader2">
											<tr   id="maintr22">

												<th>S No</th>
												<th>Transaction </th>
												<th>Frequency</th>
												<th>Maximum Count</th>
												<th>Minimum Amount</th>
												<th>Maximum Amount</th>
											</tr>
										</thead>

										<tbody id="searchBody22" >

										</tbody>
									</table>
								</div>
								<input type="hidden" name="newVal"     id="newVal" /> 
				                <input type="hidden" name="oldVal"     id="oldVal" />
				                <input type="hidden" name="columnVal"  id="columnVal"  value="Plastic Code,Limit Code,Fee Code" />
							</div>
						</div>

						<div align="center">
							<a class="btn btn-danger" href="#" onClick="lightbox_close1()">Close</a>
						</div>
					</div>
					<div id="fade1"></div>
				</div>
			 <div class="form-actions">
				<a  class="btn btn-danger" href="#" onClick="getProductScreen()">Back</a> &nbsp;&nbsp;
				<a  class="btn btn-success" href="#" onClick="createProductModify()">Modify</a>
			</div>	 
	</div>
 </form>
</body>
</html>
