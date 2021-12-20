<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %> 
 <script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery-ui-1.8.21.custom.min.js'></script> 
 <link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery-ui-1.8.21.custom.min.css' />  
 

<style type="text/css">
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
.errors {
color: red;
}
</style>
<script type="text/javascript">
var acctRules = {
		   rules : {
			   accountNumber : { required : true},
			   aliasName : { required : false}
			   
		   },  
		   messages : {
			   accountNumber : { 
			   		required : "Please enter Account Number."
				},
				aliasName : { 
			   		required : "Please enter Alias Name."
				}
		   } 
		 };
		
		var subrules = {
				   rules : {
					   langugae : { required : true},
					   telephone : { required: true },
					  
				   },  
				   messages : {
					   langugae : { 
					   		required : "Please Select Language."
						},
						telephone : { 
					   		required : "Please Input Mobile Number."
						}
				   } 
				 };
		
 
$(function() {   
	
/* 	$("#dialog").dialog({
			autoOpen: false,
			modal: false,

	      
   }); */
	
   
   
	
	
	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/modregcustinfo.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){ 
		var finalData = "";
		 var allVals = [];
		 
		//$('#billerMsg').text('');
		//$('#error_dlno').text(''); 
		//$("#form1").validate(subrules);
		
		
		
		
		 /* if($("#form1").valid() ) {  */ 
			
			 
			// submitfun();
				 $("#dialog").dialog({
				      buttons : {
				        "Confirm" : function() { 
				        	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fraudruleexemptdetack.action';
							$("#form1").submit();
				        },
				        "Cancel" : function() {
				           $(this).dialog("close");
				        }
				      }
				    });
				  
				  //  $("#dialog").dialog("open");  
				 
				 return true; 
			  
			 
		/*  } else {
			 //alert("in else");
			 return false;
		 } */
			
	});
});	




$(function() {  
	
	
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
		    
		  
		    
		    $(document).on('click','a',function(event) {
		    	var v_id=$(this).attr('id');
				var queryString = ""; 
				var v_action ="";
				
				var index1 = $(this).attr('index');  
				
				
				
				if(v_id =="actdeactacc") {
						/* v_action = "actdeactacc";
						queryString += 'accountid='+$("#FID"+index1).val()+'&closed='+v_id;  */
						//alert($("#FID"+index1).val());
						$("#fraudid").val($("#FID"+index1).val());
						
						$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/fraudruleexemptdetconfirm.action';
						$("#form1").submit();
			    } 
				
				
				
		    });
		   
	  //alert($('#product').val());  	
	    	
});



function submitfun(){
	
	var val1="";
	var chdata=$("#plimitchannel").val();
	var chdataspt=chdata.split("\|");
	for(i=0;i<chdataspt.length-1;i++){
		
		val1=val1+chdataspt[i].split("#")[0]+"#"+$("#"+chdataspt[i].split("#")[0]).val()+"|"
	}
	
	$("#plimitchannel").val(val1)
}


</script>

</head> 

<body>

		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Fraud Rule Exempt</a>  </li> 
 					</ul>
				</div>  

				<table>
					<tr>
						<td colspan="3">
							<div class="messages" id="messages"><s:actionmessage /></div>
							<div class="errors" id="errors"><s:actionerror /></div>
						</td>
					</tr>
				</table>
<form name="form1" id="form1" method="post"> 
		<div class="row-fluid sortable"> 
			<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Customer Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>  
					
				<div class="box-content">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">   
						<tr class="even">
							<td width="25%"><label for="From Date"><strong>Customer Id</strong></label></td>
							<td width="25%"><s:property value='accBean.customercode' /><input type="hidden" name="customercode"  id="customercode"   value="<s:property value='accBean.customercode' />"   /></td>
							<td width="25%"><label for="To Date"><strong>Customer Name</strong></label> </td>
							<td width="25%"><s:property value='accBean.fullname' /> <input type="hidden" name="fullname"  id="fullname"   value="<s:property value='accBean.fullname' />"   />  </td>
						</tr>  
						<tr class="even">
							<%-- <td ><label for="From Date"><strong>Branch Code</strong></label></td>
							<td ><s:property value='accBean.branchcode' /> <input type="hidden" name="branchcode"  id="branchcode"   value="<s:property value='accBean.branchcode' />"   />  </td> --%>
							<td><label for="To Date"><strong>Mobile Number</strong></label> </td>
					       <td ><s:property value='accBean.telephone' />
					       <input type="hidden" value='<s:property value='accBean.telephone' />'  name="telephone" id="telephone" /> 
								
								<input type="hidden" value="<s:property value='accBean.telco' />" style="width:85px;"  name="telco" id="telco"/>&nbsp;
								<input type="hidden" value="<s:property value='accBean.isocode' />" style="width:25px;"  name="isocode" id="isocode"/>&nbsp;
								 
 							</td>
								
 							</td>
							<td  ><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td><s:property value='accBean.idnumber' />
							<input type="hidden" style="width:85px;"  name="idnumber"  id="idnumber"   value="<s:property value='accBean.idnumber' />"   />  </td>
					
						</tr>
						<tr class="even">
							<td ><label for="From Date"><strong>Email ID</strong></label></td>
							<td ><s:property value='accBean.email' />
							<input type="hidden" name="email"  id="email"   value="<s:property value='accBean.email' />"   />  </td>
							<td ><label for="To Date"><strong>Preferred Language </strong></label> </td> 
							<td ><s:property value="accBean.langugae" />
							<input type="hidden" name="langugae"  id="langugae"   value="<s:property value='accBean.language' />"   />  
							
							         
							    <input type="hidden" name="institute"  id="institute"   value="<s:property value='accBean.institute' />"   />
						       	<input type="hidden" name="newAccountData" id="newAccountData" value="<s:property value='accBean.newAccountData' />" />
								<input type="hidden" name="multiData"  id="multiData"   value="<s:property value='accBean.multiData' />"  />
								<input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
							</td>	  
						</tr>
						<tr class="even">
							<td ><label for="Product"><strong>Current Product</strong></label></td>
							<td ><s:property value='accBean.product' />
							</td>
							 <td><label for="Description"><strong>Description</strong></label></td>
					       <td><s:property value='accBean.prodesc' /></td>  
						</tr>
						
						
								
						
				 </table>
				
				 
				 <input type="hidden"   name="apptype"  id="apptype"   value="<s:property value='accBean.apptype' />"   />
				</fieldset> 
				
				
				<div class="box-content"  > 
		  			<span id="multi-row" name="multi-row" style="display:none"> </span> 
							<table class="table table-striped table-bordered bootstrap-datatable dataTable" 
										id="ex-mytable" style="width: 100%;">
							  <thead>
									<tr>
										<th>Sno</th>
										<th>Fraud Id</th>
										<th>Fraud Description </th>
										<th>Status </th>
										<th>Action </th>
										
 									</tr>
							  </thead>    
							  <tbody id="tbody_data">  
							  <s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{accBean.multiData}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
									</s:bean>
									<s:iterator value="#jsonToList.data" var="mulData"  status="mulDataStatus" >
									<tr> 
									<td><s:property value="#mulDataStatus.index+1" /></td>
									
									  <s:set value="" var="userstatus"   />
											 <s:set value="" var="statusclass"   /> 
											 <s:set value="" var="text1"  /> 
											 <s:set value="" var="text"  /> 
											 		
									
									<s:generator val="%{#mulData}" var="bankDat" separator="," converter="%{myConverter}"  > 
													<s:iterator status="itrStatus" var="itrVar"  >
													<s:if test="#itrStatus.index == 2" > 
													<s:set var="indexVal" value="%{#attr.itrVar}" /> 
														
													
														
														<s:if test="#indexVal == 'Active'" >  
																 <s:set value="%{'label-success'}" var="userstatus"   />
																 <s:set value="%{'btn btn-danger'}" var="statusclass"   /> 
																 <s:set value="%{'Deactivate'}" var="text1"   /> 
																 <s:set value="%{'icon-ok-sign'}" var="text" />  
																
															 </s:if> 
															 <s:if test="#indexVal == 'De-Active'" > 
																 <s:set value="%{'label-warning'}" var="userstatus"/>
																 <s:set value="%{'btn btn-success'}" var="statusclass"/> 
																 <s:set value="%{'Activate'}" var="text1"/> 
																 <s:set value="%{'icon-lock'}" var="text"/>
															 </s:if> 
													
													<td>
													
													<a href='#' class='label <s:property value="#userstatus" />' index="<s:property value='#itrStatus.index' />" ><s:property value="#indexVal" /></a></td>
													 <td><a class='<s:property value="#statusclass" />' href='#' id='actdeactacc' index="<s:property value='#mulDataStatus.index' />" title='<s:property value="#text1" />'  data-rel='tooltip' > <i class='icon <s:property value="#text" /> icon-white'></i> </a>&nbsp;</td> 
													</s:if>
													<s:elseif test="#itrStatus.index == 0" >
														
																<td><s:property  />
																<input type="hidden" name="FID<s:property value='#mulDataStatus.index' />" id="FID<s:property value='#mulDataStatus.index' />"  value="<s:property  />"/>
																</td>
														</s:elseif> 
													<s:else>
																<td><s:property  /></td>
														</s:else>
													
													
													</s:iterator>
									
									</s:generator>
									</tr>
									</s:iterator> 
							   </tbody>
							   				
							  </table>
			</div>
				
				
				</div>
				</div> 
				<input type="hidden"  name="plimitchannel" id="plimitchannel"  value="<s:property value='accBean.plimitchannel' />" />
				<input type="hidden"  name="climitchannel" id="climitchannel"  value="<s:property value='accBean.climitchannel' />" />
					<input type="hidden"  name="product" id="product"  value="<s:property value='accBean.product' />" />
				 	<input type="hidden"  name="prodesc" id="prodesc"  value="<s:property value='accBean.prodesc' />" />
				 	
				<input type="hidden"  name="fraudid" id="fraudid"   />
		</div> 	
</form>	


<div id="dialog" title="Confirmation Required" style="display:none">
		   Proceed ? <div id="dia1"></div><font color="red"><div id="dia2"></div></font>
		</div>
		<div > 
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">Back </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Submit</a>	
			<span id ="error_dlno" class="errors"></span>	 
		</div> 
		
	</div> 
 <script type="text/javascript">
$(function(){
	
	
	
	$('#accountNumber').live('keypress',function(){
		//console.log($(this).length);
		if($(this).length == 0){
			$('#billerMsg').text('');
		}
	});
	
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
});  
</script>
</body> 
</html>