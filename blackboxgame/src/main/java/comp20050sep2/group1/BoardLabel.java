package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

import javax.swing.*;
import java.awt.*;

public class BoardLabel extends JLabel {

    Hexagon hexagon;
    int angle;
    Vector2D pos;
    Vector3D rayDirection;
    private final String text;

    /**
     * Constructs a board label bordering the game board
     * @param text The text (number) of the label
     * @param hexagon Hexagon it is associated with
     * @param angle Which angle the label is to the hexagon
     */
    BoardLabel(String text, Hexagon hexagon, int angle) {
        this.text = text;
        this.hexagon = hexagon;
        this.angle = angle;

        this.pos = new Vector2D(hexagon.center().x + Math.sin(Math.toRadians(angle)) * (hexagon.side * 1.2) - 10, //needs to be + because JFrame grid starts from upper left corner
                hexagon.center().y - Math.cos(Math.toRadians(angle)) * (hexagon.side * 1.2) + 5);
        this.rayDirection = new Vector3D();
        rayDirection.setCoorsFromAngle((angle + 180) % 360);
    }

    /**
     *  Write the text out (draw the labels)
     */
    public void writeText() {
        GamePanel.get().graphics.setColor(new Color(255, 150, 150));
        GamePanel.get().graphics.setFont(new Font("Arial", Font.TYPE1_FONT, 15));
        GamePanel.get().graphics.drawString(text, (int) pos.x, (int) pos.y);
    }

    /**
     * @return the position of the label
     */
    public Vector2D center() {
        return new Vector2D(hexagon.center().x + Math.sin(Math.toRadians(angle)) * (hexagon.side * 1.2),
                hexagon.center().y - Math.cos(Math.toRadians(angle)) * (hexagon.side * 1.2));
    }

}
