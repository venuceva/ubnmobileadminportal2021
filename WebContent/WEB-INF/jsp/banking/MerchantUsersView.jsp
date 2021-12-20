 <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<s:set value="responseJSON" var="respData"/>
<script type="text/javascript" >
$(document).ready(function() {
	$("#terminalDate").val($.datepicker.formatDate("dd/mm/yy", new Date()));
    var terminalId="${responseJSON.tid}";
    //alert(terminalId);
   // 
    
    
    var merchantList='${responseJSON.MERCHANT_LIST}';
	var json = jQuery.parseJSON(merchantList);
	var options = "";
 	$.each(json, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	      options = $('<option/>', {value: v.val, text: v.key }).attr('data-id',i);
	    // append the options to job selectbox
	    $('#merchantID').append(options);
	});    

  
	
	
	var tpkIndex="${responseJSON.tmkIndex}";
	if(tpkIndex==""){
		tpkIndex="${tpkIndex}";
	}
	$("#tpkIndex").val(tpkIndex);
	//$("#tmkIndexSpan").empty();
	//$("#tmkIndexSpan").append(tpkIndex);
	
	var tpkKey="${responseJSON.tpkKey}";
	if(tpkKey==""){
		tpkKey="${tpkKey}";
	}
	$("#tpkKey").val(tpkKey);
	//$("#tpkKeySpan").empty();
	//$("#tpkKeySpan").append(tpkKey);
	
});

//For Closing Select Box Error Messages_Start
$(document).on('change','select',function(event) { 

if($('#'+$(this).attr('id')).next('label').text().length > 0) {
 $('#'+$(this).attr('id')).next('label').text(''); 
 $('#'+$(this).attr('id')).next('label').remove();
}

});
//For Closing Select Box Error Messages_End

var list = "validFrom,validThru".split(",");
var datepickerOptions = {
			showTime: false,
			showHour: false,
			showMinute: false,
  		dateFormat:'dd-mm-yy',
  		alwaysSetTime: false,
		changeMonth: true,
		changeYear: true
};


$(function() {
	
	
	$('#merchantID').live('change', function () {

		var merchantId=$('#merchantID').val();
		var formInput='merchantId='+merchantId;

		$.getJSON('retriveStoresAct.action', formInput,function(data) {

			var json = data.responseJSON.STORE_LIST;
			$('#storeId').find('option:not(:first)').remove();
			$('#storeId').trigger("liszt:updated");
			$.each(json, function(i, v) {
				
				var options = $('<option/>', {value: v.val, text: v.val+'~'+v.key}).attr('data-id',i);
				$('#storeId').append(options);
				$('#storeId').trigger("liszt:updated");
			});
		});
	});
	
 	$('#storeId').live('change', function () {

 		var merchantId=$('#merchantID').val();
 		var storeId=$('#storeId').val();
 		console.log('storeId='+storeId+'merchantId='+merchantId);
		var formInput='storeId='+storeId+'&merchantId='+merchantId;
		//alert(formInput);

		$.getJSON('fetchuserAct.action', formInput,function(data) {

			
			var val = 0;
			var rowindex = 1;
			var bankfinalData=data.responseJSON.multidata;	
			
			
			console.log("asdadadasdas    "+bankfinalData);
			var bankfinalDatarows=bankfinalData.split("#");
			
			

			var addclass = "";
			var offArr = '${officeLocation}'.split(",");
			$("#tbody_data").empty();

			for(var i=0;i<bankfinalDatarows.length;i++) {

					if(val % 2 == 0 ){
						addclass = "even";
						val++;
					} else {
						addclass = "odd";
						val++;
					}

				var eachrow=bankfinalDatarows[i];
				console.log("in next page "+eachrow);
				var eachfieldArr=eachrow.split(",");
				var name=eachfieldArr[0];
				var userid=eachfieldArr[1];
				
				if (typeof userid === "undefined") {
				    alert("No Users Found");
				    break;
				}
				var status=eachfieldArr[2];
				var utype=eachfieldArr[3];
				var mobno = eachfieldArr[4];

				var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
								 "<td>"+rowindex+"</td>"+
								 "<td>"+name+"</td>"+
								 "<td>"+userid+"</td>"+
								 "<td>"+status+" </td>"+
								 "<td>"+utype+"</td>"+
								 "<td>"+mobno+"</td>"+
				
				 "</tr>";
				 
				$("#tbody_data").append(appendTxt);
				rowindex++;
			}
			
			/* 
			var json = data.responseJSON.terminalID
			$('#terminalID').val(json); */
			
			
		});
	}); 
	
	
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	});
	 
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
	$("#serialNumber").keypress(function (e) {
	 if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		$("#"+$(this).attr('id')+"_err").html("Digits Only").show().fadeOut("slow");
			   return false;
		}
   }); 
   
   var obj = {
	  "hrs1": "12",
	  "hrs2": "12",
	  "min1": "59" ,
	  "min2": "59" 
	};
 	
	$.each( obj, function( key, value ) { 
		var i = 0; 
		for (i = 1; i <= value; i++ ) { 
			var options = $('<option/>', {value: i , text: i});
			 $('#'+key).append(options);
		}
	});
	
});
var createTerminalRules = {
   rules : {
    merchantID : { required : true },
	storeId : { required : true },
	//terminalID : { required : true },
	modelNumber : { required : true },
	serialNumber : { required : true },
	terminalMake : { required : true },
	//validFrom : { required : true },
	//validThru : { required : true },
	terminalDate : { required : true },
	terminalType : { required : true },
	status : { required : true }
   },  
   messages : {
    merchantID : { 
       required : "Merchant Id Missing"
        },
	storeId : { 
       required : "Store Id Missing"
        },
	terminalID : { 
       required : "Please Enter Terminal Id"
        },
	modelNumber : { 
       required : "Please Enter Model No"
        },
	serialNumber : { 
       required : "Please Enter Serial No"
        },
	terminalMake : { 
       required : "Please Enter Terminal Make"
        },
	/*validFrom : { 
       required : "Please select Valid From"
        },*/
        status : { 
            required : "Please Select The Status"
             },
	terminalDate : { 
       required : "Please Select Date"
        },
	terminalType : { 
       required : "Please Select Terminal Type to generate Terminal Id"
        }
   } 
 };
  
function getGenerateMerchantScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateMerchantAct.action';
	$("#form1").submit();
	return true;
}

//function createTerminal(){
	
	 
$("#submit1").live('click', function () {
	 
	$("#form1").validate(createTerminalRules);
	var serialNumber =$('#serialNumber').val();

	var queryString = "method=serialNumberMethod&serialNumber="+serialNumber;	
	
	$.getJSON("postJson.action", queryString,function(data) {

		userstatus = data.status;

		console.log(data);

		v_message = data.message;

		if(userstatus == "FOUND") {
			if(v_message != "SUCCESS") {
				$('#error_dlno').text(v_message);
			}
		} else {
			
			if($("#form1").valid()){
				$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/getCreateTerminalConfirmAct.action';
				$("#form1").submit();
				return true;
			}else{
				return false;
			}
		}
		alignSerialNo(textMess);
	});
	
	
});
	 
 	

 
</script>


</head>

<body>
	<form name="form1" id="form1" method="post">
		<div id="content" class="span10"> 
		    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt;</span> </li>
					  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Merchnat Users</a></li>
					</ul>
			</div>
			<div class="row-fluid sortable"><!--/span--> 
				<div class="box span12">  
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i>Merchnat Users
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>  
							
					<div class="box-content" id="terminalDetails"> 
					 <fieldset>   
						<table width="950" border="0" cellpadding="5" cellspacing="1"  class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><label for="Merchant ID"><strong>Merchant ID<font color="red">*</font></strong></label></td>
								<!-- <td width="30%"><input name="merchantID" type="text" id="merchantID" class="field" value="mer" readonly></td> -->
								<td width="20%">
								
								<select id="merchantID" name="merchantID"  data-placeholder="Choose  Merchant Id..." class="chosen-select-width" style="width: 220px;" required=true >
								<option value="">Select</option>
								</select></td>
								
								<td width="20%"><label for="Store ID"><strong>Store ID<font color="red">*</font></strong></label></td>
								<!-- <td width="30%"><input name="storeId"  type="text" id="storeId" class="field"  value="str" readonly  > </td> -->
								<td>
								<select id="storeId" name="storeId" data-placeholder="Choose office Store Id..." class="chosen-select-width" style="width: 220px;" required=true >
							<option value="">Select</option></select>
							</td>
							</tr>
						
							
							
							
						</table>
						<input name="pinEntry" type="hidden" class="field" id="pinEntry"  value="NO"  />
						</fieldset>
						</div> 
						
						
						
				<div class="box-content" id="chdata" >
				<fieldset>
					<table width="100%" class="table table-striped table-bordered bootstrap-datatable "
								id="mytable1">
					  <thead>
						<tr>
							<th>Sno</th>
							<th>Name</th>
							<th>User ID</th>
							<th>User Status</th>
							<th>User Type</th>
							<th>Mobile Number</th>
						</tr>
					  </thead>
					  <tbody id="tbody_data">
					  </tbody>
					</table>
				</fieldset>
			</div>
			
					</div> 
				</div>
				
			<input name="tpkIndex"  type="hidden" id="tpkIndex" class="field"  value="${tmkIndex}"  />
			<input name="tpkKey"  type="hidden" id="tpkKey" class="field"  value="${tpkKey}" />
			
			<div class="form-actions">
				<a  class="btn btn-danger" href="generateMerchantAct.action" onClick="getGenerateMerchantScreen()">Next</a> &nbsp;&nbsp;
				<!-- <a  class="btn btn-success" href="#" onClick="createTerminal()">Submit</a> -->
				<!-- <a  class="btn btn-success" href="#"  id="submit1" name="submit1">Submit</a> -->
				<span id ="error_dlno" class="errors"></span>
			</div>	 
	</div>
 </form>
 <s:token/>
</body>
</html>
