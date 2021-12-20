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

  $(document).ready(function() {
	  $("textarea#message").val('<s:property value='message'/>');
		//alert('<s:property value='message'/>');
		$('#btn-submit').click(function()
		{
			$("#form1").validate(smsTemprules);
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/SmsSingleAck.action';
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
 				  <li><a href="#">SMS Management</a></li>
				</ul>
			</div>
					
			
		 <div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Single SMS Confirm
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
							    <td><strong><label for="Mobile Number"><strong>Mobile Number</strong><font color="red">*</font></label></strong></td>
								<td><input name="mobno" type="text" id="mobno" required="required"  class="field"  value="<s:property value='mobno' />" /> </td>
						</tr>
						 <tr class="even">		
								<td><strong><label for="template"><strong>Message</strong><font color="red">*</font></label></strong></td>
								<td><textarea style="margin: 0px 0px 9px; width: 500px; height: 200px;" name="message" id="message" required="required"  class="field" value="<s:property value='message'/>" /></textarea></td> 
						 </tr>
			       </table>
			     </fieldset>
				</div>
				
			</div>
			 <div > 
			<a href="#" id="btn-back" class="btn btn-danger">Back </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success">&nbsp;Submit</a>	
			<span id ="error_dlno" class="errors"></span>	 
		</div> 
		</div>
    </div>
     
 </form>
      
		
</body>
</html>