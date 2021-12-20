
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %> 

<!-- Below is ZTree Structure --> 
<link rel="stylesheet" href="<%=ctxstr%>/css/treenode/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=ctxstr%>/css/treenode/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=ctxstr%>/css/treenode/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=ctxstr%>/css/treenode/jquery.ztree.excheck-3.5.js"></script> 
<script type="text/javascript" src="<%=ctxstr%>/css/treenode/jquery.ztree.exhide-3.5.js"></script>

<style type="text/css">
label.error, .errors {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
} 
input#groupID{text-transform:uppercase};
</style>

<SCRIPT type="text/javascript">
var checBoxCnt = 0;
function checkChecked(classCheck){
	
	if(classCheck == "button chk checkbox_true_full" 
		|| classCheck == "button chk checkbox_true_part" 
		 ||classCheck == "button chk checkbox_true_disable")	return true;

	return false;
}

function getCheckVal(obj){ 
	var getText = "#"+$(obj).attr('id') +" > a > span:eq(1)";
	getText = $(getText).text(); 
	return getText;
}	 

function constructString(parentId) { 
	var getText = "";  
	var checkedStringConstruction="";	
	$(parentId).each(function(mainindex,mainele){   // Top Parent Iteration
	 
		var pid = $(this).attr('class').replace("level","");
		var id = $(this).attr('id').replace("treeDemo_","");
		 
		var parentId = $(this).attr('id');
		
		var span2Class = "#"+parentId+" > span#"+parentId+"_check";
	 
		getText = getCheckVal(this); 
		
		checkedStringConstruction+='{ "id":"'+id+'", "pId":"'+pid+'",  "name":"'+getText+'" , "checked":"'+checkChecked($(span2Class).attr('class'))+'" , "open":"true", "title":"", "chkDisabled":"false"},';
	 
		var getUlList = "#"+parentId +' > ul > li'; 
		if($(getUlList).length > 0) {
			checkedStringConstruction+=constructStringSub(getUlList,parentId); 
		} else return;  
	});  

	return checkedStringConstruction;
} 
function constructStringSub(parentSubId,parentId) {

	var getText = "";  
	var str="";	
	$(parentSubId).each(function(mainindex,mainele) {   // Sub Child Iteration
	 
		var status = false;
		var pid = $("#"+parentId).attr('id').replace("treeDemo_","");
		var id = $(this).attr('id').replace("treeDemo_","");
		
		var parentSubId = $(this).attr('id');
		
		var span2Class = "#"+parentSubId+" > span#"+parentSubId+"_check"; 
		
		getText = getCheckVal(this); 
		str+='{ "id":"'+id+'", "pId":"'+pid+'",  "name":"'+getText+'" , "checked":"'+checkChecked($(span2Class).attr('class'))+'" , "open":"true", "title":"","chkDisabled":"false"},';

		var getUlList = "#"+parentSubId +' > ul > li';
		 
		if($(getUlList).length > 0) {
			str+=constructStringSubSub(getUlList,parentSubId); 
		} else return;
	 
	}); 
	 
	return str;
}

function constructStringSubSub(parentSubSId,parentId) {

	var getText = "";  
	var str="";	
	$(parentSubSId).each(function(mainindex,mainele) {   // Sub Child Iteration

		var pid = $("#"+parentId).attr('id').replace("treeDemo_","");
		var id = $(this).attr('id').replace("treeDemo_","");
		
		var parentSubSId = $(this).attr('id');
		
		var span2Class = "#"+parentSubSId+" > span#"+parentSubSId+"_check"; 
	 
		getText = getCheckVal(this); 
		str+='{ "id":"'+id+'", "pId":"'+pid+'",  "name":"'+getText+'" , "checked":"'+checkChecked($(span2Class).attr('class'))+'" , "open":"true", "title":"","chkDisabled":"false"},';

		var getUlList = "#"+parentSubSId +' > ul > li';
		 
		if($(getUlList).length > 0) {
			str+=constructStringSubSub(getUlList); 
		} else return;
	 
	});  
	 
	return str;
}

//<!--
var setting = {
	check: {
		enable: true
	},
	data: {
		key: {
			title: "title"
		},
		simpleData: {
			enable: true
		}
	},
	callback: {
		onCheck: onCheck
	}
}; 
var zNodes =  ${responseJSON.user_rights};

function onCheck(e, treeId, treeNode) { 
	count();
}

function setTitle(node) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = node ? [node]:zTree.transformToArray(zTree.getNodes());
	for (var i=0, l=nodes.length; i<l; i++) {
		var n = nodes[i];
		n.title = "[" + n.id + "] isFirstNode = " + n.isFirstNode + ", isLastNode = " + n.isLastNode;
		//zTree.updateNode(n);
	}
}
function count() {
	function isForceHidden(node) {
		if (!node.parentTId) return false;
		var p = node.getParentNode();
		return !!p.isHidden ? true : isForceHidden(p);
	}
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	checkCount = zTree.getCheckedNodes(true).length,
	nocheckCount = zTree.getCheckedNodes(false).length,
	hiddenNodes = zTree.getNodesByParam("isHidden", true),
	hiddenCount = hiddenNodes.length;

	for (var i=0, j=hiddenNodes.length; i<j; i++) {
		var n = hiddenNodes[i];
		if (isForceHidden(n)) {
			hiddenCount -= 1;
		} else if (n.isParent) {
			hiddenCount += zTree.transformToArray(n.children).length;
		}
	}

	$("#isHiddenCount").text(hiddenNodes.length);
	$("#hiddenCount").text(hiddenCount);
	$("#checkCount").text(checkCount);
	$("#nocheckCount").text(nocheckCount);
	checBoxCnt = checkCount;
}
function showNodes() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getNodesByParam("isHidden", true);
	zTree.showNodes(nodes);
	setTitle();
	count();
}
function hideNodes() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getSelectedNodes();
	if (nodes.length == 0) {
		alert("Please select one node at least.");
		return;
	}
	zTree.hideNodes(nodes);
	setTitle();
	count();
} 

var validCharactersRegex = new RegExp(/^[A-Z0-9]$/); 
var groupIDInvalid = function(value) {
	return validCharactersRegex.test(value) 
		&& value.indexOf('  ') == -1 
		&& value.indexOf('--') == -1 
		&& value.indexOf(' -') == -1 
		&& value.indexOf('- ') == -1;
}; 

$.validator.addMethod("regex", function (value, element, regexpr) {
	 return regexpr.test(value);
}, "");    


var intermediaryRules = {
	rules : {
		groupID : { required : true,regex: /^[a-zA-Z0-9]+$/, minlength: 5},
			//groupID: { required : true } ,
		groupDesc : { required : true } ,
		groupType : { required : true } ,
		userlevel : { required : true }
	},		
	messages : {
		groupID : { 
					required : "Please enter Group Id.",
					regex : "Group Id should not contain any special characters.",  
					minlength : "Please enter atleast 5 characters for group id."
				  }, 
		groupDesc : { 
						required : "Please enter Group Description."
					},
		groupType : { 
						required : "Please select Group Type."
					},
		userlevel : { 
						required : "Please select User Level."
			  }
	}
	

};


$(document).ready(function() {

	/* $('#groupID').live('keyup',function(){
		 var id = $(this).attr('id');
		 var v_val = $(this).val();
		 
		 $("#"+id).val(v_val.toUpperCase());
	}); */
	
	
	
	
	 $.getJSON('FillUsergroup.action', '',function(data) {
	
			var json = data.responseJSON.USER_LEVEL_LIST;
			
			$('#userlevel').find('option:not(:first)').remove();
			$('#userlevel').trigger("liszt:updated");
			$.each(json, function(i, v) {
				var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);  
				$('#groupType').append(options);
				$('#groupType').trigger("liszt:updated");
			});
		});
	
	
	
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	$("#hideNodesBtn").bind("click", {type:"rename"}, hideNodes);
	$("#showNodesBtn").bind("click", {type:"icon"}, showNodes);
	setTitle();
	count();
	
	$('#btn-submit').live('click',function() { 
		$('#spn_error').text('');
		var parentId = '#treeDemo > li'; 
		var getBuildStr = constructString(parentId); 
		getBuildStr = getBuildStr.substr(0,getBuildStr.lastIndexOf(",")); 
		$('#userRights').val("["+getBuildStr+"]");
		 
		var checkedParentStringConstruction = '{ "user_details" : [' + getBuildStr+ ']}'; 
		
		$('#jsonVal').val(checkedParentStringConstruction);
		$('#keyVal').val("user_details");  
		
		console.log(checBoxCnt);
		$("#form1").validate(intermediaryRules);
		
		var entCount = 0;
		
		if($("#form1").valid()) { 
			if(checBoxCnt > 0) {
				var queryString = "entity=${loginEntity}&method=searchEntity&groupId="+$('#groupID').val();	
				$.getJSON("postJson.action", queryString,function(data) {  
					
					entCount = data.finalCount;  
					if(entCount > 0) {
						$('#groupID').addClass("error");
						$('#groupID').after('<label for="groupID" generated="true" id="error_grp_id" class="error">Group Id Exists. Please input another one.</label>');
						
					} else {
						flag = true; 
					var url="<%=ctxstr%>/<%=appName %>/confirmGroupDetails.action";
						$("#form1")[0].action=url;
						$("#form1").submit();  
					}						
				});
			} else {
				$('#spn_error').text('Please assign rights to the entered group.');
				//alert('Please assign rights to the entered group.');
			}
			
		} else {
			return false;
		}				
		
	});
	
	
	$('#btn-cancel').live('click',function() {  
		$("#form1")[0].action="<%=ctxstr%>/<%=appName %>/userGrpCreation.action";
		$("#form1").submit();	
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
	
});	

$('#groupType').live('change', function () {
	
	var groupType=$('#groupType').val();
	var grouplevel="";
	
	
	var formInput='groupType='+groupType;
	
	 $.getJSON('FillUserLevelAct.action', formInput,function(data) {
	
			var json = data.responseJSON.USER_LEVEL_LIST;
			
			$('#userlevel').find('option:not(:first)').remove();
			$('#userlevel').trigger("liszt:updated");
			$.each(json, function(i, v) {
				var options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);  
				$('#userlevel').append(options);
				$('#userlevel').trigger("liszt:updated");
			});
		});
	 
	
	
});


</SCRIPT> 

	 
		
</head>

<body>
	<form name="form1" id="form1" method="post">
	 
			<div id="content" class="span10"> 
			
			 
             			<!-- content starts -->
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="userGrpCreation.action">User Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">New Group Creation </a></li>
					</ul>
				</div>  
		<div class="row-fluid sortable">

			<div class="box span12">

				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Group Definition
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

					</div>
				</div>
						
				<div class="box-content">
					<fieldset> 
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable "> 
								<tr>
									<td width="15%"><strong><label for="Group Id">Group Id<font color="red">*</font></label></strong></td>
									<td width="35%"><input name="groupID" id="groupID" class="field"  required="true"  type="text"  maxlength="10"  /></td>
									<td width="25%"><strong><label for="Group Description">Group Description<font color="red">*</font></label></strong></td>
									<td width="25%"><input name="groupDesc" type="text" class="field"  required="true" id="groupDesc"    /></td>	
								</tr>
								<tr>
									<td width="15%"><strong><label for="Group Type">Admin Type<font color="red">*</font></label></strong></td>
									<td width="35%" >
											<select id="groupType" name="groupType"  required='true' data-placeholder="Choose member type..." 
												class="chosen-select" style="width: 220px;" >
												<option value="">Select</option>
												</select>
											
									</td>
									<td width="25%"><strong><label for="User Level">User Level<font color="red">*</font></label></strong></td>
									<td width="25%" >
											<select id="userlevel" name="userlevel"  required='true' data-placeholder="Choose member type..." 
												class="chosen-select" style="width: 220px;" >
													<option value="">Select</option>
											</select>
											
									</td> 
									
									<td></td>
									<td></td>
								</tr>
						</table>  
					</fieldset>   
				</div>
			</div>
		</div>
					
		<div class="row-fluid sortable">

			<div class="box span12">

				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Assign Rights To Group
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i
							class="icon-cog"></i></a> <a href="#"
							class="btn btn-minimize btn-round"><i
							class="icon-chevron-up"></i></a>

					</div>
				</div>
						
		<div class="box-content">
		 <fieldset>

				  
		 
		 <table  width="950" border="0" cellpadding="5" cellspacing="1"  >
				<tr class="odd">
					<td width="20%">&nbsp;</td>
				 
					<td width="80%"><div class="zTreeDemoBackground left">
						<ul id="treeDemo" class="ztree"></ul> 
					</div> 
					</td>
			 	</tr>
			</table> 
		 
		</fieldset> 
			
			 
		   <div class="form-actions">
				<input type="button" name="btn-submit" class="btn btn-success" id="btn-submit" value="Submit" />
				<!-- <input type="button" name="submit" class="btn btn-primary" id="submitDat" value="Schedule Reports"/> -->
				<input type="button" name="btn-cancel" class="btn btn-warning" id="btn-cancel" value="Cancel" /> 
				<input type="hidden" name="jsonVal"  id="jsonVal" value="" />
				<input type="hidden" name="keyVal"  id="keyVal" value="" />
				<input type="hidden" name="userRights"  id="userRights" value="" />
				<span class="errors" id="spn_error"></span>
			  </div>

		</div>
		</div>
		</div>
                 
              <!--/row--><!--/row-->
              
              						<!-- content ends -->
			</div><!--/#content.span10-->
		 
 </form>
 
</body>
</html>

