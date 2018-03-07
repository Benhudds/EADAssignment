/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.helpers;

import com.JWTHelper;
import ejb.UserEntity;
import ejb.UserEntityFacade;
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
            return true;
        } else {
            return false;
        }
    }

    public boolean ValidateRequest(HttpServletRequest request) {
        Long user = CookieHelper.GetUserIdFromCookie(request);

        if (user != null && userEntityFacade.find(user) != null) {
            return true;
        }

        return false;
    }

    public boolean ValidateTeacher(HttpServletRequest request) {
        Long user = CookieHelper.GetUserIdFromCookie(request);
        UserEntity ue;

        if (user != null) {
            ue = userEntityFacade.find(user);
            if (ue != null) {
                return ue.isTeacher();
            }
        }

        return false;
    }
}
