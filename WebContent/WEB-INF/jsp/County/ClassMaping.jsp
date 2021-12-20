 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Create Entity</title>
 
 
<script type="text/javascript">

var val = 1; 
var rowindex = 0;
var colindex = 0;

$(function () {

	
	
	$("#class-details").hide();
	$("#prof-details").hide();
	
	var mydata ='${responseJSON.CLASS_CODES}';
 	var json = jQuery.parseJSON(mydata);
 	$.each(json, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	    var options = $('<option/>', {value: v.val, text: v.val+'-'+v.key}).attr('data-id',i);
	    // append the options to job selectbox
	    $('#classId').append(options);
	}); 
	
	var mydata ='${responseJSON.PROF_CODES}';
 	var json = jQuery.parseJSON(mydata);
 	$.each(json, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	    var options = $('<option/>', {value: v.val, text: v.val+'-'+v.key}).attr('data-id',i);
	    // append the options to job selectbox
	    $('#professorId').append(options);
	}); 
	
	
	
	
	$('#classId').live('change', function () { 
		$("#class-details").show();
		var classId = $('#classId').val();
		
		if(classId != '' )  {
		   var formInput='classId='+classId+'&method=fetchClassInfo'; 
		   $.getJSON('fetchClassInfoAct.action', formInput,function(data) { 
				$("#className").empty();
				$("#noOfStudents").empty();
				var className=data.responseJSON.CLASS_NAME;
				var noOfStudents=data.responseJSON.NO_OF_STUDEBTS;
				$("#className").text(className);
				$("#noOfStudents").text(noOfStudents);
				
				
		   });  
	   } 
	});
	
	$('#professorId').live('change', function () { 
		$("#prof-details").show();
		
		var professorId = $('#professorId').val();
		
		if(professorId != '' )  {
		   var formInput='professorId='+professorId+'&method=fetchProfessorInfo'; 
		   $.getJSON('fetchProfessorInfoAct.action', formInput,function(data) { 
				$("#profName").empty();
				$("#doj").empty();
				$("#profStaus").empty();
				var profName=data.responseJSON.PROF_NAME;
				var doj=data.responseJSON.PROF_DOJ;
				var profStaus= data.responseJSON.PROF_STATUS;
				$("#profName").text(profName);
				$("#doj").text(doj);
				if(profStaus=='A')
					$("#profStaus").text('Active');
				else if(profStaus=='F')
					$("#profStaus").text('First Time');
				else if(profStaus=='B')
					$("#profStaus").text('Blocked');
				
		   });  
	   } 
	   
	});
	
	
    $('#btn-back').live('click', function () {  
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/assignProfessiorViewAct.action';
			$("#form1").submit();
			return true; 
    }); 
	
	$('#btn-submit').live('click', function () { 
			
		var rowCount = $('#tbody_data > tr').length; 
		 $("#error_dlno").text('');
		 
		if(rowCount > 0) {
			$('#bankMultiData').val(finalData);
			var url="${pageContext.request.contextPath}/<%=appName %>/classProfessorMapAct.action"; 
			$("#form1")[0].action=url;
			$("#form1").submit(); 
		} else { 
			$("#error_dlno").text("Please add atleast one Class and Professor Mapping.");
		}
	});
	
	
var mappingRules = {
	rules : {
		professorId : { required : true },
		classId : { required : true } 
	},		
	messages : {
		professorId : { 
					required : "Please Select Professor Id."
				  }, 
		classId : { 
						required : "Please Select Class Id."
					}
	} 
};

	// Add Row Script.
	var finalData="";
	var eachRow="";
	 $('#addCap').on('click', function () { 
		$("#error_dlno").text('');	
		
	 
		$("#form1").validate(mappingRules);
			
		if($("#form1").valid()) {  
	 
			var professorId = $('#professorId').val() == undefined ? ' ' : $('#professorId').val();
			var classId = $('#classId').val() == undefined ? ' ' : $('#classId').val();
			//alert(professorId);
			var  hiddenInput ="";
			eachRow=professorId+","+classId;
			
			 $("#multi-row-data").append("<span id='hidden_span_"+rowindex+"' index="+rowindex+" ></span>");
			// $('#hidden_span_'+rowindex).append(hiddenInput);

			var addclass = "";

			if(val % 2 == 0 ){
				addclass = "even";
				val++;
			}
			else {
				addclass = "odd";
				val++;
			} 
			colindex = ++ colindex; 	
			var appendTxt = "<tr class="+addclass+" align='center' id='"+rowindex+"' index='"+rowindex+"'>"+
					"<td class='col_"+colindex+"'> &nbsp;"+colindex+"   </td> "+ 
					"<td> "+professorId+" </td> "+
					"<td> "+classId+" </td> "+
					" <td><input type='button' class='btn btn-info' name='delete-map' id='delete-map' value='Delete' index='"+rowindex+"' /></td></tr>";
			rowindex = ++rowindex;
			//alert(appendTxt);
			$("#mytable").append(appendTxt);
			if(finalData.length==0){
				finalData=eachRow;
			}else{
				finalData =finalData+"#"+eachRow;
			}
			//$('#professorId').val('');
			//$('#classId').val('');
		 
			
			
			var rowCount = $('#myTable > tr').length; 
			if(rowCount > 0 )  $("#error_dlno").text('');
		} 
		else
		{
			return false;
		}  
	}); 
	var deleteStr = "";
	 $('#delete-map').live('click', function () {
		//console.log("Intial Final Data:::"+finalData);
		deleteStr = "";
		var eachStr = "";
		var row = $(this).attr('index');
		
		$('#mytable tbody tr').each(function () {
			var rowInd = $(this).attr('index');
			if(row==rowInd){
				$('td',this).each(function (){
					if($(this).text().trim().length !=1 && $(this).text().trim().length !=0 ){
						if(deleteStr.length==0){
							deleteStr= $(this).text().trim();
						}else{
							deleteStr= deleteStr+","+$(this).text().trim();
						}
					}	
					
				});
			}
		});
		//console.log("deleteStr:::"+deleteStr);
		$(this).parent().parent().remove();
		//console.log("deleteStr:::"+deleteStr);
		
		finalData= "";
		
		$('#mytable tbody tr').each(function () {
				eachStr = "";
				$('td',this).each(function (){
					if($(this).text().trim().length !=1 && $(this).text().trim().length !=0){
						if(eachStr.length==0){
							eachStr= $(this).text().trim();
						}else{
							eachStr= eachStr+","+$(this).text().trim();
						}
					}
				});
				//console.log("eachStr:::"+eachStr);
				if(finalData.length==0){
					finalData=eachStr;
				}else{
					finalData =finalData+"#"+eachStr;
				}
				
		});
		//console.log("After Remove Final Data:::"+finalData);
	 });
}); 

</script>

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
.errmsg {
color: red;
}
 
</style>  
</head>
<body>
<form name="form1" id="form1" method="post">  
	<div id="content" class="span10"> 
 		<div> 
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="#">Class Mapping</a> </li> 
			</ul>
		</div>  
		<table height="3">
				<tr>
					<td colspan="3">
						<div class="messages" id="messages">
							<s:actionmessage />
						</div>
						<div class="errors" id="errors">
							<s:actionerror />
						</div>
					</td>
				</tr>
			</table> 
		<div class="row-fluid sortable"> 
		<div class="box span12">
					
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Professor Details
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
						<td ><strong><label for="User ID">Professor ID<font color="red">*</font></label></strong></td>
						<td >
							<select  name="professorId" id="professorId"  class="chosen-select" required="required"
								style="width:250px">
								<option value="">select</option>
							</select>
						</td>
					</tr> 
				</table>
				
				<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable " id="prof-details">
					<tr class="even">
						<td ><strong><label for="User ID">Professor Name</label></strong></td>
						<td ><span id="profName"></span></td>
						<td ><strong><label for="User ID">Date Of Joining</label></strong></td>
						<td ><span id="doj"></span></td>
					</tr> 
					<tr class="even">
						<td ><strong><label for="User ID">Professor Status</label></strong></td>
						<td ><span id="profStaus"></span></td>
						<td ></td>
						<td ></td>
					</tr> 
				</table>
			</fieldset> 
		</div>
	</div>
	</div>
	
	  <div class="row-fluid sortable"> 
		<div class="box span12">
					
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Class Details
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
						<td ><strong><label for="User ID">Class ID<font color="red">*</font></label></strong></td>
						<td >
							<select  name="classId" id="classId"  class="chosen-select" required="required"
								style="width:250px">
								<option value="">select</option>
							</select>
						</td>
					</tr> 
				</table>
				
				<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable " id="class-details">
					<tr class="even">
						<td ><strong><label for="User ID">Class Name</label></strong></td>
						<td ><span id="className"></span></td>
						<td ><strong><label for="User ID">No of Students</label></strong></td>
						<td ><span id="noOfStudents"></span></td>
					</tr> 
				</table>
				
			</fieldset> 
		</div>
	</div>
	</div>
	
	
	
	<div class="row-fluid sortable"> 
		<div class="box span12">
					
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Class-Professor Mapping
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

				</div>
			</div>  
		<div class="box-content">
			<fieldset>
				<table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
								id="mytable"  >
					  <thead>
							<tr >
								<th >Sno</th>
								<th >Professor Id</th>
								<th >Class Id</th>
							</tr>
					  </thead>    
					  <tbody  id="tbody_data"> 
					  </tbody>
					</table> 
			</fieldset> 
		</div>
	</div>
	</div>
	<span id="multi-row-data" class="multi-row-data" style="display:none"> </span>
				<input type="hidden" name="bankMultiData" id="bankMultiData" />
	
	 <div class="form-actions">
			<input type="button" class="btn btn-info" name="addCap" id="addCap"  value="Add" />
			<input type="button" name="btn-submit" class="btn btn-success" id="btn-submit" value="Submit" /> 
			<input type="button" name="btn btn-danger" class="btn btn-danger" id="btn-back" value="Back" /> 
			<span id ="error_dlno" class="errors"></span>
	</div>  
</div> 
</form> 
</body> 
</html>