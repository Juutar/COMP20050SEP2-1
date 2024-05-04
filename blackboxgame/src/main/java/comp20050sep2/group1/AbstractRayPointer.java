package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import java.awt.*;

abstract public class AbstractRayPointer {

    /**
     * Abstract class for drawing ray pointers on the board
     * @param boardLabelPos Coordinates of label from which the pointer originates
     * @param hexagonCenter Coordinates of the hexagon to which it reaches
     */
    public static void drawRayPointer(Vector2D boardLabelPos, Vector2D hexagonCenter) {
        Graphics2D g = GamePanel.get().graphics;
        g.setColor(Color.white);
        g.drawLine((int) boardLabelPos.x, (int) boardLabelPos.y, (int) hexagonCenter.x, (int) hexagonCenter.y);
        g.setColor(Color.lightGray);
        g.fillOval((int) hexagonCenter.x - 10, (int) hexagonCenter.y - 10, 20, 20);
        g.setColor(Color.white);
    }
}
