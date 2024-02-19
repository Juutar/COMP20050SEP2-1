package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import java.awt.*;

public class CircleInfluence {

    Vector2D pos;
    double radius;
    Color color;

    public CircleInfluence(Atom atom) {
        this.pos = new Vector2D(atom.pos.x - (atom.radius*1.1), atom.pos.y - (atom.radius*1.1));
        this.radius = atom.radius*3.2;
        this.color = atom.color;
    }

    public void drawCircleInfluence(Graphics2D g) {
        g.setColor(color);
        g.drawOval((int) pos.x, (int) pos.y, (int) radius, (int) radius);
    }
}
