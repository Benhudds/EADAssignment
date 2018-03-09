/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import static imagehelpers.ImageHelper.SCALE;

@XmlRootElement
public class Shape implements Serializable {

    private final static String[] params = {"Y", "X"};

    public String[] getParamNames() {
        return params;
    }

    public int[] getParams() {
        return new int[]{y, x};
    }

    protected String name;

    public String getName() {
        return name;
    }
    
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    @XmlElement
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    @XmlElement
    public void setY(int y) {
        this.y = y;
    }

    public Shape() {
        x = 0;
        y = 0;
    }

    public Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public String validate() {
        if (x < -SCALE) {
            return "X must be greater than -" + SCALE;
        }

        if (y < -SCALE) {
            return "Y must be greater than -" + SCALE;
        }

        if (x > SCALE) {
            return "X must be less than " + SCALE;
        }

        if (y > SCALE) {
            return "Y must be less than " + SCALE;
        }
        
        return "";
    }
}
