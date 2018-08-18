/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dataSource.connector;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HaarTestWithSampleValues;
import model.pixel;
import model.receiver;
import model.triple_a;

/**
 *
 * @author vrg
 */
public class decryptservlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet decryptservlet</title>");  
            out.println("<link href=decryptservlet.css rel=stylesheet type=text/css>");
            out.println("</head>");
            out.println("<body style=background-color:#BC8F8F>");
                 out.println("<div id=watermark>");
 out.println("<p>Image Steganography</p>");
 out.println("</div>");    
  out.println ("<form action=validateservlet method=get>");
    out.println ("<nav class=navbar navbar-default>");
    out.println ("<div class=container-fluid>");
      out.println ("<div class=navbar-header>");
	
           out.println (" <input type=submit name=logout value=logout class=button12 button4 >");
      out.println ("    </div>");       
     

    
        out.println ("</div>");
      out.println ("</nav>"); 
            out.println("<div class=wrapper>");
            out.println("<a href=showitemservlet class=button button4>Go to decrypt mode</a>");
            out.println("<a href=encrypt.html class=button button4>Go to encrypt mode</a>"); 
            out.println("</div>");

            
            
            HttpSession session=request.getSession(false);  
    if( session!=null)
          {
              String username=   (String)session.getAttribute("name");
    String password=(String)session.getAttribute("password");
    
    System.out.println(password+"password"  + username);       
    //Set s=new HashSet();
            receiver st=new receiver();
            String key,algo,decoded;
            
            int id=Integer.parseInt(request.getParameter("txtid"));
             System.out.println(id);
key=request.getParameter("key");
System.out.println(key);

 Connection con=connector.getConnect();
            String s=" Select image,msglen,algo from table1 where index_id=?";
            PreparedStatement ps=con.prepareStatement(s);
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            rs.next();
   InputStream ip=      rs.getBinaryStream(1);
          int msglen=rs.getInt(2);
          algo=rs.getString(3);
          System.out.println(algo);
          System.out.println(key);
          System.out.println(id);

   BufferedImage buffer = ImageIO.read(ip);         
   if(algo.equals("pixelalgo"))
   {
   pixel pi=new pixel();

  decoded  =pi.decode(buffer,key.length(),0,key,msglen);
  System.out.println(decoded);
out.println("<div class=wrapper>");
out.println("<h2 class=h2 align=center>Decoded Message</h2>");
out.println("<textarea rows=10 cols=40 style=background:#F4A460>"+decoded+"</textarea>");


out.println("</div>");


   }
else if(algo.equals("triplealgo"))
{
triple_a ta=new triple_a();
    decoded=ta.decode(buffer,key,msglen);
  System.out.println(decoded);
out.println("<div class=wrapper>");
out.println("<h2 class=h2 align=center>Decoded Message</h2>");
out.println("<textarea rows=10 cols=40 style=background:#F4A460>"+decoded+"</textarea>");


out.println("</div>");


}
else if(algo.equals("dwt"))
{             HaarTestWithSampleValues ht=new HaarTestWithSampleValues();           
decoded=ht.decode(buffer, msglen,key);
out.println("<div class=wrapper>");
out.println("<h2 class=h2 align=center>Decoded Message</h2>");
out.println("<textarea rows=10 cols=40 style=background:#F4A460>"+decoded+"</textarea>");


out.println("</div>");

}//System.out.println(s1);

            
            }
        else
           {
               out.println("invalid username and password");
               RequestDispatcher rd=request.getRequestDispatcher("login.html");
               rd.include(request,response);
           }
        }
catch(Exception e)
{
 // System.out.println(e);

out.println("<h2 class=h2 align=center>Error Occured Please Try Again</h2>");

  
               RequestDispatcher rd=request.getRequestDispatcher("specificservlet");
               rd.include(request,response);
}
        finally{
            out.close();
        }
        out.println("</div>");
        out.println("</body>");
            out.println("</html>");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
