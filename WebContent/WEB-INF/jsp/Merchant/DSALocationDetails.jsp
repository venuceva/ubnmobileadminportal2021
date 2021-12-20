 <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>CEVA </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
	
  <script src="http://maps.google.com/maps/api/js?key=AIzaSyC4tceSjxbaC48h8Ofp6w3tzRgx6nDcMRc&sensor=false"    type="text/javascript"></script>
	
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
<script type="text/javascript" >
binDetails = "";
var prdIndex = new Array();


$(document).ready(function() {
	
	var feejson =  '${responseJSON.VIEW_LMT_DATA}';
	
	console.log("Welcome to pro");
	var v="${responseJSON.trans}";
	
	var feefinaljsonobj = jQuery.parseJSON(feejson);
	
	
	buildtable(feefinaljsonobj,'FEE_TBody');
	
	

});


function buildtable(jsonArray,divid)
{
	
	$('#'+divid).empty();
	var i=0;
	var htmlString="";
	
	$.each(jsonArray, function(index,jsonObject){
	
			++i;
			htmlString = htmlString + "<tr class='values' id="+i+"  >";
			htmlString = htmlString + "<td id=USER_ID"+ i + " name=USER_ID >" + jsonObject.USER_ID + "</td>";
			 htmlString = htmlString + "<td id=LATITUDE"+ i + " name=LATITUDE  >" + jsonObject.LATITUDE + "</td>";
			htmlString = htmlString + "<td id=LONGITUDE"+ i + " name=LONGITUDE >" + jsonObject.LONGITUDE + "</td>";
			htmlString = htmlString + "</tr>";
	
	});
	

	console.log("Final HtmlString ["+htmlString+"]");
	
	$('#'+divid).append(htmlString);

}

 


		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/locatordetails.action';
	$("#form1").submit();
	return true;
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
		width: 700, 
	    height: 650,
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
	<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">DSA Logged Information</a></li>
				</ul>
			</div>
			
			<div id="dialog" name="dialog" title="View" width="100" style="display:none">
  						<strong><div  id="result"><div id="pie"></div>
  						</div></strong>
					</div>
	
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>DSA Logged Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				
				
				
				
				<div class="box-content"> 
				
				
				  <div id="map" style="width: 100%; height: 500px;"></div>
				  
					<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  
						id="DataTables_Table_1" >
						<thead>
							<tr >							
								 <th width="10%">User Id</th>
								<th width="10%">Latitude</th>
							    <th width="10%">Longitude</th> 
							          
							</tr>
							
						</thead> 
						
						
						 <tbody id="FEE_TBody">
						
						</tbody>  
					</table>
						
				</div>
			</div>
		</div> 
	 <input type="hidden" id="refno" name="refno"/>
	 <input type="hidden" id="userid" name="userid"/>
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
			</div> 
			 
		</div> 
	 

  
  
</form>
 
  <script type="text/javascript">
   
  var feejson =  '${responseJSON.VIEW_LMT_DATA}';

	var feefinaljsonobj = jQuery.parseJSON(feejson);
	
    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 6,
      center: new google.maps.LatLng(9.0820, 8.6753),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var infowindow = new google.maps.InfoWindow();

    var marker, i;

    $.each(feefinaljsonobj, function(index,jsonObject){ 
      marker = new google.maps.Marker({
    	  
        position: new google.maps.LatLng(jsonObject.LATITUDE, jsonObject.LONGITUDE),
        map: map
      });

      google.maps.event.addListener(marker, 'click', (function(marker, i) {
    	  
        return function() {
          infowindow.setContent("DSA User Id :: "+jsonObject.USER_ID);
          infowindow.open(map, marker);
        }
      })(marker, i));
    });
    
    

	
	

    
  </script>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 