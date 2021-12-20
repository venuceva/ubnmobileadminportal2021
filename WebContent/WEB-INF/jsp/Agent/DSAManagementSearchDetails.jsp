
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>IMPERIAL</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">	
<%@taglib uri="/struts-tags" prefix="s"%> 	 
   	 
<script type="text/javascript" >

var userLinkData ='${USER_LINKS}';
var jsonLinks = jQuery.parseJSON(userLinkData);
var linkIndex = new Array();
var linkaction = new Array();
var linkStatus = new Array();

var num="";

$(document).ready(function () { 
	
	
	
	$.each(jsonLinks, function(index, v) {
	linkIndex[index] = index;
	linkaction[index] = v.name;
	linkStatus[index] = v.status;
	

	$("#"+v.name).removeAttr("disabled");
	
	num=index;
	
	/* $('#form1').find('a').each(function(index) {
		var anchor = $(this); 
		if(this.id!=""){
			anchor.attr("disabled", "disabled"); 
		
		}
		if(anchor.attr('id')!=undefined){
			if(v.name==anchor.attr('id')){
				anchor.removeAttr("disabled");
				
			}
		}
		
		
		
	}); 
	 */

	//alert(linkaction)
	
	
});  
}); 

 
var list = "frdate,todate".split(",");
var datepickerOptions = {
				showTime: true,
				showHour: true,
				showMinute: true,
	  		dateFormat:'dd/mm/yy',
	  		alwaysSetTime: false,
	  		yearRange: '1910:2020',
			changeMonth: true,
			changeYear: true
};
		
$(document).ready(function () {
	


	var tds=new Array();
	var merchantData ='${responseJSON.Files_List}';

	var json = jQuery.parseJSON(merchantData);
	console.log(json);
	
	var val = 1;
	var rowindex = 0;
	var colindex = 0;
	var addclass = "";
	var i=0;
	$.each(json, function(i, v) {
		if(val % 2 == 0 ) {
			addclass = "even";
			val++;
		}
		else {
			addclass = "odd";
			val++;
		}  
		var rowCount = $('#merchantTBody > tr').length;

		
		colindex = ++ colindex; 
		var REF_NO=(v.REF_NO == undefined) ? "" : v.REF_NO;
		var USER_ID=(v.USER_ID == undefined) ? "" : v.USER_ID;
		var MOBILE_NO=(v.MOBILE_NO == undefined) ? "" : v.MOBILE_NO;
		
		index=colindex-1;
		
		var tabData="DataTables_Table_0";
			
		/* &nbsp;&nbsp; <a id='C"+REF_NO+"' class='btn btn-success' href='#' onclick='lightbox_open(\""+USER_ID+"\",\""+MOBILE_NO+"\",\""+REF_NO+"\")' index='"+rowindex+"'>View Transaction</a>
		 */	
		 
			//alert(linkaction+"---"+num);
		
		
		var appendTxt = "<tr class="+addclass+" id='"+rowindex+"' index='"+rowindex+"'  > "+
			"<td >"+colindex+"</td>"+
			"<td style='display:none'>"+REF_NO+"</span></td>"+
			"<td >"+USER_ID+"</span></td>"+
			"<td>"+MOBILE_NO+"</span></td>"+
/* 			"<td><p><a id='C"+REF_NO+"' class='btn btn-success' href='agentregmodifydetails.action?userid="+REF_NO+"&actiontype=VIEW' index='"+rowindex+"'>View Details</a> </p></td>";
 */			
 			"<td id='' ><a class='btn btn-success' href='#' id='dsaview_"+i+"'  index="+i+" val="+i+" title='View' data-rel='tooltip'  disabled> <i class='icon icon-book icon-white'></i></a>"+
			"&nbsp&nbsp<a class='btn btn-warning' href='#' id='dsamodify_"+i+"'  index="+i+" val="+i+" title='modify' data-rel='tooltip'  disabled> <i class='icon icon-edit icon-white'></i></a></td>";
			/* "&nbsp&nbsp<a id='dsaactive' class='btn btn btn-danger'   href='#' title='Active/Deactivate' data-rel='tooltip' ><i class='icon icon-retweet icon-white'></i></a>"+
			"&nbsp&nbsp<a id='dsalock' class='btn btn btn-info'   href='#' title='Lock/Unlock' data-rel='tooltip' ><i class='icon icon-lock icon-white'></i></a> "; */
			
			"</tr>";
			
			i++;
			$("#merchantTBody").append(appendTxt);	
			rowindex = ++rowindex;
			
			
			  for(ii=0;ii<=num;ii++){
					//alert(linkaction[ii]+(i-1)); 
					var v=linkaction[ii]+"_"+(i-1);
					$('#'+v).removeAttr("disabled");
					$('#'+v).attr("id", linkaction[ii]);
					 
				 }  
	}); 
	
	

	
	
}); 



	

	
	
 


function radioselect(){
	 $('#errormsg').text("");
}




$(document).on('click','a',function(event) {
	
    var $row = jQuery(this).closest('tr');
    var $columns = $row.find('td');

    $columns.addClass('row-highlight');
    var values = "";
     var btn=this.id;
    
    jQuery.each($columns, function(i, item) {
    	
    	if(i==0)
    		{
    		values =  item.innerHTML;
    		}else{
    			values = values +"$"+ item.innerHTML;
    		}
    });
   
  	
	var val = values;
	var code = "";
	var name = "";
	if(val.match("$"))
		{
		var x = val.split("$");
		code = x[1];
		name = x[2];
		}

	
	if(btn == "dsaview")
	{
		$("#userid").val(code);
		$("#actiontype").val("VIEW");
		$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/agentregmodifydetails.action";
	 	$("#form1").submit();
	  return true; 
	}

	if(btn == "dsamodify")
		{
		$("#userid").val(name.toUpperCase());
		$("#actiontype").val("MODIFY");
		  $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/agentregmodifydetails.action";
		  $("#form1").submit();
		  return true; 
		}
	
	

	
	
	
    
}); 
		 



<%-- if(btn == "dsaactive")
{
$("#userid").val(code);
$("#actiontype").val("ACTIVE");
  $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/agentregmodifydetails.action";
  $("#form1").submit();
  return true; 
}
if(btn == "dsalock")
{
$("#userid").val(code);
$("#actiontype").val("LOCK");
  $("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/agentregmodifydetails.action";
  $("#form1").submit();
  return true; 
} --%>


	


function redirectAct(){
	 $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
	$("#form1").submit();
	return true; 
}


$.validator.addMethod("regex", function(value, element, regexpr) {          
return regexpr.test(value);
}, ""); 

function lightbox_open(uid,pno,ref)
{
	
 window.scrollTo(0,0);
$('#usrid').text(uid);
 $('#phno').text(pno);	
 document.getElementById('light').style.display='block';
}
function lightbox_close()
{
 document.getElementById('light').style.display='none';
}



function createLimitData(myaction){
	 $('#linkmode').val("NEW");
	    $("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/'+myaction+'.action';
		$("#form1").submit();
		return true;
}


</script>

</head>


<body>
<form name="form1" id="form1" method="post" action="">	
<!-- topbar ends -->

	
		<div id="content" class="span10">  
			<div>
				<ul class="breadcrumb">
				  <li> <a  href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li><a href="dsamanagement.action">DSA Management</a></li>
				</ul>
			</div>
			
			
			 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			 
	 <div class="box-content" id="top-layer-anchor">
				 <div>
					<a href="#" class="btn btn-success" id="dsaregistration"    title='Dsa Registration' data-rel='popover'  data-content='Dsa Registration' onClick="createLimitData('agentregmodifysearch')" disabled><i class='icon icon-plus icon-white'></i>&nbsp;Dsa Registration</a> &nbsp;					
 				 </div>	
			</div>
        	<div class="row-fluid sortable" ><!--/span--> 
				<div class="box span12"> 
					<div class="box-header well"  data-original-title>Search DSA Details
					  <div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					  </div>
					</div>
                  
				<div class="box-content"> 
					<fieldset>
						<table width="100%" class="table table-striped table-bordered bootstrap-datatable datatable" 
							id="DataTables_Table_0">
							<thead>
								<tr >
									<th width="10%">SNo</th>
									<th width="30%" style='display:none' >refno</th>
									<th width="30%">User Id</th>
									<th width="30%">Mobile Number</th>
									<th width="30%">Action</th>
								</tr>
								
							</thead>

							<tbody  id="merchantTBody">
							</tbody>
							
						</table>
						
						<input type="hidden"    id="userid" name="userid"  /> 
						<input type="hidden"    id="actiontype" name="actiontype"  /> 
					</fieldset>
					
					
				
					
              </div>
             </div>
           
           </div>
			  <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Home" />
			</div> 
		</div>	 
</form>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script> 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.js'></script> 
</body>
</html>
