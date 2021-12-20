<%@page import="com.ceva.util.DBUtils"%>
<%@page import="com.ceva.base.common.utils.DBConnector"%>
<%@ page import = "java.io.*, java.sql.*" %>

<%

  int iNumPhoto ;
  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  Blob blob = null;
  String studentID=request.getParameter("studentId");
    byte[] imgData = null;
    try
    {
       String fileName = request.getParameter("image");             
         FileInputStream fis = new FileInputStream(new File("D:\\"+fileName));
         BufferedInputStream bis = new BufferedInputStream(fis);             
         
		 response.setContentType("image/gif");
         BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());
         for (int data; (data = bis.read()) > -1;) {
           output.write(data);
         }  
	   output.flush();
       output.close();
	
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
     DBUtils.closeConnection(conn);
     conn = null;
    }
%>