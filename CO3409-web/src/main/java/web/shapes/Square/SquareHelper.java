/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes.Square;

import java.awt.Color;
import web.shapes.ImageHelper;

public class SquareHelper extends ImageHelper {
        
    public void Draw(Square square) {
        graphics.setColor(Color.RED);
        graphics.fillRect(MIDPOINT + square.getX() * SCALEPIXELS,
                MIDPOINT - square.getY() * SCALEPIXELS - square.getSize() * SCALEPIXELS,
                square.getSize() * SCALEPIXELS,
                square.getSize() * SCALEPIXELS);
        CreateAxis();
    }
}
