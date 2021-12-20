 
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
 
 
	<link rel="stylesheet" type="text/css" href="<%=ctxstr%>/css/jquery.jqChart.css" />
    <link rel="stylesheet" type="text/css" href="<%=ctxstr%>/css/jquery.jqRangeSlider.css" />
    <link rel="stylesheet" type="text/css" href="<%=ctxstr%>/css/jquery-ui-1.10.4.css" />
	
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

window.setTimeout( refreshGrid, 25000);
var g1type = '';
var g2type = '';
var g3type = '';
function refreshGrid() { 
    var formInput='';
	json = "";
 	$.getJSON('<%=ctxstr%>/<%=appName %>/graphicalReportsAjaxAct.action', 
				formInput,
		function(data) { 
			graphone(data.responseJSON.DAILY_REPORT_DATA);
			graphtwo(data.responseJSON.TERMINAL_REPORT_DATA);
			piechart(data.responseJSON.PIE_CHART_DATA);
			g1type = data.responseJSON.g1type;
			g2type = data.responseJSON.g2type;
			g3type = data.responseJSON.g3type;
	});
	window.setTimeout(refreshGrid, 25000);
}
 function linear_ggg12(){
	 $('#g1type').val('linear');
 }
 function area_ggg12(){
	 $('#g1type').val('area');
 }
 function bar_ggg12(){
	 $('#g1type').val('bar');
 }
 function combo_ggg12(){
	 $('#g1type').val('combo');
 }
 function table_ggg12(){
	 $('#g1type').val('table');
 }
$(document).ready(function () { 
	$('#ggg12-graphify-button-linear').on('click', function(){
		alert('linear in graph 1');
	});
	
	$( "#sortable" ).sortable();
	$( "#sortable" ).disableSelection();
 
	$( "#sortable1" ).sortable();
	$( "#sortable1" ).disableSelection(); 
	g1type = '${responseJSON.g1type}';
	g2type = '${responseJSON.g2type}';
	g3type = '${responseJSON.g3type}';
	
	graphone('${responseJSON.DAILY_REPORT_DATA}');
	
	graphtwo('${responseJSON.TERMINAL_REPORT_DATA}');
	piechart('${responseJSON.PIE_CHART_DATA}');
	
});
  
function piechart(pieData){
	//var pieData ='${responseJSON.PIE_CHART_DATA}';
	//alert(pieData);
	var a=new Array();
	a =pieData.split(",");
	var d1=a[0];
	var d2=a[1];
	var d3=a[2];
	var data1=[['Canteen', d2], ['Gym', d1], ['Library', d3],['Book Center',d3],['Security Check',d3]];
	//alert(data1);
	var background = {
                type: 'linearGradient',
                x0: 0,
                y0: 0,
                x1: 0,
                y1: 1,
                colorStops: [{ offset: 0, color: '#d2e6c9' },
                             { offset: 1, color: 'white' }]
            };

            $('#graph3').jqChart({
                title: { text: '' },
                legend: { title: 'Services' },
                border: { strokeStyle: '#6ba851' },
                background: background,
                animation: { duration: 1 },
                shadows: {
                    enabled: true
                },
                series: [
                    {
                        type: 'table',
                        fillStyles: ['#418CF0', '#FCB441', '#E0400A', '#056492'],
                        labels: {
                            stringFormat: '%.1f%%',
                            valueType: 'percentage',
                            font: '20px sans-serif',
                            fillStyle: 'white'
                        },
                        explodedRadius: 10,
                        explodedSlices: [5],
                        data: data1
                    }
                ]
            });

            $('#graph3').bind('tooltipFormat', function (e, data) {
                var percentage = data.series.getPercentage(data.value);
                percentage = data.chart.stringFormat(percentage, '%.2f%%');

                return '<b>' + data.dataItem[0] + '</b><br />' +
                       data.value + ' (' + percentage + ')';
            });
} 
 
function graphone(grpData){

	$('#graph').empty();
	 //var grpData="05-11-2014,04-11-2014,03-11-2014,02-11-2014,01-11-2014,31-10-2014,30-10-2014@70,26,33,74,12,49,33#32,46,75,38,62,20,52#32, 46, 75, 38, 62, 20, 52";
	  
	  /* Date string start  18-08-2014,19-08-2014,20-08-2014,21-08-2014,22-08-2014,23-08-2014,24-08-2014 */
		var grpData ='${responseJSON.DAILY_REPORT_DATA}';
		//alert(grpData);
	    // var bar_dates=main_str1.split('@');
	   var bar_dates=grpData.split('@');
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
			start: 'table',
			obj: {
				id: 'ggg12',
				width: 800,
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

	$('#graph2').empty();
	var main_str2 ='${responseJSON.TERMINAL_REPORT_DATA}';
	//alert(main_str2);
	//var main_str2="SN000002,SN000003,SN000004,SN000005,SN000006,SN000007,SN000008@70,26,33,74,12,49,33#32,46,75,38,62,20,52#32, 46, 75, 38, 62, 20, 52";
	  
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
				width: 950,
				height: 375,
				xGrid: false,
				legend: true,
				x:date_split_arr,
				title: 'Success , Declined & Reversal ' ,
				points:main_arr,
				pointRadius: 3,
				colors: ['#662583', '#599682','#9E952B','#191630'],
				xDist: 90,
				dataNames: ['Success', 'Declined','Reversal','REV'],
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
<body> 
<form method="post" name="form1" id="form1"> 
	<div id="content" class="span10"> 	
		<div>
			<ul class="breadcrumb">
				<li><a href="graphicalReportsAct.action">Home</a> <span class="divider">&gt;&gt;</span> </li>
				<li><a href="#">Reports</a></li>
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
	   <div class="row-fluid sortable">  
			<div class="box span12" >
			 <div class="box-header well" data-original-title>Terminal Based Transactions
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
			 						<td><div id="graph2"></div></td></tr> 
									
							</table>
					     </fieldset> 
					</div>
		      </div>  		 
			    
	  </div>
		
	<div class="row-fluid sortable">  
			<div class="box span12" >
			 <div class="box-header well" data-original-title>Service Based Transactions
				<div class="box-icon"> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
					<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
				</div>
			  </div>	
			    <div class="box-content">
							<div id="graph3" style="width: 600px; height: 400px;">
							</div>
					</div>
		      </div>  		 
			    <input type="hidden" name="g1type" id="g1type">
			    <input type="hidden" name="g2type" id="g1type">
			    <input type="hidden" name="g3type" id="g1type">
	  </div>
	</div>  
</form>
<script type="text/javascript" src='<%=ctxstr%>/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='<%=ctxstr%>/js/datatable-add-scr.js'></script>
</body>
</html>
