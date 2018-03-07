/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.ServletBase;
import web.helpers.LoginHelperBean;

/**
 *
 * @author Ben
 */
@WebServlet(name = "DrawController", urlPatterns = {"/draw"})
public class DrawController extends HttpServlet {

    private static final String pointShape = "Point";
    private static final String lineShape = "Line";
    private static final String squareShape = "Square";
    private static final String triangleShape = "Triangle";
    private static final String circleShape = "Circle";
    private static final String ellipseShape = "Ellipse";
    
    
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
       try (PrintWriter out = response.getWriter()) {
            ServletBase.PrintHead(out);
            ServletBase.PrintBody(request.getContextPath(), loginHelper.ValidateRequest(request), loginHelper.ValidateTeacher(request), out);
            
            out.println("<h1>Draw a shape</h1>");
            
            ServletBase.PrintForm(out, pointShape, request.getContextPath() + "/draw/" + pointShape.toLowerCase(), new String[]{});
            ServletBase.PrintForm(out, lineShape, request.getContextPath() + "/draw/" + lineShape.toLowerCase(), new String[]{});
            ServletBase.PrintForm(out, squareShape, request.getContextPath() + "/draw/" + squareShape.toLowerCase(), new String[]{});
            ServletBase.PrintForm(out, triangleShape, request.getContextPath() + "/draw/" + triangleShape.toLowerCase(), new String[]{});
            ServletBase.PrintForm(out, circleShape, request.getContextPath() + "/draw/" + circleShape.toLowerCase(), new String[]{});
            ServletBase.PrintForm(out, ellipseShape, request.getContextPath() + "/draw/" + ellipseShape.toLowerCase(), new String[]{});
            
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
        processRequest(request, response);
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
