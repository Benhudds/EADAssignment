/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes.Point;

import javax.xml.bind.annotation.XmlRootElement;
import web.shapes.Shape;

@XmlRootElement
public class Point extends Shape {

    private final static String name = "point";

    public Point() {
        super.name = name;
    }

    public Point(int x, int y) {
        super(x, y);
        super.name = name;
    }

    public Point(int[] params) {
        super(params[1], params[0]);
        super.name = name;
    }
}
