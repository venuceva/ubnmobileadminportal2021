
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


  #sortable1, #sortable2,#sortable3 {
    border: 1px solid #eee;
    width: 300px;
    height: 300px;
    list-style-type: none;
    margin: 0;
    padding: 5px 0 0 0;
    float: center;
    margin-right: 10px;
    page-align:center;
    text-align:left;
    overflow-y:auto;
    /*overflow-x:hidden;*/  
 
  }
  #sortable1 li, #sortable2 li, #sortable3 li {
    margin: 0 5px 5px 5px;
    padding: 5px;
    font-size: 1 em;
    width: 200px;
  }

</style>
<script type="text/javascript" >
$(document).ready(function() {
	
	
	$('#servicelist').on('change', function (e) {
		
		
	    var optionSelected1 = $("option:selected", this);
	    var valueSelected1 = this.value;
	    $("#prddesc").val(valueSelected1);

			$("#tbody_data").empty();
			$("#tbody_data1").empty();
			
			$("#sortable1").empty();
			$("#sortable2").empty();
			$("#sortable3").empty();
			
			var queryString91 = "method=fetchSpackDetails&prdid="+valueSelected1;
			$.getJSON("postJson.action", queryString91,function(data) {				
										
					var json1 =data.responseJSON ;
					
					var json2 = json1.SERVICEPACKS;
					
					val = 1;
					rowindex = 1;
					colindex = 0;
					var str2 = "MENULIST";
					
					
					
					var arrayData = {'sortable1' : json1.SERVICEPACKS ,'sortable3' : json1.MENULIST ,'sortable2' : json1.SELECTEDLIST };
					var userdata = "";
					 
						$.each(arrayData, function(selectKey, arrvalue ){
							var json = arrvalue;
							var options = '';
							var options1 = '';
						
							 $.each(json, function(i, v) {
								 console.log("aaa "+v);
									if(selectKey == 'sortable3') { 		
										options = $('<li/>', {id:v.key}); 
										$('#'+selectKey).append(options);
										console.log("in log "+v.key +" value"+v.val);
										$("ul li#"+v.key).append('<a id='+v.key+'>' + v.val + '</a>');
								}
									else if(selectKey == 'sortable2') {
										
										if(v.key.indexOf(str2) != -1){
											options = $('<li/>', {id: v.key});
										}else{
											
											options = $('<li/>', {id: v.key, text: v.val});
										}
										
										$('#'+selectKey).append(options);

										if(v.key.indexOf(str2) != -1){
											alert(v.key+"      " +v.val);
										    $("ul li#"+v.key).append('<a id='+v.key+'>' + v.val + '</a>');
										}
									}
									else {
										options = $('<li/>', {id: v.key, text: v.val});
								 		$('#'+selectKey).append(options);
									}
									
							 	});
							 
							});
					
					
				/* 	
					bankfinalDatarows = "";
					eachfieldArr = "";
					appendTxt = "";
					
					if(bankfinalData != "") {
						var bankfinalDatarows = bankfinalData.split("#");
						
						if(val % 2 == 0 ) {
							addclass = "even"; 
						} else {
							addclass = "odd"; 
						}   
						val++; 
						for(var i=0;i<bankfinalDatarows.length;i++){
							  eachrow=bankfinalDatarows[i];
							  eachfieldArr=eachrow.split(","); 
							  appendTxt = "<tr class="+addclass+" index='"+rowindex+"'> "+
							  	"<td>"+rowindex+"</td>"+
								"<td>"+eachfieldArr[0]+" </td>"+ 
								"<td>"+eachfieldArr[1]+" </td>"+ 
								"</tr>";
								$("#tbody_data").append(appendTxt);	  
							rowindex = ++rowindex;
							colindex = ++ colindex; 
						} 
					}*/
					
			}); 
	 
	});
	
	
	
	$('#menucode').on('keyup', function() {
		$("#menucode").val($(this).val().toUpperCase());
	});
	
	 $( "#dialog-message" ).dialog({
	    	autoOpen: false, 
	    	width : 500 ,
	    	show: {
	            effect: "blind",
	            duration: 500
	          },
	          hide: {
	            effect: "explode",
	            duration: 500
	          },
	        modal: true,
	        buttons: {
	          Ok: function() {
	            $( this ).dialog( "close" );
	          }
	        }
	      });
	    
	 
	
	function undefinedcheck(data){
		return data == undefined  ? " " :  data;
	}
	
	
	

	

		$(document).on('click',"a",function(event) {
			 var id=$(this).attr('id'); 
			 var menucode=id.split('--');
			 var datato = "";
			 
					   	var queryString91 = "method=fetchMenuDetails&menucode="+menucode[1];
						$.getJSON("postJson.action", queryString91,function(data) {		
							
						
							$('#DataTables-tbody').empty();
							var json1 =data.responseJSON;
							
							$('#DataTables_Table_11').html('');
							var req = undefinedcheck(json1.servicepackdet).split("#");
							 
							if(req.length > 0){
								for(var index=0;index<req.length;index++){
									var dat1 = " ";
									var dat2 = " ";
									var dat3 = " ";
									try {
										  dat1 = req[index].split(",")[0];
										  dat2 = req[index].split(",")[1];
										  dat3 = req[index].split(",")[2];
									}catch(e){
										
									}
			 		   				datato+="<tr><td> "+dat1+"</td>  ";
			 						datato+="<td> "+dat2+"</td><td> "+dat3+"</td> </tr> ";
								} 
							}
							
							
							$('#DataTables-tbody').append(datato);
							
							$( "#dialog-message" ).dialog( "open" );
							console.log("final table created "+datato);
			  		});  
		 });

		
	 	 
		var textObject = {	
			delay : 300,
			effect : 'replace',
			classColour : 'blue',
			flash : function(obj, effect, delay) {
				if (obj.length > 0) {
					if (obj.length > 1) {
						jQuery.each(obj, function() {
							effect = effect || textObject.effect;
							delay = delay || textObject.delay;
							textObject.flashExe($(this), effect, delay);					
						});
					} else {
						effect = effect || textObject.effect;
						delay = delay || textObject.delay;
						textObject.flashExe(obj, effect, delay);
					}
				}
			},
			flashExe : function(obj, effect, delay) {
				var flash = setTimeout(function() {
					switch(effect) {
						case 'replace':
						obj.toggle();
						break;
						case 'colour':
						obj.toggleClass(textObject.classColour);
						break;
					}
					textObject.flashExe(obj, effect, delay);
				}, delay);
			}
		}; 

    $( "#sortable1, #sortable2, #sortable3" ).sortable({
      connectWith: ".connectedSortable"
    }).disableSelection();

  
  
});


var form1Rules = {
		   rules : {
			   menuname : { required : true},
			   dispname : { required : true},
			   menucode : { required : true,minlength: 6},
			   menulevel : { required : true}
		   },  
		   messages : {
			   menuname : { 
			   		required : "Please enter Menu Name."
				},
				dispname : { 
			   		required : "Please enter Display Name."
				},
				menucode : { 
			   		required : "Please Menu Code"
				},
				menulevel : { 
			   		required : "Please Select Menu Level."
				}
		   } 
		 };


function listfwd(){
	
	var vall='';
	var txt='';
	$('#sortable2 > li').each(function(i){ 
		console.log("Values "+$(this).attr("id"));
		vall+=$(this).attr("id")+",";
		txt+=$(this).text() +" %";
		$("#seltext").val(txt);
		$("#selval").val(vall);
		
		});
	
	if ($('#selval').val().length > 0)
			{
	//var queryString = "method=verifyMenucode&mobile="+ $('#menucode').val();	
	//$.getJSON("postJson.action", queryString,function(data) { 
		
		//var menuc=$('#menucode').val();
		//v_message = data.finalCount;
		//console.log("v_message  "+v_message);
		//if (v_message>0)
			//{
			// $('#error_dlno').text('Menu Code Already Exists in DB.Please use another'); 
			//}
		//else{
	
	$("#form1").validate(form1Rules);
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/editservicepackcnf.action';
	$("#form1").submit();
	return true;
		}
	//});
		//	}
		else
			{
			$('#error_dlno').text('Please Add Services'); 
			}
}


</script>
</head>


<body>
<form name="form1" id="form1" method="post" action="">
		<div id="content" class="span10">
			<div>
				<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider">&gt;&gt; </span></li>
					<li><a href="userGrpCreation.action">Product Management</a> <span class="divider">&gt;&gt; </span></li>
					<li><a href="#">Add Service Pack</a></li>
				</ul>
			</div>
			
			<table>
					<tr>
						<td colspan="3">
							<div class="messages" id="messages"><s:actionmessage /></div>
							<div class="errors" id="errors"><s:actionerror /></div>
						</td>
					</tr>
				</table>

			<div class="row-fluid sortable">

				<div class="box span12">

					<div class="box-header well" data-original-title>
						<i class="icon-edit"></i>Edit Service Pack
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
									<td width="20%"><label for="Menu Name"><strong>Select Service Pack</strong></label></td>
									<td width="30%">
									<s:select 
										style="width: 220px;"
										headerKey="" 
										headerValue="Select"
										list="responseJSON.SERVICELIST" 
										name="servicelist" 
	 									id="servicelist" 
	 									value="institute" 
										requiredLabel="true" 
										theme="simple"
										cssClass="chosen-select"
										data-placeholder="Choose Core Banking..." 
	 									 />  &nbsp; <label id="institute-id" class="errors" ></label>
	 									 </td>
									<td></td>
									<td>								
									<input name="seltext" type="hidden" id="seltext" class="field"/>
									<input name="prddesc" type="hidden" id="prddesc" class="field"/>
									<input name="selval" type="hidden" id="selval" class="field"/></td>
								</tr>
							</table>
						</fieldset>
						</div>

						<input type="hidden" name="selectUsers" id="selectUsers"></input>
					</div>
				</div>

			 <div id="userDetails">
							<table width="950" border="0" cellpadding="5" cellspacing="1"
								class="table table-striped table-bordered bootstrap-datatable ">
									<tr >
								<td ><strong><div align="center">List Of Services</div></strong></td>
								<td><strong><div align="center">Selected Services</div></strong></td>
								<td><strong><div align="center">Menu Lists</div></strong></td>
								
								</tr>
								<tr class="even">
									<td><div align="center"><ul id="sortable1" class="connectedSortable"></ul></div> </td>
									<td><div align="center"> <ul id="sortable2" class="connectedSortable"></ul></div> </td>
									<td><div align="center"> <ul id="sortable3" class="connectedSortable"></ul></div> </td>
								</tr>
								
							</table>
							</br>
				</div>
				
	<div id="dialog-message" title="Details">
	  <table   class="table table-striped table-bordered bootstrap-datatable " 	id="DataTables_Table_11">
	   	 
		</table>
		 <table   class="table table-striped table-bordered bootstrap-datatable " id="DataTables_Table_12">
						<thead>
					<tr>
						<th>Menu Type</th>
						<th>Menu Name</th> 
						<th>Status</th> 
					</tr>
				</thead> 
				<tbody id="DataTables-tbody"></tbody>
	   	 
		</table>
		 
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