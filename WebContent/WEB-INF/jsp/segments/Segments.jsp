<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>

<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
<script language="Javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">

<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>

<link href="${pageContext.request.contextPath}/css/bootstrap-colorpicker.css" rel="stylesheet">
  <script src="${pageContext.request.contextPath}/js/bootstrap-colorpicker.js"></script>
  <link href="${pageContext.request.contextPath}/css/multi-select.css" rel="stylesheet" type="text/css">
  


<style>

 ul.tree, ul.tree ul {
    list-style: none;
     margin: 0;
     padding: 0;
   } 
   ul.tree ul {
     margin-left: 10px;
   }
   ul.tree li {
     margin: 0;
     padding: 0 7px;
     line-height: 20px;
     color: #369;
     font-weight: bold;
     /* border-left:3px solid rgb(100,100,100); */
     

   }
   ul.tree li:last-child {
       border-left:none;
   }
   ul.tree li:before {
      position:relative;
      top:-0.3em;
      height:2em;
      width:12px;
      color:white;
     /*  border-bottom:3px solid rgb(100,100,100); */
      content:"";
      display:inline-block;
      left:-7px;
   }
   ul.tree li:last-child:before {
       /* border-left:3px solid rgb(100,100,100);  */  
       
   }
   
   .spantag { 
  
	  color: #0066FF; 
	  font-family:arial; 
	  font-size: 16px; 
	  border-bottom:5px solid #000000;
	}

</style>

<script type="text/javascript">


$(function(){
	 
	 
	 
	 
	 var formInput="reporttype=SERVICES";
	 $.ajax({
	 			
	 		     url : '<%=request.getContextPath()%>'+'/<%=appName %>/segmentservices.action',
	 		     type: "POST",
	 		     data : formInput,
	 		     success: function(data, textStatus, jqXHR)
	 		     {
	 					
	 					console.log("data "+JSON.stringify(data.rptresponseJSON));
	 					
	 					var userlist = data.responseJSON.ACCOUNTNO;
	 					
	 					
	 					
	 					var i;
	 					
	 					for(i=0;i<userlist.length;i++)
	 					{
	 							$('#pre-selected-options').append(getOptionFor((userlist[i].accountno).split("@")[0],(userlist[i].accountno).split("@")[1]));
	 							$('#pre-selected-options').trigger("liszt:updated");
	 					
	 							
	 					}
	 					 $('#pre-selected-options').multiSelect(); 	
	 		     }
	 
	 		 });
	 
	 
	 
	 var formInput="reporttype=LIFESTYLE";
	 $.ajax({
	 			
	 		     url : '<%=request.getContextPath()%>'+'/<%=appName %>/segmentservices.action',
	 		     type: "POST",
	 		     data : formInput,
	 		     success: function(data, textStatus, jqXHR)
	 		     {
	 					
	 					console.log("data "+JSON.stringify(data.rptresponseJSON));
	 					
	 					var userlist = data.responseJSON.ACCOUNTNO;
	 					
	 					
	 					
	 					var i;
	 					
	 					for(i=0;i<userlist.length;i++)
	 					{
	 							$('#pre-selected-options1').append(getOptionFor((userlist[i].accountno).split("@")[0],(userlist[i].accountno).split("@")[1]));
	 							$('#pre-selected-options1').trigger("liszt:updated");
	 					
	 							
	 					}
	 					 $('#pre-selected-options1').multiSelect(); 	
	 		     }
	 
	 		 });
	
	 
	    /*  var plasticCode = '${responseJSON}'; */
	    /* var binDataList=jQuery.parseJSON('${responseJSON.BINTYPE_LIST}'); */
	    //console.log("ViewDetails"+plasticData.get[0]);
	 /*  buildbranchtable();
	   var viewBinGroup = '${responseJSON.bingroupcode}';
	   console.log("ViewBinGroup"+viewBinGroup); */
	  
});

function getOptionFor(thisValue,optionValue)
{
	
	 if(optionValue=="1"){
		 return "<option value='"+thisValue.split("-")[0]+"' selected>"+thisValue+"</option>"; 
	 }else{
		 return "<option value='"+thisValue.split("-")[0]+"' >"+thisValue+"</option>"; 
	 }
		
}

 
 
 $(function () { 
	 
	 var config = {
		      '.chosen-select'           : {},
		      '.chosen-select-deselect'  : {allow_single_deselect:true},
		      '.chosen-select-no-single' : {disable_search_threshold:10},
		      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		      '.chosen-select-width'     : {width:"95%"}
		    };
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    }
		    
		    
		   

	 
/*  $('#colours,#services,#campaigns,#lifestyle,#advertised,#tonevoice,#faqs,#cpersons').on('click', function() {
	//alert(this.id);
		if($("#"+this.id).is(':checked')){
			$("#d"+this.id).css("display","");
			
		}else{
			$("#d"+this.id).css("display","none");
		}
		
		}); */
 
		    $("#advertisedimage").change(function () {
		        $("#dvPreview").html("");
		        var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp)$/;
		        if (regex.test($(this).val().toLowerCase())) {
		            if ($.browser.msie && parseFloat(jQuery.browser.version) <= 9.0) {
		                $("#dvPreview").show();
		                $("#dvPreview")[0].filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = $(this).val();
		            }
		            else {
		                if (typeof (FileReader) != "undefined") {
		                    $("#dvPreview").show();
		                    $("#dvPreview").append("<img />");
		                    var reader = new FileReader();
		                    reader.onload = function (e) {
							//alert(e.target.result);
		                        $("#dvPreview img").attr("src", e.target.result);
		                        $("#dvPreviews").val(e.target.result);
		                    }
		                    reader.readAsDataURL($(this)[0].files[0]);
		                } else {
		                    alert("This browser does not support FileReader.");
		                }
		            }
		        } else {
		            alert("Please upload a valid image file.");
		        }
		    });
 
 });
 
 
 function confirmProduct(){
		$('#services').val($('#pre-selected-options').val())
		//alert($("#services").val());
		$('#lifestyle').val($('#pre-selected-options1').val())
		//alert($("#lifestyle").val());
		
		<%-- if(($("#branchdetails").val()).indexOf("ALL,")>= 0){
			$('#errors').text("Product Branch Restriction Setting not allowed after ALL Branch, assing to spacefic Branch");
			return false;
		}--%>
		
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/segmentsconfirm.action'; 
		$("#form1").submit();
		return true; 
	}
 
 
 function funccc(v){
	$('#campaigndis').val(v)
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
					<li><a href="agentregistration.action">Segments Creation</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						Segments Creation
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								<tr class="odd">
									<td width="25%"><label for="fname"><strong>Segments<font color="red">*</font></strong></label></td>
									<td width="25%">
									
									 <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'PERSONALBANKCUSTOMER':'Personal banking customers','ROYALBANKCUSTOMER':'Royalty banking customers','ELITEBANKCUSTOMER':'Elite banking customers','SMEBANKCUSTOMER':'SME banking customers'}" 
							         name="segments" 
							         id="segments"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
									</td>
									<td width="25%"><label for="fname"><strong>Sub Segments<font color="red">*</font></strong></label></td>
									<td width="25%">
									
									<s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'ALL':'ALL','YOUTH':'Youth','YOUTHENT':'Young Entrepreneur','AVGJJ':'Average Joe and Jane','SALENT':'Salaried Entrepreneur','ELVIS':'Elvis','SMEBANKCUSTOMER':'MOMANDPOP','SMEBANKCUSTOMER':'Mom and Pop','PROFESS':'Professionals','GOLDENAGR':'Golden Ager','SENCITIZEN':'Senior Citizen'}" 
							         name="subsegments" 
							         id="subsegments"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           />
									
									</td>
								</tr>
								
								
															
							</table>
							
							
							
							
	
						</fieldset>
						 
						<div id="colorSelector"><div style="background-color: #0000ff"></div></div>
						<ul class="tree" ><strong>Segments Settings</strong>
						
						    <li>
						    
						       <ul>
						        <li class="last" id="dcolours" name="dcolours" >
								<div class="box-header well" data-original-title>
									Colours Creation
								</div>
						         <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr>
								<td width="50%">Set colours </td>
								<td width="50%">
								
								<div id="cp2" class="input-group colorpicker-component" title="Using input value">
								  <input type="text" id="colors" name="colors" class="form-control input-lg" value="#DD0F20" readonly/>
								  <span class="input-group-addon"><i></i></span>
								</div>
								
								</td>
								</tr>
								</table>
								
						        </li>
						       </ul>
						      </li>
						      
						      <li>
						      
						    
						       <ul>
						        <li class="last" id="dservices" name="dservices"  >
						        <div class="box-header well" data-original-title>
									Select services
								</div>
  								<table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
									<tr>
									<td >
									<div align="center">
										<select id='pre-selected-options' name='pre-selected-options' multiple='multiple' >
									   
									 </select>
									</div>
									<input type="hidden"  id="services" name="services"  />
									</td>
									</tr>
									
								</table>
								</li>
						       </ul>
						      </li>
						      
						      <li>
						      
						     
						       <ul>
						        <li class="last" id="dcampaigns" name="dcampaigns" >
						        <div class="box-header well" data-original-title>
									campaigns
								</div>
						         <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr>
								<td width="50%">campaigns </td>
								<td width="50%">
								<input type="radio" name="campaigns" id="NO" checked onclick=funccc(this.id)> No <input type="radio" name="campaigns" id="YES" onclick=funccc(this.id)> YES
								<input type="hidden"  id="campaigndis" name="campaigndis" value="NO" /> 
								</td>
								</tr>
								</table>
						        </li>
						         
						       </ul>
						      </li>
						      
						      <li>
						      
						       <ul>
						        <li class="last" id="dlifestyle" name="dlifestyle" >
						        <div class="box-header well" data-original-title>
									life style
								</div>
						        <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
									<tr>
									<td >
									
									<div align="center">
										<select id='pre-selected-options1' name='pre-selected-options1' multiple='multiple' >
									   
									 </select>
									</div>
									<input type="hidden"  id="lifestyle" name="lifestyle"  />
									 </td>
									</tr>
									
								</table>
						        </li>
						       </ul>
						      </li>
						      
						      <li>
						      
						       <ul>
						        <li class="last" id="dadvertised" name="dadvertised" >
						         <div class="box-header well" data-original-title>
									partners/ benefits advertised
								</div>
						        <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr>
								<td width="50%">Image Upload </td>
								<td width="50%"> <input id="advertisedimage" type="file" /></td>
								</tr>
								<tr>
								<td width="50%">Image Preview </td>
								<td width="50%"><div id="dvPreview"></div>
								
								<input type="hidden"  id="dvPreviews" name="dvPreviews"  />
								 </td>
								</tr>
								</table>
						        </li>
						       </ul>
						      </li>
						      
						       <li>
						      
						       
						       <ul>
						        <li class="last" id="dtonevoice" name="dtonevoice"  >
						        <div class="box-header well" data-original-title>
									Tone of Voice
								</div>
						        <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr>
								<td width="50%">Enter Tone of Voice </td>
								<td width="50%">
								 <input id="tonev" name="tonev" type="file" />
								<!-- <input type="hidden" id="tonev" name="tonev"/>  -->
								</td>
								</tr>
								</table>
								
						        
						        </li>
						       </ul>
						      </li>
						      
						       <li>
						       
						        
						       <ul>
						        <li class="last" id="dfaqs" name="dfaqs"   >
						        <div class="box-header well" data-original-title>
									FAQs
								</div>
						        <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr>
								<td width="50%">Enter FAQs </td>
								<td width="50%"><textarea rows="5" cols="5" id="faqs" name="faqs"></textarea> </td>
								</tr>
								</table>
						        
						        </li>
						       </ul>
						      </li>
						      
						       <li>
						       
						       
						       <ul>
						        <li class="last" id="dcpersons" name="dcpersons"  >
						        <div class="box-header well" data-original-title>
									bank contact persons (RM)
								</div>
						        <table width="500"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								<tr>
								<td width="50%">Enter bank contact persons </td>
								<td width="50%"><input type="text" id="rms" name="rms"/> </td>
								</tr>
								</table>
						        
						        </li>
						       </ul>
						      </li>
						      
						   </ul>
						
						
					</div>
				</div>
			</div>
			
			
	
		
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit"  onClick="confirmProduct()" ></input> 
			</div>  

			
			
		
			</div>
			

</form>
<script>
  $(function () {
    $('#cp2').colorpicker();
  
  });
</script>
<script src="${pageContext.request.contextPath}/js/jquery.multi-select.js"></script>
</body>
</html>
