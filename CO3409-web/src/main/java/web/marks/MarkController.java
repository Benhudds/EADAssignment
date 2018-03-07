/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.marks;

import ejb.QuestionEntity;
import ejb.QuestionEntityFacade;
import ejb.UserEntity;
import ejb.UserEntityFacade;
import ejb.UserQuestionEntity;
import ejb.UserQuestionEntityFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.ServletBase;
import web.helpers.LoginHelperBean;

@WebServlet(name = "MarkController", urlPatterns = {"/marks/*"})
public class MarkController extends HttpServlet {

    @EJB
    private LoginHelperBean loginHelper;

    @EJB
    private UserQuestionEntityFacade uQEntityFacade;

    @EJB
    private QuestionEntityFacade qEntityFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            ServletBase.PrintHead(out);
            ServletBase.PrintBody(request.getContextPath(), loginHelper.ValidateRequest(request), loginHelper.ValidateTeacher(request), out);

            String url = request.getRequestURL().toString();
            URI uri = new URI(url);
            String path = uri.getPath();
            String lastPart = path.substring(path.lastIndexOf('/') + 1);

            if (lastPart == null) {

            }

            out.println("<h3>Students</h3>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Question</th>");
            out.println("<th>Submitted Answer</th>");
            out.println("<th>Correct Answer</th>");
            out.println("<th>Correct</th>");

            out.println("</tr>");

            int totalAnswered = 0;
            int totalCorrect = 0;

            for (UserQuestionEntity ue : uQEntityFacade.findAll()) {
                if (ue.getUserID() == Integer.parseInt(lastPart)) {

                    out.println("<tr>");

                    QuestionEntity q = qEntityFacade.find(ue.getQuestionID());
                    out.println("<td>" + q.getQuestion() + "</td>");
                    out.println("<td>" + ue.getAnswer() + "</td>");
                    out.println("<td>" + q.getAnswer() + "</td>");
                    out.println("<td>" + ue.isCorrect() + "</td>");
                    out.println("</tr>");
                    totalAnswered++;                   
                    
                    if (ue.getAnswer() == q.getAnswer()) {
                        totalCorrect++;
                    }
                }
            }

            out.println("</table>");

            out.println("<h4>Total questions answered: " + totalAnswered + "</h4>");
            out.println("<h4>Total questions correct: " + totalCorrect + "</h4>");
            
            ServletBase.EndBody(out);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MarkController.class.getName()).log(Level.SEVERE, null, ex);
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
