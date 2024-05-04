package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import java.awt.*;

public class AbstractTick {

    public static final Color color = Color.black;
    public static final Stroke stroke = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    /**
     * Drawing a tick or check on the board when the correct position is guessed
     * @param pos Position of the shape
     * @param radius Radius of the shape
     */
    public static void drawTick(Vector2D pos, double radius) {
        Graphics2D g = GamePanel.get().graphics;
        Stroke initialStroke = g.getStroke();

        g.setColor(color);
        g.setStroke(stroke);
        g.drawOval((int) (pos.x - radius / 2), (int) (pos.y - radius / 2), (int) radius, (int) radius);

        g.setStroke(initialStroke);
    }
}
