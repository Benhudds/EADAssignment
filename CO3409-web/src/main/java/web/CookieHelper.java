/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.UserEntity;
import java.io.PrintWriter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.questions.Question;

public class CookieHelper {

    private static final String USER_NAME_STRING = "currentUser";
    private static final String ERROR_MESSAGE_STRING = "errorMessage";
    private static final String QUESTION_STRING = "question";

    public static void CreateLoginCookie(HttpServletResponse response, UserEntity user) {
        Cookie loginCookie = new Cookie(USER_NAME_STRING, user.getId().toString());
        loginCookie.setMaxAge(3600);
        response.addCookie(loginCookie);
    }

    public static void RevokeSession(HttpServletRequest request, HttpServletResponse response) {
        // Remove the cookie
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals(USER_NAME_STRING)) {
                c.setMaxAge(0);
                response.addCookie(c);
                break;
            }
        }
    }

    public static Long GetUserIdFromCookie(HttpServletRequest request) {
        Long userId = null;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                System.out.println(c.getName());
                if (c.getName().equals(USER_NAME_STRING)) {
                    System.out.println(c.getValue());
                    userId = Long.parseLong(c.getValue());
                    System.out.println(userId);
                    break;
                }
            }
        }

        return userId;
    }

    public static void ShowErrors(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        Cookie[] cookies = request.getCookies();
        String errorMessage = null;
//        for(Cookie c : cookies) {
//            if (ERROR_MESSAGE_STRING.equals(c.getName())) {
//                errorMessage = c.getValue();
//                
//                // Set age to 0 to remove it now
//                c.setValue("");
//                response.addCookie(c);
//                break;
//            }
//        }

        errorMessage = (String) request.getSession().getAttribute(ERROR_MESSAGE_STRING);
        if (errorMessage != null && !"".equals(errorMessage)) {
            out.println("<h3>" + errorMessage + "</h3>");
        }
    }

    public static void SuppressErrors(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String errorMessage = null;
        for (Cookie c : cookies) {
            if (ERROR_MESSAGE_STRING.equals(c.getName())) {
                errorMessage = c.getValue();

                // Set age to 0 to remove it now
                c.setValue("");
                c.setMaxAge(0);
                response.addCookie(c);
                break;
            }
        }
    }
}
