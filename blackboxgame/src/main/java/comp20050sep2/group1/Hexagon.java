package comp20050sep2.group1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

public class Hexagon {

    public Vector3D influenceVector;
    double side;
    Vector2D pos;
    Atom guessAtom = null;
    Atom trueAtom = null;
    int pointableSides;
    BoardLabel[] boardLabels;
    ArrayList<Hexagon> neighbors;

    /**
     * Constructs a hexagon
     * @param side Side length
     * @param pos Position of the hexagon
     */
    Hexagon(double side, Vector2D pos) {
        this.pos = pos;
        this.side = side;
        this.pointableSides = 0;
        this.boardLabels = null;
        this.influenceVector = new Vector3D();
    }

    /**
     * Returns the coordinates of the hexagon
     * @return the hexagon coordinates
     */
    public Vector2D center() {
        return new Vector2D(pos);
    }

    /**
     * Function to draw hexagon
     */
    public void drawHexagon() {

        double a1 = pos.x;
        double a2 = pos.y - Math.sqrt(3) * side * 0.5 * (1.0 / Math.cos(Math.toRadians(30)));

        double b1 = a1 - (side * Math.cos(Math.toRadians(30)));
        double b2 = a2 + (side * Math.sin(Math.toRadians(30)));

        double c1 = a1 + (side * Math.cos(Math.toRadians(30))); // pi/6 = 0.523599
        double c2 = a2 + (side * Math.sin(Math.toRadians(30)));

        double d1 = b1;
        double d2 = b2 + side;

        double e1 = c1;
        double e2 = c2 + side;

        double f1 = a1;
        double f2 = a2 + 2 * side;

        Graphics2D g = GamePanel.get().graphics;
        g.setColor(Color.gray);
        g.draw(new Line2D.Double(a1, a2, b1, b2)); // top left
        g.draw(new Line2D.Double(a1, a2, c1, c2)); // top right
        g.draw(new Line2D.Double(b1, b2, d1, d2));
        g.draw(new Line2D.Double(c1, c2, e1, e2));
        g.draw(new Line2D.Double(f1, f2, d1, d2));
        g.draw(new Line2D.Double(f1, f2, e1, e2));
        //g.drawString(GamePanel.get().board.getHexes().getKey(this).toString(), (int) pos.x, (int) pos.y);

        if (hasTrueAtom() && GamePanel.get().board.trueAtomsVisible) {
            trueAtom.drawAtom();
        }

        if (hasGuessAtom()) {
            guessAtom.drawAtom();
        }

        if (GamePanel.get().board.evaluate) {
            if (isCorrect()) {
                AbstractTick.drawTick(pos, side / 2);
            }
            if (isIncorrect()) {
                AbstractCross.drawCross(pos, side);
            }
        }
    }

    /**
     * Function to place a true atom on this hexagon
     */
    public void placeTrueAtom() {
        trueAtom = new Atom(center(), side, false);
    }

    /**
     * Check to see if this hexagon has the atom guessed by user
     * @return if this hexagon has the atom guessed by user
     */
    public boolean hasGuessAtom() {
        return guessAtom != null;
    }

    /**
     * Check to see if this hexagon has the true atom
     * @return if this hexagon has the true atom
     */
    public boolean hasTrueAtom() {
        return trueAtom != null;
    }

    /**
     * Remove atom if present add atom if absent by user
     * @return reference to atom if a new atom is created or deleted
     */
    public boolean toggleGuess() {
        if (guessAtom == null) {
            guessAtom = new Atom(center(), side, true);
        } else {
            guessAtom = null;
        }
        return guessAtom != null;
    }

    /**
     * Repositions a hexagon
     * @param pos New position
     */
    public void reposition(Vector2D pos) {
        this.pos = pos;
    }

    /**
     * Checks if this hexagon has the atom guessed by the user
     * @return if this hexagon has the atom guessed by the user
     */
    public boolean isCorrect() {
        return hasGuessAtom() && hasTrueAtom();
    }

    /**
     * Checks if this hexagon does not have the atom guessed by the user
     * @return if this hexagon does not have the atom guessed by the user
     */
    public boolean isIncorrect() {
        return (hasGuessAtom() && !hasTrueAtom()) || (!hasGuessAtom() && hasTrueAtom());
    }

    /**
     * Returns board labels associated with hexagon based on coordinates
     * @param coords against which to compare
     * @return Board label found or null if it doesn't have any
     */
    public BoardLabel getBoardLabelAtCoords(Vector3D coords) {
        if (boardLabels == null) {
            return null;
        }
        for (BoardLabel b : boardLabels) {
            if (Vector3D.opposite(b.rayDirection).equals(coords)) {
                return b;
            }
        }
        return null;
    }

}
