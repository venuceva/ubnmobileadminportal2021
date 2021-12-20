<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<link rel="stylesheet" type="text/css" media="screen" href='${pageContext.request.contextPath}/css/jquery.cleditor.css' />

<script type="text/javascript" >

var mainHeader=[];
//var keys=['UM','MA','BM','FM','MP'];
var keys=['UM'];
//var abbKeys=['User Management','Merchant Management','Bin Management','Fee Management','M-Pesa Account Management'];
var abbKeys=['User Management'];
var response;
var userLinks;

function createJSON(json)
{
	//console.log(json);
	var html="";
	var i,j;
	var jarr=[];
	var jobj={};
	var a=0,b=0,c=0,d=0,e=0;
	for(i=0;i<keys.length;i++)
	{
		var key = keys[i];
		html = html + "<tr>";
		a=0;b=0;c=0;d=0;e=0;
		jarr = json[key];
		//console.log("VALUES : ");
		//console.log(jarr);
		
		for(j=0;j<jarr.length;j++)
		{
			if(jarr[j] != null)
			{
				if(jarr[j].NEW != '0')
				{
					a = a + parseFloat(jarr[j].NEW);
				}
				if(jarr[j].PENDING != '0')
				{
					b = b + parseFloat(jarr[j].PENDING);
				}
				if(jarr[j].CLOSED != '0')
				{
					c = c + parseFloat(jarr[j].CLOSED);
				}
				if(jarr[j].REJECTED != '0')
				{
					d = d + parseFloat(jarr[j].REJECTED);
				}
				if(jarr[j].DELETED != '0')
				{
					e = e + parseFloat(jarr[j].DELETED);
				}
			}
		}

		html = html + "<td><a href='#' disp='"+key+"'>"+abbKeys[i]+"</a></td>";
		html = html + "<td><a href'#' ";
		html = html + "  >"+a+"</a></td>";
		html = html + "<td><a href'#' >"+c+"</a></td>";
		html = html + "<td><a href'#' >"+d+"</a></td>";
		html = html + "<td><a href'#' >"+b+"</a></td>";
		html = html + "<td><a href'#' >"+e+"</a></td>";
		html = html + "</tr>";
	}

	$('#announceTBody').append(html);

}

function fillGrid(json,clickinfo)
{
	console.log(json);
	$('#UM').hide();
	$('#DataTables_Table_0').hide();

	$('#'+clickinfo).show();
	$('#'+clickinfo+"Body").empty();

	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var newlist=0;
	var addclass = "";
	var queryString = '?';

	$.each(json, function(i,v)
	{
		if(v != null)
		{
		
			if(val % 2 == 0 ) {
				addclass = "even";
				val++;
			}
			else {
				addclass = "odd";
				val++;
			}

			var status="";
			var status_class ="";
			var text = "";
			var authcode ="";

			authcode=v.AUTH_CODE;
			//alert(authcode);
			headingname=v.HEADING_NAME;
			if(v.PENDING > 0 ) {
				queryString ='?AUTH_CODE='+v.AUTH_CODE+'&STATUS=P';
				if (v.AUTH_CODE=='NEWACCAUTH' || v.AUTH_CODE=='ACCACTDCT' || v.AUTH_CODE=='ACCTPINRESET'|| v.AUTH_CODE=='MODCUSTDETAUTH'|| v.AUTH_CODE=='DELACCAUTH'|| v.AUTH_CODE=='BULKREGAUTH')
					{
					pending="<a href=commonauthsearch.action"+queryString+" class='label label-success' index='"+rowindex+"'>"+v.PENDING+"</a>";
					}
				else{
					pending="<a href="+v.ACTION_URL+""+queryString+" class='label label-success' index='"+rowindex+"'>"+v.PENDING+"</a>";
				}
					status_class = "btn btn-success";

				} else  {
					pending= v.PENDING;
					status_class = "btn btn-success";
			}

			if(v.NEW > 0 ) {
				queryString ='?AUTH_CODE='+v.AUTH_CODE+'&STATUS=N';
				if (v.AUTH_CODE=='NEWACCAUTH' || v.AUTH_CODE=='ACCACTDCT' || v.AUTH_CODE=='ACCTPINRESET'|| v.AUTH_CODE=='MODCUSTDETAUTH'|| v.AUTH_CODE=='DELACCAUTH'|| v.AUTH_CODE=='BULKREGAUTH')
				{
				newlist="<a href=commonauthsearch.action"+queryString+"  class='label label-success' index='"+rowindex+"'>"+v.NEW+"</a>";
				}
			else{
					newlist="<a href="+v.ACTION_URL+""+queryString+"  class='label label-success' index='"+rowindex+"'>"+v.NEW+"</a>";
			}
					status_class = "btn btn-danger";

				} else  {
					newlist= v.NEW;
					status_class = "btn btn-success";
			}

			if(v.CLOSED > 0 ) {
				
				head = "<a href='#'    index='"+rowindex+"'>"+v.HEADING_NAME+"</a>";
				queryString ='?AUTH_CODE='+v.AUTH_CODE+'&STATUS=C';
				 if (v.AUTH_CODE=='NEWACCAUTH' || v.AUTH_CODE=='ACCACTDCT' || v.AUTH_CODE=='ACCTPINRESET'|| v.AUTH_CODE=='MODCUSTDETAUTH'|| v.AUTH_CODE=='DELACCAUTH'|| v.AUTH_CODE=='BULKREGAUTH')
				{
					close="<a href=commonauthsearch.action"+queryString+"  class='label label-success' index='"+rowindex+"'>"+v.CLOSED+"</a>";
				}
			else{ 
				close="<a href="+v.ACTION_URL+""+queryString+"  class='label label-success' index='"+rowindex+"'>"+v.CLOSED+"</a>";
			}
				
				
				status_class = "btn btn-danger";
			} else  {
			    close= v.CLOSED ;
				status_class = "btn btn-success";
			}

			if(v.REJECTED > 0 ) {
				queryString ='?AUTH_CODE='+v.AUTH_CODE+'&STATUS=R';
				if (v.AUTH_CODE=='NEWACCAUTH' || v.AUTH_CODE=='ACCACTDCT' || v.AUTH_CODE=='ACCTPINRESET'|| v.AUTH_CODE=='MODCUSTDETAUTH'|| v.AUTH_CODE=='DELACCAUTH'|| v.AUTH_CODE=='BULKREGAUTH')
				{
					reject="<a href=commonauthsearch.action"+queryString+"  class='label label-success' index='"+rowindex+"'>"+v.REJECTED+"</a>";
				}
			else{
				reject="<a href="+v.ACTION_URL+""+queryString+"  class='label label-success' index='"+rowindex+"'>"+v.REJECTED+"</a>";
			}
				
				status_class = "btn btn-danger";
			} else  {
				reject= v.REJECTED ;
				status_class = "btn btn-success";
			}
			
			if(v.DELETED > 0 ) {
				queryString ='?AUTH_CODE='+v.AUTH_CODE+'&STATUS=D';
				if (v.AUTH_CODE=='NEWACCAUTH' || v.AUTH_CODE=='ACCACTDCT' || v.AUTH_CODE=='ACCTPINRESET'|| v.AUTH_CODE=='MODCUSTDETAUTH'|| v.AUTH_CODE=='DELACCAUTH'|| v.AUTH_CODE=='BULKREGAUTH')
				{
					delte="<a href=commonauthsearch.action"+queryString+"  class='label label-success' index='"+rowindex+"'>"+v.DELETED+"</a>";
				}
			else{
				delte="<a href="+v.ACTION_URL+""+queryString+"  class='label label-success' index='"+rowindex+"'>"+v.DELETED+"</a>";
			}
				
				status_class = "btn btn-danger";
			} else  {
				delte= v.DELETED ;
				status_class = "btn btn-success";
			}
			
			//alert(userLinks);


			var jsonLinks = userLinks;
			var linkIndex = new Array();
			var linkName = new Array();

		//console.log('kkkkkkkk...:'+pending);
			$.each(jsonLinks, function(index, v1) {
				linkIndex[index] = index;
				linkName[index] = v1.name;

					if(linkName[index] == authcode)  {
						var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'> "+
						"<td >"+headingname+"</span></td>"+
		 				"<td>"+newlist+"</span></td>"+
						"<td class='hidden-phone'>"+close+"</span></td>"+
						"<td class='center hidden-phone'>"+reject+"</span></td>"+
						"<td class='hidden-phone'>"+pending+"</span></td>"+
						"<td class='hidden-phone'>"+delte+"</span></td></tr>";
						//alert('clickinfo..:'+clickinfo);
						console.log(clickinfo);
					$("#"+clickinfo).append(appendTxt);
					appendTxt="";
					rowindex = ++rowindex;

					}

			});
		}
	});

	$('#bck').show();

}

$(document).ready(function ()
{
	var storeData ='${responseJSON.AUTH_PENDING_LIST}';
	$('#UM').hide();
	$('#bck').hide();

 	var json = jQuery.parseJSON(storeData);
 	console.log(json);
 	response = json;
 	userLinks=jQuery.parseJSON('${USER_LINKS}');

 	createJSON(json);


 

	 $('a').click(function(){
		var clickinfo = $(this).attr('disp');
		if(clickinfo == 'UM')
		fillGrid(response[clickinfo],clickinfo);
	}); 

	$('#bck').click(function()
	{
		$('#UM').hide();
		$('#bck').hide();
		$('#DataTables_Table_0').show();


	});

});


</script>
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
.errors {
color: red;
}
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
</style>
<link rel="shortcut icon" href="images/favicon.ico">
</head>
<body>
		<div id="content" class="span10">
			<div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Checker</a></li>
				</ul>
			</div>



		<form name="form1" id="form1" method="post" action="">
              <div class="row-fluid sortable"><!--/span-->
				<div class="box span12">
					  <div class="box-header well" data-original-title>Checker List
						  <div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					  </div>
						<div class="box-content">
							<table width='100%' class="table table-striped table-bordered bootstrap-datatable "
 								id="DataTables_Table_0" >
								<thead>
									<tr>
									<!-- 	<th>S No</th> -->

										<th>Checker Name</th>
										<th class="center hidden-phone">Today List</th>
									    <th>Authorized</th>
										<th class="hidden-phone">Rejected</th>
										<th class="hidden-phone">Pending</th>
										<th class="hidden-phone">Deleted</th>
								  	</tr>
								</thead>
								<tbody  id="announceTBody">
								</tbody>
							</table>

							<table width='100%' class="table table-striped table-bordered bootstrap-datatable "
 								id="UM" >
								<thead>
									<tr>
									<!-- 	<th>S No</th> -->

										<th>Checker Name</th>
										<th class="center hidden-phone">Today List</th>
									    <th>Authorized</th>
										<th class="hidden-phone">Rejected</th>
										<th class="hidden-phone">Pending</th>
										<th class="hidden-phone">Deleted</th>
								  	</tr>
								</thead>
								<tbody  id="UMBody">
								</tbody>
							</table>

						


						</div>
					</div>
 			</div>
 			<center> <input type="button" class="btn btn-danger" name="btn-back" value="Back" id="bck"/> </center>
		</form>
	</div>

 </body>
</html>
