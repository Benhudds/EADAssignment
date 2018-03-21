/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.helpers;

import com.JWTHelper;
import ejb.UserEntity;
import ejb.UserEntityFacade;
import io.jsonwebtoken.Claims;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.CookieHelper;

@Singleton
public class LoginHelperBean {

    private final static String authHeader = "Authorization";
    public final static String usernameParam = "Username";
    public final static String passwordParam = "Password";

    @EJB
    private UserEntityFacade userEntityFacade;

    private String CreateToken(UserEntity ue) {
        return JWTHelper.createJWT(ue.getId().toString());
    }

    private Long ParseToken(String token) {
        return new Long(JWTHelper.parseJWT(token).getSubject());
    }

    public boolean Login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter(usernameParam);
        String password = request.getParameter(passwordParam);
        UserEntity user = null;

        List users = userEntityFacade.findAll();
        for (Object e : users) {
            UserEntity ue = (UserEntity) e;

            if (ue.getUserName().equals(username)) {
                user = ue;
                break;
            }
        }

        if (user != null && user.getPassword().equals(password)) {
            CookieHelper.CreateLoginCookie(response, user);
            response.setHeader(authHeader, JWTHelper.createJWT(user.getId().toString()));
            return true;
        } else {
            return false;
        }
    }

    public boolean ValidateRequest(HttpServletRequest request) {

        String header = request.getHeader(authHeader);
        if (header != null) {
            Claims jwt = JWTHelper.parseJWT(request.getHeader(authHeader));
            String id = jwt.getSubject();
            System.out.println(id);
            if (id != null) {
                if (userEntityFacade.find(Long.valueOf(id)) != null) {
                    return true;
                }
            }
        }

        Long user = CookieHelper.GetUserIdFromCookie(request);
        System.out.println("user = " + user);
        if (user != null && userEntityFacade.find(user) != null) {
            return true;
        }

        return false;
    }

    public boolean ValidateTeacher(HttpServletRequest request) {
        UserEntity ue;
        
        String header = request.getHeader(authHeader);
        if (header != null) {
            System.out.println(header);
            Claims jwt = JWTHelper.parseJWT(request.getHeader(authHeader));
            String id = jwt.getSubject();
            if (id != null) {
                System.out.println(id);
                ue = userEntityFacade.find(Long.valueOf(id));
                if (ue != null) {
                    return ue.isTeacher();
                }
            }
        }
        
        Long user = CookieHelper.GetUserIdFromCookie(request);
        if (user != null) {
            ue = userEntityFacade.find(user);
            if (ue != null) {
            System.out.println(ue.isTeacher());
                return ue.isTeacher();
            }
        }

        return false;
    }
}
