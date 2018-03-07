/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes.Line;

import com.ArrayUtils;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import static web.shapes.ImageHelper.SCALE;
import web.shapes.Shape;

@XmlRootElement
public class Line extends Shape {

    private final static String[] params = {"Y2", "X2"};
    private final static String name = "line";

    @Override
    public String[] getParamNames() {
        return ArrayUtils.join(params, super.getParamNames());
    }
    
    @Override
    public int[] getParams() {
        return ArrayUtils.join(new int[]{x2, y2}, super.getParams());
    }
    
    private int x2;
    private int y2;

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

    public Line() {
        super.name = name;
    }

    public Line(int x2, int y2, int x, int y) {
        super(x, y);
        super.name = name;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public Line(int[] params) {
        super(params[2], params[3]);
        super.name = name;
        this.x2 = params[1];
        this.y2 = params[0];
    }

    public double getAnswer(LineQuestions question) {
        double ret = 0;
        switch (question) {
            case LENGTH:
                int xdiff = x2 - getX();
                int ydiff = y2 - getY();
                ret = Math.sqrt(xdiff * xdiff + ydiff * ydiff);
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
        
        if (getX() == x2 && getY() == y2) {
            return "Both points must be different to draw a line";
        }
        
        return "";
    }
}
