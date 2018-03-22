/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.UserEntityFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.ServletBase;
import web.helpers.LoginHelperBean;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    
    @Resource(mappedName = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(mappedName = "jms/NewMessage")
    private Queue queue;
    
    @EJB
    private UserEntityFacade userEntityFacade;
    
    @EJB
    private LoginHelperBean loginHelper;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, boolean error)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ServletBase.PrintHead(out);
            ServletBase.PrintBody(request.getContextPath(), loginHelper.ValidateRequest(request), loginHelper.ValidateTeacher(request), out);
            
            out.println("<h1>Login</h1>");
            if (error) {
                out.println("<h3>Invalid username or password</h3>");
            }
            ServletBase.PrintPostForm(out, "Login", request.getContextPath() + "/login", new String[]{LoginHelperBean.passwordParam, LoginHelperBean.usernameParam});
            
            ServletBase.EndBody(out);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, false);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if valid
        try {
            String acceptHeader = request.getHeader(ServletBase.ACCEPT_HEADER);
            if (loginHelper.Login(request, response)) {
                switch (acceptHeader) {
                    case "application/json":
                    case "application/xml":
                        request.getRequestDispatcher("/home").include(request, response);
                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/home");
                        break;
                }
            } else {
                switch (acceptHeader) {
                    case "application/json":
                    case "application/xml":
                        response.sendError(401);
                        break;
                    default:
                        processRequest(request, response, true);
                        break;
                }
            }
        } catch (Exception e) {
            
        }
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
