
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<% 


   String merchantid= "SAMG00000000001";

   
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Create Entity</title>
 
<script src="${pageContext.request.contextPath}/js/graph.js"> </script>
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
<script  type="text/javascript"> 

var reportrules= {
		rules : {
			reportType : { required : true }
		},
		messages : {
			reportType : { required : "Please Select Report Type." }
		}
	};

var mydata;
var json;

$(function () {
	
	
	var list = "reportDate".split(",");
	var datepickerOptions = {
				showTime: false,
				showHour: false,
				showMinute: false,
			dateFormat:'dd-mm-yy',
			alwaysSetTime: false,
			changeMonth: true,
			changeYear: true
	};	
	var requiredYears=100;
	var date = new Date();
	var yyyy = date.getFullYear();
      for (var i = yyyy; i > (yyyy-requiredYears); i--) {
		var options = '';
        options = '<option value="' + i + '">' + i + '</option>';
		$("#yearName").append(options);
      }
     


	//code for hide different sections in page load
	$('#dailyRptSection').hide();
	$('#YearBasedTbl').hide();
	$('#reportselection').hide();
	$('#insReportTbl').hide();
	$("#storeselection").hide();
	
	//activities happen based on Type of Report Change
	$('#reportType').live('change', function () {
    	var reportType=$('#reportType').val();
		
		if(reportType=="D"){
			$('#dailyRptSection').show();
			$('#reportselection').show();
			$('#YearBasedTbl').hide();
			$("#reportDate").val("");
			$("#monthName").val("");
			$("#weekName").val("");
			$("#quarterName").val("");
			$("#yearName").val("");
			$("#storeselection").val("");
	}else if(reportType=="Q"){
			$('#YearBasedTbl').show();
			$('#yearTr').show();
			$('#QuarterlyRptSection').show();
			$('#reportselection').show();
			$('#monthlyRptSection').hide();
			$('#weeklyRptSection').hide();
			$('#dailyRptSection').hide();
			$("#reportDate").val("");
			$("#monthName").val("");
			$("#weekName").val("");
			$("#quarterName").val("");
			$("#yearName").val("");
			$("#storeselection").val("");
		}else if(reportType=="M"){
			$('#YearBasedTbl').show();
			$('#yearTr').show();
			$('#monthlyRptSection').show();
			$('#reportselection').show();
			$('#weeklyRptSection').hide();
			$('#dailyRptSection').hide();
			$('#QuarterlyRptSection').hide();
			$("#reportDate").val("");
			$("#monthName").val("");
			$("#weekName").val("");
			$("#quarterName").val("");
			$("#yearName").val("");
			$("#storeselection").val("");
		}else if(reportType=="W"){
			$('#YearBasedTbl').show();
			$('#yearTr').show();
			$('#monthlyRptSection').show();
			$('#weeklyRptSection').show();
			$('#reportselection').show();
			$('#dailyRptSection').hide();
			$('#QuarterlyRptSection').hide();
			$("#reportDate").val("");
			$("#monthName").val("");
			$("#weekName").val("");
			$("#quarterName").val("");
			$("#yearName").val("");
			$("#storeselection").val("");
			
		}else if(reportType=="I"){
			$('#reportselection').show();
			$('#YearBasedTbl').hide();
			$('#dailyRptSection').hide();
			$("#reportDate").val("");
			$("#monthName").val("");
			$("#weekName").val("");
			$("#quarterName").val("");
			$("#yearName").val("");
			$("#storeselection").val("");
		}else{
			$('#reportselection').hide();
			$('#dailyRptSection').hide();
			$('#YearBasedTbl').hide();
			$("#reportDate").val("");
			$("#monthName").val("");
			$("#weekName").val("");
			$("#quarterName").val("");
			$("#yearName").val("");
			$("#storeselection").val("");
		}
  
	

	
	$('#monthName').live('change', function () {
		var reportType=$('#reportType').val();
		if(reportType=="W"){
			getWeeks();
		}
	});
	
    $('#generateReport').live('click', function () {
    		$("#form1").validate(reportrules);
			var reportType=$('#reportType').val();
    		if($("#form1").valid()){
	    	    $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateReportAct.action';
				$("#form1").submit();
			return true;
    	}else{
			$('#generateReport').removeAttr('disabled');
			return false;
    	} 

    });


    $('#cancel').live('click', function () { 
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
		$("#form1").submit();
		return true;

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
	
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	}); 
	
	
});

function getWeeks()
{
	document.form1.weekName.options.length=1;
	var days = new Array(7);
	days[0] = "Sunday";
	days[1] = "Monday";
	days[2] = "Tuesday"; 
	days[3] = "Wednesday";
	days[4] = "Thursday";
	days[5] = "Friday";
	days[6] = "Saturday";

	var months = new Array(12);
	months[0] = "January";
	months[1] = "February";
	months[2] = "March";
	months[3] = "April";
	months[4] = "May";
	months[5] = "June";
	months[6] = "July";
	months[7] = "August";
	months[8] = "September";
	months[9] = "October"; 
	months[10] = "November";
	months[11] = "December";

	var v_month=document.getElementById('monthName').value;
	var v_year=document.getElementById('yearName').value;
	thisMonth=new Date(v_year,v_month-1,1);
	month=months[thisMonth.getMonth()];
	var cnt=0;
	if(v_year%4!=0)
	{
		if(month=="February")
		{
			for(i=1;i<=28;i++)
			{
				today = new Date(v_year,v_month-1,i);
				day = days[today.getDay()]
				if(day =='Monday') 
				{
					cnt++;
					var itemText=cnt+" week";
					var options = "";
					var options = $('<option/>', {value:i, text: itemText}).attr('data-id',i); 
					$('#weekName').append(options);
				}
			}
		}
		else
		{
		if(month=="April"||month=="June"||month=="September"||month=="November")
			{
				
			for(i=1;i<=30;i++)
			{
				today = new Date(v_year,v_month-1,i);
				day = days[today.getDay()]
				if(day =='Monday') 
				{
					cnt++;
					var itemText=cnt+" week";
					var options = "";
					var options = $('<option/>', {value:i, text: itemText}).attr('data-id',i); 
					$('#weekName').append(options);
				}
			}
			}
			else
			{
			for(i=1;i<=31;i++)
			{
				today = new Date(v_year,v_month-1,i);
				day = days[today.getDay()];
				if(day =='Monday') 
				{
					cnt++;
					var itemText=cnt+" week";
					var options = "";
					var options = $('<option/>', {value:i, text: itemText}).attr('data-id',i); 
					$('#weekName').append(options);
				}
			}
			}
		}
	}
	else 
	{
		if(month=='February')
		{
			for(i=1;i<=29;i++)
			{
				today = new Date(v_year,v_month-1,i);
				day = days[today.getDay()]
				if(day =='Monday') 
				{
					cnt++;
					var itemText=cnt+" week";
					var options = "";
					var options = $('<option/>', {value:i, text: itemText}).attr('data-id',i); 
					$('#weekName').append(options);
				}
			}
		}
		else
		{
			if(month=="April"||month=="June"||month=="September"||month=="November")
			{
			for(i=1;i<=30;i++)
			{
				today = new Date(v_year,v_month-1,i);
				day = days[today.getDay()]
				if(day =='Monday') 
				{
					cnt++;
					var itemText=cnt+" week";
					var options = "";
					var options = $('<option/>', {value:i, text: itemText}).attr('data-id',i); 
					$('#weekName').append(options);
				}
			}
			}
			else
			{
			for(i=1;i<=31;i++)
			{
				today = new Date(v_year,v_month-1,i);
				day = days[today.getDay()]
				if(day =='Monday') 
				{
					cnt++;
					var itemText=cnt+" week";
					var options = "";
					var options = $('<option/>', {value:i, text: itemText}).attr('data-id',i); 
					$('#weekName').append(options);
				}
			}
			}
		}
	}
}


 $('#reportName').live('change', function () {
	
	var reportName=$('#reportName').val();
	var reportType=$('#reportType').val();
	var reportDate=$('#reportDate').val();
	var yearName=$('#yearName').val();
	var quarterName=$('#quarterName').val();
	var monthName=$('#monthName').val();
	var weekName=$('#weekName').val();
	var merchantid=$('#merchantid').val();
	
	
	$("#graph").text('');
	$("#graph2").text('');
	$("#graph3").text('');
	
	
	
	var bardata_split='';
	if(reportName!="") {
		
		if (reportName=='3' || reportName=='4'  ){
				
				
				var formInput='reportType='+reportType+'&reportName='+reportName+'&merchantid='+merchantid+'&method=fillStores';
				 $.getJSON('FillStoresAct.action', formInput,function(data) {
				
						var json = data.responseJSON.STORE_LIST;
						
						$('#storeid').find('option:not(:first)').remove();
						$('#storeid').trigger("liszt:updated");
						$.each(json, function(i, v) {
							var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);  
							$('#storeid').append(options);
							$('#storeid').trigger("liszt:updated");
						});
					});
				 
				 $('#storeselection').show(); 
		}else{
			
			//var formInput='REPORT_TYPE='+reportType+'&reportName='+reportName+'&merchantid='+merchantid+'&method=merchantbar';
			var formInput='reportType='+reportType+'&reportName='+reportName+'&reportDate='+reportDate+'&yearName='+yearName+'&quarterName='+quarterName+'&weekName='+weekName+'&merchantid='+merchantid+'&monthName='+monthName+'&method=merchantbar';

			
			
			 $.getJSON('MerchantBarAct.action', formInput,function(data) {
				 var status=data.responseJSON.MESSAGE;
				           
							if(status=="SUCCESS") {
									    var json1 = data.responseJSON.GRP_DATA;
									    var json2 = data.responseJSON.GRP_DATA;
									    var json3 = data.responseJSON.GRP_DATA;
										
										graphone(json1);
										graphtwo(json2);
										pie(json3);
							}
										
					
					
		     });
			
			
		}
	}

	
  

 });


 $('#storeid').live('change', function () {
	
	var reportName=$('#reportName').val();
	var reportType=$('#reportType').val();
	var reportDate=$('#reportDate').val();
	var yearName=$('#yearName').val();
	var quarterName=$('#quarterName').val();
	var monthName=$('#monthName').val();
	var weekName=$('#weekName').val();
	var merchantid=$('#merchantid').val();
	var storeid=$('#storeid').val();
	
	
	 
	$("#graph").text('');
	$("#graph2").text('');
	$("#graph3").text('');
	
	var bardata_split='';
	
		
		if (reportName=='3' || reportName=='4'  ){
				
			var formInput='reportType='+reportType+'&reportName='+reportName+'&reportDate='+reportDate+'&yearName='+yearName+'&quarterName='+quarterName+'&weekName='+weekName+'&merchantid='+merchantid+'&monthName='+monthName+'&method=merchantbar';

			 $.getJSON('MerchantBarAct.action', formInput,function(data) {
				 var status=data.responseJSON.MESSAGE;
				           
							if(status=="SUCCESS") {
								var json1 = data.responseJSON.GRP_DATA;
								var json2 = data.responseJSON.GRP_DATA;
								var json3 = data.responseJSON.GRP_DATA;
								
								graphone(json1);
								graphtwo(json2);
								pie(json3);
							}
										
					
					
		     });
			
			
		}
	

	
  

  });
	
});

function graphone(main_str1){

	
	// var main_str="18-08-2014,19-08-2014,20-08-2014,21-08-2014,22-08-2014,23-08-2014,24-08-2014@70,26,33,74,12,49,33#32,46,75,38,62,20,52#32, 46, 75, 38, 62, 20, 52";
	  
	  /* Date string start  18-08-2014,19-08-2014,20-08-2014,21-08-2014,22-08-2014,23-08-2014,24-08-2014 */
	  
	     var bar_dates=main_str1.split('@');
	  
	     var date_split_arr=bar_dates[0].split(',');
	     
	   
	     
	  
	    
	     
	   /* Date Graph Data by date wise "70,26,33,74,12,49,33#32,46,75,38,62,20,52#32, 46, 75, 38, 62, 20, 52"  */
	     var grp_data_dates=bar_dates[1].split('#');
	           
	        var main_arr=new Array();
	       
	          for (var i=0;i<grp_data_dates.length;i++){
	        	
	        	  main_arr[i]=new Array();
	        	      var eachbar=grp_data_dates[i].split(",");
	        	      for (var j=0;j<eachbar.length;j++){
	        	    	
	        	    	
	        	    	  main_arr[i][j]= parseInt(eachbar[j]);
	        	      }
	        	    
	          }
	      
		
	  
		$('#graph').graphify({
			//options: true,
			start: 'combo',
			obj: {
				id: 'ggg12',
				width: 1100,
				height: 375,
				xGrid: false,
				legend: true,
				x: date_split_arr,
				title: 'Success , Declined & Reversal ' ,
				points: main_arr,
				pointRadius: 3,
				colors: ['blue', 'red','orange'],
				xDist: 90,
				dataNames: ['Success', 'Declined','Reversal'],
				xName: 'Day',
				tooltipWidth: 15,
				animations: true,
				pointAnimation: true,
				averagePointRadius: 10,
				design: {
					tooltipColor: '#fff',
					gridColor: 'grey',
					tooltipBoxColor: 'green',
					averageLineColor: 'green',
					pointColor: 'green',
					lineStrokeColor: 'grey',
				}
			}
		});
		
}
function graphtwo(main_str2){

	
	// var main_str="18-08-2014,19-08-2014,20-08-2014,21-08-2014,22-08-2014,23-08-2014,24-08-2014@70,26,33,74,12,49,33#32,46,75,38,62,20,52#32, 46, 75, 38, 62, 20, 52";
	  
	  /* Date string start  18-08-2014,19-08-2014,20-08-2014,21-08-2014,22-08-2014,23-08-2014,24-08-2014 */
	  
	     var bar_dates=main_str2.split('@');
	  
	     var date_split_arr=bar_dates[0].split(',');
	     
	   
	     
	  
	    
	     
	   /* Date Graph Data by date wise "70,26,33,74,12,49,33#32,46,75,38,62,20,52#32, 46, 75, 38, 62, 20, 52"  */
	     var grp_data_dates=bar_dates[1].split('#');
	     var main_arr=new Array();
	       
	          for (var i=0;i<grp_data_dates.length;i++){
	        	  main_arr[i]=new Array();
	        	      var eachbar=grp_data_dates[i].split(",");
	        	      for (var j=0;j<eachbar.length;j++){
	        	    	 main_arr[i][j]= parseInt(eachbar[j]);
	        	      }
	        	    
	          }
	      
		
	  
		
		$('#graph2').graphify({
			//options: true,
			start: 'combo',
			obj: {
				id: 'ggg22',
				width: 1100,
				height: 375,
				xGrid: false,
				legend: true,
				x:date_split_arr,
				title: 'Cash Withdrawal, Deposit ,Balance Enq & Reversal ' ,
				points:main_arr,
				pointRadius: 3,
				colors: ['#662583', '#599682','#9E952B','#191630'],
				xDist: 90,
				dataNames: ['CWD','DEP','BAL','REV'],
				xName: 'Day',
				tooltipWidth: 15,
				animations: true,
				pointAnimation: true,
				averagePointRadius: 10,
				design: {
					tooltipColor: '#fff',
					gridColor: 'grey',
					tooltipBoxColor: 'green',
					averageLineColor: 'green',
					pointColor: 'green',
					lineStrokeColor: 'grey',
				}
			}
		});
		
}

function pie(main_str3){

	var main_str3="18-08,19-08,20-08,21-08,22-08,23-08,24-08@70,26,33,70,26,33";
	//var main_str="18-08,19-08@70,25";
	  
	  /* Date string start  18-08-2014,19-08-2014,20-08-2014,21-08-2014,22-08-2014,23-08-2014,24-08-2014 */
	  
	    var bar_dates=main_str3.split('@');
	  
	     var date_split_arr=bar_dates[0].split(',');
	     
	   
	     
	  
	    
	     
	   /* Date Graph Data by date wise "70,26,33,74,12,49,33#32,46,75,38,62,20,52#32, 46, 75, 38, 62, 20, 52"  */
	     var grp_data_dates=bar_dates[1].split(',');
	
	     var main_arr=new Array();
	       
	          for (var i=0;i<grp_data_dates.length;i++){
	        	  main_arr[i]=parseInt(grp_data_dates[i]); 
	          } 
	      
	$('#graph3').graphify({
		start: 'donut',
		obj: {
			id: 'lol',
			legend: false,
			showPoints: true,
		
			width: 775,
			legendX: 450,
			title: 'Day Wise Commission' ,
			pieSize: 200,
			shadow: true,
			height: 400,
			animations: true,
			//x: [2000, 2001],
			//points: [17, 33],
			
			x: date_split_arr,
			points: main_arr,
			xDist: 90,
			scale: 12,
			yDist: 35,
			grid: false,
			xName: 'Year',
			dataNames: ['Amount'],
			design: {
				lineColor: 'red',
				tooltipFontSize: '20px',
				pointColor: 'red',
				barColor: 'blue',
				areaColor: 'orange'
			}
		}
	}); 
}
</script>
 
</head>
<body >
<form name="form1" id="form1" method="post"> 
	 
		<div id="content" class="span10"> 
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					   <li> <a href="home.action">Statistics</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li><a href="#">Generation</a></li>

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
								<i class="icon-edit"></i>Reports
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> 
							</div>
						</div> 
						
					<div class="box-content">
						<fieldset>  
							<table width="950"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" >
								<tr class="even">
									<td width="20%"><label for="Type of Report"><strong>Type of Report<font color="red">*</font></strong></label></td>
									<td width="30%">
										<select id="reportType" name="reportType"  required='true' data-placeholder="Choose Report Type..." 
												 style="width: 220px;" >
											<option value="">Select</option>
											<option value="D">Daily</option>
											<option value="W">Weekly</option>
											<option value="M">Monthly</option>
											<option value="Q">Quarterly</option>
											
										</select>
									</td>
								</tr>
							</table>
							<table width="950" id="dailyRptSection"  border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" >
								<tr class="even" >
									<td width="20%"><label for="Type of Report"><strong>Select Date<font color="red">*</font></strong></label></td>
									<td width="30%">
										<input type="text" name="reportDate" id="reportDate" value=""/>
									</td>
								</tr>
							</table>
							
							<table width="950"   border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="YearBasedTbl" >
								<tr class="even" id="yearTr">
									<td width="20%"><label for="Select Year"><strong>Select Year<font color="red">*</font></strong></label></td>
									<td width="30%">
										<select id="yearName" name="yearName"  required='true' data-placeholder="Choose Year..." 
												 style="width: 220px;" >
											<option value="">Select</option>
										</select>
									</td>
								</tr>
								<tr class="even" id="QuarterlyRptSection">
									<td width="20%"><label for="Select Quarterly"><strong>Select Quarterly<font color="red">*</font></strong></label></td>
									<td width="30%">
										<select id="quarterName" name="quarterName"  required='true' data-placeholder="Choose Quarterly..." 
												 style="width: 220px;" >
											<option value="">Select</option>
											<option value="01">1st Quarter(Jan-Mar)</option>
											<option value="02">2nd Quarter(Apr-Jun)</option>
											<option value="03">3rd Quarter(Jul-Sep)</option>
											<option value="04">4th Quarter(Oct-Dec)</option>
										</select>
									</td>
								</tr>
								<tr class="even" id="monthlyRptSection" >
									<td width="20%"><label for="Select Month"><strong>Select Month<font color="red">*</font></strong></label></td>
									<td width="30%">
										<select id="monthName" name="monthName"  required='true' data-placeholder="Choose Month..." 
												 style="width: 220px;" >
											<option value="">Select</option>
											<option value="01">January</option>
											<option value="02">February</option>
											<option value="03">March</option>
											<option value="04">April</option>
											<option value="05">May</option>
											<option value="06">June</option>
											<option value="07">July</option>
											<option value="08">August</option>
											<option value="09">September</option>
											<option value="10">October</option>
											<option value="11">November</option>
											<option value="12">December</option>
										</select>
									</td>
								</tr>
								<tr class="even" id="weeklyRptSection" >
									<td width="20%"><label for="Select Week"><strong>Select Week<font color="red">*</font></strong></label></td>
									<td width="30%">
										<select id="weekName" name="weekName"  required='true' data-placeholder="Choose Week..." 
												 style="width: 220px;" >
											<option value="">Select</option>
										</select>
									</td>
								</tr>
							</table> 
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="reportselection" >
								<tr class="even" >
									<td width="20%"><label for="Select Year"><strong>Select Report<font color="red">*</font></strong></label></td>
									<td width="30%">
										<select id="reportName" name="reportName"  required='true' data-placeholder="Choose Report..." 
												 style="width: 220px;" >
												<option value="">Select</option>
										    <option value="1">1-Merchant Transaction Count</option>
										    <option value="2">2-Merchant Transaction Amount</option>
											<option value="3">3-Store Transaction Count</option>
											<option value="4">4-StoreTransaction Amount</option>
										</select>
									</td>
								</tr>
							</table>
							<table width="950"   border="0" cellpadding="5" cellspacing="1" 
								class="table table-striped table-bordered bootstrap-datatable" id="storeselection" >
								<tr class="even" >
									<td width="20%"><label for="Select Store"><strong>Select Store<font color="red">*</font></strong></label></td>
									<td width="30%">
										<select id="storeid" name="storeid"  required='true' data-placeholder="Choose Store..." 
												 style="width: 220px;" >
												<option value="">Select</option>
										</select>
									</td>
								</tr>
							</table>
							<table width="950"   border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable" id="insReportTbl" >
							</table>
						</fieldset> 
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
	   <div class="box-content">
			<fieldset> 
				 <table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable" id="user-details">  
						<tr class="odd">
 						<td><div id="graph2"></div></td> 
						</tr> 
				</table>
		     </fieldset> 
		</div>
		<div class="box-content">
			<fieldset> 
				 <table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable" id="user-details">  
						<tr class="odd">
							<td><div id="graph3"></div></td> 
						</tr> 
				</table>
		     </fieldset> 
		</div> 
		</div>
	</div>
	  <div class="form-actions">
		<input type="button" class="btn btn-success" type="text"  name="generateReport" id="generateReport" value="Generate Report" width="100" ></input>
		<input type="button" class="btn btn-danger" type="text"  name="cancel" id="cancel" value="Cancel" width="100" ></input>
	  </div>
</div>
			
<input type="hidden" name="merchantid" value=<%=merchantid%> id="merchantid" ></input> 
	 
</form>
</body> 
</html>