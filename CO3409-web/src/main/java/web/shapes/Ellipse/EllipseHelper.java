/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes.Ellipse;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import web.shapes.ImageHelper;

public class EllipseHelper extends ImageHelper {

    public void Draw(Ellipse ellipse) {
        graphics.setColor(Color.MAGENTA);
        graphics.fill(new Ellipse2D.Double(MIDPOINT + ellipse.getX() * SCALEPIXELS - (ellipse.getXRadius() * SCALEPIXELS),
                MIDPOINT - ellipse.getY() * SCALEPIXELS - ellipse.getYRadius() * SCALEPIXELS,
                ellipse.getXRadius() * SCALEPIXELS * 2,
                ellipse.getYRadius() * SCALEPIXELS * 2));

        CreateAxis();
    }
}