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


<script type="text/javascript">
	
var userictadminrules = {
 		
 		rules : {
 			
 			limitcode : { required : true } ,
 		},		
 		messages : {
 			
 		
 			limitcode : { 
		 				required : "Please Select Limit Code.",
 						}
 			
 		} 
 	};
			
			$(function () { 
				
				 $('#limitcode').on('change', function() {
					 var v=this.value; 
					  var vf=v.split("-")[0];
					
					if(vf=="RAASFILE"){
						$('#filetypess').text("csv");
					}
					  
				 });
				
				 
				 
				 var size="";
					var type="";
				 $('#file').bind('change', function() {

					  //this.files[0].size gets the size of your file.
					 var fSExt = new Array('Bytes', 'KB', 'MB', 'GB');
					    fSize = this.files[0].size; i=0;while(fSize>900){fSize/=1024;i++;}
					    
					    
					   
					    
					  $('#messages').html(
					  	"<p>File information: <strong>" + this.files[0].name +
						"<br></strong> type: <strong>" + this.files[0].type +
						"<br></strong> size: <strong>" + (Math.round(fSize*100)/100) +
						"</strong> "+fSExt[i]+"</p>");
					 
					  size=(Math.round(fSize*100)/100);
					  type=fSExt[i];

					});
				
				
				var userStatus = '${responseJSON.status}';
				
				
				
				
				 $("#form1").validate(userictadminrules); 
				 
				
				
				        $('#btn-submit').live('click', function () { 
				        	
				        	var fsize = $('#file')[0].files[0].size; //get file size
			    			var ftype = ($('#file').val()).replace(/^.*\./, ''); // get file type
			    			var filename = $('input[type=file]').val().replace(/.*(\/|\\)/, '');
				        
				        	 if(!$('#file').val()){
				    	    	 $("#errormsg").html("Please Upload File .");
				    				return false 
				    	     }else{
				    	    	 
				    	    	 if(type=="MB" && size>=50) 
					    			{
					    				$("#errormsg").html("Too big file ,Please reduce the size .");
					    				return false
					    			}else{
					    				var filesupp=$('#filetypess').text();
					    				switch(ftype.toLowerCase())
					    		        {
					    		            case filesupp:
					    		                break;
					    		            default:
					    		            	$("#errormsg").html("<b>"+ftype+"</b> Unsupported file type!");
					    						return false;
					    		        }
					    			}
				    	    	
				    	    	//alert(filename.substr(8,9));
				    	    	if(/^[0-9]*$/.test(filename.substr(0,8)) == false) {
				    	    		$("#errormsg").html('file name required  first 8  digit is current date :: file name example :: 01122017_ABCDEF.csv');
							    	return false;
				    	    	}
				    	    	if(/^[0-9_]*$/.test(filename.substr(0,9)) == false) {
				    	    		$("#errormsg").html('file name required  9  Character  is _ :: file name example :: 01122017_ABCDEF.csv');
							    	return false;
				    	    	}
				    	    	
								if(/^[a-zA-Z0-9_.]*$/.test(filename) == false) {
								    	$("#errormsg").html('file name contains illegal characters .please do rename your upload file name :: file name example :: 01122017_ABCDEF.csv');
								    	return false;
								}
								    

					    	    	   
				    	    		var queryString9 = "method=validatecnt&transaction="+filename;
				    				
				    				$.getJSON("postJson.action", queryString9,function(data) {
				    					//alert(data.finalCount);
				    					if((data.finalCount)==99){
				    						$("#errormsg").html('file name required  first 8  digit is current date ');
									    	return false;
				    					}else if((data.finalCount)!=0){
				    						$("#errormsg").html('file name already exists');
									    	return false;
				    					}else{
				    						var url="${pageContext.request.contextPath}/<%=appName %>/bulkdsaregprocess.action"; 
						    				$("#form1")[0].action=url;
						    				$("#form1").submit(); 
				    					}
				    				});
				    	    	
				    	    	 
				    				
					    			
				    	     }	
				        }); 
	    	     
		    });  

			$.validator.addMethod("regex", function(value, element, regexpr) {          
				 return regexpr.test(value);
			   }, ""); 
			
			
			function redirectHome(){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
				$("#form1").submit();
				return true;
			}
			
			
	

			 $(function() {
				 
					var config = {
				      '.chosen-select'           : {},
				      '.chosen-select-deselect'  : {allow_single_deselect:true},
				      '.chosen-select-no-single' : {disable_search_threshold:10},
				      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				      '.chosen-select-width'     : {width:"95%"}
				    }
					
				    for (var selector in config) {
				      $(selector).chosen(config[selector]);
				    } 
				  	 
					 
					
				});			
</script>
<s:set value="responseJSON" var="respData" />
</head>
<body>
<form name="form1" id="form1" method="post" enctype="multipart/form-data" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					 <li><a href="bulkdsareg.action">File Upload</a></li>
					
				</ul>
			</div>
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						File Upload
						<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
					
				

					<div id="primaryDetails" class="box-content">
						<fieldset>
							
							
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="check-kra-details" >
								
								
								<tr class="even">
								<td width="25%"><label for="gender"><strong>File Upload Type <font color="red">*</font></strong></label></td>
									<td width="25%">
									<s:select cssClass="chosen-select" headerKey=""
												headerValue="Select" list="#respData.LIMIT_CODE"
												name="limitcode" id="limitcode" requiredLabel="true"
												theme="simple" data-placeholder="Choose Limit Code..."
												required="true" /> 
									</td>
									<td width="25%"></td>
									<td width="25%"></td>
									
								</tr>
								
								
								<tr class="odd">
									<td><div >Upload Type allowed: <span id="filetypess"></span></div><div> Maximum Size 50 MB</div>
									<strong><label for="file"><strong>Upload</strong><font color="red">*</font></label></strong></td>
									<td><input type="file" id="file" name="file" ></td>
									<td></td>
									<td></td>
								</tr>
															
							</table>
							
							
						<div id="messages"></div>
							
	
						</fieldset>
						
					</div>
				</div>
			</div>
			
			
	
		
			
			<div class="form-actions" align="left"> 
				
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectHome();" value="Home" />
				 <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100"  ></input> 
			</div>  

			
			
		
			</div>
			

</form>

</body>
</html>
