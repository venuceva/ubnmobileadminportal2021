 
<%@page
	import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 
<meta charset="utf-8">
<title>NBK</title>

<link href="<%=ctxstr%>/css/bootstrap-united.css"	rel="stylesheet">
<link href="<%=ctxstr%>/css/bootstrap-responsive.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=ctxstr%>/css/jquery-ui-1.8.21.custom.css" />
<link href="<%=ctxstr%>/css/agency-app.css" rel="stylesheet" />
<link href="<%=ctxstr%>/css/core.css" rel="stylesheet" />
<link rel="shortcut icon" href="<%=ctxstr%>/images/<%=appName %>/favicon.ico" />

<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 3px 0;
}

#nav {
	float: left;
	min-width: 200px;
	list-style: none;
}

#nav li a {
	text-decoration: none;
	list-style: none;
}

#nav li a:hover,#nav li a.active {

}

#nav li ul {
	display: none;
	list-style: none;
}

#nav li ul li a {
	/*background: #D35B03;*/
	list-style: none;
}
.amtclass {
	font-weight : bold;
} 

 
.td-bold{
	font-size: 0.8em; font-family:Tahoma;
	font-weight : bold;
}
/* #collapse-div a { color:#999; text-decoration:none; } */
</style>
<s:set value="responseJSON1" var="respData"/>

 <s:if test="#respData.size == 0" >
 	<s:set value="commonBean.responseJSON1" var="respData"/>
 </s:if>
 
  <script type="text/javascript">

var halfPageDashboard ='${responseJSON1.halfpagedata}'; 
if(halfPageDashboard == '') {
	halfPageDashboard ='${commonBean.responseJSON1.halfpagedata}';
}
var json = jQuery.parseJSON(halfPageDashboard);

var dashBoardFun = '${responseJSON.dashboarddata}';
if(dashBoardFun == '') {
	dashBoardFun ='${commonBean.responseJSON1.halfpagedata}';
}
var json1 = jQuery.parseJSON(dashBoardFun);

window.setTimeout( refreshGrid, 15000);
var oTable;
function refreshGrid() { 
    var formInput='';
	json = "";
 	$.getJSON('<%=ctxstr%>/<%=appName %>/livedata.action', 
				formInput,
		function(data) {  
   			json = data.responseJSON1.halfpagedata;  
			oTable = $('#DataTables_Table_0').dataTable();
   			var oSettings = oTable.fnSettings();
   			var length = oSettings._iDisplayLength;
   			var pageCurr = parseInt($('.active a').text(), 0); 
   	    	oTable.fnDestroy();
			fillJqGrid(json);
			//fillFloatGrid(data.responseJSON1.FLOAT_DATA,data.responseJSON1.FLOAT_MSG);
			//announceData(data.responseJSON1.USER_ANNOUNCEMENT ,data.responseJSON1.GROUP_ANNOUNCEMENT);
			
			$('#DataTables_Table_0').dataTable({
 				"processing": true,
			    "serverSide": true,
			    "iDisplayLength": length,
			    /* "iPage": pageCurr,  
			   	"fnDrawCallback": function () {
			          alert( 'Now on page'+ this.fnPagingInfo().iPage );
			      }, */
		        "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
				"sPaginationType": "bootstrap",
				/* "ajax": "livedata.action",
				"sServerMethod": "POST", */
				"bDestroy": true,
				"oLanguage": {
					"sLengthMenu":  "_MENU_ records per page"
				} 
		    }); 
 			oTable.fnPageChange(pageCurr,false);
			
		});
	
	window.setTimeout(refreshGrid, 15000);
}
 
$(document).ready(function () { 
	fillJqGrid(json);
	dashBoardItems('');
	
	var userAnn = '${responseJSON1.USER_ANNOUNCEMENT}';
	var groupAnn = '${responseJSON1.GROUP_ANNOUNCEMENT}';
	
	if(userAnn == '' && groupAnn == '') {
		userAnn = '${commonBean.responseJSON1.USER_ANNOUNCEMENT}';
		groupAnn = '${commonBean.responseJSON1.GROUP_ANNOUNCEMENT}';
	}
	
	announceData(userAnn,groupAnn); 
 
	$( "#sortable" ).sortable();
	$( "#sortable" ).disableSelection();
 
	$( "#sortable1" ).sortable();
	$( "#sortable1" ).disableSelection(); 
	
});

//user-tbody
function undefinedcheck(data){
	return data == undefined  ? " " :  data;
}

function dashBoardItems(data) { 
	var val = 0;
	var valMul = 1;
	 
	var appendTxtmain ="";
	var appendTxt ="";
	$("#sortable1").html('');
	if( json1 != null ) {
		$.each(json1, function(i, v) {   
			if(v.val != undefined && v.key != undefined) { 
			
		 		var query_val=( v.val == undefined) ? "" : v.val;
				var query_key=(v.key == undefined) ? "" : v.key; 
				 
				if(val == 0) { 
					appendTxt +="<div class='sortable row-fluid' id='sortable'>";
				} 
				if( (4*valMul) == val ) { 
					appendTxt+="</div><div class='sortable row-fluid' id='sortable'>";
					valMul++;
				}
				appendTxt += "<a data-rel='tooltip' title='Ksh 0' class='well span3 top-block' href='#'><span class='icon32 icon-color icon-briefcase'></span> <div>"+query_key+"</div> <div></div> <span class='notification'>"+query_val+"</span></a>";
				val++;
			}
	 	}); 
		
		appendTxtmain=appendTxt+"</div>"; 
		$("#sortable1").append(appendTxtmain);	
	} 

}

function announceData(data1,data2) { 

	$("#userannoumcement").html("");
	$("#groupannoumcement").html("");
	
	var userannouncement = data1;  
	if(userannouncement == "NOVAL"){
		$("#userannoumcement").text("");
	}else{
		data1="<h3>"+userannouncement+"</h3>";
		$("#userannoumcement").text("");
		$("#userannoumcement").append(data1);
	}
	
	var groupannouncement = data2;  
	if(groupannouncement == "NOVAL"){
		$("#groupannoumcement").text("");
	} else {
		data2="<h3>"+groupannouncement+"</h3>";
		$("#groupannoumcement").text("");
		$("#groupannoumcement").append(data2);
	} 
	
	textObject.flash($('.flash-colour'), 'colour', 900);
}

function fillJqGrid(data1) {  
	var val = 1;
	var rowindex = 0;
 	var addclass = "";
 	var colindex = 0;
	$("#user-tbody > tr").remove();
	//$("#user-tbody").empty();
	$.each(json, function(index, v) {
		
		if(val % 2 == 0 ) {
			addclass = "even";
			val++;
		} else {
			addclass = "odd";
			val++;
		} 
		
		
		colindex = ++ colindex;
 		var user_status = "";
 		var text = undefinedcheck(v.STATUS);
		 
		if(text.trim() == 'Success') {
			user_status = "<a href='#' class='label label-success' index='"+rowindex+"'>"+text+"</a>"; 		 
		} else if(text.trim() == 'Reversal') {
			user_status = "<a href='#' class='label label-info' index='"+rowindex+"'>"+text+"</a>"; 		
  		} else if(text.trim() == 'Failed') {
			user_status = "<a href='#' class='label label-warning' index='"+rowindex+"'>"+text+"</a>"; 		
  		}
		
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+
			"<td>"+colindex+" </td>"+
			"<td>"+undefinedcheck(v.MOBILENUMBER)+" </td>"+	
			"<td>"+undefinedcheck(v.DATETIME)+"  </td>"+ 
			"<td>"+user_status+" </td>"+
			"<td>"+undefinedcheck(v.AMOUNT)+" </td>"+
 			"<td>"+undefinedcheck(v.RRN)+" </td>"+
			"<td>"+undefinedcheck(v.PY_SHRTCODE)+" </td>"+  
 			"<td><a id='opener' role='"+undefinedcheck(v.TXNID)+"'>"+undefinedcheck(v.TXNID)+"</a></td>"+
			"<td>"+undefinedcheck(v.TXNTYPE)+" </td>"+
			"<td>"+undefinedcheck(v.INSTITUTE)+" </td>"+
			"<td>"+undefinedcheck(v.OPERATOR)+" </td>"+
			"</tr>"; 
		$("#user-tbody").append(appendTxt);	
		rowindex = ++rowindex;  
	});
	
	//$('#DataTables_Table_0').DataTable().ajax.reload();
}   
  
 	 
var textObject = {	
	delay : 300,
	effect : 'replace',
	classColour : 'blue',
	flash : function(obj, effect, delay) {
		if (obj.length > 0) {
			if (obj.length > 1) {
				jQuery.each(obj, function() {
					effect = effect || textObject.effect;
					delay = delay || textObject.delay;
					textObject.flashExe($(this), effect, delay);					
				});
			} else {
				effect = effect || textObject.effect;
				delay = delay || textObject.delay;
				textObject.flashExe(obj, effect, delay);
			}
		}
	},
	flashExe : function(obj, effect, delay) {
		var flash = setTimeout(function() {
			switch(effect) {
				case 'replace':
				obj.toggle();
				break;
				case 'colour':
				obj.toggleClass(textObject.classColour);
				break;
			}
			textObject.flashExe(obj, effect, delay);
		}, delay);
	}
}; 
</script>
<script>
  $(function() { 
    $( "#dialog-message" ).dialog({
    	autoOpen: false, 
    	width : 760 ,
    	show: {
            effect: "blind",
            duration: 500
          },
          hide: {
            effect: "explode",
            duration: 500
          },
        modal: true,
        buttons: {
          Ok: function() {
            $( this ).dialog( "close" );
          }
        }
      });
    
  }); 
  
  $(document).on('click',"a",function(event) {
	 var id=$(this).attr('id'); 
	 var datato = "";
	  if(id == 'opener'){
		  	var rrn_id = $(this).attr('role'); 
		  	var formInput='RRN='+rrn_id;
			   	$.getJSON("<%=ctxstr%>/<%=appName %>/drillTxnDetails.action"
			   			,formInput,function(data) {  
   		  			$('#DataTables_Table_11').html('');
	   		  			datato+="<tr><td width='25%'><label for='Mobile Number'><strong>Mobile Number</strong></label></td>";
   					datato+="<td width='30%' colspan=3> "+undefinedcheck(data.responseJSON.MOBILENUMBER)+"</td> </tr> ";
   					datato+="<tr><td><label for='Transaction Date & Time'><strong>Transaction Date & Time</strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.DATETIME)+"</td> </tr> ";
   					datato+="<tr><td><label for='Status'><strong>Status : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.STATUS)+"</td> </tr> ";
   					datato+="<tr><td><label for='Amount'><strong>Amount : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.AMOUNT)+"</td> </tr> ";
   					datato+="<tr><td><label for='Account Number'><strong>Account Number : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.RRN)+"</td> </tr> ";
   					datato+="<tr><td><label for='Transaction Type'><strong>Transaction Type : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.TXNTYPE)+"</td> </tr> ";
   					datato+="<tr><td><label for='Institute'><strong>Institute : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.INSTITUTE)+"</td> </tr> ";
   					datato+="<tr><td><label for='Operator'><strong>Operator : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.OPERATOR)+"</td> </tr> ";
   					datato+="<tr><td><label for='Operator Ref No'><strong>Operator Ref No : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.TXNID)+"</td> </tr> ";
   					datato+="<tr><td><label for='Paybill Short Code'><strong>Paybill Short Code : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.PAYBILLSHT)+"</td> </tr> ";
   					datato+="<tr><td><label for='Invoice Number'><strong>Invoice Number : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.INVNO)+"</td> </tr> ";
   					/* datato+="<tr><td><label for='Mobile Number'><strong>Mobile Number : </strong></label></td>";
   					datato+="<td> "+(data.responseJSON.ORGACTBAL)+"</td> </tr> "; */
   					datato+="<tr><td><label for='Date Created'><strong>Date Created : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.DATECRE)+"</td> </tr> ";
   					datato+="<tr><td><label for='Paybill Response'><strong>Paybill Response : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.PAYBILLRESP)+"</td> </tr> ";
   					datato+="<tr><td><label for='Credit Account'><strong>Credit Account : </strong></label></td>";
   					datato+="<td> "+undefinedcheck(data.responseJSON.CREDITACT)+"</td> </tr> ";
	   				datato+="<tr><td><label for='Debit Account'><strong>Debit Account : </strong></label></td>";
	   				datato+="<td> "+undefinedcheck(data.responseJSON.DEBITACT)+"</td> </tr> ";
	   				datato+="<tr><td><label for='Channel'><strong>Channel : </strong></label></td>";
	   				datato+="<td> "+undefinedcheck(data.responseJSON.CHANNEL)+"</td> </tr> ";
	   				/*datato+="<tr><td><label for='Request'><strong>Narration : </strong></label></td>";
	   				 datato+="<td> "+undefinedcheck(data.responseJSON.NARRATION)+"</td> </tr> ";*/
	   				
	   				$('#DataTables_Table_11').append(datato); 
					
					datato="";
					$('#DataTables-tbody').html('');
					var req = undefinedcheck(data.responseJSON.KYCINFO).split("##");
					 
					if(req.length > 0){
						for(index=0;index<req.length;index++){
							var dat1 = " ";
							var dat2 = " ";
							try {
								  dat1 = req[index].split("~~")[0];
								  dat2 = req[index].split("~~")[1];
							}catch(e){
								
							}
	 		   				datato+="<tr><td> "+dat1+"</td>  ";
	 						datato+="<td> "+dat2+"</td> </tr> ";
						} 
					}
					
					
					$('#DataTables-tbody').append(datato);
			   		//console.log(data.responseJSON);
	   				$( "#dialog-message" ).dialog( "open" );
	  			
	  		});  
		     
	  } else {
		  
	  }  
 });
  
  </script>
<body> 
<form method="post" name="form1" id="form1"> 
	<div id="content" class="span10"> 	
		<div>
			<ul class="breadcrumb">
				<li><a href="home.action">Home</a> <span class="divider">&gt;&gt;</span> </li>
				<li><a href="#">Dashboard</a></li>
			</ul>
		</div> 
		<span class="container"><!--
			<h6 class="flash-colour"><span id="groupannoumcement"></span></h6>
			<h6 class="flash-colour"><span id="userannoumcement"></span></h6> 
		--></span> 
		<br/> 
		<div id="sortable1"></div>   
		
		<div class="row-fluid sortable">  
			<div class="box span12" id="groupInfo">
			 <div class="box-header well" data-original-title>Live Transactions
				<div class="box-icon"> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
				</div>
			</div>			 
			<div class="box-content">  
					<table  style = 'border: 1px solid #d7d7d7; font-family: Arial, Helvetica, sans-serif;font-size: 12px; color: #000000; font-weight: normal;' width='100%'
					class="table table-striped table-bordered bootstrap-datatable datatable" 
						id="DataTables_Table_0">
						<thead>
							<tr>
								<th>S.No</th>
								<th>Mobile Number</th>
								<th>Date / Time</th>
								<th>Status</th>
								<th>Amount</th>
								<th>Account Number</th>
								<!-- <th>Operator Ref No</th> -->
								<th width="20%">Short Code</th>								
								<th>RRN</th>
								<th>Txn Type</th>
								<th>Institute</th>
								<th>Operator</th>
							</tr>
						</thead> 
						<tbody id="user-tbody"> 
						</tbody>
					</table> 
				 	 
			</div>
		</div>  
	  </div>
	</div>  
	
	<div id="dialog-message" title="Transaction Drill Down Details">
	  <table   class="table table-striped table-bordered bootstrap-datatable " 
						id="DataTables_Table_11">
	   	 
		</table>
		 <table   class="table table-striped table-bordered bootstrap-datatable " 
						id="DataTables_Table_12">
			<thead>
					<tr>
						<th>Kyc Name</th>
						<th>Kyc Value</th> 
					</tr>
				</thead> 
				<tbody id="DataTables-tbody"> 
				</tbody>
	   	 
		</table>
	</div>  
</form> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
 
</body>
</html>
