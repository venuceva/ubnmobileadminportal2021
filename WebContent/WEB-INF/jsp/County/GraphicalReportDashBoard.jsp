 
<%@page
	import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 
<meta charset="utf-8">
<title>IMPERIAL</title>

<link href="<%=ctxstr%>/css/bootstrap-united.css"
	rel="stylesheet">

<link href="<%=ctxstr%>/css/bootstrap-responsive.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=ctxstr%>/css/jquery-ui-1.8.21.custom.css" />
 
<link href="<%=ctxstr%>/css/agency-app.css"
	rel="stylesheet" />
<link href="<%=ctxstr%>/css/core.css" rel="stylesheet" />


<link rel="shortcut icon" href="<%=ctxstr%>/images/<%=appName %>/favicon.ico" />
<script src="${pageContext.request.contextPath}/js/graph.js"> </script>
 <script src="<%=ctxstr%>/js/jquery.jqGauges.min.js" type="text/javascript"></script>
 <link rel="stylesheet" type="text/css" href="<%=ctxstr%>/css/jquery.jqGauges.css" />
 
  <link rel="stylesheet" type="text/css" href="<%=ctxstr%>/css/jquery.jqChart.css" />
    <link rel="stylesheet" type="text/css" href="<%=ctxstr%>/css/jquery.jqRangeSlider.css" />
    <script src="<%=ctxstr%>/js/jquery.mousewheel.js" type="text/javascript"></script>
    <script src="<%=ctxstr%>/js/jquery.jqChart.min.js" type="text/javascript"></script>
    <script src="<%=ctxstr%>/js/jquery.jqRangeSlider.min.js" type="text/javascript"></script>

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
	/*color:#ffffff;*/
	/* border-top: 1px solid #999;
    border-right: 1px solid #999;
    border-left: 1px solid #999;*/
	list-style: none;
}

#nav li a {
	/*display: block;*/
	/* padding: 10px 15px;*/
	/*background: #AC4A02;*/
	/* border-top: 1px solid #eee;
    border-bottom: 1px solid #999;*/
	text-decoration: none;
	/*color: #ffffff;*/
	list-style: none;
}

#nav li a:hover,#nav li a.active {
	/*background: #FC8023;*/
	/* color: #fff;*/
	
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
</style>
<script type="text/javascript">

//window.setTimeout( refreshGrid, 10000);

/*function refreshGrid() { 
    var formInput='';
	json = "";
 	$.getJSON('<%=ctxstr%>/<%=appName %>/graphicalTxnDashboardAjaxAct.action', 
				formInput,
		function(data) { 
			//graphone(data.responseJSON.DAILY_REPORT_DATA);
			var marketTime = data.responseJSON.MARKET_TIME;
			var parkingTime = data.responseJSON.PARKING_TIME;
			parking(parkingTime);
			market(marketTime);
			
	});
	window.setTimeout(refreshGrid, 10000);
}*/

$(document).ready(function () { 
	
	
	$( "#sortable" ).sortable();
	$( "#sortable" ).disableSelection();
 
	$( "#sortable1" ).sortable();
	$( "#sortable1" ).disableSelection(); 
	
	var marketTime = '${responseJSON.MARKET_TIME}';
	var parkingTime = '${responseJSON.PARKING_TIME}';
	parking(parkingTime);
	market(marketTime);
	graphone('${responseJSON.DAILY_REPORT_DATA}');
	
});

function graphone(grpData){
		//alert(grpData);
		var finalSubStr ="";
		var finalSuccessStr = "";
		var finalDeclinedStr = "";
		var finalReversalStr = "";
		var eachsubstr=new Array();
		var successSubStr = new Array();
		var declinedSubStr = new Array();
		var reversalSubStr = new Array();
		eachsubstr=grpData.split('@');
		var successStr=eachsubstr[0];
		var declinedStr=eachsubstr[1];
		var reversalStr=eachsubstr[2];
		//alert(successStr);
		successSubStr=successStr.split("#");
		declinedSubStr=declinedStr.split("#");
		reversalSubStr=reversalStr.split("#");
		//alert(successSubStr);
		for(var i=0;i<successSubStr.length;i++){
			var eachData=successSubStr[i];
			var oneData=eachData.split(",")[0];
			var twoData=eachData.split(",")[1];
			var subfinalStr="['"+oneData+"',"+twoData+"]";
			if(finalSuccessStr.length==0){
				finalSuccessStr=subfinalStr;
			}else{
				finalSuccessStr=finalSuccessStr+","+subfinalStr;
			}
			//alert(finalSuccessStr);
		}
		
		for(var i=0;i<declinedSubStr.length;i++){
			var eachData=declinedSubStr[i];
			var oneData=eachData.split(",")[0];
			var twoData=eachData.split(",")[1];
			var subfinalStr="['"+oneData+"',"+twoData+"]";
			if(finalDeclinedStr.length==0){
				finalDeclinedStr=subfinalStr;
			}else{
				finalDeclinedStr=finalDeclinedStr+","+subfinalStr;
			}
			//alert(finalDeclinedStr);
		}
		
		for(var i=0;i<reversalSubStr.length;i++){
			var eachData=reversalSubStr[i];
			var oneData=eachData.split(",")[0];
			var twoData=eachData.split(",")[1];
			var subfinalStr="['"+oneData+"',"+twoData+"]";
			if(finalReversalStr.length==0){
				finalReversalStr=subfinalStr;
			}else{
				finalReversalStr=finalReversalStr+","+subfinalStr;
			}
			//alert(finalReversalStr);
		}
		finalSuccessStr="["+finalSuccessStr+"]";
		finalReversalStr="["+finalReversalStr+"]";
		finalDeclinedStr="["+finalDeclinedStr+"]";

		var data1=[['A', 56], ['B', 30], ['C', 62],['D', 70], ['E', 40], ['F', 36], ['G', 60]];
	    $('#graph').jqChart({
                //title: { text: 'Spline Area Chart' },
                animation: { duration: 1 },
                series: [
                    {
						title: 'Success',
                        type: 'splineArea',
                        data: data1
                    }
                ]
        });
}


  
function parking(parkingTime){
	 $('#parking').jqSegmentedDisplay({
                background: '#DCDCDC',
                border: {
                    //padding: 10,
                    //lineWidth: 4,
                    strokeStyle: '#76786A'
                },
                digits: 20,
                segmentMode: 'sevenSegment',
                text: parkingTime,
                bevelRate: 0.6,
                textForeground: '#333333',
                textForegroundUnlit: 'rgba(201, 201, 201, 0.5)'
            });
} 
 
function market(marketTime){
	 $('#market').jqSegmentedDisplay({
                background: '#DCDCDC',
                border: {
                    //padding: 10,
                    //lineWidth: 4,
                    strokeStyle: '#76786A'
                },
                digits: 20,
                segmentMode: 'sevenSegment',
                text: marketTime,
                bevelRate: 0.6,
                textForeground: '#333333',
                textForegroundUnlit: 'rgba(201, 201, 201, 0.5)'
            });
} 

 	 
</script>
<body> 
<form method="post" name="form1" id="form1"> 
	<div id="content" class="span10"> 	
		<div>
			<ul class="breadcrumb">
				<li><a href="home.action">Home</a> <span class="divider">&gt;&gt;</span> </li>
				<li><a href="#">Transaction Information</a></li>
			</ul>
		</div> 
		<span class="container">
			<h6 class="flash-colour"><span id="groupannoumcement"></span></h6>
			<h6 class="flash-colour"><span id="userannoumcement"></span></h6> 
		</span> 
		<br/> 
		<div id="sortable1"></div>  
			<div class="row-fluid sortable">  
			<div class="box span12" ">	 
							<table width="950" border="0" cellpadding="5" cellspacing="1" class="table" >  
									<tr >
										<td><font size="4"><strong>Market Rates Service</strong></font></td>
										<td><font size="4"><strong>Parking Service</strong></font></td>
									</tr>
									<tr>
										<td> 
											<div id="market" style="width: 500px; height: 100px;">
											</div>
										</td>
										<td>
											<div id="parking" style="width: 500px; height: 100px;">
											</div>
										</td>
									</tr>
							</table>
		      </div>  
			  
		 <div class="row-fluid sortable">  
			<div class="box span12" ">
			 <div class="box-header well" data-original-title>Daily Transactions
				<div class="box-icon"> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
				</div>
			  </div>			 
			     <div class="box-content">
							<fieldset> 
							 <table width="950" border="0" cellpadding="5" cellspacing="1" 
										class="table table-striped table-bordered bootstrap-datatable" id="user-details">  
									<tr class="odd">
			 						<td><div id="graph"></div></td></tr> 
							</table>
					     </fieldset> 
					</div>
		      </div>  
		      
	  </div>
	   <div>
	   <input type="hidden" name="graph1" id="g1">
	   <input type="hidden" name="graph2" id="g2">
	   <input type="hidden" name="graph3" id="g3">
	   </div>
		
	</div>  
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script>
</body>
</html>
