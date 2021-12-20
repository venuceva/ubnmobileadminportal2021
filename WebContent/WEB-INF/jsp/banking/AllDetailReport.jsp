 
<%@page
	import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<%
String ctxstr = " ";
String appName= " ";  
try {
ctxstr = request.getContextPath();
appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString();
}catch (Exception e) {
	appName=" ";
	ctxstr = " ";
	System.out.println("Exception in All Details Reports JSP[" + e.getMessage()+ "]");
} 

%>
 
<meta charset="utf-8">
<title>CEVA</title> 
<link rel="shortcut icon" href="<%=ctxstr%>/images/<%=appName %>/favicon.ico" />
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script>

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


tfoot input {
        width: 100%;
        padding: 3px;
        box-sizing: border-box;
    }
</style>
<s:set value="responseJSON1" var="respData"/>

 <s:if test="#respData.size == 0" >
 	<s:set value="commonBean.responseJSON1" var="respData"/>
 </s:if>
 
<script type="text/javascript">

function undefinedcheck(data){
	return data == undefined  ? " " :  data;
}

</script>
<body> 
<form method="post" name="form1" id="form1"> 
	<div id="content" class="span10"> 	
		<div>
			<ul class="breadcrumb">
				<li><a href="home.action">Home</a> <span class="divider">&gt;&gt;</span> </li>
				<li><a href="#">Dashboard</a></li>
			</ul>
		</div> 
		<span class="container">
			<h6 class="flash-colour"><span id="groupannoumcement"></span></h6>
			<h6 class="flash-colour"><span id="userannoumcement"></span></h6> 
		</span> 
		<br/> 
		<div id="sortable1"></div>   
		
			<div class="row-fluid sortable" id="float-data">  
				<div class="box span12" id="groupInfo">
				 <div class="box-header well" data-original-title>Float 
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				<div class="box-content" id="user-data"> 
					<table class="table table-striped table-bordered bootstrap-datatable datatable" 
						id="DataTables_Table_1">
						<thead > 
							<tr>
										<th align="center">S No</th>
	 									<th>Customer ID</th>
										<th>First Name</th>
			 							<th>Institute</th>
										<th>Account Type</th>
										<th>Account NUmber</th>
										<th>Mobile NUmber</th>
										<th>Maker ID</th>
										<th>Maker Date</th>
										<th>Checker ID</th>
			 							<th>Checker Date</th>
										<th>Status</th>
										
	 						
							</tr>
						</thead> 
						
						<tfoot>
				            <tr>
        								<th>S No</th>
	 									<th>Customer ID</th>
										<th>First Name</th>
			 							<th>Institute</th>
										<th>Account Type</th>
										<th>Account NUmber</th>
										<th>Mobile NUmber</th>
										<th>Maker ID</th>
										<th>Maker Date</th>
										<th>Checker ID</th>
			 							<th>Checker Date</th>
										<th>Status</th>
				            </tr>
       					 </tfoot>
       					 
						<tbody id="tbody_data1"> 
						  
						  		<s:bean name="com.ceva.base.common.bean.JsonDataToObject" var="jsonToList">
										<s:param name="jsonData" value="%{accBean.multiData}"/>  
										<s:param name="searchData" value="%{'#'}"/>  
								</s:bean> 
								<s:iterator value="#jsonToList.data" var="mulData1"  status="mulDataStatus" > 
										<s:if test="#mulDataStatus.even == true" > 
											<tr class="even" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
										</s:if>
										<s:elseif test="#mulDataStatus.odd == true">
											<tr class="odd" align="center" id="tr-<s:property value="#mulDataStatus.index" />" index="<s:property value="#mulDataStatus.index" />">
										</s:elseif> 
									
										<td><s:property value="#mulDataStatus.index+1" /></td>
											<s:generator val="%{#mulData1}"
												var="bankDat" separator="," >  
												<s:iterator status="itrStatus">  
														<td><s:property /></td> 
												</s:iterator>  
											</s:generator> 
										 
									</tr> 
								</s:iterator>
						  
						  </tbody>
						
					</table> 
				</div> 
			</div>  
		  </div>

	</div>  
</form>

<script type="text/javascript">



$(document).ready(function() {
    // Setup - add a text input to each footer cell
    $('#tbody_data1 tfoot th').each( function () {
        var title = $('#example thead th').eq( $(this).index() ).text();
        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
    } );
 
    // DataTable
    var table = $('#tbody_data1').DataTable();
 
    // Apply the search
    table.columns().every( function () {
        var that = this;
 
        $( 'input', this.footer() ).on( 'keyup change', function () {
            that
                .search( this.value )
                .draw();
        } );
    } );
} );




</script>

</body>
</html>
