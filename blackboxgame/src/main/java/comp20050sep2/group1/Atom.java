package comp20050sep2.group1;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GraphicAttribute;
import java.awt.font.ShapeGraphicAttribute;
import java.awt.geom.Ellipse2D;

public class Atom {

    Hexagon hexagon;
    boolean isGuess;

    public Atom(Hexagon hexagon, boolean isGuess) {
        this.hexagon = hexagon;
        this.isGuess = isGuess;
    }

    public void drawAtom(Graphics2D g) {
        if(isGuess) {
            g.setColor(Color.white);
        } else {
            g.setColor(Color.red);
        }
        g.fillOval((int) (hexagon.pos.x-(hexagon.side/2)), (int) (hexagon.pos.y-(hexagon.side/2)), (int) hexagon.side, (int) hexagon.side);
    }
}
