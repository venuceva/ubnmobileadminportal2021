
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.min.css' />
<style type="text/css">
.errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}

</style>

<script type="text/javascript">
var billerrules = {
		rules : {
			catId : { required : true },
			instId : { required : true  },
			billerName : { required : true  },
			accountNumber : { required : true},
			billerDesc : { required : true },
			supportEmail : { required : true },
			supportContact : { required : true},
			address : { required : true}

		},
		messages : {
			catId : {
				required : "Biller Category Required."
		  	},
		  	instId : {
				required : "Biller Channel Required."
			} ,
			billerName : {
				required : "Biller Name Required."
			} ,
			billerType : {
					required : "Biller Type Required."
			} ,
			accountNumber : {
					required : "Account Number Required."
			},
			billerDesc : {
					required : "Biller Description Is Required."
			},
			supportEmail : {
				required :   "Support Email Reuired."
			},
			supportContact : {
				required :   "Support Contact Required."
			},
			address : {
				required :   "Address Required."
			}
		},
		errorPlacement: function(error, element) {
		    if ( element.is( ':radio' ) || element.is( ':checkbox' ) )
				error.appendTo( element.parent() );
		    else if ( element.is( ':password' ) )
				error.hide();
		    else
				error.insertAfter( element );
		}
	};



$(document).ready(function(){

	$.validator.addMethod("regexchk", function(value, element, regexpr) {
		 return this.optional(element) || regexpr.test(value);
	   }, "");

	$('#btn-submit').on('click',function() {
		$('#billerMsg').text('');
		$('#error_dlno').text('');
		var finalData = "";
		$("#form1").validate(billerrules);
		console.log($("#form1").valid());
		if($("#form1").valid()) {
			$("#form1").validate().cancelSubmit = true;

			$('#form1').find('input, textarea, button, select').attr('disabled','disabled');
			$('#form1').find('.chosen-select').prop('readonly', true).trigger("liszt:updated");

			$('#cnf').show();
			$('#crt').hide();
		} else {
			return false;
		}

	});

	$('#btn-cnf').live('click',function() {
		$('#form1').find('input, textarea, button, select').removeAttr('disabled');
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/saveBiller.action";
		$("#form1").submit();
	});
	$('#cnf-cancel').live('click',function() {
		$('#form1').find('input, textarea, button, select').removeAttr('disabled');
		$('#form1').find('.chosen-select').prop('readonly', false).trigger("liszt:updated");

		$('#cnf').hide();
		$('#crt').show();
	});

	$('#btn-Cancel').live('click',function() {
		$("#form1").validate().cancelSubmit = true;
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/mpesaAccManagement.action";
		$("#form1").submit();
	});
});
</script>
</head>
<body>
	  <div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="mpesaAccManagement.action">Biller Management</a><span class="divider"> &gt;&gt; </span> </li>
				   <li><a href="#">Create New Biller</a></li>
				</ul>
			</div>

			<table height="3">
			 <tr>
			    <td colspan="3">
			    	<div class="messages" id="messages"><s:actionmessage /></div>
			    	<div class="errors" id="errors"><s:actionerror /></div>
			    </td>
		    </tr>
		 </table>
	<form name="form1" id="form1" method="post" autocomplete="off" style="margin: 0px 0px 50px">
		<div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					 <i class="icon-edit"></i>Biller Information
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					</div>
				</div>

				<div class="box-content">
					<fieldset>
					<table width="98%"  border="0" cellpadding="5" cellspacing="1"
						class="table table-striped table-bordered bootstrap-datatable " >
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Biller Category<font color="red">*</font></strong></label></td>
								<td width="30%"><input type="hidden" name="catId" id="catId" value='<s:property value="catId"/>' /><input type="text" name="category" id="category" value='<s:property value="catText"/>' readonly="readonly">
								</td>
							</tr>
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Channel<font color="red">*</font></strong></label></td>
								<td width="30%" ><input type="hidden" name="instId" id="instId" value='<s:property value="instId"/>'/><input type="text" name="instText" id="instText" value='<s:property value="instText"/>' readonly="readonly">
								</td>
							</tr>
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Biller Name<font color="red">*</font></strong></label></td>
								<td width="30%" ><input type="text"  name="billerName" id="billerName" value='<s:property value="billerName"/>'/>
								</td>
							</tr>
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Account Number<font color="red">*</font></strong></label></td>
								<td width="30%" ><input type="text"  name="accountNumber" id="accountNumber" value='<s:property value="accountNumber"/>' />
								</td>
							</tr>
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Biller Description<font color="red">*</font></strong></label></td>
								<td width="30%" ><input type="text"  name="billerDesc" id="billerDesc" value='<s:property value="billerDesc"/>' />
								</td>
							</tr>
							<tr >
								<td width="20%"><label for="Select Institute"><strong>Support Email<font color="red">*</font></strong></label></td>
								<td width="30%"><input type="text"  name="supportEmail" id="supportEmail" value='<s:property value="supportEmail"/>' />
								</td>
							</tr>
							<tr >
								<td width="20%"><label for="Support Contact"><strong>Support Contact<font color="red">*</font></strong></label></td>
								<td width="30%" ><input type="text"  name="supportContact" id="supportContact" value='<s:property value="supportContact"/>' />
								</td>
							</tr>
							<tr >
								<td width="20%"><label for="Address"><strong>Address<font color="red">*</font></strong></label></td>
								<td width="30%"><input type="text" name="address" id="address" value='<s:property value="supportContact"/>'/>
								</td>
							</tr>
					</table>
				</fieldset>
			</div>
		</div>
		</div>
	</form>

   		<div class="form-actions" id="crt">
         <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100" ></input>&nbsp;
         <input type="button" class="btn btn-info" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input>&nbsp;
         <span id ="error_dlno" class="errors"></span>
       </div>
	<div class="form-actions" id="cnf" style="display:none;">
	         <input type="button" class="btn btn-success"  name="btn-cnf" id="btn-cnf" value="Confirm" width="100" ></input>&nbsp;
	         &nbsp;<input type="button" class="btn btn-info" name="cnf-cancel" id="cnf-cancel" value="Back" width="100" ></input>
       </div>
	</div>
 <script type="text/javascript">
$(function(){

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