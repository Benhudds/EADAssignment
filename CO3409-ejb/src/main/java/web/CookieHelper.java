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

public class CookieHelper {

    private static final String USER_NAME_STRING = "currentUser";

    public static void CreateLoginCookie(HttpServletResponse response, UserEntity user) {
        Cookie loginCookie = new Cookie(USER_NAME_STRING, user.getId().toString());
        loginCookie.setMaxAge(3600);
        response.addCookie(loginCookie);
    }

    public static void RevokeLoginCookie(HttpServletRequest request, HttpServletResponse response) {
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
                if (c.getName().equals(USER_NAME_STRING)) {
                    userId = Long.parseLong(c.getValue());
                    break;
                }
            }
        }

        return userId;
    }
}
