<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title> </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%>    
 
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
$(function(){
	
	$('#searchVal').show();
	$('#searchDate').hide();
	$('#status').hide();
	$('#status1').hide();
	 
	
	  /* var acctRules = {
	  			
			   rules: {
				   accDetails : { required : true},
				   searchVal: {
			            required: {
			                depends: function(element){
			                    if ($('#searchDate').val() == '') {
			                        return true;
			                    } else {
			                        return false;
			                    }
			                }
			            }
			        },
			    },
			 messages : {
					   accDetails : { 
					   		required : "Please Select Search Type."
						} ,
						searchVal : { 
					   		required : "Please Enter Search Data."
						} 
				    }
	}; */
	 
	   $('#search').on('click', function(){
		    
		    //$("#form1").validate(acctRules);
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/getSearchAccounts.action";
			$("#form1").submit();
		
	 });  
	   
	   $('#accDetails').on('change', function(){
		   
		   var  accDetails=$('#accDetails').val();
		   
		   if(accDetails=='MakerDate' || accDetails=='CheckerDate' ){
				$('#searchVal').hide();
				$('#searchDate').show();
				$('#status').hide();
				$('#status1').hide();
			}else if(accDetails=='CustomerStatus'){
				$('#searchVal').hide();
				$('#searchDate').hide();
				$('#status').show();
				$('#status1').hide();
			}else if(accDetails=='AuthorizationStatus'){
				$('#searchVal').hide();
				$('#searchDate').hide();
				$('#status').hide();
				$('#status1').show();
			} else{
				$('#searchVal').show();
				$('#searchDate').hide();
				$('#status').hide();
				$('#status1').hide();
			}
	   });
	   
	   
	   $(list).each(function(i,val){
		   $('#'+val).datepicker(datepickerOptions);
		  });
});


var list = "searchDate".split(",");
var datepickerOptions = {
   showTime: false,
   showHour: false,
   showMinute: false,
    dateFormat:'dd/mm/yy',
    alwaysSetTime: false,
  changeMonth: true,
   changeYear: true
}; 
	  
	  <%-- function searchAjax(){
			onClick="searchAjax()"
		    $("#form1").validate(acctRules);
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/getSearchAccounts.action";
			$("#form1").submit();		
			
	 }   --%>
	
	//For Closing Select Box Error Message_Start
	 $(document).on('change','select',function(event) {  
	 	 if($('#'+$(this).attr('id')).next('label').text().length > 0) {
	 		  $('#'+$(this).attr('id')).next('label').text(''); 
	 		  $('#'+$(this).attr('id')).next('label').remove();
	 	 }
	  
	 });
</script>
</head>

<body>
<form name="form1" id="form1" method="post" >	
	 
	<div id="form1-content" class="span10">   
		  <div>
			 <ul class="breadcrumb">
				<li><a href="home.action">Home </a> <span class="divider"> &gt;&gt; </span></li>
				<li><a href="#">A/C Management</a></li>
			 </ul>
		 </div>
		 
	<div class="box-content" id="top-layer-anchor">
	  
			  <span>	
		        <label for="Search Accounts"><strong>Search Accounts</strong></label>
		        <select data-placeholder="Choose a Search Type..." name="accDetails"  id="accDetails" style="width:250px;" >
                     <%--  <select  cssClass="chosen-select"  headerKey=""   headerValue="Select" 
						   requiredLabel="true" 	 theme="simple"  data-placeholder="Choose Credit Card..." > --%>
                          <option value="">Select</option>
                          <option value="CustomerId">CustomerId</option>
                          <option value="CustomerName">CustomerName</option>
                          <option value="Institute">Institute</option>
                          <option value="AccountType">AccountType</option>
                          <option value="AccountNumber">AccountNumber</option>
                          <option value="MobileNumber">MobileNumber</option>
                          <option value="MakerId">MakerId</option>
                    	  <option value="MakerDate">MakerDate</option>
                          <option value="CheckerId">CheckerId</option>
                          <option value="CheckerDate">CheckerDate</option>
                          <option value="CustomerStatus">CustomerStatus</option>
                          <option value="AuthorizationStatus">AuthorizationStatus</option>
                      </select>
                        
                      <input type="text" name="searchVal" id="searchVal"> 
                      <input type="text" name="searchDate" id="searchDate"> 
                     
	                  <select data-placeholder="Choose a Search Type..." name="status"  id="status" style="width:150px;" >
                          <option value="">Select</option>
                          <option value="A">Authorize</option>
                          <option value="R">Rejected</option>
                      </select>
                      <select data-placeholder="Choose a Search Type..." name="status1"  id="status1" style="width:150px;" >
                          <option value="">Select</option>
                          <option value="C">Completed</option>
                          <option value="P">Pending</option>
                          <option value="R">Rejected</option>
                      </select>
                       &nbsp;&nbsp;&nbsp;
                      <a href="#" class="btn btn-info" id="search" style="margin:5 5 5px;float: center;"type="text"   placeholder="Search.."   >
		              <i class="icon icon-web icon-white"></i>&nbsp;Search</a>  
            </span> 
          </div>
          
	<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Account Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				<div class="box-content"> 
					<table width='100%' class="table table-striped table-bordered bootstrap-datatable datatable"  
						id="DataTables_Table_0" >
						<thead>
							<tr>
								<th>SNo</th>
  								<th>Customer ID</th>
 								<th>Customer Name</th>
 								<th>Institute</th>
 								<th>Account Type</th>
								<th>Account Number</th>
								<th>Mobile Number</th>
							    <th>Maker Id</th>
								<th>Maker Date</th>
								<th>Checker Id</th>
								<th>Checker Date</th>
								<th>Customer Status </th>  
								<th>Authorization Status</th>    
							</tr>
						</thead> 
						<tbody id="userGroupTBody">
 								<s:iterator value="responseJSON['accounts']" var="billerData" status="billerDataStatus"> 
										    <s:if test="#userDetStatus.even == true" > 
											   <s:set value="%{'even'}" var="flag"/> 
 										    </s:if>
										    <s:elseif test="#userStatus.odd == true">
		      								   <s:set value="%{'odd'}" var="flag"/> 	
		   								    </s:elseif> 
		   								 	<tr class="<s:property value='#flag' />" index="<s:property value='#billerDataStatus.index' />"  id="<s:property value='#billerDataStatus.index' />">
		   								 	<td class='hidden-phone'><s:property value='#billerDataStatus.index+1' /></td>
 											
											<!-- Iterating TD'S -->
											<s:iterator value="#billerData" status="billerStatus" >
											 <s:if test="#billerStatus.index == 0" > 
											 	<s:set value="value" var="billerIdVal"/>
											 </s:if>
											  <s:if test="#billerStatus.index == 1" >  
												<td>  <s:property value="value" /></td> 
											  </s:if>
											  <s:elseif test="#billerStatus.index == 2" >
												 <td class='hidden-phone'><s:property value="value" /></td>
											  </s:elseif>
											  <s:elseif test="#billerStatus.index == 3" >
												 <td class='center hidden-phone'><s:property value="value" /></td>
											 </s:elseif>
											 <s:else>
													<td><s:property value="value" /></td>
											 </s:else> 
											</s:iterator> 
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
$(function(){ 
	var config = {
      '.chosen-select'           : {},
      '.chosen-select-deselect'  : {allow_single_deselect:true},
      '.chosen-select-no-single' : {disable_search_threshold:10},
      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chosen-select-width'     : {width:"95%"}
    };
	
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }  
});  
</script>
  <script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
  <script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script>   
</body>
</html>