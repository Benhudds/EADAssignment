/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.questions;

import ejb.QuestionEntity;
import ejb.QuestionEntityFacade;
import ejb.UserQuestionEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.CookieHelper;
import web.ServletBase;
import web.helpers.LoginHelperBean;
import web.shapes.ShapeServletBase;
import static web.shapes.ShapeServletBase.CreateImgUrl;

@WebServlet(name = "QuestionController", urlPatterns = {"/question"})
public class QuestionController extends HttpServlet {

    public final static String AnswerParam = "Answer";
    public final static String QuestionParam = "Question";

    @Resource(mappedName = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/NewMessage")
    private Queue queue;

    @EJB
    private QuestionEntityFacade questionEntityFacade;
    
    @EJB
    private LoginHelperBean loginHelper;

    private Long getQuestionId(String question) {
        List questions = questionEntityFacade.findAll();
        for (Object e : questions) {
            QuestionEntity qe = (QuestionEntity) e;

            if (qe.getQuestion().equals(question)) {
                return qe.getId();
            }
        }

        return new Long(-1);
    }

    public void saveEntity(Serializable s) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer(queue);

        ObjectMessage message = session.createObjectMessage();

        message.setObject(s);
        messageProducer.send(message);
        messageProducer.close();
        connection.close();
    }

    public void saveQuestion(Question q) throws JMSException {
        System.out.println("Saving question " + q.getQuestion());

        QuestionEntity e = new QuestionEntity();
        e.setQuestion(q.getQuestion());
        e.setAnswer(q.getAnswer());

        saveEntity(e);
        System.out.println("Saved question");
    }

    public void saveAnswer(Long questionId, Long userId, double answer, boolean correct) throws JMSException {
        System.out.println("Saving answer " + answer);
        System.out.println("User " + userId);
        System.out.println("Question " + questionId);
        UserQuestionEntity e = new UserQuestionEntity();
        e.setAnswer(answer);
        e.setCorrect(correct);
        e.setUserID(userId);
        e.setQuestionID(questionId);

        saveEntity(e);
        System.out.println("Saved answer");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, boolean post)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String questionString = request.getParameter(QuestionParam);

            ShapeServletBase.PrintHead(out);
            ShapeServletBase.PrintBody(request.getContextPath(), loginHelper.ValidateRequest(request), loginHelper.ValidateTeacher(request), out);
            out.println("<h1>Answer the following question to 2 decimal places: </h1>");

            Question q = null;
            if (post) {
                // Lookup the answer and compare to submitted
                List questions = questionEntityFacade.findAll();
                for (Iterator it = questions.iterator(); it.hasNext();) {
                    QuestionEntity elem = (QuestionEntity) it.next();
                    if (elem.getQuestion().equals(questionString)) {
                        q = new Question();
                        q.setQuestion(elem.getQuestion());
                        q.setAnswer(elem.getAnswer());

                        break;
                    }
                }

                String answer = request.getParameter(AnswerParam);
                System.out.println("sub = " + answer);
                System.out.println("stored = " + q.getAnswer());

                double ans = Double.valueOf(answer);

                saveAnswer(getQuestionId(questionString),
                        CookieHelper.GetUserIdFromCookie(request),
                        ans,
                        ans == q.getAnswer());

                if (q == null) {
                    out.println("<h3>Sorry, there was an error!</h3>");
                } else {
                    if (q.getAnswer() == ans) {
                        out.println("<h3>Correct! Well done. Try another question</h3>");
                    } else {
                        out.println("<h3>Incorrect. Try another question</h3>");
                    }
                }

                //CookieHelper.RemoveQuestionSession(request);

                out.println("<form method='get' action='" + request.getContextPath() + "/question'>");
                out.println("<input type='submit' value='Go back'>");
                out.println("</form>");
            } else {
                q = Question.generateQuestion();
                saveQuestion(q);
                //CookieHelper.CreateQuestionSession(request, q);

                out.println("<img src='" + CreateImgUrl(request, q.getShape()) + "'>");

                out.println("<h3>" + q.getQuestion() + "</h3>");
                ServletBase.PrintForm(out, "Submit", request.getContextPath() + "/question", new String[]{AnswerParam}, new String[]{QuestionParam}, new String[]{q.getQuestion()});
                //ServletBase.PrintForm(out, "Submit", request.getContextPath() + "/question", new String[]{AnswerParam});
            }
            
            ServletBase.EndBody(out);

//            try {
//                createQuestion(q.getQuestion(), q.getAnswer());
//                CookieHelper.CreateQuestionSession(request, q);
//            } catch (JMSException ex) {
//                Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } catch (JMSException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response, true);

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
