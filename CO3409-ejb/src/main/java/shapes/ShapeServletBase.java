/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

import shapes.Shape;
import com.JSONHelper;
import com.XMLHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import web.ServletBase;

public class ShapeServletBase extends ServletBase {
    public static ShapeParams validateShape(HttpServletRequest request, HttpServletResponse response, Class shapeClass) throws IOException {
        try {
            Method val = shapeClass.getMethod("getParamNames", null);

            String[] p = ((Shape) (shapeClass.newInstance())).getParamNames();
            IntParams params = ShapeServletBase.GetIntParams(request, response, p);

            if (!params.isValid()) {
                System.out.println(params.getValidateString());
                return new ShapeParams().setNewValidateString(params.getValidateString()).setNewShape((Shape) shapeClass.newInstance());
            }

            Constructor ctor = shapeClass.getDeclaredConstructor(int[].class);
            Shape shape = (Shape) ctor.newInstance(params.getParams());

            String validation = shape.validate();

            if (!validation.equals("")) {
                return new ShapeParams().setNewValidateString(validation).setNewShape(shape);
            }

            return new ShapeParams().setNewShape(shape);
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            return new ShapeParams().setNewValidateString("An internal error ocurred");
        }
    }

    public static void doPost(HttpServletRequest request, HttpServletResponse response, ShapeParams params, boolean loggedIn, boolean teacher)
            throws ServletException, IOException {
        ServletOutputStream sos = null;

        try {
            String acceptHeader = request.getHeader(ShapeServletBase.ACCEPT_HEADER);

            switch (acceptHeader) {
                case "application/json":
                    response.setContentType("application/json");
                    JSONHelper.WriteToServletOutputStream(response.getOutputStream(), params.getShape());
                    break;
                case "application/xml":
                    response.setContentType("application/xml");
                    XMLHelper.WriteToServletOutputStream(response.getOutputStream(), params.getClass(), params.getShape());
                    break;
                default:
                    try {
                        processHTMLRequest(request, response, params, true, loggedIn, teacher);
                    } catch (IOException ex) {
                        System.out.println("Exception = " + ex.getMessage());
                        response.sendRedirect(request.getContextPath() + "/" + params.getShape().getName());
                    }
                    break;
            }
        } catch (IOException | JAXBException ex) {
            System.out.println("Exception = " + ex.getMessage());
            response.sendError(500, "An internal error ocurred");
        } finally {
            if (sos != null) {
                sos.close();
            }
        }
    }

    public static void processHTMLRequest(HttpServletRequest request, HttpServletResponse response, ShapeParams shapeParams, boolean post,  boolean loggedIn, boolean teacher) throws IOException {
        if (request.getParameter("btn") != null && !post) {
            response.sendRedirect(request.getContextPath() + "/" + shapeParams.getShape().getName());
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            ShapeServletBase.PrintHead(out);
            ShapeServletBase.PrintBody(request.getContextPath(), loggedIn, teacher, out);
            out.println("<body>");
            out.println("<h1>Draw a " + shapeParams.getShape().getName() + "</h1>");

            if (post) {
                if (!shapeParams.isValid()) {
                    out.println("<h3>" + shapeParams.getValidateString() + "</h3>");
                }
                out.println("<img src='" + CreateImgUrl(request.getContextPath(), shapeParams.getShape()) + "'>");
            } else {
                out.println("<img src='" + request.getContextPath() + "/" + shapeParams.getShape().getName() + "'>");
            }

            String[] p = shapeParams.getShape().getParamNames();
            ShapeServletBase.PrintPostForm(out, "Draw", request.getContextPath() + "/" + shapeParams.getShape().getName(), p);
            ShapeServletBase.EndBody(out);
            out.close();
        }
    }

    public static String CreateImgUrl(String baseUrl, Shape shape) {
        String imageUrl = baseUrl + "/" + shape.getName() + "?";
        for (int i = 0; i < shape.getParamNames().length; i++) {
            imageUrl += shape.getParamNames()[i] + "=" + shape.getParams()[i];

            if (i != shape.getParamNames().length - 1) {
                imageUrl += "&";
            }
        }
        return imageUrl;
    }

    public static IntParams GetIntParams(HttpServletRequest request, HttpServletResponse response, String[] params) throws IOException {
        int[] intParams = new int[params.length];
        try {
            System.out.println("getparams");
            for (int i = 0; i < params.length; i++) {
                String urlParam = request.getParameter(params[i]);
                System.out.println("param " + params[i]);
                System.out.println("urlParam " + urlParam);
                if (urlParam.equals("")) {
                    System.out.println("empty");
                    return new IntParams().setNewValidateString(params[i] + " must not be empty");
                }

                try {
                    intParams[i] = Integer.parseInt(urlParam);
                } catch (NumberFormatException e) {
                    System.out.println("not int");
                    return new IntParams().setNewValidateString(params[i] + " is not an integer");
                }
            }

            System.out.println("returning");
            return new IntParams().setNewParams(intParams);
        } catch (Exception ex) {
            System.out.println("Exception = " + ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
            return new IntParams().setNewValidateString("An internal error ocurred");
        }
    }
}
