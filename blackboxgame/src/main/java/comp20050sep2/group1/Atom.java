package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import java.awt.*;

public class Atom {

    Vector2D pos;
    double radius;
    Color color;
    CircleInfluence circleInfluence;
    double guessScale = 1.5;

    public Atom(Hexagon hexagon, boolean isGuess) {
        this.pos = new Vector2D(hexagon.pos.x - (isGuess ? (hexagon.side/2) : (hexagon.side/2) * guessScale),
                                hexagon.pos.y - (isGuess ? (hexagon.side/2) : (hexagon.side/2) * guessScale));
        this.radius = (isGuess ? hexagon.side : hexagon.side * 1.5);
        this.color = (isGuess ? Color.white : Color.red);
        this.circleInfluence = new CircleInfluence(this, hexagon);
    }

    public void drawAtom(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int) pos.x, (int) pos.y, (int) radius, (int) radius);
        circleInfluence.drawCircleInfluence(g);
    }
}
