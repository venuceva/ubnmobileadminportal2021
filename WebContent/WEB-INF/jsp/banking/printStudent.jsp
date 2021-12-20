<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${pageContext.request.contextPath}/css/agency-app.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#cancle').on('click',function(){
		window.close();
	});
	$('#print').on('click',function(){
			var queryString="studentId="+$('#studentId').val();
			$.getJSON("updateCardStatus.action", queryString,function(data) {
				var json=data.responseJSON;
				try{
					var msg = json.message;
					if(msg = "SUCCESS"){
						window.print();
					}
				}catch(e){
					alert('Error..!Please Try Again');
				}
			});
	});
});
</script>
<style type="text/css">
.decleration {
	font-size: 2em;
	font-family: thoma;
	margin-top: 1em;
	margin-left: 1em;
}

tr {
	height: 20px;
}
</style>
</head>
<body style="overflow: scroll;">
	<table width="100%" >
		<tr>
			<td style="float: left;">
				<div class="decleration">Student Account Confirmation Form</div>
			</td>
			<td style="float: right;">
				<div class="photo" >
					 <img src="<%=request.getContextPath()%>/jsp/viewImage.jsp?studentId=<s:property value="studentId" />"/>
					<%-- <img src='<s:url action="authStudentphoto.action" />' style="width: 200px; height: 300px;" /> --%>
				</div>
			</td>
		</tr>
	</table>
	<table width="60%" style="background : #FFFFFF; float:left;">
	  <%-- <tr>
	  	 <td width="30%"><label><Strong>Card Number</Strong></label></td><td width="70%"><Strong><s:property value="cardNumber" /></Strong></td>
	  </tr> --%>
	  <tr>
	  	 <td width="50%"><label><Strong>Student Id : </Strong></label></td><td width="50%"><s:property value="studentId" /></td>
	  </tr>
	  <tr>
	  	 <td width="50%"><label><Strong>Student Name : </Strong></label></td><td width="50%"><s:property value="salutationname" /> &nbsp;<s:property value="fullName" /></td>
	  </tr>
	  <tr>
	  	 <td width="50%"><label><Strong>ID Number/Passport : </Strong></label></td><td width="50%"><s:property value="idnumber" /> / <s:property value="passportnumber" /></td>
	  </tr>
	   <tr>
	  	 <td width="50%"><label><Strong>Gender Name : </Strong></label></td><td width="50%"><s:property value="gendername" /></td>
	  </tr>
	  <tr>
	  	<td width="50%"><label><Strong>Study Method Name : </Strong></label></td><td width="50%"><s:property value="studymethodname" /></td>
	  </tr>
	   <tr>
	  	<td width="50%"><label><Strong>Cell Phone : </Strong></label></td><td width="50%"><s:property value="cellphone" /></td>
	  </tr>
	  <tr>
	  	 <td width="50%"><label><Strong>Email : </Strong></label></td><td width="50%"><s:property value="email" /></td>
	  </tr>
	  <tr>
	  	 <td width="50%"><label><Strong>Campus Name : </Strong></label></td><td width="50%"><s:property value="campusname" /></td>
	  </tr>
	  <tr>
	  	 <td width="50%"><label><Strong>Intake Description : </Strong></label></td><td width="50%"><s:property value="intakedesc" /></td>
	  </tr>
	  <%-- <tr>
	  	 <td width="50%"><label><Strong>Intake Desc</Strong></label></td><td width="50%"><s:property value="intakedesc" /></td>
	  </tr> --%>
	  <tr>
	  	 <td width="50%"><label><Strong>Product Description : </Strong></label></td><td width="50%"><s:property value="prdesc" /></td>
	  </tr>
	  <tr>
	  	 <td width="50%"><label><Strong>Post Address : </Strong></label></td><td width="50%"><s:property value="postadd" /></td>
	  </tr>
	   <tr>
	  	 <td width="50%"><label><Strong>Town Name : </Strong></label></td><td width="50%"><s:property value="townname" /></td>
	  </tr>
	   <tr>
	  	 <td width="50%"><label><Strong>Nationality : </Strong></label></td><td width="50%"><s:property value="nationality" /></td>
	  </tr>
	  <tr>
	 	 <td></td>
	  	 <td width="50%" style="float:right;"><label><Strong>Signature</Strong></label>
		  	 <div style="float:right;">
		  	 	<Textarea id="signature"></Textarea>
		  	 </div>
		 </td>
	  </tr>
	  <tr>
	  	<td colspan="10" style="text-align: center;">
	  	<input type="button" name="print" id="print" value="Print" />
	  	<input type="button" name="cancle" id="cancle" value="Cancel"/>
	  	<input type="hidden" name="studentId" id="studentId" value='<s:property value="studentId" />'>
	  	</td>
	  </tr>
  </table>
</body>
</html>