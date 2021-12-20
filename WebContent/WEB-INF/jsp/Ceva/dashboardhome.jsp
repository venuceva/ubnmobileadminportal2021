 
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


<script src="<%=ctxstr%>/js/dashboard/d3.min.js"></script>
<script src="<%=ctxstr%>/js/dashboard/d3pie.js"></script>

   <link rel="stylesheet" href="<%=ctxstr%>/css/material-charts.css">
  <script type='text/javascript' src="<%=ctxstr%>/js/material-charts.js"></script>

<s:set value="responseJSON1" var="respData"/>

 <s:if test="#respData.size == 0" >
 	<s:set value="commonBean.responseJSON1" var="respData"/>
 </s:if>
 <script >
 
/*  function beginrefresh(){
	 $('#user-tbody').empty();
	  var formInput='';
		json = "";
		 $.getJSON('livedata.action', formInput,function(data) {
			
			//alert(data.responseJSON.Files_List);
			var json =data.responseJSON.Files_List; 
			buildtable(json,'user-tbody');
			

			
});	 
setTimeout("beginrefresh()",5000)
 } */
 
 
 $(function () { 
	/*  var merchantData ='${responseJSON.Files_List}';

	 alert("mmmm"+merchantData); */
		var formInput='';
			json = "";
			 $.getJSON('livedata.action', formInput,function(data) {
				
				//alert(data.responseJSON.Files_List);
				var json =data.responseJSON.Files_List;
				var json1 =data.responseJSON.Files_List1;
				
				buildtable(json,'user-tbody');
				$.each(json1, function(index,jsonObject){
					$('#logincust').text(jsonObject.LOGIN_USERS);
					$('#newcust').text(jsonObject.NEW_USER);
					$('#notification').text(jsonObject.NOTIFICATION);
					$('#totalorder').text(jsonObject.ORDER);
					$('#tottrans').text(jsonObject.TRANS_DETAILS);
					$('#totallocator').text(jsonObject.LOCATOR);
					
					//alert(jsonObject.LOGIN_USERS);
				});
				

				
	});	
			 
 });

	//window.setTimeout("beginrefresh()",5000);
 
 function buildtable(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+">";
			htmlString = htmlString + "<td id=sno name=sno >" + i + "</td>";
			htmlString = htmlString + "<td id=ACCOUNTNO name=ACCOUNTNO >" + jsonObject.ACCOUNTNO + "</td>";
			htmlString = htmlString + "<td id=CREDITAMOUNT name=CREDITAMOUNT >" + jsonObject.CREDITAMOUNT + "</td>";
			htmlString = htmlString + "<td id=CREDITCREDITINDICATOR name=CREDITCREDITINDICATOR >" + jsonObject.CREDITCREDITINDICATOR + "</td>";
			htmlString = htmlString + "<td id=CREDITPAYMENTREFERENCE name=CREDITPAYMENTREFERENCE >" + jsonObject.CREDITPAYMENTREFERENCE + "</td>";	
			htmlString = htmlString + "<td id=BATCHID name=BATCHID >" + jsonObject.BATCHID + "</td>";
			htmlString = htmlString + "<td id=CHANNEL name=CHANNEL >" + jsonObject.CHANNEL + "</td>";
			htmlString = htmlString + "<td id=TRANS_DATE name=TRANS_DATE >" + jsonObject.TRANS_DATE + "</td>";
			htmlString = htmlString + "<td id=RESPONSEMESSAGE name=RESPONSEMESSAGE >" + jsonObject.RESPONSEMESSAGE + "</td>";
			
			
			htmlString = htmlString + "</tr>";
	
	});
	
	
	$('#'+divid).append(htmlString);

}
 
 $(function() {
	 
	
	 

	 
	    $( "#dialog" ).dialog(
	    		{
	    		autoOpen: false,
 			modal: true,
 		    draggable: false,
 		    resizable: false,
 		    show: 'blind',
 		    hide: 'blind',
	    		width: 550, 
			    height: 650,
			    buttons: {
 		        "OK": function() {
 		            $(this).dialog("close");
 		        }
 		    }
	    		}
	    	);
	  });
	  
 function dashboardfun(val){
	 
	 $( "#pie" ).text("");
	 if(val=="mail"){
		 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/notification.action';
			$("#form1").submit();
			return true;
	 }
	 
	
	 if(val=="user"){
		 $( "#dialog" ).dialog( "option", "title", "Login Customers" ); 
		 var queryString9 = "method=dashboardpie&fname="+val;
			
			$.getJSON("postJson.action", queryString9,function(data) {
				var vvv=data.finalCount;
				 var vvv1=data.finalCount1;
				if(vvv!=0 || vvv1!=0){
					
				 
				 var pie = new d3pie("pie", {
						
						labels: {
							inner: {
								format: "value"
							},
							value: {
								color: "#ffffff"
							}
						},
						data: {
							content: [{ label: "MOBILE", value: vvv },{ label: "USSD", value: vvv1 }]
						}
					});
				}else{
					
					$( "#pie" ).text("no records available ");
				}
			});
	 }else if(val=="newuser"){
		 $( "#dialog" ).dialog( "option", "title", "New Customers" ); 
		 var queryString9 = "method=dashboardpie&fname="+val;
		 $.getJSON("postJson.action", queryString9,function(data) {
				var vvv=data.finalCount;
				 var vvv1=data.finalCount1;
				if(vvv!=0 || vvv1!=0){
				
				 
				 var pie = new d3pie("pie", {
						
						labels: {
							inner: {
								format: "value"
							},
							value: {
								color: "#ffffff"
							}
						},
						data: {
							content: [{ label: "MOBILE", value: vvv },{ label: "USSD", value: vvv1 }]
						}
					});
				}else{
					
					$( "#pie" ).text("no records available ");
				}
			});
	 }else if(val=="order"){
		 $( "#dialog" ).dialog( "option", "title", "Total Order" ); 
		 var queryString9 = "method=dashboardpie&fname="+val;
		 $.getJSON("postJson.action", queryString9,function(data) {
				var vvv=data.finalCount;
				 var vvv1=data.finalCount1;
				 var vvv2=data.finalCount2;
				 var vvv3=data.finalCount3;
				 var vvv4=data.finalCount4;
				 if(vvv!=0 || vvv1!=0 || vvv2!=0 || vvv3!=0 || vvv4!=0){
				
				 
				 var pie = new d3pie("pie", {
						
						labels: {
							inner: {
								format: "value"
							},
							value: {
								color: "#ffffff"
							}
						},
						data: {
							content: [{ label: "Phones and Accessories", value: vvv },
							          { label: "Electronics and Appliances", value: vvv1 },
							          { label: "Vehicles", value: vvv2 },
							          { label: "Agriculture and Food", value: vvv3 },
							          { label: "Home  and Furniture", value: vvv4 }]
						}
					});
				}else{
					
					$( "#pie" ).text("no records available ");
				}
			});
	 }else if(val=="trans"){
		 $( "#dialog" ).dialog( "option", "title", "Total Transactions" ); 
		 var queryString9 = "method=dashboardpie&fname="+val;
		 $.getJSON("postJson.action", queryString9,function(data) {
			 var vvv=data.finalCount;
			 var vvv1=data.finalCount1;
			 var vvv2=data.finalCount2;
			 var vvv3=data.finalCount3;
			 var vvv4=data.finalCount4;
			 var vvv5=data.finalCount5;
			 var vvv6=data.finalCount6;
			 var vvv7=data.finalCount7;
				if(vvv!=0 || vvv1!=0 || vvv2!=0 || vvv3!=0 || vvv4!=0 || vvv5!=0 || vvv6!=0 || vvv7!=0){
				 
				 var pie = new d3pie("pie", {
						
						labels: {
							inner: {
								format: "value"
							},
							value: {
								color: "#ffffff"
							}
						},
						data: {
							content: [{ label: "Other Banks", value: vvv },
							          { label: "Other Banks reversal", value: vvv1 },
							          { label: "Pay Bill Reversal", value: vvv2 },
							          { label: "Recharge", value: vvv3 },
							          { label: "Pay Bills", value: vvv4 },
							          { label: "Fund Transfer", value: vvv5 },
							          { label: "Reveral", value: vvv6 },
							          { label: "Recharge Reveral", value: vvv7 }]
						}
					});
				}else{
					
					$( "#pie" ).text("no records available ");
				}
			});
	 }else if(val=="locator"){
		 $( "#dialog" ).dialog( "option", "title", "Locator Requested" ); 
		 var queryString9 = "method=dashboardpie&fname="+val;
		 $.getJSON("postJson.action", queryString9,function(data) {
				var vvv=data.finalCount;
				 var vvv1=data.finalCount1;
				 var vvv2=data.finalCount2;
				 var vvv3=data.finalCount3;
				 var vvv4=data.finalCount4;
				 if(vvv!=0 || vvv1!=0 || vvv2!=0 || vvv3!=0 || vvv4!=0){
				
				 
				 var pie = new d3pie("pie", {
						
						labels: {
							inner: {
								format: "value"
							},
							value: {
								color: "#ffffff"
							}
						},
						data: {
							content: [{ label: "Customer Request", value: vvv },
							          { label: "Accept Customer Request", value: vvv1 },
							          { label: "Customer Request Cancel", value: vvv2 },
							          { label: "Agent Cancel Customer Request", value: vvv3 }]
							          
						}
					});
				}else{
					
					$( "#pie" ).text("no records available ");
				}
			});
	 }
	
	
	/*  var pie = new d3pie("pie", {
			
			labels: {
				inner: {
					format: "value"
				},
				value: {
					color: "#ffffff"
				}
			},
			data: {
				content: [
					{ label: "Mobile", value: 1442 },
					{ label: "USSD", value: 2334 }
				]
			}
		}); */
	 
	 $("#dialog").dialog("open");
 }
 
 
 
 
 
 </script>
 
 <style>
div.gallery {
    margin: 5px;
    /* width: 150px;
    height: 80px;
    float: center; */
     font-size: 10px;
    font-weight: normal;
    /* color:#3273B2; */
    /*  border-radius: 50%; 
     border: 1px solid #ccc;  */
}



div.gallery img {
    width: 30px;
    height: 30px;
   
}

div.desc {
    padding: 15px;
    text-align: center;
    
}
</style>

<body> 
<form method="post" name="form1" id="form1"> 
	<div id="content" class="span10" >

			<div id="dialog" name="dialog" title="View" width="100" style="display:none">
  						<strong><div  id="result"><div id="pie"></div>
  						</div></strong>
					</div>	
	
		
		
		<%--  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
		      <tr>
		        <td width="100%" colspan="2" align="center" ><img src="${pageContext.request.contextPath}/images/BANK_logo.png"  style='filter:alpha(opacity=50);-moz-opacity:0.3;-khtml-opacity: 0.3;opacity: 0.3;width: 150px;height: 60px;' ></td>
		        </tr>
		 </table> --%>
		 
		  <div class='row-fluid sortable'> 
			<div class='box span12'> 
			<div class='box-header well' data-original-title>
			<i class='icon-edit'></i>Today Details Dashboard
			
			</div> 
			<div class='box-content'>
		  		<fieldset> 
		 <table width="100%"  border="0" cellpadding="5" cellspacing="1" align="center" >
		
						<tr><td width="15%">
				<div class="gallery"  id="user" onclick="dashboardfun(this.id)" style="cursor:pointer"  >
						  <div align="center"><strong>Login Customers</strong></div>
						   <div align="center"><img src="${pageContext.request.contextPath}/images/lifestyle/user.png" alt="Login Customers" width="14px" height="14px"></div> 
						   <div align="center"><strong><span id="logincust"></span></strong></div>
						  
				</div>
				</td><td width="15%">
				<div class="gallery"  id="newuser" onclick="dashboardfun(this.id)" style="cursor:pointer" >
						  <div align="center"><strong>New Customers</strong></div>
						   <div align="center"><img src="${pageContext.request.contextPath}/images/lifestyle/add.png" alt="New Customers" width="14px" height="14px"></div> 
						   <div align="center"><strong><span id="newcust"></span></strong></div>
						  
				</div>
		</td><td width="15%">
				<div class="gallery"  id="mail" onclick="dashboardfun(this.id)" style="cursor:pointer" >
						  <div align="center"><strong>Notification</strong></div>
						   <div align="center"><img src="${pageContext.request.contextPath}/images/lifestyle/post.png" alt="Notification" width="14px" height="14px"></div> 
						   <div align="center"><strong><span id="notification"></span></strong></div>
						  
				</div>
		</td><td width="15%">
				<div class="gallery" id="order" onclick="dashboardfun(this.id)" style="cursor:pointer" >
						  <div align="center"><strong>Total Order</strong></div>
						   <div align="center"><img src="${pageContext.request.contextPath}/images/lifestyle/online_order.png" alt="Total Order" width="14px" height="14px"></div> 
						   <div align="center"><strong><span id="totalorder"></span></strong></div>
						  
				</div>
		</td><td width="15%">
				<div class="gallery"  id="trans" onclick="dashboardfun(this.id)" style="cursor:pointer" >
						  <div align="center"><strong>Total Transactions</strong></div>
						   <div align="center"><img src="${pageContext.request.contextPath}/images/lifestyle/money.png" alt="Total Transaction" width="14px" height="14px"></div> 
						   <div align="center"><strong><span id="tottrans"></span></strong></div>
						  
				</div>
		</td><td width="15%">
				<div class="gallery"  id="locator" onclick="dashboardfun(this.id)" style="cursor:pointer" >
						  <div align="center"><strong>Locator Requested</strong></div>
						   <div align="center"><img src="${pageContext.request.contextPath}/images/lifestyle/Locator.png" alt="Total Transaction" width="14px" height="14px"></div> 
						   <div align="center"><strong><span id="totallocator"></span></strong></div>
						  
				</div>
		</td>
		
		</tr>
		</table>
		</fieldset> 
		 </div>
			</div>
			</div>
		 <div class='row-fluid sortable'> 
			<div class='box span12'> 
			<div class='box-header well' data-original-title>
			<i class='icon-edit'></i>Dashboard >>>Live Transaction Data Feed
			<div class='box-icon'>
			<a href='#' class='btn btn-setting btn-round'><i class='icon-cog'></i></a>
			<a href='#' class='btn btn-minimize btn-round'><i class='icon-chevron-up'></i></a>
			</div>
			</div> 
			<div class='box-content'>
			<fieldset> 			
	
				<table  style = 'border: 1px solid #d7d7d7; font-family: Arial, Helvetica, sans-serif;font-size: 10px; color: #000000; font-weight: normal;' width='100%'
					class="table table-striped table-bordered bootstrap-datatable" 
						id="DataTables_Table_0">
						<thead>
							<tr>
								<th width="5%">S.No</th>
								<th width="10%">Account Number </th>
								<th width="10%">Amount</th>
								<th width="10%">Cr/Dr</th>
<!-- 								<th>Service Name</th>
 -->								<th width="15%">Payment Reference Number</th>
								<th width="15%">Transaction Type</th>
								<th width="10%">Channel</th>								
<!-- 								<th>Applicaton Type</th>
 -->								<th width="15%">Date and Time</th>
								<th width="10%">Response Message</th>
								
							</tr>
						</thead> 
						<tbody id="user-tbody"> 
						</tbody>
					</table> 
		  
		<!-- 
					 <div class="example-chart" >
                        <div id="bar-chart-example" ></div>
                       
                    </div>  -->
           
   
                    
              </fieldset> 
			</div>
			</div>
			</div>

	  </div>
	
	  
	  
	  
	  
</form> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
 
</body>
</html>
