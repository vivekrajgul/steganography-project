/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dataSource.connector;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author shiva
 */
public class specificservlet extends HttpServlet {

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
                    //    out.println("<link href=showitem.css rel=stylesheet type=text/css>");
          
            out.println("<script> function check()");
                        out.println("{");
                                out.println("event.preventDefault();");
                        out.println("var s=document.form.key.value;");
                                out.println("if((s.length)==7)");
                                out.println ("{");
                                out.println ("document.loginform.submit();");
                                out.println("}");
                                out.println("else");
                                out.println( "{");
                                
                                out.println ("alert(wrong key);");
                                 out.println ("return false;");
                                out.println ( "}");
                                out.println ("}");
                                        out.println("</script>");
            
            out.println("<style>");
            
            out.println("#tkw{");
         out.println("background-color: white;");
         out.println("margin: 25%;");
         out.println("opacity: 0.9;");
    out.println("padding-top: 20px;");     
        out.println("}");
            
            
            out.println("a:link, a:visited {");
    out.println("background-color: #f44336;");
    out.println("color: white;");
    out.println("padding: 10px 15px;");
    out.println("text-align: center;");	
    out.println("text-decoration: none;");
    out.println("display: inline-block;");
    out.println("opacity: 0.5;");
    out.println("z-index:  auto;");
    out.println("}");

out.println("a:hover, a:active {");
    out.println("background-color: red;");
out.println("} " );

            out.println("#div1 {");
    out.println("border-radius: 5px;");
    out.println("width:70%;");
    out.println("background: url(w.jpg);");
    out.println("background-size: 900px 900px;");
    out.println("background-repeat: no-repeat;");
    out.println("padding-top: 40px;");
    out.println("}");
    
     out.println("input, select {");
    out.println("width: 250px;");
    out.println("padding: 5px 10px;");
    out.println("margin: 8px 0px;");
    out.println("display: inline-block;");
    out.println("border: 1px solid #ccc;");
    out.println("border-radius: 4px;");
    out.println("box-sizing: border-box;}");


out.println("input[type=submit] {");
    out.println("width: 125px; ");
    out.println("background-color: #4CAF50;");
    out.println("color: white;");
   out.println(" padding: 14px 20px;");
   out.println(" margin: 8px 0;");
   out.println(" border: none;");
   out.println(" border-radius: 4px;");
   out.println(" cursor: pointer;}");


out.println("input[type=submit]:hover {");
    out.println("background-color: black;}");


            
            out.print("</style>");
            
            out.println("<title>Servlet specificservlet</title>");   
         //   out.println("<link href=decryptservlet.css rel=stylesheet type=text/css>");
           out.println("</head>");
            out.println("<body style=background:#BC8F8F;opacity=0.4>");
        
            int id =Integer.parseInt(request.getParameter("imageid"));
            
            //used to detect time should be used latet  
                  HttpSession session=request.getSession(false);  
String username=(String)session.getAttribute("name");
String password=(String)session.getAttribute("password");
session.setAttribute("idimage", id);           
if(username!=null && session!=null)
           {
            
            try
            {
            Connection con=connector.getConnect();
            String s=" Select Sender from table1 where index_id=?";
            PreparedStatement ps=con.prepareStatement(s);
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            rs.next();
                    out.println("<div align=center>");
               out.println("<img src=image3.jsp?imageid="+id+"class=image style=border:2px solid black; width=250 height=300>");
             out.println("</div>"); 
         out.println("<div margin=70 align=center>");
             out.println("<form action=decryptservlet method=post onsubmit=return check(event);>");      
         out.println("<table>");
         out.println("<tr><td>Image id</td><td><input readonly=true type=text name=txtid value="+id+"></td></tr>");
         out.println("<tr><td> Enter Key</td><td><input type=text name=key value=></td></tr>");
         out.println("<tr><td>Sender</td><td><input readonly=true type=text name=txtbid value="+rs.getString(1)+"></td></tr>");
         out.println("<tr><td><input type=submit name=btnsubmit value=Decode align=center colspan=5></td></tr>");
         out.println("</table>");
         out.println("</form>");        
         out.println("</div>");
            }
        
         
         catch(Exception e)
         {
         out.println(e);
         }

           }
           else
           {
               out.println("invalid username and password");
               RequestDispatcher rd=request.getRequestDispatcher("login.html");
               rd.include(request,response);
           }
            out.println("</body>");
            out.println("</html>");
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
