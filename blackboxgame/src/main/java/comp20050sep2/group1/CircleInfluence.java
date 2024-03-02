package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import java.awt.*;

public class CircleInfluence {

    Vector2D pos;
    double radius;
    Color color;

    public CircleInfluence(Vector2D pos, double radius, Color color) {
        this.pos = pos;
        this.radius = radius;
        this.color = color;
    }

    public void drawCircleInfluence() {
        Graphics2D g = GamePanel.get().graphics;
        g.setColor(color);
        Stroke initialStroke = g.getStroke();
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[] { 10.0F }, 0.0F));
        g.drawOval((int) (pos.x - radius / 2), (int) (pos.y - radius / 2), (int) radius, (int) radius);
        g.setStroke(initialStroke);
    }
}
