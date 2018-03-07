/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes.Circle;

import java.awt.Color;
import web.shapes.ImageHelper;

public class CircleHelper extends ImageHelper {
    
    public void Draw(Circle circle) {        
        graphics.setColor(Color.GREEN);
        graphics.fillOval(MIDPOINT + circle.getX() * SCALEPIXELS - circle.getRadius() * SCALEPIXELS,
                MIDPOINT - circle.getY() * SCALEPIXELS - circle.getRadius() * SCALEPIXELS,
                circle.getRadius() * 2 * SCALEPIXELS,
                circle.getRadius() * 2 * SCALEPIXELS);
        CreateAxis(); 
    }
}
