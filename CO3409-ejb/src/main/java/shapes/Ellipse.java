/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

import questions.EllipseQuestions;
import com.ArrayUtils;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import static imagehelpers.ImageHelper.SCALE;
import shapes.Shape;

@XmlRootElement
public class Ellipse extends Shape {

    private final static String[] params = {"Y Radius", "X Radius"};
    private final static String name = "ellipse";

    @Override
    public String[] getParamNames() {
        return ArrayUtils.join(params, super.getParamNames());
    }

    @Override
    public int[] getParams() {
        return ArrayUtils.join(new int[]{xradius, yradius}, super.getParams());
    }

    private int xradius;
    private int yradius;

    public int getXRadius() {
        return xradius;
    }

    @XmlElement
    public void setXRadius(int width) {
        this.xradius = width;
    }

    public int getYRadius() {
        return yradius;
    }

    @XmlElement
    public void setYRadius(int height) {
        this.yradius = height;
    }

    public Ellipse() {
        super.name = name;
    }

    public Ellipse(int x, int y, int width, int height) {
        super(x, y);
        super.name = name;
        this.xradius = width;
        this.yradius = height;
    }

    public Ellipse(int[] params) {
        super(params[2], params[3]);
        super.name = name;
        this.yradius = params[1];
        this.xradius = params[0];
    }

    public double getAnswer(EllipseQuestions question) {
        double ret = 0;
        switch (question) {
            case AREA:
                ret = Math.PI * xradius * yradius;
                break;
            case PERIMETER:
                ret = Math.PI * 2 * Math.sqrt((xradius * xradius + yradius * yradius) / 2);
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

        if (xradius <= 0) {
            return "Width must be positive";
        }

        if (yradius <= 0) {
            return "Height must be positive";
        }

        if (getX() - (double) xradius < -SCALE) {
            return "X - width must be greater than -" + SCALE;
        }

        if (getY() - (double) yradius < -SCALE) {
            return "Y - height must be greater than -" + SCALE;
        }

        if (getX() + (double) xradius > SCALE) {
            return "X + width must be less than " + SCALE;
        }

        if (getY() + (double) yradius > SCALE) {
            return "Y + height must be less than " + SCALE;
        }

        return "";
    }
}
