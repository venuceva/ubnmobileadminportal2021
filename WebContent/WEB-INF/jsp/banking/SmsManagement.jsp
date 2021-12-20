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
var smsTemprules = {
	rules : {
		smsTemplateId : { required : true } ,
		smsTemplate : { required : true }
	},
	messages : {
		 
		   smsTemplateId : {
				required : "Please Enter TemplateId."
			},
			
			smsTemplate : {
				required : "Please Enter Template."
			}
	  }
};
  $(document).ready(function() {
	  
		$('#singlesms').on('click', function(){
			$("#form1").validate().cancelSubmit = true;
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/singlesms.action'; 
			$("#form1").submit();
			return true;
		});
		
		$('#groupsms').on('click', function(){
			$("#form1").validate().cancelSubmit = true;
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/campaigncreation.action'; 
			$("#form1").submit();
			return true;
		});
	
		$('#btn-submit').click(function()
		{
			$("#form1").validate(smsTemprules);
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/smsTemplateCnfAct.action';
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
			
				<div class="box-content" id="top-layer-anchor">
		<span>
				<a href="#" class="btn btn-info" id="singlesms" title='Single SMS' data-rel='popover'  data-content='Edit Existing Products.'>
				<i class="icon icon-book icon-white"></i>&nbsp;Single SMS</a> &nbsp; 
			</span>
			<span>
				<a href="#" class="btn btn-success" id="groupsms" title='Group SMS' data-rel='popover'  data-content='Viewing the Existing Products.'>
				<i class="icon icon-book icon-white"></i>&nbsp;Group SMS</a> &nbsp; 
			</span>  			 
		</div> 
			
			
		 <div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>SMS Template
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
							    <td><strong><label for="Bank Account"><strong>Template ID</strong><font color="red">*</font></label></strong></td>
								<td><input name="smsTemplateId" type="text" id="smsTemplateId" required="required"  class="field"  value="<s:property value='smsTemplateId' />" /> </td>
						</tr>
						 <tr class="even">		
								<td><strong><label for="template"><strong>Template</strong><font color="red">*</font></label></strong></td>
								<td><textarea style="margin: 0px 0px 9px; width: 800px; height: 302px;" name="smsTemplate"   id="smsTemplate" required="required"  class="field"   value="<s:property value='smsTemplate' />" /></textarea></td> 
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