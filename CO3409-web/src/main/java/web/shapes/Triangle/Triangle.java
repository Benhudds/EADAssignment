/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes.Triangle;

import com.ArrayUtils;
import java.awt.Polygon;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import static web.shapes.ImageHelper.MIDPOINT;
import static web.shapes.ImageHelper.SCALE;
import static web.shapes.ImageHelper.SCALEPIXELS;
import web.shapes.Line.Line;
import web.shapes.Line.LineQuestions;
import web.shapes.Shape;

@XmlRootElement
public class Triangle extends Shape {

    private final static String[] params = {"Y3", "X3", "Y2", "X2"};
    private final static String name = "triangle";

    @Override
    public String[] getParamNames() {
        return ArrayUtils.join(params, super.getParamNames());
    }
    
    @Override
    public int[] getParams() {
        return ArrayUtils.join(new int[]{x2, y2, x3, y3}, super.getParams());
    }

    private int x2;
    private int y2;
    private int x3;
    private int y3;

    public int getX2() {
        return x2;
    }

    @XmlElement
    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    @XmlElement
    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getX3() {
        return x3;
    }

    @XmlElement
    public void setX3(int x3) {
        this.x3 = x3;
    }

    public int getY3() {
        return y3;
    }

    @XmlElement
    public void setY3(int y3) {
        this.y3 = y3;
    }

    public Triangle() {
        super.name = name;

    }

    public Triangle(int x2, int y2, int x3, int y3, int x, int y) {
        super(x, y);
        super.name = name;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }
    
    public Triangle(int[] params) {
        super(params[4], params[5]);
        super.name = name;
        this.x2 = params[0];
        this.y2 = params[1];
        this.x3 = params[2];
        this.y3 = params[3];
    }

    public double getAnswer(TriangleQuestions question) {
        double ret = 0;
        switch (question) {
            case AREA:
                int xdiff = Math.abs(getX() - x2);
                int xsquare = xdiff * xdiff;
                int ydiff = Math.abs(getY() - y2);
                int ysquare = ydiff * ydiff;

                double len1 = Math.sqrt(xsquare + ysquare);

                xdiff = Math.abs(getX() - x3);
                xsquare = xdiff * xdiff;
                ydiff = Math.abs(getY() - y3);
                ysquare = ydiff * ydiff;

                double len2 = Math.sqrt(xsquare + ysquare);

                xdiff = Math.abs(x2 - x3);
                xsquare = xdiff * xdiff;
                ydiff = Math.abs(y2 - y3);
                ysquare = ydiff * ydiff;

                double len3 = Math.sqrt(xsquare + ysquare);

                if ((len1 + len2) > len3 && (len1 + len3) > len2 && (len2 + len3) > len1) {
                    double s = (len1 + len2 + len3) / 2;
                    ret = Math.sqrt(s * (s - len1) * (s - len2) * (s - len3));
                }
                break;

            case PERIMETER:
                Line line1 = new Line(getX(), getY(), x2, y2);
                Line line2 = new Line(getX(), getY(), x3, y3);
                Line line3 = new Line(x2, y2, x3, y3);

                ret = line1.getAnswer(LineQuestions.LENGTH)
                        + line2.getAnswer(LineQuestions.LENGTH)
                        + line3.getAnswer(LineQuestions.LENGTH);
                break;
            default:
                break;
        }

        return ret;
    }
    
    @Override
    public String validate() {
        String superVal = super.validate();
        if (!superVal.equals("")) {
            return superVal;
        }

        if (x2 < -SCALE) {
            return "X2 must be greater than -" + SCALE;
        }

        if (y2 < -SCALE) {
            return "Y2 must be greater than -" + SCALE;
        }

        if (x2 > SCALE) {
            return "X2 must be less than " + SCALE;
        }

        if (y2 > SCALE) {
            return "Y2 must be less than " + SCALE;
        }

        if (x3 < -SCALE) {
            return "X3 must be greater than -" + SCALE;
        }

        if (y3 < -SCALE) {
            return "Y3 must be greater than -" + SCALE;
        }

        if (x3 > SCALE) {
            return "X3 must be less than " + SCALE;
        }

        if (y3 > SCALE) {
            return "Y3 must be less than " + SCALE;
        }

        Polygon p = new Polygon();
        p.addPoint(MIDPOINT + getX() * SCALEPIXELS, MIDPOINT - getY() * SCALEPIXELS);
        p.addPoint(MIDPOINT + x2 * SCALEPIXELS, MIDPOINT - y2 * SCALEPIXELS);
        p.addPoint(MIDPOINT + x3 * SCALEPIXELS, MIDPOINT - y3 * SCALEPIXELS);

        int xdiff = Math.abs(getX() - x2);
        int xsquare = xdiff * xdiff;
        int ydiff = Math.abs(getY() - y2);
        int ysquare = ydiff * ydiff;

        double len1 = Math.sqrt(xsquare + ysquare);

        xdiff = Math.abs(getX() - x3);
        xsquare = xdiff * xdiff;
        ydiff = Math.abs(getY() - y3);
        ysquare = ydiff * ydiff;

        double len2 = Math.sqrt(xsquare + ysquare);

        xdiff = Math.abs(x2 - x3);
        xsquare = xdiff * xdiff;
        ydiff = Math.abs(y2 - y3);
        ysquare = ydiff * ydiff;

        double len3 = Math.sqrt(xsquare + ysquare);

        if ((len1 + len2) > len3 && (len1 + len3) > len2 && (len2 + len3) > len1) {
            double s = (len1 + len2 + len3) / 2;
            double area = Math.sqrt(s * (s - len1) * (s - len2) * (s - len3));
            if (area == 0) {
                return "This is not a valid triangle, area is 0";
            }
        } else {
            return "This is not a valid triangle, the points cannot be on the same line";
        }

        return "";
    }
}
