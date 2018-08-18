/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Bridge;

/**
 *
 * @author vrg
 */
public class validateservlet extends HttpServlet {

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
            out.println("<title>Servlet validateservlet</title>");            
            out.println("</head>");
            out.println("<body>");
String username,email,password;
          boolean b=false;

if (request.getParameter("login") != null) {
   
            username=request.getParameter("username");
            email=request.getParameter("userid");
            password=request.getParameter("password");
               
            try
            { Bridge bg=new Bridge();
             b=bg.uservalidate(password,username,email);
            }
             catch(Exception e)
{
    out.println(e);
}

            if(b)
            {     HttpSession session=request.getSession();  
session.setAttribute("name",username);
session.setAttribute("password",password);
         
                   
   // boolean c=dao.checkuseroradmin(password);
//if(c)
response.sendRedirect("encdec.html");
//else
  //  response.sendRedirect("getallservlet");
            }
           
            
            
            
    else{  
        out.println("<h3 align=center>Please Enter Valid  Data</h3>");
       
        
               RequestDispatcher rd=request.getRequestDispatcher("login.html");
               rd.include(request,response);
           
            }              
 
}
else if (request.getParameter("register") != null) {
     username=request.getParameter("usernamesignup");
            email=request.getParameter("emailsignup");
            password=request.getParameter("passwordsignup");
             try
            { Bridge bg=new Bridge();
             b=bg.register(username,password,email);
            if(b)
            {     
response.sendRedirect("login.html");
}
          
    else{  
                
        out.println("already exists");
               RequestDispatcher rd=request.getRequestDispatcher("login.html");
               rd.include(request,response);
           
            }              
            }



catch(Exception e)
{
    out.println(e);
}
}
else if(request.getParameter("logout") != null)
{
HttpSession session=request.getSession(false);
session.invalidate();
response.sendRedirect("login.html");
System.out.println("i am in");
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
