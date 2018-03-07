/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.shapes.Line;

import java.awt.Color;
import web.shapes.ImageHelper;

public class LineHelper extends ImageHelper {

    public void Draw(Line line) {
        graphics.setColor(Color.ORANGE);
        graphics.drawLine(MIDPOINT + line.getX() * SCALEPIXELS,
                MIDPOINT - line.getY() * SCALEPIXELS,
                MIDPOINT + line.getX2() * SCALEPIXELS,
                MIDPOINT - line.getY2() * SCALEPIXELS);
        
        CreateAxis();
    }
}
