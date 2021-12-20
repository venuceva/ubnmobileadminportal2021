<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MicroInsurance</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%
	String ctxstr = request.getContextPath();
	String appName = session.getAttribute(
			CevaCommonConstants.ACCESS_APPL_NAME).toString();
%>
 <%@taglib uri="/struts-tags" prefix="s"%>
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

.messages {
	font-weight: bold;
	color: green;
	padding: 2px 8px;
	margin-top: 2px;
}

.errors {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}
</style>

<s:set value="responseJSON" var="respData"/>
<script type="text/javascript">
var parentId = "merchant-auth-data > tr";
var subParent = "";
var checkedCheckbox = false;

$(function() {
	var auth_code = "<s:property value='#respData.auth_code' />";
	var status = "<s:property value='#respData.status' />";
 
 
	if(auth_code == 'USERAUTH') {
		$('.merchant').text('User');
 	} else if(auth_code == 'MODUSERAUTH') {
		$('.merchant').text('Modified User ');
 	} else if(auth_code == 'MERCHAUTH') {
		$('.merchant').text('New Merchant');
 	} else if(auth_code == 'STOREAUTH') {
		$('.merchant').text('New Store');
 	} else if(auth_code == 'TERMAUTH') {
 		$('.merchant').text('New Terminal ');
 	
	} 	else if(auth_code == 'FEEAUTH') {
		$('.merchant').text('New Fee');
 	
	} 	else if(auth_code == 'SERVICEAUTH') {
		$('.merchant').text('Service ');
 	}
 	else if(auth_code == 'PRCSSAUTH') {
		$('.merchant').text('Transaction Code');
 	}else if(auth_code == 'BINAUTH') {
		$('.merchant').text('Bin Authrisation');
 	}
 	else if(auth_code == 'SERVICEAUTH') {
		$('.merchant').text('Service Authrisation');
 	}
 	else if(auth_code == 'SUBSEAUTH') {
		$('.merchant').text('Sub Service Authrisation');
 	}
 	else if(auth_code == 'USERSTATUSAUTH') {
		$('.merchant').text('User Status Authrisation');
 	}
 	else if(auth_code == 'TMMAUTH') {
		$('.merchant').text('Terminal Migration ');
 	} else if(auth_code == 'MPESATYPEAUTH') {
		$('.merchant').text('M-Pesa Type Status');
 	}  
 	else if(auth_code == 'MPESAACDEAUTH') {
		$('.merchant').text('M-Pesa Type Status');
 	} 
 	else if(auth_code == 'MPESABIDACDEAUTH') {
		$('.merchant').text('M-Pesa Type Status');
 	}
 	else if(auth_code == 'MPESAIDAUTH') {
		$('.merchant').text('M-Pesa ID Status');
 	}
 	else if(auth_code == 'NEWACCAUTH') {
		$('.merchant').text('Account Registration');
 	}
 	else if(auth_code == 'WNEWACCAUTH') {
		$('.merchant').text('Wallet Customer Registration');
 	}
 	else if(auth_code == 'AGNTAUTH') {
		$('.merchant').text('Agent Wallet Customer Registration');
 	}else if(auth_code == 'POSAUTH') {
		$('.merchant').text('POS Add Creation');
 	}else if(auth_code == 'POSMODIFYAUTH') {
		$('.merchant').text('POS Modify ');
 	}else if(auth_code == 'POSSTATUSAUTH') {
		$('.merchant').text('POS Status');
 	}else if(auth_code == 'STOREAUTH') {
		$('.merchant').text('Store Creation');
 	}else if(auth_code == 'STOREMODAUTH') {
		$('.merchant').text('Store Modify');
 	}else if(auth_code == 'STORESTATUSAUTH') {
		$('.merchant').text('Store Status');
 	}else if(auth_code == 'TERMINALAUTH') {
		$('.merchant').text('Terminal Creation');
 	}else if(auth_code == 'TERMODIFYAUTH') {
		$('.merchant').text('Terminal Modify');
 	}else if(auth_code == 'TERSTATUSAUTH') {
		$('.merchant').text('Terminal Status');
 	}
 	else if(auth_code == 'AGNTBLOCK') {
		$('.merchant').text('Agent Wallet Customer Registration');
 	}
 	else if(auth_code == 'AGNTSTATUSAUTH') {
		$('.merchant').text('Agent Wallet Customer Registration');
 	}
 	else if(auth_code == 'AGNTMODIFY') {
		$('.merchant').text('Agent Wallet Customer Modify');
 	}
 	else if(auth_code == 'AGNTPRDUPDATE') {
		$('.merchant').text('Agent Wallet Customer Product Update');
 	}
 	else if(auth_code == 'COMMSWREAUT') {
		$('.merchant').text('Agent Commission Sweep Authorization');
 	}
 	else if(auth_code == 'TUPWATAUTH') {
		$('.merchant').text('Wallet Account Topup');
 	}
 	else if(auth_code == 'REFWATAUTH') {
		$('.merchant').text('Wallet Account Refund');
 	}
 	else if(auth_code == 'ACCACTDCT') {
		$('.merchant').text('Mobile Banking Account Active/De-Active');
 	}
 	else if(auth_code == 'WACCACTDCT') {
		$('.merchant').text('Wallet Account Active/De-Active');
 	}
 	else if(auth_code == 'ENLDSBCUST') {
		$('.merchant').text('Mobile Banking Customer Enable/Disable');
 	}
 	else if(auth_code == 'WENLDSBCUST') {
		$('.merchant').text('Wallet Banking Customer Enable/Disable');
 	}
 	else if(auth_code == 'ACCTPINRESET') {
		$('.merchant').text('PIN Reset ');
 	}else if(auth_code == 'WACCTPINRESET') {
		$('.merchant').text('PIN Reset ');
 	}else if(auth_code == 'WPASSWORDRESET') {
		$('.merchant').text('Password Reset ');
 	}else if(auth_code == 'WUSSDENB') {
		$('.merchant').text('USSD Channel Enable/Disable ');
 	}else if(auth_code == 'WMOBILEENB') {
		$('.merchant').text('Mobile Channel Enable/Disable ');
 	}else if(auth_code == 'MUSSDENB') {
		$('.merchant').text('USSD Channel Enable/Disable ');
 	}else if(auth_code == 'MMOBILEENB') {
		$('.merchant').text('Mobile Channel Enable/Disable ');
 	}  
 	else if(auth_code == 'DELACCAUTH') {
		$('.merchant').text('Delete Mobile Banking Accounts ');
 	}
 	else if(auth_code == 'WDELACCAUTH') {
		$('.merchant').text('Delete Wallet Accounts ');
 	}
 	else if(auth_code == 'MODCUSTDETAUTH') {
		$('.merchant').text('Modify Mobile Banking Customer ');
 	}
 	else if(auth_code == 'LMTCUSTDETAUTH') {
		$('.merchant').text('Limit Enhancement Mobile Banking Customer ');
 	}
 	else if(auth_code == 'ADDNEWACC') {
		$('.merchant').text('Added New Account Customer Details ');
 	}else if(auth_code == 'DEVICEPLMNT') {
		$('.merchant').text('Device Replacement Customer Details ');
 	}
 	else if(auth_code == 'WMODCUSTDETAUTH') {
		$('.merchant').text('Modify Wallet Details ');
 	}
	else if(auth_code == 'MPESAADDBIDAUTH') {
		$('.merchant').text('M-Pesa Add BillerID');
 	}
 	else if(auth_code == 'PRODUCTCREAUTH') {
		$('.merchant').text('Product Management ');
 	}
 	else if(auth_code == 'MPESAMODBIDSTATUSAUTH') {
		$('.merchant').text('M-Pesa Modify Biller Id Status');
 	}
 	else if(auth_code == 'BULKREGAUTH') {
		$('.merchant').text('Bulk Registration');
 	}else if(auth_code == 'MERNEWAUTH') {
		$('.merchant').text('Merchant Creation');
 	}
 	else if(auth_code == 'MERSTSAUTH') {
		$('.merchant').text('Merchant Status Update');
 	}
 	else if(auth_code == 'SUPERAGENTAUTH') {
		$('.merchant').text('Super Agent Creation');
 	}
 	else if(auth_code == 'SUPERAGENTSTATUSAUTH') {
		$('.merchant').text('Agent Status Status Update');
 	}


	// add multiple select / deselect functionality
	$("#select-all").click(function () {
		$("#error_dlno").text("");
		$('.center-chk').attr('checked', this.checked);
	});

	// if all checkbox are selected, check the selectall checkbox
	// and viceversa
	$(".center-chk").click(function(){
		$("#error_dlno").text("");
		if($(".center-chk").length == $(".center-chk:checked").length) {
			$("#select-all").attr("checked", "checked");
		} else {
			$("#select-all").removeAttr("checked");
		}
	});

	$("#btn-back").click(function(event) {
		event.preventDefault();
		<%-- var url="${pageContext.request.contextPath}/<%=appName %>/AuthorizationAll.action";
		$("#form1")[0].action=url; --%>
		
		var actype= $('#acttype').val(); 		
		if (actype=="REG"){
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/authreglist.action';		
		}
		else{
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/AuthorizationAll.action';
		}
		$("#form1").submit();
	});

	$("#btn-submit").click(function(event) {
		event.preventDefault();
		var searchIDs="";
	 
 		$("tbody#merchant-auth-data input:radio:checked").each(function(index) {
 			searchIDs=$(this).val(); 
 		   $('#REF_NO').val(searchIDs);
 		  $('#MID').val($(this).attr('id'));
		});

        if(searchIDs.length == 0) {
			$("#error_dlno").text("Please check atleast one record.");
		} else {
			
			var url="${pageContext.request.contextPath}/<%=appName %>/commonAuthListCnf.action";
			$("#form1")[0].action=url;
			$("#form1").submit();

		}
	}); 
	
	
	
	
	$("#btn-view").click(function(event) {
		event.preventDefault();
		var searchIDs="";
	 
 		$("tbody#merchant-auth-data input:radio:checked").each(function(index) {
 			searchIDs=$(this).val(); 
 		  
		});

        if(searchIDs.length == 0) {
			$("#error_dlno").text("Please check atleast one record.");
		} else {
		var queryString9 = "method=authactionview&fname="+searchIDs;
		var htmlString="";
			$.getJSON("postJson.action", queryString9,function(data) {
				var feefinaljsonobj = jQuery.parseJSON(data.message);
			
					
					htmlString = htmlString + "<table width=\"950\"  border=\"0\" cellpadding=\"5\" cellspacing=\"1\" class=\"table table-striped table-bordered bootstrap-datatable\" id=\"check-kra-details\" >";
					htmlString = htmlString + "<tr class=\"odd\">";
						htmlString = htmlString + "<td width=\"25%\"><label for=\"File\"><strong>Maker Id</strong></label></td>";
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.MAKER_ID + "</td>";
					
						htmlString = htmlString + "<td width=\"25%\"><label for=\"Client\"><strong>Maker Date</strong></label></td>"; 
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.MAKER_DTTM+  "</td>";
						
						htmlString = htmlString + "</tr>";
						htmlString = htmlString + "<tr class=\"even\">";
						htmlString = htmlString + "<td width=\"25%\"><label for=\"File\"><strong>Checker Id</strong></label></td>";
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.CHECKER_ID+ "</td>";
					
						htmlString = htmlString + "<td width=\"25%\"><label for=\"Client\"><strong>Checker Date</strong></label></td>"; 
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.CHECKER_DTTM+ "</td>";
						
						htmlString = htmlString + "</tr>";
						htmlString = htmlString + "<tr class=\"even\">";
						htmlString = htmlString + "<td width=\"25%\"><label for=\"File\"><strong>Main Menu</strong></label></td>";
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.MAIN_MENU+ "</td>";
					
						htmlString = htmlString + "<td width=\"25%\"><label for=\"Client\"><strong>Action Link</strong></label></td>"; 
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.ACTION_MENU+ "</td>";
						
						htmlString = htmlString + "</tr>";	
						htmlString = htmlString + "<tr class=\"even\">";
						htmlString = htmlString + "<td width=\"25%\"><label for=\"File\"><strong>Status</strong></label></td>";
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.STATUS+ "</td>";
					
						htmlString = htmlString + "<td width=\"25%\"></td>"; 
						htmlString = htmlString + "<td width=\"25%\"></td>"; 
						
						htmlString = htmlString + "</tr>";
						htmlString = htmlString + "<tr class=\"even\">";
						htmlString = htmlString + "<td width=\"25%\"><label for=\"File\"><strong>Action Details</strong></label></td>";
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.ACTION_DETAILS+ "</td>";
					
						htmlString = htmlString + "<td width=\"25%\"><label for=\"Client\"><strong>Reason</strong></label></td>"; 
						htmlString = htmlString + "<td width=\"25%\">"+feefinaljsonobj.REASON+ "</td>";
						
						htmlString = htmlString + "</tr>";			
				
					
					htmlString = htmlString + "</table>";
					
					
					
			
				
				
				 $( "#dialog" ).dialog( "option", "title", "Maker Id ::   "+feefinaljsonobj.MAKER_ID);
					$( "#pie" ).html(htmlString);	  
				   $("#dialog").dialog("open");
			/* var htmlString1="";
			htmlString1 = htmlString1 + "<table class='table table-striped table-bordered bootstrap-datatable datatable'>";
			htmlString1 = htmlString1 + "<tr ><td>Account Number</td><td>"+data.message+"</td></tr>";
			
			
			htmlString1 = htmlString1 + "</table>"; */
			
			
			
			});
		}
	}); 
	
	

});


$(function() {
	$( "#dialog" ).dialog(
			{
			autoOpen: false,
			modal: true,
		    draggable: false,
		    resizable: false,
		    show: 'blind',
		    hide: 'blind',
			width: 800, 
		    height: 400,
		    buttons: {
		        "OK": function() {
		            $(this).dialog("close");
		        }
		    }
			}
		);
	});
	
	
	


</script>
</head>
<body>
<form name="form1" id="form1" method="post" >
	<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">All Authorization</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#"><span id="header-data" class="merchant"> </span> Confirmation </a></li>
				</ul>
			</div>
			
			<div id="dialog" name="dialog" title="Maker Action View" width="100" style="display:none">
  						<strong><div  id="result"><div id="pie"></div>
  						</div></strong>
					</div>
			
			<table height="3">
				 <tr>
					<td colspan="3">
						<div class="messages" id="messages"><s:actionmessage /></div>
						<div class="errors" id="errors"><s:actionerror /></div>
					</td>
				</tr>
			 </table>
			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<span id="header-data" class="merchant"> </span> Information
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a> <a href="#"
								class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content" id="primaryDetails" style="display:none">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable" >
								<tr class="even">
									<td colspan="4"><strong><font color="red">No Records Found On DataBase for the Search Criteria Provided.</font></strong></td>
								</tr>
							</table>
						</fieldset>
					</div>
					<div class="box-content" id="merch-details">
						<fieldset>
							<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable"
								id="DataTables_Table_0">
								<thead id="merchant-auth-header">
									<tr>
										<th> </th>
										<s:generator val="%{#respData.headerData}"
											var="bankDat" separator="##" >  
											<s:iterator status="itrStatus">  
													<th><s:property /></th> 
											</s:iterator>  
										</s:generator> 
									</tr> 
								</thead>
								<tbody id="merchant-auth-data">
									<s:iterator value="#respData.agentMultiData" var="authCommonList" status="authCommonStatus">  
										<s:if test="#authCommonStatus.even == true" > 
											<tr class="even" index="<s:property value='#authCommonStatus.index' />"  id="<s:property value='#authCommonStatus.index' />">
										 </s:if>
										 <s:elseif test="#authCommonStatus.odd == true">
		      								<tr class="odd" index="<s:property value='#authCommonStatus.index' />"  id="<s:property value='#authCommonStatus.index' />">
		   								 </s:elseif> 
		   								 
		   								 <td ><input class='center-chk' type='radio' id='<s:property value="#authCommonList['MAKER_ID']" />' name='<s:property value="#authCommonList['MAKER_ID']" />' value='<s:property value="#authCommonList['REF_NO']" />' /></td>
		   								 
		   								 <s:if test="#respData.auth_code == 'USERAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['LOGIN_USER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['USER_NAME']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>   
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:if> 
		   								 <s:elseif test="#respData.auth_code == 'SERVICEAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['SERVICE_CODE']" /> </td>   
 											<td><s:property value="#authCommonList['SERVICE_NAME']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_DATE']" /> </td>  
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['status']" /></a> </td> 
		   								 </s:elseif> 
		   								  <s:elseif test="#respData.auth_code == 'BINAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['BIN_CODE']" /> </td>   
 											<td><s:property value="#authCommonList['BANK_NAME']" /> </td> 
 											<td><s:property value="#authCommonList['BIN']" /> </td>   
 											<td><s:property value="#authCommonList['BIN_DESC']" /> </td>     
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_DATE']" /> </td>  
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'SUBSEAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['SUB_SERVICE_CODE']" /> </td>   
 											<td><s:property value="#authCommonList['SUB_SERVICE_NAME']" /> </td>
											<td><s:property value="#authCommonList['SERVICE_CODE']" /> </td>   
 											<td><s:property value="#authCommonList['SERVICE_NAME']" /> </td>     
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_DATE']" /> </td>  
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif> 
		   								 
		   								 <s:elseif test="#respData.auth_code == 'FEEAUTH' " >		
	   								 		<td><s:property value="#authCommonList['FEE_CODE']" /> </td> 
	   								 		<td><s:property value="#authCommonList['SERVICE_CODE']" /> </td>         								 	
 											<td><s:property value="#authCommonList['SUB_SERVICE_CODE']" /> </td>   
 											<td><s:property value="#authCommonList['FEE_NAME']" /> </td> 
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_DATE']" /> </td>  
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif> 
		   								   <s:elseif test="#respData.auth_code == 'MODUSERAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['LOGIN_USER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['USER_NAME']" /> </td> 
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								  <s:elseif test="#respData.auth_code == 'USERSTATUSAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['User_id']" /> </td>   
 											<td><s:property value="#authCommonList['User_Name']" /> </td> 
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DATE']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif> 
		   								 <s:elseif test="#respData.auth_code == 'MPESATYPEAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['ID']" /> </td>   
 											<td><s:property value="#authCommonList['BILLER_TYPE_NAME']" /> </td> 
 											<td><s:property value="#authCommonList['BILLER_TYPE_DESC']" /> </td>  
 											<td><s:property value="#authCommonList['BFUB_CRE_ACCT']" /> </td>    
 											<td><s:property value="#authCommonList['BFUB_DRE_ACCT']" /> </td> 
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif> 
		   								  <s:elseif test="#respData.auth_code == 'MPESAIDAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['ID']" /> </td>   
 											<td><s:property value="#authCommonList['BILLER_ID_NAME']" /> </td> 
 											<td><s:property value="#authCommonList['BFUB_CRE_ACCT']" /> </td>  
 											<td><s:property value="#authCommonList['BFUB_DRE_ACCT']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>  
		   								 <s:elseif test="#respData.auth_code == 'MPESAMODACDEAUTH' 
		   								  			|| #respData.auth_code == 'MPESABTSTATUSAUTH'
		   								  			|| #respData.auth_code == 'MPESABIDSTATUSAUTH'" >		   								 	
 											<td><s:property value="#authCommonList['ID']" /> </td>   
 											<td><s:property value="#authCommonList['BILLER_TYPE_NAME']" /> </td> 
 											<td><s:property value="#authCommonList['BILLER_TYPE_DESC']" /> </td>  
 											<td><s:property value="#authCommonList['BFUB_CRE_ACCT']" /> </td>
 											<td><s:property value="#authCommonList['BFUB_DRE_ACCT']" /> </td>        
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								  <s:elseif test=" #respData.auth_code == 'MPESAMODBIDACDEAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['BILLER_TYPE_DESC']" /> </td>  
 											<td><s:property value="#authCommonList['BFUB_CRE_ACCT']" /> </td>
 											<td><s:property value="#authCommonList['BFUB_DRE_ACCT']" /> </td>        
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								  <s:elseif test="#respData.auth_code == 'MPESAADDBIDAUTH'" >		   								 	
 											<td><s:property value="#authCommonList['BILLER_TYPE_NAME']" /> </td> 
 											<td><s:property value="#authCommonList['BILLER_TYPE_DESC']" /> </td>  
 											<td><s:property value="#authCommonList['BFUB_CRE_ACCT']" /> </td>
 											<td><s:property value="#authCommonList['BFUB_DRE_ACCT']" /> </td>        
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'NEWACCAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td> 
 											<td><s:property value="#authCommonList['NATIONALID']" /> </td>  
 											<td><s:property value="#authCommonList['MOBILENUMBER']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif> 
		   								 <s:elseif test="#respData.auth_code == 'WNEWACCAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td> 
 											<td><s:property value="#authCommonList['NATIONALID']" /> </td>  
 											<td><s:property value="#authCommonList['MOBILENUMBER']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'AGNTAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td> 
 											<td><s:property value="#authCommonList['NATIONALID']" /> </td>  
 											<td><s:property value="#authCommonList['MOBILENUMBER']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'AGNTBLOCK' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td> 
 											<td><s:property value="#authCommonList['NATIONALID']" /> </td>  
 											<td><s:property value="#authCommonList['MOBILENUMBER']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'AGNTSTATUSAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td> 
 											<td><s:property value="#authCommonList['NATIONALID']" /> </td>  
 											<td><s:property value="#authCommonList['MOBILENUMBER']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'AGNTMODIFY' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td> 
 											<td><s:property value="#authCommonList['NATIONALID']" /> </td>  
 											<td><s:property value="#authCommonList['MOBILENUMBER']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'AGNTPRDUPDATE' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td> 
 											<td><s:property value="#authCommonList['NATIONALID']" /> </td>  
 											<td><s:property value="#authCommonList['MOBILENUMBER']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'COMMSWREAUT' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td> 
 											<td><s:property value="#authCommonList['NATIONALID']" /> </td>  
 											<td><s:property value="#authCommonList['MOBILENUMBER']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'TUPWATAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td> 
 											<td><s:property value="#authCommonList['NATIONALID']" /> </td>  
 											<td><s:property value="#authCommonList['MOBILENUMBER']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'REFWATAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td> 
 											<td><s:property value="#authCommonList['NATIONALID']" /> </td>  
 											<td><s:property value="#authCommonList['MOBILENUMBER']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif> 
		   								 <s:elseif test="#respData.auth_code == 'NEWWATAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td> 
 											<td><s:property value="#authCommonList['NATIONALID']" /> </td>  
 											<td><s:property value="#authCommonList['MOBILENUMBER']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif> 
		   								  <s:elseif test="#respData.auth_code == 'ACCACTDCT' " >		   								 	
 											<td><s:property value="#authCommonList['ACCNO']" /> </td>   
 											<td><s:property value="#authCommonList['ACCNAME']" /> </td> 
 											<td><s:property value="#authCommonList['ALIASNAME']" /> </td>  
 											<td><s:property value="#authCommonList['BRCODE']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif> 
		   								 <s:elseif test="#respData.auth_code == 'WACCACTDCT' " >		   								 	
 											<td><s:property value="#authCommonList['ACCNO']" /> </td>   
 											<td><s:property value="#authCommonList['ACCNAME']" /> </td> 
 											<td><s:property value="#authCommonList['ALIASNAME']" /> </td>  
 											<td><s:property value="#authCommonList['BRCODE']" /> </td>    
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'ENLDSBCUST' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td>  
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								</s:elseif> 
		   								 <s:elseif test="#respData.auth_code == 'WENLDSBCUST' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td>  
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
		   								<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>  
		   								   <s:elseif test="#respData.auth_code == 'ACCTPINRESET' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td>  
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'WACCTPINRESET' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td>  
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'WPASSWORDRESET' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td>  
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'WUSSDENB' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td>  
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'WMOBILEENB' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td>  
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'MUSSDENB' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td>  
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'MMOBILEENB' " >		   								 	
 											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
 											<td><s:property value="#authCommonList['NAME']" /> </td>  
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
										<s:elseif test="#respData.auth_code == 'MODCUSTDETAUTH' " >	
											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
											<td><s:property value="#authCommonList['NAME']" /> </td>   
											<td><s:property value="#authCommonList['NATIONALID']" /> </td>   
											<td><s:property value="#authCommonList['MOBILENO']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'LMTCUSTDETAUTH' " >	
											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
											<td><s:property value="#authCommonList['NAME']" /> </td>   
											<td><s:property value="#authCommonList['NATIONALID']" /> </td>   
											<td><s:property value="#authCommonList['MOBILENO']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'ADDNEWACC' " >	
											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
											<td><s:property value="#authCommonList['NAME']" /> </td>   
											<td><s:property value="#authCommonList['NATIONALID']" /> </td>   
											<td><s:property value="#authCommonList['MOBILENO']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'DEVICEPLMNT' " >	
											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
											<td><s:property value="#authCommonList['NAME']" /> </td>   
											<td><s:property value="#authCommonList['NATIONALID']" /> </td>   
											<td><s:property value="#authCommonList['MOBILENO']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'WMODCUSTDETAUTH' " >	
											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
											<td><s:property value="#authCommonList['NAME']" /> </td>   
											<td><s:property value="#authCommonList['NATIONALID']" /> </td>   
											<td><s:property value="#authCommonList['MOBILENO']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>											
										<s:elseif test="#respData.auth_code == 'DELACCAUTH' " >	
											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
											<td><s:property value="#authCommonList['NAME']" /> </td>   
											<td><s:property value="#authCommonList['ACC_NO']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>											
										<s:elseif test="#respData.auth_code == 'WDELACCAUTH' " >	
											<td><s:property value="#authCommonList['CUSTCODE']" /> </td>   
											<td><s:property value="#authCommonList['NAME']" /> </td>   
											<td><s:property value="#authCommonList['ACC_NO']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
										 <s:elseif test="#respData.auth_code == 'MPESAMODBIDSTATUSAUTH' " >	
										    <td><s:property value="#authCommonList['BILLER_TYPE_NAME']" /> </td> 
 											<td><s:property value="#authCommonList['BILLER_TYPE_DESC']" /> </td>  
 											<td><s:property value="#authCommonList['BFUB_CRE_ACCT']" /> </td>
 											<td><s:property value="#authCommonList['BFUB_DRE_ACCT']" /> </td>        
  											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>    
 											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
										 
										 <s:elseif test="#respData.auth_code == 'PRODUCTCREAUTH' " >	
											<td><s:property value="#authCommonList['AccountID']" /> </td>   
											<td><s:property value="#authCommonList['ChannelID']" /> </td>   
											<td><s:property value="#authCommonList['ProductID']" /> </td>   
											<td><s:property value="#authCommonList['CustomerType']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>
		   								 
		   								 <s:elseif test="#respData.auth_code == 'BULKREGAUTH' " >	
											<td><s:property value="#authCommonList['FILENAME']" /> </td>   
											<%-- <td><s:property value="#authCommonList['ChannelID']" /> </td>   
											<td><s:property value="#authCommonList['ProductID']" /> </td>   
											<td><s:property value="#authCommonList['CustomerType']" /> </td>  --%>  
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								 </s:elseif>	
		   								 
		   								 <s:elseif test="#respData.auth_code == 'MERCHAUTH' " >		   								 	
 											<td><s:property value="#authCommonList['MER_ID']" /></td>   
 											<td><s:property value="#authCommonList['MER_NAME']" /></td> 
 											<td><s:property value="#authCommonList['makerId']" /></td>   
 											<td><s:property value="#authCommonList['makerDate']" /></td> 
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a></td> 
		   								 </s:elseif> 	
		   								 
		   								 
		 								 <s:elseif test="#respData.auth_code == 'STOREAUTH' " >		   								 	
 							     			<td><s:property value="#authCommonList['STORE_ID']" /> </td>   
 											<td><s:property value="#authCommonList['STORE_NAME']" /> </td> 
 											<td><s:property value="#authCommonList['MERCHANT_ID']" /> </td> 
 											<td><s:property value="#authCommonList['MERCHANT_NAME']" /> </td> 
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_DATE']" /> </td>
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								  </s:elseif> 	
		   								  
		   								  <s:elseif test="#respData.auth_code == 'TERMAUTH' " >	
		   								  	<td><s:property value="#authCommonList['TERMINAL_ID']" /> </td>      								 	
 											<td><s:property value="#authCommonList['STORE_ID']" /> </td>   
 											<td><s:property value="#authCommonList['MERCHANT_ID']" /> </td> 
 											<td><s:property value="#authCommonList['SERIAL_NO']" /> </td> 
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_DATE']" /> </td>
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								  </s:elseif> 	
		   								  <s:elseif test="#respData.auth_code == 'MERMODAUTH' " >	
		   								  	<td><s:property value="#authCommonList['MERCHANT_ID']" /> </td>      								 	
 											<td><s:property value="#authCommonList['MANAGER_NAME']" /> </td>   
 											<td><s:property value="#authCommonList['EMAIL']" /> </td> 
 											<td><s:property value="#authCommonList['MOBILE']" /> </td> 
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_DATE']" /> </td>
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								  </s:elseif> 
		   								  
		   								 <s:elseif test="#respData.auth_code == 'MERCHSTATUSAUTH' " >	
		   								  	<td><s:property value="#authCommonList['MERCHANT_ID']" /> </td>      								 	
 											<td><s:property value="#authCommonList['MERCHANT_NAME']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>   
 											<td><s:property value="#authCommonList['MAKER_DATE']" /> </td>
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								  </s:elseif>	
		   								  
		   								  		
		   								  
		   								  <s:elseif test="#respData.auth_code == 'TERMMODAUTH' " >	
		   							     	<td><s:property value="#authCommonList['TERMINAL_ID']" /></td>      								 	
 											<td><s:property value="#authCommonList['STORE_ID']" /> </td>  
 											<td><s:property value="#authCommonList['MERCHANT_ID']" /> </td>
 											<td><s:property value="#authCommonList['SERIAL_NO']" /> </td>
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
 											<td><s:property value="#authCommonList['MAKER_DATE']" /> </td>   
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								  </s:elseif>  
		   								  
		   								  <s:elseif test="#respData.auth_code == 'TERMSTATUSAUTH' " >
		   								    <td><s:property value="#authCommonList['MERCHANT_ID']" /> </td>	
		   								    <td><s:property value="#authCommonList['STORE_ID']" /> </td> 
		   							     	<td><s:property value="#authCommonList['TERMINAL_ID']" /></td>      								 	
 											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
 											<td><s:property value="#authCommonList['MAKER_DATE']" /> </td>   
 											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td> 
		   								  </s:elseif> 
		   								  
		   								  <s:elseif test="#respData.auth_code == 'MERNEWAUTH' " >
											<td><s:property value="#authCommonList['ACCOUNTNUMBER']" /> </td>
											<td><s:property value="#authCommonList['ACCOUNTNAME']" /> </td>
											<td><s:property value="#authCommonList['MANAGERNAME']" /> </td>
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								 
		   								 <s:elseif test="#respData.auth_code == 'MERSTSAUTH' " >
											<td><s:property value="#authCommonList['ACCOUNTNUMBER']" /> </td>
											<td><s:property value="#authCommonList['ACCOUNTNAME']" /> </td>
											<td><s:property value="#authCommonList['MANAGERNAME']" /> </td>
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								 
		   								 <s:elseif test="#respData.auth_code == 'SUPERAGENTAUTH' " >
											<td><s:property value="#authCommonList['ACCOUNTNUMBER']" /> </td>
											<td><s:property value="#authCommonList['ACCOUNTNAME']" /> </td>
											<td><s:property value="#authCommonList['MANAGERNAME']" /> </td>
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								 
		   								 <s:elseif test="#respData.auth_code == 'SUPERAGENTSTATUSAUTH' " >
											<td><s:property value="#authCommonList['ACCOUNTNUMBER']" /> </td>
											<td><s:property value="#authCommonList['ACCOUNTNAME']" /> </td>
											<td><s:property value="#authCommonList['MANAGERNAME']" /> </td>
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								 
		   								 <s:elseif test="#respData.auth_code == 'POSAUTH' " >
											<td><s:property value="#authCommonList['CUSTOMER_CODE']" /> </td>
											<td><s:property value="#authCommonList['FIRST_NAME']" /> </td>
											<td><s:property value="#authCommonList['MOBILE_NUMBER']" /> </td>
											<td><s:property value="#authCommonList['SERIAL_NO']" /> </td>
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'POSMODIFYAUTH' " >
											<td><s:property value="#authCommonList['CUSTOMER_CODE']" /> </td>
											<td><s:property value="#authCommonList['FIRST_NAME']" /> </td>
											<td><s:property value="#authCommonList['MOBILE_NUMBER']" /> </td>
											<td><s:property value="#authCommonList['SERIAL_NO']" /> </td>
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								   <s:elseif test="#respData.auth_code == 'POSSTATUSAUTH' " >
											<td><s:property value="#authCommonList['CUSTOMER_CODE']" /> </td>
											<td><s:property value="#authCommonList['FIRST_NAME']" /> </td>
											<td><s:property value="#authCommonList['MOBILE_NUMBER']" /> </td>
											<td><s:property value="#authCommonList['SERIAL_NO']" /> </td>
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'STOREAUTH' " >
											<td><s:property value="#authCommonList['CUSTOMER_CODE']" /> </td>
											<td><s:property value="#authCommonList['MOBILE_NUMBER']" /> </td>
											<td><s:property value="#authCommonList['STORE_ID']" /> </td>
											<td><s:property value="#authCommonList['STORE_NAME']" /> </td>											
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'STOREMODAUTH' " >
											<td><s:property value="#authCommonList['CUSTOMER_CODE']" /> </td>
											<td><s:property value="#authCommonList['MOBILE_NUMBER']" /> </td>
											<td><s:property value="#authCommonList['STORE_ID']" /> </td>
											<td><s:property value="#authCommonList['STORE_NAME']" /> </td>											
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'STORESTATUSAUTH' " >
											<td><s:property value="#authCommonList['CUSTOMER_CODE']" /> </td>
											<td><s:property value="#authCommonList['MOBILE_NUMBER']" /> </td>
											<td><s:property value="#authCommonList['STORE_ID']" /> </td>
											<td><s:property value="#authCommonList['STORE_NAME']" /> </td>											
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'TERMINALAUTH' " >
											<td><s:property value="#authCommonList['TERMINAL_ID']" /> </td>
											<td><s:property value="#authCommonList['TERMINAL_MAKE']" /> </td>
											<td><s:property value="#authCommonList['MODEL_NO']" /> </td>
											<td><s:property value="#authCommonList['SERIAL_NO']" /> </td>											
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'TERMODIFYAUTH' " >
											<td><s:property value="#authCommonList['TERMINAL_ID']" /> </td>
											<td><s:property value="#authCommonList['TERMINAL_MAKE']" /> </td>
											<td><s:property value="#authCommonList['MODEL_NO']" /> </td>
											<td><s:property value="#authCommonList['SERIAL_NO']" /> </td>											
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		   								 <s:elseif test="#respData.auth_code == 'TERSTATUSAUTH' " >
											<td><s:property value="#authCommonList['TERMINAL_ID']" /> </td>
											<td><s:property value="#authCommonList['TERMINAL_MAKE']" /> </td>
											<td><s:property value="#authCommonList['MODEL_NO']" /> </td>
											<td><s:property value="#authCommonList['SERIAL_NO']" /> </td>											
											<td><s:property value="#authCommonList['MAKER_ID']" /> </td>
											<td><s:property value="#authCommonList['MAKER_DTTM']" /> </td>
											<td class='hidden-phone'><a href='#' class='label label-info' index='#authCommonStatus.index'><s:property value="#authCommonList['STATUS']" /></a> </td>
		   								 </s:elseif>
		 								</tr> 
									 </s:iterator>
								</tbody>
							</table>
						</fieldset>
					</div>
				</div>
			</div>
			<div class="form-actions"> 
				<input type="button" class="btn btn-info" name="btn-back" id="btn-back" value="Back"  /> 
				<input type="button" class="btn btn-success" name="btn-view"	id="btn-view" value="Maker Action View"   /> 
				<input type="button" class="btn btn-success" name="btn-submit"	id="btn-submit" value="Next"  /> 
			
				<span id ="error_dlno" class="errors"></span> 
  				<input name="STATUS" type="hidden" id="STATUS" value="<s:property value="#respData.status" />" />
  				<input name="AUTH_CODE" type="hidden" id="AUTH_CODE" value="<s:property value="#respData.auth_code" />"  />
				<input type="hidden" name="REF_NO" id="REF_NO" /> 
				<input type="hidden" name="MID" id="MID" />
				<input type="hidden" id="acttype" name="acttype" value="<s:property value="#respData.acttype" />" />
			</div>
 	</div>
 	
</form>
<form name="form2" id="form2" method="post">
</form>
<script type="text/javascript"> 
	if($('#merchant-auth-data > tr').length == 0)  {
		$('#primaryDetails').show();
		$('#merch-details').hide();
		$('#btn-submit').hide();
		$('#btn-back').show();
	} else {
		$('#primaryDetails').hide();
		$('#merch-details').show();
		$('#btn-submit').show();
		$('#btn-back').show();  
	} 
</script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
