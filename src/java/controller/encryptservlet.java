/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.Bridge;
import model.HaarTestWithSampleValues;
import model.pixel;
import model.triple_a;

/**
 *
 * @author vrg
 */
public class encryptservlet extends HttpServlet {
private static String getValue(Part part) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
    StringBuilder value = new StringBuilder();
    char[] buffer = new char[1024];
    for (int length = 0; (length = reader.read(buffer)) > 0;) {
        value.append(buffer, 0, length);
    }
    return value.toString();
}
    private String extractFileName(Part part) {
    String contentDisp = part.getHeader("content-disposition");
    String[] items = contentDisp.split(";");
    for (String s : items) {
        if (s.trim().startsWith("filename")) {
            return s.substring(s.indexOf("=") + 2, s.length()-1);
        }
    }
    return "";
}
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
            out.println("<title>Servlet encryptservlet</title>");            
            out.println("</head>");
            out.println("<body>");
                HttpSession session=request.getSession(false);  
    if(session!=null)
          {  String username=   (String)session.getAttribute("name");
    String password=(String)session.getAttribute("password");
    
              System.out.println(password+"password"  + username);  
            String receiver,imagepath,filepath,message=null,msgtype,key=null,algoselect;
                            Part txtmessage=request.getPart("txtmsg");
                            
                            message=getValue(txtmessage);

               receiver=getValue(request.getPart("Receiver"));
     boolean b=false;
               Bridge bg=new Bridge();
      try{
               b = bg.receiverpresent(receiver);
      
     
      if(b) 
      {     
      
      Part imagepart=request.getPart("image");
              imagepath=imagepart.getHeader("image");
              imagepath=extractFileName(imagepart);
              // String fileName = Paths.get(imagepart.getSubmittedFileName()).getFileName().toString();
               InputStream ip=imagepart.getInputStream();
            byte[] buffer = new byte[ip.available()];
    ip.read(buffer);
    File f=new File("E:/major-image/"+imagepath);
               FileOutputStream fout=new FileOutputStream(f);
               fout.write(buffer);
   Part  algosel=request.getPart("algo");
   
      
       algoselect=getValue(algosel);
       if(algoselect.equals("pixelalgo"))   
       { key=  getValue(request.getPart("pixel"));      
               pixel pi=new pixel();
               pi.encode(f,key,message,algoselect,receiver,username);
                    response.sendRedirect("home_page.html"); 
               System.out.println("hellopixel");
        } 
 
   else if(algoselect.equals("triplealgo"))
   {
             //algoselect=getValue(request.getPart("triplealgo"));
             
              key=getValue(request.getPart("triplea"));
             triple_a ta=new triple_a();
             ta.encode(f, key, message, algoselect,username,receiver);
            response.sendRedirect("home_page.html"); 
   } 
   else  
             {                       key=getValue(request.getPart("dwt"));
             HaarTestWithSampleValues ht=new HaarTestWithSampleValues();           
             ht.encode(f, key, message, "dwt", username, receiver);
             
             
             response.sendRedirect("home_page.html"); 
             }
      }
       else
           {out.println("receiver not presnt");
               RequestDispatcher rd=request.getRequestDispatcher("encrypt.html");
               rd.include(request,response);
           }
      }
       catch(Exception e)
      {System.out.println(e);
      out.println("error occured :"+e);
               RequestDispatcher rd=request.getRequestDispatcher("encrypt.html");
               rd.include(request,response);
      }
     
        
      }
      
      
      
       else
           {out.println("invalid username and password");
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
