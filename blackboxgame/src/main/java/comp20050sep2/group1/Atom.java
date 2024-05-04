package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import java.awt.*;

public class Atom {

    Color GUESS_COLOR = Color.white;
    Color TRUE_COLOR = Color.red;

    Vector2D pos;
    double radius;
    Color color;
    CircleInfluence circleInfluence;

    /**
     * Constructs an atom
     * @param pos Position of the atom
     * @param radius Radius of the atom
     * @param isGuess Boolean to determine whether the atom is a guess atom or a real one
     */
    public Atom(Vector2D pos, double radius, boolean isGuess) {
        this.pos = pos;
        this.radius = radius;
        this.color = (isGuess ? GUESS_COLOR : TRUE_COLOR);
        this.circleInfluence = new CircleInfluence(pos, radius * 3, color);
    }

    public void drawAtom() {
        Graphics2D g = GamePanel.get().graphics;
        g.setColor(color);
        g.fillOval((int) (pos.x - radius / 2), (int) (pos.y - radius / 2), (int) radius, (int) radius);
        circleInfluence.drawCircleInfluence();
    }
}
