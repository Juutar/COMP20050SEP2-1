package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import java.awt.*;

public class CircleInfluence {

    Vector2D pos;
    double radius;
    Color color;

    public CircleInfluence(Atom atom, double scale) {
        this.pos = new Vector2D(atom.pos.x - (atom.radius*scale), atom.pos.y - (atom.radius*scale));
        this.radius = atom.radius*3*scale;
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
