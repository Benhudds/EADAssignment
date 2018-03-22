/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.questions;

import com.JSONHelper;
import com.XMLHelper;
import ejb.QuestionEntity;
import ejb.QuestionEntityFacade;
import ejb.UserQuestionEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import web.CookieHelper;
import web.ServletBase;
import web.helpers.LoginHelperBean;
import shapes.Circle;
import shapes.Ellipse;
import shapes.Line;
import shapes.ShapeServletBase;
import static shapes.ShapeServletBase.CreateImgUrl;
import shapes.Square;
import shapes.Triangle;

@WebServlet(name = "QuestionController", urlPatterns = {"/*"})
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

    public void saveQuestion(QuestionEntity q) throws JMSException {
        QuestionEntity e = new QuestionEntity();
        e.setQuestion(q.getQuestion());
        e.setAnswer(q.getAnswer());

        saveEntity(e);
    }

    public UserQuestionEntity saveAnswer(Long questionId, Long userId, double answer, boolean correct) throws JMSException {
        UserQuestionEntity e = new UserQuestionEntity();
        e.setAnswer(answer);
        e.setCorrect(correct);
        e.setUserID(userId);
        e.setQuestionID(questionId);
        e.setAttempted(new Date());
        
        saveEntity(e);

        return e;
    }

    private QuestionEntity doHtmlPost(HttpServletRequest request, HttpServletResponse response, double ans) throws JMSException, IOException {
        PrintWriter out = response.getWriter();

        QuestionEntity q = null;

        String questionString = request.getParameter(QuestionParam);

        List questions = questionEntityFacade.findAll();
        for (Iterator it = questions.iterator(); it.hasNext();) {
            QuestionEntity elem = (QuestionEntity) it.next();
            if (elem.getQuestion().equals(questionString)) {
                q = elem;
            }
        }

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        
        if (q == null) {
            out.println("<h3>Sorry, there was an error!</h3>");
        } else {
            saveAnswer(getQuestionId(questionString),
                CookieHelper.GetUserIdFromCookie(request),
                ans,
                Question.TestAnswer(ans, q.getAnswer()));
            
            if (q.getAnswer() == ans) {
                out.println("<h3>Correct! Well done. Try another question</h3>");
            } else {
                out.println("<h3>Incorrect. Try another question</h3>");
            }
        }

        return q;
    }

    private UserQuestionEntity doApiPost(HttpServletRequest request) throws JMSException, URISyntaxException {
        QuestionEntity q = null;
        String answer = request.getParameter(AnswerParam);
        double ans = Double.valueOf(answer);

        q = getQuestionFromUrl(request);
        if (q == null) {
            String questionString = request.getParameter(QuestionParam);
            List questions = questionEntityFacade.findAll();
            for (Iterator it = questions.iterator(); it.hasNext();) {
                QuestionEntity elem = (QuestionEntity) it.next();
                if (elem.getQuestion().equals(questionString)) {
                    q = elem;
                    break;
                }
            }
        }
        
        return saveAnswer(q.getId(),
                CookieHelper.GetUserIdFromCookie(request),
                ans,
                Question.TestAnswer(ans, q.getAnswer()));
    }

    private void processJSONRequest(HttpServletRequest request, HttpServletResponse response, boolean post) throws IOException {
        if (post) {
            // this is a post request
            // save the answer
            try {
                response.setStatus(201);
                ServletOutputStream sos = response.getOutputStream();
                sos.println(JSONHelper.GetJson(doApiPost(request)));
            } catch (Exception ex) {
                System.out.println("Exception = " + ex);
                response.setStatus(422);
            }
        } else {
            // this is a get request
            // return a random question
            try {
                QuestionEntity q = getQuestion(request);
                ServletOutputStream sos = response.getOutputStream();
                sos.println(JSONHelper.GetJson(q));
            } catch (IOException | URISyntaxException | JMSException ex) {
                System.out.println("Exception = " + ex);
                response.setStatus(500);
            }
        }
    }

    private void processXMLRequest(HttpServletRequest request, HttpServletResponse response, boolean post) throws IOException {
        if (post) {
            // this is a post request
            // save the answer
            try {
                response.setStatus(201);
                ServletOutputStream sos = response.getOutputStream();
                XMLHelper.WriteToServletOutputStream(sos, UserQuestionEntity.class, doApiPost(request));
            } catch (Exception ex) {
                System.out.println("Exception = " + ex);
                response.setStatus(422);
            }
        } else {
            // this is a get request
            // return a random question
            try {
                QuestionEntity q = getQuestion(request);
                ServletOutputStream sos = response.getOutputStream();
                JAXBContext context = JAXBContext.newInstance(QuestionEntity.class, Line.class, Square.class, Triangle.class, Circle.class, Ellipse.class);
                XMLHelper.WriteToSOSWithContext(sos, q, context);
            } catch (IOException | JAXBException | URISyntaxException | JMSException ex) {
                System.out.println("Exception = " + ex);
                response.setStatus(500);
            }
        }
    }

    private QuestionEntity getQuestion(HttpServletRequest request) throws URISyntaxException, JMSException {
        QuestionEntity q = null;
        q = getQuestionFromUrl(request);
        if (q == null) {
            q = Question.generateQuestion();
            saveQuestion(q);
        }

        return q;
    }

    private QuestionEntity getQuestionFromUrl(HttpServletRequest request) throws URISyntaxException {
        QuestionEntity q = null;
        String url = request.getRequestURL().toString();
        URI uri = new URI(url);
        String path = uri.getPath();
        String lastPart = path.substring(path.lastIndexOf('/') + 1);

        if (lastPart == null || lastPart.equals("") || lastPart.equals("question")) {
            return null;
        } else {
            return questionEntityFacade.find(Long.valueOf(lastPart));
        }
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
    protected void processHtmlRequest(HttpServletRequest request, HttpServletResponse response, boolean post)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            ShapeServletBase.PrintHead(out);
            ShapeServletBase.PrintBody(request.getContextPath(), loginHelper.ValidateRequest(request), loginHelper.ValidateTeacher(request), out);
            out.println("<h1>Answer the following question to 2 decimal places: </h1>");

            QuestionEntity q = null;
            if (post) {
                // Lookup the answer and compare to submitted

                String answer = request.getParameter(AnswerParam);
                double ans = Double.valueOf(answer);

                doHtmlPost(request, response, ans);

                out.println("<form method='get' action='" + request.getContextPath() + ">");
                out.println("<input type='submit' value='Try another'>");
                out.println("</form>");
            } else {
                q = getQuestion(request);
                saveQuestion(q);

                out.println("<img src='" + CreateImgUrl(request.getContextPath().replace("question", "draw"), q.getShape()) + "'>");
                out.println("<h3>" + q.getQuestion() + "</h3>");
                ServletBase.PrintPostForm(out, "Submit", request.getContextPath(), new String[]{AnswerParam}, new String[]{QuestionParam}, new String[]{q.getQuestion()});
            }

            ServletBase.EndBody(out);

        } catch (JMSException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, boolean post) throws IOException {
        String acceptHeader = request.getHeader(ServletBase.ACCEPT_HEADER);
        System.out.println(acceptHeader);
        switch (acceptHeader) {
            case "application/json":
                processJSONRequest(request, response, post);
                response.setContentType("application/json");
                break;
            case "application/xml":
                processXMLRequest(request, response, post);
                response.setContentType("application/xml");
                break;
            default:
                processHtmlRequest(request, response, post);
                break;
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
