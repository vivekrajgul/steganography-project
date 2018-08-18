/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Bridge;
import model.receiver;

/**
 *
 * @author shiva
 */
public class showitemservlet extends HttpServlet {
  
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
            


/*#customers td, #customers th {
    border: 1px solid #ddd;
    padding: 8px;
}

#customers tr:nth-child(even){background-color: #f2f2f2;}
            */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet showitemservlet</title>"); 
            out.println("<link href=showitem.css rel=stylesheet type=text/css>");
            out.println("</head>");
            out.println("<body style=background:#BC8F8F>");
            
                         out.println("<div id=watermark>");
out.println("<p>Image Steganography</p>");
        out.println("</div>");


              HttpSession session=request.getSession(false);  
    
    //System.out.println(password+"password"  + username);       
    if( session!=null)
          {
String username=   (String)session.getAttribute("name");
    String password=(String)session.getAttribute("password");
   
              List<receiver> lst = new ArrayList<receiver>();
         Bridge bg=new Bridge();
            try
            {                                                               
             lst=bg.Select(username);
             
             out.println("<br><br>");
             
       
            
            out.println("<div class=wrapper1>");
          out.println("<table align=center border=3 width=50% height=50% id=csstable>");
             out.println("<tr><th><font color=red>Sender</th><th><font color=red>Stegano-image</th><th><font color=red>Imageid</th><th><font color=red>Decode</th></tr>");
             for(receiver s:lst)
             {
                out.println("<tr><td>"+s.getSender()+"</td><td><img src=imagejsp2.jsp?imageid="+s.getImageid()+" width=50 height=50 ></td><td>"+s.getImageid()+"</td><td><a href=specificservlet?imageid="+s.getImageid()+">Decode</a></td></tr>");
             }
            }
          catch(Exception e)
            {
                out.println(e);
            }  
  out.println("</table>");
  out.println("</div>");
  out.println("<div class=wrapper>");
  out.println("<a href=encdec.html class=button button4 align=center border-radius=30px>Back</a>");
  out.println("</div>");          
  out.println("</body>");
            out.println("</html>");
          }
        else
         {out.println("invalid username and password");
           RequestDispatcher rd=request.getRequestDispatcher("login.html");
         rd.include(request,response);
           }      
        
        } finally {            
            out.close();
        }
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
