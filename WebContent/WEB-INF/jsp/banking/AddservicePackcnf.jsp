
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

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


   #sortable2 {
    border: 1px solid #eee;
    width: 300px;
    height: 300px;
    list-style-type: none;
    margin: 0;
    padding: 5px 0 0 0;
    align: center;
    margin-right: 10px;
  /*   page-align:center; */
    text-align:left;
    overflow-y:auto;
    /*overflow-x:hidden;*/  
 
  }
  #sortable2 li {
    margin: 0 5px 5px 5px;
    padding: 5px;
    font-size: 1 em;
    width: 200px;
  }

</style>
<script type="text/javascript" >
$(document).ready(function() {
	
 		var selData = "<s:property value='seltext' />";
 		var selval = "<s:property value='selval' />";
 		console.log(selval);
	    var users = $('#listtable');
	    users.html('');
	    //users.append('<ul id="sellist" class="connectedSortable">');
		//var xUl = users.find('tr');
		/* for (var i = 0; i < selData.split('%').length-1; i++) {
			users.append('<tr><td>'+selData.split('%')[i]+'</td></tr>');
	    } */
});


function listfwd(){
	var allVals = [];
	
	 var locDat = "";
	 var locDat1 = "";
	 var mulDat = "";
	 var locData = [];
	 var chkstat = "";
	 var p3=0;
	 var selval = "<s:property value='selval' />";
	 
	 $('#tbody_data > tr').each(function(){  
			 
			 var v_id= '#'+$(this).attr('id');  
			 $(v_id+' > td').each(function(index1){
				// console.log("==========================================>"+index1);
				 if(index1 == 0 )   {
					 locDat=$(this).text().trim();
				 } else if(index1 == 1) {
					 	locDat+="--"+$(this).text().trim();
				 } else if(index1 == 2) { 
			 		 	locDat+="--"+$(v_id+' td#'+$(this).attr('id')+' > input[type=text]'). val().trim()
				 }else if(index1 == 3) { 
			 		 	locDat+="--"+$(v_id+' td#'+$(this).attr('id')+' > input[type=text]'). val().trim()
				 }else { 
					 
					 var isChecked = $('#td3-'+p3).attr('checked')?true:false;
					 
					 console.log("aa"+isChecked);
					if(isChecked) chkstat="E";
					else chkstat="D";
					locDat+="--"+chkstat;
					p3=p3+1;
				 }
				 
			 });
			 
			 console.log(locDat);
			locData.push(locDat); 
	 }); 
	 
	  for(ind=0;ind < locData.length ;ind ++){
		 // users.append('<tr><td>'+selval.split('%')[i]+'</td></tr>');
		 if(ind ==0){
			 mulDat=locData[ind]+"--"+selval.split('%')[ind];
		 }else {
			 mulDat+="#"+locData[ind]+"--"+selval.split('%')[ind];
		 }
		 
	 } 
	 	console.log("multi data  [ "+mulDat);
	 
	 $("#multiData").val(mulDat); 
	
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/addservicepackack.action';
	$("#form1").submit();
	return true;
}

</script>

</head>

<body>

<form name="form1" id="form1" method="post" action="">
		<div id="content" class="span10">
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider">
							&gt;&gt; </span></li>
					<li><a href="userGrpCreation.action">Product Management</a> <span class="divider">
							&gt;&gt; </span></li>
					<li><a href="#">Add Service Pack</a></li>
				</ul>
			</div>

			<div class="row-fluid sortable">

				<div class="box span12">

					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Add Service Pack
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a>

						</div>
					</div>
					<div class="box-content">
						<fieldset>
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="Menu Name"><strong>Menu Name</strong></label></td>
									<td width="30%"><s:property value='menuname' /> <input	name="menuname" type="hidden" id="menuname" value="<s:property value='menuname' />" class="field"/></td>
									<td width="20%"><label for="Display Name"><strong>Display Name</strong></label></td>
									<td width="30%"><s:property value='dispname' /> <input name="dispname" type="hidden" id="dispname" value="<s:property value='dispname' />" class="grpname"/></td>
								</tr>

								<tr class="odd">
									<td><label for="User Id"><strong>Menu Code</strong></label></td>
									<td><s:property value='menucode' /> <input name="menucode" type="hidden" id="menucode" value="<s:property value='menucode' />" class="field"/></td>
									<td><label for="Menu Level"><strong>Menu Level<font color="red">*</font></strong></label></td>
									<td><s:property value='menulevel' /><input name="menulevel" type="hidden" id="menulevel" value="<s:property value='menulevel' />" class="field"/>
									<input name="multiData" type="hidden" id="multiData" class="field"/></td>
								</tr>

							</table>
						</fieldset>
						</div>



						<input type="hidden" name="selectUsers" id="selectUsers"></input>
					</div>
				</div>

<%-- 			 <div id="userDetails">
							<table width="950" border="0" cellpadding="5" cellspacing="1" id="listtable" align="center" class="table table-striped table-bordered bootstrap-datatable ">
									<tr >
								<td><strong><div align="center">Selected Services</div></strong></td>
								
								</tr>
								<tr><td>
								<!-- <div style="margin-left: 355px;" align="center" id="sortable2" ></div> -->
								<input name="selval" type="hidden" id="selval" value="<s:property value='selval'/>"/></td></tr>
								<input name="selval" type="text" id="selval" value="<s:property value='seltext'/>"/></td></tr>
								
							</table>
							</br>
				</div> --%>
				
				
							 <div class="box-content" id="listview" name="listview">  
					  <table width="100%" class="table table-striped table-bordered bootstrap-datatable " 
								id="documentTable" style="width: 100%;">
										  <thead>
												<tr>
													<th>Menu Order</th>
													<th>Service/Menulist Name</th>
													<th>Display Name</th>
													<th>Screen Title</th>
													<th>Enabled Status</th>
													
		 										</tr>
										  </thead>    
										 <tbody id="tbody_data">
											
										<s:generator val="%{seltext}" separator="%" >
											 <s:iterator var ="it1" status="selstatus"><tr id="tr-<s:property value="#selstatus.index" />">
											 <td><s:property value="#selstatus.index+1" /></td>
											 <td><s:property /></td>
											 <td id="td-<s:property value="#selstatus.index+2" />"><input type="text" id="td1-<s:property value="#selstatus.index" />"></td>
											 <td id="td-<s:property value="#selstatus.index+3" />"><input type="text" id="td2-<s:property value="#selstatus.index" />"></td>
											 <td id="td-<s:property value="#selstatus.index+4" />"><input type="checkbox" id="td3-<s:property value="#selstatus.index" />"></td>
												  <%-- <s:generator val="%{selval}" separator="%" >
												 		<s:iterator status="selstatus1">
												 			<td><s:property /></td>
												  		</s:iterator>  
													</s:generator> --%>
											 </tr></s:iterator>  
										</s:generator>
								  </tbody>
							</table>
					   </div> 
				
	
		
		
			
		<div id="dialog" title="Confirmation Required" style="display:none">
		   Proceed ? <!-- <div id="dia1"></div><font color="red"><div id="dia2"></div> --></font>
		</div>
		

					


			<div>
				<a class="btn btn-danger" href="#"
					onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp; <a
					class="btn btn-success" href="#" onClick="listfwd()">Submit</a>
					<span id ="error_dlno" class="errors"></span>
			</div>
		</div>
	</form>
</body>


<script type="text/javascript" >

    </script>

 
</html>