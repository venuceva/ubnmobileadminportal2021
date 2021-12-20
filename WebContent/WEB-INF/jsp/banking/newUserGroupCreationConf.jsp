
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
	 
     
    <link rel="stylesheet" href="<%=ctxstr%>/css/treenode/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=ctxstr%>/css/treenode/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=ctxstr%>/css/treenode/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=ctxstr%>/css/treenode/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=ctxstr%>/css/treenode/jquery.ztree.exhide-3.5.js"></script>
	<SCRIPT type="text/javascript">
	
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
			
 			checkedStringConstruction+='{ "id":"'+id+'", "pId":"'+pid+'",  "name":"'+getText+'" , "checked":"'+checkChecked($(span2Class).attr('class'))+'" , "open":"true", "title":"" },';
 		 
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
 			
			//var span1Class = "#"+parentSubId+" > span#"+parentSubId+"_switch";
			var span2Class = "#"+parentSubId+" > span#"+parentSubId+"_check"; 
			
			getText = getCheckVal(this); 
 			str+='{ "id":"'+id+'", "pId":"'+pid+'",  "name":"'+getText+'" , "checked":"'+checkChecked($(span2Class).attr('class'))+'" , "open":"true", "title":"" },';
	 
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
 			
			//var span1Class = "#"+parentSubSId+" > span#"+parentSubSId+"_switch";
			var span2Class = "#"+parentSubSId+" > span#"+parentSubSId+"_check";
			 
		 
			getText = getCheckVal(this); 
 			str+='{ "id":"'+id+'", "pId":"'+pid+'",  "name":"'+getText+'" , "checked":"'+checkChecked($(span2Class).attr('class'))+'" , "open":"true", "title":"" },';
	 
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
		 
		var zNodes = ${userRights}; 
		 
	 
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

			/* $("#isHiddenCount").text(hiddenNodes.length);
			$("#hiddenCount").text(hiddenCount);
			$("#checkCount").text(checkCount);
			$("#nocheckCount").text(nocheckCount); */
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

		$(document).ready(function(){
			
			var gptype=$('#groupType').val();
			var gslevel=$('#userlevel').val();
			
			$('#glevel').html(gslevel.split("-")[2]);
			$('#gtype').html(gptype.split("-")[1]); 
			
			
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$("#hideNodesBtn").bind("click", {type:"rename"}, hideNodes);
			$("#showNodesBtn").bind("click", {type:"icon"}, showNodes);
			setTitle();
			count();
			//$('#treeDemo > li').attr('disabled','disabled');
			$('#btn-submit').live('click',function() { 
				
				var parentId = '#treeDemo > li'; 
				var getBuildStr = constructString(parentId); 
				getBuildStr = getBuildStr.substr(0,getBuildStr.lastIndexOf(",")); 
				
				//var checkedParentStringConstruction = '{ "user_details" : [' + getBuildStr+ ']}'; 
				$('#userRights').val("["+getBuildStr+"]");
				//$('#jsonVal').val(checkedParentStringConstruction);
				$('#keyVal').val("user_details");
				  
				$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/saveGroupDetails.action";
				$("#form1").submit();				
			});
			
			$('#btn-cancel').live('click',function() {  
				//$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/userGrpCreationNew.action";
				$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/userGrpCreation.action";
				$("#form1").submit();				
			});
			 
		});
		//--> 
	</SCRIPT>
    
 
 
		
</head>

<body>
	<form name="form1" id="form1" method="post">
	 
			<div id="content" class="span10">

			 
             			<!-- content starts -->
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="#">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">User Management</a> <span class="divider"> &gt;&gt; </span></li>
 					  <li><a href="#">New Group Creation Confirmation </a></li>
					</ul>
				</div>  
		<div class="row-fluid sortable">

			<div class="box span12">

				<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Group Definition Confirmation
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
						 <tr>
							<td><strong><label for="Group Id">Group Id </label></strong></td>
							<td >${groupID }<input name="groupID" id="groupID" class="field"   type="hidden"  value="${groupID }" /></td>
							<td><strong><label for="Group Description">Group Name </label></strong></td>
							<td >${groupDesc }<input name="groupDesc" type="hidden" class="field" id="groupDesc"  value="${groupDesc }"  /></td>	
						</tr>
						<tr>
							<td width="15%"><strong><label for="Group Type">Admin Type</label></strong></td>
							<td width="35%" ><div id="gtype"></div> <input name="groupType" type="hidden" class="field" id="groupType"  value="${groupType }"  /></td>	
							<td width="15%"><strong><label for="User Level">User Level</label></strong></td>
							<td width="35%" ><div id="glevel"></div> <input name="userlevel" type="hidden" class="field" id="userlevel"  value="${userlevel }"  /></td>	
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
				<input type="button" name="btn-submit" class="btn btn-success" id="btn-submit" value="Confirm" />
				<input type="button" name="btn-cancel" class="btn btn-warning" id="btn-cancel" value="Cancel" />
 				<input type="hidden" name="jsonVal"  id="jsonVal" value='${jsonVal}' />
				<input type="hidden" name="keyVal"  id="keyVal" value="" />
				<input type="hidden" name="userRights"  id="userRights" value="" />
				<input type="hidden" name="entity"  id="entity" value="${entity}" />
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

