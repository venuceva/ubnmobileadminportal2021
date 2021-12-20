<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap-datepicker.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.timepicker.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.ptTimeSelect.css" />
  <link rel="stylesheet" type="text/css" href="https://jonthornton.github.io/jquery-timepicker/jquery.timepicker.css">
  
  

<style type="text/css">
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
span.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
</style>




<script type="text/javascript" >


var val = 1;
var rowindex = 0;
var colindex = 0;
var flagAcc = true;
/* var smsTemprules = {
	rules : {
		mobno : { required : true } ,
		message : { required : true }
	},
	messages : {
		 
		   mobno : {
				required : "Please Enter Mobile Number."
			},
			
			message : {
				required : "Please Enter Message."
			}
	  }}; */
  $(document).ready(function() {
	  
	  
	  
	  $("#campmsg").keyup(function(){
		    el = $(this);
		    if(el.val().length >= 500){
		        el.val( el.val().substr(0, 500) );
		    } else {
		        $("#charNum").text(500-el.val().length);
		    }
		});
	  

	  $('#campfields').change(function(){
		    $('#campmsg').val($('#campmsg').val()+" "+$('#campfields option:selected').val());
		});
	  
		$('#btn-submit').click(function()
		{
			//$("#form1").validate(smsTemprules);
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/campaigncreationconf.action';
			$("#form1").submit();
		});
	 
	   $('#btn-cancel').live('click', function () {
		   var url="${pageContext.request.contextPath}/<%=appName %>/home.action";
			$("#form1")[0].action=url;
			$("#form1").submit();
	   }); 
  
  });
 

</script>
</head>
<body>
	<form name="form1" id="form1" method="post" action="">
  
			<div id="content" class="span10">
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
 				  <li><a href="#">Campaign Management</a></li>
				</ul>
			</div>
					
			
		 <div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Campaign Creation
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="box-content">
				  <fieldset>
					<table width="950"  border="0" cellpadding="5" cellspacing="1" id="user-details"
						class="table table-striped table-bordered bootstrap-datatable " >
						 <tr class="odd" width="20%">
							    <td width="25%"><strong><label for="Campaign Name"><strong>Campaign Name</strong><font color="red">*</font></label></strong></td>
								<td width="75%"><input name="campname" type="text" id="campname" required="required"  class="field"  value="<s:property value='campname' />" /> </td>
						</tr>
						 <tr class="even">		
								<td width="25%"><strong><label for="Campaign Description"><strong>Campaign Description</strong><font color="red">*</font></label></strong></td>
								<td width="75%"><textarea style="margin: 0px 0px 9px; width: 500px; height: 100px;" name="campdesc"   id="campdesc" required="required"  class="field"   value="<s:property value='campdesc' />" /></textarea></td> 
						 </tr>
						  <tr class="even">		
								<td width="25%"><strong><label for="Campaingn Parameters"><strong>Campaign Codes Avaliable</strong><font color="red">*</font></label></strong></td>
								<td width="75%"><select id="campfields" name="regtype"  required='true' data-placeholder="Select Fields" style="width:290px">
				       				<option value="SEL">Select</option>
						            <option value="CUSTOMER_NAME" Selected>Customer Name</option>
						            <option value="MOBILE_NUMBER">Mobile Number</option>
						        </select> </td> 
						 </tr>
						  <tr class="even">		
								<td width="25%"><strong><label for="Campaign SMS"><strong>Campaign Message</strong><font color="red">*</font></label></strong></td>
								<td width="75%"><textarea maxlenght="500" style="margin: 0px 0px 9px; width: 500px; height: 100px;" name="campmsg"   id="campmsg" required="required"  class="field"   value="<s:property value='campmsg' />" /></textarea> &nbsp;<span style="valign:bottom" id="charNum">500 </span> Characters Left</td> 
						 </tr>
						 <%-- <tr class="even">		
								<td width="25%"><strong><label for="Service Pack"><strong>Select Service Pack</strong><font color="red">*</font></label></strong></td>
								<td width="75%"><select id="camppack" name="camppack"  required='true' data-placeholder="Select Service Pack" style="width:290px">
				       				<option value="SEL">Select</option>
						            <option value="CUSTOMER_NAME" Selected>Customer Name</option>
						            <option value="MOBILE_NUMBER">Account Added</option>
						        </select> </td> 
						 </tr> --%>
						 <tr class="even">		
								<td width="25%"><strong>Status</strong></td>
								<td width="75%"><input type="radio" name="campstat" value="E">&nbsp;Enabled &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="campstat" value="D">&nbsp;Disabled</td> 
						 </tr>
						  <%-- <tr class="even">		
								<td width="25%"><strong><label for="Campaign Customers"><strong>Select Customer Type</strong><font color="red">*</font></label></strong></td>
								<td width="75%"><select id="campcusttype" name="campcusttype"  required='true' data-placeholder="Select Fields" style="width:290px">
				       				<option value="SEL">All</option>
						            <option value="REG">Active</option>
						            <option value="ADD">In-Active</option>
						            
						        </select> </td> 
						 </tr> --%>
			       </table>
			     </fieldset>
				</div>
				
			</div>
			 <div > 
		
		</div> 
		</div>
		
				 <div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Campaign Interval
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="box-content">
				  <fieldset>
					<table width="950"  border="0" cellpadding="5" cellspacing="1" id="user-details"
						class="table table-striped table-bordered bootstrap-datatable " >
						 <tr class="odd" width="20%">
							    <td width="25%"><strong><label for="Campaign Name"><strong>Campaign Interval</strong><font color="red">*</font></label></strong></td>
								<td width="75%"><select id="campintval" name="regtype"  required='true' data-placeholder="Select Interval" style="width:290px">
				       				<option value="SEL">Select</option>
						            <option value="O">Once</option>
						            <option value="H">Hourly</option>
						            <option value="D">Daily</option>
						            <option value="W">Weekly</option>
						            <option value="M">Monthly</option>
						        </select> </td> 
						 </tr>
						  <tr class="even">		
								<td width="25%"><strong><label for="Campaign SMS"><strong>Time Interval</strong><font color="red">*</font></label></strong></td>
								<td width="75%"><p id="basicExample">
							From &nbsp; <input id="stdate" name="stdate" style="width:100px" type="text"  class="date start" /> 
										<input id="sttime" name="sttime" style="width:100px" type="text" class="time start" />
									To	<input id="endtime" name="endtime" style="width:100px" type="text" class="time end" />
										<input id="enddate" name="enddate" style="width:100px" type="text" class="date end" />
								</p>
						        
						        </td> 
						 </tr>
					
			       </table>
			     </fieldset>
				</div>
				
			</div>
			 <div > 
		
		</div> 
		</div>
		
		
			 <div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Campaign Attachments
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="box-content">
				  <fieldset>
					<table width="98%" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable ">
								
								<tr>
								<td width="25%"></td>
								<td width="75%"><s:file name="uploadfile" id="uploadfile"	label="Campaign Attachments" /></td>
								</tr>
							</table>
			     </fieldset>
				</div>
				
			</div>
			 <div > 
		
		</div> 
		</div>
		
		
			<a href="#" id="btn-back" class="btn btn-danger">Back </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success">&nbsp;Submit</a>	
			<span id ="error_dlno" class="errors"></span>	
    </div>
     
     
      
 </form>
      
      
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datepicker.js"></script>
<script src="https://jonthornton.github.io/jquery-timepicker/jquery.timepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datepair.js"></script>
<script src="${pageContext.request.contextPath}/js/pikaday.js"></script>
<script src="${pageContext.request.contextPath}/js/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/js/site.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ptTimeSelect.js"></script>
<script>
    // initialize input widgets first
    $('#basicExample .time').timepicker({
        'showDuration': true,
        'timeFormat': 'g:ia'
    });

    $('#basicExample .date').datepicker({
        'format': 'm/d/yyyy',
        'autoclose': true
    });

    // initialize datepair
    var basicExampleEl = document.getElementById('basicExample');
    var datepair = new Datepair(basicExampleEl);
</script>
		
</body>
</html>