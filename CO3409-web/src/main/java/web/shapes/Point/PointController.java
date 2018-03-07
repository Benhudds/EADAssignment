/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes.Point;

import java.io.IOException;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.helpers.LoginHelperBean;
import web.shapes.ImageHelper;
import web.shapes.ShapeServletBase;
import web.shapes.Shape;
import web.shapes.ShapeParams;

@WebServlet(name = "PointController", urlPatterns = {"/draw/point"})
public class PointController extends HttpServlet {

    @EJB
    private LoginHelperBean loginHelper;

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
        String acceptHeader = request.getHeader(ShapeServletBase.ACCEPT_HEADER);

        switch (acceptHeader) {
            case "image/webp,image/apng,image/*,*/*;q=0.8":
                ImageHelper h = null;
                try {
                    ShapeParams shapeParams = ShapeServletBase.validateShape(request, response, Shape.class);
                    if (shapeParams.isValid()) {
                        h = new PointHelper();
                        ((PointHelper) h).Draw((Shape) shapeParams.getShape());
                        response.setContentType("image/jpeg");
                        ServletOutputStream sos = response.getOutputStream();
                        ImageIO.write(h, "jpeg", sos);
                    } else {
                        h = new ImageHelper();
                        h.CreateAxis();
                    }
                } catch (Exception ex) {
                    h = new ImageHelper();
                    h.CreateAxis();
                } finally {
                    response.setContentType("image/jpeg");
                    ServletOutputStream sos = response.getOutputStream();
                    ImageIO.write(h, "jpeg", sos);
                }
                break;
            default:
                ShapeServletBase.processHTMLRequest(request, response, new ShapeParams().setNewShape(new Shape()), false, loginHelper.ValidateRequest(request), loginHelper.ValidateTeacher(request));
                break;
        }

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
        ShapeParams shapeParams = ShapeServletBase.validateShape(request, response, Shape.class);
        ShapeServletBase.doPost(request, response, shapeParams, loginHelper.ValidateRequest(request), loginHelper.ValidateTeacher(request));

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
