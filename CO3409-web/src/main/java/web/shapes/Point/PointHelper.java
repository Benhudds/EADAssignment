/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes.Point;

import java.awt.Color;
import web.shapes.ImageHelper;
import web.shapes.Shape;

public class PointHelper extends ImageHelper {

    public void Draw(Shape shape) {
        graphics.setColor(Color.BLUE);
        graphics.drawLine(MIDPOINT + shape.getX() * SCALEPIXELS - SCALE,
                MIDPOINT - shape.getY() * SCALEPIXELS - SCALE,
                MIDPOINT + shape.getX() * SCALEPIXELS + SCALE,
                MIDPOINT - shape.getY() * SCALEPIXELS + SCALE);
        graphics.drawLine(MIDPOINT + shape.getX() * SCALEPIXELS - SCALE,
                MIDPOINT - shape.getY() * SCALEPIXELS + SCALE,
                MIDPOINT + shape.getX() * SCALEPIXELS + SCALE,
                MIDPOINT - shape.getY() * SCALEPIXELS - SCALE);
        CreateAxis();
    }
}
