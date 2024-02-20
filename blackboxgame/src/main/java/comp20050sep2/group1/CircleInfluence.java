package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import java.awt.*;

public class CircleInfluence {

    Vector2D pos;
    double radius;
    Color color;

    public CircleInfluence(Atom atom, Hexagon hex) {
        this.pos = new Vector2D(hex.pos.x - 1.6*hex.side, hex.pos.y - 1.6*hex.side);
        this.radius = hex.side*3.2;
        this.color = atom.color;
    }

    public void drawCircleInfluence(Graphics2D g) {
        g.setColor(color);
        Stroke initialStroke = g.getStroke();
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[]{10.0F}, 0.0F));
        g.drawOval((int) pos.x, (int) pos.y, (int) radius, (int) radius);
        g.setStroke(initialStroke);
    }
}
