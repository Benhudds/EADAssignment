/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.PrintWriter;
import web.helpers.LoginHelperBean;

public class ServletBase {

    public static final String ACCEPT_HEADER = "Accept";
    public static final int OK_CODE = 200;
    public static final int CREATED_CODE = 201;
    public static final int UNPROCESSABLE_CODE = 422;
    public static final int SERVER_ERROR_CODE = 500;    
    public static final String TEACHER_PARAM = "Teacher";
    public static final String BASE_URL = "/geometryapp"; 

    private static void PrintPostFormStart(PrintWriter out, String action) {
        out.println("<form method='post', action='" + action + "'>");
    }

    private static void PrintGetFormStart(PrintWriter out, String action) {
        out.println("<form method='get', action='" + action + "'>");
    }

    private static void PrintFormEnd(PrintWriter out, String btnText) {
        out.println("<input type='submit' name='btn' value='" + btnText + "'>");
        out.println("</form>");
    }

    private static void PrintInputs(PrintWriter out, String[] params) {
        for (int i = params.length - 1; i > -1; i--) {
            if (params[i].equals(LoginHelperBean.passwordParam)) {
                out.println("<div class='container'>");
                out.println("<p1>" + params[i] + ": </p1>");
                out.println("<input type='password' name='" + params[i] + "'><br>");
                out.println("</div>");
            } else if (params[i].equals(TEACHER_PARAM)) {
                out.println("<input type='checkbox' id='" + params[i] + "' value='" + params[i] + "' name='test'>" + TEACHER_PARAM + "</br>");
            } else {
                out.println("<div class='container'>");
                out.println("<p1>" + params[i] + ": </p1>");
                out.println("<input type='text' name='" + params[i] + "'><br>");
                out.println("</div>");
            }
        }
    }

    private static void PrintHiddenInputs(PrintWriter out, String[] hiddenParams, String[] hiddenParamValues) {
        // Return if called badly
        if (hiddenParams.length != hiddenParamValues.length) {
            return;
        }

        for (int i = hiddenParams.length - 1; i > -1; i--) {
            out.println("<input type='hidden' name='" + hiddenParams[i] + "' value='" + hiddenParamValues[i] + "'>");
        }
    }

    public static void PrintPostForm(PrintWriter out, String btnText, String action, String[] params) {
        PrintPostFormStart(out, action);
        PrintInputs(out, params);
        PrintFormEnd(out, btnText);
    }

    public static void PrintPostForm(PrintWriter out, String btnText, String action, String[] params, String[] hiddenParams, String[] hiddenParamValues) {
        PrintPostFormStart(out, action);
        PrintInputs(out, params);
        PrintHiddenInputs(out, hiddenParams, hiddenParamValues);
        PrintFormEnd(out, btnText);
    }

    public static void PrintGetForm(PrintWriter out, String btnText, String action, String[] params) {
        PrintGetFormStart(out, action);
        PrintInputs(out, params);
        PrintFormEnd(out, btnText);
    }

    public static void PrintHead(PrintWriter out) {
        out.println("<head>");
        out.println("<title>");
        out.println("Geometry App");
        out.println("</title>");
        PrintCSS(out);
        out.println("</head>");
    }

    public static void PrintCSS(PrintWriter out) {
        out.println("<style type=\"text/css\">\n"
                + "    .container {\n"
                + "        width: 250px;\n"
                + "        clear: both;\n"
                + "    }\n"
                + "    .container input {\n"
                + "        width: 100%;\n"
                + "        clear: both;\n"
                + "    }\n"
                + "\n"
                + "ul {"
                + "list-style-type: none;"
                + "margin: 0;"
                + "padding: 0;"
                + "overflow: hidden;"
                + "background-color: #333;"
                + "}"
                + "li {"
                + "float: left;"
                + "}"
                + "li a {"
                + "display: block;"
                + "color: white;"
                + "text-align: center;"
                + "padding: 14px 16px;"
                + "text-decoration: none;"
                + "}"
                + "li a:hover {"
                + "background-color: #111;"
                + "}"
                + "ul.topnav li.right {float: right;}"
                + "</style>");
    }

    public static void PrintNavBar(String baseUrl, boolean loggedIn, boolean teacher, PrintWriter out) {
        out.println("<ul class='topnav'>"
                + "<li><a class='active' href='" + BASE_URL + "/home" + "'>Home</a></li>"
        );

        if (loggedIn) {
            out.println("<li><a href='" + BASE_URL + "/draw" + "'>Draw</a></li>"
                    + "<li><a href='" + BASE_URL + "/question" + "'>Questions</a></li>");
            if (teacher) {
                out.println("<li><a class='active' href='" + BASE_URL + "/users" + "'>Users</a></li>");
            }

            out.println("<li><a class='right' href='" + BASE_URL + "/logout" + "'>Log Out</a></li>");
        } else {
            out.println("<li><a class='right' href='" + BASE_URL + "/login" + "'>Log In</a></li>");
        }

        out.println("</ul>");
    }

    public static void PrintBody(String baseUrl, boolean loggedIn, boolean teacher, PrintWriter out) {
        out.println("<body>");
        PrintNavBar(baseUrl, loggedIn, teacher, out);
    }

    public static void EndBody(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }
}
