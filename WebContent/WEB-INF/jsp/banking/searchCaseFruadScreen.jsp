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

<script type="text/javascript" src='${pageContext.request.contextPath}/js/bootstrap-2.3.2.min.js'></script>
<script type="text/javascript" > 


$(document).ready(function(){
	
	$('#searchCases').click(function(){
		//alert("hiii");
		
		 $("#form")[0].action='<%=request.getContextPath()%>/<%=appName %>/searchCasesAct.action'; 
			$('#form').submit();
			 return true; 
	});

	$("#btn-submit").hide();
	
	$('#btn-Generate').click(function(){
		 $("#rege").val('R');
		 $("#form")[0].action='<%=request.getContextPath()%>/<%=appName %>/keymgmtAct.action'; 
			$('#form').submit();
			 return true; 
		
	});
	
	$('#btn-Cancel').click(function(){
		
		 $("#form")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
			$('#form').submit();
			 return true; 
	});
	
	$('#btn-submit').click(function(){ 
			
		 var locDat = "";
		 var mulDat = "";
		 var locData = [];
		 
		 $('#tbody_data > tr').each(function(ind){  
				 
					 var v_id= '#tbody_data > tr#'+$(this).attr('id');  
					 $(v_id+' > td').each(function(index){
						 if(index == 1 ){
							locDat=$(this).text().trim();
						 } else {
							locDat+=","+$(this).text().trim(); 
						}
 				 });  
					 
					locData.push(locDat); 
		 }); 
		 
		 for(ind=0;ind < locData.length ;ind ++){
			 if(ind ==0){
				 mulDat=locData[ind];
			 }else {
				 mulDat+="#"+locData[ind];
			 }
		 } 
		 
		 $("#multiData").val(mulDat);
		
			if($("#form").valid()) { 
 			 $("#form")[0].action='<%=request.getContextPath()%>/<%=appName %>/keyinsact.action'; 
				$('#form').submit();
				 return true;
			} else { 
				return false;
			}
	});
});

function undefinedcheck(data){
	return data == undefined  ? " " :  data;
}


$(function() {
	
	var val = 0;
	var rowindex = 1;
	var bankfinalData="${accBean.accDetails}";	
	var bankfinalDatarows=bankfinalData.split("#");

	var addclass = "";
	var offArr = '${officeLocation}'.split(",");

	if(bankfinalDatarows==""){
		
	/* 	var appendTxt = "<tr class="+addclass+" index='"+rowindex+"' id='"+rowindex+"'> "+
		 "<td style='text-align: center;' colspan='8'><b>No Records Found</b></td></tr>";
		 
		$("#tbody_data").append(appendTxt); */
		
	}
	else{
	for(var i=0;i<bankfinalDatarows.length;i++) {

			if(val % 2 == 0 ){
				addclass = "even";
				val++;
			} else {
				addclass = "odd";
				val++;
			}

		var eachrow=bankfinalDatarows[i];
		//console.log(eachrow);
		var eachfieldArr=eachrow.split(",");
		var userid=	eachfieldArr[0];
		var txnamt=	eachfieldArr[1];
		var txndate=	eachfieldArr[2];
		var fdate=eachfieldArr[3];
		var channel =eachfieldArr[4];
		var errordesc =eachfieldArr[5];
		var fid =eachfieldArr[6];
		var fstatus =eachfieldArr[7];
		var trpk 	=eachfieldArr[8];
		var vid 	=eachfieldArr[9];
		var accno 	=eachfieldArr[10];

		
		var regval="${accBean.authStatus}";
//console.log("asdasdasdas"+trpk);

	var appendTxt = "<tr class="+addclass+" index='"+rowindex+"' id='"+rowindex+"'> "+
	 "<td>"+rowindex+"</td>"+
	 "<td>"+userid+"</td>"+
	 "<td>"+accno+"</td>"+
	 "<td>"+txnamt+"</td>"+
	 "<td>"+txndate+"</td>"+
	 "<td>"+channel+"</td>"+
	 "<td>"+errordesc+"</td>"+
	 "<td>"+fstatus+"</td>"+
	 "<td style='display:none'>"+vid+"</td>"+
	 
	
/* 	 "<td><a class='btn btn-danger' href='#' id='verifytxn' title='Verify Transaction' data-rel='tooltip'><i class='icon icon-pencil icon-white'></i></a>&nbsp;&nbsp;&nbsp;</td>"+
 */	/*  "<a class='btn btn-warning' href='#' id='viewtxn' title='View Transaction Details' data-rel='tooltip'><i class='icon icon-edit icon-white'></i></a></td>"+ */
	"</tr>";


		$("#tbody_data").append(appendTxt);
		
		rowindex++;
	}
	}

	


		
	var val1 = 0;
	var rowindex1 = 1;
	var bankfinalData1="${accBean.status}";	
	var bankfinalDatarows1=bankfinalData1.split("#");

	var addclass = "";
	var offArr1 = '${officeLocation}'.split(",");

if(bankfinalDatarows1==""){
	
	/* var appendTxt1 = "<tr class="+addclass+" index='"+rowindex+"' id='"+rowindex+"'> "+
	 "<td style='text-align: center;' colspan='9'><b>No Records Found</b></td></tr>";
	 
	$("#tbody_data1").append(appendTxt1); */
	
}
else{
	for(var i=0;i<bankfinalDatarows1.length;i++) {

			if(val % 2 == 0 ){
				addclass = "even";
				val++;
			} else {
				addclass = "odd";
				val++;
			}

		var eachrow1=bankfinalDatarows1[i];
		//console.log("in next page1 "+eachrow1);
		var eachfieldArr1=eachrow1.split(",");
		var mobnum=	eachfieldArr1[0];
		var txnamt=	eachfieldArr1[1];
		var datcre=	eachfieldArr1[2];
		var frddte=undefinedcheck(eachfieldArr1[3]);
		var reqchnl =undefinedcheck(eachfieldArr1[4]);
		var errdesc =undefinedcheck(eachfieldArr1[5]);
		var frdid =undefinedcheck(eachfieldArr1[6]);
		var status =undefinedcheck(eachfieldArr1[7]);
		var remark =undefinedcheck(eachfieldArr1[8]);
		var verid =undefinedcheck(eachfieldArr1[9]);

		
		var regval1="${accBean.authStatus}";


	var appendTxt1 = "<tr class="+addclass+" index='"+rowindex+"' id='"+rowindex+"'> "+
	 "<td>"+rowindex1+"</td>"+
	 "<td>"+mobnum+"</td>"+
	 "<td>"+txnamt+"</td>"+
	 "<td>"+datcre+"</td>"+
	 "<td>"+errdesc+"</td>"+
	 "<td>"+frdid+"</td>"+
	 "<td>"+remark+"</td>"+
	 "<td>"+verid+"</td>"+
	 "<td>"+status+"</td>"+
	 
	"</tr>";


		
		$("#tbody_data1").append(appendTxt1);
		rowindex1++;
	}
}

});

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
    
	function postData(actionName,paramString){
		$('#form').attr("action", actionName)
				.attr("method", "post");
		
		var paramArray = paramString.split("&");
		var input = "" ;
		$(paramArray).each(function(indexTd,val) {
			if(val != "") {
				input = $("<input />").attr("type", "hidden").attr("name", val.split("=")[0]).val(val.split("=")[1].trim());
				$('form').append($(input));	 
			}
		});
	}


	$( "#view-txn" ).dialog({
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
	      OK: function() {
			 $( this ).dialog( "close" );
	      }
	    }
	  });
  
$( "#verify-message" ).dialog({
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
      Verify: function() {
       	
    	var rmrk= $('#remark').val();
       	var appd= $('#appid').val();
       	var unval= $('#uniqid').val();
       	var tomail=$('#tomail').val();
       	var invstat=$('#invstat').val();
     
        var v_id1=$(this).attr('id');
        $('#casestatus').val(invstat);
        $('#caseemail').val(tomail);
//alert(unval);

        var queryString='&rege='+rmrk+'&appid='+unval+'&type=verifytxn&tomail='+tomail+'&invstat='+invstat;
   
        console.log(queryString)
        v_action="verifytxn";
        
       //alert("hiiiiiiii");
        
        
        $("#mobno").val(rmrk);  
    
		 postData(v_action+".action",queryString);
			$("#form")[0].action="<%=ctxstr%>/<%=appName %>/"+v_action+".action";
			$("#form").submit();
		
		 $( this ).dialog( "close" );
      }
    }
  });
});
$(document).on('click',"a",function(event) {
	
	 var id=$(this).attr('id'); 
	 //alert(id);
	  
	 if(id=="fruad"){
	 var datato = "";
  		  			$('#DataTables_Table_11').html('');
  		  			
	   				datato+="<tr><td><label for='Request Key'><strong>1. The Very First Natmobile Transaction of an account</strong></label></td>";
   					datato+="<tr><td><label for='Responce Key'><strong>2. 5 Success Balance Enquiries on same day</strong></label></td>";
					datato+="<tr><td><label for='Transaction Date & Time'><strong>3. 2 Subsequent transaction done just close end of the day</strong></label></td>";
					datato+="<tr><td><label for='Transaction Date & Time'><strong>4. First Nat Mobile Transaction after contact update</strong></label></td>";
					datato+="<tr><td><label for='Transaction Date & Time'><strong>5. 2 Success Transaction transaction with total of 200000 NGN in 2 hours</strong></label></td>";
					datato+="<tr><td><label for='Transaction Date & Time'><strong>6. Natmobile Debit more than 60000 NGN in case of account to account transfer</strong></label></td>";
					datato+="<tr><td><label for='Transaction Date & Time'><strong>7. Natmobile transaction done on a account and debited from other channels on same day</strong></label></td>";

	   				$('#DataTables_Table_11').append(datato); 
					
					datato="";
					$('#DataTables-tbody').html('');
					$('#DataTables-tbody').append(datato);
	   				$( "#dialog-message" ).dialog( "open" );
	 }
	
		  var v_id=$(this).attr('id');
			
			var index1 = $(this).parent().closest('tr').attr('index');
			var parentId =$(this).parent().closest('tbody').attr('id'); 
			var searchTrRows = parentId+" tr"; 
			var searchTdRow = '#'+searchTrRows+"#"+index1 +' > td';
			var appid=null;
			var channel=null;
			var accno=null;
			var desc=null;
			var uniqid=null;
			var dttm=null;
			var cevaid=null;
			$(searchTdRow).each(function(indexTd) {
				
				 if (indexTd == 1) {		appid=$(this).text().trim();	 }
				 else if (indexTd == 2) {	accno=$(this).text().trim();	 }
				 else if (indexTd == 3) {	channel=$(this).text().trim();	 }
				 else if (indexTd == 4) {   dttm=$(this).text().trim();		 } 
				 else if (indexTd == 6) {   desc=$(this).text().trim();		 } 
				 else if (indexTd == 8) {   uniqid=$(this).text().trim();	 } 
				 else if (indexTd == 5) {	cevaid=$(this).text().trim(); }
				 else if (indexTd == 7) {   status=$(this).text().trim();	  
				
				 $("#appid").val(uniqid);	 }
				 $("#uniqid").val(uniqid);	
				 
			}); 
			//console.log("channel ["+channel+"] desc [" +desc+"] appid []"+appid+"] Unique ID []"+uniqid);
			var v_action="verifytxn";
			
			 if(id=="verifytxn"){
					datato = "";
  		  			$('#DataTables_Table_21').html('');
  		  			
 					datato+="<tr><td width='15%'><label for='Mobile Number'><strong>User id/ Mobile no :</strong></label></td>";
					datato+="<td width='15%' colspan=3>"+appid+"</td></tr> ";
					datato+="<tr><td><label for='Amount'><strong>Account No :</strong></label></td>";
					datato+="<td> "+accno+"</td> </tr> ";
					datato+="<tr><td><label for='Amount'><strong>Amount :</strong></label></td>";
					datato+="<td> "+channel+"</td> </tr> ";
					datato+="<tr><td><label for='Trasaction Date'><strong>Transaction Date :</strong></label></td>";
					datato+="<td> "+dttm+"</td> </tr> ";
					datato+="<tr><td><label for='Status'><strong>Description : </strong></label></td>";
					datato+="<td> "+desc+"</td> </tr> ";
					datato+="<tr><td><div style='display:none'>"+cevaid+"</div><label for='Status'><strong>Status : </strong></label></td>";
					
					//alert(status);
					datato+="<td><select id='invstat' name='invstat'><option value='N'>NEW CASE</option><option value='O'>OPEN</option><option value='E'>ESCALATED</option><option value='C'>CLOSED</option></select></td> </tr> ";
					 /*  if(status=="CLOSED"){
							datato+="<td><select id='invstat' name='invstat'><option value='C'>CLOSED</option><option value='N'>NEW CASE</option><option value='O'>OPEN</option><option value='E'>ESCALATED</option></select></td> </tr> ";
						}
		               if(status=="OPENED"){
							datato+="<td><select id='invstat' name='invstat'><option value='O'>OPEN</option><option value='N'>NEW CASE</option><option value='E'>ESCALATED</option><option value='C'>CLOSED</option></select></td> </tr> ";
						}
		               if(status=="NEW"){
							datato+="<td><select id='invstat' name='invstat'><option value='N'>NEW CASE</option><option value='C'>CLOSED</option><option value='O'>OPEN</option><option value='E'>ESCALATED</option></select></td> </tr> ";
						}
		               if(status=="ESCLATED"){
							datato+="<td><select id='invstat' name='invstat'><option value='E'>ESCALATED</option><option value='N'>NEW CASE</option><option value='O'>OPEN</option><option value='C'>CLOSED</option></select></td> </tr> ";
						}  */
					datato+="<tr><td><label for='Status'><strong>Remarks : </strong></label></td>";
					datato+="<td> <textarea rows='4' cols='120' id='remark' name='remark'></textarea></td> </tr> ";
					datato+="<tr><td><label for='Status'><strong>To-Mail : </strong></label></td>";
					datato+="<td> <input type='text' id='tomail' name='tomail'></textarea></td> </tr> ";
					
				
	   				$('#DataTables_Table_21').append(datato); 
					
					datato="";
					$('#DataTables-tbody').html('');
					$('#DataTables-tbody').append(datato);
	   				$( "#verify-message" ).dialog( "open" );
	 			}
	 else if(id=="viewtxn"){
		
		 var rrn_id = $(this).attr('role'); 
			var v_action="verifytxn";
			var queryString = '&appID='+uniqid+'&method=txndetails';
					$.getJSON("postJson.action", queryString,function(data) { 
					var TRANSACTION_VIEW = data.requestJSON.TRANSACTION_VIEW;
						
					datato = "";
  		  			$('#DataTables_Table_22').html('');
  		  			

  		  		datato+="<tr class='even'>";
  		  		datato+="<td width='25%'><label for='Transaction ID'><strong>Transaction ID</strong></label></td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.TXN_ID)+"</td>";
  		  		datato+="<td width='25%'><label for='Transaction Time'><strong>Transaction Time</strong></label> </td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.TXN_TIME)+"</td>";
  		  		datato+="</tr>";
  		  		datato+="<tr class='even'>";
  		  		datato+="<td width='25%'><label for='Transaction Amount'><strong>Transaction Amount</strong></label></td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.TXN_AMOUNT)+"</td>";
  		  		datato+="<td width='25%'><label for='PayBill Code'><strong>PayBill Code</strong></label> </td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.PAYBILL_SHORTCODE)+"</td>";
  		  		datato+="</tr>";
  		  		datato+="<tr class='even'>";
  		  		datato+="<td width='25%'><label for='Reference Number'><strong>Reference Number</strong></label></td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.BILL_REFNUM)+"</td>";
  		  		datato+="<td width='25%'><label for='Mobile Number'><strong>Mobile Number</strong></label> </td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.MOBILENUMBER)+"</td>";
  		  		datato+="</tr>";
  		  		datato+="<tr class='even'>";
  		  		datato+="<td width='25%'><label for='Posting Date'><strong>Posting Date</strong></label></td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.POSTAGEDATE)+"</td>";
  		  		datato+="<td width='25%'><label for='Status'><strong>Status</strong></label> </td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.STATUS)+"</td>";
  		  		datato+="</tr>";
  		  		datato+="<tr class='even'>";
  		  		datato+="<td width='25%'><label for='Transaction Type'><strong>Transaction Type</strong></label></td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.TXNTYPE)+"</td>";
  		  		datato+="<td width='25%'><label for='Paybill Response'><strong>Paybill Response</strong></label> </td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.PAYBILRESPONCE)+"</td>";
  		  		datato+="</tr>";
  		  		datato+="<tr class='even'>";
  		  		datato+="<td width='25%'><label for='Institute'><strong>Institute</strong></label></td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.INSTITUTE)+"</td>";
  		  		datato+="<td width='25%'><label for='Operator'><strong>Operator</strong></label> </td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.OPERATOR)+"</td>";
  		  		datato+="</tr>";
  		  		datato+="<tr class='even'>";
  		  		datato+="<td width='25%'><label for='Credit Account'><strong>Credit Account</strong></label></td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.CREDIT_AC)+"</td>";
  		  		datato+="<td width='25%'><label for='Debit Account'><strong>Debit Account</strong></label> </td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.DEBIT_AC)+"</td>";
  		  		datato+="</tr>";
  		  		datato+="<tr class='even'>";
  		  		datato+="<td width='25%'><label for='Channel'><strong>Channel</strong></label></td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.CHANNELID)+"</td>";
  		  		datato+="<td width='25%'><label for='Transaction Time'><strong>Posting RRN</strong></label> </td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.POSTING_RRN)+"</td>";
  		  		datato+="</tr>";
  		  		datato+="<tr class='even'>";
  		  		datato+="<td width='25%'><label for='Biller Type'><strong>Biller Type</strong></label></td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.BILLER_TYPE)+"</td>";
  		  		datato+="<td width='25%'><label for='Customer ID'><strong>Customer ID</strong></label> </td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.CUST_ID)+"</td>";
  		  		datato+="</tr>";
  		  		datato+="<tr class='even'>";
  		  		datato+="<td width='25%'><label for='Lattitude'><strong>Lattitude</strong></label></td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.LATTITUDE)+"</td>";
  		  		datato+="<td width='25%'><label for='Longitude'><strong>Longitude</strong></label> </td>";
  		  		datato+="<td width='30%'>"+undefinedcheck(TRANSACTION_VIEW.LONGITUDE)+"</td>";
  		  		datato+="</tr>";
  		  		
	   				$('#DataTables_Table_22').append(datato); 
					datato="";
					$('#DataTables-tbody').html('');
					$('#DataTables-tbody').append(datato);
	   				$( "#view-txn" ).dialog( "open" );
		 
	 });
	 }
	  			
		     
});
</script>

</head>

<body>
	<form name="form" id="form" method="post" action="">
		<div id="content" class="span10">
		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="#">Fruad Monitor</a></li>
				</ul>
			</div>
		<table height="3">
			 <tr>
				<%-- <td colspan="3">
					<div class="messages" id="messages"><s:actionmessage /></div>
					<div class="errors" id="errors"><s:actionerror /></div>
					<div id="rules"><a href="#" id="fruad" name="fruad" class="btn btn-minimize btn-round btn-danger" >Fraud Cases</a> </div>
				</td> --%>
				
				<td colspan="3">
					<div class="messages" id="messages"><s:actionmessage /></div>
					<div class="errors" id="errors"><s:actionerror /></div>
				</td>  
			</tr>
		</table>
		
		

		
		<div class="row-fluid sortable" >
			<div class="box span12" id="groupInfo" >
				<div class="box-header well" data-original-title>Fraud Transactions 
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
		
			<div class="box-content">
				<fieldset>
					<table  style = 'border: 1px solid #d7d7d7; font-family: Arial, Helvetica, sans-serif;font-size: 12px; color: #000000; font-weight: normal;' width='100%'
					class="table table-striped table-bordered bootstrap-datatable datatable" 
						id="mytable">
					  <thead>
						<tr>
							<th>Sno</th>
							<th>user id/Mobile No</th>
							<th>Account No</th>
							<th>Amount</th>
							<th>Transaction Date</th>
							<th>Channel</th>
							<th>Fraud Description</th>
							<th>Status</th>
							<!-- <th>Actions</th> -->
							
						</tr>
					  </thead>
					  <tbody id="tbody_data">
					  </tbody>
					</table>
				</fieldset>
			</div>
			<input type="hidden" name="multiData"  id="multiData" value="<s:property value='multiData' />"  />

			<input type="hidden" id="uniqid" name="uniqid" />
			</div>
		</div> 

		
		</div>
		
		<div id="dialog-message" title="List of Fraud Cases ">
	  <table   class="table table-striped table-bordered bootstrap-datatable " 
						id="DataTables_Table_11">
		</table>
		
	</div>  
		<div id="verify-message" title="Verify Transaction ">
	  <table   class="table table-striped table-bordered bootstrap-datatable " 
						id="DataTables_Table_21">
	   	 
		</table>
		
	</div>  
		<div id="view-txn" title="View Transaction ">
	  <table   class="table table-striped table-bordered bootstrap-datatable " 
						id="DataTables_Table_22">
	   	 
		</table>
		
	</div>  
		

<div >
         <input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Next" width="100" ></input>&nbsp;
         <!-- <input type="button" class="btn btn-danger" name="btn-Cancel" id="btn-Cancel" value="Back" width="100" ></input>&nbsp;
         <input type="button" class="btn btn-info" name="btn-Generate" id="btn-Generate" value="Re-Generate" width="100" ></input>&nbsp; -->
         <input type="hidden" id="casestatus" name="casestatus" >
	 	 <input type="hidden" id="caseemail" name="caseemail" >
         
         <span id ="error_dlno" class="errors"></span>
       </div>  
</form>      
</body>

</html>
