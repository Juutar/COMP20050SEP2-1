package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

import java.awt.*;
import java.awt.geom.Line2D;

public class Hexagon {
    
    double side;
    Vector2D pos;
    Vector3D coors;

    Atom guessAtom = null;
    Atom trueAtom = null;

    int pointableSides;
    BoardLabel[] boardLabels;

    Hexagon(double side, Vector2D pos, Vector3D coors){
        this.pos = pos;
        this.side = side;
        this.coors = coors;
        this.pointableSides = 0;
        this.boardLabels = null;
    }

    public Vector2D center() {
        return new Vector2D(pos);
    }

    public void drawHexagon(){
        
        double a1 = pos.x;
        double a2 = pos.y - Math.sqrt(3) * side * 0.5 * (1.0 / Math.cos(Math.toRadians(30)));

        double b1 = a1 - (side * Math.cos(Math.toRadians(30)));
        double b2 = a2 + (side * Math.sin(Math.toRadians(30)));

        double c1 = a1 + (side * Math.cos(Math.toRadians(30)));       //pi/6 = 0.523599
        double c2 = a2 + (side * Math.sin(Math.toRadians(30)));

        double d1 = b1;
        double d2 = b2 + side;

        double e1 = c1;
        double e2 = c2 + side;

        double f1 = a1;
        double f2 = a2 + 2 * side;

        Graphics2D g = GamePanel.get().graphics;
        g.setColor(Color.gray);
        g.draw(new Line2D.Double(a1, a2, b1, b2));    //top left
        g.draw(new Line2D.Double(a1, a2, c1, c2));    //top right
        g.draw(new Line2D.Double(b1, b2, d1, d2));
        g.draw(new Line2D.Double(c1, c2, e1, e2));
        g.draw(new Line2D.Double(f1, f2, d1, d2));
        g.draw(new Line2D.Double(f1, f2, e1, e2));
        g.drawString(coors.toString(), (int) pos.x, (int) pos.y);

        if (hasTrueAtom() && GamePanel.get().board.trueAtomsVisible) {
            trueAtom.drawAtom();
        }

        if (hasGuessAtom()){
            guessAtom.drawAtom();
        }

        if (GamePanel.get().board.evaluate) {
            if (isCorrect()) {
                AbstractTick.drawTick(pos, side/2);
            }
            if (isIncorrect()) {
                AbstractCross.drawCross(pos, side);
            }
        }

    }

    public void placeTrueAtom() { trueAtom = new Atom(center(), side, false); }

    public boolean hasGuessAtom() {
        return guessAtom != null;
    }
    public boolean hasTrueAtom() { return trueAtom != null; }

    public boolean toggleGuess() {
        if (guessAtom == null) {
            guessAtom = new Atom(center(), side, true);
        } else {
            guessAtom = null;
        }
        return guessAtom != null;
    }

    public void reposition(Vector2D pos) {
        this.pos = pos;
    }

    public boolean isCorrect() { return hasGuessAtom() && hasTrueAtom(); }
    public boolean isIncorrect() { return (hasGuessAtom() && !hasTrueAtom()) || (!hasGuessAtom() && hasTrueAtom()); }

}
