/**

@Author : Sravana Kumar B
@Date 	: 05-02-2015
@Description : This one bulid final request json value

**/

/**

text		:	For Getting All Text Box Value
textarea	:	For Getting All Textarea Value
radio		:	For Getting radio Value			(for this we have to use name for other things we need to use id)
checkbox	:	For Getting checkbox Value		(for this we have to use name for other things we need to use id)
selecttext	:	For Getting Select Box Text Value 
selectvalue	:	For Getting Select Box value
multiselect	:	For Getting Multiple Select Box value

Example :
Sample Json :

	var str="Insurance|text#CorporateNameId|text#ProposalRemarks|textarea#policyNumber|select#newsletter|checkbox#sravan|radio#sravan2|select";
	var mydata = '{"Insurance":"insurencename data","CorporateNameId":"Corporatenameid data","policyNumber":[{"sravan1":"sravan1"},{"sravan2":"sravan2"}],"ProposalRemarks":"ProposalRemarks data","yes":true,"Daily":true,"Weekly":true,"sravan2":[{"sravan1":"sravan1"},{"sravan2":"sravan2"}]}';
	var finalobj = jQuery.parseJSON(mydata);
	
**/


 function buildSingleRequestjson(finalstr)
 {
		//console.log("Final String ["+finalstr+"]");
		var arr = finalstr.split('#');
		var len = arr.length;
		try {
		
		item = {};
		
		//console.log("len >>>>>>["+len+"]");
		
		$.each(arr,function(index,key){
				
			//console.log(key);
			
			var data = key.split('|');
			var len = data.length;
			
			//console.log("length ["+len+"]");
			
			if(len==2)
			{
				var fieldid=data[0];
				var fieldtype=data[1];
			
				//console.log(fieldid+"====="+fieldtype);
				
				if(fieldtype=='text'){
						item [fieldid] = $("#"+fieldid).val();
				}
				else if(fieldtype=='textarea'){
						item [fieldid] = $("#"+fieldid).val();
				}
				else if(fieldtype=='radio'){
						item [fieldid] = $("input[name="+fieldid+"]:checked").val(); 
				}
				else if(fieldtype=='checkbox'){
						
						jsonObj = [];
						$('input[name='+fieldid+']:checked').each(function() {
								var myvalue= this.value;
								jsonObj.push(myvalue);
						});
						
						item [fieldid] = jsonObj;
						
				}
				else if(fieldtype=='selecttext')
				{
					item [fieldid] =$( "#"+fieldid+" option:selected" ).text();
				}
				else if(fieldtype=='selectvalue'){
						item [fieldid] =$( "#"+fieldid).val();
				}
				else if(fieldtype=='multiselectval'){

						var multilselval = [];
						$.each($("#"+fieldid+" option:selected"), function(){  
						
							multilselval.push($(this).val());
							
						});
						item [fieldid] = multilselval;
		
				}
				else if(fieldtype=='multiselecttext'){

						var multilselval = [];
						$.each($("#"+fieldid+" option:selected"), function(){ 
						
							multilselval.push($(this).text());
							
						});
						item [fieldid] = multilselval;
		
				}
				else
				{
					console.log("Final Else");
				}
				
			}
			
		});
		
		var finaldata = JSON.stringify(item);
		
		console.log("finaldata > ["+finaldata+"]");
		
		} catch (e) {
			console.log("Invalied Json Object>>>>"+e);
		}
		
		return finaldata;
		
 }
	

function fillsingledata(finalstr,jsonObject)
{

		var arr = finalstr.split('#');
		var len = arr.length;
		
		try {
		
			$.each(arr,function(index,key){
			
				var data = key.split('|');
				var len = data.length;
				
				//console.log("length ["+len+"]");
				
				if(len==2)
				{
					var fieldid=data[0];
					var fieldtype=data[1];
					
					//console.log(fieldid+"====="+fieldtype);
				
					if(fieldtype=='html'){
						
							var myval=read_prop(jsonObject,fieldid); 
							myval=(myval==null)?"":myval;
							$("#"+fieldid).html(myval);
							
					}else if(fieldtype=='text'){
						
							var myval=read_prop(jsonObject,fieldid); 
							myval=(myval==null)?"":myval;
							$("#"+fieldid).val(myval);
					} else if(fieldtype=='table'){
						
						var myval=read_prop(jsonObject,fieldid); 
						myval=(myval==null)?"":myval;
						$("#"+fieldid).text(myval);
					}
					else if(fieldtype=='textarea'){
							var myval=read_prop(jsonObject,fieldid); 
							myval=(myval==null)?"":myval;
							$("#"+fieldid).val(myval);
					}
					else if(fieldtype=='radio'){
							
							$('input[name='+fieldid+']').each(function() {
	
								var myvalue= this.value;
								var myid = this.id;
								var val=read_prop(jsonObject,myid); 
								
								if(val)
									$('#'+myid).attr('checked',val);
							
							//console.log("my value ["+myvalue+"] myid ["+myid+"] val ["+val+"]");
					
							});
							
					}
					else if(fieldtype=='checkbox'){
							
							$('input[name='+fieldid+']').each(function() {
	
								var myvalue= this.value;
								var myid = this.id;
								var val=read_prop(jsonObject,myid); 
								
								if(val)
									$('#'+myid).attr('checked',val);
							
							//console.log("my value ["+myvalue+"] myid ["+myid+"] val ["+val+"]");
					
							});
							
					}
					else if(fieldtype=='select')
					{
						
						$('#'+fieldid).empty();
						$('#'+fieldid).append(getOptionFor('','Select'));
						var myval=read_prop(jsonObject,fieldid); 
						myval=(myval==null)?"":myval;
						fillselectbox(myval,fieldid);

					}
					else
					{
						console.log("Final Else");
					}
				}
				
			
			});
		
		} catch (e) {
		
			console.log("Invalied Json Object>>>>"+e);
			
		}

}


function read_prop(jsonObject, prop) 
{

	if(jsonObject.hasOwnProperty(prop)) 
	{
		return jsonObject[prop];
	}else
	{
		return "";
	}
	
}

function getOptionFor(mytext,myvalue)
{
		return "<option value='"+mytext+"'>"+myvalue+"</option>";
}

function fillselectbox(jsonArray,selectboxid)
{

	$.each(jsonArray, function(index,jsonObject){
	
		$.each(jsonObject,function(mykey,myvalue){
		
			console.log(mykey+"===="+myvalue);
			$('#'+selectboxid).append(getOptionFor(mykey,myvalue));
		
		});

	});

}


/**

	Sravan Newly Added
	Date : 23-10-2015
	Purpose : Json tag name and id name are diffrent the it wil handle

**/


function fillsingledatawithdiffid(finalstr,jsonObject)
{

		console.log("Welcome to program");
	
		var arr = finalstr.split('#');
		var len = arr.length;
		
		
		try {
		
			$.each(arr,function(index,key){
			
				var data = key.split('|');
				var len = data.length;
				
				//console.log("key ["+key+"]");
				
				//console.log("length ["+len+"]");
				
				if(len==3)
				{
					var fieldid=data[0];
					var jsonfiledid = data[1];
					var fieldtype=data[2];
					
					//console.log(fieldid+"====="+fieldtype);
				
					if(fieldtype=='html'){
						
							var myval=read_prop(jsonObject,jsonfiledid); 
							myval=(myval==null)?"":myval;
							$("#"+fieldid).html(myval);
							
					}
					/**else if(fieldtype=='text'){
						
							var myval=read_prop(jsonObject,fieldid); 
							myval=(myval==null)?"":myval;
							$("#"+fieldid).val(myval);
					} else if(fieldtype=='table'){
						
						var myval=read_prop(jsonObject,fieldid); 
						myval=(myval==null)?"":myval;
						$("#"+fieldid).text(myval);
					}
					else if(fieldtype=='textarea'){
							var myval=read_prop(jsonObject,fieldid); 
							myval=(myval==null)?"":myval;
							$("#"+fieldid).val(myval);
					}
					else if(fieldtype=='radio'){
							
							$('input[name='+fieldid+']').each(function() {
	
								var myvalue= this.value;
								var myid = this.id;
								var val=read_prop(jsonObject,myid); 
								
								if(val)
									$('#'+myid).attr('checked',val);
							
							//console.log("my value ["+myvalue+"] myid ["+myid+"] val ["+val+"]");
					
							});
							
					}
					else if(fieldtype=='checkbox'){
							
							$('input[name='+fieldid+']').each(function() {
	
								var myvalue= this.value;
								var myid = this.id;
								var val=read_prop(jsonObject,myid); 
								
								if(val)
									$('#'+myid).attr('checked',val);
							
							//console.log("my value ["+myvalue+"] myid ["+myid+"] val ["+val+"]");
					
							});
							
					}
					else if(fieldtype=='select')
					{
						
						$('#'+fieldid).empty();
						$('#'+fieldid).append(getOptionFor('','Select'));
						var myval=read_prop(jsonObject,fieldid); 
						myval=(myval==null)?"":myval;
						fillselectbox(myval,fieldid);

					} **/
					else
					{
						console.log("Final Else");
					}
				}
				
			
			});
		
		} catch (e) {
		
			console.log("Invalied Json Object>>>>"+e);
			
		}

		
		

}
