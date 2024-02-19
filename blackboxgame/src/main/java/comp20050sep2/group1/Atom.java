package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GraphicAttribute;
import java.awt.font.ShapeGraphicAttribute;
import java.awt.geom.Ellipse2D;

public class Atom {

    Vector2D pos;
    double radius;
    Color color;
    CircleInfluence circleInfluence;

    public Atom(Hexagon hexagon, boolean isGuess) {
        this.pos = new Vector2D(hexagon.pos.x-(hexagon.side/2), hexagon.pos.y-(hexagon.side/2));
        this.radius = hexagon.side;
        this.color = (isGuess ? Color.white : Color.red);
        this.circleInfluence = new CircleInfluence(this);
    }

    public void drawAtom(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int) pos.x, (int) pos.y, (int) radius, (int) radius);
        circleInfluence.drawCircleInfluence(g);
    }
}
