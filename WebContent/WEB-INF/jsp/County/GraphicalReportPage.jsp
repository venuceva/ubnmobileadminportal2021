 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Create Entity</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/graphjs/canvasjs.min.js"></script>
<script type="text/javascript">

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


$(document).ready(function() 
{
	console.log('${responseJSON}');
	console.log("DATE : "+new Date(2012, 01, 1));
	var json = $.parseJSON('${responseJSON}');
	var piearr=[];
	var obj={};
	var i;
	
	for(i=0;i<json.paymentModes.length;i++)
	{
		obj={};
		obj['y']=json.paymentModes[i].count;
		//obj['x']=json.paymentModes[i].totcount;
		obj['legendText']=json.paymentModes[i].paymentType;
		obj['label']=json.paymentModes[i].paymentType;	
		
		piearr.push(obj);
	}
	
	var colArr=[];
	obj={};
	for(i=0;i<json.weekcollections.length;i++)
	{
		obj={};
		obj['y']=parseFloat(json.weekcollections[i].amount);
		//obj['legendText']=json.paymentModes[i].paymentType;
		obj['label']=json.weekcollections[i].date;
		
		colArr.push(obj);
	}
	
	console.log(colArr);
	
	var dotArr=[];
	obj={};
	var j;
	var dt = new Date().getFullYear();
	//alert(new Date(parseFloat(dt, j, 1 )));
	for(j=1;j<=12;j++)
	{
		obj={};
		var avialablity = isavialable(json,j);
		if(avialablity > 0)
		{
			console.log(" V : "+j+"  "+avialablity);
			obj['x']=new Date(dt, j-1, 1 );
			obj['y']=parseInt(avialablity);
		}
		else
		{
			obj['x']=new Date(dt, j-1, 1 );
			obj['y']=parseFloat(0);
		}
		
		dotArr.push(obj);
	}
	
	
	console.log(dotArr);
	
	
	setTimeout(function(){
		
		var colchart=new CanvasJS.Chart("colchart",
			    {
			  
			  width:500,
		      title:{
		        text: "Transactions for the Year"    
		      },
		      animationEnabled: true,
		      axisY: {
		        title: "Transactions"
		      },
		      legend: {
		        verticalAlign: "bottom",
		        horizontalAlign: "center"
		      },
		      theme: "theme2",
		      data: [
		      {        
		        type: "column",  
		        showInLegend: true, 
		        legendMarkerColor: "grey",
		        legendText: "",
		        dataPoints: colArr
		      }   
		      ]
		 });
		
		colchart.render();

		},3000);
	
	setTimeout(function(){
		var piechart = new CanvasJS.Chart("piechart",
				{
					//exploded: true,
					width:500,
					title:{
						text: "Transactions Preference Today"
					},
					exportFileName: "Pie Chart",
					exportEnabled: false,
			                animationEnabled: true,
					legend:{
						verticalAlign: "bottom",
						horizontalAlign: "center"
					},
					data: [
					{       
						type: "pie",
						showInLegend: true,
						toolTipContent: "{legendText}: <strong>{y}</strong>",
						indexLabel: "{label} {y}",
						dataPoints: piearr
					}]
				});
		
		piechart.render();

		},3000);
	
	setTimeout(function(){
		var dotchart = new CanvasJS.Chart("dotchart",
			    {
		      theme: "theme2",
		      title:{
		        text: "Transactions YTD ('000)"
		      },
		      animationEnabled: true,
		      axisX: {
		        valueFormatString: "MMM",
		        interval:1,
		        intervalType: "month"
		        
		      },
		      axisY:{
		        includeZero: false
		        
		      },
		      data: [
		      {        
		        type: "line",
		        lineThickness: 0,        
		        dataPoints: dotArr
		      }
		      
		      
		      ]
		    });
		
		dotchart.render();

		},3000);
});

function isavialable(jres,j)
{
	var i=0;
	for(i=0;i<jres.monthcollections.length;i++)
	{
		var spl = jres.monthcollections[i].month.split("-");
		if(j == parseInt(spl[0]))
			return jres.monthcollections[i].amount;
	}
	
	return -1;
}

function gotoConfirm(json,formID)
{
	var formJSON = $.parseJSON(json);
	$('#'+formID+' td').each(function(){

		var key = $(this).attr("title");
		if(key != undefined && key != null && key != '')
		$(this).text(formJSON[key]);
	});
}


					
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
			  <li> <a href="#">Graphical Report</a> </li> 
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
	
	<div class="row-fluid sortable" id="hotelLevies"> 
		<div class="box span12">
					
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Graph Report
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

				</div>
			</div>  
			<div class="box-content">
			
			
			<div style="width:45%;float:left;">
				<div id="piechart" style="height: 300px;"></div>			
			</div>
			<div style="width:45%;margin-left:45px;float:left;">
				<div id="colchart" style="height:300px;"></div>
			</div>
			<div style="width:45%;margin-left:45px;float:left;">
				<div id="dotchart" style="height:300px;"></div>
			</div>
			
			<!-- <div id="" style="height: 300px;   width:40%;"></div> -->
			<!-- <div id="colchart" style="height: 300px;  width:40%;"></div> -->
			
			</div>
	</div>
	</div>
	
	
	 <div class="form-actions">
			<input type="hidden" name="requestJSON" id="requestJSON" />
			<input name="reffNo" type="hidden" id="reffNo" class="field"  maxlength="50" />
			<input name="amount" type="hidden" id="amount" class="field"  maxlength="50" />
			<input type="hidden" name="sbpData" id="sbpData" />
			<input type="hidden" name="lrData" id="lrData" />
			<input type="hidden" name="type" id="type"/>
	</div>  

</div> 
</form> 
</body> 
</html>