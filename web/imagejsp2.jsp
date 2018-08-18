  <%@page import="model.receiver"%>
<%@page import="dataSource.connector"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
          pageEncoding="ISO-8859-1"%>
           <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
     </head>
    <body>
     <%@ page import="java.io.*"%>
 <%@ page import="java.sql.*"%>
 <%@ page import="java.util.*"%>
  <%@ page import="java.text.*"%>
 <%@ page import="javax.servlet.*"%>
 <%@ page import="javax.servlet.http.*"%>
 <%@ page import="javax.servlet.http.HttpSession"%>
 <%@ page language="java"%>

 <%
   try{
    //PrintWriter out=response.getWriter();

       Connection con = null;
  con=connector.getConnect();
        Statement st = con.createStatement();
      receiver dao=new receiver();
            List<receiver> lst = new ArrayList<receiver>();
         int id=Integer.parseInt(request.getParameter("imageid"));
        
             PreparedStatement pre1 = con.prepareStatement("select image from table1 where index_id=?");
             pre1.setInt(1,id);
   ResultSet rs1=pre1.executeQuery();
    rs1.next();   
 byte[] bytearray1 = new byte[4096];  
           int size1=0;  
          InputStream sImage1;  
            sImage1 = rs1.getBinaryStream(1);  
            response.reset();  
           response.setContentType("image/png");  
           response.addHeader("Content-Disposition","filename=logo.png");  
           while((size1=sImage1.read(bytearray1))!= -1 )  
             {  
               response.getOutputStream().write(bytearray1,0,size1);  
               
             }  
           response.flushBuffer(); 
          sImage1.close();  
           rs1.close();  
    pre1.close();
  con.close();  
   }
 catch (Exception e){
         out.println(e);
        }
finally 
                     {
out.close();}
  %>
  %>
  </body>
    </html>