package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import java.awt.*;

public class AbstractCross {

    public static final Color color = Color.black;
    public static final Stroke stroke = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    public static void drawCross(Vector2D pos, double radius) {
        Graphics2D g = GamePanel.get().graphics;
        Stroke initialStroke = g.getStroke();

        g.setColor(color);
        g.setStroke(stroke);
        g.drawLine((int) (pos.x - radius/4), (int) (pos.y - radius/4), (int) (pos.x + radius/4), (int) (pos.y + radius/4));
        g.drawLine((int) (pos.x + radius/4), (int) (pos.y - radius/4), (int) (pos.x - radius/4), (int) (pos.y + radius/4));
        g.setStroke(initialStroke);
    }
}
