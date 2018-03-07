/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageHelper extends BufferedImage {

    public static final int SIZE = 520;
    public static final int MIDPOINT = 260;
    public static final int SCALEPIXELS = 25;
    public static final int SCALE = 10;

    protected Graphics2D graphics;

    public ImageHelper() {
        super(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        CreateGraphics();
    }

    public final void CreateGraphics() {
        graphics = this.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, SIZE, SIZE);
    }

    public void CreateAxis() {
        graphics.setColor(Color.BLACK);

        // Draw the axis lines
        graphics.fillRect(0, MIDPOINT, SIZE, 2);
        graphics.fillRect(MIDPOINT, 0, 2, SIZE);

        int offset = (SIZE % SCALEPIXELS / 2);

        // Draw the numbers and number lines
        for (int i = offset; i < SIZE; i += SCALEPIXELS) {
            int val = (i / SCALEPIXELS) - SCALE;
            int fontW = graphics.getFontMetrics().stringWidth(String.valueOf(val));
            int fontH = graphics.getFontMetrics().getHeight();

            // Draw the number lines
            graphics.fillRect(i, MIDPOINT, 2, SCALE);
            graphics.fillRect(MIDPOINT - SCALE, i, SCALE, 2);

            if (val != 0) {
                // Draw the numbers
                graphics.drawString(String.valueOf(val), i - fontW / 2, MIDPOINT + SCALE * 2);
                graphics.drawString(String.valueOf(-val), MIDPOINT - SCALE * 3, i + fontH / 2);
            }
        }
    }
}
