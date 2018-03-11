/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.users;

import com.JSONHelper;
import com.XMLHelper;
import ejb.UserEntityFacade;
import ejb.UserEntity;
import ejb.UserQuestionEntityFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;
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
import javax.xml.bind.JAXBException;
import web.ServletBase;
import web.helpers.LoginHelperBean;
import web.marks.MarkController;

@WebServlet(name = "UserController", urlPatterns = {"/users/*"})
public class UserController extends HttpServlet {

    private final static String[] paramNames = {"Teacher", "Password", "UserName", "LastName", "FirstName"};
    public final static String teacherParam = "Teacher";

    @Resource(mappedName = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/NewMessage")
    private Queue queue;

    @EJB
    private LoginHelperBean loginHelper;

    @EJB
    private UserQuestionEntityFacade uQEntityFacade;

    @EJB
    private UserEntityFacade uEFacade;

    private void saveEntity(Serializable s) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer(queue);

        ObjectMessage message = session.createObjectMessage();

        message.setObject(s);
        messageProducer.send(message);
        messageProducer.close();
        connection.close();
    }

    private void saveUser(String lastName, String firstName, String userName, String password, boolean teacher) throws JMSException {
        UserEntity e = new UserEntity();

        e.setLastName(lastName);
        e.setFirstName(firstName);
        e.setUserName(userName);
        e.setPassword(password);
        e.setTeacher(teacher);

        saveEntity(e);
    }

    private Users getUsers(HttpServletRequest request) throws URISyntaxException {
        String url = request.getRequestURL().toString();
        URI uri = new URI(url);
        String path = uri.getPath();
        String lastPart = path.substring(path.lastIndexOf('/') + 1);
        Users marks = new Users();

        if (lastPart != null && !lastPart.equals("") && !lastPart.equals("users")) {

            for (UserEntity u : uEFacade.findAll()) {
                System.out.println("lastpart = " + lastPart);
                if (Objects.equals(u.getId(), Long.valueOf(lastPart))) {
                    marks.addUser(u);
                }
            }

            return marks;
        }

        for (UserEntity u : uEFacade.findAll()) {
            marks.addUser(u);
        }

        return marks;
    }

    private void processHtmlRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            ServletBase.PrintHead(out);
            ServletBase.PrintBody(request.getContextPath(), loginHelper.ValidateRequest(request), loginHelper.ValidateTeacher(request), out);

            out.println("<h3>Create new student:</h3>");
            ServletBase.PrintPostForm(out, "Create", request.getContextPath() + "/users", paramNames);

            String url = request.getRequestURL().toString();
            URI uri = new URI(url);
            String path = uri.getPath();
            String lastPart = path.substring(path.lastIndexOf('/') + 1);

            if (lastPart == null) {

            }

            out.println("<h3>Students</h3>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Last name</th>");
            out.println("<th>First name</th>");
            out.println("<th>User name</th>");
            out.println("<th>Marks</th>");
            out.println("</tr>");

            for (UserEntity ue : uEFacade.findAll()) {
                if (!ue.isTeacher()) {
                    out.println("<tr>");
                    out.println("<td>" + ue.getLastName() + "</td>");
                    out.println("<td>" + ue.getFirstName() + "</td>");
                    out.println("<td>" + ue.getUserName() + "</td>");
                    out.println("<td><a href='" + request.getContextPath() + "/marks/" + ue.getId() + "'>Marks</a></td>");
                    out.println("</tr>");
                }
            }

            out.println("</table>");

            ServletBase.EndBody(out);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processJSONRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Users users = getUsers(request);
            ServletOutputStream sos = response.getOutputStream();
            sos.println(JSONHelper.GetJson(users));
        } catch (IOException | URISyntaxException ex) {
            System.out.println("Exception = " + ex.getMessage());
            response.sendError(500, "An internal server error ocurred");
        }
    }

    private void processXMLRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Users users = getUsers(request);
            ServletOutputStream sos = response.getOutputStream();
            XMLHelper.WriteToServletOutputStream(sos, Users.class, users);
        } catch (IOException | URISyntaxException | JAXBException ex) {
            System.out.println("Exception = " + ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
            response.sendError(500, "An internal server error ocurred");
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acceptHeader = request.getHeader(ServletBase.ACCEPT_HEADER);
        System.out.println("hit controller");
        switch (acceptHeader) {
            case "application/json":
                processJSONRequest(request, response);
                break;
            case "application/xml":
                processXMLRequest(request, response);
                break;
            default:
                processHtmlRequest(request, response);
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
        String acceptHeader = request.getHeader(ServletBase.ACCEPT_HEADER);
        try {
            String firstName = request.getParameter(paramNames[4]);
            String lastName = request.getParameter(paramNames[3]);
            String userName = request.getParameter(paramNames[2]);
            String password = request.getParameter(paramNames[1]);
            boolean teacher = Boolean.getBoolean(request.getParameter(paramNames[0]));

            saveUser(lastName, firstName, userName, password, teacher);

            response.sendRedirect(request.getContextPath() + "/users");
        } catch (Exception e) {
            switch (acceptHeader) {
                case "application/json":
                case "application/xml":
                    response.sendError(422);
                    break;
                default:
                    break;
            }
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
