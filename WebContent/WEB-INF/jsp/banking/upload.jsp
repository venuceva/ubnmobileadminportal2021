<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Struts2 File Upload Demo</title>
</head>
<body>
	<center>
		<h1>File Upload with Struts 2</h1>
		<s:form action="uploadFile" enctype="multipart/form-data" method="post">
			<s:file name="fileUpload" label="Select a file to upload" size="30"/>
			<br/>
			<s:submit value="Upload" />
		</s:form>
	</center>
</body>
</html>