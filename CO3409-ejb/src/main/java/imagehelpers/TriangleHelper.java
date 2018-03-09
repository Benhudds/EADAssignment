/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagehelpers;

import shapes.Triangle;
import java.awt.Color;
import java.awt.Polygon;

public class TriangleHelper extends ImageHelper {

    public void Draw(Triangle triangle) {
        graphics.setColor(Color.CYAN);

        Polygon p = new Polygon();
        p.addPoint(MIDPOINT + triangle.getX() * SCALEPIXELS, MIDPOINT - triangle.getY() * SCALEPIXELS);
        p.addPoint(MIDPOINT + triangle.getX2() * SCALEPIXELS, MIDPOINT - triangle.getY2() * SCALEPIXELS);
        p.addPoint(MIDPOINT + triangle.getX3() * SCALEPIXELS, MIDPOINT - triangle.getY3() * SCALEPIXELS);

        graphics.fillPolygon(p);

        CreateAxis();
    }
}
