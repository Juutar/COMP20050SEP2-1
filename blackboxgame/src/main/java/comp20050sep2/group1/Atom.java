package comp20050sep2.group1;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GraphicAttribute;
import java.awt.font.ShapeGraphicAttribute;
import java.awt.geom.Ellipse2D;

public class Atom {

    Hexagon hexagon;

    public Atom(Hexagon hexagon) {
        this.hexagon = hexagon;
    }

    public void drawAtom(Graphics2D g) {
        g.setColor(Color.white);
        g.fillOval((int) (hexagon.pos.x-(hexagon.side/2)), (int) (hexagon.pos.y-(hexagon.side/2)), (int) hexagon.side, (int) hexagon.side);
    }
}
