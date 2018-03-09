/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

import questions.CircleQuestions;
import com.ArrayUtils;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import static imagehelpers.ImageHelper.SCALE;
import shapes.Shape;

@XmlRootElement
public class Circle extends Shape {

    private final static String[] params = {"Radius"};
    private final static String name = "circle";

    @Override
    public String[] getParamNames() {
        return ArrayUtils.join(params, super.getParamNames());
    }

    @Override
    public int[] getParams() {
        return ArrayUtils.join(new int[]{radius}, super.getParams());
    }

    private int radius;

    public int getRadius() {
        return radius;
    }

    @XmlElement
    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Circle() {
        super.name = name;
    }

    public Circle(int x, int y, int radius) {
        super(x, y);
        super.name = name;
        this.radius = radius;
    }

    public Circle(int[] params) {
        super(params[2], params[1]);
        super.name = name;
        this.radius = params[0];
    }

    public double getAnswer(CircleQuestions question) {
        double ret = 0.0;
        switch (question) {
            case AREA:
                ret = Math.PI * radius * radius;
                break;
            case CIRCUMFERENCE:
                ret = Math.PI * 2 * radius;
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

        if (getX() + radius > SCALE) {
            return "X + radius must be less than " + SCALE;
        }

        if (getY() + radius > SCALE) {
            return "Y + radius must be less than " + SCALE;
        }

        return "";
    }
}
