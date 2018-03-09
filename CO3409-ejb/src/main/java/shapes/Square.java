/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

import questions.SquareQuestions;
import com.ArrayUtils;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import static imagehelpers.ImageHelper.SCALE;

@XmlRootElement
public class Square extends Shape {

    private final static String[] params = {"Size"};
    private final static String name = "square";

    @Override
    public String[] getParamNames() {
        return ArrayUtils.join(params, super.getParamNames());
    }

    @Override
    public int[] getParams() {
        return ArrayUtils.join(new int[]{size}, super.getParams());
    }

    private int size;

    public int getSize() {
        return size;
    }

    @XmlElement
    public void setSize(int width) {
        this.size = width;
    }

    public Square() {
        super.name = name;
    }

    public Square(int x, int y, int size) {
        super(x, y);
        super.name = name;
        this.size = size;
    }

    public Square(int[] params) {
        super(params[1], params[2]);
        super.name = name;
        this.size = params[0];
    }

    public double getAnswer(SquareQuestions question) {
        double ret = 0;
        switch (question) {
            case AREA:
                ret = size * size;
                break;
            case PERIMETER:
                ret = size * 4;
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

        if (getX() + size > SCALE) {
            return "X + size must be less than " + SCALE;
        }

        if (getY() + size > SCALE) {
            return "Y + size must be less than " + SCALE;
        }

        return "";
    }
}
