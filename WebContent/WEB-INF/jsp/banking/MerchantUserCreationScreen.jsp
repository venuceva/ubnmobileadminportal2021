
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript">

$(function() {


	var val = 0;
	var rowindex = 1;
	var bankfinalData="${multiData}";	
	console.log("asdadadasdas    "+bankfinalData);
	var bankfinalDatarows=bankfinalData.split("#");

	var addclass = "";
	var offArr = '${officeLocation}'.split(",");

	for(var i=0;i<bankfinalDatarows.length;i++) {

			if(val % 2 == 0 ){
				addclass = "even";
				val++;
			} else {
				addclass = "odd";
				val++;
			}

		var eachrow=bankfinalDatarows[i];
		console.log("in next page "+eachrow);
		var eachfieldArr=eachrow.split(",");
		var userid=eachfieldArr[2];
		//alert("789"+userid);
		var fname=eachfieldArr[0];
		var lname=eachfieldArr[1];
		var uid=eachfieldArr[3];
		var mobno = eachfieldArr[4];
		var mid = eachfieldArr[11];
		var sid = eachfieldArr[12];
		var utype = eachfieldArr[13];
		//alert(utype);
		if(utype=='MU'){
			$("#chdata").show();
		}

		var appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
						 "<td>"+rowindex+"</td>"+
						 "<td>"+fname+"</td>"+
						 "<td>"+lname+"</td>"+
						 "<td>"+uid+" </td>"+
						 "<td>"+mobno+"</td>"+
						 "<td>"+mid+"</td>"+
		<s:if test="typeuser == 'merusr'" >
						 "<td>"+sid+"</td>"+	</s:if>
		 "</tr>";
		$("#tbody_data").append(appendTxt);
		rowindex++;
	}

	
	var val1 = 0;
	var rowindex1 = 1;
	var channelData="${channeldata}";	
	console.log("channel data "+channelData);
	var channelDatarows=channelData.split("#");
	console.log("row data "+channelDatarows);
	var addclass1 = "";

	for(var j=0;j<channelDatarows.length;j++) {

			if(val1 % 2 == 0 ){
				addclass1 = "even";
				val1++;
			} else {
				addclass1 = "odd";
				val1++;
			}

		var eachrow1=channelDatarows[j];
		console.log("in next page two "+eachrow1);  
		var eachfieldArr1=eachrow1.split(",");

		var chname=eachfieldArr1[0];
		var chval=eachfieldArr1[1];

		var appendTxt1 = "<tr class="+addclass1+" index='"+rowindex1+"'> "+
						 "<td>"+rowindex1+"</td>"+
						 "<td>"+chname+"</td>"+
						 "<td>"+chval+"</td>"+
		 "</tr>";
		$("#tbody_data1").append(appendTxt1);
		rowindex1++;
	}

	
	
	
	$('#btn-cancel').live('click', function () {
		
		var bankfinalData="${multiData}";
		//alert(bankfinalData);
		var rows = bankfinalData.split('#'),cols,finalString="";
		var r,c;
		console.log(rows);
		for(r=0;r<rows.length;r++)
		{
			cols = rows[r].split(',');
			console.log(cols);
			finalString = finalString + cols[0]+","+cols[1]+","+cols[2]+","+cols[3];
			for(c=4;c<cols.length;c++)
			{
				finalString = finalString + "," + cols[c];
			}
			if(r != rows.length-1)
			finalString = finalString + '#';
		}

		$('#multiData').val(finalString);

		console.log("MultiDataSSSS : "+finalString);
		
		var url="${pageContext.request.contextPath}/<%=appName %>/generateMerchantAct.action";
		$("#form1")[0].action=url;
		$("#form1").submit();
	});

	$('#btn-submit').live('click', function () {
		var url="${pageContext.request.contextPath}/<%=appName %>/inserMerchantCreateDetails.action";
		$("#form1")[0].action=url;
		$("#form1").submit();

	});
});


</script>

</head>
<body>


	<div id="content" class="span10">

		<div>
			<ul class="breadcrumb">
			  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
			  <li> <a href="userGrpCreation.action">User Management</a> <span class="divider"> &gt;&gt; </span></li>
			  <li> <a href="#">User Creation</a>  </li>
			  
			</ul>
		</div>

<form name="form1" id="form1" method="post">

	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Merchant User Details Confirm
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>
			
			<div class="box-content">
				<fieldset>
					<table width="100%" class="table table-striped table-bordered bootstrap-datatable "
								id="mytable">
					  <thead>
						<tr>
							<th>Sno</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>User Id</th>
							<th>Mobile Number</th>
							<th>Merchant ID</th>
							<s:if test="typeuser == 'merusr'" >
							<th>Store ID</th>
							</s:if>
						</tr>
					  </thead>
					  <tbody id="tbody_data">
					  </tbody>
					</table>
				</fieldset>
			</div>
			
			<div class="box-content" id="chdata" style="display:none">
				<fieldset>
					<table width="100%" class="table table-striped table-bordered bootstrap-datatable "
								id="mytable1">
					  <thead>
						<tr>
							<th>Sno</th>
							<th>Channel Name</th>
							<th>Channel Value</th>
						</tr>
					  </thead>
					  <tbody id="tbody_data1">
					  </tbody>
					</table>
				</fieldset>
			</div>
			
		</div>
	</div>

	<div class="errors" id="errors"><s:actionerror /></div>
	
	<div class="form-actions">
		  <input type="button" class="btn btn-success" type="text"  name="btn-submit" id="btn-submit" value="Confirm" width="100" ></input>
			&nbsp;<input type="button" class="btn btn-danger" type="text"  name="btn-cancel" id="btn-cancel" value="Back" width="100" ></input>

			<input name="groupID" type="hidden" id="groupID" value="${groupID}" />
			<input name="entity" type="hidden" id="entity" value="${entity}" />
			<input type="hidden" name="multiData" id="multiData" value="${multiData}" />
			<input type="hidden" name="channeldata" id="channeldata" value="${channeldata}" />
			<input type="hidden" name="officeLocation" id="officeLocation" value="${officeLocation}" />
			<input type="hidden" name="typeuser" id="typeuser" value="${typeuser}" />
	</div>

</form>
</div>


</body>
</html>